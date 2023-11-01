package com.vertex.quality.connectors.hybris.tests.addressCleansing;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.utils.properties.CommonDataProperties;
import com.vertex.quality.connectors.hybris.enums.backoffice.HybrisBOBaseStoreTabOptions;
import com.vertex.quality.connectors.hybris.enums.backoffice.HybrisBONavTreeOptions;
import com.vertex.quality.connectors.hybris.enums.backoffice.HybrisPropertiesAndValues;
import com.vertex.quality.connectors.hybris.enums.electronics.HybrisAddressCleansingWrongZip;
import com.vertex.quality.connectors.hybris.enums.electronics.HybrisProductIds;
import com.vertex.quality.connectors.hybris.enums.oseries.OSeriesData;
import com.vertex.quality.connectors.hybris.pages.backoffice.HybrisBOBaseStorePage;
import com.vertex.quality.connectors.hybris.pages.backoffice.HybrisBOHomePage;
import com.vertex.quality.connectors.hybris.pages.backoffice.HybrisBOVertexConfigurationPage;
import com.vertex.quality.connectors.hybris.pages.electronics.HybrisEStoreCheckOutPage;
import com.vertex.quality.connectors.hybris.pages.electronics.HybrisEStoreGuestLoginPage;
import com.vertex.quality.connectors.hybris.pages.electronics.HybrisEStorePage;
import com.vertex.quality.connectors.hybris.tests.base.HybrisBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Test class to validate address cleansing messages for Ship To address.
 *
 * @author Shivam.Soni
 */
public class HybrisAddressCleansingShipToTests extends HybrisBaseTest {

    HybrisBOHomePage boHomePage;
    HybrisBOBaseStorePage electronicsStorePage;
    HybrisBOVertexConfigurationPage vertexConfigPage;
    HybrisEStorePage storeFront;
    HybrisEStoreGuestLoginPage eStoreGuestLoginPage;
    HybrisEStoreCheckOutPage checkoutPage;
    String cleansedAddressFromUI;

    /**
     * CHYB-223 HYB - Address Cleansing OFF with Invalid Zip
     */
    @Test(groups = {"hybris_regression"})
    public void storeAddressCleansingOffInvalidZipTest() {
        try {
            // login as Backoffice user into Hybris-Backoffice Page
            boHomePage = loginBOUser();

            // navigate to BaseStore - Electronic Store Page
            electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());
            electronicsStorePage.selectElectronicsStore();

            // Set Allow Vertex Cleansed Address as False to off address cleansing
            electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption());
            electronicsStorePage.setStorefrontElementProperty(HybrisPropertiesAndValues.ALWAYS_ACCEPT_CLEANSED_ADDRESS.getData(), HybrisPropertiesAndValues.PROPERTY_VALUE_FALSE.getData());
            electronicsStorePage.saveElectronicsStoreConfiguration();

            // Logout from Back office
            boHomePage.logoutFromBackOffice();

            // launch Electronics store-front page
            storeFront = launchB2CPage();

            // Add Products to Cart and Proceed to Checkout
            storeFront.searchAndAddProductToCart(HybrisProductIds.TMAXP3200ID.getproductID());
            eStoreGuestLoginPage = storeFront.proceedToCheckout();

            // Set Electronics Store Guest Credentials and checkout as Guest
            setGuestEmailAndConfirmEmail(eStoreGuestLoginPage);
            checkoutPage = eStoreGuestLoginPage.checkoutAsGuest();

