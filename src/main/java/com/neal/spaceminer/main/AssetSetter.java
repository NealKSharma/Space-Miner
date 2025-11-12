package com.neal.spaceminer.main;

import com.neal.spaceminer.object.OBJ_Astronaut;
import com.neal.spaceminer.object.OBJ_Chest;

public class AssetSetter {

    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObject() {
        gamePanel.obj[0] = new OBJ_Chest();
        gamePanel.obj[0].worldX = 23 * gamePanel.tileSize;
        gamePanel.obj[0].worldY = 7 * gamePanel.tileSize;

        gamePanel.obj[1] = new OBJ_Chest();
        gamePanel.obj[1].worldX = 23 * gamePanel.tileSize;
        gamePanel.obj[1].worldY = 40 * gamePanel.tileSize;

        gamePanel.obj[2] = new OBJ_Astronaut();
        gamePanel.obj[2].worldX = 24 * gamePanel.tileSize;
        gamePanel.obj[2].worldY = 8 * gamePanel.tileSize;

        gamePanel.obj[3] = new OBJ_Astronaut();
        gamePanel.obj[3].worldX = 22 * gamePanel.tileSize;
        gamePanel.obj[3].worldY = 8 * gamePanel.tileSize;
    }
}
