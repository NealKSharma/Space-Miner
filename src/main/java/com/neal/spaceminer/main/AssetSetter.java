package com.neal.spaceminer.main;

import com.neal.spaceminer.object.OBJ_Key;

public class AssetSetter {

    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObject() {
        gamePanel.obj[0] = new OBJ_Key();
        gamePanel.obj[0].worldX = 23 * gamePanel.tileSize;
        gamePanel.obj[0].worldY = 7 * gamePanel.tileSize;

        gamePanel.obj[1] = new OBJ_Key();
        gamePanel.obj[1].worldX = 23 * gamePanel.tileSize;
        gamePanel.obj[1].worldY = 40 * gamePanel.tileSize;
    }
}
