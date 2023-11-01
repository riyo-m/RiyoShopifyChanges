package com.vertex.quality.connectors.hybris.tests.invoice;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.enums.CreditCard;
import com.vertex.quality.common.enums.TaxRate;
import com.vertex.quality.common.utils.properties.CommonDataProperties;
import com.vertex.quality.connectors.hybris.enums.backoffice.HybrisBOBaseStoreTabOptions;
import com.vertex.quality.connectors.hybris.enums.backoffice.HybrisBONavTreeOptions;
import com.vertex.quality.connectors.hybris.enums.backoffice.HybrisBOOriginAddress;
import com.vertex.quality.connectors.hybris.enums.electronics.HybrisProductIds;
import com.vertex.quality.connectors.hybris.enums.electronics.HybrisProductQuantities;
import com.vertex.quality.connectors.hybris.enums.electronics.HybrisTaxationDiscountPercentAmount;
import com.vertex.quality.connectors.hybris.pages.backoffice.HybrisBOBaseStorePage;
import com.vertex.quality.connectors.hybris.pages.backoffice.HybrisBODefaultDeliveryOriginPage;
import com.vertex.quality.connectors.hybris.pages.backoffice.HybrisBOHomePage;
import com.vertex.quality.connectors.hybris.pages.backoffice.HybrisBOVertexConfigurationPage;
import com.vertex.quality.connectors.hybris.pages.electronics.*;
import com.vertex.quality.connectors.hybris.tests.base.HybrisBaseTest;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

/**
 * Hybris create invoice test-cases
 *
 * @author Shivam.Soni
 */
public class HybrisInvoiceRequestTests extends HybrisBaseTest {

    HybrisBOHomePage boHomePage;
    HybrisBOBaseStorePage electronicsStorePage;
    HybrisBODefaultDeliveryOriginPage deliveryOriginPage;
    HybrisBOVertexConfigurationPage vertexConfigPage;
    HybrisEStorePage storeFront;
    HybrisEStoreCartPage cartPage;
    HybrisEStoreGuestLoginPage eStoreGuestLoginPage;
    HybrisEStoreCheckOutPage checkoutPage;
    HybrisEStoreOrderConfirmationPage orderConfirmPage;

    /**
     * CHYB-197 HYB - Create Sales Order with Invoice
     */
    @Test(groups = {"hybris_regression"})
    public void salesInvoiceOnlyTest() {
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
        checkoutPage.fillShippingAddressDetails(Address.LosAngeles.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                Address.LosAngeles.addressLine1, Address.LosAngeles.city, Address.LosAngeles.state.fullName, Address.LosAngeles.zip5);
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
        assertEquals(orderConfirmPage.calculatePercentageBasedTax(TaxRate.LosAngeles.tax), orderConfirmPage.getTax());
    }

    /**
     * CHYB-196 HYB - Create Invoice with quantity 10
     */
    @Test(groups = {"hybris_regression"})
    public void salesInvoiceBulkQuantitiesTest() {
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
        storeFront.searchAndSelectProduct(HybrisProductIds.TMAXP3200ID.getproductID());
        storeFront.updateProductQuantity(HybrisProductQuantities.QUANTITIES_TEN.getQuantity());
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
     * CHYB-200 HYB - Create Sales Order with Invoice US to CAN
     */
    @Test(groups = {"hybris_regression"})
    public void salesInvoiceUSToCANTest() {
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
        checkoutPage.fillShippingAddressDetails(Address.Victoria.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                Address.Victoria.addressLine1, Address.Victoria.city, Address.Victoria.province.fullName, Address.Victoria.zip5);
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
        assertEquals(orderConfirmPage.calculatePercentageBasedTax(HybrisTaxationDiscountPercentAmount.US_CANBC_TAX.getPercentOrAmount()), orderConfirmPage.getTax());
    }

    /**
     * CHYB-201 HYB - Create Sales Order with Invoice CAN to US
     */
    @Test(groups = {"hybris_regression"})
    public void salesInvoiceCANToUSTest() {
        // login as Backoffice user into Hybris-Backoffice Page
        boHomePage = loginBOUser();

        // navigate to BaseStore - Electronic Store Page
        electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());

        // navigate Default Delivery Origin page and Set new Origin Address
        electronicsStorePage.selectElectronicsStore();
        electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.LOCATIONS.getoption());
        deliveryOriginPage = electronicsStorePage.doubleClickOnDefaultDeliveryOrigin();
        deliveryOriginPage.doubleClickOnOriginAddress();
        deliveryOriginPage.setOriginAddress(HybrisBOOriginAddress.QUEBEC.stName, HybrisBOOriginAddress.QUEBEC.stNumber, HybrisBOOriginAddress.QUEBEC.postalCode,
                HybrisBOOriginAddress.QUEBEC.town, HybrisBOOriginAddress.QUEBEC.country);

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
        checkoutPage.fillShippingAddressDetails(Address.Gettysburg.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                Address.Gettysburg.addressLine1, Address.Gettysburg.city, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5);
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
     * CHYB-202 HYB - Create Sales Order with Invoice CANNB to CANNB same Province (HST)
     */
    @Test(groups = {"hybris_regression"})
    public void salesInvoiceCANNBToCANNBTest() {
        // login as Backoffice user into Hybris-Backoffice Page
        boHomePage = loginBOUser();

        // navigate to BaseStore - Electronic Store Page
        electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());

