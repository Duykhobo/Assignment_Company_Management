package utilities;

import java.util.Scanner;

/**
 * A utility class for handling validated user input from the console.
 */
public class Inputter {

    // A single, static Scanner instance to be shared by all methods.
    private static final Scanner sc = new Scanner(System.in);

    /**
     * Gets an integer from the user within a specified range. The loop
     * continues until a valid integer in the range is entered.
     *
     * @param inputMsg The message to display to the user.
     * @param errorMsg The error message to display for invalid input.
     * @param lowerBound The minimum acceptable value (inclusive).
     * @param upperBound The maximum acceptable value (inclusive).
     * @return The valid integer entered by the user.
     */
    public static int getAnInteger(String inputMsg, String errorMsg, int lowerBound, int upperBound) {
        if (lowerBound > upperBound) { // Swap if bounds are in wrong order
            int tmp = lowerBound;
            lowerBound = upperBound;
            upperBound = tmp;
        }

        while (true) {
            try {
                System.out.print(inputMsg);
                int number = Integer.parseInt(sc.nextLine());
                if (number < lowerBound || number > upperBound) {
                    System.out.println(errorMsg);
                } else {
                    return number;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. " + errorMsg);
            }
        }
    }

    /**
     * Gets a non-empty string from the user. The loop continues until the user
     * enters a non-blank string.
     *
     * @param inputMsg The message to display to the user.
     * @param errorMsg The error message for empty input.
     * @return The non-empty string entered by the user.
     */
    public static String getAString(String inputMsg, String errorMsg) {
        while (true) {
            System.out.print(inputMsg);
            String str = sc.nextLine().trim();
            if (str.isEmpty()) {
                System.out.println(errorMsg);
            } else {
                return str;
            }
        }
    }

    /**
     * Gets a non-empty string that matches a given regex pattern.
     *
     * @param inputMsg The message to display to the user.
     * @param errorMsg The error message for non-matching input.
     * @param regex The regular expression pattern to match against.
     * @return The validated string.
     */
    public static String getAString(String inputMsg, String errorMsg, String regex) {
        while (true) {
            // Reuses the method above to ensure the string is not empty first
            String str = getAString(inputMsg, "Input cannot be empty.");
            if (!str.matches(regex)) {
                System.out.println(errorMsg);
            } else {
                return str;
            }
        }
    }

    /**
     * Gets a double from the user within a specified range.
     *
     * @param inputMsg The message to display to the user.
     * @param errorMsg The error message for invalid input.
     * @param lowerBound The minimum acceptable value (inclusive).
     * @param upperBound The maximum acceptable value (inclusive).
     * @return The valid double entered by the user.
     */
    public static double getADouble(String inputMsg, String errorMsg, double lowerBound, double upperBound) {
        if (lowerBound > upperBound) { // Swap if bounds are in wrong order
            double tmp = lowerBound;
            lowerBound = upperBound;
            upperBound = tmp;
        }

        while (true) {
            try {
                System.out.print(inputMsg);
                double number = Double.parseDouble(sc.nextLine());
                if (number < lowerBound || number > upperBound) {
                    System.out.println(errorMsg);
                } else {
                    return number;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. " + errorMsg);
            }
        }
    }

    /**
     * Gets a string from the user, allowing it to be blank. This is useful for
     * update functions where the user can press Enter to skip.
     *
     * @param inputMsg The message to display to the user.
     * @return The string entered by the user (can be empty).
     */
    public static String getStringWithBlank(String inputMsg) {
        System.out.print(inputMsg);
        return sc.nextLine();
    }

    public static int getAnInteger(String inputMsg, String errMsg) {
        while (true) {
            try {
                System.out.println(inputMsg);
                int number = Integer.parseInt(sc.nextLine());
                return number;
            } catch (NumberFormatException e) {
                System.out.println(errMsg);
            }
        }
    }

    public static double getADouble(String inputMsg, String errMsg) {
        while (true) {
            try {
                System.out.println(inputMsg);
                double number = Double.parseDouble(sc.nextLine());
                return number;
            } catch (NumberFormatException e) {
                System.out.println(errMsg);
            }
        }
    }
}
