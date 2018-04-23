package jpaint.shape2D;

import java.awt.Point;
import java.util.ArrayList;
import jpaint.algorithm.DrawAlgorithm;
import static jpaint.algorithm.DrawAlgorithm.algorithmEllipse;


public class Ellipse extends Shape2D{
   public void createPoints() {
        super.createPoints();
        curType=Shape2D.TYPE_ELIP;
        ArrayList<Point> pList = algorithmEllipse(wrap[0], wrap[4], alpha, stroke);
        if (pList!=null) pointList.addAll(pList);
       // rotateShape(getType());
    }
    public String getShapeName() {
        return "circle";
    }
        public int[] getAnchorLocation() {
        return new int[]{-1,-1,-1,-1};
    }
    public float[] getAnchorScale() {
        float[] t = {0,0,0,0};
        return t;
    }
    public int getType() {
        return Shape2D.TYPE_ELIP;
    }
    public String getRealShapeName() {
        int dx = Math.abs(wrap[0].x-wrap[4].x);
        int dy = Math.abs(wrap[0].y-wrap[4].y);
        if (dx==dy) {
            return "Circle";
        }
        return "Ellipse";
    }
}
