package com.neal.spaceminer.object;

import com.neal.spaceminer.entity.Projectile;
import com.neal.spaceminer.main.GamePanel;

public class OBJ_CorruptionOrb extends Projectile {

    GamePanel gamePanel;
    public static final String objName = "Corruption Orb";

    public OBJ_CorruptionOrb(GamePanel gamePanel){
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = objName;
        speed = 3;
        maxLife = 160;
        life = maxLife;
        damage = 30;
        alive = false;

        getImage();
    }
    public void getImage(){
        up1 = setup("/projectile/corruption_orb", gamePanel.tileSize, gamePanel.tileSize);
        up2 = up1;
        down1 = up1;
        down2 = up1;
        left1 = up1;
        left2 = up1;
        right1 = up1;
        right2 = up1;
    }
}
