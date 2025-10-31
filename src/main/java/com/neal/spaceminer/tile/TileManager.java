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
            tile[0].img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/testimages/grass.png")));

            tile[1] = new Tile();
            tile[1].img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/testimages/wall.png")));

            tile[2] = new Tile();
            tile[2].img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/testimages/water.png")));
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

            while (col < gamePanel.maxScreenCol && row < gamePanel.maxScreenRow) {
                String line = br.readLine();
                while(col < gamePanel.maxScreenCol){
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gamePanel.maxScreenCol){
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
            int screenX = (int) (worldX - gamePanel.ship.worldX + gamePanel.ship.screenX);
            int screenY = (int) (worldY - gamePanel.ship.worldY + gamePanel.ship.screenY);

            if (worldX + gamePanel.tileSize> gamePanel.ship.worldX - gamePanel.ship.screenX &&
                worldX - gamePanel.tileSize < gamePanel.ship.worldX + gamePanel.ship.screenX &&
                worldY + gamePanel.tileSize > gamePanel.ship.worldY - gamePanel.ship.screenY &&
                worldY - gamePanel.tileSize < gamePanel.ship.worldY + gamePanel.ship.screenY){

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
