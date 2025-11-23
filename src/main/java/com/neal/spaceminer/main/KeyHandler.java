package com.neal.spaceminer.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gamePanel;

    public boolean up, down, left, right, chest;

    boolean showDebug = false;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (gamePanel.gameState == gamePanel.titleState){
            titleState(key);
        } else if (gamePanel.gameState == gamePanel.playState){
            playState(key);
        } else if (gamePanel.gameState == gamePanel.pauseState){
            pauseState(key);
        } else if (gamePanel.gameState == gamePanel.inventoryState){
            inventoryState(key);
        } else if (gamePanel.gameState == gamePanel.chestState){
            chestState(key);
        }
    }

    public void titleState(int key){
        if (key == KeyEvent.VK_W) {
            gamePanel.ui.commandNum--;
            if(gamePanel.ui.commandNum < 0){
                gamePanel.ui.commandNum = 2;
            }
        }
        if (key == KeyEvent.VK_S) {
            gamePanel.ui.commandNum++;
            if(gamePanel.ui.commandNum > 2){
                gamePanel.ui.commandNum = 0;
            }
        }

        if(key ==KeyEvent.VK_ENTER){
            if(gamePanel.ui.commandNum == 0){
                gamePanel.gameState = gamePanel.playState;
            } else if(gamePanel.ui.commandNum == 1){
                // ADD LATER
            } else if(gamePanel.ui.commandNum == 2){
                System.exit(0);
            }
        }
    }

    public void playState(int key){

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

        if(key == KeyEvent.VK_ESCAPE){
            gamePanel.gameState = gamePanel.pauseState;
        }

        if(key == KeyEvent.VK_E){
            if (gamePanel.player.canUse){
                gamePanel.gameState = gamePanel.chestState;
            } else {
                gamePanel.gameState = gamePanel.inventoryState;
            }
        }

        if(key == KeyEvent.VK_F3){
            showDebug = !showDebug;
        }
    }

    public void pauseState(int key) {
        if (key == KeyEvent.VK_ESCAPE) {
            gamePanel.gameState = gamePanel.playState;
        }
    }

    public void inventoryState(int key) {

        if (key == KeyEvent.VK_E) {
            gamePanel.gameState = gamePanel.playState;
            gamePanel.ui.slotRow = 0;
            gamePanel.ui.slotCol = 0;
        }

        // CURSOR MOVEMENT
        if (key == KeyEvent.VK_W) {
            if(gamePanel.ui.slotRow > 0) {
                gamePanel.ui.slotRow--;
            }
        }
        if (key == KeyEvent.VK_A) {
            if(gamePanel.ui.slotCol > 0) {
                gamePanel.ui.slotCol--;
            }
        }
        if (key == KeyEvent.VK_S) {
            if(gamePanel.ui.slotRow < 3) {
                gamePanel.ui.slotRow++;
            }
        }
        if (key == KeyEvent.VK_D) {
            if(gamePanel.ui.slotCol < 4) {
                gamePanel.ui.slotCol++;
            }
        }

        // SWAPS
        if (key == KeyEvent.VK_1) {
            gamePanel.player.swapItems(gamePanel.ui.getItemIndexOnSlotInventory(), 0);
        }
        if (key == KeyEvent.VK_2) {
            gamePanel.player.swapItems(gamePanel.ui.getItemIndexOnSlotInventory(), 1);
        }
        if (key == KeyEvent.VK_3) {
            gamePanel.player.swapItems(gamePanel.ui.getItemIndexOnSlotInventory(), 2);
        }
        if (key == KeyEvent.VK_4) {
            gamePanel.player.swapItems(gamePanel.ui.getItemIndexOnSlotInventory(), 3);
        }
        if (key == KeyEvent.VK_5) {
            gamePanel.player.swapItems(gamePanel.ui.getItemIndexOnSlotInventory(), 4);
        }
    }

    public void chestState(int key) {

        if (key == KeyEvent.VK_E) {
            gamePanel.gameState = gamePanel.playState;
            gamePanel.ui.slotRow = 0;
            gamePanel.ui.slotCol = 0;
            chest = false;
        }

        if (key == KeyEvent.VK_W) {
            if(gamePanel.ui.slotRow > 0) {
                gamePanel.ui.slotRow--;
            }
        }
        if (key == KeyEvent.VK_A) {
            if(gamePanel.ui.slotCol > 0) {
                gamePanel.ui.slotCol--;
                if(gamePanel.ui.slotCol == 7) {
                    gamePanel.ui.slotCol = 5;
                }
            }
        }
        if (key == KeyEvent.VK_S) {
            if(gamePanel.ui.slotRow < 6 && gamePanel.ui.slotCol < 6) {
                gamePanel.ui.slotRow++;
            } else if (gamePanel.ui.slotRow < 3 && gamePanel.ui.slotCol > 7) {
                gamePanel.ui.slotRow++;
            }
        }
        if (key == KeyEvent.VK_D) {
            if(gamePanel.ui.slotCol < 12) {
                gamePanel.ui.slotCol++;
                if(gamePanel.ui.slotCol == 6) {
                    gamePanel.ui.slotCol = 8;
                }
                if(gamePanel.ui.slotCol > 7 && gamePanel.ui.slotRow > 2) {
                    gamePanel.ui.slotRow = 3;
                }
            }
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
    }
}