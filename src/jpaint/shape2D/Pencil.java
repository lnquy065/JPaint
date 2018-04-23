package jpaint.shape2D;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import jpaint.LayerPanel;


public class Pencil extends Shape2D{

    public void draw(Point p) {
        putPixelNonPaint(p.x, p.y);
        paintToLayer();
    }


    public String getShapeName() {
        return "pencil";
    }
        public int getType() {
        return Shape2D.TYPE_PENCIL;
    }
}
