package com.vertex.quality.connectors.hybris.tests.shippingIncoterms;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.enums.CreditCard;
import com.vertex.quality.common.utils.properties.CommonDataProperties;
import com.vertex.quality.connectors.hybris.enums.backoffice.HybrisBOBaseStoreTabOptions;
import com.vertex.quality.connectors.hybris.enums.backoffice.HybrisBONavTreeOptions;
import com.vertex.quality.connectors.hybris.enums.backoffice.HybrisBOOriginAddress;
import com.vertex.quality.connectors.hybris.enums.electronics.HybrisProductIds;
import com.vertex.quality.connectors.hybris.enums.electronics.HybrisProductQuantities;
import com.vertex.quality.connectors.hybris.enums.electronics.HybrisTaxationDiscountPercentAmount;
import com.vertex.quality.connectors.hybris.pages.backoffice.*;
import com.vertex.quality.connectors.hybris.pages.electronics.*;
import com.vertex.quality.connectors.hybris.tests.base.HybrisBaseTest;
import com.vertex.quality.connectors.oseriesfinal.api.enums.OSeriesFinalData;
import com.vertex.quality.connectors.oseriesfinal.ui.pages.taxpayers.OSeriesLoginPage;
import com.vertex.quality.connectors.oseriesfinal.ui.pages.taxpayers.OSeriesTaxpayers;
import org.testng.annotations.Test;

import static org.testng.Assert.fail;
import static org.testng.AssertJUnit.assertEquals;

/**
 * Test class to validate sales order for VAT by applying Shipping Terms / Incoterms.
 *
 * @author Shivam.Soni
 */
public class HybrisShippingTermIncotermsTests extends HybrisBaseTest {

    OSeriesLoginPage oSeriesLogin;
    OSeriesTaxpayers oSeriesTax;
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
     * CHYB-216 HYB - Create Sales Invoice with Shipping - change shipping address
     */
    @Test(groups = {"hybris_regression"})
    public void salesOrderWithShippingChangeLocationTest() {
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
        storeFront.searchAndAddProductToCart(HybrisProductIds.MONOPODID.getproductID());
        eStoreGuestLoginPage = storeFront.proceedToCheckout();

        // Set Electronics Store Guest Credentials and checkout as Guest
        setGuestEmailAndConfirmEmail(eStoreGuestLoginPage);
        checkoutPage = eStoreGuestLoginPage.checkoutAsGuest();

        // Fill Shipping Address Details and Proceed to Checkout
        checkoutPage.fillShippingAddressDetails(Address.Berwyn.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                Address.Berwyn.addressLine1, Address.Berwyn.city, Address.Berwyn.state.fullName, Address.Berwyn.zip5);
        checkoutPage.clickDeliveryAddressNext();
        checkoutPage.clickDeliveryMethodNext();

        // Assertion for percent based tax amount
        assertEquals(checkoutPage.calculatePercentageBasedTax(HybrisTaxationDiscountPercentAmount.PA_TAX.getPercentOrAmount()), checkoutPage.getTaxAmount());

        // Add Products to Cart and Proceed to Checkout
        storeFront.searchAndSelectProduct(HybrisProductIds.TMAXP3200ID.getproductID());
        storeFront.updateProductQuantity(HybrisProductQuantities.QUANTITY_ONE.getQuantity());
        storeFront.addProductToCart();
        eStoreGuestLoginPage = storeFront.proceedToCheckout();

        // Set Electronics Store Guest Credentials and checkout as Guest
        setGuestEmailAndConfirmEmail(eStoreGuestLoginPage);
        checkoutPage = eStoreGuestLoginPage.checkoutAsGuest();

        // Fill Shipping Address Details and Proceed to Checkout
        checkoutPage.fillShippingAddressDetails(Address.Washington.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                Address.Washington.addressLine1, Address.Washington.city, Address.Washington.state.fullName, Address.Washington.zip5);
        checkoutPage.clickDeliveryAddressNext();
        checkoutPage.clickDeliveryMethodNext();

        // Assertion for percent based tax amount
        assertEquals(checkoutPage.calculatePercentageBasedTax(HybrisTaxationDiscountPercentAmount.PA_WA_TAX.getPercentOrAmount()), checkoutPage.getTaxAmount());

        // Fill Payment card details
        checkoutPage.fillPaymentDetails(CreditCard.TYPE.text, CreditCard.NAME.text, CreditCard.NUMBER.text, CreditCard.EXPIRY_MONTH.text, CreditCard.EXPIRY_YEAR.text, CreditCard.CODE.text);

        // check use delivery address checkbox and proceed to checkout
        checkoutPage.enableUseDeliveryAddress();
        checkoutPage.clickpaymentBillingAddressNext();

        // Enable Terms And Conditions and Place Order
        checkoutPage.enableTermsConditions();
        orderConfirmPage = checkoutPage.placeOrder();

        // Assertion for percent based tax amount
        assertEquals(orderConfirmPage.calculatePercentageBasedTax(HybrisTaxationDiscountPercentAmount.PA_WA_TAX.getPercentOrAmount()), orderConfirmPage.getTax());
    }

