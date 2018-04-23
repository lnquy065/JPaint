package jpaint.shape3D;


public class Pyramid extends Shape3D{
   public void createWires() {
        super.createWires();
        
        Point3D centerTop = new Point3D((wrapPoints[3].x + wrapPoints[5].x) / 2, (wrapPoints[3].y + wrapPoints[5].y) / 2, (wrapPoints[3].z + wrapPoints[3].z) / 2);  
        
        Line3D l1 = rotateLine(new Line3D(wrapPoints[0], wrapPoints[1], Line3D.TYPE_NORMAL, "1"));
        Line3D l2 = rotateLine(new Line3D(wrapPoints[1], wrapPoints[5], Line3D.TYPE_NORMAL, "2"));
        Line3D l3 = rotateLine(new Line3D(wrapPoints[5], wrapPoints[4], Line3D.TYPE_NORMAL, "3"));
        Line3D l4 = rotateLine(new Line3D(wrapPoints[4], wrapPoints[0], Line3D.TYPE_NORMAL, "4"));
        
        lineList.add(l1);
        lineList.add(l2);
        lineList.add(l3);
        lineList.add(l4);
        
        Line3D l5 = rotateLine(new Line3D(wrapPoints[0], centerTop, Line3D.TYPE_NORMAL, "5"));
        Line3D l6 = rotateLine(new Line3D(wrapPoints[1], centerTop, Line3D.TYPE_NORMAL, "6"));
        Line3D l7 = rotateLine(new Line3D(wrapPoints[5], centerTop, Line3D.TYPE_NORMAL, "7"));
        Line3D l8 = rotateLine(new Line3D(wrapPoints[4],centerTop, Line3D.TYPE_NORMAL, "8"));

        lineList.add(l5);
        lineList.add(l6);
        lineList.add(l7);
        lineList.add(l8);    
        
        //Init surface
        surfaceList.add(new Surface(rotatePoint(centerTop), l1,l2,l3,l4));
        surfaceList.add(new Surface(rotatePoint(wrapPoints[4]), l1,l5,l6));
        surfaceList.add(new Surface(rotatePoint(wrapPoints[0]), l3,l7,l8));
        surfaceList.add(new Surface(rotatePoint(wrapPoints[0]), l2,l6,l7));
        surfaceList.add(new Surface(rotatePoint(wrapPoints[2]), l4,l5,l8));     

    }
    public int getType() {
        return Shape3D.TYPE_BOX;
    }
    
    public String getRealShapeName() {
        return "Pyramid";
    }
}
