package com.vertex.quality.connectors.dynamics365.commerce.pages;

import com.vertex.quality.connectors.dynamics365.commerce.pages.base.DCommerceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Contains methods for interacting with sign in page
 */
public class DCommerceSignInPage extends DCommerceBasePage {

    protected By EMAIL_INPUT = By.cssSelector("[id='email']");
    protected By PASSWORD_INPUT = By.cssSelector("[id='password']");
    protected By SIGN_IN_BUTTON = By.cssSelector("[id='next']");
    public DCommerceSignInPage(WebDriver driver )
    {
        super(driver);
    }


    /**
     * Enter given email address into email field
     * @param email
     */
    public void enterEmailAddress(String email) {
        WebElement emailInput = wait.waitForElementDisplayed(EMAIL_INPUT);
        text.selectAllAndInputText(emailInput, email);
    }

    /**
     * Emter given password into password field
     * @param password
     */
    public void enterPassword(String password) {
        WebElement passwordInput = wait.waitForElementDisplayed(PASSWORD_INPUT);
        text.selectAllAndInputText(passwordInput, password);
    }

    /**
     * Click on sign in button to submit form for signing in
     */
    public void clickSignIn() {
        WebElement signInButton = wait.waitForElementDisplayed(SIGN_IN_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(signInButton);

        waitForPageLoad();
    }
}
