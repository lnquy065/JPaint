package jpaint;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import jpaint.shape2D.Shape2D;

public class ImagePanel extends JLayeredPane implements Serializable{

    public File path=null;
    private String fname;
    private ArrayList<LayerPanel> layersList = new ArrayList();  //Danh sach cac Layer
    private TransparentPane transparent;                        //Tam nen Trong suot
    private int width = 0, height = 0, numLay = 1;              //CHieu dai, rong cua Anh (Ly thuyet)
    private int rW = 0, rH = 0;                                      //Chieu dai rong cua Anh (Thuc Te) co Zoom
    private int zoom = 1;
    private JPanel viewPort;
    private JScrollPane scrollPane;
    transient private GridPanel gridPane;
    private LayerPanel tempLay;
    private MainForm mf;
    private LayerPanel curLay;
    transient GroupLayout viewPortLayout;

    public void updateZoom(int zoom) {
        this.zoom = zoom;
        this.setNewSize(width, height);
        for (LayerPanel dp : layersList) {
            dp.updateZoom(zoom);
        }
    }

    public ImagePanel(String name, MainForm mf) {
        super();
        this.fname=name;
        this.setBackground(Color.blue);
        transparent = new TransparentPane();
        gridPane = new GridPanel();

        tempLay = new LayerPanel();
                tempLay.setLayerName("templayer");
        this.add(gridPane, JLayeredPane.MODAL_LAYER);
        this.add(tempLay, JLayeredPane.MODAL_LAYER);
        this.add(transparent, JLayeredPane.DEFAULT_LAYER);
        this.setObServer(mf);
        DefaultListModel<ListItem> dlm = (DefaultListModel<ListItem>) mf.getListLayer().getModel();
        dlm.clear();
        setViewPort(mf.getViewPort(), mf.getScrollPanel());
        mf.getLbImagePos().setEnabled(true);
        mf.getLbImageSize().setEnabled(true);
        mf.showLayerToolNew();
    }

    public void setObServer(MainForm mf) {
        this.mf = mf;
        gridPane.setObServer(mf);
    }

    public void addNewLayer(String lname, int mode) {
        LayerPanel dp = new LayerPanel();
        String fname = lname.equals("") == false ? lname : "Layer " + (numLay - 1);

        dp.createNew(this.width, this.height, this.zoom, fname, mode);

        DefaultListModel<ListItem> dlm = (DefaultListModel<ListItem>) mf.getListLayer().getModel();
        ListItem nitem = new ListItem(true, dp);
        //if (lname.equals("Default")) {
        //    nitem.setSelected(true);
       // }
        
        if (layersList.isEmpty()) {
            mf.getListLayer().setSelectedIndex(0);
        }
        for(int i=0;i<dlm.size();i++) {
           if ( dlm.get(i).isSelected() ) {
               dlm.get(i).setSelected(false);
           }
        }
        nitem.setSelected(true);
        dlm.add(0, nitem);
        mf.getListLayer().setSelectedValue(nitem, true);
        layersList.add(dp);
        curLay = dp;
        //Chon layer moi them vao
        
        this.add(dp, new Integer(numLay++));
    }
    
    public void addNewLayer(LayerPanel dp) {
        String lname = dp.getLayerName();
        String fname = lname.equals("") == false ? lname : "Layer " + (numLay - 1);
        //dp.updateZoom(zoom);

        DefaultListModel<ListItem> dlm = (DefaultListModel<ListItem>) mf.getListLayer().getModel();
        ListItem nitem = new ListItem(true, dp);
        //if (lname.equals("Default")) {
        //    nitem.setSelected(true);
       // }
        
        if (layersList.isEmpty()) {
            mf.getListLayer().setSelectedIndex(0);
        }
        for(int i=0;i<dlm.size();i++) {
           if ( dlm.get(i).isSelected() ) {
               dlm.get(i).setSelected(false);
           }
        }
        nitem.setSelected(true);
        dlm.add(0, nitem);
        mf.getListLayer().setSelectedValue(nitem, true);
        layersList.add(dp);
        curLay = dp;
        //Chon layer moi them vao
        
        this.add(dp, new Integer(numLay++));        
    }

    public void removeLayer(ListItem lp) {
        if (lp.getLayer().getLayerName().equals("Default")) {
            return;
        }
        layersList.remove(lp.getLayer());
        curLay = this.getLayer("Default");
        this.remove(lp.getLayer());
        this.repaint();
        //Cap nhat list layer
        DefaultListModel<ListItem> dlm = (DefaultListModel<ListItem>) mf.getListLayer().getModel();
        dlm.removeElement(lp);
        mf.getListLayer().setSelectedIndex(mf.getListLayer().getLastVisibleIndex());
        mf.getListLayer().getSelectedValue().setSelected(true);
        mf.getListLayer().repaint();
    }

    public void replaceLayer(LayerPanel nLp) {
        for (LayerPanel lp : layersList) {
            if (lp.getLayerName().equals(nLp.getLayerName())) {
                lp.copy(nLp);
                lp.rePaintAllPixel();
                break;
            }
        }
    }

