package com.vertex.quality.connectors.magentoTap.storefront.pages;

import com.vertex.quality.connectors.magentoTap.common.enums.MagentoData;
import com.vertex.quality.connectors.magentoTap.common.pages.MagentoStorefrontPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

/**
 * Representation of the Shipping Info Page
 *
 * @author alewis
 */
public class M2StorefrontShippingInfoPage extends MagentoStorefrontPage {
    protected By shippingAddressLabel = By.xpath(".//div[text()='Shipping Address']");
    protected By newAddressButton = By.xpath(".//button[normalize-space(.)='New Address']");
    protected By emailField = By.id("customer-email");
    protected By firstNameField = By.name("firstname");
    protected By lastNameField = By.name("lastname");
    protected By streetAddressField = By.name("street[0]");
    protected By cityField = By.name("city");
    protected By countryDropdown = By.name("country_id");
    protected By stateDropdown = By.xpath(".//select[@name='region_id']");
    protected By stateBox = By.xpath(".//input[@name='region']");
    protected By postalCodeBox = By.name("postcode");
    protected By telephoneField = By.name("telephone");
    protected By saveShippingAddressCheckBox = By.xpath(".//input[@id='shipping-save-in-address-book']");
    protected By shipAddressModalShipHereButton = By.xpath(".//footer//button[normalize-space()='Ship Here']");
    protected By flatRateRadioButton = By.xpath(".//td[text()='Flat Rate']//preceding-sibling::td//input");
    protected By bestWayRadioButton = By.xpath(".//td[text()='Best Way']//preceding-sibling::td//input");

    protected By shippingAddressItems = By.className("shipping-address-items");
    protected By nextButtonContainer = By.id("shipping-method-buttons-container");
    protected By nextPrimaryClass = By.className("primary");
    protected By buttonClass = By.className("button");
    protected By shipHereClass = By.className("action-select-shipping-item");
    protected By shipUpdateAddress = By.className("update-address");

    By maskClass = By.className("loading-mask");

    protected By shipMethodRadioClass = By.className("radio");
    protected String flatRateShipping = "flatrate_flatrate";
    protected String bestWayShipping = "tablerate_bestway";
    protected String freeShipping = "freeshipping_freeshipping";

    /**
     * Parameterized constructor of the class
     *
     * @param driver Object of WebDriver
     */
    public M2StorefrontShippingInfoPage(WebDriver driver) {
        super(driver);
    }

    /**
     * enters a string into the 'email' text box
     *
     * @param email Email of the customer
     */
    public void enterEmail(String email) {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        wait.waitForElementDisplayed(emailField);
        WebElement field = wait.waitForElementPresent(emailField);
        field.clear();
        field.sendKeys(email);
    }

    /**
     * enters a string into the 'First Name' text box
     *
     * @param firstName Firstname of the customer
     */
    public void enterFirstName(String firstName) {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        wait.waitForElementDisplayed(firstNameField);
        WebElement field = driver.findElement(firstNameField);
        field.clear();
        field.sendKeys(firstName);
    }

    /**
     * enters a string into the 'Last Name' text box
     *
     * @param lastName Lastname of customer
     */
    public void enterLastName(String lastName) {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        wait.waitForElementDisplayed(lastNameField);
        WebElement field = driver.findElement(lastNameField);
        field.clear();
        field.sendKeys(lastName);
    }

    /**
     * enters a string into the 'Street Address' text box
     *
     * @param streetAddress value of street address
     */
    public void enterStreetAddress(String streetAddress) {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        wait.waitForElementDisplayed(streetAddressField);
        WebElement field = driver.findElement(streetAddressField);
        field.clear();
        field.sendKeys(streetAddress);
    }

    /**
     * Select the country
     *
     * @param country name of the country
     */
    public void selectCountry(String country) {
        waitForSpinnerToBeDisappeared();
        wait.waitForElementPresent(shippingAddressLabel);
        dropdown.selectDropdownByDisplayName(wait.waitForElementPresent(countryDropdown), country);
        waitForSpinnerToBeDisappeared();
    }

