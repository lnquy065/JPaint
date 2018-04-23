package jpaint.shape3D;

import jpaint.algorithm.DrawAlgorithm;


public class HexagonalPrism extends Shape3D {
 public void createWires() {
        super.createWires();

//        float heso1 = (float) 1/4;
//        float heso2 = (float) 3/4;
//        float heso3 = (float) 1/4;
//        float heso4 = (float) 3/4;
//        
//        Point3D p451 = DrawAlgorithm.getPointInLine3D(wrapPoints[4],wrapPoints[5],heso1,'z');
//        Point3D p452 = DrawAlgorithm.getPointInLine3D(wrapPoints[4],wrapPoints[5],heso2,'z');
//        Point3D p563 = DrawAlgorithm.getPointInLine3D(wrapPoints[5],wrapPoints[6],heso3,'x');
//        Point3D p564 = DrawAlgorithm.getPointInLine3D(wrapPoints[5],wrapPoints[6],heso4,'x');
//        Point3D p761 = DrawAlgorithm.getPointInLine3D(wrapPoints[7],wrapPoints[6],heso1,'y');
//        Point3D p762 = DrawAlgorithm.getPointInLine3D(wrapPoints[7],wrapPoints[6],heso2,'y');
//        Point3D p473 = DrawAlgorithm.getPointInLine3D(wrapPoints[4],wrapPoints[7],heso3,'x');
//        Point3D p474 = DrawAlgorithm.getPointInLine3D(wrapPoints[4],wrapPoints[7],heso4,'x');
//        
//        Line3D l1 = rotateLine(new Line3D(p451, p452, Line3D.TYPE_NORMAL, "1"));
//        Line3D l2 = rotateLine(new Line3D(p563, p564, Line3D.TYPE_NORMAL, "2"));
//        
//        
//        
//        
//        lineList.add(new Line3D(DrawAlgorithm.getPointInLine3D(wrapPoints[4],wrapPoints[5],heso1,'z'),DrawAlgorithm.getPointInLine3D(wrapPoints[4],wrapPoints[5],heso2,'z'), Line3D.TYPE_NORMAL));
//        lineList.add(new Line3D(DrawAlgorithm.getPointInLine3D(wrapPoints[5],wrapPoints[6],heso3,'x'),DrawAlgorithm.getPointInLine3D(wrapPoints[5],wrapPoints[6],heso4,'x'), Line3D.TYPE_NORMAL));
//        lineList.add(new Line3D(DrawAlgorithm.getPointInLine3D(wrapPoints[7],wrapPoints[6],heso1,'y'),DrawAlgorithm.getPointInLine3D(wrapPoints[7],wrapPoints[6],heso2,'y'), Line3D.TYPE_NORMAL));
//        lineList.add(new Line3D(DrawAlgorithm.getPointInLine3D(wrapPoints[4],wrapPoints[7],heso3,'x'),DrawAlgorithm.getPointInLine3D(wrapPoints[4],wrapPoints[7],heso4,'x'), Line3D.TYPE_NORMAL));
//        lineList.add(new Line3D(DrawAlgorithm.getPointInLine3D(wrapPoints[4],wrapPoints[5],heso2,'z'),DrawAlgorithm.getPointInLine3D(wrapPoints[5],wrapPoints[6],heso3,'x'), Line3D.TYPE_NORMAL));
//        lineList.add(new Line3D(DrawAlgorithm.getPointInLine3D(wrapPoints[5],wrapPoints[6],heso4,'x'),DrawAlgorithm.getPointInLine3D(wrapPoints[7],wrapPoints[6],heso2,'y'), Line3D.TYPE_NORMAL));
//        lineList.add(new Line3D(DrawAlgorithm.getPointInLine3D(wrapPoints[7],wrapPoints[6],heso1,'y'),DrawAlgorithm.getPointInLine3D(wrapPoints[4],wrapPoints[7],heso4,'x'), Line3D.TYPE_NORMAL));
//        lineList.add(new Line3D(DrawAlgorithm.getPointInLine3D(wrapPoints[4],wrapPoints[7],heso3,'x'),DrawAlgorithm.getPointInLine3D(wrapPoints[4],wrapPoints[5],heso1,'z'), Line3D.TYPE_NORMAL));  
// 
//        
//        Point3D p011 = DrawAlgorithm.getPointInLine3D(wrapPoints[0],wrapPoints[1],heso1,'z');
//        Point3D p012 = DrawAlgorithm.getPointInLine3D(wrapPoints[0],wrapPoints[1],heso2,'z');
//        Point3D p123 = DrawAlgorithm.getPointInLine3D(wrapPoints[1],wrapPoints[2],heso3,'x');
//        Point3D p124 = DrawAlgorithm.getPointInLine3D(wrapPoints[1],wrapPoints[2],heso4,'x');
//        Point3D p321 = DrawAlgorithm.getPointInLine3D(wrapPoints[3],wrapPoints[2],heso1,'y');
//        Point3D p322 = DrawAlgorithm.getPointInLine3D(wrapPoints[3],wrapPoints[2],heso2,'y');
//        Point3D p033 = DrawAlgorithm.getPointInLine3D(wrapPoints[0],wrapPoints[3],heso3,'x');
//        Point3D p034 = DrawAlgorithm.getPointInLine3D(wrapPoints[0],wrapPoints[3],heso4,'x');
//        
//        
//        
//        lineList.add(new Line3D(DrawAlgorithm.getPointInLine3D(wrapPoints[0],wrapPoints[1],heso1,'z'),DrawAlgorithm.getPointInLine3D(wrapPoints[0],wrapPoints[1],heso2,'z'), Line3D.TYPE_DASH));
//        lineList.add(new Line3D(DrawAlgorithm.getPointInLine3D(wrapPoints[1],wrapPoints[2],heso3,'x'),DrawAlgorithm.getPointInLine3D(wrapPoints[1],wrapPoints[2],heso4,'x'), Line3D.TYPE_NORMAL));
//        lineList.add(new Line3D(DrawAlgorithm.getPointInLine3D(wrapPoints[3],wrapPoints[2],heso1,'y'),DrawAlgorithm.getPointInLine3D(wrapPoints[3],wrapPoints[2],heso2,'y'), Line3D.TYPE_NORMAL));
//        lineList.add(new Line3D(DrawAlgorithm.getPointInLine3D(wrapPoints[0],wrapPoints[3],heso3,'x'),DrawAlgorithm.getPointInLine3D(wrapPoints[0],wrapPoints[3],heso4,'x'), Line3D.TYPE_DASH));        
//        lineList.add(new Line3D(DrawAlgorithm.getPointInLine3D(wrapPoints[0],wrapPoints[1],heso2,'z'),DrawAlgorithm.getPointInLine3D(wrapPoints[1],wrapPoints[2],heso3,'x'), Line3D.TYPE_NORMAL));
//        lineList.add(new Line3D(DrawAlgorithm.getPointInLine3D(wrapPoints[1],wrapPoints[2],heso4,'x'),DrawAlgorithm.getPointInLine3D(wrapPoints[3],wrapPoints[2],heso2,'y'), Line3D.TYPE_NORMAL));
//        lineList.add(new Line3D(DrawAlgorithm.getPointInLine3D(wrapPoints[3],wrapPoints[2],heso1,'y'),DrawAlgorithm.getPointInLine3D(wrapPoints[0],wrapPoints[3],heso4,'x'), Line3D.TYPE_DASH));
//        lineList.add(new Line3D(DrawAlgorithm.getPointInLine3D(wrapPoints[0],wrapPoints[3],heso3,'x'),DrawAlgorithm.getPointInLine3D(wrapPoints[0],wrapPoints[1],heso1,'z'), Line3D.TYPE_DASH));
//        
//        Point3D p451y = DrawAlgorithm.getPointInLine3D(wrapPoints[4],wrapPoints[5],heso1,'y');
//        Point3D p452y = DrawAlgorithm.getPointInLine3D(wrapPoints[4],wrapPoints[5],heso2,'y');
//        Point3D p011z = DrawAlgorithm.getPointInLine3D(wrapPoints[0],wrapPoints[1],heso1,'z');
//        Point3D p012z = DrawAlgorithm.getPointInLine3D(wrapPoints[0],wrapPoints[1],heso2,'z');
//        Point3D p563x = DrawAlgorithm.getPointInLine3D(wrapPoints[5],wrapPoints[6],heso3,'x');
//        Point3D p565x = DrawAlgorithm.getPointInLine3D(wrapPoints[5],wrapPoints[6],heso4,'x');
//        Point3D p762y = DrawAlgorithm.getPointInLine3D(wrapPoints[7],wrapPoints[6],heso2,'y');
//        Point3D p761y = DrawAlgorithm.getPointInLine3D(wrapPoints[7],wrapPoints[6],heso1,'y');
//        Point3D p474x = DrawAlgorithm.getPointInLine3D(wrapPoints[4],wrapPoints[7],heso4,'x');
//        Point3D p473x = DrawAlgorithm.getPointInLine3D(wrapPoints[4],wrapPoints[7],heso3,'x');
//        
//        
//        lineList.add(new Line3D(DrawAlgorithm.getPointInLine3D(wrapPoints[4],wrapPoints[5],heso1,'y'),DrawAlgorithm.getPointInLine3D(wrapPoints[0],wrapPoints[1],heso1,'z'), Line3D.TYPE_DASH));
//        lineList.add(new Line3D(DrawAlgorithm.getPointInLine3D(wrapPoints[4],wrapPoints[5],heso2,'y'),DrawAlgorithm.getPointInLine3D(wrapPoints[0],wrapPoints[1],heso2,'z'), Line3D.TYPE_NORMAL));
//        lineList.add(new Line3D(DrawAlgorithm.getPointInLine3D(wrapPoints[5],wrapPoints[6],heso3,'x'),DrawAlgorithm.getPointInLine3D(wrapPoints[1],wrapPoints[2],heso3,'x'), Line3D.TYPE_NORMAL));
//        lineList.add(new Line3D(DrawAlgorithm.getPointInLine3D(wrapPoints[5],wrapPoints[6],heso4,'x'),DrawAlgorithm.getPointInLine3D(wrapPoints[1],wrapPoints[2],heso4,'x'), Line3D.TYPE_NORMAL));
//        lineList.add(new Line3D(DrawAlgorithm.getPointInLine3D(wrapPoints[7],wrapPoints[6],heso2,'y'),DrawAlgorithm.getPointInLine3D(wrapPoints[3],wrapPoints[2],heso2,'y'), Line3D.TYPE_NORMAL));
//        lineList.add(new Line3D(DrawAlgorithm.getPointInLine3D(wrapPoints[7],wrapPoints[6],heso1,'y'),DrawAlgorithm.getPointInLine3D(wrapPoints[3],wrapPoints[2],heso1,'y'), Line3D.TYPE_NORMAL));
//        lineList.add(new Line3D(DrawAlgorithm.getPointInLine3D(wrapPoints[4],wrapPoints[7],heso4,'x'),DrawAlgorithm.getPointInLine3D(wrapPoints[0],wrapPoints[3],heso4,'x'), Line3D.TYPE_DASH));
//        lineList.add(new Line3D(DrawAlgorithm.getPointInLine3D(wrapPoints[4],wrapPoints[7],heso3,'x'),DrawAlgorithm.getPointInLine3D(wrapPoints[0],wrapPoints[3],heso3,'x'), Line3D.TYPE_DASH));
//        
//        
//        //Line list
//        Line3D l1 = rotateLine(new Line3D(wrapPoints[0], wrapPoints[1], Line3D.TYPE_NORMAL, "1"));
//        Line3D l2 = rotateLine(new Line3D(wrapPoints[1], wrapPoints[4], Line3D.TYPE_NORMAL, "2"));
//        Line3D l3 = rotateLine(new Line3D(wrapPoints[4], wrapPoints[0], Line3D.TYPE_NORMAL, "3"));
//        Line3D l4 = rotateLine(new Line3D(wrapPoints[3], wrapPoints[2], Line3D.TYPE_NORMAL, "4"));
//        Line3D l5 = rotateLine(new Line3D(wrapPoints[2], wrapPoints[7], Line3D.TYPE_NORMAL, "5"));
//        Line3D l6 = rotateLine(new Line3D(wrapPoints[7], wrapPoints[3], Line3D.TYPE_NORMAL, "6"));
//         Line3D l7 = rotateLine(new Line3D(wrapPoints[3], wrapPoints[0], Line3D.TYPE_NORMAL, "7"));
//          Line3D l8 = rotateLine(new Line3D(wrapPoints[2], wrapPoints[1], Line3D.TYPE_NORMAL, "8"));
//           Line3D l9 = rotateLine(new Line3D(wrapPoints[7], wrapPoints[4], Line3D.TYPE_NORMAL, "9"));
//                   Line3D l10 = rotateLine(new Line3D(wrapPoints[0], wrapPoints[1], Line3D.TYPE_NORMAL, "10"));
//        Line3D l11 = rotateLine(new Line3D(wrapPoints[1], wrapPoints[4], Line3D.TYPE_NORMAL, "11"));
//        Line3D l12 = rotateLine(new Line3D(wrapPoints[4], wrapPoints[0], Line3D.TYPE_NORMAL, "12"));
//        Line3D l13 = rotateLine(new Line3D(wrapPoints[3], wrapPoints[2], Line3D.TYPE_NORMAL, "13"));
//        Line3D l14 = rotateLine(new Line3D(wrapPoints[2], wrapPoints[7], Line3D.TYPE_NORMAL, "14"));
//        Line3D l15 = rotateLine(new Line3D(wrapPoints[7], wrapPoints[3], Line3D.TYPE_NORMAL, "15"));
//         Line3D l16 = rotateLine(new Line3D(wrapPoints[3], wrapPoints[0], Line3D.TYPE_NORMAL, "16"));
//          Line3D l17 = rotateLine(new Line3D(wrapPoints[2], wrapPoints[1], Line3D.TYPE_NORMAL, "17"));
//           Line3D l18 = rotateLine(new Line3D(wrapPoints[7], wrapPoints[4], Line3D.TYPE_NORMAL, "18"));
//              Line3D l19 = rotateLine(new Line3D(wrapPoints[3], wrapPoints[2], Line3D.TYPE_NORMAL, "19"));
//        Line3D l20 = rotateLine(new Line3D(wrapPoints[2], wrapPoints[7], Line3D.TYPE_NORMAL, "20"));
//        Line3D l21 = rotateLine(new Line3D(wrapPoints[7], wrapPoints[3], Line3D.TYPE_NORMAL, "21"));
//         Line3D l22 = rotateLine(new Line3D(wrapPoints[3], wrapPoints[0], Line3D.TYPE_NORMAL, "22"));
//          Line3D l23 = rotateLine(new Line3D(wrapPoints[2], wrapPoints[1], Line3D.TYPE_NORMAL, "23"));
//           Line3D l24 = rotateLine(new Line3D(wrapPoints[7], wrapPoints[4], Line3D.TYPE_NORMAL, "24"));
//        
//        lineList.add(l1);
//        lineList.add(l2);
//        lineList.add(l3);
//        lineList.add(l4);
//        lineList.add(l5);
//        lineList.add(l6);
//        lineList.add(l7);
//        lineList.add(l8);
//        lineList.add(l9);
//        
//        //Init surface
//        surfaceList.add(new Surface(rotatePoint(wrapPoints[3]), l1,l2,l3));
//        surfaceList.add(new Surface(rotatePoint(wrapPoints[0]), l4,l5,l6));
//        surfaceList.add(new Surface(rotatePoint(wrapPoints[0]), l5,l2,l9,l8));
//        surfaceList.add(new Surface(rotatePoint(wrapPoints[2]), l3,l6,l7,l9));
//        surfaceList.add(new Surface(rotatePoint(wrapPoints[4]), l1,l4,l7,l8));
    }
    public int getType() {
        return Shape3D.TYPE_BOX;
    }
    
    
    public String getRealShapeName() {
        return "Hexagonal Prism";
    }
}
