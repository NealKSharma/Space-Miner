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

        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = 8;
        solidAreaDefaultY = 16;
        down1 = setup("/objects/Lumen_Cell");
        description = "[" + name + "]\nSimple, effective. \nA cell that produces light.";
    }
}
