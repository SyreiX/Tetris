package Tetris;

import java.awt.*;

public class Block {
    private int x, y;
    private int fieldX, fieldY;
    private Color color1;
    private Color color2;

    public Block(int x, int y, Color color1, Color color2) {
        this.x = x;
        this.y = y;
        this.fieldX = x + 5;
        this.fieldY = y + 1;
        this.color1 = color1;
        this.color2 = color2;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getFieldX() {
        return fieldX;
    }

    public void setFieldX(int fieldX) {
        this.fieldX = fieldX;
    }

    public int getFieldY() {
        return fieldY;
    }

    public void setFieldY(int fieldY) {
        this.fieldY = fieldY;
    }

    public Color getColor1() {
        return color1;
    }

    public Color getColor2() {
        return color2;
    }
}
