package jpaint.shape3D;

import java.awt.Point;
import java.io.Serializable;


public class Point3D implements Serializable{
    public int x;
    public int y;
    public int z;
    public Point3D(int x, int y, int z) {
        this.x=x;
        this.y=y;
        this.z=z;
    }

    Point3D() {
    }
    public Point toPoint2DUser() {
        double a = Math.sqrt(2)/2;
        int x2D = (int)Math.round(x-y*a);
        int y2D = (int)Math.round(z-y*a);
        return new Point(x2D, y2D);
    }
    public static Point3D doi2Dsang3D( Point p){
        double a = Math.sqrt(2);
        int x = (int) (p.x + (-p.y));
        int y = (int) ((-p.y)*a);       
        int z = 0;
        return new Point3D(x, y, z);
    }
    
    public String toString() {
        return x+" "+y+" "+z;
    }
    
    public Point3D clone() {
        return new Point3D(x,y,z);
    }
    
    public boolean equals(Object o) {
        Point3D p = (Point3D) o;
        if (x==p.x && y==p.y && z==p.z) return true;
        return false;
    }
}
