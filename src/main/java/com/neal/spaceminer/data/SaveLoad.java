package com.neal.spaceminer.data;

import com.neal.spaceminer.entity.Entity;
import com.neal.spaceminer.main.GamePanel;
import com.neal.spaceminer.object.OBJ_Chest;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class SaveLoad {

    GamePanel gamePanel;

    public SaveLoad(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    public ArrayList<String> getSaveFiles(){
        ArrayList<String> saveNames = new ArrayList<>();
        File folder = new File("saves");
        if(folder.exists() && folder.isDirectory()){
            File[] listOfFiles = folder.listFiles();
            if (listOfFiles != null) {
                for (File file : listOfFiles) {
                    if (file.isFile() && file.getName().endsWith(".dat")) {
                        String name = file.getName().replace(".dat", "");
                        saveNames.add(name);
                    }
                }
            }
        }
        return saveNames;
    }
    public void save() {
        try{
            Files.createDirectories(Paths.get("saves"));
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("saves/" + gamePanel.ui.fileName + ".dat"));
            DataStorage ds = new DataStorage();

            // PLAYER
            ds.playerX = gamePanel.player.worldX;
            ds.playerY = gamePanel.player.worldY;
            ds.suiteIntegrity = gamePanel.player.suiteIntegrity;

            // BOT
            ds.botX = gamePanel.bot.worldX;
            ds.botY = gamePanel.bot.worldY;
            ds.currentDialogue = gamePanel.bot.dialogueIndex;

            // CURRENT MAP
            ds.currentMap = gamePanel.currentMap;

            for(int i = 0; i < gamePanel.maxMap; i++){

                // NPCs
                ArrayList<String> nNames = new ArrayList<>();
                ArrayList<Integer> nX = new ArrayList<>();
                ArrayList<Integer> nY = new ArrayList<>();
                for(Entity e : gamePanel.npc.get(i)){
                    if(e != null){
                        nNames.add(e.name);
                        nX.add(e.worldX);
                        nY.add(e.worldY);
                    }
                }
                ds.npcNames.add(nNames);
                ds.npcWorldX.add(nX);
                ds.npcWorldY.add(nY);

                // HOSTILES
                ArrayList<String> hNames = new ArrayList<>();
                ArrayList<Integer> hX = new ArrayList<>();
                ArrayList<Integer> hY = new ArrayList<>();
                ArrayList<Integer> hL = new ArrayList<>();
                for(Entity e : gamePanel.hostile.get(i)){
                    if(e != null){
                        hNames.add(e.name);
                        hX.add(e.worldX);
                        hY.add(e.worldY);
                        hL.add(e.life);
                    }
                }
                ds.hostileNames.add(hNames);
                ds.hostileWorldX.add(hX);
                ds.hostileWorldY.add(hY);
                ds.hostileLife.add(hL);
            }

            // PLAYER INVENTORY
            for(int i = 0; i < gamePanel.player.inventory.size(); i++){
                if(gamePanel.player.inventory.get(i) != null){
                    ds.itemNames.add(gamePanel.player.inventory.get(i).name);
                    ds.itemQuantity.add(gamePanel.player.inventory.get(i).itemAmount);
                    ds.itemSlot.add(i);
                }
            }

            // OBJECTS ON MAP
            ds.mapObjectNames = new String[gamePanel.maxMap][];
            ds.mapObjectWorldX = new int[gamePanel.maxMap][];
            ds.mapObjectWorldY = new int[gamePanel.maxMap][];

            for (int i = 0; i < gamePanel.maxMap; i++){

                int mapSize = gamePanel.obj.get(i).size();
                ds.mapObjectNames[i] = new String[mapSize];
                ds.mapObjectWorldX[i] = new int[mapSize];
                ds.mapObjectWorldY[i] = new int[mapSize];

                for (int j = 0; j < gamePanel.obj.get(i).size(); j++){
                    ds.mapObjectNames[i][j] = gamePanel.obj.get(i).get(j).name;
                    ds.mapObjectWorldX[i][j] = gamePanel.obj.get(i).get(j).worldX;
                    ds.mapObjectWorldY[i][j] = gamePanel.obj.get(i).get(j).worldY;

                    // CHEST INVENTORIES
                    if (gamePanel.obj.get(i).get(j).name.equals("Chest")) {
                        OBJ_Chest chest = (OBJ_Chest) gamePanel.obj.get(i).get(j);
                        ArrayList<String> chestItems = new ArrayList<>();
                        ArrayList<Integer> chestSlots = new ArrayList<>();
                        ArrayList<Integer> chestAmounts = new ArrayList<>();

                        for(int k = 0; k < chest.chestInv.size(); k++) {
                            if(chest.chestInv.get(k) != null) {
                                chestItems.add(chest.chestInv.get(k).name);
                                chestSlots.add(k);
                                chestAmounts.add(chest.chestInv.get(k).itemAmount);
                            }
                        }
                        if (!ds.chestItemNames.containsKey(i)) {
                            ds.chestItemNames.put(i, new HashMap<>());
                            ds.chestItemSlots.put(i, new HashMap<>());
                            ds.chestItemAmounts.put(i, new HashMap<>());
                        }

                        ds.chestItemNames.get(i).put(j, chestItems);
                        ds.chestItemSlots.get(i).put(j, chestSlots);
                        ds.chestItemAmounts.get(i).put(j, chestAmounts);
                    }
                }
            }
            // WRITE THE DATA STORAGE OBJECT
            oos.writeObject(ds);
            oos.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void load() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("saves/" + gamePanel.ui.fileName + ".dat"));
            DataStorage ds = (DataStorage) ois.readObject();

            // PLAYER
            gamePanel.player.worldX = ds.playerX;
            gamePanel.player.worldY = ds.playerY;
            gamePanel.player.suiteIntegrity = ds.suiteIntegrity;

            // BOT
            gamePanel.bot.worldX = ds.botX;
            gamePanel.bot.worldY = ds.botY;
            gamePanel.bot.dialogueIndex = ds.currentDialogue;

            for (int i = 0; i < gamePanel.maxMap; i++) {
                gamePanel.npc.get(i).clear();
                gamePanel.hostile.get(i).clear();
            }

            // REBUILD NPCs
            for (int i = 0; i < ds.npcNames.size(); i++) {
                for (int j = 0; j < ds.npcNames.get(i).size(); j++) {
                    Entity npc = gamePanel.entityGenerator.getObject(ds.npcNames.get(i).get(j));
                    if(npc != null) {
                        npc.worldX = ds.npcWorldX.get(i).get(j);
                        npc.worldY = ds.npcWorldY.get(i).get(j);
                        gamePanel.npc.get(i).add(npc);
                    }
                }
            }

            // REBUILD HOSTILES
            for (int i = 0; i < ds.hostileNames.size(); i++) {
                for (int j = 0; j < ds.hostileNames.get(i).size(); j++) {
                    Entity monster = gamePanel.entityGenerator.getObject(ds.hostileNames.get(i).get(j));
                    if(monster != null) {
                        monster.worldX = ds.hostileWorldX.get(i).get(j);
                        monster.worldY = ds.hostileWorldY.get(i).get(j);
                        monster.life = ds.hostileLife.get(i).get(j);
                        gamePanel.hostile.get(i).add(monster);
                    }
                }
            }

            // CURRENT MAP
            gamePanel.currentMap = ds.currentMap;

            // PLAYER INVENTORY
            gamePanel.player.inventory.clear();
            for(int i = 0; i < gamePanel.player.maxInventorySize; i++){
                gamePanel.player.inventory.add(null);
            }
            for(int i = 0; i < ds.itemNames.size(); i++){
                int slot = ds.itemSlot.get(i);
                Entity item = gamePanel.entityGenerator.getObject(ds.itemNames.get(i));

                item.itemAmount = ds.itemQuantity.get(i);
                gamePanel.player.inventory.set(slot, item);
            }

            // OBJECTS ON MAP
            for (int i = 0; i < gamePanel.maxMap; i++) {
                gamePanel.obj.get(i).clear();
            }

            for(int i = 0; i < ds.mapObjectNames.length; i++){
                if (ds.mapObjectNames[i] == null) continue;
                for (int j = 0; j < ds.mapObjectNames[i].length; j++) {
                    if(ds.mapObjectNames[i][j] != null){
                        Entity obj = gamePanel.entityGenerator.getObject(ds.mapObjectNames[i][j]);
                        obj.worldX = ds.mapObjectWorldX[i][j];
                        obj.worldY = ds.mapObjectWorldY[i][j];

                        // LOAD CHEST INVENTORY
                        if(ds.mapObjectNames[i][j].equals("Chest")) {
                            OBJ_Chest chest = (OBJ_Chest) obj;

                            // Clear chest inventory first
                            for(int k = 0; k < chest.chestInv.size(); k++) {
                                chest.chestInv.set(k, null);
                            }

                            // Load chest items if they exist
                            if (ds.chestItemNames.containsKey(i) && ds.chestItemNames.get(i).containsKey(j)) {
                                ArrayList<String> chestItems = ds.chestItemNames.get(i).get(j);
                                ArrayList<Integer> chestSlots = ds.chestItemSlots.get(i).get(j);
                                ArrayList<Integer> chestAmounts = ds.chestItemAmounts.get(i).get(j);

                                for (int k = 0; k < chestItems.size(); k++) {
                                    int slot = chestSlots.get(k);
                                    Entity item = gamePanel.entityGenerator.getObject(chestItems.get(k));
                                    if (chestAmounts != null && k < chestAmounts.size()) {
                                        item.itemAmount = chestAmounts.get(k);
                                    }
                                    chest.chestInv.set(slot, item);
                                }
                            }
                        }
                        gamePanel.obj.get(i).add(obj);
                    }
                }
            }
            ois.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}