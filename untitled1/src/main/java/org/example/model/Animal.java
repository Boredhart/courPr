package org.example.model;

import lombok.Builder;
import lombok.Data;

/**
 * Класс Animal реализует интерфейс Comparable и представляет животное с полями вид, цвет глаз и наличие шерсти.
 */

@Data
@Builder
public class Animal implements Comparable<Animal> {
    private final String species; // вид
    private final String eyeColor; // цвет глаз
    private final boolean hasFur; // наличие шерсти

    public boolean hasFur() {
        return hasFur;
    }

    /**
     * Метод compareTo сравнивает Animal по виду, цвету глаз и наличию шерсти.
     */
    public int compareTo(Animal other) {
        int result = this.species.compareTo(other.species);
        if (result == 0) result = this.eyeColor.compareTo(other.eyeColor);
        if (result == 0) result = Boolean.compare(this.hasFur, other.hasFur);
        return result;
    }

    /**
     * Метод toString возвращает строку с информацией о животном.
     */
    @Override
    public String toString() {
        return "Животное - Вид: " + species +
                ", Цвет глаз: " + eyeColor +
                ", Наличие шерсти: " + (hasFur ? "Есть" : "Нет");
    }
}

