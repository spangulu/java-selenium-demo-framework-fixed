package com.demo.framework.config;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.fail;

import org.testng.annotations.Test;

public class ConfigManagerTest {

    @Test
    public void getReturnsSystemPropertyWhenPresent() {
        String original = System.getProperty("baseUrl");
        System.setProperty("baseUrl", "https://example.com");

        try {
            assertEquals(ConfigManager.get("baseUrl"), "https://example.com");
        } finally {
            restoreProperty("baseUrl", original);
        }
    }

    @Test
    public void getReturnsTrimmedValueFromResourceBundle() {
        assertEquals(ConfigManager.get("browser"), "chrome");
    }

    @Test
    public void getBooleanReadsBooleanValue() {
        assertFalse(ConfigManager.getBoolean("headless"));
    }

    @Test
    public void getLongParsesNumericValue() {
        assertEquals(ConfigManager.getLong("explicitWaitSeconds"), 12L);
    }

    @Test
    public void getThrowsForMissingKey() {
        try {
            ConfigManager.get("missing.key");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException exception) {
            assertEquals(exception.getMessage(), "Missing configuration value: missing.key");
        }
    }

    private void restoreProperty(String key, String value) {
        if (value == null) {
            System.clearProperty(key);
        } else {
            System.setProperty(key, value);
        }
    }
}
