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
            case OBJ_Rock.objName: obj = new OBJ_Rock(gamePanel); break;
            case OBJ_ChronoFilament.objName: obj = new OBJ_ChronoFilament(gamePanel); break;
            case OBJ_Pulsarite.objName: obj = new OBJ_Pulsarite(gamePanel); break;
            case OBJ_Scoria.objName: obj = new OBJ_Scoria(gamePanel); break;
            case OBJ_VoidShard.objName: obj = new OBJ_VoidShard(gamePanel); break;
            case OBJ_Habitat.objName: obj = new OBJ_Habitat(gamePanel); break;
            case OBJ_CraftingStation.objName: obj = new OBJ_CraftingStation(gamePanel); break;
            case OBJ_EMPNullifier.objName: obj = new OBJ_EMPNullifier(gamePanel); break;
            case OBJ_SignalJammer.objName: obj = new OBJ_SignalJammer(gamePanel); break;
            case OBJ_PlasmaRipper.objName: obj = new OBJ_PlasmaRipper(gamePanel); break;
            case OBJ_DataCore.objName: obj = new OBJ_DataCore(gamePanel); break;
            case OBJ_PowerCell.objName: obj = new OBJ_PowerCell(gamePanel); break;
            case OBJ_TechScrap.objName: obj = new OBJ_TechScrap(gamePanel); break;
            case OBJ_VirusCore.objName: obj = new OBJ_VirusCore(gamePanel); break;
            case OBJ_VirusResidue.objName: obj = new OBJ_VirusResidue(gamePanel); break;
            case OBJ_VirusSlurry.objName: obj = new OBJ_VirusSlurry(gamePanel); break;

            case IT_Rock.objName: obj = new IT_Rock(gamePanel); break;
            case IT_Chrono.objName: obj = new IT_Chrono(gamePanel); break;
            case IT_Pulsarite.objName: obj = new IT_Pulsarite(gamePanel); break;
            case IT_Scoria.objName: obj = new IT_Scoria(gamePanel); break;
            case IT_Void.objName: obj = new IT_Void(gamePanel); break;
        }
        return obj;
    }
}