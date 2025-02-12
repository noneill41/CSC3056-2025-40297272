package app;

import controller.UserController;
import controller.AccountController;
import controller.TransactionController;
import model.Account;
import model.User;

import java.util.Vector;

public class SimpleBankingApp {

    public static void main(String[] args) {
        // Load user and account data through controllers
        Vector<User> users = UserController.loadUserData();
        Vector<Account> accounts = AccountController.loadAccountData();

        // Print loaded users
        System.out.println("There are: " + users.size() + " users in the system.");
        System.out.println(String.format("%-25s| %-15s| %-15s| %-15s| %-15s",
                "username", "password", "first_name", "last_name", "mobile_number"));
        System.out.println("-------------------------------------------------------------------------------------------");
        for (User user : users) {
            System.out.println(user.toString());
        }
        System.out.println();

        // Print loaded accounts
        System.out.println("Accounts: initial state, after loading...");
        printAllAccounts(accounts);

        // Perform some transactions
        TransactionController.addTransaction("5495-1234", -50.21);
        System.out.println("Account: after the 1st addTransaction function call...");
        printAllAccounts(accounts);

        // Additional transactions
        TransactionController.addTransaction("5495-1234", 520.00);
        TransactionController.addTransaction("9999-1111", 21.00); // Account does not exist
        System.out.println("Account: after the 2nd/3rd addTransaction function calls...");
        printAllAccounts(accounts);
    }

    private static void printAllAccounts(Vector<Account> accounts) {
        System.out.println("There are: " + accounts.size() + " accounts in the system.");
        System.out.println(String.format("%-15s| %-30s| %-10s| %-15s| %-10s",
                "Account #", "Username of Account Holder", "Type", "Opening Date", "Balance"));
        System.out.println("---------------------------------------------------------------------------------------------");

        for (Account account : accounts) {
            System.out.printf("%-15s| %-30s| %-10s| %-15s| $%-10.2f%n",
                    account.getAccount_number(),
                    account.getUsername_of_account_holder(),
                    account.getAccount_type(),
                    account.getAccount_opening_date(),
                    TransactionController.getBalance(account.getAccount_number()));
        }
        System.out.println();
    }
}
