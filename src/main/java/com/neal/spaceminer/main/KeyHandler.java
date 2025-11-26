package com.neal.spaceminer.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gamePanel;

    public boolean up, down, left, right, chest, saved;

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

        if(key == KeyEvent.VK_ENTER){
            if(gamePanel.ui.commandNum == 0){
                gamePanel.gameState = gamePanel.playState;
                gamePanel.ui.subState = 0;
            } else if(gamePanel.ui.commandNum == 1){
                gamePanel.saveLoad.load();
                gamePanel.gameState = gamePanel.playState;
                gamePanel.ui.subState = 0;
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
            gamePanel.gameState = gamePanel.inventoryState;
        }

        if(key == KeyEvent.VK_F && gamePanel.player.canUse){
            chest = true;
            gamePanel.player.interactWithNearbyChest();
        }

        if(key == KeyEvent.VK_F3){
            showDebug = !showDebug;
        }
    }
    // NEEDS OPTIMIZATIONS
    public void pauseState(int key) {
        if (key == KeyEvent.VK_ESCAPE) {
            gamePanel.gameState = gamePanel.playState;
            gamePanel.ui.commandNum = 0;
            gamePanel.ui.subState = 0;
            saved = false;
        }

        if (key == KeyEvent.VK_W && gamePanel.ui.subState==0) {
            gamePanel.ui.commandNum--;
            if(gamePanel.ui.commandNum < 0){
                gamePanel.ui.commandNum = 5;
            }
        }
        if (key == KeyEvent.VK_S && gamePanel.ui.subState==0) {
            gamePanel.ui.commandNum++;
            if(gamePanel.ui.commandNum > 5){
                gamePanel.ui.commandNum = 0;
            }
        }

        if (key == KeyEvent.VK_A && gamePanel.ui.commandNum == 1 && gamePanel.ui.subState==0) {
            if(gamePanel.ui.volume > 0){
                gamePanel.ui.volume--;
            }
        }
        if (key == KeyEvent.VK_D  && gamePanel.ui.commandNum == 1 && gamePanel.ui.subState==0) {
            if(gamePanel.ui.volume <= 5){
                gamePanel.ui.volume++;
            }
        }

        if (key == KeyEvent.VK_W && gamePanel.ui.subState==3) {
            gamePanel.ui.commandNum--;
            if(gamePanel.ui.commandNum < 0){
                gamePanel.ui.commandNum = 1;
            }
        }
        if (key == KeyEvent.VK_S && gamePanel.ui.subState==3) {
            gamePanel.ui.commandNum++;
            if(gamePanel.ui.commandNum > 1){
                gamePanel.ui.commandNum = 0;
            }
        }

        if (key == KeyEvent.VK_W && gamePanel.ui.subState==1) {
            gamePanel.ui.commandNum--;
            if(gamePanel.ui.commandNum < 0){
                gamePanel.ui.commandNum = 1;
            }
        }
        if (key == KeyEvent.VK_S && gamePanel.ui.subState==1) {
            gamePanel.ui.commandNum++;
            if(gamePanel.ui.commandNum > 1){
                gamePanel.ui.commandNum = 0;
            }
        }

        if(key == KeyEvent.VK_ENTER && gamePanel.ui.subState == 0){
            if(gamePanel.ui.commandNum == 0){
                gamePanel.ui.subState = 1;
                gamePanel.ui.commandNum = 1;
            } else if(gamePanel.ui.commandNum == 2){
                gamePanel.ui.subState = 2;
            } else if(gamePanel.ui.commandNum == 3){
                gamePanel.saveLoad.save();
                saved = true;
            }
            else if (gamePanel.ui.commandNum == 4){
                gamePanel.ui.subState = 3;
                gamePanel.ui.commandNum = 1;
            } else if (gamePanel.ui.commandNum == 5) {
                gamePanel.gameState = gamePanel.playState;
                gamePanel.ui.commandNum = 0;
            }
        } else if(key == KeyEvent.VK_ENTER && gamePanel.ui.subState == 1){
            if(gamePanel.ui.commandNum == 0){
                gamePanel.fullScreen = !gamePanel.fullScreen;
                gamePanel.config.saveConfig();
                System.exit(0);
            } else if(gamePanel.ui.commandNum == 1){
                gamePanel.ui.subState = 0;
                gamePanel.ui.commandNum = 0;
            }
        } else if(key == KeyEvent.VK_ENTER && gamePanel.ui.subState == 2){
            gamePanel.ui.subState = 0;
        } else if(key == KeyEvent.VK_ENTER && gamePanel.ui.subState == 3){
            if(gamePanel.ui.commandNum == 0){
                gamePanel.gameState = gamePanel.titleState;
            } else if(gamePanel.ui.commandNum == 1){
                gamePanel.ui.subState = 0;
                gamePanel.ui.commandNum = 3;
            }
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
        if (key == KeyEvent.VK_ESCAPE || key == KeyEvent.VK_F) {
            gamePanel.gameState = gamePanel.playState;
            gamePanel.ui.slotRow = 0;
            gamePanel.ui.slotCol = 0;
            gamePanel.player.currentChest = null;
            chest = false;
        }

        // Navigation
        if (key == KeyEvent.VK_W) {
            if(gamePanel.ui.slotRow > 0) {
                gamePanel.ui.slotRow--;
            }
        }

        if (key == KeyEvent.VK_S) {
            // In chest (0-5 columns, max 6 rows)
            if(gamePanel.ui.slotCol < 6 && gamePanel.ui.slotRow < 6) {
                gamePanel.ui.slotRow++;
            }
            // In inventory (8-12 columns, max 3 rows)
            else if(gamePanel.ui.slotCol >= 8 && gamePanel.ui.slotRow < 3) {
                gamePanel.ui.slotRow++;
            }
        }

        if (key == KeyEvent.VK_A) {
            if(gamePanel.ui.slotCol > 0) {
                gamePanel.ui.slotCol--;
                // Jump the gap between chest and inventory
                if(gamePanel.ui.slotCol == 6 || gamePanel.ui.slotCol == 7) {
                    gamePanel.ui.slotCol = 5; // Move to last chest column
                    if(gamePanel.ui.slotRow > 6) {
                        gamePanel.ui.slotRow = 6; // Adjust row if needed
                    }
                }
            }
        }

        if (key == KeyEvent.VK_D) {
            if(gamePanel.ui.slotCol < 12) {
                gamePanel.ui.slotCol++;
                // Jump the gap between chest and inventory
                if(gamePanel.ui.slotCol == 6 || gamePanel.ui.slotCol == 7) {
                    gamePanel.ui.slotCol = 8; // Move to first inventory column
                    if(gamePanel.ui.slotRow > 3) {
                        gamePanel.ui.slotRow = 3; // Adjust row if needed
                    }
                }
            }
        }

        // Item transfer
        if (key == KeyEvent.VK_ENTER) {
            gamePanel.player.transferChestItem(gamePanel.ui.slotCol, gamePanel.ui.slotRow);
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