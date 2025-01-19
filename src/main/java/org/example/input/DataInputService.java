package org.example.input;

import java.util.List;

public interface DataInputService {
    <T> T[] readFromFile(String filePath, Class<T> type);
    <T> T[] generateRandom(int size, Class<T> type);
    <T> T[] readFromConsole(int size, Class<T> type);
    List<String> getInputFromConsole(int size);
}
