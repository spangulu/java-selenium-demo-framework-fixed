package com.demo.tests;

import com.demo.framework.config.ConfigManager;
import com.demo.framework.pages.InventoryPage;
import com.demo.framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public final class LoginTest extends BaseTest {
    @Test(description = "A valid user can sign in and reach the inventory page", groups = {"smoke", "regression"})
    public void validLogin() {
        InventoryPage inventory = new LoginPage()
                .open()
                .loginAs(ConfigManager.get("standardUsername"), ConfigManager.get("password"));

        Assert.assertTrue(inventory.isLoaded(), "Inventory page was not displayed");
    }

    @Test(description = "An invalid user receives a useful login error", groups = {"negative", "regression"})
    public void invalidLogin() {
        LoginPage loginPage = new LoginPage()
                .open()
                .submitInvalidCredentials("invalid_user", "wrong_password");

        Assert.assertTrue(loginPage.errorMessage().contains("Username and password do not match"),
                "Expected login validation message was not displayed");
    }
}