            // Fill Shipping Address Details and Proceed to Checkout
            checkoutPage.fillShippingAddressDetails(Address.Philadelphia.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                    Address.Philadelphia.addressLine1, Address.Philadelphia.city, Address.Philadelphia.state.fullName, HybrisAddressCleansingWrongZip.INVALID_ZIP_99999.getZip());
            checkoutPage.clickDeliveryAddressNext();
            assertTrue(checkoutPage.isAddressCleansingPopupDisplayed(), "Address Cleansing Popup is not displayed, please check...");
            assertTrue(checkoutPage.isUseThisAddressButtonDisplayed(), "Use This Address Button is NOT displayed, please check...");
            assertTrue(checkoutPage.isSubmittAsIsButtonDisplayed(), "Submit As Is Button is NOT displayed, please check...");
            cleansedAddressFromUI = checkoutPage.getOriginalAddressFromAddressCleansingPopUp();
            assertTrue(cleansedAddressFromUI.contains(Address.Philadelphia.country.fullName));
            assertTrue(cleansedAddressFromUI.contains(Address.Philadelphia.addressLine1));
            assertTrue(cleansedAddressFromUI.contains(Address.Philadelphia.city));
            assertTrue(cleansedAddressFromUI.contains(Address.Philadelphia.state.fullName));
            assertTrue(cleansedAddressFromUI.contains(HybrisAddressCleansingWrongZip.INVALID_ZIP_99999.getZip()));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            quitDriver();
            createChromeDriver();

            // login as Backoffice user into Hybris-Backoffice Page
            boHomePage = loginBOUser();

