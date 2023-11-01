package com.vertex.quality.connectors.dynamics365.business.pages.accountpayable;

import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessSalesBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class BusinessVendorPage extends BusinessSalesBasePage {
    protected By vendorName = By.xpath("//div[@controlname='Name']//input");
    protected By vendorClassField = By.xpath("//div[contains(@controlname,'VER_Vendor Class')]//input");
    protected By addressLineOneField = By.xpath("//div[contains(@controlname,'Address')]//input");
    protected By addressLineTwoFieldLoc = By.xpath("//div[contains(@controlname,'Address 2')]//input");
    protected By countryFieldLoc = By.xpath("//div[contains(@controlname,'Country/Region')]//input");
    protected By cityFieldLoc = By.xpath("//div[contains(@controlname,'City')]//input");
    protected By stateFieldLoc = By.xpath("//div[contains(@controlname,'County')]//input");
    protected By zipCodeFieldLoc = By.xpath("//div[contains(@controlname,'Post Code')]//input");
    protected By categoryLoc = By.className("caption-text");
    protected By taxAreaFieldLoc = By.xpath("//div[contains(@controlname,'Tax Area Code')]//input");
    protected By readOnlyModeIcon = By.cssSelector("button[title='Open the page in read-only mode.']");
    protected By zipCode = By.xpath("//div[@controlname='Post Code']//span");

    public BusinessVendorPage(WebDriver driver) {
        super(driver);
    }

    /**
     * enters the Vendor name
     *
     * @param name
     */

    public void enterVendorNameOnVendorPage(String name) {
        WebElement vendorNameLoc = wait.waitForElementDisplayed(vendorName);
        text.enterText(vendorNameLoc, name);
    }

    /**
     * enters the vertex Vendor class
     *
     * @param classCode
     */
    public void enterVertexVendorClass(String classCode) {
        WebElement vendorClass = wait.waitForElementDisplayed(vendorClassField);
        text.enterText(vendorClass, classCode);
        text.pressEnter(vendorClass);
    }

    /**
     * enters the first line of the street address in Address & Contact category
     *
     * @param address
     */
    public void enterAddressLineOne(String address) {
        WebElement addressField1 = wait.waitForElementDisplayed(addressLineOneField);
        click.clickElementCarefully(addressField1);
        text.enterText(addressField1, address);
    }

    /**
     * enters the second line of the street address in Address & Contact category
     *
     * @param address2
     */
    public void enterAddressLineTwo(String address2) {
        WebElement field = wait.waitForElementDisplayed(addressLineTwoFieldLoc);
        text.enterText(field, address2);
    }

    /**
     * enters the country in Address & Contact category
     *
     * @param country
     */
    public void enterCountry(String country) {
        WebElement countryField = wait.waitForElementDisplayed(countryFieldLoc);
        text.enterText(countryField, country);
        text.pressEnter(countryField);
    }

    /**
     * enters the city for the address & Contact category
     *
     * @param city
     */
    public void enterCity(String city) {
        WebElement cityField = wait.waitForElementDisplayed(cityFieldLoc);
        text.enterText(cityField, city);
    }

    /**
     * enters the state for the address & Contact category
     *
     * @param state
     */
    public void enterStateInAddress(String state) {
        WebElement stateField = wait.waitForElementDisplayed(stateFieldLoc);
        text.enterText(stateField, state);
    }

    /**
     * enters the zip code for the address & Contact category
     *
     * @param zipCode
     */
    public void enterZip(String zipCode) {
        WebElement field = wait.waitForElementDisplayed(zipCodeFieldLoc);
        text.enterText(field, zipCode);
    }

    /**
     * click the invoicing section toggle to expand those fields
     */
    public void openInvoicingCategory() {
        List<WebElement> categoryList = wait.waitForAllElementsPresent(categoryLoc);
        WebElement invoicing = element.selectElementByText(categoryList, "Invoicing");
        click.clickElement(invoicing);
    }

    /**
     * enters the tax area code for the address & Contact category
     */
    public void enterTaxAreaCode(String taxAreaCode) {
        WebElement taxAreaLoc = wait.waitForElementDisplayed(taxAreaFieldLoc);
        text.enterText(taxAreaLoc, taxAreaCode);
        text.pressTab(taxAreaLoc);
    }

    /**
     * opens page in read only mode by clicking on icon
     */
    public void openReadOnlyMode() {
        WebElement button = wait.waitForElementPresent(readOnlyModeIcon);
        click.clickElement(button);
        wait.waitForElementNotPresent(readOnlyModeIcon);
    }

    /**
     * gets the zip code from field
     *
     * @return zip
     */
    public String geZipCode() {
        WebElement field = wait.waitForElementDisplayed(zipCode);
        String zip = field.getText();
        return zip;
    }
}
