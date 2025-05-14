/**
 * ------------------------------------------------------------
 * Project     : GameVerse
 * Game Title  : Number Guessing
 * Package     : games.numberGuessing
 * Class       : NumberGuessing.java
 *
 * Description : A simple interactive game where the player tries to guess 
 *               a randomly selected number. The system gives hints such as 
 *               "Too High" or "Too Low" until the correct number is guessed.
 *               
 * Developed by: Vivek Kumar
 * Email       : vivekkumar120103@gmail.com
 * College     : Graphic Era University (Deemed)
 * Date        : 05-12-2025
 * Version     : 1.0
 * ------------------------------------------------------------
 */

package games.numberGuessing;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
import java.io.*;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class NumberGuessing extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    private int numberToGuess;
    private int attempts;
    private JTextField guessField;
    private JButton guessButton, resetButton, exitButton;
    private JLabel messageLabel;
    private MediaPlayer mediaPlayer;
    private BackgroundPanel bgpanel;

    public NumberGuessing() {
        setTitle("Number Guessing");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
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

        bgpanel.setLayout(new BoxLayout(bgpanel, BoxLayout.Y_AXIS));
        bgpanel.setBorder(BorderFactory.createEmptyBorder(100, 200, 100, 200));

        JLabel title = new JLabel("Guess the Number");
        title.setFont(new Font("Segoe UI", Font.BOLD, 48));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(Color.WHITE);
        bgpanel.add(title);
        bgpanel.add(Box.createRigidArea(new Dimension(0, 40)));

        messageLabel = new JLabel("Enter a number between 1 and 100");
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        messageLabel.setForeground(new Color(220, 220, 220));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bgpanel.add(messageLabel);
        bgpanel.add(Box.createRigidArea(new Dimension(0, 30)));

        guessField = new JTextField();
        guessField.setMaximumSize(new Dimension(300, 50));
        guessField.setFont(new Font("Segoe UI", Font.PLAIN, 26));
        guessField.setHorizontalAlignment(JTextField.CENTER);
        guessField.setBackground(Color.WHITE);
        guessField.setForeground(new Color(60, 60, 60));
        bgpanel.add(guessField);
        bgpanel.add(Box.createRigidArea(new Dimension(0, 30)));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));

        guessButton = createModernButton("Guess", new Color(30, 50, 90));  // Dark blue
        resetButton = createModernButton("Reset", new Color(169, 169, 169));  // Light gray
        exitButton = createModernButton("Exit", new Color(255, 69, 58));  // Dark red

        guessButton.addActionListener(this);
        resetButton.addActionListener((ActionEvent _) -> resetGame());
        exitButton.addActionListener((ActionEvent _) -> System.exit(0));

        buttonPanel.add(guessButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(exitButton);

        bgpanel.add(buttonPanel);

        resetGame();
        playMusic("src/resources/background.mp3");
        setVisible(true);
    }

    private JButton createModernButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 22));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false);
        button.setOpaque(true);

        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 2, true),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });

        return button;
    }

    private void resetGame() {
        numberToGuess = new Random().nextInt(100) + 1;
        attempts = 0;
        guessField.setText("");
        guessButton.setEnabled(true);
        messageLabel.setText("Enter a number between 1 and 100");
        messageLabel.setForeground(new Color(220, 220, 220));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int guess = Integer.parseInt(guessField.getText().trim());
            attempts++;
            if (guess < numberToGuess) {
                messageLabel.setText("Too low! Try again.");
                messageLabel.setForeground(new Color(255, 87, 34));
            } else if (guess > numberToGuess) {
                messageLabel.setText(" Too high! Try again.");
                messageLabel.setForeground(new Color(255, 87, 34));
            } else {
                messageLabel.setText(" Correct! Guessed in " + attempts + " attempt(s).");
                messageLabel.setForeground(new Color(0, 150, 136));
                guessButton.setEnabled(false);
            }
        } catch (NumberFormatException ex) {
            messageLabel.setText("Please enter a valid number.");
            messageLabel.setForeground(new Color(255, 152, 0));
        }
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
        // Initialize JavaFX runtime
        new JFXPanel(); // Needed to initialize JavaFX environment
        SwingUtilities.invokeLater(NumberGuessing::new);
    }
}
