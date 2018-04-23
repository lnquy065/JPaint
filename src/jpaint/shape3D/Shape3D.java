package jpaint.shape3D;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import jpaint.ImagePanel;
import jpaint.LayerPanel;
import jpaint.algorithm.DrawAlgorithm;
import jpaint.algorithm.TransformAlgorithm;
import jpaint.shape2D.Pixel2D;


public class Shape3D implements Serializable{
    public static int TYPE_BOX = 0;
    public static int TYPE_PYRAMID = 1;
    public static int TYPE_CONE = 2;
    public static int TYPE_SPHARE = 3;
    public static int X=0;
    public static int Y=1;
    public static int Z=2;
    public static int OXY=0;
    public static int OXZ=1;
    public static int OYZ=2;
    
    protected ArrayList<Point3D> vertexList = new ArrayList();
    protected ArrayList<Surface> surfaceList = new ArrayList();
    
    protected Point3D[] wrapPoints = new Point3D[8];
 //   protected ArrayList<Point3D> pointList = new ArrayList(); 
    protected ArrayList<Line3D> lineList = new ArrayList();
    protected ArrayList<Elip3D> elipList = new ArrayList();
    protected ArrayList<Pixel2D> pixelList = new ArrayList();
    protected Color outColor;
    protected int[] stroke = {0, 0};
    protected int size =1;
    protected LayerPanel tempPanel;
    private boolean visible=true;
    private double alpha[] = {0,0,0}; // X Y Z
    private Point3D centerPoint3D;
    private int imageW;
    private int imageH;
    
     public void createWires() {
        lineList.clear();
        elipList.clear();
        vertexList.clear();
        surfaceList.clear();
    }
     public void create(int imageW, int imageH) {
         this.imageH=imageH;
         this.imageW=imageW;
     }
    //setter
     public void setVisible(boolean stt) {
         this.visible=stt;
     }
    public void setWrap3D(Point3D p0, Point3D p3, Point3D p5) {
        wrapPoints[0] = p0;
        wrapPoints[3] = p3;
        wrapPoints[5] = p5;
        wrapPoints[1] = new Point3D(p5.x, p0.y, p0.z);
        wrapPoints[2] = new Point3D(p5.x, p3.y, p3.z);
        wrapPoints[4] = new Point3D(p0.x, p5.y, p0.z);
        wrapPoints[6] = new Point3D(p5.x, p5.y, p3.z);
        wrapPoints[7] = new Point3D(p0.x, p5.y, p3.z);
        centerPoint3D = new Point3D ( Math.round((p0.x+p5.x)/2) , Math.round((p0.y+p5.y)/2), Math.round((p0.z+p3.z)/2));
    }
    
    public void setOutColor(Color c) {
        outColor = c;
    }
    public void setDotSize(int size) {
        this.size=size;
    }
    public void setAlphaRAD(double a, int axis) {
        alpha[axis]=a;
    }
        public void setAlphaDEG(int a, int axis) {
        alpha[axis]=Math.toRadians(a);
    }
    //getter
         public boolean isVisible() {
         return visible;
     }
         
    public ImageIcon getShapeIcon() {
        return new ImageIcon(this.getClass().getResource("/rsc/shape/"+getRealShapeName()+".png"));
    }
    
    public Point getWrapPoints2D(int index) {  
        return rotatePoint(wrapPoints[index]).toPoint2DUser();
    }
    
    public String getRealShapeName() {
        return "";
    }
    public Point3D getPoint3DAt(int index) {
        return wrapPoints[index];
    }
    public ArrayList<Pixel2D> getPixelList() {
        return pixelList;
    }
    public double getAlphaRAD(int axis) {
        return alpha[axis];
    }
    
    public Point3D getSize() {
        Point3D ret = new Point3D();
        ret.x = Math.abs(wrapPoints[5].x - wrapPoints[0].x);
        ret.y = Math.abs(wrapPoints[5].y - wrapPoints[0].y);
        ret.z = Math.abs(wrapPoints[3].z - wrapPoints[0].z);
        return ret;
    }
    
    public int getAlphaDEG(int axis) {
        return (int) Math.round(Math.toDegrees(alpha[axis]));
    }
        
    public int getPixelSize() {
        return size;
    }
    //draw
    public void draw(int type) {
        tempPanel.clear();
        pixelList.clear();
        
       if (getSize().x!=0 && getSize().y!=0 && getSize().z!=0 ) {
       getVisibleSurface();
       }
        for (Line3D l: lineList) {
                 Point3D p1 = l.p1;
                 Point3D p2 = l.p2;
                 Point p2D1 = toPoint2DComputer(p1.toPoint2DUser()); 
                 Point p2D2 = toPoint2DComputer(p2.toPoint2DUser()); 
                putPixelList(DrawAlgorithm.algorithmDDA(p2D1, p2D2, l.getStroke()));
        }
        for (Elip3D e: elipList) {
                 Point3D p1 = e.p1;
                 Point3D p2 = e.p2;
                 Point p2D1 = toPoint2DComputer(p1.toPoint2DUser()); 
                 Point p2D2 = toPoint2DComputer(p2.toPoint2DUser()); 
                putPixelList(DrawAlgorithm.algorithmEllipse(p2D1, p2D2, 0 , e.getStroke()));
        }
        
      //  System.out.println("========================");
        
        paintToLayer();
    }
    
