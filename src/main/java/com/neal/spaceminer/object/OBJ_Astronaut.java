package com.neal.spaceminer.object;

import com.neal.spaceminer.main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Astronaut extends SuperObject {

    GamePanel gamePanel;

    public OBJ_Astronaut(GamePanel gamePanel) {
        name = "Astronaut";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/dead_astronaut.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        collision = false;
    }
}
