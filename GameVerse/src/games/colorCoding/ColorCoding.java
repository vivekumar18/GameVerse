/**
 * ------------------------------------------------------------
 * Project     : GameVerse
 * Game Title  : Color Coding
 * Package     : games.colorCoding
 * Class       : ColorCoding.java
 *
 * Description : A memory and reflex-based game where the player 
 * 				 identifies or matches colors based on visual 
 * 				 prompts. The game enhances color recognition, 
 * 				 focus, and reaction speed.
 *                              
 * Developed by: Vivek Kumar
 * Email       : vivekkumar120103@gmail.com
 * College     : Graphic Era University (Deemed)
 * Date        : 05-12-2025
 * Version     : 1.0
 * ------------------------------------------------------------
 */

package games.colorCoding;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.io.*;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class ColorCoding extends JFrame{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String[] colorNames = { "Red", "Green", "Blue", "Yellow", "Orange", "Cyan", "Magenta", "Pink",
            "Black" };
    private final Color[] colorValues = { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.ORANGE, Color.CYAN,
            Color.MAGENTA, Color.PINK, Color.BLACK };

    private JLabel displayLabel, resultLabel, scoreLabel;
    private JTextField inputField;
    private JButton startButton, exitButton;
    private Color correctColor;
    private int score = 0;
    private Random random = new Random();
    private MediaPlayer mediaPlayer;
    private BackgroundPanel bgPanel;

    public ColorCoding() {
    		new JFXPanel();
    		
        setTitle("Color Coding");
        setUndecorated(false); 
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setLocationRelativeTo(null);
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

        bgPanel = new BackgroundPanel("src/resources/background.jpg");
        bgPanel.setLayout(new BorderLayout());
        setContentPane(bgPanel);
    
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(30, 30, 30));
        topPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        scoreLabel.setForeground(Color.WHITE);
        topPanel.add(scoreLabel, BorderLayout.WEST);

        resultLabel = new JLabel("Type the COLOR of the word!", SwingConstants.RIGHT);
        resultLabel.setFont(new Font("Segoe UI", Font.ITALIC, 18));
        resultLabel.setForeground(Color.LIGHT_GRAY);
        topPanel.add(resultLabel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        displayLabel = new JLabel("COLOR", SwingConstants.CENTER);
        displayLabel.setFont(new Font("Segoe UI", Font.BOLD, 100));
        displayLabel.setForeground(Color.RED);
        add(displayLabel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(30, 30, 30));
        bottomPanel.setBorder(new EmptyBorder(20, 10, 20, 10));

        inputField = new JTextField(12);
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 26));
        inputField.setForeground(Color.WHITE);
        inputField.setBackground(new Color(50, 50, 50));
        inputField.setCaretColor(Color.WHITE);
        inputField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        bottomPanel.add(inputField);

        startButton = new JButton("Start Game");
        startButton.setFont(new Font("Segoe UI", Font.BOLD, 22));
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(new Color(70, 130, 180));
        startButton.setFocusPainted(false);
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bottomPanel.add(startButton);

        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Segoe UI", Font.BOLD, 22));
        exitButton.setForeground(Color.WHITE);
        exitButton.setBackground(new Color(255, 69, 58)); 
        exitButton.setFocusPainted(false);
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitButton.addActionListener(_ -> System.exit(0)); 
        bottomPanel.add(exitButton);

        add(bottomPanel, BorderLayout.SOUTH);

        startButton.addActionListener(_ -> startGame());
        inputField.addActionListener(_ -> checkAnswer());
        exitButton.addActionListener(_ -> exit());

        playMusic("src/resources/background.mp3");
        
        setVisible(true);
    }
    
    private void exit()
    {
        SwingUtilities.invokeLater(() -> new core.GameDashboard(""));
        dispose();
    }

    private void startGame() {
        score = 0;
        scoreLabel.setText("Score: 0");
        inputField.setEnabled(true);
        inputField.setText("");
        inputField.requestFocus();
        resultLabel.setText("Game started! Type the COLOR of the word.");
        startButton.setText("Restart");
        nextColor();
    }

    private void nextColor() {
        int textIndex = random.nextInt(colorNames.length);
        int colorIndex = random.nextInt(colorValues.length);
        displayLabel.setText(colorNames[textIndex]);
        correctColor = colorValues[colorIndex];
        displayLabel.setForeground(correctColor);
    }

    private void checkAnswer() {
        String guess = inputField.getText().trim().toLowerCase();
        inputField.setText("");

        String correctName = getColorName(correctColor).toLowerCase();

        if (guess.equals(correctName)) {
            score++;
            resultLabel.setText("Correct! Score: " + score);
        } else {
            resultLabel.setText("Wrong! The correct color was: " + correctName + ". Score: " + score);
        }

        scoreLabel.setText("Score: " + score);
        nextColor();
    }

    private String getColorName(Color color) {
        for (int i = 0; i < colorValues.length; i++) {
            if (color.equals(colorValues[i])) {
                return colorNames[i];
            }
        }
        return "Unknown";
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
        SwingUtilities.invokeLater(ColorCoding::new);
    }
}
