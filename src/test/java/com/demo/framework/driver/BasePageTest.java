package com.demo.framework.driver;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import com.demo.framework.pages.BasePage;
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

public class BasePageTest {

    @AfterMethod
    public void teardown() {
        DriverManager.unload();
    }

    @Test
    public void clickUsesClickableElement() {
        AtomicBoolean clicked = new AtomicBoolean(false);
        WebDriver driver = buildDriver("", clicked, new AtomicReference<>(""));
        DriverManager.setDriver(driver);

        TestPage page = new TestPage();
        page.doClick(By.id("submit"));

        assertTrue(clicked.get());
    }

    @Test
    public void typeSendsKeysToElement() {
        AtomicBoolean clicked = new AtomicBoolean(false);
        AtomicReference<String> sentText = new AtomicReference<>("");
        WebDriver driver = buildDriver("", clicked, sentText);
        DriverManager.setDriver(driver);

        TestPage page = new TestPage();
        page.doType(By.id("username"), "demo-user");

        assertEquals(sentText.get(), "demo-user");
    }

    @Test
    public void textReturnsTrimmedValue() {
        AtomicBoolean clicked = new AtomicBoolean(false);
        AtomicReference<String> sentText = new AtomicReference<>("");
        WebDriver driver = buildDriver("  Logged in user  ", clicked, sentText);
        DriverManager.setDriver(driver);

        TestPage page = new TestPage();

        assertEquals(page.doText(By.id("title")), "Logged in user");
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

    private static final class TestPage extends BasePage {
        void doClick(By locator) {
            click(locator);
        }

        void doType(By locator, String value) {
            type(locator, value);
        }

        String doText(By locator) {
            return text(locator);
        }
    }
}
