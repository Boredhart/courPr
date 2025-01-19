package org.example.output;

import java.util.List;

public interface DataOutputservice {
    <T> boolean writeToFile(String filePath, List<T> data);
    boolean writeLineToFile(String filePath, String value);
}
