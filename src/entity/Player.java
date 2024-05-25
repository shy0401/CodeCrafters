package entity;

import main.GamePanel;
import main.KeyHandler;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    public int worldX;
    public int worldY;
    private int screenX, screenY;
    GamePanel gp;
    KeyHandler keyH;
    BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    BufferedImage currentImage;
    String direction;
    int spriteCounter = 0;
    int spriteNum = 1;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - gp.tileSize / 2;
        screenY = gp.screenHeight / 2 - gp.tileSize / 2;

        solidArea = new Rectangle(8, 16, 32, 32);
        direction = "down";
        setDefaultValues();
    }

    public void setDefaultValues() {
        // Set initial position to grid coordinates [9][11]
        int initialGridX = 10;
        int initialGridY = 8;
        worldX = initialGridX * gp.tileSize;
        worldY = initialGridY * gp.tileSize;
        speed = 4;
        direction = "down";
        currentImage = down1;
    }

    public void setPlayerImage(String imagePath, int character) throws IOException {
        SpriteManager sp = new SpriteManager(imagePath, character);

        up1 = sp.getImage("up1");
        up2 = sp.getImage("up2");
        down1 = sp.getImage("down1");
        down2 = sp.getImage("down2");
        left1 = sp.getImage("left1");
        left2 = sp.getImage("left2");
        right1 = sp.getImage("right1");
        right2 = sp.getImage("right2");
        
        currentImage = down1;  // Set the default image to down1
    }

    public void move(GamePanel gp) {
        boolean canMove = true;

        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) {
                direction = "up";
                canMove = gp.cChecker.canMove(worldX, worldY, speed, direction, solidArea);
                if (canMove) {
                    worldY -= speed;
                }
            } else if (keyH.downPressed) {
                direction = "down";
                canMove = gp.cChecker.canMove(worldX, worldY, speed, direction, solidArea);
                if (canMove) {
                    worldY += speed;
                }
            } else if (keyH.leftPressed) {
                direction = "left";
                canMove = gp.cChecker.canMove(worldX, worldY, speed, direction, solidArea);
                if (canMove) {
                    worldX -= speed;
                }
            } else if (keyH.rightPressed) {
                direction = "right";
                canMove = gp.cChecker.canMove(worldX, worldY, speed, direction, solidArea);
                if (canMove) {
                    worldX += speed;
                }
            }

            updateSprite();
        }
    }

    private void updateSprite() {
        spriteCounter++;
        if (spriteCounter > 12) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }

        switch (direction) {
            case "up":
                currentImage = (spriteNum == 1) ? up1 : up2;
                break;
            case "down":
                currentImage = (spriteNum == 1) ? down1 : down2;
                break;
            case "left":
                currentImage = (spriteNum == 1) ? left1 : left2;
                break;
            case "right":
                currentImage = (spriteNum == 1) ? right1 : right2;
                break;
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(currentImage, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }
}
