package com.neal.spaceminer.object;
import com.neal.spaceminer.main.GamePanel;
import com.neal.spaceminer.entity.Entity;

public class OBJ_PlasmaRipper extends Entity {

    public static final String objName = "Plasma Ripper";

    public OBJ_PlasmaRipper(GamePanel gamePanel) {
        super(gamePanel);

        name = objName;
        shrink = true;
        canPickup = true;
        damage = 2;

        solidArea.x = 16;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        down1 = setup("/objects/plasma_ripper", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]\nShort ranged weapon.";
    }
}