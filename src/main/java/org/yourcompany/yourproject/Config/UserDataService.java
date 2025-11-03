package org.yourcompany.yourproject.Config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.yourcompany.yourproject.Entity.User;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
public class UserDataService {
    private static final String USER_FILE = "users.json";
    private Gson gson;
    public UserDataService() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public ArrayList<User> loadUsers() {
        try (FileReader reader = new FileReader(USER_FILE)) {
            Type userListType = new TypeToken<ArrayList<User>>() {
            }.getType();
            ArrayList<User> users = gson.fromJson(reader, userListType);
            return users != null ? users : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
    public void saveUsers(ArrayList<User> users) {
        try (FileWriter writer = new FileWriter(USER_FILE)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            System.err.println("Error while saving file: " + e.getMessage());
        }
    }
}