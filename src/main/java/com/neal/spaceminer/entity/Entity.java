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
    public String direction = "down";

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public String name;
    public boolean collision = false;

    // COLLISION
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
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

        // Only draw if visible on screen
        if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {

            switch (direction) {
                case "up":    image = (spriteNum == 1) ? up1 : up2;       break;
                case "down":  image = (spriteNum == 1) ? down1 : down2;   break;
                case "left":  image = (spriteNum == 1) ? left1 : left2;   break;
                case "right": image = (spriteNum == 1) ? right1 : right2; break;
            }

            if(image != null) {
                int displayWidth, displayHeight;

                // If this entity is flagged to be drawn small (like a Pickaxe on floor)
                if (shrink) {
                    displayWidth = gamePanel.tileSize / 2;
                    displayHeight = gamePanel.tileSize / 2;
                }
                // If it's a big object (like a Chest), use the image's actual size
                else {
                    displayWidth = image.getWidth();
                    displayHeight = image.getHeight();
                }

                // Calculate Center Offset
                int offsetX = (gamePanel.tileSize - displayWidth) / 2;
                int offsetY = (gamePanel.tileSize - displayHeight) / 2;

                g2.drawImage(image, screenX + offsetX, screenY + offsetY, displayWidth, displayHeight, null);
            }
        }
    }
    public BufferedImage setup(String imagePath) {
        Utility utility = new Utility();
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResource(imagePath + ".png")));
            image = utility.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
