package org.yourcompany.yourproject.Ui;

import javax.swing.*;
import org.yourcompany.yourproject.Entity.User;
import org.yourcompany.yourproject.Config.PasswordUtil;
import org.yourcompany.yourproject.Config.UserDataService;

import java.awt.*;
import java.util.regex.Pattern;

public class RegisterFrame extends JFrame {
    private JTextField nameField, emailField, phoneField, addressField;
    private JPasswordField passwordField;
    private UserDataService userDataService;

    // Pattern để kiểm tra email
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");

    public RegisterFrame() {
        this.userDataService = new UserDataService();
        setTitle("Đăng ký tài khoản");
        setSize(450, 450); // Kích thước cửa sổ
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE); // Nền cửa sổ màu trắng

        // --- 2. TIÊU ĐỀ (NORTH) ---
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
        northPanel.setBackground(Color.WHITE);
        northPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel titleLabel = new JLabel("Tạo tài khoản mới");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel noteLabel = new JLabel("(*) là thông tin bắt buộc");
        noteLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        noteLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        noteLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        northPanel.add(titleLabel);
        northPanel.add(noteLabel);

        add(northPanel, BorderLayout.NORTH);

        // --- 3. PANEL NỘI DUNG CHÍNH (CENTER) ---
        // Panel này chứa CẢ HỘP XÁM và CÁC NÚT BẤM
        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.setBackground(Color.WHITE);
        add(mainContentPanel, BorderLayout.CENTER);

        // --- HỘP FORM MÀU XÁM (Đặt ở phía BẮC của mainContentPanel) ---
        // Sử dụng RoundedPanel với nền xám nhạt
        RoundedPanel roundedFormPanel = new RoundedPanel(new GridBagLayout(), 20, new Color(240, 242, 245));
        roundedFormPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // Wrapper để căn giữa hộp xám
        JPanel formWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        formWrapper.setBackground(Color.WHITE);
        formWrapper.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        formWrapper.add(roundedFormPanel);

        // Thêm wrapper (chứa hộp xám) vào VÙNG BẮC (NORTH) của panel chính
        mainContentPanel.add(formWrapper, BorderLayout.NORTH);

        // (Code thêm các trường vào roundedFormPanel)
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        // Hàng 1: Tên
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel nameLabel = new JLabel("Tên (*):");
        nameLabel.setFont(labelFont);
        roundedFormPanel.add(nameLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        nameField = new JTextField(20);
        nameField.setFont(fieldFont);
        roundedFormPanel.add(nameField, gbc);
        gbc.weightx = 0;
        // Hàng 2: Email
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel emailLabel = new JLabel("Email (*):");
        emailLabel.setFont(labelFont);
        roundedFormPanel.add(emailLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        emailField = new JTextField(20);
        emailField.setFont(fieldFont);
        roundedFormPanel.add(emailField, gbc);
        // Hàng 3: SĐT
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel phoneLabel = new JLabel("SĐT:");
        phoneLabel.setFont(labelFont);
        roundedFormPanel.add(phoneLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        phoneField = new JTextField(20);
        phoneField.setFont(fieldFont);
        roundedFormPanel.add(phoneField, gbc);
        // Hàng 4: Địa chỉ
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel addressLabel = new JLabel("Địa chỉ:");
        addressLabel.setFont(labelFont);
        roundedFormPanel.add(addressLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        addressField = new JTextField(20);
        addressField.setFont(fieldFont);
        roundedFormPanel.add(addressField, gbc);
        // Hàng 5: Mật khẩu
        gbc.gridx = 0; gbc.gridy = 4;
        JLabel passLabel = new JLabel("Mật khẩu (*):");
        passLabel.setFont(labelFont);
        roundedFormPanel.add(passLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 4;
        passwordField = new JPasswordField(20);
        passwordField.setFont(fieldFont);
        roundedFormPanel.add(passwordField, gbc);


        // --- 4. PANEL NÚT BẤM (Đặt ở VÙNG GIỮA của mainContentPanel) ---
        // Panel này sẽ được căn giữa trong không gian còn lại
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
        buttonPanel.setOpaque(false); // Nền trắng
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JButton registerBtn = new RoundedButton("Đăng ký");
        JButton backBtn = new RoundedButton("Quay lại");

        Dimension buttonSize = new Dimension(120, 35);
        registerBtn.setPreferredSize(buttonSize);
        backBtn.setPreferredSize(buttonSize);

        buttonPanel.add(registerBtn);
        buttonPanel.add(backBtn);

        // Thêm panel nút vào VÙNG GIỮA (CENTER) của panel chính
        mainContentPanel.add(buttonPanel, BorderLayout.CENTER);


        // --- 5. ACTION LISTENERS ---
        registerBtn.addActionListener(e -> register());
        backBtn.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
    }

    // (Phương thức register() giữ nguyên)
    private void register() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ Tên, Email, và Mật khẩu!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            JOptionPane.showMessageDialog(this, "Email không đúng định dạng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (userDataService.findUserByEmail(email) != null) {
            JOptionPane.showMessageDialog(this, "Email này đã được sử dụng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String hashedPassword = PasswordUtil.hashPassword(password);
        int id = userDataService.getNextUserId();
        User newUser = new User(id, name, email, phone, address, hashedPassword);
        userDataService.saveUser(newUser);

        JOptionPane.showMessageDialog(this, "Đăng ký thành công! Vui lòng đăng nhập.", "Thành công",
                JOptionPane.INFORMATION_MESSAGE);

        new LoginFrame().setVisible(true);
        dispose();
    }
}