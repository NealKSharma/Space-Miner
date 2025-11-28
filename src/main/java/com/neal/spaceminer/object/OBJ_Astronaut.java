package com.neal.spaceminer.object;

import com.neal.spaceminer.main.GamePanel;
import com.neal.spaceminer.entity.Entity;

public class OBJ_Astronaut extends Entity {

    public static final String objName = "Astronaut";

    public OBJ_Astronaut(GamePanel gamePanel) {
        super(gamePanel);

        name = objName;

        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 56;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        down1 = setup("/objects/dead_astronaut", gamePanel.tileSize, gamePanel.tileSize);
    }
}