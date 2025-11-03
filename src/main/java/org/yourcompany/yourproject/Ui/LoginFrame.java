package org.yourcompany.yourproject.Ui;

import javax.swing.*;
import org.yourcompany.yourproject.Entity.User;
import org.yourcompany.yourproject.Config.PasswordUtil; 

import java.awt.*;
import java.util.ArrayList;

public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private ArrayList<User> users;

    public LoginFrame(ArrayList<User> users) {
        this.users = users;
        setTitle("Đăng nhập");
        setSize(550, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));

        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Mật khẩu:"));
        passwordField = new JPasswordField();
        add(passwordField);

        JButton loginBtn = new JButton("Đăng nhập");
        JButton registerBtn = new JButton("Đăng ký");
        add(loginBtn);
        add(registerBtn);

        loginBtn.addActionListener(e -> login());
        registerBtn.addActionListener(e -> {
            new RegisterFrame(users).setVisible(true);
            dispose(); 
        });
    }

    private void login() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập email và mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (User u : users) {
            //  Dùng PasswordUtil để kiểm tra
            if (u.getEmail().equals(email) && PasswordUtil.checkPassword(password, u.getPassword())) {
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công! Xin chào " + u.getName());
                // Mở MarketFrame và đóng cửa sổ hiện tại**
                new MarketFrame(u).setVisible(true);
                dispose();
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Sai email hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}