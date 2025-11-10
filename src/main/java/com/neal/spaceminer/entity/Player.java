package com.neal.spaceminer.entity;

import com.neal.spaceminer.main.GamePanel;
import com.neal.spaceminer.main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {

    GamePanel gamePanel;
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;
    boolean use = false;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        screenX = gamePanel.screenWidth / 2 - gamePanel.tileSize / 2;
        screenY = gamePanel.screenHeight / 2 - gamePanel.tileSize / 2;

        solidArea = new Rectangle();
        solidArea.x = 24;
        solidArea.y = 24;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = gamePanel.tileSize -  40;
        solidArea.height = gamePanel.tileSize -  40;

        initialize();
        getImage();
    }

    public void initialize() {
        worldX = gamePanel.tileSize * 23;
        worldY = gamePanel.tileSize * 21;
        speed = 2;
        direction = "down";
    }

    public void getImage() {
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/astronaut/back1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/astronaut/back2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/astronaut/front1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/astronaut/front2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/astronaut/left1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/astronaut/left2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/astronaut/right1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/astronaut/right2.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyHandler.up || keyHandler.down || keyHandler.left || keyHandler.right) {
            if (keyHandler.up) {
                direction = "up";
            }
            if (keyHandler.down) {
                direction = "down";
            }
            if (keyHandler.left) {
                direction = "left";
            }
            if (keyHandler.right) {
                direction = "right";
            }

            collisionOn = false;
            gamePanel.collisionChecker.checkTile(this);

            int objIndex = gamePanel.collisionChecker.checkObject(this, true);
            if (objIndex != -1) pickUpObject(objIndex);

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (!collisionOn) {
                switch (direction) {
                    case "up":
                        worldY -= speed; break;
                    case "down":
                        worldY += speed; break;
                    case "left":
                        worldX -= speed; break;
                    case "right":
                        worldX += speed; break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        int objIndex = gamePanel.collisionChecker.checkObject(this, true);
        if (objIndex != -1) pickUpObject(objIndex);
    }

    public void pickUpObject(int index) {
        String objectName = gamePanel.obj[index].name;

        switch(objectName){
            case "Key":
                if(keyHandler.use){

                    gamePanel.obj[index] = null;
                    use = true;

                    keyHandler.use = false;
                    }
                break;
        }
    }

    public void draw(Graphics g2) {

        BufferedImage image = null;
        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
