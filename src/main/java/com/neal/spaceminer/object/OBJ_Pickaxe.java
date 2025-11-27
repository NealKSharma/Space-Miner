package com.neal.spaceminer.object;
import com.neal.spaceminer.main.GamePanel;
import com.neal.spaceminer.entity.Entity;

public class OBJ_Pickaxe extends Entity {

    public static final String objName = "Iron Pickaxe";

    public OBJ_Pickaxe(GamePanel gamePanel) {
        super(gamePanel);

        name = objName;
        collision = false;
        shrink = true;
        canPickup = true;

        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = 8;
        solidAreaDefaultY = 16;
        down1 = setup("/objects/pickaxe", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]\nA simple pickaxe.\nCan mine normal rocks.";
    }
}