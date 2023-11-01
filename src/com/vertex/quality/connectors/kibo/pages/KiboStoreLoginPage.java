package com.vertex.quality.connectors.kibo.pages;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.kibo.enums.KiboCredentials;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class KiboStoreLoginPage extends VertexPage {

    public KiboStoreLoginPage(WebDriver driver) {
        super(driver);
    }

    protected By storeLoginButton = By.xpath(".//h2//following-sibling::button[normalize-space(.)='Log In']");
    protected By logInEmail = By.xpath(".//input[@name='email']");
    protected By loginPassword = By.xpath(".//input[@name='password']");
    protected By loginLoginButton = By.xpath(".//input[@name='password']//following-sibling::div//button[text()='Log In']");

    /**
     * Loads Kibo Store's login page.
     */
    public void loadKiboStoreLoginPage() {
        driver.get(KiboCredentials.KIBO_STORE_URL.value);
    }

    /**
     * Used to get logged-in to Kibo's store
     *
     * @param email    email for login user
     * @param password password to login user
     */
    public void loginToKiboStore(String email, String password) {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementPresent(storeLoginButton));
        waitForPageLoad();
        text.enterText(wait.waitForElementPresent(logInEmail), email);
        text.enterText(wait.waitForElementPresent(loginPassword), password);
        click.moveToElementAndClick(wait.waitForElementPresent(loginLoginButton));
        waitForPageLoad();
    }
}
