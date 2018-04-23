package jpaint.shape2D;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import jpaint.LayerPanel;
import jpaint.algorithm.DrawAlgorithm;
import jpaint.algorithm.TransformAlgorithm;

public class Shape2D implements Cloneable, Serializable {

    public static int TYPE_POLYGON = 0;
    public static int TYPE_ELIP = 1;
    public static int TYPE_PENCIL = 2;
    public static int TYPE_PIXEL = 3;
    public static int FLIP_OX = 0;
    public static int FLIP_OY = 1;
    public static int[] STROKE_NORMAL = {0, 0};
    public static int[] STROKE_DASH = {5, 5};
    protected LayerPanel lp;                                     //Layer ve shape 
    protected int size = 1;                                     //Kich thuoc pixel
    protected ArrayList<Pixel2D> pixelList = new ArrayList();     //Ds tat ca pixel cua Shape
    protected ArrayList<Point> pointList = new ArrayList();     //Ds cac Dinh cua Shape
    protected ArrayList<Pixel2D> fillList = new ArrayList();
    protected Map<String, Pixel2D> pixelMap = new HashMap();
    protected Point[] wrap = new Point[8];                      //Khung dieu chinh
    protected Color outColor;                                          //Mau Outline
    protected Color solidColor = null;
    protected double alpha = 0;                                 //Goc quay alpha
    protected String alg;                                       //Thuat toan su dung
    protected Dimension dim;                                    //Huong goc
    protected int[] stroke = {0, 0};
    protected int curType;
    protected boolean visible = true;
    protected int id;
    protected float[] anchorScale = {-1, -1, -1, -1};
    protected int edge = 0;
    private boolean isManual;
    protected Point pTam;
    final private boolean fillFlag[] = {false, false, false, false};
    private ArrayList<Pixel2D>[] fillTemp = new ArrayList[4];
    private Timer tm;

    public Shape2D() {
        id = this.hashCode();
    }

    public Shape2D(Shape2D tShape) {
        this.pixelList = (ArrayList<Pixel2D>) tShape.pixelList.clone();
        this.pointList = (ArrayList<Point>) tShape.pointList.clone();
        this.fillList = (ArrayList<Pixel2D>) tShape.fillList.clone();
        this.wrap = Arrays.copyOf(tShape.wrap, tShape.wrap.length);
        this.id = tShape.id;
        this.lp = tShape.lp;
    }

    //Init and Create
    public void createPoints() {
        pointList.clear();
        fillList.clear();
    }

    public void create(LayerPanel g) {
        g.addAndPaintShape(this);
        lp = g;
    }

    //Getter
    public boolean isVisible() {
        return visible;
    }

    public int getR1() {
        Point pc = new Point((wrap[0].x + wrap[4].x) / 2, (wrap[0].y + wrap[4].y) / 2);
        return Math.abs(pc.x - wrap[0].x);
    }

    public int getR2() {
        Point pc = new Point((wrap[0].x + wrap[4].x) / 2, (wrap[0].y + wrap[4].y) / 2);
        return Math.abs(pc.y - wrap[0].y);

    }

    public int getType() {
        return -1;
    }

    public float[] getAnchorScale() {
        return anchorScale;
    }

    public int[] getAnchorLocation() {
        return new int[]{0, 0, 0, 0};
    }

    ;
    public Dimension getDim() {
        return dim;
    }

    public void clearDim() {
        dim = null;
    }

    public ImageIcon getShapeIcon() {
        return new ImageIcon(this.getClass().getResource("/rsc/shape/" + getRealShapeName() + ".png"));
    }

    public String getRealShapeName() {
        return "";
    }

    public int getID() {
        return id;
    }

    public double getAlpha() {
        return alpha;
    }

    public String getAlog() {
        return alg;
    }

    public String getShapeName() {
        return "null";
    }

    public int getPixelSize() {
        return size;
    }

    public Point[] getWrap() {
        return wrap;
    }

