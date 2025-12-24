package com.neal.spaceminer.object;

import com.neal.spaceminer.main.GamePanel;
import com.neal.spaceminer.entity.Entity;

import java.awt.*;

public class OBJ_Astronaut extends Entity {

    public static final String objName = "Astronaut";

    public OBJ_Astronaut(GamePanel gamePanel) {
        super(gamePanel);

        name = objName;

        down1 = setup("/objects/dead_astronaut", gamePanel.tileSize, gamePanel.tileSize);
    }
}