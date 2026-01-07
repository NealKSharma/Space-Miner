package com.neal.spaceminer.entity;

import com.neal.spaceminer.main.GamePanel;
import com.neal.spaceminer.main.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Entity {

    protected GamePanel gamePanel;

    public int worldX, worldY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage mineUp1, mineUp2, mineDown1, mineDown2, mineLeft1,  mineLeft2, mineRight1, mineRight2;
    public String direction = "down";

    public int spriteCounter = 0;
    public int spriteNum = 1;
    public int actionCooldown = 0;
    boolean mining = false;

    // ENTITIES
    ArrayList<String> dialogue = new  ArrayList<>();
    public int maxLife, life;
    public boolean invincible = false;
    public int invincibleCounter = 0;
    public int damage = 0;
    public int type; // 0 - Player, 1 - NPC, 2 - Hostile

    // COLLISION
    public Rectangle solidArea = new Rectangle(0, 0, 64, 64);
    public Rectangle swingArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

    // STATE
    public boolean alive = true;
    public boolean onPath = false;
    public int dialogueIndex = 0;

    // ITEM ATTRIBUTES
    public String description = "";
    public String name;
    public boolean collision = false;
    public boolean shrink = false;
    public boolean canPickup = false;
    public boolean isBreakable = false;
    public boolean isStackable = false;
    public int itemAmount = 1;
    public int strength;
    public boolean placedOnGround = false;
    public int mineCount = 0;

    public Entity(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        // Temp variables for mining animation offsets
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        // DETERMINE WHICH IMAGE TO USE FIRST
        switch (direction) {
            case "up":
                if (!mining) {
                    image = (spriteNum == 1) ? up1 : up2;
                } else {
                    tempScreenY = screenY - gamePanel.tileSize;
                    image = (spriteNum == 1) ? mineUp1 : mineUp2;
                }
                break;
            case "down":
                if (!mining) {
                    image = (spriteNum == 1) ? down1 : down2;
                } else {
                    image = (spriteNum == 1) ? mineDown1 : mineDown2;
                }
                break;
            case "left":
                if (!mining) {
                    image = (spriteNum == 1) ? left1 : left2;
                } else {
                    tempScreenX = screenX - gamePanel.tileSize;
                    image = (spriteNum == 1) ? mineLeft1 : mineLeft2;
                }
                break;
            case "right":
                if (!mining) {
                    image = (spriteNum == 1) ? right1 : right2;
                } else {
                    image = (spriteNum == 1) ? mineRight1 : mineRight2;
                }
                break;
        }

        // Safety check
        if (image == null) return;

        // GET THE ACTUAL SIZE OF THE ENTITY
        int width = image.getWidth();
        int height = image.getHeight();

        // "shrink" logic (for small items)
        if (shrink) {
            width = gamePanel.tileSize / 2;
            height = gamePanel.tileSize / 2;
            tempScreenX += (gamePanel.tileSize - width) / 2;
            tempScreenY += (gamePanel.tileSize - height) / 2;
        }

        //CHECK VISIBILITY USING THE REAL WIDTH/HEIGHT
        if (worldX + width > gamePanel.player.worldX - gamePanel.player.screenX &&
                worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                worldY + height > gamePanel.player.worldY - gamePanel.player.screenY &&
                worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {

            g2.drawImage(image, tempScreenX, tempScreenY, width, height, null);

            // Draw collision box for debugging
            if(gamePanel.keyHandler.showDebug) {
                g2.setColor(Color.red);
                g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
            }
        }
    }
    public void checkCollision(){
        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);
        gamePanel.collisionChecker.checkObject(this, false);
        gamePanel.collisionChecker.checkEntity(this, gamePanel.npc);
        gamePanel.collisionChecker.checkEntity(this, gamePanel.hostile);
        gamePanel.collisionChecker.checkBot(this, gamePanel.bot);
        boolean contactPlayer = gamePanel.collisionChecker.checkPlayer(this);

        if(this.type == 2 && contactPlayer){
            if(!gamePanel.player.invincible){
                gamePanel.player.suiteIntegrity -= this.damage;
                gamePanel.player.invincible = true;
            }
        }
    }
    public void update() {
        setAction();
        checkCollision();

        // IF COLLISION IS FALSE, ENTITY CAN MOVE
        if (!collisionOn) {
            switch (direction) {
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
            }
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
    public void setAction() {

    }
    public void speak(){}
    public BufferedImage setup(String imagePath, int width, int height) {
        Utility utility = new Utility();
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResource(imagePath + ".png")));
            image = utility.scaleImage(image, width, height);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
    public Entity getDrop(){
        return null;
    }
    public void generateParticle(Entity generator, Entity target){
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int duration = generator.getParticleDuration();

        Particle p1 = new Particle(gamePanel, generator, color, size, duration, -2, -1);
        Particle p2 = new Particle(gamePanel, generator, color, size, duration, 2, -1);
        Particle p3 = new Particle(gamePanel, generator, color, size, duration, -2, 1);
        Particle p4 = new Particle(gamePanel, generator, color, size, duration, 2, 1);
        gamePanel.particleList.add(p1);
        gamePanel.particleList.add(p2);
        gamePanel.particleList.add(p3);
        gamePanel.particleList.add(p4);
    }
    public Color getParticleColor(){
        return null;
    }
    public int getParticleSize(){
        return -1;
    }
    public int getParticleDuration(){
        return -1;
    }
}