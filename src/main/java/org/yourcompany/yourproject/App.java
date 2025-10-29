package org.yourcompany.yourproject;

import java.util.ArrayList;

import org.yourcompany.yourproject.Entity.House;
import org.yourcompany.yourproject.Entity.User;
import org.yourcompany.yourproject.Ui.LoginFrame;

/**
 *
 * @author HACOM
 */
public class App { 

    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User(1, "Admin", "admin@gmail.com", "0123456789", "Hanoi", "123"));
        javax.swing.SwingUtilities.invokeLater(() -> {
            new LoginFrame(users).setVisible(true);
        });
    }
}
