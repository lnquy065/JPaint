package jpaint.shape2D;

import java.awt.Point;
import jpaint.LayerPanel;


public class Line extends Shape2D{

    public void createPoints() {
        super.createPoints();
        curType=Shape2D.TYPE_POLYGON;
        addPoint(wrap[dim.width],-1);
        addPoint(wrap[dim.height],-1);
       //System.out.println(dim.width+" "+dim.height);
     //   rotateShape(getType());
    }
    
    @Override
    public String getShapeName() {
        return "line";
    }
    public int[] getAnchorLocation() {
        return new int[]{-1,-1,-1,-1};
    }
    public float[] getAnchorScale() {
        float[] t = {0,0,0,0};
        return t;
    }
    public int getType() {
        return Shape2D.TYPE_POLYGON;
    }
        public String getRealShapeName() {
        return "Line";
    }
}
