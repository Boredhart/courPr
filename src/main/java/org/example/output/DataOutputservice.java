package org.example.output;

import java.util.List;

public interface DataOutputservice {
    public <T> boolean writeToFile(String filePath, List<T> data);
    public boolean writeLineToFile(String filePath, String value);
}
