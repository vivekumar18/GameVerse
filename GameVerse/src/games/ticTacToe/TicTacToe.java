/**
 * ------------------------------------------------------------
 * Project     : GameVerse
 * Game Title  : Tic-Tac-Toe
 * Package     : games.ticTacToe
 * Class       : TicTacToe.java
 *
 * Description : A classic two-player game where players 
 * 				 alternate placing X and O on a 3x3 grid. The 
 * 				 first player to align three marks horizontally, 
 *               vertically, or diagonally wins the game.
 *                                             
 * Developed by: Rajan Kumar
 * Email       : 73521689rajan@gmail.com
 * College     : Graphic Era University (Deemed)
 * Date        : 05-12-2025
 * Version     : 1.0 
 * ------------------------------------------------------------
 */ 

package games.ticTacToe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class TicTacToe extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private final int WIDTH = 600;
    private final int HEIGHT = 650;

    private JPanel mini;
    private JLabel statusLabel;
    private JPanel boardPanel;
    private BackgroundPanel bgPanel;

    private JButton[][] cells = new JButton[3][3];
    private String currentPlayer = "X";
    private boolean isGameFinished = false;
    private int moveCount = 0;
    private MediaPlayer mediaPlayer;

    public TicTacToe() {
        new JFXPanel(); // Initialize JavaFX to play background music

        setTitle("Tic Tac Toe");
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

        // Set full-window background
        bgPanel = new BackgroundPanel("src/resources/background.jpg");
        bgPanel.setLayout(new GridBagLayout()); // Important for centering
        setContentPane(bgPanel);

        // Centered game panel with fixed size
        miniWindow();

        playMusic("src/resources/background.mp3");

        setVisible(true);
    }

    private void miniWindow() {
        mini = new JPanel();
        mini.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        mini.setLayout(new BorderLayout());
        mini.setOpaque(false); // Transparent so background shows through

        // Center mini on bgPanel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        bgPanel.add(mini, gbc);

        setupStatusBar();
        setupGameBoard();
    }

    private void setupStatusBar() {
        statusLabel = new JLabel("Tic Tac Toe", SwingConstants.CENTER);
        statusLabel.setOpaque(true);
        statusLabel.setBackground(Color.DARK_GRAY);
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 48));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(statusLabel, BorderLayout.CENTER);
        mini.add(topPanel, BorderLayout.NORTH);
    }

    private void setupGameBoard() {
        boardPanel = new JPanel(new GridLayout(3, 3));
        boardPanel.setOpaque(false);
        mini.add(boardPanel, BorderLayout.CENTER);

        Font cellFont = new Font("Arial", Font.BOLD, 100);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton cell = new JButton();
                cell.setFont(cellFont);
                cell.setFocusable(false);
                cell.setBackground(new Color(64, 64, 64));
                cell.setForeground(Color.WHITE);
                final int r = row;
                final int c = col;

                cell.addActionListener(_ -> handleMove(cell, r, c));
                cells[row][col] = cell;
                boardPanel.add(cell);
            }
        }
    }

    private void handleMove(JButton cell, int row, int col) {
        if (isGameFinished || !cell.getText().isEmpty()) return;

        cell.setText(currentPlayer);
        moveCount++;

        if (checkForWinner(row, col)) {
            highlightWinningCells();
            statusLabel.setText(currentPlayer + " wins!");
            isGameFinished = true;
        } else if (moveCount == 9) {
            showTieResult();
        } else {
            currentPlayer = currentPlayer.equals("X") ? "O" : "X";
            statusLabel.setText(currentPlayer + "'s turn");
        }
    }

    private boolean checkForWinner(int row, int col) {
        String symbol = currentPlayer;

        if (cells[row][0].getText().equals(symbol) &&
            cells[row][1].getText().equals(symbol) &&
            cells[row][2].getText().equals(symbol)) {
            return true;
        }

        if (cells[0][col].getText().equals(symbol) &&
            cells[1][col].getText().equals(symbol) &&
            cells[2][col].getText().equals(symbol)) {
            return true;
        }

        if (row == col &&
            cells[0][0].getText().equals(symbol) &&
            cells[1][1].getText().equals(symbol) &&
            cells[2][2].getText().equals(symbol)) {
            return true;
        }

        if (row + col == 2 &&
            cells[0][2].getText().equals(symbol) &&
            cells[1][1].getText().equals(symbol) &&
            cells[2][0].getText().equals(symbol)) {
            return true;
        }

        return false;
    }

    private void highlightWinningCells() {
        Color winColor = new Color(34, 139, 34);

        for (int r = 0; r < 3; r++) {
            if (cells[r][0].getText().equals(currentPlayer) &&
                cells[r][1].getText().equals(currentPlayer) &&
                cells[r][2].getText().equals(currentPlayer)) {
                cells[r][0].setBackground(winColor);
                cells[r][1].setBackground(winColor);
                cells[r][2].setBackground(winColor);
                return;
            }
        }

        for (int c = 0; c < 3; c++) {
            if (cells[0][c].getText().equals(currentPlayer) &&
                cells[1][c].getText().equals(currentPlayer) &&
                cells[2][c].getText().equals(currentPlayer)) {
                cells[0][c].setBackground(winColor);
                cells[1][c].setBackground(winColor);
                cells[2][c].setBackground(winColor);
                return;
            }
        }

        if (cells[0][0].getText().equals(currentPlayer) &&
            cells[1][1].getText().equals(currentPlayer) &&
            cells[2][2].getText().equals(currentPlayer)) {
            cells[0][0].setBackground(winColor);
            cells[1][1].setBackground(winColor);
            cells[2][2].setBackground(winColor);
        } else if (cells[0][2].getText().equals(currentPlayer) &&
                   cells[1][1].getText().equals(currentPlayer) &&
                   cells[2][0].getText().equals(currentPlayer)) {
            cells[0][2].setBackground(winColor);
            cells[1][1].setBackground(winColor);
            cells[2][0].setBackground(winColor);
        }
    }

    private void showTieResult() {
        statusLabel.setText("It's a draw!");
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                cells[r][c].setBackground(new Color(255, 140, 0));
            }
        }
        isGameFinished = true;
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
        SwingUtilities.invokeLater(TicTacToe::new);
    }
}
