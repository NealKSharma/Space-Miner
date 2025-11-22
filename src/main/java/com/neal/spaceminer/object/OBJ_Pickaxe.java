package com.neal.spaceminer.object;
import com.neal.spaceminer.main.GamePanel;
import com.neal.spaceminer.entity.Entity;

public class OBJ_Pickaxe extends Entity {
    public OBJ_Pickaxe(GamePanel gamePanel) {
        super(gamePanel);

        name = "Pickaxe";
        collision = true;

        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = 8;
        solidAreaDefaultY = 16;
        down1 = setup("/objects/pickaxe");
        description = "[" + name + "]\nA simple pickaxe.\nCan mine normal rocks.";
    }
}