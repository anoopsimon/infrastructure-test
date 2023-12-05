package io.github.anoopsimon.utils;

import java.io.*;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Locators {
    private static Map<String, String> locatorsMap = new HashMap<>();

    public Locators() {
        loadLocators();
    }

    private void loadLocators() {
        try {
            // Assuming the project root is the working directory
            File file = Paths.get("src", "test", "resources", "locators", "locators.csv").toFile();
            if (!file.exists()) {
                System.out.println("Sorry, unable to find locators.csv");
                return;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                boolean isFirstLine = true;
                while ((line = reader.readLine()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue;
                    }
                    String[] keyValue = line.split(",");
                    if (keyValue.length == 2) {
                        locatorsMap.put(keyValue[0].trim(), keyValue[1].trim());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String get(String key) {
        new Locators().loadLocators();

        return locatorsMap.get(key);
    }
}
