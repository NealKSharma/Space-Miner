package com.neal.spaceminer.entity;

import com.neal.spaceminer.main.GamePanel;

public class Projectile extends Entity{

    Entity origin;

    public Projectile(GamePanel gamePanel){
        super(gamePanel);
    }
    public void set(int worldX, int worldY, String direction, boolean alive, Entity origin){
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.origin = origin;
        this.life = this.maxLife;
    }
    public void update(){
        if(origin == gamePanel.player){
            int hostileIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.hostile);
            if(hostileIndex != -1){
                gamePanel.player.damage = damage;
                gamePanel.player.attack(hostileIndex);
                alive = false;
            }
        } else {
            boolean contactPlayer = gamePanel.collisionChecker.checkPlayer(this);
            if(contactPlayer && !gamePanel.player.invincible){
                gamePanel.playSE(3);
                gamePanel.player.suiteIntegrity -= damage;
                gamePanel.player.invincible = true;
                alive = false;
            }
        }

        switch(direction){
            case "up": worldY -= speed; break;
            case "down": worldY += speed; break;
            case "left": worldX -= speed; break;
            case "right": worldX += speed; break;
        }

        life--;
        if(life <= 0){
            alive = false;
        }

        spriteCounter++;
        if (spriteCounter > 32) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }
}
