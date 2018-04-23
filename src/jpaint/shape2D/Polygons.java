package jpaint.shape2D;

import java.awt.Point;
import jpaint.algorithm.TransformAlgorithm;


public class Polygons extends Shape2D {
   public void createPoints() {
        super.createPoints();
        curType=Shape2D.TYPE_POLYGON;
        if (edge==0) edge=3;
        Point pt = new Point((wrap[0].x + wrap[4].x)/2, (wrap[0].y+wrap[4].y)/2);
        int r = Math.abs(wrap[0].x - wrap[2].x)/2;
        
        Point[] pointL = new Point[edge];
        double grad = Math.toRadians(360.0/edge);
        pointL[0] = new Point(pt.x,pt.y - r);
        addPoint(pointL[0], -1);
        for (int i = 1; i < edge/2; i++) {
        	pointL[i] = TransformAlgorithm.transRotate(pointL[0], pt, i*grad);
        	addPoint( pointL[i],-1);
               // System.out.println(Math.toDegrees(i*grad));
        }
        for (int i = edge/2; i < edge; i++) {
        	pointL[i] = TransformAlgorithm.transRotate(pointL[0], pt, - ( (edge*grad)-(i*grad)));
        	addPoint( pointL[i],-1);
               // System.out.println(Math.toDegrees((edge*grad)-(i*grad)));
        }
    }
    public int[] getAnchorLocation() {
        return new int[]{-1,-1,-1,-1};
    }
    public String getShapeName() {
        return "polygons";
    }
    public float[] getAnchorScale() {
        float[] t = {0,0,0,0};
        return t;
    }
    public int getType() {
        return Shape2D.TYPE_POLYGON;
    }    
    public String getRealShapeName() {
        switch(edge) {
            case 3: return "Equilateral Triangle";
            case 4: return "Square";
            case 5: return "Pentagon";
            case 6: return "Hexagon";
            case 7: return "Heptagon";
            case 8: return "Octagon";
            case 9: return "Nonagon";
            case 10: return "Decagon";
        }
        return "Polygons";
    }
}