    /**
     * Select the state if Dropdown is present or Enter the value of state if the text-box is present
     *
     * @param state name of the state.
     */
    public void selectOrEnterState(String state) {
        waitForSpinnerToBeDisappeared();
        wait.waitForElementPresent(shippingAddressLabel);
        if (element.isElementDisplayed(stateDropdown)) {
            waitForSpinnerToBeDisappeared();
            dropdown.selectDropdownByDisplayName(stateDropdown, state);
            waitForSpinnerToBeDisappeared();
        } else {
            waitForSpinnerToBeDisappeared();
            text.enterText(stateBox, state);
        }
    }

    /**
     * enters a string into the 'City' text box
     *
     * @param city name of the city
     */
    public void enterCity(String city) {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        wait.waitForElementDisplayed(cityField);
        WebElement field = driver.findElement(cityField);
        field.clear();
        field.sendKeys(city);
    }

    /**
     * Enter the postal code of the customer
     *
     * @param postCode Postal Code
     */
    public void enterPostalCode(String postCode) {
        waitForSpinnerToBeDisappeared();
        wait.waitForElementPresent(shippingAddressLabel);
        text.enterText(wait.waitForElementPresent(postalCodeBox), postCode);
    }

    /**
     * enters a string into the 'Phone' text box
     *
     * @param phoneNumber Contact of the customer
     */
    public void enterPhoneNumber(String phoneNumber) {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        wait.waitForElementDisplayed(telephoneField);
        WebElement field = driver.findElement(telephoneField);
        field.clear();
        field.sendKeys(phoneNumber);
    }

    /**
     * changes the shipping address on an order
     */
    public void changeShippingAddress() {
        waitForPageLoad();
        List<WebElement> shipHeres = wait.waitForAllElementsPresent(shipHereClass);
        WebElement correctShipHere = null;

        for (WebElement shipHere : shipHeres) {
            if (shipHere.isDisplayed()) {
                correctShipHere = shipHere;
            }
        }
        scroll.scrollElementIntoView(correctShipHere);
        correctShipHere.click();

        try {
            attribute.waitForElementAttributeChange(shippingAddressItems, "innerHTML");
        } catch (Exception e) {
            e.printStackTrace();
        }
        waitForPageLoad();
    }

    /**
     * set every item to free shipping
     */
    public void setFreeShippingAllItems() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        List<WebElement> radiosList = wait.waitForAllElementsPresent(shipMethodRadioClass);

