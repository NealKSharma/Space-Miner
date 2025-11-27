package com.neal.spaceminer.object;
import com.neal.spaceminer.main.GamePanel;
import com.neal.spaceminer.entity.Entity;

import java.util.ArrayList;

public class OBJ_Chest extends Entity {

    public ArrayList<Entity> chestInv = new ArrayList<>();
    public final int maxChestInvSize = 42;

    public static final String objName = "Chest";

    public OBJ_Chest(GamePanel gamePanel) {
        super(gamePanel);

        name = objName;
        collision = true;
        shrink = false;
        canPickup = false;

        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = 8;
        solidAreaDefaultY = 16;
        down1 = setup("/objects/Chest", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]\nUsed to store items.";

        for(int i = 0; i < maxChestInvSize; i++) {
            chestInv.add(null);
        }
    }
}