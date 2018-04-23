package jpaint.algorithm;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import jpaint.LayerPanel;
import jpaint.shape2D.Pixel2D;
import jpaint.shape2D.Shape2D;
import jpaint.shape3D.Point3D;

public class DrawAlgorithm {
    public static int FILLMODE_ALL = 0;
    public static int FILLMODE_RANGE = 1;
    private static boolean fillFlag[] = {false,false,false,false};
    private static ArrayList<Pixel2D>[] fillTemp = new ArrayList[4];

    
    public static void algorithmDDA(BufferedImage img, Point p1, Point p2, int stroke[], Color color) {
        int maskL = 0;
        for (int i = 0; i < stroke.length; i++) {
            maskL += stroke[i];
        }

        if (p1.x == p2.x && p1.y == p2.y) {
            img.setRGB(p1.x, p1.y, color.getRGB());
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
            if (maskL == 0) {
                img.setRGB(Math.round(x), Math.round(y), color.getRGB());
            } else if (i % maskL < stroke[0]) {
                img.setRGB(Math.round(x), Math.round(y), color.getRGB());
            }
            x += x_inc;
            y += y_inc;
        }
    }

    public static ArrayList<Point> algorithmDDA(Point p1, Point p2, int stroke[]) {
        ArrayList<Point> retList = new ArrayList();
        int maskL = 0;
        for (int i = 0; i < stroke.length; i++) {
            maskL += stroke[i];
        }

        if (p1.x == p2.x && p1.y == p2.y) {
            retList.add(new Point(p1.x, p1.y));
            return retList;
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
            if (maskL == 0) {
                retList.add(new Point(Math.round(x), Math.round(y)));
            } else if (i % maskL < stroke[0]) {
                retList.add(new Point(Math.round(x), Math.round(y)));
            }
            x += x_inc;
            y += y_inc;
        }
        return retList;
    }


    public static ArrayList<Point> algorithmEllipse(Point po1, Point po2, double alpha, int stroke[]) {
        Point p1 = (Point) po1.clone();
        Point p3 = (Point) po2.clone();
        Point pc = new Point((p1.x + p3.x) / 2, (p1.y + p3.y) / 2);
        int a = Math.abs(pc.x - p1.x);
        int b = Math.abs(pc.y - p1.y);
        if (a == 0 || b == 0) {
            return null;
        }
        if ((a < 10 || b < 10 || Math.toDegrees(alpha) != 0) && a != b) {
            return algorithmBezierEllipse(pc, a, b);
        }
        return algorithmMidPointEllipse(pc, a, b, stroke);
    }

    public static ArrayList<Point> algorithmBezierEllipse(Point pc, int a, int b) {
        ArrayList<Point> retList = new ArrayList();
        Point p1c = new Point(pc.x, pc.y - b);
        Point p2c = new Point(pc.x + a, pc.y - b);
        Point p3c = new Point(p2c.x, pc.y);
        int dx = a;
        int dy = b;

        float t = 20;
        for (float i = 0; i <= t; i += 1) {
            Point pp1 = new Point((int) (p1c.x + dx * i / t), p1c.y);
            Point pp2 = getPointInLine(p2c, p3c, (double) (i / t));
            //System.out.println(deltaX + " "+deltaY);
            Point pn = getPointInLine(pp1, pp2, (double) (i / t));
            pn.x -= pc.x;
            pn.y -= pc.y;
            retList.add(new Point(pc.x + pn.x, pc.y + pn.y));
            //retList.addAll(algorithmDDA(pp1, pp2, new int[]{0,0}));
            retList.add(new Point(pc.x - pn.x, pc.y + pn.y));
            retList.add(new Point(pc.x + pn.x, pc.y - pn.y));
            retList.add(new Point(pc.x - pn.x, pc.y - pn.y));
        }
        return retList;
    }

