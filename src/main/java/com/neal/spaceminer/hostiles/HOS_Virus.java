package com.neal.spaceminer.hostiles;

import com.neal.spaceminer.entity.Entity;
import com.neal.spaceminer.main.GamePanel;

import java.util.Random;

public class HOS_Virus extends Entity {

    int speedLock = 0;

    public HOS_Virus(GamePanel gamePanel) {
        super(gamePanel);

        name = "Virus";
        type = 2;
        speed = 1;
        maxLife = 4;
        life = maxLife;
        damage = 20;

        solidArea.x = 12;
        solidArea.y = 12;
        solidArea.width = 40;
        solidArea.height = 40;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }
    public void getImage() {
        // ONE TYPE OF IMAGE FOR THE ROBOT
        up1 = setup("/hostiles/hostile_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = up1;
        down1 = up1;
        down2 = up1;
        left1 = up1;
        left2 = up1;
        right1 = up1;
        right2 = up1;
    }
    public void update(){
        super.update();

        int xDistance = Math.abs(worldX - gamePanel.player.worldX);
        int yDistance = Math.abs(worldY - gamePanel.player.worldY);
        int tileDistance = (xDistance + yDistance)/gamePanel.tileSize;

        if(!onPath && tileDistance < 5 && Math.random() < 0.005) onPath = true;
        if(onPath && tileDistance > 8) onPath = false;
    }
    public void setAction() {
        if(speedLock == 5){
            speed = gamePanel.player.speed;
            speedLock = 0;
        } else {
            speed = 0;
        }
        speedLock++;

        if(onPath){
            int goalCol = (gamePanel.player.worldX + gamePanel.player.solidArea.x) / gamePanel.tileSize;
            int goalRow = (gamePanel.player.worldY + gamePanel.player.solidArea.y) / gamePanel.tileSize;
            searchPath(goalCol, goalRow);
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
        }
    }
}
