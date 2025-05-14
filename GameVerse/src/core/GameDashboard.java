package core;
import java.awt.*;
import javax.swing.*;

public class GameDashboard extends JFrame {

    private static final long serialVersionUID = 1L;
    JPanel displayPanel;
    private BackgroundPanel bgPanel;

    public GameDashboard(String username) {
        setTitle("Welcome " + username);
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        bgPanel = new BackgroundPanel("src/resources/background.jpg");
        bgPanel.setLayout(new BorderLayout());

        // Display panel
        displayPanel = new JPanel();
        displayPanel.setOpaque(false);
        displayPanel.setLayout(new BorderLayout());
        displayPanel.setBackground(new Color(240, 240, 255));

        JLabel welcome = new JLabel("GameVerse", SwingConstants.CENTER);
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 72));
        welcome.setForeground(Color.WHITE);
        welcome.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));

        displayPanel.add(welcome, BorderLayout.NORTH);

        bgPanel.add(displayPanel, BorderLayout.CENTER);

        setContentPane(bgPanel);

        // Show all games directly
        showAllGames();

        setVisible(true);
    }

    void showAllGames() {
        JPanel gameBoxPanel = new JPanel(new GridLayout(0, 3, 200, 100)); // 3 columns
//    	JPanel gameBoxPanel = new JPanel(new FlowLayout());
        gameBoxPanel.setOpaque(false);
        gameBoxPanel.setBackground(new Color(240, 240, 255));
        gameBoxPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

        String[] allGames = {
            "Business", "RockPaperScissors", "TicTacToe", 
            "Snake", "WordJumbles", "TreasureHunt", 
            "SnapMatch", "ColorCoding", "NumberGuessing",
            "BrainTeaser"
        };

        for (String game : allGames) {
        	ImageIcon rawIcon = new ImageIcon("src/resources/" + game + ".png");
        	JPanel card = new JPanel(new BorderLayout());
        	card.setOpaque(false);

        	Image scaledImage = rawIcon.getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH);
        	ImageIcon gameIcon = new ImageIcon(scaledImage);

        	JButton gameButton = new JButton(gameIcon);
        	gameButton.setPreferredSize(new Dimension(300, 400));
        	gameButton.setBorderPainted(true);
        	gameButton.setContentAreaFilled(false);
        	gameButton.setFocusPainted(true);
        	gameButton.setMargin(new Insets(0, 0, 0, 0));
        	gameButton.setOpaque(false);




            
            gameButton.addActionListener(_ -> {
                switch (game) {
                    case "Business":
                        new games.business.Business();
                        break;
                    case "TicTacToe":
                        new games.ticTacToe.TicTacToe();
                        break;
                    case "Snake":
                        new games.snake.Snake();
                        break;
                    case "RockPaperScissors":
                        new games.rockPaperScissors.RockPaperScissors();
                        break;
                    case "TreasureHunt":
                        new games.treasureHunt.TreasureHunt();
                        break;
                    case "SnapMatch":
                        new games.snapMatch.SnapMatch();
                        break;
                    case "BrainTeaser":
                        new games.brainTreasure.BrainTreasure();
                        break;
                    case "NumberGuessing":
                        new games.numberGuessing.NumberGuessing();
                        break;
                    case "ColorCoding":
                        new games.colorCoding.ColorCoding();
                        break;
                    case "WordJumbles":
                        new games.wordJumble.WordJumble();
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Game not available: " + game);
                }
            });

            card.add(gameButton, BorderLayout.CENTER);
            gameBoxPanel.add(card);
        }

        JScrollPane scrollPane = new JScrollPane(gameBoxPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20); // smoother scroll
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        displayPanel.add(scrollPane, BorderLayout.CENTER);
        displayPanel.revalidate();
        displayPanel.repaint();
    }



    class BackgroundPanel extends JPanel {
        private static final long serialVersionUID = 1L;
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            this.backgroundImage = new ImageIcon(imagePath).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameDashboard("vicoderrr"));
    }
}
