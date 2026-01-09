package com.neal.spaceminer.object;
import com.neal.spaceminer.main.GamePanel;
import com.neal.spaceminer.entity.Entity;

public class OBJ_Habitat extends Entity {

    public static final String objName = "Habitat";

    public OBJ_Habitat(GamePanel gamePanel) {
        super(gamePanel);

        name = objName;
        collision = true;

        solidArea.x = 32;
        solidArea.y = 80;
        solidArea.width = 245;
        solidArea.height = 165;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        down1 = setup("/objects/habitat", gamePanel.tileSize*5, gamePanel.tileSize*5);
        description = "[" + name + "]";
    }
}