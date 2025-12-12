package com.neal.spaceminer.data;

import com.neal.spaceminer.entity.Entity;
import com.neal.spaceminer.main.GamePanel;
import com.neal.spaceminer.object.OBJ_Chest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

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

            // PLAYER INVENTORY
            for(int i = 0; i < gamePanel.player.inventory.size(); i++){
                if(gamePanel.player.inventory.get(i) != null){
                    ds.itemNames.add(gamePanel.player.inventory.get(i).name);
                    ds.itemQuantity.add(gamePanel.player.inventory.get(i).itemAmount);
                    ds.itemSlot.add(i);
                }
            }

            // OBJECTS ON MAP
            ds.mapObjectNames = new String[gamePanel.obj.size()];
            ds.mapObjectWorldX = new int[gamePanel.obj.size()];
            ds.mapObjectWorldY = new int[gamePanel.obj.size()];

            for (int i = 0; i < gamePanel.obj.size(); i++) {
                ds.mapObjectNames[i] = gamePanel.obj.get(i).name;
                ds.mapObjectWorldX[i] = gamePanel.obj.get(i).worldX;
                ds.mapObjectWorldY[i] = gamePanel.obj.get(i).worldY;

                    // CHEST INVENTORIES
                if (gamePanel.obj.get(i).name.equals("Chest")) {
                    OBJ_Chest chest = (OBJ_Chest) gamePanel.obj.get(i);
                        ArrayList<String> chestItems = new ArrayList<>();
                        ArrayList<Integer> chestSlots = new ArrayList<>();
                        ArrayList<Integer> chestAmounts = new ArrayList<>();

                        for(int j = 0; j < chest.chestInv.size(); j++) {
                            if(chest.chestInv.get(j) != null) {
                                chestItems.add(chest.chestInv.get(j).name);
                                chestSlots.add(j);
                                chestAmounts.add(chest.chestInv.get(j).itemAmount);
                            }
                        }
                        ds.chestItemNames.put(i, chestItems);
                        ds.chestItemSlots.put(i, chestSlots);
                        ds.chestItemAmounts.put(i, chestAmounts);
                }
            }

            // WRITE THE DATASTORAGE OBJECT
            oos.writeObject(ds);

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
            gamePanel.obj.clear();
            for (int i = 0; i < ds.mapObjectNames.length; i++) {
                if(ds.mapObjectNames[i] != null){

                    Entity obj = gamePanel.entityGenerator.getObject(ds.mapObjectNames[i]);
                    obj.worldX = ds.mapObjectWorldX[i];
                    obj.worldY = ds.mapObjectWorldY[i];

                    // LOAD CHEST INVENTORY
                    if(ds.mapObjectNames[i].equals("Chest")) {
                        OBJ_Chest chest = (OBJ_Chest) obj;

                        // Clear chest inventory first
                        for(int j = 0; j < chest.chestInv.size(); j++) {
                            chest.chestInv.set(j, null);
                        }

                        // Load chest items if they exist
                        if(ds.chestItemNames.containsKey(i)) {
                            ArrayList<String> chestItems = ds.chestItemNames.get(i);
                            ArrayList<Integer> chestSlots = ds.chestItemSlots.get(i);
                            ArrayList<Integer> chestAmounts = ds.chestItemAmounts.get(i);

                            for(int j = 0; j < chestItems.size(); j++) {
                                int slot = chestSlots.get(j);
                                Entity item = gamePanel.entityGenerator.getObject(chestItems.get(j));
                                if(chestAmounts != null && j < chestAmounts.size()){
                                    item.itemAmount = chestAmounts.get(j);
                                }
                                chest.chestInv.set(slot, item);
                            }
                        }
                    }
                    gamePanel.obj.add(obj);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}