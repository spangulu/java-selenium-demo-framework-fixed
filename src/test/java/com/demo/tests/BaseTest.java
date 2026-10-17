package com.demo.tests;

import com.demo.framework.driver.DriverFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {
    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        DriverFactory.createDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
