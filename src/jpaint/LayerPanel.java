package jpaint;

import jpaint.shape2D.Pixel2D;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.JPanel;
import jpaint.shape2D.Shape2D;
import jpaint.shape3D.Shape3D;

public class LayerPanel extends JPanel implements Cloneable {

    private String lname="null";
    
    private int width, height, zoom = 1;
    private int rW = 0, rH = 0;
    transient private BufferedImage img;
    private ArrayList<Shape2D> shapeList2D = new ArrayList();
    private ArrayList<Shape3D> shapeList3D = new ArrayList();
    
    private Pixel2D[][] pixelArray;
        //Ma tran chua toan bo pixel cua anh, => truy xuat tung diem mau
    
    private ArrayList<Pixel2D> pixelCoreList = new ArrayList();             
        //Chi chua thong tin cua 1 pixel trung tam, => ho tro repaint nhanh
        // size = 3       x x x
        //                x 0 x
        //                x x x

    public LayerPanel() {
        super();
        this.setLayout(null);
        this.setOpaque(false);
    }

    public LayerPanel(LayerPanel lp) {
        this.width = lp.width;
        this.height = lp.height;
        this.lname = lp.lname;
        this.img = new BufferedImage(width * zoom, height * zoom, BufferedImage.TYPE_INT_ARGB);
        img.setData(lp.img.getData());
        this.shapeList2D = lp.shapeList2D;
        pixelArray = new Pixel2D[lp.width][lp.height];
        for (int i = 0; i < lp.width; i++) {
            for (int j = 0; j < lp.height; j++) {
                pixelArray[i][j] = lp.pixelArray[i][j];
            }
        }
    }
    
    public void clear() {
         
        //Arrays.fill(pixels, rW, rW, 0);
        img = new BufferedImage(width * zoom, height * zoom, BufferedImage.TYPE_INT_ARGB);
        if (this.getLayerName().equals("Default")) {
           Graphics2D g = (Graphics2D) img.getGraphics();
            g.setBackground(new Color(255, 255, 255, 255));
            g.clearRect(0, 0, img.getWidth(), img.getHeight());
        }
        repaint();
    }

    public void createNew(int w, int h, int zoom, String lname, int mode) { //mode=0 create white, mode=1 create trans
        this.width = w;
        this.height = h;
        this.zoom = zoom;
        this.lname = lname;
        setNewSize(w * zoom, h * zoom);
        pixelArray = new Pixel2D[w][h];
        img = new BufferedImage(w * zoom, h * zoom, BufferedImage.TYPE_INT_ARGB);
        //long startTime = System.currentTimeMillis();
        Graphics g = img.getGraphics();
        //FILL
        if (mode == 0) {
            for (Pixel2D[] pxA: pixelArray) {
                Arrays.fill(pxA, new Pixel2D(-1,-1,new Color(255, 255, 255, 255),1));
            }
            g.setColor(new Color(255, 255, 255, 255));
        } else {
           for (Pixel2D[] pxA: pixelArray) {
                Arrays.fill(pxA, new Pixel2D(-1,-1,new Color(255, 255, 255, 0),1));
            }
           
            g.setColor(new Color(255, 255, 255, 0));
        }
        g.fillRect(0, 0, w*zoom, h*zoom);
        
//        for (int i = 0; i < w; i++) {
//            for (int j = 0; j < h; j++) {
//                pixelArray[i][j] = new Pixel2D(i, j, mode == 0 ? new Color(255, 255, 255, 255) : new Color(255, 255, 255, 0));
              // setPixel(pixelArray[i][j], 1, 1);
//            }
//        }
        //long stopTime = System.currentTimeMillis();
        //System.out.println("80 Layer: "+ getLayerName()+" "+(w*h) +" " +(stopTime-startTime));
        this.repaint();
    }

