package com.uni.iceart.frame;

import com.google.gson.Gson;
import com.uni.iceart.shapes.CircleShape;
import com.uni.iceart.shapes.Line;
import com.uni.iceart.shapes.RectangleShape;
import com.uni.iceart.shapes.Shape;
import com.uni.iceart.utils.CColor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class IceArt {
    private final ImageIcon APPLICATION_ICON = new ImageIcon(getClass().getResource("/img/icon/app_icon.png"));

    private static final String DEFAULT_EXTENSION = "json";

    private JFrame frame = new JFrame("Ice Art | Drawing Application");

    private static List<Shape> shapeList = new ArrayList<>();

    private DrawArea drawArea;

    private static JRadioButton freeHandRB = new JRadioButton();
    private static JRadioButton lineRB = new JRadioButton();
    private static JRadioButton rectangleRB = new JRadioButton();
    private static JRadioButton circleRB = new JRadioButton();
    private static JRadioButton deleteRB = new JRadioButton();

    private CColor defaultColor = new CColor();

    private static CColor currentColor = new CColor();

    private static JLabel coordinates = new JLabel("0;0");
    private static final JLabel LAST_ACTION_PREF = new JLabel("Last action: ");
    private static JLabel lastAction = new JLabel("Unknown");
    private static final JLabel SHAPES_COUNT_PREF = new JLabel("Shapes: ");
    private static JLabel shapesCount = new JLabel("0");

    private static JCheckBox fillChB = new JCheckBox("Fill");

    public void show() {
        frame.setPreferredSize(new Dimension(700, 550));

        frame.setIconImage(APPLICATION_ICON.getImage());

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // za promiana na dizaina na UI
//        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//        SwingUtilities.updateComponentTreeUI(frame);

        Container container = frame.getContentPane();

        container.setLayout(new BorderLayout());

        // Added menus
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem optionNew = new JMenuItem("New");
        optionNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));

        JMenuItem optionOpen = new JMenuItem("Open");
        optionOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));

        JMenuItem optionSave = new JMenuItem("Save");
        optionSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));

        JMenuItem optionSaveAs = new JMenuItem("Save As");
        optionSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));

        JMenuItem optionExit = new JMenuItem("Exit");
        optionExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));

        fileMenu.add(optionNew);
        fileMenu.add(optionOpen);
//        fileMenu.add(optionSave);
        fileMenu.add(optionSaveAs);
        fileMenu.addSeparator();
        fileMenu.add(optionExit);

        menuBar.add(fileMenu);

        // Added main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel controls = new JPanel();
        JButton colorChange = new JButton();

        colorChange.setBorderPainted(false);
        colorChange.setBorder(null);
//        colorChange.setFocusable(false);
        colorChange.setMargin(new Insets(0, 0, 0, 0));
        colorChange.setContentAreaFilled(false);
        colorChange.setIcon(new ImageIcon(getClass().getResource("/img/buttons/PaletteTool.png")));

        controls.setLayout(new FlowLayout(FlowLayout.LEFT));

        ButtonGroup controlsGroup = new ButtonGroup();

//        controls.add(drawCB);
        freeHandRB.setIcon(new ImageIcon(getClass().getResource("/img/buttons/FreeMoveGlyph.png")));
        freeHandRB.setSelected(true);
        freeHandRB.setBorder(BorderFactory.createLineBorder(new Color(128, 128, 128)));
        freeHandRB.setBorderPainted(true);

        lineRB.setIcon(new ImageIcon(getClass().getResource("/img/buttons/LineTool.png")));
        styleRB(lineRB);

        rectangleRB.setIcon(new ImageIcon(getClass().getResource("/img/buttons/RectangleTool.png")));
        styleRB(rectangleRB);

        circleRB.setIcon(new ImageIcon(getClass().getResource("/img/buttons/CircleTool.png")));
        styleRB(circleRB);

        deleteRB.setIcon(new ImageIcon(getClass().getResource("/img/buttons/DeleteTool.png")));
        styleRB(deleteRB);

        controlsGroup.add(freeHandRB);
        controlsGroup.add(lineRB);
        controlsGroup.add(rectangleRB);
        controlsGroup.add(circleRB);
        controlsGroup.add(deleteRB);

        controls.add(freeHandRB);
        controls.add(lineRB);
        controls.add(rectangleRB);
        controls.add(circleRB);
