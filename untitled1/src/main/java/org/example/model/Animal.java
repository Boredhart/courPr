package org.example.model;
import java.lang.Comparable;

public class Animal implements Comparable<Animal>{
    private final String species;
    private final String eyeColor;
    private final boolean hasFur;

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

    public int compareTo(Animal other) {
        int result = this.species.compareTo(other.species);
        if (result == 0) result = this.eyeColor.compareTo(other.eyeColor);
        if (result == 0) result = Boolean.compare(this.hasFur, other.hasFur);
        return result;
    }

    @Override
    public String toString() {
        return "Animal {";
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

