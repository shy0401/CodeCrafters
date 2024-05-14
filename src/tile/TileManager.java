package tile; // 패키지 타일;

import main.GamePanel; // 메인.GamePanel을 가져오기;
import javax.imageio.ImageIO; // javax.imageio.ImageIO를 가져오기;
import java.awt.Graphics2D; // java.awt.Graphics2D를 가져오기;
import java.awt.image.BufferedImage; // java.awt.image.BufferedImage를 가져오기;
import java.io.BufferedReader; // java.io.BufferedReader를 가져오기;
import java.io.IOException; // java.io.IOException을 가져오기;
import java.io.InputStream; // java.io.InputStream을 가져오기;
import java.io.InputStreamReader; // java.io.InputStreamReader을 가져오기;

public class TileManager {
    private Tile[] tiles; // 타일 배열
    private int[][] mapData;
    private GamePanel gp;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tiles = new Tile[10];
        mapData = new int[gp.maxWorldCol][gp.maxWorldRow];
        loadTileImages();
        try {
            loadMapData("/maps/world01.txt");
        } catch (IOException e) {
            System.err.println("Error loading map data: " + e.getMessage());
            loadDefaultMap();
        }
    }

    // 이미지 반환 메서드 추가
    public BufferedImage getTileImage(int index) {
        if (index < tiles.length && tiles[index] != null) {
            return tiles[index].image;
        } else {
            return null;
        }
    }

    private void loadTileImages() {
        try {
            tiles[0] = new Tile(loadImage("/tiles/grass00.png"), false);
            tiles[1] = new Tile(loadImage("/tiles/wall.png"), true);
            // 추가 타일 로드
        } catch (IOException e) {
            System.err.println("Error loading tile images: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private BufferedImage loadImage(String path) throws IOException {
        return ImageIO.read(getClass().getResourceAsStream(path));
    }


    private void loadMapData(String path) throws IOException { // private 맵 데이터를 불러오는 메소드;
        InputStream is = getClass().getResourceAsStream(path); // 지정된 경로에서 입력 스트림을 가져옴;
        if (is == null) {
            throw new IOException("Map file not found at " + path); // 입력 스트림이 null이면 예외 발생;
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) { // BufferedReader를 사용하여 파일을 읽음;
            String line; // 한 줄씩 읽기 위한 변수;
            int row = 0; // 행 인덱스 초기화;
            while ((line = br.readLine()) != null && row < mapData.length) { // 파일의 끝에 도달하거나 배열 크기를 초과할 때까지 반복;
                String[] tokens = line.split("\\s+"); // 공백을 기준으로 토큰 분리;
                for (int col = 0; col < tokens.length && col < mapData[row].length; col++) { // 열 인덱스를 반복하면서 각 토큰을 int로 변환하여 mapData에 저장;
                    mapData[row][col] = Integer.parseInt(tokens[col]);
                }
                row++; // 다음 행으로 이동;
            }
        }
    }

    private void loadDefaultMap() { // private 기본 맵 데이터를 불러오는 메소드;
        // mapData를 기본 값으로 채움;
        for (int i = 0; i < mapData.length; i++) {
            for (int j = 0; j < mapData[i].length; j++) {
                mapData[i][j] = 0; // 모든 값을 타일 인덱스 0으로 설정;
            }
        }
    }

    public void draw(Graphics2D g2) {
        for (int row = 0; row < gp.getMaxWorldRow(); row++) {
            for (int col = 0; col < gp.getMaxWorldCol(); col++) {
                int tileNum = mapData[row][col];

                // null 체크 추가
                if (tileNum < 0 || tileNum >= tiles.length || tiles[tileNum] == null) {
                    continue;  // 타일 정보가 없으면 다음 타일로 넘어감
                }

                BufferedImage tileImage = tiles[tileNum].image;
                if (tileImage != null) {
                    int worldX = col * gp.getTileSize();
                    int worldY = row * gp.getTileSize();
                    int screenX = worldX - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX();
                    int screenY = worldY - gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY();
                    g2.drawImage(tileImage, screenX, screenY, gp.getTileSize(), gp.getTileSize(), null);
                }
            }
        }
    }

} // 클래스 끝
