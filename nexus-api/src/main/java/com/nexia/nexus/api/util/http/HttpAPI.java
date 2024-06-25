package com.nexia.nexus.api.util.http;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class HttpAPI {

    public static String userAgent = "Mozilla/5.0 (compatible; nexus)";

    /**
     * Sends a GET request to an url.
     * @param url The url, e.g. https://nexia.dev/
     * @return The response.
     */
    public static String get(@NotNull URL url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("User-Agent", userAgent);
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.defaultCharset()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            connection.disconnect();

            return response.toString();
        } catch (Exception ignored) { return null; }
    }
}