    public static ArrayList<Point> algorithmMidPointEllipse(Point pc, int a, int b, int stroke[]) {
        ArrayList<Point> retList = new ArrayList();
        int maskL = 0;
        for (int i = 0; i < stroke.length; i++) {
            maskL += stroke[i];
        }
        int x, y;
        float z1, z2, p;
        x = 0;
        y = b;
        z1 = (float) (b * b) / (a * a);
        z2 = 1 / z1;
        p = 2 * z1 - 2 * b + 1;
        int step = 0;
        while (z1 * ((float) x / y) <= 1) {
            Point pd = new Point(x, y);
            if (maskL == 0 || step % maskL < stroke[0]) {
                //.out.println(maskL+ " "+ ((maskL!=0)? step%maskL:0));
                retList.add(new Point(pc.x + pd.x, pc.y + pd.y));
                retList.add(new Point(pc.x - pd.x, pc.y + pd.y));
                retList.add(new Point(pc.x + pd.x, pc.y - pd.y));
                retList.add(new Point(pc.x - pd.x, pc.y - pd.y));
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
        step = 0;
        while (z2 * ((float) y / x) <= 1) {
            Point pd = new Point(x, y);
            if (maskL == 0 || step % maskL < stroke[0]) {

                retList.add(new Point(pc.x + pd.x, pc.y + pd.y));
                retList.add(new Point(pc.x - pd.x, pc.y + pd.y));
                retList.add(new Point(pc.x + pd.x, pc.y - pd.y));
                retList.add(new Point(pc.x - pd.x, pc.y - pd.y));
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
        return retList;
    }
    
        public static boolean inImage(Point p, Point pTam, int side) {
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

    public static void fillThread(Point p, int side,  Color fillColor, LayerPanel lp) {
        Thread floodFill1 = new Thread(new Runnable() {
            private Color oriColor = lp.getPixelAt(p.x, p.y).getColor();

            public Pixel2D getPixelAt(Point po) {
                return lp.getPixelAt(po.x, po.y);
            }

            @Override
            public void run() {
                fillFlag[side]=false;
                fillTemp[side] = new ArrayList();
                Pixel2D cur=null;
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
                    
                    if (getPixelAt(cur.getPoint()).getRGB() == oriColor.getRGB() && getPixelAt(cur.getPoint()).getRGB() != fillColor.getRGB() && inImage(cur.getPoint(),p, side)) {
                        Pixel2D p2D = new Pixel2D(cur.getX(), cur.getY(), fillColor, 1);
                        //putAndPaintFillPixel(new Pixel2D(cur.getX(), cur.getY(), c, size));
                        fillTemp[side].add(p2D);
                    //   System.out.println("Them Mang "+p2D.x+" "+p2D.y);
                        lp.setAndPaintPixel(p2D, 0);
                        //lp.repaint();

                        next = getPixelAt(new Point(cur.getX() - 1, cur.getY()));
//                        System.out.println("==============");
//                        System.out.println(next.getRGB() == oriColor.getRGB());
//                        System.out.println(next.getRGB() != fillColor.getRGB());
//                        System.out.println(inImage(next.getPoint(),p, side));
                        if (next!=null && next.getRGB() == oriColor.getRGB() && next.getRGB() != fillColor.getRGB() && inImage(next.getPoint(),p, side)) {
//                            System.out.println("Push: "+next.x+" "+next.y);
                            st.push(next);
                        }
                        next = getPixelAt(new Point(cur.getX(), cur.getY() + 1));
                        if (next!=null && next.getRGB() == oriColor.getRGB() && next.getRGB() != fillColor.getRGB() && inImage(next.getPoint(),p, side)) {
//                            System.out.println("Push: "+next.x+" "+next.y);
                            st.push(next);
                        }
                        next = getPixelAt(new Point(cur.getX() + 1, cur.getY()));
                        if (next!=null && next.getRGB() == oriColor.getRGB() && next.getRGB() != fillColor.getRGB() && inImage(next.getPoint(),p, side)) {
//                            System.out.println("Push: "+next.x+" "+next.y);
                            st.push(next);
                        }
                        next = getPixelAt(new Point(cur.getX(), cur.getY() - 1));
                        if (next!=null && next.getRGB() == oriColor.getRGB() && next.getRGB() != fillColor.getRGB() && inImage(next.getPoint(),p, side)) {
                            
                            st.push(next);
                        }
                    }

                }
                //System.out.println("True Side "+side);
                fillFlag[side]=true;
                Thread.currentThread().stop();
            }
        });
        
        floodFill1.start();
    }
    
    public static ArrayList<Pixel2D> algorithmFloodFill(Point p, Color c, LayerPanel lp, int mode) {
        long start = System.currentTimeMillis();
        ArrayList<Pixel2D> ret = new ArrayList();
        ret.add(new Pixel2D(p.x,p.y,c,1));
          
        
        fillThread(p,0,c,lp);
        fillThread(p,1,c,lp);
        fillThread(p,2,c,lp);
        fillThread(p,3,c,lp);
        
        Thread fillPaint = new Thread(new Runnable(){
            @Override
            public void run() {
                synchronized(fillFlag) {
                while(!fillFlag[0] || !fillFlag[1] || !fillFlag[2]  || !fillFlag[3]  ){
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Shape2D.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                for (int i=0;i<4;i++) {
                    ret.addAll(fillTemp[i]);
                }
               
               // for (Pixel2D e : fillList) {
                    
                //}
                lp.setAndPaintPixel(new Pixel2D(p.x,p.y,c,1), mode);
                //lp.repaint();
                long end = System.currentTimeMillis();
                Thread.currentThread().stop();
                }
            }
            
        });
        fillPaint.start();
        synchronized(fillPaint) {
            try {
                fillPaint.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(DrawAlgorithm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        lp.repaint();
        return ret;
    }

    public static Point getPointInLine(Point p1, Point p2, double sigma) {
        int deltaX = Math.abs(p1.x - p2.x);
        int deltaY = Math.abs(p1.y - p2.y);
        return new Point((int) (p1.x + deltaX * sigma), (int) (p1.y + deltaY * sigma));
    }
    
    

    public static Point3D getPointInLine3D(Point3D p1, Point3D p2, double sigma, char giuNguyen) {
        Point po1, po2, pFinal;
        if (giuNguyen == 'x') {
            po1 = new Point(p1.y, p1.z);
            po2 = new Point(p2.y, p2.z);
            pFinal = getPointInLine(po1, po2, sigma);
            return new Point3D(p1.x, pFinal.x, pFinal.y);
        }
        if (giuNguyen == 'y') {
            po1 = new Point(p1.x, p1.z);
            po2 = new Point(p2.x, p2.z);
            pFinal = getPointInLine(po1, po2, sigma);
            return new Point3D( pFinal.x, p1.y, pFinal.y);
        }
        if (giuNguyen == 'z') {
            po1 = new Point(p1.x, p1.y);
            po2 = new Point(p2.x, p2.y);
            pFinal = getPointInLine(po1, po2, sigma);
            return new Point3D(pFinal.x, pFinal.y, p1.z);
        }
        return null;
    }

}
