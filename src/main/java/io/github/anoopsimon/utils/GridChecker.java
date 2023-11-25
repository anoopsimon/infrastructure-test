package io.github.anoopsimon.utils;

import java.net.HttpURLConnection;
import java.net.URL;

public class GridChecker {

    public static boolean checkGridAvailability(String gridUrl) {
        try {
            URL url = new URL(gridUrl + "/status");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            return responseCode == 200;  // HTTP OK status
        } catch (Exception e) {
            throw new RuntimeException("Error checking Selenium Grid status: " + e.getMessage());
        }
    }
}