        // navigate Default Delivery Origin page and Set new Origin Address
        electronicsStorePage.selectElectronicsStore();
        electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.LOCATIONS.getoption());
        deliveryOriginPage = electronicsStorePage.doubleClickOnDefaultDeliveryOrigin();
        deliveryOriginPage.doubleClickOnOriginAddress();
        deliveryOriginPage.setOriginAddress(HybrisBOOriginAddress.GRAND_MANAN.stName, HybrisBOOriginAddress.GRAND_MANAN.stNumber, HybrisBOOriginAddress.GRAND_MANAN.postalCode,
                HybrisBOOriginAddress.GRAND_MANAN.town, HybrisBOOriginAddress.GRAND_MANAN.country);

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
        checkoutPage.fillShippingAddressDetails(Address.Quispamsis.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                Address.Quispamsis.addressLine1, Address.Quispamsis.city, Address.Quispamsis.province.fullName, Address.Quispamsis.zip5);
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
        assertEquals(orderConfirmPage.calculatePercentageBasedTax(HybrisTaxationDiscountPercentAmount.CANNB_CANNB_TAX.getPercentOrAmount()), orderConfirmPage.getTax());
    }

    /**
     * CHYB-203 HYB - Create Sales Order with Invoice CANQC to CANBC different Province (GST/PST)
     */
    @Test(groups = {"hybris_regression"})
    public void salesInvoiceCANQCToCANBCTest() {
        // login as Backoffice user into Hybris-Backoffice Page
        boHomePage = loginBOUser();

        // navigate to BaseStore - Electronic Store Page
        electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());

        // navigate Default Delivery Origin page and Set new Origin Address
        electronicsStorePage.selectElectronicsStore();
        electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.LOCATIONS.getoption());
        deliveryOriginPage = electronicsStorePage.doubleClickOnDefaultDeliveryOrigin();
        deliveryOriginPage.doubleClickOnOriginAddress();
        deliveryOriginPage.setOriginAddress(HybrisBOOriginAddress.QUEBEC.stName, HybrisBOOriginAddress.QUEBEC.stNumber, HybrisBOOriginAddress.QUEBEC.postalCode,
                HybrisBOOriginAddress.QUEBEC.town, HybrisBOOriginAddress.QUEBEC.country);

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
        checkoutPage.fillShippingAddressDetails(Address.Victoria.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                Address.Victoria.addressLine1, Address.Victoria.city, Address.Victoria.province.fullName, Address.Victoria.zip5);
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
        assertEquals(orderConfirmPage.calculatePercentageBasedTax(HybrisTaxationDiscountPercentAmount.CANQC_CANBC_TAX.getPercentOrAmount()), orderConfirmPage.getTax());
    }

    /**
     * CHYB-204 HYB - Create Sales Order with Invoice CANBC to CANON different Province (GST/HST)
     */
    @Test(groups = {"hybris_regression"})
    public void salesInvoiceCANBCToCANONTest() {
        // login as Backoffice user into Hybris-Backoffice Page
        boHomePage = loginBOUser();

        // navigate to BaseStore - Electronic Store Page
        electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());

        // navigate Default Delivery Origin page and Set new Origin Address
        electronicsStorePage.selectElectronicsStore();
        electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.LOCATIONS.getoption());
        deliveryOriginPage = electronicsStorePage.doubleClickOnDefaultDeliveryOrigin();
        deliveryOriginPage.doubleClickOnOriginAddress();
        deliveryOriginPage.setOriginAddress(HybrisBOOriginAddress.VICTORIA.stName, HybrisBOOriginAddress.VICTORIA.stNumber, HybrisBOOriginAddress.VICTORIA.postalCode,
                HybrisBOOriginAddress.VICTORIA.town, HybrisBOOriginAddress.VICTORIA.country);

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
        checkoutPage.fillShippingAddressDetails(Address.Ottawa.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                Address.Ottawa.addressLine1, Address.Ottawa.city, Address.Ottawa.province.fullName, Address.Ottawa.zip5);
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
        assertEquals(orderConfirmPage.calculatePercentageBasedTax(HybrisTaxationDiscountPercentAmount.CANBC_CANON_TAX.getPercentOrAmount()), orderConfirmPage.getTax());
    }

    /**
     * CHYB-205 HYB - Create Sales Order with Invoice CANBC to CANQC different Province (GST/QST)
     */
    @Test(groups = {"hybris_regression"})
    public void salesInvoiceCANBCToCANQCTest() {
        // login as Backoffice user into Hybris-Backoffice Page
        boHomePage = loginBOUser();

        // navigate to BaseStore - Electronic Store Page
        electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());

        // navigate Default Delivery Origin page and Set new Origin Address
        electronicsStorePage.selectElectronicsStore();
        electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.LOCATIONS.getoption());
        deliveryOriginPage = electronicsStorePage.doubleClickOnDefaultDeliveryOrigin();
        deliveryOriginPage.doubleClickOnOriginAddress();
        deliveryOriginPage.setOriginAddress(HybrisBOOriginAddress.VICTORIA.stName, HybrisBOOriginAddress.VICTORIA.stNumber, HybrisBOOriginAddress.VICTORIA.postalCode,
                HybrisBOOriginAddress.VICTORIA.town, HybrisBOOriginAddress.VICTORIA.country);

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
        checkoutPage.fillShippingAddressDetails(Address.QuebecCity.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                Address.QuebecCity.addressLine1, Address.QuebecCity.city, Address.QuebecCity.province.fullName, Address.QuebecCity.zip5);
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
        assertEquals(orderConfirmPage.calculatePercentageBasedTax(HybrisTaxationDiscountPercentAmount.CANBC_CANQC_TAX.getPercentOrAmount()), orderConfirmPage.getTax());
    }

    /**
     * CHYB-198 HYB - Create Sales Order different ship and bill
     */
    @Test(groups = {"hybris_regression"})
    public void salesInvoiceDifferentShipAndBillTest() {
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
        checkoutPage.fillShippingAddressDetails(Address.LosAngeles.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                Address.LosAngeles.addressLine1, Address.LosAngeles.city, Address.LosAngeles.state.fullName, Address.LosAngeles.zip5);
        checkoutPage.clickDeliveryAddressNext();
        checkoutPage.clickDeliveryMethodNext();

        // Fill Payment card details
        checkoutPage.fillPaymentDetails(CreditCard.TYPE.text, CreditCard.NAME.text, CreditCard.NUMBER.text, CreditCard.EXPIRY_MONTH.text, CreditCard.EXPIRY_YEAR.text, CreditCard.CODE.text);

        // uncheck use delivery address checkbox
        checkoutPage.uncheckUseDeliveryAddress();

        // Fill Billing Address Details and proceed to checkout
        checkoutPage.fillShippingAddressDetails(Address.Delaware.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                Address.Delaware.addressLine1, Address.Delaware.city, Address.Delaware.state.fullName, Address.Delaware.zip5);
        checkoutPage.clickpaymentBillingAddressNext();

        // Enable Terms And Conditions and Place Order
        checkoutPage.enableTermsConditions();
        orderConfirmPage = checkoutPage.placeOrder();

        // Assertion for percent based tax amount
        assertEquals(orderConfirmPage.calculatePercentageBasedTax(TaxRate.LosAngeles.tax), orderConfirmPage.getTax());
    }

    /**
     * CHYB-195 HYB - End-to-End Test Create Sales Order and add/delete lines and change quantity and location
     */
    @Test(groups = {"hybris_regression"})
    public void salesOrderChangeLocationUpdateQuantitiesEndToEndTest() {
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
        storeFront.searchAndAddProductToCart(HybrisProductIds.POWERSHOTID.getproductID());
        eStoreGuestLoginPage = storeFront.proceedToCheckout();

        // Set Electronics Store Guest Credentials and checkout as Guest
        setGuestEmailAndConfirmEmail(eStoreGuestLoginPage);
        checkoutPage = eStoreGuestLoginPage.checkoutAsGuest();

        // Fill Shipping Address Details and Proceed to Checkout
        checkoutPage.fillShippingAddressDetails(Address.LosAngeles.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                Address.LosAngeles.addressLine1, Address.LosAngeles.city, Address.LosAngeles.state.fullName, Address.LosAngeles.zip5);
        checkoutPage.clickDeliveryAddressNext();
        checkoutPage.clickDeliveryMethodNext();

        // Assertion for percent based tax amount
        assertEquals(checkoutPage.calculatePercentageBasedTax(TaxRate.LosAngeles.tax), checkoutPage.getTaxAmount());

        // Add Products to Cart and Proceed to Checkout
        storeFront.searchAndSelectProduct(HybrisProductIds.TMAXP3200ID.getproductID());
        storeFront.updateProductQuantity(HybrisProductQuantities.QUANTITIES_FIVE.getQuantity());
        storeFront.addProductToCart();
        eStoreGuestLoginPage = storeFront.proceedToCheckout();

        // Set Electronics Store Guest Credentials and checkout as Guest
        setGuestEmailAndConfirmEmail(eStoreGuestLoginPage);
        checkoutPage = eStoreGuestLoginPage.checkoutAsGuest();

        // Fill Shipping Address Details and Proceed to Checkout
        checkoutPage.fillShippingAddressDetails(Address.Louisiana.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                Address.Louisiana.addressLine1, Address.Louisiana.city, Address.Louisiana.state.fullName, Address.Louisiana.zip5);
        checkoutPage.clickDeliveryAddressNext();
        checkoutPage.clickDeliveryMethodNext();

        // Assertion for percent based tax amount
        assertEquals(checkoutPage.calculatePercentageBasedTax(HybrisTaxationDiscountPercentAmount.PA_LA_TAX.getPercentOrAmount()), checkoutPage.getTaxAmount());

        // Add Products to Cart and Proceed to Checkout
        storeFront.searchAndAddProductToCart(HybrisProductIds.MONOPODID.getproductID());
        eStoreGuestLoginPage = storeFront.proceedToCheckout();

        // Set Electronics Store Guest Credentials and checkout as Guest
        setGuestEmailAndConfirmEmail(eStoreGuestLoginPage);
        checkoutPage = eStoreGuestLoginPage.checkoutAsGuest();

        // Fill Shipping Address Details and Proceed to Checkout
        checkoutPage.fillShippingAddressDetails(Address.ColoradoSprings.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                Address.ColoradoSprings.addressLine1, Address.ColoradoSprings.city, Address.ColoradoSprings.state.fullName, Address.ColoradoSprings.zip5);
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
        double expectedTax = orderConfirmPage.calculatePercentageBasedTax(HybrisTaxationDiscountPercentAmount.PA_CO_TAX.getPercentOrAmount())
                + HybrisTaxationDiscountPercentAmount.CO_RDF_FLAT.getPercentOrAmount();
        assertEquals("If failed then please check CO RDF mapping in the configured O Series instance",
                Double.parseDouble(String.format("%.2f", expectedTax)), Double.parseDouble(String.format("%.2f", orderConfirmPage.getTax())));
    }

    /**
     * CHYB-194 HYB - Create Sale Order where the Customer is registered in O Series and the Financial System
     */
    @Test(groups = {"hybris_regression"})
    public void salesOrderCustomerNotRegisteredTest() {
        // login as Backoffice user into Hybris-Backoffice Page
        boHomePage = loginBOUser();

        // navigate to BaseStore - Electronic Store Page
        electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());

        // navigate Default Delivery Origin page and Set new Origin Address
        electronicsStorePage.selectElectronicsStore();
        electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.LOCATIONS.getoption());
        deliveryOriginPage = electronicsStorePage.doubleClickOnDefaultDeliveryOrigin();
        deliveryOriginPage.doubleClickOnOriginAddress();
        deliveryOriginPage.setOriginAddress(HybrisBOOriginAddress.BERWYN.stName, HybrisBOOriginAddress.BERWYN.stNumber, HybrisBOOriginAddress.BERWYN.postalCode,
                HybrisBOOriginAddress.BERWYN.town, HybrisBOOriginAddress.BERWYN.country);

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
        storeFront.searchAndAddProductToCart(HybrisProductIds.POWERSHOTID.getproductID());
        eStoreGuestLoginPage = storeFront.proceedToCheckout();

        // Set Electronics Store Guest Credentials and checkout as Guest
        setGuestEmailAndConfirmEmail(eStoreGuestLoginPage);
        checkoutPage = eStoreGuestLoginPage.checkoutAsGuest();

        // Fill Shipping Address Details and Proceed to Checkout
        checkoutPage.fillShippingAddressDetails(Address.SaltLakeCity.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                Address.SaltLakeCity.addressLine1, Address.SaltLakeCity.city, Address.SaltLakeCity.state.fullName, Address.SaltLakeCity.zip5);

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
        assertEquals(orderConfirmPage.calculatePercentageBasedTax(true, false, HybrisTaxationDiscountPercentAmount.PA_UT_TAX.getPercentOrAmount()), orderConfirmPage.getTax());
    }

    /**
     * CHYB-199 HYB - Create Sales Order with taxpayer not registered in O Series or the Financial System
     */
    @Test(groups = {"hybris_regression"})
    public void salesOrderTaxPayerNotRegisteredTest() {
        // login as Backoffice user into Hybris-Backoffice Page
        boHomePage = loginBOUser();

        // navigate to BaseStore - Electronic Store Page
        electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());

        // navigate Default Delivery Origin page and Set new Origin Address
        electronicsStorePage.selectElectronicsStore();
        electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.LOCATIONS.getoption());
        deliveryOriginPage = electronicsStorePage.doubleClickOnDefaultDeliveryOrigin();
        deliveryOriginPage.doubleClickOnOriginAddress();
        deliveryOriginPage.setOriginAddress(HybrisBOOriginAddress.BERWYN.stName, HybrisBOOriginAddress.BERWYN.stNumber, HybrisBOOriginAddress.BERWYN.postalCode,
                HybrisBOOriginAddress.BERWYN.town, HybrisBOOriginAddress.BERWYN.country);

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
        storeFront.searchAndAddProductToCart(HybrisProductIds.POWERSHOTID.getproductID());
        eStoreGuestLoginPage = storeFront.proceedToCheckout();

        // Set Electronics Store Guest Credentials and checkout as Guest
        setGuestEmailAndConfirmEmail(eStoreGuestLoginPage);
        checkoutPage = eStoreGuestLoginPage.checkoutAsGuest();

        // Fill Shipping Address Details and Proceed to Checkout
        checkoutPage.fillShippingAddressDetails(Address.Wisconsin.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                Address.Wisconsin.addressLine1, Address.Wisconsin.city, Address.Wisconsin.state.fullName, Address.Wisconsin.zip5);

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
        assertEquals(orderConfirmPage.calculatePercentageBasedTax(HybrisTaxationDiscountPercentAmount.PA_WI_TAX.getPercentOrAmount()), orderConfirmPage.getTax());
    }
}