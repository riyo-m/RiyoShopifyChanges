package com.vertex.quality.connectors.salesforce.pages.ob2b;

import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * This page object handles all logic for the CC Admin Page in SF B2B Commerce
 */
public class SalesForceOB2BCCAdminPage extends SalesForceBasePage {

    By CONFIG_CACHE_ACTIVATE_BUTTON = By.xpath(".//tbody/tr/td/a[text()='LLI Enabled']/../../td/button/span[text()='Activate']");
    By CONFIG_CACHE_DEACTIVATE_BUTTON = By.xpath(".//tbody/tr/td/a[text()='LLI Enabled']/../../td/button/span[text()='Deactivate']");

    By DEFAULT_STOREFRONT_INPUT = By.xpath(".//div/label[text()='Default Storefront']/../following-sibling::div/input");
    By SAVE_BUTTON = By.xpath(".//button[contains(@onclick, 'save')]");

    public SalesForceOB2BCCAdminPage( WebDriver driver )
    {
        super(driver);
    }

    /**
     * Select global setting from list
     *
     * @param globalSetting
     */
    public void selectGlobalSetting(String globalSetting)
    {
        String path = String.format(".//ul[@*='Global Settings']/li/a[contains(@onClick, '%s')]", globalSetting);
        By listLocator = By.xpath(path);
        wait.waitForElementDisplayed(listLocator);
        click.clickElement(listLocator);
    }

    /**
     * Activates LLI Config Cache
     */
    public void activateLLIConfigCache()
    {
        wait.waitForElementDisplayed(CONFIG_CACHE_ACTIVATE_BUTTON);
        click.clickElement(CONFIG_CACHE_ACTIVATE_BUTTON);
    }

    /**
     * Deactivates LLI Config Cache
     */
    public void deactivateLLIConfigCache()
    {
        wait.waitForElementDisplayed(CONFIG_CACHE_DEACTIVATE_BUTTON);
        click.clickElement(CONFIG_CACHE_DEACTIVATE_BUTTON);
    }

    /**
     * Sets Default Storefront Value (Changes between LLI and Non-LLI) storefront
     * @param defaultStorefront
     */
    public void setDefaultStorefront(String defaultStorefront)
    {
        wait.waitForElementDisplayed(DEFAULT_STOREFRONT_INPUT);
        text.enterText(DEFAULT_STOREFRONT_INPUT, defaultStorefront);
        waitForSalesForceLoaded();
    }

    /**
     * Validate Default Storefront Value matches expected
     * @param defaultStorefront
     */
    public void validateDefaultStorefront(String defaultStorefront)
    {
        String currStorefront = wait.waitForElementDisplayed(DEFAULT_STOREFRONT_INPUT).getAttribute("value");
        // Expected storefront doesn't match actual storefront, reset the storefront value to expected
        if(!currStorefront.equals(defaultStorefront)){
            setDefaultStorefront(defaultStorefront);
            clickSaveButton();
        }
    }

    /**
     * Click Save Button
     */
    public void clickSaveButton()
    {
        wait.waitForElementDisplayed(SAVE_BUTTON);
        click.clickElement(SAVE_BUTTON);
        waitForPageLoad();
    }
}