package org.yourcompany.yourproject;

import javax.swing.*;
import com.formdev.flatlaf.FlatLightLaf;

import org.yourcompany.yourproject.Ui.LoginFrame;

/**
 *
 * @author HACOM
 */
public class App { 

    public static void main(String[] args) {
        try {
            // Chọn giao diện Sáng (FlatLightLaf) hoặc Tối (FlatDarkLaf)
            UIManager.setLookAndFeel(new FlatLightLaf());

            // Tùy chỉnh bo tron
            UIManager.put("Button.arc", 9);
            UIManager.put("Component.arc", 9);
            UIManager.put("TextComponent.arc", 9);

        } catch (Exception ex) {
            System.err.println("Không thể khởi tạo FlatLaf");
        }
        SwingUtilities.invokeLater(() -> {new LoginFrame().setVisible(true);});
    }
}
