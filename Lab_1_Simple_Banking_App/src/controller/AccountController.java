package controller;

import model.Account;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.Date;

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
        // Check if the account already exists
        if (accounts.stream().anyMatch(acc -> acc.getAccount_number().equals(account_number))) {
            System.out.println("ERROR: Account with number " + account_number + " already exists.");
            return false;
        }
        // If unique, add new account
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

        // Remove account and its transactions
        accounts.remove(accountToRemove);
        TransactionController.deleteTransactionsForAccount(account_number);
        return true;
    }
}
