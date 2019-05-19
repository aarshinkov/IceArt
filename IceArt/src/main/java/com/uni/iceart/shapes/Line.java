package com.uni.iceart.shapes;

import com.uni.iceart.frame.IceArt;

import java.awt.*;

public class Line extends Shape {

    public Line() {

    }

    @Override
    public void redrawSelf(Graphics2D g) {
        g.setPaint(this.getBorderColor().getAWTColor());
        g.drawLine(this.getX(), this.getY(), this.getX2(), this.getY2());
    }

    @Override
    public void drawSelf(Graphics2D g) {
        g.setPaint(this.getBorderColor().getAWTColor());
        g.drawLine(this.getX(), this.getY(), this.getX2(), this.getY2());
        IceArt.getLastAction().setText("Drew a line");
    }

    @Override
    public boolean contains(int x, int y) {
        return false;
    }
}
