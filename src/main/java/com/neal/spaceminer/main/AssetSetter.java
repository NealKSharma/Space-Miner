package com.neal.spaceminer.main;

import com.neal.spaceminer.entity.Entity;
import com.neal.spaceminer.object.*;
import com.neal.spaceminer.tiles_interactive.IT_Rock;

public class AssetSetter {

    GamePanel gamePanel;

    int i;
    int numIT = 1; // NUMBER OF DIFFERENT INTERACTIVE TILES
    final int maxIT = 5; // NUMBER OF INTERACTIVE TILE THAT CAN BE PLACED
    final int maxTotalIT = numIT * maxIT; // MAX INTERACTIVE TILES ON MAP
    int currIT = 0;
    int[] ITCount = new int[numIT];

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        for (i = 0; i < numIT; i++) {
            ITCount[i] = 0;
        }
    }

    public void setObject() {
        // TO SET STACKABLE ITEMS IN CHEST
        OBJ_LumenCell cells = new OBJ_LumenCell(gamePanel);
        cells.itemAmount = 5;

        // CHESTS
        OBJ_Chest chest1 = (OBJ_Chest) place(new OBJ_Chest(gamePanel), 76, 79);
        chest1.chestInv.set(0, new OBJ_Pickaxe(gamePanel));
        chest1.chestInv.set(7, cells);

        cells.itemAmount = 10;
        OBJ_Chest chest2 = (OBJ_Chest) place(new OBJ_Chest(gamePanel), 76, 81);
        chest2.chestInv.set(1, new OBJ_Pickaxe(gamePanel));
        chest2.chestInv.set(3, cells);

        // ITEMS
        place(new OBJ_Pickaxe(gamePanel), 72, 80);
        place(new OBJ_LumenCell(gamePanel), 73, 80);
        place(new OBJ_SuitGenerator(gamePanel), 72, 76);

        // SHIP
        place(new OBJ_ShipFront(gamePanel), 12, 90);
        place(new OBJ_ShipMiddle(gamePanel), 16, 86);
        place(new OBJ_ShipBack(gamePanel), 21, 82);

        // MISC
        place(new OBJ_Astronaut(gamePanel), 18, 90);
        place(new OBJ_Astronaut(gamePanel), 18, 84);
        place(new OBJ_Astronaut(gamePanel), 11, 90);
    }
    public void setInteractiveTile(){
        while (currIT < maxTotalIT) {
            int randCol = (int)(Math.random() * (99 - 1 + 1) + 1); // 99 IS FOR 100 MAP SIZE
            int randRow = (int)(Math.random() * (99 - 1 + 1) + 1);

            if(canPlaceIT(randCol, randRow)) {
                int randIT = (int)(Math.random() * numIT);

                // CHECK IF THIS SPECIFIC TILE HAS REACHED ITS MAX CAP
                if(randIT == 0 && ITCount[randIT] < maxIT) {
                    place(new IT_Rock(gamePanel), randCol, randRow);
                    ITCount[randIT]++;
                    currIT++;
                }
            }
        }
    }
    public boolean canPlaceIT(int col, int row){
        // CHECK IF AN INTERACTIVE TILE CAN BE PLACE ON THE TILE
        int tile = gamePanel.tileManager.mapTileNum[col][row];
        // ONLY SURFACE'S COLLISION IS FALSE
        if (gamePanel.tileManager.tile[tile].collision){
            return false; // RETURN FALSE IF COLLISION IS TRUE FOR THE TILE
        }

        // CHECK IF THERE'S AN OBJECT ALREADY THERE ON THE TILE
        int worldX = col * gamePanel.tileSize;
        int worldY = row * gamePanel.tileSize;
        for(int i = 0; i < gamePanel.obj.size(); i++) {
            if(gamePanel.obj.get(i).worldX == worldX && gamePanel.obj.get(i).worldY == worldY) {
                return false;
            }
        }
        return true;
    }
    public Entity place(Entity entity, int col, int row) {
            entity.worldX = col * gamePanel.tileSize;
            entity.worldY = row * gamePanel.tileSize;
            gamePanel.obj.add(entity);
            return entity;
    }
}
