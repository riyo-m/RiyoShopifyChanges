package com.vertex.quality.connectors.magentoTap.storefront.pages;

import com.vertex.quality.connectors.magentoTap.common.pages.MagentoStorefrontPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Representation of the Add/Edit Address page
 *
 * @author alewis
 */
public class M2StorefrontAddOrEditAddressPage extends MagentoStorefrontPage {
    protected By phoneNumberId = By.id("telephone");
    protected By streetLineOneId = By.id("street_1");
    protected By cityId = By.id("city");
    protected By stateProvinceId = By.id("region_id");
    protected By zipCodeId = By.id("zip");
    protected By countryId = By.id("country");

    protected By saveAddressButtonClass = By.className("submit");

    /**
     * Parameterized constructor of the class
     *
     * @param driver Object of WebDriver.
     */
    public M2StorefrontAddOrEditAddressPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Enter phone number
     *
     * @param phoneNumber phone number
     */
    public void inputPhoneNumber(String phoneNumber) {
        WebElement field = wait.waitForElementEnabled(phoneNumberId);

        field.sendKeys(phoneNumber);
    }

    /**
     * Enter street line
     *
     * @param street street line
     */
    public void inputStreetLine(String street) {
        WebElement field = wait.waitForElementEnabled(streetLineOneId);

        field.sendKeys(street);
    }

    /**
     * Enter city
     *
     * @param city City
     */
    public void inputCity(String city) {
        WebElement field = wait.waitForElementEnabled(cityId);

        field.sendKeys(city);
    }

    /**
     * Select state
     *
     * @param selectString state
     */
    public void selectState(String selectString) {
        WebElement field = wait.waitForElementEnabled(stateProvinceId);

        dropdown.selectDropdownByValue(field, selectString);
    }

    /**
     * Enter zip code
     *
     * @param zip zip code
     */
    public void inputZipCode(String zip) {
        WebElement field = wait.waitForElementEnabled(zipCodeId);

        field.sendKeys(zip);
    }

    /**
     * Select country
     *
     * @param selectString country
     */
    public void selectCountry(String selectString) {
        WebElement field = wait.waitForElementEnabled(countryId);

        dropdown.selectDropdownByValue(field, selectString);
    }

    /**
     * Saves the address
     *
     * @return M2StorefrontAddressBookPage.java
     */
    public M2StorefrontAddressBookPage clickSaveAddressButton() {
        WebElement button = wait.waitForElementEnabled(saveAddressButtonClass);

        click.clickElementCarefully(button);

        M2StorefrontAddressBookPage addressBookPage = initializePageObject(M2StorefrontAddressBookPage.class);

        return addressBookPage;
    }
}
