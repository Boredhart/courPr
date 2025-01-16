package org.example.model;

import java.lang.Comparable;

/**
 * Класс Barrel  реализует интерфейс Comparable и представляет бочку с полями объём, содержимое и материал.
 */
public class Barrel implements Comparable<Barrel> {
    private final int volume; // объём
    private final String storedMaterial; // содержимое
    private final String material; // материал

    private Barrel(Builder builder) {
        this.volume = builder.volume;
        this.storedMaterial = builder.storedMaterial;
        this.material = builder.material;
    }

    public int getVolume() {
        return volume;
    }

    public String getStoredMaterial() {
        return storedMaterial;
    }

    public String getMaterial() {
        return material;
    }

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

    /**
     * Вложенный статический класс Builder используется для пошагового создания объекта класса Barrel.
     */
    public static class Builder {
        private int volume;
        private String storedMaterial;
        private String material;

        public Builder volume(int volume) {
            this.volume = volume;
            return this;
        }

        public Builder storedMaterial(String storedMaterial) {
            this.storedMaterial = storedMaterial;
            return this;
        }

        public Builder material(String material) {
            this.material = material;
            return this;
        }

        public Barrel build() {
            return new Barrel(this);
        }
    }
}
