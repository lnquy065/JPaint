package jpaint;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.awt.event.InputEvent.CTRL_DOWN_MASK;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_N;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;

import javax.swing.ListCellRenderer;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import jpaint.shape2D.Brush;
import jpaint.shape2D.Line;
import jpaint.shape2D.Pencil;
import jpaint.shape2D.Rectangle;
import jpaint.shape2D.Shape2D;
import javax.swing.SwingConstants;
import jpaint.shape2D.Ellipse;
import jpaint.shape2D.Trapezoid;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import jpaint.shape3D.Shape3D;
import jpaint.shape3D.Box;
import jpaint.shape3D.Point3D;
import jpaint.shape3D.Pyramid;
import javax.swing.Timer;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import jpaint.shape2D.Diamond;
import jpaint.shape2D.Parallelogram;
import jpaint.shape2D.Triangle;
import jpaint.shape2D.Polygons;
import jpaint.algorithm.DrawAlgorithm;
import jpaint.file.JPTFile;
import jpaint.shape2D.PixelShape;
import jpaint.shape3D.Tetrahedron;
import jpaint.shape3D.TriangularPrism;

/**
 *
 * @author LN Quy
 */
public class MainForm extends javax.swing.JFrame {

    private int MODE_DRAW_2D = 1;
    private int MODE_EDIT_2D = 2;
    private int MODE_EDIT_3D = 7;
    private int MODE_DRAW_3D = 6;
    private int MODE_FLIPPOINT = 3;
    private int MODE_FLIPLINE = 4;
    private int MODE_ROTATEPOINT = 5;
    private int curMode = 0;
    private int CLIPBOARD_MODE_COPY = 0;
    private int CLIPBOARD_MODE_CUT = 1;
    public static int CROOD_MODE_DEFAULT = 0;
    public static int CROOD_MODE_USER = 1;
    public static int CROOD_MODE_3D = 2;

    private ArrayList<JToggleButton> toolList2D = new ArrayList();
    private ArrayList<JToggleButton> toolList3D = new ArrayList();
    private ArrayList<JLabel> colorList = new ArrayList();
    private DefaultListModel<ListItem> model = new DefaultListModel<>();
    private Vector<DoItem> undoList = new Vector<DoItem>();
    private Vector<DoItem> redoList = new Vector<DoItem>();
    private ImagePanel imagePane;
    private ImagePanel curImage = null;
    private String curTool = "";
    private Color[] color = new Color[2];
    private JLabel[] colorC = new JLabel[2];
    private int colorMode = 0;
    private int zoom = 1;
    private boolean grid = false;
    private Cursor csPointer;
    private Point mouse = new Point(0, 0);
    private boolean cClick = false;
    private Color cColor;
    private int toolOpSize = 1;
    private WrapPane2D wpane;
    private WrapPane2D wrapPane;
    private Cursor csTemp;
    private boolean isResize = false;
    private int wrapAnchorPos;
    private boolean isRotate = false;
    private boolean isScroll;
    private Point oldScroll;
    private boolean isMove;
    private Point oldMove;
    private boolean isCreateShape;
    private int gridMode;
    private boolean inFLipPoint;
    private boolean inFlipLine;
    private boolean isMoveRotatePoint = false;
    private boolean isRotatePoint;
    private boolean tShape2DExist;
    private boolean addedUndoList;
    private Shape2D clipBoardShape2D;
    private int clipBoardMode;
    private boolean isRuler;
    private RulerPanel rulerPanel;
    private boolean isShapeAnchor;
    private int wrapShapeAnchorPos;
    private Point endCreate3DShape;

    public MainForm() {
        setUI();
        initComponents();
        initVars();
        customInit();
    }

    public void initVars() {
        color[0] = new Color(0, 0, 0);
        color[1] = new Color(255, 255, 255);
        colorC[0] = lbPriColor;
        colorC[1] = lbSecColor;
        lbPriColor.setBackground(color[0]);
        lbSecColor.setBackground(color[1]);
    }

