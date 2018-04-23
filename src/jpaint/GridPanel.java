package jpaint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GridPanel extends JPanel {

    private MainForm mf;
    private int w, h, zoom;
    private int mode = 1;
    private Color colorAxis=Color.RED;
    private Color colorWeb=Color.LIGHT_GRAY;

    public GridPanel() {
        super();
        this.setOpaque(false);
        this.setBackground(Color.CYAN);

    }

 public void paint(Graphics g1) {
        super.paint(g1);
        if (mf.isGrid() == false) {
            return;
        }
        if (zoom < 5) {
            return;
        }
        Graphics2D g = (Graphics2D) g1;
        g.setColor(colorWeb);
        //g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,new float[] { 2, 2 }, 0));
        //Ve truc Y
        if (mode == MainForm.CROOD_MODE_DEFAULT) {
            for (int i = 0; i < w * zoom; i += zoom) {
                g.drawLine(i, 0, i, h * zoom - 1);
            }
            for (int j = 0; j < h * zoom; j += zoom) {
                g.drawLine(0, j, w * zoom - 1, j);
            }
        }
        if (mode == MainForm.CROOD_MODE_USER) {
            for (int i = 0; i < (w * zoom); i += zoom) {
                g.drawLine(i+zoom/2, 0, i+zoom/2, h * zoom - 1);
            }
            for (int j = 0; j < (h * zoom); j += zoom) {
                g.drawLine(0, j+zoom/2, w * zoom - 1, j+zoom/2);
            }
            g.setColor(colorAxis);
            g.drawImage(new ImageIcon(getClass().getResource("/rsc/Y.png")).getImage(),(w * zoom)/2-17,0, null);
            g.drawImage(new ImageIcon(getClass().getResource("/rsc/X.png")).getImage(),(w * zoom)-17,(h*zoom)/2-17, null);
            g.drawImage(new ImageIcon(getClass().getResource("/rsc/O.png")).getImage(),(w * zoom)/2-17,(h*zoom)/2-17, null);
            g.drawLine((w * zoom)/2+zoom/2, 0, (w * zoom)/2+zoom/2, h * zoom - 1);
             g.drawLine(0, (h * zoom)/2+zoom/2, w * zoom - 1, (h * zoom)/2+zoom/2);
        }
        if (mode == MainForm.CROOD_MODE_3D) {
             for (int j = 0; j < (h * zoom); j += zoom) {
                g.drawLine(0, j+zoom/2, w * zoom - 1, j+zoom/2);
            }
            
            int i = zoom;
            int j = zoom;
            while (j <= h * zoom){
                g.drawLine(i, 0, 0, j);
                i += zoom;
                j += zoom;
            }
            int k = zoom;
            while (i <= w * zoom){
                
                g.drawLine(i, 0, k, h * zoom);
                i += zoom;
                k += zoom;
            }
            k = w * zoom - zoom;
            j = h * zoom - zoom;
            while (j >= zoom){
                g.drawLine(w * zoom, j, k, h * zoom);
                j -= zoom;
                k -= zoom;
            }
            
            g.drawImage(new ImageIcon(getClass().getResource("/rsc/Z.png")).getImage(),(w * zoom)/2-17,0, null);
            g.drawImage(new ImageIcon(getClass().getResource("/rsc/X.png")).getImage(),(w * zoom)-17,(h*zoom)/2-17, null);
            g.drawImage(new ImageIcon(getClass().getResource("/rsc/O.png")).getImage(),(w * zoom)/2-17,(h*zoom)/2-17, null);
            
            
            
            g.setColor(Color.RED);
            g.drawLine((w * zoom)/2+zoom/2, 0, (w * zoom)/2+zoom/2, (h * zoom)/2+zoom/2);  //z
             g.drawLine((w * zoom)/2+zoom/2, (h * zoom)/2+zoom/2, w * zoom - 1, (h * zoom)/2+zoom/2); //x
             g.setColor(Color.BLUE);
             if (w>=h) {
             g.drawImage(new ImageIcon(getClass().getResource("/rsc/Y.png")).getImage(),((w * zoom)/2+zoom/2)-((h * zoom)/2-zoom/2)-17,(h * zoom)-25, null);    
             g.drawLine((w * zoom)/2+zoom/2, (h * zoom)/2+zoom/2, ((w * zoom)/2+zoom/2)-((h * zoom)/2-zoom/2)+1, (h * zoom)-1); //y
             } else {
              g.drawImage(new ImageIcon(getClass().getResource("/rsc/Y.png")).getImage(),0+17,((h * zoom)/2+zoom/2)+((w * zoom)/2+zoom/2)-17, null);    
             g.drawLine((w * zoom)/2+zoom/2, (h * zoom)/2+zoom/2, 0, ((h * zoom)/2+zoom/2)+((w * zoom)/2+zoom/2));    
             }
        }
    }

    public void setObServer(MainForm mainFrame) {
        this.mf = mainFrame;
    }

    public void setNewSize(int w, int h, int zoom) {
        this.w = w;
        this.h = h;
        this.zoom = zoom;
        this.setSize(w * zoom, h * zoom);
        repaint();
    }

    public void setMode(int m) {
        mode = m;
        repaint();
    }
}
