package com.neal.spaceminer.object;
import com.neal.spaceminer.main.GamePanel;
import com.neal.spaceminer.entity.Entity;

public class OBJ_Chest extends Entity {
    public OBJ_Chest(GamePanel gamePanel) {
        super(gamePanel);

        name = "Chest";
        collision = true;

        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = 8;
        solidAreaDefaultY = 16;
        down1 = setup("/objects/Chest");
    }
}