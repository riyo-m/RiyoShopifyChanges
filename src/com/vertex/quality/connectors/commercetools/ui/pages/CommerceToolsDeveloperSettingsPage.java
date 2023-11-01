package com.vertex.quality.connectors.commercetools.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * a generic representation of a CommerceTools Developer setting.
 *
 * @author vivek-kumar
 */
public class CommerceToolsDeveloperSettingsPage extends CommerceToolsBasePage {

    public CommerceToolsDeveloperSettingsPage(WebDriver driver) {
        super(driver);
    }

    protected final By settingButton = By.xpath("//div[contains(text(),'Settings')]");
    protected final By developerSettingButton = By.xpath("//a[text()='Developer settings']");
    protected final By apiClientButton = By.xpath("//span[contains(text(),'Create new API client')]");
    protected final By apiClientName = By.xpath("//input[@class='css-1hif6gh']");
    protected final By scopeField = By.xpath("//input[@id='api-client-template-selector']");
    protected final By createApiClientButton = By.xpath("//span[contains(text(),'Create API client')]");
    protected final By goBackButton = By.xpath("//button[@label='Go back']");
    protected final By className = By.xpath("//input[@class='css-7v3pxr']");

    /**
     * click on the setting present in the commercetools.
     */
    public void clickSettingIcon() {
        WebElement settingIconField = wait.waitForElementEnabled(settingButton);
        click.moveToElementAndClick(settingIconField);
    }

    /**
     * click on the Developer setting present in the commercetools.
     */
    public void clickDeveloperSettingIcon() {
        WebElement developerSettingField = wait.waitForElementEnabled(developerSettingButton);
        click.moveToElementAndClick(developerSettingField);
    }

    /**
     * click on create APi client Button in the commercetools.
     */
    public void clickApiClientButton() {
        WebElement apiClientField = wait.waitForElementEnabled(apiClientButton);
        click.moveToElementAndClick(apiClientField);
    }

    /**
     * enter Api Client Name in Commercetools.
     *
     * @param apiClient
     */
    public void enterApiClientName(final String apiClient) {
        WebElement clientNameField = wait.waitForElementDisplayed(apiClientName);
        text.enterText(clientNameField, apiClient);
    }

    /**
     * enter Scope Name in Commercetools.
     *
     * @param scope
     */
    public void enterScope(final String scope) {
        WebElement scopesField = wait.waitForElementDisplayed(scopeField);
        text.enterText(scopesField, scope);
        text.pressEnter(scopesField);
    }

    /**
     * click on create APi client Button to confirm the input in the commercetools.
     */
    public void clickCreateApiClientButton() {
        WebElement createsApiClientButton = wait.waitForElementEnabled(createApiClientButton);
        click.moveToElementAndClick(createsApiClientButton);
    }

    /**
     * click to Go Back to the Home Page in the commercetools.
     */
    public void clickGoBackButton() {
        WebElement goBacksButton = wait.waitForElementDisplayed(goBackButton);
        click.performDoubleClick(goBacksButton);
    }
}
