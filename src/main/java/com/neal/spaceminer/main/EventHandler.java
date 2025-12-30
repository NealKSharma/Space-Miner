package com.neal.spaceminer.main;

import java.awt.*;

public class EventHandler {

    GamePanel gamePanel;
    EventRect[][][] eventRect;
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    int tempMap, tempCol, tempRow;

    public EventHandler(GamePanel gamePanel){
        this.gamePanel = gamePanel;

        eventRect = new EventRect[gamePanel.maxMap][gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        int map = 0;
        int col = 0;
        int row = 0;
        while(map < gamePanel.maxMap && col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow){
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 31;
            eventRect[map][col][row].y = 31;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
            col++;
            if(col == gamePanel.maxWorldCol){
                row++;
                col = 0;

                if(row == gamePanel.maxWorldRow){
                    row = 0;
                    map++;
                }
            }
        }
    }

    public void checkEvent(){
        // CHECK IF THE PLAYER CHARACTER IS MORE THAN 1 TILE AWAY
        int xDistance = Math.abs(gamePanel.player.worldX - previousEventX);
        int yDistance = Math.abs(gamePanel.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if(distance > gamePanel.tileSize/2){
            canTouchEvent = true;
        }

        if(canTouchEvent){
            if(hit(0, 74, 70, "any")){ mapSwitch(1, 50, 59); }
            else if(hit(1, 50, 59, "any")){ mapSwitch(0, 74, 70); }
        }
    }
    public boolean hit(int map, int col, int row, String reqDirection){
        boolean hit = false;

        if(map == gamePanel.currentMap){
            gamePanel.player.solidArea.x = gamePanel.player.worldX + gamePanel.player.solidArea.x;
            gamePanel.player.solidArea.y = gamePanel.player.worldY + gamePanel.player.solidArea.y;
            eventRect[map][col][row].x = col*gamePanel.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row*gamePanel.tileSize + eventRect[map][col][row].y;
            if(gamePanel.player.solidArea.intersects(eventRect[map][col][row]) && !eventRect[map][col][row].eventDone){
                if(gamePanel.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
                    hit = true;
                    previousEventX = gamePanel.player.worldX;
                    previousEventY = gamePanel.player.worldY;
                }
            }

            gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
            gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }
        return hit;
    }
    public void mapSwitch(int map, int col, int row){
        tempMap = map;
        tempCol = col;
        tempRow = row;

        gamePanel.gameState = gamePanel.transitionState;

        gamePanel.ui.transitionType = 0;

        gamePanel.ui.counter = 0;
        canTouchEvent = false;
    }
}
