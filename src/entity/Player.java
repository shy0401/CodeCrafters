package entity;

import main.GamePanel;
import main.KeyHandler;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Player extends Entity {
    private int worldX, worldY; // 플레이어의 월드 좌표
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
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 100;  // 초기 x 위치
        y = 100;  // 초기 y 위치
        speed = 4;
        direction = "down";
        currentImage = down1;  // 초기 이미지 설정
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
        if (keyH.upPressed) {
            y -= speed;
            direction = "up";
            updateSprite();
        } else if (keyH.downPressed) {
            y += speed;
            direction = "down";
            updateSprite();
        } else if (keyH.leftPressed) {
            x -= speed;
            direction = "left";
            updateSprite();
        } else if (keyH.rightPressed) {
            x += speed;
            direction = "right";
            updateSprite();
        }
    }

    private void updateSprite() {
        spriteCounter++;
        if (spriteCounter > 12) {
            spriteNum = spriteNum == 1 ? 2 : 1;
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
    
    public void draw(Graphics2D g2) {
        g2.drawImage(currentImage, x, y, GamePanel.tileSize, GamePanel.tileSize, null);
    }

    // Update getX and getY to return the player's current position
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
