package com.demo.framework.driver;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.fail;

import java.lang.reflect.Proxy;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class DriverManagerTest {

    @AfterMethod
    public void teardown() {
        DriverManager.unload();
    }

    @Test
    public void getDriverThrowsWhenNotInitialized() {
        DriverManager.unload();

        try {
            DriverManager.getDriver();
            fail("Expected IllegalStateException");
        } catch (IllegalStateException exception) {
            assertEquals(exception.getMessage(), "WebDriver has not been initialized for this thread");
        }
    }

    @Test
    public void setDriverStoresDriverForCurrentThread() {
        WebDriver driver = buildDriver();

        DriverManager.setDriver(driver);

        assertNotNull(DriverManager.getDriver());
        assertSame(DriverManager.getDriver(), driver);
    }

    @Test
    public void unloadClearsStoredDriver() {
        DriverManager.setDriver(buildDriver());

        DriverManager.unload();

        try {
            DriverManager.getDriver();
            fail("Expected IllegalStateException");
        } catch (IllegalStateException exception) {
            assertEquals(exception.getMessage(), "WebDriver has not been initialized for this thread");
        }
    }

    private WebDriver buildDriver() {
        return (WebDriver) Proxy.newProxyInstance(
                WebDriver.class.getClassLoader(),
                new Class<?>[]{WebDriver.class},
                (proxy, method, args) -> null);
    }
}
