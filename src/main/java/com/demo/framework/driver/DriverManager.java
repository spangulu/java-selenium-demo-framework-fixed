package com.demo.framework.driver;

import org.openqa.selenium.WebDriver;

public final class DriverManager {
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverManager() {
    }

    public static WebDriver getDriver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("WebDriver has not been initialized for this thread");
        }
        return driver;
    }

    public static void setDriver(WebDriver driver) {
        DRIVER.set(driver);
    }

    public static void unload() {
        DRIVER.remove();
    }
}
