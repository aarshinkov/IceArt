package com.uni.iceart.shapes;

import com.uni.iceart.frame.IceArt;

import java.awt.*;

public class CircleShape extends Shape {

    @Override
    public void redrawSelf(Graphics2D g) {
        g.setPaint(this.getBorderColor().getAWTColor());
        System.out.println("here");
        if (this.isFilled()) {
            g.fillOval(this.getX(), this.getY(), this.getSize().getWidth(), this.getSize().getHeight());
        } else {
            g.drawOval(this.getX(), this.getY(), this.getSize().getWidth(), this.getSize().getHeight());
        }

        IceArt.getLastAction().setText("Redrawing a circle");
    }

    @Override
    public void drawSelf(Graphics2D g) {
        System.out.println("Circle shape!");
        g.setPaint(IceArt.getCurrentColor().getAWTColor());

        if (this.isFilled()) {
            g.fillOval(this.getX(), this.getY(), this.getSize().getWidth(), this.getSize().getHeight());

        } else {
            g.drawOval(this.getX(), this.getY(), this.getSize().getWidth(), this.getSize().getHeight());
        }

        IceArt.getLastAction().setText("Drew a circle");
    }

    @Override
    public boolean contains(int x, int y) {

        int centerX = this.getX() + (this.getSize().getWidth() / 2);
        int centerY = this.getY() + (this.getSize().getHeight() / 2);

        return Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2)) <= (this.getSize().getWidth() / 2);
    }
}
