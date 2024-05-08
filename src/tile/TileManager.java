package tile; // 패키지 타일;

import main.GamePanel; // 메인.GamePanel을 가져오기;
import javax.imageio.ImageIO; // javax.imageio.ImageIO를 가져오기;
import java.awt.Graphics2D; // java.awt.Graphics2D를 가져오기;
import java.awt.image.BufferedImage; // java.awt.image.BufferedImage를 가져오기;
import java.io.BufferedReader; // java.io.BufferedReader를 가져오기;
import java.io.IOException; // java.io.IOException을 가져오기;
import java.io.InputStream; // java.io.InputStream을 가져오기;
import java.io.InputStreamReader; // java.io.InputStreamReader을 가져오기;

public class TileManager { // 공개 클래스 타일매니저 {
    private Tile[] tiles; // private 타일 배열 tiles;
    private int[][] mapData; // private int 이차원 배열 mapData;
    private GamePanel gp; // private GamePanel gp;

    public TileManager(GamePanel gp) { // 공개 TileManager 생성자(GamePanel gp) {
        this.gp = gp; // this.gp = gp;
        tiles = new Tile[10]; // 타일 배열을 10으로 초기화;
        mapData = new int[gp.maxWorldCol][gp.maxWorldRow]; // mapData를 gp.maxWorldCol, gp.maxWorldRow 크기의 배열로 초기화;
        
        loadTileImages(); // 타일 이미지를 불러오는 메소드 호출;
        try {
            loadMapData("/maps/world01.txt"); // "/maps/world01.txt"에서 맵 데이터를 불러오려고 시도;
        } catch (IOException e) {
            System.err.println("Error loading map data: " + e.getMessage()); // 맵 데이터 로딩 오류 메시지 출력;
            loadDefaultMap(); // 기본 맵 데이터를 불러오는 메소드 호출;
        }
    }

    private void loadTileImages() { // private 타일 이미지를 불러오는 메소드 {
        try {
            tiles[0] = new Tile(loadImage("/tiles/grass00.png"), false); // 0번 인덱스 타일에 grass00.png 이미지를 불러와 설정;
            tiles[1] = new Tile(loadImage("/tiles/wall.png"), true); // 1번 인덱스 타일에 wall.png 이미지를 불러와 설정;
            tiles[2] = new Tile(loadImage("/tiles/water00.png"), false); // 2번 인덱스 타일에 water00.png 이미지를 불러와 설정;
            tiles[3] = new Tile(loadImage("/tiles/earth.png"), false); // 3번 인덱스 타일에 earth.png 이미지를 불러와 설정;
            tiles[4] = new Tile(loadImage("/tiles/grass01.png"), false); // 4번 인덱스 타일에 grass01.png 이미지를 불러와 설정;
            tiles[5] = new Tile(loadImage("/tiles/tree.png"), true); // 5번 인덱스 타일에 tree.png 이미지를 불러와 설정;
            // 여기에 더 많은 타일을 추가할 수 있음
        } catch (Exception e) {
            System.err.println("Error loading tile images: " + e.getMessage()); // 타일 이미지 로딩 오류 메시지 출력;
            e.printStackTrace(); // 오류 스택 추적 출력;
        }
    }

    private BufferedImage loadImage(String path) throws IOException { // private 이미지를 불러오는 메소드;
        return ImageIO.read(getClass().getResourceAsStream(path)); // 지정된 경로에서 이미지를 읽어서 반환;
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

    public void draw(Graphics2D g2) { // 공개 그리기 메소드(Graphics2D g2);
        int worldCol = 0;
        int worldRow = 0;
        
        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapData[worldCol][worldRow];
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            g2.drawImage(tiles[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            
            worldCol++;
            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
} // 클래스 끝
