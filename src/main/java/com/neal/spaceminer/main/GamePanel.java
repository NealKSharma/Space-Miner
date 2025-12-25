package com.neal.spaceminer.main;

import com.neal.spaceminer.ai.PathFinder;
import com.neal.spaceminer.data.SaveLoad;
import com.neal.spaceminer.entity.Entity;
import com.neal.spaceminer.entity.NPC_Robot;
import com.neal.spaceminer.entity.Particle;
import com.neal.spaceminer.entity.Player;
import com.neal.spaceminer.environment.EnvironmentManager;
import com.neal.spaceminer.tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {
    // Screen Settings
    final int originalTileSize = 16; // 16x16 Tile
    final int scale = 4;

    public final int tileSize = originalTileSize * scale; // After Scaling becomes 64x64
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 1024px (64x16)
    public final int screenHeight = tileSize * maxScreenRow; // 768px (64x12)

    public final int maxWorldCol = 100;
    public final int maxWorldRow = 100;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;
    public final int maxMap = 10;
    public int currentMap = 0;

    // FULLSCREEN
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    Graphics2D g2;
    public boolean fullScreen = true;

    // FPS
    public int FPS = 265;
    public int currentFPS = 0;

    // SYSTEM
    public TileManager tileManager = new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Config config = new Config(this);
    SaveLoad saveLoad = new SaveLoad(this);
    public EnvironmentManager environmentManager = new EnvironmentManager(this);
    public EntityGenerator entityGenerator = new EntityGenerator(this);
    public PathFinder pathFinder = new PathFinder(this);
    public EventHandler eventHandler = new EventHandler(this);
    Thread gameThread;

    // PLAYER AND OBJECTS
    public Player player = new Player(this, keyHandler);
    public Entity bot;
    public ArrayList<ArrayList<Entity>> obj = new ArrayList<>();
    public ArrayList<ArrayList<Entity>> npc = new ArrayList<>();
    public ArrayList<Entity> particleList = new  ArrayList<>();
    public ArrayList<Entity> entityList = new ArrayList<>();

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
        for (int i = 0; i < maxMap; i++) {
            obj.add(new ArrayList<>());
            npc.add(new ArrayList<>());
        }

        assetSetter.setObject();
        assetSetter.setInteractiveTile();
        assetSetter.setNPC();
        environmentManager.setup();

        if(fullScreen) setFullScreen();

        // DRY RUN TO LOAD ASSETS
        gameState = playState;
        update();

        gameState = titleState;
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
                repaint();
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
        if(gameState != titleState && gameState != transitionState){
            environmentManager.update();
        }
        if(gameState == playState) {
            player.update();

            if(bot != null){
                bot.update();
            }

            for (Entity entity : npc.get(currentMap)) {
                if (entity != null) {
                    entity.update();
                }
            }

            for (int i = 0; i < particleList.size(); i++) {
                if(particleList.get(i) != null) {
                    if(particleList.get(i).alive){
                        particleList.get(i).update();
                    } else {
                        particleList.remove(i);
                    }
                }
            }
        }
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // CALCULATE SCALING
        double drawWidth = screenWidth2;
        double drawHeight = screenHeight2;
        double scaleX = drawWidth / screenWidth;
        double scaleY = drawHeight / screenHeight;

        g2.scale(scaleX, scaleY);

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        // TITLE SCREEN
        if (gameState == titleState) {
            ui.draw(g2);
        }
        else {
            tileManager.draw(g2);

            // ADD PLAYER TO LIST
            entityList.add(player);

            // ADD ROBOT
            if(bot != null){
                entityList.add(bot);
            }

            // ADD OBJECTS TO LIST
            for (Entity value : obj.get(currentMap)) {
                if (value != null) {
                    entityList.add(value);
                }
            }

            // NPC
            for(Entity value : npc.get(currentMap)) {
                if (value != null) {
                    entityList.add(value);
                }
            }

            // PARTICLES
            for (Entity particle: particleList){
                if(particle != null) {
                    entityList.add(particle);
                }
            }

            // SORTING
            entityList.sort(Comparator.comparingInt(e -> {
                if (e.isBreakable) {
                    // For breakable blocks: Sort using the TOP of the collision box.
                    return e.worldY + e.solidArea.y;
                } else {
                    // For everything else sort using the FEET (Bottom).
                    return e.worldY + e.solidArea.y + e.solidArea.height;
                }
            }));

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
}