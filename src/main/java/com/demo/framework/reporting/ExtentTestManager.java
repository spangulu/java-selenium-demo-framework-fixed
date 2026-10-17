package com.demo.framework.reporting;

import com.aventstack.extentreports.ExtentTest;

public final class ExtentTestManager {
    private static final ThreadLocal<ExtentTest> TEST = new ThreadLocal<>();

    private ExtentTestManager() {
    }

    public static void start(String name, String description) {
        TEST.set(ExtentManager.get().createTest(name, description));
    }

    public static ExtentTest get() {
        return TEST.get();
    }

    public static void unload() {
        TEST.remove();
    }
}
