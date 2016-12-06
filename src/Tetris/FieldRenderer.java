package Tetris;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class FieldRenderer extends TimerTask {

    Tetris tetris;
    TetrisWindow tetrisWindow;
    GameField gameField;
    Timer timer = new Timer();
    Block[][] playField;
    List currentElement;
    Graphics2D gr;
    int diffLevel;
    int score = 0;

    public FieldRenderer(Tetris tetris, TetrisWindow tetrisWindow, GameField gameField) {
        this.tetris = tetris;
        this.diffLevel = TetrisWindow.getDiffLevel();
        this.gameField = gameField;
        this.playField = gameField.playField;
        this.currentElement = gameField.currentElement;
        this.tetrisWindow = tetrisWindow;
    }

    //functions
    public void setGraphics2DPls(Graphics2D gr) {
        this.gr = gr;
    }

    public void startRendering() {
        timer.scheduleAtFixedRate(this, TetrisWindow.getDelay(), diffLevel);
    }

    public void createNewElement() {
        Elements elements = new Elements();
        this.currentElement = elements.getRandomElement();
        for(int i = 0; i < 4; i++) {
            ((Block) (this.currentElement.get(i))).setFieldX(((Block) (currentElement.get(i))).getX() + 4);
            ((Block) (this.currentElement.get(i))).setFieldY(((Block) (currentElement.get(i))).getY() + 1);
        }
    }

    public List<Block> getCurrentElement() {
        return currentElement;
    }

    public void run() {
        try {
//            tetrisWindow.setRunning(true);
//            while(tetrisWindow.isRunning()){
            int placeable = 0;
            if(Tetris.getElementPlaced()) {
                createNewElement();
                Tetris.setElementPlaced(false);
            } else {
                this.currentElement = gameField.currentElement;
                if(currentElement.size() > 0) {
                    for(int i = 0; i < 4; i++) {
                        int iX = ((Block) (currentElement.get(i))).getFieldX();
                        int iY = ((Block) (currentElement.get(i))).getFieldY();

                        if(iY + 1 < playField.length) {
                            if(playField[iY + 1][iX] == null) {
                                placeable++;
                            }
                        } else {
                            Tetris.setElementPlaced(true);
                            break;
                        }
                    }
                    if(placeable == 4 && !Tetris.getElementPlaced()) {
                        for(int i = 0; i < 4; i++) {
                            ((Block) (currentElement.get(i))).setFieldY(((Block) (currentElement.get(i))).getFieldY() + 1);
                        }
                    } else {
                        Tetris.setElementPlaced(true);
                        for(int i = 0; i < 4; i++) {
                            int iX = ((Block) (currentElement.get(i))).getFieldX();
                            int iY = ((Block) (currentElement.get(i))).getFieldY();
                            playField[iY][iX] = (Block) currentElement.get(i);
                        }
                    }
                    if(Tetris.getElementPlaced()) {
                        findRowToClear();
                        findRowToClear();
                        currentElement.clear();
                        gameField.repaint();
                    }
                }

            }
            gameField.repaint();
//            }
        } catch(NullPointerException e) {
            System.err.println("kamu   " + e);
        }
    }

    public void moveElement(int direction) {
        if(currentElement.size() != 0) {
            int placeable = 0;
            for(int i = 0; i < 4; i++) {
                int iX = ((Block) (currentElement.get(i))).getFieldX();
                int iY = ((Block) (currentElement.get(i))).getFieldY();

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
                    ((Block) (currentElement.get(i))).setFieldX(((Block) (currentElement.get(i))).getFieldX() + direction);
                }
            }
        }
    }

    public void fallOneRow() {
        if(currentElement.size() != 0) {
            try {
                int placeable = 0;
                for(int i = 0; i < 4; i++) {
                    int iX = ((Block) (currentElement.get(i))).getFieldX();
                    int iY = ((Block) (currentElement.get(i))).getFieldY();

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
                        ((Block) (currentElement.get(i))).setFieldY(((Block) (currentElement.get(i))).getFieldY() + 1);
                    }
                }
            } catch(NullPointerException e) {
                System.err.println("finomság  " + e);
            }
        }
    }

    public void rotateElement() {
//        try {
        int xPos = ((Block) (currentElement.get(0))).getFieldX();
        int yPos = ((Block) (currentElement.get(0))).getFieldY();

        boolean rotatable = true;
        for(int i = 1; i < 4; i++) {
            int iX = ((Block) (currentElement.get(i))).getFieldX() - xPos;
            int iY = ((Block) (currentElement.get(i))).getFieldY() - yPos;

            if(!((((iX + yPos) >= 0) && ((iX + yPos) < 15)) && ((((iY * (-1)) + xPos) >= 0) && ((iY * (-1)) + xPos) < 9))) {
                if(playField[iX + yPos][(iY * (-1)) + xPos] != null) {
                    rotatable = false;
                }
            }
        }

        if(rotatable) {
            int temp;
            for(int i = 1; i < 4; i++) {
                int iX = ((Block) (currentElement.get(i))).getFieldX() - xPos;
                int iY = ((Block) (currentElement.get(i))).getFieldY() - yPos;

                temp = iX;
                iX = iY * (-1);
                iY = temp;
                ((Block) (currentElement.get(i))).setFieldX(iX + xPos);
                ((Block) (currentElement.get(i))).setFieldY(iY + yPos);
            }
        }
//        } catch(IndexOutOfBoundsException e) {
//            System.err.println("forgatási probléma");
//        }
    }

    public void findRowToClear() {
        boolean deletableRow;
        boolean rowsDeleted = false;
        int deletedRows = 0;

        for(int i = playField.length - 1; i > 0; i--) {
            deletableRow = true;
            for(int j = 0; j < playField[0].length; j++) {
                if(playField[i][j] == null) {
                    deletableRow = false;
                }
            }
            if(deletableRow) {
                Arrays.fill(playField[i], null);
                deletedRows += 1;
                rowsDeleted = true;
            }

            if(rowsDeleted) {
                boolean emptyRow;
                for(int k = playField.length - 1; k > 0; k--) {
                    emptyRow = true;
                    for(int j = 0; j < playField[0].length; j++) {
                        if(playField[k][j] != null) {
                            emptyRow = false;
                        }
                    }
                    if(emptyRow) {
                        for(int l = k; l > 0; l--) {
                            for(int j = 0; j < 9; j++) {
                                if(playField[l - 1][j] != null) {
                                    playField[l][j] = playField[l - 1][j];
                                    playField[l][j].setFieldY(playField[l][j].getFieldY() + 1);
                                } else {
                                    playField[l][j] = null;
                                }
                            }
                        }
                        gameField.setPlayField(playField);
                    }
                }
            }
        }
        if(deletedRows > 0) {
            score += Math.pow(100, deletedRows);
            TetrisWindow.score.setText(Integer.toString(score));
        }
    }
}

