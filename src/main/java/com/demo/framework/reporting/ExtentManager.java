package com.demo.framework.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ExtentManager {
    private static final ExtentReports EXTENT = create();

    private ExtentManager() {
    }

    private static ExtentReports create() {
        try {
            Files.createDirectories(Path.of("target", "reports"));
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to create report directory", exception);
        }
        ExtentSparkReporter reporter = new ExtentSparkReporter("target/reports/extent-report.html");
        reporter.config().setDocumentTitle("Selenium Demo Results");
        reporter.config().setReportName("SauceDemo UI Automation");

        ExtentReports extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Java", System.getProperty("java.version"));
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        return extent;
    }

    public static ExtentReports get() {
        return EXTENT;
    }
}
