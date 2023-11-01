package com.vertex.quality.connectors.hybris.tests.salesVAT;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.enums.CreditCard;
import com.vertex.quality.common.enums.TaxRate;
import com.vertex.quality.common.utils.properties.CommonDataProperties;
import com.vertex.quality.connectors.hybris.enums.backoffice.HybrisBOBaseStoreTabOptions;
import com.vertex.quality.connectors.hybris.enums.backoffice.HybrisBONavTreeOptions;
import com.vertex.quality.connectors.hybris.enums.backoffice.HybrisBOOriginAddress;
import com.vertex.quality.connectors.hybris.enums.electronics.HybrisProductIds;
import com.vertex.quality.connectors.hybris.enums.electronics.HybrisTaxationDiscountPercentAmount;
import com.vertex.quality.connectors.hybris.pages.backoffice.*;
import com.vertex.quality.connectors.hybris.pages.electronics.*;
import com.vertex.quality.connectors.hybris.tests.base.HybrisBaseTest;
import com.vertex.quality.connectors.oseriesfinal.api.enums.OSeriesFinalData;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

/**
 * Test class to validate sales order for VAT
 *
 * @author Shivam.Soni
 */
public class HybrisSalesVatTests extends HybrisBaseTest {

    HybrisBOHomePage boHomePage;
    HybrisBOBaseStorePage electronicsStorePage;
    HybrisBOVertexCustomizationPage vertexCustomization;
    HybrisBODefaultDeliveryOriginPage deliveryOriginPage;
    HybrisBOVertexConfigurationPage vertexConfigPage;
    HybrisEStorePage storeFront;
    HybrisEStoreCartPage cartPage;
    HybrisEStoreGuestLoginPage eStoreGuestLoginPage;
    HybrisEStoreCheckOutPage checkoutPage;
    HybrisEStoreOrderConfirmationPage orderConfirmPage;

    /**
     * CHYB-209 HYB - Consignment Sales Order Invoice for VAT (DE FR)
     */
    @Test(groups = {"hybris_regression"})
    public void salesVATConsignmentFRToDETest() {
        // login as Backoffice user into Hybris-Backoffice Page
        boHomePage = loginBOUser();

        // navigate to BaseStore - Electronic Store Page
        electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());

