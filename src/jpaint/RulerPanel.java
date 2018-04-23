package jpaint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import jpaint.algorithm.DrawAlgorithm;


public class RulerPanel extends JPanel {
    private BufferedImage img;
    private JToggleButton btnRuler;
    private ImagePanel ip;
    public RulerPanel(JToggleButton btnRuler) {
        this.setOpaque(false);
        this.btnRuler=btnRuler;
    }
    public void setImagePanel(ImagePanel ip) {
        this.ip = ip;
    }
    public void setRuler(Point p, int size, int zoom, String curTool) {
        clearImg();
        if ( ( (curTool.equals("brush") || curTool.equals("eraser")) && size*zoom>1) || 
                (curTool.equals("pencil") || curTool.equals("picker")  )) {
        if (curTool.equals("pencil") || curTool.equals("picker")) size=1;    
        size*=zoom;
        Graphics g = img.getGraphics();
        g.setColor(Color.BLUE);
        g.drawRect(p.x-size/2, p.y-size/2, size, size);
        repaint();
        }
        if (!btnRuler.isSelected()) return;
        Point p1 = new Point(p.x, 0);
        Point p2 = new Point(p.x,img.getHeight()-1);
        Point p3 = new Point (0, p.y);
        Point p4 = new Point (img.getWidth()-1, p.y);
      DrawAlgorithm.algorithmDDA(img, p1, p2, new int[]{0,0}, Color.BLUE);
        DrawAlgorithm.algorithmDDA(img, p3, p4, new int[]{0,0}, Color.BLUE);
        repaint();
    }
    public void setNewSize() {
        int fwidth = 1, fheight = 1;
        if (ip == null) {
            return;
        }
        fwidth = ip.getLocation().x * 2 + ip.getWidth();
        fheight = ip.getLocation().y * 2 + ip.getHeight();
        img = new BufferedImage(fwidth, fheight, BufferedImage.TYPE_INT_ARGB);
        this.setSize(fwidth, fheight);
        
    }
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(img, 0, 0, null);
    }
    public void clearImg() {
        //System.out.println(System.currentTimeMillis()+"clear");
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setBackground(new Color(0, 0, 0, 0));
        g.clearRect(0, 0, img.getWidth(), img.getHeight());
    }
}
