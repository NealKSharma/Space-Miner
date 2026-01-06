package com.neal.spaceminer.entity;

import com.neal.spaceminer.main.GamePanel;
import com.neal.spaceminer.main.KeyHandler;
import com.neal.spaceminer.object.OBJ_Chest;
import com.neal.spaceminer.object.OBJ_Pickaxe;

import java.awt.*;
import java.util.ArrayList;

public class Player extends Entity {
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;
    public int maxStamina = gamePanel.FPS;;
    public int stamina = maxStamina;
    public int staminaRechargeCounter = 0;
    public int suiteIntegrity = 100;

    public Entity currentObj = null;
    public boolean hasLight = false;
    public int objType;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        super(gamePanel);
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
    public void setDefaultValues() {
        initialize();
        direction = "down";

        currentObj = null;
        hasLight = false;
        mineCount = 0;

        inventory.clear();
        for(int i = 0; i < maxInventorySize; i++) {
            inventory.add(null);
        }
        setItems();
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
        mineUp1 = setup("/astronaut/pickaxe_back1", gamePanel.tileSize, gamePanel.tileSize*2);
        mineUp2 = setup("/astronaut/pickaxe_back2", gamePanel.tileSize, gamePanel.tileSize*2);
        mineDown1 = setup("/astronaut/pickaxe_front1", gamePanel.tileSize, gamePanel.tileSize*2);
        mineDown2 = setup("/astronaut/pickaxe_front2", gamePanel.tileSize, gamePanel.tileSize*2);
        mineLeft1 = setup("/astronaut/pickaxe_left1", gamePanel.tileSize*2, gamePanel.tileSize);
        mineLeft2 = setup("/astronaut/pickaxe_left2", gamePanel.tileSize*2, gamePanel.tileSize);
        mineRight1 = setup("/astronaut/pickaxe_right1", gamePanel.tileSize*2, gamePanel.tileSize);
        mineRight2 = setup("/astronaut/pickaxe_right2", gamePanel.tileSize*2, gamePanel.tileSize);
    }
    public void interactWithObject(int index) {
        currentObj = gamePanel.obj.get(gamePanel.currentMap).get(index);
            if (currentObj.canPickup) {
                if(currentObj.isStackable && searchInventory(currentObj.name) != -1) {
                    inventory.get(searchInventory(currentObj.name)).itemAmount += currentObj.itemAmount;
                    gamePanel.obj.get(gamePanel.currentMap).remove(index);
                } else {
                    int emptySlot = getFirstEmptySlot();
                    if (emptySlot != -1) {
                        inventory.set(emptySlot, currentObj);
                        gamePanel.obj.get(gamePanel.currentMap).remove(index);
                        itemBehaviour();
                    } else {
                        // INVENTORY FULL
                    }
                    inventory.set(emptySlot, currentObj);
                    itemBehaviour();
                }
            } else {
                switch (currentObj.name) {
                    case "Chest": objType = 1; break;
                    case "Crafting Station": objType = 2; break;
                }
            }
    }
    public void removeItems(String name, int amount){
        int itemSlot = searchInventory(name);
        if(itemSlot == -1) return;
        if(inventory.get(itemSlot).itemAmount > amount){
            inventory.get(itemSlot).itemAmount -= amount;
        } else {
            inventory.set(itemSlot, null);
        }
        itemBehaviour();
    }
    public void interactWithEntity(int index) { }
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
        if(currentObj == null) return;
        OBJ_Chest chest = (OBJ_Chest) currentObj;

