package jpaint.shape2D;

import java.awt.Point;
import java.util.ArrayList;
import jpaint.LayerPanel;


public class Brush extends Shape2D{
    private int type=0;
    public void draw(Point p) {
        if (!pointList.isEmpty()) {
       // Point po=pointList.get(0);
       // algorithmDDA(po, p);
        //} else {
            putPixelNonPaint(p.x,p.y);
        }
        paintToLayer();
    }
    
    public void setType(int t) {
        type=t;
    }

    public String getShapeName() {
        return "brush";
    }

}
