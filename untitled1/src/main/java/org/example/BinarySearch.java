package org.example;

import java.util.Comparator;
import java.util.Objects;

public class BinarySearch<T> {

    private boolean isSorted(T[] arr, Comparator<T> comparator) {

        for (int i = 0; i < arr.length - 1; i++) {
            if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                return false;
            }
        }
        return true;
    }

    private int binarySearch(T[] arr, T key, Comparator<T> comparator) {
        if (Objects.isNull(arr) || arr.length == 0) {
            throw new IllegalArgumentException("Array is empty or null");
        }

        if (!isSorted(arr, comparator)) {
            throw new IllegalStateException("Array is not sorted");
        }

        int low = 0;
        int high = arr.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            T val = arr[mid];
            int result = comparator.compare(key, val);
            if (result < 0) { // поиск в левой части массива
                high = mid - 1;
            } else if (result > 0) { // поиск в правой части массива
                low = mid + 1;
            } else {
                System.out.println("Element found: " + val);
                return mid;
            }
        }
        return -1;
    }

    public T findElement(T[] arr, T key, Comparator<T> comparator) {
        try {
            int index = binarySearch(arr, key, comparator);
            if (index == -1) {
                System.err.println("Element not found: " + key);
                return null;
            } else {
                return arr[index];
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
