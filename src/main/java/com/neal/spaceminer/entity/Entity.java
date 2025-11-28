package com.neal.spaceminer.entity;

import com.neal.spaceminer.main.GamePanel;
import com.neal.spaceminer.main.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Entity {

    GamePanel gamePanel;

    public int worldX, worldY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage mineUp1, mineUp2, mineDown1, mineDown2, mineLeft1,  mineLeft2, mineRight1, mineRight2;
    public String direction = "down";

    public int spriteCounter = 0;
    public int spriteNum = 1;
    boolean mining = false;

    public String name;
    public boolean collision = false;

    // COLLISION
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle swingArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

    // ITEM ATTRIBUTES
    public String description = "";
    public boolean shrink = false;
    public boolean canPickup;

    public Entity(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        // Temp variables for mining animation offsets
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        // DETERMINE WHICH IMAGE TO USE FIRST
        switch (direction) {
            case "up":
                if (!mining) {
                    image = (spriteNum == 1) ? up1 : up2;
                } else {
                    tempScreenY = screenY - gamePanel.tileSize;
                    image = (spriteNum == 1) ? mineUp1 : mineUp2;
                }
                break;
            case "down":
                if (!mining) {
                    image = (spriteNum == 1) ? down1 : down2;
                } else {
                    image = (spriteNum == 1) ? mineDown1 : mineDown2;
                }
                break;
            case "left":
                if (!mining) {
                    image = (spriteNum == 1) ? left1 : left2;
                } else {
                    tempScreenX = screenX - gamePanel.tileSize;
                    image = (spriteNum == 1) ? mineLeft1 : mineLeft2;
                }
                break;
            case "right":
                if (!mining) {
                    image = (spriteNum == 1) ? right1 : right2;
                } else {
                    image = (spriteNum == 1) ? mineRight1 : mineRight2;
                }
                break;
        }

        // Safety check
        if (image == null) return;

        // GET THE ACTUAL SIZE OF THE ENTITY
        int width = image.getWidth();
        int height = image.getHeight();

        // "shrink" logic (for small items)
        if (shrink) {
            width = gamePanel.tileSize / 2;
            height = gamePanel.tileSize / 2;
            tempScreenX += (gamePanel.tileSize - width) / 2;
            tempScreenY += (gamePanel.tileSize - height) / 2;
        }

        //CHECK VISIBILITY USING THE REAL WIDTH/HEIGHT
        if (worldX + width > gamePanel.player.worldX - gamePanel.player.screenX &&
                worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                worldY + height > gamePanel.player.worldY - gamePanel.player.screenY &&
                worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {

            g2.drawImage(image, tempScreenX, tempScreenY, width, height, null);

            // Draw collision box for debugging
            if(gamePanel.keyHandler.showDebug) {
                g2.setColor(Color.red);
                g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
            }
        }
    }
    public BufferedImage setup(String imagePath, int width, int height) {
        Utility utility = new Utility();
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResource(imagePath + ".png")));
            image = utility.scaleImage(image, width, height);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
