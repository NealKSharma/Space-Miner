package com.neal.spaceminer.tile;

import com.neal.spaceminer.main.GamePanel;
import com.neal.spaceminer.main.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {

    GamePanel gamePanel;
    public Tile[] tile;
    public int[][][] mapTileNum;
    public boolean drawPath = false;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tile = new Tile[20];
        mapTileNum = new int[gamePanel.maxMap][gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        getTileImage();
        loadMap("/maps/map00.txt", 0);
        loadMap("/maps/map01.txt", 1);
    }
    public void getTileImage() {
        // Lava
        setup(0, "00_lava", true);
        setup(1, "01_lava", true);
        setup(2, "02_lava", true);
        setup(3, "03_lava", true);
        setup(4, "04_lava", true);
        setup(5, "05_lava", true);
        setup(6, "06_lava", true);
        setup(7, "07_lava", true);
        setup(8, "08_lava", true);
        setup(9, "09_lava", true);
        setup(10, "10_lava", true);
        setup(11, "11_lava", true);
        setup(12, "12_lava", true);
        setup(13, "13_lava", true);

        // Surfaces
        setup(14, "14_surface", false);
        setup(15, "15_surface", false);

        // Black Tile
        setup(16, "16_black", true);

        // Habitat
        setup(17, "17_wall", true);
        setup(18, "18_floor", false);
        setup(19, "19_door", true);
    }
    public void setup(int index, String imageName, boolean collision) {
        Utility utility = new Utility();
        try {
            tile[index] = new Tile();
            tile[index].img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + imageName + ".png")));
            tile[index].img = utility.scaleImage(tile[index].img, gamePanel.tileSize, gamePanel.tileSize);
            tile[index].collision = collision;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadMap(String filePath, int map) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
                String line = br.readLine();
                while (col < gamePanel.maxWorldCol) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[map][col][row] = num;
                    col++;
                }
                if (col == gamePanel.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean isOnScreen(int worldX, int worldY) {
        int playerWorldX = gamePanel.player.worldX;
        int playerWorldY = gamePanel.player.worldY;
        int playerScreenX = gamePanel.player.screenX;
        int playerScreenY = gamePanel.player.screenY;

        int buffer = gamePanel.tileSize;

        return worldX + gamePanel.tileSize > playerWorldX - playerScreenX - buffer &&
                worldX - gamePanel.tileSize < playerWorldX + playerScreenX + buffer &&
                worldY + gamePanel.tileSize > playerWorldY - playerScreenY - buffer &&
                worldY - gamePanel.tileSize < playerWorldY + playerScreenY + buffer;
    }
    public void draw(Graphics2D g2) {
        int currentWorldX = gamePanel.player.worldX;
        int currentWorldY = gamePanel.player.worldY;
        int screenXOffset = gamePanel.player.screenX;
        int screenYOffset = gamePanel.player.screenY;

        int startCol = Math.max(0, (currentWorldX - screenXOffset) / gamePanel.tileSize - 1);
        int endCol = Math.min(gamePanel.maxWorldCol, (currentWorldX + screenXOffset) / gamePanel.tileSize + 2);
        int startRow = Math.max(0, (currentWorldY - screenYOffset) / gamePanel.tileSize - 1);
        int endRow = Math.min(gamePanel.maxWorldRow, (currentWorldY + screenYOffset) / gamePanel.tileSize + 2);

        for (int worldRow = startRow; worldRow < endRow; worldRow++) {
            for (int worldCol = startCol; worldCol < endCol; worldCol++) {
                int tileNum = mapTileNum[gamePanel.currentMap][worldCol][worldRow];

                int worldX = worldCol * gamePanel.tileSize;
                int worldY = worldRow * gamePanel.tileSize;

                int screenX = worldX - currentWorldX + screenXOffset;
                int screenY = worldY - currentWorldY + screenYOffset;

                g2.drawImage(tile[tileNum].img, screenX, screenY, gamePanel.tileSize + 1, gamePanel.tileSize + 1, null);
            }
        }

        if(drawPath){
            g2.setColor(new Color(255, 0, 0, 70));
            for(int i = 0; i < gamePanel.pathFinder.pathList.size(); i++){
                int worldX = gamePanel.pathFinder.pathList.get(i).col * gamePanel.tileSize;
                int worldY = gamePanel.pathFinder.pathList.get(i).row * gamePanel.tileSize;
                if(isOnScreen(worldX, worldY)){
                    int screenX = worldX - currentWorldX + screenXOffset;
                    int screenY = worldY - currentWorldY + screenYOffset;
                    g2.fillRect(screenX, screenY, gamePanel.tileSize, gamePanel.tileSize);
                }
            }
        }
    }
}