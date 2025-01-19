package org.example;

import org.example.input.InputService;
import org.example.model.*;
import org.example.sorted.*;
import org.example.output.OutputService;
import java.util.*;

import static org.example.input.DataValidator.enterNum;


public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;

        cycle(scanner, flag);
    }

    public static void cycle(Scanner scanner, boolean flag) {
        while (flag) {
            System.out.println();
            System.out.println("Выберите действие:");
            System.out.println("1. Создать массив объектов.");
            System.out.println();
            System.out.println("-----------------------------");
            System.out.println("5. Выйти.");
            System.out.print("Ваш выбор: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> processObjectType(scanner);
                case 5 -> {
                    System.out.println("Выход из программы...");
                    flag = false;
                }
                default -> {
                    System.out.println();
                    System.out.println("Неверный выбор. Повторите попытку.");
                }
            }
        }
    }

    private static void processObjectType(Scanner scanner) {
        System.out.println();
        System.out.println("Выберите тип объекта:");
        System.out.println("1. Animal");
        System.out.println("2. Barrel");
        System.out.println("3. Person");
        System.out.println();
        System.out.println("-----------------------------");
        System.out.println("4. Назад.");
        System.out.println("5. Выйти.");
        System.out.print("Ваш выбор: ");
        int objectType = scanner.nextInt();
        scanner.nextLine();

        switch (objectType) {
            case 1 -> {
                Animal[] dataArray = getDataArray(scanner, Animal.class);
                processSorting(scanner, Animal.class, dataArray);
            }
            case 2 -> {
                Barrel[] dataArray = getDataArray(scanner, Barrel.class);
                processSorting(scanner, Barrel.class, dataArray);
            }
            case 3 -> {
                Person[] dataArray = getDataArray(scanner, Person.class);
                processSorting(scanner, Person.class, dataArray);
            }
            case 4 -> {
                System.out.println();
                System.out.println("Вовзрат на предыдущий шаг.");
                cycle(scanner, true);
            }
            case 5 -> {
                System.out.println();
                System.out.println("Выход из программы...");
                System.exit(0);
            }
            default -> {
                System.out.println();
                System.out.println("Неверный выбор. Повторите попытку.");
                processObjectType(scanner);
            }
        }
    }

    private static <T> T[] getDataArray(Scanner scanner, Class<T> type) {
        System.out.println();
        System.out.println("Выберите источник данных:");
        System.out.println("1. Вручную.");
        System.out.println("2. Из файла.");
        System.out.println("3. Случайно.");
        System.out.println();
        System.out.println("-----------------------------");
        System.out.println("4. Назад.");
        System.out.println("5. Выйти.");
        System.out.print("Ваш выбор: ");
        int dataSource = scanner.nextInt();
        scanner.nextLine();

        InputService inputService = new InputService();

        switch (dataSource) {
            case 1:
                int manualSize = enterNum(scanner);

                while (manualSize == -919793) {
                    System.out.println("Ошибка ввода: пожалуйста, введите целое число больше 0.");
                    manualSize = enterNum(scanner);
                }

                return inputService.readFromConsole(manualSize, type);
            case 2:
                System.out.println();
                System.out.print("Введите путь к файлу: ");
                String filePath = scanner.nextLine();
                T[] result = inputService.readFromFile(filePath, type);
                if (result != null && result.length != 0) {
                    return result;
                } else {
                    System.out.println();
                    System.out.println("Неверный путь. Попробуйте еще раз.");
                    getDataArray(scanner, type);
                }
                break;
            case 3:
                System.out.println();
                System.out.print("Введите количество элементов: ");
                int randomSize = scanner.nextInt();
                scanner.nextLine();
                return inputService.generateRandom(randomSize, type);
            case 4:
                System.out.println();
                System.out.println("Возврат к предыдущему шагу.");
                processObjectType(scanner);
                break;
            case 5:
                System.out.println();
                System.out.println("Выход из программы...");
                System.exit(0);
            default:
                System.out.println();
                System.out.println("Неверный выбор. Повторите попытку.");
                return getDataArray(scanner, type);
        }
        return null;
    }

    private static <T> void processSorting(Scanner scanner, Class<T> type, T[] dataArray) {
        OutputService outputService = new OutputService();

        System.out.println();
        System.out.println("Выберите тип сортировки:");
        System.out.println("1. Обычная");
        System.out.println("2. Кастомная");
        System.out.println();
        System.out.println("-----------------------------");
        System.out.println("4. Назад.");
        System.out.println("5. Выйти.");
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
                System.out.println();
                System.out.println("Неизвестный тип объекта.");
                return;
            }

            try {
                // Сортировка массива
                TimSort<T> timSort = new TimSort<>();
                timSort.sort(dataArray, comparator);
                System.out.println();
                System.out.println("Отсортированный массив: ");
                Arrays.stream(dataArray).forEach(System.out::println);

                saveRequest(scanner, outputService, type, dataArray, comparator);
            } catch (NullPointerException e) {
                System.out.println();
                System.out.println("Массив пуст. Попробуйте еще раз.");
                getDataArray(scanner, type);
            }


        } else if (sortType == 4) {
            System.out.println();
            System.out.println("Вовзрат на предыдущий шаг.");
            getDataArray(scanner, type);
        } else if (sortType == 5) {
            System.out.println();
            System.out.println("Выход из программы...");
            System.exit(0);
        }
        else {
            System.out.println();
            System.out.println("Неверный выбор. Повторите попытку.");
            processSorting(scanner, type, dataArray);
        }

    }

    private static <T> void saveRequest(Scanner scanner, OutputService outputService, Class<T> type, T[] dataArray, Comparator<T> comparator) {
        System.out.println();
        System.out.println("Хотите сохранить отсортированный массив?");
        System.out.println("1. Да.");
        System.out.println("2. Нет.");
        System.out.println();
        System.out.println("-----------------------------");
        System.out.println("4. Назад.");
        System.out.println("5. Выйти.");
        System.out.print("Ваш выбор: ");
        int saveChoice = scanner.nextInt();
        scanner.nextLine();

        switch (saveChoice) {
            case 1:
                // Запись отсортированного массива в файл
                boolean flag;
                do {
                    System.out.println();
                    System.out.print("Введите путь к файлу для записи отсортированных данных: ");
                    String sortedFilePath = scanner.nextLine();
                    flag = outputService.writeToFile(sortedFilePath, Arrays.asList(dataArray));
                } while (flag);

                searchRequest(scanner, type, dataArray, comparator);
                break;
            case 2:
                searchRequest(scanner, type, dataArray, comparator);
                break;
            case 4:
                System.out.println();
                System.out.println("Вовзрат на предыдущий шаг.");
                processSorting(scanner, type, dataArray);
                break;
            case 5:
                System.out.println();
                System.out.println("Выход из программы...");
                System.exit(0);
                break;
            default:
                System.out.println();
                System.out.println("Неверный выбор. Повторите попытку.");
                saveRequest(scanner, outputService, type, dataArray, comparator);
        }
    }

    private static <T> void searchRequest(Scanner scanner, Class<T> type, T[] dataArray, Comparator<T> comparator) {
        OutputService outputService = new OutputService();

        System.out.println();
        System.out.println("Хотите найти элемент в массиве?");
        System.out.println("1. Да.");
        System.out.println("2. Нет.");
        System.out.println();
        System.out.println("-----------------------------");
        System.out.println("4. Назад.");
        System.out.println("5. Выход.");
        System.out.print("Ваш выбор: ");
        int searchChoice = scanner.nextInt();
        scanner.nextLine();

        switch (searchChoice) {
            case 1:
                binarySearch(scanner, type, dataArray, comparator, outputService);
                break;
            case 2:
                System.out.println();
                System.out.println("Возврат в начало.");
                break;
            case 4:
                System.out.println();
                System.out.println("Возврат на предыдущий шаг.");
                saveRequest(scanner, outputService, type, dataArray, comparator);
                break;
            case 5:
                System.out.println();
                System.out.println("Выход из программы...");
                System.exit(0);
            default:
                System.out.println();
                System.out.println("Неверный выбор. Повторите попытку.");
                searchRequest(scanner, type, dataArray, comparator);
        }
    }

    public static <T> void binarySearch(Scanner scanner, Class<T> type, T[] dataArray, Comparator<T> comparator, OutputService outputService) {
        // Бинарный поиск
        System.out.println();
        System.out.println("Введите ключ для бинарного поиска. Регистр не важен. Пример: bird,red,true");
        String key = scanner.nextLine();
        T keyObject = parseKey(key, type);
        T foundElement = null;

        if (keyObject != null) {
            BinarySearch<T> binarySearch = new BinarySearch<>();
            foundElement = binarySearch.findElement(dataArray, keyObject, comparator);
            System.out.println();
        }

        if (foundElement != null) {
            saveRequestSolo(scanner, foundElement, outputService, type, dataArray, comparator);
        } else {
            repeat(scanner, type, dataArray, comparator, outputService);
        }
    }

    private static <T> void repeat(Scanner scanner, Class<T> type, T[] dataArray, Comparator<T> comparator, OutputService outputService) {
        System.out.println();
        System.out.println("Объект не найден. Повторить?");
        System.out.println("1. Да.");
        System.out.println("2. Нет.");
        System.out.println();
        System.out.println("-----------------------------");
        System.out.println("5. Выйти.");
        System.out.print("Ваш выбор:");
        int repeat = scanner.nextInt();
        scanner.nextLine();

        switch (repeat) {
            case 1 -> binarySearch(scanner, type, dataArray, comparator, outputService);
            case 2 -> {
                System.out.println();
                System.out.println("Возврат в начало.");
            }
            case 5 -> {
                System.out.println();
                System.out.println("Выход из программы...");
                System.exit(0);
            }
            default -> {
                System.out.println();
                System.out.println("Неверный выбор. Повторите попытку.");
                repeat(scanner, type, dataArray, comparator, outputService);
            }
        }
    }

    private static <T> void saveRequestSolo(Scanner scanner, T foundElement, OutputService outputService, Class<T> type, T[] dataArray, Comparator<T> comparator) {
        System.out.println();
        System.out.println("Хотите записать найденный элемент в документ?");
        System.out.println("1. Да.");
        System.out.println("2. Нет.");
        System.out.println();
        System.out.println("-----------------------------");
        System.out.println("4. Назад.");
        System.out.println("5. Выйти.");
        System.out.print("Ваш выбор: ");
        int saveChoice = scanner.nextInt();
        scanner.nextLine();

        switch (saveChoice) {
            case 1:
                // Запись найденного элемента в файл
                boolean flag;
                do {
                    System.out.println();
                    System.out.print("Введите путь к файлу для записи найденного значения: ");
                    String searchFilePath = scanner.nextLine();
                    flag = outputService.writeLineToFile(searchFilePath, foundElement.toString());
                    System.out.println();
                } while (flag);
                break;
            case 2:
                System.out.println();
                System.out.println("Возврат в начало.");
                cycle(scanner, true);
                break;
            case 4:
                binarySearch(scanner, type, dataArray, comparator, outputService);
                break;
            case 5:
                System.out.println();
                System.out.println("Выход из программы...");
                System.exit(0);
            default:
                System.out.println();
                System.out.println("Неверный выбор. Повторите попытку.");
                saveRequestSolo(scanner, foundElement, outputService, type, dataArray, comparator);
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
}