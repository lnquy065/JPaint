package jpaint.shape3D;


public class Tetrahedron extends Shape3D {
   public void createWires() {
        super.createWires();
        //rotateAllPoint();l1

        //Line list
        Line3D l1 = rotateLine(new Line3D(wrapPoints[0], wrapPoints[1], Line3D.TYPE_NORMAL, "1"));
        Line3D l2 = rotateLine(new Line3D(wrapPoints[1], wrapPoints[4], Line3D.TYPE_NORMAL, "2"));
        Line3D l3 = rotateLine(new Line3D(wrapPoints[4], wrapPoints[0], Line3D.TYPE_NORMAL, "3"));
        Line3D l4 = rotateLine(new Line3D(wrapPoints[3], wrapPoints[4], Line3D.TYPE_NORMAL, "4"));
        Line3D l5 = rotateLine(new Line3D(wrapPoints[3], wrapPoints[1], Line3D.TYPE_NORMAL, "5"));
        Line3D l6 = rotateLine(new Line3D(wrapPoints[3], wrapPoints[0], Line3D.TYPE_NORMAL, "6"));
        
        lineList.add(l1);
        lineList.add(l2);
        lineList.add(l3);
        lineList.add(l4);
        lineList.add(l5);
        lineList.add(l6);
        
        //Init surface
        surfaceList.add(new Surface(rotatePoint(wrapPoints[3]), l1,l2,l3));
        surfaceList.add(new Surface(rotatePoint(wrapPoints[0]), l2,l5,l4));
        surfaceList.add(new Surface(rotatePoint(wrapPoints[4]), l1,l5,l6));
        surfaceList.add(new Surface(rotatePoint(wrapPoints[2]), l3,l6,l4));
    }
    public int getType() {
        return Shape3D.TYPE_BOX;
    }
    
    
    public String getRealShapeName() {
        return "Tetrahedron";
    }
}
