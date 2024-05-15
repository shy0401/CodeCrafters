package entity;

import main.GamePanel;
import main.KeyHandler;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Player extends Entity {
    public int worldX; // 플레이어의 월드 좌표
    public int worldY;
    private int screenX, screenY; // 플레이어의 스크린 좌표
    GamePanel gp;
    KeyHandler keyH;
    BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    BufferedImage currentImage;  // 현재 이미지를 저장할 변수
    String direction;
    int spriteCounter = 0;
    int spriteNum = 1;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        
        screenX = gp.screenWidth / 2 - gp.tileSize / 2; // 스크린 상 중앙 위치
        screenY = gp.screenHeight / 2 - gp.tileSize / 2;
        
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;
        
        direction = "down"; //default 방향을 아래로
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.worldWidth / 2 - gp.tileSize / 2;  // 맵 중앙에서 시작
        worldY = gp.worldHeight / 2 - gp.tileSize / 2;
        speed = 4;
        direction = "down";
        getPlayerImage();
        currentImage = down1;
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void move() {
        int newWorldX = worldX;
        int newWorldY = worldY;
        boolean moved = false; // 움직임이 있는지 확인하는 플래그

        if (keyH.upPressed) {
            newWorldY -= speed;
            direction = "up";
            moved = true;
        }
        if (keyH.downPressed) {
            newWorldY += speed;
            direction = "down";
            moved = true;
        }
        if (keyH.leftPressed) {
            newWorldX -= speed;
            direction = "left";
            moved = true;
        }
        if (keyH.rightPressed) {
            newWorldX += speed;
            direction = "right";
            moved = true;
        }
       
        
        // 경계 검사
        if (newWorldX < 0 || newWorldX > gp.worldWidth - gp.tileSize) {
            newWorldX = worldX;
        }
        if (newWorldY < 0 || newWorldY > gp.worldHeight - gp.tileSize) {
            newWorldY = worldY;
        }

        // 실제 위치가 변경되었다면 업데이트
        if (newWorldX != worldX || newWorldY != worldY) {
            worldX = newWorldX;
            worldY = newWorldY;
            if (moved) {
                updateSprite(); // 움직임이 있을 때만 스프라이트 업데이트
            }
        }
    }


    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }
    
    // 좌표를 기반으로 이미지를 업데이트합니다.
    private void updateSprite() {
        
        collisionOn = false;
        gp.cChecker.checkTile(this);
        
        if(collisionOn == false) {
        	
        	switch(direction) {
        	case "up": worldY -= speed; break;
        	case "down": worldY += speed; break;
        	case "left": worldX -= speed; break;
        	case "right": worldX += speed; break;
        	}
        }
        spriteCounter++;
        
        
        if (spriteCounter > 12 && spriteNum == 1) {
            spriteNum = 2;
            spriteCounter = 0;
        }
        switch(direction) {
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

    public void draw(Graphics2D g2, int cameraX, int cameraY) {
        // 화면에 이미지를 그립니다.
        g2.drawImage(currentImage, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }

    // Update getX and getY to return the player's current position
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