    public void copy(LayerPanel lp) {
        this.zoom=zoom;
        this.lname = lp.lname;
        this.img = new BufferedImage(width * zoom, height * zoom, BufferedImage.TYPE_INT_ARGB);
        img.setData(lp.img.getData());
        this.shapeList2D = lp.shapeList2D;
        pixelArray = new Pixel2D[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixelArray[i][j] = lp.pixelArray[i][j];
            }
        }
    }

    public void updateZoom(int zoom) {
        this.zoom = zoom;
        this.setNewSize(width * zoom, height * zoom);
        Pixel2D p;
        int i=0,j=0;
        int dem=0;
       // System.out.println("zoom======================================");
 
//        Timer timer = new Timer(100, new ActionListener() {
//            int dem=0;
//            @Override
//            public void actionPerformed(ActionEvent ae) {  
//                if (dem<pixelCoreList.size()) {
//                Point2D p2D2 = pixelCoreList.get(dem);
//                dem++;
//                int i = p2D2.x;
//                int j = p2D2.y;
//                Pixel2D p2 = pixelArray[i][j];
//                paintPixel(p2); 
//                repaint();
//                }
//            }
//        });
//        
//        
//        timer.start();
//        
        for (Pixel2D p2D: pixelCoreList) {
                paintPixel(p2D);
        }
        
    }
        
    public void paintPixel(Pixel2D px) {
        int size = px.getSize();
        int p = size / 2;
        int x = (px.getX()-p)*zoom;
        int y = (px.getY()-p)*zoom;
        int w = zoom*size;
       // System.out.println("->>>"+px.x+" "+px.y);
        if ((x < 0 || x >= width*zoom) || (y < 0 || y >= height*zoom)) return;
        Graphics g = img.getGraphics();
        g.setColor(px.getColor());
        g.fillRect( (px.getX()-p)*zoom, (px.getY()-p)*zoom, zoom*size, zoom*size);
      //  System.out.println(x+" "+y+" "+w+" "+px.getColor());
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(img, 0, 0, null);
        //System.out.println(System.currentTimeMillis()+"paint");
    }

    public void rePaintAllPixel() {
        Pixel2D p;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                p = pixelArray[i][j];
                for (int x = i * zoom; x < i * zoom + zoom; x++) {
                    for (int y = j * zoom; y < j * zoom + zoom; y++) {
                        img.setRGB(x, y, p.getColor().getRGB());
                    }
                }
            }
        }
        repaint();
    }
    
    public void repaintAllShape() {
        this.clear();
        pixelCoreList.clear();
        for (Shape2D s: shapeList2D) {
            if (s.isVisible()) s.paintShapeTo(this);
        }
        for (Shape3D s: shapeList3D) {
            if (s.isVisible()) s.paintToLayer(this);
        }
        repaint();
    }
    public int sizeOfShape() {
        return shapeList2D.size();
    }
    
    public int indexOfShape(Shape2D sp) {
       return shapeList2D.indexOf(sp);
    }
    
    public void repaintShape(int index) {

        Shape2D t = shapeList2D.get(0);
        t.setVisible(true);
        t.paintShapeTo(this);
        repaint();
    }
    
    public void repaintShape(Shape2D sp) {
        if (shapeList2D.contains(sp)) {
            sp.setVisible(true);
            sp.paintShapeTo(this);
        }
    }
    
    public void moveUpShape(Shape2D sp) {
        int index = shapeList2D.indexOf(sp);
        Collections.swap(shapeList2D, index, index+1);
    }
    
    public void moveDownShape(Shape2D sp) {
        int index = shapeList2D.indexOf(sp);
        Collections.swap(shapeList2D, index, index-1); 
    }
    
    public void removeShape(Shape2D sp) {
        shapeList2D.remove(sp);
    }
     
    public void replaceShape(Shape2D sp, Shape2D sp2) {
        int index = shapeList2D.indexOf(sp);
        if (index==-1) return;
        shapeList2D.set(index, sp2);
    }
    
    public void replaceShape(Shape2D sp, int index) {
        shapeList2D.set(index, sp);
    }   
    public void addShape(Shape2D sp, int index) {
        shapeList2D.add(index, sp);
    }

    
    public void addAndPaintShape(Shape2D sp) {
        System.out.println("Them hinh "+shapeList2D.size());
        if (shapeList2D.contains(sp)) {
            sp.setVisible(true);
            repaintAllShape();
            //System.out.println("159 Layer: Trung Hinh");
            return;   
        }
        if (sp.getType()!=Shape2D.TYPE_PIXEL) shapeList2D.add(0, sp);
        else shapeList2D.add(sp);
        //System.out.println("147 Layer: Them moi vao "+lname);
        if (sp.isVisible()==false) return;
        //System.out.println("147 Layer: Da ve hinh "+sp.isVisible());
        sp.paintShapeTo(this);
    }
    
        public void addAndPaintShape(Shape3D sp) {
        //new Shape da ton tai
        if (shapeList3D.contains(sp)) {
            sp.setVisible(true);
            repaintAllShape();
            //System.out.println("159 Layer: Trung Hinh");
            return;   
        }
        shapeList3D.add(sp);
        //System.out.println("147 Layer: Them moi vao "+lname);
        if (sp.isVisible()==false) return;
        //System.out.println("147 Layer: Da ve hinh "+sp.isVisible());
        for (Pixel2D p: sp.getPixelList()) {
            this.setAndPaintPixel(p, 0);
        }
    }
    public void addShape(Shape3D sp) {
        shapeList3D.add(sp);
         for (Pixel2D p: sp.getPixelList()) {
            this.setAndPaintPixel(p, 0);
        }
    }

    synchronized public void setAndPaintPixel(Pixel2D px, int mode) {
        int size = px.getSize();
        int p = size / 2;
        int x = (px.getX()-p)*zoom;
        int y = (px.getY()-p)*zoom;
        int w = zoom*size;
        int deltaX=0;
        int deltaY=0;
        if (x < 0 || x >= width*zoom) {
            
            deltaX= x<0? 0-x:x-width*zoom;
        } 
        if (y < 0 || y >= height*zoom) {
            deltaY= y<0? 0-y:y-height*zoom;
        }
        x+=deltaX;
        y+=deltaY;
        
        Graphics g = img.getGraphics();
        g.setColor(px.getColor());
        g.fillRect(x, y, zoom*size-deltaX, zoom*size-deltaY);
        //Them ma mau vao danh sanh
       // pixelArray[px.getX()][px.getY()] = px;
        for (int i = px.x-p;i < px.x-p+size;i ++) {
            if (i<0 || i>=width) continue;
            int j = px.y-p+deltaY;
            int jMax = j+size;
            if (jMax >=height) jMax -= jMax-height;
            if (j<jMax)
            Arrays.fill(pixelArray[i], j, jMax, px);
        }

        if (pixelCoreList.add(px)) {
         //    System.out.println("Them Core "+px.x+" "+px.y);
        }
        
//        for (int x = px.getX() - p; x < px.getX() + size; x++) {
//            for (int y = px.getY() - p; y < px.getY() + size; y++) {
//                if ((x < 0 || x >= width) || (y < 0 || y >= height)) {
//                    continue;
//                }
//                pixelArray[x][y] = px;
//                pixelList.add(new Point2D(px.getX(),px.getY()));
//                for (int i = x * zoom; i < x * zoom + zoom; i++) {
//                    for (int j = y * zoom; j < y * zoom + zoom; j++) {
//                        if ((i < 0 || i >= width * zoom) || (j < 0 || j >= height * zoom)) {
//                            continue;
//                        }
//                       img.setRGB(i, j, px.getColor().getRGB());
//                    }
//                }
//            }
//        }
    }

    public void setNewSize(int w, int h) {
        this.setBounds(0, 0, w, h);
        //
        img = new BufferedImage(width * zoom, height * zoom, BufferedImage.TYPE_INT_ARGB);
        clear();
    }

    public void setNewSize(String w, String h) {
        setNewSize(Integer.valueOf(w), Integer.valueOf(h));
    }

    public void setNewSize(String wh) {
        String[] item = wh.split("x");
        setNewSize(Integer.valueOf(item[0]), Integer.valueOf(item[1]));
    }

    public String getLayerName() {
        return lname;
    }

    public void setLayerName(String lname) {
        this.lname = lname;
    }

    public int getrW() {
        return rW;
    }

    public void setrW(int rW) {
        this.rW = rW;
    }

    public int getrH() {
        return rH;
    }

    public BufferedImage getImg() {
        return img;
    }
    public void setrH(int rH) {
        this.rH = rH;
    }

    public int getZoom() {
        return zoom;
    }

    public void debugPixel() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                System.out.println("[" + i + "][" + j + "]: " + pixelArray[i][j].getColor());
            }
        }
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException ex) {
        }
        return null;
    }
    public ArrayList<Pixel2D> getPixelCoreList() {
        return pixelCoreList;
    }
    public Pixel2D getPixelAt(int i, int j) {
       
       if (i<0 || i>=width) return null;
       if (j<0 || j>=height) return null;
       if (pixelArray[i][j].x==-1 && pixelArray[i][j].y==-1) {
           pixelArray[i][j] = new Pixel2D(i,j, pixelArray[i][j].getColor(), pixelArray[i][j].getSize());
       }
        return pixelArray[i][j];
    }

    public ArrayList<Shape2D> getShapeList() {
        return shapeList2D;
    }
    
    public ArrayList<Shape3D> getShapeList3D() {
        return shapeList3D;
    }

    public void setShapeList(ArrayList<Shape2D> shapeList) {
        this.shapeList2D = shapeList;
    }
    public Shape2D getShapeAtPoint(Point p) {
        Shape2D s;
        for (int i=shapeList2D.size()-1;i>=0;i--) {
            s = shapeList2D.get(i);
            if (s.contain(p, s.getType())) return s;
        }
        return null;
    }
    
}

class Point2D implements Serializable{
    public int x;
    public int y;
    
    public Point2D(int x, int y) {
        this.x=x;
        this.y=y;
    }
    
    @Override
    public boolean equals(Object object)
    {
        boolean ret = false;

        if (object != null && object instanceof Point2D)
        {
            if (this.x == ((Point2D) object).x && this.y == ((Point2D) object).y) ret=true;
        }

        return ret;
    }
}