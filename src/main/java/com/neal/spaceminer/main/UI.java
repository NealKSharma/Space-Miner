package com.neal.spaceminer.main;

import com.neal.spaceminer.entity.Entity;
import com.neal.spaceminer.object.OBJ_Chest;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class UI {

    GamePanel gamePanel;
    Graphics2D g2;
    Font arial_40;

    BufferedImage titleScreenBackground;
    Color subWindowBackground = new Color(0, 0, 0, 170);
    BasicStroke borderStroke = new BasicStroke(5);

    public int commandNum = 0;
    public int subState = 0;
    public int volume = 0;
    int counter = 0;

    public int slotCol = 0;
    public int slotRow = 0;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        arial_40 = new Font("Arial", Font.PLAIN, 25);

        loadTitleBackground();
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
        } else if(gamePanel.gameState == gamePanel.transitionState){
            drawTransition();
        }
        g2.setFont(originalFont);
    }
    public void drawTitleScreen() {

        g2.drawImage(titleScreenBackground, 0, 0, gamePanel.screenWidth, gamePanel.screenHeight, null);

        // HEADING
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 64F));
        String text = "SPACE-MINER";
        int x = gamePanel.tileSize;
        int y = gamePanel.tileSize * 2;
        g2.setColor(Color.DARK_GRAY);
        g2.drawString(text, x+3, y+3);
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        // MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
        text = "New Game";
        y += gamePanel.tileSize * 2;
        if(commandNum == 0){
            g2.drawString(">", x - (gamePanel.tileSize/2), y);
        }
        g2.setColor(Color.DARK_GRAY);
        g2.drawString(text, x+3, y+3);
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);


        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
        text = "Load Game";
        y += gamePanel.tileSize;
        if(commandNum == 1){
            g2.drawString(">", x - (gamePanel.tileSize/2), y);
        }
        g2.setColor(Color.DARK_GRAY);
        g2.drawString(text, x+3, y+3);
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);


        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
        text = "Quit";
        y += gamePanel.tileSize;
        if(commandNum == 2){
            g2.drawString(">", x - (gamePanel.tileSize/2), y);
        }
        g2.setColor(Color.DARK_GRAY);
        g2.drawString(text, x+3, y+3);
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
    }
    public void loadTitleBackground() {
        try {
            titleScreenBackground = ImageIO.read(
                    Objects.requireNonNull(getClass().getResourceAsStream("/tiles/titleBackground.png"))
            );
            Utility utility = new Utility();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void drawTransition(){
        counter++;
        g2.setColor(new Color(0, 0,0,counter*3));
        g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        if(counter == 85){
            counter = 0;
            gamePanel.gameState = gamePanel.playState;
        }
    }
    public void drawPlayScreen() {
        int frameX = (gamePanel.tileSize * 14) + 10;
        int frameY = (gamePanel.tileSize * 10) + 10;
        int frameWidth = (gamePanel.tileSize * 6) - 25;
        int frameHeight = (gamePanel.tileSize * 2) - 45;

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

        if (gamePanel.player.canOpen) {
            g2.drawString("Press F to interact", 10, gamePanel.screenHeight / 2);
        }
    }
    public void drawPauseScreen() {
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(32F));

        // SUB WINDOW
        int frameX = gamePanel.tileSize * 6;
        int frameY = gamePanel.tileSize;
        int frameWidth = gamePanel.tileSize * 8;
        int frameHeight = gamePanel.tileSize * 10;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState){
            case 0: options_heading(frameX, frameY); break;
            case 1: options_fullScreenNotification(frameX, frameY); break;
            case 2: options_controls(frameX, frameY); break;
            case 3: options_endGameConfirmation(frameX, frameY); break;
        }
    }
    public void options_heading(int frameX, int frameY){
        // TITLE
        String text = "OPTIONS";
        int textX = getXforCenteredText(text);
        int textY = frameY + gamePanel.tileSize;
        g2.drawString(text, textX, textY);

        // FULLSCREEN
        textX = frameX + gamePanel.tileSize;
        textY += (int) (gamePanel.tileSize*1.5);
        g2.drawString("Full Screen", textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX-32, textY);
        }

        // SOUND
        textY += gamePanel.tileSize;
        g2.drawString("Sound", textX, textY);
        if(commandNum == 1){
            g2.drawString(">", textX-32, textY);
        }

        // CONTROLS
        textY += gamePanel.tileSize;
        g2.drawString("Controls", textX, textY);
        if(commandNum == 2){
            g2.drawString(">", textX-32, textY);
        }

        // SAVE GAME
        textY += gamePanel.tileSize;
        g2.drawString("Save Game", textX, textY);
        if(commandNum == 3){
            g2.drawString(">", textX-32, textY);
        }

        // QUIT
        textY += gamePanel.tileSize;
        g2.drawString("Quit", textX, textY);
        if(commandNum == 4){
            g2.drawString(">", textX-32, textY);
        }

        // BACK
        textY = frameY + gamePanel.tileSize*9;
        g2.drawString("Back", textX, textY);
        if(commandNum == 5){
            g2.drawString(">", textX-32, textY);
        }

        // FULLSCREEN CHECKBOX
        textX = (int) (frameX + gamePanel.tileSize*4.5);
        textY = frameY + gamePanel.tileSize*2;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, gamePanel.tileSize/2, gamePanel.tileSize/2);
        if(gamePanel.fullScreen){
            g2.fillRect(textX + 5, textY + 5, (gamePanel.tileSize/2) - 9, (gamePanel.tileSize/2) - 9);
        }

        // SOUND VOLUME
        textY += gamePanel.tileSize;
        g2.drawRect(textX, textY, (gamePanel.tileSize*3) + 9, gamePanel.tileSize/2);
        int volumeWidth = (gamePanel.tileSize/2) * volume; // TEMPORARY VARIABLE
        g2.fillRect(textX + 5, textY + 5, volumeWidth, (gamePanel.tileSize/2) - 9);
        gamePanel.config.saveConfig();

        // GAME SAVE
        if(gamePanel.keyHandler.saved){
            textY += (int) (gamePanel.tileSize*2.5);
            g2.drawString("Saved!", textX, textY);
        }
    }
    public void options_fullScreenNotification(int frameX, int frameY){
        int textX = frameX + gamePanel.tileSize;
        int textY = frameY + gamePanel.tileSize*3;
        String text = "The change will take effect \nafter restarting the game.";
        for(String line: text.split("\n")){
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // Restart
        textY = frameY + gamePanel.tileSize*8;
        g2.drawString("Restart", textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX-32, textY);
        }

        // BACK
        textY += gamePanel.tileSize;
        g2.drawString("Back", textX, textY);
        if(commandNum == 1){
            g2.drawString(">", textX-32, textY);
        }
    }
    public void options_controls(int frameX, int frameY){
        String text = "CONTROLS";
        int textX = getXforCenteredText(text);
        int textY = frameY + gamePanel.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gamePanel.tileSize;
        textY += (int) (gamePanel.tileSize*1.5);
        g2.drawString("Move", textX, textY); textY += gamePanel.tileSize;
        g2.drawString("Interact", textX, textY); textY += gamePanel.tileSize;
        g2.drawString("Inventory", textX, textY); textY += gamePanel.tileSize;
        g2.drawString("Slots", textX, textY); textY += gamePanel.tileSize;
        g2.drawString("Options", textX, textY);

        textX = frameX + gamePanel.tileSize*5;
        textY = (int) (frameY + gamePanel.tileSize*2.5);
        g2.drawString("W A S D", textX, textY); textY += gamePanel.tileSize;
        g2.drawString("F", textX, textY); textY += gamePanel.tileSize;
        g2.drawString("E", textX, textY); textY += gamePanel.tileSize;
        g2.drawString("1 2 3 4 5", textX, textY); textY += gamePanel.tileSize;
        g2.drawString("ESC", textX, textY);

        textX = frameX + gamePanel.tileSize;
        textY = frameY + gamePanel.tileSize*9;
        g2.drawString("Back", textX, textY);
        g2.drawString(">", textX-32, textY);

    }
    public void options_endGameConfirmation(int frameX, int frameY){
        int textX = frameX + gamePanel.tileSize;
        int textY = frameY + gamePanel.tileSize*3;

        String text = "Quit the game and return \nto the title screen?";
        for(String line: text.split("\n")){
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // YES
        text = "Yes";
        textX = getXforCenteredText(text);
        textY += gamePanel.tileSize*3;
        g2.drawString(text, textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX-32, textY);
        }

        // NO
        text = "No";
        textX = getXforCenteredText(text);
        textY += gamePanel.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum == 1){
            g2.drawString(">", textX-32, textY);
        }
    }
    public void drawInventory() {
        int frameX = gamePanel.tileSize * 13;
        int frameY = gamePanel.tileSize * 2;
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
            for(String line: item.description.split("\n")){
                g2.drawString(line, textX, textY);
                textY += 32;
            }
        }
    }
    public void drawChest() {
        // GET THE CURRENT CHEST OBJECT
        OBJ_Chest chest = (OBJ_Chest) gamePanel.player.currentChest;

        int frameChestX = gamePanel.tileSize * 5;
        int frameChestY = gamePanel.tileSize * 2;
        int frameChestWidth = gamePanel.tileSize * 7;
        int frameChestHeight = gamePanel.tileSize * 8;

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 50));
        String text = "Chest";
        g2.drawString(text, frameChestX, frameChestY - 15);

        drawSubWindow(frameChestX, frameChestY, frameChestWidth, frameChestHeight);

        // PLAYER INVENTORY WINDOW (Right side - 5 cols x 4 rows)
        int frameX = gamePanel.tileSize * 13;
        int frameY = gamePanel.tileSize * 2;
        int frameWidth = gamePanel.tileSize * 6;
        int frameHeight = gamePanel.tileSize * 5;

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 50));
        text = "Inventory";
        g2.drawString(text, frameX, frameY - 15);

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // DRAW CHEST ITEMS (6x7 grid)
        final int chestSlotXstart = frameChestX + 20;
        final int chestSlotYstart = frameChestY + 20;
        int slotSize = gamePanel.tileSize + 3;

        int slotX = chestSlotXstart;
        int slotY = chestSlotYstart;

        for (int i = 0; i < chest.chestInv.size(); i++) {
            Entity item = chest.chestInv.get(i);
            if(item != null){
                g2.drawImage(item.down1, slotX, slotY, gamePanel.tileSize, gamePanel.tileSize, null);
            }

            slotX += slotSize;

            if ((i + 1) % 6 == 0) { // 6 columns
                slotX = chestSlotXstart;
                slotY += slotSize;
            }
        }

        // DRAW PLAYER INVENTORY ITEMS (5x4 grid)
        final int invSlotXstart = frameX + 20;
        final int invSlotYstart = frameY + 20;

        slotX = invSlotXstart;
        slotY = invSlotYstart;

        for (int i = 0; i < gamePanel.player.inventory.size(); i++) {
            Entity item = gamePanel.player.inventory.get(i);
            if(item != null){
                g2.drawImage(item.down1, slotX, slotY, gamePanel.tileSize, gamePanel.tileSize, null);
            }

            slotX += slotSize;

            if (i == 4 || i == 9 || i == 14 || i == 19) { // 5 columns
                slotX = invSlotXstart;
                slotY += slotSize;
            }
        }

        // DRAW CURSOR
        int cursorX, cursorY;

        if(slotCol < 6) {
            // Cursor is in chest area
            cursorX = chestSlotXstart + (slotSize * slotCol);
            cursorY = chestSlotYstart + (slotSize * slotRow);
        } else {
            // Cursor is in inventory area (offset by 8 to account for gap)
            cursorX = invSlotXstart + (slotSize * (slotCol - 8));
            cursorY = invSlotYstart + (slotSize * slotRow);
        }

        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, gamePanel.tileSize, gamePanel.tileSize, 10, 10);

        // DESCRIPTION FRAME
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeight + 10;
        int dFrameWidth = frameWidth;
        int dFrameHeight = gamePanel.tileSize * 3;

        // DRAW DESCRIPTION TEXT
        int textX = dFrameX + 20;
        int textY = dFrameY + gamePanel.tileSize - 20;
        g2.setFont(g2.getFont().deriveFont(28F));

        Entity item = null;

        // Determine which item to show based on cursor position
        if(slotCol < 6) {
            // Cursor is in chest area
            int chestIndex = slotCol + (slotRow * 6);
            if(chestIndex < chest.chestInv.size()) {
                item = chest.chestInv.get(chestIndex);
            }
        } else {
            // Cursor is in inventory area
            int invIndex = (slotCol - 8) + (slotRow * 5);
            if(invIndex < gamePanel.player.inventory.size()) {
                item = gamePanel.player.inventory.get(invIndex);
            }
        }

        // Draw description if item exists
        if(item != null){
            drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
            for(String line: item.description.split("\n")){
                g2.drawString(line, textX, textY);
                textY += 32;
            }
        }
    }
    public int getItemIndexOnSlotInventory(){
        return slotCol + (slotRow*5);
    }
    public void drawSubWindow(int x, int y, int width, int height) {
        g2.setColor(subWindowBackground);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        g2.setColor(Color.white);
        g2.setStroke(borderStroke);
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }
    public int getXforCenteredText(String text) {

        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gamePanel.screenWidth / 2 - length / 2;
    }
}
