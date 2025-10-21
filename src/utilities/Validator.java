package utilities;

import java.util.List;

public class Validator {

    public static String getValidatedStringInput(String message, String currentValue) {
        String input = Inputter.getStringWithBlank(message + " (current: " + currentValue + "): ");
        return input.trim().isEmpty() ? null : input;
    }

    public static int getValidatedIntInput(String message, int currentValue, int min, int max) {
        while (true) {
            String inputStr = Inputter.getStringWithBlank(message + " (current: " + currentValue + "): ");
            if (inputStr.trim().isEmpty()) {
                return -1;
            }
            try {
                int newValue = Integer.parseInt(inputStr);
                if (newValue >= min && newValue <= max) {
                    return newValue;
                } else {
                    System.out.println("Error: Value must be between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid number format.");
            }
        }
    }

    public static double getValidatedDoubleInput(String message, double currentValue, double min, double max) {
        while (true) {
            String inputStr = Inputter.getStringWithBlank(message + " (current: " + currentValue + "): ");
            if (inputStr.trim().isEmpty()) {
                return -1;
            }
            try {
                double newValue = Double.parseDouble(inputStr);
                if (newValue >= min && newValue <= max) {
                    return newValue;
                } else {
                    System.out.println("Error: Value must be between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid number format.");
            }
        }
    }

    public static List<String> getValidatedLanguagesInput() {
        String langStr = Inputter
                .getStringWithBlank("Enter new programming languages (comma-separated, press Enter to skip): ");
        if (langStr.trim().isEmpty()) {
            return null;
        }
        String[] langArray = langStr.split(",");
        List<String> langList = new java.util.ArrayList<>();
        for (String lang : langArray) {
            langList.add(lang.trim());
        }
        return langList;
    }

    public static String getValidatedTesterTypeInput(String currentValue) {
        while (true) {
            String newTypeStr = Inputter
                    .getStringWithBlank("Enter new testing type (AM/MT) (current: " + currentValue + "): ");
            if (newTypeStr.trim().isEmpty()) {
                return null;
            }
            if (newTypeStr.matches(RegexConst.REG_TEST_TYPE)) {
                return newTypeStr;
            } else {
                System.out.println("Error: Type must be 'AM' or 'MT'.");
            }
        }
    }
}
