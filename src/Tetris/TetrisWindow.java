package Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

public class TetrisWindow implements KeyListener {

    private JFrame f;
    private JMenuItem newGame;
    private JMenu optionsMenu;
    public static JTextField score = new JTextField();
    private static int diffLevel = 400;
    URL iconURL = getClass().getResource("icon.png");
    ImageIcon icon = new ImageIcon(iconURL);
    FieldRenderer fieldRenderer;
    GameField gameField = new GameField();
    Tetris tetris;
    GameTask gameTask = new GameTask();

    public TetrisWindow(Tetris tetris) {
        this.tetris = tetris;
        this.gameField = new GameField();
        fieldRenderer = new FieldRenderer(this.tetris, this, this.gameField);
        gameField.setFieldRenderer(fieldRenderer);
    }

    public void setDiffLevel(int diffLevel) {
        TetrisWindow.diffLevel = diffLevel;
    }

    public static int getDiffLevel() {
        return diffLevel;
    }

    public static long getDelay() {
        return 0;
    }

    JFrame TetrisFrame() {
        f = new JFrame("Tetris");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(gameField);
        f.setMinimumSize(new Dimension(366, 653));
        f.setResizable(false);
        f.setIconImage(icon.getImage());
        f.addKeyListener(this);
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        newGame = new JMenuItem("New Game   F2");
        JMenuItem quitGame = new JMenuItem("Quit");
        optionsMenu = new JMenu("Options");
        final JMenuItem difficulty = new JMenuItem("Difficulty");
        menuBar.setBackground(Color.lightGray);
        score.setHorizontalAlignment(JTextField.RIGHT);
        score.setFont(new Font("Arial Black", Font.BOLD, 12));
        score.setDisabledTextColor(Color.cyan);
        score.setBackground(Color.darkGray);
        score.setEnabled(false);
        fileMenu.add(newGame);
        fileMenu.add(quitGame);
        optionsMenu.add(difficulty);
        menuBar.add(fileMenu);
        menuBar.add(optionsMenu);
        menuBar.add(new JLabel("                            Your score: "));
        menuBar.add(score);
        f.setJMenuBar(menuBar);
        f.pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        f.setLocation(dim.width / 2 - f.getSize().width / 2, dim.height / 2 - f.getSize().height / 2);
        f.setVisible(true);

        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });

        quitGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                System.exit(0);
            }
        });

        difficulty.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JPanel diff = new JPanel();
                final JRadioButton button1 = new JRadioButton("Easy", true);
                final JRadioButton button2 = new JRadioButton("Normal");
                final JRadioButton button3 = new JRadioButton("Hard");

                diff.add(button1);
                diff.add(button2);
                diff.add(button3);

                button1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        button1.setSelected(true);
                        button2.setSelected(false);
                        button3.setSelected(false);
                    }
                });

                button2.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        button1.setSelected(false);
                        button2.setSelected(true);
                        button3.setSelected(false);
                    }
                });

                button3.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        button1.setSelected(false);
                        button2.setSelected(false);
                        button3.setSelected(true);
                    }
                });

                JOptionPane.showMessageDialog(null, diff, "Choose difficulty!", JOptionPane.PLAIN_MESSAGE);
                if(button1.isSelected()) {
                    setDiffLevel(1000);
                } else if(button2.isSelected()) {
                    setDiffLevel(750);
                } else {
                    setDiffLevel(500);
                }
            }
        });
        return f;
    }

    private void startNewGame() {
        fieldRenderer.clearEverything();
        tetris.setGameStarted(true);
        optionsMenu.setEnabled(false);
        newGame.setEnabled(false);
        gameTask = new GameTask();
        gameTask.setGameOver(false);
        gameTask.startRendering(tetris, this, gameField, fieldRenderer);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(tetris.getGameStarted()) {
            if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                fieldRenderer.moveElement(-1);
                f.repaint();
            } else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                fieldRenderer.moveElement(1);
                f.repaint();
            } else if(e.getKeyCode() == KeyEvent.VK_UP) {
                if(fieldRenderer.currentElement.size() != 0) {
                    if(((fieldRenderer.currentElement.get(0))).getColor1() != Color.YELLOW) {
                        fieldRenderer.rotateElement();
                        f.repaint();
                    }
                }
            } else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                fieldRenderer.fallOneRow();
                f.repaint();
            } else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                while(!Tetris.getElementPlaced()) {
                    fieldRenderer.fallOneRow();
                }
            }
        } else {
            if(e.getKeyCode() == KeyEvent.VK_F2) {
                startNewGame();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void gameOverMessage(String infoMessage, String titleBar) {
        JOptionPane.showConfirmDialog(null, infoMessage, titleBar, JOptionPane.PLAIN_MESSAGE);
        fieldRenderer.clearEverything();
        newGame.setEnabled(true);
        gameField.repaint();
    }
}