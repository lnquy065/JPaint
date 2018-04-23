package jpaint.shape2D;

import java.awt.Point;


public class Diamond extends Shape2D {
   public void createPoints() {
        super.createPoints();
        curType=Shape2D.TYPE_POLYGON;
        Point p1 = new Point ( (wrap[0].x +wrap[2].x)/2, wrap[0].y);
        Point p2 = new Point (wrap[0].x,(wrap[0].y + wrap[6].y)/2);
        Point p3 = new Point ( (wrap[0].x +wrap[2].x)/2, wrap[4].y);
        Point p4 = new Point ( wrap[4].x,(wrap[0].y + wrap[6].y)/2);
        addPoint(p1, -1);
        addPoint(p2, -1);
        addPoint(p3, -1);
        addPoint(p4, -1);
        
    }
    public int[] getAnchorLocation() {
        return new int[]{-1,-1,-1,-1};
    }
    public String getShapeName() {
        return "diamond";
    }
    public float[] getAnchorScale() {
        float[] t = {0,0,0,0};
        return t;
    }
    public int getType() {
        return Shape2D.TYPE_POLYGON;
    }
    public String getRealShapeName() {
        return "Diamond";
    }
}
