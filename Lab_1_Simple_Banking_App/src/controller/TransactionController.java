package controller;

import model.Transaction;
import java.util.Calendar;
import java.util.Vector;

public class TransactionController {
    private static Vector<Transaction> transactions = new Vector<>();

    public static void addTransaction(String account_number, double amount) {
        if (!accountExists(account_number)) {
            System.out.println("ERROR: Transaction failed. Account " + account_number + " does not exist.");
            return;
        }
        transactions.add(new Transaction(account_number, amount, Calendar.getInstance().getTime()));
    }

    public static double getBalance(String account_number) {
        return transactions.stream()
                .filter(transaction -> transaction.getAccount_number().equals(account_number))
                .mapToDouble(Transaction::getTransaction_amount)
                .sum();
    }

    public static String getBalanceFormatted(String account_number) {
        double balance = getBalance(account_number);
        return String.format("%.2f", balance);
    }

    public static void deleteTransactionsForAccount(String account_number) {
        transactions.removeIf(transaction -> transaction.getAccount_number().equals(account_number));
    }

    public static boolean accountExists(String account_number) {
        return AccountController.getAccounts().stream()
                .anyMatch(acc -> acc.getAccount_number().equals(account_number));
    }

    public static Vector<Transaction> getTransactions() {
        return transactions;
    }
}
