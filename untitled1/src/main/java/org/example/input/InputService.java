package org.example.input;

import org.example.model.Animal;
import org.example.model.Barrel;
import org.example.model.Person;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class InputService implements DataInputService {
    private final Random random = new Random();

    @Override
    public <T> T[] readFromFile(String filePath, Class<T> type) {
        File file = new File(filePath);

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

    @Override
    public <T> T[] readFromConsole(int size, Class<T> type) {
        List<String> input = getInputFromConsole(size);


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


        if (type.equals(Animal.class)) {
            Animal[] animals = new Animal[size];
            for (int i = 0; i < size; i++) {
                animals[i] = (Animal) constructObject(type, input.get(i));
            }
            return (T[]) animals;
        }
        else if (type.equals(Barrel.class)) {
            Barrel[] barrels = new Barrel[size];
            for (int i = 0; i < size; i++) {
                barrels[i] = (Barrel) constructObject(type, input.get(i));
            }
            return (T[]) barrels;
        }
        else if (type.equals(Person.class)) {
            Person[] people = new Person[size];
            for (int i = 0; i < size; i++) {
                people[i] = (Person) constructObject(type, input.get(i));
            }
            return (T[]) people;
        } else {
            throw new IllegalArgumentException("Unsupported class: " + type.getName());
        }
    }

    @Override
    public <T> List<String> getInputFromConsole(int size){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter " + size + " lines of data separated by ','");
        System.out.println("Example:");
        System.out.println("Animal - Dog,Brown,true ");
        System.out.println("Barrel - Wood,Water,1000");
        System.out.println("Person - Male,Dima,25");
        List<String> input = new ArrayList<>();
        for (int i = 0; i < size; i++) {
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
                builder.species(fields[0].trim());
                builder.eyeColor(fields[1].trim());
                builder.hasFur(Boolean.parseBoolean(fields[2].trim()));
                return (T) builder.build();

            } else if (type.equals(Barrel.class)) {
                Barrel.Builder builder = new Barrel.Builder();
                builder.volume(Integer.parseInt(fields[0].trim()));
                builder.material(fields[1].trim());
                builder.storedMaterial(fields[2].trim());
                return (T) builder.build();

            } else if (type.equals(Person.class)) {
                Person.Builder builder = new Person.Builder();
                builder.gender(fields[0].trim());
                builder.age(Integer.parseInt(fields[1].trim()));
                builder.lastName(fields[2].trim());
                return (T) builder.build();

            } else {
                throw new IllegalArgumentException("Unknown class type: " + type.getName());
            }
        } catch (Exception e) {
            System.out.println("Incorrect input data: " + e);
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
                        if(DataValidator.isValidString(species) && DataValidator.isValidString(eyeColor))
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
            System.err.println("File not founded: " + file.getAbsolutePath());
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
                        if(DataValidator.isValidString(material) && DataValidator.isValidString(storedMaterial))
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
            System.err.println("File not founded: " + file.getAbsolutePath());
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
                        if(DataValidator.isValidString(gender) && DataValidator.isValidString(lastName))
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
            System.err.println("File not founded: " + file.getAbsolutePath());
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
                    .age(random.nextInt(100))
                    .lastName(lastNames[random.nextInt(lastNames.length)])
                    .build();
        }
        return people;
    }
}