            // navigate to BaseStore - Electronic Store Page
            electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());
            electronicsStorePage.selectElectronicsStore();

            // Set Allow Vertex Cleansed Address as True to on address cleansing
            electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption());
            electronicsStorePage.setStorefrontElementProperty(HybrisPropertiesAndValues.ALWAYS_ACCEPT_CLEANSED_ADDRESS.getData(), HybrisPropertiesAndValues.PROPERTY_VALUE_TRUE.getData());
            electronicsStorePage.saveElectronicsStoreConfiguration();

            // Logout from Back office
            boHomePage.logoutFromBackOffice();
        }
    }

    /**
     * CHYB-224 HYB - Address Cleansing OFF with No Zip
     */
    @Test(groups = {"hybris_regression"})
    public void storeAddressCleansingOffNoZipTest() {
        try {
            // login as Backoffice user into Hybris-Backoffice Page
            boHomePage = loginBOUser();

            // navigate to BaseStore - Electronic Store Page
            electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());
            electronicsStorePage.selectElectronicsStore();

            // Set Allow Vertex Cleansed Address as False to off address cleansing
            electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption());
            electronicsStorePage.setStorefrontElementProperty(HybrisPropertiesAndValues.ALWAYS_ACCEPT_CLEANSED_ADDRESS.getData(), HybrisPropertiesAndValues.PROPERTY_VALUE_FALSE.getData());
            electronicsStorePage.saveElectronicsStoreConfiguration();

            // Logout from Back office
            boHomePage.logoutFromBackOffice();

            // launch Electronics store-front page
            storeFront = launchB2CPage();

            // Add Products to Cart and Proceed to Checkout
            storeFront.searchAndAddProductToCart(HybrisProductIds.TMAXP3200ID.getproductID());
            eStoreGuestLoginPage = storeFront.proceedToCheckout();

            // Set Electronics Store Guest Credentials and checkout as Guest
            setGuestEmailAndConfirmEmail(eStoreGuestLoginPage);
            checkoutPage = eStoreGuestLoginPage.checkoutAsGuest();

            // Fill Shipping Address Details and Proceed to Checkout
            checkoutPage.fillShippingAddressDetails(Address.Philadelphia.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                    Address.Philadelphia.addressLine1, Address.Philadelphia.city, Address.Philadelphia.state.fullName, HybrisAddressCleansingWrongZip.EMPTY_ZIP.getZip());
            checkoutPage.clickDeliveryAddressNext();
            assertTrue(checkoutPage.isAddressCleansingPopupDisplayed(), "Address Cleansing Popup is not displayed, please check...");
            assertTrue(checkoutPage.isUseThisAddressButtonDisplayed(), "Use This Address Button is NOT displayed, please check...");
            assertTrue(checkoutPage.isSubmittAsIsButtonDisplayed(), "Submit As Is Button is NOT displayed, please check...");
            cleansedAddressFromUI = checkoutPage.getOriginalAddressFromAddressCleansingPopUp();
            assertTrue(cleansedAddressFromUI.contains(Address.Philadelphia.country.fullName));
            assertTrue(cleansedAddressFromUI.contains(Address.Philadelphia.addressLine1));
            assertTrue(cleansedAddressFromUI.contains(Address.Philadelphia.city));
            assertTrue(cleansedAddressFromUI.contains(Address.Philadelphia.state.fullName));
            assertTrue(cleansedAddressFromUI.contains(HybrisAddressCleansingWrongZip.EMPTY_ZIP.getZip()));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            quitDriver();
            createChromeDriver();

            // login as Backoffice user into Hybris-Backoffice Page
            boHomePage = loginBOUser();

            // navigate to BaseStore - Electronic Store Page
            electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());
            electronicsStorePage.selectElectronicsStore();

            // Set Allow Vertex Cleansed Address as True to on address cleansing
            electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption());
            electronicsStorePage.setStorefrontElementProperty(HybrisPropertiesAndValues.ALWAYS_ACCEPT_CLEANSED_ADDRESS.getData(), HybrisPropertiesAndValues.PROPERTY_VALUE_TRUE.getData());
            electronicsStorePage.saveElectronicsStoreConfiguration();

            // Logout from Back office
            boHomePage.logoutFromBackOffice();
        }
    }

    /**
     * CHYB-229 HYB - Address Cleansing Continue to Calc OFF with Invalid Zip - Shipping Address
     */
    @Test(groups = {"hybris_regression"})
    public void storeAddressCleansingCalcOffInvalidZipTest() {
        try {
            // login as Backoffice user into Hybris-Backoffice Page
            boHomePage = loginBOUser();

            // navigate to BaseStore - Electronic Store Page
            electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());
            electronicsStorePage.selectElectronicsStore();

            // Set Allow Vertex Cleansed Address as False to off address cleansing
            electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption());
            electronicsStorePage.setStorefrontElementProperty(HybrisPropertiesAndValues.ALWAYS_ACCEPT_CLEANSED_ADDRESS.getData(), HybrisPropertiesAndValues.PROPERTY_VALUE_TRUE.getData());
            electronicsStorePage.saveElectronicsStoreConfiguration();

            // navigate to vertex configuration
            vertexConfigPage = boHomePage.navigateToConfigurationPage(HybrisBONavTreeOptions.VERTEX.getMenuName(), HybrisBONavTreeOptions.VERTEX_CONFIGURATION.getMenuName());
            vertexConfigPage.selectVertexConfigurationRow(0);

            // select authentication tab
            vertexConfigPage.navigateToAdministrationTab();

            // set bad url for tax to disable tax calculation
            vertexConfigPage.setEndpointTaxCalculation(OSeriesData.BAD_URL.data);
            vertexConfigPage.saveVertexConfiguration();

            // Logout from Back office
            boHomePage.logoutFromBackOffice();

            // launch Electronics store-front page
            storeFront = launchB2CPage();

            // Add Products to Cart and Proceed to Checkout
            storeFront.searchAndAddProductToCart(HybrisProductIds.TMAXP3200ID.getproductID());
            eStoreGuestLoginPage = storeFront.proceedToCheckout();

            // Set Electronics Store Guest Credentials and checkout as Guest
            setGuestEmailAndConfirmEmail(eStoreGuestLoginPage);
            checkoutPage = eStoreGuestLoginPage.checkoutAsGuest();

            // Fill Shipping Address Details and Proceed to Checkout
            checkoutPage.fillShippingAddressDetails(Address.ChesterInvalidAddress.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                    Address.ChesterInvalidAddress.addressLine1, Address.ChesterInvalidAddress.city, Address.ChesterInvalidAddress.state.fullName, Address.ChesterInvalidAddress.zip5);
            checkoutPage.clickDeliveryAddressNext();
            assertFalse(checkoutPage.isAddressCleansingPopupDisplayed(), "Address Cleansing Popup is not displayed, please check...");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            quitDriver();
            createChromeDriver();

            // login as Backoffice user into Hybris-Backoffice Page
            boHomePage = loginBOUser();

            // navigate to BaseStore - Electronic Store Page
            electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());
            electronicsStorePage.selectElectronicsStore();

            // Set Allow Vertex Cleansed Address as True to on address cleansing
            electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption());
            electronicsStorePage.setStorefrontElementProperty(HybrisPropertiesAndValues.ALWAYS_ACCEPT_CLEANSED_ADDRESS.getData(), HybrisPropertiesAndValues.PROPERTY_VALUE_TRUE.getData());
            electronicsStorePage.saveElectronicsStoreConfiguration();

            // navigate to vertex configuration
            vertexConfigPage = boHomePage.navigateToConfigurationPage(HybrisBONavTreeOptions.VERTEX.getMenuName(), HybrisBONavTreeOptions.VERTEX_CONFIGURATION.getMenuName());
            vertexConfigPage.selectVertexConfigurationRow(0);

            // select authentication tab
            vertexConfigPage.navigateToAdministrationTab();

            // set bad url for tax to disable tax calculation
            vertexConfigPage.setEndpointTaxCalculation(OSeriesData.O_SERIES_TAX_CALCULATION_URL.data);
            vertexConfigPage.saveVertexConfiguration();

            // Logout from Back office
            boHomePage.logoutFromBackOffice();
        }
    }

    /**
     * CHYB-225 HYB - Validate addresses with address cleansing OFF - Shipping Address
     */
    @Test(groups = {"hybris_regression"})
    public void storeAddressCleansingOffTest() {
        try {
            // login as Backoffice user into Hybris-Backoffice Page
            boHomePage = loginBOUser();

            // navigate to BaseStore - Electronic Store Page
            electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());
            electronicsStorePage.selectElectronicsStore();

            // Set Allow Vertex Cleansed Address as False to off address cleansing
            electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption());
            electronicsStorePage.setStorefrontElementProperty(HybrisPropertiesAndValues.ALWAYS_ACCEPT_CLEANSED_ADDRESS.getData(), HybrisPropertiesAndValues.PROPERTY_VALUE_FALSE.getData());
            electronicsStorePage.saveElectronicsStoreConfiguration();

            // Logout from Back office
            boHomePage.logoutFromBackOffice();

            // launch Electronics store-front page
            storeFront = launchB2CPage();

            // Add Products to Cart and Proceed to Checkout
            storeFront.searchAndAddProductToCart(HybrisProductIds.TMAXP3200ID.getproductID());
            eStoreGuestLoginPage = storeFront.proceedToCheckout();

            // Set Electronics Store Guest Credentials and checkout as Guest
            setGuestEmailAndConfirmEmail(eStoreGuestLoginPage);
            checkoutPage = eStoreGuestLoginPage.checkoutAsGuest();

            // Fill Shipping Address Details and Proceed to Checkout
            checkoutPage.fillShippingAddressDetails(Address.Birmingham.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                    Address.Birmingham.addressLine1, Address.Birmingham.city, Address.Birmingham.state.fullName, HybrisAddressCleansingWrongZip.EMPTY_ZIP.getZip());
            checkoutPage.clickDeliveryAddressNext();
            assertTrue(checkoutPage.isAddressCleansingPopupDisplayed(), "Address Cleansing Popup is not displayed, please check...");
            assertTrue(checkoutPage.isUseThisAddressButtonDisplayed(), "Use This Address Button is NOT displayed, please check...");
            assertTrue(checkoutPage.isSubmittAsIsButtonDisplayed(), "Submit As Is Button is NOT displayed, please check...");
            cleansedAddressFromUI = checkoutPage.getOriginalAddressFromAddressCleansingPopUp();
            assertTrue(cleansedAddressFromUI.contains(Address.Birmingham.country.fullName));
            assertTrue(cleansedAddressFromUI.contains(Address.Birmingham.addressLine1));
            assertTrue(cleansedAddressFromUI.contains(Address.Birmingham.city));
            assertTrue(cleansedAddressFromUI.contains(Address.Birmingham.state.fullName));
            assertTrue(cleansedAddressFromUI.contains(HybrisAddressCleansingWrongZip.EMPTY_ZIP.getZip()));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            quitDriver();
            createChromeDriver();

            // login as Backoffice user into Hybris-Backoffice Page
            boHomePage = loginBOUser();

            // navigate to BaseStore - Electronic Store Page
            electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());
            electronicsStorePage.selectElectronicsStore();

            // Set Allow Vertex Cleansed Address as True to on address cleansing
            electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption());
            electronicsStorePage.setStorefrontElementProperty(HybrisPropertiesAndValues.ALWAYS_ACCEPT_CLEANSED_ADDRESS.getData(), HybrisPropertiesAndValues.PROPERTY_VALUE_TRUE.getData());
            electronicsStorePage.saveElectronicsStoreConfiguration();

            // Logout from Back office
            boHomePage.logoutFromBackOffice();
        }
    }

    /**
     * CHYB-226 HYB - Validate Sales Order with Address Cleansing No Zip
     */
    @Test(groups = {"hybris_regression"})
    public void storeAddressCleansingOffNullZipTest() {
        try {
            // login as Backoffice user into Hybris-Backoffice Page
            boHomePage = loginBOUser();

            // navigate to BaseStore - Electronic Store Page
            electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());
            electronicsStorePage.selectElectronicsStore();

            // Set Allow Vertex Cleansed Address as False to off address cleansing
            electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption());
            electronicsStorePage.setStorefrontElementProperty(HybrisPropertiesAndValues.ALWAYS_ACCEPT_CLEANSED_ADDRESS.getData(), HybrisPropertiesAndValues.PROPERTY_VALUE_FALSE.getData());
            electronicsStorePage.saveElectronicsStoreConfiguration();

            // Logout from Back office
            boHomePage.logoutFromBackOffice();

            // launch Electronics store-front page
            storeFront = launchB2CPage();

            // Add Products to Cart and Proceed to Checkout
            storeFront.searchAndAddProductToCart(HybrisProductIds.TMAXP3200ID.getproductID());
            eStoreGuestLoginPage = storeFront.proceedToCheckout();

            // Set Electronics Store Guest Credentials and checkout as Guest
            setGuestEmailAndConfirmEmail(eStoreGuestLoginPage);
            checkoutPage = eStoreGuestLoginPage.checkoutAsGuest();

            // Fill Shipping Address Details and Proceed to Checkout
            checkoutPage.fillShippingAddressDetails(Address.Philadelphia.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                    Address.Philadelphia.addressLine1, Address.Philadelphia.city, Address.Philadelphia.state.fullName, HybrisAddressCleansingWrongZip.NULL_ZIP.getZip());
            checkoutPage.clickDeliveryAddressNext();
            assertTrue(checkoutPage.isAddressCleansingPopupDisplayed(), "Address Cleansing Popup is not displayed, please check...");
            assertTrue(checkoutPage.isUseThisAddressButtonDisplayed(), "Use This Address Button is NOT displayed, please check...");
            assertTrue(checkoutPage.isSubmittAsIsButtonDisplayed(), "Submit As Is Button is NOT displayed, please check...");
            cleansedAddressFromUI = checkoutPage.getOriginalAddressFromAddressCleansingPopUp();
            assertTrue(cleansedAddressFromUI.contains(Address.Philadelphia.country.fullName));
            assertTrue(cleansedAddressFromUI.contains(Address.Philadelphia.addressLine1));
            assertTrue(cleansedAddressFromUI.contains(Address.Philadelphia.city));
            assertTrue(cleansedAddressFromUI.contains(Address.Philadelphia.state.fullName));
            assertTrue(cleansedAddressFromUI.contains(HybrisAddressCleansingWrongZip.NULL_ZIP.getZip()));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            quitDriver();
            createChromeDriver();

            // login as Backoffice user into Hybris-Backoffice Page
            boHomePage = loginBOUser();

            // navigate to BaseStore - Electronic Store Page
            electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());
            electronicsStorePage.selectElectronicsStore();

            // Set Allow Vertex Cleansed Address as True to on address cleansing
            electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption());
            electronicsStorePage.setStorefrontElementProperty(HybrisPropertiesAndValues.ALWAYS_ACCEPT_CLEANSED_ADDRESS.getData(), HybrisPropertiesAndValues.PROPERTY_VALUE_TRUE.getData());
            electronicsStorePage.saveElectronicsStoreConfiguration();

            // Logout from Back office
            boHomePage.logoutFromBackOffice();
        }
    }

    /**
     * CHYB-227 HYB - Address Cleansing OFF with Invalid City and Zip
     */
    @Test(groups = {"hybris_regression"})
    public void storeAddressCleansingOffInvalidCityZipTest() {
        try {
            // login as Backoffice user into Hybris-Backoffice Page
            boHomePage = loginBOUser();

            // navigate to BaseStore - Electronic Store Page
            electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());
            electronicsStorePage.selectElectronicsStore();

            // Set Allow Vertex Cleansed Address as False to off address cleansing
            electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption());
            electronicsStorePage.setStorefrontElementProperty(HybrisPropertiesAndValues.ALWAYS_ACCEPT_CLEANSED_ADDRESS.getData(), HybrisPropertiesAndValues.PROPERTY_VALUE_FALSE.getData());
            electronicsStorePage.saveElectronicsStoreConfiguration();

            // Logout from Back office
            boHomePage.logoutFromBackOffice();

            // launch Electronics store-front page
            storeFront = launchB2CPage();

            // Add Products to Cart and Proceed to Checkout
            storeFront.searchAndAddProductToCart(HybrisProductIds.TMAXP3200ID.getproductID());
            eStoreGuestLoginPage = storeFront.proceedToCheckout();

            // Set Electronics Store Guest Credentials and checkout as Guest
            setGuestEmailAndConfirmEmail(eStoreGuestLoginPage);
            checkoutPage = eStoreGuestLoginPage.checkoutAsGuest();

            // Fill Shipping Address Details and Proceed to Checkout
            checkoutPage.fillShippingAddressDetails(Address.Philadelphia.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                    Address.Philadelphia.addressLine1, Address.Chester.city, Address.Philadelphia.state.fullName, Address.Philadelphia.zip5);
            checkoutPage.clickDeliveryAddressNext();
            assertTrue(checkoutPage.isAddressCleansingPopupDisplayed(), "Address Cleansing Popup is not displayed, please check...");
            assertTrue(checkoutPage.isUseThisAddressButtonDisplayed(), "Use This Address Button is NOT displayed, please check...");
            assertTrue(checkoutPage.isSubmittAsIsButtonDisplayed(), "Submit As Is Button is NOT displayed, please check...");
            cleansedAddressFromUI = checkoutPage.getOriginalAddressFromAddressCleansingPopUp();
            assertTrue(cleansedAddressFromUI.contains(Address.Philadelphia.country.fullName));
            assertTrue(cleansedAddressFromUI.contains(Address.Philadelphia.addressLine1));
            assertTrue(cleansedAddressFromUI.contains(Address.Chester.city));
            assertTrue(cleansedAddressFromUI.contains(Address.Philadelphia.state.fullName));
            assertTrue(cleansedAddressFromUI.contains(Address.Philadelphia.zip5));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            quitDriver();
            createChromeDriver();

            // login as Backoffice user into Hybris-Backoffice Page
            boHomePage = loginBOUser();

            // navigate to BaseStore - Electronic Store Page
            electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());
            electronicsStorePage.selectElectronicsStore();

            // Set Allow Vertex Cleansed Address as True to on address cleansing
            electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption());
            electronicsStorePage.setStorefrontElementProperty(HybrisPropertiesAndValues.ALWAYS_ACCEPT_CLEANSED_ADDRESS.getData(), HybrisPropertiesAndValues.PROPERTY_VALUE_TRUE.getData());
            electronicsStorePage.saveElectronicsStoreConfiguration();

            // Logout from Back office
            boHomePage.logoutFromBackOffice();
        }
    }

    /**
     * CHYB-228 HYB - Address Cleansing OFF with Invalid State and Zip
     */
    @Test(groups = {"hybris_regression"})
    public void storeAddressCleansingOffInvalidStateZipTest() {
        try {
            // login as Backoffice user into Hybris-Backoffice Page
            boHomePage = loginBOUser();

            // navigate to BaseStore - Electronic Store Page
            electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());
            electronicsStorePage.selectElectronicsStore();

            // Set Allow Vertex Cleansed Address as False to off address cleansing
            electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption());
            electronicsStorePage.setStorefrontElementProperty(HybrisPropertiesAndValues.ALWAYS_ACCEPT_CLEANSED_ADDRESS.getData(), HybrisPropertiesAndValues.PROPERTY_VALUE_FALSE.getData());
            electronicsStorePage.saveElectronicsStoreConfiguration();

            // Logout from Back office
            boHomePage.logoutFromBackOffice();

            // launch Electronics store-front page
            storeFront = launchB2CPage();

            // Add Products to Cart and Proceed to Checkout
            storeFront.searchAndAddProductToCart(HybrisProductIds.TMAXP3200ID.getproductID());
            eStoreGuestLoginPage = storeFront.proceedToCheckout();

            // Set Electronics Store Guest Credentials and checkout as Guest
            setGuestEmailAndConfirmEmail(eStoreGuestLoginPage);
            checkoutPage = eStoreGuestLoginPage.checkoutAsGuest();

            // Fill Shipping Address Details and Proceed to Checkout
            checkoutPage.fillShippingAddressDetails(Address.Philadelphia.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                    Address.Philadelphia.addressLine1, Address.Philadelphia.city, Address.Delaware.state.fullName, Address.Philadelphia.zip5);
            checkoutPage.clickDeliveryAddressNext();
            assertTrue(checkoutPage.isAddressCleansingPopupDisplayed(), "Address Cleansing Popup is not displayed, please check...");
            assertTrue(checkoutPage.isUseThisAddressButtonDisplayed(), "Use This Address Button is NOT displayed, please check...");
            assertTrue(checkoutPage.isSubmittAsIsButtonDisplayed(), "Submit As Is Button is NOT displayed, please check...");
            cleansedAddressFromUI = checkoutPage.getOriginalAddressFromAddressCleansingPopUp();
            assertTrue(cleansedAddressFromUI.contains(Address.Philadelphia.country.fullName));
            assertTrue(cleansedAddressFromUI.contains(Address.Philadelphia.addressLine1));
            assertTrue(cleansedAddressFromUI.contains(Address.Philadelphia.city));
            assertTrue(cleansedAddressFromUI.contains(Address.Delaware.state.fullName));
            assertTrue(cleansedAddressFromUI.contains(Address.Philadelphia.zip5));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            quitDriver();
            createChromeDriver();

            // login as Backoffice user into Hybris-Backoffice Page
            boHomePage = loginBOUser();

            // navigate to BaseStore - Electronic Store Page
            electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());
            electronicsStorePage.selectElectronicsStore();

            // Set Allow Vertex Cleansed Address as True to on address cleansing
            electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption());
            electronicsStorePage.setStorefrontElementProperty(HybrisPropertiesAndValues.ALWAYS_ACCEPT_CLEANSED_ADDRESS.getData(), HybrisPropertiesAndValues.PROPERTY_VALUE_TRUE.getData());
            electronicsStorePage.saveElectronicsStoreConfiguration();

            // Logout from Back office
            boHomePage.logoutFromBackOffice();
        }
    }
}
