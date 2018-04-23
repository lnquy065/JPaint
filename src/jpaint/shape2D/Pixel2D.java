package jpaint.shape2D;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;


public class Pixel2D implements Serializable{
    public int x,y;
    private int size;
    private Color color;
    
    public Pixel2D() {
    }

    public Pixel2D(int x, int y, Color color, int size) {
        this.x=x;
        this.y=y;
        this.size = size;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    

    public int getSize() {
        return size;
    }

    public void setSize(byte size) {
        this.size = size;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    public void setPoint(Point p) {
        x=p.x;
        y=p.y;
    }
    public Point getPoint() {
        return new Point(x,y);
    }
    public int getRGB() {
        return color.getRGB();
    }
    public boolean equals(Object o) {
        Pixel2D p = (Pixel2D) o;
        if (p.x==this.x && p.y==this.y)
            return true;
        return false;
    }
}
