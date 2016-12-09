package Tetris;

import java.awt.*;
import java.util.*;
import java.util.List;

public class FieldRenderer extends TimerTask {

    Tetris tetris;
    TetrisWindow tetrisWindow;
    GameField gameField;
    private Timer timer;
    private boolean gameOver = false;
    Block[][] playField;
    List<Block> currentElement;
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
        timer = new Timer();
        timer.purge();
        timer.scheduleAtFixedRate(this, TetrisWindow.getDelay(), diffLevel);
    }

    public void createNewElement() {
        if(!gameOver) {
            Elements elements = new Elements();
            this.currentElement = elements.getRandomElement();
            for(int i = 0; i < 4; i++) {
                Block currentBlock = this.currentElement.get(i);
                currentBlock.setFieldX(currentBlock.getX() + 4);
                currentBlock.setFieldY(currentBlock.getY());
            }
            for(int i = 0; i < 4; i++) {
                if(playField[currentElement.get(i).getFieldY()][currentElement.get(i).getFieldX()] != null) {
                    tetrisWindow.gameOverMessage("You lose!", "Game Over");
                    gameOver = true;
                    this.timer.cancel();
                    this.timer.purge();
                    tetris.setGameStarted(false);
                    break;
                }
            }
        }
    }

    public List<Block> getCurrentElement() {
        return currentElement;
    }

    public void run() {
        try {
            if(!gameOver) {
                int placeable = 0;
                if(Tetris.getElementPlaced()) {
                    createNewElement();
                    Tetris.setElementPlaced(false);
                } else {
                    this.currentElement = gameField.currentElement;
                    if(currentElement.size() > 0) {
                        for(int i = 0; i < 4; i++) {
                            int iX = ((currentElement.get(i))).getFieldX();
                            int iY = ((currentElement.get(i))).getFieldY();

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
                                ((currentElement.get(i))).setFieldY(((currentElement.get(i))).getFieldY() + 1);
                            }
                        } else {
                            Tetris.setElementPlaced(true);
                            for(int i = 0; i < 4; i++) {
                                int iX = ((currentElement.get(i))).getFieldX();
                                int iY = ((currentElement.get(i))).getFieldY();
                                playField[iY][iX] = currentElement.get(i);
                            }
                        }
                        if(Tetris.getElementPlaced()) {
                            findRowToClear();
                            findRowToClear();
                            findRowToClear();
                            currentElement.clear();
                            gameField.repaint();
                        }
                    }
                }
                if(!gameOver) {
                    gameField.repaint();
                }
            }
        } catch(NullPointerException e) {
            System.err.println("kamu   " + e);
            e.printStackTrace();
        }
        timer.purge();
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
        if(currentElement.size() != 0) {
            try {
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
            } catch(NullPointerException e) {
                e.printStackTrace();
            }
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

    public void clearEverything() {
        currentElement.clear();
        for(int i = 0; i < playField.length; i++) {
            for(int j = 0; j < playField[0].length; j++) {
                playField[i][j] = null;
            }
        }
    }
}