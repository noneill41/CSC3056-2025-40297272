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

    public static boolean addUser(String username, String password, String firstName, String lastName, String mobileNumber) {
    	if (username == null || username.trim().isEmpty()) {
            System.out.println("ERROR: Username cannot be empty.");
            return false;
        }
        if (users.stream().anyMatch(user -> user.getUsername().equals(username))) {
            System.out.println("ERROR: User with username " + username + " already exists.");
            return false;
        }
        users.add(new User(username, password, firstName, lastName, mobileNumber));
        return true;
    }

    public static boolean deleteUser(String username) {
        User userToRemove = users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);

        if (userToRemove == null) {
            System.out.println("ERROR: User " + username + " not found.");
            return false;
        }

        users.remove(userToRemove);
        System.out.println("User " + username + " has been successfully deleted.");
        return true;
    }

    public static void printUsers() {
        System.out.println("There are: " + users.size() + " users in the system.");
        System.out.println(String.format("%-25s| %-15s| %-15s| %-15s",
                "Username", "First Name", "Last Name", "Mobile Number"));
        System.out.println("---------------------------------------------------------------");

        for (User user : users) {
            System.out.printf("%-25s| %-15s| %-15s| %-15s%n",
                    user.getUsername(),
                    user.getFirst_name(),
                    user.getLast_name(),
                    user.getMobile_number());
        }
        System.out.println();
    }

    public static boolean authenticateUser(String username, String password) {
        return users.stream()
                .anyMatch(user -> user.getUsername().equals(username) && user.getPassword().equals(password));
    }
}
