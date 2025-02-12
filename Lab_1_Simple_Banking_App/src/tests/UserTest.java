package tests;
import model.User;
import utils.TestUtils;

public class UserTest {
    public static void testUserConstructor() {
        String test_username = "mike";
        String test_password = "my_passwd";
        String test_first_name = "Mike";
        String test_last_name = "Smith";
        String test_mobile_number = "07771234567";

        User testUser = new User(test_username, test_password, test_first_name, test_last_name, test_mobile_number);

        System.out.println("Starting the assertions of the test method: testUserConstructor");

        if (testUser.getUsername() != null && testUser.getUsername().equals(test_username))
            TestUtils.printTestPassed("TC1-getUsername");
        else
            TestUtils.printTestFailed("TC1-getUsername");

        if (testUser.getPassword() != null && testUser.getPassword().equals(test_password))
            TestUtils.printTestPassed("TC2-getPassword");
        else
            TestUtils.printTestFailed("TC2-getPassword");

        if (testUser.getFirst_name() != null && testUser.getFirst_name().equals(test_first_name))
            TestUtils.printTestPassed("TC3-getFirst_name");
        else
            TestUtils.printTestFailed("TC3-getFirst_name");

        if (testUser.getLast_name() != null && testUser.getLast_name().equals(test_last_name))
            TestUtils.printTestPassed("TC4-getLast_name");
        else
            TestUtils.printTestFailed("TC4-getLast_name");

        if (testUser.getMobile_number() != null && testUser.getMobile_number().equals(test_mobile_number))
            TestUtils.printTestPassed("TC5-getMobile_number");
        else
            TestUtils.printTestFailed("TC5-getMobile_number");
        
        assert testUser.getUsername().equals(test_username) : "getUsername() test failed!";
        assert testUser.getPassword().equals("wrong_password") : "getPassword() test failed!";
        assert testUser.getFirst_name().equals(test_first_name) : "getFirst_name() test failed!";
        assert testUser.getLast_name().equals(test_last_name) : "getLast_name() test failed!";
        assert testUser.getMobile_number().equals(test_mobile_number) : "getMobile_number() test failed!";
        
        System.out.println("All Java assertions in the test suite passed (none failed).");
    }
    public static void main(String[] args) {
        testUserConstructor();
    }
}