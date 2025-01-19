package org.example.output;

import java.util.List;

public interface DataOutputStrategy {
    <T> boolean writeToFile(String filePath, List<T> data);
    boolean writeLineToFile(String filePath, String value);
}
