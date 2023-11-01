package com.vertex.quality.connectors.hybris.tests.basicOrder;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.enums.CreditCard;
import com.vertex.quality.common.enums.TaxRate;
import com.vertex.quality.common.utils.properties.CommonDataProperties;
import com.vertex.quality.connectors.hybris.enums.backoffice.HybrisBOBaseStoreTabOptions;
import com.vertex.quality.connectors.hybris.enums.backoffice.HybrisBONavTreeOptions;
import com.vertex.quality.connectors.hybris.enums.backoffice.HybrisBOOriginAddress;
import com.vertex.quality.connectors.hybris.enums.electronics.HybrisProductIds;
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
 * Test class to validate basic sales orders
 *
 * @author Shivam.Soni
 */
public class HybrisBasicSalesOrderTests extends HybrisBaseTest {

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
     * CHYB-206 HYB - Create Sales Order with no State Tax
     */
    @Test(groups = {"hybris_regression"})
    public void salesOrderNoTaxTest() {
        // login as Backoffice user into Hybris-Backoffice Page
        boHomePage = loginBOUser();

        // navigate to BaseStore - Electronic Store Page
        electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());

        // navigate Default Delivery Origin page and Set new Origin Address
        electronicsStorePage.selectElectronicsStore();
        electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.LOCATIONS.getoption());
        deliveryOriginPage = electronicsStorePage.doubleClickOnDefaultDeliveryOrigin();
        deliveryOriginPage.doubleClickOnOriginAddress();
        deliveryOriginPage.setOriginAddress(HybrisBOOriginAddress.HIGHLAND_RANCH.stName, HybrisBOOriginAddress.HIGHLAND_RANCH.stNumber, HybrisBOOriginAddress.HIGHLAND_RANCH.postalCode,
                HybrisBOOriginAddress.HIGHLAND_RANCH.town, HybrisBOOriginAddress.HIGHLAND_RANCH.country);

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
        checkoutPage.fillShippingAddressDetails(Address.Delaware.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                Address.Delaware.addressLine1, Address.Delaware.city, Address.Delaware.state.fullName, Address.Delaware.zip5);
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
        assertEquals(orderConfirmPage.calculatePercentageBasedTax(HybrisTaxationDiscountPercentAmount.ZERO_TAX.getPercentOrAmount()), orderConfirmPage.getTax());
    }

    /**
     * CHYB-207 HYB - Create Sales Order with no State Tax, locally administered
     */
    @Test(groups = {"hybris_regression"})
    public void salesInvoiceNoStateTaxTest() {
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
        checkoutPage.fillShippingAddressDetails(Address.Juneau.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                Address.Juneau.addressLine1, Address.Juneau.city, Address.Juneau.state.fullName, Address.Juneau.zip5);
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
        assertEquals(orderConfirmPage.calculatePercentageBasedTax(TaxRate.Juneau.tax), orderConfirmPage.getTax());
    }

    /**
     * CHYB-208 HYB - Create Sales Order with Modified Origin State
     */
    @Test(groups = {"hybris_regression"})
    public void salesInvoiceModifiedOriginStateTest() {
        // login as Backoffice user into Hybris-Backoffice Page
        boHomePage = loginBOUser();

        // navigate to BaseStore - Electronic Store Page
        electronicsStorePage = boHomePage.navigateToElectronicsStorePage(HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName(), HybrisBONavTreeOptions.BASE_STORE.getMenuName());

        // navigate Default Delivery Origin page and Set new Origin Address
        electronicsStorePage.selectElectronicsStore();
        electronicsStorePage.selectTabFromElectronicsStore(HybrisBOBaseStoreTabOptions.LOCATIONS.getoption());
        deliveryOriginPage = electronicsStorePage.doubleClickOnDefaultDeliveryOrigin();
        deliveryOriginPage.doubleClickOnOriginAddress();
        deliveryOriginPage.setOriginAddress(HybrisBOOriginAddress.LOS_ANGELES.stName, HybrisBOOriginAddress.LOS_ANGELES.stNumber, HybrisBOOriginAddress.LOS_ANGELES.postalCode,
                HybrisBOOriginAddress.LOS_ANGELES.town, HybrisBOOriginAddress.LOS_ANGELES.country);

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
        assertEquals(orderConfirmPage.calculatePercentageBasedTax(TaxRate.LosAngeles.tax), orderConfirmPage.getTax());
    }
}
