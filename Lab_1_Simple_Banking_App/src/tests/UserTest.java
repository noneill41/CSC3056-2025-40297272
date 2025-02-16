package tests;
import model.User;
import utils.TestUtils;
import controller.UserController;

public class UserTest {
	public static void testAddUser() {
	    System.out.println("Starting test: testAddUser");
	    boolean success = UserController.addUser("test.user", "test123", "Test", "User", "07123456789");

	    if (success)
	        TestUtils.printTestPassed("TC1-User should be added successfully");
	    else
	        TestUtils.printTestFailed("TC1-User should be added successfully");
	    
	    boolean userExists = UserController.getUsers().stream()
	            .anyMatch(user -> user.getUsername().equals("test.user"));

	    if (userExists)
	        TestUtils.printTestPassed("TC2-User should exist in the system after being added");
	    else
	        TestUtils.printTestFailed("TC2-User should exist in the system after being added");

	    boolean duplicateUser = UserController.addUser("test.user", "test123", "Test", "User", "07123456789");

	    if (!duplicateUser)
	        TestUtils.printTestPassed("TC3-Duplicate user should not be allowed");
	    else
	        TestUtils.printTestFailed("TC3-Duplicate user should not be allowed");

	    boolean invalidUser = UserController.addUser("", "password", "No", "Name", "0700000000");

	    if (!invalidUser)
	        TestUtils.printTestPassed("TC4-User with an empty username should not be added");
	    else
	        TestUtils.printTestFailed("TC4-User with an empty username should not be added");
	}

	public static void testDeleteUser() {
	    System.out.println("Starting test: testDeleteUser");

	    UserController.addUser("delete.user", "password", "Delete", "User", "07111222333");

	    boolean userExistsBefore = UserController.getUsers().stream()
	            .anyMatch(user -> user.getUsername().equals("delete.user"));

	    if (userExistsBefore)
	        TestUtils.printTestPassed("TC1-User should exist before deletion");
	    else
	        TestUtils.printTestFailed("TC1-User should exist before deletion");

	    boolean success = UserController.deleteUser("delete.user");

	    if (success)
	        TestUtils.printTestPassed("TC2-User should be deleted successfully");
	    else
	        TestUtils.printTestFailed("TC2-User should be deleted successfully");

	    boolean userExistsAfter = UserController.getUsers().stream()
	            .anyMatch(user -> user.getUsername().equals("delete.user"));

	    if (!userExistsAfter)
	        TestUtils.printTestPassed("TC3-User should no longer exist after deletion");
	    else
	        TestUtils.printTestFailed("TC3-User should no longer exist after deletion");

	    boolean secondDeleteAttempt = UserController.deleteUser("delete.user");

	    if (!secondDeleteAttempt)
	        TestUtils.printTestPassed("TC4-Deleting a non-existent user should fail");
	    else
	        TestUtils.printTestFailed("TC4-Deleting a non-existent user should NOT be successful");
	}

	public static void testAuthenticateUser() {
	    System.out.println("Starting test: testAuthenticateUser");

	    boolean userAdded = UserController.addUser("auth.user", "securepass", "Auth", "User", "0700111222");

	    if (userAdded)
	        TestUtils.printTestPassed("TC1-User should be added successfully for authentication testing");
	    else
	        TestUtils.printTestFailed("TC1-User should be added successfully for authentication testing");

	    boolean userExists = UserController.getUsers().stream()
	            .anyMatch(user -> user.getUsername().equals("auth.user"));

	    if (userExists)
	        TestUtils.printTestPassed("TC2-User should exist before authentication");
	    else
	        TestUtils.printTestFailed("TC2-User should exist before authentication");

	    boolean correctAuth = UserController.authenticateUser("auth.user", "securepass");

	    if (correctAuth)
	        TestUtils.printTestPassed("TC3-Authentication should succeed with the correct password");
	    else
	        TestUtils.printTestFailed("TC3-Authentication should succeed with the correct password");

	    boolean wrongAuth = UserController.authenticateUser("auth.user", "wrongpass");

	    if (!wrongAuth)
	        TestUtils.printTestPassed("TC4-Authentication should fail with an incorrect password");
	    else
	        TestUtils.printTestFailed("TC4-Authentication should fail with an incorrect password");

	    boolean nonExistentAuth = UserController.authenticateUser("nonexistent.user", "somepassword");

	    if (!nonExistentAuth)
	        TestUtils.printTestPassed("TC5-Authentication should fail for a non-existent user");
	    else
	        TestUtils.printTestFailed("TC5-Authentication should fail for a non-existent user");

	    boolean emptyPasswordAuth = UserController.authenticateUser("auth.user", "");

	    if (!emptyPasswordAuth)
	        TestUtils.printTestPassed("TC6-Authentication should fail with an empty password");
	    else
	        TestUtils.printTestFailed("TC6-Authentication should fail with an empty password");
	}
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
        assert testUser.getPassword().equals(test_password) : "getPassword() test failed!";
        assert testUser.getFirst_name().equals(test_first_name) : "getFirst_name() test failed!";
        assert testUser.getLast_name().equals(test_last_name) : "getLast_name() test failed!";
        assert testUser.getMobile_number().equals(test_mobile_number) : "getMobile_number() test failed!";
        
        System.out.println("All Java assertions in the test suite passed (none failed).");
    }
    public static void main(String[] args) {
        testUserConstructor();
        testAddUser();
        testDeleteUser();
        testAuthenticateUser();
    }
}