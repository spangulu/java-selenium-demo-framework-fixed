package com.demo.framework.utils;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import com.demo.framework.driver.DriverManager;
import java.lang.reflect.Proxy;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class WaitUtilsTest {

    @AfterMethod
    public void teardown() {
        DriverManager.unload();
    }

    @Test
    public void visibleReturnsElementWhenDisplayed() {
        AtomicBoolean clicked = new AtomicBoolean(false);
        AtomicReference<String> sentText = new AtomicReference<>("");
        DriverManager.setDriver(buildDriver("visible", clicked, sentText));

        WebElement element = WaitUtils.visible(By.id("username"));

        assertNotNull(element);
    }

    @Test
    public void clickableReturnsElementWhenEnabled() {
        AtomicBoolean clicked = new AtomicBoolean(false);
        AtomicReference<String> sentText = new AtomicReference<>("");
        DriverManager.setDriver(buildDriver("clickable", clicked, sentText));

        WebElement element = WaitUtils.clickable(By.id("login-button"));

        assertNotNull(element);
    }

    @Test
    public void urlContainsReturnsTrueWhenUrlMatches() {
        AtomicBoolean clicked = new AtomicBoolean(false);
        AtomicReference<String> sentText = new AtomicReference<>("");
        DriverManager.setDriver(buildDriver("visible", clicked, sentText));

        assertTrue(WaitUtils.urlContains("inventory"));
    }

    private WebDriver buildDriver(String textValue, AtomicBoolean clicked, AtomicReference<String> sentText) {
        WebElement element = (WebElement) Proxy.newProxyInstance(
                WebElement.class.getClassLoader(),
                new Class<?>[]{WebElement.class},
                (proxy, method, args) -> {
                    switch (method.getName()) {
                        case "click":
                            clicked.set(true);
                            return null;
                        case "clear":
                            sentText.set("");
                            return null;
                        case "sendKeys":
                            StringBuilder value = new StringBuilder();
                            Object[] keys = (Object[]) args[0];
                            for (Object key : keys) {
                                value.append(key);
                            }
                            sentText.set(value.toString());
                            return null;
                        case "getText":
                            return textValue;
                        case "isDisplayed":
                            return true;
                        case "isEnabled":
                            return true;
                        case "getLocation":
                            return new Point(0, 0);
                        case "getSize":
                            return new Dimension(100, 30);
                        case "getRect":
                            return new Rectangle(0, 0, 100, 30);
                        case "getTagName":
                            return "input";
                        case "isSelected":
                            return false;
                        case "equals":
                            return proxy == args[0];
                        case "hashCode":
                            return System.identityHashCode(proxy);
                        case "toString":
                            return "FakeElement";
                        default:
                            return null;
                    }
                });

        return (WebDriver) Proxy.newProxyInstance(
                WebDriver.class.getClassLoader(),
                new Class<?>[]{WebDriver.class},
                (proxy, method, args) -> {
                    switch (method.getName()) {
                        case "findElement":
                            return element;
                        case "getCurrentUrl":
                            return "https://example.com/inventory";
                        case "getTitle":
                            return "Inventory";
                        case "quit":
                            return null;
                        default:
                            return null;
                    }
                });
    }
}
