package org.example.model;

import lombok.Builder;
import lombok.Data;

/**
 * Класс Barrel реализует интерфейс Comparable и представляет бочку с полями объём, содержимое и материал.
 */
@Data
@Builder
public class Barrel implements Comparable<Barrel> {
    private final int volume; // объём
    private final String storedMaterial; // содержимое
    private final String material; // материал

    /**
     * Метод compareTo сравнивает Barrel по объёму, материалу и содержимому.
     */
    public int compareTo(Barrel other) {
        int result = Integer.compare(this.volume, other.volume);
        if (result == 0) result = this.material.compareTo(other.material);
        if (result == 0) result = this.storedMaterial.compareTo(other.storedMaterial);
        return result;
    }

    /**
     * Метод toString возвращает строку с информацией о бочке.
     */
    public String toString() {
        return "Бочка - Объём: " + volume +
                ", Содержимое: " + storedMaterial +
                ", Материал: " + material;
    }
}
