package jpaint.shape3D;


public class TriangularPrism extends Shape3D {
  public void createWires() {
        super.createWires();

        //Line list
        Line3D l1 = rotateLine(new Line3D(wrapPoints[0], wrapPoints[1], Line3D.TYPE_NORMAL, "1"));
        Line3D l2 = rotateLine(new Line3D(wrapPoints[1], wrapPoints[4], Line3D.TYPE_NORMAL, "2"));
        Line3D l3 = rotateLine(new Line3D(wrapPoints[4], wrapPoints[0], Line3D.TYPE_NORMAL, "3"));
        Line3D l4 = rotateLine(new Line3D(wrapPoints[3], wrapPoints[2], Line3D.TYPE_NORMAL, "4"));
        Line3D l5 = rotateLine(new Line3D(wrapPoints[2], wrapPoints[7], Line3D.TYPE_NORMAL, "5"));
        Line3D l6 = rotateLine(new Line3D(wrapPoints[7], wrapPoints[3], Line3D.TYPE_NORMAL, "6"));
         Line3D l7 = rotateLine(new Line3D(wrapPoints[3], wrapPoints[0], Line3D.TYPE_NORMAL, "7"));
          Line3D l8 = rotateLine(new Line3D(wrapPoints[2], wrapPoints[1], Line3D.TYPE_NORMAL, "8"));
           Line3D l9 = rotateLine(new Line3D(wrapPoints[7], wrapPoints[4], Line3D.TYPE_NORMAL, "9"));
        
        lineList.add(l1);
        lineList.add(l2);
        lineList.add(l3);
        lineList.add(l4);
        lineList.add(l5);
        lineList.add(l6);
        lineList.add(l7);
        lineList.add(l8);
        lineList.add(l9);
        
        //Init surface
        surfaceList.add(new Surface(rotatePoint(wrapPoints[3]), l1,l2,l3));
        surfaceList.add(new Surface(rotatePoint(wrapPoints[0]), l4,l5,l6));
        surfaceList.add(new Surface(rotatePoint(wrapPoints[0]), l5,l2,l9,l8));
        surfaceList.add(new Surface(rotatePoint(wrapPoints[2]), l3,l6,l7,l9));
        surfaceList.add(new Surface(rotatePoint(wrapPoints[4]), l1,l4,l7,l8));
    }
    public int getType() {
        return Shape3D.TYPE_BOX;
    }
    
    
    public String getRealShapeName() {
        return "Triangular Prism";
    }
}
