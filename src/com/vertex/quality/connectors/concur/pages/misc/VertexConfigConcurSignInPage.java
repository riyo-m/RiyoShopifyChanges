package com.vertex.quality.connectors.concur.pages.misc;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * represents the login page of the Vertex Concur environment
 * contains all the functions needed to login
 *
 * @author alewis
 */
public class VertexConfigConcurSignInPage extends VertexPage {

    protected By userName = By.id("username");
    protected By passWord = By.id("password");
    protected By login = By.xpath("//button[@id='login']");
    protected By verifyConnectorLogin = By.xpath("//main[@class='content__container']//*[contains(text(),'VERTEX® TAX LINKS FOR SAP® CONCUR® System Status')]");
    protected By logoutButton = By.className("utility-control__btn");

    public VertexConfigConcurSignInPage(WebDriver driver) {
        super(driver);
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
    public void clickLogin() {
        WebElement submit = wait.waitForElementDisplayed(login);
        click.clickElementCarefully(submit);
    }

    /**
     * click logout button
     */
    public void clickLogoutButton() {
        WebElement signOutButton = wait.waitForElementDisplayed(logoutButton);
        click.clickElement(signOutButton);
    }

    /**
     * verify the login page
     *
     * @return status
     */
    public boolean verifyVertexConcurLogin() {
        WebElement connectorNameFiled = wait.waitForElementDisplayed(verifyConnectorLogin);
        boolean status = connectorNameFiled.isDisplayed();
        return status;
    }
}