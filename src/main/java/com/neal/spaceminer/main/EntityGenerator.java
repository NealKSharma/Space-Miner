package com.neal.spaceminer.main;

import com.neal.spaceminer.entity.Entity;
import com.neal.spaceminer.object.OBJ_Astronaut;
import com.neal.spaceminer.object.OBJ_Chest;
import com.neal.spaceminer.object.OBJ_LumenCell;
import com.neal.spaceminer.object.OBJ_Pickaxe;

public class EntityGenerator {

    GamePanel gamePanel;

    public EntityGenerator(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    public Entity getObject(String itemName){
        Entity obj = null;
        switch(itemName){
            case OBJ_Astronaut.objName: obj = new OBJ_Astronaut(gamePanel); break;
            case OBJ_Chest.objName: obj = new OBJ_Chest(gamePanel); break;
            case OBJ_LumenCell.objName: obj = new OBJ_LumenCell(gamePanel); break;
            case OBJ_Pickaxe.objName: obj = new OBJ_Pickaxe(gamePanel); break;
        }
        return obj;
    }
}