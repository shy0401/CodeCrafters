package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class NPC1 extends Entity {
    private String characterType;
    private boolean isMoving;
    private int actionLockCounter;

    public NPC1(String type) {
        super();
        this.characterType = type;
        initCharacter();
    }

    public NPC1(GamePanel gp) {
		// TODO Auto-generated constructor stub
	}

	private void initCharacter() {
        // Load sprite images based on character type
        switch (characterType) {
            case "oldMan":
                up1 = getImage("up1");
                up2 = getImage("up2");
                down1 = getImage("down1");
                down2 = getImage("down2");
                left1 = getImage("left1");
                left2 = getImage("left2");
                right1 = getImage("right1");
                right2 = getImage("right2");
                break;
            // Add cases for other character types with different sprites
        }
        // Initial Direction and Speed
        direction = "down";
        speed = 1;
        solidArea = new Rectangle(0, 0, 48, 48); // Default collision box, can be adjusted per NPC type
        isMoving = false;
        actionLockCounter = 0;
    }

    private BufferedImage getImage(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
    public void update() {
        actionLockCounter++;
        if (actionLockCounter == 120) {  // Example: change direction every 2 seconds if moving
            changeDirection();
            actionLockCounter = 0;
        }
    }

    private void changeDirection() {
        int dir = (int) (Math.random() * 4) + 1;  // Random direction change
        switch (dir) {
            case 1:
                direction = "up";
                break;
            case 2:
                direction = "down";
                break;
            case 3:
                direction = "left";
                break;
            case 4:
                direction = "right";
                break;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = switch (direction) {
            case "up" -> spriteNum == 1 ? up1 : up2;
            case "down" -> spriteNum == 1 ? down1 : down2;
            case "left" -> spriteNum == 1 ? left1 : left2;
            case "right" -> spriteNum == 1 ? right1 : right2;
            default -> null;
        };
        g2.drawImage(image, x, y, null);
    }

	public void setWorldX(int i) {
		// TODO Auto-generated method stub
		
	}

	public void setWorldY(int i) {
		// TODO Auto-generated method stub
		
	}

	public void setDirection(String string) {
		// TODO Auto-generated method stub
		
	}
}
