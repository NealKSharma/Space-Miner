package com.neal.spaceminer.main;

import com.neal.spaceminer.object.*;

public class AssetSetter {

    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObject() {
        int i = 0;
        gamePanel.obj[i] = new OBJ_Chest(gamePanel);
        gamePanel.obj[i].worldX = 76 * gamePanel.tileSize;
        gamePanel.obj[i].worldY = 79 * gamePanel.tileSize;
        OBJ_Chest chest1 = (OBJ_Chest) gamePanel.obj[i];
        chest1.chestInv.set(0, new OBJ_Pickaxe(gamePanel));
        i++;

        gamePanel.obj[i] = new OBJ_Chest(gamePanel);
        gamePanel.obj[i].worldX = 76 * gamePanel.tileSize;
        gamePanel.obj[i].worldY = 81 * gamePanel.tileSize;
        OBJ_Chest chest2 = (OBJ_Chest) gamePanel.obj[i];
        chest2.chestInv.set(1, new OBJ_Pickaxe(gamePanel));
        i++;

        gamePanel.obj[i] = new OBJ_Pickaxe(gamePanel);
        gamePanel.obj[i].worldX = 72 * gamePanel.tileSize;
        gamePanel.obj[i].worldY = 80 * gamePanel.tileSize;
        i++;

        gamePanel.obj[i] = new OBJ_LumenCell(gamePanel);
        gamePanel.obj[i].worldX = 73 * gamePanel.tileSize;
        gamePanel.obj[i].worldY = 80 * gamePanel.tileSize;
        i++;

        gamePanel.obj[i] = new OBJ_Ship_Front(gamePanel);
        gamePanel.obj[i].worldX = 12 * gamePanel.tileSize;
        gamePanel.obj[i].worldY = 90 * gamePanel.tileSize;
        i++;

        gamePanel.obj[i] = new OBJ_Ship_Middle(gamePanel);
        gamePanel.obj[i].worldX = 16 * gamePanel.tileSize;
        gamePanel.obj[i].worldY = 86 * gamePanel.tileSize;
        i++;

        gamePanel.obj[i] = new OBJ_Ship_Back(gamePanel);
        gamePanel.obj[i].worldX = 21 * gamePanel.tileSize;
        gamePanel.obj[i].worldY = 82 * gamePanel.tileSize;
        i++;

        gamePanel.obj[i] = new OBJ_Astronaut(gamePanel);
        gamePanel.obj[i].worldX = 18 * gamePanel.tileSize;
        gamePanel.obj[i].worldY = 90 * gamePanel.tileSize;
        i++;

        gamePanel.obj[i] = new OBJ_Astronaut(gamePanel);
        gamePanel.obj[i].worldX = 18 * gamePanel.tileSize;
        gamePanel.obj[i].worldY = 84 * gamePanel.tileSize;
        i++;

        gamePanel.obj[i] = new OBJ_Astronaut(gamePanel);
        gamePanel.obj[i].worldX = 11 * gamePanel.tileSize;
        gamePanel.obj[i].worldY = 90 * gamePanel.tileSize;
        i++;
    }
}
