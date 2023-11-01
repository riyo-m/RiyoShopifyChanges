package com.vertex.quality.connectors.shopify.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * Shopify admin panel -> Settings -> Location page - contains all helper methods & variables
 *
 * @author Shivam.Soni
 */
public class ShopifyAdminSettingsLocationPage extends ShopifyPage {

    protected By locationsHeader = By.xpath(".//h1[text()='Locations']");
    protected By defaultAddress = By.xpath(".//span[text()='Default']");
    protected By countryDropdown = By.xpath(".//select[@name='country']");
    protected By addressLine1Box = By.xpath(".//input[@name='address1']");
    protected By cityBox = By.xpath(".//input[@name='city']");
    protected By stateProvinceDropdown = By.xpath(".//select[@name='province']");
    protected By zipBox = By.xpath(".//input[@name='zip']");
    protected By saveButton = By.xpath("(.//button[normalize-space(.)='Save'])[last()]");

    /**
     * Parameterized constructor of the class that helps to initialize the object & to access the parents.
     *
     * @param driver Object of WebDriver
     */
    public ShopifyAdminSettingsLocationPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Clicks on Default address
     */
    public void clickOnDefaultAddress() {
        waitForPageLoad();
        click.clickElement(defaultAddress);
        waitForPageLoad();
    }

    /**
     * Changes/ updates the default set address - used in different Ship From tests
     * Sequence to be followed: Country, Address Line 1, City, Stage, Zip
     * On the UI, we have only 5 fields for address so if more than 5 params passed then it will cause failures
     * Ex: modifyDefaultAddress("United States", "1270 York Rd", "Gettysburg", "Pennsylvania", "17325");
     *
     * @param address value of address
     */
    public void modifyDefaultAddress(String... address) {
        if (address.length != 5) {
            Assert.fail("Wrong parameters...Kindly refer JavaDoc");
        }
        waitForPageLoad();
        click.clickElement(defaultAddress);
        dropdown.selectDropdownByDisplayName(wait.waitForElementPresent(countryDropdown), address[0]);
        text.selectAllAndInputText(wait.waitForElementPresent(addressLine1Box), address[1]);
        text.selectAllAndInputText(wait.waitForElementPresent(cityBox), address[2]);
        dropdown.selectDropdownByDisplayName(wait.waitForElementPresent(stateProvinceDropdown), address[3]);
        text.selectAllAndInputText(wait.waitForElementPresent(zipBox), address[4]);
        waitForPageLoad();
    }

    /**
     * Saves the form
     */
    public void saveTheForm() {
        waitForPageLoad();
        click.clickElement(saveButton);
        waitForPageLoad();
    }
}
