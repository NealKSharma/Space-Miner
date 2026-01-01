package com.neal.spaceminer.main;

import com.neal.spaceminer.entity.Entity;
import com.neal.spaceminer.object.OBJ_LumenCell;

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

        // RECIPE 1
        craftableItems.add(new OBJ_LumenCell(gamePanel));
        recipeNames.add(new String[]{ "Rock", "Chrono Filament", "Pulsarite", "Void Shard" });
        recipeAmounts.add(new int[]{ 5, 2, 2, 1 });

        // RECIPE 1
        craftableItems.add(new OBJ_LumenCell(gamePanel));
        recipeNames.add(new String[]{ "Rock", "Chrono Filament", "Pulsarite", "Void Shard" });
        recipeAmounts.add(new int[]{ 5, 2, 2, 1 });

        // RECIPE 1
        craftableItems.add(new OBJ_LumenCell(gamePanel));
        recipeNames.add(new String[]{ "Rock", "Chrono Filament", "Pulsarite", "Void Shard" });
        recipeAmounts.add(new int[]{ 5, 2, 2, 1 });

        // RECIPE 1
        craftableItems.add(new OBJ_LumenCell(gamePanel));
        recipeNames.add(new String[]{ "Rock", "Chrono Filament", "Pulsarite", "Void Shard" });
        recipeAmounts.add(new int[]{ 5, 2, 2, 1 });

        // RECIPE 1
        craftableItems.add(new OBJ_LumenCell(gamePanel));
        recipeNames.add(new String[]{ "Rock", "Chrono Filament", "Pulsarite", "Void Shard" });
        recipeAmounts.add(new int[]{ 5, 2, 2, 1 });

        // RECIPE 1
        craftableItems.add(new OBJ_LumenCell(gamePanel));
        recipeNames.add(new String[]{ "Rock", "Chrono Filament", "Pulsarite", "Void Shard" });
        recipeAmounts.add(new int[]{ 5, 2, 2, 1 });

        // RECIPE 1
        craftableItems.add(new OBJ_LumenCell(gamePanel));
        recipeNames.add(new String[]{ "Rock", "Chrono Filament", "Pulsarite", "Void Shard" });
        recipeAmounts.add(new int[]{ 5, 2, 2, 1 });

        // RECIPE 1
        craftableItems.add(new OBJ_LumenCell(gamePanel));
        recipeNames.add(new String[]{ "Rock", "Chrono Filament", "Pulsarite", "Void Shard" });
        recipeAmounts.add(new int[]{ 5, 2, 2, 1 });

        // RECIPE 1
        craftableItems.add(new OBJ_LumenCell(gamePanel));
        recipeNames.add(new String[]{ "Rock", "Chrono Filament", "Pulsarite", "Void Shard" });
        recipeAmounts.add(new int[]{ 5, 2, 2, 1 });

        // RECIPE 1
        craftableItems.add(new OBJ_LumenCell(gamePanel));
        recipeNames.add(new String[]{ "Rock", "Chrono Filament", "Pulsarite", "Void Shard" });
        recipeAmounts.add(new int[]{ 5, 2, 2, 1 });

        // RECIPE 1
        craftableItems.add(new OBJ_LumenCell(gamePanel));
        recipeNames.add(new String[]{ "Rock", "Chrono Filament", "Pulsarite", "Void Shard" });
        recipeAmounts.add(new int[]{ 5, 2, 2, 1 });
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
        if(slot == -1) return;

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
            case "Lumen Cell": return new OBJ_LumenCell(gamePanel);
            default: return null;
        }
    }
}
