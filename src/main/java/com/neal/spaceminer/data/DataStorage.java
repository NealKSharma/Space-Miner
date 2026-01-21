package com.neal.spaceminer.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class DataStorage implements Serializable {

    // PLAYER AND BOT
    int playerX, playerY, suiteIntegrity;
    int botX, botY, currentDialogue;
    int currentMap = 0;

    // NPC
    ArrayList<ArrayList<String>> npcNames = new ArrayList<>();
    ArrayList<ArrayList<Integer>> npcWorldX = new ArrayList<>();
    ArrayList<ArrayList<Integer>> npcWorldY = new ArrayList<>();

    // HOSTILE LOCATIONS
    ArrayList<ArrayList<String>> hostileNames = new ArrayList<>();
    ArrayList<ArrayList<Integer>> hostileWorldX = new ArrayList<>();
    ArrayList<ArrayList<Integer>> hostileWorldY = new ArrayList<>();
    ArrayList<ArrayList<Integer>> hostileLife = new ArrayList<>();

    // PLAYER INVENTORY
    ArrayList<String> itemNames = new ArrayList<>();
    ArrayList<Integer> itemSlot = new ArrayList<>();
    ArrayList<Integer> itemQuantity = new ArrayList<>();

    // OBJECTS ON MAP
    String[][] mapObjectNames;
    int[][] mapObjectWorldX;
    int[][] mapObjectWorldY;

    // CHEST INVENTORIES
    HashMap<Integer, HashMap<Integer, ArrayList<String>>> chestItemNames = new HashMap<>();
    HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> chestItemSlots = new HashMap<>();
    HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> chestItemAmounts = new HashMap<>();
}