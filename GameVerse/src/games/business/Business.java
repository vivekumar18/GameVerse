/**
 * ------------------------------------------------------------
 * Project     : GameVerse
 * Game Title  : Business
 * Package     : games.business
 * Class       : Business.java
 *
 * Description : A Monopoly-inspired business board game where 
 * 				 players buy, properties to accumulate wealth. 
 * 				 Players roll dice to move across the board, 
 * 				 and make strategic investments to bankrupt 
 *               opponents and become the richest player.
 *                                             
 * Developed by: Rishav Raj
 * Email       : rishavraj05072002@gmail.com
 * College     : Graphic Era University (Deemed)
 * Date        : 05-12-2025
 * Version     : 1.0 
 * ------------------------------------------------------------
 */ 
package games.business;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.*;
import java.awt.event.*;
import java.io.*;
import javafx.embed.swing.JFXPanel;  
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Business extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private City[] cities;
    private JLabel userInfo, computerInfo;
    private JButton rollDiceButton;
    private BackgroundPanel bgpanel;
    private Random random = new Random();
    private MediaPlayer mediaPlayer;

    private java.util.List<City> userCities = new ArrayList<>();
    private java.util.List<City> computerCities = new ArrayList<>();

    public Business() {
        new JFXPanel();
    	cities = new City[] {
    		    new City("RANCHI", 60, false), new City("GUWAHATI", 60, false),
    		    new City("BHUBANESHWAR", 60, false), new City("STATION", 200, false), new City("SRINAGAR", 100, false),
    		    new City("AGRA", 100, false), new City("VADODARA", 120, false), new City("LUDHIANA", 140, false), 
    		    new City("PATNA", 140, false), new City("BHOPAL", 160, false), new City("STATION", 200, false), 
    		    new City("INDORE", 160, false), new City("NAGPUR", 180, false), new City("KOCHI", 200, false), 
    		    new City("LUCKNOW", 220, false), new City("CHANDIGARH", 220, false), new City("JAIPUR", 240, false), 
    		    new City("STATION", 200, false), new City("PUNE", 260, false), new City("HYDERABAD", 260, false), 
    		    new City("AHMEDABAD", 280, false), new City("KOLKATA", 300, false), new City("CHENNAI", 300, false), 
    		    new City("BENGALURU", 320, false), new City("STATION", 200, false), new City("SURAT", 300, false),
    		    new City("DELHI", 350, false), new City("MUMBAI", 400, false)
    		};



        setTitle("Business Game");
        setSize(800, 800);
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



        bgpanel = new BackgroundPanel("/resources/background.jpg");
        bgpanel.setLayout(new BorderLayout());
        setContentPane(bgpanel);

        JPanel imagePanel = new ImagePanel("/games/business/resources/dashboard.png");
        imagePanel.setLayout(new BorderLayout());
        imagePanel.setOpaque(false);

        dashboard(imagePanel);

        bgpanel.add(imagePanel, BorderLayout.CENTER);
        
        playMusic("src/resources/background.mp3");
        
        rollDiceButton.setIcon(loadDiceImage(1));

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


    private void dashboard(JPanel parent) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);

        // User Info Panel (left side)
        userInfo = new JLabel();
        userInfo.setVerticalAlignment(SwingConstants.TOP);
        userInfo.setForeground(Color.WHITE);
        updateUserInfo();
        JScrollPane userScroll = new JScrollPane(userInfo);
        userScroll.setOpaque(false);
        userScroll.getViewport().setOpaque(false);
        userScroll.setPreferredSize(new Dimension(300, 500));
        mainPanel.add(userScroll, BorderLayout.WEST);

        // Computer Info Panel (right side)
        computerInfo = new JLabel();
        computerInfo.setVerticalAlignment(SwingConstants.TOP);
        computerInfo.setForeground(Color.WHITE);
        updateComputerInfo();
        JScrollPane compScroll = new JScrollPane(computerInfo);
        compScroll.setOpaque(false);
        compScroll.getViewport().setOpaque(false);
        compScroll.setPreferredSize(new Dimension(300, 500));
        mainPanel.add(compScroll, BorderLayout.EAST);

        // Center Panel for Dice and Button
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        rollDiceButton = new JButton();
        rollDiceButton.setBackground(new Color(255, 165, 0));
        rollDiceButton.setFocusPainted(false);
        rollDiceButton.setText("");
        rollDiceButton.setIcon(loadDiceImage(6));

        ImageIcon icon = loadDiceImage(1);
        if (icon != null) {
            int width = icon.getIconWidth();
            int height = icon.getIconHeight();
            rollDiceButton.setPreferredSize(new Dimension(width, height));
        }

        rollDiceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int dice = random.nextInt(6) + 1;
                ImageIcon diceIcon = loadDiceImage(dice);
                rollDiceButton.setIcon(diceIcon);

                City c = cities[random.nextInt(cities.length)];
                if (!c.isBuyed()) {
                    c.setBuyed(true);
                    if (random.nextBoolean()) {
                        userCities.add(c);
                        updateUserInfo();
                    } else {
                        computerCities.add(c);
                        updateComputerInfo();
                    }
                }
                if (userCities.size() + computerCities.size() == cities.length) {
                    declareWinner();
                }
            }
        });

        centerPanel.add(rollDiceButton, gbc);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        parent.add(mainPanel, BorderLayout.CENTER);
    }

    private void updateUserInfo() {
        StringBuilder sb = new StringBuilder("<html><body style='color:white;font-family:Arial;font-size:14pt;'>");
        sb.append("USER CITIES (").append(userCities.size()).append("):<br>");
        for (City c : userCities) {
            sb.append(c.getName()).append("<br>");
        }
        sb.append("</body></html>");
        userInfo.setText(sb.toString());
    }

    private void updateComputerInfo() {
        StringBuilder sb = new StringBuilder("<html><body style='color:white;font-family:Arial;font-size:14pt;'>");
        sb.append("COMPUTER CITIES (").append(computerCities.size()).append("):<br>");
        for (City c : computerCities) {
            sb.append(c.getName()).append("<br>");
        }
        sb.append("</body></html>");
        computerInfo.setText(sb.toString());
    }

    private void declareWinner() {
        String winner ="";
        int city = 0;
        if(userCities.size() > computerCities.size()){
        		winner = "User";
        		city = userCities.size();           
        }
        else if(userCities.size() < computerCities.size()){
        		winner = "Computer";
        		city = computerCities.size(); 
        }
        else{
        		JOptionPane.showMessageDialog(this, "Match Draw\nUser has " + userCities.size() + "cities! \nComputer has " + computerCities.size() + "cities!");
        		System.exit(0);
        }
        String message = winner + " WINS! \n" + winner + " has " + city + " cities out of 28";
        JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    private ImageIcon loadDiceImage(int diceNumber) {
        String path = "/games/business/resources/dice" + diceNumber + ".png";
        try {
            URL imgUrl = getClass().getResource(path);
            if (imgUrl != null) {
                ImageIcon icon = new ImageIcon(imgUrl);
                Image img = icon.getImage().getScaledInstance(icon.getIconWidth(), icon.getIconHeight(),
                        Image.SCALE_SMOOTH);
                return new ImageIcon(img);
            } else {
                System.err.println("Dice image not found: " + path);
                return null;
            }
        } catch (Exception e) {
            System.err.println("Failed to load dice image: " + path);
            return null;
        }
    }

    public class City {
        private String name;
        private int price;
        private boolean buyed;

        public City(String name, int price, boolean buyed) {
            this.name = name;
            this.price = price;
            this.buyed = false;
        }

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }

        public boolean isBuyed() {
            return buyed;
        }

        public void setBuyed(boolean buyed) {
            this.buyed = buyed;
        }
    }

    class BackgroundPanel extends JPanel {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        private Image bgImage;

        public BackgroundPanel(String imagePath) {
            try {
                URL imgUrl = getClass().getResource(imagePath);
                if (imgUrl != null) {
                    bgImage = new ImageIcon(imgUrl).getImage();
                } else {
                    System.err.println("Background image not found: " + imagePath);
                }
            } catch (Exception e) {
                System.err.println("Error loading background image: " + imagePath);
            }
            setOpaque(true);
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (bgImage != null) {
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    class ImagePanel extends JPanel {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        private Image image;

        public ImagePanel(String path) {
            try {
                URL url = getClass().getResource(path);
                if (url != null) {
                    image = new ImageIcon(url).getImage();
                } else {
                    System.err.println("Image not found: " + path);
                }
            } catch (Exception e) {
                System.err.println("Failed to load image: " + path);
            }
            setPreferredSize(new Dimension(800, 800));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                int x = (getWidth() - 800) / 2;
                int y = (getHeight() - 800) / 2;
                g.drawImage(image, x, y, 800, 800, this);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Business::new);
    }
}
