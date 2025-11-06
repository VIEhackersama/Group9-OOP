package org.yourcompany.yourproject.Ui;

import org.yourcompany.yourproject.Entity.User;
import javax.swing.*;
import java.awt.*;

public class MarketFrame extends JFrame {

    private User loggedInUser;
    public MarketFrame(User user) {
        this.loggedInUser = user;

        setTitle("Market - Chào mừng " + user.getName());
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Thoát app khi đóng

        // Thêm một label chào mừng
        JLabel welcomeLabel = new JLabel("Chào mừng bạn, " + user.getName() + ", đến với Market!",
                SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(welcomeLabel, BorderLayout.CENTER);

        // Bạn có thể thêm các component khác vào đây sau
    }
}