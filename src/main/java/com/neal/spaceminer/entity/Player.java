package com.neal.spaceminer.entity;

import com.neal.spaceminer.main.GamePanel;
import com.neal.spaceminer.main.KeyHandler;
import com.neal.spaceminer.object.OBJ_Chest;
import com.neal.spaceminer.object.OBJ_Pickaxe;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity {

    GamePanel gamePanel;
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;
    public boolean canUse = false;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {

        super(gamePanel);

        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        screenX = gamePanel.screenWidth / 2 - gamePanel.tileSize / 2;
        screenY = gamePanel.screenHeight / 2 - gamePanel.tileSize / 2;

        solidArea = new Rectangle();
        solidArea.x = 18;
        solidArea.y = 15;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = gamePanel.tileSize - 35;
        solidArea.height = gamePanel.tileSize - 25;

        initialize();
        getImage();
        setItems();
    }

    public void initialize() {
        worldX = gamePanel.tileSize * 24;
        worldY = gamePanel.tileSize * 24;
        speed = 2;

    }

    public void getImage() {
        up1 = setup("/astronaut/back1");
        up2 = setup("/astronaut/back2");
        down1 = setup("/astronaut/front1");
        down2 = setup("/astronaut/front2");
        left1 = setup("/astronaut/left1");
        left2 = setup("/astronaut/left2");
        right1 = setup("/astronaut/right1");
        right2 = setup("/astronaut/right2");
    }

    public void interactObject(int index) {
        String objectName = gamePanel.obj[index].name;

        switch (objectName) {
            case "Chest":
                if (keyHandler.chest) {
                    gamePanel.gameState = gamePanel.chestState;
                }
                break;
        }
    }

    public void setItems(){
        inventory.add(new OBJ_Pickaxe(gamePanel));
        inventory.add(new OBJ_Pickaxe(gamePanel));
        inventory.add(new OBJ_Pickaxe(gamePanel));
        inventory.add(new OBJ_Pickaxe(gamePanel));
        inventory.add(new OBJ_Pickaxe(gamePanel));
        inventory.add(new OBJ_Pickaxe(gamePanel));
        inventory.add(new OBJ_Pickaxe(gamePanel));
        inventory.add(new OBJ_Pickaxe(gamePanel));
        inventory.add(new OBJ_Pickaxe(gamePanel));
        inventory.add(new OBJ_Pickaxe(gamePanel));
        inventory.add(new OBJ_Pickaxe(gamePanel));
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
}
