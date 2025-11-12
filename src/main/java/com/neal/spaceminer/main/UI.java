package com.neal.spaceminer.main;

import java.awt.*;

public class UI {

    GamePanel gamePanel;
    Font arial_40;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        arial_40 = new Font("Arial", Font.PLAIN, 20);
    }

    public void draw(Graphics2D g2){

        g2.setFont(arial_40);
        g2.setColor(Color.white);
        if(gamePanel.player.canUse){
            g2.drawString("Press E to interact with the object", 25, 384);
        }

    }
}
