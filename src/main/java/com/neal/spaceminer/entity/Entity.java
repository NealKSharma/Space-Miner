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
    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        // Only draw if visible on screen
        if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {

            switch (direction) {
                case "up":
                    if(!mining){
                        image = (spriteNum == 1) ? up1 : up2;
                    } else {
                        tempScreenY = screenY - gamePanel.tileSize;
                        image = (spriteNum == 1) ? mineUp1 : mineUp2;
                    }
                    break;
                case "down":
                    if(!mining){
                        image = (spriteNum == 1) ? down1 : down2;
                    } else {
                        image = (spriteNum == 1) ? mineDown1 : mineDown2;
                    }
                    break;
                case "left":
                    if(!mining){
                        image = (spriteNum == 1) ? left1 : left2;
                    } else {
                        tempScreenX = screenX - gamePanel.tileSize;
                        image = (spriteNum == 1) ? mineLeft1 : mineLeft2;
                    }
                    break;
                case "right":
                    if(!mining){
                        image = (spriteNum == 1) ? right1 : right2;
                    } else {
                        image = (spriteNum == 1) ? mineRight1 : mineRight2;
                    }
                    break;
            }

            if(image != null) {
                int displayWidth, displayHeight;

                // If this entity is flagged to be drawn small (like a Pickaxe on floor)
                if (shrink) {
                    displayWidth = gamePanel.tileSize / 2;
                    displayHeight = gamePanel.tileSize / 2;

                    tempScreenX += (gamePanel.tileSize - displayWidth) / 2;
                    tempScreenY += (gamePanel.tileSize - displayHeight) / 2;
                }
                // If it's a big object (like a Chest), use the image's actual size
                else {
                    displayWidth = image.getWidth();
                    displayHeight = image.getHeight();
                }

                g2.drawImage(image, tempScreenX, tempScreenY, displayWidth, displayHeight, null);
            }
        }
        g2.setColor(Color.red);
        g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
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
