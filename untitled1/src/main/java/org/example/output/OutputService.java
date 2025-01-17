package org.example.output;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;



public class OutputService implements DataOutputservice{

    /**
     * Записывает коллекцию в файл в режиме добавления.
     * filePath путь к файлу
     * data коллекция для записи
     * <T> тип данных
     */
    public <T> boolean writeToFile(String filePath, List<T> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            for (T item : data) {
                writer.write(item.toString());
                writer.newLine();
            }
            System.out.println("Объекты записаны успешно.");
            return false;
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл: " + e.getMessage());
            return true;
        }
    }

    /**
     * Записывает одну строку в файл в режиме добавления.
     * filePath путь к файлу
     * value    строка для записи
     */
    public boolean writeLineToFile(String filePath, String value) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(value);
            writer.newLine();
            System.out.println("Объект записан успешно.");
            return false;
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл: " + e.getMessage());
            return true;
        }
    }
}