    public Point getWrapAt(int i) {
        return wrap[i];
    }

    public Color getSolidColor() {
        return solidColor;
    }

    public Color getOutLineColor() {
        return outColor;
    }

    public Pixel2D getPixelAt(Point p) {
        String key = p.x + "-" + p.y;
        Pixel2D p2D = pixelMap.get(key);
        //System.out.println(p2D);
        if (p2D != null) {
            return p2D;
        }
        return new Pixel2D(p.x, p.y, new Color(255, 255, 255, 0), size);
    }

    public ArrayList<Pixel2D> getPixelCoreList() {
        return pixelList;
    }

    public ArrayList<Pixel2D> getAllPixel() {
        ArrayList<Pixel2D> temp = new ArrayList();
        temp.addAll(pixelList);
        temp.addAll(fillList);
        return temp;
    }

    //Setter
    public void setStroke(String s) {
        if (s.equals("─────")) {
            stroke = STROKE_NORMAL;
        }
        if (s.equals("- - - - -")) {
            stroke = STROKE_DASH;
        }
    }

    public void setEdge(int e) {
        this.edge = e;
    }

    public void setShapeAnchor(float[] a) {
        anchorScale = a;
    }

    public void setSolidColor(Color c) {
        solidColor = c;
    }

    public void setType(int type) {
        this.curType = type;
    }

    public void setDimension(int i, int j) {
        if (dim == null) {
            dim = new Dimension(i, j);
        }
    }

    public void setDimensionFlip(int flip) {
        int p1 = dim.width;
        int p2 = dim.height;
        if (flip == FLIP_OX) {
            p1 = p1 == 0 ? 6 : p1 == 2 ? 4 : p1 == 4 ? 2 : 0;
            p2 = p2 == 0 ? 6 : p2 == 2 ? 4 : p2 == 4 ? 2 : 0;
        }
        if (flip == FLIP_OY) {
            p1 = p1 == 0 ? 2 : p1 == 2 ? 0 : p1 == 4 ? 6 : 4;
            p2 = p2 == 0 ? 2 : p2 == 2 ? 0 : p2 == 4 ? 6 : 4;
        }
        dim = new Dimension(p1, p2);
    }

    public void setAlgorithm(String alog) {
        this.alg = alog;
    }

    public void setVisible(boolean v) {
        visible = v;
    }

    public void setSize(int i) {
        this.size = i;
    }

    public void setPoint(Point p, int index) {
        pointList.set(index, p);
    }

    public void setOutlineColor(Color c) {
        this.outColor = c;
        for (Pixel2D p : pixelList) {
            p.setColor(c);
        }
    }

    public void setWrap(Point p1, Point p2) {
        isManual = true;
        wrap[0] = p1;
        wrap[1] = wrap[3] = wrap[5] = wrap[7] = null;
        wrap[4] = p2;
        wrap[2] = new Point(p2.x, p1.y);
        wrap[6] = new Point(p1.x, p2.y);
        pTam = new Point((wrap[0].x + wrap[4].x) / 2, (wrap[0].y + wrap[4].y) / 2);
    }

    public void setLayerPanel(LayerPanel lp) {
        this.lp = lp;
    }

