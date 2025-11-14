package com.neal.spaceminer.main;

import java.awt.*;

public class UI {

    GamePanel gamePanel;
    Graphics2D g2;
    Font arial_40;

    public int slotCol = 0;
    public int slotRow = 0;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        arial_40 = new Font("Arial", Font.PLAIN, 25);
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);

        if (gamePanel.gameState == gamePanel.playState) {
            if (gamePanel.player.canUse) {
                g2.drawString("Press E to interact", 25, gamePanel.screenHeight / 2);
            }
        }

        if (gamePanel.gameState == gamePanel.pauseState) {
            drawPauseScreen();
        }

        if(gamePanel.gameState == gamePanel.inventoryState){
            drawInventory();
        }

        if(gamePanel.gameState == gamePanel.chestState){
            drawChest();
        }
    }

    public void drawPauseScreen() {

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80));
        String text = "PAUSED";

        g2.drawString(text, getXforCenteredText(text), gamePanel.screenHeight / 2);
    }

    public void drawInventory() {

        int frameX = gamePanel.tileSize * 9;
        int frameY = gamePanel.tileSize;
        int frameWidth = gamePanel.tileSize * 6;
        int frameHeight = gamePanel.tileSize * 5;

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 50));
        String text = "Inventory";
        g2.drawString(text, frameX + 85, frameY + frameHeight + 50);

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // SLOTS
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;

        // CURSOR
        int cursorX = slotXstart + (gamePanel.tileSize * slotCol);
        int cursorY = slotYstart + (gamePanel.tileSize * slotRow);
        int cursorWidth = gamePanel.tileSize;
        int cursorHeight = gamePanel.tileSize;

        // DRAW CURSOR
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

    }

    public void drawChest() {

        int frameX = gamePanel.tileSize * 9;
        int frameY = gamePanel.tileSize;
        int frameWidth = gamePanel.tileSize * 6;
        int frameHeight = gamePanel.tileSize * 5;

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 50));
        String text = "Chest";
        g2.drawString(text, frameX + 85, frameY + frameHeight + 50);

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // SLOTS
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;

        // CURSOR
        int cursorX = slotXstart + (gamePanel.tileSize * slotCol);
        int cursorY = slotYstart + (gamePanel.tileSize * slotRow);
        int cursorWidth = gamePanel.tileSize;
        int cursorHeight = gamePanel.tileSize;

        // DRAW CURSOR
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

    }

    public void drawSubWindow(int x, int y, int width, int height) {

        Color color = new Color(173, 216, 230, 50); // LIGHT BLUE
        g2.setColor(color);

        g2.fillRoundRect(x, y, width, height, 35, 35);

        color = new Color(255, 255, 255);
        g2.setColor(color);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);

    }

    public int getXforCenteredText(String text) {

        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gamePanel.screenWidth / 2 - length / 2;
    }
}
