package com.demo.framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigManager {
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream stream = ConfigManager.class.getClassLoader()
                .getResourceAsStream("config/config.properties")) {
            if (stream == null) {
                throw new IllegalStateException("config/config.properties was not found");
            }
            PROPERTIES.load(stream);
        } catch (IOException exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }

    private ConfigManager() {
    }

    public static String get(String key) {
        String systemValue = System.getProperty(key);
        if (systemValue != null && !systemValue.isBlank()) {
            return systemValue.trim();
        }
        String value = PROPERTIES.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Missing configuration value: " + key);
        }
        return value.trim();
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static long getLong(String key) {
        try {
            return Long.parseLong(get(key));
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Configuration value must be numeric: " + key, exception);
        }
    }
}
