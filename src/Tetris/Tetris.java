package Tetris;

public class Tetris {
    TetrisWindow tetrisWindow;
    private boolean gameStarted;
    public static boolean elementPlaced = true;

    public Tetris(){
        this.gameStarted = false;
    }

    public void startGame() {
        tetrisWindow = new TetrisWindow(this);
        tetrisWindow.TetrisFrame();
    }

    public boolean getGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public static void setElementPlaced(boolean elementPlaced) {
        Tetris.elementPlaced = elementPlaced;
    }

    public static boolean getElementPlaced() {
        return elementPlaced;
    }
}

