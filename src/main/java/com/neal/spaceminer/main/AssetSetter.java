package com.neal.spaceminer.main;

import com.neal.spaceminer.entity.Entity;
import com.neal.spaceminer.entity.NPC_Robot;
import com.neal.spaceminer.hostiles.HOS_Virus;
import com.neal.spaceminer.object.*;
import com.neal.spaceminer.tiles_interactive.*;

import java.util.Arrays;

public class AssetSetter {

    GamePanel gamePanel;

    int i;
    int numIT = 5; // NUMBER OF DIFFERENT INTERACTIVE TILES
    final int maxIT = 10; // NUMBER OF INTERACTIVE TILE THAT CAN BE PLACED
    final int maxTotalIT = numIT * maxIT; // TOTAL MAX INTERACTIVE TILES ON MAP
    int currIT = 0;
    int[] ITCount = new int[numIT];
    final int object = 1;
    final int npc = 2;
    final int hostile = 3;

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
        OBJ_Chest chest1 = (OBJ_Chest) place(new OBJ_Chest(gamePanel), 0, 76, 79, object);
        chest1.chestInv.set(0, new OBJ_Pickaxe(gamePanel));
        chest1.chestInv.set(7, cells);

        cells.itemAmount = 10;
        OBJ_Chest chest2 = (OBJ_Chest) place(new OBJ_Chest(gamePanel), 0, 77, 79, object);
        chest2.chestInv.set(1, new OBJ_Pickaxe(gamePanel));
        chest2.chestInv.set(3, cells);

        // ITEMS
        place(new OBJ_Pickaxe(gamePanel), 0, 72, 80, object);
        place(new OBJ_LumenCell(gamePanel), 0, 73, 80, object);
        place(new OBJ_SuitGenerator(gamePanel), 0, 72, 76, object);

        // SHIP
        place(new OBJ_ShipFront(gamePanel), 0, 12, 90, object);
        place(new OBJ_ShipMiddle(gamePanel), 0, 16, 86, object);
        place(new OBJ_ShipBack(gamePanel), 0, 21, 82, object);

        // MISC
        place(new OBJ_Astronaut(gamePanel), 0, 18, 90, object);
        place(new OBJ_Astronaut(gamePanel), 0, 18, 84, object);
        place(new OBJ_Astronaut(gamePanel), 0, 11, 90, object);
        place(new OBJ_Habitat(gamePanel), 0, 72, 66, object);

        // MAP 2
        OBJ_Chest chest3 = (OBJ_Chest) place(new OBJ_Chest(gamePanel), 1, 47, 47, object);
        chest3.chestInv.set(0, new OBJ_Pickaxe(gamePanel));
        chest3.chestInv.set(7, cells);

        place(new OBJ_CraftingStation(gamePanel), 1, 50, 53, object);
    }
    public void setNPC(){
        // NPC THAT FOLLOWS PLAYER - MAP 1
        gamePanel.bot = new NPC_Robot(gamePanel);
        gamePanel.bot.worldX = gamePanel.player.worldX + 32; // Start next to player
        gamePanel.bot.worldY = gamePanel.player.worldY + 32;
    }
    public void setHostile(){
        place(new HOS_Virus(gamePanel), 0, 70, 80, hostile);
    }
    public void setInteractiveTile(){
        currIT = 0;
        Arrays.fill(ITCount, 0);
        Entity interactiveTile = null;

        while (currIT < maxTotalIT) {
            int randCol = (int)(Math.random() * (99 - 1 + 1) + 1); // 99 IS FOR 100 MAP SIZE
            int randRow = (int)(Math.random() * (99 - 1 + 1) + 1);

            if(canPlaceIT(randCol, randRow)) {
                int randIT = (int)(Math.random() * numIT);
                switch(randIT) {
                    case 0: interactiveTile = new IT_Rock(gamePanel); break;
                    case 1: interactiveTile = new IT_Chrono(gamePanel); break;
                    case 2: interactiveTile = new IT_Pulsarite(gamePanel); break;
                    case 3: interactiveTile = new IT_Scoria(gamePanel); break;
                    case 4: interactiveTile = new IT_Void(gamePanel); break;
                }

                // CHECK IF THIS SPECIFIC TILE HAS REACHED ITS MAX CAP
                if(ITCount[randIT] < maxIT && interactiveTile != null) {
                    place(interactiveTile, 0, randCol, randRow, object);
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
        if (gamePanel.tileManager.tile[tile].collision || tile == 17){
            return false; // RETURN FALSE IF COLLISION IS TRUE FOR THE TILE
        }

        int worldX = col * gamePanel.tileSize;
        int worldY = row * gamePanel.tileSize;

        // CHECK IF THE PLAYER IS SPAWNING ON THE TILE
        if(gamePanel.player.worldX == worldX && gamePanel.player.worldY == worldY){
            return false;
        }

        // CHECK IF THE ROBOT IS ON THE TILE
        if(gamePanel.bot.worldX == worldX && gamePanel.bot.worldY == worldY){
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

        // CHECK FOR NPCS
        for(int i = 0; i < gamePanel.npc.get(gamePanel.currentMap).size(); i++) {
            Entity npc = gamePanel.npc.get(gamePanel.currentMap).get(i);

            npc.solidArea.x = npc.worldX + npc.solidArea.x;
            npc.solidArea.y = npc.worldY + npc.solidArea.y;

            if(npc.solidArea.intersects(worldX, worldY, height, width)) {
                npc.solidArea.x = npc.solidAreaDefaultX;
                npc.solidArea.y = npc.solidAreaDefaultY;
                return false;
            }
            npc.solidArea.x = npc.solidAreaDefaultX;
            npc.solidArea.y = npc.solidAreaDefaultY;
        }

        // CHECK FOR HOSTILES
        for(int i = 0; i < gamePanel.hostile.get(gamePanel.currentMap).size(); i++) {
            Entity hostile = gamePanel.hostile.get(gamePanel.currentMap).get(i);

            hostile.solidArea.x = hostile.worldX + hostile.solidArea.x;
            hostile.solidArea.y = hostile.worldY + hostile.solidArea.y;

            if(hostile.solidArea.intersects(worldX, worldY, height, width)) {
                hostile.solidArea.x = hostile.solidAreaDefaultX;
                hostile.solidArea.y = hostile.solidAreaDefaultY;
                return false;
            }
            hostile.solidArea.x = hostile.solidAreaDefaultX;
            hostile.solidArea.y = hostile.solidAreaDefaultY;
        }

        return true;
    }
    public Entity place(Entity entity, int map, int col, int row, int type) {
        entity.worldX = col * gamePanel.tileSize;
        entity.worldY = row * gamePanel.tileSize;
        switch(type) {
            case object: gamePanel.obj.get(map).add(entity); break;
            case npc: gamePanel.npc.get(map).add(entity); break;
            case hostile: gamePanel.hostile.get(map).add(entity); break;
        }
        return entity;
    }
    public void replaceTile(Entity entity){
        boolean placed = false;
        while(!placed){
            int randCol = (int)(Math.random() * (99 - 1 + 1) + 1); // 99 IS FOR 100 MAP SIZE
            int randRow = (int)(Math.random() * (99 - 1 + 1) + 1);
            if(canPlaceIT(randCol, randRow) && !gamePanel.tileManager.isOnScreen(randCol*gamePanel.tileSize, randRow*gamePanel.tileSize)) {
                placed = true;
                place(entity, gamePanel.currentMap, randCol, randRow, object);
            }
        }
    }
}