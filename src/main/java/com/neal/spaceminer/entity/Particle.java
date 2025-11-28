package com.neal.spaceminer.entity;

import com.neal.spaceminer.main.GamePanel;

import java.awt.*;

public class Particle extends Entity{

    Entity generator;
    Color color;
    int size, maxLife, life;
    int xd;
    int yd;

    public Particle(GamePanel gamePanel, Entity generator, Color color, int size, int maxLife, int xd, int yd){
        super(gamePanel);

        this.generator = generator;
        this.color = color;
        this.size = size;
        this.maxLife = maxLife;
        this.xd = xd;
        this.yd = yd;

        life = maxLife;
        int offset = (gamePanel.tileSize/2) - (size / 2);
        worldX = generator.worldX + offset;
        worldY = generator.worldY + offset;
    }
    public void update(){
        life--;
        if(life % 4 == 1){
            if (life < maxLife/4){
                yd++;
            }
            worldX += xd;
            worldY += yd;
        }
        if(life == 0){
            alive = false;
        }
    }
    public void draw(Graphics2D g2){
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        g2.setColor(color);
        g2.fillRect(screenX, screenY, size, size);
    }
}