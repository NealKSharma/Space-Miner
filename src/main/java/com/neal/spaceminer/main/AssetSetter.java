package com.neal.spaceminer.main;

import com.neal.spaceminer.object.OBJ_Astronaut;
import com.neal.spaceminer.object.OBJ_Chest;
import com.neal.spaceminer.object.OBJ_LumenCell;
import com.neal.spaceminer.object.OBJ_Pickaxe;

public class AssetSetter {

    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObject() {
        int i = 0;
        gamePanel.obj[i] = new OBJ_Chest(gamePanel);
        gamePanel.obj[i].worldX = 24 * gamePanel.tileSize;
        gamePanel.obj[i].worldY = 22 * gamePanel.tileSize;
        OBJ_Chest chest1 = (OBJ_Chest) gamePanel.obj[i];
        chest1.chestInv.set(0, new OBJ_Pickaxe(gamePanel));
        i++;

        gamePanel.obj[i] = new OBJ_Chest(gamePanel);
        gamePanel.obj[i].worldX = 22 * gamePanel.tileSize;
        gamePanel.obj[i].worldY = 22 * gamePanel.tileSize;
        OBJ_Chest chest2 = (OBJ_Chest) gamePanel.obj[i];
        chest2.chestInv.set(1, new OBJ_Pickaxe(gamePanel));
        i++;

        gamePanel.obj[i] = new OBJ_Astronaut(gamePanel);
        gamePanel.obj[i].worldX = 24 * gamePanel.tileSize;
        gamePanel.obj[i].worldY = 20 * gamePanel.tileSize;
        i++;

        gamePanel.obj[i] = new OBJ_Pickaxe(gamePanel);
        gamePanel.obj[i].worldX = 21 * gamePanel.tileSize;
        gamePanel.obj[i].worldY = 23 * gamePanel.tileSize;
        i++;

        gamePanel.obj[i] = new OBJ_LumenCell(gamePanel);
        gamePanel.obj[i].worldX = 20 * gamePanel.tileSize;
        gamePanel.obj[i].worldY = 23 * gamePanel.tileSize;
        i++;
    }
}
