package jpaint;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.Timer;
import jpaint.algorithm.TransformAlgorithm;
import jpaint.shape2D.Shape2D;
import jpaint.shape3D.Point3D;
import jpaint.shape3D.Shape3D;

public class WrapPane2D extends JPanel {


    
    static int DEG = 0;
    static int RAD = 1;
    static int WRAP_STYLE_SQUARE = 0;
    static int WRAP_STYLE_RECT = 1;
    static int WRAP_STYLE_CIRCLE = 2;
    static int SHAPE_MODE_2D = 0;
    static int SHAPE_MODE_3D = 1;
    
    private Point[] points2D = {null, null, null, null, null, null, null, null, null, null};       //8 dinh chua quay
    private Point[] anchor = {null, null, null, null, null, null, null, null};                  //8 diem neo
    private Point[] pointAlpha = {null, null, null, null, null, null, null, null};              //8 dinh da quay
    private Point[] pointAlphaReal = {null, null, null, null, null, null, null, null};          //8 dinh toa do that da quay
    private Point[] anchorAlpha = {null, null, null, null, null, null, null, null};
    private Point[] centerPoint = {null, null};
    private ShapeAnchor[] shapeAnchorPoints = {null, null, null, null};                 //4 diem neo cua shape
    private Point[] shapeAnchorRealPoints = {null, null, null, null};                   //4 diem neo cua shape toa do that
    private Point[] flipLine = {null, null};
    private Point rotatePoint;
    private boolean rotatePointIsCenter = true;
    private int rotatePointD;
    private float[] shapeAnchors;
    private double rotatePointAlpha = 0;
    private BufferedImage img;
    private JScrollPane sp;
    private ImagePanel ip;
    private MainForm main;
    private boolean flipStt = true;
    private double alpha = 0;
    private double alphaT = 0;
    private int zoom = 1;
    private int[][] m = {{5, 5, 0}, {5, 5, 1}};
    private boolean debug = false;
    
    private Point3D centerPoint3D;
    private Point3D[] points3D = new Point3D[18]; 
    private Shape2D curShape2D;                                                                     //Hinh hien tai
    private Shape3D curShape3D;
    
    private boolean show = false;
//Phu thuoc
    private JPanel propertiesPanel;

