package com.demo.framework.pages;

import org.openqa.selenium.By;

public final class CheckoutPage extends BasePage {
    private final By firstName = By.id("first-name");
    private final By lastName = By.id("last-name");
    private final By postalCode = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By finishButton = By.id("finish");
    private final By completeHeader = By.cssSelector("[data-test='complete-header']");

    public CheckoutPage enterCustomer(String first, String last, String postal) {
        type(firstName, first);
        type(lastName, last);
        type(postalCode, postal);
        click(continueButton);
        return this;
    }

    public CheckoutPage finish() {
        click(finishButton);
        return this;
    }

    public String confirmation() {
        return text(completeHeader);
    }
}
