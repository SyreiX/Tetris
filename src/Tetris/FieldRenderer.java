package Tetris;

import java.awt.*;
import java.util.List;

public class FieldRenderer {

    Tetris tetris;
    TetrisWindow tetrisWindow;
    GameField gameField;
    Block[][] playField;
    List<Block> currentElement;
    Graphics2D gr;

    public FieldRenderer(Tetris tetris, TetrisWindow tetrisWindow, GameField gameField) {
        this.tetris = tetris;
        this.gameField = gameField;
        this.playField = gameField.playField;
        this.currentElement = gameField.currentElement;
        this.tetrisWindow = tetrisWindow;
    }

    //functions
    public void setGraphics2DPls(Graphics2D gr) {
        this.gr = gr;
    }

    public List<Block> getCurrentElement() {
        return currentElement;
    }

    public void moveElement(int direction) {
        if(currentElement.size() != 0) {
            int placeable = 0;
            for(int i = 0; i < 4; i++) {
                int iX = ((currentElement.get(i))).getFieldX();
                int iY = ((currentElement.get(i))).getFieldY();

                if(iX + direction < playField[0].length && iX + direction >= 0) {
                    if(playField[iY][iX + direction] == null) {
                        placeable++;
                    }
                } else {
                    break;
                }
            }
            if(placeable == 4 && !Tetris.getElementPlaced()) {
                for(int i = 0; i < 4; i++) {
                    ((currentElement.get(i))).setFieldX(((currentElement.get(i))).getFieldX() + direction);
                }
            }
        }
    }

    public void fallOneRow() {
        try {
            if(currentElement.size() == 4 && !Tetris.getElementPlaced()) {
                int placeable = 0;
                for(int i = 0; i < 4; i++) {
                    int iX = ((currentElement.get(i))).getFieldX();
                    int iY = ((currentElement.get(i))).getFieldY();

                    if(iY + 1 < playField.length) {
                        if(playField[iY + 1][iX] == null) {
                            placeable++;
                        }
                    } else {
                        break;
                    }
                }
                if(placeable == 4 && !Tetris.getElementPlaced()) {
                    for(int i = 0; i < 4; i++) {
                        ((currentElement.get(i))).setFieldY(((currentElement.get(i))).getFieldY() + 1);
                    }
                }
            }

        } catch(NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void rotateElement() {
        Block currentBlock = currentElement.get(0);
        int xPos = currentBlock.getFieldX();
        int yPos = currentBlock.getFieldY();

        boolean rotatable = true;
        for(int i = 1; i < 4; i++) {
            currentBlock = currentElement.get(i);
            int iX = currentBlock.getFieldX() - xPos;
            int iY = currentBlock.getFieldY() - yPos;

            int rotX = (iY * (-1)) + xPos;
            int rotY = iX + yPos;

            if(rotX < 0 || rotX > 8 || rotY < 0 || rotY > 14 || playField[rotY][rotX] != null) {
                rotatable = false;
                break;
            }
        }

        if(rotatable) {
            int temp;
            for(int i = 1; i < 4; i++) {
                currentBlock = currentElement.get(i);
                int iX = currentBlock.getFieldX() - xPos;
                int iY = currentBlock.getFieldY() - yPos;

                temp = iX;
                iX = iY * (-1);
                iY = temp;
                currentBlock.setFieldX(iX + xPos);
                currentBlock.setFieldY(iY + yPos);
            }
        }
    }

    public void clearEverything() {
        currentElement.clear();
        for(int i = 0; i < playField.length; i++) {
            for(int j = 0; j < playField[0].length; j++) {
                playField[i][j] = null;
            }
        }
    }
}