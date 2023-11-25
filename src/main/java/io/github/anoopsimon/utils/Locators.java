package io.github.anoopsimon.utils;

import java.io.InputStream;
import java.util.Properties;

public class Locators {
    private static Properties properties;

    public Locators() {
        loadProperties();
    }

    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("locators/locator.properties")) {
            properties = new Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find locator.properties");
                return;
            }
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        new Locators().loadProperties();

        return properties.getProperty(key);
    }
}
