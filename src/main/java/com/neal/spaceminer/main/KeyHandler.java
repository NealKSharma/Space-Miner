package com.neal.spaceminer.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gamePanel;

    public boolean up, down, left, right, use, escape;

    public boolean usePressed = false;
    public boolean escapePressed = false;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W) {
            up = true;
        }
        if (key == KeyEvent.VK_A) {
            left = true;
        }
        if (key == KeyEvent.VK_S) {
            down = true;
        }
        if (key == KeyEvent.VK_D) {
            right = true;
        }

        // GAME STATE
        if (key == KeyEvent.VK_P) {
            if (gamePanel.gameState == gamePanel.playState) {
                gamePanel.gameState = gamePanel.pauseState;
            } else if (gamePanel.gameState == gamePanel.pauseState) {
                gamePanel.gameState = gamePanel.playState;
            }
        }

        // OBJECT INTERACTION
        if (key == KeyEvent.VK_E && !usePressed) {
            use = true;
            usePressed = true;
        }
        if (key == KeyEvent.VK_ESCAPE && !escapePressed) {
            escape = true;
            escapePressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W) {
            up = false;
        }
        if (key == KeyEvent.VK_A) {
            left = false;
        }
        if (key == KeyEvent.VK_S) {
            down = false;
        }
        if (key == KeyEvent.VK_D) {
            right = false;
        }

        if (key == KeyEvent.VK_E) {
            use = false;
            usePressed = false;
        }
        if (key == KeyEvent.VK_ESCAPE) {
            escape = false;
            escapePressed = false;
        }
    }
}