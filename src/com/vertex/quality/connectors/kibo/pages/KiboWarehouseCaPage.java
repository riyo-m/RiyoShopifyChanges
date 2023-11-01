package com.vertex.quality.connectors.kibo.pages;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.kibo.components.KiboMainMenuNavPanel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

/**
 * this class represents the WarehouseCA page*
 *
 * @author osabha
 */
public class KiboWarehouseCaPage extends VertexPage {
    public KiboMainMenuNavPanel navPanel;

    public KiboWarehouseCaPage(WebDriver driver) {
        super(driver);
        navPanel = new KiboMainMenuNavPanel(driver, this);
    }

    protected By addressBoxConLoc = By.className("mozu-c-minor-headline");
    protected By addressBoxLoc = By.className("mozu-c-container--contact-form-address");
    protected By validateAddressButtonConLoc = By.className("mozu-c-modal__toolbar");
    protected By validateAddressButtonLoc = By.tagName("button");
    protected By cityFieldLoc = By.cssSelector("input[name='cityOrTown']");
    protected By stateFieldLoc = By.cssSelector("input[name='stateOrProvince']");
    protected By zipCodeFieldLoc = By.cssSelector("input[name='postalOrZipCode']");
    protected By streetAddressLoc = By.cssSelector("input[name='address1']");
    protected By warehouseAddressLoc = By.xpath(".//label[normalize-space(.)='Address']/following-sibling::div");
    protected By countryDropdownLoc = By.xpath(".//label[normalize-space(.)='Country']/preceding-sibling::div[2]");
    protected By countryDropDownPanel = By.xpath(".//div[@class='mozu-c-selector mozu-c-selector--dropdown mozu-c-selector--flyout']");
    protected By countryDropdownAllValues = By.xpath(".//div[@class='mozu-c-selector mozu-c-selector--dropdown mozu-c-selector--flyout']//a");
    protected String countryValueLoc = ".//a[text()='<<text_replace>>']";
    protected By saveButtonLoc = By.className("mozu-c-btn__cross--primary");
    protected By confirmButtonLoc = By.className("mozu-c-btn__content--primary");
    protected By confirmButton = By.xpath(".//button[normalize-space(.)='Confirm']");
    protected By cancelButton = By.xpath("(.//button[normalize-space(.)='Cancel'])[2]");
    protected By mainMenuButtonLoc = By.className("mozu-c-nav__hamburger");
    protected By loadMaskLoc = By.className("mozu-c-modal__screen--medium");
    protected By addressFieldLoc = By.cssSelector(
            ".mozu-c-container.mozu-c-displayfield.mozu-is-required.mozu-qa-address");
    protected By validateAddress = By.xpath(".//button[normalize-space(.)='Validate Address']");
    String streetAddress = "1 World way";
    String defaultStreetAddress = "1835 Kramer Lane #100";
    String city = "Los Angeles";
    String defaultCity = "Austin";
    String state = "CA";
    String defaultState = "TX";
    String zip = "90045";

    /**
     * gets the address field for the shipping warehouse
     *
     * @return warehouse address field WebElement
     */
    protected WebElement getAddressField() {
        WebElement addressField = wait.waitForElementPresent(addressFieldLoc);

        return addressField;
    }

    /**
     * uses the getter  method to locate the warehouse  address field and then clicks on it to open
     * up the dialog to edit the address
     */
    public void clickAddressField() {
        WebElement addressField = getAddressField();
        addressField.click();
    }

    /**
     * getter method to locate the WebElement of the street address field
     *
     * @return street address field WebElement
     */
    protected WebElement getStreetAddressField() {
        WebElement streetAddressField = wait.waitForElementPresent(streetAddressLoc);

        return streetAddressField;
    }

    /**
     * uses the getter method to locate the street address field and then types in the street
     * address
     */
    public void enterStreetAddress() {
        WebElement streetAddressField = getStreetAddressField();

        streetAddressField.clear();
        streetAddressField.sendKeys(streetAddress);
    }

    /**
     * uses the getter method to locate the street address field and then types in the street address
     *
     * @param address pass street address
     */
    public void enterStreetAddress(String address) {
        WebElement streetAddressField = getStreetAddressField();

        streetAddressField.clear();
        streetAddressField.sendKeys(address);
    }

    /**
     * uses the getter method to locate the street address field and then
     * enters the defaulted street address for the warehouse after the test case has run
     */
    public void streetAddressPostCleanup() {
        WebElement streetAddressField = getStreetAddressField();
        text.enterText(streetAddressField, defaultStreetAddress);
    }

    /**
     * getter method to locate the city field
     *
     * @return city field WebElement
     */
    protected WebElement getCityField() {
        WebElement cityField = wait.waitForElementPresent(cityFieldLoc);

        return cityField;
    }

