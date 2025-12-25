package com.neal.spaceminer.main;

import com.neal.spaceminer.entity.Entity;
import com.neal.spaceminer.entity.NPC_Robot;
import com.neal.spaceminer.object.*;
import com.neal.spaceminer.tiles_interactive.IT_Rock;

public class AssetSetter {

    GamePanel gamePanel;

    int i;
    int numIT = 1; // NUMBER OF DIFFERENT INTERACTIVE TILES
    final int maxIT = 15; // NUMBER OF INTERACTIVE TILE THAT CAN BE PLACED
    final int maxTotalIT = numIT * maxIT; // TOTAL MAX INTERACTIVE TILES ON MAP
    int currIT = 0;
    int[] ITCount = new int[numIT];

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        for (i = 0; i < numIT; i++) {
            ITCount[i] = 0;
        }
    }

    public void setObject() {
        // MAP 1
        // TO SET STACKABLE ITEMS IN CHEST
        OBJ_LumenCell cells = new OBJ_LumenCell(gamePanel);
        cells.itemAmount = 5;

        // CHESTS
        OBJ_Chest chest1 = (OBJ_Chest) place(new OBJ_Chest(gamePanel), 0, 76, 79, true);
        chest1.chestInv.set(0, new OBJ_Pickaxe(gamePanel));
        chest1.chestInv.set(7, cells);

        cells.itemAmount = 10;
        OBJ_Chest chest2 = (OBJ_Chest) place(new OBJ_Chest(gamePanel), 0, 77, 79, true);
        chest2.chestInv.set(1, new OBJ_Pickaxe(gamePanel));
        chest2.chestInv.set(3, cells);

        // ITEMS
        place(new OBJ_Pickaxe(gamePanel), 0, 72, 80, true);
        place(new OBJ_LumenCell(gamePanel), 0, 73, 80, true);
        place(new OBJ_SuitGenerator(gamePanel), 0, 72, 76, true);

        // SHIP
        place(new OBJ_ShipFront(gamePanel), 0, 12, 90, true);
        place(new OBJ_ShipMiddle(gamePanel), 0, 16, 86, true);
        place(new OBJ_ShipBack(gamePanel), 0, 21, 82, true);

        // MISC
        place(new OBJ_Astronaut(gamePanel), 0, 18, 90, true);
        place(new OBJ_Astronaut(gamePanel), 0, 18, 84, true);
        place(new OBJ_Astronaut(gamePanel), 0, 11, 90, true);

        // MAP 2
        OBJ_Chest chest3 = (OBJ_Chest) place(new OBJ_Chest(gamePanel), 1, 70, 77, true);
        chest3.chestInv.set(0, new OBJ_Pickaxe(gamePanel));
        chest3.chestInv.set(7, cells);

        place(new OBJ_Astronaut(gamePanel), 1, 70, 75, true);
        place(new OBJ_Pickaxe(gamePanel), 1, 72, 75, true);
    }
    public void setNPC(){
        // NPC THAT FOLLOWS PLAYER - MAP 1
        gamePanel.bot = new NPC_Robot(gamePanel);
        gamePanel.bot.worldX = gamePanel.player.worldX + 32; // Start next to player
        gamePanel.bot.worldY = gamePanel.player.worldY + 32;
    }
    public void setInteractiveTile(){
        while (currIT < maxTotalIT) {
            int randCol = (int)(Math.random() * (99 - 1 + 1) + 1); // 99 IS FOR 100 MAP SIZE
            int randRow = (int)(Math.random() * (99 - 1 + 1) + 1);

            if(canPlaceIT(randCol, randRow)) {
                int randIT = (int)(Math.random() * numIT);

                // CHECK IF THIS SPECIFIC TILE HAS REACHED ITS MAX CAP
                if(randIT == 0 && ITCount[randIT] < maxIT) {
                    place(new IT_Rock(gamePanel), 0, randCol, randRow, true);
                    ITCount[randIT]++;
                    currIT++;
                }
            }
        }
    }
    public boolean canPlaceIT(int col, int row){
        // CHECK IF AN INTERACTIVE TILE CAN BE PLACE ON THE TILE
        int tile = gamePanel.tileManager.mapTileNum[gamePanel.currentMap][col][row];
        // ONLY SURFACE'S COLLISION IS FALSE
        if (gamePanel.tileManager.tile[tile].collision){
            return false; // RETURN FALSE IF COLLISION IS TRUE FOR THE TILE
        }

        int worldX = col * gamePanel.tileSize;
        int worldY = row * gamePanel.tileSize;

        // CHECK IF THE PLAYER IS SPAWNING ON THE TILE
        if(gamePanel.player.worldX == worldX && gamePanel.player.worldY == worldY){
            return false;
        }

        // CHECK IF THERE'S AN OBJECT ALREADY THERE ON THE TILE
        int height = 32;
        int width = 40;
        for(int i = 0; i < gamePanel.obj.get(gamePanel.currentMap).size(); i++) {
            Entity obj = gamePanel.obj.get(gamePanel.currentMap).get(i);

            obj.solidArea.x = obj.worldX + obj.solidArea.x;
            obj.solidArea.y = obj.worldY + obj.solidArea.y;

            if(obj.solidArea.intersects(worldX, worldY, height, width)) {
                obj.solidArea.x = obj.solidAreaDefaultX;
                obj.solidArea.y = obj.solidAreaDefaultY;
                return false;
            }
            obj.solidArea.x = obj.solidAreaDefaultX;
            obj.solidArea.y = obj.solidAreaDefaultY;
        }
        return true;
    }
    public Entity place(Entity entity,int map, int col, int row, boolean isObject) {
        entity.worldX = col * gamePanel.tileSize;
        entity.worldY = row * gamePanel.tileSize;
        if(isObject){
            gamePanel.obj.get(map).add(entity);
        } else {
            gamePanel.npc.get(map).add(entity);
        }
        return entity;
    }
}