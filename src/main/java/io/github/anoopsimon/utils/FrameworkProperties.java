package io.github.anoopsimon.utils;

import java.io.InputStream;
import java.util.Properties;

public class FrameworkProperties {
    private static Properties properties;

    public FrameworkProperties() {
        loadProperties();
    }

    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("framework.properties")) {
            properties = new Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        new FrameworkProperties().loadProperties();
        return properties.getProperty(key);
    }
}
