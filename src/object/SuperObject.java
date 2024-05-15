package object;

public class SuperObject {
    public String name;
    public int worldX, worldY;
    public boolean collision = false;

    public SuperObject(String name) {
        this.name = name;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public int getWorldX() {
        return worldX;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public int getWorldY() {
        return worldY;
    }

    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }
}