    /**
     * uses the getter method to locate the city field and then types in the city
     */
    public void enterCity() {
        WebElement cityField = getCityField();
        text.enterText(cityField, city);
    }

    /**
     * uses the getter method to locate the city field and then types in the city
     *
     * @param city pass city value
     */
    public void enterCity(String city) {
        WebElement cityField = getCityField();
        text.enterText(cityField, city);
    }

    /**
     * uses the getter method to locate the city field and then clears the field and then types in
     * the default warehouse address
     * this is a post clean up to restore the default address after the test case has run
     */
    public void cityPostCleanup() {
        WebElement cityField = getCityField();
        text.enterText(cityField, defaultCity);
    }

    /**
     * getter method to locate the state field
     *
     * @return stat field WebElement
     */
    protected WebElement getStateField() {
        WebElement stateField = wait.waitForElementPresent(stateFieldLoc);

        return stateField;
    }

    /**
     * uses the getter method to locate the state field and then types in the state
     */
    public void enterState() {
        WebElement stateField = getStateField();
        text.enterText(stateField, state);
    }

    /**
     * uses the getter method to locate the state field and then types in the state
     *
     * @param state pass state value
     */
    public void enterState(String state) {
        WebElement stateField = getStateField();
        text.enterText(stateField, state);
    }

    /**
     * locates the main menu button WebElement
     *
     * @return main menu WebElement
     */
    protected WebElement getMainMenuButton() {
        WebElement mainMenu = wait.waitForElementPresent(mainMenuButtonLoc);

        return mainMenu;
    }

    /**
     * uses getter method to locate the main menu button and then clicks it
     */
    public void clickMainMenu() {
        WebElement mainMenu = getMainMenuButton();
        mainMenu.click();
        waitForPageLoad();
    }

    /**
     * uses the getter method to locate the state field and then clears it and types in state
     * this is a post cleanup method to change back the address to the default after the test case
     * run
     */
    public void statePostCleanup() {
        WebElement stateField = getStateField();
        text.enterText(stateField, defaultState);
    }

    /**
     * gets the zip code field WebElement
     *
     * @return zip code field WebElement
     */
    protected WebElement getZipField() {
        WebElement zipField = wait.waitForElementPresent(zipCodeFieldLoc);

        return zipField;
    }

    /**
     * uses the getter method to locate the zip code field and then types in the zip code
     */
    public void enterZip() {
        WebElement zipField = getZipField();
        text.enterText(zipField, zip);
    }

    /**
     * uses the getter method to locate the zip code field and then types in the zip code
     *
     * @param zip pass zip code
     */
    public void enterZip(String zip) {
        WebElement zipField = getZipField();
        text.enterText(zipField, zip);
    }

    /**
     * uses the getter method to locate the country field and then types in the country
     *
     * @param country pass country
     */
    public void enterCountry(String country) {
        WebElement countryField = wait.waitForElementPresent(countryDropdownLoc);
        click.moveToElementAndClick(countryField);
        WebElement panel = wait.waitForElementDisplayed(countryDropDownPanel);
        WebElement countryValue = wait.waitForElementPresent(By.xpath(countryValueLoc.replace("<<text_replace>>", country)));
        List<WebElement> countries = element.getWebElements(countryDropdownAllValues);
        for (int i = 0; i < countries.size(); i++) {
            if (text.getElementText(countryField).equalsIgnoreCase(country)) {
                break;
            }
            if (!panel.isDisplayed()) {
                click.moveToElementAndClick(countryField);
            }
            scroll.scrollElementIntoView(countryValue);
            wait.waitForTextInElement(countryValue, country);
            click.moveToElementAndClick(wait.waitForElementPresent(By.xpath(countryValueLoc.replace("<<text_replace>>", country))));
        }
        wait.waitForTextInElement(countryDropdownLoc, country);
    }

    /**
     * Clicks on confirm button
     */
    public void clickOnConfirmButton() {
        wait.waitForElementPresent(confirmButton);
        click.moveToElementAndClick(confirmButton);
        waitForPageLoad();
    }

    /**
     * Clicks on cancel button
     */
    public void clickOnCancelButton() {
        wait.waitForElementPresent(cancelButton);
        click.moveToElementAndClick(cancelButton);
        waitForPageLoad();
    }

    /**
     * getter method to locate the confirm button
     *
     * @return confirm button WebElement
     */
    protected WebElement getConfirmButton() {
        WebElement confirmButton = null;
        String expectedText = "Confirm";
        List<WebElement> confirmButtonContainers = wait.waitForAllElementsPresent(confirmButtonLoc);
        confirmButton = element.selectElementByText(confirmButtonContainers, expectedText);

        return confirmButton;
    }

