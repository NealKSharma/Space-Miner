package com.neal.spaceminer.object;

import com.neal.spaceminer.main.GamePanel;
import com.neal.spaceminer.entity.Entity;

import java.awt.*;

public class OBJ_Teleporter extends Entity {

    public static final String objName = "Teleporter";

    public OBJ_Teleporter(GamePanel gamePanel) {
        super(gamePanel);

        name = objName;
        placedOnGround = true;

        solidArea.x = 4;
        solidArea.y = 4;
        solidArea.width = 56;
        solidArea.height = 56;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        down1 = setup("/objects/teleporter", gamePanel.tileSize, gamePanel.tileSize);
    }
}