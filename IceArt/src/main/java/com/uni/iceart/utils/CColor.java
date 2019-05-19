package com.uni.iceart.utils;

import java.awt.*;

public class CColor {
    public static final CColor WHITE = new CColor(255, 255, 255);
    public static final CColor BLACK = new CColor(0, 0, 0);

    private int red;
    private int green;
    private int blue;


    /**
     * Creates a default color with r=0, g=0, b=0 a.k.a BLACK
     */
    public CColor() {
        this.red = 0;
        this.green = 0;
        this.blue = 0;
    }

    public CColor(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public CColor(java.awt.Color color) {
        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();
    }

    public String getHex() {
        StringBuilder builder = new StringBuilder();

        builder.append("#");

        if (this.red >= 0 && this.red <= 15) {
            builder.append("0");
        }
        builder.append(Integer.toHexString(this.red));

        if (this.green >= 0 && this.green <= 15) {
            builder.append("0");
        }
        builder.append(Integer.toHexString(this.green));

        if (this.blue >= 0 && this.blue <= 15) {
            builder.append("0");
        }
        builder.append(Integer.toHexString(this.blue));

        return String.valueOf(builder);
    }

    @Override
    public String toString() {
        return "CColor{red=" + red + ", green=" + green + ", blue=" + blue + '}';
    }

    public Color getAWTColor() {
        return new Color(this.getRed(), this.getGreen(), this.getBlue());
    }

    public static CColor getWHITE() {
        return WHITE;
    }

    public static CColor getBLACK() {
        return BLACK;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }
}

