package com.neal.spaceminer.object;
import com.neal.spaceminer.main.GamePanel;
import com.neal.spaceminer.entity.Entity;

public class OBJ_ShipBack extends Entity {

    public static final String objName = "Ship Back";

    public OBJ_ShipBack(GamePanel gamePanel) {
        super(gamePanel);

        name = objName;
        collision = true;
        shrink = false;
        canPickup = false;

        solidArea.x = 16;
        solidArea.y = 96;
        solidArea.width = 192;
        solidArea.height = 128;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        down1 = setup("/objects/ship_back", gamePanel.tileSize*4, gamePanel.tileSize*4);
        description = "[" + name + "]";
    }
}