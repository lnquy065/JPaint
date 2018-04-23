package jpaint.shape2D;


public class PixelShape extends Shape2D{

    public PixelShape() {}
    public PixelShape(Shape2D t) {
        super(t);
    }

    public String getShapeName() {
        return "pixel";
    }
    public int[] getAnchorLocation() {
        return new int[]{-1,-1,-1,-1};
    }
    public float[] getAnchorScale() {
        float[] t = {0,0,0,0};
        return t;
    }
    public int getType() {
        return Shape2D.TYPE_PIXEL;
    }
}
