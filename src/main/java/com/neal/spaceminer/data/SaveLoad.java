package com.neal.spaceminer.data;

import com.neal.spaceminer.entity.Entity;
import com.neal.spaceminer.main.GamePanel;
import com.neal.spaceminer.object.OBJ_Chest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class SaveLoad {

    GamePanel gamePanel;

    public SaveLoad(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    public void save() {
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("save.dat"));
            DataStorage ds = new DataStorage();

            // PLAYER LOCATION
            ds.playerX = gamePanel.player.worldX;
            ds.playerY = gamePanel.player.worldY;

            // BOT LOCATION
            ds.botX = gamePanel.bot.worldX;
            ds.botY = gamePanel.bot.worldY;

            // NPC LOCATIONS
            ds.NPCX = new int[gamePanel.maxMap][];
            ds.NPCY = new int[gamePanel.maxMap][];

            // HOSTILE LOCATIONS
            ds.hostileX = new int[gamePanel.maxMap][];
            ds.hostileY = new int[gamePanel.maxMap][];

            // CURRENT MAP
            ds.currentMap = gamePanel.currentMap;

            // NPC
            for(int i = 0; i < gamePanel.maxMap; i++){
                // Check if the NPC list for this map exists
                if(gamePanel.npc.get(i) == null) continue;
                int npcCount = gamePanel.npc.get(i).size();

                ds.NPCX[i] = new int[npcCount];
                ds.NPCY[i] = new int[npcCount];

                for(int j = 0; j < npcCount; j++){
                    if(gamePanel.npc.get(i).get(j) != null){
                        ds.NPCX[i][j] = gamePanel.npc.get(i).get(j).worldX;
                        ds.NPCY[i][j] = gamePanel.npc.get(i).get(j).worldY;
                    }
                }
            }

            // HOSTILE
            for(int i = 0; i < gamePanel.maxMap; i++){
                if(gamePanel.hostile.get(i) == null) continue;
                int hostileCount = gamePanel.hostile.get(i).size();

                ds.hostileX[i] = new int[hostileCount];
                ds.hostileY[i] = new int[hostileCount];

                for(int j = 0; j < hostileCount; j++){
                    if(gamePanel.hostile.get(i).get(j) != null){
                        ds.hostileX[i][j] = gamePanel.hostile.get(i).get(j).worldX;
                        ds.hostileY[i][j] = gamePanel.hostile.get(i).get(j).worldY;
                    }
                }
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
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("save.dat"));
            DataStorage ds = (DataStorage) ois.readObject();

            // PLAYER LOCATION
            gamePanel.player.worldX = ds.playerX;
            gamePanel.player.worldY = ds.playerY;

            // BOT LOCATION
            gamePanel.bot.worldX = ds.botX;
            gamePanel.bot.worldY = ds.botY;

            // NPC LOCATION
            if (ds.NPCX != null) {
                for(int i = 0; i < ds.NPCX.length; i++){
                    if(ds.NPCX[i] == null) continue;
                    for(int j = 0; j < ds.NPCX[i].length; j++){
                        if(j < gamePanel.npc.get(i).size() && gamePanel.npc.get(i).get(j) != null){
                            gamePanel.npc.get(i).get(j).worldX = ds.NPCX[i][j];
                            gamePanel.npc.get(i).get(j).worldY = ds.NPCY[i][j];
                        }
                    }
                }
            }

            // HOSTILE LOCATIONS
            if(ds.hostileX != null) {
                for(int i = 0; i < ds.hostileX.length; i++){
                    if(ds.hostileX[i] == null) continue;
                    for(int j = 0; j < ds.hostileX[i].length; j++){
                        if(j < gamePanel.hostile.get(i).size() && gamePanel.hostile.get(i).get(j) != null){
                            gamePanel.hostile.get(i).get(j).worldX = ds.hostileX[i][j];
                            gamePanel.hostile.get(i).get(j).worldY = ds.hostileY[i][j];
                        }
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