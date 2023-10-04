package org.brickztech;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

;

public class ConfigReader {

    private final Properties properties = new Properties();

    public Properties getData(String filePath) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(filePath)) {
            properties.load(inputStream);
        }
        return properties;
    }
}
