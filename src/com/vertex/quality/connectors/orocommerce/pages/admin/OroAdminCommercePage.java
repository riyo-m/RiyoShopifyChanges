package com.vertex.quality.connectors.orocommerce.pages.admin;

import com.vertex.quality.connectors.orocommerce.enums.OroAddresses;
import com.vertex.quality.connectors.orocommerce.enums.OroTestData;
import com.vertex.quality.connectors.orocommerce.pages.base.OroAdminBasePage;
import com.vertex.quality.connectors.orocommerce.pages.storefront.OroProductSearchResultsPage;
import com.vertex.quality.connectors.orocommerce.pages.storefront.OroShoppingListPage;
import com.vertex.quality.connectors.orocommerce.pages.storefront.OroStoreFrontHomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.testng.Assert.assertTrue;

/**
 * represents the vertex settings page in the system configurations page
 * contains all the methods necessary to interact with it
 *
 * @author alewis
 */
public class OroAdminCommercePage extends OroAdminBasePage {
    public OroAdminCommercePage(WebDriver driver) {
        super(driver);
    }

    By commerceButton = By.id("commerce_anchor");
    protected By shippingIcon = By.id("shipping_anchor");
    protected By shippingOrigin = By.xpath("//a[@id='shipping_origin_anchor']");
    By countryField = By.xpath("//*[contains(text(),'Country')]//following::div[2]");
    By countryInput = By.xpath("//*[contains(text(),'Country')]//following::input[11]");
    By zipCode = By.xpath("//input[@placeholder='Zip/Postal Code']");
    By cityField = By.xpath("//input[@placeholder='City']");
    By streetAddress = By.xpath("//input[@placeholder='Street Address 1']");
    By stateField = By.xpath("//div[contains(@id,'shipping_origin_value_region-uid')]");
    By stateInput = By.xpath("//div[@id='select2-drop']//input");
    By saveSetting = By.xpath("//button[contains(text(),'Save settings')]");
    By oroLoader = By.xpath(".//div[@class='loader-frame']");
    By oroBodyLoader = By.className("desktop-version lang-en loading");

    /**
     * enter street address field for shipping origin.
     *
     * @param streetAddress1 street address of ship from(origin) address
     */
    public void enterStreetAddress(String streetAddress1) {
        WebElement streetAddressField = wait.waitForElementDisplayed(streetAddress);
        text.enterText(streetAddressField, streetAddress1);
    }

    /**
     * enter state name for shipping origin.
     *
     * @param state state address of ship from(origin) address
     */
    public void enterStateAddress(String state) {
        waitForPageLoad();
        WebElement stateAddressField = wait.waitForElementEnabled(stateInput);
        text.enterText(stateAddressField, state);
        text.pressEnter(stateAddressField);
    }

    /**
     * enter country name for shipping origin.
     *
     * @param country country address of ship from(origin) address
     */
    public void enterCountry(String country) {
        waitForPageLoad();
        WebElement countryField = wait.waitForElementEnabled(countryInput);
        text.enterText(countryField, country);
        text.pressEnter(countryField);
    }

    /**
     * enter city name for shipping origin.
     *
     * @param city city address of ship from(origin) address
     */
    public void enterCity(String city) {
        WebElement cityInputField = wait.waitForElementDisplayed(cityField);
        text.enterText(cityInputField, city);
    }

    /**
     * enter zipCode for shipping origin.
     *
     * @param zipCd zipCode address of ship from(origin) address
     */
    public void enterZipCode(String zipCd) {
        WebElement zipCodeField = wait.waitForElementDisplayed(zipCode);
        text.enterText(zipCodeField, zipCd);
    }

    /**
     * clicks commerce icon on configuration page.
     */
    public void clickCommerceIcon() {
        WebElement commerceIconField = wait.waitForElementPresent(commerceButton);
        click.javascriptClick(commerceIconField);
    }

    /**
     * clicks shipping icon on commerce page.
     */
    public void clickShippingIcon() {
        WebElement shippingIconField = wait.waitForElementPresent(shippingIcon);
        click.javascriptClick(shippingIconField);
    }

    /**
     * clicks shipping icon on commerce page.
     */
    public void clickShippingOrigin() {
        WebElement shippingOriginField = wait.waitForElementPresent(shippingOrigin);
        click.javascriptClick(shippingOriginField);
        waitForPageLoad();
    }

