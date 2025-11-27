package com.neal.spaceminer.object;

import com.neal.spaceminer.main.GamePanel;
import com.neal.spaceminer.entity.Entity;

public class OBJ_Astronaut extends Entity {
    public OBJ_Astronaut(GamePanel gamePanel) {
        super(gamePanel);

        name = "Astronaut";
        collision = false;
        shrink = false;
        canPickup = false;

        solidArea.x = 8;
        solidArea.y = 8;
        solidAreaDefaultX = 8;
        solidAreaDefaultY = 8;
        down1 = setup("/objects/dead_astronaut");
    }
}