    public void setTypeLine(Line3D l, int MODE) {
        int index = lineList.indexOf(l);
      //  System.out.println(index);
        if (index!=-1) lineList.set(index, new Line3D(l.p1, l.p2, MODE, l.id));
    }
    
    public Line3D rotateLine(Line3D line) {
        Point3D p1 = rotatePoint(line.p1);
        Point3D p2 = rotatePoint(line.p2);
        return new Line3D(p1,p2, line.getStrokeType(), line.id);
    }
    
    public void rotateAllPoint() {
        for (int i=0;i<8;i++) {
            wrapPoints[i] = TransformAlgorithm.trans3DRotate(wrapPoints[i], centerPoint3D, alpha[X], X);
            wrapPoints[i] = TransformAlgorithm.trans3DRotate(wrapPoints[i], centerPoint3D, alpha[Y], Y);
            wrapPoints[i] = TransformAlgorithm.trans3DRotate(wrapPoints[i], centerPoint3D, alpha[Z], Z);
        }  
    }
    
    public Point3D rotatePoint(Point3D p ) {
        Point3D ret;
           ret = TransformAlgorithm.trans3DRotate(p, centerPoint3D, alpha[X], X);
            ret = TransformAlgorithm.trans3DRotate(ret, centerPoint3D, alpha[Y], Y);
            ret = TransformAlgorithm.trans3DRotate(ret, centerPoint3D, alpha[Z], Z);      
            return ret;
    }
    
    public void rotateAllLine() {
        for(Line3D line: lineList) {
            int index = lineList.indexOf(line);
            Point3D p1 = line.p1;
            Point3D p2 = line.p2;
            p1 = rotatePoint(p1);
            p2 = rotatePoint(p2);
            lineList.set(index, new Line3D(p1,p2,line.getStrokeType(), line.id));
        }  
    }
    
    public void paintToLayer() {                //Ve tat ca pixel trog pixelList
        for (Pixel2D e : pixelList) {
            tempPanel.setAndPaintPixel(e, 0);
        }
        tempPanel.repaint();
    }    
   public void paintToLayer(LayerPanel lp) {                //Ve tat ca pixel trog pixelList
        for (Pixel2D e : pixelList) {
            lp.setAndPaintPixel(e, 0);
        }
        lp.repaint();
    }    
    
    public void putpixel(int x, int y) {        //Them pixel vao pixelList
        pixelList.add(new Pixel2D(x, y, outColor, size));
    }
    public void putPixelList(ArrayList<Point> pList) {
        for (Point p: pList) {
            pixelList.add(new Pixel2D(p.x, p.y, outColor, size));
        }
    }
    
    public ArrayList<Surface> getVisibleSurface() {
        ArrayList<Surface> ret= new ArrayList();
        for (Surface s: surfaceList) {
            if (s.isVisible()==false) {
                ret.add(s);
                for(Point3D p: s.getVertex()) {
                    for (int i=0;i<8;i++) {
                        if (p.equals(wrapPoints[i])) {
                         //   System.out.print(i+" ");
                            break;
                        }
                    }
                }
               // System.out.println("");
            }
        }   
       // System.out.println("=====Line dash=====");
        ArrayList<Line3D> dupp = new ArrayList();
        for (int i=1;i<ret.size();i++) {
            for (Line3D line: ret.get(i).getLine()) {
                if (ret.get(i-1).getLine().contains(line)) {
                    dupp.add(line);
                   // line.setStroke(Line3D.TYPE_DASH);
                    setTypeLine(line, Line3D.TYPE_DASH);
                 //   System.out.println(line);
                }
            }
        };
        if (ret.size()>1) {
            for (Line3D line: ret.get(0).getLine()) {
                if (ret.get(ret.size()-1).getLine().contains(line)) {
                    dupp.add(line);
                   // line.setStroke(Line3D.TYPE_DASH);
                    setTypeLine(line, Line3D.TYPE_DASH);
                  //  System.out.println(line);
                }
            }            
        }
        
        
        

        return ret;
    }
    
    public int getType() {
        return -1;
    }
    public Point toPoint2DComputer(Point p) {
         Point onImg = new Point(p.x + imageW / 2, -(p.y - imageH / 2));
         return onImg;
    }

    public void setLayerPanel(LayerPanel tempLayer) {
        this.tempPanel = tempLayer;
    }

    public Color getOutLineColor() {
        return outColor;
    }

