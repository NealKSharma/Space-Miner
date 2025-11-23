package com.neal.spaceminer.main;

import com.neal.spaceminer.entity.Entity;

import java.awt.*;

public class UI {

    GamePanel gamePanel;
    Graphics2D g2;
    Font arial_40;

    public int commandNum = 0;

    public int slotCol = 0;
    public int slotRow = 0;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        arial_40 = new Font("Arial", Font.PLAIN, 25);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        Font originalFont = g2.getFont();

        g2.setFont(arial_40);
        g2.setColor(Color.white);

        if (gamePanel.gameState == gamePanel.playState) {
            drawPlayScreen();
        } else if (gamePanel.gameState == gamePanel.titleState){
            drawTitleScreen();
        } else if (gamePanel.gameState == gamePanel.pauseState) {
            drawPauseScreen();
        } else if(gamePanel.gameState == gamePanel.inventoryState){
            drawInventory();
        } else if(gamePanel.gameState == gamePanel.chestState){
            drawChest();
        }
        g2.setFont(originalFont);
    }
    public void drawTitleScreen() {

        g2.setColor(new Color(20, 0, 20));
        g2.fillRect(0,0,gamePanel.screenWidth, gamePanel.screenHeight);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String text = "Space Miner";
        int x = getXforCenteredText(text);
        int y = gamePanel.tileSize * 3;

        g2.setColor(Color.DARK_GRAY);
        g2.drawString(text, x+5, y+5);

        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        // IMAGE
        x = gamePanel.screenWidth/2 - (gamePanel.tileSize*2) / 2;
        y +=  gamePanel.tileSize;
        g2.drawImage(gamePanel.player.down1, x, y, gamePanel.tileSize*2, gamePanel.tileSize*2, null);

        // MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = "NEW GAME";
        x = getXforCenteredText(text);
        y += (int) (gamePanel.tileSize * 3.5);
        if(commandNum == 0){
            g2.drawString(">", x-gamePanel.tileSize, y);
        }

        g2.setColor(Color.DARK_GRAY);
        g2.drawString(text, x+5, y+5);

        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);


        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = "LOAD GAME";
        x = getXforCenteredText(text);
        y += gamePanel.tileSize;
        if(commandNum == 1){
            g2.drawString(">", x-gamePanel.tileSize, y);
        }

        g2.setColor(Color.DARK_GRAY);
        g2.drawString(text, x+5, y+5);

        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);


        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = "QUIT";
        x = getXforCenteredText(text);
        y += gamePanel.tileSize;
        if(commandNum == 2){
            g2.drawString(">", x-gamePanel.tileSize, y);
        }

        g2.setColor(Color.DARK_GRAY);
        g2.drawString(text, x+5, y+5);

        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
    }
    public void drawPlayScreen() {
        int frameX = (gamePanel.tileSize * 10) + 10;
        int frameY = (gamePanel.tileSize * 10) + 10;
        int frameWidth = (gamePanel.tileSize * 6) - 30;
        int frameHeight = (gamePanel.tileSize * 2) - 35;

        // SLOTS
        final int slotXstart = frameX + 15;
        final int slotYstart = frameY + 10;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gamePanel.tileSize+3;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // DRAW PLAYER'S ITEMS
        for (int i = 0; i < 5; i++) {
            Entity item = gamePanel.player.inventory.get(i);
            if(item != null){
                g2.drawImage(item.down1, slotX, slotY,null);
            }
            slotX += slotSize;
        }

        if (gamePanel.player.canUse) {
            g2.drawString("Press E to interact", 10, gamePanel.screenHeight / 2);
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
        g2.drawString(text, frameX, frameY - 15);

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // SLOTS
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gamePanel.tileSize+3;

        // DRAW PLAYER'S ITEMS
        for (int i = 0; i < gamePanel.player.inventory.size(); i++) {
            Entity item = gamePanel.player.inventory.get(i);
            if(item != null){
                g2.drawImage(item.down1, slotX, slotY,null);
            }

            slotX += slotSize;

            if (i == 4 || i == 9 || i == 14){
                slotX = slotXstart;
                slotY += slotSize;
            }
        }

        // CURSOR
        int cursorX = slotXstart + (slotSize * slotCol);
        int cursorY = slotYstart + (slotSize * slotRow);
        int cursorWidth = gamePanel.tileSize;
        int cursorHeight = gamePanel.tileSize;

        // DRAW CURSOR
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        // DESCRIPTION FRAME
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeight + 10;
        int dFrameWidth = frameWidth;
        int dFrameHeight = gamePanel.tileSize*3;

        // DRAW DESCRIPTION TEXT
        int textX = dFrameX + 20;
        int textY = dFrameY + gamePanel.tileSize-20;
        g2.setFont(g2.getFont().deriveFont(28F));

        int itemIndex = getItemIndexOnSlotInventory();
        Entity item = gamePanel.player.inventory.get(itemIndex);

        if(item != null){
            drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
            for(String line: gamePanel.player.inventory.get(itemIndex).description.split("\n")){
                g2.drawString(line, textX, textY);
                textY += 32;
            }
        }
    }
    public void drawChest() {
        // Inventory
        int frameX = gamePanel.tileSize * 9;
        int frameY = gamePanel.tileSize;
        int frameWidth = gamePanel.tileSize * 6;
        int frameHeight = gamePanel.tileSize * 5;

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 50));
        String text = "Inventory";
        g2.drawString(text, frameX + 85, frameY + frameHeight + 50);

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Chest
        int frameChestX = gamePanel.tileSize;
        int frameChestY = gamePanel.tileSize;
        int frameChestWidth = gamePanel.tileSize * 7;
        int frameChestHeight = gamePanel.tileSize * 8;

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 50));
        text = "Chest";
        g2.drawString(text, frameChestX + 160, frameChestY + frameChestHeight + 50);

        drawSubWindow(frameChestX, frameChestY, frameChestWidth, frameChestHeight);

        // SLOTS
        final int slotXstart = frameChestX + 20;
        final int slotYstart = frameChestY + 20;
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
    public int getItemIndexOnSlotInventory(){
        return slotCol + (slotRow*5);
    }
    public void drawSubWindow(int x, int y, int width, int height) {

        Color color = new Color(0, 0, 0, 170);
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
