package jpaint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class TransparentPane extends JPanel {
    private BufferedImage img;
    private int rW, rH;
    private int plateSize = 28;
    public TransparentPane() {
        this.setLayout(null);
        this.setBackground(Color.yellow);
        try {
            img = ImageIO.read(this.getClass().getResource("/rsc/tranplate.png"));
        } catch (IOException ex) {
        }
        repaint();
    }
    public void updateSize(int rW, int rH) {
        this.rW=rW;
        this.rH=rH;
        this.setSize(rW, rH);
        repaint();
    }
    public void paint(Graphics g) {
        for (int i=0;i<=rW/plateSize;i++)
            for (int j=0;j<=rH/plateSize;j++)
                g.drawImage(img,i*plateSize,j*plateSize,null);
    }
}
