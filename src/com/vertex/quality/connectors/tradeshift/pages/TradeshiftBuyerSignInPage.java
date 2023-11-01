package com.vertex.quality.connectors.tradeshift.pages;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.List;
import java.util.Map;

public class TradeshiftBuyerSignInPage extends VertexPage {
    //Page objects
    protected By usernameInput = By.id("j_username");
    protected By passwordInput = By.id("j_password");
    protected By loginButton = By.id("proceed");
    protected By createDocPage = By.xpath("//*[@id=\"root\"]/div/div[2]/div[1]/div[2]/div[2]/div/button");
    protected By invoicePageLink = By.xpath("//a[@title='Invoice']");
    protected By dismissMessage = By.xpath("//*[@id=\"frames-container\"]/div[2]/div/ts-modal//div/ts-header/ts-button");
    protected By applinks = By.className("//a/div/img[contains(@alt,'Invoice')]");
    protected By creditNoteLink = By.xpath("//a[@title='Credit Note']");
    protected By purchaseOrderLink = By.xpath("//*[@id=\"frames-container\"]/div[2]/div/ts-modal/div/div/div[5]/div/div/h4");
    protected By invoiceFromPOLink = By.xpath("//*[@id=\"frames-container\"]/div[2]/div/ts-modal/div/div/div[4]/div/div/h4");
    protected By supplierInvoiceLink = By.xpath("/html/body/div[1]/div[1]/div/div/section[1]/ds-home-component/div/div[3]/ds-document-layout/div/div[2]/ds-document-actions/div/div/div/div[1]/div/div[2]/div[1]/span");
    protected By supplierCreditNoteLink = By.xpath("/html/body/div[2]/aside[4]/div/menu/li[3]/button/span");
    protected By supplierPurchaseOrderLink = By.xpath("/html/body/div[2]/aside[4]/div/menu/li[5]/button");
    protected By otherDocumentsLink = By.xpath("/html/body/div[1]/div[1]/div/div/section[1]/ds-home-component/div/div[3]/ds-document-layout/div/div[2]/ds-document-actions/div/div/div/div[2]/div/div[2]/div[1]/span");
    protected By acceptCookieButtons = By.id("cookie-consent-accept-all");
    protected By documentManagerButton = By.xpath("//img[@alt='Create Documents']");
    protected By allAppsButton = By.xpath("//*/img[contains(@alt, 'All apps')]");
    protected By searchAppInput = By.xpath("//*[@id=\"root\"]/div/div[2]/div[@id='frames-container']/div[@class='modal-container']/div/ts-modal/div/ts-search");
    protected By openButton = By.className("open-button");
    protected By SearchRoot = By.cssSelector("ts-modal");
    protected By SearchInnerRoot = By.cssSelector("ts-search");
    protected By CreateDocInput = By.xpath("//*/input[@placeholder='Search apps']");
    //variables
    public String devUrl = "https://sandbox.tradeshift.com";
    public String usernameValue = "Proservcustsetup+Vertex_US_Branch1_Sandbox@account.tradeshift.com";
    public String passwordValue = "Shift1234!";

    public TradeshiftBuyerSignInPage(WebDriver driver) { super(driver); };

