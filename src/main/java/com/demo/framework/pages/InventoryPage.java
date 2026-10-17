package com.demo.framework.pages;

import org.openqa.selenium.By;

public final class InventoryPage extends BasePage {
    private final By title = By.cssSelector("[data-test='title']");
    private final By backpackAddButton = By.id("add-to-cart-sauce-labs-backpack");
    private final By cartLink = By.cssSelector("[data-test='shopping-cart-link']");
    private final By cartBadge = By.cssSelector("[data-test='shopping-cart-badge']");

    public boolean isLoaded() {
        return "Products".equals(text(title));
    }

    public InventoryPage addBackpackToCart() {
        click(backpackAddButton);
        return this;
    }

    public String cartCount() {
        return text(cartBadge);
    }

    public CartPage openCart() {
        click(cartLink);
        return new CartPage();
    }
}
