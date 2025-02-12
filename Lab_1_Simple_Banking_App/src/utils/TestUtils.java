package utils;

public class TestUtils {
    public static final String TEXT_COLOR_GREEN = "\u001B[32m"; 
    public static final String TEXT_COLOR_RED = "\u001B[31m"; 
    public static final String TEXT_COLOR_RESET = "\u001B[0m"; 

    public static void printTestPassed(String testCaseName) {
        System.out.println(TEXT_COLOR_GREEN + testCaseName + "-Passed" + TEXT_COLOR_RESET);
    }
    public static void printTestFailed(String testCaseName) {
        System.out.println(TEXT_COLOR_RED + testCaseName + "-FAILED" + TEXT_COLOR_RESET);
    }
}