    //Tao anh moi voi new Size
    public void setNewSize(int w, int h) {
        this.width = w;
        this.height = h;
        this.rW = w * zoom;
        this.rH = h * zoom;
        this.setSize(rW,rH);
        resizeViewPort(rW, rH);
        transparent.updateSize(rW, rH);
        gridPane.setNewSize(w, h, zoom);
        tempLay.createNew(this.width, this.height, this.zoom, "temlayer", 1); //tran
        mf.getLbImageSize().setText(w + "x" + h);
        
    }
    
    public void setLayerList(ArrayList<LayerPanel> lpT) {
        layersList=lpT;
    }

    public void setNewSize(String w, String h) {
        setNewSize(Integer.valueOf(w), Integer.valueOf(h));
    }

    public void setNewSize(String wh) {
        String[] item = wh.split("x");
        setNewSize(Integer.valueOf(item[0]), Integer.valueOf(item[1]));
    }

    public void setViewPort(JPanel vp, JScrollPane sp) {
        this.viewPort = vp;
        this.scrollPane = sp;
    }

    public void resizeViewPort(int width, int height) {
        if (this.getSize().height<scrollPane.getSize().height) scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        else scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
         if (this.getSize().width<scrollPane.getSize().width) scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        else scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        int offsetBar=30;
        int gapw = ((scrollPane.getSize().width - width) / 2);
        gapw = gapw >= 10 ? gapw : 10;
        int gaph = ((scrollPane.getSize().height - height) / 2);
        gaph = gaph >= 10 ? gaph : 10;
        viewPortLayout = (GroupLayout) viewPort.getLayout();
        viewPortLayout.setHorizontalGroup(
                viewPortLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addGroup(viewPortLayout.createSequentialGroup()
                        .addGap(gapw, gapw, Short.MAX_VALUE)
                        .addComponent(this, javax.swing.GroupLayout.PREFERRED_SIZE, width, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(gapw, gapw, Short.MAX_VALUE))
        );
        viewPortLayout.setVerticalGroup(
                viewPortLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(viewPortLayout.createSequentialGroup()
                        .addGap(gaph, gaph, Short.MAX_VALUE)
                        .addComponent(this, javax.swing.GroupLayout.PREFERRED_SIZE, height, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(gaph, gaph, Short.MAX_VALUE))
        );
        //System.out.println(viewPortLayout.toString());
        this.setLocation(gapw, gaph);
    }

    public ArrayList<LayerPanel> getLayersList() {
        return layersList;
    }

    public String getFName() {
        return fname;
    }

    public void setFName(String name) {
        this.fname = name;
    }

    public LayerPanel getTempLayer() {
        return tempLay;
    }

    public GridPanel getGridPane() {
        return gridPane;
    }

    public LayerPanel getCurrentLayer() {
        return curLay;
    }

    public void setCurLay(LayerPanel curLay) {
        this.curLay = curLay;
    }

    public int getImageWidth() {
        return width;
    }

    public int getImageHeight() {
        return height;
    }

    public int getZoom() {
        return zoom;
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }


    public LayerPanel getLayer(String fname) {
        LayerPanel rlp = null;
        String s;
        for (LayerPanel lp : layersList) {
            if (lp.getLayerName().equals(fname)) {
                rlp = lp;
                break;
            }
        };
        return rlp;
    }

    public void drawShape(Shape2D sp) {
        curLay.addAndPaintShape(sp);
        sp.paintShapeTo(curLay);
    }
    
    public void mergeLayer(ListItem lpItem) {  //Gop lp vao lp2
        LayerPanel lp  = lpItem.getLayer();
       // System.out.println(layersList.contains(lp)+ " "+lp.getLayerName().equals("Default"));
        if (!layersList.contains(lp) || lp.getLayerName().equals("Default")) return;
        int indexOfLp = layersList.indexOf(lp);
        LayerPanel lp2 = layersList.get(indexOfLp-1);
        for (Shape2D sp: lp.getShapeList()) {
            lp2.addAndPaintShape(sp);
            
        }
//Them moi
        this.removeLayer(lpItem);
    }
    
    public void swapLayer(int index1, int index2) {
        Collections.swap(layersList, index1, index2);
        DefaultListModel<ListItem> dlm = (DefaultListModel<ListItem>) mf.getListLayer().getModel();
        dlm.clear();
        int zIndex1 = this.getComponentZOrder(layersList.get(index1));
        int zIndex2 = this.getComponentZOrder(layersList.get(index2));
        this.setComponentZOrder(layersList.get(index1), zIndex2);
        this.setComponentZOrder(layersList.get(index2), zIndex1);
        this.repaint();
        for (LayerPanel lp: layersList) {
            dlm.add(0, new ListItem(true, lp));
        }
        mf.getListLayer().setSelectedIndex(dlm.getSize()-index2-1);
        mf.getListLayer().getSelectedValue().setSelected(true);
        mf.getListLayer().repaint();        
    }

    public void moveLayerDown(ListItem sValue) {
        int index = layersList.indexOf(sValue.getLayer());
        swapLayer(index,index-1);
    }

    public void moveLayerUp(ListItem sValue) {
       int index = layersList.indexOf(sValue.getLayer());
       swapLayer(index,index+1);
    }
    
    public BufferedImage getNormalImage(int type) {
        BufferedImage outImg = new BufferedImage(this.getImageWidth(), this.getImageHeight(), type);
        Graphics g = outImg.getGraphics();
        for (LayerPanel lp: layersList) {
            g.drawImage(lp.getImg(), 0, 0, null);
        }
        return outImg;
    }
}
