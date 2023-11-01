package com.vertex.quality.connectors.shopify.ui.pages;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Shopify admin panel -> Settings -> Apps and channel page - contains all helper methods & variables
 *
 * @author Shivam.Soni
 */
public class ShopifyAdminSettingsAppPage extends ShopifyPage {

    protected By installedButton = By.id("installed");
    protected By installedAppList = By.id("installed-apps-list");
    protected By allInstalledApps = By.xpath(".//*[@id='installed-apps-list']//li");
    protected String installedAppName = ".//*[@id='installed-apps-list']//li//span[text()='<<text_replace>>']";
    protected String appMoreActionButton = "(.//*[@id='installed-apps-list']//li//a[@aria-label='<<text_replace>>']/following-sibling::div//div)[15]";
    protected By appMoreActionMenu = By.xpath(".//div[contains(@class,'Polaris-Popover__Content')]");
    protected By uninstallOption = By.xpath(".//li[normalize-space(.)='Uninstall']");
    protected By uninstallAppConfirmationPopup = By.xpath("(.//div[contains(@class,'Polaris-Modal-Dialog')])[2]");
    protected By uninstallConfirmButton = By.xpath("(.//button[normalize-space(.)='Uninstall'])[last()]");

    /**
     * Parameterized constructor of the class that helps to initialize the object & to access the parents.
     *
     * @param driver Object of WebDriver
     */
    public ShopifyAdminSettingsAppPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Selects Installed apps
     */
    public void selectInstalled() {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementEnabled(installedButton));
        waitForPageLoad();
        wait.waitForAllElementsEnabled(installedAppList);
    }

    /**
     * Validates whether given app name is installed or not
     *
     * @param appName Name of the App which needs to be validated
     * @return true or false based on matching condition
     */
    public boolean verifyAppIsInstalled(String appName) {
        boolean isInstalled = false;
        waitForPageLoad();
        wait.waitForElementPresent(installedAppList);
        wait.waitForAllElementsPresent(allInstalledApps);
        if (element.isElementDisplayed(By.xpath(installedAppName.replace("<<text_replace>>", appName)))) {
            VertexLogger.log("Vertex's Taxation App/ Service is already installed", VertexLogLevel.INFO);
            isInstalled = true;
        } else {
            VertexLogger.log("Vertex's Taxation App/ Service is not installed", VertexLogLevel.INFO);
        }
        return isInstalled;
    }

    /**
     * Verify whether app installed or not & if found installed then Uninstalls the app
     *
     * @param appName name of the app that needs to be uninstalled
     */
    public void unInstallTheApp(String appName) {
        waitForPageLoad();
        wait.waitForElementPresent(installedAppList);
        wait.waitForAllElementsPresent(allInstalledApps);
        waitForPageLoad();
        wait.waitForElementPresent(installedAppList);
        wait.waitForAllElementsPresent(allInstalledApps);
        if (verifyAppIsInstalled(appName)) {
            WebElement moreAction = wait.waitForElementEnabled(By.xpath(appMoreActionButton.replace("<<text_replace>>", appName)));
            click.moveToElementAndClick(moreAction);
            wait.waitForElementPresent(appMoreActionMenu);
            click.moveToElementAndClick(wait.waitForElementPresent(uninstallOption));
            waitForPageLoad();
            wait.waitForElementPresent(uninstallAppConfirmationPopup);
            click.moveToElementAndClick(wait.waitForElementPresent(uninstallConfirmButton));
            waitForPageLoad();
            new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(uninstallAppConfirmationPopup));
            waitForPageLoad();
        }
        VertexLogger.log("Vertex's Taxation App/ Service is uninstalled", VertexLogLevel.INFO);
    }
}