    /**
     * CHYB-217 HYB - Create Sales Order with shipping
     */
    @Test(groups = {"hybris_regression"})
    public void salesOrderWithShippingTest() {
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
        checkoutPage.fillShippingAddressDetails(Address.Berwyn.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                Address.Berwyn.addressLine1, Address.Berwyn.city, Address.Berwyn.state.fullName, Address.Berwyn.zip5);
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
        assertEquals(orderConfirmPage.calculatePercentageBasedTax(HybrisTaxationDiscountPercentAmount.PA_TAX.getPercentOrAmount()), orderConfirmPage.getTax());
    }

    /**
     * CHYB-218 HYB - Create Sale Order for VAT with Shipping Terms CUS and Invoice
     */
    @Test(groups = {"hybris_regression"})
    public void salesOrderShippingTermCUSTest() {
        try {
            // login as Backoffice user into Hybris-Backoffice Page
            boHomePage = loginBOUser();

            // navigate to BaseStore - Electronic Store Page
            electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());
            electronicsStorePage.selectElectronicsStore();

            // Go to vertex customization
            electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption());
            vertexCustomization = new HybrisBOVertexCustomizationPage(driver);

            // Setting Delivery term
            vertexCustomization.selectVertexDeliveryTerm(OSeriesFinalData.SHIP_TERM_CUS.data);
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
            checkoutPage.fillShippingAddressDetails(Address.Berlin.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                    Address.Berlin.addressLine1, Address.Berlin.city, "", Address.Berlin.zip5);
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

