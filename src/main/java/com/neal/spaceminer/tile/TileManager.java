package com.neal.spaceminer.tile;

import com.neal.spaceminer.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {

    GamePanel gamePanel;
    Tile[] tile;
    int mapTileNum[][];

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tile = new Tile[10];
        mapTileNum = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        getTileImage();
        loadMap("/maps/map01.txt");
    }

    public void getTileImage(){
        try{
            tile[0] = new Tile();
            tile[0].img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/surface.png")));

            tile[1] = new Tile();
            tile[1].img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/testimages/wall.png")));

            tile[2] = new Tile();
            tile[2].img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/lava.png")));

            tile[3] = new Tile();
            tile[3].img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/drytree.png")));

            tile[4] = new Tile();
            tile[4].img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/rock1.png")));

            tile[5] = new Tile();
            tile[5].img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/path.png")));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath){
        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
                String line = br.readLine();
                while(col < gamePanel.maxWorldCol){
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gamePanel.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
            br.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){

        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow){
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gamePanel.tileSize;
            int worldY = worldRow * gamePanel.tileSize;
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

            if (worldX + gamePanel.tileSize> gamePanel.player.worldX - gamePanel.player.screenX &&
                worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY){

                g2.drawImage(tile[tileNum].img, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
            }

            worldCol++;

            if (worldCol==gamePanel.maxWorldCol){
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
