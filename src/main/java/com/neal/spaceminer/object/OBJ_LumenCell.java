package com.neal.spaceminer.object;

import com.neal.spaceminer.entity.Entity;
import com.neal.spaceminer.main.GamePanel;

public class OBJ_LumenCell extends Entity {

    public static final String objName = "Lumen Cell";

    public OBJ_LumenCell(GamePanel gamePanel) {
        super(gamePanel);

        name = objName;
        collision = false;
        shrink = true;
        canPickup = true;

        solidArea.x = 16;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        down1 = setup("/objects/Lumen_Cell", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]\nSimple, effective. \nA cell that produces light.";
    }
}
