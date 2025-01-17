package org.example;
import org.example.input.InputService;
import org.example.model.Animal;
import org.example.model.Barrel;
import org.example.model.Person;
import org.example.Sorted.CustomSort;
import org.example.Sorted.TimSort;

import java.util.Comparator;
import java.util.Scanner;


import java.util.*;


public class App {
    public static void main( String[] args ) {
        Scanner scanner = new Scanner(System.in);

        boolean flag = true;

        while (flag) {
            System.out.println();
            System.out.println("Выберите действие:");
            System.out.println("1. Выбрать тип объекта (Animal, Barrel, Person)");
            System.out.println("2. Выйти.");
            System.out.print("Ваш выбор: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> processObjectType(scanner);
                case 2 -> {
                    System.out.println("Выход из программы...");
                    flag = false;
                }
                default -> System.out.println("Неверный выбор. Повторите попытку.");
            }
        }

    }

    private static void processObjectType(Scanner scanner) {
        System.out.println();
        System.out.println("Выберите тип объекта:");
        System.out.println("1. Animal");
        System.out.println("2. Barrel");
        System.out.println("3. Person");
        System.out.print("Ваш выбор: ");
        int objectType = scanner.nextInt();
        scanner.nextLine();

        switch (objectType) {
            case 1 -> processSorting(scanner, Animal.class);
            case 2 -> processSorting(scanner, Barrel.class);
            case 3 -> processSorting(scanner, Person.class);
            default -> System.out.println("Неверный выбор. Повторите попытку.");
        }
    }

    private static <T> void processSorting(Scanner scanner, Class<T> type) {
        System.out.println();
        System.out.println("Выберите тип сортировки:");
        System.out.println("1. Обычная");
        System.out.println("2. Кастомная");
        System.out.print("Ваш выбор: ");
        int sortType = scanner.nextInt();
        scanner.nextLine();

        Comparator<T> comparator;

        if (sortType > 0 && sortType < 3) {
            if (type == Animal.class) {
                comparator = (Comparator<T>) (sortType == 1 ? CustomSort.getAnimalComparator() : CustomSort.getCustomAnimalComparator());
            } else if (type == Barrel.class) {
                comparator = (Comparator<T>) (sortType == 1 ? CustomSort.getBarrelComparator() : CustomSort.getCustomBarrelComparator());
            } else if (type == Person.class) {
                comparator = (Comparator<T>) (sortType == 1 ? CustomSort.getPersonComparator() : CustomSort.getCustomPersonComparator());
            } else {
                System.out.println("Неизвестный тип объекта.");
                return;
            }

            T[] dataArray = getDataArray(scanner, type);
            if (dataArray == null) return;

            // Сортировка массива
            TimSort<T> timSort = new TimSort<>();
            timSort.sort(dataArray, comparator);
            System.out.println("Отсортированный массив: " + Arrays.toString(dataArray));

            // Бинарный поиск
            System.out.println("Введите ключ для бинарного поиска:");
            String key = scanner.nextLine().toLowerCase();
            T keyObject = parseKey(key, type);
            if (keyObject != null) {
                BinarySearch<T> binarySearch = new BinarySearch<>();
                binarySearch.findElement(dataArray, keyObject, comparator);
                System.out.println();
            }
        } else {
            System.out.println("Ошибка. Неверный номер сортировки. Попробуйте еще раз.");
            System.out.println();
        }

    }

