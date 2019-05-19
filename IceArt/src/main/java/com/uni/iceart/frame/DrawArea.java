package com.uni.iceart.frame;

import com.uni.iceart.shapes.*;
import com.uni.iceart.shapes.Shape;
import com.uni.iceart.utils.Point;
import com.uni.iceart.utils.Size;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class DrawArea extends JComponent {

    // Image in which we're going to draw
    private Image image;
    // Graphics2D object ==> used to draw on
    private Graphics2D g2;

    private Shape shape;

    private Shape selectedShape = null;

    private boolean isShapeSelected = false;

    private Point startPoint = new Point();

    private static Size defaultSize = new Size(100, 100);

    public DrawArea() {
        setDoubleBuffered(false);
        this.setBackground(Color.GREEN);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                if (IceArt.getFreeHandRB().isSelected()) {
                    List<Shape> shapeList = IceArt.getShapeList();

                    for (int i = shapeList.size() - 1; i >= 0; i--) {
                        Shape shape = shapeList.get(i);

                        int x = e.getX();
                        int y = e.getY();

                        if (shape.contains(x, y)) {
                            selectedShape = shape;
                            isShapeSelected = true;

                            IceArt.getLastAction().setText(shape.getType() + " selected");
                        }
                    }

                } else {

                    startPoint.setX(e.getX());
                    startPoint.setY(e.getY());
                }

            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

//                int shapeX = (e.getX() - (shape.getSize().getWidth() / 2));
//                int shapeY = (e.getY() - (shape.getSize().getHeight() / 2));

                int shapeX = (e.getX() - (defaultSize.getWidth() / 2));
                int shapeY = (e.getY() - (defaultSize.getHeight() / 2));

                if (IceArt.getRectangleRB().isSelected()) {
                    System.out.println("Rectangle");
                    shape = new RectangleShape();

                    shape.setSize(defaultSize);

                    shape.setX(shapeX);
                    shape.setY(shapeY);
                    shape.setBorderRect(new Rectangle(shapeX, shapeY, defaultSize.getWidth(), defaultSize.getHeight()));
                    shape.setBorderColor(IceArt.getCurrentColor());
                    if (IceArt.getFillChB().isSelected()) {
                        shape.setFilled(true);
                    }
                    IceArt.getShapeList().add(shape);
                    shape.drawSelf(g2);
                    IceArt.getShapesCount().setText("" + IceArt.getShapeList().size());
                    repaint();

                } else if (IceArt.getCircleRB().isSelected()) {
                    System.out.println("Circle");
                    shape = new CircleShape();

                    shape.setSize(defaultSize);
                    shape.setX(shapeX);
                    shape.setY(shapeY);
                    shape.setBorderRect(new Rectangle(shapeX, shapeY, defaultSize.getWidth(), defaultSize.getHeight()));
                    shape.setBorderColor(IceArt.getCurrentColor());
                    if (IceArt.getFillChB().isSelected()) {
                        shape.setFilled(true);
                    }
                    IceArt.getShapeList().add(shape);
                    shape.drawSelf(g2);
                    IceArt.getShapesCount().setText("" + IceArt.getShapeList().size());
                    repaint();

                } else if (IceArt.getLineRB().isSelected()) {
                    // TODO consider changing X and Y for line shape
                    System.out.println("Line");
                    shape = new Line();

                    shape.setX(startPoint.getX());
                    shape.setY(startPoint.getY());
                    shape.setX2(e.getX());
                    shape.setY2(e.getY());
                    shape.setBorderColor(IceArt.getCurrentColor());
                    IceArt.getShapeList().add(shape);
                    shape.drawSelf(g2);
                    IceArt.getShapesCount().setText("" + IceArt.getShapeList().size());
                    repaint();
                } else if (IceArt.getFreeHandRB().isSelected()) {
                    // TODO implement selection
                    System.out.println("Selection");
                } else if (IceArt.getDeleteRB().isSelected()) {
                    System.out.println("Deleting an element");

                    List<Shape> shapeList = IceArt.getShapeList();
                    for (int i = shapeList.size() - 1; i >= 0; i--) {
                        Shape currentShape = shapeList.get(i);
                        System.out.println(currentShape.contains(e.getX(), e.getY()));
                        if (currentShape.contains(e.getX(), e.getY())) {
                            clear();
                            IceArt.getShapeList().remove(currentShape);
                            for (Shape newShape : shapeList) {
                                newShape.redrawSelf(g2);
                                repaint();
                            }
                            IceArt.getLastAction().setText("Deleted " + currentShape.getType());
                            IceArt.getShapesCount().setText("" + IceArt.getShapeList().size());
                            break;
                        }
                    }
                }
            }
        });


        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);

                if (!isShapeSelected) {
                    selectedShape = null;
                } else {
                    System.out.println(selectedShape.toString());
                }
            }
        });

        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);

                IceArt.getCoordinates().setText(e.getX() + "; " + e.getY());
            }
        });

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                resize();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        System.out.println("Before");
        if (image == null) {
            System.out.println("Image is null");
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

            image = createImage((int) screen.getWidth(), (int) screen.getHeight());

            g2 = (Graphics2D) image.getGraphics();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }

        g.drawImage(image, 0, 0, null);
    }

    // now we create exposed methods
    public void clear() {
        // draw white on entire draw area to clear
        System.out.println("Clearing canvas");
        g2.clearRect(0, 0, this.getWidth(), this.getHeight());
        repaint();
    }

    private void resize() {
        repaint();
    }

    public void redrawShape(Shape shape) {
        Shape newShape = null;
        if (shape.getType().equals(RectangleShape.class.getSimpleName())) {
            newShape = new RectangleShape();
            newShape.setBorderRect(new Rectangle(shape.getX(), shape.getY(), shape.getSize().getWidth(), shape.getSize().getHeight()));
        } else if (shape.getType().equals(CircleShape.class.getSimpleName())) {
            newShape = new CircleShape();
            newShape.setBorderRect(new Rectangle(shape.getX(), shape.getY(), shape.getSize().getWidth(), shape.getSize().getHeight()));
        } else if (shape.getType().equals(Line.class.getSimpleName())) {
            newShape = new Line();
            newShape.setX2(shape.getX2());
            newShape.setY2(shape.getY2());
        }

        newShape.setSize(shape.getSize());
        newShape.setX(shape.getX());
        newShape.setY(shape.getY());
//        newShape.setBorderRect(new Rectangle(shape.getX(), shape.getY(), shape.getSize().getWidth(), shape.getSize().getHeight()));
        newShape.setBorderColor(shape.getBorderColor());
        newShape.setFilled(shape.isFilled());
        IceArt.getShapeList().add(newShape);
        newShape.redrawSelf(g2);
        IceArt.getShapesCount().setText("" + IceArt.getShapeList().size());
        repaint();
    }
}
