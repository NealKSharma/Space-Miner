package com.neal.spaceminer.object;
import com.neal.spaceminer.main.GamePanel;
import com.neal.spaceminer.entity.Entity;

public class OBJ_Ship_Middle extends Entity {

    public static final String objName = "Ship Middle";

    public OBJ_Ship_Middle(GamePanel gamePanel) {
        super(gamePanel);

        name = objName;
        collision = true;
        shrink = false;
        canPickup = false;

        solidArea.x = 32;
        solidArea.y = 64;
        solidArea.width = 192;
        solidArea.height = 72;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        down1 = setup("/objects/ship_middle", gamePanel.tileSize*4, gamePanel.tileSize*3);
        description = "[" + name + "]";
    }
}