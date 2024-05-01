package main;

import javazoom.jl.player.Player;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class Music extends Thread {
    private Player player;
    private boolean isLoop;
    private String filePath; // 음악 파일 경로를 저장하는 변수

    // 생성자
    public Music(String filePath, boolean isLoop) {
        this.filePath = filePath; // 생성자를 통해 파일 경로를 설정
        this.isLoop = isLoop; // 음악을 반복할지 여부를 결정
    }

    @Override
    public void run() {
        try {
            do {
                // FileInputStream을 통해 음악 파일을 읽습니다.
                FileInputStream fis = new FileInputStream(filePath);
                BufferedInputStream bis = new BufferedInputStream(fis);
                
                // javazoom.jl.player.Player를 사용하여 음악 파일 재생
                player = new Player(bis);
                player.play(); // 음악 재생
                
                bis.close();
                fis.close();
            } while (isLoop && !Thread.interrupted()); // isLoop가 true이고 스레드가 중단되지 않은 경우에 반복
        } catch (Exception e) {
            System.out.println("음악 재생 중 오류 발생: " + e.getMessage());
        }
    }

    // 음악 재생 중지 메서드
    public void close() {
        // 반복 재생 중지
        isLoop = false;
        // 현재 스레드에 인터럽트 신호를 보냄
        this.interrupt();
        // 음악 재생 중지
        if (player != null) {
            player.close();
        }
    }
}