        if(slotCol < 6) {
            // Taking from chest
            int chestIndex = slotCol + (slotRow * 6);
            if(chestIndex < chest.chestInv.size()) {
                Entity item = chest.chestInv.get(chestIndex);
                if(item != null) {
                    // STACKABLE ITEM ALREADY IN INVENTORY
                    if(item.isStackable && searchInventory(item.name) != -1) {
                        inventory.get(searchInventory(item.name)).itemAmount += item.itemAmount;
                        chest.chestInv.set(chestIndex, null);
                    } else {
                        // NOT STACKABLE OR NOT IN INVENTORY
                        int emptySlot = getFirstEmptySlot();
                        if(emptySlot != -1) {
                            inventory.set(emptySlot, item);
                            chest.chestInv.set(chestIndex, null);
                        }
                    }
                }
            }
        } else {
            // Putting into chest
            int invIndex = (slotCol - 8) + (slotRow * 5);
            if(invIndex < inventory.size()) {
                Entity item = inventory.get(invIndex);
                if(item != null) {
                    // IF STACKABLE, SEARCH FOR EXISTING STACK IN CHEST FIRST
                    if (item.isStackable) {
                        for (int i = 0; i < chest.chestInv.size(); i++) {
                            Entity chestItem = chest.chestInv.get(i);
                            if (chestItem != null && chestItem.name.equals(item.name)) {
                                chestItem.itemAmount += item.itemAmount;
                                inventory.set(invIndex, null);
                                itemBehaviour();
                                return;
                            }
                        }
                    }

                    // IF NOT MOVED YET, FIND EMPTY SLOT
                    for (int i = 0; i < chest.chestInv.size(); i++) {
                        if (chest.chestInv.get(i) == null) {
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
    public int searchInventory(String name){
        for(int i = 0; i < maxInventorySize; i++) {
            if(inventory.get(i) != null && inventory.get(i).name.equals(name)) {
                return i;
            }
        }
        return -1;
    }
    public void useHotbarItem(int index){
        // MINING
        if(inventory.get(index) != null && inventory.get(index).name.equals("Pickaxe")) {
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

            objType = 0;
            collisionOn = false;
            gamePanel.collisionChecker.checkTile(this);

            // CHECK COLLISION WITH OBJECT
            int objIndex = gamePanel.collisionChecker.checkObject(this, true);
            if (objIndex != -1) interactWithObject(objIndex);
            // CHECK COLLISION WITH ENTITIES
            int entityIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.npc);
            if(entityIndex != -1) interactWithEntity(entityIndex);
            // CHECK COLLISION WITH BOT
            boolean collision = gamePanel.collisionChecker.checkBot(this, gamePanel.bot);
            if(collision) {
                gamePanel.gameState = gamePanel.dialogueState;
                gamePanel.bot.speak();
            }

            // CHECK EVENTS
            gamePanel.eventHandler.checkEvent();

            if(keyHandler.sprint){
                if(stamina > 0){
                    stamina--;
                    speed = speed*2;
                }
            }

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (!collisionOn) {
                switch (direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            speed = 1;

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
        if (stamina < maxStamina) {
            staminaRechargeCounter++;
            if(staminaRechargeCounter > 12){
                stamina++;
                staminaRechargeCounter = 0;
            }
        }
    }
    public void mining() {
        spriteCounter++;
        if (spriteCounter <= 10) {
            spriteNum = 1;
        }
        if (spriteCounter > 10 && spriteCounter <= 50) {
            spriteNum = 2;
            Entity swingCheck = new Entity(gamePanel);

            swingCheck.worldX = this.worldX;
            swingCheck.worldY = this.worldY;

            swingCheck.solidArea = new Rectangle();
            swingCheck.solidArea.x = 0;
            swingCheck.solidArea.y = 0;
            swingCheck.solidArea.width = swingArea.width;
            swingCheck.solidArea.height = swingArea.height;

            swingCheck.solidAreaDefaultX = swingCheck.solidArea.x;
            swingCheck.solidAreaDefaultY = swingCheck.solidArea.y;

            switch (direction) {
                case "up": swingCheck.worldY -= swingArea.height; break;
                case "down": swingCheck.worldY += swingArea.height; break;
                case "left": swingCheck.worldX -= swingArea.width; break;
                case "right": swingCheck.worldX += swingArea.width; break;
            }

            int objectIndex = gamePanel.collisionChecker.checkObject(swingCheck, true);
            if (objectIndex != -1 && gamePanel.obj.get(gamePanel.currentMap).get(objectIndex).isBreakable) {
                mineObject(objectIndex);
            }
        }
        if (spriteCounter > 50 && spriteCounter <= 100) {
            spriteNum = 1;
            spriteCounter = 0;
            mining = false;
        }
    }
    public void mineObject(int i) {
        Entity ore = gamePanel.obj.get(gamePanel.currentMap).get(i);
        if (ore.mineCount >= ore.strength * 39) {
            generateParticle(ore, ore);

            int randAmount = (int)(Math.random() * 3) + 1;
            for (int k = 0; k < randAmount; k++) {
                gamePanel.obj.get(gamePanel.currentMap).add(ore.getDrop());
            }

            gamePanel.assetSetter.replaceTile(ore);
            gamePanel.obj.get(gamePanel.currentMap).remove(i);
            ore.mineCount = 0;
        } else {
            ore.mineCount++;
            if (ore.mineCount % 40 == 0) {
                generateParticle(ore, ore);
            }
        }
    }
}