    /**
     * Signs into Tradeshift as a buyer
     * */
    public void tradeshiftSignIn(){
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
     * Dismiss the modal that pops up during each test
     * TODO figure out why you can't just click the 'X'
     * */
    public void dismissPopUp(){
        int i=0;
        WebElement dismissLink = wait.waitForElementDisplayed(By.xpath("//*[@id=\"frames-container\"]/div[2]/div/ts-modal/div/div[3]/ts-button"));
        click.clickElementCarefully(dismissLink);
        WebElement dismissLink1 = wait.waitForElementDisplayed(By.xpath("//*[@id=\"frames-container\"]/div[2]/div/ts-modal/div/div[3]/ts-button[2]"));
        while(i<3){
            click.clickElementCarefully(dismissLink1);
            i++;
        }
        click.clickElementCarefully(dismissLink);
    }

    /**
     * Navigates to the invoice page from the
     * Tradeshift homepage
     * */
    public void toInvoicePage(){
        dismissPopUp();
        WebElement createDocumentPage = wait.waitForElementDisplayed(createDocPage);
        click.clickElementCarefully(createDocumentPage);
        WebElement invoiceLink = wait.waitForElementDisplayed(invoicePageLink);
        click.clickElementCarefully(invoiceLink);
    }

    /**
     * Navigates to the invoice page from the Tradeshift homepage; alternative path
     */
    public void toAlternativeInvoicePage() {
        toCreateDocuments();
        try {
            driver.switchTo().frame(driver.findElement(By.id("main-app-iframe")));
        }
        catch (Exception e) {
            driver.switchTo().frame(driver.findElement(By.id("legacy-frame")));
        }
        WebElement invoiceLink = wait.waitForElementDisplayed(invoicePageLink);
        click.clickElementCarefully(invoiceLink);
    }

    /**
     * Navigates to the credit note page
     * */
    public void toCreditNotePage(){
        toCreateDocuments();
        try {
            driver.switchTo().frame(driver.findElement(By.id("main-app-iframe")));
        }
        catch (Exception e) {
            driver.switchTo().frame(driver.findElement(By.id("legacy-frame")));
        }
        WebElement creditNote = wait.waitForElementDisplayed(creditNoteLink);
        click.clickElementCarefully(creditNote);


    }


    /**
     * Navigates to the purchase order page
     * */
    public void toPurchaseOrderPage(){
        WebElement createDocumentPage = wait.waitForElementDisplayed(createDocPage);
        click.clickElementCarefully(createDocumentPage);
        WebElement purchaseOrder = wait.waitForElementDisplayed(purchaseOrderLink);
        click.clickElementCarefully(purchaseOrder);

        try {
            driver.switchTo().frame(driver.findElement(By.id("main-app-iframe")));
            click.clickElementCarefully(By.id("documentId"));
        }
        catch (Exception e) {
            driver.switchTo().frame(driver.findElement(By.id("legacy-frame")));
        }
    }


    /**
     * Navigates to the purchase order page
     * */
    public void toInvoiceFromPOPage(){
        WebElement createDocumentPage = wait.waitForElementDisplayed(createDocPage);
        click.clickElementCarefully(createDocumentPage);
        WebElement invoiceFromPO = wait.waitForElementDisplayed(invoiceFromPOLink);
        click.clickElementCarefully(invoiceFromPO);
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

    /**
     * Gets the shadow dom for an element in order to interact with it
     * otherwise it isn't recognized
     *
     * @param element the web element that is the parent of the shadow element
     * @return the shadow root to be interacted with
     * */
    public WebElement expandRootElement(WebElement element) {
		Object shadowRoot = ((JavascriptExecutor) driver).executeScript("return arguments[0].shadowRoot", element);
		String id = (String) ((Map<String, Object>) shadowRoot).get("shadow-6066-11e4-a52e-4f735466cecf");

		RemoteWebElement shadowRootElement = new RemoteWebElement();
		shadowRootElement.setParent((RemoteWebDriver) driver);
		shadowRootElement.setId(id);

		WebElement shadowContent = shadowRootElement.findElement(By.cssSelector("input"));

        return shadowContent;
    }

    /**
     * Clicks the all apps button then navigates to the create documents page
     * */
    public void toCreateDocuments(){
        WebElement allApps = wait.waitForElementDisplayed(allAppsButton);
        click.clickElementCarefully(allApps);

        WebElement shadowRootParent = wait.waitForElementDisplayed(SearchInnerRoot);
        WebElement shadowRootSearchInput = expandRootElement(shadowRootParent);
        shadowRootSearchInput.sendKeys("create");

        WebElement createDocumentPage = wait.waitForElementDisplayed(documentManagerButton);
        click.clickElementCarefully(createDocumentPage);
    }
}