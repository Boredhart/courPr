package org.example.sorted;

import java.util.Comparator;

/**
 * Интерфейс SortStrategy представляет стратегию сортировки массива.
 * @param <T> Тип элементов массива
 */
public interface SortStrategy<T> {
    /**
     * Метод sort сортирует массив по заданному компаратору.
     * @param array Массив, который нужно отсортировать
     * @param comparator Компаратор, по которому нужно отсортировать массив
     */
    void sort(T[] array, Comparator<T> comparator);
}
