package com.neal.spaceminer.object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Astronaut extends SuperObject{

    public OBJ_Astronaut(){
        name = "Astronaut";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/dead_astronaut.png")));
        } catch(IOException e){
            e.printStackTrace();
        }

        collision = false;
    }
}
