package main;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import entity.NPC1;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener {
    public Player player;
    private Thread gameThread;
    private KeyHandler keyH = new KeyHandler();

    // Constants for the game panel dimensions and tile sizes
    public static final int originalTileSize = 16; // Original tile size (before scaling)
    public static final int scale = 3; // Scale factor for the tile size

    public static final int tileSize = originalTileSize * scale; // Scaled tile size
    public static final int maxScreenCol = 16; // Maximum number of columns
    public static final int maxScreenRow = 12; // Maximum number of rows
    public static final int screenWidth = tileSize * maxScreenCol; // Total width of the game panel
    public static final int screenHeight = tileSize * maxScreenRow; // Total height of the game panel

    public static final int maxWorldCol = 50;
    public static final int maxWorldRow = 50;
    public static final int worldWidth = tileSize * maxWorldCol;
    public static final int worldHeight = tileSize * maxWorldRow;

    private int cameraX, cameraY; // 카메라의 x, y 위치를 저장할 변수

    
    public CollisionChecker cChecker = new CollisionChecker(this);
    TileManager tileM;
	public SuperObject[] objects;
	public NPC1[] npcs;
	public Object collisionChecker;

    public GamePanel() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        setFocusable(true);
        addKeyListener(keyH);
        
        //게임패널 순서 변경 0516 04:08 - 하윤
        tileM = new TileManager(this);
        player = new Player(this, keyH);
        cChecker = new CollisionChecker(this);
        startGameThread();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
        requestFocusInWindow(); // 게임 시작 시 캐릭터가 화면에 나타나도록 함
    }

    @Override
    public void run() {
        while (gameThread != null) {
            repaint();
            updateGame();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateGame() {
        player.move(); // Move the player based on key inputs
        updateCamera(player.getWorldX(), player.getWorldY());
    }

    private void updateCamera(int playerX, int playerY) {
        int halfWidth = screenWidth / 2;
        int halfHeight = screenHeight / 2;

        cameraX = playerX - halfWidth;
        cameraY = playerY - halfHeight;

        // 카메라가 맵의 경계를 넘지 않도록 조정
        cameraX = Math.max(0, Math.min(cameraX, worldWidth - screenWidth));
        cameraY = Math.max(0, Math.min(cameraY, worldHeight - screenHeight));
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        long drawStart = 0;
        if(keyH.checkDrawTime = true) {
        	drawStart = System.nanoTime();
        }
        
        // 카메라 위치를 기준으로 타일 매니저와 플레이어를 그림
        tileM.draw(g2, cameraX, cameraY);
        player.draw(g2, cameraX + (screenWidth / 2 - tileSize / 2), cameraY + (screenHeight / 2 - tileSize / 2));

        g2.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        keyH.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyH.keyReleased(e);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getMaxWorldRow() {
        return maxWorldRow;
    }

    public int getMaxWorldCol() {
        return maxWorldCol;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }
}
