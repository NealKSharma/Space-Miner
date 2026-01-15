package com.neal.spaceminer.entity;

import com.neal.spaceminer.main.GamePanel;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class NPC_Robot extends Entity {

    public static final String npcName = "Robot";

    public NPC_Robot(GamePanel gamePanel){
        super(gamePanel);

        direction = "down";
        onPath = true;
        name = npcName;

        solidArea = new Rectangle();
        solidArea.x = 18;
        solidArea.y = 15;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = gamePanel.tileSize - 35;
        solidArea.height = gamePanel.tileSize - 25;

        getImage();
        setDialogue();
    }

    public void getImage() {
        // ONE TYPE OF IMAGE FOR THE ROBOT
        up1 = setup("/robot/robot", gamePanel.tileSize, gamePanel.tileSize);
        up2 = up1;
        down1 = up1;
        down2 = up1;
        left1 = up1;
        left2 = up1;
        right1 = up1;
        right2 = up1;
    }
    public void setDialogue(){
        dialogue.add("First Text");
        dialogue.add("Second Text");
        dialogue.add("Third Text");
        dialogue.add("Fourth Text");
    }
    public void speak(){
        if(dialogueIndex >= dialogue.size()){
            dialogueIndex = 0;
        }
        gamePanel.ui.currentDialogue = dialogue.get(dialogueIndex);
        dialogueIndex++;

        switch(gamePanel.player.direction){
            case "up": direction = "down"; break;
            case "down": direction = "up"; break;
            case "left": direction = "right"; break;
            case "right": direction = "left"; break;
        }
    }
    public void setAction() {
        if(onPath){
            int distanceX = Math.abs(worldX - gamePanel.player.worldX);
            int distanceY = Math.abs(worldY - gamePanel.player.worldY);

            int range = 128;

            if(distanceX > range || distanceY > range) {
                speed = 1;
                searchPath();
            } else {
                speed = 0;
                spriteCounter = 0;
            }
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
            // IF NPC HAS TO GET TO A CERTAIN ROW AND COL
            // if(nextCol == goalCol && nextRow == goalRow) onPath = false;
        }
    }
}