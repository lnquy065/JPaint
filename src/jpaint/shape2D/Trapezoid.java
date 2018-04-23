package jpaint.shape2D;

import java.awt.Point;


public class Trapezoid extends Shape2D{
    public void createPoints() {
        super.createPoints();
        Point p1 = wrap[dim.width];
        Point p2 = wrap[dim.height];
        if (anchorScale[0]==-1 && anchorScale[1]==-1) {
            anchorScale[0]=(float) (1.0/4);
            anchorScale[1]=(float) (3.0/4);
        }
        //System.out.println(anchorScale[0]+" "+anchorScale[1]);
        int delta = p2.x - p1.x;
        if (dim.width==4 || dim.width==2) ;
        addPoint(new Point(p1.x + (int)(delta *anchorScale[0]), p1.y),-1);
        addPoint(new Point(p1.x + (int)(delta * anchorScale[1]), p1.y),-1);
        addPoint(new Point(p2.x, p2.y),-1);
        addPoint(new Point(p1.x, p2.y),-1);
       
    }
    public String getShapeName() {
        return "trapezoid";
    }
    public int[] getAnchorLocation() {
      //  System.out.println("25 Trap: " + dim);
        if (dim.width==0 && dim.height==4) return new int[]{0,0,-1,-1};
        if (dim.width==6 && dim.height==2) return new int[]{2,2,-1,-1};
        if (dim.width==2 && dim.height==6) return new int[]{2,2,-1,-1};
        if (dim.width==4 && dim.height==0) return new int[]{0,0,-1,-1};
        return new int[]{-1,-1,-1,-1};
    }
    public int getType() {
        return Shape2D.TYPE_POLYGON;
    }       
    public String getRealShapeName() {
        if (anchorScale[0]==0 || anchorScale[1]==0) {
            return "Rectangle Trapezoid";
        } else if (anchorScale[0]==1.0/4 && anchorScale[1]==3.0/4) {
            return "Isosceles Trapezoid";
        } else {
           return "Trapezoid"; 
        }
    }
}
