package controller;

import model.Transaction;
import java.util.Calendar;
import java.util.Vector;

public class TransactionController {
    private static Vector<Transaction> transactions = new Vector<>();

    public static void addTransaction(String account_number, double amount) {
        transactions.add(new Transaction(account_number, amount, Calendar.getInstance().getTime()));
    }

    public static double getBalance(String account_number) {
        double balance = 0.0;
        for (Transaction transaction : transactions) {
            if (transaction.getAccount_number().equals(account_number)) {
                balance += transaction.getTransaction_amount();
            }
        }
        return balance;
    }

    public static Vector<Transaction> getTransactions() {
        return transactions;
    }
    public static String getBalanceFormatted(String account_number) {
        double balance = getBalance(account_number);
        return String.format("%.2f", balance); // Formats balance to always have two decimal places
    }
    public static void deleteTransactionsForAccount(String account_number) {
        transactions.removeIf(transaction -> transaction.getAccount_number().equals(account_number));
    }
}
