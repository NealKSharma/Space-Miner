package com.neal.spaceminer.object;

import com.neal.spaceminer.entity.Entity;
import com.neal.spaceminer.main.GamePanel;

public class OBJ_VirusResidue extends Entity {

    public static final String objName = "Virus Residue";

    public OBJ_VirusResidue(GamePanel gamePanel) {
        super(gamePanel);

        name = objName;
        shrink = true;
        canPickup = true;
        isStackable = true;

        solidArea.x = 16;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        down1 = setup("/objects/virus_residue", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]\nUsed for crafting and meds.";
    }
}