    public void setOutlineColor(Color ret) {
        outColor = ret;
    }
}
 class Line3D implements Serializable{
     public static int TYPE_NORMAL=0;
     public static int TYPE_DASH=1;
     public Point3D p1;
     public Point3D p2;
     private int[] stroke;
     private int typeStroke;
     public String id;
     
     public Line3D(Point3D p1, Point3D p2, int stroke, String id) {
         this.p1 = p1;
         this.p2 = p2;
         if (stroke==TYPE_NORMAL) {
             this.stroke = new int[]{0,0};
         } else {
             this.stroke = new int[]{5,5};
         }
         typeStroke=stroke;
         this.id=id;
     }
     public void setStroke(int stroke) {
          if (stroke==TYPE_NORMAL) {
             this.stroke = new int[]{0,0};
         } else {
             this.stroke = new int[]{5,5};
         }
         typeStroke=stroke;        
     }
     
     public int getStrokeType() {
         return typeStroke;
     }
     
     public int[] getStroke() {
         return stroke;
     }
     public boolean equals(Object o) {
         Line3D l=(Line3D) o;
            return this.id.equals(l.id);
     }
     public String toString() {
         return "{ ["+p1.x+ ", "+p1.y+", "+ p1.z+"] ["+p2.x+", "+p2.y+", "+ p2.z+"] }";
     }
 }

class Elip3D implements Serializable{
    public static int TYPE_NORMAL=0;
    public static int TYPE_DASH=1;
    public Point3D p1;
    public Point3D p2;
    public int stroke;
    public Elip3D(Point3D p1, Point3D p2, int stroke) {
        this.p1=p1;
        this.p2=p2;
        this.stroke=stroke;
    }
    public int[] getStroke() {
      if (stroke==TYPE_NORMAL) {
            return new int[]{0,0};
         } else {
            return new int[]{5,5};
         }         
    }
}

class Surface implements Serializable{
    private ArrayList<Point3D> vertex = new ArrayList(); 
    private ArrayList<Line3D> lineList = new ArrayList();
    public Surface(Point3D... v) {
        for (int i=0;i<v.length;i++) {
            vertex.add(v[i]);
        }
    }
    public Surface(Point3D opp, Line3D... l) {
        for (int i=0;i<l.length;i++) {
            lineList.add(l[i]);
            Point3D p1 = l[i].p1;
            Point3D p2 = l[i].p2;
            if (!vertex.contains(p1)) vertex.add(p1);
            if (!vertex.contains(p2)) vertex.add(p2);
        }        
        vertex.add(opp);
    }
    
    public void addVertex(Point3D... v) {
        for (int i=0;i<v.length;i++) {
            vertex.add(v[i]);
        }
    }
    
    public ArrayList<Line3D> getLine() {
        return lineList;
    }
    
    public ArrayList<Point3D> getVertex() {
        return vertex;
    }

    public int[] getVector() {
        Point3D vertex1 = vertex.get(0);
        Point3D vertex2 = vertex.get(1);
        Point3D vertex3 = vertex.get(2);
        
        Point3D v  = vertex.get(vertex.size()-1);   //Dinh doi dien
        
        int[] u12= { vertex2.x- vertex1.x, vertex2.y- vertex1.y, vertex2.z - vertex1.z };    //Vector chi phuong
        int[] u13= { vertex3.x- vertex1.x, vertex3.y- vertex1.y,  vertex3.z - vertex1.z  }; 
        
        int[] n = new int[3];                               //Vector phap tuyen
        n[0] = u12[1]*u13[2]-u12[2]*u13[1];
        n[1] = u12[2]*u13[0]-u12[0]*u13[2];
        n[2] = u12[0]*u13[1]-u12[1]*u13[0];
        //Xet huong cua vector phap tuyen
        
        Point3D vVP = new Point3D (  v.x - vertex1.x,   v.y - vertex1.y   ,  v.z - vertex1.z);
        int tichNvVP = n[0]*vVP.x + n[1]*vVP.y +n[2]*vVP.z;
        //System.out.println(tichNvVP);
        if (tichNvVP>=0) {
            n[0]*=-1;
            n[1]*=-1;
            n[2]*=-1;
        }
        
        
        return n;
    }
    public String toString() {
        String out="";
        for (Point3D p: vertex) {
            out = out.concat( " ["+ p.x+", "+p.y+", "+p.z+"]");
        }
        return out;
    }
    public boolean isVisible() {        //Mat phang co the nhin thay
        int[] v = {1,1,1};     //Vector huong nhin
        //int[] v = { s[0]-vertex.get(0).x, s[1]-vertex.get(0).y, s[2]-vertex.get(0).z};
        int[] n = getVector();  //Vector phap tuyen
       // System.out.println();
        int tichVoHuong = v[0]*n[0]  + v[1]*n[1] + v[2]*n[2]; 
        //System.out.println(tichVoHuong);
        if (tichVoHuong>=0) return true;    //Nhin thay
        return false;       //RIP mat phang
    }
}