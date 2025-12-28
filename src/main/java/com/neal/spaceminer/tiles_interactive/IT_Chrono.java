package com.neal.spaceminer.tiles_interactive;

import com.neal.spaceminer.entity.Entity;
import com.neal.spaceminer.main.GamePanel;
import com.neal.spaceminer.object.OBJ_LumenCell;

import java.awt.*;

public class IT_Chrono extends Entity {

    public static final String objName = "Chrono Filament Ore";

    public IT_Chrono(GamePanel gamePanel) {
        super(gamePanel);

        name = objName;
        collision = true;
        isBreakable = true;
        strength = 4;

        solidArea.x = 12;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        down1 = setup("/tiled_objects/chrono_filament_ore", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]";
    }
    @Override
    public Entity getDrop (){
        Entity drop = new OBJ_LumenCell(gamePanel);
        drop.worldX = this.worldX;
        drop.worldY = this.worldY;
        return drop;
    }
    @Override
    public Color getParticleColor() {
        return new Color(57, 137, 207, 255);
    }
    @Override
    public int getParticleSize() {
        return 6;
    }
    @Override
    public int getParticleDuration(){
        return 80;
    }
}
