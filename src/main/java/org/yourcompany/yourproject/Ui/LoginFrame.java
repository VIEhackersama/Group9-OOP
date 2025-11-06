package org.yourcompany.yourproject.Ui;

import javax.swing.*;
import org.yourcompany.yourproject.Entity.User;
import org.yourcompany.yourproject.Config.PasswordUtil;
import org.yourcompany.yourproject.Config.UserDataService; // Import service mới

import java.awt.*;
// import java.util.ArrayList; // Không còn dùng ArrayList

public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    // private ArrayList<User> users; // Bỏ
    private UserDataService userDataService; // Dùng service

    // Constructor không cần ArrayList<User> nữa
    public LoginFrame(/* ArrayList<User> users */) {
        // this.users = users; // Bỏ
        this.userDataService = new UserDataService(); // Khởi tạo service

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
            new RegisterFrame().setVisible(true); // Không cần truyền 'users'
            dispose();
        });
    }

    private void login() {
        String email = emailField.getText().trim(); // Thêm .trim()
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập email và mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Tìm user trong DB thay vì duyệt list
        User user = userDataService.findUserByEmail(email);

        // Kiểm tra user có tồn tại và mật khẩu có khớp không
        if (user != null && PasswordUtil.checkPassword(password, user.getPassword())) {
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công! Xin chào " + user.getName());
            // Mở MarketFrame và đóng cửa sổ hiện tại
            // new MarketFrame(user).setVisible(true); // Giữ nguyên logic của bạn
            dispose();
            return;
        }

        // Nếu user == null hoặc sai mật khẩu
        JOptionPane.showMessageDialog(this, "Sai email hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}