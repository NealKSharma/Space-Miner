package com.neal.spaceminer.entity;

import com.neal.spaceminer.main.GamePanel;
import com.neal.spaceminer.main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Objects;

public class Ship extends Entity{

    GamePanel gamePanel;
    KeyHandler keyHandler = new KeyHandler();

    public Ship(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        initialize();
        getImage();
    }

    public void initialize(){
        x = 100;
        y = 100;
        speed = 2;
        direction = "right";
    }

    public void getImage(){
        try{
            up = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ship/ship_up.png")));
            down = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ship/ship_down.png")));
            left = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ship/ship_left.png")));
            right = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ship/ship_right.png")));
            nw = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ship/ship_nw.png")));
            ne = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ship/ship_ne.png")));
            sw = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ship/ship_sw.png")));
            se = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ship/ship_se.png")));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void update(){
        if (keyHandler.up){
            direction = "up";
            y -= speed;
        }
        if (keyHandler.down){
            direction = "down";
            y += speed;
        }
        if (keyHandler.left){
            direction = "left";
            x -= speed;
        }
        if (keyHandler.right){
            direction = "right";
            x += speed;
        }
    }

    public void draw(Graphics g2){

        BufferedImage img = switch (direction) {
            case "up" -> up;
            case "down" -> down;
            case "left" -> left;
            case "right" -> right;
            default -> null;
        };

        g2.drawImage(img, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}