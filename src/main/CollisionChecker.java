package main;

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
            int entityRightWorldX = entity.worldX + entity.solidArea.width;
            int entityTopWorldY = entity.worldY + entity.solidArea.y;
            int entityBottomWorldY = entity.worldY + entity.solidArea.height;

            // 수정된 계산 로직 및 충돌 검사
            checkTileCollision(entity, entityLeftWorldX, entityTopWorldY, "up");
            checkTileCollision(entity, entityLeftWorldX, entityBottomWorldY, "down");
            checkTileCollision(entity, entityLeftWorldX, entityTopWorldY, "left");
            checkTileCollision(entity, entityRightWorldX, entityTopWorldY, "right");
        }

        public void checkTileCollision(Entity entity, int x, int y, String direction) {
            int col = x / gp.tileSize;
            int row = y / gp.tileSize;

            if (row < 0 || col < 0 || row >= gp.tileM.mapTileNum.length || col >= gp.tileM.mapTileNum[0].length) {
                return;
            }

            int tileNum = gp.tileM.mapTileNum[row][col];
            if (tileNum < gp.tileM.tiles.length && gp.tileM.tiles[tileNum] != null && gp.tileM.tiles[tileNum].collision) {
                entity.collisionOn = true;
            }
        }
        public boolean canMove(int x, int y, int speed, String direction) {
            int nextX = x;
            int nextY = y;
            
            switch (direction) {
                case "up": nextY -= speed; break;
                case "down": nextY += speed; break;
                case "left": nextX -= speed; break;
                case "right": nextX += speed; break;
            }
            
            int col = nextX / gp.tileSize;
            int row = nextY / gp.tileSize;

            // Check for boundary conditions to prevent ArrayIndexOutOfBoundsException
            if (row < 0 || col < 0 || row >= gp.tileM.mapTileNum.length || col >= gp.tileM.mapTileNum[0].length) {
                return false;
            }

            int tileNum = gp.tileM.mapTileNum[row][col];
            if (tileNum < gp.tileM.tiles.length && gp.tileM.tiles[tileNum] != null && gp.tileM.tiles[tileNum].collision) {
                return false; // There is a collision, cannot move
            }
            
            return true; // No collision detected, can move
        }
}
