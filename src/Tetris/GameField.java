package Tetris;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class GameField extends JComponent {
    FieldRenderer fieldRenderer;
    Block[][] playField = new Block[15][9];
    java.util.List<Block> currentElement = new ArrayList<Block>();

    public GameField() {
    }

    public void setFieldRenderer(FieldRenderer fieldRenderer) {
        this.fieldRenderer = fieldRenderer;
    }

    public void setPlayField(Block[][] playField) {
        this.playField = playField;
    }

    @Override
    public void paintComponent(Graphics g) {
        setBackground(Color.lightGray);
        setSize(new Dimension(700, 700));
        Graphics2D g2d = (Graphics2D) g.create();
        GradientPaint cyanToBlue = new GradientPaint(0, 0, Color.gray, 0, 600, Color.black);
        g2d.setPaint(cyanToBlue);
        g2d.fillRect(0, 0, 360, 600);
        g2d.setColor(Color.white);
        for(int x = 0; x <= 360; x += 40) {
            g2d.drawLine(x, 0, x, 600);
        }
        for(int y = 0; y <= 600; y += 40) {
            g2d.drawLine(0, y, 360, y);
        }

        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(0, 0, 360, 600);
        fieldRenderer.setGraphics2DPls(g2d);

        for(int i = 0; i < 15; i++) {
            for(int j = 0; j < 9; j++) {
                if(playField[i][j] != null) {
                    g2d.setColor(playField[i][j].getColor1());
                    g2d.fillRoundRect(playField[i][j].getFieldX() * 40 + 2, playField[i][j].getFieldY() * 40 + 2, 37, 37, 15, 15);
                    g2d.setColor(playField[i][j].getColor2());
                    g2d.fillRoundRect(playField[i][j].getFieldX() * 40 + 4, playField[i][j].getFieldY() * 40 + 4, 28, 28, 15, 15);
                }
            }
        }

        currentElement = fieldRenderer.getCurrentElement();
//        if(currentElement.size() > 0) {
            for(int i = 0; i < currentElement.size(); i++) {
                g2d.setColor(currentElement.get(i).getColor1());
                g2d.fillRoundRect(currentElement.get(i).getFieldX() * 40 + 2, currentElement.get(i).getFieldY() * 40 + 2, 37, 37, 15, 15);
                g2d.setColor(currentElement.get(i).getColor2());
                g2d.fillRoundRect(currentElement.get(i).getFieldX() * 40 + 4, currentElement.get(i).getFieldY() * 40 + 4, 28, 28, 15, 15);
            }
//        }
    }
}