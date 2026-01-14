package com.neal.spaceminer.main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Utility {

    public BufferedImage scaleImage(BufferedImage original, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        g2.drawImage(original, 0, 0, width, height, null);

        g2.dispose();
        return scaledImage;
    }
}