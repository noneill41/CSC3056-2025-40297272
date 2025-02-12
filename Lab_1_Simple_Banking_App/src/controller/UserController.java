package controller;

import model.User;
import java.util.Vector;

public class UserController {
    private static Vector<User> users = new Vector<>();

    public static Vector<User> loadUserData() {
        users.clear();

        users.add(new User("mike", "my_passwd", "Mike", "Smith", "07771234567"));
        users.add(new User("james.cameron@gmail.com", "angel", "James", "Cameron", "07777654321"));
        users.add(new User("julia.roberts@gmail.com", "change_me", "Julia", "roberts", "07770123456"));

        return users;
    }

    public static Vector<User> getUsers() {
        return users;
    }
}
