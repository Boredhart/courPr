package org.example.model;

public class Person implements Comparable<Person> {
    private final String gender; // пол
    private final int age; // возраст
    private final String lastName; // фамилия

    private Person(Builder builder) {
        this.gender = builder.gender;
        this.age = builder.age;
        this.lastName = builder.lastName;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getLastName() {
        return lastName;
    }

    public int compareTo(Person other) {
        int result = this.lastName.compareToIgnoreCase(other.lastName);
        if (result == 0) result = this.gender.compareToIgnoreCase(other.gender);
        if (result == 0) result = Integer.compare(this.age, other.age);
        return result;
    }

    public String toString() {
        return "Человек - Пол: " + gender +
                ", Возраст: " + age +
                ", Фамилия: " + lastName;
    }

    public static class Builder {
        private String gender;
        private int age;
        private String lastName;

        public Builder gender(String gender) {
            this.gender = gender;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }
}