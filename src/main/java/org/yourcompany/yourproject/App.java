package org.yourcompany.yourproject;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import com.formdev.flatlaf.FlatLightLaf;

import org.yourcompany.yourproject.Config.HouseMongoController;
import org.yourcompany.yourproject.Config.UserDataService;
import org.yourcompany.yourproject.Entity.House;
import org.yourcompany.yourproject.Entity.User;
import org.yourcompany.yourproject.Ui.LoginFrame;
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

            // Tùy chỉnh thêm (Ví dụ: Làm cho các nút mặc định bo tròn)
            UIManager.put("Button.arc", 9);
            UIManager.put("Component.arc", 9);
            UIManager.put("TextComponent.arc", 9);

        } catch (Exception ex) {
            System.err.println("Không thể khởi tạo FlatLaf");
        }
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
        /* HouseMongoController mongoController = new HouseMongoController();
        List<House> houseList = mongoController.loadHousesFromDb();
        for (House house : houseList) {
            System.out.println(house); 
        } */
    }
}
