package com.demo.framework.pages;

import com.demo.framework.driver.DriverManager;
import com.demo.framework.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class BasePage {
    protected final WebDriver driver = DriverManager.getDriver();

    protected void click(By locator) {
        WaitUtils.clickable(locator).click();
    }

    protected void type(By locator, String value) {
        var element = WaitUtils.visible(locator);
        element.clear();
        element.sendKeys(value);
    }

    protected String text(By locator) {
        return WaitUtils.visible(locator).getText().trim();
    }
}
