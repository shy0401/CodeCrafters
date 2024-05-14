package main;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import entity.Player;
import tile.TileManager;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener {
    private Player player;
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
	public static final Object[] tiles = null;

    TileManager tileM = new TileManager(this);
    
    
    public GamePanel() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        setFocusable(true);
        addKeyListener(keyH);
        player = new Player(this, keyH);
        tileM = new TileManager(this);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); // Start the game thread
        requestFocusInWindow(); //게임시작시 캐릭터 안나타나는 문제 해결 -240503 ㅅㅎㅇ
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
        getPlayer().move(); // Move the player based on key inputs
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        tileM.draw(g2);
        getPlayer().draw(g2); // Draw the player on the game panel
        
        g2.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // This method is not used, but must be implemented because of the KeyListener interface
    }
    @Override
    public void keyPressed(KeyEvent e) {
        keyH.keyPressed(e); // Pass key pressed events to the key handler
    }
    @Override
    public void keyReleased(KeyEvent e) {
        keyH.keyReleased(e); // Pass key released events to the key handler
    }

	public Player getPlayer() {
		return player;
	}
    public void setPlayer(Player player) { this.player = player; }

    public int getTileSize() { return tileSize; }
    
	public BufferedImage getTileImage(int tileNum) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getMaxWorldRow() {
		// TODO Auto-generated method stub
		return maxWorldRow;
	}

	public int getMaxWorldCol() {
		// TODO Auto-generated method stub
		return maxWorldCol;
	}
}
