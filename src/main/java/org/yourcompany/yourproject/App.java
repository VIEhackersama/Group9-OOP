package org.yourcompany.yourproject;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

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
