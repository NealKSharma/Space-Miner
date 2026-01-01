package com.neal.spaceminer.object;

import com.neal.spaceminer.entity.Entity;
import com.neal.spaceminer.main.GamePanel;

public class OBJ_SignalJammer extends Entity {

    public static final String objName = "Chronal Signal Jammer";

    public OBJ_SignalJammer(GamePanel gamePanel) {
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

        down1 = setup("/objects/signal_jammer", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]\nAdvanced Singal Jammer.\nBlocks all surrounding\nsignals.";
    }
}
