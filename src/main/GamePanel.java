package main;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

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
    public static final int originalTileSize = 16;
    public static final int scale = 3;
    public static final int tileSize = originalTileSize * scale;
    public static final int maxScreenCol = 16;
    public static final int maxScreenRow = 12;
    public static final int screenWidth = tileSize * maxScreenCol;
    public static final int screenHeight = tileSize * maxScreenRow;

    public static final int maxWorldCol = 50;
    public static final int maxWorldRow = 50;
    public static final int worldWidth = tileSize * maxWorldCol;
    public static final int worldHeight = tileSize * maxWorldRow;

    private int cameraX, cameraY;

    public CollisionChecker cChecker;
    TileManager tileM;
    public SuperObject[] objects;
    public NPC1[] npcs;

    public GamePanel() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        setFocusable(true);
        addKeyListener(keyH);

        // Initialize game components
        tileM = new TileManager(this);
        cChecker = new CollisionChecker(this);
        player = new Player(this, keyH);

        // Load player images
        try {
            player.setPlayerImage("player/Actor1.png", 1);  // Use the correct path
        } catch (IOException e) {
            e.printStackTrace();
        }

        startGameThread();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
        requestFocusInWindow();
    }

    @Override
    public void run() {
        while (gameThread != null) {
            updateGame();
            repaint();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateGame() {
        player.move(this);
        updateCamera(player.getWorldX(), player.getWorldY());
    }

    private void updateCamera(int playerX, int playerY) {
        int halfWidth = screenWidth / 2;
        int halfHeight = screenHeight / 2;

        cameraX = playerX - halfWidth;
        cameraY = playerY - halfHeight;

        cameraX = Math.max(0, Math.min(cameraX, worldWidth - screenWidth));
        cameraY = Math.max(0, Math.min(cameraY, worldHeight - screenHeight));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        long drawStart = 0;
        if (keyH.checkDrawTime) {
            drawStart = System.nanoTime();
        }

        tileM.draw(g2, cameraX, cameraY);
        player.draw(g2);

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
