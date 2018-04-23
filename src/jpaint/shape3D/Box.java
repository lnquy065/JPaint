package jpaint.shape3D;


public class Box extends Shape3D{
    public void createWires() {
        super.createWires();
        //rotateAllPoint();
        
        //Line list
        Line3D l01 = rotateLine(new Line3D(wrapPoints[0],wrapPoints[1], Line3D.TYPE_NORMAL, "01"));
        Line3D l12 = rotateLine(new Line3D(wrapPoints[1],wrapPoints[2], Line3D.TYPE_NORMAL, "12"));
        Line3D l23 = rotateLine(new Line3D(wrapPoints[2],wrapPoints[3], Line3D.TYPE_NORMAL, "23"));
        Line3D l30 = rotateLine(new Line3D(wrapPoints[3],wrapPoints[0], Line3D.TYPE_NORMAL, "30"));
        
        lineList.add(l01);
        lineList.add(l12);
        lineList.add(l23);
        lineList.add(l30);
        
        Line3D l45 = rotateLine(new Line3D(wrapPoints[4],wrapPoints[5], Line3D.TYPE_NORMAL, "45"));
        Line3D l56 = rotateLine(new Line3D(wrapPoints[5],wrapPoints[6], Line3D.TYPE_NORMAL, "56"));
        Line3D l67 = rotateLine(new Line3D(wrapPoints[6],wrapPoints[7], Line3D.TYPE_NORMAL, "67"));
        Line3D l74 = rotateLine(new Line3D(wrapPoints[7],wrapPoints[4], Line3D.TYPE_NORMAL, "174"));
        
        lineList.add(l45);
        lineList.add(l56);
        lineList.add(l67);
        lineList.add(l74);
        
        Line3D l04 = rotateLine(new Line3D(wrapPoints[0],wrapPoints[4], Line3D.TYPE_NORMAL, "104"));
        Line3D l15 = rotateLine(new Line3D(wrapPoints[1],wrapPoints[5], Line3D.TYPE_NORMAL, "115"));
        Line3D l26 = rotateLine(new Line3D(wrapPoints[2],wrapPoints[6], Line3D.TYPE_NORMAL, "126"));
        Line3D l37 = rotateLine(new Line3D(wrapPoints[3],wrapPoints[7], Line3D.TYPE_NORMAL, "137"));
        
        lineList.add(l04);
        lineList.add(l15);
        lineList.add(l26);
        lineList.add(l37);
        
        //Init surface
        surfaceList.add(new Surface(rotatePoint(wrapPoints[6]), l01,l12,l23,l30));
        surfaceList.add(new Surface(rotatePoint(wrapPoints[2]), l45,l56,l67,l74));
        surfaceList.add(new Surface(rotatePoint(wrapPoints[4]), l12,l26,l56,l15));
        surfaceList.add(new Surface(rotatePoint(wrapPoints[5]), l30,l37,l74,l04));
        surfaceList.add(new Surface(rotatePoint(wrapPoints[5]), l23,l37,l67,l26));
        surfaceList.add(new Surface(rotatePoint(wrapPoints[3]), l01,l15,l45,l04));
    }
    public int getType() {
        return Shape3D.TYPE_BOX;
    }
    
    
    public String getRealShapeName() {
        return "Box";
    }
}
