package com.neal.spaceminer.entity;

import com.neal.spaceminer.main.GamePanel;

import java.awt.*;
import java.util.Random;

public class NPC_Robot extends Entity {

    public NPC_Robot(GamePanel gamePanel){
        super(gamePanel);

        direction = "down";
        onPath = true;

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
        speed = (speed == 0) ? gamePanel.player.speed : 0;
        if(onPath){
            int goalCol = (gamePanel.player.worldX + gamePanel.player.solidArea.x) / gamePanel.tileSize;
            int goalRow = (gamePanel.player.worldY + gamePanel.player.solidArea.y) / gamePanel.tileSize;

            int distanceX = Math.abs(worldX - gamePanel.player.worldX);
            int distanceY = Math.abs(worldY - gamePanel.player.worldY);

            int range = 128;

            if(distanceX > range || distanceY > range) {
                searchPath(goalCol, goalRow);
            } else {
                speed = 0;
                spriteCounter = 0;
            }
        } else {
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
    public void searchPath(int goalCol, int goalRow){
        int startCol = (worldX + solidArea.x) / gamePanel.tileSize;
        int startRow = (worldY + solidArea.y) / gamePanel.tileSize;

        gamePanel.pathFinder.setNodes(startCol, startRow, goalCol, goalRow);
        if(gamePanel.pathFinder.search()){
            // NEXT WORLDX AND WORLDY
            int nextCol = gamePanel.pathFinder.pathList.getFirst().col;
            int nextRow = gamePanel.pathFinder.pathList.getFirst().row;
            int nextX = nextCol * gamePanel.tileSize;
            int nextY = nextRow * gamePanel.tileSize;

            // ENTITY'S SOLID AREA POSITION
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gamePanel.tileSize){
                direction = "up";
            } else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gamePanel.tileSize){
                direction = "down";
            } else if(enTopY >= nextY && enBottomY < nextY + gamePanel.tileSize){
                // LEFT OR RIGHT
                if(enLeftX > nextX) {
                    direction = "left";
                }
                if(enLeftX < nextX) {
                    direction = "right";
                }
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

            // IF NPC HAS TO GET TO A CERTAIN ROW AND COL
            // if(nextCol == goalCol && nextRow == goalRow) onPath = false;
        }
    }
}
