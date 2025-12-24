package com.neal.spaceminer.entity;

import com.neal.spaceminer.main.GamePanel;

import java.awt.*;
import java.util.Random;

public class NPC_Robot extends Entity {

    public NPC_Robot(GamePanel gamePanel){
        super(gamePanel);

        direction = "down";
        speed = gamePanel.player.speed;

        solidArea = new Rectangle();
        solidArea.x = 18;
        solidArea.y = 15;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = gamePanel.tileSize - 35;
        solidArea.height = gamePanel.tileSize - 25;

        getImage();
    }

    public void getImage() {
        up1 = setup("/astronaut/back1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/astronaut/back2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/astronaut/front1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/astronaut/front2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/astronaut/left1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/astronaut/left2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/astronaut/right1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/astronaut/right2", gamePanel.tileSize, gamePanel.tileSize);
    }
    public void setAction() {
        actionCooldown++;

        if(actionCooldown == gamePanel.FPS*2){
            actionCooldown = 0;

            Random rand = new Random();
            int i = rand.nextInt(4) + 1; // PICK A NUMBER FROM 1 to 4

            switch (i) {
                case 1: direction = "up"; break;
                case 2: direction = "down"; break;
                case 3: direction = "left"; break;
                case 4: direction = "right"; break;
            }
        }
    }
}
