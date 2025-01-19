package org.example.input;

import java.util.InputMismatchException;
import java.util.Scanner;

public class DataValidator {

    public static boolean isValidString(String input) {
        return input != null && !input.trim().isEmpty() && input.length() <= 12;
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

    public static int enterNum(Scanner scanner) {
        System.out.println();
        System.out.print("Введите количество элементов: ");

        try {
            int manualSize = scanner.nextInt();
            scanner.nextLine();
            if (manualSize < 0) return -919793;
            return manualSize;
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return -919793;
        }
    }
}
