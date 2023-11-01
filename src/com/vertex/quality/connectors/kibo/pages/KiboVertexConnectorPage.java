package com.vertex.quality.connectors.kibo.pages;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.kibo.components.KiboMainMenuNavPanel;
import com.vertex.quality.connectors.kibo.dialog.KiboApplicationConfigurationDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * this class represents the vertex connector page
 * on Kibo portal.
 * it contains all the needed methods to interact with this page.
 *
 * @author osabha
 */
public class KiboVertexConnectorPage extends VertexPage {
    public KiboApplicationConfigurationDialog configurationDialog;

    protected By configApplicationButtonLoc = By.className("mozu-c-btn__content--card");
    protected By mainMenuButtonLoc = By.className("mozu-c-nav__hamburger");
    protected By mainMenuScreenBlockerLoc = By.className("mozu-c-modal__screen");
    protected By applicationSwitchLoc = By.className("mozu-c-checkbox__input");
    protected By loadMaskLoc = By.className("taco-loadmask");
    protected By mainMenuButton = By.xpath("//span/div/div/div/div/a");
    protected By connectorAllSettingMenu = By.xpath(".//ol//li[starts-with(@class,'site-nav__item')]");
    protected By appVersion = By.xpath(".//label[normalize-space(.)='Version']/following-sibling::p[1]");

    public KiboVertexConnectorPage(WebDriver driver) {
        super(driver);
        this.configurationDialog = new KiboApplicationConfigurationDialog(driver, this);
    }

    /**
     * this method located enable application switch and enables  it if it's disabled
     */
    public void makeSureApplicationEnabled() {
        String expectedStatus = "false";
        String attributeName = "value";
        WebElement enableApplicationSwitch = wait.waitForElementPresent(applicationSwitchLoc);
        String isEnabledText = enableApplicationSwitch.getAttribute(attributeName);
        if (expectedStatus.equals(isEnabledText)) {
            click.javascriptClick(enableApplicationSwitch);
            VertexLogger.log("Enabled Vertex Connector application");
        } else {
            VertexLogger.log("Vertex Connector Application is already enabled");
        }
    }

    /**
     * Disable vertex connector application
     * Used in some test-case to validate sales order with config settings on/off
     */
    public void disableApplication() {
        String expectedStatus = "true";
        String attributeName = "value";
        WebElement enableApplicationSwitch = wait.waitForElementPresent(applicationSwitchLoc);
        String isEnabledText = enableApplicationSwitch.getAttribute(attributeName);
        if (expectedStatus.equals(isEnabledText)) {
            click.javascriptClick(enableApplicationSwitch);
            VertexLogger.log("Disabled Vertex Connector application");
        } else {
            VertexLogger.log("Vertex Connector Application is already enabled");
        }
    }

    /**
     * Get O-Series application's version from Kibo.
     *
     * @return connector's configured version with Kibo.
     */
    public String getApplicationVersion() {
        waitForPageLoad();
        WebElement version = wait.waitForElementPresent(appVersion);
        return text.getElementText(version);
    }

    /**
     * a getter method to locate the configure application button
     *
     * @return configure application Button WebElement
     */
    protected WebElement getConfigureApplicationButton() {
        WebElement configureApplicationButton = wait.waitForElementPresent(configApplicationButtonLoc);

        return configureApplicationButton;
    }

    /**
     * uses the getter method to locate the configButton and then clicks on it
     */
    public void clickConfigureApplicationButton() {
        WebElement configureApplicationButton = getConfigureApplicationButton();
        List<WebElement> masks = driver.findElements(loadMaskLoc);
        for (WebElement mask : masks) {
            try {
                wait.waitForElementNotDisplayed(mask);
            } catch (Exception e) {
                VertexLogger.log("Load Mask isn't present");
            }
        }
        configureApplicationButton.click();
        waitForPageLoad();
        configurationDialog.switchToApplicationFrame();
        wait.waitForAllElementsPresent(connectorAllSettingMenu);
    }

    /**
     * locates the main menu button WebElement
     *
     * @return main menu WebElement
     */
    protected WebElement getMainMenuButton() {
        WebElement mainMenu = wait.waitForElementPresent(mainMenuButtonLoc);
        wait.waitForElementNotDisplayed(mainMenuScreenBlockerLoc, DEFAULT_TIMEOUT);
        wait.waitForElementEnabled(mainMenuButtonLoc);
        return mainMenu;
    }

    /**
     * locates the main menu button WebElement
     *
     * @return main menu WebElement
     */
    protected WebElement getMainMenusButton() {
        WebElement mainMenu = wait.waitForElementPresent(mainMenuButtonLoc);
        wait.waitForElementNotDisplayed(mainMenuScreenBlockerLoc, DEFAULT_TIMEOUT);
        wait.waitForElementEnabled(mainMenuButton);
        return mainMenu;
    }

    /**
     * uses getter method to locate the main menu button and then clicks it
     *
     * @return new instance if the Main Menu navigation panel
     */
    public KiboMainMenuNavPanel clickMainMenus() {
        WebElement mainMenu = getMainMenusButton();
        wait.waitForElementEnabled(mainMenu);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", mainMenu);
        waitForPageLoad();
        return new KiboMainMenuNavPanel(driver, this);
    }

    /**
     * uses getter method to locate the main menu button and then clicks it
     *
     * @return new instance if the Main Menu navigation panel
     */
    public KiboMainMenuNavPanel clickMainMenu() {
        WebElement mainMenu = getMainMenuButton();
        wait.waitForElementEnabled(mainMenu);
        mainMenu.click();

        waitForPageLoad();
        return new KiboMainMenuNavPanel(driver, this);
    }
}
