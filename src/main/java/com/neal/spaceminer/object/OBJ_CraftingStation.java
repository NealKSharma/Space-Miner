package com.neal.spaceminer.object;
import com.neal.spaceminer.main.GamePanel;
import com.neal.spaceminer.entity.Entity;

import java.util.ArrayList;

public class OBJ_CraftingStation extends Entity {

    public ArrayList<Entity> chestInv = new ArrayList<>();

    public static final String objName = "Crafting Station";

    public OBJ_CraftingStation(GamePanel gamePanel) {
        super(gamePanel);

        name = objName;
        collision = true;

        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 112;
        solidArea.height = 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        down1 = setup("/objects/crafting_station", gamePanel.tileSize*2, gamePanel.tileSize);
        description = "[" + name + "]\n";
    }
}