package com.neal.spaceminer.main;

import javax.swing.*;

public class Main {

    public static JFrame window;
    static 

    public static void main(String[] args) {

        window = new JFrame("Space Miner");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        gamePanel.config.loadConfig();

        if (gamePanel.fullScreen) {
            window.setUndecorated(true);
        }

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        if (gamePanel.fullScreen) {
            gamePanel.setFullScreen();
        }

        gamePanel.setupGame();
        gamePanel.startGame();
    }
}