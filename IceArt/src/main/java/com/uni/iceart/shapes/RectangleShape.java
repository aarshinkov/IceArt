package com.uni.iceart.shapes;

import com.uni.iceart.frame.IceArt;

import java.awt.*;

public class RectangleShape extends Shape {

    public RectangleShape() {
    }

    public RectangleShape(Rectangle rectangle) {
        this.setBorderRect(rectangle);
    }

    @Override
    public void redrawSelf(Graphics2D g) {
        g.setPaint(this.getBorderColor().getAWTColor());
        if (this.isFilled()) {
            g.fillRect(this.getX(), this.getY(), this.getSize().getWidth(), this.getSize().getHeight());
        } else {
            g.drawRect(this.getX(), this.getY(), this.getSize().getWidth(), this.getSize().getHeight());
        }

        IceArt.getLastAction().setText("Redrawing a rectangle");
    }

    @Override
    public void drawSelf(Graphics2D g) {
        System.out.println("Rectangle shape!");
        g.setPaint(IceArt.getCurrentColor().getAWTColor());

        if (this.isFilled()) {
            g.fillRect(this.getX(), this.getY(), this.getSize().getWidth(), this.getSize().getHeight());
        } else {
            g.drawRect(this.getX(), this.getY(), this.getSize().getWidth(), this.getSize().getHeight());
        }

        IceArt.getLastAction().setText("Drew a rectangle");
    }

    @Override
    public boolean contains(int x, int y) {
        return super.contains(x, y);
    }
}

