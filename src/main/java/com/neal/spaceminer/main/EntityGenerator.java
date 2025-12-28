package com.neal.spaceminer.main;

import com.neal.spaceminer.entity.Entity;
import com.neal.spaceminer.object.*;
import com.neal.spaceminer.tiles_interactive.*;

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
            case OBJ_ShipBack.objName: obj = new OBJ_ShipBack(gamePanel); break;
            case OBJ_ShipFront.objName: obj = new OBJ_ShipFront(gamePanel); break;
            case OBJ_ShipMiddle.objName: obj = new OBJ_ShipMiddle(gamePanel); break;
            case OBJ_SuitGenerator.objName: obj = new OBJ_SuitGenerator(gamePanel); break;
            case OBJ_Teleporter.objName: obj = new OBJ_Teleporter(gamePanel); break;
            case IT_Rock.objName: obj = new IT_Rock(gamePanel); break;
            case IT_Chrono.objName: obj = new IT_Chrono(gamePanel); break;
            case IT_Pulsarite.objName: obj = new IT_Pulsarite(gamePanel); break;
            case IT_Scoria.objName: obj = new IT_Scoria(gamePanel); break;
            case IT_Void.objName: obj = new IT_Void(gamePanel); break;
        }
        return obj;
    }
}