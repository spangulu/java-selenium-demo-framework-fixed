package com.demo.framework.utils;

import com.demo.framework.config.ConfigManager;
import com.demo.framework.driver.DriverManager;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public final class WaitUtils {
    private WaitUtils() {
    }

    private static WebDriverWait waitDriver() {
        return new WebDriverWait(DriverManager.getDriver(),
                Duration.ofSeconds(ConfigManager.getLong("explicitWaitSeconds")));
    }

    public static WebElement visible(By locator) {
        return waitDriver().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement clickable(By locator) {
        return waitDriver().until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static boolean urlContains(String value) {
        return waitDriver().until(ExpectedConditions.urlContains(value));
    }
}
