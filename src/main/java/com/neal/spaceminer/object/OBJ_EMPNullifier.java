package com.neal.spaceminer.object;

import com.neal.spaceminer.entity.Entity;
import com.neal.spaceminer.main.GamePanel;

public class OBJ_EMPNullifier extends Entity {

    public static final String objName = "EMP Nullifier";

    public OBJ_EMPNullifier(GamePanel gamePanel) {
        super(gamePanel);

        name = objName;
        shrink = true;
        canPickup = true;
        isStackable = false;

        solidArea.x = 16;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        down1 = setup("/objects/emp_nullifier", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]\nAn advance EMP Shielder.\nProtects from EMP surges.";
    }
}
