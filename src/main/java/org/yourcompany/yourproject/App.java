package org.yourcompany.yourproject;

import java.util.ArrayList;
import java.util.List;

import org.yourcompany.yourproject.Config.HouseCsvController;
import org.yourcompany.yourproject.Entity.House;
import org.yourcompany.yourproject.Entity.User;
import org.yourcompany.yourproject.Ui.LoginFrame;

/**
 *
 * @author HACOM
 */
public class App { 

    // public static void main(String[] args) {
    //     ArrayList<User> users = new ArrayList<>();
    //     users.add(new User(1, "Admin", "admin@gmail.com", "0123456789", "Hanoi", "123"));
    //     javax.swing.SwingUtilities.invokeLater(() -> {
    //         new LoginFrame(users).setVisible(true);
    //     });
    // }
    public static void main(String[] args) {
        HouseCsvController controller = new HouseCsvController();
                // Thay đổi "muanhadat_data_dangcap.csv" thành đường dẫn tuyệt đối nếu cần.
        String csvFile = "C:/Users/PC/Documents/GitHub/Group9-OOP/src/main/java/org/yourcompany/yourproject/muanhadat_data_dangcap.csv";
        List<House> houseList = controller.loadHousesFromCsv(csvFile);
        if (houseList.isEmpty()) {
            System.out.println("Cannot parse the data");
        } else {
            System.out.println("Query successfully. Retrived " + houseList.size() + " records.");
            System.out.println("Show the first 5 records:");
            for (int i = 0; i < 5 && i < houseList.size(); i++) {
                System.out.println(houseList.get(i));
            }
        }
    }
}
