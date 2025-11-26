package com.neal.spaceminer.main;

import javax.swing.*;

public class Main {

    public static JFrame window;

    public static void main(String[] args) {

        window = new JFrame("Space Miner");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        gamePanel.config.loadConfig();
        window.setUndecorated(gamePanel.fullScreen);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGame();
    }
}