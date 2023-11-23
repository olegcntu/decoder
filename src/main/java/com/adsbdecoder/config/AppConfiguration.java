package com.adsbdecoder.config;

import com.adsbdecoder.App;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class for reading configuration properties from the "config.properties" file.
 */
public class AppConfiguration {

    private static final String CONFIG_FILE = "config.properties";
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = App.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input != null) {
                properties.load(input);
            } else {
                throw new IOException("Unable to find or open the configuration file: " + CONFIG_FILE);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading configuration properties.", e);
        }
    }

    /**
     * Get the file path from the configuration.
     *
     * @return The file path.
     */
    public static String getFilePath() {
        return properties.getProperty("file.path");
    }

    /**
     * Get the segment size from the configuration.
     *
     * @return The segment size.
     * @throws NumberFormatException If the property value is not a valid integer.
     */
    public static int getSegmentSize() {
        return Integer.parseInt(properties.getProperty("segment.size"));
    }

    /**
     * Get the number of threads from the configuration.
     *
     * @return The number of threads.
     * @throws NumberFormatException If the property value is not a valid integer.
     */
    public static int getNumThreads() {
        return Integer.parseInt(properties.getProperty("num.threads"));
    }
}
