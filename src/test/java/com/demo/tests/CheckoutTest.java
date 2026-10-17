package com.demo.tests;

import com.demo.framework.config.ConfigManager;
import com.demo.framework.pages.InventoryPage;
import com.demo.framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public final class CheckoutTest extends BaseTest {
    @Test(description = "A customer can add a product and complete checkout", groups = {"smoke", "regression"})
    public void completeCheckout() {
        InventoryPage inventory = new LoginPage()
                .open()
                .loginAs(ConfigManager.get("standardUsername"), ConfigManager.get("password"));

        Assert.assertTrue(inventory.isLoaded(), "Inventory page was not displayed");
        inventory.addBackpackToCart();
        Assert.assertEquals(inventory.cartCount(), "1", "Cart badge did not update");

        String confirmation = inventory.openCart()
                .checkout()
                .enterCustomer("Demo", "User", "10001")
                .finish()
                .confirmation();

        Assert.assertEquals(confirmation, "Thank you for your order!", "Checkout did not complete");
    }
}
