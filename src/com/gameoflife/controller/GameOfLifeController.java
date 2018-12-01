package com.gameoflife.controller;

import com.gameoflife.view.GameOfLife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class GameOfLifeController {

    private static int ROWS;
    private static int COLUMNS;

    private GameOfLife gameOfLife;

    private JPanel lifePanel;

    private JButton playButton;
    private JButton stepButton;
    private JButton resetButton;

    private JSlider cellsSlider;

    private JComboBox presets;

    private JCheckBox gridToggler;

    private boolean[][] currentMove;
    private boolean[][] nextMove;

    private int mouseCellX;
    private int mouseCellY;

    private boolean isStep;
    private boolean isGrid;
    private boolean isAlive;

    public GameOfLifeController() {
        gameOfLife = new GameOfLife();

        gameOfLife.setVisible(true);

        initComponents();
        initListeners();
        initTimer();

        repaint(true);
    }

    private void initComponents() {
        lifePanel = gameOfLife.getLifePanel();

        playButton = gameOfLife.getPlayButton();
        stepButton = gameOfLife.getStepButton();
        resetButton = gameOfLife.getResetButton();

        cellsSlider = gameOfLife.getCellsSlider();

        presets = gameOfLife.getPresets();

        gridToggler = gameOfLife.getGridCheckBox();

        ROWS = cellsSlider.getValue();
        COLUMNS = cellsSlider.getValue();

        isGrid = gridToggler.isSelected();

        currentMove = new boolean[ROWS][COLUMNS];
        nextMove = new boolean[ROWS][COLUMNS];
    }

    private void initListeners() {
        gameOfLife.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {

            }

            @Override
            public void componentMoved(ComponentEvent e) {
                repaint();
            }

            @Override
            public void componentShown(ComponentEvent e) {
                repaint();
            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });

        lifePanel.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                repaint();
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });

        lifePanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mouseCellX = -1;
                mouseCellY = -1;

                fillCell(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        lifePanel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                fillCell(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });

        playButton.addActionListener(e -> {
            isAlive = !isAlive;
            isStep = false;

            playButton.setText((isAlive) ? "Stop" : "Play");
            cellsSlider.setEnabled(!isAlive);
        });

        stepButton.addActionListener(e -> {
            isAlive = true;
            isStep = true;

            playButton.setText("Play");
            cellsSlider.setEnabled(true);
        });

        resetButton.addActionListener(e -> {
            isAlive = false;

            playButton.setText("Play");
            cellsSlider.setEnabled(true);

            repaint(true);
        });

        cellsSlider.addChangeListener(e -> {
            presets.setSelectedIndex(0);

            initComponents();
            repaint(true);
        });

        presets.addActionListener(e -> {
            isAlive = false;

            playButton.setText("Play");

            repaint(true);
            initPreset();
            repaint();
        });

        gridToggler.addActionListener(e -> {
            isGrid = !isGrid;

            repaint();
        });
    }

    private void initPreset() {
        int currentPreset = presets.getSelectedIndex();

        switch (currentPreset) {

            case 1: //Outer Stroke

                for (int i = 0; i < COLUMNS; i++) {
                    currentMove[0][i] = true;
                    currentMove[ROWS - 1][i] = true;
                    currentMove[i][0] = true;
                    currentMove[i][COLUMNS - 1] = true;
                }

                break;

            case 2: //Gosper Glider Gun

                currentMove[5][1] = true;
                currentMove[5][2] = true;
                currentMove[6][1] = true;
                currentMove[6][2] = true;

                currentMove[3][14] = true;
                currentMove[4][13] = true;
                currentMove[4][15] = true;
                currentMove[5][12] = true;
                currentMove[5][16] = true;
                currentMove[5][17] = true;
                currentMove[6][12] = true;
                currentMove[6][16] = true;
                currentMove[6][17] = true;
                currentMove[7][12] = true;
                currentMove[7][16] = true;
                currentMove[7][17] = true;
                currentMove[8][13] = true;
                currentMove[8][15] = true;
                currentMove[9][14] = true;

                currentMove[1][26] = true;
                currentMove[2][23] = true;
                currentMove[2][24] = true;
                currentMove[2][25] = true;
                currentMove[2][26] = true;
                currentMove[3][22] = true;
                currentMove[3][23] = true;
                currentMove[3][24] = true;
                currentMove[3][25] = true;
                currentMove[4][22] = true;
                currentMove[4][25] = true;
                currentMove[5][22] = true;
                currentMove[5][23] = true;
                currentMove[5][24] = true;
                currentMove[5][25] = true;
                currentMove[6][23] = true;
                currentMove[6][24] = true;
                currentMove[6][25] = true;
                currentMove[6][26] = true;
                currentMove[7][26] = true;

                currentMove[5][31] = true;
                currentMove[6][31] = true;

                currentMove[3][35] = true;
                currentMove[3][36] = true;
                currentMove[4][35] = true;
                currentMove[4][36] = true;

                break;

            case 3: //Glider

                currentMove[1][2] = true;
                currentMove[2][3] = true;
                currentMove[3][1] = true;
                currentMove[3][2] = true;
                currentMove[3][3] = true;

                break;

            case 4: //Spaceship

                currentMove[44][1] = true;
                currentMove[44][4] = true;
                currentMove[45][5] = true;
                currentMove[46][1] = true;
                currentMove[46][5] = true;
                currentMove[47][2] = true;
                currentMove[47][3] = true;
                currentMove[47][4] = true;
                currentMove[47][5] = true;

                break;

            case -1:
            case 0:
            default:
                break;
        }
    }

    private void initTimer() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                if (isAlive) {

                    for (int h = 0; h < ROWS; h++) {

                        for (int v = 0; v < COLUMNS; v++) {
                            nextMove[h][v] = hardDecision(h, v);
                        }
                    }

                    for (int h = 0; h < ROWS; h++) {
                        System.arraycopy(nextMove[h], 0, currentMove[h], 0, COLUMNS);
                    }

                    repaint();

                    if (isStep) {
                        isAlive = false;
                        isStep = false;
                    }
                }
            }
        };

        timer.scheduleAtFixedRate(timerTask, 0, 100);
    }

    private void fillCell(MouseEvent e) {
        int mv = COLUMNS * e.getX() / lifePanel.getWidth();
        int mh = ROWS * e.getY() / lifePanel.getHeight();

        if (mv >= 0 && mv < COLUMNS && mh >= 0 && mh < ROWS) {

            if (mouseCellX != mv || mouseCellY != mh) {
                currentMove[mh][mv] = !currentMove[mh][mv];

                repaint();
            }
        }

        mouseCellX = mv;
        mouseCellY = mh;
    }

    private void repaint() {
        repaint(false);
    }

    private void repaint(boolean isResetting) {

        if (isResetting) {
            currentMove = new boolean[ROWS][COLUMNS];
        }

        Image preImage = gameOfLife.createImage(lifePanel.getWidth(), lifePanel.getHeight());
        Graphics preGraphics = preImage.getGraphics();

        preGraphics.setColor(lifePanel.getBackground());
        preGraphics.fillRect(0, 0, lifePanel.getWidth(), lifePanel.getHeight());

        //---------- Cells - Start ----------
        if (!isResetting) {
            preGraphics.setColor(Color.YELLOW);

            for (int h = 0; h < ROWS; h++) {

                for (int v = 0; v < COLUMNS; v++) {

                    if (currentMove[h][v]) {
                        int width = v * lifePanel.getWidth() / COLUMNS;
                        int height = h * lifePanel.getHeight() / ROWS;

                        preGraphics.fillRect(width, height, lifePanel.getWidth() / COLUMNS, lifePanel.getHeight() / ROWS);
                    }
                }
            }
        }
        //---------- Cells - End ----------

        //---------- Grid - Start ----------
        if (isGrid) {
            preGraphics.setColor(Color.BLACK);

            for (int h = 1; h < ROWS; h++) {
                int height = h * lifePanel.getHeight() / ROWS;
                preGraphics.drawLine(0, height, lifePanel.getWidth(), height);
            }

            for (int v = 1; v < COLUMNS; v++) {
                int width = v * lifePanel.getWidth() / COLUMNS;
                preGraphics.drawLine(width, 0, width, lifePanel.getHeight());
            }
        }
        //---------- Grid - End ----------

        lifePanel.getGraphics().drawImage(preImage, 0, 0, lifePanel);
    }

    private boolean hardDecision(int h, int v) {
        int neighbours = 0;

        if (v > 0) {

            if (currentMove[h][v - 1]) {
                neighbours++;
            }

            if (h > 0) {

                if (currentMove[h - 1][v - 1]) {
                    neighbours++;
                }
            }

            if (h < ROWS - 1) {

                if (currentMove[h + 1][v - 1]) {
                    neighbours++;
                }
            }
        }

        if (v < COLUMNS - 1) {

            if (currentMove[h][v + 1]) {
                neighbours++;
            }

            if (h > 0) {

                if (currentMove[h - 1][v + 1]) {
                    neighbours++;
                }
            }

            if (h < ROWS - 1) {

                if (currentMove[h + 1][v + 1]) {
                    neighbours++;
                }
            }
        }

        if (h > 0) {

            if (currentMove[h - 1][v]) {
                neighbours++;
            }
        }

        if (h < ROWS - 1) {

            if (currentMove[h + 1][v]) {
                neighbours++;
            }
        }

        if (neighbours == 3) {
            return true;
        }

        return currentMove[h][v] && neighbours == 2;
    }
}
