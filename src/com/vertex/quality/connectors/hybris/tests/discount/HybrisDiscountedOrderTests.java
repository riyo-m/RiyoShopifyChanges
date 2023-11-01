package com.vertex.quality.connectors.hybris.tests.discount;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.enums.CreditCard;
import com.vertex.quality.common.enums.TaxRate;
import com.vertex.quality.common.utils.properties.CommonDataProperties;
import com.vertex.quality.connectors.hybris.enums.backoffice.HybrisBOBaseStoreTabOptions;
import com.vertex.quality.connectors.hybris.enums.backoffice.HybrisBONavTreeOptions;
import com.vertex.quality.connectors.hybris.enums.backoffice.HybrisBOOriginAddress;
import com.vertex.quality.connectors.hybris.enums.electronics.HybrisDiscountCoupons;
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
import static org.testng.AssertJUnit.assertTrue;

/**
 * Test class that validates sales order with discount coupons.
 * As a part of pre-condition of these tests please execute CHYB-266 test case under manual interim test type to set all environment & other things to be ready.
 * Ignorance of execution of CHYB-266 may cause failures in the automation execution
 *
 * @author Shivam.Soni
 */
public class HybrisDiscountedOrderTests extends HybrisBaseTest {

    HybrisBOHomePage boHomePage;
    HybrisBOBaseStorePage electronicsStorePage;
    HybrisBODefaultDeliveryOriginPage deliveryOriginPage;
    HybrisBOVertexConfigurationPage vertexConfigPage;
    HybrisEStorePage storeFront;
    HybrisEStoreCartPage cartPage;
    HybrisEStoreGuestLoginPage eStoreGuestLoginPage;
    HybrisEStoreCheckOutPage checkoutPage;
    HybrisEStoreOrderConfirmationPage orderConfirmPage;
    double expectedDiscount;
    double actualDiscount;
    double subtotalBeforeCouponApplied;

    /**
     * CHYB-269 HYB - Test Case -Create Sales Order with Discount - Multi Line Order with Discount Order Amount
     */
    @Test(groups = {"hybris_regression"})
    public void multiLineOrderWithDiscountOrderAmountTest() {
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
        storeFront.doCheckout();

        // Apply coupon & Verify
        storeFront.addCouponAndApply(HybrisDiscountCoupons.COUPON_5_DOLLAR_ORDER.getCouponName());

        // Continue to complete order
        storeFront.clickContinue();

        // Set Electronics Store Guest Credentials and checkout as Guest
        eStoreGuestLoginPage = new HybrisEStoreGuestLoginPage(driver);
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
     * CHYB-268 - HYB - Create Sales Order with Discount - Multi line with Discount order Percent, Change Discount Order Percent
     */
    @Test(groups = {"hybris_regression"})
    public void multiLineOrderWithDiscountOrderPercentChangePercentTest() {
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
        storeFront.searchAndAddProductToCart(HybrisProductIds.POWERSHOTID.getproductID());
        storeFront.doCheckout();
        subtotalBeforeCouponApplied = storeFront.getSubTotalAmount();

        // Apply coupon & Verify
        storeFront.addCouponAndApply(HybrisDiscountCoupons.COUPON_10_PERCENT_ORDER.getCouponName());

        // Continue to complete order
        storeFront.clickContinue();

        // Set Electronics Store Guest Credentials and checkout as Guest
        eStoreGuestLoginPage = new HybrisEStoreGuestLoginPage(driver);
        setGuestEmailAndConfirmEmail(eStoreGuestLoginPage);
        checkoutPage = eStoreGuestLoginPage.checkoutAsGuest();

        // Fill Shipping Address Details and Proceed to Checkout
        checkoutPage.fillShippingAddressDetails(Address.Louisiana.country.fullName, CommonDataProperties.TITLE, CommonDataProperties.FIRST_NAME, CommonDataProperties.LAST_NAME,
                Address.Louisiana.addressLine1, Address.Louisiana.city, Address.Louisiana.state.fullName, Address.Louisiana.zip5);
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
        assertEquals(orderConfirmPage.calculatePercentageBasedTax(HybrisTaxationDiscountPercentAmount.PA_LA_TAX.getPercentOrAmount()), orderConfirmPage.getTax());
    }

    /**
     * CHYB-267 HYB - Test Case - Create Sale Order with a Discount Shipping for VAT (Intra EU DE-DE) and Invoice
     */
    @Test(groups = {"hybris_regression"})
    public void salesOrderWithDiscountForVATTest() {
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
        storeFront.searchAndAddProductToCart(HybrisProductIds.POWERSHOTID.getproductID());
        storeFront.doCheckout();
        subtotalBeforeCouponApplied = storeFront.getSubTotalAmount();

        // Apply coupon & Verify
        storeFront.addCouponAndApply(HybrisDiscountCoupons.COUPON_10_PERCENT_ORDER.getCouponName());

        // Continue to complete order
        storeFront.clickContinue();

        // Set Electronics Store Guest Credentials and checkout as Guest
        eStoreGuestLoginPage = new HybrisEStoreGuestLoginPage(driver);
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
        assertEquals(orderConfirmPage.calculatePercentageBasedTax(HybrisTaxationDiscountPercentAmount.DE_DE_TAX.getPercentOrAmount()), orderConfirmPage.getTax());
    }
}
