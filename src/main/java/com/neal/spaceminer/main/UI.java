package com.neal.spaceminer.main;

import java.awt.*;

public class UI {

    GamePanel gamePanel;
    Graphics2D g2;
    Font arial_40;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        arial_40 = new Font("Arial", Font.PLAIN, 25);
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);

        if (gamePanel.gameState == gamePanel.playState) {

            if (gamePanel.player.canUse) {
                g2.drawString("Press E to interact", 25, gamePanel.screenHeight / 2);

            }
        }

        if (gamePanel.gameState == gamePanel.pauseState) {
            drawPauseScreen();
        }
    }

    public void drawPauseScreen() {

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80));
        String text = "PAUSED";

        g2.drawString(text, getXforCenteredText(text), gamePanel.screenHeight / 2);
    }

    public int getXforCenteredText(String text) {

        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gamePanel.screenWidth / 2 - length / 2;
    }
}
