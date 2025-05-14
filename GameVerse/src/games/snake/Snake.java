/**
 * ------------------------------------------------------------
 * Project     : GameVerse
 * Game Title  : Snake
 * Package     : games.snake
 * Class       : Snake.java
 *
 * Description : A classic arcade-style game where the player controls 
 *               a snake that moves around the screen to collect food. 
 *               Each time the snake eats food, it grows in size. 
 *               The goal is to survive as long as possible without 
 *               hitting the walls or colliding with itself.
 *
 *               Designed to enhance reaction time, coordination, and 
 *               strategic movement.
 *                              
 * Developed by: Rajan Kumar
 * Email       : 73521689rajan@gmail.com
 * College     : Graphic Era University (Deemed)
 * Date        : 05-12-2025
 * Version     : 1.0 
 * ------------------------------------------------------------
 */ 


package games.snake;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import java.io.*;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Snake extends JFrame{
	private static final long serialVersionUID = 1L;


	private MediaPlayer mediaPlayer;
	private BackgroundPanel bgpanel;

	public Snake() {
	        setTitle("Number Guessing");
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

	        playMusic("src/resources/background.mp3");
	        setVisible(true);
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
		SwingUtilities.invokeLater(Snake::new);
	}
}
