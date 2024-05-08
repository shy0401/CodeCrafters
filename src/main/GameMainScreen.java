package main; // 패키지 main;

import javax.swing.*; // javax.swing 패키지를 가져옴;
import java.awt.*; // java.awt 패키지를 가져옴;
import java.awt.event.MouseAdapter; // MouseAdapter 클래스를 가져옴;
import java.awt.event.MouseEvent; // MouseEvent 클래스를 가져옴;

@SuppressWarnings("serial")
public class GameMainScreen extends JFrame { // JFrame을 상속받는 GameMainScreen 클래스 선언;
    private CardLayout cardLayout; // 카드 레이아웃을 관리하는 cardLayout 변수 선언;
    private StoryScreen storyScreen; // 스토리 스크린을 관리하는 storyScreen 변수 선언;
    private JPanel container; // 컨테이너 역할을 하는 JPanel 변수 선언;
    private Music bgMusic; // 배경 음악을 관리하는 Music 변수 선언;

    public GameMainScreen() { // GameMainScreen 생성자;
        setTitle("The Dream"); // 창 제목 설정;
        setSize(1024, 1024); // 창 크기 설정;
        setLocationRelativeTo(null); // 창 위치를 화면 중앙에 위치시킴;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창 닫기 옵션 설정;

        cardLayout = new CardLayout(); // cardLayout 인스턴스 생성;
        container = new JPanel(cardLayout); // cardLayout을 사용하는 JPanel 인스턴스 생성;
        add(container, BorderLayout.CENTER); // container를 창 중앙에 추가;

        bgMusic = new Music("resources/music/intro.mp3", true); // 배경 음악 인스턴스 생성 및 시작;
        bgMusic.start(); 
        
        JPanel mainPanel = createMainPanel(); // 메인 패널 생성;
        container.add(mainPanel, "MainScreen"); // 메인 패널을 container에 추가;

        storyScreen = new StoryScreen(); // StoryScreen 인스턴스 생성;
        container.add(storyScreen, "StoryScreen"); // storyScreen을 container에 추가;

        setVisible(true); // 창을 보이게 설정;
    }

    private JPanel createMainPanel() { // 메인 패널을 생성하는 메소드;
        JPanel mainPanel = new JPanel(new BorderLayout()); // BorderLayout을 사용하는 JPanel 생성;
        ImageIcon backgroundImageIcon = new ImageIcon(getClass().getResource("/background1.jpg")); // 배경 이미지 아이콘 로드;
        JLabel background = new JLabel(backgroundImageIcon); // 배경 이미지를 포함하는 레이블 생성;
        mainPanel.add(background); // 배경 레이블을 메인 패널에 추가;
        background.setLayout(new GridBagLayout()); // 배경 레이블 레이아웃 설정;

        JPanel buttonPanel = createButtonPanel(); // 버튼 패널 생성;
        background.add(buttonPanel, new GridBagConstraints()); // 버튼 패널을 배경 레이블에 추가;

        return mainPanel; // 생성된 메인 패널 반환;
    }