    public void setAnchorScale(float[] a) {
        if (getShapeName().equals("trapezoid")) {
            for (int i = 0; i < 4; i++) {
                if (a[0] == -1) {
                    continue;
                }
                if (a[i] < 0) {
                    a[i] = 0;
                }
                if (a[i] > 1) {
                    a[i] = 1;
                }
            }
        }
        this.anchorScale = a;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public void setWraps(Point[] point) {
        wrap = point;
        pTam = new Point((wrap[0].x + wrap[4].x) / 2, (wrap[0].y + wrap[4].y) / 2);
    }

    public void setStroke(int[] a) {
        stroke = a;
    }

    public boolean inImage(Point p, Point pTam, int side) {
        //   0   |    1
        // ------+------
        //   3   |    2
        //System.out.println(p);
        if (side == 0) {
            if (p.x <= pTam.x && p.y < pTam.y) {
                return true;
            }
        }
        if (side == 1) {
            if (p.x > pTam.x && p.y <= pTam.y) {
                return true;
            }
        }
        if (side == 2) {
            if (p.x >= pTam.x && p.y > pTam.y) {
                return true;
            }
        }
        if (side == 3) {
            if (p.x < pTam.x && p.y >= pTam.y) {
                return true;
            }
        }
        return false;
    }

    public void fillThread(Point p, int side, Color c) {
        Thread floodFill1 = new Thread(new Runnable() {
            private Map<String, Pixel2D> pMap = new HashMap(pixelMap);

            public Pixel2D getPixelAt(Point p) {
                String key = p.x + "-" + p.y;
                Pixel2D p2D = pMap.get(key);
                //System.out.println(p2D);
                if (p2D != null) {
                    return p2D;
                }
                return new Pixel2D(p.x, p.y, new Color(255, 255, 255, 0), size);
            }

            @Override
            public void run() {
                fillFlag[side] = false;
                fillTemp[side] = new ArrayList();
                Pixel2D cur = null;
                switch (side) {
                    case 0:
                        cur = getPixelAt(new Point(p.x, p.y - 1));
                        break;
                    case 1:
                        cur = getPixelAt(new Point(p.x + 1, p.y));
                        break;
                    case 2:
                        cur = getPixelAt(new Point(p.x, p.y + 1));
                        break;
                    case 3:
                        cur = getPixelAt(new Point(p.x - 1, p.y));
                        break;
                }
                Pixel2D next;
                Stack<Pixel2D> st = new Stack();
                st.push(cur);
                //System.out.println(pTam);
                while (!st.empty()) {
                    cur = st.pop();

                    if (getPixelAt(cur.getPoint()).getRGB() != outColor.getRGB() && getPixelAt(cur.getPoint()).getRGB() != c.getRGB()) {
                        Pixel2D p2D = new Pixel2D(cur.getX(), cur.getY(), c, 1);
                        //putAndPaintFillPixel(new Pixel2D(cur.getX(), cur.getY(), c, size));
                        pMap.put(String.valueOf(p2D.getX() + "-" + p2D.getY()), p2D);
                        fillTemp[side].add(p2D);
                        lp.setAndPaintPixel(p2D, 0);
                        //lp.repaint();

                        next = getPixelAt(new Point(cur.getX() - 1, cur.getY()));
                        if (next.getRGB() != outColor.getRGB() && next.getRGB() != c.getRGB() && inImage(next.getPoint(), p, side)) {
                            st.push(next);
                        }
                        next = getPixelAt(new Point(cur.getX(), cur.getY() + 1));
                        if (next.getRGB() != outColor.getRGB() && next.getRGB() != c.getRGB() && inImage(next.getPoint(), p, side)) {
                            st.push(next);
                        }
                        next = getPixelAt(new Point(cur.getX() + 1, cur.getY()));
                        if (next.getRGB() != outColor.getRGB() && next.getRGB() != c.getRGB() && inImage(next.getPoint(), p, side)) {
                            st.push(next);
                        }
                        next = getPixelAt(new Point(cur.getX(), cur.getY() - 1));
                        if (next.getRGB() != outColor.getRGB() && next.getRGB() != c.getRGB() && inImage(next.getPoint(), p, side)) {
                            st.push(next);
                        }
                    }

                }
                //System.out.println("True Side "+side);
                fillFlag[side] = true;
                Thread.currentThread().stop();
            }
        });
        floodFill1.start();
    }

    //Algorithm
    public void algorithmPaintFloodFillMultithread(Point p, Color c) {                             //Lay mau tai diem do
        long start = System.currentTimeMillis();
        putAndPaintFillPixel(new Pixel2D(p.x, p.y, c, 1));

        fillThread(p, 0, c);
        fillThread(p, 1, c);
        fillThread(p, 2, c);
        fillThread(p, 3, c);

        Thread fillPaint = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (fillFlag) {
                    while (!fillFlag[0] | !fillFlag[1] | !fillFlag[2] | !fillFlag[3]) {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Shape2D.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    for (int i = 0; i < 4; i++) {
                        fillList.addAll(fillTemp[i]);
                    }
                    // for (Pixel2D e : fillList) {

                    //}
                    lp.repaint();
                    long end = System.currentTimeMillis();
                    System.out.println("To xong: " + (end - start));
                    Thread.currentThread().stop();
                }
            }

        });
        fillPaint.start();
        synchronized (fillPaint) {
            try {
                fillPaint.wait();
                tm.restart();
            } catch (InterruptedException ex) {
                Logger.getLogger(Shape2D.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //Pain && draw
    public void paintShapeTo(LayerPanel lp) {    //Luu shape vao Layer
        //this.lp.clear();        //Xoa temp panel
        // lp.getShapeList().add(this);
        for (Pixel2D e : pixelList) {
            lp.setAndPaintPixel(e, 0);
        }
        for (Pixel2D e : fillList) {
            lp.setAndPaintPixel(e, 0);
        }
        lp.repaint();
    }

    public void paintToLayer() {                //Ve tat ca pixel trog pixelList
        for (Pixel2D e : pixelList) {
            lp.setAndPaintPixel(e, 0);
        }
        for (Pixel2D e : fillList) {
            lp.setAndPaintPixel(e, 0);
        }
        lp.repaint();
    }

    /* putPixelNonPaint
        Them tung Point, Pixel2D vao pixelList (core) va pixelMap
     */
    public void putPixelNonPaint(Point p) {
        putPixelNonPaint(p.x, p.y);
    }

    public void putPixelNonPaint(int x, int y) {
        Pixel2D p = new Pixel2D(x, y, outColor, size);
        pixelList.add(p);
        putPixelMapWithRange(p);
    }

    public void putPixelNonPaint(Pixel2D p2D) {
        pixelList.add(p2D);
        putPixelMapWithRange(p2D);
    }

    public void putPixelNonPaint(float x, float y) {
        putPixelNonPaint(Math.round(x), Math.round(y));
    }

    public void putAndPaintPixelList(ArrayList<Point> pList) {
        if (pList == null) {
            return;
        }
        for (Point p : pList) {
            Pixel2D p2D = new Pixel2D(p.x, p.y, outColor, size);
            pixelList.add(p2D);
            putPixelMapWithRange(p2D);
            lp.setAndPaintPixel(p2D, 0);
        }
        lp.repaint();
    }

    /* putPixelMapWithRange
    
        Them vào pixelMap tat ca cac diem
     */
    public void putPixelMapWithRange(Pixel2D p2D) {
        int p = p2D.getSize() / 2;
        for (int i = p2D.x - p; i < p2D.x - p + p2D.getSize(); i++) {
            for (int j = p2D.y - p; j < p2D.y - p + p2D.getSize(); j++) {
                pixelMap.put(String.valueOf(i + "-" + j), p2D);
            }
        }
    }

    public void putPixelMapWithRangeList(ArrayList<Point> pList) {
        for (Point p : pList) {
            Pixel2D p2D = new Pixel2D(p.x, p.y, outColor, 1);
            putPixelMapWithRange(p2D);
        }
    }

    /* putPixelFillList
    
        Them pixel vao FillList
     */
    public void putPixelFillList(Pixel2D p2D) {
        fillList.add(p2D);
        putPixelMapWithRange(p2D);
    }

    public void putAndPaintFillPixel(Pixel2D p2D) {
        //System.out.println("337 Put and paint "+p2D.getPoint());
        putPixelFillList(p2D);
        lp.setAndPaintPixel(p2D, 0);
        lp.repaint();
    }

    /* importPixel2DList
    
        Them List pixel vao pixelList
     */
    public void importPixel2DList(ArrayList<Pixel2D> pList) {
        if (pList == null) {
            return;
        }

        for (Pixel2D p : pList) {
            pixelList.add(p);
            putPixelMapWithRange(p);
        }
    }

    public void importPixelList(ArrayList<Point> pList) {
        if (pList == null) {
            return;
        }
        for (Point p : pList) {
            putPixelNonPaint(p.x, p.y);
        }
    }

    public void importImage(BufferedImage bimg) {
        for (int c = 0; c < bimg.getWidth(); c++) {
            for (int r = 0; r < bimg.getHeight(); r++) {
                Pixel2D p = new Pixel2D(c, r, new Color(bimg.getRGB(c, r)), 1);
                pixelList.add(p);
                putPixelMapWithRange(p);
            }
        }
    }

    public void draw(int type) {
        if (type != TYPE_PENCIL) {
            if (type != TYPE_PIXEL) {
                pixelList.clear();
            }
            pixelMap.clear();
            fillList.clear();
            lp.clear();
        }

        if (type == TYPE_POLYGON) {
            rotateShape(type);
            for (int i = 0; i < pointList.size() - 1; i++) {
                putAndPaintPixelList(DrawAlgorithm.algorithmDDA(pointList.get(i), pointList.get(i + 1), stroke));
                if (stroke == STROKE_DASH) {
                    putPixelMapWithRangeList(DrawAlgorithm.algorithmDDA(pointList.get(i), pointList.get(i + 1), STROKE_NORMAL));
                }
            }
            putAndPaintPixelList(DrawAlgorithm.algorithmDDA(pointList.get(0), pointList.get(pointList.size() - 1), stroke));
            if (stroke == STROKE_DASH) {
                putPixelMapWithRangeList(DrawAlgorithm.algorithmDDA(pointList.get(0), pointList.get(pointList.size() - 1), STROKE_NORMAL));
            }
        }
        if (type == TYPE_PENCIL) {
            if (pointList.size() == 1) {
                putPixelNonPaint(pointList.get(0));
            } else {
                int last = pointList.size() - 2;
                int newIndex = pointList.size() - 1;
                int fIndex = pixelList.size() - 1;
                //System.out.println(last+ " "+newIndex);
                //for (int i = 0; i < pointList.size() - 1; i++) {
                ArrayList<Point> pL = DrawAlgorithm.algorithmDDA(pointList.get(last), pointList.get(newIndex), new int[]{0, 0});
                importPixelList(pL);
                for (int i = fIndex; i < fIndex + pL.size(); i++) {
                    lp.setAndPaintPixel(pixelList.get(i), 0);
                }
                lp.repaint();
            }
        }
        if (type == TYPE_ELIP) {
            int a = Math.abs(wrap[0].x - wrap[4].x) / 2;
            int b = Math.abs(wrap[0].y - wrap[4].y) / 2;
            if ((a < 10 || b < 10 || Math.toDegrees(alpha) != 0) && a != b) {
                rotateShape(TYPE_POLYGON);
                for (int i = 0; i < pointList.size() - 4; i++) {
                    putAndPaintPixelList(DrawAlgorithm.algorithmDDA(pointList.get(i), pointList.get(i + 4), stroke));
                }
            } else {
                putAndPaintPixelList(pointList);
            }
        }
        if (type == TYPE_PIXEL) {
            paintShapeTo(lp);
        }
    }

    public void reFill(Timer tm) {
        this.tm = tm;
        if (solidColor == null) {
            return;
        }
        tm.stop();
        Point trongTam;
        int x = 0;
        int y = 0;
        int total = pointList.size();
        for (Point p : pointList) {
            x += p.x;
            y += p.y;
        }
        trongTam = new Point(x / total, y / total);

        algorithmPaintFloodFillMultithread(trongTam, solidColor);
    }

    //Other
    public void addPoint(Point p, int index) {
        if (index == -1) {
            pointList.add(p);
        } else {
            pointList.add(index, p);
        }
    }

    public void rotateShape(int type) {
        //System.out.println("=============");
        Point mid = new Point((wrap[0].x + wrap[4].x) / 2, (wrap[0].y + wrap[4].y) / 2);
        if (type == TYPE_POLYGON) {
            for (int i = 0; i < pointList.size(); i++) {
                //  System.out.println("Quay "+i);
                pointList.set(i, TransformAlgorithm.transRotate(pointList.get(i), mid, alpha));
            }
        }
        if (type == TYPE_ELIP) {
            for (Pixel2D p : pixelList) {
                p.setPoint(TransformAlgorithm.transRotate(p.getPoint(), mid, alpha));
            }
        }
    }

    public boolean containCircle(Point p, Point p1, Point p2) {
        boolean result = false;
        Point pt = new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
        float d = (float) Math.sqrt(Math.pow(Math.abs(p.x - pt.x), 2) + Math.pow(Math.abs(p.y - pt.y), 2));
        int r = Math.abs(pt.x - p1.x);
        if (r >= d) {
            result = !result;
        } else {
            return result;
        }
        return result;
    }

    public boolean containEllipse(Point p, Point p1, Point p2) {
        boolean result = false;
        Point pt = new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
        double a = Math.abs(pt.x - p1.x);
        double b = Math.abs(pt.y - p1.y);
        double F;

        Point normalized = new Point(p.x - pt.x, p.y - pt.y);
        // System.out.println(normalized);
        if (a <= 0.0 || b <= 0.0) {
            return false;
        }
// Cong thuc dx^2 / a^2 + dy^2 / b^2 <= 1: o trong + tren
        F = ((double) (normalized.x * normalized.x) / (a * a)) + ((double) (normalized.y * normalized.y) / (b * b));

        if (F <= 1.0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean containRect(Point p, Point p1, Point p2) {
        int i;
        int j;
        boolean result = false;
        System.out.println("=============");
        for (i = 0, j = pointList.size() - 1; i < pointList.size(); j = i++) {
            
            if ((pointList.get(i).y > p.y) != (pointList.get(j).y > p.y)
                    && (p.x < (pointList.get(j).x - pointList.get(i).x) * (p.y - pointList.get(i).y) / (pointList.get(j).y - pointList.get(i).y) + pointList.get(i).x)) {
                result = !result;
            }
        //    System.out.println( (pointList.get(j).x - pointList.get(i).x) * (p.y - pointList.get(i).y) / (pointList.get(j).y - pointList.get(i).y) + pointList.get(i).x);
            System.out.println(i+" "+j+" "+result);
        }
        return result;
    }

    public boolean contain(Point p, int shapeType) {
        if (shapeType == TYPE_ELIP) {
            return containEllipse(p, wrap[0], wrap[4]);
        }
        if (shapeType == TYPE_POLYGON) {
            return containRect(p, wrap[0], wrap[4]);
        }
        return false;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (!(o instanceof Shape2D)) return false;
//        Shape2D t = (Shape2D) o;
//        if (t.pointList.size()!=this.pointList.size()) return false;
//        if (t.alpha!=this.alpha) return false;
//        for (int i=0;i<this.pointList.size();i++) {
//            if (!t.pointList.get(i).equals(this.pointList.get(i))) return false;
//        }
//        return true;
//    }
    public Shape2D clone() {
        Shape2D t = null;
        try {
            t = (Shape2D) super.clone();
            t.pixelList = (ArrayList<Pixel2D>) this.pixelList.clone();
            t.pointList = (ArrayList<Point>) this.pointList.clone();
            t.fillList = (ArrayList<Pixel2D>) this.fillList.clone();
            t.wrap = Arrays.copyOf(this.wrap, this.wrap.length);
            t.id = this.id;
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Shape2D.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (Shape2D) t;
    }
}
