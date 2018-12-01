package com.gameoflife.view;

import javax.swing.*;

public class GameOfLife extends JFrame {

    private static final int HEIGHT = 1000;
    private static final int WIDTH = 1000;

    private JPanel mainPanel;
    private JPanel lifePanel;

    private JButton playButton;
    private JButton stepButton;
    private JButton resetButton;

    private JSlider cellsSlider;

    private JComboBox presets;

    private JCheckBox gridCheckBox;

    public GameOfLife() {
        setResizable(false);
        setSize(WIDTH, HEIGHT);
        setContentPane(mainPanel);
        setLocationRelativeTo(null);
    }

    public JPanel getLifePanel() {
        return lifePanel;
    }

    public JButton getPlayButton() {
        return playButton;
    }

    public JButton getStepButton() {
        return stepButton;
    }

    public JButton getResetButton() {
        return resetButton;
    }

    public JSlider getCellsSlider() {
        return cellsSlider;
    }

    public JComboBox getPresets() {
        return presets;
    }

    public JCheckBox getGridCheckBox() {
        return gridCheckBox;
    }
}
