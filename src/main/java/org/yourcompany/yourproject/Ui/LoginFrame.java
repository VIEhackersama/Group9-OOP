package org.yourcompany.yourproject.Ui;

import javax.swing.*;

import org.yourcompany.yourproject.Entity.User;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private ArrayList<User> users;

    public LoginFrame(ArrayList<User> users) {
        this.users = users;
        setTitle("Đăng nhập");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));

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

        for (User u : users) {
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công! Xin chào " + u.getName());
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Sai email hoặc mật khẩu!");
    }
}