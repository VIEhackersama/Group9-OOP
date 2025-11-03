package org.yourcompany.yourproject.Ui;

import javax.swing.*;
import org.yourcompany.yourproject.Entity.User;
import org.yourcompany.yourproject.Config.PasswordUtil; 
import org.yourcompany.yourproject.Config.UserDataService; 

import java.awt.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class RegisterFrame extends JFrame {
    private JTextField nameField, emailField, phoneField, addressField;
    private JPasswordField passwordField;
    private ArrayList<User> users;
    private UserDataService userDataService; 

    // Pattern để kiểm tra email
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");

    public RegisterFrame(ArrayList<User> users) {
        this.users = users;
        this.userDataService = new UserDataService(); 
        setTitle("Đăng ký");
        setSize(400, 350); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(7, 2, 10, 10)); 
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(new JLabel("Tên (*):"));
        nameField = new JTextField();
        add(nameField);
        add(new JLabel("Email (*):"));
        emailField = new JTextField();
        add(emailField);
        add(new JLabel("SĐT:"));
        phoneField = new JTextField();
        add(phoneField);
        add(new JLabel("Địa chỉ:"));
        addressField = new JTextField();
        add(addressField);
        add(new JLabel("Mật khẩu (*):"));
        passwordField = new JPasswordField();
        add(passwordField);

        JButton registerBtn = new JButton("Đăng ký");
        JButton backBtn = new JButton("Quay lại");
        add(registerBtn);
        add(backBtn);

        add(new JLabel("(*) là thông tin bắt buộc"));
        add(new JLabel(""));

        registerBtn.addActionListener(e -> register());
        backBtn.addActionListener(e -> {
            new LoginFrame(users).setVisible(true);
            dispose();
        });
    }

    private void register() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();
        String password = new String(passwordField.getPassword());

        //  Kiểm tra thông tin bắt buộc
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ Tên, Email, và Mật khẩu!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        //  Kiểm tra định dạng email
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            JOptionPane.showMessageDialog(this, "Email không đúng định dạng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //  Kiểm tra email đã tồn tại chưa
        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                JOptionPane.showMessageDialog(this, "Email này đã được sử dụng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        //  Hash mật khẩu
        String hashedPassword = PasswordUtil.hashPassword(password);

        //  Tạo ID mới
        int id = users.isEmpty() ? 1 : users.get(users.size() - 1).getId() + 1;

        //  Thêm user mới vào list
        User newUser = new User(id, name, email, phone, address, hashedPassword);
        users.add(newUser);

        //  Lưu danh sách mới vào file JSON
        userDataService.saveUsers(users);

        JOptionPane.showMessageDialog(this, "Đăng ký thành công! Vui lòng đăng nhập.", "Thành công",
                JOptionPane.INFORMATION_MESSAGE);

        //  Quay lại trang Login
        new LoginFrame(users).setVisible(true);
        dispose();
    }
}