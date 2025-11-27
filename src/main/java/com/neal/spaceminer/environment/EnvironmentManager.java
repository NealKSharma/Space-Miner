package com.neal.spaceminer.environment;

import com.neal.spaceminer.main.GamePanel;

import java.awt.*;

public class EnvironmentManager {

    GamePanel gamePanel;
    Lighting lighting;

    public EnvironmentManager(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    public void setup(){
        lighting = new Lighting(gamePanel);
    }
    public void update(){
        if(lighting == null) return;
        lighting.setLightSource();
    }
    public void draw(Graphics2D g2){
        lighting.draw(g2);
    }
}