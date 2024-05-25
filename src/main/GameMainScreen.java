package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class GameMainScreen extends JFrame {
    private CardLayout cardLayout;
    private StoryScreen storyScreen;
    private JPanel container;
    private Music bgMusic;

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
        buttonPanel.add(settingsButton, gbc);

        JButton exitButton = createButton("게임 종료");
        exitButton.addActionListener(e -> System.exit(0));
        buttonPanel.add(exitButton, gbc);

        return buttonPanel;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setOpaque(true);
        button.setForeground(Color.yellow);
        button.setBackground(new Color(0, 0, 0, 255));
        button.setFocusPainted(false);
        button.setFont(new Font("나눔고딕", Font.BOLD, 24));
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
        button.setPreferredSize(new Dimension(200, 60));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                JButton sourceButton = (JButton) e.getSource();
                sourceButton.setBackground(new Color(0, 0, 0, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                JButton sourceButton = (JButton) e.getSource();
                sourceButton.setBackground(new Color(0, 0, 0, 255));
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
        this.removeAll(); // Ensure previous components are removed
        this.add(gamePanel, BorderLayout.CENTER); // Add the game panel in the center
        this.revalidate();
        this.repaint();
        gamePanel.startGameThread();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        JLabel storyLabel = new JLabel("여기에 스토리 화면을 표시합니다.", SwingConstants.CENTER);
        add(storyLabel, BorderLayout.CENTER);
    }
}
