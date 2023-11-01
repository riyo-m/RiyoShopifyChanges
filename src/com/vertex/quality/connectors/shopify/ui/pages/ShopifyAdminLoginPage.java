package com.vertex.quality.connectors.shopify.ui.pages;

import com.vertex.quality.connectors.shopify.common.ShopifyDataUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Shopify Admin panel's login page - contains all the login related methods & variables
 *
 * @author Shivam.Soni
 */
public class ShopifyAdminLoginPage extends ShopifyPage {

    static String adminUserEmail = ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER.text;
    static String adminUserPassword = ShopifyDataUI.AdminPanelData.VTX_QA_ADMIN_USER_KEY.text;

    protected By emailBox = By.xpath("(.//label[text()='Email']/following-sibling::div//input)[2]");
    protected By continueWithEmail = By.xpath(".//button[text()='Continue with Email']");
    protected By passwordBox = By.cssSelector(".ui-password__input");
    protected By logInButton = By.xpath(".//button[text()='Log in']");
    protected By partnerAccountHeader = By.xpath(".//h1[text()='Your partner accounts']");
    protected By allPartnerAccounts = By.xpath(".//h1[text()='Your partner accounts']/following-sibling::ul//li");
    protected By vertexIncPartnerAccount = By.xpath(".//h1[text()='Your partner accounts']/following-sibling::ul//span[normalize-space(.)='Vertex Inc.']");

    /**
     * Parameterized constructor of the class that helps to initialize the object & to access the parents.
     *
     * @param driver Object of WebDriver
     */
    public ShopifyAdminLoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Enters email id
     *
     * @param email value of email
     */
    private void enterEmail(String email) {
        waitForPageLoad();
        text.enterText(wait.waitForElementEnabled(emailBox), email);
    }



    /**
     * Clicks on Continue with Email button
     */
    private void clickContinueEmail() {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementEnabled(continueWithEmail));
        waitForPageLoad();
    }

    /**
     * Enters password
     *
     * @param pwd value of email
     */
    private void enterPassword(String pwd) {
        waitForPageLoad();
        text.enterText(wait.waitForElementEnabled(passwordBox), pwd);
    }


    /**
     * Clicks on LogIn button
     */
    private void clickLogInButton() {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementEnabled(logInButton));
        waitForPageLoad();
    }

    /**
     * Log In to the admin panel
     *
     * @param email email value
     * @param pwd   password value
     */
    public void loginToAdminPanel(String email, String pwd) {
        waitForPageLoad();
        enterEmail(email);
        clickContinueEmail();
        enterPassword(pwd);
        clickLogInButton();
        waitForPageLoad();
        selectVertexIncPartnerAccount();
    }

    /**
     * Log In to the admin panel with default user
     */
    public void loginToAdminPanel() {
        waitForPageLoad();
        loginToAdminPanel(adminUserEmail, adminUserPassword);
        waitForPageLoad();
    }

    /**
     * Selects Vertex Inc. partner account after the login
     */
    public void selectVertexIncPartnerAccount() {
        waitForPageLoad();
        if (driver.getCurrentUrl().contains("partners")) {
            wait.waitForElementPresent(partnerAccountHeader);
            wait.waitForAllElementsPresent(allPartnerAccounts);
            click.moveToElementAndClick(wait.waitForElementPresent(vertexIncPartnerAccount));
            waitForPageLoad();
        }
    }
}
