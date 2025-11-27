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

        initialize();
        getImage();

        for(int i = 0; i < maxInventorySize; i++) {
            inventory.add(null);
        }
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
    public void interactWithObject(int index) {
        if(gamePanel.obj[index] != null){
            int objIndex = gamePanel.collisionChecker.checkObject(this, true);
            if (gamePanel.obj[index].canPickup && getFirstEmptySlot() != -1) {
                inventory.set(getFirstEmptySlot(), gamePanel.obj[index]);
                gamePanel.obj[index] = null;
                itemBehaviour();
            } else if ("Chest".equals(gamePanel.obj[objIndex].name)) {
                canOpen = true;
                currentChest = gamePanel.obj[objIndex];
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
        // LIGHT
        if(searchHotbar("Lumen_Cell")){
            hasLight = true;
            gamePanel.environmentManager.update();
        } else {
            hasLight = false;
            gamePanel.environmentManager.update();
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
    public void update() {
        if (keyHandler.up || keyHandler.down || keyHandler.left || keyHandler.right) {
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
        if (objIndex != -1) interactWithObject(objIndex);
    }
}