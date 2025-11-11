package org.yourcompany.yourproject.Ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import org.yourcompany.yourproject.Entity.User;
import org.yourcompany.yourproject.Config.PasswordUtil;
import org.yourcompany.yourproject.Config.UserDataService;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private UserDataService userDataService;

    public LoginFrame() {
        this.userDataService = new UserDataService();

        setTitle("Dự đoán giá nhà - Đăng nhập");
        setSize(750, 400);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        setLayout(new BorderLayout());

        // --- PHẦN HÌNH ẢNH (WEST) ---
        JLabel imageLabel = new JLabel();
        ImageIcon icon = new ImageIcon((getClass().getResource("/img.png")));
        Image img = icon.getImage().getScaledInstance(350, 400, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(img));
        add(imageLabel, BorderLayout.WEST);

        // --- PHẦN FORM (CENTER) ---
        JPanel mainFormPanel = new JPanel(new GridBagLayout());
        mainFormPanel.setBackground(Color.WHITE);
        mainFormPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        add(mainFormPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1.0;

        // **HÀNG 0: TIÊU ĐỀ**
        JLabel title = new JLabel("Đăng nhập hệ thống");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainFormPanel.add(title, gbc);

        // **HÀNG 1: HỘP BO GÓC (Chỉ chứa các trường nhập liệu)**
        RoundedPanel fieldsPanel = new RoundedPanel(new GridBagLayout(), 20, new Color(245, 245, 245));
        fieldsPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        GridBagConstraints gbcFields = new GridBagConstraints();
        gbcFields.fill = GridBagConstraints.HORIZONTAL;
        gbcFields.insets = new Insets(5, 5, 5, 5);

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        // Hàng 1.1: Email
        gbcFields.gridx = 0;
        gbcFields.gridy = 0;
        gbcFields.weightx = 0.1;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setOpaque(false);
        emailLabel.setFont(labelFont);
        fieldsPanel.add(emailLabel, gbcFields);

        gbcFields.gridx = 1;
        gbcFields.gridy = 0;
        gbcFields.weightx = 0.9;
        emailField = new JTextField(15);
        emailField.setFont(fieldFont);
        fieldsPanel.add(emailField, gbcFields);

        // Hàng 1.2: Mật khẩu
        gbcFields.gridx = 0;
        gbcFields.gridy = 1;
        JLabel passLabel = new JLabel("Mật khẩu:");
        passLabel.setOpaque(false);
        passLabel.setFont(labelFont);
        fieldsPanel.add(passLabel, gbcFields);

        gbcFields.gridx = 1;
        gbcFields.gridy = 1;
        passwordField = new JPasswordField(15);
        passwordField.setFont(fieldFont);
        fieldsPanel.add(passwordField, gbcFields);

        // Thêm hộp bo góc vào panel chính
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        mainFormPanel.add(fieldsPanel, gbc);

        // **HÀNG 2: CÁC NÚT BẤM**
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        JButton loginBtn = new RoundedButton("Đăng nhập");
        JButton registerBtn = new RoundedButton("Đăng ký");
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnPanel.setOpaque(false);
        btnPanel.add(loginBtn);
        btnPanel.add(registerBtn);
        mainFormPanel.add(btnPanel, gbc);

        // --- HÀNH ĐỘNG CỦA NÚT ---
        loginBtn.addActionListener(e -> login());
        registerBtn.addActionListener(e -> {
            new RegisterFrame().setVisible(true);
            dispose();
        });
    }

    private void login() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        User user = userDataService.findUserByEmail(email);

        if (user != null && PasswordUtil.checkPassword(password, user.getPassword())) {
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công! Xin chào " + user.getName());
            new MarketFrame(user).setVisible(true);
            dispose();
            return;
        }

        JOptionPane.showMessageDialog(this, "Sai email hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}