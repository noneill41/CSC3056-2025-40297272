package app;

import controller.UserController;
import controller.AccountController;
import controller.TransactionController;
import java.util.Date;

public class SimpleBankingApp {

    public static void main(String[] args) {

        // Load initial data
        UserController.loadUserData();
        AccountController.loadAccountData();

        System.out.println("\n--- Initial Users ---");
        UserController.printUsers();

        System.out.println("\n--- Initial Accounts ---");
        AccountController.printAccounts();

        // Adding a new user
        System.out.println("\nAdding a new user...");
        UserController.addUser("john.doe", "secure123", "John", "Doe", "07891234567");
        UserController.printUsers();

        // Deleting a user
        System.out.println("\nDeleting user: james.cameron@gmail.com");
        UserController.deleteUser("james.cameron@gmail.com");
        UserController.printUsers();

        // Adding a new account
        System.out.println("\nAdding a new account...");
        AccountController.addAccount("123456", "john.doe", "Saving", new Date());
        AccountController.printAccounts();

        // Trying to add a duplicate account
        System.out.println("\nTrying to add a duplicate account...");
        AccountController.addAccount("123456", "john.doe", "Saving", new Date());

        // Deleting an account
        System.out.println("\nDeleting account 123456...");
        AccountController.deleteAccount("123456");
        AccountController.printAccounts();

        // Performing transactions
        System.out.println("\nPerforming Transactions...");
        TransactionController.addTransaction("5495-1234", -50.21);
        TransactionController.getBalance("5495-1234");
        System.out.println("\nAccount balances after the 1st transaction:");
        AccountController.printAccounts();

        TransactionController.addTransaction("5495-1234", 520.00);
        System.out.println("\nAccount balances after additional transactions:");
        AccountController.printAccounts();

        // Authenticating a user
        System.out.println("\nAuthenticating user: john.doe...");
        boolean isAuthenticated = UserController.authenticateUser("john.doe", "secure123");
        System.out.println(isAuthenticated ? "User authentication successful." : "Authentication failed.");

        System.out.println("\nSimpleBankingApp execution completed.");
    }
}
