package com.neal.spaceminer.entity;

import com.neal.spaceminer.main.GamePanel;
import com.neal.spaceminer.main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Ship extends Entity{

    GamePanel gamePanel;
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;

    // Rotation properties
    private double angle = 0; // Current angle in radians
    private double rotationSpeed = 0.01; // Rotation speed in radians per frame
    private BufferedImage shipImage;

    public Ship(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        screenX = gamePanel.screenWidth/2 - gamePanel.tileSize/2;
        screenY = gamePanel.screenHeight/2 - gamePanel.tileSize/2;;

        initialize();
        getImage();
    }

    public void initialize(){
        worldX = gamePanel.tileSize*23;
        worldY = gamePanel.tileSize*21;
        speed = 1.5;
        angle = 0; // Start facing North
    }

    public void getImage(){
        try{
            // Use the up-facing ship image as base
            shipImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ship/ship_up.png")));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void update(){
        // Rotation - A rotates left (counterclockwise), D rotates right (clockwise)
        if (keyHandler.left){
            angle -= rotationSpeed;
        }
        if (keyHandler.right){
            angle += rotationSpeed;
        }

        // Forward movement - moves in the direction the ship is facing
        if (keyHandler.up){
            double moveAngle = angle - Math.PI / 2;

            worldX += Math.cos(moveAngle) * speed;
            worldY += Math.sin(moveAngle) * speed;

        }
    }

    public void draw(Graphics g2){
        Graphics2D g2d = (Graphics2D) g2;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        // Save original transform
        AffineTransform oldTransform = g2d.getTransform();

        // Translate to center, rotate, then draw centered
        g2d.translate(screenX + gamePanel.tileSize / 2, screenY + gamePanel.tileSize / 2);
        g2d.rotate(angle);
        g2d.drawImage(shipImage, -gamePanel.tileSize / 2, -gamePanel.tileSize / 2, gamePanel.tileSize, gamePanel.tileSize, null);

        // Restore original transform
        g2d.setTransform(oldTransform);
    }
}