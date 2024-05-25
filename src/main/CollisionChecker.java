package main;

import java.awt.Rectangle;
import entity.Entity;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        if (entity.direction == null || gp.tileM.mapTileNum == null) {
            System.out.println("Direction or mapTileNum is not set.");
            return;
        }

        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityTopRow][entityLeftCol];
                tileNum2 = gp.tileM.mapTileNum[entityTopRow][entityRightCol];
                if (gp.tileM.tiles[tileNum1].collision || gp.tileM.tiles[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityBottomRow][entityLeftCol];
                tileNum2 = gp.tileM.mapTileNum[entityBottomRow][entityRightCol];
                if (gp.tileM.tiles[tileNum1].collision || gp.tileM.tiles[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityTopRow][entityLeftCol];
                tileNum2 = gp.tileM.mapTileNum[entityBottomRow][entityLeftCol];
                if (gp.tileM.tiles[tileNum1].collision || gp.tileM.tiles[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityTopRow][entityRightCol];
                tileNum2 = gp.tileM.mapTileNum[entityBottomRow][entityRightCol];
                if (gp.tileM.tiles[tileNum1].collision || gp.tileM.tiles[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
        }
    }

    public boolean canMove(int worldX, int worldY, int speed, String direction, Rectangle solidArea) {
        int nextX = worldX;
        int nextY = worldY;
        Rectangle nextArea = new Rectangle(solidArea);
        nextArea.x += worldX;
        nextArea.y += worldY;

        switch (direction) {
            case "up":
                nextArea.y -= speed;
                break;
            case "down":
                nextArea.y += speed;
                break;
            case "left":
                nextArea.x -= speed;
                break;
            case "right":
                nextArea.x += speed;
                break;
        }

        int leftCol = nextArea.x / gp.tileSize;
        int rightCol = (nextArea.x + nextArea.width) / gp.tileSize;
        int topRow = nextArea.y / gp.tileSize;
        int bottomRow = (nextArea.y + nextArea.height) / gp.tileSize;

        if (leftCol < 0 || rightCol >= gp.maxWorldCol || topRow < 0 || bottomRow >= gp.maxWorldRow) {
            return false;
        }

        for (int col = leftCol; col <= rightCol; col++) {
            for (int row = topRow; row <= bottomRow; row++) {
                int tileNum = gp.tileM.mapTileNum[row][col];
                if (tileNum < gp.tileM.tiles.length && gp.tileM.tiles[tileNum] != null && gp.tileM.tiles[tileNum].collision) {
                    return false;
                }
            }
        }

        return true;
    }
}
