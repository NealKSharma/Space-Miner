package com.neal.spaceminer.main;

import com.neal.spaceminer.entity.Entity;
import com.neal.spaceminer.object.OBJ_ChronoFilament;
import com.neal.spaceminer.object.OBJ_EMPNullifier;
import com.neal.spaceminer.object.OBJ_SignalJammer;

import java.util.ArrayList;

public class Crafting {

    GamePanel gamePanel;
    public ArrayList<Entity> craftableItems = new ArrayList<>();
    public ArrayList<String[]> recipeNames = new ArrayList<>();
    public ArrayList<int[]> recipeAmounts = new ArrayList<>();

    public Crafting(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        initialize();
    }

    public void initialize(){
        craftableItems.clear();
        recipeNames.clear();
        recipeAmounts.clear();

        // RECIPE 1: EMP NULLIFIER
        craftableItems.add(new OBJ_EMPNullifier(gamePanel));
        recipeNames.add(new String[]{ "Rock", "Chrono Filament", "Pulsarite", "Void Shard" });
        recipeAmounts.add(new int[]{ 2, 4, 4, 1 });

        // RECIPE 2: CHRONO SIGNAL JAMMER
        craftableItems.add(new OBJ_SignalJammer(gamePanel));
        recipeNames.add(new String[]{ "Scoria", "Chrono Filament", "Pulsarite", "Void Shard" });
        recipeAmounts.add(new int[]{ 3, 2, 2, 3 });
    }
    public boolean canCraft(int index){
        if(index >= craftableItems.size()) return false;

        String[] requiredNames = recipeNames.get(index);
        int[] requiredAmounts = recipeAmounts.get(index);

        for(int i = 0; i < requiredNames.length; i++){
            if(!hasIngredient(requiredNames[i], requiredAmounts[i])){
                return false;
            }
        }
        return true;
    }
    public void craft(int index){
        if(!canCraft(index)) return;
        int slot = gamePanel.player.getFirstEmptySlot();
        if(slot == -1) return; // INVENTORY FULL

        String[] requiredNames = recipeNames.get(index);
        int[] requiredAmounts = recipeAmounts.get(index);

        for(int i = 0; i < requiredNames.length; i++){
            gamePanel.player.removeItems(requiredNames[i], requiredAmounts[i]);
        }

        Entity result = craftableItems.get(index);

        if(gamePanel.player.searchInventory(result.name) != -1 && result.isStackable){
            gamePanel.player.inventory.get(gamePanel.player.searchInventory(result.name)).itemAmount++;
        } else {
            gamePanel.player.inventory.set(slot, generateNewItem(result.name));
        }
        gamePanel.player.itemBehaviour();
    }
    private boolean hasIngredient(String itemName, int amount){
        int index = gamePanel.player.searchInventory(itemName);
        if(index == -1) return false;
        return gamePanel.player.inventory.get(index).itemAmount >= amount;
    }
    private Entity generateNewItem(String itemName) {
        switch(itemName) {
            case "EMP Nullifier": return new OBJ_EMPNullifier(gamePanel);
            case "Chronal Signal Jammer": return new OBJ_SignalJammer(gamePanel);
            default: return null;
        }
    }
}