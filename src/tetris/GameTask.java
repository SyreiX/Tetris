package tetris;

import javax.swing.*;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class GameTask extends TimerTask {

    private boolean gameOver = false;
    Timer timer;
    Tetris tetris;
    FieldRenderer fieldRenderer;
    GameField gameField;
    TetrisWindow tetrisWindow;
    JTextField tetrisScore;
    int diffLevel;
    int score = 0;

    public GameTask() {

    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public void createTimer() {
        this.timer = new Timer();
    }

    public void startRendering(Tetris tetris, TetrisWindow tetrisWindow, GameField gameField, FieldRenderer fieldRenderer) {
        this.tetris = tetris;
        this.fieldRenderer = fieldRenderer;
        this.tetrisWindow = tetrisWindow;
        this.tetrisScore = tetrisWindow.getScore();
        this.gameField = gameField;
        this.diffLevel = TetrisWindow.getDiffLevel();
        this.score = 0;
        tetrisScore.setText(Integer.toString(score));
        this.timer.scheduleAtFixedRate(this, TetrisWindow.getDelay(), diffLevel);
    }

    private void handleClearedRow(int deletedRows) {
        if(deletedRows > 0) {
            score += Math.pow(100, deletedRows);
            tetrisScore.setText(Integer.toString(score));
        }
    }

    @Override
    public void run() {
        if(!gameOver) {
            fieldRenderer.fallOneRow();
            handleClearedRow(findRowToClear());
            handleClearedRow(findRowToClear());
            handleClearedRow(findRowToClear());
        }
    }

    public int findRowToClear() {
        boolean deletableRow;
        boolean rowsDeleted = false;
        int deletedRows = 0;

        for(int i = gameField.playField.length - 1; i > 0; i--) {
            deletableRow = true;
            for(int j = 0; j < gameField.playField[0].length; j++) {
                if(gameField.playField[i][j] == null) {
                    deletableRow = false;
                }
            }
            if(deletableRow) {
                Arrays.fill(gameField.playField[i], null);
                deletedRows += 1;
                rowsDeleted = true;
            }

            if(rowsDeleted) {
                boolean emptyRow;
                for(int k = gameField.playField.length - 1; k > 0; k--) {
                    emptyRow = true;
                    for(int j = 0; j < gameField.playField[0].length; j++) {
                        if(gameField.playField[k][j] != null) {
                            emptyRow = false;
                        }
                    }
                    if(emptyRow) {
                        for(int l = k; l > 0; l--) {
                            for(int j = 0; j < 9; j++) {
                                if(gameField.playField[l - 1][j] != null) {
                                    gameField.playField[l][j] = gameField.playField[l - 1][j];
                                    gameField.playField[l][j].setFieldY(gameField.playField[l][j].getFieldY() + 1);
                                } else {
                                    gameField.playField[l][j] = null;
                                }
                            }
                        }
                        gameField.setPlayField(gameField.playField);
                    }
                }
            }
        }
        return deletedRows;
    }
}
