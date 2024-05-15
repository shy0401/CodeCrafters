package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
    public int x, y;
    public int speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public Rectangle solidArea;
    public boolean collisionOn = false;

    // Sprite animation counters
    public int spriteCounter = 0;
    public int spriteNum = 1;
	public int worldX;
	public int worldY;
    
    // Update and Draw methods should be overridden in subclasses
    public void update() {}
    public void draw(Graphics2D g2) {}
}
