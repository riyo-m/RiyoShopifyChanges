package com.vertex.quality.connectors.magentoTap.admin.pages;

import com.vertex.quality.connectors.magentoTap.common.pages.MagentoAdminPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

/**
 * Representation of the New Tax Rule page
 *
 * @author alewis
 */
public class M2AdminNewTaxRulePage extends MagentoAdminPage {
    protected By additionalSettingsId = By.id("details-summarybase_fieldset");

    protected By customerTaxClassSectionLoc = By.cssSelector(
            "div[data-ui-id='tax-rate-form-fieldset-element-form-field-tax-customer-class']");
    protected By addButtonsClass = By.className("action-add");
    protected By newTaxInputFieldClass = By.className("mselect-input");
    protected By fieldTaxProductClass = By.className("field-tax_product_class");
    protected By newTaxAddButtonClass = By.className("mselect-save");
    protected By popupOkButton = By.xpath("//div[@id='modal-content-20']//..//footer//button//span");
    protected By listItemClass = By.className("mselect-list-item");
    protected By addTaxClass = By.className("mselect-button-add");
    protected By deleteButtonClass = By.className("mselect-delete");

    protected By confirmationPopupClass = By.cssSelector(
            "aside[class*='modal-popup confirm'] div[class='modal-inner-wrap']");
    protected By buttonTag = By.tagName("button");

    Actions actions = new Actions(driver);

    /**
     * Parameterized constructor of the class
     *
     * @param driver Object of WebDriver.
     */
    public M2AdminNewTaxRulePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Checks whether the additional settings section has been expanded
     *
     * @return whether the additional settings have been expanded
     */
    public boolean isAdditionalSettingsExpanded() {
        boolean isExpanded = false;
        String expandedAttribute = "aria-expanded";

        WebElement additionalSettingExpandButton = wait.waitForElementEnabled(additionalSettingsId);
        String expanded = additionalSettingExpandButton.getAttribute(expandedAttribute);

        if (expanded.equals("true")) {
            isExpanded = true;
        }

        return isExpanded;
    }

    /**
     * Clicks on Additional Settings, expandnig or contracting the
     * additional settings section
     */
    public void clickAdditionalSettings() {
        WebElement additionalSettingExpandButton = wait.waitForElementEnabled(additionalSettingsId);
        click.clickElement(additionalSettingExpandButton);
    }

    /**
     * Under additional settings, click the button to add a new tax class
     * for customers
     */
    public void clickAddNewCustomerTaxClass() {
        WebElement customerTaxClassSection = wait.waitForElementDisplayed(customerTaxClassSectionLoc);
        WebElement addCustomerTaxClassButton = wait.waitForElementEnabled(addButtonsClass, customerTaxClassSection);
        click.clickElement(addCustomerTaxClassButton);
    }

    /**
     * Input a tax class name when adding a new tax class for customers
     *
     * @param inputString tax class name
     */
    public void inputTaxClassName(String inputString) {
        WebElement customerTaxClassSection = wait.waitForElementDisplayed(customerTaxClassSectionLoc);
        WebElement field = wait.waitForElementEnabled(newTaxInputFieldClass, customerTaxClassSection);
        field.sendKeys(inputString);
    }

    /**
     * After naming the customer tax class, click the check mark
     * on the input field to confirm the addition
     */
    public void confirmNewTaxClass() {
        WebElement customerTaxClassSection = wait.waitForElementDisplayed(customerTaxClassSectionLoc);
        WebElement addTaxClassButton = wait.waitForElementEnabled(newTaxAddButtonClass, customerTaxClassSection);
        click.clickElement(addTaxClassButton);
    }

    /**
     * Delete a created customer tax class
     *
     * @param itemName name of the tax class to delete
     */
    public void deleteCustomerTaxClass(String itemName) {
        WebElement customerTaxClassSection = wait.waitForElementDisplayed(customerTaxClassSectionLoc);
        List<WebElement> itemList = wait.waitForAllElementsDisplayed(listItemClass, customerTaxClassSection);

        WebElement item = element.selectElementByText(itemList, itemName);

        actions
                .moveToElement(item)
                .perform();
        WebElement deleteButton = wait.waitForElementEnabled(deleteButtonClass, item);
        actions
                .click(deleteButton)
                .perform();

        WebElement popup = wait.waitForElementPresent(confirmationPopupClass);

        List<WebElement> buttonList = wait.waitForAllElementsEnabled(buttonTag, popup);

        WebElement okButton = element.selectElementByText(buttonList, "OK");

        click.clickElement(okButton);
    }

    public void clickAddNewTaxClass(String taxClass) {
        WebElement fieldTaxProduct = wait.waitForElementPresent(fieldTaxProductClass);
        WebElement addTaxRule = wait.waitForElementDisplayed(addTaxClass, fieldTaxProduct);

        click.clickElementCarefully(addTaxRule);

        WebElement input = wait.waitForElementDisplayed(newTaxInputFieldClass, fieldTaxProduct);

        text.enterText(input, taxClass);

        click.clickElementCarefully(newTaxAddButtonClass, fieldTaxProduct);

        WebElement popup = wait.waitForElementPresent(popupOkButton);

        click.clickElement(popup);
    }
}
