package com.vertex.quality.connectors.kibo.tests.base;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.connectors.kibo.components.KiboMainMenuNavPanel;
import com.vertex.quality.connectors.kibo.enums.KiboCredentials;
import com.vertex.quality.connectors.kibo.enums.KiboCustomers;
import com.vertex.quality.connectors.kibo.enums.KiboData;
import com.vertex.quality.connectors.kibo.enums.OSeriesData;
import com.vertex.quality.connectors.kibo.pages.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import static org.testng.Assert.assertTrue;

/**
 * this class represents all the common methods used in most of the test cases
 * such as logging in to the admin home page, setting up the basic configs and other helper methods
 *
 * @author osabha
 */
@Test(groups = {"kibo"})
public abstract class KiboTaxCalculationBaseTest extends VertexUIBaseTest<KiboVertexConnectorPage> {

    private final boolean isDriverHeadlessMode = Boolean.getBoolean("services.webdriver.headless");
    private final boolean isDriverServiceProvisioned = Boolean.getBoolean("services.webdriver.provisioned");
    private final boolean isSeleniumHost = Boolean.getBoolean("services.webdriver.host.selenium");

    @Override
    public KiboVertexConnectorPage loadInitialTestPage() {
        KiboVertexConnectorPage newPage = signInAndSetupConfig();
        return newPage;
    }

    /**
     * initializes the ChromeDriver in 1920*1080 resolution which interacts with the browser
     */
    @Override
    protected void createChromeDriver() {
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", VertexPage.DOWNLOAD_DIRECTORY_PATH);

        // Add ChromeDriver-specific capabilities through ChromeOptions.
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
        options.addArguments("window-size=1920,1080");
        options.addArguments("--disable-infobars");
        if (isDriverHeadlessMode) {
            options.addArguments("--headless");
        }
        driver = isDriverServiceProvisioned
                ? new RemoteWebDriver(getDriverServiceUrl(), options)
                : new ChromeDriver(options);
    }

