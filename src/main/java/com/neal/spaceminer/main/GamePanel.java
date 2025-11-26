package com.neal.spaceminer.main;

import com.neal.spaceminer.data.SaveLoad;
import com.neal.spaceminer.entity.Entity;
import com.neal.spaceminer.entity.Player;
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

    int FPS = 144;

    // SYSTEM
    TileManager tileManager = new TileManager(this);
    KeyHandler keyHandler = new KeyHandler(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Config config = new Config(this);
    SaveLoad saveLoad = new SaveLoad(this);
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

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setupGame() {
        assetSetter.setObject();
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
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread.isAlive()) {

            update();
            drawToTempScreen();
            drawToScreen();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void update() {

        if(gameState == playState) {
            player.update();
        } else if (gameState == pauseState){

        } else if(gameState == inventoryState){

        } else if(gameState == chestState){

        }
    }
    public void drawToTempScreen(){
        long drawStartTime = 0;
        if(keyHandler.showDebug){
            drawStartTime = System.nanoTime();
        }

        // TITLE SCREEN
        if (gameState == titleState){
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

            ui.draw(g2);
        }

        if (keyHandler.showDebug){
            long drawEndTime = System.nanoTime();
            long passed = drawEndTime - drawStartTime;

            // Convert to microseconds for more consistent display
            double drawTimeMs = passed / 1000000.0;

            g2.setColor(Color.white);
            g2.drawString(String.format("FPS: %.0f", 1000.0 / drawTimeMs), 10, 20);
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
