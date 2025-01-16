package org.example.input;

public class DataValidator {
    public static boolean isValidString(String input) {
        return input != null && !input.trim().isEmpty();
    }

    public static boolean isValidInteger(String input) {
        if(!isValidString(input)) return false;
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static boolean isValidBoolean(String input) {
        if (!isValidString(input)) {
            return false;
        }
        String lowerCaseInput = input.trim().toLowerCase();
        return lowerCaseInput.equals("true") || lowerCaseInput.equals("false");
    }
}
