/**
 * ------------------------------------------------------------
 * Project     : GameVerse
 * Game Title  : Rock Paper Scissors
 * Package     : games.rockPaperScissors
 * Class       : RockPaperScissors.java
 *
 * Description : A classic hand game where two players choose 
 * 				 between rock, paper, and scissors. The game 
 * 				 follows simple rules: Rock beats Scissors, 
 * 				 Scissors beats Paper, and Paper beats Rock.
 *                              
 * Developed by: Rishav Raj
 * Email       : rishavraj05072002@gmail.com
 * College     : Graphic Era University (Deemed)
 * Date        : 05-12-2025
 * Version     : 1.0 
 * ------------------------------------------------------------
 */ 

package games.rockPaperScissors;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.*;
import java.io.*;
import javafx.embed.swing.JFXPanel;  
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class RockPaperScissors extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int user;
    private int computer;
    private JLabel usr, comp;
    private BackgroundPanel bgpanel;
    private MediaPlayer mediaPlayer;

    public RockPaperScissors() {

        new JFXPanel(); 

        setTitle("Rock Paper Scissors");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        bgpanel = new BackgroundPanel("src/resources/background.jpg");
        bgpanel.setLayout(new BorderLayout());
        setContentPane(bgpanel);

        user = 0;
        computer = 0;

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setOpaque(false);

        JLabel label = new JLabel("Rock Paper Scissors", SwingConstants.CENTER);
        label.setFont(new Font("Times New Roman", Font.BOLD, 60));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        topPanel.add(label);

        JPanel uVc = new JPanel();
        uVc.setOpaque(false);
        uVc.setLayout(new GridLayout(1, 3));

        Font f = new Font("Times New Roman", Font.BOLD, 46);

        usr = new JLabel("User : " + user, SwingConstants.CENTER);
        JLabel vs = new JLabel(" V/S ", SwingConstants.CENTER);
        comp = new JLabel("Computer : " + computer, SwingConstants.CENTER);

        usr.setFont(f);
        vs.setFont(f);
        comp.setFont(f);

        usr.setForeground(Color.WHITE);
        vs.setForeground(Color.WHITE);
        comp.setForeground(Color.WHITE);

        uVc.add(usr);
        uVc.add(vs);
        uVc.add(comp);

        topPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        topPanel.add(uVc);

        bgpanel.add(topPanel, BorderLayout.NORTH);

        playMusic("src/resources/background.mp3");

        setupGamePanel();

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

    private void setupGamePanel() {
        JPanel gamePanel = new JPanel();
        gamePanel.setOpaque(false);
        gamePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));

        ImageIcon rock = new ImageIcon("src/games/rockPaperScissors/resources/rock.png");
        ImageIcon paper = new ImageIcon("src/games/rockPaperScissors/resources/paper.png");
        ImageIcon scissors = new ImageIcon("src/games/rockPaperScissors/resources/scissors.png");

        JButton rockBtn = new JButton(rock);
        JButton paperBtn = new JButton(paper);
        JButton scissorsBtn = new JButton(scissors);

        rockBtn.setPreferredSize(new Dimension(rock.getIconWidth(), rock.getIconHeight()));
        paperBtn.setPreferredSize(new Dimension(paper.getIconWidth(), paper.getIconHeight()));
        scissorsBtn.setPreferredSize(new Dimension(scissors.getIconWidth(), scissors.getIconHeight()));

        rockBtn.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		playRound("Rock");
        	}
        });
        
        paperBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playRound("Paper");
            }
        });

        scissorsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playRound("Scissors");
            }
        });

        gamePanel.add(rockBtn);
        gamePanel.add(paperBtn);
        gamePanel.add(scissorsBtn);

        bgpanel.add(gamePanel, BorderLayout.CENTER);
    }

    private void playRound(String userChoice) {
        String[] choices = {"Rock", "Paper", "Scissors"};
        String computerChoice = choices[new Random().nextInt(3)];

        if (userChoice.equals(computerChoice)) {
            JOptionPane.showMessageDialog(this, "It's a Tie!\nComputer chose: " + computerChoice);
        } else if ((userChoice.equals("Rock") && computerChoice.equals("Scissors")) ||
                   (userChoice.equals("Paper") && computerChoice.equals("Rock")) ||
                   (userChoice.equals("Scissors") && computerChoice.equals("Paper"))) {
            user++;
            JOptionPane.showMessageDialog(this, "You Win!\nComputer chose: " + computerChoice);
        } else {
            computer++;
            JOptionPane.showMessageDialog(this, "You Lose!\nComputer chose: " + computerChoice);
        }

        usr.setText("User : " + user);
        comp.setText("Computer : " + computer);
    }

    class BackgroundPanel extends JPanel {
        /**
		 * 
		 */
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
        SwingUtilities.invokeLater(() -> new RockPaperScissors());
    }
}
