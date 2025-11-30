package com.neal.spaceminer.main;

import com.neal.spaceminer.entity.Entity;
import com.neal.spaceminer.object.*;
import com.neal.spaceminer.tiles_interactive.IT_Rock;

public class AssetSetter {

    GamePanel gamePanel;
    int i;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObject() {
        i = 0;

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

        // INTERACTIVE TILES
        place(new IT_Rock(gamePanel), 78, 79);
        place(new IT_Rock(gamePanel), 78, 80);
        place(new IT_Rock(gamePanel), 78, 81);
    }
    public Entity place(Entity entity, int col, int row) {
        //if(i < gamePanel.obj.length) {
            entity.worldX = col * gamePanel.tileSize;
            entity.worldY = row * gamePanel.tileSize;
            gamePanel.obj[i] = entity;
            i++;
            return entity;
        //} else {
            // OBJECT ARRAY FULL
            //return null;
        //}
    }
}
