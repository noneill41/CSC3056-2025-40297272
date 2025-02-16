package controller;

import model.Account;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class AccountController {
    private static Vector<Account> accounts = new Vector<>();

    public static Vector<Account> loadAccountData() {
        accounts.clear();
        try {
            accounts.add(new Account("5495-1234", "mike", "Standard", new SimpleDateFormat("dd/MM/yyyy").parse("20/08/2019")));
            accounts.add(new Account("5495-1239", "mike", "Standard", new SimpleDateFormat("dd/MM/yyyy").parse("20/08/2020")));
            accounts.add(new Account("5495-1291", "mike", "Saving", new SimpleDateFormat("21/07/2019").parse("21/07/2019")));
            accounts.add(new Account("5495-6789", "David.McDonald@gmail.com", "Saving", new SimpleDateFormat("20/08/2019").parse("20/08/2019")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public static Vector<Account> getAccounts() {
        return accounts;
    }

    public static boolean addAccount(String account_number, String username, String account_type, Date opening_date) {
        if (accounts.stream().anyMatch(acc -> acc.getAccount_number().equals(account_number))) {
            System.out.println("ERROR: Account with number " + account_number + " already exists.");
            return false;
        }
        Account newAccount = new Account(account_number, username, account_type, opening_date);
        accounts.add(newAccount);
        return true;
    }

    public static boolean deleteAccount(String account_number) {
        Account accountToRemove = accounts.stream()
                .filter(acc -> acc.getAccount_number().equals(account_number))
                .findFirst()
                .orElse(null);

        if (accountToRemove == null) {
            System.out.println("ERROR: Account " + account_number + " not found.");
            return false;
        }

        accounts.remove(accountToRemove);
        TransactionController.deleteTransactionsForAccount(account_number);
        return true;
    }

    public static void printAccounts() {
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
