package com.neal.spaceminer.environment;

import com.neal.spaceminer.main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Lighting {

    GamePanel gamePanel;
    BufferedImage darknessFilter;

    Color[] color = new Color[12];
    float[] fraction = new float[12];

    int dayCounter;
    public float filterAlpha = 0f;

    final int day = 0;
    final int dusk = 1;
    final int night = 2;
    final int dawn = 3;
    int dayState = day;

    public Lighting(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        color[0] = new Color(0, 0, 0.1f, 0.1f);
        color[1] = new Color(0, 0, 0.1f, 0.42f);
        color[2] = new Color(0, 0, 0.1f, 0.52f);
        color[3] = new Color(0, 0, 0.1f, 0.61f);
        color[4] = new Color(0, 0, 0.1f, 0.69f);
        color[5] = new Color(0, 0, 0.1f, 0.76f);
        color[6] = new Color(0, 0, 0.1f, 0.82f);
        color[7] = new Color(0, 0, 0.1f, 0.87f);
        color[8] = new Color(0, 0, 0.1f, 0.91f);
        color[9] = new Color(0, 0, 0.1f, 0.94f);
        color[10] = new Color(0, 0, 0.1f, 0.96f);
        color[11] = new Color(0, 0, 0.1f, 0.98f);

        fraction[0] = 0f;
        fraction[1] = 0.4f;
        fraction[2] = 0.5f;
        fraction[3] = 0.6f;
        fraction[4] = 0.65f;
        fraction[5] = 0.7f;
        fraction[6] = 0.75f;
        fraction[7] = 0.8f;
        fraction[8] = 0.85f;
        fraction[9] = 0.9f;
        fraction[10] = 0.95f;
        fraction[11] = 1f;

        setLightSource();
    }

    public void setLightSource(){
        BufferedImage newFilter = new BufferedImage(gamePanel.screenWidth, gamePanel.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) newFilter.getGraphics();

        if (gamePanel.player.hasLight) {
            // CENTER
            int centerX = gamePanel.screenWidth / 2;
            int centerY = gamePanel.screenHeight / 2;

            // GRADIENT
            RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, 450/2, fraction, color);
            g2.setPaint(gPaint);
        } else {
            // NO LIGHT -> Pitch black
            g2.setColor(new Color(0, 0, 0.1f, 0.98f));
        }

        g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);
        g2.dispose();
        darknessFilter = newFilter;
    }
    public void update(){
        // CHECK THE STATE OF THE DAY
        if(dayState == day){
            dayCounter++;
            if(dayCounter > 60*gamePanel.FPS){ // 60 SECONDS
                dayState = dusk;
                dayCounter = 0;
            }
        }
        if(dayState == dusk){
            filterAlpha += 0.0003f;
            if(filterAlpha > 1f){
                filterAlpha = 1f;
                dayState = night;
            }
        }
        if(dayState == night){
            dayCounter++;
            if(dayCounter > 10*gamePanel.FPS){ // 10 SECONDS
                dayState = dawn;
                dayCounter = 0;
            }
        }
        if(dayState == dawn){
            filterAlpha -= 0.0003f;
            if(filterAlpha < 0f){
                filterAlpha = 0f;
                dayState = day;
            }
        }
    }
    public void draw(Graphics2D g2){
        if(filterAlpha <= 0f) return;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
        g2.drawImage(darknessFilter, 0, 0, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}