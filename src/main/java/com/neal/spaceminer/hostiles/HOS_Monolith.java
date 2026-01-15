package com.neal.spaceminer.hostiles;

import com.neal.spaceminer.entity.Entity;
import com.neal.spaceminer.main.GamePanel;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class HOS_Monolith extends Entity {
    public static final String hosName = "Monolith";

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
        // ONE TYPE OF IMAGE FOR THE ROBOT
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

        int xDistance = Math.abs(worldX - gamePanel.player.worldX);
        int yDistance = Math.abs(worldY - gamePanel.player.worldY);
        int tileDistance = (xDistance + yDistance)/gamePanel.tileSize;

        if(!onPath && tileDistance < 9 && Math.random() < 0.009) onPath = true;
        if(onPath && tileDistance > 14) onPath = false;
    }
    public void setAction() {
        if(onPath){
            searchPath();
        } else {
            actionCooldown++;

            if(actionCooldown == gamePanel.FPS*2){
                actionCooldown = 0;

                int i = ThreadLocalRandom.current().nextInt(1, 5);

                switch (i) {
                    case 1: direction = "up"; break;
                    case 2: direction = "down"; break;
                    case 3: direction = "left"; break;
                    case 4: direction = "right"; break;
                }
            }
        }
    }
    public void searchPath(){
        int startCol = (worldX + solidArea.x) / gamePanel.tileSize;
        int startRow = (worldY + solidArea.y) / gamePanel.tileSize;
        int goalCol = (gamePanel.player.worldX + gamePanel.player.solidArea.x) / gamePanel.tileSize;
        int goalRow = (gamePanel.player.worldY + gamePanel.player.solidArea.y) / gamePanel.tileSize;

        gamePanel.pathFinder.setNodes(startCol, startRow, goalCol, goalRow);
        if(gamePanel.pathFinder.search()){
            // NEXT WORLDX AND WORLDY
            int nextX = gamePanel.pathFinder.pathList.getFirst().col * gamePanel.tileSize;
            int nextY = gamePanel.pathFinder.pathList.getFirst().row * gamePanel.tileSize;

            // ENTITY'S SOLID AREA POSITION
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gamePanel.tileSize) direction = "up";
            else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gamePanel.tileSize) direction = "down";
            else if(enTopY >= nextY && enBottomY < nextY + gamePanel.tileSize){
                // LEFT OR RIGHT
                if(enLeftX > nextX) direction = "left";
                if(enLeftX < nextX) direction = "right";
            } else if (enTopY > nextY && enLeftX > nextX){
                // UP OR LEFT
                direction = "up";
                checkCollision();
                if(collisionOn) direction = "left";
            }
            else if(enTopY > nextY && enLeftX < nextX){
                // UP OR RIGHT
                direction = "up";
                checkCollision();
                if(collisionOn) direction = "right";
            }
            else if(enTopY < nextY && enLeftX > nextX){
                // DOWN OR LEFT
                direction = "down";
                checkCollision();
                if(collisionOn) direction = "left";
            }
            else if(enTopY < nextY && enLeftX < nextX){
                // DOWN OR RIGHT
                direction = "down";
                checkCollision();
                if(collisionOn) direction = "right";
            }
        }
    }
}