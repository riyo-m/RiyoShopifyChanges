package com.vertex.quality.connectors.tradeshift.pages;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TradeshiftSupplierSignInPage extends VertexPage {
    //Page objects
    protected By usernameInput = By.id("j_username");
    protected By passwordInput = By.id("j_password");
    protected By loginButton = By.id("proceed");
    protected By createDocPage = By.xpath("//*[@id=\"root\"]/div/div[2]/div[1]/div[2]/div[2]/div/button");
    protected By invoicePageLink = By.xpath("//div/span[contains(text(),'Create invoice')]");
    protected By dismissMessage = By.xpath("//*[@id=\"frames-container\"]/div[2]/div/ts-modal//div/ts-header/ts-button");
    protected By applinks = By.className("//a/div/img[contains(@alt,'Invoice')]");
    protected By creditNoteLink = By.xpath("//*[@id=\"frames-container\"]/div[2]/div/ts-modal/div/div/div[6]/div/div/h4");
    protected By purchaseOrderLink = By.xpath("//*[@id=\"frames-container\"]/div[2]/div/ts-modal/div/div/div[5]/div/div/h4");
    protected By invoiceFromPOLink = By.xpath("//*[@id=\"frames-container\"]/div[2]/div/ts-modal/div/div/div[4]/div/div/h4");
    protected By supplierInvoiceLink = By.xpath("/html/body/div[1]/div[1]/div/div/section[1]/ds-home-component/div/div[3]/ds-document-layout/div/div[2]/ds-document-actions/div/div/div/div[1]/div/div[2]/div[1]/span");
    protected By supplierCreditNoteLink = By.xpath("/html/body/div[2]/aside[4]/div/menu/li[3]/button/span");
    protected By supplierPurchaseOrderLink = By.xpath("/html/body/div[2]/aside[4]/div/menu/li[5]/button");
    protected By otherDocumentsLink = By.xpath("/html/body/div[1]/div[1]/div/div/section[1]/ds-home-component/div/div[3]/ds-document-layout/div/div[2]/ds-document-actions/div/div/div/div[2]/div/div[2]/div[1]/span");
    protected By acceptCookieButtons = By.id("cookie-consent-accept-all");
    protected By notNowButton = By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div/button[1]");

    //variables
    public String devUrl = "https://sandbox.tradeshift.com";
    public String usernameValue = "Proservcustsetup+Services_US_Supplier1_Sandbox@account.tradeshift.com";
    public String passwordValue = "Shift1234!";

    public TradeshiftSupplierSignInPage(WebDriver driver) { super(driver); };


    /**
     * Signs into Tradeshift as a supplier
     * */
    public void tradeshiftSupplierSignIn(){
        driver.get(devUrl);
        WebElement acceptCookies = wait.waitForElementDisplayed(acceptCookieButtons);
        click.clickElementCarefully(acceptCookies);
        WebElement username = wait.waitForElementDisplayed(usernameInput);
        click.clickElementCarefully(username);
        username.sendKeys(usernameValue);
        WebElement password = wait.waitForElementDisplayed(passwordInput);
        click.clickElementCarefully(password);
        password.sendKeys(passwordValue);
        WebElement login = wait.waitForElementDisplayed(loginButton);
        click.clickElementCarefully(login);
    }


    /**
     * Navigates to the credit note page
     * when logged into Tradeshift as a supplier
     * */
    public void toCreditNotePageSupplier(){
        WebElement otherDocs = wait.waitForElementDisplayed(otherDocumentsLink);
        click.clickElementCarefully(otherDocs);
        WebElement creditNote = wait.waitForElementDisplayed(supplierCreditNoteLink);
        click.clickElementCarefully(creditNote);
    }



    /**
     * Navigates to the purchase order page
     * when logged into Tradeshift as a supplier
     * */
    public void toPurchaseOrderSupplier(){
        WebElement otherDocs = wait.waitForElementDisplayed(otherDocumentsLink);
        click.clickElementCarefully(otherDocs);
        WebElement purchaseOrders = wait.waitForElementDisplayed(supplierPurchaseOrderLink);
        click.clickElementCarefully(purchaseOrders);
    }


    /**
     * Navigates to the invoice page as a supp;ier
     * */
    public void toInvoicePageSupplier(){
        WebElement supplerInvoice = wait.waitForElementDisplayed(supplierInvoiceLink);
        click.clickElementCarefully(supplerInvoice);
    }

    /**
     * Used for switching iframes on the Tradeshift UI
     * to enable navigation
     * */
    public void switchIframes(){
        jsWaiter.sleep(2000);
        try {
            driver.switchTo().frame(driver.findElement(By.id("main-app-iframe")));
        }
        catch (Exception e) {
            driver.switchTo().frame(driver.findElement(By.xpath("//*[@id=\"legacy-frame\"]")));
        }
    }
}