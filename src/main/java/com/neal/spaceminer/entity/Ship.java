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

    // Rotation properties
    private double angle = 0; // Current angle in radians
    private double rotationSpeed = 0.01; // Rotation speed in radians per frame
    private BufferedImage shipImage;

    public Ship(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        initialize();
        getImage();
    }

    public void initialize(){
        x = 100.0;
        y = 100.0;
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

            x += Math.cos(moveAngle) * speed;
            y += Math.sin(moveAngle) * speed;

        }
    }

    public void draw(Graphics g2){
        Graphics2D g2d = (Graphics2D) g2;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        // Save original transform
        AffineTransform oldTransform = g2d.getTransform();

        // Create new transform for rotation
        AffineTransform transform = new AffineTransform();

        // Calculate center of the ship
        double centerX = x + gamePanel.tileSize / 2.0;
        double centerY = y + gamePanel.tileSize / 2.0;

        // Move to ship center, rotate, then offset back
        transform.translate(centerX, centerY);
        transform.rotate(angle); // Rotate by the current angle
        transform.translate(-gamePanel.tileSize / 2.0, -gamePanel.tileSize / 2.0);

        g2d.setTransform(transform);
        g2d.drawImage(shipImage, 0, 0, gamePanel.tileSize, gamePanel.tileSize, null);

        // Restore original transform
        g2d.setTransform(oldTransform);
    }
}