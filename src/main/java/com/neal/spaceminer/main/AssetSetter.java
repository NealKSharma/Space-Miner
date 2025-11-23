package com.neal.spaceminer.main;

import com.neal.spaceminer.object.OBJ_Astronaut;
import com.neal.spaceminer.object.OBJ_Chest;
import com.neal.spaceminer.object.OBJ_Pickaxe;

public class AssetSetter {

    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObject() {
        gamePanel.obj[0] = new OBJ_Chest(gamePanel);
        gamePanel.obj[0].worldX = 24 * gamePanel.tileSize;
        gamePanel.obj[0].worldY = 22 * gamePanel.tileSize;

        gamePanel.obj[1] = new OBJ_Chest(gamePanel);
        gamePanel.obj[1].worldX = 24 * gamePanel.tileSize;
        gamePanel.obj[1].worldY = 26 * gamePanel.tileSize;

        gamePanel.obj[2] = new OBJ_Astronaut(gamePanel);
        gamePanel.obj[2].worldX = 26 * gamePanel.tileSize;
        gamePanel.obj[2].worldY = 24 * gamePanel.tileSize;

        gamePanel.obj[3] = new OBJ_Astronaut(gamePanel);
        gamePanel.obj[3].worldX = 22 * gamePanel.tileSize;
        gamePanel.obj[3].worldY = 24 * gamePanel.tileSize;

        gamePanel.obj[4] = new OBJ_Pickaxe(gamePanel);
        gamePanel.obj[4].worldX = 21 * gamePanel.tileSize;
        gamePanel.obj[4].worldY = 23 * gamePanel.tileSize;
    }
}
