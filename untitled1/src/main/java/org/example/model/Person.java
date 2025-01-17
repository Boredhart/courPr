package org.example.model;

import lombok.Builder;
import lombok.Data;

/**
 * Класс Person реализует интерфейс Comparable и представляет человека с полями пол, возраст и фамилия.
 */
@Data
@Builder
public class Person implements Comparable<Person> {
    private final String gender; // пол
    private final int age; // возраст
    private final String lastName; // фамилия

    /**
     * Метод compareTo сравнивает Person по фамилии, полу и возрасту.
     */
    public int compareTo(Person other) {
        int result = this.lastName.compareToIgnoreCase(other.lastName);
        if (result == 0) result = this.gender.compareToIgnoreCase(other.gender);
        if (result == 0) result = Integer.compare(this.age, other.age);
        return result;
    }

    /**
     * Метод toString возвращает строку с информацией о человеке.
     */
    public String toString() {
        return "Человек - Пол: " + gender +
                ", Возраст: " + age +
                ", Фамилия: " + lastName;
    }
}