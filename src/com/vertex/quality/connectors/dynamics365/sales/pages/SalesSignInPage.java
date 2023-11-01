package com.vertex.quality.connectors.dynamics365.sales.pages;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
/**
 * this class represents Sales login process
 *
 * @author Shruti
 */
public class SalesSignInPage extends VertexPage {
    protected By emailInnerFieldLoc = By.className("ltr_override");
    protected By passwordFieldLoc = By.className("form-control");
    protected By signInButtonLoc = By.id("idSIButton9");
    protected By staySignedInNoButtonLoc = By.id("idBtn_Back");

    public SalesSignInPage(WebDriver driver) {
        super(driver);
    }

    /**
     * On Sign In page gets the email address field and enters
     * the environment emailID
     *  @param username
     */
    public void enterEmailAddress(String username)
    {
        WebElement emailAddressField = wait.waitForElementEnabled(emailInnerFieldLoc);
        text.enterText(emailAddressField, username);
        WebElement nextButton = wait.waitForElementEnabled(signInButtonLoc);
        click.clickElement(nextButton);
        wait.waitForElementNotDisplayedOrStale(emailAddressField, 5);
    }

    /**
     * navigates to the password field and enters the environment password
     *  @param password
     */
    public void enterPassword(String password)
    {
        WebElement passwordField = wait.waitForElementPresent(passwordFieldLoc);
        text.enterText(passwordField, password);
        WebElement signInButton = wait.waitForElementPresent(signInButtonLoc);
        click.clickElement(signInButton);
        wait.waitForElementNotDisplayedOrStale(passwordField, 5);
    }
    /**
     * method to no longer stay logged in by clicking No
     *  @return sales admin home page
     */
    public SalesAdminHomePage clickDoNotStayLoggedIn()
    {
        WebElement noButton = wait.waitForElementEnabled(staySignedInNoButtonLoc);
        click.clickElement(noButton);
        return initializePageObject(SalesAdminHomePage.class);
    }
}