        for (WebElement radio : radiosList) {
            if (freeShipping.equals(radio.getAttribute("value"))) {
                wait.waitForElementEnabled(radio);
                click.clickElement(radio);
            }
        }
    }

    /**
     * verifies on update Button
     *
     * @return update address button
     */
    public boolean verifyUpdateAddressButton() {
        wait.waitForElementNotDisplayed(maskClass);
        waitForPageLoad();
        boolean update = element.isElementDisplayed(shipUpdateAddress);
        return update;
    }

    /**
     * Clicks on update Button
     */
    public void clickUpdateAddress() {
        wait.waitForElementNotDisplayed(maskClass);
        waitForPageLoad();
        WebElement button = wait.waitForElementDisplayed(shipUpdateAddress);
        click.clickElement(button);
    }

    /**
     * Clicks the 'Next' Button
     *
     * @return the Payment Method Page
     */
    public M2StorefrontPaymentMethodPage clickNextButton() {
        wait.waitForElementNotDisplayed(maskClass);
        waitForPageLoad();
        WebElement container = wait.waitForElementDisplayed(nextButtonContainer);
        WebElement nextButton = container.findElement(nextPrimaryClass);
        wait.waitForElementEnabled(buttonClass);
        WebElement button = wait.waitForElementEnabled(buttonClass, nextButton);
        wait.waitForElementEnabled(button, 5);
        click.clickElement(button);

        return initializePageObject(M2StorefrontPaymentMethodPage.class);
    }

    /**
     * clicks the Continue button
     *
     * @return billing information page
     */
    public M2StorefrontBillingInformationPage clickContinueButton() {
        WebElement button = wait.waitForElementEnabled(nextPrimaryClass);

        click.clickElementCarefully(button);

        M2StorefrontBillingInformationPage billingInformationPage = initializePageObject(
                M2StorefrontBillingInformationPage.class);

        return billingInformationPage;
    }

    /**
     * Set shipping address
     * Kindly maintain sequence: Address Line1, Country, State, City, Postal Code.
     * example: shippingInfoPage.enterShippingDetails("1270 York Road", "United States", "Pennsylvania", "Gettysburg", "17325");
     *
     * @param address address detail
     */
    public void enterShippingDetails(String... address) {
        if (address.length != 5) {
            Assert.fail("Parameters mismatched kindly check JavaDoc.");
        }
        waitForSpinnerToBeDisappeared();
        wait.waitForElementPresent(shippingAddressLabel);
        enterEmail(MagentoData.CUSTOMER_EMAIL.data);
        enterFirstName(MagentoData.CUSTOMER_FIRST_NAME.data);
        enterLastName(MagentoData.CUSTOMER_LAST_NAME.data);
        selectCountry(address[1]);
        enterStreetAddress(address[0]);
        selectOrEnterState(address[2]);
        enterCity(address[3]);
        enterPostalCode(address[4]);
        enterPhoneNumber(MagentoData.CUSTOMER_PHONE.data);
        waitForSpinnerToBeDisappeared();
    }

    /**
     * Set shipping address
     * Kindly maintain sequence: Address Line1, Country, State, City, Postal Code.
     * example: shippingInfoPage.enterShippingDetails("1270 York Road", "United States", "Pennsylvania", "Gettysburg", "17325");
     *
     * @param isLoggedIn  true for Customer User and false for Guest User
     * @param saveAddress true to save address in Address Book & false to not saving the address in Address Book
     * @param address     address detail
     */
    public void enterShippingDetails(boolean isLoggedIn, boolean saveAddress, String... address) {
        if (address.length != 5) {
            Assert.fail("Parameters mismatched kindly check JavaDoc.");
        }
        waitForSpinnerToBeDisappeared();
        wait.waitForElementPresent(shippingAddressLabel);
        if (isLoggedIn) {
            click.moveToElementAndClick(wait.waitForElementPresent(newAddressButton));
        }
        enterFirstName(MagentoData.CUSTOMER_FIRST_NAME.data);
        enterLastName(MagentoData.CUSTOMER_LAST_NAME.data);
        selectCountry(address[1]);
        enterStreetAddress(address[0]);
        selectOrEnterState(address[2]);
        enterCity(address[3]);
        enterPostalCode(address[4]);
        if (!isLoggedIn) {
            enterEmail(MagentoData.CUSTOMER_EMAIL.data);
        }
        enterPhoneNumber(MagentoData.CUSTOMER_PHONE.data);
        waitForSpinnerToBeDisappeared();
        if (isLoggedIn && !saveAddress && checkbox.isCheckboxChecked(saveShippingAddressCheckBox)) {
            click.moveToElementAndClick(saveShippingAddressCheckBox);
        }
        else if (!isLoggedIn && !saveAddress && checkbox.isCheckboxChecked(saveShippingAddressCheckBox)) {
            click.moveToElementAndClick(saveShippingAddressCheckBox);
        }
        click.moveToElementAndClick(wait.waitForElementPresent(shipAddressModalShipHereButton));
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
    }

    /**
     * Select the Flat Rate shipping method
     */
    public void selectFlatRateForShippingMethod() {
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        wait.waitForElementPresent(shippingAddressLabel);
        click.moveToElementAndClick(wait.waitForElementPresent(flatRateRadioButton));
    }
}