    private static <T> T[] getDataArray(Scanner scanner, Class<T> type) {
        System.out.println();
        System.out.println("Выберите источник данных:");
        System.out.println("1. Вручную");
        System.out.println("2. Из файла");
        System.out.println("3. Случайно");
        System.out.print("Ваш выбор: ");
        int dataSource = scanner.nextInt();
        scanner.nextLine();

        InputService inputService = new InputService();

        switch (dataSource) {
            case 1:
                System.out.print("Введите количество элементов: ");
                int manualSize = scanner.nextInt();
                scanner.nextLine();
                return inputService.readFromConsole(manualSize, type);
            case 2:
                System.out.print("Введите путь к файлу: ");
                String filePath = scanner.nextLine();
                return inputService.readFromFile(filePath, type);
            case 3:
                System.out.print("Введите количество элементов: ");
                int randomSize = scanner.nextInt();
                scanner.nextLine();
                return inputService.generateRandom(randomSize, type);
            default:
                System.out.println("Неверный выбор. Повторите попытку.");
                return null;
        }
    }

    private static <T> T parseKey(String key, Class<T> type) {
        try {
            String[] fields = key.split(",");
            if (type == Animal.class) {
                String species = fields[0].trim();
                String eyeColor = fields[1].trim();
                boolean hasFur = Boolean.parseBoolean(fields[2].trim());
                return type.cast(new Animal.Builder()
                        .species(species)
                        .eyeColor(eyeColor)
                        .hasFur(hasFur)
                        .build());
            } else if (type == Barrel.class) {
                int volume = Integer.parseInt(fields[0]);
                String material = fields[1].trim();
                String storedMaterial = fields[2].trim();
                return type.cast(new Barrel.Builder()
                        .volume(volume)
                        .storedMaterial(storedMaterial)
                        .material(material)
                        .build());
            } else if (type == Person.class) {
                String gender = fields[0].trim();
                int age = Integer.parseInt(fields[1]);
                String lastName = fields[2].trim();
                return type.cast(new Person.Builder()
                        .gender(gender)
                        .age(age)
                        .lastName(lastName)
                        .build());
            } else {
                System.out.println("Неизвестный тип ключа.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Некорректный формат ключа: " + e.getMessage());
            return null;
        }
    }

//    private static <T> T[] inputDataManually(Scanner scanner, Class<T> type) {
//        System.out.println();
//        System.out.println("Введите количество элементов: ");
//        int size = scanner.nextInt();
//        scanner.nextLine();
//        T[] array = (T[]) new Object[size];
//        for (int i = 0; i < size; i++) {
//            System.out.println();
//            System.out.println("Введите данные для элемента " + (i + 1) + ":");
//            String input = scanner.nextLine();
//            T obj = parseKey(input, type);
//            if (obj != null) {
//                array[i] = obj;
//            } else {
//                System.out.println();
//                System.out.println("Ошибка при вводе данных.");
//                i--;
//            }
//        }
//        return array;
//    }
//
//    private static <T> T parseKey(String input, Class<T> type) {
//        try {
//            if (type == Animal.class) {
//                String[] parts = input.split(",");
//                String species = parts[0];
//                String eyeColor = parts[1];
//                boolean hasFur = Boolean.parseBoolean(parts[2]);
//                return type.cast(new Animal.Builder()
//                        .species(species)
//                        .eyeColor(eyeColor)
//                        .hasFur(hasFur)
//                        .build());
//            } else if (type == Barrel.class) {
//                String[] parts = input.split(",");
//                int volume = Integer.parseInt(parts[0]);
//                String material = parts[1];
//                String storedMaterial = parts[2];
//                return type.cast(new Barrel.Builder()
//                        .volume(volume)
//                        .storedMaterial(storedMaterial)
//                        .material(material)
//                        .build());
//            } else if (type == Person.class) {
//                String[] parts = input.split(",");
//                String gender = parts[0];
//                int age = Integer.parseInt(parts[1]);
//                String lastName = parts[2];
//                return type.cast(new Person.Builder()
//                        .gender(gender)
//                        .age(age)
//                        .lastName(lastName)
//                        .build());
//            } else {
//                System.out.println();
//                System.out.println("Неизвестный тип объекта.");
//                return null;
//            }
//        } catch (Exception e) {
//            System.out.println();
//            System.out.println("Ошибка при разборе данных: " + e.getMessage());
//            return null;
//        }
//    }
}
