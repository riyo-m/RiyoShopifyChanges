package com.vertex.quality.connectors.dynamics365.commerce.pages;

import com.vertex.quality.connectors.dynamics365.commerce.pages.base.DCommerceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Contains methods for interacting with home page
 */
public class DCommerceHomePage extends DCommerceBasePage {

    protected By ACCEPT_COOKIES_BUTTON = By.xpath("//button[@aria-label='Accept cookies']");
    protected By PROFILE_BUTTON = By.xpath("//button[@aria-describedby='myprofilePopover']");
    protected By SIGN_IN_BUTTON = By.cssSelector("[aria-label='Sign in']");
    protected By SIGN_OUT_BUTTON = By.cssSelector("[aria-label='Sign out']");

    public DCommerceHomePage( WebDriver driver )
    {
        super(driver);
    }

    /**
     * click on sign in button and return instance of DCommerceSignInPage
     *
     * @return DCommerceSignInPage
     */
    public DCommerceSignInPage navigateToSignInPage() {
        WebElement signInButton = wait.waitForElementDisplayed(SIGN_IN_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(signInButton);

        waitForPageLoad();

        DCommerceSignInPage loginPage = new DCommerceSignInPage(driver);
        return loginPage;
    }

    /**
     * Click Accept in cookie banner
     */
    public void clickAcceptButton() {
        wait.waitForElementDisplayed(ACCEPT_COOKIES_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(ACCEPT_COOKIES_BUTTON);

        waitForPageLoad();
    }

    /**
     * sign out of account
     */
    public void signOutOfAccount() {
        wait.waitForElementDisplayed(PROFILE_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(PROFILE_BUTTON);

        wait.waitForElementDisplayed(SIGN_OUT_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(SIGN_OUT_BUTTON);

        waitForPageLoad();
    }

}
