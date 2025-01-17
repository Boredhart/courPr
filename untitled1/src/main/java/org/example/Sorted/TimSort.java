package org.example.Sorted;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Класс TimSort реализует интерфейс SortStrategy и предоставляет метод для сортировки массива с использованием алгоритма TimSort.
 *
 * @param <T> Тип элементов в массиве, который нужно отсортировать.
 */

public class TimSort<T> implements SortStrategy<T> {

    /**
     * Сортирует данный массив с использованием алгоритма TimSort.
     *
     * @param array      Массив, который нужно отсортировать.
     * @param comparator Компаратор, который нужно использовать для сравнения элементов в массиве.
     */
    @Override
    public void sort(T[] array, Comparator<T> comparator) {
        if (array == null || comparator == null) {
            throw new IllegalArgumentException("Массив и компаратор не могут быть null");
        }
        timSort(array, comparator, 0, array.length - 1);
    }

    /**
     * Сортирует данный массив с использованием алгоритма TimSort.
     *
     * @param array      Массив, который нужно отсортировать.
     * @param comparator Компаратор, который нужно использовать для сравнения элементов в массиве.
     * @param left       Левый индекс массива.
     * @param right      Правый индекс массива.
     */
    private void timSort(T[] array, Comparator<T> comparator, int left, int right) {
        int RUN = 32;

        for (int i = left; i <= right; i += RUN) {
            insertionSort(array, comparator, i, Math.min(i + RUN - 1, right));
        }

        for (int size = RUN; size < array.length; size = 2 * size) {
            for (int leftStart = 0; leftStart < array.length; leftStart += 2 * size) {
                int mid = leftStart + size - 1;
                int rightEnd = Math.min(leftStart + 2 * size - 1, array.length - 1);

                if (mid < rightEnd) {
                    merge(array, comparator, leftStart, mid, rightEnd);
                }
            }
        }
    }

    /**
     * Сортирует данный массив с использованием алгоритма вставки.
     *
     * @param array      Массив, который нужно отсортировать.
     * @param comparator Компаратор, который нужно использовать для сравнения элементов в массиве.
     * @param left       Левый индекс массива.
     * @param right      Правый индекс массива.
     */
    private void insertionSort(T[] array, Comparator<T> comparator, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            T key = array[i];
            int j = i - 1;

            while (j >= left && comparator.compare(array[j], key) > 0) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }

    /**
     * Объединяет два отсортированных подмассива в один отсортированный массив.
     *
     * @param array      Массив, который нужно отсортировать.
     * @param comparator Компаратор, который нужно использовать для сравнения элементов в массиве.
     * @param left       Левый индекс массива.
     * @param mid        Средний индекс массива.
     * @param right      Правый индекс массива.
     */
    private void merge(T[] array, Comparator<T> comparator, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        T[] leftArray = Arrays.copyOfRange(array, left, mid + 1);
        T[] rightArray = Arrays.copyOfRange(array, mid + 1, right + 1);

        System.arraycopy(array, left, leftArray, 0, n1);
        System.arraycopy(array, mid + 1, rightArray, 0, n2);

        int i = 0, j = 0;
        int k = left;

        while (i < n1 && j < n2) {
            if (comparator.compare(leftArray[i], rightArray[j]) <= 0) {
                array[k++] = leftArray[i++];
            } else {
                array[k++] = rightArray[j++];
            }
        }

        while (i < n1) {
            array[k++] = leftArray[i++];
        }

        while (j < n2) {
            array[k++] = rightArray[j++];
        }
    }
}



