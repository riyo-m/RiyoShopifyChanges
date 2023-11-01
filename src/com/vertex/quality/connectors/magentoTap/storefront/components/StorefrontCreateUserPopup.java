package com.vertex.quality.connectors.magentoTap.storefront.components;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Popup that forces you to sign in a user or create an account when purchasing a downloadable product
 *
 * @author alewis
 */
public class StorefrontCreateUserPopup extends VertexComponent {
    By customerEmailID = By.id("customer-email");
    By passID = By.id("pass");
    By sendID = By.id("send2");
    By maskClass = By.className("loading-mask");

    /**
     * Parameterized constructor of the class
     *
     * @param driver Object of WebDriver
     */
    public StorefrontCreateUserPopup(WebDriver driver, VertexPage parent) {
        super(driver, parent);
    }

    /**
     * Enter a users information
     *
     * @param username Username
     * @param password Password
     */
    public void enterUserInfo(String username, String password) {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        wait.waitForElementDisplayed(customerEmailID);
        WebElement customerEmail = wait.waitForElementPresent(customerEmailID);
        text.enterText(customerEmail, username);

        WebElement passwordField = wait.waitForElementPresent(passID);
        text.enterText(passwordField, password);

        WebElement send = wait.waitForElementPresent(sendID);
        click.clickElement(send);
    }
}