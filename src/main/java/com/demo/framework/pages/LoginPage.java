package com.demo.framework.pages;

import com.demo.framework.config.ConfigManager;
import org.openqa.selenium.By;

public final class LoginPage extends BasePage {
    private final By username = By.id("user-name");
    private final By password = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public LoginPage open() {
        driver.get(ConfigManager.get("baseUrl"));
        return this;
    }

    public InventoryPage loginAs(String user, String secret) {
        type(username, user);
        type(password, secret);
        click(loginButton);
        return new InventoryPage();
    }

    public LoginPage submitInvalidCredentials(String user, String secret) {
        type(username, user);
        type(password, secret);
        click(loginButton);
        return this;
    }

    public String errorMessage() {
        return text(errorMessage);
    }
}