    /**
     * uses the getter method to locate the confirm button and then clicks on it
     */
    public void clickConfirmButton() {
        WebElement confirmButton = getConfirmButton();
        confirmButton.click();

        List<WebElement> masks = wait.waitForAllElementsPresent(loadMaskLoc);
        for (WebElement mask : masks) {
            try {
                wait.waitForElementNotDisplayed(mask);
            } catch (Exception e) {
                System.out.println("Load mask isn't present");
            }
        }
    }

    /**
     * getter method to locate the save button on the page
     *
     * @return save button WebElement
     */
    protected WebElement getSaveButton() {
        WebElement saveButton = wait.waitForElementPresent(saveButtonLoc);

        return saveButton;
    }

    /**
     * uses the getter method to locate save button on the page and then clicks on it
     */
    public void clickSaveButton() {
        WebElement saveButton = getSaveButton();

        saveButton.click();
    }

    /**
     * getter method to locate the validate address button from the address dialog
     *
     * @return validate address WebElement
     */
    protected WebElement getValidateAddressButton() {
        WebElement validateAddressButton = null;
        String expectedText = "Validate Address";
        WebElement validateAddressButtonContainer = wait.waitForElementPresent(validateAddressButtonConLoc);

        List<WebElement> validateAddressButtonClasses = wait.waitForAllElementsPresent(validateAddressButtonLoc,
                validateAddressButtonContainer);
        validateAddressButton = element.selectElementByText(validateAddressButtonClasses, expectedText);
        return validateAddressButton;
    }

    /**
     * uses the getter method to locate the validate address button and then clicks on it
     */
    public void clickValidateAddress() {
        WebElement validateAddressButton = getValidateAddressButton();
        validateAddressButton.click();
        wait.waitForTextInElement(validateAddress, "Validate Address");
    }

    /**
     * getter method to locate the validated address box
     *
     * @return WebElement of validated address box
     */
    protected WebElement getValidatedAddressBox() {
        String expectedText = "VALIDATED";
        WebElement validatedAddressBox = null;
        List<WebElement> addressBoxContainers = wait.waitForAllElementsPresent(addressBoxConLoc);
        WebElement container = element.selectElementByText(addressBoxContainers, expectedText);
        if (container != null) {
            WebElement parent = (WebElement) executeJs("return arguments[0].parentNode;", container);
            validatedAddressBox = wait.waitForElementPresent(addressBoxLoc, parent);
        }

        return validatedAddressBox;
    }

    /**
     * uses the getter method to locate the validated address box and then clicks on it
     */
    public void selectValidatedAddress() {
        WebElement validatedAddressBox = getValidatedAddressBox();
        validatedAddressBox.click();
    }

    /**
     * Edit ware-house's address
     * Pass in this format only: Street Address, City, State, Zip, Country
     *
     * @param whAddress pass ware-house address
     */
    public void editWareHouseAddress(String... whAddress) {
        if (whAddress.length != 5) {
            Assert.fail("Here all 5 fields are mandatory so kindly pass correct formatted data");
        }
        waitForPageLoad();
        wait.waitForElementPresent(warehouseAddressLoc);
        click.moveToElementAndClick(warehouseAddressLoc);
        enterStreetAddress(whAddress[0]);
        enterCity(whAddress[1]);
        enterState(whAddress[2]);
        enterZip(whAddress[3]);
        enterCountry(whAddress[4]);
        clickOnConfirmButton();
        clickSaveButton();
        waitForPageLoad();
    }

    /**
     * Change and validate ware-house's address
     * Pass in this format only: Street Address, City, State, Zip, Country
     *
     * @param whAddress pass ware-house address
     */
    public void changeAndValidateWarehouseAddress(String... whAddress) {
        if (whAddress.length != 5) {
            Assert.fail("Here all 5 fields are mandatory so kindly pass correct formatted data");
        }
        waitForPageLoad();
        wait.waitForElementPresent(warehouseAddressLoc);
        click.moveToElementAndClick(warehouseAddressLoc);
        enterStreetAddress(whAddress[0]);
        enterCity(whAddress[1]);
        enterState(whAddress[2]);
        enterZip(whAddress[3]);
        enterCountry(whAddress[4]);
        clickValidateAddress();
    }

    /**
     * Set ware-house's address for Ship From address
     *
     * @param whName    pass ware-house name which is to be edited
     * @param whAddress pass address which should be set
     */
    public void setWarehouseAddress(String whName, String... whAddress) {
        KiboVertexConnectorPage connectorPage = new KiboVertexConnectorPage(driver);
        navPanel = connectorPage.clickMainMenu();
        navPanel.clickMainTab();

        KiboLocationsPage locationsPage = navPanel.goToLocationsPage();
        locationsPage.selectWareHouse(whName);
        KiboWarehouseCaPage warehousePage = new KiboWarehouseCaPage(driver);
        warehousePage.editWareHouseAddress(whAddress);
    }
}



