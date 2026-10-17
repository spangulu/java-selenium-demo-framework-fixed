package com.demo.framework.reporting;

import com.demo.framework.utils.ScreenshotUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public final class TestListener implements ITestListener {
    private static final Logger LOGGER = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        String description = result.getMethod().getDescription();
        ExtentTestManager.start(result.getMethod().getMethodName(), description == null ? "" : description);
        LOGGER.info("START: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTestManager.get().pass("Test passed");
        LOGGER.info("PASS: {}", result.getMethod().getMethodName());
        ExtentTestManager.unload();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTestManager.get().fail(result.getThrowable());
        String screenshot = ScreenshotUtils.capture(result.getMethod().getMethodName());
        if (!screenshot.isBlank()) {
            try {
                ExtentTestManager.get().addScreenCaptureFromPath(screenshot);
            } catch (Exception exception) {
                LOGGER.warn("Could not attach screenshot: {}", screenshot, exception);
            }
        }
        LOGGER.error("FAIL: {}", result.getMethod().getMethodName(), result.getThrowable());
        ExtentTestManager.unload();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTestManager.get().skip(result.getThrowable());
        LOGGER.warn("SKIP: {}", result.getMethod().getMethodName());
        ExtentTestManager.unload();
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.get().flush();
    }
}
