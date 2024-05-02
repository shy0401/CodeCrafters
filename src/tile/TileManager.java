package tile;

import main.GamePanel;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    private Tile[] tiles;
    private int[][] mapData;
    private GamePanel gp;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tiles = new Tile[10]; // Assuming we have 10 different tiles
        mapData = new int[gp.maxWorldCol][gp.maxWorldRow]; // Adjusted from mapTileNum
        
        loadTileImages();
        try {
            loadMapData("/maps/world01.txt"); // Adjust the map data file path as necessary
        } catch (IOException e) {
            System.err.println("Error loading map data: " + e.getMessage());
            loadDefaultMap();
        }
    }

    private void loadTileImages() {
        try {
            tiles[0] = new Tile(loadImage("/tiles/grass00.png"), false);
            tiles[1] = new Tile(loadImage("/tiles/wall.png"), true);
            tiles[2] = new Tile(loadImage("/tiles/water00.png"), false); // Changed to water tile
            tiles[3] = new Tile(loadImage("/tiles/earth.png"), false); // Added earth tile
            tiles[4] = new Tile(loadImage("/tiles/grass01.png"), false); // Added sand tile
            tiles[5] = new Tile(loadImage("/tiles/tree.png"), true); // Added tree tile
            // More tiles can be added here
        } catch (Exception e) {
            System.err.println("Error loading tile images: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private BufferedImage loadImage(String path) throws IOException {
        return ImageIO.read(getClass().getResourceAsStream(path));
    }

    private void loadMapData(String path) throws IOException {
        InputStream is = getClass().getResourceAsStream(path);
        if (is == null) {
            throw new IOException("Map file not found at " + path);
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null && row < mapData.length) {
                String[] tokens = line.split("\\s+");
                for (int col = 0; col < tokens.length && col < mapData[row].length; col++) {
                    mapData[row][col] = Integer.parseInt(tokens[col]);
                }
                row++;
            }
        }
    }

    private void loadDefaultMap() {
        // Fill mapData with default values in case of error
        for (int i = 0; i < mapData.length; i++) {
            for (int j = 0; j < mapData[i].length; j++) {
                mapData[i][j] = 0; // Default to tile index 0
            }
        }
    }

    public void draw(Graphics2D g2) {
        int worldWidth = gp.maxWorldCol * gp.tileSize;
        int worldHeight = gp.maxWorldRow * gp.tileSize;
        int x = 0;
        int y = 0;

        for (int row = 0; row < gp.maxWorldRow; row++) {
            for (int col = 0; col < gp.maxWorldCol; col++) {
                int tileIndex = mapData[row][col];
                Tile tile = tiles[tileIndex];
                g2.drawImage(tile.image, x, y, gp.tileSize, gp.tileSize, null);
                x += gp.tileSize;
            }
            y += gp.tileSize;
            x = 0; // Reset x coordinate at the end of each row
        }
    }
}
