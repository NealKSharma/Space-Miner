package com.neal.spaceminer.tiles_interactive;

import com.neal.spaceminer.entity.Entity;
import com.neal.spaceminer.main.GamePanel;
import com.neal.spaceminer.object.OBJ_Pulsarite;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class IT_Pulsarite extends Entity {

    public static final String objName = "Pulsarite Ore";

    public IT_Pulsarite(GamePanel gamePanel) {
        super(gamePanel);

        name = objName;
        collision = true;
        isBreakable = true;
        strength = 3;

        solidArea.x = 12;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        down1 = setup("/tiled_objects/pulsarite_ore", gamePanel.tileSize, gamePanel.tileSize);
        description = "[" + name + "]\n Ore for Pulsarite.";
    }
    @Override
    public Entity getDrop (){
        Entity drop = new OBJ_Pulsarite(gamePanel);
        int randX = ThreadLocalRandom.current().nextInt(-32, 33);
        int randY = ThreadLocalRandom.current().nextInt(-32, 33);
        drop.worldX = this.worldX + randX;
        drop.worldY = this.worldY + randY;
        return drop;
    }
    @Override
    public Color getParticleColor() {
        return new Color(125, 177, 221, 255);
    }
    @Override
    public int getParticleSize() {
        return 6;
    }
    @Override
    public int getParticleDuration(){
        return 40;
    }
}