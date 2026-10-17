package com.demo.framework.utils;

import com.demo.framework.driver.DriverManager;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public final class ScreenshotUtils {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

    private ScreenshotUtils() {
    }

    public static String capture(String testName) {
        try {
            Path directory = Path.of("target", "screenshots");
            Files.createDirectories(directory);
            String safeName = testName.replaceAll("[^a-zA-Z0-9._-]", "_");
            Path destination = directory.resolve(safeName + "_" + LocalDateTime.now().format(FORMATTER) + ".png");
            byte[] image = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
            Files.write(destination, image);
            return destination.toAbsolutePath().toString();
        } catch (IOException | RuntimeException exception) {
            return "";
        }
    }
}
