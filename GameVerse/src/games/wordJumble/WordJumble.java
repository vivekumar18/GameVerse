/**
 * ------------------------------------------------------------
 * Project     : GameVerse
 * Game Title  : Word Jumble
 * Package     : games.wordJumble
 * Class       : WordJumble.java
 *
 * Description : A game where the player must unscramble jumbled 
 * 				 words.
 *
 * Developed by: Vivek Kumar
 * Email       : vivekkumar120103@gmail.com
 * College     : Graphic Era University (Deemed)
 * Date        : 05-12-2025
 * Version     : 1.0
 * ------------------------------------------------------------
 */

package games.wordJumble;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import java.util.Random;
import java.io.File;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class WordJumble extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JLabel jumbledWordLabel, feedbackLabel;
    private JTextField answerField;
    private String currentWord;
    private String[] words = {"vivek", "mango", "ram", "language", "internet", "java","apple","developer", "software","rajan","rishav","vishal"};
    private MediaPlayer mediaPlayer;
    private BackgroundPanel bgpanel;
    
    public WordJumble() {
    		new JFXPanel();
        setTitle("Word Jumble Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                }
                dispose();
                SwingUtilities.invokeLater(() -> new core.GameDashboard(""));
            }
        });


        bgpanel = new BackgroundPanel("src/resources/background.jpg");
        bgpanel.setLayout(new BorderLayout());
        setContentPane(bgpanel);
        
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setOpaque(false);
        JPanel welcomePanel = createWelcomePanel();

        JPanel gamePanel = createGamePanel();

        cardPanel.add(welcomePanel, "welcome");
        cardPanel.add(gamePanel, "game");
        bgpanel.add(cardPanel);
        
        playMusic("src/resources/background.mp3");
        
        setVisible(true);
    }

    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("WORD JUMBLE GAME");
        title.setFont(new Font("Verdana", Font.BOLD, 48));
        title.setForeground(Color.ORANGE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea about = new JTextArea(
            "Welcome to the Word Jumble Game!\n\n"+
            "Your goal is to unscramble the letters to find the correct word.\n" +
            "Improve your vocabulary and spelling while having fun!\n\n" +
            "Click 'Start Game' to begin.\n\n\n Designed by Vivek"
        );
        about.setFont(new Font("SansSerif", Font.PLAIN, 20));
        about.setEditable(false);
        about.setLineWrap(true);
        about.setWrapStyleWord(true);
        about.setBackground(new Color(52, 73, 94));
        about.setForeground(Color.WHITE);
        about.setMargin(new Insets(20, 20, 20, 20));
        about.setMaximumSize(new Dimension(600, 300));
        about.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton startButton = new JButton("START GAME");
        startButton.setFont(new Font("Arial", Font.BOLD, 24));
        startButton.setBackground(new Color(39, 174, 96));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setPreferredSize(new Dimension(200, 60));
        startButton.addActionListener(_ -> {
            nextWord();
            cardLayout.show(cardPanel, "game");
        });

        panel.add(Box.createVerticalStrut(100));
        panel.add(title);
        panel.add(Box.createVerticalStrut(30));
        panel.add(about);
        panel.add(Box.createVerticalStrut(30));
        panel.add(startButton);
        return panel;
    }
    private JPanel createGamePanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        jumbledWordLabel = new JLabel("JUMBLED");
        jumbledWordLabel.setFont(new Font("Monospaced", Font.BOLD, 60));
        jumbledWordLabel.setForeground(Color.WHITE);
        jumbledWordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        answerField = new JTextField(20);
        answerField.setFont(new Font("SansSerif", Font.PLAIN, 32));
        answerField.setMaximumSize(new Dimension(400, 50));
        answerField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton submitButton = new JButton("CHECK");
        submitButton.setFont(new Font("Arial", Font.BOLD, 24));
        submitButton.setBackground(new Color(52, 152, 219));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.addActionListener(_ -> checkAnswer());

        feedbackLabel = new JLabel(" ");
        feedbackLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        feedbackLabel.setForeground(Color.WHITE);
        feedbackLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton nextButton = new JButton("NEXT WORD");
        nextButton.setFont(new Font("Arial", Font.BOLD, 20));
        nextButton.setBackground(new Color(155, 89, 182));
        nextButton.setForeground(Color.WHITE);
        nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        nextButton.addActionListener(_ -> nextWord());

        panel.add(Box.createVerticalStrut(100));
        panel.add(jumbledWordLabel);
        panel.add(Box.createVerticalStrut(30));
        panel.add(answerField);
        panel.add(Box.createVerticalStrut(20));
        panel.add(submitButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(feedbackLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(nextButton);

        return panel;
    }

    private void nextWord() {
        Random rand = new Random();
        currentWord = words[rand.nextInt(words.length)];
        String jumbled = jumbleWord(currentWord);
        jumbledWordLabel.setText(jumbled.toUpperCase());
        answerField.setText("");
        feedbackLabel.setText(" ");
    }

    private void checkAnswer() {
        String guess = answerField.getText().trim().toLowerCase();
        if (guess.equals(currentWord)) {
            feedbackLabel.setText(" Correct!");
        } else {
            feedbackLabel.setText(" Try Again!");
        }
    }

    private String jumbleWord(String word) {
        char[] chars = word.toCharArray();
        Random rand = new Random();
        for (int i = 0; i < chars.length; i++) {
            int j = rand.nextInt(chars.length);
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }
        return new String(chars);
    }
    
    private void playMusic(String filePath) {
        try {
            Media sound = new Media(new File(filePath).toURI().toString());
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: Unable to play music.");
            e.printStackTrace();
        }
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
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // Stretch to fill
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WordJumble());
    }
}