//        controls.add(shapes);
        controls.add(colorChange);
        controls.add(deleteRB);

        controls.add(fillChB);
        fillChB.setEnabled(false);

        drawArea = new DrawArea();

        mainPanel.add(controls, BorderLayout.NORTH);
        mainPanel.add(drawArea, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new BorderLayout());

        JPanel actionPanel = new JPanel();
        JPanel shapesCountPanel = new JPanel();
        footerPanel.add(actionPanel, BorderLayout.WEST);
        footerPanel.add(shapesCountPanel, BorderLayout.EAST);

        actionPanel.add(coordinates);
        actionPanel.add(LAST_ACTION_PREF);
        actionPanel.add(lastAction);
        shapesCountPanel.add(SHAPES_COUNT_PREF);
        shapesCountPanel.add(shapesCount);

        container.add(menuBar, BorderLayout.NORTH);
        container.add(mainPanel, BorderLayout.CENTER);
        container.add(footerPanel, BorderLayout.SOUTH);

        // Menu listeners
        optionNew.addActionListener(e -> newFile());

        optionOpen.addActionListener(e -> openFile());

        optionSaveAs.addActionListener(e -> saveFile());

        optionExit.addActionListener(e -> {
            System.out.println("Exiting application");
            System.exit(0);
        });

        // Controls listeners
        freeHandRB.addItemListener(e -> {
            changeRBState(freeHandRB, "Free hand tool");
            fillChB.setEnabled(false);
        });

        lineRB.addItemListener(e -> {
            changeRBState(lineRB, "Line tool");
            fillChB.setEnabled(false);
        });

        rectangleRB.addItemListener(e -> {
            changeRBState(rectangleRB, "Rectangle tool");
            fillChB.setEnabled(true);
        });

        circleRB.addItemListener(e -> {
            changeRBState(circleRB, "Circle tool");
            fillChB.setEnabled(true);
        });

        deleteRB.addItemListener(e -> {
            changeRBState(deleteRB, "Delete tool");
            fillChB.setEnabled(false);
        });

        colorChange.addActionListener(e -> {
            Color color = JColorChooser.showDialog(frame, "Choose a color", defaultColor.getAWTColor());
            if (color != null) {
                currentColor = new CColor(color);
            }
            lastAction.setText("Changed color to " + currentColor.getHex());
        });

        // Window listeners
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                String[] options = {"Exit", "Cancel"};

                int n = JOptionPane.showOptionDialog(frame,
                        "Are you sure you want to exit Ice Art?",
                        "Confirm exit",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);

                if (n == 0) {
                    System.out.println("Exiting application");
                    System.exit(0);
                }
            }
        });

        frame.pack();
        frame.setVisible(true);
    }

    private void styleRB(JRadioButton radioButton) {
        radioButton.setBorder(null);
        radioButton.setBorderPainted(true);
    }

    private void changeRBState(JRadioButton radioButton, String action) {
        lastAction.setText(action);
        if (radioButton.isSelected()) {
            radioButton.setBorder(BorderFactory.createLineBorder(new Color(128, 128, 128)));
        } else {
            radioButton.setBorder(null);
        }
    }

    public void newFile() {
        System.out.println("New File");
        shapeList.clear();
        drawArea.clear();
        shapesCount.setText("" + shapeList.size());
        lastAction.setText("New file");
    }

    private void openFile() {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        int returnValue = jfc.showOpenDialog(frame);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            try {
                String path = jfc.getSelectedFile().getAbsoluteFile().toString();
                File file = new File(path);

                FileReader reader = new FileReader(file);

                JSONParser jsonParser = new JSONParser();

                JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

                JSONArray jsonArray = (JSONArray) jsonObject.get("Shapes");

                for (Object o : jsonArray) {
                    JSONObject jsonElement = (JSONObject) o;
                    String className = (String) jsonElement.get("type");

                    Shape shape = null;
                    if (className.equals(RectangleShape.class.getSimpleName())) {
                        shape = parseJSONObjectToShape(jsonElement, new RectangleShape());
                    } else if (className.equals(CircleShape.class.getSimpleName())) {
                        shape = parseJSONObjectToShape(jsonElement, new CircleShape());
                    } else if (className.equals(Line.class.getSimpleName())) {
                        shape = parseJSONObjectToShape(jsonElement, new Line());
                    }

                    drawArea.redrawShape(shape);
                    lastAction.setText("Opened project from file");
                }

                reader.close();

            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFile() {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = jfc.showSaveDialog(frame);

        if (returnValue == JFileChooser.APPROVE_OPTION) {

            try {
                String path = jfc.getSelectedFile().getAbsoluteFile().toString();

                path = convertPath(path, DEFAULT_EXTENSION);

                File file = new File(path);

                FileWriter writer = new FileWriter(file);

                writer.write("{\"Shapes\":[");

                for (int i = 0; i < shapeList.size(); i++) {
                    Shape shape = shapeList.get(i);

                    if (shape.getBorderColor() == null) {
                        shape.setBorderColor(CColor.BLACK);
                    }

                    Gson gson = new Gson();

                    writer.append(gson.toJson(shape));

                    if (i != shapeList.size() - 1) {
                        writer.write(",\n");
                    } else {
                        writer.write("\n");
                    }
                }

                writer.append("]}");

                writer.flush();
                writer.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

        }
    }

    private static String convertPath(String path, String extension) {
        StringBuilder builder = new StringBuilder();

        extension = extension.toLowerCase();

        String[] split = path.split("\\\\");

        String fileNameExtension = split[split.length - 1];

        String fileName = "";

        if (fileNameExtension.contains(".")) {
            String[] splitExtension = fileNameExtension.split("\\.");
            fileName = splitExtension[0];
        } else {
            fileName = fileNameExtension;
        }

        for (int i = 0; i < split.length - 1; i++) {
            builder.append(split[i]);
            builder.append("\\");
        }

        builder.append(fileName);
        builder.append(".");
        builder.append(extension);

        return String.valueOf(builder);
    }

    private <T extends Shape> T parseJSONObjectToShape(JSONObject jsonElement, T type) {
        Gson gson = new Gson();

        return gson.fromJson(jsonElement.toJSONString(), (Type) type.getClass());
    }

    public static JLabel getCoordinates() {
        return coordinates;
    }

    public static void setCoordinates(JLabel coordinates) {
        IceArt.coordinates = coordinates;
    }

    public static List<Shape> getShapeList() {
        return shapeList;
    }

    public static JRadioButton getFreeHandRB() {
        return freeHandRB;
    }

    public static JRadioButton getLineRB() {
        return lineRB;
    }

    public static JRadioButton getRectangleRB() {
        return rectangleRB;
    }

    public static JRadioButton getCircleRB() {
        return circleRB;
    }

    public static JRadioButton getDeleteRB() {
        return deleteRB;
    }

    public static CColor getCurrentColor() {
        return currentColor;
    }

    public static JLabel getLastAction() {
        return lastAction;
    }

    public static JLabel getShapesCount() {
        return shapesCount;
    }

    public static JCheckBox getFillChB() {
        return fillChB;
    }

    public static void setFillChB(JCheckBox fillChB) {
        IceArt.fillChB = fillChB;
    }
}
