package com.neal.spaceminer.hostiles;

import com.neal.spaceminer.entity.Entity;
import com.neal.spaceminer.main.GamePanel;
import com.neal.spaceminer.object.OBJ_TechScrap;
import com.neal.spaceminer.object.OBJ_PowerCell;
import com.neal.spaceminer.object.OBJ_DataCore;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class HOS_Dronex extends Entity {
    public static final String hosName = "Dronex";

    public HOS_Dronex(GamePanel gamePanel) {
        super(gamePanel);

        type = 2;
        speed = 1;
        maxLife = 4;
        life = maxLife;
        damage = 5;
        name = hosName;

        speedLock = 1;

        solidArea.x = 12;
        solidArea.y = 12;
        solidArea.width = 40;
        solidArea.height = 40;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }
    public void getImage() {
        up1 = setup("/hostiles/dronex_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/hostiles/dronex_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = up1;
        down2 = up2;
        left1 = up1;
        left2 = up2;
        right1 = up1;
        right2 = up2;
    }
    public void update(){
        super.update();

        int xDistance = Math.abs(worldX - gamePanel.player.worldX);
        int yDistance = Math.abs(worldY - gamePanel.player.worldY);
        int tileDistance = (xDistance + yDistance)/gamePanel.tileSize;

        if(!onPath && tileDistance < 5 && Math.random() < 0.007) onPath = true;
        if(onPath && tileDistance > 8) onPath = false;
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
    @Override
    public Entity getDrop (){
        int randDrop = ThreadLocalRandom.current().nextInt(1, 101);
        Entity drop = new OBJ_TechScrap(gamePanel);;
        if(randDrop <= 10) drop = new OBJ_DataCore(gamePanel);
        else if( randDrop <= 55) drop = new OBJ_PowerCell(gamePanel);

        int randX = ThreadLocalRandom.current().nextInt(-32, 33);
        int randY = ThreadLocalRandom.current().nextInt(-32, 33);
        drop.worldX = this.worldX + randX;
        drop.worldY = this.worldY + randY;
        return drop;
    }
    @Override
    public Color getParticleColor() {
        return new Color(99, 124, 138, 255);
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