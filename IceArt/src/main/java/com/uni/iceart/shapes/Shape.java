package com.uni.iceart.shapes;

import com.uni.iceart.utils.CColor;
import com.uni.iceart.utils.Size;
import lombok.Data;

import java.awt.*;

public abstract class Shape {

    private String type = getClass().getSimpleName();
    private Rectangle borderRect;
    private int x;
    private int y;

    private int x2;
    private int y2;

    private Size size;
    private CColor borderColor = CColor.BLACK;

    private boolean isFilled = false;

    public abstract void redrawSelf(Graphics2D g);

    public abstract void drawSelf(Graphics2D g);

    public boolean contains(int x, int y) {
        return this.getBorderRect().contains(x, y);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Rectangle getBorderRect() {
        return borderRect;
    }

    public void setBorderRect(Rectangle borderRect) {
        this.borderRect = borderRect;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public CColor getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(CColor borderColor) {
        this.borderColor = borderColor;
    }

    public boolean isFilled() {
        return isFilled;
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
    }
}
