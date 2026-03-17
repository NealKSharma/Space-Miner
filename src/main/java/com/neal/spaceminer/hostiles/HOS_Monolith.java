package com.neal.spaceminer.hostiles;

import com.neal.spaceminer.entity.Entity;
import com.neal.spaceminer.main.GamePanel;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class HOS_Monolith extends Entity {
    public static final String hosName = "Monolith";
    int cooldown = 0;

    public HOS_Monolith(GamePanel gamePanel) {
        super(gamePanel);

        type = 2;
        speed = 1;
        maxLife = 40;
        life = maxLife;
        damage = 30;
        name = hosName;

        speedLock = 2;

        solidArea.x = 16;
        solidArea.y = 12;
        solidArea.width = 100;
        solidArea.height = 100;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }
    public void getImage() {
        up1 = setup("/hostiles/back1", gamePanel.tileSize*2, gamePanel.tileSize*2);
        up2 = setup("/hostiles/back2", gamePanel.tileSize*2, gamePanel.tileSize*2);
        down1 = setup("/hostiles/front1", gamePanel.tileSize*2, gamePanel.tileSize*2);
        down2 = setup("/hostiles/front2", gamePanel.tileSize*2, gamePanel.tileSize*2);
        left1 = setup("/hostiles/left1", gamePanel.tileSize*2, gamePanel.tileSize*2);
        left2 = setup("/hostiles/left2", gamePanel.tileSize*2, gamePanel.tileSize*2);
        right1 = setup("/hostiles/right1", gamePanel.tileSize*2, gamePanel.tileSize*2);
        right2 = setup("/hostiles/right2", gamePanel.tileSize*2, gamePanel.tileSize*2);
    }
    public void update(){
        super.update();

        int xDistance = Math.abs(getCenterX() - gamePanel.player.worldX);
        int yDistance = Math.abs(getCenterY() - gamePanel.player.worldY);
        int tileDistance = (xDistance + yDistance)/gamePanel.tileSize;

        if(!onPath && tileDistance < 8) onPath = true;
        if(onPath && tileDistance > 12) onPath = false;
    }
    public void setAction() {
        speed = 1;
        if(onPath){
            moveToPlayer();
        } else {
            actionCooldown++;

            if(actionCooldown == gamePanel.FPS*2){
                actionCooldown = 0;

                int i = ThreadLocalRandom.current().nextInt(1, 8);

                switch (i) {
                    case 1: direction = "up"; break;
                    case 2: direction = "down"; break;
                    case 3: direction = "left"; break;
                    case 4: direction = "right"; break;
                    default: speed = 0;
                }
            }
        }
    }
    public void moveToPlayer(){
        cooldown++;
        if(cooldown == gamePanel.FPS/2){
            int xDistance = Math.abs(getCenterX() - gamePanel.player.worldX);
            int yDistance = Math.abs(getCenterY() - gamePanel.player.worldY);
            if(xDistance > yDistance){
                if(gamePanel.player.worldX < getCenterX()){
                    direction = "left";
                } else {
                    direction = "right";
                }
            } else if (xDistance < yDistance){
                if(gamePanel.player.worldY < getCenterY()){
                    direction = "up";
                } else {
                    direction = "down";
                }
            }
            cooldown = 0;
        }
    }
}