    private URL getDriverServiceUrl() {
        try {
            String hostname = "localhost";
            if (isSeleniumHost) {
                hostname = "selenium"; //instead of "localhost" for GitHub Actions
            }
            return new URL("http://" + hostname + ":4444/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * does sign in and navigate to setup all the preReq to the test cases
     *
     * @return new instance of the Kibo Vertex Connector Page
     */
    protected KiboVertexConnectorPage signInAndSetupConfigs() {
        KiboAdminHomePage homePage = signInToHomePage();
        homePage.clickMainMenu();
        homePage.navPanel.clickMainTab();
        KiboLocationsPage locationsPage = homePage.navPanel.goToLocationsPage();
        KiboHomeBasePage homeBasePage = locationsPage.goToHomeBasePage();
        homeBasePage.clickAddressField();
        homeBasePage.enterStreetAddress();
        homeBasePage.enterCity();
        homeBasePage.enterState();
        homeBasePage.enterZip();
        homeBasePage.clickConfirmButton();
        homeBasePage.clickSaveButton();
        homeBasePage.clickMainMenu();
        homeBasePage.navPanel.clickSystemTab();
        KiboApplicationsPage applicationsPage = homeBasePage.navPanel.goToApplicationsPage();
        KiboVertexConnectorPage connectorPage = applicationsPage.clickVertexConnector();
        connectorPage.makeSureApplicationEnabled();
        connectorPage.clickConfigureApplicationButton();
        connectorPage.configurationDialog.clickProductField();
        connectorPage.configurationDialog.selectOSeriesProduct();
        connectorPage.configurationDialog.enterCompanyCode(KiboCredentials.COMPANY_CODE);
        connectorPage.configurationDialog.clickAuthenticationTypeUsernamePassword();
        connectorPage.configurationDialog.enterOseriesUsername(KiboCredentials.OSERIES_USERNAME);
        connectorPage.configurationDialog.enterOseriesPassword(KiboCredentials.OSERIES_PASSWORD);
        connectorPage.configurationDialog.enterOseriesLink(KiboCredentials.OSERIES_URL);
        connectorPage.configurationDialog.clickSaveButton();
        connectorPage.configurationDialog.clickOptionsButton();
        connectorPage.configurationDialog.turnOnInvoicing();
        connectorPage.configurationDialog.enableAddressCleansing();
        connectorPage.configurationDialog.clickSaveButton();
        connectorPage.configurationDialog.closeConfigAppPopup();

        return new KiboVertexConnectorPage(driver);
    }

    /**
     * does sign in and navigate to setup all the preReq to the test cases
     *
     * @return new instance of the Kibo Vertex Connector Page
     */
    protected KiboVertexConnectorPage signInAndSetupConfig() {
        KiboAdminHomePage homePage = signInToHomePage();
        homePage.clickMainMenu();
        homePage.navPanel.clickMainTab();
        KiboLocationsPage locationsPage = homePage.navPanel.goToLocationsPage();
        return new KiboVertexConnectorPage(driver);
    }

    /**
     * does the sign in to the test environment
     *
     * @return new instance of the admin home page class
     */
    protected KiboAdminHomePage signInToHomePage() {
        driver.get(KiboCredentials.CONFIG_SIGN_ON_URL.value);

        KiboAdminSignOnPage signOnPage = new KiboAdminSignOnPage(driver);

        boolean isUsernameFieldPresent = signOnPage.isUsernameFieldPresent();
        assertTrue(isUsernameFieldPresent);

        signOnPage.enterUsername(KiboCredentials.CONFIG_USERNAME.value);

        signOnPage.clickLoginButton();
        boolean isPasswordFieldPresent = signOnPage.isPasswordFieldPresent();
        assertTrue(isPasswordFieldPresent);

        signOnPage.enterPassword(KiboCredentials.CONFIG_PASSWORD.value);
        KiboAdminHomePage homepage = signOnPage.clickLoginButton();
        return homepage;
    }

    /**
     * Navigates to vertex application configuration.
     */
    protected void navigateToVertexConfiguration() {
        KiboVertexConnectorPage connectorPage = new KiboVertexConnectorPage(driver);
        KiboMainMenuNavPanel navPanel = connectorPage.clickMainMenus();
        navPanel.gotoSystemTab();
        KiboApplicationsPage applicationsPage = navPanel.goToApplicationsPage();
        applicationsPage.selectVertexConnector();
        connectorPage.makeSureApplicationEnabled();
        connectorPage.clickConfigureApplicationButton();
    }

    /**
     * does the navigation from the vertex connector page ( after basic config setup) to the live maxine store front
     *
     * @return new instance of Kibo store front page
     */
    protected KiboStoreFrontPage navigateToStoreFront() {
        KiboVertexConnectorPage vertexConnectorPage = new KiboVertexConnectorPage(driver);
        KiboMainMenuNavPanel navPanel = vertexConnectorPage.clickMainMenu();
        navPanel.clickMainTab();
        KiboSiteBuilderEditorPage siteEditor = navPanel.goToSiteBuilderEditorPage();
        siteEditor.clickViewButton();
        siteEditor.clickViewLiveButton();
        return new KiboStoreFrontPage(driver);
    }

    /**
     * navigates to the Maxine store in the back office from the vertex connector page
     *
     * @return new instance of the Kibo back office store page
     */
    protected KiboBackOfficeStorePage navigateToBackOfficeStore() {
        KiboVertexConnectorPage vertexConnectorPage = new KiboVertexConnectorPage(driver);
        KiboMainMenuNavPanel navPanel = vertexConnectorPage.clickMainMenu();
        navPanel.clickMainTab();
        KiboOrdersPage orderPage = navPanel.goToOrderPage();
        orderPage.clickCreateNewOrder();
        orderPage.goToMaxineStore();
        return new KiboBackOfficeStorePage(driver);
    }


    /**
     * navigates to the Maxine store in the back office from the vertex connector page
     *
     * @return new instance of the Kibo back office store page
     */
    protected KiboBackOfficeStorePage navigateToBackOfficeStores() {
        KiboVertexConnectorPage vertexConnectorPage = new KiboVertexConnectorPage(driver);
        KiboMainMenuNavPanel navPanel = vertexConnectorPage.clickMainMenu();
        navPanel.clickMainTab();
        KiboOrdersPage orderPage = navPanel.goToOrderPage();
        orderPage.clickCreateNewOrder();
        orderPage.goToMysticSportsStore();
        orderPage.waitForPageLoad();
        return new KiboBackOfficeStorePage(driver);
    }

    /**
     * navigates to the Mystic store in the back office from the vertex connector page
     *
     * @return new instance of the Kibo back office store page
     */
    protected KiboBackOfficeStorePage navigateToBackOfficeMysticStores() {
        KiboVertexConnectorPage vertexConnectorPage = new KiboVertexConnectorPage(driver);
        KiboMainMenuNavPanel navPanel = vertexConnectorPage.clickMainMenus();
        navPanel.clickMainTab();
        KiboOrdersPage orderPage = navPanel.goToOrderPage();
        orderPage.clickCreateNewOrder();
        orderPage.goToMysticSportsStore();
        return new KiboBackOfficeStorePage(driver);
    }

    /**
     * Helper method to select a customer from the registered customers list
     *
     * @param customer registered customer from the list.
     */
    protected void selectCustomerAndOpenOrderDetailsDialog(KiboCustomers customer) {
        KiboBackOfficeStorePage maxinePage = new KiboBackOfficeStorePage(driver);

        maxinePage.clickCustomerList();
        maxinePage.selectCustomer(customer);
        maxinePage.clickEditDetails();
    }

    /**
     * Helper method to select a customer from the registered customers list.
     *
     * @param customer customer to be selected from list.
     * @param address1 street address of the customer.
     * @param City     residing City Of the customer.
     * @param State    residing State of the customer.
     * @param Zip      postal zip of the customer
     * @param Country  residing country of the customer.
     */
    protected void selectCustomerAndOpenOrdersDetails(KiboCustomers customer, String address1, String City, String State, String Zip, String Country) {
        KiboBackOfficeStorePage maxinePage = new KiboBackOfficeStorePage(driver);

        maxinePage.clickCustomerList();
        maxinePage.selectCustomer(customer);
        maxinePage.clickEditButton();
        maxinePage.enterAddressLine(address1);
        maxinePage.enterAddressCity(City);
        maxinePage.enterAddressState(State);
        maxinePage.enterAddressZip(Zip);
        maxinePage.enterCountryCode(Country);
        maxinePage.clickSaveAddressDetail();
        maxinePage.clickSavesDetails();
        maxinePage.clickEditDetails();
    }

    /**
     * Helper method to select a customer from the registered customers list
     *
     * @param customer customer to be selected from list.
     * @param address1 street address of the customer.
     * @param City     residing City Of the customer.
     * @param State    residing State of the customer.
     * @param Zip      postal zip of the customer
     * @param Country  residing country of the customer.
     */
    protected void selectCustomerAndOpenOrderDetails(KiboCustomers customer, String address1, String City, String State, String Zip, String Country) {
        KiboBackOfficeStorePage maxinePage = new KiboBackOfficeStorePage(driver);

        maxinePage.clickCustomerList();
        maxinePage.selectCustomer(customer);
        maxinePage.clickEditButton();
        maxinePage.enterAddressLine(address1);
        maxinePage.enterAddressCity(City);
        maxinePage.enterAddressState(State);
        maxinePage.enterAddressZip(Zip);
        maxinePage.enterCountryCode(Country);
        maxinePage.clickSaveAddressDetail();
        maxinePage.clickSaveDetails();

        maxinePage.clickEditDetails();
    }

    /**
     * Select customer, enter address & validate address
     * Parameter sequence must be followed as: Address Line, City, State, Zip & Country
     *
     * @param customer pass customer name which is to be selected
     * @param address  pass address which is to be validated
     */
    public void selectCustomerEnterAndValidateAddress(KiboCustomers customer, String... address) {
        if (address.length < 5) {
            Assert.fail("All fields are mandatory so make sure address length must be 5");
        }
        KiboBackOfficeStorePage maxinePage = new KiboBackOfficeStorePage(driver);
        maxinePage.clickCustomerList();
        maxinePage.selectCustomer(customer);
        maxinePage.clickEditButton();
        maxinePage.enterAddressLine(address[0]);
        maxinePage.enterAddressCity(address[1]);
        maxinePage.enterAddressState(address[2]);
        maxinePage.enterAddressZip(address[3]);
        maxinePage.enterCountryCode(address[4]);
        maxinePage.clickValidateAddress();
        maxinePage.waitForPageLoad();
    }

    /**
     * Helper method to do the fulfillment steps for orders to ship
     */
    protected void fulfillOrderForShipping() {
        KiboBackOfficeStorePage maxinePage = new KiboBackOfficeStorePage(driver);

        maxinePage.fulfillment.clickFulfillmentButton();
        maxinePage.fulfillment.clickMoveToButton();
        maxinePage.fulfillment.selectNewPackage();
        maxinePage.fulfillment.clickMarkAsShippedButton();
    }

    /**
     * Helper method to do the fulfillment steps for pickup orders
     */
    protected void fulfillOrderForPickup() {
        KiboBackOfficeStorePage maxinePage = new KiboBackOfficeStorePage(driver);

        maxinePage.fulfillment.clickFulfillmentButton();
        maxinePage.fulfillment.clickMoveToButton();
        maxinePage.fulfillment.selectNewPickup();
        maxinePage.fulfillment.clickMarkAsFulfilledButton();
    }

    /**
     * Helper method to do all the navigation to make payment for orders
     *
     * @param checkNumber check number for processing payment.
     */
    protected void payForTheOrder(String checkNumber) {
        KiboBackOfficeStorePage maxinePage = new KiboBackOfficeStorePage(driver);

        maxinePage.payment.clickPaymentButton();
        maxinePage.payment.clickAddPaymentTypeButton();
        maxinePage.payment.selectCheck();
        maxinePage.payment.enterPayerFirstName();
        maxinePage.payment.clickCheckSaveButton();
        maxinePage.payment.clickCaptureButton();
        maxinePage.payment.enterCheckNumber(checkNumber);
        maxinePage.payment.clickSaveCollectedCheck();
    }

    /**
     * navigates to the Customer Class in the back office from the Customers page
     *
     * @param customerClass customer class of the customer.
     * @return new instance of the Kibo back office store page
     */
    protected KiboBackOfficeStorePage navigateToCustomerClass(String customerClass) {
        KiboVertexConnectorPage vertexConnectorPage = new KiboVertexConnectorPage(driver);
        KiboMainMenuNavPanel navPanel = vertexConnectorPage.clickMainMenu();
        navPanel.clickMainTab();
        navPanel.goToCustomersPage();
        navPanel.editCustomerByName(KiboCustomers.Customer5.value);
        navPanel.selectVertexCustomerClass(customerClass);
        navPanel.removeVertexCustomerCode();
        navPanel.clickSaveButton();
        navPanel.waitForPageLoad();
        return new KiboBackOfficeStorePage(driver);
    }

    /**
     * Removes customer code & customer class if any applied to the customer
     *
     * @param customerName pass customer name on which customer code & class should be removed
     */
    protected void removeCustomerCodeClass(String customerName) {
        KiboVertexConnectorPage vertexConnectorPage = new KiboVertexConnectorPage(driver);
        KiboMainMenuNavPanel navPanel = vertexConnectorPage.clickMainMenus();
        navPanel.clickMainTab();
        navPanel.goToCustomersPage();
        navPanel.editCustomerByName(customerName);
        navPanel.deSelectVertexCustomerClass();
        navPanel.removeVertexCustomerCode();
        navPanel.clickSaveButton();
        navPanel.waitForPageLoad();
    }

    /**
     * navigates to the Product Class in the back office from the Customers page
     *
     * @param productClass product class of the customer to be exempted.
     * @return new instance of the Kibo back office store page
     */
    protected KiboBackOfficeStorePage navigateToProductClass(String productClass) {
        KiboVertexConnectorPage vertexConnectorPage = new KiboVertexConnectorPage(driver);
        KiboMainMenuNavPanel navPanel = vertexConnectorPage.clickMainMenu();
        navPanel.clickMainTab();
        navPanel.goToProductsPage();
        if (!navPanel.checkProductAvailability(productClass)) {
            navPanel.createNewProduct();
            navPanel.enterProductTitle(productClass);
            navPanel.enterProductCode(productClass);
            navPanel.enterProductType(KiboData.PRODUCT_TYPE_BIKE.value);
            navPanel.enterProductUsage();
            navPanel.enterPrice(KiboData.PRICE_AMOUNT.value);
            navPanel.clickSaveButton();
        }
        return new KiboBackOfficeStorePage(driver);
    }

    /**
     * navigates to the Customer Code in the back office from the Customers page
     *
     * @param customerCode customer code of the customer to be exempted.
     * @return new instance of the Kibo back office store page
     */
    protected KiboBackOfficeStorePage navigateToCustomerCode(String customerCode) {
        KiboVertexConnectorPage vertexConnectorPage = new KiboVertexConnectorPage(driver);
        KiboMainMenuNavPanel navPanel = vertexConnectorPage.clickMainMenu();
        navPanel.clickMainTab();
        navPanel.goToCustomersPage();
        navPanel.editCustomerByName(KiboCustomers.Customer5.value);
        navPanel.enterVertexCustomerCode(customerCode);
        navPanel.deSelectVertexCustomerClass();
        navPanel.clickSaveButton();
        navPanel.waitForPageLoad();
        return new KiboBackOfficeStorePage(driver);
    }

    /**
     * navigates to the Product Code in the back office from the Customers page
     *
     * @param productCode product code of the customer to be exempted.
     * @return new instance of the Kibo back office store page
     */
    protected KiboBackOfficeStorePage navigateToProductCode(String productCode) {
        KiboVertexConnectorPage vertexConnectorPage = new KiboVertexConnectorPage(driver);
        KiboMainMenuNavPanel navPanel = vertexConnectorPage.clickMainMenu();
        navPanel.clickMainTab();
        navPanel.goToProductsPage();
        if (!navPanel.checkProductAvailability(productCode)) {
            navPanel.createNewProduct();
            navPanel.enterProductTitle(productCode);
            navPanel.enterProductCode(productCode);
            navPanel.enterProductType(KiboData.PRODUCT_TYPE_BIKE.value);
            navPanel.enterProductUsage();
            navPanel.enterPrice(KiboData.PRICE_AMOUNT.value);
            navPanel.clickSaveButton();
        }
        return new KiboBackOfficeStorePage(driver);
    }

    /**
     * Takes to vertex application configuration.
     */
    protected void gotoVertexConfiguration() {
        KiboVertexConnectorPage connectorPage = new KiboVertexConnectorPage(driver);
        connectorPage.waitForPageLoad();
        KiboMainMenuNavPanel navPanel = connectorPage.clickMainMenu();
        navPanel.gotoSystemTab();
        KiboApplicationsPage applicationsPage = navPanel.goToApplicationsPage();
        applicationsPage.selectVertexConnector();
        connectorPage.makeSureApplicationEnabled();
        connectorPage.clickConfigureApplicationButton();
    }

    /**
     * Enable or disable address cleansing from Kibo
     *
     * @param enable pass true to enable & false to disable address cleansing
     */
    protected void enableOrDisableAddressCleansingFromKibo(boolean enable) {
        KiboVertexConnectorPage connectorPage = new KiboVertexConnectorPage(driver);
        connectorPage.configurationDialog.gotoOptions();
        connectorPage.configurationDialog.enableDisableAddressCleansing(enable);
        connectorPage.configurationDialog.clickSaveButton();
    }

    /**
     * Enable or disable invoice from Kibo
     *
     * @param enable pass true to enable & false to disable invoice
     */
    protected void enableOrDisableInvoiceFromKibo(boolean enable) {
        KiboVertexConnectorPage connectorPage = new KiboVertexConnectorPage(driver);
        connectorPage.configurationDialog.gotoOptions();
        connectorPage.configurationDialog.enableDisableInvoice(enable);
        connectorPage.configurationDialog.clickSaveButton();
    }

    /**
     * Enter bad urls or correct urls to Tax Calculation & Address Cleansing to O-Series from Kibo.
     *
     * @param tax       pass true to enter bad url & false to enter correct url for tax calc
     * @param cleansing pass true to enter bad url & false to enter correct url for cleansing
     */
    protected void enterOrRemoveBadUrlsForTaxAndCleansing(boolean tax, boolean cleansing) {
        KiboVertexConnectorPage connectorPage = new KiboVertexConnectorPage(driver);
        connectorPage.configurationDialog.clickConnectionButton();
        if (tax) {
            connectorPage.configurationDialog.enterTaxationUrl(OSeriesData.BAD_URL.data);
        } else {
            connectorPage.configurationDialog.enterTaxationUrl(OSeriesData.O_SERIES_TAX_CALCULATION_URL.data);
        }
        if (cleansing) {
            connectorPage.configurationDialog.enterCleansingUrl(OSeriesData.BAD_URL.data);
        } else {
            connectorPage.configurationDialog.enterCleansingUrl(OSeriesData.O_SERIES_ADDRESS_CLEANSING_URL.data);
        }
        connectorPage.configurationDialog.clickSaveButton();
    }
}