            // Go to vertex customization
            electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption());
            vertexCustomization = new HybrisBOVertexCustomizationPage(driver);

            // Setting default delivery term
            vertexCustomization.selectVertexDeliveryTerm(OSeriesFinalData.SHIP_TERM_SUP.data);
            electronicsStorePage.saveElectronicsStoreConfiguration();
            boHomePage.logoutFromBackOffice();
        }
    }

    /**
     * CHYB-219 HYB - Create Sale Order for VAT with Shipping Terms SUP and Invoice
     */
    @Test(groups = {"hybris_regression"})
    public void salesOrderShippingTermSUPTest() {
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
        checkoutPage.fillShippingAddressDetails(Address.Berlin.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                Address.Berlin.addressLine1, Address.Berlin.city, "", Address.Berlin.zip5);
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
        assertEquals(orderConfirmPage.calculatePercentageBasedTax(HybrisTaxationDiscountPercentAmount.FR_DE_SUP_TAX.getPercentOrAmount()), orderConfirmPage.getTax());
    }

    /**
     * CHYB-220 HYB - Create Sale Order for VAT with Shipping Terms EXW and Invoice
     */
    @Test(groups = {"hybris_regression"})
    public void salesOrderShippingTermEXWTest() {
        try {
            // login as Backoffice user into Hybris-Backoffice Page
            boHomePage = loginBOUser();

            // navigate to BaseStore - Electronic Store Page
            electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());
            electronicsStorePage.selectElectronicsStore();

            // Go to vertex customization
            electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption());
            vertexCustomization = new HybrisBOVertexCustomizationPage(driver);

            // Setting Delivery term
            vertexCustomization.selectVertexDeliveryTerm(OSeriesFinalData.SHIP_TERM_EXW.data);
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
            checkoutPage.fillShippingAddressDetails(Address.Berlin.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                    Address.Berlin.addressLine1, Address.Berlin.city, "", Address.Berlin.zip5);
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

            // Go to vertex customization
            electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption());
            vertexCustomization = new HybrisBOVertexCustomizationPage(driver);

            // Setting default Delivery term
            vertexCustomization.selectVertexDeliveryTerm(OSeriesFinalData.SHIP_TERM_SUP.data);
            electronicsStorePage.saveElectronicsStoreConfiguration();
            boHomePage.logoutFromBackOffice();
        }
    }

    /**
     * CHYB-221 HYB - Create Sale Order for VAT with Shipping Terms DDP and Invoice
     */
    @Test(groups = {"hybris_regression"})
    public void salesOrderShippingTermDDPTest() {
        try {
            // login as Backoffice user into Hybris-Backoffice Page
            boHomePage = loginBOUser();

            // navigate to BaseStore - Electronic Store Page
            electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());
            electronicsStorePage.selectElectronicsStore();

            // Go to vertex customization
            electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption());
            vertexCustomization = new HybrisBOVertexCustomizationPage(driver);

            // Setting Delivery term
            vertexCustomization.selectVertexDeliveryTerm(OSeriesFinalData.SHIP_TERM_DDP.data);
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
            checkoutPage.fillShippingAddressDetails(Address.Berlin.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                    Address.Berlin.addressLine1, Address.Berlin.city, "", Address.Berlin.zip5);
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
            assertEquals(orderConfirmPage.calculatePercentageBasedTax(HybrisTaxationDiscountPercentAmount.FR_DE_DDP_TAX.getPercentOrAmount()), orderConfirmPage.getTax());
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

            // Go to vertex customization
            electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption());
            vertexCustomization = new HybrisBOVertexCustomizationPage(driver);

            // Setting default Delivery term
            vertexCustomization.selectVertexDeliveryTerm(OSeriesFinalData.SHIP_TERM_SUP.data);
            electronicsStorePage.saveElectronicsStoreConfiguration();
            boHomePage.logoutFromBackOffice();
        }
    }

    /**
     * CHYB-222 HYB - Create Sale Order with Shipping DDP for VAT (Intra EU DE-DE) and Invoice
     */
    @Test(groups = {"hybris_regression"})
    public void salesOrderShippingTermDDPForDEtoDETest() {
        try {
            // login as Backoffice user into Hybris-Backoffice Page
            boHomePage = loginBOUser();

            // navigate to BaseStore - Electronic Store Page
            electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());
            electronicsStorePage.selectElectronicsStore();

            // Go to vertex customization
            electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption());
            vertexCustomization = new HybrisBOVertexCustomizationPage(driver);

            // Setting Delivery term
            vertexCustomization.selectVertexDeliveryTerm(OSeriesFinalData.SHIP_TERM_DDP.data);
            electronicsStorePage.saveElectronicsStoreConfiguration();

            // Setting Ship-From address
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
            checkoutPage.fillShippingAddressDetails(Address.BerlinAlternate.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                    Address.BerlinAlternate.addressLine1, Address.BerlinAlternate.city, "", Address.BerlinAlternate.zip5);
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
            assertEquals(orderConfirmPage.calculatePercentageBasedTax(HybrisTaxationDiscountPercentAmount.FR_DE_DDP_TAX.getPercentOrAmount()), orderConfirmPage.getTax());
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

            // Go to vertex customization
            electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption());
            vertexCustomization = new HybrisBOVertexCustomizationPage(driver);

            // Setting default Delivery term
            vertexCustomization.selectVertexDeliveryTerm(OSeriesFinalData.SHIP_TERM_DDP.data);
            electronicsStorePage.saveElectronicsStoreConfiguration();
            boHomePage.logoutFromBackOffice();
        }
    }
}
