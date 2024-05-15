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
    public int[][] mapTileNum;
    public Tile[] tiles;
    private int[][] mapData;
    private GamePanel gp;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tiles = new Tile[10];
        initializeTiles();
        mapData = new int[gp.maxWorldRow][gp.maxWorldCol]; // 정확한 배열 크기 할당
        mapTileNum = new int[gp.maxWorldRow][gp.maxWorldCol]; // 초기화
        try {
            loadMapData("/maps/world01.txt");
        } catch (IOException e) {
            System.err.println("Error loading map data: " + e.getMessage());
            loadDefaultMap(); // Load default map as a fallback
        }
    }

    private void initializeTiles() {
        try {
            tiles[0] = new Tile(loadImage("/tiles/grass00.png"), false);
            tiles[1] = new Tile(loadImage("/tiles/wall.png"), true);
            tiles[2] = new Tile(loadImage("/tiles/wall.png"), true);
            tiles[3] = new Tile(loadImage("/tiles/floor01.png"), false);
            tiles[4] = new Tile(loadImage("/tiles/tree.png"), true);
            tiles[5] = new Tile(loadImage("/tiles/earth.png"), false);
        } catch (IOException e) {
            System.err.println("Error loading tile images: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private BufferedImage loadImage(String path) throws IOException {
        InputStream is = getClass().getResourceAsStream(path);
        if (is == null) {
            throw new IOException("Image file not found at " + path);
        }
        return ImageIO.read(is);
    }

    private void loadMapData(String path) throws IOException {
        InputStream is = getClass().getResourceAsStream(path);
        if (is == null) {
            throw new IOException("Map file not found at " + path);
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        int row = 0;
        while ((line = br.readLine()) != null && row < mapData.length) {
            String[] tokens = line.split("\\s+");
            for (int col = 0; col < tokens.length && col < mapData[row].length; col++) {
                mapData[row][col] = Integer.parseInt(tokens[col]);
            }
            row++;
        }
        br.close();
        is.close();

        generateMapTileNum(); // 데이터 로드 후 mapTileNum 생성
    }

    private void loadDefaultMap() {
        for (int i = 0; i < mapData.length; i++) {
            for (int j = 0; j < mapData[i].length; j++) {
                mapData[i][j] = 0;
            }
        }
        generateMapTileNum(); // 기본 맵 데이터 설정 후 mapTileNum 생성
    }

    private void generateMapTileNum() {
        for (int row = 0; row < mapData.length; row++) {
            for (int col = 0; col < mapData[row].length; col++) {
                mapTileNum[row][col] = mapData[row][col];
            }
        }
    }

    public void draw(Graphics2D g2, int cameraX, int cameraY) {
        if (mapTileNum == null || tiles == null) {
            System.err.println("mapTileNum or tiles not initialized!");
            return;
        }

        int playerX = gp.getPlayer().getWorldX();
        int playerY = gp.getPlayer().getWorldY();
        int tileSize = gp.getTileSize();

        for (int row = 0; row < gp.getMaxWorldRow(); row++) {
            for (int col = 0; col < gp.getMaxWorldCol(); col++) {
                int tileNum = mapTileNum[row][col];
                if (tileNum < 0 || tileNum >= tiles.length || tiles[tileNum] == null) {
                    continue; // Skip if tile info is missing
                }
                BufferedImage tileImage = tiles[tileNum].image;
                if (tileImage != null) {
                    int worldX = col * tileSize;
                    int worldY = row * tileSize;
                    int screenX = worldX - playerX + (gp.getScreenWidth() / 2 - tileSize / 2);
                    int screenY = worldY - playerY + (gp.getScreenHeight() / 2 - tileSize / 2);
                    g2.drawImage(tileImage, screenX, screenY, tileSize, tileSize, null);
                }
            }
        }
    }
}
