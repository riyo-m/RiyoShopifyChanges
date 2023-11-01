package com.vertex.quality.connectors.dynamics365.nav.pages;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.dynamics365.business.pages.BusinessAdminHomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NavSignOnPage extends VertexPage
{
    public NavSignOnPage(WebDriver driver) {
        super(driver);
    }
    protected By startFreeButtonLoc = By.id("cta-start-free-connect");
    protected By emailAddressFieldLoc = By.className("placeholderContainer");
    protected By signUpButtonLoc = By.className("mpl-button-span-rectangle-DynamicsOps");
    protected By textTitleLoc = By.className("text-title");
    protected By staySignedInNoButtonLoc = By.id("idBtn_Back");
    protected By staySignedInYesButtonLoc = By.className("btn-primary");
    protected By primaryClass = By.className("btn-primary");
    protected By passwordFieldLoc = By.className("form-control");
    protected By signInButtonLoc = By.id("idSIButton9");
    protected By emailInnerFieldLoc = By.className("ltr_override");

    /**
     * clicks on start free button on the current page
     */
    public void clickStartFreeButton( )
    {
        WebElement startFreeButton = wait.waitForElementPresent(startFreeButtonLoc);

        click.clickElement(startFreeButton);
        String existingWindowHandle = driver.getWindowHandle();
        window.waitForAndSwitchToNewWindowHandle(existingWindowHandle);
    }

    /**
     * navigates through the login pages until it get to the email address (username) field and enters
     * the environment username
     */
    public void enterEmailAddress( String username )
    {
        WebElement emailAddressField = wait.waitForElementEnabled(emailInnerFieldLoc);
        text.enterText(emailAddressField, username);
        WebElement nextButton = wait.waitForElementEnabled(signInButtonLoc);
        click.clickElement(nextButton);

        wait.waitForElementNotDisplayedOrStale(emailAddressField, 5);
    }

    /**
     * navigates to the password field and enters the environment password
     */
    public void enterPassword( String password )
    {
        WebElement passwordField = wait.waitForElementPresent(passwordFieldLoc);
        text.enterText(passwordField, password);
        WebElement signInButton = wait.waitForElementPresent(signInButtonLoc);
        click.clickElement(signInButton);

        wait.waitForElementNotDisplayedOrStale(passwordField, 5);
    }

    /**
     * clicks on the Ok button on the current page
     *
     * @return business admin home page
     */
    public NavAdminHomePage clickOk( )
    {
        WebElement okButton = wait.waitForElementPresent(signUpButtonLoc);
        click.clickElement(okButton);
        clickDoNotStayLoggedIn();
        return initializePageObject(NavAdminHomePage.class);
    }

    /**
     * when prompted on whether to stay signed in to reduce the number of times asked to log in,
     * click the No button
     *
     * @return business admin home page
     */
    public NavAdminHomePage clickDoNotStayLoggedIn( )
    {
        WebElement noButton = wait.waitForElementEnabled(staySignedInNoButtonLoc);
        click.clickElement(noButton);
        return initializePageObject(NavAdminHomePage.class);
    }
}
