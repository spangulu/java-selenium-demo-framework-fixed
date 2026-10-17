package com.demo.framework.pages;

import org.openqa.selenium.By;

public final class CartPage extends BasePage {
    private final By itemName = By.cssSelector("[data-test='inventory-item-name']");
    private final By checkoutButton = By.id("checkout");

    public String itemName() {
        return text(itemName);
    }

    public CheckoutPage checkout() {
        click(checkoutButton);
        return new CheckoutPage();
    }
}
