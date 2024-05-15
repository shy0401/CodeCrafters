package main;

import entity.NPC1;
import object.SuperObject;
import main.GamePanel;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        int objectIndex = 0;
        gp.objects[objectIndex] = new SuperObject("Key");
        gp.objects[objectIndex].setWorldX(23 * gp.tileSize);
        gp.objects[objectIndex].setWorldY(7 * gp.tileSize);

        objectIndex++;
        gp.objects[objectIndex] = new SuperObject("Key");
        gp.objects[objectIndex].setWorldX(23 * gp.tileSize);
        gp.objects[objectIndex].setWorldY(40 * gp.tileSize);
    }

    public void setNPC() {
        int npcIndex = 0;
        gp.npcs[npcIndex] = new NPC1(gp);
        gp.npcs[npcIndex].setWorldX(10 * gp.tileSize);
        gp.npcs[npcIndex].setWorldY(5 * gp.tileSize);
        gp.npcs[npcIndex].setDirection("down");
    }
}