    public void setUI() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ex) {
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        toolBar = new javax.swing.JToolBar();
        toolBarBtnNew = new javax.swing.JButton();
        toolBarBtnOpen = new javax.swing.JButton();
        toolBarBtnSave = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        toolBarBtnUndo = new javax.swing.JButton();
        toolBarBtnRedo = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        toolBarBtnCut = new javax.swing.JButton();
        toolBarBtnCopy = new javax.swing.JButton();
        toolBarBtnPaste = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        toolBarBtnTgGrid = new javax.swing.JToggleButton();
        toolBarBtnTgOxy = new javax.swing.JToggleButton();
        toolBarBtnRuler = new javax.swing.JToggleButton();
        WPControlSp = new javax.swing.JToolBar.Separator();
        WPControlBtnFlipOx = new javax.swing.JButton();
        WPControlBtnFlipOy = new javax.swing.JButton();
        WPControlBtnFlipPoint = new javax.swing.JButton();
        WPControlBtnFlipLine = new javax.swing.JButton();
        WPControlBtnRotateRight = new javax.swing.JButton();
        WPControlBtnRotateLeft = new javax.swing.JButton();
        WPControlSp0 = new javax.swing.JToolBar.Separator();
        WPControlMoveUp = new javax.swing.JButton();
        WPControlMoveDown = new javax.swing.JButton();
        WPControlNormalize = new javax.swing.JButton();
        WPControlBtnRemove = new javax.swing.JButton();
        WPControl3DSp1 = new javax.swing.JToolBar.Separator();
        WPControlBtnMove3D = new javax.swing.JToggleButton();
        WPControlBtnRotate3D = new javax.swing.JToggleButton();
        WPControlBtnFlip3D = new javax.swing.JToggleButton();
        WPControl3DFlipOxyz = new javax.swing.JButton();
        WPControl3DSp2 = new javax.swing.JToolBar.Separator();
        WPControl3DRemove = new javax.swing.JButton();
        deskPane = new javax.swing.JDesktopPane();
        panelToolbox = new javax.swing.JPanel();
        btnPencil = new javax.swing.JToggleButton();
        btnBrush = new javax.swing.JToggleButton();
        btnPicker = new javax.swing.JToggleButton();
        btnFill = new javax.swing.JToggleButton();
        btnSelector = new javax.swing.JToggleButton();
        btnSelection = new javax.swing.JToggleButton();
        btnLasso = new javax.swing.JToggleButton();
        spTool1 = new javax.swing.JSeparator();
        btnEraser = new javax.swing.JToggleButton();
        jLabel3 = new javax.swing.JLabel();
        btnRect = new javax.swing.JToggleButton();
        btnTriangle = new javax.swing.JToggleButton();
        btnTrapezoid = new javax.swing.JToggleButton();
        btnPara = new javax.swing.JToggleButton();
        btnPoly = new javax.swing.JToggleButton();
        btnWand = new javax.swing.JToggleButton();
        btnLine = new javax.swing.JToggleButton();
        spTool2 = new javax.swing.JSeparator();
        btnDiamond = new javax.swing.JToggleButton();
        btnElip = new javax.swing.JToggleButton();
        btnSwitchShape = new javax.swing.JToggleButton();
        btnBox = new javax.swing.JToggleButton();
        btnPyramid = new javax.swing.JToggleButton();
        btnTetrahedron = new javax.swing.JToggleButton();
        btnTriangularPrism = new javax.swing.JToggleButton();
        panelProperties = new javax.swing.JPanel();
        panelPropertieslb1 = new javax.swing.JLabel();
        panelPropertiesScrollPane = new javax.swing.JScrollPane();
        panelPropertiesViewPort = new javax.swing.JPanel();
        proTypePanel = new javax.swing.JPanel();
        proLocationlb4 = new javax.swing.JLabel();
        proTypelbShapeName = new javax.swing.JLabel();
        proLocationPanel = new javax.swing.JPanel();
        proLocationlb1 = new javax.swing.JLabel();
        proLocationlb2 = new javax.swing.JLabel();
        proLocationtxtX = new javax.swing.JTextField();
        proLocationlb3 = new javax.swing.JLabel();
        proLocationtxtY = new javax.swing.JTextField();
        proLocationbtnXYReset = new javax.swing.JButton();
        proSizePanel = new javax.swing.JPanel();
        proSizelb1 = new javax.swing.JLabel();
        proSizelbW = new javax.swing.JLabel();
        proSizetxtW = new javax.swing.JTextField();
        proSizelbH = new javax.swing.JLabel();
        proSizetxtH = new javax.swing.JTextField();
        proSizelbR = new javax.swing.JLabel();
        proSizetxtR = new javax.swing.JTextField();
        proSizelnR1 = new javax.swing.JLabel();
        proSizetxtR1 = new javax.swing.JTextField();
        proSizelbR2 = new javax.swing.JLabel();
        proSizetxtR2 = new javax.swing.JTextField();
        proTransformPanel = new javax.swing.JPanel();
        proTransformlb1 = new javax.swing.JLabel();
        proTransformlbRotate = new javax.swing.JLabel();
        proTransformtxtRotate = new javax.swing.JTextField();
        proTransformbtnResetRotate = new javax.swing.JButton();
        proTransformlbRotatePoint = new javax.swing.JLabel();
        proTransformtxtRotatePointX = new javax.swing.JTextField();
        proTransformtxtRotatePointY = new javax.swing.JTextField();
        proTransformbtnResetRotatePoint = new javax.swing.JButton();
        proTransformScaling = new javax.swing.JLabel();
        proTransformtxtScalingX = new javax.swing.JTextField();
        proTransformlb2 = new javax.swing.JLabel();
        proTransformtxtScalingY = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        proRotate3DPanel = new javax.swing.JPanel();
        proLocationlb10 = new javax.swing.JLabel();
        proRotate3DX = new javax.swing.JLabel();
        proRotate3DTxtX = new javax.swing.JTextField();
        proRotate3DY = new javax.swing.JLabel();
        proRotate3DTxtY = new javax.swing.JTextField();
        proRotate3DZ = new javax.swing.JLabel();
        proRotate3DTxtZ = new javax.swing.JTextField();
        proColorPanel = new javax.swing.JPanel();
        proColorlb1 = new javax.swing.JLabel();
        proColorlbOutline = new javax.swing.JLabel();
        proColorlbSolid = new javax.swing.JLabel();
        proColorbtnReset = new javax.swing.JButton();
        lbOutLineColor = new javax.swing.JLabel();
        lbFillColor = new javax.swing.JLabel();
        proPointsPanel = new javax.swing.JPanel();
        proPointslb1 = new javax.swing.JLabel();
        proPointslbP1 = new javax.swing.JLabel();
        proPointsP1X = new javax.swing.JTextField();
        proPointslb2 = new javax.swing.JLabel();
        proPointsP1Y = new javax.swing.JTextField();
        proPointslbP2 = new javax.swing.JLabel();
        proPointstxtP2X = new javax.swing.JTextField();
        proPointslb3 = new javax.swing.JLabel();
        proPointstxtP2Y = new javax.swing.JTextField();
        proPointslbP3 = new javax.swing.JLabel();
        proPointstxtP3X = new javax.swing.JTextField();
        proPointslb4 = new javax.swing.JLabel();
        proPointstxtP3Y = new javax.swing.JTextField();
        proPointslbP4 = new javax.swing.JLabel();
        proPointstxtP4X = new javax.swing.JTextField();
        proPointslb5 = new javax.swing.JLabel();
        proPointstxtP4Y = new javax.swing.JTextField();
        proPoints3DPanel = new javax.swing.JPanel();
        proLocationlb11 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        proPoints3DP1X = new javax.swing.JTextField();
        proPointslb6 = new javax.swing.JLabel();
        proPoints3DP1Y = new javax.swing.JTextField();
        proPointslb7 = new javax.swing.JLabel();
        proPoints3DP1Z = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        proPoints3DP2X = new javax.swing.JTextField();
        proPointslb8 = new javax.swing.JLabel();
        proPoints3DP2Y = new javax.swing.JTextField();
        proPointslb9 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        proPointslb10 = new javax.swing.JLabel();
        proPointslb11 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        proPointslb12 = new javax.swing.JLabel();
        proPointslb13 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        proPointslb14 = new javax.swing.JLabel();
        proPointslb15 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        proPointslb16 = new javax.swing.JLabel();
        proPointslb17 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        proPointslb18 = new javax.swing.JLabel();
        proPointslb19 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        proPointslb20 = new javax.swing.JLabel();
        proPointslb21 = new javax.swing.JLabel();
        proPoints3DP2Z = new javax.swing.JTextField();
        proPoints3DP3X = new javax.swing.JTextField();
        proPoints3DP3Y = new javax.swing.JTextField();
        proPoints3DP3Z = new javax.swing.JTextField();
        proPoints3DP4X = new javax.swing.JTextField();
        proPoints3DP4Y = new javax.swing.JTextField();
        proPoints3DP4Z = new javax.swing.JTextField();
        proPoints3DP5X = new javax.swing.JTextField();
        proPoints3DP5Y = new javax.swing.JTextField();
        proPoints3DP5Z = new javax.swing.JTextField();
        proPoints3DP6X = new javax.swing.JTextField();
        proPoints3DP6Y = new javax.swing.JTextField();
        proPoints3DP6Z = new javax.swing.JTextField();
        proPoints3DP7X = new javax.swing.JTextField();
        proPoints3DP7Y = new javax.swing.JTextField();
        proPoints3DP7Z = new javax.swing.JTextField();
        proPoints3DP8X = new javax.swing.JTextField();
        proPoints3DP8Y = new javax.swing.JTextField();
        proPoints3DP8Z = new javax.swing.JTextField();
        proLocation3DPanel = new javax.swing.JPanel();
        proLocationlb8 = new javax.swing.JLabel();
        proLocationLbX = new javax.swing.JLabel();
        proLocationTxtX = new javax.swing.JTextField();
        proLocationLbY = new javax.swing.JLabel();
        proLocationTxtY = new javax.swing.JTextField();
        proLocationLbZ = new javax.swing.JLabel();
        proLocationTxtZ = new javax.swing.JTextField();
        proSize3DPanel = new javax.swing.JPanel();
        proLocationlb9 = new javax.swing.JLabel();
        proSizeLbLength = new javax.swing.JLabel();
        proSize3DTxtL = new javax.swing.JTextField();
        proSizeLbWidth = new javax.swing.JLabel();
        proSize3DTxtW = new javax.swing.JTextField();
        proSizeLbHeight = new javax.swing.JLabel();
        proSize3DTxtH = new javax.swing.JTextField();
        scrollPanel = new javax.swing.JScrollPane();
        viewPort = new javax.swing.JPanel();
        statusBar = new javax.swing.JPanel();
        slZoom = new javax.swing.JSlider();
        lbZoom = new javax.swing.JLabel();
        lbImageSize = new javax.swing.JLabel();
        lbImagePos = new javax.swing.JLabel();
        panelLayers = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listLayer = new javax.swing.JList<>();
        layerToolBtnAdd = new javax.swing.JButton();
        layerToolBtnRemove = new javax.swing.JButton();
        layerToolBtnMerge = new javax.swing.JButton();
        layerToolBtnMoveUp = new javax.swing.JButton();
        layerToolBtnMoveDown = new javax.swing.JButton();
        panelColors = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lbPriColor = new javax.swing.JLabel();
        lbSecColor = new javax.swing.JLabel();
        lbCurColor = new javax.swing.JLabel();
        lbColor1 = new javax.swing.JLabel();
        lbColor2 = new javax.swing.JLabel();
        lbColor3 = new javax.swing.JLabel();
        lbColor4 = new javax.swing.JLabel();
        lbColor5 = new javax.swing.JLabel();
        lbColor6 = new javax.swing.JLabel();
        lbColor7 = new javax.swing.JLabel();
        lbColor8 = new javax.swing.JLabel();
        lbColor9 = new javax.swing.JLabel();
        lbColor10 = new javax.swing.JLabel();
        lbColor11 = new javax.swing.JLabel();
        lbColor12 = new javax.swing.JLabel();
        lbColor13 = new javax.swing.JLabel();
        lbColor14 = new javax.swing.JLabel();
        lbColor15 = new javax.swing.JLabel();
        lbColor16 = new javax.swing.JLabel();
        lbColor17 = new javax.swing.JLabel();
        lbColor18 = new javax.swing.JLabel();
        lbColor19 = new javax.swing.JLabel();
        lbColor20 = new javax.swing.JLabel();
        slRed = new javax.swing.JSlider();
        slGreen = new javax.swing.JSlider();
        slBlue = new javax.swing.JSlider();
        lbColorRed = new javax.swing.JLabel();
        lbColorGreen = new javax.swing.JLabel();
        lbColorBlue = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        extendBar = new javax.swing.JToolBar();
        lbCurTool = new javax.swing.JLabel();
        spSizeBrush = new javax.swing.JToolBar.Separator();
        lbSizeBrush = new javax.swing.JLabel();
        extendBarCbSize = new javax.swing.JComboBox<>();
        spStyleBrush = new javax.swing.JToolBar.Separator();
        lbStyleBrush = new javax.swing.JLabel();
        btnStyleBrushRect = new javax.swing.JToggleButton();
        btnStyleBrushCircle = new javax.swing.JToggleButton();
        spLineAlg = new javax.swing.JToolBar.Separator();
        lbLineAlg = new javax.swing.JLabel();
        cbLineAlg = new javax.swing.JComboBox<>();
        extendBarSpEdge = new javax.swing.JToolBar.Separator();
        extendBarLbEdge = new javax.swing.JLabel();
        extendBarCbEdge = new javax.swing.JComboBox<>();
        extendBarSpStroke = new javax.swing.JToolBar.Separator();
        extendBarLbStroke = new javax.swing.JLabel();
        extendBarCbStroke = new javax.swing.JComboBox<>();
        extendBarSpTypes = new javax.swing.JToolBar.Separator();
        extendBarLbTypes = new javax.swing.JLabel();
        extendBarBtnTypesTri1 = new javax.swing.JButton();
        extendBarBtnTypesTri2 = new javax.swing.JButton();
        extendBarBtnTypesTri3 = new javax.swing.JButton();
        extendBarBtnTypesTrap1 = new javax.swing.JButton();
        extendBarBtnTypesTrap2 = new javax.swing.JButton();
        extendBarBtnTypesTrap3 = new javax.swing.JButton();
        extendBarBtnTypesSquare1 = new javax.swing.JButton();
        extendBarBtnTypesCircle1 = new javax.swing.JButton();
        extendBarSPRotate3D = new javax.swing.JToolBar.Separator();
        extendBarLbRotate3D = new javax.swing.JLabel();
        extendBarLbDX = new javax.swing.JLabel();
        sliderRotateX = new javax.swing.JSlider();
        extendBarLbDY = new javax.swing.JLabel();
        sliderRotateY = new javax.swing.JSlider();
        extendBarLbDZ = new javax.swing.JLabel();
        sliderRotateZ = new javax.swing.JSlider();
        extendBarSpLocation = new javax.swing.JToolBar.Separator();
        extendBarLbLocation = new javax.swing.JLabel();
        extendBarLbLocationX = new javax.swing.JLabel();
        sliderLocationX = new javax.swing.JSlider();
        extendBarLbLocationY = new javax.swing.JLabel();
        sliderLocationY = new javax.swing.JSlider();
        extendBarLbLocationZ = new javax.swing.JLabel();
        sliderLocationZ = new javax.swing.JSlider();
        extendBarSpFlip = new javax.swing.JToolBar.Separator();
        extendBarLbFlip3D = new javax.swing.JLabel();
        extendBarLbFlipAxis = new javax.swing.JLabel();
        btnFlipOx = new javax.swing.JButton();
        btnFlipOy = new javax.swing.JButton();
        btnFlipOz = new javax.swing.JButton();
        extendBarLbFlipPlane = new javax.swing.JLabel();
        btnFlipOxy = new javax.swing.JButton();
        btnFlipOyz = new javax.swing.JButton();
        btnFlipOxz = new javax.swing.JButton();
        extendBarSpDone = new javax.swing.JToolBar.Separator();
        extendBarBtnDone = new javax.swing.JButton();
        proChangedFinish = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuFileNew = new javax.swing.JMenuItem();
        menuFileOpen = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        menuFileSave = new javax.swing.JMenuItem();
        menuFileSaveAs = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        menuFileExit = new javax.swing.JMenuItem();
        menuEdit = new javax.swing.JMenu();
        menuEditUndo = new javax.swing.JMenuItem();
        menuEditRedo = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        menuEditCopy = new javax.swing.JMenuItem();
        menuEditCut = new javax.swing.JMenuItem();
        menuEditPaste = new javax.swing.JMenuItem();
        menuView = new javax.swing.JMenu();
        menuViewGrid = new javax.swing.JCheckBoxMenuItem();
        menuViewUserMode = new javax.swing.JCheckBoxMenuItem();
        menuViewRuler = new javax.swing.JCheckBoxMenuItem();
        menuLayer = new javax.swing.JMenu();
        menuLayerAdd = new javax.swing.JMenuItem();
        menuLayerRemove = new javax.swing.JMenuItem();
        menuLayerMerge = new javax.swing.JMenuItem();
        menuLayerMoveUp = new javax.swing.JMenuItem();
        menuLayerMoveDown = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JPaint");
        setBackground(new java.awt.Color(153, 153, 153));

        toolBar.setFloatable(false);
        toolBar.setRollover(true);

        toolBarBtnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/new.png"))); // NOI18N
        toolBarBtnNew.setMnemonic('n');
        toolBarBtnNew.setFocusable(false);
        toolBarBtnNew.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        toolBarBtnNew.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBarBtnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventFile(evt);
            }
        });
        toolBar.add(toolBarBtnNew);
        toolBarBtnNew.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(VK_N, CTRL_DOWN_MASK),"en");
        toolBarBtnNew.getActionMap().put("en",new AbstractAction("doSomething") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                eventFile(evt);
            }
        });

        toolBarBtnOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/open.png"))); // NOI18N
        toolBarBtnOpen.setFocusable(false);
        toolBarBtnOpen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        toolBarBtnOpen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBarBtnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventFile(evt);
            }
        });
        toolBar.add(toolBarBtnOpen);

        toolBarBtnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/save.png"))); // NOI18N
        toolBarBtnSave.setFocusable(false);
        toolBarBtnSave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        toolBarBtnSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBarBtnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventFile(evt);
            }
        });
        toolBar.add(toolBarBtnSave);
        toolBar.add(jSeparator1);

        toolBarBtnUndo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/undo.png"))); // NOI18N
        toolBarBtnUndo.setMnemonic('z');
        toolBarBtnUndo.setEnabled(false);
        toolBarBtnUndo.setFocusable(false);
        toolBarBtnUndo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        toolBarBtnUndo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBarBtnUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventUndoRedo(evt);
            }
        });
        toolBar.add(toolBarBtnUndo);
        toolBarBtnUndo.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, CTRL_DOWN_MASK),"en");
        toolBarBtnUndo.getActionMap().put("en",new AbstractAction("doSomething") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                eventUndoRedo(evt);
            }
        });

        toolBarBtnRedo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/redo.png"))); // NOI18N
        toolBarBtnRedo.setEnabled(false);
        toolBarBtnRedo.setFocusable(false);
        toolBarBtnRedo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        toolBarBtnRedo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBarBtnRedo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventUndoRedo(evt);
            }
        });
        toolBar.add(toolBarBtnRedo);
        toolBarBtnRedo.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, CTRL_DOWN_MASK),"en");
        toolBarBtnRedo.getActionMap().put("en",new AbstractAction("doSomething") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                eventUndoRedo(evt);
            }
        });
        toolBar.add(jSeparator2);

        toolBarBtnCut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/cut.png"))); // NOI18N
        toolBarBtnCut.setFocusable(false);
        toolBarBtnCut.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        toolBarBtnCut.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBarBtnCut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventCopyPaste(evt);
            }
        });
        toolBar.add(toolBarBtnCut);
        toolBarBtnCut.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_X, CTRL_DOWN_MASK),"en");
        toolBarBtnCut.getActionMap().put("en",new AbstractAction("doSomething") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                eventCopyPaste(evt);
            }
        });

        toolBarBtnCopy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/copy.png"))); // NOI18N
        toolBarBtnCopy.setFocusable(false);
        toolBarBtnCopy.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        toolBarBtnCopy.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBarBtnCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventCopyPaste(evt);
            }
        });
        toolBar.add(toolBarBtnCopy);
        toolBarBtnCopy.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_C, CTRL_DOWN_MASK),"en");
        toolBarBtnCopy.getActionMap().put("en",new AbstractAction("doSomething") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                eventCopyPaste(evt);
            }
        });

        toolBarBtnPaste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/paste.png"))); // NOI18N
        toolBarBtnPaste.setEnabled(false);
        toolBarBtnPaste.setFocusable(false);
        toolBarBtnPaste.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        toolBarBtnPaste.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBarBtnPaste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventCopyPaste(evt);
            }
        });
        toolBar.add(toolBarBtnPaste);
        toolBarBtnPaste.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_V, CTRL_DOWN_MASK),"en");
        toolBarBtnPaste.getActionMap().put("en",new AbstractAction("doSomething") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                eventCopyPaste(evt);
            }
        });
        toolBar.add(jSeparator3);

        toolBarBtnTgGrid.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/grid.png"))); // NOI18N
        toolBarBtnTgGrid.setFocusable(false);
        toolBarBtnTgGrid.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        toolBarBtnTgGrid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventGrid(evt);
            }
        });
        toolBar.add(toolBarBtnTgGrid);

        toolBarBtnTgOxy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/Oxy.png"))); // NOI18N
        toolBarBtnTgOxy.setFocusable(false);
        toolBarBtnTgOxy.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        toolBarBtnTgOxy.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBarBtnTgOxy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventGrid(evt);
            }
        });
        toolBar.add(toolBarBtnTgOxy);

        toolBarBtnRuler.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/ruler.png"))); // NOI18N
        toolBarBtnRuler.setFocusable(false);
        toolBarBtnRuler.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        toolBarBtnRuler.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBarBtnRuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventRuler(evt);
            }
        });
        toolBar.add(toolBarBtnRuler);
        toolBar.add(WPControlSp);

        WPControlBtnFlipOx.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/flipOx.png"))); // NOI18N
        WPControlBtnFlipOx.setToolTipText("Flip Horizontal");
        WPControlBtnFlipOx.setFocusable(false);
        WPControlBtnFlipOx.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        WPControlBtnFlipOx.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        WPControlBtnFlipOx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventActionToolBarWrap(evt);
            }
        });
        toolBar.add(WPControlBtnFlipOx);

        WPControlBtnFlipOy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/flipOy.png"))); // NOI18N
        WPControlBtnFlipOy.setToolTipText("Flip Vertical");
        WPControlBtnFlipOy.setFocusable(false);
        WPControlBtnFlipOy.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        WPControlBtnFlipOy.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        WPControlBtnFlipOy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventActionToolBarWrap(evt);
            }
        });
        toolBar.add(WPControlBtnFlipOy);

        WPControlBtnFlipPoint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/flipPoint.png"))); // NOI18N
        WPControlBtnFlipPoint.setToolTipText("Flip Point");
        WPControlBtnFlipPoint.setFocusable(false);
        WPControlBtnFlipPoint.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        WPControlBtnFlipPoint.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        WPControlBtnFlipPoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventActionToolBarWrap(evt);
            }
        });
        toolBar.add(WPControlBtnFlipPoint);

        WPControlBtnFlipLine.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/flipD.png"))); // NOI18N
        WPControlBtnFlipLine.setToolTipText("Flip Line");
        WPControlBtnFlipLine.setFocusable(false);
        WPControlBtnFlipLine.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        WPControlBtnFlipLine.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        WPControlBtnFlipLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventActionToolBarWrap(evt);
            }
        });
        toolBar.add(WPControlBtnFlipLine);

        WPControlBtnRotateRight.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/rotateRight.png"))); // NOI18N
        WPControlBtnRotateRight.setToolTipText("Rotate Right");
        WPControlBtnRotateRight.setFocusable(false);
        WPControlBtnRotateRight.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        WPControlBtnRotateRight.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        WPControlBtnRotateRight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventActionToolBarWrap(evt);
            }
        });
        toolBar.add(WPControlBtnRotateRight);

        WPControlBtnRotateLeft.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/rotateLeft.png"))); // NOI18N
        WPControlBtnRotateLeft.setToolTipText("Rotate Left");
        WPControlBtnRotateLeft.setFocusable(false);
        WPControlBtnRotateLeft.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        WPControlBtnRotateLeft.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        WPControlBtnRotateLeft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventActionToolBarWrap(evt);
            }
        });
        toolBar.add(WPControlBtnRotateLeft);
        toolBar.add(WPControlSp0);

        WPControlMoveUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/moveShapeUp.png"))); // NOI18N
        WPControlMoveUp.setToolTipText("Move Shape Up");
        WPControlMoveUp.setEnabled(false);
        WPControlMoveUp.setFocusable(false);
        WPControlMoveUp.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        WPControlMoveUp.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        WPControlMoveUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventActionToolBarWrap(evt);
            }
        });
        toolBar.add(WPControlMoveUp);

        WPControlMoveDown.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/moveShapeDown.png"))); // NOI18N
        WPControlMoveDown.setToolTipText("Move Shape Down");
        WPControlMoveDown.setEnabled(false);
        WPControlMoveDown.setFocusable(false);
        WPControlMoveDown.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        WPControlMoveDown.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        WPControlMoveDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventActionToolBarWrap(evt);
            }
        });
        toolBar.add(WPControlMoveDown);

        WPControlNormalize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/normalize.png"))); // NOI18N
        WPControlNormalize.setToolTipText("Convert To Pixel");
        WPControlNormalize.setFocusable(false);
        WPControlNormalize.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        WPControlNormalize.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        WPControlNormalize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventActionToolBarWrap(evt);
            }
        });
        toolBar.add(WPControlNormalize);

        WPControlBtnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/removeShape.png"))); // NOI18N
        WPControlBtnRemove.setToolTipText("Remove Shape");
        WPControlBtnRemove.setFocusable(false);
        WPControlBtnRemove.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        WPControlBtnRemove.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        WPControlBtnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventActionToolBarWrap(evt);
            }
        });
        toolBar.add(WPControlBtnRemove);
        WPControlBtnRemove.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE ,0),"en");
        WPControlBtnRemove.getActionMap().put("en",new AbstractAction("doSomething") {
            @Override
            public void actionPerformed(ActionEvent e) {
                eventActionToolBarWrap(e);
            }
        });
        toolBar.add(WPControl3DSp1);

        WPControlBtnMove3D.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/move3D.png"))); // NOI18N
        WPControlBtnMove3D.setFocusable(false);
        WPControlBtnMove3D.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        WPControlBtnMove3D.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        WPControlBtnMove3D.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventActionWP3D(evt);
            }
        });
        toolBar.add(WPControlBtnMove3D);

        WPControlBtnRotate3D.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/rotate3D.png"))); // NOI18N
        WPControlBtnRotate3D.setFocusable(false);
        WPControlBtnRotate3D.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        WPControlBtnRotate3D.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        WPControlBtnRotate3D.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventActionWP3D(evt);
            }
        });
        toolBar.add(WPControlBtnRotate3D);

        WPControlBtnFlip3D.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/flipD.png"))); // NOI18N
        WPControlBtnFlip3D.setFocusable(false);
        WPControlBtnFlip3D.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        WPControlBtnFlip3D.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        WPControlBtnFlip3D.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventActionWP3D(evt);
            }
        });
        toolBar.add(WPControlBtnFlip3D);

        WPControl3DFlipOxyz.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/flipPoint.png"))); // NOI18N
        WPControl3DFlipOxyz.setFocusable(false);
        WPControl3DFlipOxyz.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        WPControl3DFlipOxyz.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        WPControl3DFlipOxyz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventActionWP3D(evt);
            }
        });
        toolBar.add(WPControl3DFlipOxyz);
        toolBar.add(WPControl3DSp2);

        WPControl3DRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/removeShape.png"))); // NOI18N
        WPControl3DRemove.setToolTipText("Remove Shape");
        WPControl3DRemove.setFocusable(false);
        WPControl3DRemove.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        WPControl3DRemove.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        WPControl3DRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventActionWP3D(evt);
            }
        });
        toolBar.add(WPControl3DRemove);

        deskPane.setBackground(new java.awt.Color(153, 153, 153));
        deskPane.setForeground(new java.awt.Color(153, 153, 153));

        panelToolbox.setBackground(new java.awt.Color(204, 204, 204));
        panelToolbox.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnPencil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/pencil.png"))); // NOI18N
        btnPencil.setToolTipText("Pencil");
        btnPencil.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPencil.setRequestFocusEnabled(false);
        btnPencil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventChangeTool(evt);
            }
        });

        btnBrush.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/brush.png"))); // NOI18N
        btnBrush.setToolTipText("Brush");
        btnBrush.setAlignmentY(0.0F);
        btnBrush.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBrush.setRequestFocusEnabled(false);
        btnBrush.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventChangeTool(evt);
            }
        });

        btnPicker.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/picker.png"))); // NOI18N
        btnPicker.setToolTipText("Color Picker");
        btnPicker.setAlignmentY(0.0F);
        btnPicker.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPicker.setRequestFocusEnabled(false);
        btnPicker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventChangeTool(evt);
            }
        });

        btnFill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/fill.png"))); // NOI18N
        btnFill.setToolTipText("Fill");
        btnFill.setAlignmentY(0.0F);
        btnFill.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFill.setRequestFocusEnabled(false);
        btnFill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventChangeTool(evt);
            }
        });

        btnSelector.setBackground(new java.awt.Color(204, 204, 204));
        btnSelector.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/mouse.png"))); // NOI18N
        btnSelector.setToolTipText("Selector");
        btnSelector.setAlignmentY(0.0F);
        btnSelector.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSelector.setRequestFocusEnabled(false);
        btnSelector.setRolloverEnabled(false);
        btnSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventChangeTool(evt);
            }
        });

        btnSelection.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/selection.png"))); // NOI18N
        btnSelection.setToolTipText("Rectangle Select");
        btnSelection.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSelection.setRequestFocusEnabled(false);
        btnSelection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventChangeTool(evt);
            }
        });

        btnLasso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/lasso.png"))); // NOI18N
        btnLasso.setToolTipText("Lasso Select");
        btnLasso.setAlignmentY(0.0F);
        btnLasso.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLasso.setRequestFocusEnabled(false);
        btnLasso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventChangeTool(evt);
            }
        });

        btnEraser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/eraser.png"))); // NOI18N
        btnEraser.setToolTipText("Eraser");
        btnEraser.setAlignmentY(0.0F);
        btnEraser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEraser.setRequestFocusEnabled(false);
        btnEraser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventChangeTool(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(102, 102, 102));
        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Tools");
        jLabel3.setOpaque(true);

        btnRect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/square.png"))); // NOI18N
        btnRect.setToolTipText("Rectangle");
        btnRect.setAlignmentY(0.0F);
        btnRect.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRect.setRequestFocusEnabled(false);
        btnRect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventChangeTool(evt);
            }
        });

        btnTriangle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/triangle.png"))); // NOI18N
        btnTriangle.setToolTipText("Triangle");
        btnTriangle.setAlignmentY(0.0F);
        btnTriangle.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTriangle.setRequestFocusEnabled(false);
        btnTriangle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventChangeTool(evt);
            }
        });

        btnTrapezoid.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/trapezoid.png"))); // NOI18N
        btnTrapezoid.setToolTipText("Trapezoid");
        btnTrapezoid.setAlignmentY(0.0F);
        btnTrapezoid.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTrapezoid.setRequestFocusEnabled(false);
        btnTrapezoid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventChangeTool(evt);
            }
        });

        btnPara.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/parallelogram.png"))); // NOI18N
        btnPara.setToolTipText("Parallelogram");
        btnPara.setAlignmentY(0.0F);
        btnPara.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPara.setRequestFocusEnabled(false);
        btnPara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventChangeTool(evt);
            }
        });

        btnPoly.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/polygon.png"))); // NOI18N
        btnPoly.setToolTipText("Polygons");
        btnPoly.setAlignmentY(0.0F);
        btnPoly.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPoly.setRequestFocusEnabled(false);
        btnPoly.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventChangeTool(evt);
            }
        });

        btnWand.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/wand.png"))); // NOI18N
        btnWand.setToolTipText("Wand");
        btnWand.setAlignmentY(0.0F);
        btnWand.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnWand.setRequestFocusEnabled(false);
        btnWand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventChangeTool(evt);
            }
        });

        btnLine.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/line.png"))); // NOI18N
        btnLine.setToolTipText("Trapezoid");
        btnLine.setAlignmentY(0.0F);
        btnLine.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLine.setRequestFocusEnabled(false);
        btnLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventChangeTool(evt);
            }
        });

        btnDiamond.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/diamond.png"))); // NOI18N
        btnDiamond.setToolTipText("Diamond");
        btnDiamond.setAlignmentY(0.0F);
        btnDiamond.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDiamond.setRequestFocusEnabled(false);
        btnDiamond.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventChangeTool(evt);
            }
        });

        btnElip.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/circle.png"))); // NOI18N
        btnElip.setToolTipText("Ellipse");
        btnElip.setAlignmentY(0.0F);
        btnElip.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnElip.setName(""); // NOI18N
        btnElip.setRequestFocusEnabled(false);
        btnElip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventChangeTool(evt);
            }
        });

        btnSwitchShape.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/3D.png"))); // NOI18N
        btnSwitchShape.setMnemonic('z');
        btnSwitchShape.setToolTipText("Polygon");
        btnSwitchShape.setAlignmentY(0.0F);
        btnSwitchShape.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSwitchShape.setRequestFocusEnabled(false);
        btnSwitchShape.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSwitchShapeeventChangeTool(evt);
            }
        });

        btnBox.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/box.png"))); // NOI18N
        btnBox.setToolTipText("Box");
        btnBox.setAlignmentY(0.0F);
        btnBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBox.setName("box"); // NOI18N
        btnBox.setRequestFocusEnabled(false);
        btnBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventChangeTool(evt);
            }
        });

        btnPyramid.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/pyramid.png"))); // NOI18N
        btnPyramid.setToolTipText("Pyramid");
        btnPyramid.setAlignmentY(0.0F);
        btnPyramid.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPyramid.setName("pyramid"); // NOI18N
        btnPyramid.setRequestFocusEnabled(false);
        btnPyramid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventChangeTool(evt);
            }
        });

        btnTetrahedron.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/shape/Tetrahedron.png"))); // NOI18N
        btnTetrahedron.setToolTipText("Tetrahedron");
        btnTetrahedron.setAlignmentY(0.0F);
        btnTetrahedron.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTetrahedron.setName("tetrahedron"); // NOI18N
        btnTetrahedron.setRequestFocusEnabled(false);
        btnTetrahedron.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventChangeTool(evt);
            }
        });

        btnTriangularPrism.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/shape/Triangular Prism.png"))); // NOI18N
        btnTriangularPrism.setToolTipText("Triangular Prism");
        btnTriangularPrism.setAlignmentY(0.0F);
        btnTriangularPrism.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTriangularPrism.setName("tprism"); // NOI18N
        btnTriangularPrism.setRequestFocusEnabled(false);
        btnTriangularPrism.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventChangeTool(evt);
            }
        });

        javax.swing.GroupLayout panelToolboxLayout = new javax.swing.GroupLayout(panelToolbox);
        panelToolbox.setLayout(panelToolboxLayout);
        panelToolboxLayout.setHorizontalGroup(
            panelToolboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelToolboxLayout.createSequentialGroup()
                .addGroup(panelToolboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEraser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnLine, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnTrapezoid, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnPara, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnPoly, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnTriangle, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnDiamond, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnElip, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnSwitchShape, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPyramid, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelToolboxLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(panelToolboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnLasso, javax.swing.GroupLayout.Alignment.TRAILING, 0, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSelection, javax.swing.GroupLayout.Alignment.TRAILING, 0, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPencil, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelToolboxLayout.createSequentialGroup()
                        .addGroup(panelToolboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelToolboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnWand, 0, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnPicker, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(btnBrush, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(btnFill, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(btnSelector, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(spTool1)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(spTool2))
                            .addComponent(btnRect, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTetrahedron, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTriangularPrism, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelToolboxLayout.setVerticalGroup(
            panelToolboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelToolboxLayout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSelector, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnLasso, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(spTool1, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnPencil, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnBrush, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnPicker, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnFill, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnEraser, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnWand, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(spTool2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(btnLine, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnRect, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnElip, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnTrapezoid, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnTriangle, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnPara, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnDiamond, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnPoly, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnBox, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnPyramid, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnTetrahedron, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnTriangularPrism, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addComponent(btnSwitchShape, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1))
        );

        panelProperties.setBackground(new java.awt.Color(204, 204, 204));
        panelProperties.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelProperties.setMaximumSize(new java.awt.Dimension(100, 300));
        panelProperties.setMinimumSize(new java.awt.Dimension(100, 0));

        panelPropertieslb1.setBackground(new java.awt.Color(102, 102, 102));
        panelPropertieslb1.setForeground(new java.awt.Color(255, 255, 255));
        panelPropertieslb1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panelPropertieslb1.setText("Properties");
        panelPropertieslb1.setOpaque(true);

        panelPropertiesScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panelPropertiesScrollPane.getVerticalScrollBar().setUnitIncrement(20);

        proLocationlb4.setBackground(new java.awt.Color(204, 204, 204));
        proLocationlb4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        proLocationlb4.setText(" Type:");
        proLocationlb4.setName("location"); // NOI18N
        proLocationlb4.setOpaque(true);

        proTypelbShapeName.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/polygon.png"))); // NOI18N
        proTypelbShapeName.setText("Polygons");

        javax.swing.GroupLayout proTypePanelLayout = new javax.swing.GroupLayout(proTypePanel);
        proTypePanel.setLayout(proTypePanelLayout);
        proTypePanelLayout.setHorizontalGroup(
            proTypePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(proLocationlb4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(proTypePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(proTypelbShapeName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        proTypePanelLayout.setVerticalGroup(
            proTypePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proTypePanelLayout.createSequentialGroup()
                .addComponent(proLocationlb4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(proTypelbShapeName)
                .addGap(7, 7, 7))
        );

        proLocationPanel.setName("location"); // NOI18N

        proLocationlb1.setBackground(new java.awt.Color(204, 204, 204));
        proLocationlb1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        proLocationlb1.setText(" Location:");
        proLocationlb1.setName("location"); // NOI18N
        proLocationlb1.setOpaque(true);

        proLocationlb2.setText("X: ");

        proLocationtxtX.setBackground(new java.awt.Color(228, 228, 228));
        proLocationtxtX.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        proLocationtxtX.setText("1000");

        proLocationlb3.setText("Y: ");

        proLocationtxtY.setBackground(new java.awt.Color(228, 228, 228));
        proLocationtxtY.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        proLocationtxtY.setText("1000");

        proLocationbtnXYReset.setText("↺");
        proLocationbtnXYReset.setMargin(new java.awt.Insets(2, 2, 2, 2));
        proLocationbtnXYReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventActionResetProperties(evt);
            }
        });

        javax.swing.GroupLayout proLocationPanelLayout = new javax.swing.GroupLayout(proLocationPanel);
        proLocationPanel.setLayout(proLocationPanelLayout);
        proLocationPanelLayout.setHorizontalGroup(
            proLocationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(proLocationlb1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(proLocationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(proLocationlb2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(proLocationtxtX, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(proLocationlb3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(proLocationtxtY, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(proLocationbtnXYReset, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        proLocationPanelLayout.setVerticalGroup(
            proLocationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proLocationPanelLayout.createSequentialGroup()
                .addComponent(proLocationlb1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(proLocationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(proLocationlb2)
                    .addComponent(proLocationtxtX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proLocationlb3)
                    .addComponent(proLocationtxtY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proLocationbtnXYReset))
                .addGap(7, 7, 7))
        );

        proLocationtxtX.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                // propertiesChange(e);
            }
            public void removeUpdate(DocumentEvent e) {
                // propertiesChange(e);
                //System.out.print("remove");
            }
            public void insertUpdate(DocumentEvent e) {
                //  propertiesChange(e);
                //  System.out.print("insert");
            }
        });

        proSizePanel.setName("size"); // NOI18N

        proSizelb1.setBackground(new java.awt.Color(204, 204, 204));
        proSizelb1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        proSizelb1.setText(" Size:");
        proSizelb1.setName("size"); // NOI18N
        proSizelb1.setOpaque(true);

        proSizelbW.setText("Width: ");

        proSizetxtW.setBackground(new java.awt.Color(228, 228, 228));
        proSizetxtW.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        proSizetxtW.setText("1000");

        proSizelbH.setText("Height: ");

        proSizetxtH.setBackground(new java.awt.Color(228, 228, 228));
        proSizetxtH.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        proSizetxtH.setText("1000");

        proSizelbR.setText("Radius: ");

        proSizetxtR.setBackground(new java.awt.Color(228, 228, 228));
        proSizetxtR.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        proSizetxtR.setText("1000");

        proSizelnR1.setText("HRadius: ");

        proSizetxtR1.setBackground(new java.awt.Color(228, 228, 228));
        proSizetxtR1.setText("1000");

        proSizelbR2.setText("VRadius:");

        proSizetxtR2.setBackground(new java.awt.Color(228, 228, 228));
        proSizetxtR2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        proSizetxtR2.setText("1000");

        javax.swing.GroupLayout proSizePanelLayout = new javax.swing.GroupLayout(proSizePanel);
        proSizePanel.setLayout(proSizePanelLayout);
        proSizePanelLayout.setHorizontalGroup(
            proSizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proSizePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(proSizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(proSizePanelLayout.createSequentialGroup()
                        .addGroup(proSizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(proSizelbW)
                            .addComponent(proSizelbR))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(proSizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(proSizePanelLayout.createSequentialGroup()
                                .addComponent(proSizetxtW, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(proSizelbH)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(proSizetxtH, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(proSizetxtR, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(proSizePanelLayout.createSequentialGroup()
                        .addComponent(proSizelnR1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proSizetxtR1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(proSizelbR2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proSizetxtR2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(proSizelb1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        proSizePanelLayout.setVerticalGroup(
            proSizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proSizePanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(proSizelb1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(proSizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(proSizelbW)
                    .addComponent(proSizetxtW, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proSizelbH)
                    .addComponent(proSizetxtH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(proSizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(proSizelbR)
                    .addComponent(proSizetxtR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(proSizePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(proSizelnR1)
                    .addComponent(proSizetxtR1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proSizelbR2)
                    .addComponent(proSizetxtR2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7))
        );

        proTransformPanel.setName("transform"); // NOI18N

        proTransformlb1.setBackground(new java.awt.Color(204, 204, 204));
        proTransformlb1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        proTransformlb1.setText(" Transform:");
        proTransformlb1.setName("transform"); // NOI18N
        proTransformlb1.setOpaque(true);

        proTransformlbRotate.setText("Rotate (At Center): ");

        proTransformtxtRotate.setBackground(new java.awt.Color(228, 228, 228));
        proTransformtxtRotate.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        proTransformtxtRotate.setText("180");

        proTransformbtnResetRotate.setText("↺");
        proTransformbtnResetRotate.setMargin(new java.awt.Insets(2, 2, 2, 2));
        proTransformbtnResetRotate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventActionResetProperties(evt);
            }
        });

        proTransformlbRotatePoint.setText("Rotate Point:");

        proTransformtxtRotatePointX.setBackground(new java.awt.Color(228, 228, 228));
        proTransformtxtRotatePointX.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        proTransformtxtRotatePointX.setText("1000");

        proTransformtxtRotatePointY.setBackground(new java.awt.Color(228, 228, 228));
        proTransformtxtRotatePointY.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        proTransformtxtRotatePointY.setText("1000");

        proTransformbtnResetRotatePoint.setText("↺");
        proTransformbtnResetRotatePoint.setMargin(new java.awt.Insets(2, 2, 2, 2));
        proTransformbtnResetRotatePoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventActionResetProperties(evt);
            }
        });

        proTransformScaling.setText("Scaling: ");

        proTransformtxtScalingX.setBackground(new java.awt.Color(228, 228, 228));
        proTransformtxtScalingX.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        proTransformtxtScalingX.setText("100");

        proTransformlb2.setText("-");

        proTransformtxtScalingY.setBackground(new java.awt.Color(228, 228, 228));
        proTransformtxtScalingY.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        proTransformtxtScalingY.setText("100");

        jLabel2.setText("-");

        javax.swing.GroupLayout proTransformPanelLayout = new javax.swing.GroupLayout(proTransformPanel);
        proTransformPanel.setLayout(proTransformPanelLayout);
        proTransformPanelLayout.setHorizontalGroup(
            proTransformPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proTransformPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(proTransformPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(proTransformPanelLayout.createSequentialGroup()
                        .addComponent(proTransformScaling)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proTransformtxtScalingX, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proTransformlb2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proTransformtxtScalingY, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(proTransformPanelLayout.createSequentialGroup()
                        .addGroup(proTransformPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(proTransformPanelLayout.createSequentialGroup()
                                .addComponent(proTransformlbRotate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(proTransformtxtRotate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(proTransformPanelLayout.createSequentialGroup()
                                .addComponent(proTransformlbRotatePoint)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(proTransformtxtRotatePointX, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(proTransformtxtRotatePointY, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(2, 2, 2)
                        .addGroup(proTransformPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(proTransformbtnResetRotatePoint, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(proTransformbtnResetRotate, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(proTransformlb1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        proTransformPanelLayout.setVerticalGroup(
            proTransformPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proTransformPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(proTransformlb1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(proTransformPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(proTransformScaling)
                    .addComponent(proTransformtxtScalingX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proTransformlb2)
                    .addComponent(proTransformtxtScalingY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(proTransformPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(proTransformlbRotate)
                    .addComponent(proTransformtxtRotate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proTransformbtnResetRotate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(proTransformPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(proTransformlbRotatePoint)
                    .addComponent(proTransformtxtRotatePointX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proTransformtxtRotatePointY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proTransformbtnResetRotatePoint)
                    .addComponent(jLabel2))
                .addGap(5, 5, 5))
        );

        proLocationlb10.setBackground(new java.awt.Color(204, 204, 204));
        proLocationlb10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        proLocationlb10.setText(" Rotate:");
        proLocationlb10.setName("location"); // NOI18N
        proLocationlb10.setOpaque(true);

        proRotate3DX.setText("Δx:");

        proRotate3DTxtX.setBackground(new java.awt.Color(228, 228, 228));
        proRotate3DTxtX.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, sliderRotateX, org.jdesktop.beansbinding.ELProperty.create("${value}"), proRotate3DTxtX, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        proRotate3DY.setText("Δy:");

        proRotate3DTxtY.setBackground(new java.awt.Color(228, 228, 228));
        proRotate3DTxtY.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, sliderRotateY, org.jdesktop.beansbinding.ELProperty.create("${value}"), proRotate3DTxtY, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        proRotate3DZ.setText("Δz:");

        proRotate3DTxtZ.setBackground(new java.awt.Color(228, 228, 228));
        proRotate3DTxtZ.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, sliderRotateZ, org.jdesktop.beansbinding.ELProperty.create("${value}"), proRotate3DTxtZ, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout proRotate3DPanelLayout = new javax.swing.GroupLayout(proRotate3DPanel);
        proRotate3DPanel.setLayout(proRotate3DPanelLayout);
        proRotate3DPanelLayout.setHorizontalGroup(
            proRotate3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(proLocationlb10, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
            .addGroup(proRotate3DPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(proRotate3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(proRotate3DX)
                    .addComponent(proRotate3DY)
                    .addComponent(proRotate3DZ))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(proRotate3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(proRotate3DTxtX, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proRotate3DTxtY, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proRotate3DTxtZ, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        proRotate3DPanelLayout.setVerticalGroup(
            proRotate3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proRotate3DPanelLayout.createSequentialGroup()
                .addComponent(proLocationlb10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(proRotate3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(proRotate3DTxtX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proRotate3DX))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(proRotate3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(proRotate3DTxtY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proRotate3DY))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(proRotate3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(proRotate3DTxtZ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proRotate3DZ))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        proColorPanel.setName("color"); // NOI18N

        proColorlb1.setBackground(new java.awt.Color(204, 204, 204));
        proColorlb1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        proColorlb1.setText(" Color:");
        proColorlb1.setName("color"); // NOI18N
        proColorlb1.setOpaque(true);

        proColorlbOutline.setText("Outline Color");

        proColorlbSolid.setText("Solid Color");

        proColorbtnReset.setText("↺");
        proColorbtnReset.setMargin(new java.awt.Insets(2, 2, 2, 2));
        proColorbtnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventActionResetProperties(evt);
            }
        });

        lbOutLineColor.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lbOutLineColor.setOpaque(true);
        lbOutLineColor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                eventChangeShapeColor(evt);
            }
        });

        lbFillColor.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lbFillColor.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lbFillColor.setOpaque(true);
        lbFillColor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                eventChangeShapeColor(evt);
            }
        });

        javax.swing.GroupLayout proColorPanelLayout = new javax.swing.GroupLayout(proColorPanel);
        proColorPanel.setLayout(proColorPanelLayout);
        proColorPanelLayout.setHorizontalGroup(
            proColorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(proColorlb1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(proColorPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(proColorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(proColorlbOutline)
                    .addComponent(proColorlbSolid))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(proColorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(proColorPanelLayout.createSequentialGroup()
                        .addComponent(lbFillColor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addComponent(proColorbtnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbOutLineColor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        proColorPanelLayout.setVerticalGroup(
            proColorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proColorPanelLayout.createSequentialGroup()
                .addComponent(proColorlb1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(proColorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(proColorPanelLayout.createSequentialGroup()
                        .addGroup(proColorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(proColorPanelLayout.createSequentialGroup()
                                .addComponent(proColorlbOutline)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(lbOutLineColor, javax.swing.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(proColorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lbFillColor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE)
                            .addComponent(proColorlbSolid, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(proColorPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(proColorbtnReset)))
                .addGap(5, 5, 5))
        );

        proPointsPanel.setName("points"); // NOI18N

        proPointslb1.setBackground(new java.awt.Color(204, 204, 204));
        proPointslb1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        proPointslb1.setText(" Points:");
        proPointslb1.setName("points"); // NOI18N
        proPointslb1.setOpaque(true);

        proPointslbP1.setText("Point 1:");

        proPointsP1X.setBackground(new java.awt.Color(228, 228, 228));
        proPointsP1X.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        proPointsP1X.setText("1000");

        proPointslb2.setText("-");

        proPointsP1Y.setBackground(new java.awt.Color(228, 228, 228));
        proPointsP1Y.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        proPointsP1Y.setText("1000");

        proPointslbP2.setText("Point 2:");

        proPointstxtP2X.setBackground(new java.awt.Color(228, 228, 228));
        proPointstxtP2X.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        proPointstxtP2X.setText("1000");

        proPointslb3.setText("-");

        proPointstxtP2Y.setBackground(new java.awt.Color(228, 228, 228));
        proPointstxtP2Y.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        proPointstxtP2Y.setText("1000");

        proPointslbP3.setText("Point 3:");

        proPointstxtP3X.setBackground(new java.awt.Color(228, 228, 228));
        proPointstxtP3X.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        proPointstxtP3X.setText("1000");

        proPointslb4.setText("-");

        proPointstxtP3Y.setBackground(new java.awt.Color(228, 228, 228));
        proPointstxtP3Y.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        proPointstxtP3Y.setText("1000");

        proPointslbP4.setText("Point 4:");

        proPointstxtP4X.setBackground(new java.awt.Color(228, 228, 228));
        proPointstxtP4X.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        proPointstxtP4X.setText("1000");

        proPointslb5.setText("-");

        proPointstxtP4Y.setBackground(new java.awt.Color(228, 228, 228));
        proPointstxtP4Y.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        proPointstxtP4Y.setText("1000");

        javax.swing.GroupLayout proPointsPanelLayout = new javax.swing.GroupLayout(proPointsPanel);
        proPointsPanel.setLayout(proPointsPanelLayout);
        proPointsPanelLayout.setHorizontalGroup(
            proPointsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(proPointslb1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(proPointsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(proPointsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(proPointsPanelLayout.createSequentialGroup()
                        .addComponent(proPointslbP1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(proPointsP1X, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPointslb2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPointsP1Y, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(proPointsPanelLayout.createSequentialGroup()
                        .addComponent(proPointslbP2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(proPointstxtP2X, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPointslb3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPointstxtP2Y, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(proPointsPanelLayout.createSequentialGroup()
                        .addComponent(proPointslbP3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(proPointstxtP3X, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPointslb4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPointstxtP3Y, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(proPointsPanelLayout.createSequentialGroup()
                        .addComponent(proPointslbP4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(proPointstxtP4X, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPointslb5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPointstxtP4Y, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        proPointsPanelLayout.setVerticalGroup(
            proPointsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proPointsPanelLayout.createSequentialGroup()
                .addComponent(proPointslb1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(proPointsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(proPointslbP1)
                    .addComponent(proPointsP1X, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proPointslb2)
                    .addComponent(proPointsP1Y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(proPointsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(proPointslbP2)
                    .addComponent(proPointstxtP2X, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proPointslb3)
                    .addComponent(proPointstxtP2Y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(proPointsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(proPointslbP3)
                    .addComponent(proPointstxtP3X, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proPointslb4)
                    .addComponent(proPointstxtP3Y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(proPointsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(proPointslbP4)
                    .addComponent(proPointstxtP4X, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proPointslb5)
                    .addComponent(proPointstxtP4Y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );

        proLocationlb11.setBackground(new java.awt.Color(204, 204, 204));
        proLocationlb11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        proLocationlb11.setText(" Points:");
        proLocationlb11.setName("location"); // NOI18N
        proLocationlb11.setOpaque(true);

        jLabel19.setText("Point 1: ");

        proPoints3DP1X.setBackground(new java.awt.Color(228, 228, 228));
        proPoints3DP1X.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        proPoints3DP1X.setText("1000");
        proPoints3DP1X.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                proPoints3DP1XActionPerformed(evt);
            }
        });

        proPointslb6.setText("-");

        proPoints3DP1Y.setBackground(new java.awt.Color(228, 228, 228));
        proPoints3DP1Y.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        proPoints3DP1Y.setText("1000");

        proPointslb7.setText("-");

        proPoints3DP1Z.setBackground(new java.awt.Color(228, 228, 228));
        proPoints3DP1Z.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        proPoints3DP1Z.setText("1000");

        jLabel20.setText("Point 2: ");

        proPoints3DP2X.setBackground(new java.awt.Color(228, 228, 228));
        proPoints3DP2X.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        proPoints3DP2X.setText("1000");

        proPointslb8.setText("-");

        proPoints3DP2Y.setBackground(new java.awt.Color(228, 228, 228));
        proPoints3DP2Y.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        proPoints3DP2Y.setText("1000");

        proPointslb9.setText("-");

        jLabel21.setText("Point 3: ");

        proPointslb10.setText("-");

        proPointslb11.setText("-");

        jLabel22.setText("Point 4: ");

        proPointslb12.setText("-");

        proPointslb13.setText("-");

        jLabel23.setText("Point 5: ");

        proPointslb14.setText("-");

        proPointslb15.setText("-");

        jLabel24.setText("Point 6: ");

        proPointslb16.setText("-");

        proPointslb17.setText("-");

        jLabel25.setText("Point 7: ");

        proPointslb18.setText("-");

        proPointslb19.setText("-");

        jLabel26.setText("Point 8: ");

        proPointslb20.setText("-");

        proPointslb21.setText("-");

        proPoints3DP2Z.setBackground(new java.awt.Color(228, 228, 228));
        proPoints3DP2Z.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        proPoints3DP3X.setBackground(new java.awt.Color(228, 228, 228));
        proPoints3DP3X.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        proPoints3DP3Y.setBackground(new java.awt.Color(228, 228, 228));
        proPoints3DP3Y.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        proPoints3DP3Z.setBackground(new java.awt.Color(228, 228, 228));
        proPoints3DP3Z.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        proPoints3DP4X.setBackground(new java.awt.Color(228, 228, 228));
        proPoints3DP4X.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        proPoints3DP4Y.setBackground(new java.awt.Color(228, 228, 228));
        proPoints3DP4Y.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        proPoints3DP4Z.setBackground(new java.awt.Color(228, 228, 228));
        proPoints3DP4Z.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        proPoints3DP5X.setBackground(new java.awt.Color(228, 228, 228));
        proPoints3DP5X.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        proPoints3DP5Y.setBackground(new java.awt.Color(228, 228, 228));
        proPoints3DP5Y.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        proPoints3DP5Z.setBackground(new java.awt.Color(228, 228, 228));
        proPoints3DP5Z.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        proPoints3DP6X.setBackground(new java.awt.Color(228, 228, 228));
        proPoints3DP6X.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        proPoints3DP6Y.setBackground(new java.awt.Color(228, 228, 228));
        proPoints3DP6Y.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        proPoints3DP6Z.setBackground(new java.awt.Color(228, 228, 228));
        proPoints3DP6Z.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        proPoints3DP7X.setBackground(new java.awt.Color(228, 228, 228));
        proPoints3DP7X.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        proPoints3DP7Y.setBackground(new java.awt.Color(228, 228, 228));
        proPoints3DP7Y.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        proPoints3DP7Z.setBackground(new java.awt.Color(228, 228, 228));
        proPoints3DP7Z.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        proPoints3DP7Z.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                proPoints3DP7ZActionPerformed(evt);
            }
        });

        proPoints3DP8X.setBackground(new java.awt.Color(228, 228, 228));
        proPoints3DP8X.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        proPoints3DP8Y.setBackground(new java.awt.Color(228, 228, 228));
        proPoints3DP8Y.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        proPoints3DP8Z.setBackground(new java.awt.Color(228, 228, 228));
        proPoints3DP8Z.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout proPoints3DPanelLayout = new javax.swing.GroupLayout(proPoints3DPanel);
        proPoints3DPanel.setLayout(proPoints3DPanelLayout);
        proPoints3DPanelLayout.setHorizontalGroup(
            proPoints3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(proLocationlb11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(proPoints3DPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(proPoints3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(proPoints3DPanelLayout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPoints3DP8X, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPointslb20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPoints3DP8Y, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPointslb21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPoints3DP8Z, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(proPoints3DPanelLayout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPoints3DP4X, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPointslb12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPoints3DP4Y, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPointslb13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPoints3DP4Z, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(proPoints3DPanelLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPoints3DP1X, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPointslb6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPoints3DP1Y, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPointslb7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPoints3DP1Z, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(proPoints3DPanelLayout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPoints3DP2X, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPointslb8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPoints3DP2Y, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPointslb9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPoints3DP2Z, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(proPoints3DPanelLayout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPoints3DP3X, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPointslb10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPoints3DP3Y, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPointslb11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPoints3DP3Z, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(proPoints3DPanelLayout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPoints3DP5X, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPointslb14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPoints3DP5Y, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPointslb15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPoints3DP5Z, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(proPoints3DPanelLayout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPoints3DP6X, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPointslb16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPoints3DP6Y, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPointslb17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPoints3DP6Z, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(proPoints3DPanelLayout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPoints3DP7X, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPointslb18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPoints3DP7Y, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPointslb19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proPoints3DP7Z, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        proPoints3DPanelLayout.setVerticalGroup(
            proPoints3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proPoints3DPanelLayout.createSequentialGroup()
                .addComponent(proLocationlb11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(proPoints3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(proPoints3DP1X, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proPointslb6)
                    .addComponent(proPoints3DP1Y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proPointslb7)
                    .addComponent(proPoints3DP1Z, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(proPoints3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(proPoints3DP2X, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proPointslb8)
                    .addComponent(proPoints3DP2Y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proPointslb9)
                    .addComponent(proPoints3DP2Z, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(proPoints3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(proPointslb10)
                    .addComponent(proPointslb11)
                    .addComponent(proPoints3DP3X, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proPoints3DP3Y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proPoints3DP3Z, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(proPoints3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(proPointslb12)
                    .addComponent(proPointslb13)
                    .addComponent(proPoints3DP4X, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proPoints3DP4Y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proPoints3DP4Z, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(proPoints3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(proPointslb14)
                    .addComponent(proPointslb15)
                    .addComponent(proPoints3DP5X, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proPoints3DP5Y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proPoints3DP5Z, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(proPoints3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(proPointslb16)
                    .addComponent(proPointslb17)
                    .addComponent(proPoints3DP6X, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proPoints3DP6Y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proPoints3DP6Z, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(proPoints3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(proPointslb18)
                    .addComponent(proPointslb19)
                    .addComponent(proPoints3DP7X, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proPoints3DP7Y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proPoints3DP7Z, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(proPoints3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(proPointslb20)
                    .addComponent(proPointslb21)
                    .addComponent(proPoints3DP8X, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proPoints3DP8Y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proPoints3DP8Z, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        proLocationlb8.setBackground(new java.awt.Color(204, 204, 204));
        proLocationlb8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        proLocationlb8.setText(" Location:");
        proLocationlb8.setName("location"); // NOI18N
        proLocationlb8.setOpaque(true);

        proLocationLbX.setText("X:");

        proLocationTxtX.setBackground(new java.awt.Color(228, 228, 228));
        proLocationTxtX.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        proLocationLbY.setText("Y:");

        proLocationTxtY.setBackground(new java.awt.Color(228, 228, 228));
        proLocationTxtY.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        proLocationLbZ.setText("Z:");

        proLocationTxtZ.setBackground(new java.awt.Color(228, 228, 228));
        proLocationTxtZ.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout proLocation3DPanelLayout = new javax.swing.GroupLayout(proLocation3DPanel);
        proLocation3DPanel.setLayout(proLocation3DPanelLayout);
        proLocation3DPanelLayout.setHorizontalGroup(
            proLocation3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(proLocationlb8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(proLocation3DPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(proLocationLbX)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(proLocationTxtX, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(proLocationLbY)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(proLocationTxtY, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(proLocationLbZ)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(proLocationTxtZ, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        proLocation3DPanelLayout.setVerticalGroup(
            proLocation3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proLocation3DPanelLayout.createSequentialGroup()
                .addComponent(proLocationlb8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(proLocation3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(proLocationLbX)
                    .addComponent(proLocationTxtX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proLocationLbY)
                    .addComponent(proLocationTxtY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proLocationLbZ)
                    .addComponent(proLocationTxtZ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7))
        );

        proLocationtxtX.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                // propertiesChange(e);
            }
            public void removeUpdate(DocumentEvent e) {
                // propertiesChange(e);
                //System.out.print("remove");
            }
            public void insertUpdate(DocumentEvent e) {
                //  propertiesChange(e);
                //  System.out.print("insert");
            }
        });
        proLocationtxtX.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                // propertiesChange(e);
            }
            public void removeUpdate(DocumentEvent e) {
                // propertiesChange(e);
                //System.out.print("remove");
            }
            public void insertUpdate(DocumentEvent e) {
                //  propertiesChange(e);
                //  System.out.print("insert");
            }
        });
        proLocationtxtX.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                // propertiesChange(e);
            }
            public void removeUpdate(DocumentEvent e) {
                // propertiesChange(e);
                //System.out.print("remove");
            }
            public void insertUpdate(DocumentEvent e) {
                //  propertiesChange(e);
                //  System.out.print("insert");
            }
        });

        proLocationlb9.setBackground(new java.awt.Color(204, 204, 204));
        proLocationlb9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        proLocationlb9.setText(" Size:");
        proLocationlb9.setName("location"); // NOI18N
        proLocationlb9.setOpaque(true);

        proSizeLbLength.setText("Lenght:");

        proSize3DTxtL.setBackground(new java.awt.Color(228, 228, 228));
        proSize3DTxtL.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        proSize3DTxtL.setText("1000");

        proSizeLbWidth.setText("Width:");

        proSize3DTxtW.setBackground(new java.awt.Color(228, 228, 228));
        proSize3DTxtW.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        proSize3DTxtW.setText("1000");
        proSize3DTxtW.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventChangeProperties(evt);
            }
        });

        proSizeLbHeight.setText("Height:");

        proSize3DTxtH.setBackground(new java.awt.Color(228, 228, 228));
        proSize3DTxtH.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        proSize3DTxtH.setText("1000");

        javax.swing.GroupLayout proSize3DPanelLayout = new javax.swing.GroupLayout(proSize3DPanel);
        proSize3DPanel.setLayout(proSize3DPanelLayout);
        proSize3DPanelLayout.setHorizontalGroup(
            proSize3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(proLocationlb9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(proSize3DPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(proSize3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(proSize3DPanelLayout.createSequentialGroup()
                        .addGroup(proSize3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(proSizeLbLength)
                            .addComponent(proSizeLbWidth))
                        .addGap(22, 22, 22)
                        .addGroup(proSize3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(proSize3DTxtH, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(proSize3DTxtW, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(proSize3DTxtL, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(proSizeLbHeight))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        proSize3DPanelLayout.setVerticalGroup(
            proSize3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proSize3DPanelLayout.createSequentialGroup()
                .addComponent(proLocationlb9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(proSize3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(proSizeLbLength)
                    .addComponent(proSize3DTxtL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(proSize3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(proSizeLbWidth)
                    .addComponent(proSize3DTxtW, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(proSize3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(proSizeLbHeight)
                    .addComponent(proSize3DTxtH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        proLocationtxtX.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                // propertiesChange(e);
            }
            public void removeUpdate(DocumentEvent e) {
                // propertiesChange(e);
                //System.out.print("remove");
            }
            public void insertUpdate(DocumentEvent e) {
                //  propertiesChange(e);
                //  System.out.print("insert");
            }
        });
        proLocationtxtX.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                // propertiesChange(e);
            }
            public void removeUpdate(DocumentEvent e) {
                // propertiesChange(e);
                //System.out.print("remove");
            }
            public void insertUpdate(DocumentEvent e) {
                //  propertiesChange(e);
                //  System.out.print("insert");
            }
        });
        proLocationtxtX.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                // propertiesChange(e);
            }
            public void removeUpdate(DocumentEvent e) {
                // propertiesChange(e);
                //System.out.print("remove");
            }
            public void insertUpdate(DocumentEvent e) {
                //  propertiesChange(e);
                //  System.out.print("insert");
            }
        });

        javax.swing.GroupLayout panelPropertiesViewPortLayout = new javax.swing.GroupLayout(panelPropertiesViewPort);
        panelPropertiesViewPort.setLayout(panelPropertiesViewPortLayout);
        panelPropertiesViewPortLayout.setHorizontalGroup(
            panelPropertiesViewPortLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPropertiesViewPortLayout.createSequentialGroup()
                .addGroup(panelPropertiesViewPortLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(proSize3DPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(proLocation3DPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(proRotate3DPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proTransformPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(proPoints3DPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(proSizePanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(proLocationPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(proTypePanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(proPointsPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(proColorPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(119, Short.MAX_VALUE))
        );
        panelPropertiesViewPortLayout.setVerticalGroup(
            panelPropertiesViewPortLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPropertiesViewPortLayout.createSequentialGroup()
                .addComponent(proTypePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(proLocationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(proLocation3DPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(proSizePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(proSize3DPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(proTransformPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(proRotate3DPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(proColorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(proPointsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(proPoints3DPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        panelPropertiesScrollPane.setViewportView(panelPropertiesViewPort);

        javax.swing.GroupLayout panelPropertiesLayout = new javax.swing.GroupLayout(panelProperties);
        panelProperties.setLayout(panelPropertiesLayout);
        panelPropertiesLayout.setHorizontalGroup(
            panelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPropertieslb1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelPropertiesLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(panelPropertiesScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelPropertiesLayout.setVerticalGroup(
            panelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPropertiesLayout.createSequentialGroup()
                .addComponent(panelPropertieslb1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelPropertiesScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );

        scrollPanel.setBackground(new java.awt.Color(204, 204, 204));
        scrollPanel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                eventChangeSizeWrap(evt);
            }
        });

        viewPort.setBackground(new java.awt.Color(102, 102, 102));
        viewPort.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                eventCursorDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                eventCursorMoved(evt);
            }
        });
        viewPort.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eventCursorClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                eventMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                eventCursorPressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                eventCursorReleased(evt);
            }
        });

        javax.swing.GroupLayout viewPortLayout = new javax.swing.GroupLayout(viewPort);
        viewPort.setLayout(viewPortLayout);
        viewPortLayout.setHorizontalGroup(
            viewPortLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1295, Short.MAX_VALUE)
        );
        viewPortLayout.setVerticalGroup(
            viewPortLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 773, Short.MAX_VALUE)
        );

        scrollPanel.setViewportView(viewPort);

        statusBar.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        slZoom.setMaximum(10);
        slZoom.setMinimum(1);
        slZoom.setValue(1);
        slZoom.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        slZoom.setValueIsAdjusting(true);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, lbImageSize, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), slZoom, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        slZoom.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                eventZoom(evt);
            }
        });
        slZoom.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                eventZoomWheel(evt);
            }
        });

        lbZoom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/zoom.png"))); // NOI18N
        lbZoom.setText("Zoom 1x");

        lbImageSize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/size.png"))); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, lbImageSize, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), lbImageSize, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        lbImagePos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/pos.png"))); // NOI18N
        lbImagePos.setEnabled(false);

        javax.swing.GroupLayout statusBarLayout = new javax.swing.GroupLayout(statusBar);
        statusBar.setLayout(statusBarLayout);
        statusBarLayout.setHorizontalGroup(
            statusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbImageSize)
                .addGap(18, 18, 18)
                .addComponent(lbImagePos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbZoom)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(slZoom, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        statusBarLayout.setVerticalGroup(
            statusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(slZoom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbZoom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbImageSize, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbImagePos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelLayers.setBackground(new java.awt.Color(204, 204, 204));
        panelLayers.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelLayers.setMaximumSize(new java.awt.Dimension(300, 150));

        jLabel1.setBackground(new java.awt.Color(102, 102, 102));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Layers");
        jLabel1.setOpaque(true);

        listLayer.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        listLayer.setModel(model);
        listLayer.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listLayer.setCellRenderer(new ListItemRenderer());
        listLayer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                eventChooseLayer(evt);
            }
        });
        jScrollPane1.setViewportView(listLayer);

        layerToolBtnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/addlayer.png"))); // NOI18N
        layerToolBtnAdd.setToolTipText("Add new layer");
        layerToolBtnAdd.setEnabled(false);
        layerToolBtnAdd.setOpaque(false);
        layerToolBtnAdd.setRequestFocusEnabled(false);
        layerToolBtnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventLayerTool(evt);
            }
        });

        layerToolBtnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/removelayer.png"))); // NOI18N
        layerToolBtnRemove.setToolTipText("Remove layer");
        layerToolBtnRemove.setEnabled(false);
        layerToolBtnRemove.setOpaque(false);
        layerToolBtnRemove.setRequestFocusEnabled(false);
        layerToolBtnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventLayerTool(evt);
            }
        });

        layerToolBtnMerge.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/mergedown.png"))); // NOI18N
        layerToolBtnMerge.setToolTipText("Merge down layer");
        layerToolBtnMerge.setEnabled(false);
        layerToolBtnMerge.setOpaque(false);
        layerToolBtnMerge.setRequestFocusEnabled(false);
        layerToolBtnMerge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventLayerTool(evt);
            }
        });

        layerToolBtnMoveUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/moveup.png"))); // NOI18N
        layerToolBtnMoveUp.setToolTipText("Move up");
        layerToolBtnMoveUp.setEnabled(false);
        layerToolBtnMoveUp.setOpaque(false);
        layerToolBtnMoveUp.setRequestFocusEnabled(false);
        layerToolBtnMoveUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventLayerTool(evt);
            }
        });

        layerToolBtnMoveDown.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/movedown.png"))); // NOI18N
        layerToolBtnMoveDown.setToolTipText("Move down");
        layerToolBtnMoveDown.setEnabled(false);
        layerToolBtnMoveDown.setOpaque(false);
        layerToolBtnMoveDown.setRequestFocusEnabled(false);
        layerToolBtnMoveDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventLayerTool(evt);
            }
        });

        javax.swing.GroupLayout panelLayersLayout = new javax.swing.GroupLayout(panelLayers);
        panelLayers.setLayout(panelLayersLayout);
        panelLayersLayout.setHorizontalGroup(
            panelLayersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLayersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayersLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(layerToolBtnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(layerToolBtnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(layerToolBtnMerge, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(layerToolBtnMoveUp, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(layerToolBtnMoveDown, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)))
                .addGap(10, 10, 10))
        );
        panelLayersLayout.setVerticalGroup(
            panelLayersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayersLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(layerToolBtnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(layerToolBtnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(layerToolBtnMerge, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(layerToolBtnMoveUp, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(layerToolBtnMoveDown, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7))
        );

        panelColors.setBackground(new java.awt.Color(204, 204, 204));
        panelColors.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setBackground(new java.awt.Color(102, 102, 102));
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Colors");
        jLabel4.setOpaque(true);

        lbPriColor.setBackground(new java.awt.Color(0, 0, 0));
        lbPriColor.setForeground(new java.awt.Color(51, 51, 255));
        lbPriColor.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        lbPriColor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbPriColor.setOpaque(true);
        lbPriColor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                eventChangeColorMode(evt);
            }
        });

        lbSecColor.setBackground(new java.awt.Color(255, 255, 255));
        lbSecColor.setForeground(new java.awt.Color(51, 51, 255));
        lbSecColor.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lbSecColor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbSecColor.setOpaque(true);
        lbSecColor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                eventChangeColorMode(evt);
            }
        });

        lbCurColor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbCurColor.setText("Primary Color");

        lbColor1.setBackground(new java.awt.Color(0, 0, 0));
        lbColor1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lbColor1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbColor1.setOpaque(true);
        lbColor1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                eventChangeColor(evt);
            }
        });

        lbColor2.setBackground(new java.awt.Color(102, 102, 102));
        lbColor2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lbColor2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbColor2.setOpaque(true);
        lbColor2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                eventChangeColor(evt);
            }
        });

        lbColor3.setBackground(new java.awt.Color(88, 57, 53));
        lbColor3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lbColor3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbColor3.setOpaque(true);
        lbColor3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                eventChangeColor(evt);
            }
        });

        lbColor4.setBackground(new java.awt.Color(255, 0, 0));
        lbColor4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lbColor4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbColor4.setOpaque(true);
        lbColor4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                eventChangeColor(evt);
            }
        });

        lbColor5.setBackground(new java.awt.Color(255, 102, 0));
        lbColor5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lbColor5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbColor5.setOpaque(true);
        lbColor5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                eventChangeColor(evt);
            }
        });

        lbColor6.setBackground(new java.awt.Color(255, 255, 51));
        lbColor6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lbColor6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbColor6.setOpaque(true);
        lbColor6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                eventChangeColor(evt);
            }
        });

        lbColor7.setBackground(new java.awt.Color(0, 255, 0));
        lbColor7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lbColor7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbColor7.setOpaque(true);
        lbColor7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                eventChangeColor(evt);
            }
        });

        lbColor8.setBackground(new java.awt.Color(0, 204, 204));
        lbColor8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lbColor8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbColor8.setOpaque(true);
        lbColor8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                eventChangeColor(evt);
            }
        });

        lbColor9.setBackground(new java.awt.Color(0, 51, 204));
        lbColor9.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lbColor9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbColor9.setOpaque(true);
        lbColor9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                eventChangeColor(evt);
            }
        });

        lbColor10.setBackground(new java.awt.Color(102, 0, 102));
        lbColor10.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lbColor10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbColor10.setOpaque(true);
        lbColor10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                eventChangeColor(evt);
            }
        });

        lbColor11.setBackground(new java.awt.Color(255, 255, 255));
        lbColor11.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lbColor11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbColor11.setOpaque(true);
        lbColor11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                eventChangeColor(evt);
            }
        });

        lbColor12.setBackground(new java.awt.Color(204, 204, 204));
        lbColor12.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lbColor12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbColor12.setOpaque(true);
        lbColor12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                eventChangeColor(evt);
            }
        });

        lbColor13.setBackground(new java.awt.Color(136, 88, 46));
        lbColor13.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lbColor13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbColor13.setOpaque(true);
        lbColor13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                eventChangeColor(evt);
            }
        });

        lbColor14.setBackground(new java.awt.Color(255, 102, 102));
        lbColor14.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lbColor14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbColor14.setOpaque(true);
        lbColor14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                eventChangeColor(evt);
            }
        });

        lbColor15.setBackground(new java.awt.Color(255, 204, 0));
        lbColor15.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lbColor15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbColor15.setOpaque(true);
        lbColor15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                eventChangeColor(evt);
            }
        });

        lbColor16.setBackground(new java.awt.Color(255, 255, 153));
        lbColor16.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lbColor16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbColor16.setOpaque(true);
        lbColor16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                eventChangeColor(evt);
            }
        });

        lbColor17.setBackground(new java.awt.Color(102, 255, 102));
        lbColor17.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lbColor17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbColor17.setOpaque(true);
        lbColor17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                eventChangeColor(evt);
            }
        });

        lbColor18.setBackground(new java.awt.Color(51, 255, 204));
        lbColor18.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lbColor18.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbColor18.setOpaque(true);
        lbColor18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                eventChangeColor(evt);
            }
        });

        lbColor19.setBackground(new java.awt.Color(0, 0, 255));
        lbColor19.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lbColor19.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbColor19.setOpaque(true);
        lbColor19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                eventChangeColor(evt);
            }
        });

        lbColor20.setBackground(new java.awt.Color(204, 0, 204));
        lbColor20.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lbColor20.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbColor20.setOpaque(true);
        lbColor20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                eventChangeColor(evt);
            }
        });

        slRed.setBackground(new java.awt.Color(204, 204, 204));
        slRed.setMaximum(255);
        slRed.setValue(0);
        slRed.setRequestFocusEnabled(false);
        slRed.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                eventAjustColor(evt);
            }
        });

        slGreen.setBackground(new java.awt.Color(204, 204, 204));
        slGreen.setMaximum(255);
        slGreen.setValue(0);
        slGreen.setRequestFocusEnabled(false);
        slGreen.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                eventAjustColor(evt);
            }
        });

        slBlue.setBackground(new java.awt.Color(204, 204, 204));
        slBlue.setMaximum(255);
        slBlue.setValue(0);
        slBlue.setRequestFocusEnabled(false);
        slBlue.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                eventAjustColor(evt);
            }
        });

        lbColorRed.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbColorRed.setText("0");

        lbColorGreen.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbColorGreen.setText("0");

        lbColorBlue.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbColorBlue.setText("0");

        jLabel11.setText("R");

        jLabel12.setText("G");

        jLabel13.setText("B");

        jLabel14.setText("Adjustments");

        javax.swing.GroupLayout panelColorsLayout = new javax.swing.GroupLayout(panelColors);
        panelColors.setLayout(panelColorsLayout);
        panelColorsLayout.setHorizontalGroup(
            panelColorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelColorsLayout.createSequentialGroup()
                .addGroup(panelColorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelColorsLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(panelColorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelColorsLayout.createSequentialGroup()
                                .addGap(105, 105, 105)
                                .addGroup(panelColorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel13))
                                .addGap(0, 0, 0)
                                .addGroup(panelColorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(slRed, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(slGreen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(slBlue, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelColorsLayout.createSequentialGroup()
                                .addComponent(lbCurColor, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel14))
                            .addGroup(panelColorsLayout.createSequentialGroup()
                                .addComponent(lbPriColor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbSecColor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelColorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbColorGreen, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbColorBlue, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbColorRed, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelColorsLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(panelColorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelColorsLayout.createSequentialGroup()
                                .addComponent(lbColor11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(lbColor12, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(lbColor13, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(lbColor14, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(lbColor15, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(lbColor16, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(lbColor17, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(lbColor18, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(lbColor19, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(lbColor20, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelColorsLayout.createSequentialGroup()
                                .addComponent(lbColor1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(lbColor2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(lbColor3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(lbColor4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(lbColor5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(lbColor6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(lbColor7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(lbColor8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(lbColor9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(lbColor10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(10, 10, 10))
        );
        panelColorsLayout.setVerticalGroup(
            panelColorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelColorsLayout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelColorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelColorsLayout.createSequentialGroup()
                        .addGroup(panelColorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbCurColor)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelColorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbPriColor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbSecColor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelColorsLayout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(0, 0, 0)
                                .addComponent(jLabel12)))
                        .addGap(0, 0, 0)
                        .addComponent(jLabel13))
                    .addGroup(panelColorsLayout.createSequentialGroup()
                        .addGroup(panelColorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(slRed, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbColorRed))
                        .addGap(0, 0, 0)
                        .addGroup(panelColorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbColorGreen)
                            .addComponent(slGreen, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(panelColorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(slBlue, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbColorBlue, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelColorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbColor1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbColor2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbColor3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbColor4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbColor5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbColor6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbColor7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbColor9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbColor8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbColor10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(panelColorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbColor11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbColor12, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbColor13, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbColor14, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbColor15, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbColor16, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbColor17, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbColor19, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbColor18, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbColor20, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        deskPane.setLayer(panelToolbox, javax.swing.JLayeredPane.DEFAULT_LAYER);
        deskPane.setLayer(panelProperties, javax.swing.JLayeredPane.DEFAULT_LAYER);
        deskPane.setLayer(scrollPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        deskPane.setLayer(statusBar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        deskPane.setLayer(panelLayers, javax.swing.JLayeredPane.DEFAULT_LAYER);
        deskPane.setLayer(panelColors, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout deskPaneLayout = new javax.swing.GroupLayout(deskPane);
        deskPane.setLayout(deskPaneLayout);
        deskPaneLayout.setHorizontalGroup(
            deskPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deskPaneLayout.createSequentialGroup()
                .addComponent(panelToolbox, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(scrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 816, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addGroup(deskPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelLayers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelColors, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelProperties, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addComponent(statusBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        deskPaneLayout.setVerticalGroup(
            deskPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deskPaneLayout.createSequentialGroup()
                .addGroup(deskPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, deskPaneLayout.createSequentialGroup()
                        .addComponent(panelColors, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(panelProperties, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(panelLayers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(scrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelToolbox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(statusBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        extendBar.setFloatable(false);

        lbCurTool.setBackground(new java.awt.Color(153, 153, 153));
        lbCurTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/brush.png"))); // NOI18N
        lbCurTool.setText("Brush Tool");
        lbCurTool.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        extendBar.add(lbCurTool);
        extendBar.add(spSizeBrush);

        lbSizeBrush.setText("Size: ");
        extendBar.add(lbSizeBrush);

        extendBarCbSize.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "20", "25", "30", "35", "40", "45", "50", "55", "60", "75", "80", "85", "90", "95", "100" }));
        extendBarCbSize.setFocusable(false);
        extendBarCbSize.setMaximumSize(new java.awt.Dimension(50, 50));
        extendBarCbSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventExtendBarChanged(evt);
            }
        });
        extendBar.add(extendBarCbSize);
        extendBar.add(spStyleBrush);

        lbStyleBrush.setText("Style: ");
        extendBar.add(lbStyleBrush);

        btnStyleBrushRect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/style_rect.png"))); // NOI18N
        btnStyleBrushRect.setFocusable(false);
        btnStyleBrushRect.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnStyleBrushRect.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        extendBar.add(btnStyleBrushRect);

        btnStyleBrushCircle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/style_circle.png"))); // NOI18N
        btnStyleBrushCircle.setFocusable(false);
        btnStyleBrushCircle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnStyleBrushCircle.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        extendBar.add(btnStyleBrushCircle);
        extendBar.add(spLineAlg);

        lbLineAlg.setText("Algorithm: ");
        extendBar.add(lbLineAlg);

        cbLineAlg.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DDA", "Bresenham" }));
        cbLineAlg.setFocusable(false);
        cbLineAlg.setMaximumSize(new java.awt.Dimension(100, 32767));
        extendBar.add(cbLineAlg);
        extendBar.add(extendBarSpEdge);

        extendBarLbEdge.setText("Edges: ");
        extendBar.add(extendBarLbEdge);

        extendBarCbEdge.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "3", "4", "5", "6", "7", "8", "9", "10" }));
        extendBarCbEdge.setFocusable(false);
        extendBarCbEdge.setMaximumSize(new java.awt.Dimension(40, 50));
        extendBarCbEdge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventExtendBarChanged(evt);
            }
        });
        extendBar.add(extendBarCbEdge);
        extendBar.add(extendBarSpStroke);

        extendBarLbStroke.setText("Stroke: ");
        extendBar.add(extendBarLbStroke);

        extendBarCbStroke.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "─────", "- - - - -" }));
        extendBarCbStroke.setFocusable(false);
        extendBarCbStroke.setMaximumSize(new java.awt.Dimension(70, 32767));
        extendBarCbStroke.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventExtendBarChanged(evt);
            }
        });
        extendBar.add(extendBarCbStroke);
        extendBar.add(extendBarSpTypes);

        extendBarLbTypes.setText("Types: ");
        extendBar.add(extendBarLbTypes);

        extendBarBtnTypesTri1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/triangle_1.png"))); // NOI18N
        extendBarBtnTypesTri1.setFocusable(false);
        extendBarBtnTypesTri1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        extendBarBtnTypesTri1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        extendBarBtnTypesTri1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventExtendBarChanged(evt);
            }
        });
        extendBar.add(extendBarBtnTypesTri1);

        extendBarBtnTypesTri2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/triangle_2.png"))); // NOI18N
        extendBarBtnTypesTri2.setFocusable(false);
        extendBarBtnTypesTri2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        extendBarBtnTypesTri2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        extendBarBtnTypesTri2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventExtendBarChanged(evt);
            }
        });
        extendBar.add(extendBarBtnTypesTri2);

        extendBarBtnTypesTri3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/triangle_3.png"))); // NOI18N
        extendBarBtnTypesTri3.setFocusable(false);
        extendBarBtnTypesTri3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        extendBarBtnTypesTri3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        extendBarBtnTypesTri3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventExtendBarChanged(evt);
            }
        });
        extendBar.add(extendBarBtnTypesTri3);

        extendBarBtnTypesTrap1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/trapezoid_1.png"))); // NOI18N
        extendBarBtnTypesTrap1.setFocusable(false);
        extendBarBtnTypesTrap1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        extendBarBtnTypesTrap1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        extendBarBtnTypesTrap1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventExtendBarChanged(evt);
            }
        });
        extendBar.add(extendBarBtnTypesTrap1);

        extendBarBtnTypesTrap2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/trapezoid_2.png"))); // NOI18N
        extendBarBtnTypesTrap2.setFocusable(false);
        extendBarBtnTypesTrap2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        extendBarBtnTypesTrap2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        extendBarBtnTypesTrap2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventExtendBarChanged(evt);
            }
        });
        extendBar.add(extendBarBtnTypesTrap2);

        extendBarBtnTypesTrap3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/trapezoid_3.png"))); // NOI18N
        extendBarBtnTypesTrap3.setFocusable(false);
        extendBarBtnTypesTrap3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        extendBarBtnTypesTrap3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        extendBarBtnTypesTrap3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventExtendBarChanged(evt);
            }
        });
        extendBar.add(extendBarBtnTypesTrap3);

        extendBarBtnTypesSquare1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/square_1.png"))); // NOI18N
        extendBarBtnTypesSquare1.setFocusable(false);
        extendBarBtnTypesSquare1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        extendBarBtnTypesSquare1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        extendBarBtnTypesSquare1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventExtendBarChanged(evt);
            }
        });
        extendBar.add(extendBarBtnTypesSquare1);

        extendBarBtnTypesCircle1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/circle_1.png"))); // NOI18N
        extendBarBtnTypesCircle1.setFocusable(false);
        extendBarBtnTypesCircle1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        extendBarBtnTypesCircle1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        extendBarBtnTypesCircle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventExtendBarChanged(evt);
            }
        });
        extendBar.add(extendBarBtnTypesCircle1);
        extendBar.add(extendBarSPRotate3D);

        extendBarLbRotate3D.setBackground(new java.awt.Color(204, 204, 204));
        extendBarLbRotate3D.setText("Rotate3D: ");
        extendBar.add(extendBarLbRotate3D);

        extendBarLbDX.setText("Δx:");
        extendBar.add(extendBarLbDX);

        sliderRotateX.setBackground(new java.awt.Color(51, 51, 51));
        sliderRotateX.setForeground(new java.awt.Color(0, 0, 0));
        sliderRotateX.setMaximum(180);
        sliderRotateX.setMinimum(-180);
        sliderRotateX.setValue(0);
        sliderRotateX.setFocusable(false);
        sliderRotateX.setMaximumSize(new java.awt.Dimension(120, 120));
        sliderRotateX.setOpaque(false);
        sliderRotateX.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                eventChangeRotate3D(evt);
            }
        });
        extendBar.add(sliderRotateX);

        extendBarLbDY.setText(" Δy:");
        extendBar.add(extendBarLbDY);

        sliderRotateY.setBackground(new java.awt.Color(51, 51, 51));
        sliderRotateY.setForeground(new java.awt.Color(0, 0, 0));
        sliderRotateY.setMaximum(180);
        sliderRotateY.setMinimum(-180);
        sliderRotateY.setValue(0);
        sliderRotateY.setFocusable(false);
        sliderRotateY.setMaximumSize(new java.awt.Dimension(120, 120));
        sliderRotateY.setOpaque(false);
        sliderRotateY.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                eventChangeRotate3D(evt);
            }
        });
        extendBar.add(sliderRotateY);

        extendBarLbDZ.setText(" Δz:");
        extendBar.add(extendBarLbDZ);

        sliderRotateZ.setBackground(new java.awt.Color(51, 51, 51));
        sliderRotateZ.setForeground(new java.awt.Color(0, 0, 0));
        sliderRotateZ.setMaximum(180);
        sliderRotateZ.setMinimum(-180);
        sliderRotateZ.setValue(0);
        sliderRotateZ.setFocusable(false);
        sliderRotateZ.setMaximumSize(new java.awt.Dimension(120, 120));
        sliderRotateZ.setOpaque(false);
        sliderRotateZ.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                eventChangeRotate3D(evt);
            }
        });
        extendBar.add(sliderRotateZ);
        extendBar.add(extendBarSpLocation);

        extendBarLbLocation.setBackground(new java.awt.Color(204, 204, 204));
        extendBarLbLocation.setText("Location 3D: ");
        extendBar.add(extendBarLbLocation);

        extendBarLbLocationX.setText("X:");
        extendBar.add(extendBarLbLocationX);

        sliderLocationX.setBackground(new java.awt.Color(51, 51, 51));
        sliderLocationX.setForeground(new java.awt.Color(0, 0, 0));
        sliderLocationX.setMaximum(1000);
        sliderLocationX.setMinimum(-1000);
        sliderLocationX.setMaximumSize(new java.awt.Dimension(120, 120));
        sliderLocationX.setOpaque(false);
        sliderLocationX.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                eventChangeLocation3D(evt);
            }
        });
        extendBar.add(sliderLocationX);

        extendBarLbLocationY.setText(" Y:");
        extendBar.add(extendBarLbLocationY);

        sliderLocationY.setBackground(new java.awt.Color(51, 51, 51));
        sliderLocationY.setForeground(new java.awt.Color(0, 0, 0));
        sliderLocationY.setMaximum(1000);
        sliderLocationY.setMinimum(-1000);
        sliderLocationY.setMaximumSize(new java.awt.Dimension(120, 120));
        sliderLocationY.setOpaque(false);
        sliderLocationY.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                eventChangeLocation3D(evt);
            }
        });
        extendBar.add(sliderLocationY);

        extendBarLbLocationZ.setText(" Z:");
        extendBar.add(extendBarLbLocationZ);

        sliderLocationZ.setBackground(new java.awt.Color(51, 51, 51));
        sliderLocationZ.setForeground(new java.awt.Color(0, 0, 0));
        sliderLocationZ.setMaximum(1000);
        sliderLocationZ.setMinimum(-1000);
        sliderLocationZ.setMaximumSize(new java.awt.Dimension(120, 120));
        sliderLocationZ.setOpaque(false);
        sliderLocationZ.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                eventChangeLocation3D(evt);
            }
        });
        extendBar.add(sliderLocationZ);
        extendBar.add(extendBarSpFlip);

        extendBarLbFlip3D.setBackground(new java.awt.Color(204, 204, 204));
        extendBarLbFlip3D.setText("Flip 3D: ");
        extendBar.add(extendBarLbFlip3D);

        extendBarLbFlipAxis.setText("Axis ");
        extendBar.add(extendBarLbFlipAxis);

        btnFlipOx.setBackground(new java.awt.Color(204, 204, 204));
        btnFlipOx.setForeground(new java.awt.Color(51, 51, 255));
        btnFlipOx.setText("Ox");
        btnFlipOx.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnFlipOx.setOpaque(false);
        btnFlipOx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventActionFlip3D(evt);
            }
        });
        extendBar.add(btnFlipOx);

        btnFlipOy.setBackground(new java.awt.Color(204, 204, 204));
        btnFlipOy.setForeground(new java.awt.Color(51, 51, 255));
        btnFlipOy.setText("Oy");
        btnFlipOy.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnFlipOy.setOpaque(false);
        btnFlipOy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventActionFlip3D(evt);
            }
        });
        extendBar.add(btnFlipOy);

        btnFlipOz.setBackground(new java.awt.Color(204, 204, 204));
        btnFlipOz.setForeground(new java.awt.Color(51, 51, 255));
        btnFlipOz.setText("Oz");
        btnFlipOz.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnFlipOz.setOpaque(false);
        btnFlipOz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventActionFlip3D(evt);
            }
        });
        extendBar.add(btnFlipOz);

        extendBarLbFlipPlane.setText("- Plane ");
        extendBar.add(extendBarLbFlipPlane);

        btnFlipOxy.setBackground(new java.awt.Color(204, 204, 204));
        btnFlipOxy.setForeground(new java.awt.Color(51, 51, 255));
        btnFlipOxy.setText("Oxy");
        btnFlipOxy.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnFlipOxy.setOpaque(false);
        btnFlipOxy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventActionFlip3D(evt);
            }
        });
        extendBar.add(btnFlipOxy);

        btnFlipOyz.setBackground(new java.awt.Color(204, 204, 204));
        btnFlipOyz.setForeground(new java.awt.Color(51, 51, 255));
        btnFlipOyz.setText("Oyz");
        btnFlipOyz.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnFlipOyz.setOpaque(false);
        btnFlipOyz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventActionFlip3D(evt);
            }
        });
        extendBar.add(btnFlipOyz);

        btnFlipOxz.setBackground(new java.awt.Color(204, 204, 204));
        btnFlipOxz.setForeground(new java.awt.Color(51, 51, 255));
        btnFlipOxz.setText("Oxz");
        btnFlipOxz.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnFlipOxz.setOpaque(false);
        btnFlipOxz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventActionFlip3D(evt);
            }
        });
        extendBar.add(btnFlipOxz);
        extendBar.add(extendBarSpDone);

        extendBarBtnDone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/finish.png"))); // NOI18N
        extendBarBtnDone.setText("Done");
        extendBarBtnDone.setFocusable(false);
        extendBarBtnDone.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        extendBarBtnDone.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        extendBarBtnDone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventFinishEdit(evt);
            }
        });
        extendBar.add(extendBarBtnDone);
        extendBarBtnDone.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE,CTRL_DOWN_MASK),"en");
        extendBarBtnDone.getActionMap().put("en",new AbstractAction("doSomething") {
            @Override
            public void actionPerformed(ActionEvent e) {
                eventFinishEdit(e);
            }
        });

        proChangedFinish.setFocusable(false);
        proChangedFinish.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        proChangedFinish.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        proChangedFinish.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventChangeProperties(evt);
            }
        });
        extendBar.add(proChangedFinish);
        proChangedFinish.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0),"en");
        proChangedFinish.getActionMap().put("en",new AbstractAction("doSomething") {
            @Override
            public void actionPerformed(ActionEvent e) {
                eventChangeProperties(e);
            }
        });

        menuFile.setText("File");

        menuFileNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        menuFileNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/new.png"))); // NOI18N
        menuFileNew.setText("New");
        menuFileNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventFile(evt);
            }
        });
        menuFile.add(menuFileNew);

        menuFileOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        menuFileOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/open.png"))); // NOI18N
        menuFileOpen.setText("Open");
        menuFileOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventFile(evt);
            }
        });
        menuFile.add(menuFileOpen);
        menuFile.add(jSeparator5);

        menuFileSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        menuFileSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/save.png"))); // NOI18N
        menuFileSave.setText("Save");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, toolBarBtnSave, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), menuFileSave, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        menuFileSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventFile(evt);
            }
        });
        menuFile.add(menuFileSave);

        menuFileSaveAs.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuFileSaveAs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/menu/saveAs.png"))); // NOI18N
        menuFileSaveAs.setText("Save As ...");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, toolBarBtnSave, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), menuFileSaveAs, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        menuFileSaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventFile(evt);
            }
        });
        menuFile.add(menuFileSaveAs);
        menuFile.add(jSeparator4);

        menuFileExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/menu/exit.png"))); // NOI18N
        menuFileExit.setText("Exit");
        menuFileExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventFile(evt);
            }
        });
        menuFile.add(menuFileExit);

        menuBar.add(menuFile);

        menuEdit.setText("Edit");

        menuEditUndo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        menuEditUndo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/menu/undo.png"))); // NOI18N
        menuEditUndo.setText("Undo");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, toolBarBtnUndo, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), menuEditUndo, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        menuEditUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventUndoRedo(evt);
            }
        });
        menuEdit.add(menuEditUndo);

        menuEditRedo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        menuEditRedo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/menu/redo.png"))); // NOI18N
        menuEditRedo.setText("Redo");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, toolBarBtnRedo, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), menuEditRedo, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        menuEditRedo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventUndoRedo(evt);
            }
        });
        menuEdit.add(menuEditRedo);
        menuEdit.add(jSeparator6);

        menuEditCopy.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        menuEditCopy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/menu/copy.png"))); // NOI18N
        menuEditCopy.setText("Copy");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, toolBarBtnCopy, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), menuEditCopy, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        menuEditCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventCopyPaste(evt);
            }
        });
        menuEdit.add(menuEditCopy);

        menuEditCut.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        menuEditCut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/menu/cut.png"))); // NOI18N
        menuEditCut.setText("Cut");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, toolBarBtnCut, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), menuEditCut, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        menuEditCut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventCopyPaste(evt);
            }
        });
        menuEdit.add(menuEditCut);

        menuEditPaste.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        menuEditPaste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/menu/paste.png"))); // NOI18N
        menuEditPaste.setText("Paste");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, toolBarBtnPaste, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), menuEditPaste, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        menuEditPaste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventCopyPaste(evt);
            }
        });
        menuEdit.add(menuEditPaste);

        menuBar.add(menuEdit);

        menuView.setText("View");

        menuViewGrid.setText("Pixel Grid");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, toolBarBtnTgGrid, org.jdesktop.beansbinding.ELProperty.create("${selected}"), menuViewGrid, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        menuViewGrid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventGrid(evt);
            }
        });
        menuView.add(menuViewGrid);

        menuViewUserMode.setText("User mode");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, toolBarBtnTgOxy, org.jdesktop.beansbinding.ELProperty.create("${selected}"), menuViewUserMode, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        menuViewUserMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventGrid(evt);
            }
        });
        menuView.add(menuViewUserMode);

        menuViewRuler.setText("Ruler");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, toolBarBtnRuler, org.jdesktop.beansbinding.ELProperty.create("${selected}"), menuViewRuler, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        menuView.add(menuViewRuler);

        menuBar.add(menuView);

        menuLayer.setText("Layer");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, layerToolBtnAdd, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), menuLayer, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        menuLayerAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/menu/addlayer.png"))); // NOI18N
        menuLayerAdd.setText("Add New Layer");
        menuLayerAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventLayerTool(evt);
            }
        });
        menuLayer.add(menuLayerAdd);

        menuLayerRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/menu/removelayer.png"))); // NOI18N
        menuLayerRemove.setText("Remove Layer");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, layerToolBtnRemove, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), menuLayerRemove, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        menuLayerRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventLayerTool(evt);
            }
        });
        menuLayer.add(menuLayerRemove);

        menuLayerMerge.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/menu/mergedown.png"))); // NOI18N
        menuLayerMerge.setText("Merge Down Layer");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, layerToolBtnMerge, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), menuLayerMerge, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        menuLayerMerge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventLayerTool(evt);
            }
        });
        menuLayer.add(menuLayerMerge);

        menuLayerMoveUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/menu/moveup.png"))); // NOI18N
        menuLayerMoveUp.setText("Move Layer Up");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, layerToolBtnMoveUp, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), menuLayerMoveUp, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        menuLayerMoveUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventLayerTool(evt);
            }
        });
        menuLayer.add(menuLayerMoveUp);

        menuLayerMoveDown.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/menu/movedown.png"))); // NOI18N
        menuLayerMoveDown.setText("Move Layer Down");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, layerToolBtnMoveDown, org.jdesktop.beansbinding.ELProperty.create("${enabled}"), menuLayerMoveDown, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        menuLayerMoveDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventLayerTool(evt);
            }
        });
        menuLayer.add(menuLayerMoveDown);

        menuBar.add(menuLayer);

        jMenu1.setText("About");

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/colors.png"))); // NOI18N
        jMenuItem1.setText("JPaint");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ABOUTJPAINT(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsc/menu/jTeam.png"))); // NOI18N
        jMenuItem2.setText("JPaint Team");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ABOUTJPAINT(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        menuBar.add(jMenu1);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(extendBar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(deskPane)
            .addComponent(toolBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(extendBar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(deskPane))
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void eventFile(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eventFile
        if (wrapPane.isShow()) {
            finishEditMode();
        }
        if (evt.getSource() == toolBarBtnNew || evt.getSource() == menuFileNew) {
            createNewFile();
        }
        if (evt.getSource() == toolBarBtnOpen || evt.getSource() == menuFileOpen) {
            openFile();
        }
        if (evt.getSource() == toolBarBtnSave || evt.getSource() == menuFileSave) {
            saveFile();
        }
        if (evt.getSource() == menuFileSaveAs) {
            saveAsFile();
        }
    }//GEN-LAST:event_eventFile

    private void eventZoom(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_eventZoom
        // System.out.println("1356 Zoom =====");
        // System.out.println("Old: " + imagePane.getLocation());
        int value = slZoom.getValue();
        zoom = value;
        lbZoom.setText("Zoom " + value + "x");
        imagePane.updateZoom(value);
        // System.out.println("New: " + imagePane.getLocation());
        wrapPane.updateZoom(value);
        rulerPanel.setNewSize();
    }//GEN-LAST:event_eventZoom
    public int getGridMode() {
        return gridMode;
    }

    //ToolBar
    private void eventGrid(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eventGrid
        //if (evt.getSource())
        this.grid = toolBarBtnTgGrid.isSelected();
        if (imagePane == null) {
            return;
        }
        this.gridMode = toolBarBtnTgOxy.isSelected() ? 1 : 0;
        if (btnSwitchShape.isSelected()) {
            gridMode = CROOD_MODE_3D;
        }
        imagePane.getGridPane().setMode(gridMode);
        imagePane.getGridPane().repaint();
        if (wrapPane.isShow()) {
            wrapPane.notifyProperties();
        }
    }//GEN-LAST:event_eventGrid

    private void eventChangeColor(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eventChangeColor
        for (JLabel x : colorList) {
            if (evt.getSource() == x) {
                if (evt.getClickCount() == 2) {   //Double Click
                    colorC[colorMode].setBackground(cColor);
                    Color nColor = JColorChooser.showDialog(this, "Change color...", color[colorMode]);
                    if (nColor != null) {
                        x.setBackground(nColor);
                    }
                    cClick = false;
                    return;
                } else {
                    if (cClick == true) {
                        break;
                    }
                    cColor = colorC[colorMode].getBackground();
                    Color t = color[colorMode] = x.getBackground();
                    setRGBSlider(t);
                    colorC[colorMode].setBackground(t);
                    if (wrapPane.isShow()) {
                        Color ret = this.getCurrentColor();
                        if (tShape2D != null) {
                            tShape2D.setOutlineColor(ret);
                            wrapPane.repaintWrapWithFill();
                        } else {
                            tShape3D.setOutlineColor(ret);
                            wrapPane.paintWrap3D();
                        }
                    }
                    return;
                }
            }
        }
    }//GEN-LAST:event_eventChangeColor

    private void eventChangeColorMode(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eventChangeColorMode
        if (evt.getSource() == lbPriColor) {
            if (colorMode == 0) {
                return;
            }
            colorMode = 0;
            lbPriColor.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            lbSecColor.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            lbCurColor.setText("Primary Color");

            setRGBSlider(color[0]);
        } else {
            if (colorMode == 1) {
                return;
            }
            colorMode = 1;
            lbSecColor.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            lbPriColor.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            lbCurColor.setText("Secondary Color");
            setRGBSlider(color[1]);
        }
    }//GEN-LAST:event_eventChangeColorMode

    private void eventAjustColor(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_eventAjustColor
        lbColorRed.setText(String.valueOf(slRed.getValue()));
        lbColorGreen.setText(String.valueOf(slGreen.getValue()));
        lbColorBlue.setText(String.valueOf(slBlue.getValue()));
        Color p = new Color(slRed.getValue(), slGreen.getValue(), slBlue.getValue());
        color[colorMode] = p;
        if (colorMode == 0) {
            lbPriColor.setBackground(p);
        } else {
            lbSecColor.setBackground(p);
        }
        if (wrapPane.isShow()) {
            if (tShape2D != null) {
                tShape2D.setOutlineColor(this.getCurrentColor());
                wrapPane.repaintWrap2D();
            } else {
                tShape3D.setOutlineColor(this.getCurrentColor());
                wrapPane.paintWrap3D();
            }
        }
    }//GEN-LAST:event_eventAjustColor

    private void eventLayerTool(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eventLayerTool
        if (wrapPane.isShow()) {
            finishEditMode();
        }
        if (evt.getSource() == layerToolBtnAdd || evt.getSource() == menuLayerAdd) {
            imagePane.addNewLayer("", 1);   //addTrans
        }
        if (evt.getSource() == layerToolBtnRemove || evt.getSource() == menuLayerRemove) {
            if (imagePane.getCurrentLayer().getLayerName().equals("Default")) {
                JOptionPane.showMessageDialog(this, "Can't remove Default layer!", "JPaint", JOptionPane.ERROR_MESSAGE);
                return;
            }
            imagePane.removeLayer(listLayer.getSelectedValue());
        }
        if (evt.getSource() == layerToolBtnMerge || evt.getSource() == menuLayerMerge) {
            imagePane.mergeLayer(listLayer.getSelectedValue());
        }
        if (evt.getSource() == layerToolBtnMoveDown || evt.getSource() == menuLayerMoveDown) {
            imagePane.moveLayerDown(listLayer.getSelectedValue());
        }

        if (evt.getSource() == layerToolBtnMoveUp || evt.getSource() == menuLayerMoveUp) {
            int index = listLayer.getModel().getSize() - listLayer.getSelectedIndex() - 1;
            imagePane.moveLayerUp(listLayer.getSelectedValue());
        }
        int indexChoose = listLayer.getModel().getSize() - listLayer.getSelectedIndex() - 1;
        int layerCount = listLayer.getModel().getSize() - 1;
        layerToolBtnMerge.setEnabled(true);
        layerToolBtnMoveDown.setEnabled(true);
        layerToolBtnMoveUp.setEnabled(true);
        layerToolBtnRemove.setEnabled(true);
        if (indexChoose == 0) {       //Default
            layerToolBtnMerge.setEnabled(false);
            layerToolBtnMoveDown.setEnabled(false);
            layerToolBtnMoveUp.setEnabled(false);
            layerToolBtnRemove.setEnabled(false);
        } else if (indexChoose == layerCount) {
            layerToolBtnMoveUp.setEnabled(false);
            if (indexChoose == 1) {
                layerToolBtnMoveDown.setEnabled(false);
            }
        } else if (indexChoose == 1) {
            layerToolBtnMoveDown.setEnabled(false);
        }
    }//GEN-LAST:event_eventLayerTool

    private void eventChooseLayer(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eventChooseLayer
        if (imagePane == null) {
            return;
        }
        if (wrapPane.isShow()) {
            finishEditMode();
        }
        JList<ListItem> list = (JList<ListItem>) evt.getSource();
        int index = list.locationToIndex(evt.getPoint());
        ListItem item = (ListItem) list.getModel().getElementAt(index);
        imagePane.setCurLay(item.getLayer());
        if (item.isSelected()) {
            item.setShow(!item.isShow());
            item.getLayer().setVisible(item.isShow());
        } //An/Hien Layer
        else {
            item.setSelected(true);
            DefaultListModel<ListItem> dlm = (DefaultListModel<ListItem>) list.getModel();
            for (int i = 0; i < dlm.size(); i++) {
                if (i == index) {
                    continue;
                }
                dlm.get(i).setSelected(false);
            }
        }
        list.repaint();
        int indexChoose = listLayer.getModel().getSize() - listLayer.getSelectedIndex() - 1;
        int layerCount = listLayer.getModel().getSize() - 1;
        layerToolBtnMerge.setEnabled(true);
        layerToolBtnMoveDown.setEnabled(true);
        layerToolBtnMoveUp.setEnabled(true);
        layerToolBtnRemove.setEnabled(true);
        if (indexChoose == 0) {       //Default
            layerToolBtnMerge.setEnabled(false);
            layerToolBtnMoveDown.setEnabled(false);
            layerToolBtnMoveUp.setEnabled(false);
            layerToolBtnRemove.setEnabled(false);
        } else if (indexChoose == layerCount) {
            layerToolBtnMoveUp.setEnabled(false);
            if (indexChoose == 1) {
                layerToolBtnMoveDown.setEnabled(false);
            }
        } else if (indexChoose == 1) {
            layerToolBtnMoveDown.setEnabled(false);
        }
    }//GEN-LAST:event_eventChooseLayer

    private void eventChangeTool(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eventChangeTool
        resetExtendBar();
        JToggleButton t = (JToggleButton) evt.getSource();
        if (toolList2D.contains(t)) {
            this.changeTool2D(t);
            //System.out.println(t.getName());
            showExtendBarShape(t.getName());

        }
        if (toolList3D.contains(t)) {
            changeTool3D(t);
            showExtendBarShape(t.getName());
        }
    }//GEN-LAST:event_eventChangeTool

    private void eventUndoRedo(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eventUndoRedo
        if (wrapPane.isShow()) {
            finishEditMode();
        }
        ArrayList<Shape2D> tList = imagePane.getCurrentLayer().getShapeList();
        Shape2D t1 = null;
        if (tList.size() != 0) {
            t1 = tList.get(tList.size() - 1);
        }

        if (evt.getSource() == toolBarBtnUndo || evt.getSource() == menuEditUndo) {

            DoItem dI = undoList.firstElement();
            LayerPanel lP = dI.getLayer();
            Shape2D t2 = dI.getShape();
            //System.out.println("2518 Main: "+dI.getIndex());
            if (dI.getMode() == DoItem.DELETE_MODE) { //Mode Xoa => Ve lai
                t2.setVisible(true);
                lP.addShape(t2, dI.getIndex());
                if (wrapPane.isShow()) {
                    wrapPane.clearWrap();
                    tShape2D = null;
                }
                if (t2.getType() != Shape2D.TYPE_PENCIL) {
                    putShapeToEdit(t2);
                }
            }
            if (dI.getMode() == dI.DRAW_MODE) {    //Mode Ve => xoa
                lP.getShapeList().remove(dI.getIndex());

                if (wrapPane.isShow()) {
                    wrapPane.clearWrap();
                    tShape2D = null;
                }
                curMode = MODE_DRAW_2D;
            }
            if (dI.getMode() == dI.EDIT_MODE) {   //Edit Mode => thay the
                System.out.println("2484 main: Ko trung nhau" + t1.getWrapAt(0) + " " + t2.getWrapAt(0));
                t2.setVisible(false);
                lP.replaceShape(t2, dI.getIndex());
                wrapPane.clearWrap();
                if (t1.getType() != Shape2D.TYPE_PENCIL) {
                    putShapeToEdit(t2);
                }
            }
            redoList.add(0, new DoItem(dI.getMode(), t1 != null ? t1.clone() : null, dI.getIndex(), lP));
            undoList.remove(0);
            //System.out.println("2497 Main: So luong trong stack undo: "+undoList.size());
            lP.repaintAllShape();
        }
        if (evt.getSource() == toolBarBtnRedo || evt.getSource() == menuEditRedo) {
            DoItem dI = redoList.firstElement();
            LayerPanel lP = dI.getLayer();
            Shape2D t2 = dI.getShape();
            // System.out.println("2508 Main: ReList Mode"+dI.getMode());
            if (dI.getMode() == dI.EDIT_MODE) {   // Edit => Thay the
                //    System.out.println("2509 Main: Redo Edit");
                t2.setVisible(false);
                lP.replaceShape(t2, dI.getIndex());
                wrapPane.clearWrap();
                if (t2.getType() != Shape2D.TYPE_PENCIL) {
                    putShapeToEdit(t2);
                }
            }
            if (dI.getMode() == dI.DRAW_MODE) {   //Draw => Ve moi
                t2.setVisible(true);
                lP.addShape(t2, dI.getIndex());
                if (wrapPane.isShow()) {
                    wrapPane.clearWrap();
                    tShape2D = null;
                }
                if (t2.getType() != Shape2D.TYPE_PENCIL) {
                    putShapeToEdit(t2);
                }
            }
            if (dI.getMode() == DoItem.DELETE_MODE) {    //Mode Xoa => xoa
                lP.getShapeList().remove(dI.getIndex());
                if (wrapPane.isShow()) {
                    wrapPane.clearWrap();
                    tShape2D = null;
                }
                curMode = MODE_DRAW_2D;
            }
            //if (dI.getMode()==dI.DRAW_MODE)
            if (t1 == null) {
                t1 = t2;
            }
            undoList.add(0, new DoItem(dI.getMode(), t1.clone(), dI.getIndex(), lP));
            redoList.remove(0);
            lP.repaintAllShape();
        }
        //       System.out.println("2519 Main: Undo "+undoList.size()+" Redo "+redoList.size());

        toolBarBtnUndo.setEnabled(!undoList.isEmpty());
        toolBarBtnRedo.setEnabled(!redoList.isEmpty());
    }//GEN-LAST:event_eventUndoRedo

    private void eventChangeSizeWrap(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_eventChangeSizeWrap
        //System.out.println("1527"+scrollPanel.getSize());
        // if (imagePane!=null) {
        //     imagePane.resizeViewPort();
        wrapPane.setNewSize();
        rulerPanel.setNewSize();
        wrapPane.repaintWrapWithFill();
        //  }
    }//GEN-LAST:event_eventChangeSizeWrap

    private void eventCursorReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eventCursorReleased
        viewPort.setCursor(csTemp);
        if (tShape2D != null && curMode == MODE_DRAW_2D) {
            if (tShape2D.getType() != Shape2D.TYPE_PIXEL) {
                tShape2D.setVisible(false);
            } else {
                tShape2D.setVisible(true);
            }
            imagePane.getCurrentLayer().addAndPaintShape(tShape2D);
            addUndoList(tShape2D, curMode);
            redoList.clear();
            if (tShape2D.getType() == Shape2D.TYPE_PENCIL) {
                imagePane.getTempLayer().clear();
                finishEditMode();
            }
        }
        if (tShape2D != null && curMode == MODE_EDIT_2D) {
            tShape2D.reFill(wrapPane.getWrapTimer());
        }

        endCreate3DShape = getImageCursorPos(evt.getX(), evt.getY());
        //Reset flag
        wrapPane.setManual(false);
        isResize = false;
        isRotate = false;
        isRotatePoint = false;
        isMoveRotatePoint = false;
        isShapeAnchor = false;
        addedUndoList = false;
        if (isScroll || isMove) {
            viewPort.setCursor(csTemp);
        }
        isScroll = false;
        isMove = false;
        //An tip
        mouseTip.hideTips();
        //Doi he toa do
        if (isCreateShape) {
            isCreateShape = false;
            int p1i = wrapPane.getCurShape().getDim().width;
            int p2i = wrapPane.getCurShape().getDim().height;
            Point p1 = wrapPane.getPointAt(p1i);
            Point p2 = wrapPane.getPointAt(p2i);
            //System.out.println(wrapPane.getCurShape().getDim());
            wrapPane.setPointWrap2D(p1, p2);
            wrapPane.repaintWrap2D();
            //wrapPane.getCurShape().fill(Color.YELLOW);
        }
        //Neu co Hinh, chuyen qua edit mode
        if (tShape2D != null && curMode == MODE_DRAW_2D) {     //Co hinh ve
            if (!curTool.equals("selector") && !curTool.equals("picker")
                    && !curTool.equals("fill") && !curTool.equals("pencil")) {
                curMode = MODE_EDIT_2D;
            }
        }
        //Che do Flip line
        if (curMode == MODE_FLIPLINE && tShape2D != null) {
            wrapPane.transShapeFlipLine();
            curMode = MODE_EDIT_2D;
            inFlipLine = false;
            viewPort.setCursor(csTemp);
            wrapPane.clearFlipLine();
            wrapPane.repaintWrapWithFill();
        }
        //Cap nhat Undo Redo
        toolBarBtnUndo.setEnabled(!undoList.isEmpty());
        toolBarBtnRedo.setEnabled(!redoList.isEmpty());
    }//GEN-LAST:event_eventCursorReleased

    private void eventCursorPressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eventCursorPressed
        // if (curTool.equals("line")||curTool.equals("rect")||curTool.equals("triangle")||curTool.equals("trapezoid")||curTool.equals("poly")||curTool.equals("para")) return;
   if (tShape3D != null && curMode == MODE_DRAW_3D) {
            tShape3D.setVisible(false);
            imagePane.getCurrentLayer().addAndPaintShape(tShape3D);
            curMode = MODE_EDIT_3D;
        } else {
            eventCursorDragged(evt);
        }
    }//GEN-LAST:event_eventCursorPressed

    private void eventCursorMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eventCursorMoved
        int anc = -1, ancR = -1, ancS = -1;
        if (imagePane == null) {
            return;
        }
        //Gan chuot cho tool
        if (!curTool.equals("")) {
            this.setCursor(viewPort, curTool);
        }
        //Cap nhat vi tri tren man hinh
        Point p = getImageCursorPos(evt.getX(), evt.getY());
        setImagePos(evt.getPoint());
        //Che do flip point
        if (curMode == MODE_FLIPPOINT || curMode == MODE_FLIPLINE) {
            if (!csTemp.equals(csFLipPoint)) {
                csTemp = viewPort.getCursor();
            }
            viewPort.setCursor(csFLipPoint);
        }
        //Che do Edit Mode
        if (curMode == MODE_EDIT_2D) {
            csTemp = viewPort.getCursor();
            //Resize hover
            anc = wrapPane.inResizeAnchorAtCorner(evt.getPoint());
            ancR = wrapPane.inRotateAnchorAtCorner(evt.getPoint());
            ancS = wrapPane.inShapeAnchor(evt.getPoint());
            //Hover chuot theo lop tu tren xuong
            if (ancS != -1) {
                viewPort.setCursor(csHandOpen);
            } else if (anc != -1) {                           //Layer Resize
                viewPort.setCursor(csResize[anc]);
            } else if (wrapPane.inRotatePoint(evt.getPoint())) {
                viewPort.setCursor(csHandOpen);
            } else if (wrapPane.inRotateAtRotatePoint(evt.getPoint())) {
                viewPort.setCursor(csRoateAtPoint);
            } else if (wrapPane.contain(p)) {       //Layer Move
                viewPort.setCursor(csMoveH);
            } else if (ancR != -1) {                  //Layer Rotate (chi lay ben ngoai)
                viewPort.setCursor(csRotate[ancR]);
            } else {
                viewPort.setCursor(csTemp);
            }
        }

        //Tao z 3D
        if (tShape3D != null && curMode == MODE_DRAW_3D) {
            Point cur = getImageCursorPos(evt.getX(), evt.getY());
            //int delta = (int) Point.distance(endCreate3DShape.x, endCreate3DShape.y, cur.x, cur.y);
            int delta = endCreate3DShape.y - cur.y;
            Point3D p3 = tShape3D.getPoint3DAt(3);
            p3.z = delta;
            wrapPane.setPoint3DAt(p3, 3);
            wrapPane.paintWrap3D();
            mouseTip.showTips(evt.getPoint(), wrapPane.getSizeWrap3D());
        }
    }//GEN-LAST:event_eventCursorMoved

    private void eventCursorDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eventCursorDragged
        if (imagePane == null) {
            return;    //chua load hinh anh => Out
        }        //Lay toa do chuot
        //if (wrapPane.isShow() && curMode!=MODE_EDIT_2D) finishEditMode();
        Point p = getImageCursorPos(evt.getX(), evt.getY());
        setImagePos(evt.getPoint());
        //Khoi tao bien
        LayerPanel curLayer = imagePane.getCurrentLayer();
        LayerPanel tempLayer = imagePane.getTempLayer();
        int anc = -1, ancR = -1, ancS = -1;
        boolean mouseInImage = true;
        //Chuot chay ra khoi Image
        if (p.x >= imagePane.getImageWidth() || p.y >= imagePane.getImageHeight() || p.x < 0 || p.y < 0) {
            mouseInImage = false;
        }
        //Scroll Image
        if (SwingUtilities.isMiddleMouseButton(evt)) {
            if (isScroll == true) {
                viewPort.setCursor(csHandClose);
                int dx = evt.getX() - oldScroll.x;
                int dy = evt.getY() - oldScroll.y;
                JScrollBar hb = scrollPanel.getHorizontalScrollBar();
                JScrollBar vb = scrollPanel.getVerticalScrollBar();
                hb.setValue(hb.getValue() - dx);
                vb.setValue(vb.getValue() - dy);
            } else {
                oldScroll = evt.getPoint();
                isScroll = true;
                csTemp = viewPort.getCursor();
            }
        }
        if (SwingUtilities.isLeftMouseButton(evt)) {
            // <editor-fold desc="Ve 2D">
            if (curMode == MODE_DRAW_2D && mouseInImage == true) {         //Che do ve 2D
                //Ve Pencil
                if (curTool.equals("pencil")) {
                    if (tShape2D == null) {
                        tShape2D = new Pencil();
                        tShape2D.create(tempLayer);
                        tShape2D.setOutlineColor(getCurrentColor());
                        tShape2D.setSize(1);
                        tShape2D.addPoint(p, -1);
                        tShape2D.draw(tShape2D.getType());
                    } else {
                        tShape2D.addPoint(p, -1);
                        tShape2D.draw(tShape2D.getType());
                    }
                }
                //Brush
                if (curTool.equals("brush")) {
                    if (tShape2D == null) {
                        tShape2D = new Pencil();
                        tShape2D.create(tempLayer);
                        tShape2D.setOutlineColor(getCurrentColor());
                        tShape2D.setSize(Integer.valueOf(extendBarCbSize.getSelectedItem().toString()));
                        tShape2D.addPoint(p, -1);
                        tShape2D.draw(tShape2D.getType());
                    } else {
                        tShape2D.addPoint(p, -1);
                        tShape2D.draw(tShape2D.getType());
                    }
                }
                //eraser
                if (curTool.equals("eraser")) {
                    if (tShape2D == null) {
                        tShape2D = new Pencil();
                        tShape2D.create(imagePane.getCurrentLayer());
                        Color eraColor = imagePane.getCurrentLayer().getLayerName().equals("Default") ? lbSecColor.getBackground() : new Color(0, 0, 0, 0);
                        tShape2D.setOutlineColor(eraColor);
                        tShape2D.setSize(Integer.valueOf(extendBarCbSize.getSelectedItem().toString()));
                        tShape2D.addPoint(p, -1);
                        tShape2D.draw(tShape2D.getType());
                    } else {
                        tShape2D.addPoint(p, -1);
                        tShape2D.draw(tShape2D.getType());
                    }
                }
                //Line
                if (curTool.equals("line")) {
                    createShape2D("line", evt);
                }
                //Rectangle
                if (curTool.equals("rect")) {
                    createShape2D("rect", evt);
                }
                //Trapezoid
                if (curTool.equals("trapezoid")) {
                    createShape2D("trapezoid", evt);
                }
                //Triangle
                if (curTool.equals("triangle")) {
                    createShape2D("triangle", evt);
                }
                //Diamond
                if (curTool.equals("diamond")) {
                    createShape2D("diamond", evt);
                }
                //Elip
                if (curTool.equals("ellipse")) {
                    createShape2D("ellipse", evt);
                }
                //Parallelogram
                if (curTool.equals("para")) {
                    createShape2D("para", evt);
                }
                //Polygons
                if (curTool.equals("polygons")) {
                    createShape2D("polygons", evt);
                }
                //Picker
                if (curTool.equals("picker")) {
                    setCurColor(curLayer.getPixelAt(p.x, p.y).getColor());
                }
                //Paint Bucket
                if (curTool.equals("fill")) {
                    tShape2D = new PixelShape();
                    tShape2D.setLayerPanel(curLayer);
                    tShape2D.importPixel2DList(DrawAlgorithm.algorithmFloodFill(p, getCurrentColor(), curLayer, curMode));

                    //DrawAlgorithm.algorithmFloodFill(p, getCurrentColor(), curLayer, curMode);
                }
                //selector
                if (curTool.equals("selector")) {
                    tShape2D = imagePane.getCurrentLayer().getShapeAtPoint(p);
                    if (tShape2D != null) {
                        tShape2D.setVisible(false); //an khoi layer
                        wrapPane.create(tShape2D);
                        wrapPane.paintWrap2D();
                        wrapPane.repaintWrapWithFill();
                        imagePane.getCurrentLayer().repaintAllShape();

                        extendBarCbSize.setSelectedItem(tShape2D.getPixelSize());

                        showExtendBarShape(tShape2D.getShapeName());
                        tShape2DExist = true;
                        curMode = MODE_EDIT_2D;
                    } else {
                        resetExtendBar();

                    }
                    showExtendBarShape("selector");
                    lbCurTool.setText("Selector Tool");
                    lbCurTool.setIcon(new ImageIcon(getClass().getResource("/rsc/mouse.png")));
                }
                // </editor-fold>
                // <editor-fold desc="Ve 3D">
            } else if (curMode == MODE_DRAW_3D) {
                if (curTool.equals("box")) {
                    createShape3D(curTool, evt);
                }
                if (curTool.equals("pyramid")) {
                    createShape3D(curTool, evt);
                }
                if (curTool.equals("tetrahedron")) {
                    createShape3D(curTool, evt);
                }
                if (curTool.equals("tprism")) {
                    createShape3D(curTool, evt);
                }
                if (curTool.equals("hprism")) {
                    createShape3D(curTool, evt);
                }
                // </editor-fold>  
            } else if (curMode == MODE_EDIT_3D) {
                finishEditMode();
                curMode = MODE_DRAW_3D;
                // <editor-fold desc="Chinh sua 2D">
            } else if (curMode == MODE_EDIT_2D) {          //Che do chinh sua
                anc = wrapPane.inResizeAnchorAtCorner(evt.getPoint());    //Lay anchor
                ancR = wrapPane.inRotateAnchorAtCorner(evt.getPoint());                 //<===
                ancS = wrapPane.inShapeAnchor(evt.getPoint());
                // <editor-fold desc="Dieu chinh Points">
                if ((ancS != -1 || isShapeAnchor) && !isRotate && !isResize && !isMoveRotatePoint && !isMove && !isRotatePoint) {
                    addUndoList(tShape2D, curMode);
                    if (viewPort.getCursor() != csHandClose) {
                        csTemp = viewPort.getCursor();
                    }
                    viewPort.setCursor(csHandClose);
                    if (ancS != -1 && isShapeAnchor == false) {
                        wrapShapeAnchorPos = ancS;
                    }
                    isShapeAnchor = true;

                    wrapPane.setShapeAnchor(p, wrapShapeAnchorPos);
                    wrapPane.repaintWrap2D();
                    mouseTip.showTips(evt.getPoint(), wrapPane.getShapeAnchorPoint(wrapShapeAnchorPos));
                }
                // </editor-fold>  
                // <editor-fold desc="Thay doi kich thuoc">
                if ((anc != -1 || isResize == true) && !isRotate && !isMove && !isShapeAnchor && !isMoveRotatePoint && !isRotatePoint) {
                    if (anc != -1 && isResize == false) {
                        wrapAnchorPos = anc;
                    }
                    //Doi he toa do
                    p = wrapPane.transRotate(p, wrapPane.getCenterPoint(0), -wrapPane.getAlpha(curMode));
                    wrapAnchorPos = wrapPane.flipAnchor(p, wrapAnchorPos);
                    isResize = true;
                    addUndoList(tShape2D, curMode);
                    if (SwingUtilities.isRightMouseButton(evt) || tShape2D.getShapeName().equals("polygons")) {
                        wrapPane.setPointResizeAt(p, wrapAnchorPos, WrapPane2D.WRAP_STYLE_SQUARE);
                    } else {
                        wrapPane.setPointResizeAt(p, wrapAnchorPos, WrapPane2D.WRAP_STYLE_RECT);
                    }
                    wrapPane.paintWrap2D();
                    if (tShape2D.getType() == Shape2D.TYPE_ELIP) {
                        mouseTip.showTips(evt.getPoint(), MouseTip.TYPE_2R, tShape2D.getR1(), tShape2D.getR2());
                    } else {
                        mouseTip.showTips(evt.getPoint(), wrapPane.getSizeWrap2D());
                    }

                }
                // </editor-fold>  
                // <editor-fold desc="Di chuyen diem Xoay">
                if ((wrapPane.inRotatePoint(evt.getPoint()) || isMoveRotatePoint) && !isRotate && !isShapeAnchor && !isResize && !isRotatePoint && !isMove) {
                    addUndoList(tShape2D, curMode);
                    if (viewPort.getCursor() != csHandClose) {
                        csTemp = viewPort.getCursor();
                    }
                    viewPort.setCursor(csHandClose);
                    if (SwingUtilities.isRightMouseButton(evt)) {
                        isMoveRotatePoint = false;
                        wrapPane.setLocationRotatePoint(wrapPane.getCenterPoint(0));
                    } else {
                        isMoveRotatePoint = true;
                        wrapPane.setLocationRotatePoint(p);

                    }
                    wrapPane.repaintWrap2D();
                }
                // </editor-fold> 
                // <editor-fold desc="Xoay xung quanh Diem Xoay">
                if ((wrapPane.inRotateAtRotatePoint(evt.getPoint()) || isRotatePoint) && !isRotate && !isResize && !isMoveRotatePoint && !isMove && !isShapeAnchor) {
                    addUndoList(tShape2D, curMode);
                    if (isRotatePoint == true) {
                        wrapPane.transShapeRotateAtRotatePoint(p, isRotatePoint);
                    } else {
                        wrapPane.transShapeRotateAtRotatePoint(p, isRotatePoint);
                        isRotatePoint = true;

                    }
                    wrapPane.repaintWrap2D();
                }
                // </editor-fold> 
                // <editor-fold desc="Di chuyen hinh">
                if ((wrapPane.contain(p) || isMove) && !isRotate && !isResize && !isMoveRotatePoint && !isRotatePoint && !isShapeAnchor) {
                    addUndoList(tShape2D, curMode);
                    if (isMove == true) {
                        viewPort.setCursor(csMoveP);
                        int dx = p.x - oldMove.x;
                        int dy = p.y - oldMove.y;
                        wrapPane.setPointWrap2D(wrapPane.transLocation(wrapPane.getPointAt(0), dx, dy), wrapPane.transLocation(wrapPane.getPointAt(4), dx, dy));
                        wrapPane.repaintWrap2D();
                        oldMove = p;
                    } else {
                        isMove = true;
                        oldMove = p;
                        csTemp = viewPort.getCursor();
                    }

                }
                // </editor-fold> 
                // <editor-fold desc="Xoay xung quanh tam">
                if ((ancR != -1 || isRotate == true) && !isResize && !isShapeAnchor && !isMove && !isMoveRotatePoint & !isRotatePoint) {
                    if (ancR != -1 && isRotate == false) {
                        wrapAnchorPos = ancR;
                    }
                    addUndoList(tShape2D, curMode);
                    isRotate = true;
                    wrapPane.transShapeRotateAtCorner(p, wrapAnchorPos);
                    wrapPane.repaintWrap2D();
                    mouseTip.showTips(evt.getPoint(), MouseTip.TYPE_ALPHA, wrapPane.getAlpha(WrapPane2D.RAD));
                }
                // </editor-fold> 
                // <editor-fold desc="Ket thuc edit mode">
                if (!wrapPane.contain(p) && !isRotate && !isResize && !isMoveRotatePoint && !isRotatePoint && !isMove && !isShapeAnchor) {
                    if (curTool.equals("selector")) {
                        resetExtendBar();
                        showExtendBarShape("selector");
                        lbCurTool.setText("Selector Tool");
                        lbCurTool.setIcon(new ImageIcon(getClass().getResource("/rsc/mouse.png")));
                    }
                    finishEditMode();
                }
                // </editor-fold> 
            }
            // </editor-fold>
            // <editor-fold desc="Bien doi 2D Flip">
            if (curMode == MODE_FLIPPOINT) {
                wrapPane.transShapeFlipPoint(p);
                wrapPane.repaintWrap2D();
                curMode = MODE_EDIT_2D;
                viewPort.setCursor(csTemp);
            }
            if (curMode == MODE_FLIPLINE) {
                if (inFlipLine == false) {
                    oldScroll = p;
                }
                inFlipLine = true;
                wrapPane.setFlipLine(oldScroll, p);
                wrapPane.repaintWrap2D();
            }
            // </editor-fold>

        }
    }//GEN-LAST:event_eventCursorDragged

    public void finishEditMode() {

        if (tShape2D != null) {
            // System.out.println("3175 Main: " + imagePane.getCurrentLayer().getLayerName());
            tShape2D.setVisible(true);
            imagePane.getCurrentLayer().repaintAllShape();
            tShape2D = null;
            curMode = MODE_DRAW_2D;
        }
        if (tShape3D != null) {
            tShape3D.setVisible(true);
            imagePane.getCurrentLayer().repaintAllShape();
            tShape3D = null;
            curMode = MODE_DRAW_3D;
        }
        //System.out.println("2890 Main: So hinh trong list"+imagePane.getCurrentLayer().getShapeList().size());
        if (wrapPane.isShow()) {
            wrapPane.clearWrap();
        }
    }

    public void createShape3D(String sname, MouseEvent evt) {
        Point realP = viewPort.getMousePosition();
        Point p = getImageCursorPos(evt.getX(), evt.getY());
        p = convertPointToUserMode(p);
        if (tShape3D == null) {
            if (sname.equals("box")) {
                tShape3D = new Box();
            }
            if (sname.equals("pyramid")) {
                tShape3D = new Pyramid();
            }
            if (sname.equals("tetrahedron")) {
                tShape3D = new Tetrahedron();
            }
            if (sname.equals("tprism")) {
                tShape3D = new TriangularPrism();
            }
            if (sname.equals("hprism")) {
                tShape3D = new Pyramid();
            }

            tShape3D.create(imagePane.getImageWidth(), imagePane.getImageHeight());
            tShape3D.setOutColor(getCurrentColor());
            tShape3D.setDotSize(1);
            Point3D p1 = Point3D.doi2Dsang3D(p);
            wrapPane.create(tShape3D, p1);
        } else {
            Point3D p5 = Point3D.doi2Dsang3D(p);
            wrapPane.setPoint3DAt(p5, 5);
            wrapPane.paintWrap3D();
            mouseTip.showTips(realP, wrapPane.getSizeWrap3D());
        }
    }

    public void createShape2D(String sname, MouseEvent evt) {
        Point realP = viewPort.getMousePosition();
        Point p = getImageCursorPos(evt.getX(), evt.getY());
        if (tShape2D == null) {
            if (sname.equals("line")) {
                tShape2D = new Line();
            }
            if (sname.equals("rect")) {
                tShape2D = new Rectangle();
            }
            if (sname.equals("trapezoid")) {
                tShape2D = new Trapezoid();
            }
            if (sname.equals("ellipse")) {
                tShape2D = new Ellipse();
            }
            if (sname.equals("triangle")) {
                tShape2D = new Triangle();
            }
            if (sname.equals("para")) {
                tShape2D = new Parallelogram();
            }
            if (sname.equals("diamond")) {
                tShape2D = new Diamond();
            }
            if (sname.equals("polygons")) {
                tShape2D = new Polygons();
            }

            //tShape2D.create(imagePane.getTempLayer());
            tShape2D.setOutlineColor(getCurrentColor());
            tShape2D.setSize(Integer.valueOf(extendBarCbSize.getSelectedItem().toString()));
            tShape2D.setAlgorithm(cbLineAlg.getSelectedItem().toString());
            tShape2D.setStroke(extendBarCbStroke.getSelectedItem().toString());
            isCreateShape = true;

            wrapPane.create(tShape2D, p);
            wrapPane.paintWrap2D();
        } else {
            //long start = System.nanoTime();
            if (SwingUtilities.isRightMouseButton(evt) || sname.equals("polygons")) {
                tShape2D.setEdge(Integer.valueOf(extendBarCbEdge.getSelectedItem().toString()));
                wrapPane.setPointManualAt(p, 4, WrapPane2D.WRAP_STYLE_SQUARE);
            } else {
                wrapPane.setPointManualAt(p, 4, WrapPane2D.WRAP_STYLE_RECT);
            }
            wrapPane.paintWrap2D();
            if (tShape2D.getType() == Shape2D.TYPE_ELIP) {
                mouseTip.showTips(realP, MouseTip.TYPE_2R, tShape2D.getR1(), tShape2D.getR2());
            } else {
                mouseTip.showTips(realP, wrapPane.getSizeWrap2D());
            }
            //System.out.println("Manual: "+(System.nanoTime()-start));
        }
    }
    private void eventFinishEdit(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eventFinishEdit
        if (evt.getSource() == extendBarBtnDone) {
            finishEditMode();
        }
    }//GEN-LAST:event_eventFinishEdit

    private void eventActionToolBarWrap(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eventActionToolBarWrap
        curMode = MODE_EDIT_2D;
        if (evt.getSource() == WPControlBtnFlipOx) {
            wrapPane.transShapeFlipLine(new Point(0, imagePane.getImageHeight() / 2), new Point(imagePane.getImageWidth(), imagePane.getImageHeight() / 2), Shape2D.FLIP_OX);
            wrapPane.repaintWrapWithFill();
        }
        if (evt.getSource() == WPControlBtnFlipOy) {
            wrapPane.transShapeFlipLine(new Point(imagePane.getImageWidth() / 2, 0), new Point(imagePane.getImageWidth() / 2, imagePane.getImageHeight()), Shape2D.FLIP_OY);
            wrapPane.repaintWrapWithFill();
        }
        if (evt.getSource() == WPControlBtnFlipPoint) {
            curMode = MODE_FLIPPOINT;
        }
        if (evt.getSource() == WPControlBtnFlipLine) {
            curMode = MODE_FLIPLINE;
        }
        if (evt.getSource() == WPControlBtnRotateRight) {
            wrapPane.transShapeRotateAtCenterPoint(Math.toRadians(90));
            wrapPane.repaintWrapWithFill();
        }
        if (evt.getSource() == WPControlBtnRotateLeft) {
            wrapPane.transShapeRotateAtCenterPoint(Math.toRadians(-90));
            wrapPane.repaintWrapWithFill();
        }
        if (evt.getSource() == WPControlMoveDown) {
            imagePane.getCurrentLayer().moveDownShape(tShape2D);
        }
        if (evt.getSource() == WPControlMoveUp) {
            imagePane.getCurrentLayer().moveUpShape(tShape2D);
        }
        if (evt.getSource() == WPControlMoveUp || evt.getSource() == WPControlMoveDown) {
            ArrayList<Shape2D> tList = imagePane.getCurrentLayer().getShapeList();
            WPControlMoveUp.setEnabled(true);
            WPControlMoveDown.setEnabled(true);
            if (tList.indexOf(tShape2D) == tList.size() - 1) {
                WPControlMoveUp.setEnabled(false);
            }
            if (tList.indexOf(tShape2D) == 0) {
                WPControlMoveDown.setEnabled(false);
            }
        }
        if (evt.getSource() == WPControlBtnRemove) {
            addUndoList(tShape2D, DoItem.DELETE_MODE);
            imagePane.getCurrentLayer().getShapeList().remove(tShape2D);
            imagePane.getTempLayer().clear();
            putShapeToHell(tShape2D);
        }

        if (evt.getSource() == WPControlNormalize) {
            Shape2D t = tShape2D.clone();
            imagePane.getCurrentLayer().getShapeList().remove(tShape2D);
            tShape2D = new PixelShape();
            tShape2D.importPixel2DList(t.getAllPixel());
            imagePane.getCurrentLayer().addAndPaintShape(tShape2D);
            finishEditMode();
        }
    }//GEN-LAST:event_eventActionToolBarWrap

    private void eventChangeProperties(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eventChangeProperties
        if (tShape2D == null && tShape3D == null) {
            return;
        }
        if (tShape2D != null) {
            addUndoList(tShape2D, curMode);
            int proLocationX = Integer.valueOf(proLocationtxtX.getText());
            int proLocationY = Integer.valueOf(proLocationtxtY.getText());
            int proSizeW = Integer.valueOf(proSizetxtW.getText());
            int proSizeH = Integer.valueOf(proSizetxtH.getText());
            float proScalingX = Float.valueOf(proTransformtxtScalingX.getText());
            float proScalingY = Float.valueOf(proTransformtxtScalingY.getText());
            int proTransRotate = Integer.valueOf(proTransformtxtRotate.getText());
            int proTransRotatePointX = Integer.valueOf(proTransformtxtRotatePointX.getText());
            int proTransRotatePointY = Integer.valueOf(proTransformtxtRotatePointY.getText());
            int proSizeR = Integer.valueOf(proSizetxtR.getText());
            int proSizeR1 = Integer.valueOf(proSizetxtR1.getText());
            int proSizeR2 = Integer.valueOf(proSizetxtR2.getText());

            int scalingX = (int) (proScalingX * wrapPane.getSizeWrap2D().width);
            int scalingY = (int) (proScalingY * wrapPane.getSizeWrap2D().height);

            Point np = new Point(proLocationX, proLocationY);

            np = convertPointToComputerMode(np);
            //Cap nhat Location
            if (proLocationtxtX.isFocusOwner() || proLocationtxtY.isFocusOwner()) {
                wrapPane.setLocationWrapCenter2D(np);
            }
            //Cap nhat Point
            if (proPointsP1X.isFocusOwner() || proPointsP1Y.isFocusOwner()) {
                int x = Integer.valueOf(proPointsP1X.getText());
                int y = Integer.valueOf(proPointsP1Y.getText());
                Point p = convertPointToComputerMode(new Point(x, y));
                int side = 0;
                p = wrapPane.transRotate(p, wrapPane.getCenterPoint(0), -wrapPane.getAlpha(curMode));
                side = wrapPane.flipAnchor(p, side);
                wrapPane.setPointResizeAt(p, side, WrapPane2D.WRAP_STYLE_RECT);
            }
            if (proPointstxtP2X.isFocusOwner() || proPointstxtP2Y.isFocusOwner()) {
                int x = Integer.valueOf(proPointstxtP2X.getText());
                int y = Integer.valueOf(proPointstxtP2Y.getText());
                Point p = convertPointToComputerMode(new Point(x, y));
                int side = 2;
                p = wrapPane.transRotate(p, wrapPane.getCenterPoint(0), -wrapPane.getAlpha(curMode));
                side = wrapPane.flipAnchor(p, side);
                wrapPane.setPointResizeAt(p, side, WrapPane2D.WRAP_STYLE_RECT);
            }
            if (proPointstxtP3X.isFocusOwner() || proPointstxtP3Y.isFocusOwner()) {
                int x = Integer.valueOf(proPointstxtP3X.getText());
                int y = Integer.valueOf(proPointstxtP3Y.getText());
                Point p = convertPointToComputerMode(new Point(x, y));
                int side = 0;
                p = wrapPane.transRotate(p, wrapPane.getCenterPoint(0), -wrapPane.getAlpha(curMode));
                side = wrapPane.flipAnchor(p, 4);
                wrapPane.setPointResizeAt(p, side, WrapPane2D.WRAP_STYLE_RECT);
            }
            if (proPointstxtP4X.isFocusOwner() || proPointstxtP4Y.isFocusOwner()) {
                int x = Integer.valueOf(proPointstxtP4X.getText());
                int y = Integer.valueOf(proPointstxtP4Y.getText());
                Point p = convertPointToComputerMode(new Point(x, y));
                int side = 0;
                p = wrapPane.transRotate(p, wrapPane.getCenterPoint(0), -wrapPane.getAlpha(curMode));
                side = wrapPane.flipAnchor(p, 4);
                wrapPane.setPointResizeAt(p, side, WrapPane2D.WRAP_STYLE_RECT);
            }

            //Cap nhat Size
            if (proSizetxtH.isFocusOwner() || proSizetxtW.isFocusOwner()) {
                wrapPane.setSizeWrap2D(proSizeW - 1, proSizeH - 1);
            }

            if (proSizetxtR1.isFocusOwner() || proSizetxtR2.isFocusOwner()) {
                wrapPane.setRadius(proSizeR1, proSizeR2);
            }
            //Cap nhat Scaling
            if (proTransformtxtScalingX.isFocusOwner() || proTransformtxtScalingY.isFocusOwner()) {

                wrapPane.setSizeWrap2D(scalingX, scalingY);
            }
            //Cap nhat alphap
            if (proTransformtxtRotate.isFocusOwner()) {
                wrapPane.setAlpha(Math.toRadians(proTransRotate));
            }
            //Cap nhat rotate point
            if (proTransformtxtRotatePointX.isFocusOwner() || proTransformtxtRotatePointY.isFocusOwner()) {
                Point nPoint = new Point(proTransRotatePointX, proTransRotatePointY);
                nPoint = convertPointToComputerMode(nPoint);
                wrapPane.setLocationRotatePoint(nPoint);
            }

            wrapPane.repaintWrapWithFill();
        } else {
            //Location
            if (proLocationTxtX.isFocusOwner() || proLocationTxtY.isFocusOwner() || proLocationTxtZ.isFocusOwner()) {
                int X = Integer.valueOf(proLocationTxtX.getText());
                int Y = Integer.valueOf(proLocationTxtY.getText());
                int Z = Integer.valueOf(proLocationTxtZ.getText());
                wrapPane.setLocationWrapCenter3D(new Point3D(X, Y, Z));
                wrapPane.paintWrap3D();
            }

            //Size
            if (proSize3DTxtH.isFocusOwner() || proSize3DTxtL.isFocusOwner() || proSize3DTxtW.isFocusOwner()) {
                int L = Integer.valueOf(proSize3DTxtL.getText());
                int W = Integer.valueOf(proSize3DTxtW.getText());
                int H = Integer.valueOf(proSize3DTxtH.getText());
                wrapPane.setSizeWrap3D(L, W, H);
                wrapPane.paintWrap3D();
            }

            //Rotate
            if (proRotate3DTxtX.isFocusOwner() || proRotate3DTxtY.isFocusOwner() || proRotate3DTxtZ.isFocusOwner()) {
                int X = Integer.valueOf(proRotate3DTxtX.getText());
                int Y = Integer.valueOf(proRotate3DTxtY.getText());
                int Z = Integer.valueOf(proRotate3DTxtZ.getText());
                wrapPane.setAlphaDEG3D(X, Y, Z);
                wrapPane.paintWrap3D();
            }

        }
    }//GEN-LAST:event_eventChangeProperties

    private void eventActionResetProperties(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eventActionResetProperties
        if (tShape2D == null) {
            return;
        }
        addUndoList(tShape2D, curMode);
        if (evt.getSource() == proLocationbtnXYReset) {
            wrapPane.setLocationWrapCenter2D(new Point(imagePane.getImageWidth() / 2, imagePane.getImageHeight() / 2));
            wrapPane.repaintWrapWithFill();
        }
        if (evt.getSource() == proTransformbtnResetRotate) {
            wrapPane.setAlpha(Math.toRadians(0));
            wrapPane.repaintWrapWithFill();
        }
        if (evt.getSource() == proTransformbtnResetRotatePoint) {
            wrapPane.setLocationRotatePoint(wrapPane.getCenterPoint(0));
            wrapPane.repaintWrapWithFill();
        }
        if (evt.getSource() == proColorbtnReset) {
            tShape2D.setSolidColor(null);
            wrapPane.repaintWrapWithFill();
        }
    }//GEN-LAST:event_eventActionResetProperties

    private void btnSwitchShapeeventChangeTool(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSwitchShapeeventChangeTool
        if (curMode == MODE_EDIT_2D) {
            finishEditMode();
            curMode = MODE_DRAW_3D;
        } else {
            curMode = MODE_DRAW_2D;
        }
        changeShapeMode(btnSwitchShape.isSelected() ? MODE_DRAW_3D : MODE_DRAW_2D);
        imagePane.getGridPane().setMode(btnSwitchShape.isSelected() ? CROOD_MODE_3D : toolBarBtnTgOxy.isSelected() ? CROOD_MODE_USER : CROOD_MODE_DEFAULT);

    }//GEN-LAST:event_btnSwitchShapeeventChangeTool

    private void eventZoomWheel(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_eventZoomWheel
        slZoom.setValue(slZoom.getValue() + evt.getWheelRotation());
    }//GEN-LAST:event_eventZoomWheel

    private void eventCopyPaste(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eventCopyPaste
        Point mousePos = scrollPanel.getMousePosition();
        if (evt.getSource() == toolBarBtnCopy || evt.getSource() == menuEditCopy) {
            if (wrapPane.isShow() == false) {
                return;
            }
            clipBoardShape2D = tShape2D.clone();
            clipBoardMode = CLIPBOARD_MODE_COPY;
            mouseTip.showTips(mousePos, " Copied");
            toolBarBtnPaste.setEnabled(true);
        }
        if (evt.getSource() == toolBarBtnPaste || evt.getSource() == menuEditPaste) {
            if (clipBoardShape2D == null) {
                return;
            }
            finishEditMode();
            Shape2D nShape = clipBoardShape2D.clone();
            imagePane.getCurrentLayer().addAndPaintShape(nShape);
            addUndoList(nShape, curMode);
            putShapeToEdit(nShape);
            Point viewM = viewPort.getMousePosition();
            wrapPane.setLocationWrapCenter2D(getImageCursorPos(viewM.x, viewM.y));
            wrapPane.repaintWrapWithFill();
            if (clipBoardMode == CLIPBOARD_MODE_CUT) {
                clipBoardShape2D = null;
                toolBarBtnPaste.setEnabled(false);
            }
            redoList.clear();
            toolBarBtnRedo.setEnabled(false);
            mouseTip.showTips(mousePos, " Pasted");
        }
        if (evt.getSource() == toolBarBtnCut || evt.getSource() == menuEditCut) {
            if (wrapPane.isShow() == false) {
                return;
            }
            clipBoardShape2D = tShape2D.clone();
            clipBoardMode = CLIPBOARD_MODE_CUT;
            wrapPane.clearWrap();
            imagePane.getCurrentLayer().removeShape(tShape2D);
            tShape2D = null;
            toolBarBtnPaste.setEnabled(true);
            mouseTip.showTips(mousePos, " Cutted");
        }
    }//GEN-LAST:event_eventCopyPaste

    private void eventRuler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eventRuler
        JToggleButton t = (JToggleButton) evt.getSource();
        isRuler = t.isSelected();
    }//GEN-LAST:event_eventRuler

    private void eventMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eventMouseExited
        if (toolBarBtnRuler.isSelected() || curTool.equals("brush")
                || curTool.equals("pencil") || curTool.equals("eraser") || curTool.equals("picker")) {
            rulerPanel.clearImg();
        }
        rulerPanel.repaint();
    }//GEN-LAST:event_eventMouseExited

    private void eventExtendBarChanged(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eventExtendBarChanged
        if (!wrapPane.isShow()) {
            return;
        }
        if (evt.getSource() == extendBarCbEdge) {
            tShape2D.setEdge(Integer.valueOf(extendBarCbEdge.getSelectedItem().toString()));
            wrapPane.repaintWrapWithFill();
        }
        if (evt.getSource() == extendBarCbStroke) {
            tShape2D.setStroke(extendBarCbStroke.getSelectedItem().toString());
            wrapPane.repaintWrapWithFill();
        }
        if (evt.getSource() == extendBarCbSize) {
            tShape2D.setSize(Integer.valueOf(extendBarCbSize.getSelectedItem().toString()));
            wrapPane.repaintWrapWithFill();
        }
        //Btn
        if (evt.getSource() == extendBarBtnTypesTri1) {
            float[] scale = tShape2D.getAnchorScale();
            scale[0] = 0;
            tShape2D.setAnchorScale(scale);
            wrapPane.repaintWrapWithFill();
        }
        if (evt.getSource() == extendBarBtnTypesTri2) {
            float[] scale = tShape2D.getAnchorScale();
            scale[0] = (float) 0.5;
            tShape2D.setAnchorScale(scale);
            wrapPane.repaintWrapWithFill();
        }
        if (evt.getSource() == extendBarBtnTypesTri3) {
            float[] scale = tShape2D.getAnchorScale();
            scale[0] = 1;
            tShape2D.setAnchorScale(scale);
            wrapPane.repaintWrapWithFill();
        }
        if (evt.getSource() == extendBarBtnTypesTrap1) {
            float[] scale = tShape2D.getAnchorScale();
            scale[0] = 0;
            tShape2D.setAnchorScale(scale);
            wrapPane.repaintWrapWithFill();
        }
        if (evt.getSource() == extendBarBtnTypesTrap2) {
            float[] scale = tShape2D.getAnchorScale();
            scale[0] = (float) (1.0 / 4);
            scale[1] = (float) (3.0 / 4);
            tShape2D.setAnchorScale(scale);
            wrapPane.repaintWrapWithFill();
        }
        if (evt.getSource() == extendBarBtnTypesTrap3) {
            float[] scale = tShape2D.getAnchorScale();
            scale[1] = 1;
            tShape2D.setAnchorScale(scale);
            wrapPane.repaintWrapWithFill();
        }
        if (evt.getSource() == extendBarBtnTypesCircle1 || evt.getSource() == extendBarBtnTypesSquare1) {
            int width = wrapPane.getSizeWrap2D().width;
            wrapPane.setSizeWrap2D(width, width);
            wrapPane.repaintWrapWithFill();
        }
    }//GEN-LAST:event_eventExtendBarChanged

    private void eventChangeShapeColor(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eventChangeShapeColor
        if (evt.getSource() == lbOutLineColor) {
            Color ret = JColorChooser.showDialog(this, "Choose outline color...", cColor);
            if (ret != null) {
                if (tShape2D != null) {
                    tShape2D.setOutlineColor(ret);
                    wrapPane.repaintWrapWithFill();
                } else {
                    tShape3D.setOutlineColor(ret);
                    wrapPane.paintWrap3D();
                }
            }
        }
        if (evt.getSource() == lbFillColor) {
            Color ret = JColorChooser.showDialog(this, "Choose solid color...", cColor);
            if (ret != null) {
                tShape2D.setSolidColor(ret);
                lbFillColor.setBackground(ret);
                lbFillColor.setText("");
                wrapPane.repaintWrapWithFill();
            }
        }
    }//GEN-LAST:event_eventChangeShapeColor

    private void eventCursorClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eventCursorClicked
     
    }//GEN-LAST:event_eventCursorClicked

    private void proPoints3DP1XActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_proPoints3DP1XActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_proPoints3DP1XActionPerformed

    private void proPoints3DP7ZActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_proPoints3DP7ZActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_proPoints3DP7ZActionPerformed

    private void eventActionWP3D(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eventActionWP3D
        if (evt.getSource() == WPControlBtnMove3D) {
            resetExtendBar();
            WPControlBtnRotate3D.setSelected(false);
            WPControlBtnFlip3D.setSelected(false);
            if (WPControlBtnMove3D.isSelected()) {
                showExtendBarShape(curTool);
                showExtendBar("move3D");
            }
        }
        if (evt.getSource() == WPControlBtnRotate3D) {
            resetExtendBar();
            WPControlBtnMove3D.setSelected(false);
            WPControlBtnFlip3D.setSelected(false);
            if (WPControlBtnRotate3D.isSelected()) {
                showExtendBarShape(curTool);
                showExtendBar("rotate3D");
            }
        }
        if (evt.getSource() == WPControlBtnFlip3D) {
            resetExtendBar();
            WPControlBtnRotate3D.setSelected(false);
            WPControlBtnMove3D.setSelected(false);
            if (WPControlBtnFlip3D.isSelected()) {
                showExtendBarShape(curTool);
                showExtendBar("flip3D");
            }
        }
        if (evt.getSource() == WPControl3DFlipOxyz) {
            wrapPane.trans3DShapeFlipO();
            wrapPane.paintWrap3D();
        }
        if (evt.getSource() == WPControlBtnRotate3D) {

        }
        if (evt.getSource() == WPControl3DRemove) {
                imagePane.getCurrentLayer().getShapeList3D().remove(tShape3D);
            imagePane.getTempLayer().clear();
            putShapeToHell(tShape3D);
        }

    }//GEN-LAST:event_eventActionWP3D

    private void eventChangeRotate3D(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_eventChangeRotate3D
        if (tShape3D==null) return;
        int x = sliderRotateX.getValue();
        int y = sliderRotateY.getValue();
        int z = sliderRotateZ.getValue();
        wrapPane.setAlphaDEG3D(x, y, z);
        wrapPane.paintWrap3D();
    }//GEN-LAST:event_eventChangeRotate3D

    private void eventChangeLocation3D(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_eventChangeLocation3D
        if (curMode != MODE_EDIT_3D) {
            return;
        }
        if (!WPControlBtnMove3D.isSelected()) {
            return;
        }
        int x = sliderLocationX.getValue();
        int y = sliderLocationY.getValue();
        int z = sliderLocationZ.getValue();
        wrapPane.setLocationWrapCenter3D(new Point3D(x, y, z));
        wrapPane.paintWrap3D();
    }//GEN-LAST:event_eventChangeLocation3D

    private void eventActionFlip3D(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eventActionFlip3D
        if (evt.getSource() == btnFlipOx) {
            wrapPane.trans3DShapeFlipAxis(Shape3D.X);
        }
        if (evt.getSource() == btnFlipOy) {
            wrapPane.trans3DShapeFlipAxis(Shape3D.Y);
        }
        if (evt.getSource() == btnFlipOz) {
            wrapPane.trans3DShapeFlipAxis(Shape3D.Z);
        }
        if (evt.getSource() == btnFlipOxy) {
            wrapPane.trans3DShapeFlipPlane(Shape3D.OXY);
        }
        if (evt.getSource() == btnFlipOyz) {
            wrapPane.trans3DShapeFlipPlane(Shape3D.OYZ);
        }
        if (evt.getSource() == btnFlipOxz) {
            wrapPane.trans3DShapeFlipPlane(Shape3D.OXZ);
        }

        wrapPane.paintWrap3D();
    }//GEN-LAST:event_eventActionFlip3D

    private void ABOUTJPAINT(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ABOUTJPAINT
        if (evt.getSource()==jMenuItem1) {
            JOptionPane.showMessageDialog(this, "JPaint Tool\nVersion: 1.0\nDate created: 15/3/07\nRelease: 26/5/07", "JPaint Tool", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(this.getClass().getResource("/rsc/shape/jPaintIcon.png")));
        }
        if (evt.getSource()==jMenuItem2) {
            JOptionPane.showMessageDialog(this, "Học Viện Công Nghệ Bưu Chính Viễn Thông\nBáo cáo: Kỹ Thuật Đồ Họa\nGiảng Viên: Dương Thanh Thảo\nLớp: D14CQCN02\n\nLương Ngọc Quý - N14DCCN132\nLý Quang Huy - N14DCCN143\nLê Nguyễn Chánh Tín - N14DCCN112 ", "JPaint Team", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_ABOUTJPAINT

    private void resetExtendBar() {
        lbCurTool.setVisible(false);
        extendBarBtnDone.setVisible(false);
        extendBarSpDone.setVisible(false);
        //Size
        spSizeBrush.setVisible(false);
        lbSizeBrush.setVisible(false);
        extendBarCbSize.setVisible(false);
        //style
        spStyleBrush.setVisible(false);
        lbStyleBrush.setVisible(false);
        btnStyleBrushCircle.setVisible(false);
        btnStyleBrushRect.setVisible(false);
        //alg line
        spLineAlg.setVisible(false);
        lbLineAlg.setVisible(false);
        cbLineAlg.setVisible(false);
        //edge
        extendBarCbEdge.setVisible(false);
        extendBarLbEdge.setVisible(false);
        extendBarSpEdge.setVisible(false);
        //stroke
        extendBarSpStroke.setVisible(false);
        extendBarLbStroke.setVisible(false);
        extendBarCbStroke.setVisible(false);
        //types
        extendBarSpTypes.setVisible(false);
        extendBarLbTypes.setVisible(false);
        extendBarBtnTypesTri1.setVisible(false);
        extendBarBtnTypesTri2.setVisible(false);
        extendBarBtnTypesTri3.setVisible(false);
        extendBarBtnTypesTrap1.setVisible(false);
        extendBarBtnTypesTrap2.setVisible(false);
        extendBarBtnTypesTrap3.setVisible(false);
        extendBarBtnTypesCircle1.setVisible(false);
        extendBarBtnTypesSquare1.setVisible(false);

        //Move3D
        extendBarSpLocation.setVisible(false);
        extendBarLbLocation.setVisible(false);
        extendBarLbLocationX.setVisible(false);
        extendBarLbLocationY.setVisible(false);
        extendBarLbLocationZ.setVisible(false);
        sliderLocationX.setVisible(false);
        sliderLocationY.setVisible(false);
        sliderLocationZ.setVisible(false);

        //Rotate 3D
        extendBarSPRotate3D.setVisible(false);
        extendBarLbRotate3D.setVisible(false);
        extendBarLbDX.setVisible(false);
        extendBarLbDY.setVisible(false);
        extendBarLbDZ.setVisible(false);
        sliderRotateX.setVisible(false);
        sliderRotateY.setVisible(false);
        sliderRotateZ.setVisible(false);

        //Flip 3D
        extendBarSpFlip.setVisible(false);
        extendBarLbFlip3D.setVisible(false);
        extendBarLbFlipAxis.setVisible(false);
        extendBarLbFlipPlane.setVisible(false);

        btnFlipOx.setVisible(false);
        btnFlipOy.setVisible(false);
        btnFlipOz.setVisible(false);
        btnFlipOxy.setVisible(false);
        btnFlipOyz.setVisible(false);
        btnFlipOxz.setVisible(false);
        sliderRotateX.setVisible(false);
        sliderRotateY.setVisible(false);
        sliderRotateZ.setVisible(false);
        
        

    }

    private void showExtendBar(String mod) {
        if (mod.equals("done")) {
            extendBarBtnDone.setVisible(true);
            extendBarSpDone.setVisible(true);
        }
        if (mod.equals("curtool")) {
            lbCurTool.setVisible(true);
        }
        if (mod.equals("sizebrush")) {
            spSizeBrush.setVisible(true);
            lbSizeBrush.setVisible(true);
            extendBarCbSize.setVisible(true);
        }
        if (mod.equals("typebrush")) {
            spStyleBrush.setVisible(true);
            lbStyleBrush.setVisible(true);
            btnStyleBrushCircle.setVisible(true);
            btnStyleBrushRect.setVisible(true);
        }
        if (mod.equals("linealg")) {
            spLineAlg.setVisible(true);
            lbLineAlg.setVisible(true);
            cbLineAlg.setVisible(true);
        }
        if (mod.equals("edge")) {
            extendBarCbEdge.setVisible(true);
            extendBarLbEdge.setVisible(true);
            extendBarSpEdge.setVisible(true);
        }
        if (mod.equals("stroke")) {
            extendBarCbStroke.setVisible(true);
            extendBarLbStroke.setVisible(true);
            extendBarSpStroke.setVisible(true);
        }
        if (mod.equals("typestri")) {
            extendBarSpTypes.setVisible(true);
            extendBarLbTypes.setVisible(true);
            extendBarBtnTypesTri1.setVisible(true);
            extendBarBtnTypesTri2.setVisible(true);
            extendBarBtnTypesTri3.setVisible(true);
        }
        if (mod.equals("typestrap")) {
            extendBarSpTypes.setVisible(true);
            extendBarLbTypes.setVisible(true);
            extendBarBtnTypesTrap1.setVisible(true);
            extendBarBtnTypesTrap2.setVisible(true);
            extendBarBtnTypesTrap3.setVisible(true);
        }
        if (mod.equals("typesellipse")) {
            extendBarSpTypes.setVisible(true);
            extendBarLbTypes.setVisible(true);
            extendBarBtnTypesCircle1.setVisible(true);
        }
        if (mod.equals("typesrect")) {
            extendBarSpTypes.setVisible(true);
            extendBarLbTypes.setVisible(true);
            extendBarBtnTypesSquare1.setVisible(true);
        }

        if (mod.equals("move3D")) {
            //Move3D
            extendBarSpLocation.setVisible(true);
            extendBarLbLocation.setVisible(true);
            extendBarLbLocationX.setVisible(true);
            extendBarLbLocationY.setVisible(true);
            extendBarLbLocationZ.setVisible(true);
            sliderLocationX.setVisible(true);
            sliderLocationY.setVisible(true);
            sliderLocationZ.setVisible(true);
        }
        if (mod.equals("rotate3D")) {
            //Rotate 3D

            extendBarSPRotate3D.setVisible(true);
            extendBarLbRotate3D.setVisible(true);
            extendBarLbDX.setVisible(true);
            extendBarLbDY.setVisible(true);
            extendBarLbDZ.setVisible(true);
            sliderRotateX.setVisible(true);
            sliderRotateY.setVisible(true);
            sliderRotateZ.setVisible(true);

        }
        if (mod.equals("flip3D")) {
            //Flip 3D
            extendBarSpFlip.setVisible(true);
            extendBarLbFlip3D.setVisible(true);
            extendBarLbFlipAxis.setVisible(true);
            extendBarLbFlipPlane.setVisible(true);

            btnFlipOx.setVisible(true);
            btnFlipOy.setVisible(true);
            btnFlipOz.setVisible(true);
            btnFlipOxy.setVisible(true);
            btnFlipOyz.setVisible(true);
            btnFlipOxz.setVisible(true);
            sliderRotateX.setVisible(false);
            sliderRotateY.setVisible(false);
            sliderRotateZ.setVisible(false);
        }

    }

    public void changeShapeMode(int mode) {
        if (mode == MODE_DRAW_2D) {
            curMode = MODE_DRAW_2D;
            for (JToggleButton btn : toolList3D) {
                btn.setVisible(false);
            }
            for (JToggleButton btn : toolList2D) {
                btn.setVisible(true);
            }
            spTool1.setVisible(true);
        } else if (mode == MODE_DRAW_3D) {
            curMode = MODE_DRAW_3D;
            for (JToggleButton btn : toolList2D) {
                btn.setVisible(false);
            }
            for (JToggleButton btn : toolList3D) {
                btn.setVisible(true);
            }
            spTool1.setVisible(false);
        }
    }

    public void setWrapPaneControl2DVisible(boolean b) {
        if (tShape2D != null) {
//            System.out.println("3594 Main: "+imagePane.getCurrentLayer().getLayerName());
            ArrayList<Shape2D> tList = imagePane.getCurrentLayer().getShapeList();
            WPControlMoveUp.setEnabled(true);
            WPControlMoveDown.setEnabled(true);
            if (tList.indexOf(tShape2D) == tList.size() - 1) {
                WPControlMoveUp.setEnabled(false);
            }
            if (tList.indexOf(tShape2D) == 0) {
                WPControlMoveDown.setEnabled(false);
            }
        }
        WPControlSp.setVisible(b);
        WPControlSp0.setVisible(b);
        WPControlBtnFlipLine.setVisible(b);
        WPControlBtnFlipOx.setVisible(b);
        WPControlBtnFlipOy.setVisible(b);
        WPControlBtnFlipPoint.setVisible(b);
        WPControlBtnRemove.setVisible(b);
        WPControlBtnRotateLeft.setVisible(b);
        WPControlBtnRotateRight.setVisible(b);
        WPControlMoveDown.setVisible(b);
        WPControlMoveUp.setVisible(b);
        WPControlNormalize.setVisible(b);
        toolBarBtnCopy.setEnabled(b);
        toolBarBtnCut.setEnabled(b);
    }

    public void setWrapPaneControl3DVisible(boolean b) {
        WPControl3DFlipOxyz.setVisible(b);
        WPControl3DSp1.setVisible(b);
        WPControl3DSp2.setVisible(b);
        WPControlBtnFlip3D.setVisible(b);
        WPControlBtnMove3D.setVisible(b);
        WPControlBtnRotate3D.setVisible(b);
        WPControl3DRemove.setVisible(b);
    }

    private void addDoList(Vector<LayerPanel> doList, LayerPanel lp) {
        doList.add(0, lp);
        toolBarBtnUndo.setEnabled(!undoList.isEmpty());
        toolBarBtnRedo.setEnabled(!redoList.isEmpty());
    }

    private void createNewFile() {
        if (curImage != null) {
            int re = JOptionPane.showConfirmDialog(this, imagePane.getFName() + " has unsaved changes.\nDo you want to save changes to " + imagePane.getFName()+"?", "JPaint", JOptionPane.YES_NO_CANCEL_OPTION);
            if (re == JOptionPane.YES_OPTION) {
                saveFile();
                return;
            }

            if (re == JOptionPane.NO_OPTION) {
                viewPort.remove(imagePane);
            }
            if (re == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }
        //Bang tao moi
        String[] size = {"320x240", "640x480", "800x600"};
        JTextField name = new JTextField(5);
        name.setText("Untitled");
        JTextField width = new JTextField(4);
        JTextField height = new JTextField(4);
        JComboBox cb = new JComboBox(size);
        JPanel option = new JPanel();
        GridLayout grid = new GridLayout(0, 2);
        grid.setVgap(2);
        option.setLayout(grid);
        option.add(new JLabel("File name: "));
        option.add(name);
        option.add(new JLabel("Size: "));
        option.add(cb);
        option.add(new JLabel("Width: "));
        option.add(width);
        option.add(new JLabel("Height: "));
        option.add(height);

        //Ket qua Option
        int result = JOptionPane.showConfirmDialog(this, option, "Create new file...", JOptionPane.OK_CANCEL_OPTION, -1);
        if (result == JOptionPane.OK_OPTION) {
            //   long startTime = System.currentTimeMillis();
            imagePane = new ImagePanel(name.getText(), this);
            //   long stopTime = System.currentTimeMillis();
            //System.out.println("4255 Main: "+ (stopTime-startTime));
            curImage = imagePane;
            int w = 0, h = 0;

            if (!width.getText().equals("")) {  //Custom
                w = Integer.valueOf(width.getText());
                h = Integer.valueOf(height.getText());

            } else {
                String[] item = cb.getSelectedItem().toString().split("x");
                w = Integer.valueOf(item[0]);
                h = Integer.valueOf(item[1]);
            }
            imagePane.setFName(name.getText());
            imagePane.setNewSize(w, h);
            imagePane.addNewLayer("Default", 0); //Create Layer white

            //imagePane.updateZoom(1);
            imagePane.setZoom(1);
            slZoom.setValue(1);

            wrapPane.setImagePane(imagePane);
            wrapPane.setNewSize();

            rulerPanel.setImagePanel(imagePane);
            rulerPanel.setNewSize();

            sliderLocationX.setMaximum(w / 2);
            sliderLocationX.setMinimum(-w / 2);

            sliderLocationY.setMaximum(h / 2);
            sliderLocationY.setMinimum(-h / 2);

            sliderLocationZ.setMaximum(h / 2);
            sliderLocationZ.setMinimum(-h / 2);

            //Update Form
            this.setTitle("JPaint "+ imagePane.getFName());

        }

    }

    public void changeTool3D(JToggleButton tool) {
        if (imagePane == null) {
            return;
        }
         //Reset wrapper
        finishEditMode();
        //Reset goc
        sliderRotateX.setValue(0);
        sliderRotateY.setValue(0);
        sliderRotateZ.setValue(0);
        //resetbutton
        WPControlBtnFlip3D.setSelected(false);
        WPControlBtnMove3D.setSelected(false);
        WPControlBtnRotate3D.setSelected(false);
        //Doi tool
        for (JToggleButton bt : toolList3D) {
            bt.setBorder(null);
            bt.setSelected(false);
            bt.setContentAreaFilled(true);
        }
        tool.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        tool.setSelected(true);
        tool.setContentAreaFilled(false);
        curTool = tool.getName();
        if (!curTool.equals("selector")) {
            curMode = MODE_DRAW_3D;
        }
    }

    public void changeTool2D(JToggleButton tool) {
        if (imagePane == null) {
            return;
        }
        //Reset wrapper
        finishEditMode();
        //Doi tool
        for (JToggleButton bt : toolList2D) {
            bt.setBorder(null);
            bt.setSelected(false);
            bt.setContentAreaFilled(true);
        }
        tool.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        tool.setSelected(true);
        tool.setContentAreaFilled(false);
        curTool = tool.getName();
        //Chuyen mode
        if (!curTool.equals("selector") && !curTool.equals("picker")
                && !curTool.equals("fill")) {
            curMode = MODE_DRAW_2D;
        }

    }

    public void showLayerToolAll(boolean ena) {
        layerToolBtnAdd.setEnabled(ena);
        layerToolBtnRemove.setEnabled(ena);
        layerToolBtnMerge.setEnabled(ena);
        layerToolBtnMoveUp.setEnabled(ena);
        layerToolBtnMoveDown.setEnabled(ena);
    }

    public void showLayerToolNew() {
        layerToolBtnAdd.setEnabled(true);
        layerToolBtnRemove.setEnabled(true);
        layerToolBtnMerge.setEnabled(false);
        layerToolBtnMoveUp.setEnabled(false);
        layerToolBtnMoveDown.setEnabled(false);
    }

    public void openFile() {
        if (curImage != null) {
            int re = JOptionPane.showConfirmDialog(this, imagePane.getFName() + " has unsaved changes.\nDo you want to save changes to " + imagePane.getFName()+"?", "JPaint", JOptionPane.YES_NO_CANCEL_OPTION);
            if (re == JOptionPane.YES_OPTION) {
                saveFile();
                return;
            }

            if (re == JOptionPane.NO_OPTION) {
                viewPort.remove(imagePane);
            }
            if (re == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }
        
        
        JFileChooser openF = new JFileChooser();
        openF.setDialogTitle("Open image file...");
        openF.setAcceptAllFileFilterUsed(false);
        openF.addChoosableFileFilter(new FileNameExtensionFilter("JPEG (*.jpg)", "jpg"));
        openF.addChoosableFileFilter(new FileNameExtensionFilter("PNG (*.png)", "png"));
        openF.addChoosableFileFilter(new FileNameExtensionFilter("JPaint File (*.jpt)", "jpt"));
        if (openF.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File savePath = openF.getSelectedFile();

            String ext = "";

            String name = savePath.getName();
            ext = name.substring(name.lastIndexOf(".") + 1);

            // JPT
            if (ext.equals("jpt")) {
                System.out.println(savePath);
                try (FileInputStream fout = new FileInputStream(savePath); ObjectInputStream oos = new ObjectInputStream(fout)) {
                    JPTFile openJPT = (JPTFile) oos.readObject();
                    ArrayList<LayerPanel> lpT = openJPT.lpList;
                    imagePane = new ImagePanel(openJPT.fname, this);
                    curImage = imagePane;
                    int w = openJPT.width, h = openJPT.height;

                    imagePane.setNewSize(w, h);
                    for (LayerPanel lp : lpT) {
                        imagePane.addNewLayer(lp);
                    }

                } catch (IOException ex) {
                    System.out.println("Fail");
                } catch (ClassNotFoundException ex) {
                    System.out.println("Class not found");
                }
            }
            //JPG
            if (ext.equals("jpg")) {
                try {
                    BufferedImage openImage = ImageIO.read(savePath);
                    
                    imagePane = new ImagePanel(savePath.getName(), this);
                    curImage = imagePane;
                    int w = openImage.getWidth(), h = openImage.getHeight();
                    imagePane.setNewSize(w, h);
                    imagePane.addNewLayer("Default", 0);
                    
                    Shape2D sT = new PixelShape();
                    sT.setLayerPanel(imagePane.getCurrentLayer());
                    sT.importImage(openImage);
                    sT.setVisible(true);
                    imagePane.getCurrentLayer().addAndPaintShape(sT);
                    
                } catch (IOException ex) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            imagePane.path=savePath;
            imagePane.updateZoom(1);
            slZoom.setValue(1);

            wrapPane.setImagePane(imagePane);
            wrapPane.setNewSize();
            rulerPanel.setImagePanel(imagePane);
            rulerPanel.setNewSize();
            this.setTitle("JPaint "+imagePane.getFName());
        }

    }
    
    public void saveAsFile() {
        JFileChooser openF = new JFileChooser();
        openF.setDialogTitle("Save as image...");
        openF.setAcceptAllFileFilterUsed(false);
        openF.addChoosableFileFilter(new FileNameExtensionFilter("JPEG (*.jpg)", "jpg"));
        openF.addChoosableFileFilter(new FileNameExtensionFilter("PNG (*.png)", "png"));
        openF.addChoosableFileFilter(new FileNameExtensionFilter("JPaint File (*.jpt)", "jpt"));
        openF.setSelectedFile(new File(imagePane.getFName()));
        if (openF.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File savePath = openF.getSelectedFile();
            String name = savePath.getName();
            String extOfPath = name.substring(name.lastIndexOf(".") + 1);
            String ext = openF.getFileFilter().getDescription();
            saveNonDialog(savePath, extOfPath, ext);
            this.setTitle("JPaint "+savePath.getName());
        }        
        
    }

    public void saveFile() {
        if (imagePane.path==null) {
        JFileChooser openF = new JFileChooser();
        openF.setDialogTitle("Save image...");
        openF.setAcceptAllFileFilterUsed(false);
        openF.addChoosableFileFilter(new FileNameExtensionFilter("JPEG (*.jpg)", "jpg"));
        openF.addChoosableFileFilter(new FileNameExtensionFilter("PNG (*.png)", "png"));
        openF.addChoosableFileFilter(new FileNameExtensionFilter("JPaint File (*.jpt)", "jpt"));
        openF.setSelectedFile(new File(imagePane.getFName()));
        if (openF.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File savePath = openF.getSelectedFile();
            String name = savePath.getName();
            String extOfPath = name.substring(name.lastIndexOf(".") + 1);
            String ext = openF.getFileFilter().getDescription();
            saveNonDialog(savePath, extOfPath, ext);
        }
        } else {
            File savePath = imagePane.path;
            String name = savePath.getName();
            String extOfPath = name.substring(name.lastIndexOf(".") + 1);
            saveNonDialog(savePath, extOfPath, extOfPath);
        }
    }
    
    public void saveNonDialog(File savePath, String extOfPath, String ext) {           
            // JPT
            if (ext.equals("JPaint File (*.jpt)")||ext.equals("jpt")) {
                if (!extOfPath.equals("jpt")) {
                    savePath = new File(savePath.toString().concat(".jpt"));
                }
                try (FileOutputStream fout = new FileOutputStream(savePath); ObjectOutputStream oos = new ObjectOutputStream(fout)) {
                    JPTFile saveJPT = new JPTFile(imagePane.getImageWidth(), imagePane.getImageHeight(), imagePane.getFName(), imagePane.getLayersList());
                    oos.writeObject(saveJPT);
                } catch (IOException ex) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            // JPG
            if (ext.equals("JPEG (*.jpg)")||ext.equals("jpg")) {
                if (!extOfPath.equals("jpg")) {
                    savePath = new File(savePath.toString().concat(".jpg"));
                }
                imagePane.updateZoom(1);
                BufferedImage saveImage = imagePane.getNormalImage(BufferedImage.TYPE_INT_RGB);
                //saveImage.
                try {
                    ImageIO.write(saveImage, "JPG", savePath);
                } catch (IOException ex) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            // PNG
            if (ext.equals("PNG (*.png)")||ext.equals("png")) {
                if (!extOfPath.equals("png")) {
                    savePath = new File(savePath.toString().concat(".png"));
                }
                imagePane.updateZoom(1);
                BufferedImage saveImage = imagePane.getNormalImage(BufferedImage.TYPE_INT_ARGB);

                try {
                    ImageIO.write(saveImage, "png", savePath);
                } catch (IOException ex) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }        
            imagePane.path=savePath;
            mouseTip.showTips(viewPort.getLocation(), " Saved");
    }

    public void showExtendBarShape(String s) {
        if (s.equals("box")) {
            lbCurTool.setText("Box Tool");
            lbCurTool.setIcon(new ImageIcon(getClass().getResource("/rsc/box.png")));
            showExtendBar("curtool");
            showExtendBar("done");
        }
        if (s.equals("pyramid")) {
            lbCurTool.setText("Pyramid Tool");
            lbCurTool.setIcon(new ImageIcon(getClass().getResource("/rsc/pyramid.png")));
            showExtendBar("curtool");
            showExtendBar("done");
        }
        if (s.equals("tetrahedron")) {
            lbCurTool.setText("Tetrahedron Tool");
            lbCurTool.setIcon(new ImageIcon(getClass().getResource("/rsc/pyramid.png")));
            showExtendBar("curtool");
            showExtendBar("done");
        }
        if (s.equals("tprism")) {
            lbCurTool.setText("Triangular Prism Tool");
            lbCurTool.setIcon(new ImageIcon(getClass().getResource("/rsc/pyramid.png")));
            showExtendBar("curtool");
            showExtendBar("done");
        }
        if (s.equals("hprism")) {
            lbCurTool.setText("Hexagonal Prism Tool");
            lbCurTool.setIcon(new ImageIcon(getClass().getResource("/rsc/pyramid.png")));
            showExtendBar("curtool");
            showExtendBar("done");
        }
        if (s.equals("eraser")) {
            lbCurTool.setText("Eraser Tool");
            lbCurTool.setIcon(new ImageIcon(getClass().getResource("/rsc/eraser.png")));
            showExtendBar("curtool");
            showExtendBar("sizebrush");
            //showExtendBar("typebrush");
        }
        if (s.equals("brush")) {
            lbCurTool.setText("Brush Tool");
            lbCurTool.setIcon(new ImageIcon(getClass().getResource("/rsc/brush.png")));
            showExtendBar("curtool");
            showExtendBar("sizebrush");
            //showExtendBar("typebrush");
        }
        if (s.equals("pencil")) {
            lbCurTool.setText("Pencil Tool");
            lbCurTool.setIcon(new ImageIcon(getClass().getResource("/rsc/pencil.png")));
            showExtendBar("curtool");
        }
        if (s.equals("picker")) {
            lbCurTool.setText("Picker Tool");
            lbCurTool.setIcon(new ImageIcon(getClass().getResource("/rsc/picker.png")));
            showExtendBar("curtool");
        }
        if (s.equals("selector")) {
            lbCurTool.setText("Selector Tool");
            lbCurTool.setIcon(new ImageIcon(getClass().getResource("/rsc/mouse.png")));
            showExtendBar("curtool");
        }

        if (s.equals("line")) {
            lbCurTool.setText("Line Tool");
            lbCurTool.setIcon(new ImageIcon(getClass().getResource("/rsc/line.png")));
            showExtendBar("curtool");
            showExtendBar("done");
            showExtendBar("sizebrush");
            //  showExtendBar("linealg");
            showExtendBar("stroke");
        }
        if (s.equals("para")) {
            lbCurTool.setText("Parallelogram Tool");
            lbCurTool.setIcon(new ImageIcon(getClass().getResource("/rsc/parallelogram.png")));
            showExtendBar("curtool");
            showExtendBar("done");
            showExtendBar("sizebrush");
            //  showExtendBar("linealg");
            showExtendBar("stroke");
        }
        if (s.equals("ellipse")) {
            lbCurTool.setText("Ellipse Tool");
            lbCurTool.setIcon(new ImageIcon(getClass().getResource("/rsc/circle.png")));
            showExtendBar("curtool");
            showExtendBar("done");
            showExtendBar("sizebrush");
            showExtendBar("typesellipse");
            showExtendBar("stroke");
        }
        if (s.equals("trapezoid")) {
            lbCurTool.setText("Trapezoid Tool");
            lbCurTool.setIcon(new ImageIcon(getClass().getResource("/rsc/trapezoid.png")));
            showExtendBar("curtool");
            showExtendBar("done");
            showExtendBar("sizebrush");
            //  showExtendBar("linealg");
            showExtendBar("stroke");
            showExtendBar("typestrap");
        }
        if (s.equals("diamond")) {
            lbCurTool.setText("Diamond Tool");
            lbCurTool.setIcon(new ImageIcon(getClass().getResource("/rsc/diamond.png")));
            showExtendBar("curtool");
            showExtendBar("done");
            showExtendBar("sizebrush");
            //  showExtendBar("linealg");
            showExtendBar("stroke");
        }
        if (s.equals("triangle")) {
            lbCurTool.setText("Triangle Tool");
            lbCurTool.setIcon(new ImageIcon(getClass().getResource("/rsc/triangle.png")));
            showExtendBar("curtool");
            showExtendBar("done");
            showExtendBar("sizebrush");
            // showExtendBar("linealg");
            showExtendBar("stroke");
            showExtendBar("typestri");
        }
        if (s.equals("rect")) {
            lbCurTool.setText("Rectangle Tool");
            lbCurTool.setIcon(new ImageIcon(getClass().getResource("/rsc/square.png")));
            showExtendBar("curtool");
            showExtendBar("done");
            showExtendBar("sizebrush");
            showExtendBar("typesrect");
            showExtendBar("stroke");
        }
        if (s.equals("polygons")) {
            lbCurTool.setText("Polygons Tool");
            lbCurTool.setIcon(new ImageIcon(getClass().getResource("/rsc/polygon.png")));
            showExtendBar("done");
            showExtendBar("sizebrush");
            showExtendBar("curtool");
            showExtendBar("edge");
            showExtendBar("stroke");
        }

    }

    private void customInit() {

        panelProperties.getComponent(1).setVisible(false);
        wrapPane = new WrapPane2D(this, panelProperties);
        rulerPanel = new RulerPanel(toolBarBtnRuler);
        viewPort.add(wrapPane, 0);
        viewPort.add(rulerPanel, 0);

        wrapPane.setSrollPane(scrollPanel);
        wrapPane.setNewSize();
        rulerPanel.setNewSize();

        resetExtendBar();
        this.setWrapPaneControl3DVisible(false);
        this.setWrapPaneControl2DVisible(false);
        mouseTip = new MouseTip();
        viewPort.add(mouseTip, 0);
        //Cursor
        csPointer = getToolkit().createCustomCursor(new ImageIcon(getClass().getResource("/rsc/cursor/pointer.gif")).getImage(), new Point(15, 15), "pointer");
        csSelector = getToolkit().createCustomCursor(new ImageIcon(getClass().getResource("/rsc/cursor/selector.gif")).getImage(), new Point(0, 0), "selector");
        csRectSe = getToolkit().createCustomCursor(new ImageIcon(getClass().getResource("/rsc/cursor/rectSelection.gif")).getImage(), new Point(15, 15), "selector");
        csLasso = getToolkit().createCustomCursor(new ImageIcon(getClass().getResource("/rsc/cursor/lassoSelection.gif")).getImage(), new Point(15, 15), "lasso");
        csPencil = getToolkit().createCustomCursor(new ImageIcon(getClass().getResource("/rsc/cursor/pencil.gif")).getImage(), new Point(15, 15), "pencil");
        csBrush = getToolkit().createCustomCursor(new ImageIcon(getClass().getResource("/rsc/cursor/brush.gif")).getImage(), new Point(15, 15), "brush");
        csWand = getToolkit().createCustomCursor(new ImageIcon(getClass().getResource("/rsc/cursor/wand.gif")).getImage(), new Point(7, 7), "wand");
        csPicker = getToolkit().createCustomCursor(new ImageIcon(getClass().getResource("/rsc/cursor/picker.gif")).getImage(), new Point(6, 24), "picker");
        csFill = getToolkit().createCustomCursor(new ImageIcon(getClass().getResource("/rsc/cursor/fill.gif")).getImage(), new Point(6, 24), "fill");
        csEraser = getToolkit().createCustomCursor(new ImageIcon(getClass().getResource("/rsc/cursor/eraser.gif")).getImage(), new Point(6, 24), "eraser");
        csLine = getToolkit().createCustomCursor(new ImageIcon(getClass().getResource("/rsc/cursor/line.gif")).getImage(), new Point(7, 7), "line");
        csRect = getToolkit().createCustomCursor(new ImageIcon(getClass().getResource("/rsc/cursor/rect.gif")).getImage(), new Point(7, 7), "rect");
        csTri = getToolkit().createCustomCursor(new ImageIcon(getClass().getResource("/rsc/cursor/triangle.gif")).getImage(), new Point(7, 7), "triangle");
        csPara = getToolkit().createCustomCursor(new ImageIcon(getClass().getResource("/rsc/cursor/para.gif")).getImage(), new Point(7, 7), "para");
        csTrap = getToolkit().createCustomCursor(new ImageIcon(getClass().getResource("/rsc/cursor/trapezoid.gif")).getImage(), new Point(7, 7), "trapezoid");
        csPoly = getToolkit().createCustomCursor(new ImageIcon(getClass().getResource("/rsc/cursor/poly.gif")).getImage(), new Point(7, 7), "poly");
        csDia = getToolkit().createCustomCursor(new ImageIcon(getClass().getResource("/rsc/cursor/diamond.gif")).getImage(), new Point(7, 7), "diamond");
        csResize[0] = csResize[4] = getToolkit().createCustomCursor(new ImageIcon(getClass().getResource("/rsc/cursor/resize0.gif")).getImage(), new Point(15, 15), "resize0");
        csResize[1] = csResize[5] = getToolkit().createCustomCursor(new ImageIcon(getClass().getResource("/rsc/cursor/resize1.gif")).getImage(), new Point(15, 15), "resize1");
        csResize[2] = csResize[6] = getToolkit().createCustomCursor(new ImageIcon(getClass().getResource("/rsc/cursor/resize2.gif")).getImage(), new Point(15, 15), "resize2");
        csResize[3] = csResize[7] = getToolkit().createCustomCursor(new ImageIcon(getClass().getResource("/rsc/cursor/resize3.gif")).getImage(), new Point(15, 15), "resize3");
        csRotate[0] = getToolkit().createCustomCursor(new ImageIcon(getClass().getResource("/rsc/cursor/rotate0.gif")).getImage(), new Point(15, 15), "rotate0");
        csRotate[2] = getToolkit().createCustomCursor(new ImageIcon(getClass().getResource("/rsc/cursor/rotate2.gif")).getImage(), new Point(15, 15), "rotate2");
        csRotate[4] = getToolkit().createCustomCursor(new ImageIcon(getClass().getResource("/rsc/cursor/rotate4.gif")).getImage(), new Point(15, 15), "rotate4");
        csRotate[6] = getToolkit().createCustomCursor(new ImageIcon(getClass().getResource("/rsc/cursor/rotate6.gif")).getImage(), new Point(15, 15), "rotate6");
        csHandOpen = getToolkit().createCustomCursor(new ImageIcon(getClass().getResource("/rsc/cursor/handOpen.gif")).getImage(), new Point(15, 15), "handOpen");
        csHandClose = getToolkit().createCustomCursor(new ImageIcon(getClass().getResource("/rsc/cursor/handClose.gif")).getImage(), new Point(15, 15), "handClose");
        csMoveH = getToolkit().createCustomCursor(new ImageIcon(getClass().getResource("/rsc/cursor/moveH.gif")).getImage(), new Point(15, 15), "moveH");
        csMoveP = getToolkit().createCustomCursor(new ImageIcon(getClass().getResource("/rsc/cursor/moveP.gif")).getImage(), new Point(15, 15), "moveP");
        csFLipPoint = getToolkit().createCustomCursor(new ImageIcon(getClass().getResource("/rsc/cursor/pointer.gif")).getImage(), new Point(15, 15), "pointer");
        csRoateAtPoint = getToolkit().createCustomCursor(new ImageIcon(getClass().getResource("/rsc/cursor/rotatePoint.gif")).getImage(), new Point(15, 15), "rotateAtPoint");
         cs3D = getToolkit().createCustomCursor(new ImageIcon(getClass().getResource("/rsc/cursor/3d.gif")).getImage(), new Point(7, 7), "3d");
          csEllipse = getToolkit().createCustomCursor(new ImageIcon(getClass().getResource("/rsc/cursor/ellipse.gif")).getImage(), new Point(7, 7), "ellipse");
//Tool Init
        btnSelector.setName("selector");
        btnSelection.setName("rselect");
        btnLasso.setName("lselect");
        btnPencil.setName("pencil");
        btnBrush.setName("brush");
        btnPicker.setName("picker");
        btnFill.setName("fill");
        btnEraser.setName("eraser");
        btnWand.setName("wand");
        btnLine.setName("line");
        btnRect.setName("rect");
        btnTrapezoid.setName("trapezoid");
        btnTriangle.setName("triangle");
        btnPara.setName("para");
        btnDiamond.setName("diamond");
        btnPoly.setName("polygons");
        btnElip.setName("ellipse");

        toolList2D.add(btnSelector);
        toolList2D.add(btnSelection);
        toolList2D.add(btnLasso);
        toolList2D.add(btnPencil);
        toolList2D.add(btnBrush);
        toolList2D.add(btnPicker);
        toolList2D.add(btnFill);
        toolList2D.add(btnEraser);
        toolList2D.add(btnWand);
        toolList2D.add(btnLine);
        toolList2D.add(btnRect);
        toolList2D.add(btnTrapezoid);
        toolList2D.add(btnTriangle);
        toolList2D.add(btnPara);
        toolList2D.add(btnDiamond);
        toolList2D.add(btnPoly);
        toolList2D.add(btnElip);

        //toolList3D.add(btnSelector);
        toolList3D.add(btnBox);
        toolList3D.add(btnPyramid);
        toolList3D.add(btnTetrahedron);
        toolList3D.add(btnTriangularPrism);
        //Color Init
        colorList.add(lbColor1);
        colorList.add(lbColor2);
        colorList.add(lbColor3);
        colorList.add(lbColor4);
        colorList.add(lbColor5);
        colorList.add(lbColor6);
        colorList.add(lbColor7);
        colorList.add(lbColor8);
        colorList.add(lbColor9);
        colorList.add(lbColor10);
        colorList.add(lbColor11);
        colorList.add(lbColor12);
        colorList.add(lbColor13);
        colorList.add(lbColor14);
        colorList.add(lbColor15);
        colorList.add(lbColor16);
        colorList.add(lbColor17);
        colorList.add(lbColor18);
        colorList.add(lbColor19);
        colorList.add(lbColor20);
        //Change tool
        changeShapeMode(MODE_DRAW_2D);
    }

    public void setCursor(JPanel con, String cur) {
        if (cur.equals("selector")) {
            con.setCursor(csSelector);
            return;
        }
        if (cur.equals("ellipse")) {
            con.setCursor(csEllipse);
        }
        if (cur.equals("rselect")) {
            con.setCursor(csRectSe);
            return;
        }
        if (cur.equals("lselect")) {
            con.setCursor(csLasso);
            return;
        }
        if (cur.equals("pencil")) {
            con.setCursor(csPencil);
            return;
        }
        if (cur.equals("brush")) {
            con.setCursor(csBrush);
            return;
        }
        if (cur.equals("picker")) {
            con.setCursor(csPicker);
            return;
        }
        if (cur.equals("fill")) {
            con.setCursor(csFill);
            return;
        }
        if (cur.equals("eraser")) {
            con.setCursor(csEraser);
            return;
        }
        if (cur.equals("wand")) {
            con.setCursor(csWand);
            return;
        }
        if (cur.equals("line")) {
            con.setCursor(csLine);
            return;
        }
        if (cur.equals("rect")) {
            con.setCursor(csRect);
            return;
        }
        if (cur.equals("trapezoid")) {
            con.setCursor(csTrap);
            return;
        }
        if (cur.equals("triangle")) {
            con.setCursor(csTri);
            return;
        }
        if (cur.equals("para")) {
            con.setCursor(csPara);
            return;
        }
        if (cur.equals("diamond")) {
            con.setCursor(csDia);
            return;
        }
        if (cur.equals("polygons")) {
            con.setCursor(csPoly);
            return;
        }
        if (cur.equals("box")||cur.equals("pyramid")||cur.equals("tetrahedron")||cur.equals("tprism")) {
            con.setCursor(cs3D);
            return;
        }
    }

    public void addUndoList(Shape2D t, int curMode) {
        if (addedUndoList == true) {
            return;
        }
//        System.out.println("3347 Main: Added Undo " + curMode);
        addedUndoList = true;
        int index = imagePane.getCurrentLayer().getShapeList().indexOf(t);
        undoList.add(0, new DoItem(curMode, t.clone(), index, imagePane.getCurrentLayer()));
        toolBarBtnUndo.setEnabled(!undoList.isEmpty());
        toolBarBtnRedo.setEnabled(!redoList.isEmpty());
    }

    public Color getCurrentColor() {
        Color p = color[colorMode];
        return new Color(p.getRed(), p.getGreen(), p.getBlue(), 255);
    }

    public void putShapeToHell(Shape2D sp) {
        tShape2D = null;
        if (wrapPane.isShow()) {
            wrapPane.clearWrap();
        }
        curMode = MODE_DRAW_2D;
    }
        public void putShapeToHell(Shape3D sp) {
        tShape3D = null;
        if (wrapPane.isShow()) {
            wrapPane.clearWrap();
        }
        curMode = MODE_DRAW_3D;
    }

    public void putShapeToEdit(Shape2D sp) {
        if (sp.getType() == Shape2D.TYPE_PENCIL || sp.getType() == Shape2D.TYPE_PIXEL) {
            return;
        }
        tShape2D = sp;
        sp.setVisible(false);
        wrapPane.create(sp);
        wrapPane.paintWrap2D();
        wrapPane.repaintWrapWithFill();
        curMode = MODE_EDIT_2D;
    }

    public void setCurColor(Color c) {
        color[colorMode] = c;
        colorC[colorMode].setBackground(c);
    }

    public JLabel getLbImagePos() {
        return lbImagePos;
    }

    public JLabel getLbImageSize() {
        return lbImageSize;
    }

    public boolean isGrid() {
        return grid;
    }

    public void setRGBSlider(Color p) {
        slRed.setValue(p.getRed());
        slGreen.setValue(p.getGreen());
        slBlue.setValue(p.getBlue());
        lbColorRed.setText(String.valueOf(p.getRed()));
        lbColorGreen.setText(String.valueOf(p.getGreen()));
        lbColorBlue.setText(String.valueOf(p.getBlue()));
    }

    public JPanel getViewPort() {
        return viewPort;
    }

    public JScrollPane getScrollPanel() {
        return scrollPanel;
    }

    public JList<ListItem> getListLayer() {
        return listLayer;
    }

    public Point getImageCursorPos(int x, int y) {
        if (imagePane == null) {
            return null;
        }
        Point p = imagePane.getLocation();
        Point onImg = new Point((x - p.x) / zoom, (y - p.y) / zoom);
        return onImg;
    }

    public Point convertPointToUserMode(Point p) {
        Point onImg = new Point(p.x - imagePane.getImageWidth() / 2, -(p.y - imagePane.getImageHeight() / 2));
        return onImg;
    }

    public Point convertPointToComputerMode(Point p) {
        if (gridMode != CROOD_MODE_USER) {
            return p;
        }
        Point onImg = new Point(p.x + imagePane.getImageWidth() / 2, -(p.y - imagePane.getImageHeight() / 2));
        return onImg;
    }

    public void setImagePos(Point p) {
        Point t = getImageCursorPos(p.x, p.y);
        rulerPanel.setRuler(p, Integer.valueOf(extendBarCbSize.getSelectedItem().toString()), slZoom.getValue(), curTool);
        if (gridMode == 0) {
            lbImagePos.setText(t.x + ", " + t.y);
        } else {
            t = convertPointToUserMode(t);
            lbImagePos.setText(t.x + ", " + t.y);
        }
    }

    public void drawTemp2Layer() {
        LayerPanel p = imagePane.getCurrentLayer();
        tShape2D.paintShapeTo(p);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton WPControl3DFlipOxyz;
    private javax.swing.JButton WPControl3DRemove;
    private javax.swing.JToolBar.Separator WPControl3DSp1;
    private javax.swing.JToolBar.Separator WPControl3DSp2;
    private javax.swing.JToggleButton WPControlBtnFlip3D;
    private javax.swing.JButton WPControlBtnFlipLine;
    private javax.swing.JButton WPControlBtnFlipOx;
    private javax.swing.JButton WPControlBtnFlipOy;
    private javax.swing.JButton WPControlBtnFlipPoint;
    private javax.swing.JToggleButton WPControlBtnMove3D;
    private javax.swing.JButton WPControlBtnRemove;
    private javax.swing.JToggleButton WPControlBtnRotate3D;
    private javax.swing.JButton WPControlBtnRotateLeft;
    private javax.swing.JButton WPControlBtnRotateRight;
    private javax.swing.JButton WPControlMoveDown;
    private javax.swing.JButton WPControlMoveUp;
    private javax.swing.JButton WPControlNormalize;
    private javax.swing.JToolBar.Separator WPControlSp;
    private javax.swing.JToolBar.Separator WPControlSp0;
    private javax.swing.JToggleButton btnBox;
    private javax.swing.JToggleButton btnBrush;
    private javax.swing.JToggleButton btnDiamond;
    private javax.swing.JToggleButton btnElip;
    private javax.swing.JToggleButton btnEraser;
    private javax.swing.JToggleButton btnFill;
    private javax.swing.JButton btnFlipOx;
    private javax.swing.JButton btnFlipOxy;
    private javax.swing.JButton btnFlipOxz;
    private javax.swing.JButton btnFlipOy;
    private javax.swing.JButton btnFlipOyz;
    private javax.swing.JButton btnFlipOz;
    private javax.swing.JToggleButton btnLasso;
    private javax.swing.JToggleButton btnLine;
    private javax.swing.JToggleButton btnPara;
    private javax.swing.JToggleButton btnPencil;
    private javax.swing.JToggleButton btnPicker;
    private javax.swing.JToggleButton btnPoly;
    private javax.swing.JToggleButton btnPyramid;
    private javax.swing.JToggleButton btnRect;
    private javax.swing.JToggleButton btnSelection;
    private javax.swing.JToggleButton btnSelector;
    private javax.swing.JToggleButton btnStyleBrushCircle;
    private javax.swing.JToggleButton btnStyleBrushRect;
    private javax.swing.JToggleButton btnSwitchShape;
    private javax.swing.JToggleButton btnTetrahedron;
    private javax.swing.JToggleButton btnTrapezoid;
    private javax.swing.JToggleButton btnTriangle;
    private javax.swing.JToggleButton btnTriangularPrism;
    private javax.swing.JToggleButton btnWand;
    private javax.swing.JComboBox<String> cbLineAlg;
    private javax.swing.JDesktopPane deskPane;
    private javax.swing.JToolBar extendBar;
    private javax.swing.JButton extendBarBtnDone;
    private javax.swing.JButton extendBarBtnTypesCircle1;
    private javax.swing.JButton extendBarBtnTypesSquare1;
    private javax.swing.JButton extendBarBtnTypesTrap1;
    private javax.swing.JButton extendBarBtnTypesTrap2;
    private javax.swing.JButton extendBarBtnTypesTrap3;
    private javax.swing.JButton extendBarBtnTypesTri1;
    private javax.swing.JButton extendBarBtnTypesTri2;
    private javax.swing.JButton extendBarBtnTypesTri3;
    private javax.swing.JComboBox<String> extendBarCbEdge;
    private javax.swing.JComboBox<String> extendBarCbSize;
    private javax.swing.JComboBox<String> extendBarCbStroke;
    private javax.swing.JLabel extendBarLbDX;
    private javax.swing.JLabel extendBarLbDY;
    private javax.swing.JLabel extendBarLbDZ;
    private javax.swing.JLabel extendBarLbEdge;
    private javax.swing.JLabel extendBarLbFlip3D;
    private javax.swing.JLabel extendBarLbFlipAxis;
    private javax.swing.JLabel extendBarLbFlipPlane;
    private javax.swing.JLabel extendBarLbLocation;
    private javax.swing.JLabel extendBarLbLocationX;
    private javax.swing.JLabel extendBarLbLocationY;
    private javax.swing.JLabel extendBarLbLocationZ;
    private javax.swing.JLabel extendBarLbRotate3D;
    private javax.swing.JLabel extendBarLbStroke;
    private javax.swing.JLabel extendBarLbTypes;
    private javax.swing.JToolBar.Separator extendBarSPRotate3D;
    private javax.swing.JToolBar.Separator extendBarSpDone;
    private javax.swing.JToolBar.Separator extendBarSpEdge;
    private javax.swing.JToolBar.Separator extendBarSpFlip;
    private javax.swing.JToolBar.Separator extendBarSpLocation;
    private javax.swing.JToolBar.Separator extendBarSpStroke;
    private javax.swing.JToolBar.Separator extendBarSpTypes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JButton layerToolBtnAdd;
    private javax.swing.JButton layerToolBtnMerge;
    private javax.swing.JButton layerToolBtnMoveDown;
    private javax.swing.JButton layerToolBtnMoveUp;
    private javax.swing.JButton layerToolBtnRemove;
    private javax.swing.JLabel lbColor1;
    private javax.swing.JLabel lbColor10;
    private javax.swing.JLabel lbColor11;
    private javax.swing.JLabel lbColor12;
    private javax.swing.JLabel lbColor13;
    private javax.swing.JLabel lbColor14;
    private javax.swing.JLabel lbColor15;
    private javax.swing.JLabel lbColor16;
    private javax.swing.JLabel lbColor17;
    private javax.swing.JLabel lbColor18;
    private javax.swing.JLabel lbColor19;
    private javax.swing.JLabel lbColor2;
    private javax.swing.JLabel lbColor20;
    private javax.swing.JLabel lbColor3;
    private javax.swing.JLabel lbColor4;
    private javax.swing.JLabel lbColor5;
    private javax.swing.JLabel lbColor6;
    private javax.swing.JLabel lbColor7;
    private javax.swing.JLabel lbColor8;
    private javax.swing.JLabel lbColor9;
    private javax.swing.JLabel lbColorBlue;
    private javax.swing.JLabel lbColorGreen;
    private javax.swing.JLabel lbColorRed;
    private javax.swing.JLabel lbCurColor;
    private javax.swing.JLabel lbCurTool;
    public javax.swing.JLabel lbFillColor;
    private javax.swing.JLabel lbImagePos;
    private javax.swing.JLabel lbImageSize;
    private javax.swing.JLabel lbLineAlg;
    public javax.swing.JLabel lbOutLineColor;
    private javax.swing.JLabel lbPriColor;
    private javax.swing.JLabel lbSecColor;
    private javax.swing.JLabel lbSizeBrush;
    private javax.swing.JLabel lbStyleBrush;
    private javax.swing.JLabel lbZoom;
    private javax.swing.JList<ListItem> listLayer;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuEdit;
    private javax.swing.JMenuItem menuEditCopy;
    private javax.swing.JMenuItem menuEditCut;
    private javax.swing.JMenuItem menuEditPaste;
    private javax.swing.JMenuItem menuEditRedo;
    private javax.swing.JMenuItem menuEditUndo;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenuItem menuFileExit;
    private javax.swing.JMenuItem menuFileNew;
    private javax.swing.JMenuItem menuFileOpen;
    private javax.swing.JMenuItem menuFileSave;
    private javax.swing.JMenuItem menuFileSaveAs;
    private javax.swing.JMenu menuLayer;
    private javax.swing.JMenuItem menuLayerAdd;
    private javax.swing.JMenuItem menuLayerMerge;
    private javax.swing.JMenuItem menuLayerMoveDown;
    private javax.swing.JMenuItem menuLayerMoveUp;
    private javax.swing.JMenuItem menuLayerRemove;
    private javax.swing.JMenu menuView;
    private javax.swing.JCheckBoxMenuItem menuViewGrid;
    private javax.swing.JCheckBoxMenuItem menuViewRuler;
    private javax.swing.JCheckBoxMenuItem menuViewUserMode;
    private javax.swing.JPanel panelColors;
    private javax.swing.JPanel panelLayers;
    private javax.swing.JPanel panelProperties;
    public javax.swing.JScrollPane panelPropertiesScrollPane;
    private javax.swing.JPanel panelPropertiesViewPort;
    private javax.swing.JLabel panelPropertieslb1;
    private javax.swing.JPanel panelToolbox;
    private javax.swing.JButton proChangedFinish;
    public javax.swing.JPanel proColorPanel;
    public javax.swing.JButton proColorbtnReset;
    private javax.swing.JLabel proColorlb1;
    public javax.swing.JLabel proColorlbOutline;
    public javax.swing.JLabel proColorlbSolid;
    public javax.swing.JPanel proLocation3DPanel;
    private javax.swing.JLabel proLocationLbX;
    private javax.swing.JLabel proLocationLbY;
    private javax.swing.JLabel proLocationLbZ;
    public javax.swing.JPanel proLocationPanel;
    public javax.swing.JTextField proLocationTxtX;
    public javax.swing.JTextField proLocationTxtY;
    public javax.swing.JTextField proLocationTxtZ;
    public javax.swing.JButton proLocationbtnXYReset;
    private javax.swing.JLabel proLocationlb1;
    private javax.swing.JLabel proLocationlb10;
    private javax.swing.JLabel proLocationlb11;
    public javax.swing.JLabel proLocationlb2;
    public javax.swing.JLabel proLocationlb3;
    private javax.swing.JLabel proLocationlb4;
    private javax.swing.JLabel proLocationlb8;
    private javax.swing.JLabel proLocationlb9;
    public javax.swing.JTextField proLocationtxtX;
    public javax.swing.JTextField proLocationtxtY;
    public javax.swing.JTextField proPoints3DP1X;
    public javax.swing.JTextField proPoints3DP1Y;
    public javax.swing.JTextField proPoints3DP1Z;
    public javax.swing.JTextField proPoints3DP2X;
    public javax.swing.JTextField proPoints3DP2Y;
    public javax.swing.JTextField proPoints3DP2Z;
    public javax.swing.JTextField proPoints3DP3X;
    public javax.swing.JTextField proPoints3DP3Y;
    public javax.swing.JTextField proPoints3DP3Z;
    public javax.swing.JTextField proPoints3DP4X;
    public javax.swing.JTextField proPoints3DP4Y;
    public javax.swing.JTextField proPoints3DP4Z;
    public javax.swing.JTextField proPoints3DP5X;
    public javax.swing.JTextField proPoints3DP5Y;
    public javax.swing.JTextField proPoints3DP5Z;
    public javax.swing.JTextField proPoints3DP6X;
    public javax.swing.JTextField proPoints3DP6Y;
    public javax.swing.JTextField proPoints3DP6Z;
    public javax.swing.JTextField proPoints3DP7X;
    public javax.swing.JTextField proPoints3DP7Y;
    public javax.swing.JTextField proPoints3DP7Z;
    public javax.swing.JTextField proPoints3DP8X;
    public javax.swing.JTextField proPoints3DP8Y;
    public javax.swing.JTextField proPoints3DP8Z;
    public javax.swing.JPanel proPoints3DPanel;
    public javax.swing.JTextField proPointsP1X;
    public javax.swing.JTextField proPointsP1Y;
    public javax.swing.JPanel proPointsPanel;
    private javax.swing.JLabel proPointslb1;
    public javax.swing.JLabel proPointslb10;
    public javax.swing.JLabel proPointslb11;
    public javax.swing.JLabel proPointslb12;
    public javax.swing.JLabel proPointslb13;
    public javax.swing.JLabel proPointslb14;
    public javax.swing.JLabel proPointslb15;
    public javax.swing.JLabel proPointslb16;
    public javax.swing.JLabel proPointslb17;
    public javax.swing.JLabel proPointslb18;
    public javax.swing.JLabel proPointslb19;
    public javax.swing.JLabel proPointslb2;
    public javax.swing.JLabel proPointslb20;
    public javax.swing.JLabel proPointslb21;
    public javax.swing.JLabel proPointslb3;
    public javax.swing.JLabel proPointslb4;
    public javax.swing.JLabel proPointslb5;
    public javax.swing.JLabel proPointslb6;
    public javax.swing.JLabel proPointslb7;
    public javax.swing.JLabel proPointslb8;
    public javax.swing.JLabel proPointslb9;
    public javax.swing.JLabel proPointslbP1;
    public javax.swing.JLabel proPointslbP2;
    public javax.swing.JLabel proPointslbP3;
    public javax.swing.JLabel proPointslbP4;
    public javax.swing.JTextField proPointstxtP2X;
    public javax.swing.JTextField proPointstxtP2Y;
    public javax.swing.JTextField proPointstxtP3X;
    public javax.swing.JTextField proPointstxtP3Y;
    public javax.swing.JTextField proPointstxtP4X;
    public javax.swing.JTextField proPointstxtP4Y;
    public javax.swing.JPanel proRotate3DPanel;
    public javax.swing.JTextField proRotate3DTxtX;
    public javax.swing.JTextField proRotate3DTxtY;
    public javax.swing.JTextField proRotate3DTxtZ;
    private javax.swing.JLabel proRotate3DX;
    private javax.swing.JLabel proRotate3DY;
    private javax.swing.JLabel proRotate3DZ;
    public javax.swing.JPanel proSize3DPanel;
    public javax.swing.JTextField proSize3DTxtH;
    public javax.swing.JTextField proSize3DTxtL;
    public javax.swing.JTextField proSize3DTxtW;
    private javax.swing.JLabel proSizeLbHeight;
    private javax.swing.JLabel proSizeLbLength;
    private javax.swing.JLabel proSizeLbWidth;
    public javax.swing.JPanel proSizePanel;
    private javax.swing.JLabel proSizelb1;
    public javax.swing.JLabel proSizelbH;
    public javax.swing.JLabel proSizelbR;
    public javax.swing.JLabel proSizelbR2;
    public javax.swing.JLabel proSizelbW;
    public javax.swing.JLabel proSizelnR1;
    public javax.swing.JTextField proSizetxtH;
    public javax.swing.JTextField proSizetxtR;
    public javax.swing.JTextField proSizetxtR1;
    public javax.swing.JTextField proSizetxtR2;
    public javax.swing.JTextField proSizetxtW;
    public javax.swing.JPanel proTransformPanel;
    public javax.swing.JLabel proTransformScaling;
    public javax.swing.JButton proTransformbtnResetRotate;
    public javax.swing.JButton proTransformbtnResetRotatePoint;
    private javax.swing.JLabel proTransformlb1;
    private javax.swing.JLabel proTransformlb2;
    public javax.swing.JLabel proTransformlbRotate;
    public javax.swing.JLabel proTransformlbRotatePoint;
    public javax.swing.JTextField proTransformtxtRotate;
    public javax.swing.JTextField proTransformtxtRotatePointX;
    public javax.swing.JTextField proTransformtxtRotatePointY;
    public javax.swing.JTextField proTransformtxtScalingX;
    public javax.swing.JTextField proTransformtxtScalingY;
    private javax.swing.JPanel proTypePanel;
    public javax.swing.JLabel proTypelbShapeName;
    private javax.swing.JScrollPane scrollPanel;
    private javax.swing.JSlider slBlue;
    private javax.swing.JSlider slGreen;
    private javax.swing.JSlider slRed;
    private javax.swing.JSlider slZoom;
    public javax.swing.JSlider sliderLocationX;
    public javax.swing.JSlider sliderLocationY;
    public javax.swing.JSlider sliderLocationZ;
    public javax.swing.JSlider sliderRotateX;
    public javax.swing.JSlider sliderRotateY;
    public javax.swing.JSlider sliderRotateZ;
    private javax.swing.JToolBar.Separator spLineAlg;
    private javax.swing.JToolBar.Separator spSizeBrush;
    private javax.swing.JToolBar.Separator spStyleBrush;
    private javax.swing.JSeparator spTool1;
    private javax.swing.JSeparator spTool2;
    private javax.swing.JPanel statusBar;
    private javax.swing.JToolBar toolBar;
    private javax.swing.JButton toolBarBtnCopy;
    private javax.swing.JButton toolBarBtnCut;
    private javax.swing.JButton toolBarBtnNew;
    private javax.swing.JButton toolBarBtnOpen;
    private javax.swing.JButton toolBarBtnPaste;
    private javax.swing.JButton toolBarBtnRedo;
    private javax.swing.JToggleButton toolBarBtnRuler;
    private javax.swing.JButton toolBarBtnSave;
    private javax.swing.JToggleButton toolBarBtnTgGrid;
    private javax.swing.JToggleButton toolBarBtnTgOxy;
    private javax.swing.JButton toolBarBtnUndo;
    private javax.swing.JPanel viewPort;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
    private Cursor csSelector;
    private Cursor csRectSe;
    private Cursor csLasso;
    private Cursor csPencil;
    private Cursor csBrush;
    private Cursor csFill;
    private Cursor csEraser;
    private Cursor csWand;
    private Cursor csPicker;
    private Cursor csLine;
    private Cursor csRect;
    private Cursor csPoly;
    private Cursor csPara;
    private Cursor csTri;
    private Cursor csDia;
    private Cursor csTrap;
    private Cursor csHandOpen;
    private Cursor csHandClose;
    private Cursor[] csResize = new Cursor[8];
    private Cursor[] csRotate = new Cursor[8];
    private Cursor csMoveH;
    private Cursor csMoveP;
    private Cursor csFLipPoint;
    private Cursor csRoateAtPoint;
    private Cursor cs3D;
    private Cursor csEllipse;

    private Line tLine;
    private Pencil tPencil;
    private Brush tBrush;
    private Rectangle tRect;
    private Shape2D tShape2D;
    private Shape3D tShape3D;
    private MouseTip mouseTip;
}

class ListItem {

    private boolean show;
    private LayerPanel layer;
    private boolean selected = false;

    public ListItem(boolean show, LayerPanel layer) {
        this.show = show;
        this.layer = layer;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public LayerPanel getLayer() {
        return layer;
    }

    public void setLayer(LayerPanel layer) {
        this.layer = layer;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}

class ListItemRenderer extends JPanel implements ListCellRenderer<ListItem> {

    private JCheckBox cb = new JCheckBox();

    public ListItemRenderer() {
        this.setOpaque(true);
        this.setLayout(new GridLayout(1, 1));
        cb.setSize(100, 27);
        cb.setOpaque(false);
        this.add(cb);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends ListItem> jlist, ListItem e, int i, boolean bln, boolean bln1) {
        cb.setText(e.getLayer().getLayerName());
        cb.setSelected(e.isShow());
        if (e.isSelected()) {
            this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            this.setBackground(new Color(204, 204, 204));
        } else {
            this.setBorder(BorderFactory.createEtchedBorder());
            this.setBackground(new Color(240, 240, 240));
        }
        return this;
    }

}

class MouseTip extends JPanel {

    public static int TYPE_SIZE = 0;
    public static int TYPE_ALPHA = 1;
    public static int TYPE_R = 2;
    public static int TYPE_2R = 3;
    private JLabel op1, op2, op3;
    private Timer tm = new Timer(200, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            setVisible(false);
            tm.stop();
        }
    });

    public MouseTip() {
        setBackground(Color.black);
        setLayout(null);
        setOpaque(false);
        op1 = new JLabel();
        op1.setForeground(Color.WHITE);
        op1.setBounds(4, 2, 100, 15);
        op1.setVerticalAlignment(SwingConstants.CENTER);
        op2 = new JLabel();
        op2.setForeground(Color.WHITE);
        op2.setBounds(4, 17, 100, 15);
        op2.setVerticalAlignment(SwingConstants.CENTER);
        op3 = new JLabel();
        op3.setForeground(Color.WHITE);
        op3.setBounds(4, 32, 100, 15);
        op3.setVerticalAlignment(SwingConstants.CENTER);
        add(op1);
        add(op2);
        add(op3);
    }

    public void showTips(Point p, Point3D size) {
        setBounds(p.x + 10, p.y + 10, 60, 51);
        op1.setText("→: " + (size.x));
        op2.setText("↙: " + (size.y));
        op3.setText("↑: " + (size.z));
        setVisible(true);
    }

    public void showTips(Point p, Dimension d) {
        setBounds(p.x + 10, p.y + 10, 60, 34);
        op1.setText("↔: " + (d.width + 1));
        op2.setText("↨ : " + (d.height + 1));
        setVisible(true);
        //repaint();
    }

    public void showTips(Point p, Point d) {
        setBounds(p.x + 10, p.y + 10, 60, 34);
        op1.setText("X: " + d.x);
        op2.setText("Y: " + d.y);
        setVisible(true);
        //repaint();
    }

    public void showTips(Point p, String mess) {
        setBounds(p.x + 10, p.y + 10, 50, 17);
        op1.setText(mess);
        setVisible(true);
        tm.restart();
    }

    public void showTips(Point p, int type, double r) {
        setBounds(p.x + 10, p.y + 10, 55, 17);
        if (type == TYPE_ALPHA) {
            op1.setText("∆: " + (int) (Math.toDegrees(r)) + "°");
        }
        setVisible(true);
    }

    public void showTips(Point p, int type, int r1, int r2) {
        if (type == TYPE_2R) {
            if (r1 != r2) {
                setBounds(p.x + 10, p.y + 10, 60, 34);
                op1.setText("R1: " + r1);
                op2.setText("R2: " + r2);
            } else {
                setBounds(p.x + 10, p.y + 10, 60, 17);
                op1.setText("R: " + r1);
            }
        }
        setVisible(true);
    }

    public void hideTips() {
        setVisible(false);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g;
        graphics.setColor(Color.black);
        graphics.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight() / 5, 10);
    }
}

class DoItem {

    public static int DRAW_MODE = 1;
    public static int EDIT_MODE = 2;
    public static int DELETE_MODE = 3;
    private int mode;
    private Shape2D shape;
    private int index;
    private LayerPanel lp;

    public DoItem(int mode, Shape2D shape, int index, LayerPanel lp) {
        this.mode = mode;
        this.shape = shape;
        this.index = index;
        this.lp = lp;
    }

    public LayerPanel getLayer() {
        return lp;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getMode() {
        return mode;
    }

    public Shape2D getShape() {
        return shape;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setShape(Shape2D shape) {
        this.shape = shape;
    }
}
