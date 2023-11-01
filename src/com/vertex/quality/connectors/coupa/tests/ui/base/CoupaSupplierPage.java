package com.vertex.quality.connectors.coupa.tests.ui.base;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.coupa.pages.CoupaSignInPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.testng.Assert.assertTrue;

/**
 * represents the login page of the Coupa supplier environment
 * contains all the functions needed to login
 *
 * @author alewis
 */
class CoupaSupplierLoginPage extends CoupaSignInPage
{
    protected By userName= By.xpath("//input[@id='sessions_email']");
    protected By passWord= By.xpath("//input[@id='sessions_password']");
    protected By coupaSupplierLogInButton = By.xpath("//span[contains(text(),'Log In')]");
    protected By supplierHomePage = By.xpath("//div[@id='wrap']");
    protected By supplierInvoiceButton = By.xpath("//body/div[@id='wrap']/div[@id='']/div[@id='global-tab-bar']");

    public String coupaSupplierURL = "https://supplier-demo.coupahost.com/home";
    public String coupaSupplierUsername = "vertexconnectors+coupatestsupplier000@gmail.com";
    public String coupaSupplierPassword = "supplier000";


    public CoupaSupplierLoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Method to login for Vertex supplier Coupa login
     */
    public void verifyCoupaLogin() {
        driver.get(coupaSupplierURL);
        CoupaSupplierLoginPage signInPage = new CoupaSupplierLoginPage(driver);
        signInPage.enterCoupaSupplierUsername(coupaSupplierUsername);
        signInPage.enterCoupaSupplierPassword(coupaSupplierPassword);
        signInPage.clickSupplierlogin();
        waitForPageLoad();
        //assertTrue(signInPage.verifyCoupaSupplierLogin());
    }

    /**
     * enters a string into the 'username' text box
     *
     * @param uName the string that is entered into the 'username' text box
     */
    public void enterCoupaSupplierUsername(String uName) {
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
    public void enterCoupaSupplierPassword(String pwd) {
        WebElement passwordField = wait.waitForElementDisplayed(passWord);
        click.clickElementCarefully(passwordField);
        passwordField.clear();
        text.enterText(passwordField, pwd);
    }

    /**
     * click on coupa supplier login button
     */
    public void clickSupplierlogin() {
        WebElement submit = wait.waitForElementDisplayed(coupaSupplierLogInButton);
        click.clickElementCarefully(coupaSupplierLogInButton);
    }

    /**
     * click on coupa supplier login button
     */
    public void verifyCoupaSupplierLogin() {
        WebElement submit = wait.waitForElementDisplayed(supplierHomePage);
        click.clickElementCarefully(supplierHomePage);
    }

    /**
     * click on coupa supplier login button
     */
    public void supplierInvoice() {
        WebElement submit = wait.waitForElementDisplayed(supplierInvoiceButton);
        click.clickElementCarefully(supplierInvoiceButton);
    }

    public void userTax() {

    }

    public void vertexTax() {

    }

}
