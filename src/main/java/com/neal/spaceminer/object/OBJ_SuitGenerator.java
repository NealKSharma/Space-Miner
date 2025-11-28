package com.neal.spaceminer.object;
import com.neal.spaceminer.main.GamePanel;
import com.neal.spaceminer.entity.Entity;

public class OBJ_SuitGenerator extends Entity {

    public static final String objName = "Suit Generator";

    public OBJ_SuitGenerator(GamePanel gamePanel) {
        super(gamePanel);

        name = objName;
        collision = true;
        shrink = false;
        canPickup = false;

        solidArea.x = 24;
        solidArea.y = 64;
        solidArea.width = 80;
        solidArea.height = 155;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        down1 = setup("/objects/suit_generator", gamePanel.tileSize*2, gamePanel.tileSize*4);
        description = "[" + name + "]";
    }
}