    /**
     * clicks country field under shipping origin.
     */
    public void clickCountryField() {
        waitForPageLoad();
        wait.waitForElementPresent(oroLoader);
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(oroBodyLoader));
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.elementToBeClickable(countryField));
        WebElement countryFieldIcon = wait.waitForElementEnabled(countryField);
        click.moveToElementAndClick(countryFieldIcon);
    }

    /**
     * clicks state field under shipping origin.
     */
    public void clickStateField() {
        waitForPageLoad();
        WebElement stateFieldIcon = wait.waitForElementPresent(stateField);
        click.moveToElementAndClick(stateFieldIcon);
    }

    /**
     * clicks save settings Button for saving shipping origin address.
     */
    public void saveShippingOriginAddress() {
        WebElement saveSettingButton = wait.waitForElementPresent(saveSetting);
        click.moveToElementAndClick(saveSettingButton);
    }

    /**
     * enter shipping address for shipping origin.
     *
     * @param address address of ship from(origin) address
     */
    public void enterShippingAddress(OroAddresses address) {
        waitForPageLoad();
        clickCommerceIcon();
        clickShippingIcon();
        clickShippingOrigin();
        clickCountryField();
        enterCountry(address.getCountry());
        clickStateField();
        enterStateAddress(address.getState());
        enterZipCode(address.getZip());
        enterCity(address.getCity());
        enterStreetAddress(address.getLine1());
        saveShippingOriginAddress();
        waitForPageLoad();
    }

    /**
     * enter shipping address for shipping origin.
     *
     * @param address address of ship from(origin) address
     */
    public void setShipFromAddress(OroAddresses address) {
        driver.get(OroTestData.ADMIN_URL.data);
        OroAdminLoginPage loginPage = new OroAdminLoginPage(driver);
        OroAdminHomePage adminHomePage = loginPage.loginAsUser(OroTestData.ADMIN_USERNAME.data,
                OroTestData.ADMIN_PASSWORD.data);
        assertTrue(adminHomePage.checkErrorMessageInAdminHomePage());
        adminHomePage.mainMenu.goToSystemTab();
        OroSystemConfigurationsPage configPage = adminHomePage.mainMenu.system.goToConfigurationsPage();
        OroAdminCommercePage commercePage = new OroAdminCommercePage(driver);
        commercePage.enterShippingAddress(address);
    }

    /**
     * Set ship from origin
     * This method is designed specifically for Oro version update user can leverage the same method for Oro 4.2 version or earlier version
     *
     * @param environmentURL enter oro admin url
     * @param address        address of shipping origin
     */
    public void setShippingOrigin(String environmentURL, OroAddresses address) {
        driver.get(environmentURL);
        OroAdminLoginPage loginPage = new OroAdminLoginPage(driver);
        OroAdminHomePage adminHomePage = loginPage.loginAsUser(OroTestData.ADMIN_USERNAME.data,
                OroTestData.ADMIN_PASSWORD.data);
        assertTrue(adminHomePage.checkErrorMessageInAdminHomePage());
        adminHomePage.mainMenu.goToSystemTab();
        OroSystemConfigurationsPage configPage = adminHomePage.mainMenu.system.goToConfigurationsPage();
        OroAdminCommercePage commercePage = new OroAdminCommercePage(driver);
        commercePage.enterShippingAddress(address);
    }

    /**
     * Set warehouse address same as Shipping Origin
     */
    public void useShippingOriginForWarehouse() {
        OroAdminHomePage adminHomePage = new OroAdminHomePage(driver);
        adminHomePage.mainMenu.gotoWarehousePage();
        OroAdminWarehousesPage warehouse = new OroAdminWarehousesPage(driver);
        warehouse.editActiveWarehouseAddress();
        warehouse.selectOrDeselectUseShippingOrigin(true);
        warehouse.clickSaveAndClose();
    }

    /**
     * tests whether customer can add product and add address without sign in and products.
     *
     * @param qty1     quantity for first product
     * @param qty2     quantity for second product
     * @param qty3     quantity for third product
     * @param qty4     quantity for fourth product
     * @param product1 first product to be added to the cart.
     * @param product2 second product to be added to the cart.
     * @param product3 third product to be added to the cart.
     * @param product4 fourth product to be added to the cart.
     */
    public void addMultipleProductDetail(String qty1, String qty2, String qty3, String qty4, String product1,
                                         String product2, String product3, String product4) {
        OroStoreFrontHomePage homePage1 = new OroStoreFrontHomePage(driver);

        if (!qty1.equals("0")) {
            OroProductSearchResultsPage searchResultsPage = homePage1.headerMiddleBar.searchForProduct(
                    product1);
            searchResultsPage.selectProductFromList(product1);
            OroShoppingListPage shoppingListPage = searchResultsPage.goToShoppingListPage();
            searchResultsPage.enterProductQuantity(qty1, "1");
        }
        if (!qty2.equals("0")) {
            OroProductSearchResultsPage searchResultsPage = homePage1.headerMiddleBar.searchForProduct(
                    product2);
            searchResultsPage.selectProductFromList(product2);
            OroShoppingListPage shoppingListPage = searchResultsPage.goToShoppingListPage();
            searchResultsPage.enterProductQuantity(qty2, "2");
        }
        if (!qty3.equals("0")) {
            OroProductSearchResultsPage searchResultsPage = homePage1.headerMiddleBar.searchForProduct(
                    product3);
            searchResultsPage.selectProductFromList(product3);
            OroShoppingListPage shoppingListPage = searchResultsPage.goToShoppingListPage();
            searchResultsPage.enterProductQuantity(qty3, "3");
        }
        if (!qty4.equals("0")) {
            OroProductSearchResultsPage searchResultsPage = homePage1.headerMiddleBar.searchForProduct(
                    product4);
            searchResultsPage.selectProductFromList(product4);
            OroShoppingListPage shoppingListPage = searchResultsPage.goToShoppingListPage();
            searchResultsPage.enterProductQuantity(qty4, "4");
        }
    }
}
