package com.neal.spaceminer.object;

import com.neal.spaceminer.main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Chest extends SuperObject {

    GamePanel gamePanel;

    public OBJ_Chest(GamePanel gamePanel) {
        name = "Chest";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/Chest.png")));
            utility.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);

        } catch (IOException e) {
            e.printStackTrace();
        }

        collision = true;
    }
}
