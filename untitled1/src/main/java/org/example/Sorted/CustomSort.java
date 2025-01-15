package org.example.Sorted;

import org.example.model.Animal;
import org.example.model.Barrel;
import org.example.model.Person;

import java.util.Comparator;

/**
 * Этот класс предоставляет пользовательские компараторы для разных типов объектов.
 */
public class CustomSort {

    /**
     * Возвращает компаратор для объектов Animal, который сравнивает их по виду, затем по цвету глаз и, наконец, по наличию шерсти.
     * @return компаратор для объектов Animal
     */
    public static Comparator<Animal> getAnimalComparator() {
        return Comparator.comparing(Animal::getSpecies)
                .thenComparing(Animal::getEyeColor)
                .thenComparing(Animal::hasFur);
    }

    /**
     * Возвращает компаратор для объектов Animal, который сравнивает их по виду, затем по наличию шерсти (с животными без шерсти, идущими первыми), и, наконец, по цвету глаз.
     * @return компаратор для объектов Animal
     */
    public static Comparator<Animal> getCustomAnimalComparator() {
        return Comparator.comparing(Animal::getSpecies)
                .thenComparing((a1, a2) -> Boolean.compare(!a1.hasFur(), !a2.hasFur()))
                .thenComparing(Animal::getEyeColor);
    }

    /**
     * Возвращает компаратор для объектов Barrel, который сравнивает их по объему, затем по материалу, который они хранят, и, наконец, по тому, из чего они сделаны.
     * @return компаратор для объектов Barrel
     */
    public static Comparator<Barrel> getBarrelComparator() {
        return Comparator.comparingDouble(Barrel::getVolume)
                .thenComparing(Barrel::getStoredMaterial)
                .thenComparing(Barrel::getMaterial);
    }

    /**
     * Возвращает компаратор для объектов Barrel, который сравнивает их по объему, затем по тому, является ли их объем четным или нечетным (с бочками с четными объемами, идущими первыми), и, наконец, по тому, из чего они сделаны.
     * @return компаратор для объектов Barrel
     */
    public static Comparator<Barrel> getCustomBarrelComparator() {
        return Comparator.comparingDouble(Barrel::getVolume)
                .thenComparing((b1, b2) -> {
                    if (b1.getVolume() % 2 == 0 && b2.getVolume() % 2 == 0) {
                        return Double.compare(b1.getVolume(), b2.getVolume());
                    } else if (b1.getVolume() % 2 != 0 && b2.getVolume() % 2 != 0) {
                        return 0;
                    } else {
                        return b1.getVolume() % 2 == 0 ? -1 : 1;
                    }
                })
                .thenComparing(Barrel::getMaterial);
    }

    /**
     * Возвращает компаратор для объектов Person, который сравнивает их по полу, затем по возрасту и, наконец, по фамилии.
     * @return компаратор для объектов Person
     */
    public static Comparator<Person> getPersonComparator() {
        return Comparator.comparing(Person::getGender)
                .thenComparingInt(Person::getAge)
                .thenComparing(Person::getLastName);
    }

    /**
     * Возвращает компаратор для объектов Person, который сравнивает их по возрасту, затем по тому, является ли их возраст четным или нечетным (с людьми с четным возрастом, идущими первыми), и, наконец, по фамилии.
     * @return компаратор для объектов Person
     */
    public static Comparator<Person> getCustomPersonComparator() {
        return Comparator.comparingInt(Person::getAge)
                .thenComparing((p1, p2) -> {
                    if (p1.getAge() % 2 == 0 && p2.getAge() % 2 == 0) {
                        return Integer.compare(p1.getAge(), p2.getAge());
                    } else if (p1.getAge() % 2 != 0 && p2.getAge() % 2 != 0) {
                        return 0;
                    } else {
                        return p1.getAge() % 2 == 0 ? -1 : 1;
                    }
                })
                .thenComparing(Person::getLastName);
    }
}
