package org.example.model;
import java.lang.Comparable;

public class Barrel implements Comparable<Barrel> {
    private final int volume;
    private final String storedMaterial;
    private final String material;

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

    @Override
    public int compareTo(Barrel other) {
        int result = Integer.compare(this.volume, other.volume);
        if (result == 0) result = this.material.compareTo(other.material);
        if (result == 0) result = this.storedMaterial.compareTo(other.storedMaterial);
        return result;
    }

    @Override
    public String toString() {
        return "Barrel{" +
                "volume=" + volume +
                ", storedMaterial='" + storedMaterial + '\'' +
                ", material='" + material + '\'' +
                '}';
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
