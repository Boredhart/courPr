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

    public static boolean isValidPersonData(String data) {

        // Логика проверки корректности данных для Person
        String[] fields = data.split(",");
        if (fields.length != 3) {
            return false;
        }
        String gender = fields[0].trim();
        String age = fields[1].trim();
        String lastName = fields[2].trim();
        return isValidString(gender) && isValidInteger(age) && isValidString(lastName);
    }

    public static boolean isValidAnimalData(String data) {

        // Логика проверки корректности данных для Animal
        String[] fields = data.split(",");
        if (fields.length != 3) {
            return false;
        }
        String species = fields[0].trim();
        String eyeColor = fields[1].trim();
        String hasFur = fields[2].trim();
        return isValidString(species) && isValidString(eyeColor) && isValidBoolean(hasFur);
    }

    public static boolean isValidBarrelData(String data) {

        // Логика проверки корректности данных для Barrel
        String[] fields = data.split(",");
        if (fields.length != 3) {
            return false;
        }
        String volume = fields[0].trim();
        String material = fields[1].trim();
        String storedMaterial = fields[2].trim();
        return isValidInteger(volume) && isValidString(material) && isValidString(storedMaterial);
    }
}
