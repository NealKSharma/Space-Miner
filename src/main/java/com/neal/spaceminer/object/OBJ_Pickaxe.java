package com.neal.spaceminer.object;
import com.neal.spaceminer.main.GamePanel;
import com.neal.spaceminer.entity.Entity;

public class OBJ_Pickaxe extends Entity {

    public static final String objName = "Pickaxe";

    public OBJ_Pickaxe(GamePanel gamePanel) {
        super(gamePanel);

        name = objName;
        shrink = true;
        canPickup = true;

        solidArea.x = 16;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        down1 = setup("/objects/pickaxe", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]\nCan mine normal rocks.";
    }
}