package com.neal.spaceminer.object;
import com.neal.spaceminer.main.GamePanel;
import com.neal.spaceminer.entity.Entity;

public class OBJ_ShipFront extends Entity {

    public static final String objName = "Ship Front";

    public OBJ_ShipFront(GamePanel gamePanel) {
        super(gamePanel);

        name = objName;
        collision = true;

        solidArea.x = 72;
        solidArea.y = 64;
        solidArea.width = 172;
        solidArea.height = 64;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        down1 = setup("/objects/ship_front", gamePanel.tileSize*4, (int) (gamePanel.tileSize*2.5));
        description = "[" + name + "]";
    }
}