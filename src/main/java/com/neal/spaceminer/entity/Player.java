package com.neal.spaceminer.entity;

import com.neal.spaceminer.main.GamePanel;
import com.neal.spaceminer.main.KeyHandler;
import com.neal.spaceminer.object.OBJ_Chest;
import com.neal.spaceminer.object.OBJ_Pickaxe;

import java.awt.*;
import java.util.ArrayList;

public class Player extends Entity {

    GamePanel gamePanel;
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;

    public Entity currentChest = null;
    public boolean canOpen;
    public boolean hasLight = false;

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

        swingArea.width = gamePanel.tileSize - 16;
        swingArea.height = gamePanel.tileSize - 16;

        initialize();
        getImage();
        getMineImage();

        for(int i = 0; i < maxInventorySize; i++) {
            inventory.add(null);
        }
        setItems();
    }
    public void initialize() {
        worldX = gamePanel.tileSize * 75;
        worldY = gamePanel.tileSize * 80;
        speed = 1;
    }
    public void getImage() {
        up1 = setup("/astronaut/back1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/astronaut/back2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/astronaut/front1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/astronaut/front2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/astronaut/left1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/astronaut/left2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/astronaut/right1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/astronaut/right2", gamePanel.tileSize, gamePanel.tileSize);
    }
    public void getMineImage(){
        mineUp1 = setup("/astronaut_pickaxing/pickaxe_back1", gamePanel.tileSize, gamePanel.tileSize*2);
        mineUp2 = setup("/astronaut_pickaxing/pickaxe_back2", gamePanel.tileSize, gamePanel.tileSize*2);
        mineDown1 = setup("/astronaut_pickaxing/pickaxe_front1", gamePanel.tileSize, gamePanel.tileSize*2);
        mineDown2 = setup("/astronaut_pickaxing/pickaxe_front2", gamePanel.tileSize, gamePanel.tileSize*2);
        mineLeft1 = setup("/astronaut_pickaxing/pickaxe_left1", gamePanel.tileSize*2, gamePanel.tileSize);
        mineLeft2 = setup("/astronaut_pickaxing/pickaxe_left2", gamePanel.tileSize*2, gamePanel.tileSize);
        mineRight1 = setup("/astronaut_pickaxing/pickaxe_right1", gamePanel.tileSize*2, gamePanel.tileSize);
        mineRight2 = setup("/astronaut_pickaxing/pickaxe_right2", gamePanel.tileSize*2, gamePanel.tileSize);
    }
    public void interactWithObject(int index) {
        if(gamePanel.obj[index] != null){
            if (gamePanel.obj[index].canPickup && getFirstEmptySlot() != -1) {
                inventory.set(getFirstEmptySlot(), gamePanel.obj[index]);
                gamePanel.obj[index] = null;
                itemBehaviour();
            } else if ("Chest".equals(gamePanel.obj[index].name)) {
                canOpen = true;
                currentChest = gamePanel.obj[index];
            }
        }
    }
    public void setItems(){
        inventory.set(0, new OBJ_Pickaxe(gamePanel));
        itemBehaviour();
    }
    public void swapItems(int at, int to){
        if (at < 0 || at >= maxInventorySize) return;
        if (to < 0 || to >= maxInventorySize) return;

        Entity item = inventory.get(at);
        inventory.set(at, inventory.get(to));
        inventory.set(to, item);

        itemBehaviour();
    }
    public int getFirstEmptySlot() {
        for (int i = 0; i < maxInventorySize; i++) {
            if (inventory.get(i) == null) return i;
        }
        return -1;  // inventory full
    }
    public void transferChestItem(int slotCol, int slotRow){
        if(currentChest == null) return;
        OBJ_Chest chest = (OBJ_Chest) currentChest;

        if(slotCol < 6) {
            // Taking from chest
            int chestIndex = slotCol + (slotRow * 6);
            if(chestIndex < chest.chestInv.size()) {
                Entity item = chest.chestInv.get(chestIndex);
                if(item != null) {
                    int emptySlot = getFirstEmptySlot();
                    if(emptySlot != -1) {
                        inventory.set(emptySlot, item);
                        chest.chestInv.set(chestIndex, null);
                    }
                }
            }
        } else {
            // Putting into chest
            int invIndex = (slotCol - 8) + (slotRow * 5);
            if(invIndex < inventory.size()) {
                Entity item = inventory.get(invIndex);
                if(item != null) {
                    // Find first empty chest slot
                    for(int i = 0; i < chest.chestInv.size(); i++) {
                        if(chest.chestInv.get(i) == null) {
                            chest.chestInv.set(i, item);
                            inventory.set(invIndex, null);
                            break;
                        }
                    }
                }
            }
        }
        itemBehaviour();
    }
    public void itemBehaviour(){
        boolean wasHoldingLight = hasLight;
        hasLight = searchHotbar("Lumen Cell");
        if (wasHoldingLight != hasLight) {
            gamePanel.environmentManager.refreshLightMap();
        }
    }
    public boolean searchHotbar(String name){
        for(int i = 0; i < 5; i++) {
            if(inventory.get(i) != null && inventory.get(i).name.equals(name)) {
                return true;
            }
        }
        return false;
    }
    public boolean searchInventory(String name){
        for(int i = 0; i < maxInventorySize; i++) {
            if(inventory.get(i) != null && inventory.get(i).name.equals(name)) {
                return true;
            }
        }
        return false;
    }
    public void useHotbarItem(int index){
        // MINING
        if(inventory.get(index) != null && inventory.get(index).name.equals("Iron Pickaxe")) {
            mining = true;
            spriteCounter = 0;
        }
    }
    public void update() {
        if(mining){
            // PLAYER IS MINING
            mining();
        } else if (keyHandler.up || keyHandler.down || keyHandler.left || keyHandler.right) {
            // PLAYER IS MOVING
            if (keyHandler.up) direction = "up";
            if (keyHandler.down) direction = "down";
            if (keyHandler.left) direction = "left";
            if (keyHandler.right) direction = "right";

            canOpen = false;
            collisionOn = false;
            gamePanel.collisionChecker.checkTile(this);

            int objIndex = gamePanel.collisionChecker.checkObject(this, true);
            if (objIndex != -1) interactWithObject(objIndex);

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (!collisionOn) {
                switch (direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 32) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        } else {
            // PLAYER IS STANDING STILL
            int objIndex = gamePanel.collisionChecker.checkObject(this, true);
            if (objIndex != -1) interactWithObject(objIndex);
        }
    }
    public void mining(){
        spriteCounter++;
        if(spriteCounter <= 10){
            spriteNum = 1;
        }
        if(spriteCounter > 10 && spriteCounter <= 50){
            spriteNum = 2;

            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // ADJUST PLAYER's WORLD X/Y FOR THE ATTACK AREA
            switch(direction){
                case "up": worldY -= swingArea.height; break;
                case "down": worldY += swingArea.height; break;
                case "left": worldX -= swingArea.width; break;
                case "right": worldX += swingArea.width; break;
            }
            // SWING AREA BECOMES THE SOLID AREA
            solidArea.width = swingArea.width;
            solidArea.height = swingArea.height;

            int objectIndex = gamePanel.collisionChecker.checkObject(this, true);
            if(objectIndex != -1) mineObject(objectIndex);

            // AFTER CHECKING FOR COLLISION RESTORE ORIGINAL DATA
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if(spriteCounter > 50 && spriteCounter <= 100){
            spriteNum = 1;
            spriteCounter = 0;
            mining = false;
        }
    }
    public void mineObject(int objectIndex){

        // TODO

        System.out.println("Hit Detected on" + gamePanel.obj[objectIndex].name);
    }
}