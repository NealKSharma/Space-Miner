package com.neal.spaceminer.tile;

import com.neal.spaceminer.main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Map extends TileManager{
    GamePanel gamePanel;
    BufferedImage[] worldMap;
    public boolean miniMapOn = true;
    BasicStroke borderStroke = new BasicStroke(10);

    public Map(GamePanel gamePanel){
        super(gamePanel);

        this.gamePanel = gamePanel;

        createWorldMap();
    }

    public void createWorldMap(){
        worldMap = new BufferedImage[gamePanel.maxMap];
        int worldMapWidth = gamePanel.tileSize * gamePanel.maxWorldCol;
        int worldMapHeight = gamePanel.tileSize * gamePanel.maxWorldRow;

        for(int i = 0; i < gamePanel.maxMap; i++){
            worldMap[i] = new BufferedImage(worldMapWidth, worldMapHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = worldMap[i].createGraphics();

            int col = 0;
            int row = 0;

            while(col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow){
                int tileNum = mapTileNum[i][col][row];
                int x = gamePanel.tileSize * col;
                int y = gamePanel.tileSize * row;
                g2.drawImage(tile[tileNum].img, x, y, null);

                col++;
                if(col == gamePanel.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
        }
    }
    public void drawFullMapScreen(Graphics2D g2){
        g2.setColor(Color.black);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
        g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // DRAW MAP
        int width = 750;
        int height = 750;
        int x = gamePanel.screenWidth / 2 - width / 2;
        int y = gamePanel.screenHeight / 2 - height / 2;
        g2.drawImage(worldMap[gamePanel.currentMap], x, y, width, height, null);

        // DRAW PLAYER
        double scale = (double)(gamePanel.tileSize*gamePanel.maxWorldCol)/width;
        int playerX = (int)(x + gamePanel.player.worldX / scale);
        int playerY = (int)(y + gamePanel.player.worldY / scale);
        int playerSize = (int)(gamePanel.tileSize / scale) * 5;
        g2.drawImage(gamePanel.player.down1, playerX-10, playerY-20, playerSize, playerSize, null);
    }
    public void drawMiniMap(Graphics2D g2){
        if(miniMapOn && gamePanel.gameState == gamePanel.playState){
            int width = 200;
            int height = 200;
            int x = gamePanel.screenWidth - width - 10;
            int y = 10;
            g2.setStroke(borderStroke);
            g2.setColor(Color.black);
            g2.drawRect(x, y, width, height);
            g2.drawImage(worldMap[gamePanel.currentMap], x, y, width, height, null);

            // DRAW PLAYER
            double scale = (double)(gamePanel.tileSize*gamePanel.maxWorldCol)/width;
            int playerX = (int)(x + gamePanel.player.worldX / scale);
            int playerY = (int)(y + gamePanel.player.worldY / scale);
            int playerSize = (int)(gamePanel.tileSize / scale) * 5;
            g2.drawImage(gamePanel.player.down1, playerX-3, playerY-6, playerSize, playerSize, null);
        }
    }
}