    private Timer tm = new Timer(400, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            flipStt = !flipStt;
            clearImg();
            if (curShape2D!=null) {
            paintAnchor();
            paintCenterPoint();
            paintRotateAnchor();
            paintRectLine();
            if (curShape2D.getShapeName().equals("polygons")) paintCircleLine();
            paintDebug();
            paintFlipLine();
            paintShapeAnchor();
            } else {
            paintAnchor3D();
            }
            repaint();
        }
    });
    private Point rotatePointT;
    private boolean isManual;

    public WrapPane2D(MainForm main, JPanel propertiesPanel) {
        this.main = main;
        this.propertiesPanel = propertiesPanel;
        this.setBackground(Color.GREEN);
        this.setOpaque(false);
    }

    public void create(Shape2D s, Point p) {
        show = true;
        setShape(s);
        alpha = s.getAlpha();
        setPointWrap2D(p, p);
        curShape2D.setLayerPanel(ip.getTempLayer());
        showProperties(SHAPE_MODE_2D);
    }

    public void create(Shape2D s) {
        show = true;
        setShape(s);
        alpha = s.getAlpha();
        this.setPointWrap2D(s.getWrapAt(0), s.getWrapAt(4));
        curShape2D.setLayerPanel(ip.getTempLayer());
        showProperties(SHAPE_MODE_2D);
    }
    
    public void create(Shape3D s, Point3D p0) {
        show = true;
        curShape3D = s;
        alpha = 0;
        
        setPointWrap3D(p0, p0.clone(), p0.clone());
        curShape3D.setLayerPanel(ip.getTempLayer());
        showProperties(SHAPE_MODE_3D);
    }

    //Getter
    public int getRV() {
        Point pc = centerPoint[0];
        return Math.abs(pc.x - points2D[0].x);
    }

    public int getRH() {
        Point pc = centerPoint[0];
        return Math.abs(pc.y - points2D[0].y);
    }

    public Shape2D getCurShape() {
        return curShape2D;
    }

    public Dimension getSizeWrap2D() {
        return new Dimension(Math.abs(points2D[2].x - points2D[0].x), Math.abs(points2D[6].y - points2D[1].y));
    }
    
    public Point3D getSizeWrap3D() {
        int lenght = Math.abs(points3D[5].x-points3D[0].x);
        int width = Math.abs(points3D[5].y-points3D[0].y);
        int height = Math.abs(points3D[3].z-points3D[0].z);
        return new Point3D(lenght, width, height );
    }

    public Point getRealLocation(Point p) {
        int w = ip.getLocation().x;
        int h = ip.getLocation().y;
        return new Point(p.x * zoom + w, p.y * zoom + h);
    }
    
    public Point getShapeAnchorPoint(int i) {
        return shapeAnchorPoints[i].getLocation();
    }

    public Point getAnchor(int index) {
        return anchor[index];
    }

    public int getSideOfPoint(Point p, Point pTam) {
        Point t = pTam;
        int index = -1;
        if (p.x < t.x && p.y < t.y) {
            index = 0;
        }
        if (p.x < t.x && p.y > t.y) {
            index = 6;
        }
        if (p.x > t.x && p.y < t.y) {
            index = 2;
        }
        if (p.x > t.x && p.y > t.y) {
            index = 4;
        }
        if (p.x == t.x && p.y > t.y) {
            index = 5;
        }
        if (p.x == t.x && p.y < t.y) {
            index = 1;
        }
        if (p.x > t.x && p.y == t.y) {
            index = 3;
        }
        if (p.x < t.x && p.y == t.y) {
            index = 7;
        }
        return index;
    }

    public Point getFinalLocation(Point p) {
        Point t = centerPoint[0];
        int index = getSideOfPoint(p, t);
        Point np = getRealLocation(p);
        Point r1 = getRealLocation(points2D[0]);
        Point r2 = getRealLocation(points2D[2]);
        Point r3 = getRealLocation(points2D[4]);
        Point r4 = getRealLocation(points2D[6]);
        
        int w = r2.x - r1.x;
        int h = r3.y- r1.y;
        int offset = zoom/2;
        
        switch (index) {
            case 0:
                np.setLocation(np.x - 1, np.y - 1);
                break;
            case 1:
                np.setLocation(r1.x+w/2+offset , np.y - 1);
                break;
            case 2:
                np.setLocation(np.x + zoom, np.y - 1);
                break;
            case 3:
                np.setLocation(np.x + zoom, r1.y+h/2+offset);
                break;
            case 4:
                np.setLocation(np.x + zoom, np.y + zoom);
                break;
            case 5:
                np.setLocation(r1.x+w/2+offset , np.y + zoom);
                break;
            case 6:
                np.setLocation(np.x - 1, np.y + zoom);
                break;
            case 7:
                np.setLocation(np.x - 1, r1.y+h/2+offset);
                break;
        }
        return transRotate(np, centerPoint[1], alpha);
    }

    public Point getPointAt(int i) {
        return points2D[i];
    }
    public Point3D getPoint3DAt(int index) {
        return points3D[index];
    }

    public int getIndexOppersite(int i) {
        if (i >= 0 && i < 4) {
            return i + 4;
        }
        return i - 4;
    }

    public Point getPointAtOffset(int i, int dx, int dy) {
        return new Point(points2D[i].x + dx, points2D[i].y + dy);
    }

    public Point getCenterPoint(int mode) {
        return centerPoint[mode];
    }
    
    public boolean isManual() {
        return isManual;
    }
    
    public Timer getWrapTimer() {
        return tm;
    }

    public double getAlpha(int mode) {
        if (mode == DEG) {
            return (int) Math.toDegrees(curShape2D.getAlpha());
        } else {
            return alpha;
        }
    }

    //Setter
    public void setShapeAnchor(Point p, int index) {
        Point pR = transRotate(p, centerPoint[0], -alpha);
        float[] scale = curShape2D.getAnchorScale();
        int[] location = curShape2D.getAnchorLocation();
        int side = shapeAnchorPoints[index].getSide();
        Point pNeo = points2D[curShape2D.getDim().width];
        Point pW1 = points2D[curShape2D.getDim().width];
        Point pW2 = points2D[curShape2D.getDim().height];
        int dimX = curShape2D.getDim().width;

        // points[8]=pR;
        // points[9]=pNeo;
        Point pNew = shapeAnchorPoints[index].getLocation();
        if (side == 0 || side == 2) {
            scale[index] = (float) (Math.abs(pR.x - pNeo.x) * 1.0 / getSizeWrap2D().width);
        }
        if (side == 1 || side == 3) {
            scale[index] = (float) (Math.abs(pR.y - pNeo.y) * 1.0 / getSizeWrap2D().height);
        }
        //Ko cho diem Neo ra khoi Wrap
        if (side == 0 || side == 2) {
            if (pR.x < pW1.x && (dimX == 0 || dimX == 6)) {
                scale[index] = 0;
            }
            if (pR.x > pW2.x && (dimX == 0 || dimX == 6)) {
                scale[index] = 1;
            }
            if (pR.x > pW1.x && (dimX == 2 || dimX == 4)) {
                scale[index] = 0;
            }
            if (pR.x < pW2.x && (dimX == 2 || dimX == 4)) {
                scale[index] = 1;
            }
        } else {
            if (pR.y < pW1.y && (dimX == 0 || dimX == 2)) {
                scale[index] = 0;
            }
            if (pR.y > pW2.y && (dimX == 0 || dimX == 2)) {
                scale[index] = 1;
            }
            if (pR.y > pW1.y && (dimX == 4 || dimX == 6)) {
                scale[index] = 0;
            }
            if (pR.y < pW2.y && (dimX == 4 || dimX == 6)) {
                scale[index] = 1;
            }
        }
        //Rang buoc cac diem Neo
        if (curShape2D.getShapeName().equals("trapezoid")) {
            if (index == 0 && scale[0] > scale[1]) {
                scale[1] = scale[0];
            }
            if (index == 1 && scale[0] > scale[1]) {
                scale[0] = scale[1];
            }
        }

        curShape2D.setAnchorScale(scale.clone());
        //  shapeAnchorRealPoints[index] = transRotate(shapeAnchorPoints[index].getLocation(), centerPoint[1], alpha);
    }

    public void setNewSize() {
        int fwidth = 1, fheight = 1;
        if (ip == null) {
            return;
        }
        fwidth = ip.getLocation().x * 2 + ip.getWidth();
        fheight = ip.getLocation().y * 2 + ip.getHeight();
        img = new BufferedImage(fwidth, fheight, BufferedImage.TYPE_INT_ARGB);
        this.setSize(fwidth, fheight);
    }
    
    public void setManual(boolean flag) {
        isManual=flag;
    }
    
    public void setPointWrap3D(Point3D p0, Point3D p3, Point3D p5) {
        points3D[0] = p0;
        points3D[3] = p3;
        points3D[5] = p5;
        points3D[1] = new Point3D(p5.x, p0.y, p0.z);
        points3D[2] = new Point3D(p5.x, p3.y, p3.z);
        points3D[4] = new Point3D(p0.x, p5.y, p0.z);
        points3D[6] = new Point3D(p5.x, p5.y, p3.z);
        points3D[7] = new Point3D(p0.x, p5.y, p3.z); 
        centerPoint3D = new Point3D ( Math.round((p0.x+p5.x)/2) , Math.round((p0.y+p5.y)/2), Math.round((p0.z+p3.z)/2));
        curShape3D.setWrap3D(p0, p3, p5);
    }

    public void setPointWrap2D(Point p1, Point p2) {
        if (p1.x > p2.x) {                 //x1 > x2
            if (p1.y > p2.y) {                  //y1 > y2
                //System.out.println("x1>x2|y1>y2");
                points2D[0] = p2;
                points2D[2] = new Point(p1.x, p2.y);
                points2D[4] = p1;
                points2D[6] = new Point(p2.x, p1.y);
                curShape2D.setDimension(4, 0);
            } else {                            //y1 <= y2
                //System.out.println("x1>x2|y1<=y2");
                points2D[0] = new Point(p2.x, p1.y);
                points2D[2] = p1;
                points2D[4] = new Point(p1.x, p2.y);
                points2D[6] = p2;
                curShape2D.setDimension(2, 6);
            }
        } else //x1 <= x2
         if (p1.y > p2.y) {                 //y1 > y2
                // System.out.println("x1<=x2|y1>y2");
                points2D[0] = new Point(p1.x, p2.y);
                points2D[2] = p2;
                points2D[4] = new Point(p2.x, p1.y);
                points2D[6] = p1;
                curShape2D.setDimension(6, 2);
            } else {                            //y1 < y2
                //System.out.println("x1<=x2|y1<y2");
                points2D[0] = p1;
                points2D[2] = new Point(p2.x, p1.y);
                points2D[4] = p2;
                points2D[6] = new Point(p1.x, p2.y);
                curShape2D.setDimension(0, 4);
            }

        points2D[1] = new Point((points2D[0].x + points2D[2].x) / 2, (points2D[0].y + points2D[2].y) / 2);
        points2D[3] = new Point((points2D[2].x + points2D[4].x) / 2, (points2D[2].y + points2D[4].y) / 2);
        points2D[5] = new Point((points2D[4].x + points2D[6].x) / 2, (points2D[4].y + points2D[6].y) / 2);
        points2D[7] = new Point((points2D[6].x + points2D[0].x) / 2, (points2D[6].y + points2D[0].y) / 2);
        centerPoint[0] = new Point((points2D[0].x + points2D[4].x) / 2, (points2D[0].y + points2D[4].y) / 2);
        centerPoint[1] = new Point(getRealLocation(centerPoint[0]).x + zoom / 2, getRealLocation(centerPoint[0]).y + zoom / 2);
        curShape2D.setWrap(points2D[0], points2D[4]);
        for (int i = 0; i < 8; i++) {
            if (points2D[i] == null) {
                continue;
            }
            if (i % 2 == 0) {
                setAnchor(getRealLocation(points2D[i]), i);
            }
        }
    }
    
    public void setPoint3DAt(Point3D p, int index) {
       // this.setPointWrap3D(points3D[0], points3D[3], p);
        switch(index) {
            case 3:
                this.setPointWrap3D(curShape3D.getPoint3DAt(0), p, curShape3D.getPoint3DAt(5));break;
            case 5:
                this.setPointWrap3D(points3D[0], points3D[3], p);break;
            case 0:
                this.setPointWrap3D(p, points3D[3], points3D[5]);break;
        }
        
    }

    public void setPointManualAt(Point p, int i, int mode) {
        long startTime = System.nanoTime();
        if (mode == WRAP_STYLE_RECT) {
            points2D[4] = p;
            points2D[2] = new Point(points2D[4].x, points2D[0].y);
            points2D[6] = new Point(points2D[0].x, points2D[4].y);
        } else if (mode == WRAP_STYLE_SQUARE) {
            int d = Math.abs(p.x - points2D[0].x);
            int dy = p.y - points2D[0].y;
            d = dy >= 0 ? d : -d;
            //System.out.println(dy);
            points2D[4] = new Point(p.x, points2D[0].y + d);
            points2D[2] = new Point(points2D[4].x, points2D[0].y);
            points2D[6] = new Point(points2D[0].x, points2D[4].y);
        }
        // System.out.println(System.nanoTime()-startTime);
        points2D[1] = new Point((points2D[0].x + points2D[2].x) / 2, (points2D[0].y + points2D[2].y) / 2);
        points2D[3] = new Point((points2D[2].x + points2D[4].x) / 2, (points2D[2].y + points2D[4].y) / 2);
        points2D[5] = new Point((points2D[4].x + points2D[6].x) / 2, (points2D[4].y + points2D[6].y) / 2);
        points2D[7] = new Point((points2D[6].x + points2D[0].x) / 2, (points2D[6].y + points2D[0].y) / 2);
        curShape2D.clearDim();
        setDimension(points2D[0], points2D[4]);
        curShape2D.setWrap(points2D[curShape2D.getDim().width], points2D[curShape2D.getDim().height]);
        isManual=true;
    }

    public void setDimension(Point p1, Point p2) {
        if (p1.x > p2.x) {                 //x1 > x2
            if (p1.y > p2.y) {                  //y1 > y2
                curShape2D.setDimension(4, 0);
            } else {                            //y1 <= y2
                curShape2D.setDimension(2, 6);
            }
        } else //x1 <= x2
         if (p1.y > p2.y) {                 //y1 > y2
                curShape2D.setDimension(6, 2);
            } else {                            //y1 < y2
                curShape2D.setDimension(0, 4);
            }
    }

    public void setSrollPane(JScrollPane scrollPanel) {
        this.sp = scrollPanel;
        this.setVisible(true);
    }
    
    public void setAlphaDEG3D(int x, int y, int z) {
        curShape3D.setAlphaDEG(x, Shape3D.X);
        curShape3D.setAlphaDEG(y, Shape3D.Y);
        curShape3D.setAlphaDEG(z, Shape3D.Z);
    }

    public void setImagePane(ImagePanel ip) {
        this.ip = ip;
    }

    public void setShape(Shape2D s) {
        this.curShape2D = s;
    }

    public void setAnchor(Point p, int index) {
        anchor[index] = new Point(p.x, p.y);
    }

    public void setLocationRotatePoint(Point p) {
        rotatePoint = p;
        if (p.x != centerPoint[0].x || p.y != centerPoint[0].y) {
            rotatePointIsCenter = false;
            rotatePointD = (int) Point.distance(centerPoint[0].x, centerPoint[0].y, p.x, p.y);
        } else {
            rotatePointIsCenter = true;
            rotatePointD = 0;
        }
    }

    public void setSizeWrap2D(int width, int height) {
        int offsetX = width % 2 == 0 ? 0 : 1;
        int offsetY = height % 2 == 0 ? 0 : 1;
        Point p0 = new Point(centerPoint[0].x - width / 2, centerPoint[0].y - height / 2);
        Point p4 = new Point(centerPoint[0].x + width / 2 + offsetX, centerPoint[0].y + height / 2 + offsetY);
        setPointWrap2D(p0, p4);
    }
    
    public void setSizeWrap3D(int length, int width, int height) {
        int offsetL = length % 2 == 0 ? 0 : 1;
        int offsetW = width % 2 == 0 ? 0 : 1;
        int offsetH = height % 2 == 0 ? 0 : 1;
        Point3D p0 = new Point3D(centerPoint3D.x - length / 2, centerPoint3D.y - width / 2, centerPoint3D.z - height / 2);
        Point3D p5 = new Point3D(centerPoint3D.x + length / 2 +offsetL, centerPoint3D.y + width / 2 +offsetW, p0.z);
        Point3D p3 = new Point3D(centerPoint3D.x - length / 2, centerPoint3D.y - width / 2, centerPoint3D.z + height / 2 + offsetH);
        setPointWrap3D(p0, p3, p5);
    }

    public void setPointResizeAt(Point p, int i, int mode) {
        points2D[i] = p;
        int index = -1;
        if (i >= 0 && i < 4) {
            index = i + 4; //Tim canh doi dien
        } else {
            index = i - 4;
        }
        if (i % 2 == 0) {
            if (mode == WRAP_STYLE_RECT) {
            } else if (mode == WRAP_STYLE_SQUARE) {
                int d = Math.abs(p.x - points2D[index].x);
                if (i == 2 || i == 0) {
                    d = -d;
                }
                if (i == 4 || i == 6) {
                    d = d;
                }
                //System.out.println(dy);
                p.setLocation(p.x, points2D[index].y + d);
            }
            points2D[8] = p;
            points2D[9] = points2D[index];      //Diem doi dien

            setPointWrap2D(p, points2D[index]);
        } else {
            if (i == 1) {
                points2D[0] = new Point(points2D[0].x, p.y);
                index = 4;
            }
            if (i == 7) {
                points2D[0] = new Point(p.x, points2D[0].y);
                index = 4;
            }
            if (i == 3) {
                points2D[4] = new Point(p.x, points2D[4].y);
                index = 0;
            }
            if (i == 5) {
                points2D[4] = new Point(points2D[4].x, p.y);
                index = 0;
            }
            setPointWrap2D(points2D[0], points2D[4]);
        }
    }

    public void setAlphaAdd(double a) {
        int a2 = (int) Math.toDegrees(a);
        alpha += Math.toRadians(a2);
        int alphaDEG = (int) Math.toDegrees(alpha);
        int delta;
        if (alphaDEG > 180 || alphaDEG < -180) {
            delta = Math.abs(alphaDEG) - 180;
            // System.out.println(alphaDEG + " " + delta);
            alphaDEG = -alphaDEG + (2 * delta * (alphaDEG > 180 ? 1 : alphaDEG < -180 ? -1 : 0));
            // System.out.println(alphaDEG);
            alpha = Math.toRadians(alphaDEG);
        }
    }

    public void setAlpha(double a) {
        this.alpha = a;
    }
    
    public void showProperties(int mode) {
        propertiesPanel.getComponent(1).setVisible(true);
        
        if (mode==SHAPE_MODE_2D) {
            main.proLocationPanel.setVisible(true);
            main.proSizePanel.setVisible(true);
            main.proTransformPanel.setVisible(true);
            main.proPointsPanel.setVisible(true);
            
            main.proLocation3DPanel.setVisible(false);
            main.proSize3DPanel.setVisible(false);
            main.proRotate3DPanel.setVisible(false);
            main.proPoints3DPanel.setVisible(false); 
        }
        if (mode==SHAPE_MODE_3D) {
            main.proLocation3DPanel.setVisible(true);
            main.proSize3DPanel.setVisible(true);
            main.proRotate3DPanel.setVisible(true);
            main.proPoints3DPanel.setVisible(true);  
            
            main.proLocationPanel.setVisible(false);
            main.proSizePanel.setVisible(false);
            main.proTransformPanel.setVisible(false);
            main.proPointsPanel.setVisible(false);
        }
    }

    public void setRadius(int r1, int r2) {
        this.setSizeWrap2D(r2 * 2, r1 * 2);
    }

    public void transShapeRotateAtCorner(Point p, int rotAn) {        //Tinh goc alpha PO,OrotAn
        Point pTam;    //Quay quanh tam
        pTam = centerPoint[0];
        Point vP = new Point(pTam.x - p.x, pTam.y - p.y);                        //Vector P->Tam
        Point vA = new Point(pTam.x - points2D[rotAn].x, pTam.y - points2D[rotAn].y); //Vector Tam->DiemNeo
        double PxA = vP.x * vA.x + vP.y * vA.y;
        double dP = Math.sqrt(vP.x * vP.x + vP.y * vP.y);
        double dA = Math.sqrt(vA.x * vA.x + vA.y * vA.y);
        double cosAlpha = PxA / (dP * dA);
        alpha = Math.acos(cosAlpha);                            //Goc 2 vector P,PTam va DiemNeo,PTam
        double beta = goc2Vector(points2D[0], pTam, points2D[1]);     //point[0], pTam, point[1]
        if (rotAn == 0) {                                       //Quay DiemNeo va P ve truc Ox, xet chieu Am hay Duong
            beta = beta;
        }
        if (rotAn == 2) {
            beta = -beta;
        }
        if (rotAn == 4) {
            beta = -(Math.toRadians(180) - beta);
        }
        if (rotAn == 6) {
            beta = Math.toRadians(180) - beta;
        }

        points2D[8] = transRotate(points2D[rotAn], pTam, beta);
        points2D[9] = transRotate(p, pTam, beta);
        if (points2D[9].x < points2D[8].x) {
            alpha = -alpha;
        }
    }

    public void setFlipLine(Point p1, Point p2) {
        flipLine[0] = p1;
        flipLine[1] = p2;
    }

    public void setLocationWrapCenter2D(Point p) {
        int deltaX = p.x - centerPoint[0].x;
        int deltaY = p.y - centerPoint[0].y;      
        Point p0 = TransformAlgorithm.transLocation(points2D[0], deltaX, deltaY);
        Point p4 =  TransformAlgorithm.transLocation(points2D[4], deltaX, deltaY);
        this.setPointWrap2D(p0, p4);
    }
    
    public void setLocationWrapCenter3D(Point3D p) {
        int deltaX = p.x-centerPoint3D.x;
        int deltaY = p.y-centerPoint3D.y;
        int deltaZ = p.z-centerPoint3D.z;
        
        Point3D p0 = TransformAlgorithm.trans3DLocation(points3D[0], deltaX, deltaY, deltaZ);
        Point3D p3 = TransformAlgorithm.trans3DLocation(points3D[3], deltaX, deltaY, deltaZ);
        Point3D p5 = TransformAlgorithm.trans3DLocation(points3D[5], deltaX, deltaY, deltaZ);
        System.out.println(points3D[0]+" -> "+p0);
        System.out.println(points3D[3]+" -> "+p3);
        System.out.println(points3D[5]+" -> "+p5);
        setPointWrap3D(p0, p3, p5);
    }

    //Paint
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(img, 0, 0, null);
    }
    
    public void paintWrap3D() {
        clearImg();
        if (curShape3D==null) return;      

        curShape3D.createWires();
        curShape3D.draw(curShape3D.getType());

        
        //paintDebug3D();
        //algorithmDDA(getRealLocation(toPoint2DComputer(points3D[0].toPoint2DUser())), getRealLocation(toPoint2DComputer(points3D[5].toPoint2DUser())), flipStt == true ? m[0] : m[1], Color.BLACK);
       // algorithmDDA(getRealLocation(toPoint2DComputer(points3D[0].toPoint2DUser())), getRealLocation(toPoint2DComputer(points3D[3].toPoint2DUser())), flipStt == true ? m[0] : m[1], Color.BLACK);
        main.setWrapPaneControl3DVisible(true);
       paintAnchor3D();
       repaint();       
       tm.start();
          notifyProperties();
    }

    public void paintWrap2D() {
        clearImg();
        if (curShape2D==null) return;
        
        //Tinh toan cac diem dieu chinh wrap
        for (int i = 0; i < 8; i++) {
            if (points2D[i] == null) {
                continue;
            }
            pointAlpha[i] = transRotate(points2D[i], centerPoint[0], alpha);       //Su dung cho contain
            pointAlphaReal[i] = getFinalLocation(points2D[i]);                     //Su dung cho anchor
            setAnchor(pointAlphaReal[i], i);
        }
        
        if (curShape2D.getShapeName().equals("polygons")) {
            anchor[1]=anchor[3]=anchor[5]=anchor[7]=null;
        }
         
        // Tin toan diem Tam xoay
        centerPoint[0] = new Point((points2D[0].x + points2D[4].x) / 2, (points2D[0].y + points2D[4].y) / 2);
        centerPoint[1] = new Point(getRealLocation(centerPoint[0]).x + zoom / 2, getRealLocation(centerPoint[0]).y + zoom / 2);
        if (rotatePointIsCenter) {
            rotatePoint = centerPoint[0];
        }
        
        //TInh toan cac diem dieu chinh Shape
        int[] t = curShape2D.getAnchorLocation();
        float[] scale = curShape2D.getAnchorScale();
        for (int i = 0; i < 3; i++) {
            if (t[i] != -1) {
                int dim = curShape2D.getDim().width;
                Point r = points2D[curShape2D.getDim().width];
                int offSetX = (int) (this.getSizeWrap2D().width * scale[i]);
                int offSetY = (int) (this.getSizeWrap2D().height * scale[i]);
                Point nP = null;
                if (t[i] == 0 || t[i] == 2) {

                    nP = new Point(r.x + ((dim == 0 || dim == 6) ? offSetX : -offSetX), r.y);
                } else {
                    nP = new Point(r.x, r.y + (int) (this.getSizeWrap2D().height * scale[i]));
                }
                shapeAnchorPoints[i] = new ShapeAnchor(nP, t[i]);
            } else {
                shapeAnchorPoints[i] = null;
            }
        }

        curShape2D.setAlpha(alpha);
        curShape2D.createPoints();
        curShape2D.draw(curShape2D.getType());

        paintAnchor();
        paintCenterPoint();
        paintRotateAnchor();
        paintRectLine();
        if (curShape2D.getShapeName().equals("polygons")) paintCircleLine();
        paintDebug();
        paintFlipLine();
        paintShapeAnchor();
        repaint();

        tm.start();
        main.setWrapPaneControl2DVisible(true);
        notifyProperties();
    }

    public void paintRectLine() {
        algorithmDDA(pointAlphaReal[0], pointAlphaReal[2], flipStt == true ? m[0] : m[1], Color.BLACK);
        algorithmDDA(pointAlphaReal[2], pointAlphaReal[4], flipStt == true ? m[0] : m[1], Color.BLACK);
        algorithmDDA(pointAlphaReal[4], pointAlphaReal[6], flipStt == true ? m[0] : m[1], Color.BLACK);
        algorithmDDA(pointAlphaReal[6], pointAlphaReal[0], flipStt == true ? m[0] : m[1], Color.BLACK);
    }

    public void paintCircleLine() {
        algorithmCircle(getRealLocation(points2D[0]), getRealLocation(points2D[4]), flipStt == true ? m[0] : m[1], Color.BLACK);
    }
    
    public void paintDebug3D() {
 Graphics g = img.getGraphics();
        Point temp;
        for (int i = 0; i < 10; i++) {
            if (points3D[i] == null) {
                continue;
            }
            temp = getRealLocation(toPoint2DComputer(points3D[i].toPoint2DUser()));
            if (!inIMG(temp));
                g.drawImage(new ImageIcon(getClass().getResource("/rsc/debugDot" + i + ".png")).getImage(), temp.x - 7, temp.y - 7, null);
        }
        repaint();        
    }
    
    public void paintDebug() {
        if (debug == false) {
            return;
        }
        Graphics g = img.getGraphics();
        Point temp;
        for (int i = 0; i < 10; i++) {
            if (points2D[i] == null) {
                continue;
            }
            temp = getRealLocation(points2D[i]);
            if (!inIMG(temp));
            if (i <= 7 && i % 2 == 0) {
                g.drawImage(new ImageIcon(getClass().getResource("/rsc/debugDot" + i + ".png")).getImage(), temp.x - 7, temp.y - 7, null);
            } else if (i == 8) {
                g.drawImage(new ImageIcon(getClass().getResource("/rsc/debugGreen.png")).getImage(), temp.x - 7, temp.y - 7, null);
            } else {
                g.drawImage(new ImageIcon(getClass().getResource("/rsc/debugYellow.png")).getImage(), temp.x - 7, temp.y - 7, null);
            }
        }
        repaint();
    }

    public void clearImg() {
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setBackground(new Color(0, 0, 0, 0));
        g.clearRect(0, 0, img.getWidth(), img.getHeight());
    }

    public void clearWrap() {
        if (curShape2D == null && curShape3D==null) {
            return;
        }
        ip.getTempLayer().clear();
        alpha = 0;
        tm.stop();
        curShape2D = null;
        curShape3D = null;
        rotatePointIsCenter = true;
        clearImg();
        repaint();
        main.setWrapPaneControl2DVisible(false);
        main.setWrapPaneControl3DVisible(false);
        propertiesPanel.getComponent(1).setVisible(false);
        show = false;
    }

    public void clearFlipLine() {
        if (curShape2D == null) {
            return;
        }
        flipLine[0] = flipLine[1] = null;
        repaintWrap2D();
    }
    
    public Point toPoint2DComputer(Point p) {
         Point onImg = new Point(p.x + ip.getImageWidth() / 2, -(p.y - ip.getImageHeight() / 2));
         return onImg;
    }

    public void updateZoom(int z) {
        zoom = z;
        if (ip == null) {
            return;
        }
        setNewSize();
        if (curShape2D!=null) repaintWrapWithFill();
        if (curShape3D!=null) paintWrap3D();
    }

    public void repaintWrap2D() {
        if (points2D[0] == null) {
            return;
        }
        paintWrap2D();
        
    }
    
    public void repaintWrapWithFill() {
        repaintWrap2D();
        if (curShape2D!=null) curShape2D.reFill(tm);
    }

    public void paintAnchor() {
        Graphics g = img.getGraphics();
        for (int i = 0; i < 8; i++) {
            if (anchor[i] == null) {
                continue;
            }
            if (!inIMG(anchor[i])) {
                continue;
            }
            if (flipStt == true) {
                g.drawImage(new ImageIcon(getClass().getResource("/rsc/rectPoint.png")).getImage(), anchor[i].x - 5, anchor[i].y - 5, null);
            } else {
                g.drawImage(new ImageIcon(getClass().getResource("/rsc/rectPoint2.png")).getImage(), anchor[i].x - 5, anchor[i].y - 5, null);
            }
        }
    }
    
    public void paintAnchor3D() {
        Graphics g = img.getGraphics();
        for (int i = 0; i < 8; i++) {
            Point p = getRealLocation(toPoint2DComputer(curShape3D.getWrapPoints2D(i)));
            if (!inIMG(p)) {
                continue;
            }
            if (flipStt == true) {
                g.drawImage(new ImageIcon(getClass().getResource("/rsc/rectPoint.png")).getImage(), p.x - 5, p.y - 5, null);
            } else {
                g.drawImage(new ImageIcon(getClass().getResource("/rsc/rectPoint2.png")).getImage(), p.x - 5, p.y - 5, null);
            }
        }
    }

    public void paintShapeAnchor() {
        if (isManual) return;
        Graphics g = img.getGraphics();
        for (int i = 0; i < 4; i++) {
            if (shapeAnchorPoints[i] == null) {
                continue;
            }
            Point p = getFinalLocation(shapeAnchorPoints[i].getLocation());
            if (!inIMG(p)) {
                continue;
            }
            if (flipStt == true) {
                g.drawImage(new ImageIcon(getClass().getResource("/rsc/ShapeAnchor.png")).getImage(), p.x - 5, p.y - 5, null);
            } else {
                g.drawImage(new ImageIcon(getClass().getResource("/rsc/ShapeAnchor2.png")).getImage(), p.x - 5, p.y - 5, null);
            }
        }
    }

    public void paintRotateAnchor() {
        Graphics g = img.getGraphics();
        Point r1 = getRealLocation(points2D[0]);
        Point r4 = getRealLocation(points2D[4]);
        Point p;
        if (rotatePointIsCenter)
            p = new Point( (r1.x+r4.x+1)/2, (r1.y+r4.y+1)/2);
        else
            p = getRealLocation(rotatePoint);
        p.x+=zoom/2;
        p.y+=zoom/2;
        if (!inIMG(p)) {
            return;
        }
        if (flipStt == true) {
            g.drawImage(new ImageIcon(getClass().getResource("/rsc/circlePoint.png")).getImage(), p.x - 8, p.y - 8, null);
        } else {
            g.drawImage(new ImageIcon(getClass().getResource("/rsc/circlePoint2.png")).getImage(), p.x - 8, p.y - 8, null);
        }
        //repaint();
    }

    public void paintCenterPoint() {
        Graphics g = img.getGraphics();
        Point p;
        Point r1 = getRealLocation(points2D[0]);
        Point r4 = getRealLocation(points2D[4]);
        p = new Point( (r1.x+r4.x+1)/2, (r1.y+r4.y+1)/2);
        p.x+=zoom/2;
        p.y+=zoom/2;
        
        if (!inIMG(p)) {
            return;
        }
        if (rotatePointIsCenter) {
            return;
        }
        if (flipStt == true) {
            g.drawImage(new ImageIcon(getClass().getResource("/rsc/rotateAnchor.png")).getImage(), p.x - 8, p.y - 8, null);
        } else {
            g.drawImage(new ImageIcon(getClass().getResource("/rsc/rotateAnchor2.png")).getImage(), p.x - 8, p.y - 8, null);
        }
        //repaint();
    }

    public void paintFlipLine() {
        if (flipLine[0] == null) {
            return;
        }
        if (flipStt == true) {
            algorithmDDA(getRealLocation(flipLine[0]), getRealLocation(flipLine[1]), m[0], Color.BLACK);
        } else {
            algorithmDDA(getRealLocation(flipLine[0]), getRealLocation(flipLine[1]), m[1], Color.BLACK);
        }
    }

    //Algorithm
    public void algorithmDDA(Point po1, Point po2, int mask[], Color c) {
        int maskL = 0;
        int first = mask[mask.length - 1];
        Point p1 = po1;
        Point p2 = po2;
        for (int i = 0; i < mask.length - 1; i++) {
            maskL += mask[i];
        }

        if (p1.x == p2.x && p1.y == p2.y) {
            img.setRGB(p1.x, p1.y, c.getRGB());
            return;
        }
        int Dx, Dy, steps;
        float x, y, x_inc, y_inc;
        Dx = p2.x - p1.x;
        Dy = p2.y - p1.y;
        if (Math.abs(Dy) > Math.abs(Dx)) {
            steps = Math.abs(Dy);
        } else {
            steps = Math.abs(Dx);
        }

        x_inc = Dx;
        x_inc /= steps;
        y_inc = Dy;
        y_inc /= steps;
        x = p1.x;
        y = p1.y;

        for (int i = 0; i <= steps; i++) {
            int xR = Math.round(x);
            int yR = Math.round(y);

            if (inIMG(new Point(xR, yR))) {    //Nam trong image
                if (maskL == 0) {
                    img.setRGB((int) xR, (int) yR, c.getRGB());
                    x += x_inc;
                    y += y_inc;
                    continue;
                }
                if (i % maskL < mask[0]) {
                    img.setRGB((int) xR, (int) yR, first == 0 ? c.getRGB() : new Color(255, 255, 255).getRGB());
                } else {
                    img.setRGB((int) xR, (int) yR, first == 0 ? new Color(255, 255, 255).getRGB() : c.getRGB());
                }
            }
            x += x_inc;
            y += y_inc;
        }
    }

    public void algorithmCircle(Point p1, Point p2, int mask[], Color c) {
        int maskL=0;
        int first = mask[mask.length - 1];
        for (int i = 0; i < mask.length - 1; i++) {
            maskL += mask[i];
        }
        Point pc = new Point((p1.x + (p2.x+zoom)) / 2, (p1.y + (p2.y+zoom)) / 2);
        int a = Math.abs(pc.x - p1.x);
        int b = Math.abs(pc.y - p1.y);
        int x, y;
        float z1, z2, p;
        x = 0;
        y = b;
        z1 = (float) (b * b) / (a * a);
        z2 = 1 / z1;
        p = 2 * z1 - 2 * b + 1;
        int step=0;
        while (z1 * ((float) x / y) <= 1) {
            Point pd = new Point(x, y);
            if (inIMG(pd)) {
            Color c2;
             if (step % maskL < mask[0]) {
                    c2 = first == 0 ? c:Color.WHITE;
             } else {
                   c2 = first == 0 ? Color.WHITE: c;
             }
            
            setPixel(pc.x + pd.x, pc.y + pd.y, c2.getRGB());
            setPixel(pc.x - pd.x, pc.y + pd.y, c2.getRGB());
            setPixel(pc.x + pd.x, pc.y - pd.y, c2.getRGB());
            setPixel(pc.x - pd.x, pc.y - pd.y, c2.getRGB());
            }
            if (p < 0) {
                p = p + 2 * z1 * (2 * x + 3);
            } else {
                p = p + 2 * z1 * (2 * x + 3) + 4 * (1 - y);
                y--;
            }
            x++;
            step++;
        }
        x = a;
        y = 0;
        p = 2 * z2 - 2 * a + 1;
        step=0;
        while (z2 * ((float) y / x) <= 1) {
            Point pd = new Point(x, y);
           if (inIMG(pd)) {
            Color c2;
             if (step % maskL < mask[0]) {
                    c2 = first == 0 ? c:Color.WHITE;
             } else {
                   c2 = first == 0 ? Color.WHITE: c;
             }
            
            setPixel(pc.x + pd.x, pc.y + pd.y, c2.getRGB());
            setPixel(pc.x - pd.x, pc.y + pd.y, c2.getRGB());
            setPixel(pc.x + pd.x, pc.y - pd.y, c2.getRGB());
            setPixel(pc.x - pd.x, pc.y - pd.y, c2.getRGB());
            }
            if (p < 0) {
                p = p + 2 * z2 * (2 * y + 3);
            } else {
                p = p + 2 * z2 * (2 * y + 3) + 4 * (1 - x);
                x--;
            }
            y++;
            step++;
        }
    }
    
    public void setPixel(int x, int y, int RGB) {
        if ( inIMG(new Point(x,y)) )
            img.setRGB(x, y, RGB);
    }

    //Trans Shape Wrap
    public void transShapeFlipLine(Point dp1, Point dp2, int o) {
        points2D[0] = transFlipLine(points2D[0], dp1, dp2);
        points2D[4] = transFlipLine(points2D[4], dp1, dp2);
        alpha = -alpha;
        setPointWrap2D(points2D[4], points2D[0]);
        curShape2D.setDimensionFlip(o);
    }

    public void transShapeFlipLine() {
        Point dp1 = flipLine[0];
        Point dp2 = flipLine[1];
        int oldW = Math.abs(points2D[0].x - points2D[4].x);
        int oldH = Math.abs(points2D[0].y - points2D[4].y);
        int dH, newW;
        int dW, newH;
        //Lat 5 diem wrap qua dt dp1,dp2
        points2D[0] = transFlipLine(points2D[0], dp1, dp2);
        points2D[1] = transFlipLine(points2D[1], dp1, dp2);
        points2D[4] = transFlipLine(points2D[4], dp1, dp2);
        points2D[6] = transFlipLine(points2D[6], dp1, dp2);
        points2D[2] = transFlipLine(points2D[2], dp1, dp2);
        centerPoint[0] = new Point((points2D[0].x + points2D[4].x) / 2, (points2D[0].y + points2D[4].y) / 2);
        //Tinh goc gia center,p1 voi Oy
        double a = goc2Vector(points2D[1], centerPoint[0], new Point(0, 0), new Point(0, 100));
        //Neu p1 nam ben trai thi xoay + 1 goc alpha, nam ben phai thi xoay -a
        if (points2D[1].x > centerPoint[0].x) {
            a = -a;
        }
        points2D[0] = transRotate(points2D[0], centerPoint[0], a);
        points2D[4] = transRotate(points2D[4], centerPoint[0], a);
        points2D[2] = transRotate(points2D[2], centerPoint[0], a);
        points2D[6] = transRotate(points2D[6], centerPoint[0], a);
        //Cap nhat alpha cho hinh
        alpha = -alpha - a;
        //Tinh chenh lech giua 2 hinh sau khi quay
        newW = Math.abs(points2D[0].x - points2D[4].x);
        newH = Math.abs(points2D[0].y - points2D[4].y);
        dW = oldW - newW;
        dH = oldH - newH;
        //Tinh huong cua hinh
        int dim1 = getSideOfPoint(points2D[curShape2D.getDim().width], centerPoint[0]);
        int dim2 = getSideOfPoint(points2D[curShape2D.getDim().height], centerPoint[0]);
        curShape2D.clearDim();
        curShape2D.setDimension(dim1, dim2);
        //Chuan hoa cac diem
        setPointWrap2D(points2D[0], points2D[4]);
        //Chuan hoa do dai
        points2D[4].setLocation(points2D[4].x + dW, points2D[4].y + dH);
        setPointWrap2D(points2D[0], points2D[4]);
    }

    public void transShapeFlipPoint(Point p) {
        points2D[0] = transFlipPoint(points2D[0], p);
        points2D[4] = transFlipPoint(points2D[4], p);
        //alpha=-alpha;
        setPointWrap2D(points2D[0], points2D[4]);
        curShape2D.setDimensionFlip(Shape2D.FLIP_OX);
        curShape2D.setDimensionFlip(Shape2D.FLIP_OY);
    }

    public void transShapeRotateAtCenterPoint(double a) {
        setAlphaAdd(a);
    }

    public void transShapeRotateAtRotatePoint(Point p, boolean inRotate) {
        Point pTam = rotatePoint;
        if (inRotate == false) {
            rotatePointD = (int) Point.distance(rotatePoint.x, rotatePoint.y, p.x, p.y);
            rotatePointAlpha = alpha;
            rotatePointT = p;
        }
        double beta = goc2Vector(p, pTam, new Point(pTam.x, pTam.y - 100));     //point[0], pTam, point[1]
        double delta = goc2Vector(rotatePointT, pTam, new Point(pTam.x, pTam.y - 100));
        if (p.x < pTam.x) {
            beta = -beta;
        }
        if (rotatePointT.x < pTam.x) {
            delta = -delta;
        }

        alpha = (beta - delta) + rotatePointAlpha;
        Point pGoc = new Point(pTam.x, pTam.y - rotatePointD);
        points2D[8] = pGoc;
        points2D[9] = centerPoint[0] = transRotate(pGoc, pTam, beta);
        int dx = getSizeWrap2D().width / 2;
        int dy = getSizeWrap2D().height / 2;
        points2D[0] = new Point(centerPoint[0].x - dx, centerPoint[0].y - dy);
        points2D[4] = new Point(centerPoint[0].x + dx, centerPoint[0].y + dy);
        setPointWrap2D(points2D[0], points2D[4]);
    }
    

    //Trans
    public int inResizeAnchorAtCorner(Point m) {
        for (int i = 0; i < 8; i++) {
            if (anchor[i] == null) {
                continue;
            }
            if (m.x >= anchor[i].x - 5 && m.x <= anchor[i].x + 5
                    && m.y >= anchor[i].y - 5 && m.y <= anchor[i].y + 5) {
                return i;
            }
        }
        return -1;
    }

    public int inShapeAnchor(Point m) {
        for (int i = 0; i < 4; i++) {
            if (shapeAnchorPoints[i] == null) {
                continue;
            }
            Point p = getFinalLocation(shapeAnchorPoints[i].getLocation());
            if (!inIMG(p)) {
                continue;
            }
            if (m.x >= p.x - 5 && m.x <= p.x + 5
                    && m.y >= p.y - 5 && m.y <= p.y + 5) {
                return i;
            }
        }
        return -1;
    }

    public int inRotateAnchorAtCorner(Point m) {
        for (int i = 0; i < 8; i++) {
            if (anchor[i] == null && i % 2 != 0) {
                continue;
            }
            if (m.x >= anchor[i].x - 15 && m.x <= anchor[i].x + 15
                    && m.y >= anchor[i].y - 15 && m.y <= anchor[i].y + 15 && inResizeAnchorAtCorner(m) == -1) {
                return i;
            }
        }
        return -1;
    }

    public boolean inRotatePoint(Point m) {
        Point p;
                Point r1 = getRealLocation(points2D[0]);
        Point r4 = getRealLocation(points2D[4]);
        if (rotatePointIsCenter)
            p = new Point( (r1.x+r4.x+1)/2, (r1.y+r4.y+1)/2);
        else
            p = getRealLocation(rotatePoint);
        p.x+=zoom/2;
        p.y+=zoom/2;
        return m.x > p.x - 8 && m.x < p.x + 8
                && m.y > p.y - 8 && m.y < p.y + 8;
    }

    public double goc2Vector(Point A, Point B, Point C) {   //=> Goc ABC
        Point vAB = new Point(B.x - A.x, B.y - A.y);
        Point vBC = new Point(B.x - C.x, B.y - C.y);
        double ABxBC = vAB.x * vBC.x + vAB.y * vBC.y;
        double dAB = Math.sqrt(vAB.x * vAB.x + vAB.y * vAB.y);
        double dBC = Math.sqrt(vBC.x * vBC.x + vBC.y * vBC.y);
        double cosAlpha = ABxBC / (dAB * dBC);
        return Math.acos(cosAlpha);
    }

    public double goc2Vector(Point A, Point B, Point C, Point D) {   //=> Goc AB - CD
        Point vAB = new Point(B.x - A.x, B.y - A.y);
        Point vDC = new Point(D.x - C.x, D.y - C.y);
        double ABxBC = vAB.x * vDC.x + vAB.y * vDC.y;
        double dAB = Math.sqrt(vAB.x * vAB.x + vAB.y * vAB.y);
        double dDC = Math.sqrt(vDC.x * vDC.x + vDC.y * vDC.y);
        double cosAlpha = ABxBC / (dAB * dDC);
        return Math.acos(cosAlpha);
    }

    public Point nhanMaTranBienDoi(Point p, double[][] matrix) {
        int[] result = new int[3];
        int[] point = {p.x, p.y, 1};
        for (int i = 0; i < 3; i++) {
            result[i] = (int) (point[0] * matrix[0][i] + point[1] * matrix[1][i] + point[2] * matrix[2][i]);
        }
        return new Point(result[0], result[1]);
    }

    public Point transLocation(Point p, int dx, int dy) {           //Tinh tien p 1 khoang dx dy
        double[][] Tm = {{1, 0, 0}, {0, 1, 0}, {dx, dy, 1}};//Ma tran tinh tien
        return nhanMaTranBienDoi(p, Tm);
    }

    public Point transRotate(Point p1, Point p2, double alpha2) {    //Quay p1 quanh p2 mot goc alpha(rad)
        int alpha1 = (int) Math.toDegrees(alpha2);
        double alpha;

        alpha = (alpha1 * Math.PI) / 180;
        // int alphaInt = (int) Math.round(Math.toDegrees(alpha));
        // if (alphaInt==0) return p1;
        // if (alphaInt==180 || alphaInt==180) return transFlipPoint(p1,p2);
        float c = (float) (Math.cos(alpha));
        float s = (float) (Math.sin(alpha));
        // System.out.println(alpha1);
        if (alpha1 % 90 == 0) {
            c = Math.round(c);
            s = Math.round(s);
        }
        double[][] Rm = {{c, s, 0}, {-s, c, 0}, {0, 0, 1}}; //Ma tran quay
        //  System.out.println("old: "+p1);
        Point afLocation = transLocation(p1, -p2.x, -p2.y);         //Chuyen ve goc toa do
        Point afRotate = nhanMaTranBienDoi(afLocation, Rm);           //Quay quanh goc toa do
        // System.out.println("New: "+afRotate);
        Point afReLocation = transLocation(afRotate, p2.x, p2.y);   //Chuyen ve vi tri cu
        //System.out.println("New: "+afReLocation);
        return afReLocation;
    }
    
    public void trans3DShapeFlipO() {
        Point3D p0 = curShape3D.getPoint3DAt(0);
        Point3D p3 = curShape3D.getPoint3DAt(3);
        Point3D p5 = curShape3D.getPoint3DAt(5);
        p0 = TransformAlgorithm.trans3DFlipO(p0);
        p3 = TransformAlgorithm.trans3DFlipO(p3);
        p5 = TransformAlgorithm.trans3DFlipO(p5);
        TransformAlgorithm.trans3DFlipO(centerPoint3D);
        System.out.println(centerPoint3D);
        System.out.println(TransformAlgorithm.trans3DFlipO(centerPoint3D));
        setLocationWrapCenter3D(TransformAlgorithm.trans3DFlipO(centerPoint3D));
        //setPointWrap3D(p0, p3, p5);
    }
    
    public void trans3DShapeFlipAxis(int axis) {
        Point3D p0 = curShape3D.getPoint3DAt(0);
        Point3D p3 = curShape3D.getPoint3DAt(3);
        Point3D p5 = curShape3D.getPoint3DAt(5);
        p0 = TransformAlgorithm.trans3DFlipAxis(p0,axis);
        p3 = TransformAlgorithm.trans3DFlipAxis(p3,axis);
        p5 = TransformAlgorithm.trans3DFlipAxis(p5,axis);
        setPointWrap3D(p0, p3, p5);        
    }

    public Point transFlipLine(Point p, Point dp1, Point dp2) {  	//Doi xung p qua duong thang d (dp1,dp2)
        Point pQ = dp1.y <= dp2.y ? dp1 : dp2;                  	//Tim diem quay
        Point pT = dp1.y <= dp2.y ? dp2 : dp1;                  	//Tim diem tam
        int x = pT.x;                                               //Luu lai pT.x
        pQ = transLocation(pQ, -pT.x, 0);                       	//TT pQ 1 doan dx=-x
        pT = transLocation(pT, -pT.x, 0);                       	//TT pT 1 doan dx=-x
        p = transLocation(p, -x, 0);                            	//TT p
        float dx = Math.abs(pQ.x);
        float dy = pT.y - pQ.y;
        double a = Math.atan(dx / dy);                          	//Tinh alpha (goc giua pQ,pT voi Oy)
        p = transRotate(p, pT, pQ.x > 0 ? -a : a);              	//Quay p 1 goc alpha
        p = new Point(-p.x, p.y);                               	//Doi xung p qua Oy
        p = transRotate(p, pT, pQ.x > 0 ? a : -a);              	//Quay p 1 goc -alpha
        p = transLocation(p, x, 0);                             	//TT p 1 doan x
        return p;
    }

    public Point transFlipPoint(Point p1, Point p2) {               //Doi xung p1, qua p2
        double[][] Dm = {{-1, 0, 0}, {0, -1, 0}, {0, 0, 1}};//Ma tran doi xung 0,0
        Point afLocation = transLocation(p1, -p2.x, -p2.y);         //Chuyen ve goc toa do
        Point afFlipPoint = nhanMaTranBienDoi(afLocation, Dm);        //Doi xung
        Point afReLocation = transLocation(afFlipPoint, p2.x, p2.y);          //Chuyen ve vi tri cu
        return afReLocation;
    }

    public boolean contain(Point p) {

        int i;
        int j;
        boolean result = false;
        for (i = 0, j = 7 - 1; i < 7; j = i++) {
            if ((pointAlpha[i].y > p.y) != (pointAlpha[j].y > p.y)
                    && (p.x < (pointAlpha[j].x - pointAlpha[i].x) * (p.y - pointAlpha[i].y) / (pointAlpha[j].y - pointAlpha[i].y) + pointAlpha[i].x)) {
                result = !result;
            }
        }
        return result;
    }

    public boolean isShow() {
        return show;
    }

    public boolean inIMG(Point p) {
        if (p.x < 0 || p.x >= img.getWidth() || p.y < 0 || p.y >= img.getHeight()) {
            // System.out.println(p);
            return false;
        }
        return true;
    }

    public boolean inRotateAtRotatePoint(Point p) {
        if (rotatePointIsCenter) {
            return false;
        }
        if (p.x > centerPoint[1].x - 8 && p.x < centerPoint[1].x + 8
                && p.y > centerPoint[1].y - 8 && p.y < centerPoint[1].y + 8) {
            return true;
        }
        return false;
    }

    public Point getLocationByUser(Point p) {
        if (main.getGridMode() == 1) {    //Computer
            return main.convertPointToUserMode(p);
        } else {                        //User
            return p;
        }
    }

    public int flipAnchor(Point p, int wrapAnchorPos) {
        if (wrapAnchorPos == 0 || wrapAnchorPos == 1 || wrapAnchorPos == 7) {
            if (p.y > getPointAt(6).y) {
                setPointWrap2D(getPointAtOffset(6, 0, 1), getPointAtOffset(4, 0, 1));
                getCurShape().setDimensionFlip(Shape2D.FLIP_OX);
                return wrapAnchorPos == 0 ? 6 : getIndexOppersite(wrapAnchorPos);
            }
            if (p.x > getPointAt(2).x) {
                setPointWrap2D(getPointAtOffset(2, 1, 0), getPointAtOffset(4, 1, 0));
                getCurShape().setDimensionFlip(Shape2D.FLIP_OY);
                return wrapAnchorPos == 0 ? 2 : getIndexOppersite(wrapAnchorPos);
            }
        }
        if (wrapAnchorPos == 2) {
            if (p.y > getPointAt(4).y) {
                setPointWrap2D(getPointAtOffset(6, 0, 1), getPointAtOffset(4, 0, 1));
                getCurShape().setDimensionFlip(Shape2D.FLIP_OX);
                return 4;
            }
            if (p.x < getPointAt(0).x) {
                setPointWrap2D(getPointAtOffset(0, -1, 0), getPointAtOffset(6, -1, 0));
                getCurShape().setDimensionFlip(Shape2D.FLIP_OY);
                return 0;
            }
        }
        if (wrapAnchorPos == 4 || wrapAnchorPos == 5 || wrapAnchorPos == 3) {
            if (p.y < getPointAt(2).y) {
                setPointWrap2D(getPointAtOffset(0, 0, -1), getPointAtOffset(2, 0, -1));
                getCurShape().setDimensionFlip(Shape2D.FLIP_OX);
                return wrapAnchorPos == 4 ? 2 : getIndexOppersite(wrapAnchorPos);
            }
            if (p.x < getPointAt(6).x) {
                setPointWrap2D(getPointAtOffset(0, -1, 0), getPointAtOffset(6, -1, 0));
                getCurShape().setDimensionFlip(Shape2D.FLIP_OY);
                return wrapAnchorPos == 4 ? 6 : getIndexOppersite(wrapAnchorPos);
            }
        }
        if (wrapAnchorPos == 6) {
            if (p.y < getPointAt(0).y) {
                setPointWrap2D(getPointAtOffset(0, 0, -1), getPointAtOffset(2, 0, -1));
                getCurShape().setDimensionFlip(Shape2D.FLIP_OX);
                return 0;
            }
            if (p.x > getPointAt(4).x) {
                setPointWrap2D(getPointAtOffset(2, 1, 0), getPointAtOffset(4, 1, 0));
                getCurShape().setDimensionFlip(Shape2D.FLIP_OY);
                return 4;
            }
        }
//        if (wrapAnchorPos == 1) {
//            if (p.y > getPointAt(6).y) {
//                setPointWrap(getPointAtOffset(6, 0, 1), getPointAtOffset(4, 0, 1));
//                getCurShape().setDimensionFlip(Shape2D.FLIP_OX);
//                return 6;
//            }
//            if (p.x > getPointAt(2).x) {
//                setPointWrap(getPointAtOffset(2, 1, 0), getPointAtOffset(4, 1, 0));
//                getCurShape().setDimensionFlip(Shape2D.FLIP_OY);
//                return 2;
//            }
//        }
//        if (wrapAnchorPos == 3) {
//            if (p.y > getPointAt(6).y) {
//                setPointWrap(getPointAtOffset(6, 0, 1), getPointAtOffset(4, 0, 1));
//                getCurShape().setDimensionFlip(Shape2D.FLIP_OX);
//                return 6;
//            }
//            if (p.x > getPointAt(2).x) {
//                setPointWrap(getPointAtOffset(2, 1, 0), getPointAtOffset(4, 1, 0));
//                getCurShape().setDimensionFlip(Shape2D.FLIP_OY);
//                return 2;
//            }
//        }
//        if (wrapAnchorPos == 5) {
//            if (p.y > getPointAt(6).y) {
//                setPointWrap(getPointAtOffset(6, 0, 1), getPointAtOffset(4, 0, 1));
//                getCurShape().setDimensionFlip(Shape2D.FLIP_OX);
//                return 6;
//            }
//            if (p.x > getPointAt(2).x) {
//                setPointWrap(getPointAtOffset(2, 1, 0), getPointAtOffset(4, 1, 0));
//                getCurShape().setDimensionFlip(Shape2D.FLIP_OY);
//                return 2;
//            }
//        }
//        if (wrapAnchorPos == 7) {
//            if (p.y > getPointAt(6).y) {
//                setPointWrap(getPointAtOffset(6, 0, 1), getPointAtOffset(4, 0, 1));
//                getCurShape().setDimensionFlip(Shape2D.FLIP_OX);
//                return 6;
//            }
//            if (p.x > getPointAt(2).x) {
//                setPointWrap(getPointAtOffset(2, 1, 0), getPointAtOffset(4, 1, 0));
//                getCurShape().setDimensionFlip(Shape2D.FLIP_OY);
//                return 2;
//            }
//        }
        return wrapAnchorPos;
    }

    public void notifyProperties() {
        if (curShape2D!=null) {         //2D
        JScrollPane proScrollPane = (JScrollPane) propertiesPanel.getComponent(1);
        JViewport viewPort = (JViewport) proScrollPane.getComponent(0);
        JPanel proPanel = (JPanel) viewPort.getComponent(0);
        Component[] cp = proPanel.getComponents();
        JPanel proLocation = null, proSize = null, proTransform = null, proColor = null, proPoints = null;
                proLocation = main.proLocationPanel;
                proSize = main.proSizePanel;
                proColor = main.proColorPanel;
                proPoints = main.proPointsPanel;
                proTransform = main.proTransformPanel;

        JTextField proLocationX = main.proLocationtxtX;       //Location X
        JTextField proLocationY = main.proLocationtxtY;        //Location Y

        JLabel proSizelbW = main.proSizelbW;
        JLabel proSizelbR = main.proSizelbR;
        JTextField proSizeW = main.proSizetxtW;            //Pro SizeW
        JLabel proSizelbH = main.proSizelbH;
        JTextField proSizeH = main.proSizetxtH;             //Pro SizeH
        JTextField proSizeR = main.proSizetxtR;
        JLabel proSizelbHR = main.proSizelnR1;
        JTextField proSizeR1 = main.proSizetxtR1;
        JLabel proSizelbVR = main.proSizelbR2;
        JTextField proSizeR2 = main.proSizetxtR2;

        JTextField proTransScaleX = main.proTransformtxtScalingX;
        JTextField proTransScaleY = main.proTransformtxtScalingY;
        JTextField proTransAlpha = main.proTransformtxtRotate;
        JTextField proTransRotateX = main.proTransformtxtRotatePointX;
        JTextField proTransRotateY = main.proTransformtxtRotatePointY;

        JLabel proColorOutline = main.lbOutLineColor;
        JLabel proColorSolid = main.lbFillColor;
        
        //Out shape name
        main.proTypelbShapeName.setText(curShape2D.getRealShapeName());
        main.proTypelbShapeName.setIcon(curShape2D.getShapeIcon());


        //Out Location
        proLocationX.setText(String.valueOf(getLocationByUser(centerPoint[0]).x));
        proLocationY.setText(String.valueOf(getLocationByUser(centerPoint[0]).y));
        //Out Transform
        proTransScaleX.setText("1");
        proTransScaleY.setText("1");
        proTransAlpha.setText(String.valueOf((int) (Math.toDegrees(alpha))));
        proTransRotateX.setText(String.valueOf(getLocationByUser(rotatePoint).x));
        proTransRotateY.setText(String.valueOf(getLocationByUser(rotatePoint).y));
        //Out Color
        proColorOutline.setBackground(curShape2D.getOutLineColor());
        if (curShape2D.getShapeName().equals("line")) {
            main.lbFillColor.setVisible(false);
            main.proColorbtnReset.setVisible(false);
            main.proColorlbSolid.setVisible(false);
        } else {
            main.lbFillColor.setVisible(true);
            main.proColorbtnReset.setVisible(true);
            main.proColorlbSolid.setVisible(true);
        }
        if (curShape2D.getSolidColor()!=null) {
            proColorSolid.setText("");
            proColorSolid.setBackground(curShape2D.getSolidColor());
        } else {
            proColorSolid.setBackground(Color.WHITE);
            proColorSolid.setText("None");
        }

        if (curShape2D.getType() == Shape2D.TYPE_POLYGON) {
            proSizeW.setText(String.valueOf(getSizeWrap2D().width+1));
            proSizeH.setText(String.valueOf(getSizeWrap2D().height+1));
            proSizeW.setVisible(true);
            proSizeH.setVisible(true);
            proSizelbW.setVisible(true);
            proSizelbH.setVisible(true);
            proSizeR.setVisible(false);
            proSizeR1.setVisible(false);
            proSizeR2.setVisible(false);
            proSizelbR.setVisible(false);
            proSizelbHR.setVisible(false);
            proSizelbVR.setVisible(false);
        } else if (curShape2D.getType() == Shape2D.TYPE_ELIP) {
            proSizeR1.setText(String.valueOf(this.getRH()));
            proSizeR2.setText(String.valueOf(this.getRV()));
            proSizeW.setVisible(false);
            proSizeH.setVisible(false);
            proSizelbW.setVisible(false);
            proSizelbH.setVisible(false);
            proSizeR.setVisible(false);
            proSizeR1.setVisible(true);
            proSizeR2.setVisible(true);
            proSizelbR.setVisible(false);
            proSizelbHR.setVisible(true);
            proSizelbVR.setVisible(true);
        }

        //Out point
        main.proPointsP1X.setText(String.valueOf(getLocationByUser(pointAlpha[0]).x));
        main.proPointsP1Y.setText(String.valueOf(getLocationByUser(pointAlpha[0]).y));
        
         main.proPointstxtP2X.setText(String.valueOf(getLocationByUser(pointAlpha[2]).x));
        main.proPointstxtP2Y.setText(String.valueOf(getLocationByUser(pointAlpha[2]).y));
        
         main.proPointstxtP3X.setText(String.valueOf(getLocationByUser(pointAlpha[4]).x));
        main.proPointstxtP3Y.setText(String.valueOf(getLocationByUser(pointAlpha[4]).y));
        
         main.proPointstxtP4X.setText(String.valueOf(getLocationByUser(pointAlpha[6]).x));
        main.proPointstxtP4Y.setText(String.valueOf(getLocationByUser(pointAlpha[6]).y));
        }
        if (curShape3D!=null) {         //3D
            //set type
                main.proTypelbShapeName.setText(curShape3D.getRealShapeName());
                main.proTypelbShapeName.setIcon(curShape3D.getShapeIcon());
            //set size
            main.proSize3DTxtL.setText(String.valueOf(getSizeWrap3D().x));
            main.proSize3DTxtW.setText(String.valueOf(getSizeWrap3D().y));
            main.proSize3DTxtH.setText(String.valueOf(getSizeWrap3D().z));
            //set location
            main.proLocationTxtX.setText(String.valueOf(centerPoint3D.x));
            main.proLocationTxtY.setText(String.valueOf(centerPoint3D.y));
            main.proLocationTxtZ.setText(String.valueOf(centerPoint3D.z));
            
            main.sliderLocationX.setValue(centerPoint3D.x);
            main.sliderLocationY.setValue(centerPoint3D.y);
            main.sliderLocationZ.setValue(centerPoint3D.z);
            
            //set color
            main.lbOutLineColor.setBackground(curShape3D.getOutLineColor());
            main.lbFillColor.setVisible(false);
            main.proColorbtnReset.setVisible(false);
            main.proColorlbSolid.setVisible(false);
            //set rotate
            main.proRotate3DTxtX.setText(String.valueOf(curShape3D.getAlphaDEG(Shape3D.X)));
            main.proRotate3DTxtY.setText(String.valueOf(curShape3D.getAlphaDEG(Shape3D.Y)));
            main.proRotate3DTxtZ.setText(String.valueOf(curShape3D.getAlphaDEG(Shape3D.Z)));
            //set points
            main.proPoints3DP1X.setText(String.valueOf(points3D[0].x));
            main.proPoints3DP1Y.setText(String.valueOf(points3D[0].y));
            main.proPoints3DP1Z.setText(String.valueOf(points3D[0].z));
            
            main.proPoints3DP2X.setText(String.valueOf(points3D[1].x));
            main.proPoints3DP2Y.setText(String.valueOf(points3D[1].y));
            main.proPoints3DP2Z.setText(String.valueOf(points3D[1].z));
            
            main.proPoints3DP3X.setText(String.valueOf(points3D[2].x));
            main.proPoints3DP3Y.setText(String.valueOf(points3D[2].y));
            main.proPoints3DP3Z.setText(String.valueOf(points3D[2].z));
            
            main.proPoints3DP4X.setText(String.valueOf(points3D[3].x));
            main.proPoints3DP4Y.setText(String.valueOf(points3D[3].y));
            main.proPoints3DP4Z.setText(String.valueOf(points3D[3].z));
            
            main.proPoints3DP5X.setText(String.valueOf(points3D[4].x));
            main.proPoints3DP5Y.setText(String.valueOf(points3D[4].y));
            main.proPoints3DP5Z.setText(String.valueOf(points3D[4].z));
            
            main.proPoints3DP6X.setText(String.valueOf(points3D[5].x));
            main.proPoints3DP6Y.setText(String.valueOf(points3D[5].y));
            main.proPoints3DP6Z.setText(String.valueOf(points3D[5].z));
            
            main.proPoints3DP7X.setText(String.valueOf(points3D[6].x));
            main.proPoints3DP7Y.setText(String.valueOf(points3D[6].y));
            main.proPoints3DP7Z.setText(String.valueOf(points3D[6].z));
            
            main.proPoints3DP8X.setText(String.valueOf(points3D[7].x));
            main.proPoints3DP8Y.setText(String.valueOf(points3D[7].y));
            main.proPoints3DP8Z.setText(String.valueOf(points3D[7].z));
        }
    }

    void trans3DShapeFlipPlane(int X) {
          Point3D p0 = curShape3D.getPoint3DAt(0);
        Point3D p3 = curShape3D.getPoint3DAt(3);
        Point3D p5 = curShape3D.getPoint3DAt(5);
        p0 = TransformAlgorithm.trans3DFlipPlane(p0,X);
        p3 = TransformAlgorithm.trans3DFlipPlane(p3,X);
        p5 = TransformAlgorithm.trans3DFlipPlane(p5,X);
        setPointWrap3D(p0, p3, p5);    
    }

}

class ShapeAnchor {

    private Point pos;
    private int l1;

    public ShapeAnchor(Point pos, int l1) {
        this.l1 = l1;
        this.pos = pos;
    }

    public void setLocation(Point p) {
        this.pos = p;
    }

    public void setLocationAuto(Point p) {
        //Neu Anchor nam tren 0-2 , 6-4 => Giu nguyen Y
        if (l1 == 0 || l1 == 2) {
            pos.x = p.x;
        }
        //Neu Anchor nam tren 0-6, 2-4 => Giu nguyen X
        if (l1 == 1 || l1 == 3) {
            pos.y = p.y;
        }
    }

    public Point getLocation() {
        return pos;
    }

    public int getSide() {
        return l1;
    }
}
