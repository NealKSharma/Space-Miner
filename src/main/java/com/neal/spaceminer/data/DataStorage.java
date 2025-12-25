package com.neal.spaceminer.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class DataStorage implements Serializable {
    // PLAYER LOCATION
    int playerX;
    int playerY;

    // BOT LOCATION
    int botX;
    int botY;

    // NPC LOCATIONS
    int[][] NPCX;
    int[][] NPCY;

    // PLAYER INVENTORY
    ArrayList<String> itemNames = new ArrayList<String>();
    ArrayList<Integer> itemSlot = new ArrayList<>();
    ArrayList<Integer> itemQuantity = new ArrayList<>();

    // CURRENT MAP
    int currentMap = 0;

    // OBJECTS ON MAP
    String[][] mapObjectNames;
    int[][] mapObjectWorldX;
    int[][] mapObjectWorldY;

    // CHEST INVENTORIES
    HashMap<Integer, HashMap<Integer, ArrayList<String>>> chestItemNames = new HashMap<>();
    HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> chestItemSlots = new HashMap<>();
    HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> chestItemAmounts = new HashMap<>();
}
