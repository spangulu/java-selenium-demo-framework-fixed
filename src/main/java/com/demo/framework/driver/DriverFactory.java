package com.demo.framework.driver;

import com.demo.framework.config.ConfigManager;
import java.time.Duration;
import java.util.Locale;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public final class DriverFactory {
    private DriverFactory() {
    }

    public static void createDriver() {
        String browser = ConfigManager.get("browser").toLowerCase(Locale.ROOT);
        boolean headless = ConfigManager.getBoolean("headless");

        WebDriver driver = switch (browser) {
            case "chrome" -> new ChromeDriver(chromeOptions(headless));
            case "firefox" -> new FirefoxDriver(firefoxOptions(headless));
            case "edge" -> new EdgeDriver(edgeOptions(headless));
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        };

        driver.manage().timeouts().pageLoadTimeout(
                Duration.ofSeconds(ConfigManager.getLong("pageLoadTimeoutSeconds")));
        driver.manage().window().maximize();
        DriverManager.setDriver(driver);
    }

    public static void quitDriver() {
        try {
            DriverManager.getDriver().quit();
        } catch (IllegalStateException ignored) {
            // No driver exists for this thread.
        } finally {
            DriverManager.unload();
        }
    }

    private static ChromeOptions chromeOptions(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications", "--disable-popup-blocking");
        if (headless) {
            options.addArguments("--headless=new", "--window-size=1920,1080", "--no-sandbox", "--disable-dev-shm-usage");
        }
        return options;
    }

    private static FirefoxOptions firefoxOptions(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        if (headless) {
            options.addArguments("-headless");
        }
        return options;
    }

    private static EdgeOptions edgeOptions(boolean headless) {
        EdgeOptions options = new EdgeOptions();
        if (headless) {
            options.addArguments("--headless=new", "--window-size=1920,1080");
        }
        return options;
    }
}
