package org.example.input;

import org.example.model.Animal;
import org.example.model.Barrel;
import org.example.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputService implements DataInputService {
    @Override
    public <T> T[] readFromFile(String filePath, Class<T> type) {
        return null;
    }

    @Override
    public <T> T[] generateRandom(int size, Class<T> type) {
        return null;
    }

    public <T> T[] readFromConsole(int size, Class<T> type) {
        List<String> input = getInputFromConsole(size);

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
                builder.hasWool(Boolean.parseBoolean(fields[2].trim()));
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
}
