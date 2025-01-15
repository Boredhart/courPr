package org.example.model;
import java.lang.Comparable;

public class Animal implements Comparable<Animal>{
    private final String species;
    private final String eyeColor;
    private final boolean hasWool;

    private Animal(Builder builder) {
        this.species = builder.species;
        this.eyeColor = builder.eyeColor;
        this.hasWool = builder.hasWool;
    }
    public String getSpecies() {
        return species;
    }
    public String getEyeColor() {
        return eyeColor;
    }
    public boolean isHasWool() {
        return hasWool;
    }
    public int compareTo(Animal other) {
        int result = this.species.compareTo(other.species);
        if (result == 0) result = this.eyeColor.compareTo(other.eyeColor);
        if (result == 0) result = Boolean.compare(this.hasWool, other.hasWool);
        return result;
    }
    @Override
    public String toString() {
        return "Animal{" +
                "species='" + species + '\'' +
                ", eyeColor='" + eyeColor + '\'' +
                ", hasWool=" + hasWool +
                '}';
    }
    public static class Builder {
        private String species;
        private String eyeColor;
        private boolean hasWool;

        public Builder species(String species) {
            this.species = species;
            return this;
        }
        public Builder eyeColor(String eyeColor) {
            this.eyeColor = eyeColor;
            return this;
        }
        public Builder hasWool(boolean hasWool) {
            this.hasWool = hasWool;
            return this;
        }
        public Animal build() {
            return new Animal(this);
        }



    }
    }

