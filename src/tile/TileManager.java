package tile;

import main.GamePanel;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    private Tile[] tiles;
    private int[][] mapData;

    public TileManager() {
        tiles = new Tile[10]; // Assuming we have 10 different tiles
        loadTileImages();
        loadMapData("/res/maps/world01.txt"); // Adjust the map data file path as necessary
    }

    private void loadTileImages() {
        try {
            tiles[0] = new Tile(ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass00.png")), false);
            tiles[1] = new Tile(ImageIO.read(getClass().getResourceAsStream("/res/tiles/wall.png")), true);
            tiles[2] = new Tile(ImageIO.read(getClass().getResourceAsStream("/res/tiles/road00.png")), false);
            tiles[3] = new Tile(ImageIO.read(getClass().getResourceAsStream("/res/tiles/road10.png")), false);
            tiles[4] = new Tile(ImageIO.read(getClass().getResourceAsStream("/res/tiles/water.png")), true);
            // Add more tiles as needed
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadMapData(String path) {
        try {
            InputStream is = getClass().getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            mapData = new int[GamePanel.maxScreenRow][GamePanel.maxScreenCol];
            for (int row = 0; row < GamePanel.maxScreenRow; row++) {
                String line = br.readLine();
                String[] numbers = line.split(" ");
                for (int col = 0; col < GamePanel.maxScreenCol; col++) {
                    mapData[row][col] = Integer.parseInt(numbers[col]);
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        for (int row = 0; row < GamePanel.maxScreenRow; row++) {
            for (int col = 0; col < GamePanel.maxScreenCol; col++) {
                int tileIndex = mapData[row][col];
                Tile tile = tiles[tileIndex];
                g2.drawImage(tile.image, col * GamePanel.tileSize, row * GamePanel.tileSize, GamePanel.tileSize, GamePanel.tileSize, null);
            }
        }
    }
}
