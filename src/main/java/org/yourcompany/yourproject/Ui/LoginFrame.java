package org.yourcompany.yourproject.Ui;

import javax.swing.*;
import org.yourcompany.yourproject.Entity.User;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * LoginFrame — The login screen of the House Price Prediction app.
 *
 * Responsibilities:
 * - Display a form for user login (email + password)
 * - Show an image for better visual appeal
 * - Provide navigation to RegisterFrame (for new users)
 * - Redirect logged-in users to MainFrame
 */
public class LoginFrame extends JFrame {

    private JTextField emailField;       // Input for user email
    private JPasswordField passwordField; // Input for password (hidden text)
    private ArrayList<User> users;        // Reference to shared user list


    public LoginFrame(ArrayList<User> users) {
        this.users = users;

        setTitle("Dự đoán giá nhà - Đăng nhập");  // Sets window title in title bar
        ImageIcon logoIcon = new ImageIcon(Objects.requireNonNull(
                getClass().getResource("/img.png")));
        setIconImage(logoIcon.getImage());
        setSize(700, 400);                        // Set window width = 700px, height = 400px
        setLocationRelativeTo(null);              // Center the frame on screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit app when this frame closes
        setResizable(false);                      // Disable manual resizing for consistent layout

        setLayout(new BorderLayout());


        JLabel imageLabel = new JLabel();

        // Load image file from resources folder inside classpath
        ImageIcon icon = new ImageIcon((getClass().getResource("/img.png")));

        // Scale image to fit the panel’s height without distortion
        Image img = icon.getImage().getScaledInstance(350, 400, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(img)); // Apply scaled image to label

        // Add this image label to the WEST region of BorderLayout
        add(imageLabel, BorderLayout.WEST);


        JPanel formPanel = new JPanel();                    // Holds all form controls
        formPanel.setLayout(new GridBagLayout());            // Flexible grid-based layout
        formPanel.setBackground(new Color(245, 245, 245));   // Light-gray background for contrast

        // GridBagConstraints controls how components are placed in the GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);             // Add 10px padding on all sides
        gbc.fill = GridBagConstraints.HORIZONTAL;            // Components expand horizontally


        JLabel title = new JLabel("Đăng nhập hệ thống");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));  // Larger bold title font
        title.setHorizontalAlignment(SwingConstants.CENTER); // Center align the text horizontally

        gbc.gridx = 0;               // Column index = 0
        gbc.gridy = 0;               // Row index = 0 (top of grid)
        gbc.gridwidth = 2;           // Span across 2 columns
        formPanel.add(title, gbc);   // Add title to panel


        gbc.gridwidth = 1;           // Reset to single-column width
        gbc.gridy++;                 // Move to next row
        formPanel.add(new JLabel("Email:"), gbc); // Label in left column

        emailField = new JTextField(15);  // 15-column width text field
        gbc.gridx = 1;                    // Move to right column
        formPanel.add(emailField, gbc);   // Add email text field


        gbc.gridx = 0;               // Reset to left column
        gbc.gridy++;                 // Move to next row
        formPanel.add(new JLabel("Mật khẩu:"), gbc); // Add password label

        passwordField = new JPasswordField(15); // Input field that hides characters
        gbc.gridx = 1;                          // Move to right column
        formPanel.add(passwordField, gbc);      // Add password field


        gbc.gridx = 0;
        gbc.gridy++;                 // Move to next row
        gbc.gridwidth = 2;           // Buttons centered across both columns
        gbc.anchor = GridBagConstraints.CENTER; // Align content to center

        // Create two buttons
        JButton loginBtn = new RoundedButton("Đăng nhập");
        JButton registerBtn = new RoundedButton("Đăng ký");

        // Create a button container with FlowLayout for natural spacing
        // FlowLayout(centerAlign, horizontalGap=20, verticalGap=10)
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnPanel.setOpaque(false); // Make panel background transparent to match parent

        // Add both buttons to the same row (FlowLayout takes care of placement)
        btnPanel.add(loginBtn);
        btnPanel.add(registerBtn);

        // Add the button row to the grid layout
        formPanel.add(btnPanel, gbc);

        // This puts the entire login form (right side) into the CENTER of BorderLayout
        add(formPanel, BorderLayout.CENTER);


        // When “Đăng nhập” is clicked → trigger login logic
        loginBtn.addActionListener(e -> login());

        // When “Đăng ký” is clicked → open RegisterFrame and close this one
        registerBtn.addActionListener(e -> {
            new RegisterFrame(users).setVisible(true);
            dispose();
        });
    }


    /**
     * login() — Handles login authentication
     *
     * Steps:
     * 1. Get input from text fields
     * 2. Search for a user in the list with matching email & password
     * 3. If found → show success message and open MainFrame
     * 4. Otherwise → show error message
     */
    private void login() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        for (User u : users) {
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công! Xin chào " + u.getName());
                new MarketFrame(u).setVisible(true);
                dispose();
                return;
            }
        }

        // If no user matches the credentials
        JOptionPane.showMessageDialog(this, "Sai email hoặc mật khẩu!");
    }
}