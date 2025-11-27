package com.neal.spaceminer.main;

import com.neal.spaceminer.data.SaveLoad;
import com.neal.spaceminer.entity.Entity;
import com.neal.spaceminer.entity.Player;
import com.neal.spaceminer.environment.EnvironmentManager;
import com.neal.spaceminer.tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
    // Screen Settings
    final int originalTileSize = 16; // 16x16 Tile
    final int scale = 4;

    public final int tileSize = originalTileSize * scale; // After Scaling becomes 64x64
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 1024px (64x16)
    public final int screenHeight = tileSize * maxScreenRow; // 768px (64x12)

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    // FULLSCREEN
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreen = true;

    // FPS
    int FPS = 144;
    public int currentFPS = 0;

    // SYSTEM
    TileManager tileManager = new TileManager(this);
    KeyHandler keyHandler = new KeyHandler(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Config config = new Config(this);
    SaveLoad saveLoad = new SaveLoad(this);
    public EnvironmentManager environmentManager = new EnvironmentManager(this);
    Thread gameThread;

    // PLAYER AND OBJECTS
    public Player player = new Player(this, keyHandler);
    public Entity[] obj = new Entity[10];
    ArrayList<Entity> entityList = new ArrayList<Entity>();

    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int inventoryState = 3;
    public final int chestState = 4;
    public final int transitionState = 5;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setupGame() {
        assetSetter.setObject();
        environmentManager.setup();

        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth2, screenHeight2, BufferedImage.TYPE_INT_ARGB);
        g2 =  (Graphics2D) tempScreen.getGraphics();

        if(fullScreen) {
            setFullScreen();
        }
    }
    public void setFullScreen(){
        // GET LOCAL SCREEN DEVICE
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        // GET FULL SCREEN WIDTH AND HEIGHT
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }
    public void startGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        double drawInterval = 1000000000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                drawToTempScreen();
                drawToScreen();
                delta--;
                drawCount++;
            }

            // SAVE FPS TO THE VARIABLE
            if (timer >= 1000000000) {
                currentFPS = drawCount;
                drawCount = 0;
                timer = timer % 1000000000;
            }
        }
    }
    public void update() {
        if(gameState == playState) {
            player.update();
        }
    }
    public void drawToTempScreen(){
        // TITLE SCREEN
        if (gameState == titleState || gameState == transitionState) {
            ui.draw(g2);
        }
        else {
            tileManager.draw(g2);

            // ADD PLAYER TO LIST
            entityList.add(player);

            // ADD OBJECTS TO LIST
            for (Entity value : obj) {
                if (value != null) {
                    entityList.add(value);
                }
            }

            // SORTING - Objects with collision=true are drawn after (on top of) entities with collision=false
            entityList.sort((e1, e2) -> {
                // Chests (collision=true) always on top
                if (e1.collision != e2.collision) {
                    return e1.collision ? 1 : -1;
                }

                // Among non-collision entities, player always draws last
                if (!e1.collision && !e2.collision) {
                    if (e1 instanceof Player) return 1;  // Player draws after
                    if (e2 instanceof Player) return -1; // Player draws after
                    return e1.worldY - e2.worldY; // Other objects sort by Y
                }

                return e1.worldY - e2.worldY;
            });

            // DRAW ENTITIES
            for (Entity entity : entityList) {
                entity.draw(g2);
            }

            // EMPTY LIST
            entityList.clear();

            // ENVIRONMENT
            environmentManager.draw(g2);

            // UI
            ui.draw(g2);
        }

        // DEBUG UI
        if (keyHandler.showDebug){
            g2.setColor(Color.white);
            g2.drawString("FPS: " + currentFPS, 10, 20);
            g2.drawString("WorldX: " + player.worldX, 10, 40);
            g2.drawString("WorldY: " + player.worldY, 10, 60);
            g2.drawString("Col: " + (player.worldX/tileSize), 10, 80);
            g2.drawString("Row: " + (player.worldY/tileSize), 10, 100);
        }
    }
    public void drawToScreen(){
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }
}
