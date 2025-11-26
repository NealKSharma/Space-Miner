package com.neal.spaceminer.data;

import com.neal.spaceminer.entity.Entity;
import com.neal.spaceminer.main.GamePanel;
import com.neal.spaceminer.object.OBJ_Astronaut;
import com.neal.spaceminer.object.OBJ_Chest;
import com.neal.spaceminer.object.OBJ_Pickaxe;

import java.io.*;
import java.util.ArrayList;

public class SaveLoad {

    GamePanel gamePanel;

    public SaveLoad(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    public Entity getObject(String name){
        Entity obj = null;

        switch(name){
            case "Pickaxe": obj = new OBJ_Pickaxe(gamePanel); break;
            case "Astronaut": obj = new OBJ_Astronaut(gamePanel); break;
            case "Chest": obj = new OBJ_Chest(gamePanel); break;
        }

        return obj;
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
                    ds.itemSlot.add(i);
                }
            }

            // OBJECTS ON MAP
            ds.mapObjectNames = new String[gamePanel.obj.length];
            ds.mapObjectWorldX = new int[gamePanel.obj.length];
            ds.mapObjectWorldY = new int[gamePanel.obj.length];

            for(int i = 0; i < gamePanel.obj.length; i++){
                if (gamePanel.obj[i] != null) {
                    ds.mapObjectNames[i] = gamePanel.obj[i].name;
                    ds.mapObjectWorldX[i] = gamePanel.obj[i].worldX;
                    ds.mapObjectWorldY[i] = gamePanel.obj[i].worldY;

                    // CHEST INVENTORIES
                    if(gamePanel.obj[i].name.equals("Chest")){
                        OBJ_Chest chest = (OBJ_Chest) gamePanel.obj[i];
                        ArrayList<String> chestItems = new ArrayList<>();
                        ArrayList<Integer> chestSlots = new ArrayList<>();

                        for(int j = 0; j < chest.chestInv.size(); j++) {
                            if(chest.chestInv.get(j) != null) {
                                chestItems.add(chest.chestInv.get(j).name);
                                chestSlots.add(j);
                            }
                        }

                        ds.chestItemNames.put(i, chestItems);
                        ds.chestItemSlots.put(i, chestSlots);
                    }
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
            for(int i = 0; i < gamePanel.player.maxInventorySize; i++){
                gamePanel.player.inventory.set(i, null);
            }
            for(int i = 0; i < ds.itemNames.size(); i++){
                gamePanel.player.inventory.set(ds.itemSlot.get(i), getObject(ds.itemNames.get(i)));
            }

            // OBJECTS ON MAP
            for (int i = 0; i < gamePanel.obj.length; i++){
                if(ds.mapObjectNames[i] != null){
                    gamePanel.obj[i] = getObject(ds.mapObjectNames[i]);
                    gamePanel.obj[i].worldX = ds.mapObjectWorldX[i];
                    gamePanel.obj[i].worldY = ds.mapObjectWorldY[i];

                    // LOAD CHEST INVENTORY
                    if(ds.mapObjectNames[i].equals("Chest")) {
                        OBJ_Chest chest = (OBJ_Chest) gamePanel.obj[i];

                        // Clear chest inventory first
                        for(int j = 0; j < chest.chestInv.size(); j++) {
                            chest.chestInv.set(j, null);
                        }

                        // Load chest items if they exist
                        if(ds.chestItemNames.containsKey(i)) {
                            ArrayList<String> chestItems = ds.chestItemNames.get(i);
                            ArrayList<Integer> chestSlots = ds.chestItemSlots.get(i);

                            for(int j = 0; j < chestItems.size(); j++) {
                                int slot = chestSlots.get(j);
                                Entity item = getObject(chestItems.get(j));
                                chest.chestInv.set(slot, item);
                            }
                        }
                    }
                } else {
                    gamePanel.obj[i] = null;
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
