package tests;

import model.Account;
import utils.TestUtils;
import java.util.Date;
import java.text.SimpleDateFormat;
import controller.AccountController;
import controller.TransactionController;

public class AccountTest {

    public static void testAccountConstructor() {
        String test_account_number = "123456789";
        String test_username = "mike";
        String test_account_type = "Savings";
        Date test_opening_date = new Date(); 

        Account testAccount = new Account(test_account_number, test_username, test_account_type, test_opening_date);

        System.out.println("Starting assertions for testAccountConstructor");

        if (testAccount.getAccount_number().equals(test_account_number))
            TestUtils.printTestPassed("TC1-getAccount_number");
        else
            TestUtils.printTestFailed("TC1-getAccount_number");

        if (testAccount.getUsername_of_account_holder().equals(test_username))
            TestUtils.printTestPassed("TC2-getUsername_of_account_holder");
        else
            TestUtils.printTestFailed("TC2-getUsername_of_account_holder");

        if (testAccount.getAccount_type().equals(test_account_type))
            TestUtils.printTestPassed("TC3-getAccount_type");
        else
            TestUtils.printTestFailed("TC3-getAccount_type");

        if (testAccount.getAccount_opening_date().equals(test_opening_date))
            TestUtils.printTestPassed("TC4-getAccount_opening_date");
        else
            TestUtils.printTestFailed("TC4-getAccount_opening_date");
    }

    public static void testSetAccountNumber() {
        Account account = new Account("000000", "mike", "Savings", new Date());
        account.setAccount_number("987654321");

        if (account.getAccount_number().equals("987654321"))
            TestUtils.printTestPassed("TC5-setAccount_number");
        else
            TestUtils.printTestFailed("TC5-setAccount_number");
    }

    public static void testSetAccountOpeningDateNull() {
        Account account = new Account("123456", "mike", "Checking", new Date());
        account.setAccount_opening_date(null);

        if (account.getAccount_opening_date() == null)
            TestUtils.printTestPassed("TC6-setAccount_opening_date Null Check");
        else
            TestUtils.printTestFailed("TC6-setAccount_opening_date Null Check");
    }

    public static void testToString() {
        Date testDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(testDate);

        Account account = new Account("987654321", "john.doe@gmail.com", "Saving", testDate);

        String expectedString = String.format("%-15s| %-30s| %-10s| %-15s",
                "987654321", "john.doe@gmail.com", "Saving", formattedDate);

        if (account.toString().equals(expectedString))
            TestUtils.printTestPassed("TC11-toString (Account)");
        else
            TestUtils.printTestFailed("TC11-toString (Account)");
    }
    public static void testPreventDuplicateAccounts() {
        System.out.println("Starting test: testPreventDuplicateAccounts");

        // Setup: Add an account
        boolean firstAdd = AccountController.addAccount("123999", "test.user", "Saving", new Date());
        boolean secondAdd = AccountController.addAccount("123999", "test.user", "Saving", new Date()); // Duplicate attempt

        if (firstAdd)
            TestUtils.printTestPassed("TC1-First account creation should succeed");
        else
            TestUtils.printTestFailed("TC1-First account creation should succeed");

        if (!secondAdd)
            TestUtils.printTestPassed("TC2-Duplicate account should not be allowed");
        else
            TestUtils.printTestFailed("TC2-Duplicate account should not be allowed");
    }
    
        public static void testDeleteAccount() {
            System.out.println("Starting test: testDeleteAccount");

            // Setup: Add an account and transactions
            Date testDate = new Date();
            boolean accountAdded = AccountController.addAccount("999888", "john.doe", "Standard", testDate);
            TransactionController.addTransaction("999888", 150.00);

            if (accountAdded)
                TestUtils.printTestPassed("TC1-Account added successfully");
            else
                TestUtils.printTestFailed("TC1-Account added successfully");

            // Ensure the account exists
            boolean accountExistsBefore = AccountController.getAccounts().stream()
                    .anyMatch(acc -> acc.getAccount_number().equals("999888"));

            if (accountExistsBefore)
                TestUtils.printTestPassed("TC2-Account exists before deletion");
            else
                TestUtils.printTestFailed("TC2-Account exists before deletion");

            // Delete the account
            boolean deleteResult = AccountController.deleteAccount("999888");

            // Verify deletion
            if (deleteResult)
                TestUtils.printTestPassed("TC3-Account deleted successfully");
            else
                TestUtils.printTestFailed("TC3-Account deleted successfully");

            // Ensure account is deleted
            boolean accountExistsAfter = AccountController.getAccounts().stream()
                    .anyMatch(acc -> acc.getAccount_number().equals("999888"));

            if (!accountExistsAfter)
                TestUtils.printTestPassed("TC4-Account no longer exists after deletion");
            else
                TestUtils.printTestFailed("TC4-Account no longer exists after deletion");

            // Ensure transactions related to the deleted account are also removed
            boolean transactionsExist = TransactionController.getTransactions().stream()
                    .anyMatch(tx -> tx.getAccount_number().equals("999888"));

            if (!transactionsExist)
                TestUtils.printTestPassed("TC5-All transactions for deleted account are removed");
            else
                TestUtils.printTestFailed("TC5-All transactions for deleted account are removed");
        }


    public static void main(String[] args) {
        testAccountConstructor();
        testSetAccountNumber();
        testSetAccountOpeningDateNull();
        testToString();
        testDeleteAccount();
        testPreventDuplicateAccounts();
        
    }
}
