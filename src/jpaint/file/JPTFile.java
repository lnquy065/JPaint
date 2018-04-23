package jpaint.file;

import java.io.Serializable;
import java.util.ArrayList;
import jpaint.LayerPanel;


public class JPTFile implements Serializable{
    public int width;
    public int height;
    public String fname;
    public ArrayList<LayerPanel> lpList;
    public JPTFile(int W, int H, String name, ArrayList<LayerPanel> lpList) {
        width=W;
        height=H;
        fname=name;
        this.lpList=lpList;
    }
}