        // navigate Default Delivery Origin page and Set new Origin Address
        electronicsStorePage.selectElectronicsStore();
        electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.LOCATIONS.getoption());
        deliveryOriginPage = electronicsStorePage.doubleClickOnDefaultDeliveryOrigin();
        deliveryOriginPage.doubleClickOnOriginAddress();
        deliveryOriginPage.setOriginAddress(HybrisBOOriginAddress.BERLIN_ORANIENSTRASSE.stName, HybrisBOOriginAddress.BERLIN_ORANIENSTRASSE.stNumber, HybrisBOOriginAddress.BERLIN_ORANIENSTRASSE.postalCode,
                HybrisBOOriginAddress.BERLIN_ORANIENSTRASSE.town, HybrisBOOriginAddress.BERLIN_ORANIENSTRASSE.country);

        // save Vertex Configuration & Logout from Back office
        vertexConfigPage = new HybrisBOVertexConfigurationPage(driver);
        vertexConfigPage.saveVertexConfiguration();
        boHomePage.logoutFromBackOffice();

        // launch Electronics store-front page
        storeFront = launchB2CPage();

        // Get Cart Quantity and Remove All Items from Cart
        if (storeFront.getCartQuantity() > 0) {
            cartPage = storeFront.navigateToCart();
            cartPage.removeItemsFromCart();
        }

        // Add Products to Cart and Proceed to Checkout
        storeFront.searchAndAddProductToCart(HybrisProductIds.TMAXP3200ID.getproductID());
        eStoreGuestLoginPage = storeFront.proceedToCheckout();

        // Set Electronics Store Guest Credentials and checkout as Guest
        setGuestEmailAndConfirmEmail(eStoreGuestLoginPage);
        checkoutPage = eStoreGuestLoginPage.checkoutAsGuest();

        // Fill Shipping Address Details and Proceed to Checkout
        checkoutPage.fillShippingAddressDetails(Address.Marseille.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                Address.Marseille.addressLine1, Address.Marseille.city, "", Address.Marseille.zip5);
        checkoutPage.clickDeliveryAddressNext();
        checkoutPage.clickDeliveryMethodNext();

        // Fill Payment card details
        checkoutPage.fillPaymentDetails(CreditCard.TYPE.text, CreditCard.NAME.text, CreditCard.NUMBER.text, CreditCard.EXPIRY_MONTH.text, CreditCard.EXPIRY_YEAR.text, CreditCard.CODE.text);

        // check use delivery address checkbox and proceed to checkout
        checkoutPage.enableUseDeliveryAddress();
        checkoutPage.clickpaymentBillingAddressNext();

        // Enable Terms And Conditions and Place Order
        checkoutPage.enableTermsConditions();
        orderConfirmPage = checkoutPage.placeOrder();

        // Assertion for percent based tax amount
        assertEquals(orderConfirmPage.calculatePercentageBasedTax(HybrisTaxationDiscountPercentAmount.DE_FR_TAX.getPercentOrAmount()), orderConfirmPage.getTax());
    }

    /**
     * CHYB-210 HYB - Create Sale Order for VAT (EU-US) and Invoice
     */
    @Test(groups = {"hybris_regression"})
    public void salesVATOrderEUToUSTest() {
        // login as Backoffice user into Hybris-Backoffice Page
        boHomePage = loginBOUser();

        // navigate to BaseStore - Electronic Store Page
        electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());

        // navigate Default Delivery Origin page and Set new Origin Address
        electronicsStorePage.selectElectronicsStore();
        electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.LOCATIONS.getoption());
        deliveryOriginPage = electronicsStorePage.doubleClickOnDefaultDeliveryOrigin();
        deliveryOriginPage.doubleClickOnOriginAddress();
        deliveryOriginPage.setOriginAddress(HybrisBOOriginAddress.BERLIN_ALLE.stName, HybrisBOOriginAddress.BERLIN_ALLE.stNumber, HybrisBOOriginAddress.BERLIN_ALLE.postalCode,
                HybrisBOOriginAddress.BERLIN_ALLE.town, HybrisBOOriginAddress.BERLIN_ALLE.country);

        // save Vertex Configuration & Logout from Back office
        vertexConfigPage = new HybrisBOVertexConfigurationPage(driver);
        vertexConfigPage.saveVertexConfiguration();
        boHomePage.logoutFromBackOffice();

        // launch Electronics store-front page
        storeFront = launchB2CPage();

        // Get Cart Quantity and Remove All Items from Cart
        if (storeFront.getCartQuantity() > 0) {
            cartPage = storeFront.navigateToCart();
            cartPage.removeItemsFromCart();
        }

        // Add Products to Cart and Proceed to Checkout
        storeFront.searchAndAddProductToCart(HybrisProductIds.TMAXP3200ID.getproductID());
        eStoreGuestLoginPage = storeFront.proceedToCheckout();

        // Set Electronics Store Guest Credentials and checkout as Guest
        setGuestEmailAndConfirmEmail(eStoreGuestLoginPage);
        checkoutPage = eStoreGuestLoginPage.checkoutAsGuest();

        // Fill Shipping Address Details and Proceed to Checkout
        checkoutPage.fillShippingAddressDetails(Address.UniversalCity.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                Address.UniversalCity.addressLine1, Address.UniversalCity.city, Address.UniversalCity.state.fullName, Address.UniversalCity.zip5);
        checkoutPage.clickDeliveryAddressNext();
        checkoutPage.clickDeliveryMethodNext();

        // Fill Payment card details
        checkoutPage.fillPaymentDetails(CreditCard.TYPE.text, CreditCard.NAME.text, CreditCard.NUMBER.text, CreditCard.EXPIRY_MONTH.text, CreditCard.EXPIRY_YEAR.text, CreditCard.CODE.text);

        // check use delivery address checkbox and proceed to checkout
        checkoutPage.enableUseDeliveryAddress();
        checkoutPage.clickpaymentBillingAddressNext();

        // Enable Terms And Conditions and Place Order
        checkoutPage.enableTermsConditions();
        orderConfirmPage = checkoutPage.placeOrder();

        // Assertion for percent based tax amount
        assertEquals(orderConfirmPage.calculatePercentageBasedTax(TaxRate.UniversalCity.tax), orderConfirmPage.getTax());
    }

    /**
     * CHYB-211 HYB - Create Sale Order for VAT (US-EU) and Invoice
     */
    @Test(groups = {"hybris_regression"})
    public void salesVATOrderUSToEUTest() {
        // login as Backoffice user into Hybris-Backoffice Page
        boHomePage = loginBOUser();

        // navigate to BaseStore - Electronic Store Page
        electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());

        // navigate Default Delivery Origin page and Set new Origin Address
        electronicsStorePage.selectElectronicsStore();
        electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.LOCATIONS.getoption());
        deliveryOriginPage = electronicsStorePage.doubleClickOnDefaultDeliveryOrigin();
        deliveryOriginPage.doubleClickOnOriginAddress();
        deliveryOriginPage.setOriginAddress(HybrisBOOriginAddress.GETTYSBURG.stName, HybrisBOOriginAddress.GETTYSBURG.stNumber, HybrisBOOriginAddress.GETTYSBURG.postalCode,
                HybrisBOOriginAddress.GETTYSBURG.town, HybrisBOOriginAddress.GETTYSBURG.country);

        // save Vertex Configuration & Logout from Back office
        vertexConfigPage = new HybrisBOVertexConfigurationPage(driver);
        vertexConfigPage.saveVertexConfiguration();
        boHomePage.logoutFromBackOffice();

        // launch Electronics store-front page
        storeFront = launchB2CPage();

        // Get Cart Quantity and Remove All Items from Cart
        if (storeFront.getCartQuantity() > 0) {
            cartPage = storeFront.navigateToCart();
            cartPage.removeItemsFromCart();
        }

        // Add Products to Cart and Proceed to Checkout
        storeFront.searchAndAddProductToCart(HybrisProductIds.TMAXP3200ID.getproductID());
        eStoreGuestLoginPage = storeFront.proceedToCheckout();

        // Set Electronics Store Guest Credentials and checkout as Guest
        setGuestEmailAndConfirmEmail(eStoreGuestLoginPage);
        checkoutPage = eStoreGuestLoginPage.checkoutAsGuest();

        // Fill Shipping Address Details and Proceed to Checkout
        checkoutPage.fillShippingAddressDetails(Address.Paris.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                Address.Paris.addressLine1, Address.Paris.city, Address.Paris.country.iso2code, Address.Paris.zip5);
        checkoutPage.clickDeliveryAddressNext();
        checkoutPage.clickDeliveryMethodNext();

        // Fill Payment card details
        checkoutPage.fillPaymentDetails(CreditCard.TYPE.text, CreditCard.NAME.text, CreditCard.NUMBER.text, CreditCard.EXPIRY_MONTH.text, CreditCard.EXPIRY_YEAR.text, CreditCard.CODE.text);

        // check use delivery address checkbox and proceed to checkout
        checkoutPage.enableUseDeliveryAddress();
        checkoutPage.clickpaymentBillingAddressNext();

        // Enable Terms And Conditions and Place Order
        checkoutPage.enableTermsConditions();
        orderConfirmPage = checkoutPage.placeOrder();

        // Assertion for percent based tax amount
        assertEquals(orderConfirmPage.calculatePercentageBasedTax(HybrisTaxationDiscountPercentAmount.US_FR_TAX.getPercentOrAmount()), orderConfirmPage.getTax());
    }

    /**
     * CHYB-212 HYB - Create Sale Order for VAT (Intra EU FR-DE) and Invoice
     */
    @Test(groups = {"hybris_regression"})
    public void salesVATOrderEUFRToDETest() {
        // login as Backoffice user into Hybris-Backoffice Page
        boHomePage = loginBOUser();

        // navigate to BaseStore - Electronic Store Page
        electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());

        // navigate Default Delivery Origin page and Set new Origin Address
        electronicsStorePage.selectElectronicsStore();
        electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.LOCATIONS.getoption());
        deliveryOriginPage = electronicsStorePage.doubleClickOnDefaultDeliveryOrigin();
        deliveryOriginPage.doubleClickOnOriginAddress();
        deliveryOriginPage.setOriginAddress(HybrisBOOriginAddress.PARIS.stName, HybrisBOOriginAddress.PARIS.stNumber, HybrisBOOriginAddress.PARIS.postalCode,
                HybrisBOOriginAddress.PARIS.town, HybrisBOOriginAddress.PARIS.country);

        // save Vertex Configuration & Logout from Back office
        vertexConfigPage = new HybrisBOVertexConfigurationPage(driver);
        vertexConfigPage.saveVertexConfiguration();
        boHomePage.logoutFromBackOffice();

        // launch Electronics store-front page
        storeFront = launchB2CPage();

        // Get Cart Quantity and Remove All Items from Cart
        if (storeFront.getCartQuantity() > 0) {
            cartPage = storeFront.navigateToCart();
            cartPage.removeItemsFromCart();
        }

        // Add Products to Cart and Proceed to Checkout
        storeFront.searchAndAddProductToCart(HybrisProductIds.TMAXP3200ID.getproductID());
        eStoreGuestLoginPage = storeFront.proceedToCheckout();

        // Set Electronics Store Guest Credentials and checkout as Guest
        setGuestEmailAndConfirmEmail(eStoreGuestLoginPage);
        checkoutPage = eStoreGuestLoginPage.checkoutAsGuest();

        // Fill Shipping Address Details and Proceed to Checkout
        checkoutPage.fillShippingAddressDetails(Address.Berlin.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                Address.Berlin.addressLine1, Address.Berlin.city, Address.Berlin.country.iso2code, Address.Berlin.zip5);
        checkoutPage.clickDeliveryAddressNext();
        checkoutPage.clickDeliveryMethodNext();

        // Fill Payment card details
        checkoutPage.fillPaymentDetails(CreditCard.TYPE.text, CreditCard.NAME.text, CreditCard.NUMBER.text, CreditCard.EXPIRY_MONTH.text, CreditCard.EXPIRY_YEAR.text, CreditCard.CODE.text);

        // check use delivery address checkbox and proceed to checkout
        checkoutPage.enableUseDeliveryAddress();
        checkoutPage.clickpaymentBillingAddressNext();

        // Enable Terms And Conditions and Place Order
        checkoutPage.enableTermsConditions();
        orderConfirmPage = checkoutPage.placeOrder();

        // Assertion for percent based tax amount
        assertEquals(orderConfirmPage.calculatePercentageBasedTax(HybrisTaxationDiscountPercentAmount.FR_DE_TAX.getPercentOrAmount()), orderConfirmPage.getTax());
    }

    /**
     * CHYB-213 HYB - Create Sale Order for VAT (Greek Territory) and Invoice
     */
    @Test(groups = {"hybris_regression"})
    public void salesVATOrderGreekTerritoryTest() {
        // login as Backoffice user into Hybris-Backoffice Page
        boHomePage = loginBOUser();

        // navigate to BaseStore - Electronic Store Page
        electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());
        electronicsStorePage.selectElectronicsStore();

        // Go to vertex customization
        electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption());
        vertexCustomization = new HybrisBOVertexCustomizationPage(driver);

        // Setting Delivery term
        vertexCustomization.selectVertexDeliveryTerm(OSeriesFinalData.SHIP_TERM_SUP.data);
        electronicsStorePage.saveElectronicsStoreConfiguration();

        // Setting Ship-From address
        electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.LOCATIONS.getoption());
        deliveryOriginPage = electronicsStorePage.doubleClickOnDefaultDeliveryOrigin();
        deliveryOriginPage.doubleClickOnOriginAddress();
        deliveryOriginPage.setOriginAddress(HybrisBOOriginAddress.PARIS.stName, HybrisBOOriginAddress.PARIS.stNumber, HybrisBOOriginAddress.PARIS.postalCode,
                HybrisBOOriginAddress.PARIS.town, HybrisBOOriginAddress.PARIS.country);

        // save Vertex Configuration & Logout from Back office
        vertexConfigPage = new HybrisBOVertexConfigurationPage(driver);
        vertexConfigPage.saveVertexConfiguration();
        boHomePage.logoutFromBackOffice();

        // launch Electronics store-front page
        storeFront = launchB2CPage();

        // Get Cart Quantity and Remove All Items from Cart
        if (storeFront.getCartQuantity() > 0) {
            cartPage = storeFront.navigateToCart();
            cartPage.removeItemsFromCart();
        }

        // Add Products to Cart and Proceed to Checkout
        storeFront.searchAndAddProductToCart(HybrisProductIds.TMAXP3200ID.getproductID());
        eStoreGuestLoginPage = storeFront.proceedToCheckout();

        // Set Electronics Store Guest Credentials and checkout as Guest
        setGuestEmailAndConfirmEmail(eStoreGuestLoginPage);
        checkoutPage = eStoreGuestLoginPage.checkoutAsGuest();

        // Fill Shipping Address Details and Proceed to Checkout
        checkoutPage.fillShippingAddressDetails(Address.Analipsi.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                Address.Analipsi.addressLine1, Address.Analipsi.city, Address.Analipsi.country.iso2code, Address.Analipsi.zip5);
        checkoutPage.clickDeliveryAddressNext();
        checkoutPage.clickDeliveryMethodNext();

        // Fill Payment card details
        checkoutPage.fillPaymentDetails(CreditCard.TYPE.text, CreditCard.NAME.text, CreditCard.NUMBER.text, CreditCard.EXPIRY_MONTH.text, CreditCard.EXPIRY_YEAR.text, CreditCard.CODE.text);

        // check use delivery address checkbox and proceed to checkout
        checkoutPage.enableUseDeliveryAddress();
        checkoutPage.clickpaymentBillingAddressNext();

        // Enable Terms And Conditions and Place Order
        checkoutPage.enableTermsConditions();
        orderConfirmPage = checkoutPage.placeOrder();

        // Assertion for percent based tax amount
        assertEquals(orderConfirmPage.calculatePercentageBasedTax(HybrisTaxationDiscountPercentAmount.GREECE_TAX.getPercentOrAmount()), orderConfirmPage.getTax());
    }

    /**
     * CHYB-214 HYB - Create Sale Order for VAT (Austrian Sub-Division) and Invoice
     */
    @Test(groups = {"hybris_regression"})
    public void salesVATOrderAustrianSubDivisionTest() {
        // login as Backoffice user into Hybris-Backoffice Page
        boHomePage = loginBOUser();

        // navigate to BaseStore - Electronic Store Page
        electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());

        // navigate Default Delivery Origin page and Set new Origin Address
        electronicsStorePage.selectElectronicsStore();
        electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.LOCATIONS.getoption());
        deliveryOriginPage = electronicsStorePage.doubleClickOnDefaultDeliveryOrigin();
        deliveryOriginPage.doubleClickOnOriginAddress();
        deliveryOriginPage.setOriginAddress(HybrisBOOriginAddress.BERLIN_ALLE.stName, HybrisBOOriginAddress.BERLIN_ALLE.stNumber, HybrisBOOriginAddress.BERLIN_ALLE.postalCode,
                HybrisBOOriginAddress.BERLIN_ALLE.town, HybrisBOOriginAddress.BERLIN_ALLE.country);

        // save Vertex Configuration & Logout from Back office
        vertexConfigPage = new HybrisBOVertexConfigurationPage(driver);
        vertexConfigPage.saveVertexConfiguration();
        boHomePage.logoutFromBackOffice();

        // launch Electronics store-front page
        storeFront = launchB2CPage();

        // Get Cart Quantity and Remove All Items from Cart
        if (storeFront.getCartQuantity() > 0) {
            cartPage = storeFront.navigateToCart();
            cartPage.removeItemsFromCart();
        }

        // Add Products to Cart and Proceed to Checkout
        storeFront.searchAndAddProductToCart(HybrisProductIds.TMAXP3200ID.getproductID());
        eStoreGuestLoginPage = storeFront.proceedToCheckout();

        // Set Electronics Store Guest Credentials and checkout as Guest
        setGuestEmailAndConfirmEmail(eStoreGuestLoginPage);
        checkoutPage = eStoreGuestLoginPage.checkoutAsGuest();

        // Fill Shipping Address Details and Proceed to Checkout
        checkoutPage.fillShippingAddressDetails(Address.Mittelberg.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                Address.Mittelberg.addressLine1, Address.Mittelberg.city, Address.Mittelberg.country.iso2code, Address.Mittelberg.zip5);
        checkoutPage.clickDeliveryAddressNext();
        checkoutPage.clickDeliveryMethodNext();

        // Fill Payment card details
        checkoutPage.fillPaymentDetails(CreditCard.TYPE.text, CreditCard.NAME.text, CreditCard.NUMBER.text, CreditCard.EXPIRY_MONTH.text, CreditCard.EXPIRY_YEAR.text, CreditCard.CODE.text);

        // check use delivery address checkbox and proceed to checkout
        checkoutPage.enableUseDeliveryAddress();
        checkoutPage.clickpaymentBillingAddressNext();

        // Enable Terms And Conditions and Place Order
        checkoutPage.enableTermsConditions();
        orderConfirmPage = checkoutPage.placeOrder();

        // Assertion for percent based tax amount
        assertEquals(orderConfirmPage.calculatePercentageBasedTax(HybrisTaxationDiscountPercentAmount.DE_AT_TAX.getPercentOrAmount()), orderConfirmPage.getTax());
    }

    /**
     * CHYB-215 HYB - Create Sale Order with currency conversion for VAT (Intra EU FR-DE) and Invoice
     */
    @Test(groups = {"hybris_regression"})
    public void salesVATOrderEUFRtoDETest() {
        // login as Backoffice user into Hybris-Backoffice Page
        boHomePage = loginBOUser();

        // navigate to BaseStore - Electronic Store Page
        electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());

        // navigate Default Delivery Origin page and Set new Origin Address
        electronicsStorePage.selectElectronicsStore();
        electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.LOCATIONS.getoption());
        deliveryOriginPage = electronicsStorePage.doubleClickOnDefaultDeliveryOrigin();
        deliveryOriginPage.doubleClickOnOriginAddress();
        deliveryOriginPage.setOriginAddress(HybrisBOOriginAddress.PARIS.stName, HybrisBOOriginAddress.PARIS.stNumber, HybrisBOOriginAddress.PARIS.postalCode,
                HybrisBOOriginAddress.PARIS.town, HybrisBOOriginAddress.PARIS.country);

        // save Vertex Configuration & Logout from Back office
        vertexConfigPage = new HybrisBOVertexConfigurationPage(driver);
        vertexConfigPage.saveVertexConfiguration();
        boHomePage.logoutFromBackOffice();

        // launch Electronics store-front page
        storeFront = launchB2CPage();

        // Get Cart Quantity and Remove All Items from Cart
        if (storeFront.getCartQuantity() > 0) {
            cartPage = storeFront.navigateToCart();
            cartPage.removeItemsFromCart();
        }

        // Add Products to Cart and Proceed to Checkout
        storeFront.searchAndAddProductToCart(HybrisProductIds.TMAXP3200ID.getproductID());
        eStoreGuestLoginPage = storeFront.proceedToCheckout();

        // Set Electronics Store Guest Credentials and checkout as Guest
        setGuestEmailAndConfirmEmail(eStoreGuestLoginPage);
        checkoutPage = eStoreGuestLoginPage.checkoutAsGuest();

        // Fill Shipping Address Details and Proceed to Checkout
        checkoutPage.fillShippingAddressDetails(Address.Berlin.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                Address.Berlin.addressLine1, Address.Berlin.city, Address.Berlin.country.iso2code, Address.Berlin.zip5);
        checkoutPage.clickDeliveryAddressNext();
        checkoutPage.clickDeliveryMethodNext();

        // Fill Payment card details
        checkoutPage.fillPaymentDetails(CreditCard.TYPE.text, CreditCard.NAME.text, CreditCard.NUMBER.text, CreditCard.EXPIRY_MONTH.text, CreditCard.EXPIRY_YEAR.text, CreditCard.CODE.text);

        // check use delivery address checkbox and proceed to checkout
        checkoutPage.enableUseDeliveryAddress();
        checkoutPage.clickpaymentBillingAddressNext();

        // Enable Terms And Conditions and Place Order
        checkoutPage.enableTermsConditions();
        orderConfirmPage = checkoutPage.placeOrder();

        // Assertion for percent based tax amount
        assertEquals(orderConfirmPage.calculatePercentageBasedTax(HybrisTaxationDiscountPercentAmount.FR_DE_TAX.getPercentOrAmount()), orderConfirmPage.getTax());
    }
}
