package com.neal.spaceminer.entity;

import com.neal.spaceminer.main.GamePanel;
import com.neal.spaceminer.main.KeyHandler;
import com.neal.spaceminer.main.Utility;

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
    public boolean canUse = false;
    public boolean escape = false;

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
        solidArea.width = gamePanel.tileSize - 40;
        solidArea.height = gamePanel.tileSize - 40;

        initialize();
        getImage();
    }

    public void initialize() {
        worldX = gamePanel.tileSize * 24;
        worldY = gamePanel.tileSize * 24;
        speed = 2;
        direction = "down";
    }

    public void getImage() {
        up1 = setup("back1");
        up2 = setup("back2");
        down1 = setup("front1");
        down2 = setup("front2");
        left1 = setup("left1");
        left2 = setup("left2");
        right1 = setup("right1");
        right2 = setup("right2");
    }

    public BufferedImage setup(String imageName) {
        Utility utility = new Utility();
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/astronaut/" + imageName + ".png")));
            image = utility.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
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
            if (objIndex != -1) interactObject(objIndex);

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (!collisionOn) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
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
        if (objIndex != -1) {
            interactObject(objIndex);
            if (gamePanel.obj[objIndex].name.equals("Chest")) {
                canUse = true;
            }
        } else {
            canUse = false;
        }
    }

    public void interactObject(int index) {
        String objectName = gamePanel.obj[index].name;

        switch (objectName) {
            case "Chest":
                if (keyHandler.use) {

                    use = true;

                    keyHandler.use = false;
                }
                if (keyHandler.escape) {

                    escape = true;

                    keyHandler.escape = false;
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
        g2.drawImage(image, screenX, screenY, null);
    }
}
