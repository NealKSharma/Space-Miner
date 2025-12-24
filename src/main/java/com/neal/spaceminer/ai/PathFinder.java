package com.neal.spaceminer.ai;

import com.neal.spaceminer.main.GamePanel;

import java.util.ArrayList;

public class PathFinder {
    GamePanel gamePanel;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;

    public PathFinder(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        instantiateNodes();
    }
    public void instantiateNodes(){
        node = new Node[gamePanel.maxWorldCol][gamePanel.maxWorldRow];
        int col =  0;
        int row = 0;

        while(col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow){
            node[col][row] = new Node(col, row);
            col++;
            if(col == gamePanel.maxWorldCol){
                row++;
                col = 0;
            }
        }
    }
    public void resetNodes(){
        int col = 0;
        int row = 0;
        while(col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow){
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;
            col++;
            if(col == gamePanel.maxWorldCol){
                row++;
                col = 0;
            }
        }
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }
    public void setNodes(int startCol, int startRow, int goalCol, int goalRow){
        resetNodes();

        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);

        int col = 0;
        int row = 0;

        while(col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow){
            // CHECK SOLID TILES
            int tileNum = gamePanel.tileManager.mapTileNum[col][row];
            if(gamePanel.tileManager.tile[tileNum].collision){
                node[col][row].solid = true;
            }

            // SET COST
            getCost(node[col][row]);
            col++;
            if(col == gamePanel.maxWorldCol){
                row++;
                col = 0;
            }
        }

        // CHECK OBJECTS
        for (int i = 0; i < gamePanel.obj.size(); i++) {
            if (gamePanel.obj.get(i) != null && gamePanel.obj.get(i).collision) {
                int objLeftX = gamePanel.obj.get(i).worldX + gamePanel.obj.get(i).solidArea.x;
                int objRightX = gamePanel.obj.get(i).worldX + gamePanel.obj.get(i).solidArea.x + gamePanel.obj.get(i).solidArea.width;
                int objTopY = gamePanel.obj.get(i).worldY + gamePanel.obj.get(i).solidArea.y;
                int objBottomY = gamePanel.obj.get(i).worldY + gamePanel.obj.get(i).solidArea.y + gamePanel.obj.get(i).solidArea.height;

                int objStartCol = objLeftX / gamePanel.tileSize;
                int objEndCol = (objRightX - 1) / gamePanel.tileSize;
                int objStartRow = objTopY / gamePanel.tileSize;
                int objEndRow = (objBottomY - 1) / gamePanel.tileSize;

                for(int c = objStartCol; c <= objEndCol; c++) {
                    for(int r = objStartRow; r <= objEndRow; r++) {
                        if(c >= 0 && c < gamePanel.maxWorldCol && r >= 0 && r < gamePanel.maxWorldRow) {
                            node[c][r].solid = true;
                        }
                    }
                }
            }
        }
        if(node[startCol][startRow] != null)
            node[startCol][startRow].solid = false;

        if(node[goalCol][goalRow] != null)
            node[goalCol][goalRow].solid = false;
    }
    public void getCost(Node node){
        // G Cost
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        // H Cost
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        // F Cost
        node.fCost = node.gCost + node.hCost;
    }
    public boolean search(){
        while(!goalReached && step < 5000){
            int col = currentNode.col;
            int row = currentNode.row;

            // CHECK THE CURRENT NODE
            currentNode.checked = true;
            openList.remove(currentNode);

            // OPEN THE UP NODE
            if(row - 1 >= 0){
                openNode(node[col][row-1]);
            }

            // OPEN THE LEFT NODE
            if(col - 1 >= 0){
                openNode(node[col-1][row]);
            }

            // OPEN THE RIGHT NODE
            if(row + 1 < gamePanel.maxWorldRow){
                openNode(node[col][row+1]);
            }

            // OPEN THE DOWN NODE
            if(col + 1 < gamePanel.maxWorldCol){
                openNode(node[col+1][row]);
            }

            // FIND THE BEST NODE
            int bestNodeIndex = 0;
            int bestNodeFCost = 999;

            for(int i = 0; i < openList.size(); i++){
                // CHECK IF THIS NODE'S F COST IS BETTER
                if(openList.get(i).fCost < bestNodeFCost){
                    bestNodeIndex = i;
                    bestNodeFCost = openList.get(i).fCost;
                }
                // IF F COST IS EQUAL CHECK THE G COST
                else if (openList.get(i).fCost == bestNodeFCost){
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost){
                        bestNodeIndex = i;
                    }
                }
            }

            if(openList.isEmpty()) break;

            // AFTER THE LOOP openList[bestNodeIndex] IS THE NEXT STEP
            currentNode = openList.get(bestNodeIndex);

            if(currentNode == goalNode){
                goalReached = true;
                trackThePath();
            }
            step++;
        }
        return goalReached;
    }
    public void openNode(Node node){
        if(!node.open && !node.checked && !node.solid){
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }
    public void trackThePath(){
        Node current = goalNode;
        while(current != startNode){
            pathList.addFirst(current);
            current = current.parent;
        }
    }
}
