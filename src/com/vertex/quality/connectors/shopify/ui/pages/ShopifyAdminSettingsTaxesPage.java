package com.vertex.quality.connectors.shopify.ui.pages;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Shopify admin panel -> Settings -> Taxes and duties page - contains all helper methods & variables
 *
 * @author Shivam.Soni
 */
public class ShopifyAdminSettingsTaxesPage extends ShopifyPage {

    protected By allListedApps = By.xpath("(.//h2[text()='Tax services']/ancestor::div)[15]/following-sibling::div");
    protected String listedAppName = ".//span[normalize-space(.)='<<text_replace>>']";
    protected String addAppButton = ".//span[normalize-space(.)='<<text_replace>>']/parent::div/parent::div/following-sibling::div//button[normalize-space(.)='Add App']";
    protected By shopifyAppAccountLoginHeader = By.className("login-card ");
    protected By appAccountListHeader = By.xpath(".//h1[text()='Choose an account']");
    protected By allAppAccounts = By.xpath(".//h1[text()='Choose an account']/following-sibling::div//a");
    protected By vertexAppAccount = By.xpath(".//div[normalize-space(.)='connectorsdevelopment@vertexinc.com']");
    protected String appStatusConnected = "(.//span[normalize-space(.)='<<text_replace>>']/following-sibling::span//span)[last()]";
    protected String manageAppButton = ".//span[normalize-space(.)='<<text_replace>>']/parent::div/parent::div/following-sibling::div//a[normalize-space(.)='Manage Account']";
    protected String activateButton = ".//span[normalize-space(.)='<<text_replace>>']/parent::div/parent::div/following-sibling::div//button[normalize-space(.)='Activate']";

    /**
     * Parameterized constructor of the class that helps to initialize the object & to access the parents.
     *
     * @param driver Object of WebDriver
     */
    public ShopifyAdminSettingsTaxesPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Clicks on the Add App button
     *
     * @param appName name of the add which is to be installed
     * @return Shopify Install App confirmation page
     */
    public ShopifyAppInstallConfirmationPage clickAddAppButton(String appName) {
        waitForPageLoad();
        wait.waitForAllElementsPresent(allListedApps);
        WebElement addApp = wait.waitForElementPresent(By.xpath(addAppButton.replace("<<text_replace>>", appName)));
        click.javascriptClick(addApp);
        waitForPageLoad();
        selectVertexAppAccount();
        VertexLogger.log("Adding Vertex's Taxation App/ Service", VertexLogLevel.INFO);
        return new ShopifyAppInstallConfirmationPage(driver);
    }

    /**
     * Selects the Vertex's App account
     */
    public void selectVertexAppAccount() {
        waitForPageLoad();
        if (driver.getCurrentUrl().contains("accounts.shopify.com/select")) {
            click.javascriptClick(wait.waitForElementPresent(vertexAppAccount));
            waitForPageLoad();
        }
    }

    /**
     * Validates whether Vertex App is connected successfully or not
     *
     * @param appName name of the app which status to be checked
     * @return true or false based on condition match
     */
    public boolean verifyAppConnectionEstablished(String appName) {
        boolean isConnected = false;
        waitForPageLoad();
        wait.waitForAllElementsPresent(allListedApps);
        if (element.isElementDisplayed(By.xpath(listedAppName.replace("<<text_replace>>", appName)))
                && "Connected".equalsIgnoreCase(text.getElementText(By.xpath(appStatusConnected.replace("<<text_replace>>", appName))))) {
            VertexLogger.log(appName + " App is connected", VertexLogLevel.INFO);
            isConnected = true;
        } else {
            VertexLogger.log(appName + " App is not connected", VertexLogLevel.ERROR);
        }
        return isConnected;
    }

    /**
     * Activates the tax calc app
     *
     * @param appName name of the app which is to be activated
     */
    public void activateTheApp(String appName) {
        waitForPageLoad();
        wait.waitForAllElementsPresent(allListedApps);
        click.javascriptClick(By.xpath(activateButton.replace("<<text_replace>>", appName)));
        waitForPageLoad();
        VertexLogger.log("Vertex's Taxation App/ Service is activated", VertexLogLevel.INFO);
    }
}
