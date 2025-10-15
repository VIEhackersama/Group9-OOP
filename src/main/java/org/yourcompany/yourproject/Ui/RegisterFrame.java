package org.yourcompany.yourproject.Ui;

import javax.swing.*;

import org.yourcompany.yourproject.Entity.User;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class RegisterFrame extends JFrame {
    private JTextField nameField, emailField, phoneField, addressField;
    private JPasswordField passwordField;
    private ArrayList<User> users;

    public RegisterFrame(ArrayList<User> users) {
        this.users = users;
        setTitle("Đăng ký");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 10, 10));

        add(new JLabel("Tên:"));
        nameField = new JTextField();
        add(nameField);
        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);
        add(new JLabel("SĐT:"));
        phoneField = new JTextField();
        add(phoneField);
        add(new JLabel("Địa chỉ:"));
        addressField = new JTextField();
        add(addressField);
        add(new JLabel("Mật khẩu:"));
        passwordField = new JPasswordField();
        add(passwordField);

        JButton registerBtn = new JButton("Đăng ký");
        JButton backBtn = new JButton("Quay lại");
        add(registerBtn);
        add(backBtn);

        registerBtn.addActionListener(e -> register());
        backBtn.addActionListener(e -> {
            new LoginFrame(users).setVisible(true);
            dispose();
        });
    }

    private void register() {
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();
        String password = new String(passwordField.getPassword());

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin bắt buộc!");
            return;
        }

        int id = users.size() + 1;
        users.add(new User(id, name, email, phone, address, password));
        JOptionPane.showMessageDialog(this, "Đăng ký thành công!");
        new LoginFrame(users).setVisible(true);
        dispose();
    }
}