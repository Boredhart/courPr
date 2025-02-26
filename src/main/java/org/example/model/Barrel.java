package org.example.model;

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

    public int compareTo(Barrel other) {
        int result = Integer.compare(this.volume, other.volume);
        if (result == 0) result = this.material.compareToIgnoreCase(other.material);
        if (result == 0) result = this.storedMaterial.compareToIgnoreCase(other.storedMaterial);
        return result;
    }

    public String toString() {
        return "Бочка - Объём: " + volume +
                ", Содержимое: " + storedMaterial +
                ", Материал: " + material;
    }

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
