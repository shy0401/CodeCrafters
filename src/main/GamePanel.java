package main;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import entity.Player;

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

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(keyH); // Listen for key presses

        player = new Player(this, keyH); // Initialize the player
        this.requestFocusInWindow(); // Ensure the panel is focused to receive key events
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); // Start the game thread
    }

    @Override
    public void run() {
        while (gameThread != null) {
            updateGame(); // Update game logic
            repaint(); // Redraw the game panel

            try {
                Thread.sleep(10); // Sleep to cap frame rate
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateGame() {
        player.move(); // Move the player based on key inputs
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        player.draw(g2); // Draw the player on the game panel
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
}
