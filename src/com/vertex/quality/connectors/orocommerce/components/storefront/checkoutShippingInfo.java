package com.vertex.quality.connectors.orocommerce.components.storefront;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.orocommerce.enums.OroAddresses;
import com.vertex.quality.connectors.orocommerce.pages.storefront.OroStoreFrontHomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author alewis
 */
public class checkoutShippingInfo extends VertexComponent {
    public checkoutShippingInfo(WebDriver driver, VertexPage parent) {

        super(driver, parent);
    }

    By selectAddressDropdown = By.xpath(".//label[text()='SELECT SHIPPING ADDRESS' or(text()='Select Shipping Address')]/following-sibling::div//a//span");
    By addressBookSearch = By.xpath(".//*[@id='select2-drop']//div[@class='select2-search']//input");
    By firstNameFieldLoc = By.xpath(".//label[text()='First name']/following-sibling::input");
    By lastNameFieldLoc = By.xpath(".//label[text()='Last name']/following-sibling::input");
    By organizationFieldLoc = By.cssSelector("input[id*='billing_address_organization']");
    By zipCodeFieldLoc = By.xpath(".//label[text()='Zip/Postal Code']/following-sibling::input");
    By cityFieldLoc = By.xpath(".//label[text()='City']/following-sibling::input");
    By streetFieldLoc = By.xpath(".//label[text()='Street']/following-sibling::input");
    By countryDropdown = By.xpath(".//label[text()='Country']/following-sibling::div//a");
    By countrySearchBox = By.xpath("(.//input[@class='select2-input'])[3]");
    By stateDropdown = By.xpath(".//label[text()='State']/following-sibling::div//a");
    By stateSearchBox = By.xpath(".//input[@aria-controls='select2-results-699']");
    By continueButtonLink = By.xpath("//button[contains(text(),'Continue')]");

    /**
     * This will select new address at shipping info page
     */
    public void selectNewAddress() {
        jsWaiter.sleep(1000);
        WebElement newAddress = wait.waitForElementPresent(selectAddressDropdown);
        click.moveToElementAndClick(newAddress);
        jsWaiter.sleep(1000);
        WebElement searchAddress = wait.waitForElementPresent(addressBookSearch);
        text.enterText(searchAddress, "New Address");
        text.pressEnter(addressBookSearch);
        waitForPageLoad();
        wait.waitForElementPresent(By.xpath(".//label[text()='First name']/following-sibling::input"));
    }

    /**
     * fill all address details for providing billing address while checkout.
     *
     * @param address billing address while checkout.
     */
    public void fillAddressFields(OroAddresses address) {
        WebElement firstNameField = wait.waitForElementDisplayed(firstNameFieldLoc);
        OroStoreFrontHomePage homepage = new OroStoreFrontHomePage(driver);
        homepage.enterJavascriptText("FirstNameTester", firstNameField);
        text.enterText(lastNameFieldLoc, "LastNameTester");
        text.enterText(streetFieldLoc, address.getLine1());
        text.enterText(cityFieldLoc, address.getCity());
        selectCountry(address);
        selectState(address);
        text.enterText(zipCodeFieldLoc, address.getZip());
    }

    /**
     * select billing country while checkout.
     *
     * @param address billing country address while checkout.
     */
    public void selectCountry(OroAddresses address) {
        WebElement country = wait.waitForElementDisplayed(countryDropdown);
        click.clickElement(country);
        WebElement searchCountry = wait.waitForElementPresent(By.xpath("/html/body/div[10]/div/input"));
        text.enterText(searchCountry, address.getCountry());
        wait.waitForElementPresent(By.cssSelector("ul[class='select2-results']"));
        text.pressEnter(searchCountry);
    }

    /**
     * select billing state while checkout.
     *
     * @param address billing state address while checkout.
     */
    public void selectState(OroAddresses address) {
        WebElement state = wait.waitForElementDisplayed(stateDropdown);
        click.clickElement(state);
        WebElement searchState = wait.waitForElementPresent(By.xpath("/html/body/div[11]/div/input"));
        text.enterText(searchState, address.getState());
        wait.waitForElementPresent(By.cssSelector("ul[class='select2-results']"));
        text.pressEnter(searchState);
    }

    /**
     * clicks on continue button after entering billing address while checkout.
     */
    public void clickContinue() {
        WebElement continueButton = wait.waitForElementEnabled(continueButtonLink);
        click.clickElement(continueButton);
        this.waitForPageLoad();
    }
}
