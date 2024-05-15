package entity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SpliteSprites {
    private int sheetHeight = 384;
    private int sheetWidth = 576;

    private int heightNum = 8;   // 높이 당 8개의 이미지
    private int widthNum = 12;   // 너비 당 12개의 이미지
    private int spriteHeight = sheetHeight / heightNum; // 이미지 당 높이
    private int spriteWidth = sheetWidth / widthNum;    // 이미지 당 너비
    private int totalSpriteNum = 96;    // 시트 당 이미지 수

    private BufferedImage[] sprites = new BufferedImage[totalSpriteNum + 1];  // 1~96개의 이미지 저장

    Map<String, BufferedImage> image = new HashMap<>();

    public SpliteSprites(String filePath, int character) throws IOException {
        BufferedImage spriteSheet = ImageIO.read(new File("res/player/Actor1"));

        int cnt = 1;
        for (int i = 0; i < heightNum; i++) {
            for (int j = 0; j < widthNum; j++) {
                sprites[cnt] = spriteSheet.getSubimage(j * spriteWidth, i * spriteHeight, spriteWidth, spriteHeight);
                cnt++;
            }
        }

        switch (character) {    // 시트의 n번째 캐릭터 이미지
        case 1: setImageName(character); break;
        case 2: setImageName(character); break;
        case 3: setImageName(character); break;
        case 4: setImageName(character); break;
        case 5: setImageName(character); break;
        case 6: setImageName(character); break;
        case 7: setImageName(character); break;
        case 8: setImageName(character); break;
        }
    }

    public BufferedImage getImage(String str) {
        return image.get(str);
    }

    public void setName(int d1, int l1, int r1, int u1) {
        int i, n;

        for (i = d1, n = 1; i <= d1 + 2; i++, n++)
            image.put("down" + n, sprites[i]);

        for (i = l1, n = 1; i <= l1 + 2; i++, n++)
            image.put("left" + n, sprites[i]);

        for (i = r1, n = 1; i <= r1 + 2; i++, n++)
            image.put("right" + n, sprites[i]);

        for (i = u1, n = 1; i <= u1 + 2; i++, n++)
            image.put("up" + n, sprites[i]);
    }

    public void setImageName(int ch) {
        switch (ch) {
        case 1:
            setName(1, 13, 25, 37);
            break;
        case 2:
            setName(4, 16, 28, 40);
            break;
        case 3:
            setName(7, 19, 31, 43);
            break;
        case 4:
            setName(10, 22, 34, 46);
            break;
        case 5:
            setName(49, 61, 73, 85);
            break;
        case 6:
            setName(52, 64, 76, 88);
            break;
        case 7:
            setName(55, 67, 79, 91);
            break;
        case 8:
            setName(58, 70, 82, 94);
            break;
        }
    }
}
