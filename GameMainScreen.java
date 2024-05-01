package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;

public class GameMainScreen extends JFrame {
	
	
	private static final long serialVersionUID = 1L;
    private CardLayout cardLayout;
    private StoryScreen storyScreen;
    private JPanel container; // 메인 컨테이너 패널
    private Music bgMusic; // bgMusic을 클래스의 멤버 변수로 선언

    public GameMainScreen() {
        setTitle("The Dream");
        setSize(1024, 1024);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);
        add(container, BorderLayout.CENTER);

        bgMusic = new Music("resources/music/intro.mp3", true);
        bgMusic.start();
        
        JPanel mainPanel = createMainPanel();
        container.add(mainPanel, "MainScreen");

        storyScreen = new StoryScreen();
        container.add(storyScreen, "StoryScreen");

        setVisible(true);
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        ImageIcon backgroundImageIcon = new ImageIcon(getClass().getResource("/background1.jpg"));
        JLabel background = new JLabel(backgroundImageIcon);
        mainPanel.add(background);
        background.setLayout(new GridBagLayout());

        JPanel buttonPanel = createButtonPanel();
        background.add(buttonPanel, new GridBagConstraints());

        return mainPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0);

        JButton startButton = createButton("게임 시작");
        startButton.addActionListener(e -> {
            bgMusic.close();
            Music gameMusic = new Music("resources/music/ending.mp3", true);
            gameMusic.start();
            cardLayout.show(container, "StoryScreen");
            storyScreen.startGameThread();
        });
        buttonPanel.add(startButton, gbc);

        JButton settingsButton = createButton("설정");
        // 설정 버튼 액션 리스너 구현 생략
        buttonPanel.add(settingsButton, gbc);

        JButton exitButton = createButton("게임 종료");
        exitButton.addActionListener(e -> System.exit(0));
        buttonPanel.add(exitButton, gbc);

        return buttonPanel;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 0, 0, 160));
        button.setFocusPainted(false);
        button.setFont(new Font("나눔고딕", Font.BOLD, 24));
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
        button.setPreferredSize(new Dimension(200, 60));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0, 0, 0, 0));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(0, 0, 0, 160));
            }
        });
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameMainScreen().setVisible(true));
    }
}

@SuppressWarnings("serial")
class StoryScreen extends JPanel {
    public StoryScreen() {
        initializeUI();
    }
    public void startGameThread() {

        GamePanel gamePanel = new GamePanel();
        this.add(gamePanel);
        gamePanel.startGameThread();
        this.revalidate();
        this.repaint();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        JLabel storyLabel = new JLabel("여기에 스토리 화면을 표시합니다.", SwingConstants.CENTER);
        add(storyLabel, BorderLayout.CENTER);
    }
}

