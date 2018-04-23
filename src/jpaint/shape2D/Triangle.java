package jpaint.shape2D;

import java.awt.Point;


public class Triangle extends Shape2D {
   public void createPoints() {
        super.createPoints();
        Point p1 = wrap[dim.width];
        Point p2 = wrap[dim.height];
        if (anchorScale[0]==-1) {
            anchorScale[0]=(float) (1.0/2);
        }
        //System.out.println(anchorScale[0]+" "+anchorScale[1]);
        int delta = p2.x - p1.x;
        addPoint(new Point (p1.x+ (int)(delta *anchorScale[0]), p1.y),-1);
        addPoint(new Point(p2.x, p2.y),-1);
        addPoint(new Point(p1.x, p2.y),-1);
    }
    public String getShapeName() {
        return "triangle";
    }
    public int[] getAnchorLocation() {
      //  System.out.println("25 Trap: " + dim);
        if (dim.width==0 && dim.height==4) return new int[]{0,-1,-1,-1};
        if (dim.width==6 && dim.height==2) return new int[]{2,-1,-1,-1};
        if (dim.width==2 && dim.height==6) return new int[]{2,-1,-1,-1};
        if (dim.width==4 && dim.height==0) return new int[]{0,-1,-1,-1};
        return new int[]{-1,-1,-1,-1};
    }
    public int getType() {
        return Shape2D.TYPE_POLYGON;
    }     
        public String getRealShapeName() {
        if (anchorScale[0]==0 || anchorScale[0]==1) {
            return "Rectangle Triangle";
        } else if (anchorScale[0]==1.0/2) {
            return "Isosceles Triangle";
        } else {
           return "Triangle"; 
        }
    }
}
