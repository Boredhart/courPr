package org.example.model;

public class Animal implements Comparable<Animal> {
    private final String species; // вид
    private final String eyeColor; // цвет глаз
    private final boolean hasFur; // наличие шерсти

    private Animal(Builder builder) {
        this.species = builder.species;
        this.eyeColor = builder.eyeColor;
        this.hasFur = builder.hasFur;
    }
    public String getSpecies() {
        return species;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public boolean hasFur() {
        return hasFur;
    }

    @Override
    public int compareTo(Animal other) {
        int result = this.species.compareToIgnoreCase(other.species);
        if (result == 0) result = this.eyeColor.compareToIgnoreCase(other.eyeColor);
        if (result == 0) result = Boolean.compare(this.hasFur, other.hasFur);
        return result;
    }

    @Override
    public String toString() {
        return "Животное - Вид: " + species +
                ", Цвет глаз: " + eyeColor +
                ", Наличие шерсти: " + (hasFur ? "Есть" : "Нет");
    }

    public static class Builder {
        private String species;
        private String eyeColor;
        private boolean hasFur;

        public Builder species(String species) {
            this.species = species;
            return this;
        }

        public Builder eyeColor(String eyeColor) {
            this.eyeColor = eyeColor;
            return this;
        }

        public Builder hasFur(boolean hasFur) {
            this.hasFur = hasFur;
            return this;
        }

        public Animal build() {
            return new Animal(this);
        }
    }
}

