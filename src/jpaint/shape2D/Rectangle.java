package jpaint.shape2D;

import java.awt.Point;


public class Rectangle extends Shape2D {
    public void createPoints() {
        super.createPoints();
        curType=Shape2D.TYPE_POLYGON;
        addPoint(wrap[0],-1);
        addPoint(wrap[2],-1);
        addPoint(wrap[4],-1);
        addPoint(wrap[6],-1);
       // rotateShape(getType());
    }
    public int[] getAnchorLocation() {
        return new int[]{-1,-1,-1,-1};
    }
    public String getShapeName() {
        return "rect";
    }
    public float[] getAnchorScale() {
        float[] t = {0,0,0,0};
        return t;
    }
    public int getType() {
        return Shape2D.TYPE_POLYGON;
    }
    public String getRealShapeName() {
        int dx = Math.abs(wrap[0].x-wrap[4].x);
        int dy = Math.abs(wrap[0].y-wrap[4].y);
        if (dx==dy) {
            return "Square";
        }
        return "Rectangle";
    }
}
