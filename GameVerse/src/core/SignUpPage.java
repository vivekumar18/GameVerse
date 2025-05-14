package core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SignUpPage extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField usernameField;
    private JPasswordField passwordField;

    public SignUpPage() {
        setTitle("GameVerse - Signup");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(30, 30, 60));

        JLabel title = new JLabel("Create Your GameVerse Account", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBounds(100, 30, 300, 40);
        add(title);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.WHITE);
        userLabel.setBounds(100, 100, 100, 25);
        add(userLabel);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        usernameField.setBounds(200, 100, 200, 30);
        add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.WHITE);
        passLabel.setBounds(100, 150, 100, 25);
        add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordField.setBounds(200, 150, 200, 30);
        add(passwordField);

        JButton signup = createStyledButton("Signup");
        signup.setBounds(100, 220, 90, 35);
        add(signup);

        // ActionListener for Signup button
        signup.addActionListener(_ -> signupAction());

        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return button;
    }

    private void signupAction() {
        String user = usernameField.getText();
        String pass = new String(passwordField.getPassword());
        if (user.isBlank() || pass.isBlank()) {
            JOptionPane.showMessageDialog(this, "Fields can't be empty!");
            return;
        }
        UserUtil.saveUser(user, pass); // Assuming UserUtil handles saving user data
        JOptionPane.showMessageDialog(this, "Signup Successful! Login now.");
        dispose();
        new GameLogin(); // Redirect back to login page after successful signup
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignUpPage::new);
    }
}
