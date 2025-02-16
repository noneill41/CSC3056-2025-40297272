package tests;

import model.Transaction;
import utils.TestUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import controller.TransactionController;

public class TransactionTest {

    public static void testTransactionConstructor() {
        String test_account_number = "987654321";
        double test_transaction_amount = 150.75;
        Date test_transaction_date = new Date();

        Transaction testTransaction = new Transaction(test_account_number, test_transaction_amount, test_transaction_date);

        System.out.println("Starting assertions for testTransactionConstructor");

        if (testTransaction.getAccount_number().equals(test_account_number))
            TestUtils.printTestPassed("TC1-getAccount_number");
        else
            TestUtils.printTestFailed("TC1-getAccount_number");

        if (testTransaction.getTransaction_amount() == test_transaction_amount)
            TestUtils.printTestPassed("TC2-getTransaction_amount");
        else
            TestUtils.printTestFailed("TC2-getTransaction_amount");

        if (testTransaction.getTransaction_date().equals(test_transaction_date))
            TestUtils.printTestPassed("TC3-getTransaction_date");
        else
            TestUtils.printTestFailed("TC3-getTransaction_date");
    }

    public static void testSetAccountNumber() {
        Transaction transaction = new Transaction("123456", 200.50, new Date());
        transaction.setAccount_number("654321");

        if (transaction.getAccount_number().equals("654321"))
            TestUtils.printTestPassed("TC4-setAccount_number");
        else
            TestUtils.printTestFailed("TC4-setAccount_number");
    }

    public static void testSetTransactionAmount() {
        Transaction transaction = new Transaction("123456", 300.00, new Date());
        transaction.setTransaction_amount(500.75);

        if (transaction.getTransaction_amount() == 500.75)
            TestUtils.printTestPassed("TC5-setTransaction_amount");
        else
            TestUtils.printTestFailed("TC5-setTransaction_amount");
    }

    public static void testSetTransactionDate() {
        Date oldDate = new Date(System.currentTimeMillis() - 86400000L);
        Date newDate = new Date();

        Transaction transaction = new Transaction("123456", 400.00, oldDate);
        transaction.setTransaction_date(newDate);

        if (transaction.getTransaction_date().equals(newDate))
            TestUtils.printTestPassed("TC6-setTransaction_date");
        else
            TestUtils.printTestFailed("TC6-setTransaction_date");
    }

    public static void testNullValues() {
        Transaction transaction = new Transaction(null, 0.0, null);

        if (transaction.getAccount_number() == null)
            TestUtils.printTestPassed("TC7-getAccount_number Null Check");
        else
            TestUtils.printTestFailed("TC7-getAccount_number Null Check");

        if (transaction.getTransaction_date() == null)
            TestUtils.printTestPassed("TC8-getTransaction_date Null Check");
        else
            TestUtils.printTestFailed("TC8-getTransaction_date Null Check");
    }
    
    public static void testToString() {
        Date testDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = formatter.format(testDate);

        Transaction transaction = new Transaction("987654321", 200.50, testDate);

        String expectedString = String.format("%-15s| %-10.2f| %-20s",
                "987654321", 200.50, formattedDate);

        if (transaction.toString().equals(expectedString))
            TestUtils.printTestPassed("TC12-toString (Transaction)");
        else
            TestUtils.printTestFailed("TC12-toString (Transaction)");
    }
    public static void testBalanceFormatting() {
        System.out.println("Starting test: testBalanceFormatting");

        // Setup: Add some transactions
        TransactionController.addTransaction("5495-1234", 100.5);
        TransactionController.addTransaction("5495-1234", -50.25);
        String expectedBalance = "50.25";
        String actualBalance = TransactionController.getBalanceFormatted("5495-1234");

        // Verify balance is formatted correctly
        if (actualBalance.equals(expectedBalance))
            TestUtils.printTestPassed("TC1-Balance formatting is correct");
        else
            TestUtils.printTestFailed("TC1-Balance formatting is correct: Expected " + expectedBalance + " but got " + actualBalance);
    }


    public static void main(String[] args) {
        testTransactionConstructor();
        testSetAccountNumber();
        testSetTransactionAmount();
        testSetTransactionDate();
        testNullValues();
        testToString();
        testBalanceFormatting();
    }
}

