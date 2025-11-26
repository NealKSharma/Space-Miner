package com.neal.spaceminer.data;

import java.io.Serializable;
import java.util.ArrayList;

public class DataStorage implements Serializable {

    // PLAYER LOCATION
    int playerX;
    int playerY;

    // PLAYER INVENTORY
    ArrayList<String> itemNames = new ArrayList<String>();
    ArrayList<Integer> itemSlot = new ArrayList<>();

    // OBJECTS ON MAP
    String[] mapObjectNames;
    int[] mapObjectWorldX;
    int[] mapObjectWorldY;
}