    private JPanel createButtonPanel() { // 버튼 패널을 생성하는 메소드;
        JPanel buttonPanel = new JPanel(new GridBagLayout()); // GridBagLayout을 사용하는 JPanel 생성;
        buttonPanel.setOpaque(false); // 버튼 패널을 투명하게 설정;

        GridBagConstraints gbc = new GridBagConstraints(); // GridBagConstraints 인스턴스 생성;
        gbc.gridwidth = GridBagConstraints.REMAINDER; // 컴포넌트가 남은 그리드 너비를 차지하도록 설정;
        gbc.anchor = GridBagConstraints.CENTER; // 컴포넌트가 중앙에 위치하도록 설정;
        gbc.insets = new Insets(10, 0, 10, 0); // 컴포넌트의 여백 설정;

        JButton startButton = createButton("게임 시작"); // "게임 시작" 버튼 생성;
        startButton.addActionListener(e -> { // "게임 시작" 버튼의 액션 리스너 설정;
            bgMusic.close(); // 배경 음악 종료;
            Music gameMusic = new Music("resources/music/ending.mp3", true); // 게임 음악 인스턴스 생성 및 시작;
            gameMusic.start();
            cardLayout.show(container, "StoryScreen"); // 스토리 스크린으로 전환;
            storyScreen.startGameThread(); // 스토리 게임 스레드 시작;

        });
        buttonPanel.add(startButton, gbc); // "게임 시작" 버튼을 버튼 패널에 추가;

        JButton settingsButton = createButton("설정"); // "설정" 버튼 생성;
        // 설정 버튼 액션 리스너 구현 생략
        buttonPanel.add(settingsButton, gbc); // "설정" 버튼을 버튼 패널에 추가;

        JButton exitButton = createButton("게임 종료"); // "게임 종료" 버튼 생성;
        exitButton.addActionListener(e -> System.exit(0)); // "게임 종료" 버튼의 액션 리스너 설정;
        buttonPanel.add(exitButton, gbc); // "게임 종료" 버튼을 버튼 패널에 추가;

        return buttonPanel; // 생성된 버튼 패널 반환;
    }

    private JButton createButton(String text) { // 버튼을 생성하는 메소드;
        JButton button = new JButton(text); // 텍스트를 포함하는 JButton 인스턴스 생성;
        button.setOpaque(true); // 버튼을 불투명하게 설정;
        button.setForeground(Color.yellow); // 버튼 글자색 설정;
        button.setBackground(new Color(0, 0, 0, 255)); // 버튼 배경색 설정;
        button.setFocusPainted(false); // 버튼 포커스 표시 비활성화;
        button.setFont(new Font("나눔고딕", Font.BOLD, 24)); // 버튼 폰트 설정;
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true)); // 버튼 테두리 설정;
        button.setPreferredSize(new Dimension(200, 60)); // 버튼 크기 설정;
        button.addMouseListener(new MouseAdapter() { // 버튼에 마우스 리스너 추가;
            @Override
            public void mouseEntered(MouseEvent e) { // 마우스가 버튼 위에 올라올 때;
                JButton sourceButton = (JButton) e.getSource();
                sourceButton.setBackground(new Color(0, 0, 0, 255)); // 메뉴 글씨 겹침오류 해결 - 0508 하윤
            }

            @Override
            public void mouseExited(MouseEvent e) { // 마우스가 버튼에서 벗어날 때;
                JButton sourceButton = (JButton) e.getSource();
                sourceButton.setBackground(new Color(0, 0, 0, 255)); // 메뉴 글씨 겹침오류 해결 - 0508 하윤
            }
        });
        return button; // 생성된 버튼 반환;
    }



    public static void main(String[] args) { // 메인 메소드;
        SwingUtilities.invokeLater(() -> new GameMainScreen().setVisible(true)); // 이벤트 디스패치 스레드에서 GameMainScreen 인스턴스 생성 및 표시;
    }
}

@SuppressWarnings("serial")
class StoryScreen extends JPanel { // JPanel을 상속받는 StoryScreen 클래스 선언;
    public StoryScreen() { // StoryScreen 생성자;
        initializeUI(); // UI 초기화 메소드 호출;
    }
    public void startGameThread() { // 게임 스레드 시작 메소드;

        GamePanel gamePanel = new GamePanel(); // GamePanel 인스턴스 생성;
        this.add(gamePanel); // gamePanel을 StoryScreen에 추가;
        gamePanel.startGameThread(); // gamePanel의 게임 스레드 시작;
        this.revalidate(); // 컴포넌트를 다시 검증;
        this.repaint(); // 컴포넌트를 다시 그림;
    }

    private void initializeUI() { // UI를 초기화하는 메소드;
        setLayout(new BorderLayout()); // 레이아웃을 BorderLayout으로 설정;
        JLabel storyLabel = new JLabel("여기에 스토리 화면을 표시합니다.", SwingConstants.CENTER); // 중앙 정렬된 레이블 생성;
        add(storyLabel, BorderLayout.CENTER); // storyLabel을 중앙에 추가;
    }
}
