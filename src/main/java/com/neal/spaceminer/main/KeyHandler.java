package com.neal.spaceminer.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gamePanel;

    public boolean up, down, left, right, saved;

    public boolean showDebug = false;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (gamePanel.gameState == gamePanel.titleState){ titleState(key); }
        else if (gamePanel.gameState == gamePanel.playState){ playState(key); }
        else if (gamePanel.gameState == gamePanel.pauseState){ pauseState(key); }
        else if (gamePanel.gameState == gamePanel.inventoryState){ inventoryState(key); }
        else if (gamePanel.gameState == gamePanel.chestState){ chestState(key); }
        else if (gamePanel.gameState == gamePanel.mapState) { mapState(key); }
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
                gamePanel.reinitializeGame();
                gamePanel.gameState = gamePanel.transitionState;
                gamePanel.ui.transitionType = 1;
                gamePanel.ui.subState = 0;
            } else if(gamePanel.ui.commandNum == 1){
                gamePanel.saveLoad.load();
                gamePanel.gameState = gamePanel.transitionState;
                gamePanel.ui.transitionType = 1;
                gamePanel.ui.subState = 0;
            } else if(gamePanel.ui.commandNum == 2){
                System.exit(0);
            }
        }
    }
    public void playState(int key){
        //MOVEMENT
        if (key == KeyEvent.VK_W) up = true;
        if (key == KeyEvent.VK_A) left = true;
        if (key == KeyEvent.VK_S) down = true;
        if (key == KeyEvent.VK_D) right = true;

        // SLOTS
        if (key == KeyEvent.VK_1) {
            gamePanel.player.useHotbarItem(0);
        }
        if (key == KeyEvent.VK_2) gamePanel.player.useHotbarItem(1);
        if (key == KeyEvent.VK_3) gamePanel.player.useHotbarItem(2);
        if (key == KeyEvent.VK_4) gamePanel.player.useHotbarItem(3);
        if (key == KeyEvent.VK_5) gamePanel.player.useHotbarItem(4);

        // MISC
        if(key == KeyEvent.VK_ESCAPE) gamePanel.gameState = gamePanel.pauseState;
        if(key == KeyEvent.VK_E) gamePanel.gameState = gamePanel.inventoryState;
        if(key == KeyEvent.VK_E && gamePanel.player.canOpen) gamePanel.gameState = gamePanel.chestState;
        if(key == KeyEvent.VK_M) gamePanel.gameState = gamePanel.mapState;
        if(key == KeyEvent.VK_F3) {
            showDebug = !showDebug;
            gamePanel.tileManager.drawPath = !gamePanel.tileManager.drawPath;
        }
    }
    public void pauseState(int key) {
        UI ui = gamePanel.ui;
        if (key == KeyEvent.VK_ESCAPE) {
            gamePanel.gameState = gamePanel.playState;
            ui.commandNum = 0;
            ui.subState = 0;
            gamePanel.config.saveConfig();
            saved = false;
            return;
        }

        switch (ui.subState) {
            case 0: // MAIN PAUSE MENU
                handleMainMenuInput(key, ui);
                break;
            case 1: // FULL SCREEN SETTINGS
                handleYesNoInput(key, ui);
                if (key == KeyEvent.VK_ENTER) enterFullScreenSettings(ui);
                break;
            case 2: // CONTROL SCREEN
                if (key == KeyEvent.VK_ENTER) ui.subState = 0;
                break;
            case 3: // QUIT CONFIRMATION
                handleYesNoInput(key, ui);
                if (key == KeyEvent.VK_ENTER) enterQuitConfirmation(ui);
                break;
        }
    }
    private void handleMainMenuInput(int key, UI ui) {
        switch (key) {
            case KeyEvent.VK_W -> {
                ui.commandNum--;
                if (ui.commandNum < 0) ui.commandNum = 6;
            }
            case KeyEvent.VK_S -> {
                ui.commandNum++;
                if (ui.commandNum > 6) ui.commandNum = 0;
            }
            case KeyEvent.VK_A -> {
                if (ui.commandNum == 2 && ui.volume > 0) {
                    ui.volume--;
                    gamePanel.config.saveConfig();
                }
            }
            case KeyEvent.VK_D -> {
                if (ui.commandNum == 2 && ui.volume < 6) {
                    ui.volume++;
                    gamePanel.config.saveConfig();
                }
            }
            case KeyEvent.VK_ENTER -> {
                switch (ui.commandNum) {
                    case 0 -> ui.subState = 1;  // FULLSCREEN MENU
                    case 1 -> { gamePanel.map.miniMapOn = !gamePanel.map.miniMapOn; gamePanel.config.saveConfig(); } // MINIMAP
                    case 2 -> {} // VOLUME
                    case 3 -> ui.subState = 2; // CONTROLS
                    case 4 -> { gamePanel.saveLoad.save(); saved = true; } // SAVE
                    case 5 -> { ui.subState = 3; ui.commandNum = 1; saved = false;} // QUIT CONFIRM MENU
                    case 6 -> { gamePanel.gameState = gamePanel.playState; ui.commandNum = 0; gamePanel.config.saveConfig(); saved = false;} // BACK
                }
            }
        }
    }
    private void handleYesNoInput(int key, UI ui) {
        if (key == KeyEvent.VK_W) {
            ui.commandNum--;
            if (ui.commandNum < 0) ui.commandNum = 1;
        }
        if (key == KeyEvent.VK_S) {
            ui.commandNum++;
            if (ui.commandNum > 1) ui.commandNum = 0;
        }
    }
    private void enterFullScreenSettings(UI ui) {
        if (ui.commandNum == 0) {
            gamePanel.fullScreen = !gamePanel.fullScreen;
            gamePanel.config.saveConfig();
            System.exit(0);
        } else {
            ui.subState = 0;
            ui.commandNum = 0;
        }
    }
    private void enterQuitConfirmation(UI ui) {
        if (ui.commandNum == 0) {
            gamePanel.gameState = gamePanel.titleState;
        } else {
            ui.subState = 0;
            ui.commandNum = 3;
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
            } else {
                gamePanel.ui.slotRow = 3;
            }
        }
        if (key == KeyEvent.VK_A) {
            if(gamePanel.ui.slotCol > 0) {
                gamePanel.ui.slotCol--;
            } else {
                gamePanel.ui.slotCol = 4;
            }
        }
        if (key == KeyEvent.VK_S) {
            if(gamePanel.ui.slotRow < 3) {
                gamePanel.ui.slotRow++;
            } else {
                gamePanel.ui.slotRow = 0;
            }
        }
        if (key == KeyEvent.VK_D) {
            if(gamePanel.ui.slotCol < 4) {
                gamePanel.ui.slotCol++;
            } else {
                gamePanel.ui.slotCol = 0;
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
        if (key == KeyEvent.VK_ESCAPE || key == KeyEvent.VK_E) {
            gamePanel.gameState = gamePanel.playState;
            gamePanel.ui.slotRow = 0;
            gamePanel.ui.slotCol = 0;
            gamePanel.player.currentChest = null;
        }

        // Navigation
        if (key == KeyEvent.VK_W) {
            if(gamePanel.ui.slotRow > 0) {
                gamePanel.ui.slotRow--;
            } else {
                // IN CHEST
                if (gamePanel.ui.slotCol < 6){
                    gamePanel.ui.slotRow = 6;
                } else {
                    gamePanel.ui.slotRow = 3;
                }
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
            } else {
                gamePanel.ui.slotRow = 0;
            }
        }

        if (key == KeyEvent.VK_A) {
            if(gamePanel.ui.slotCol > 0) {
                gamePanel.ui.slotCol--;
                // Jump the gap between chest and inventory
                if(gamePanel.ui.slotCol == 6 || gamePanel.ui.slotCol == 7) {
                    gamePanel.ui.slotCol = 5; // Move to last chest column
                }
            } else {
                if(gamePanel.ui.slotRow > 3) {
                    gamePanel.ui.slotRow = 3;
                }
                gamePanel.ui.slotCol = 12;
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
            }  else {
                gamePanel.ui.slotCol = 0;
            }
        }

        // Item transfer
        if (key == KeyEvent.VK_ENTER) {
            gamePanel.player.transferChestItem(gamePanel.ui.slotCol, gamePanel.ui.slotRow);
        }
    }
    public void mapState(int key) {
        if(key == KeyEvent.VK_ESCAPE || key == KeyEvent.VK_M) { gamePanel.gameState = gamePanel.playState; }
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