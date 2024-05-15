package entity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SpriteManager {
    private int sheetHeight = 384;
    private int sheetWidth = 576;
    private int heightNum = 8;
    private int widthNum = 12;
    private int spriteHeight = sheetHeight / heightNum;
    private int spriteWidth = sheetWidth / widthNum;
    private int totalSpriteNum = 96;

    private BufferedImage[] sprites = new BufferedImage[totalSpriteNum + 1];
    private Map<String, BufferedImage> imageMap = new HashMap<>();

    public SpriteManager(String filePath, int characterRow) throws IOException {
        BufferedImage spriteSheet = ImageIO.read(new File(filePath));

        int count = 1;
        for (int i = 0; i < heightNum; i++) {
            for (int j = 0; j < widthNum; j++) {
                sprites[count] = spriteSheet.getSubimage(j * spriteWidth, i * spriteHeight, spriteWidth, spriteHeight);
                count++;
            }
        }

        configureImages(characterRow);
    }

    private void configureImages(int row) {
        int baseIndex = (row - 1) * widthNum + 1;
        setName(baseIndex, baseIndex + 12, baseIndex + 24, baseIndex + 36);
    }

    private void setName(int down, int left, int right, int up) {
        for (int i = down, n = 1; i < down + 3; i++, n++) {
            imageMap.put("down" + n, sprites[i]);
        }
        for (int i = left, n = 1; i < left + 3; i++, n++) {
            imageMap.put("left" + n, sprites[i]);
        }
        for (int i = right, n = 1; i < right + 3; i++, n++) {
            imageMap.put("right" + n, sprites[i]);
        }
        for (int i = up, n = 1; i < up + 3; i++, n++) {
            imageMap.put("up" + n, sprites[i]);
        }
    }

    public BufferedImage getImage(String key) {
        return imageMap.get(key);
    }
}
