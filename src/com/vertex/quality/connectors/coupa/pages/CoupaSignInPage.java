package com.vertex.quality.connectors.coupa.pages;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.vertex.quality.common.utils.selenium.VertexDropdownUtilities;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * represents the login page of the Coupa environment
 * contains all the functions needed to login
 *
 * @author alewis
 */
public class CoupaSignInPage extends VertexPage
{
    protected By userName = By.xpath("//input[@id='user_login']");
    protected By passWord = By.xpath("//input[@id='user_password']");
    protected By coupaSignInButton = By.id("login_button");


    public String coupaURL = "https://vertex-integration.coupacloud.com";
    public String coupaUsername = "kjones";
    public String coupaPassword = "Ver!tex432";

    public CoupaSignInPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Method to login for VertexCoupa login
     */
    public void verifyCoupaLogin()
    {
        driver.get(coupaURL);
        CoupaSignInPage signInPage = new CoupaSignInPage(driver);
        signInPage.enterUsername(coupaUsername);
        signInPage.enterPassword(coupaPassword);
        signInPage.clicklogin();
        waitForPageLoad();
        //assertTrue(signInPage.verifyCoupaLogin());
    }

    /**
     * enters a string into the 'username' text box
     *
     * @param uName the string that is entered into the 'username' text box
     */
    public void enterUsername(String uName) {
        WebElement userNameField = wait.waitForElementDisplayed(userName);
        click.clickElementCarefully(userNameField);
        userNameField.clear();
        text.enterText(userNameField, uName);
    }

    /**
     * enters a string into the 'password' text box
     *
     * @param pwd the string that is entered into the 'password' text box
     */
    public void enterPassword(String pwd) {
        WebElement passwordField = wait.waitForElementDisplayed(passWord);
        click.clickElementCarefully(passwordField);
        passwordField.clear();
        text.enterText(passwordField, pwd);
    }

    /**
     * click on login button
     */
    public void clicklogin() {
        WebElement submit = wait.waitForElementDisplayed(coupaSignInButton);
        click.clickElementCarefully(submit);
    }

}