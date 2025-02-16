package tests;

import controller.UserController;
import controller.AccountController;
import controller.TransactionController;
import utils.TestUtils;

public class SimpleBankingAppTest {

    public static void testLoadData() {
        // Load Users & Accounts
        UserController.loadUserData();
        AccountController.loadAccountData();

        if (UserController.getUsers().size() == 3)
            System.out.println(TestUtils.TEXT_COLOR_GREEN + "testLoadData: loadUserData: TC1 passed" + TestUtils.TEXT_COLOR_RESET);
        else
            System.out.println(TestUtils.TEXT_COLOR_RED + "testLoadData: loadUserData: TC1 FAILED" + TestUtils.TEXT_COLOR_RESET);

        if (AccountController.getAccounts().size() == 4)
            System.out.println(TestUtils.TEXT_COLOR_GREEN + "testLoadData: loadAccountData: TC1 passed" + TestUtils.TEXT_COLOR_RESET);
        else
            System.out.println(TestUtils.TEXT_COLOR_RED + "testLoadData: loadAccountData: TC1 FAILED" + TestUtils.TEXT_COLOR_RESET);
    }

    public static void testDeposits() {
        double balanceBefore = TransactionController.getBalance("5495-1234");
        double depositAmount = 50.21;

        TransactionController.addTransaction("5495-1234", depositAmount);
        double balanceAfter = TransactionController.getBalance("5495-1234");

        if (balanceBefore + depositAmount == balanceAfter)
            System.out.println(TestUtils.TEXT_COLOR_GREEN + "testDeposits: TC1 passed" + TestUtils.TEXT_COLOR_RESET);
        else {
            System.out.println(TestUtils.TEXT_COLOR_RED + "testDeposits: TC1 FAILED" + TestUtils.TEXT_COLOR_RESET);
            System.out.format("testDeposits: balanceBefore = %.2f ; depositAmount = %.2f ; balanceAfter = %.2f%n",
                    balanceBefore, depositAmount, balanceAfter);
        }

        // Teardown: Revert the deposit to restore the original state
        TransactionController.addTransaction("5495-1234", -depositAmount);
    }

    public static void testWithdrawals() {
        double balanceBefore = TransactionController.getBalance("5495-1234");
        double withdrawalAmount = 75.50;

        TransactionController.addTransaction("5495-1234", -withdrawalAmount);
        double balanceAfter = TransactionController.getBalance("5495-1234");

        if (balanceBefore - withdrawalAmount == balanceAfter)
            System.out.println(TestUtils.TEXT_COLOR_GREEN + "testWithdrawals: TC1 passed" + TestUtils.TEXT_COLOR_RESET);
        else {
            System.out.println(TestUtils.TEXT_COLOR_RED + "testWithdrawals: TC1 FAILED" + TestUtils.TEXT_COLOR_RESET);
            System.out.format("testWithdrawals: balanceBefore = %.2f ; withdrawalAmount = %.2f ; balanceAfter = %.2f%n",
                    balanceBefore, withdrawalAmount, balanceAfter);
        }

        // Teardown: Revert the withdrawal to restore the original state
        TransactionController.addTransaction("5495-1234", withdrawalAmount);
    }

    public static void main(String[] args) {
        testLoadData();
        testDeposits();
        testWithdrawals();
    }
}
