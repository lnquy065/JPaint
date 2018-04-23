package jpaint.algorithm;

import java.awt.Point;
import jpaint.shape3D.Point3D;
import jpaint.shape3D.Shape3D;


public class TransformAlgorithm {
    public static Point nhanMaTranBienDoi(Point p, double[][] matrix) {
        double[] result = new double[3];
        double[] point = {p.x, p.y, 1};
        for (int i = 0; i < 3; i++) {
            result[i] =(point[0] * matrix[0][i] + point[1] * matrix[1][i] + point[2] * matrix[2][i]);
        }
        return new Point((int)(result[0]), (int)(result[1]));
    }
    
    

    public static Point transLocation(Point p, int dx, int dy) {           //Tinh tien p 1 khoang dx dy
        double[][] Tm = {{1, 0, 0}, {0, 1, 0}, {dx, dy, 1}};//Ma tran tinh tien
        return nhanMaTranBienDoi(p, Tm);
    }

    public static Point transRotate(Point p1, Point p2, double alpha2) {    //Quay p1 quanh p2 mot goc alpha(rad)
        int alpha1 = (int) Math.toDegrees(alpha2);
        double alpha;
        
        alpha = (alpha1*Math.PI)/180;
       // int alphaInt = (int) Math.round(Math.toDegrees(alpha));
       // if (alphaInt==0) return p1;
       // if (alphaInt==180 || alphaInt==180) return transFlipPoint(p1,p2);
       float c = (float) (Math.cos(alpha));
       float s = (float) (Math.sin(alpha));
        //System.out.println(alpha1);
       if (alpha1%90==0) {
           c=Math.round(c);
           s=Math.round(s);
       }
        double[][] Rm = {{c, s, 0}, {-s, c, 0}, {0, 0, 1}}; //Ma tran quay
       // System.out.println(c +" "+ s);
      //  System.out.println("old: "+p1);
        Point afLocation = transLocation(p1, -p2.x, -p2.y);         //Chuyen ve goc toa do
        Point afRotate = nhanMaTranBienDoi(afLocation, Rm);           //Quay quanh goc toa do
        // System.out.println("New: "+afRotate);
        Point afReLocation = transLocation(afRotate, p2.x, p2.y);   //Chuyen ve vi tri cu
        //System.out.println("New: "+afReLocation);
        return afReLocation;
    }

    public static Point transFlipPoint(Point p1, Point p2) {               //Doi xung p1, qua p2
        double[][] Dm = {{-1, 0, 0}, {0, -1, 0}, {0, 0, 1}};//Ma tran doi xung 0,0
        Point afLocation = transLocation(p1, -p2.x, -p2.y);         //Chuyen ve goc toa do
        Point afFlipPoint = nhanMaTranBienDoi(afLocation, Dm);        //Doi xung
        Point afReLocation = transLocation(afFlipPoint, p2.x, p2.y);          //Chuyen ve vi tri cu
        return afReLocation;
    }
    
    public static Point3D nhanMaTranBienDoi3D(Point3D p, double[][] matrix) {
        double[] result = new double[4];
        double[] point3D = {p.x, p.y, p.z, 1};
        for (int i = 0; i < 4; i++) {
            result[i] =(point3D[0] * matrix[0][i] + point3D[1] * matrix[1][i] + point3D[2] * matrix[2][i] + point3D[3] * matrix[3][i]);
        }
        return new Point3D((int)(result[0]), (int)(result[1]), (int)(result[2]));
    }
    
    public static Point3D trans3DLocation(Point3D p, int trX, int trY, int trZ) {
        double[][] Tm = {{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1,0}, {trX,trY,trZ,1}};//Ma tran tinh tien
        return nhanMaTranBienDoi3D(p, Tm);        
    }
    
    public static Point3D trans3DRotate(Point3D pQuay, Point3D pTam, double alpha, int axis) {
        int x=pQuay.x;
        int y=pQuay.y;
        int z=pQuay.z;
        int xt=pTam.x;
        int yt=pTam.y;
        int zt=pTam.z;
        
        if (axis==Shape3D.Z){
            Point p = transRotate(new Point(x, y), new Point(xt, yt), alpha);
            return new Point3D(p.x, p.y, pQuay.z);
        }
        if (axis==Shape3D.X){
            Point p = transRotate(new Point(z, y), new Point(zt, yt), alpha);
            return new Point3D(pQuay.x, p.y, p.x);
        }
        if (axis==Shape3D.Y){
            Point p = transRotate(new Point(x, z), new Point(xt, zt), alpha);
            return new Point3D(p.x, pQuay.y, p.y);
        }
        return null;
    }
    public static Point3D trans3DFlipO(Point3D p) {
        double[][] Tm = {{-1, 0, 0, 0}, {0, -1, 0, 0}, {0, 0, -1,0}, {0,0,0,-1}};
        return nhanMaTranBienDoi3D(p, Tm);               
    }
    public static Point3D trans3DFlipAxis(Point3D p, int axis) {
        if (axis==Shape3D.X) {
        double[][] Tm = {{1, 0, 0, 0}, {0, -1, 0, 0}, {0, 0, -1,0}, {0,0,0,1}};
        return nhanMaTranBienDoi3D(p, Tm);   
        }
         if (axis==Shape3D.Y) {
        double[][] Tm = {{-1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, -1,0}, {0,0,0,1}};
        return nhanMaTranBienDoi3D(p, Tm);   
        }       
                 if (axis==Shape3D.Z) {
        double[][] Tm = {{-1, 0, 0, 0}, {0, -1, 0, 0}, {0, 0, 1,0}, {0,0,0,1}};
        return nhanMaTranBienDoi3D(p, Tm);   
        }
                 return null;
    }
    public static Point3D trans3DFlipPlane(Point3D p, int plane) {
        if (plane==Shape3D.OXY) {
        double[][] Tm = {{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, -1,0}, {0,0,0,1}};
        return nhanMaTranBienDoi3D(p, Tm);   
        }
         if (plane==Shape3D.OXZ) {
        double[][] Tm = {{1, 0, 0, 0}, {0, -1, 0, 0}, {0, 0, 1,0}, {0,0,0,1}};
        return nhanMaTranBienDoi3D(p, Tm);   
        }       
                 if (plane==Shape3D.OYZ) {
        double[][] Tm = {{-1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1,0}, {0,0,0,1}};
        return nhanMaTranBienDoi3D(p, Tm);   
        }
                 return null;        
    }
    
    public static Point3D trans3DRotate(Point3D p, double alpha,  int axis){
        int alpha1 = (int) Math.toDegrees(alpha);
           
       float c = (float) (Math.cos(alpha));
       float s = (float) (Math.sin(alpha));
       double[][] Rm = new double[4][4];
       
       if (alpha1%90==0) {
           c=Math.round(c);
           s=Math.round(s);
       }
       
       if (axis==Shape3D.X){
          
           Rm = new double[][]{{1, 0, 0, 0}, {0, c, s, 0}, {0, -s, c, 0}, {0, 0, 0, 1}}; //Ma tran quay quanh Ox
           
       }
       if (axis==Shape3D.Y){
           Rm = new double[][]{{c, 0, s, 0}, {-s, 0, c, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}}; //Ma tran quay quanh Oy
           
       }
       if (axis==Shape3D.Z){
           Rm = new double[][]{{c, 0, -s, 0}, {0, 1, 0, 0}, {s, 0, c, 0}, {0, 0, 0, 1}}; //Ma tran quay quanh Oz
           
       }
       
       Point3D afRotate = nhanMaTranBienDoi3D(p, Rm);
       return afRotate;
    }

}   
