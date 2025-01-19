package org.example.input;

import org.example.model.Animal;
import org.example.model.Barrel;
import org.example.model.Person;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static org.example.input.DataValidator.*;

public class InputService implements DataInputService {
    private final Random random = new Random();

    @Override
    public <T> T[] readFromFile(String filePath, Class<T> type) {
        File file = new File(filePath);

        if (filePath.isEmpty()) {
            return null;
        }

        if (type.equals(Animal.class)) {
            return (T[]) readAnimalFromFile(file);
        } else if (type.equals(Barrel.class)) {
            return (T[]) readBarrelFromFile(file);
        } else if (type.equals(Person.class)) {
            return (T[]) readPersonFromFile(file);
        } else {
            throw new IllegalArgumentException("Unsupported class: " + type.getName());
        }
    }

    @Override
    public <T> T[] generateRandom(int size, Class<T> type) {
        if (type.equals(Animal.class)) {
            return (T[]) generateRandomAnimalArray(size);
        } else if (type.equals(Barrel.class)) {
            return (T[]) generateRandomBarrelArray(size);
        } else if (type.equals(Person.class)) {
            return (T[]) generateRandomPersonArray(size);
        } else {
            throw new IllegalArgumentException("Unsupported class type: " + type.getName());
        }
    }

    public boolean isValidAnimalData(String data) {

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

    private boolean isValidBarrelData(String data) {

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

    private boolean isValidPersonData(String data) {

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

    @Override
    public <T> T[] readFromConsole(int size, Class<T> type) {

        Scanner scanner = new Scanner(System.in);

        List<String> input = getInputFromConsole(size);

        if (type.equals(Animal.class)) {
            Animal[] animals = new Animal[size];
            for (int i = 0; i < size; i++) {
                String data = input.get(i);

                // Проверка корректности данных для Animal
                if (isValidAnimalData(data)) {
                    animals[i] = (Animal) constructObject(type, data);
                } else {
                    System.out.println("Некорректные данные для животного, пропускаем ввод.");
                }
                animals[i] = (Animal) constructObject(type, input.get(i));
            }
            return (T[]) animals;
        }
        else if (type.equals(Barrel.class)) {
            Barrel[] barrels = new Barrel[size];
            for (int i = 0; i < size; i++) {
                String data = input.get(i);

                // Проверка корректности данных для Barrel
                if (isValidBarrelData(data)) {
                    barrels[i] = (Barrel) constructObject(type, data);
                } else {
                    System.out.println("Некорректные данные для бочки, пропускаем ввод.");
                }
            }
            return (T[]) barrels;
        }
        else if (type.equals(Person.class)) {
            Person[] people = new Person[size];
            for (int i = 0; i < size; i++) {
                String data = input.get(i);

                // Проверка корректности данных для Person
                if (isValidPersonData(data)) {
                    people[i] = (Person) constructObject(type, data);
                } else {
                    System.out.println("Некорректные данные для человека, пропускаем ввод.");
                }
            }
            return (T[]) people;
        } else {
            throw new IllegalArgumentException("Unsupported class: " + type.getName());
        }
    }

    @Override
    public List<String> getInputFromConsole(int size){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите " + size + " строк данных разделенных через ','");
        System.out.println("Пример:");
        System.out.println("Animal - Dog,Brown,true ");
        System.out.println("Barrel - Wood,Water,1000");
        System.out.println("Person - Male,Dima,25");
        List<String> input = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            System.out.println("Введите информацию для объекта " + (i + 1) + ":");
            String line = scanner.nextLine();
            input.add(line);
        }
        return input;
    }

    private <T> T constructObject(Class<T> type, String data) {
        String[] fields = data.split(",");
        try {
            if (type.equals(Animal.class)) {
                Animal.Builder builder = new Animal.Builder();
                String species = fields[0].trim();
                builder.species(species.substring(0, 1).toUpperCase() + species.substring(1));
                String eye = fields[1].trim();
                builder.eyeColor(eye.substring(0, 1).toUpperCase() + eye.substring(1));
                builder.hasFur(Boolean.parseBoolean(fields[2].trim()));
                return (T) builder.build();

            } else if (type.equals(Barrel.class)) {
                Barrel.Builder builder = new Barrel.Builder();
                builder.volume(Integer.parseInt(fields[0].trim()));
                String mat = fields[1].trim();
                builder.material(mat.substring(0, 1).toUpperCase() + mat.substring(1));
                String sortMat = fields[2].trim();
                builder.storedMaterial(sortMat.substring(0, 1).toUpperCase() + sortMat.substring(1));
                return (T) builder.build();

            } else if (type.equals(Person.class)) {
                Person.Builder builder = new Person.Builder();
                String gender = fields[0].trim();
                builder.gender(gender.substring(0, 1).toUpperCase() + gender.substring(1));
                builder.age(Integer.parseInt(fields[1].trim()));
                String lastName = fields[2].trim();
                builder.lastName(lastName.substring(0, 1).toUpperCase() + lastName.substring(1));
                return (T) builder.build();

            } else {
                throw new IllegalArgumentException("Unknown class type: " + type.getName());
            }
        } catch (Exception e) {
//            System.out.println("Incorrect input data: " + e);
            return null;
        }
    }

    private Animal[] readAnimalFromFile(File file) {
        List<Animal> animals = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    try {
                        String species = parts[0].trim();
                        String eyeColor = parts[1].trim();
                        boolean hasFur = Boolean.parseBoolean(parts[2].trim());
                        if(isValidString(species) && isValidString(eyeColor))
                            animals.add(new Animal.Builder()
                                    .species(species)
                                    .eyeColor(eyeColor)
                                    .hasFur(hasFur)
                                    .build());
                    } catch (NumberFormatException e) {
                        System.err.println("Incorrect format of number field, skip line: " + line);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Incorrect format of logic field, skip line: " + line);
                    }
                }
                else {
                    System.err.println("Incorrect format line, skip line: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + file.getAbsolutePath());
            return new Animal[0];
        }
        return animals.toArray(new Animal[0]);
    }

    private Barrel[] readBarrelFromFile(File file) {
        List<Barrel> barrels = new ArrayList<>();
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    try {
                        int volume = Integer.parseInt(parts[0].trim());
                        String material = parts[1].trim();
                        String storedMaterial = parts[2].trim();
                        if(isValidString(material) && isValidString(storedMaterial))
                            barrels.add(new Barrel.Builder()
                                    .volume(volume)
                                    .material(material)
                                    .storedMaterial(storedMaterial)
                                    .build());
                    } catch (NumberFormatException e) {
                        System.err.println("Incorrect format of number field, skip line: " + line);
                    }
                } else {
                    System.err.println("Incorrect format line, skip line: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + file.getAbsolutePath());
            return new Barrel[0];
        }
        return barrels.toArray(new Barrel[0]);
    }

    private Person[] readPersonFromFile(File file) {
        List<Person> persons = new ArrayList<>();
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    try {
                        String gender = parts[0].trim();
                        int age = Integer.parseInt(parts[1].trim());
                        String lastName = parts[2].trim();
                        if(isValidString(gender) && isValidString(lastName))
                            persons.add(new Person.Builder()
                                    .gender(gender)
                                    .age(age)
                                    .lastName(lastName)
                                    .build());
                    } catch (NumberFormatException e) {
                        System.err.println("Incorrect format of number field, skip line: " + line);
                    }
                } else {
                    System.err.println("Incorrect format line, skip line: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + file.getAbsolutePath());
            return new Person[0];
        }
        return persons.toArray(new Person[0]);
    }

    private Animal[] generateRandomAnimalArray(int size) {
        Animal[] animals = new Animal[size];
        String[] species = {"Dog", "Cat", "Bird", "Fish", "Rabbit"};
        String[] eyeColors = {"Blue", "Green", "Brown", "Black", "Gray"};
        for (int i = 0; i < size; i++) {
            animals[i] = new Animal.Builder()
                    .species(species[random.nextInt(species.length)])
                    .eyeColor(eyeColors[random.nextInt(eyeColors.length)])
                    .hasFur(random.nextBoolean())
                    .build();
        }
        return animals;
    }

    private Barrel[] generateRandomBarrelArray(int size) {
        Barrel[] barrels = new Barrel[size];
        String[] materials = {"Wood", "Metal", "Plastic", "Gold"};
        String[] storedMaterials = {"Water", "Oil", "Wine", "Grain"};
        for (int i = 0; i < size; i++) {
            barrels[i] = new Barrel.Builder()
                    .volume(random.nextInt(1000))
                    .material(materials[random.nextInt(materials.length)])
                    .storedMaterial(storedMaterials[random.nextInt(storedMaterials.length)])
                    .build();
        }
        return barrels;
    }

    private Person[] generateRandomPersonArray(int size) {
        Person[] people = new Person[size];
        String[] genders = {"Male", "Female"};
        String[] lastNames = {"Julian", "Dima", "Sergey", "Anton", "Ars"};
        for (int i = 0; i < size; i++) {
            people[i] = new Person.Builder()
                    .gender(genders[random.nextInt(genders.length)])
                    .age(random.nextInt(1,101))
                    .lastName(lastNames[random.nextInt(lastNames.length)])
                    .build();
        }
        return people;
    }
}
