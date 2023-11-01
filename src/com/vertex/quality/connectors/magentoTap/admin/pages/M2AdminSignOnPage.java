package com.vertex.quality.connectors.magentoTap.admin.pages;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * representation of the page for signing into this admin site Note- this is the
 * default page that loads when you first access the configuration site with the
 * standard URL
 *
 * @author alewis
 */
public class M2AdminSignOnPage extends VertexPage {

    /**
     * Parameterized constructor of the class
     *
     * @param driver Object of WebDriver.
     */
    public M2AdminSignOnPage(WebDriver driver) {
        super(driver);
    }

    protected By usernameField = By.id("username");
    protected By passwordField = By.id("login");

    protected By loginFormID = By.id("login-form");
    protected By fieldsetClass = By.className("admin__fieldset");
    protected By formActionsClass = By.className("form-actions");
    protected By actionsClass = By.className("actions");
    protected By loginButtonClass = By.className("action-login");
    protected By loginButtonTag = By.tagName("span");

    /**
     * Locates the Login Button
     *
     * @return the Login Button
     */
    protected WebElement findLoginButton() {
        waitForPageLoad();
        WebElement loginForm = wait.waitForElementPresent(loginFormID);
        WebElement fieldSet = loginForm.findElement(fieldsetClass);
        WebElement formAction = fieldSet.findElement(formActionsClass);
        WebElement actions = formAction.findElement(actionsClass);
        List<WebElement> buttons = actions.findElements(loginButtonClass);
        WebElement loginButton = null;

        for (WebElement button : buttons) {
            List<WebElement> loginLabels = button.findElements(loginButtonTag);

            for (WebElement label : loginLabels) {
                String labelType = label.getTagName();
                if ("span".equals(labelType)) {
                    loginButton = button;
                }
            }
        }
        if (loginButton == null) {
            VertexLogger.log("loginButton is null", VertexLogLevel.INFO, M2AdminSignOnPage.class);
        }
        return loginButton;
    }

    /**
     * enters a string into the 'username' text box
     *
     * @param username the string that is entered into the 'username' text box
     */
    public void enterUsername(String username) {
        waitForPageLoad();
        WebElement field = driver.findElement(usernameField);
        field.clear();
        field.sendKeys(username);
    }

    /**
     * enters a string into the 'password' text box
     *
     * @param password the string that is entered into the 'password' text box
     */
    public void enterPassword(String password) {
        waitForPageLoad();
        WebElement field = driver.findElement(passwordField);
        field.clear();
        field.sendKeys(password);
    }

    /**
     * tries to log into this homepage site
     *
     * @return a representation of the page that loads right after successfully logging in
     */
    public M2AdminHomepage login() {
        waitForPageLoad();
        WebElement loginButton = findLoginButton();

        click.clickElement(loginButton);

        return initializePageObject(M2AdminHomepage.class);
    }

    /**
     * collects the contents of the 'username' text box
     *
     * @return the contents of the 'username' text box
     */
    public String getUsername() {
        WebElement field = wait.waitForElementPresent(usernameField);
        String text = field.getText();

        return text;
    }

    /**
     * checks whether the 'username' text box is visible/editable
     *
     * @return whether the 'username' text box is visible/editable
     */
    public boolean isUsernameFieldPresent() {
        boolean isUsernameFieldPresent;

        try {
            wait.waitForElementPresent(usernameField);
            isUsernameFieldPresent = true;
        } catch (Exception e) {
            isUsernameFieldPresent = false;
        }

        return isUsernameFieldPresent;
    }

    /**
     * checks whether the 'password' text box is visible/editable
     *
     * @return whether the 'password' text box is visible/editable
     */
    public boolean isPasswordFieldPresent() {
        boolean isPasswordFieldPresent = false;

        try {
            driver.findElement(passwordField);
            isPasswordFieldPresent = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isPasswordFieldPresent;
    }

    /**
     * checks whether the button for submitting login credentials is
     * visible/clickable
     *
     * @return whether the button for submitting login credentials is
     * visible/clickable
     */
    public boolean isLoginButtonPresent() {
        boolean isLoginButtonPresent = false;

        try {
            WebElement loginButton = findLoginButton();
            isLoginButtonPresent = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isLoginButtonPresent;
    }
}