package com.vertex.quality.connectors.magentoTap.admin.pages;

import com.vertex.quality.connectors.magentoTap.common.pages.MagentoAdminPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

/**
 * Shipping setting page
 *
 * @author Shivam.Soni
 */
public class M2AdminShippingSettingsPage extends MagentoAdminPage {

    protected By expandedOriginSection = By.xpath(".//div[@class='section-config active']//a[@id='shipping_origin-head']");
    protected By originSection = By.id("shipping_origin-head");
    protected By countryUseSystemValueCheckbox = By.id("shipping_origin_country_id_inherit");
    protected By stateUseSystemValueCheckbox = By.id("shipping_origin_region_id_inherit");
    protected By postalUseSystemValueCheckbox = By.id("shipping_origin_postcode_inherit");
    protected By country = By.xpath(".//select[@id='shipping_origin_country_id']");
    protected By regionStateDropdown = By.xpath(".//select[@id='shipping_origin_region_id']");
    protected By regionStateBox = By.xpath(".//input[@id='shipping_origin_region_id']");
    protected By postalCode = By.id("shipping_origin_postcode");
    protected By city = By.id("shipping_origin_city");
    protected By addressLine1 = By.id("shipping_origin_street_line1");

    /**
     * Parameterized constructor of the class
     *
     * @param driver Object of WebDriver
     */
    public M2AdminShippingSettingsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Expands origin section settings if it is collapsed
     */
    public void expandOriginSettings() {
        waitForSpinnerToBeDisappeared();
        WebElement origin = wait.waitForElementPresent(originSection);
        if (!element.isElementDisplayed(expandedOriginSection)) {
            click.moveToElementAndClick(origin);
        }
        waitForSpinnerToBeDisappeared();
    }

    /**
     * Select or De-select country's Use System Value checkbox
     *
     * @param select true to select & false to de-select.
     */
    public void selectDeselectCountryDefaultValue(boolean select) {
        waitForSpinnerToBeDisappeared();
        WebElement countryCheckbox = wait.waitForElementPresent(countryUseSystemValueCheckbox);
        if (select && !checkbox.isCheckboxChecked(countryCheckbox)) {
            click.moveToElementAndClick(countryCheckbox);
        } else if (!select && checkbox.isCheckboxChecked(countryCheckbox)) {
            click.moveToElementAndClick(countryCheckbox);
        }
        waitForSpinnerToBeDisappeared();
    }

    /**
     * Select or De-select state's Use System Value checkbox
     *
     * @param select true to select & false to de-select.
     */
    public void selectDeselectStateDefaultValue(boolean select) {
        waitForSpinnerToBeDisappeared();
        WebElement stateCheckbox = wait.waitForElementPresent(stateUseSystemValueCheckbox);
        if (select && !checkbox.isCheckboxChecked(stateCheckbox)) {
            click.moveToElementAndClick(stateCheckbox);
        } else if (!select && checkbox.isCheckboxChecked(stateCheckbox)) {
            click.moveToElementAndClick(stateCheckbox);
        }
        waitForSpinnerToBeDisappeared();
    }

    /**
     * Select or De-select postal's Use System Value checkbox
     *
     * @param select true to select & false to de-select.
     */
    public void selectDeselectPostalDefaultValue(boolean select) {
        waitForSpinnerToBeDisappeared();
        WebElement postalCheckbox = wait.waitForElementPresent(postalUseSystemValueCheckbox);
        if (select && !checkbox.isCheckboxChecked(postalCheckbox)) {
            click.moveToElementAndClick(postalCheckbox);
        } else if (!select && checkbox.isCheckboxChecked(postalCheckbox)) {
            click.moveToElementAndClick(postalCheckbox);
        }
        waitForSpinnerToBeDisappeared();
    }

    /**
     * Sets shipping origin
     *
     * @param address address of ship from
     */
    public void setShippingOrigin(String... address) {
        if (address.length != 5) {
            Assert.fail("All parameters are mandatory, Parameters must be in the sequence. Please check JavaDoc for more details");
        }
        waitForSpinnerToBeDisappeared();
        expandOriginSettings();
        selectDeselectCountryDefaultValue(false);
        selectDeselectStateDefaultValue(false);
        selectDeselectPostalDefaultValue(false);
        dropdown.selectDropdownByDisplayName(wait.waitForElementPresent(country), address[0]);
        waitForSpinnerToBeDisappeared();
        if (element.isElementPresent(regionStateDropdown)) {
            waitForSpinnerToBeDisappeared();
            dropdown.selectDropdownByDisplayName(wait.waitForElementPresent(regionStateDropdown), address[1]);
        } else {
            text.enterText(wait.waitForElementPresent(regionStateBox), address[1]);
        }
        waitForSpinnerToBeDisappeared();
        text.enterText(wait.waitForElementPresent(postalCode), address[2]);
        text.enterText(wait.waitForElementPresent(city), address[3]);
        text.enterText(wait.waitForElementPresent(addressLine1), address[4]);
    }
}
