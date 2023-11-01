package com.vertex.quality.connectors.episerver.tests.v324x.store.customerCodeClass;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.episerver.common.enums.EpiDataCommon;
import com.vertex.quality.connectors.episerver.common.enums.NavigationAndMenuOptions;
import com.vertex.quality.connectors.episerver.common.enums.PaymentMethodType;
import com.vertex.quality.connectors.episerver.pages.epiCommon.*;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiAdminHomePage;
import com.vertex.quality.connectors.episerver.pages.v324x.EpiOrderManagementHomePage;
import com.vertex.quality.connectors.episerver.pages.v324x.EpiOrdersPage;
import com.vertex.quality.connectors.episerver.tests.base.EpiBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Test class that contains tests related to
 *
 * @author Shivam.Soni
 */
public class EpiCustomerCodeClassTest extends EpiBaseTest {

    EpiAdminHomePage adminHomePage;
    EpiStoreLoginPage storeLoginPage;
    EpiStoreFrontHomePage storeFrontHomePage;
    EpiCartPage cartPage;
    EpiCheckoutPage checkoutPage;
    EpiAddressPage addressPage;
    EpiOrderConfirmationPage orderConfirmationPage;
    EpiOrderManagementHomePage orderManagementHomePage;
    EpiOrdersPage ordersPage;
    String addressName;
    String orderNo;
    double storeOrderTax;

    /**
     * CEPI-294 EPI - Test Case - Create Sales order with Customer Class Exemption (v3.2.4)
     */
    @Test(groups = {"epi_regression_v324"})
    public void salesOrderWithCustomerClassTest() {
        addressName = Address.LosAngeles.city + ", " + Address.LosAngeles.state.abbreviation;
        // Launch Epi-Server's store
        launch324EpiStorePage();

        // Login to storefront
        storeLoginPage = new EpiStoreLoginPage(driver);
        storeLoginPage.loginToEpiStore(EpiDataCommon.EpiCredentials.EPI_324_CUSTOMER_CLASS_USER.text, EpiDataCommon.EpiCredentials.EPI_324_STORE_PASS.text);
        storeFrontHomePage = new EpiStoreFrontHomePage(driver);
        storeFrontHomePage.navigateToHomePage();

        // Clear all items from Cart
        cartPage = new EpiCartPage(driver);
        cartPage.clearAllItemsInCart();

        // Search for Product and Add to Cart
        cartPage.searchAndAddProductToCart(EpiDataCommon.ProductNames.FADED_GLORY_MENS_SHOES.text);

        // Proceed to checkout
        checkoutPage = cartPage.proceedToCheckout();

        // check existing address & if exists then select or add new address
        addressPage = new EpiAddressPage(driver);
        if (checkoutPage.verifyExistingBillingAddress(addressName)) {
            checkoutPage.selectExistingBillingAddressByName(addressName);
        } else {
            addressPage.addNewAddress(addressName, Address.LosAngeles.addressLine1, Address.LosAngeles.zip5, Address.LosAngeles.city, Address.LosAngeles.state.fullName, Address.LosAngeles.country.fullName);
        }

        // Added below condition to add alternate or shipping address to avoid failures in Automation.
        // We can add billing & shipping as same but sometime hidden elements represents different shipping address & hence we got some discrepancies in Actual Vs Expected tax calculations.
        checkoutPage.clickOnAddAlternateAddress();
        if (checkoutPage.verifyExistingShippingAddress(addressName)) {
            checkoutPage.selectExistingShippingAddressByName(addressName);
        } else {
            addressPage.addNewAddress(addressName, Address.LosAngeles.addressLine1, Address.LosAngeles.zip5, Address.LosAngeles.city, Address.LosAngeles.state.fullName, Address.LosAngeles.country.fullName);
        }

        // select payment type
        checkoutPage.choosePaymentMethod(PaymentMethodType.CASH_ON_DELIVERY.text);

        // Place order
        orderConfirmationPage = checkoutPage.clickPlaceOrderButton();

        // Storing order Number to validate on Commerce Manager
        orderNo = orderConfirmationPage.getOrderNo();

        // Assertion on Actual & Expected values
        storeOrderTax = orderConfirmationPage.getTaxCostFromUI();
        assertEquals(storeOrderTax, orderConfirmationPage.calculateIndividualPercentBaseTaxesAndDoAddition(EpiDataCommon.TaxRates.ZERO_TAX.tax, EpiDataCommon.TaxRates.ZERO_TAX.tax));

        // Sign-out from customer's login
        ordersPage = new EpiOrdersPage(driver);
        ordersPage.signOutOfEpiServerStore();

        // Login to storefront with Admin user
        storeLoginPage.loginToEpiStore(EpiDataCommon.EpiCredentials.EPI_324_STORE_USER.text, EpiDataCommon.EpiCredentials.EPI_324_STORE_PASS.text);
        storeFrontHomePage.navigateToHomePage();

        // navigate to Order Management option
        adminHomePage = new EpiAdminHomePage(driver);
        adminHomePage.clickOnMainMenu(NavigationAndMenuOptions.SiteNavigationOptions.COMMERCE.text);
        adminHomePage.clickOnSubMenu(NavigationAndMenuOptions.CommerceMenuOptions.ORDER_MANAGEMENT.text);

        orderManagementHomePage = new EpiOrderManagementHomePage(driver);
        orderManagementHomePage.goToTab(NavigationAndMenuOptions.OrderManagementTabs.ORDERS.text);

        ordersPage.openOrderAndSelectTab(orderNo, NavigationAndMenuOptions.OrderDetailsTabs.SUMMARY.text);

        // Assertion on Order Status & Total Tax
        assertTrue(ordersPage.verifyOrderStatus(EpiDataCommon.OrderStatus.IN_PROGRESS.text));
        assertEquals(ordersPage.getOrderTotalTaxesFromUI(), storeOrderTax);

        // Open order page & select Details Tab
        ordersPage.selectTab(NavigationAndMenuOptions.OrderDetailsTabs.FORM_DETAILS.text);

        // Release shipment, Add to Picklist & complete the order
        ordersPage.releaseOrderAddToPickListAndCompleteOrder();

        // Select Summary Tab
        ordersPage.selectTab(NavigationAndMenuOptions.OrderDetailsTabs.SUMMARY.text);

        // Assertion on Order Status & Total Tax
        assertTrue(ordersPage.verifyOrderStatus(EpiDataCommon.OrderStatus.COMPLETED.text));
        assertEquals(ordersPage.getOrderTotalTaxesFromUI(), storeOrderTax);
    }

    /**
     * CEPI-293 EPI - Test Case - Create Sales order with Customer Code Exemption (v3.2.4)
     */
    @Test(groups = {"epi_regression_v324"})
    public void salesOrderWithCustomerCodeTest() {
        addressName = Address.LosAngeles.city + ", " + Address.LosAngeles.state.abbreviation;
        // Launch Epi-Server's store
        launch324EpiStorePage();

        // Login to storefront
        storeLoginPage = new EpiStoreLoginPage(driver);
        storeLoginPage.loginToEpiStore(EpiDataCommon.EpiCredentials.EPI_324_CUSTOMER_CODE_USER.text, EpiDataCommon.EpiCredentials.EPI_324_STORE_PASS.text);
        storeFrontHomePage = new EpiStoreFrontHomePage(driver);
        storeFrontHomePage.navigateToHomePage();

        // Clear all items from Cart
        cartPage = new EpiCartPage(driver);
        cartPage.clearAllItemsInCart();

        // Search for Product and Add to Cart
        cartPage.searchAndAddProductToCart(EpiDataCommon.ProductNames.FADED_GLORY_MENS_SHOES.text);

        // Proceed to checkout
        checkoutPage = cartPage.proceedToCheckout();

        // check existing address & if exists then select or add new address
        addressPage = new EpiAddressPage(driver);
        if (checkoutPage.verifyExistingBillingAddress(addressName)) {
            checkoutPage.selectExistingBillingAddressByName(addressName);
        } else {
            addressPage.addNewAddress(addressName, Address.LosAngeles.addressLine1, Address.LosAngeles.zip5, Address.LosAngeles.city, Address.LosAngeles.state.fullName, Address.LosAngeles.country.fullName);
        }

        // Added below condition to add alternate or shipping address to avoid failures in Automation.
        // We can add billing & shipping as same but sometime hidden elements represents different shipping address & hence we got some discrepancies in Actual Vs Expected tax calculations.
        checkoutPage.clickOnAddAlternateAddress();
        if (checkoutPage.verifyExistingShippingAddress(addressName)) {
            checkoutPage.selectExistingShippingAddressByName(addressName);
        } else {
            addressPage.addNewAddress(addressName, Address.LosAngeles.addressLine1, Address.LosAngeles.zip5, Address.LosAngeles.city, Address.LosAngeles.state.fullName, Address.LosAngeles.country.fullName);
        }

        // select payment type
        checkoutPage.choosePaymentMethod(PaymentMethodType.CASH_ON_DELIVERY.text);

        // Place order
        orderConfirmationPage = checkoutPage.clickPlaceOrderButton();

        // Storing order Number to validate on Commerce Manager
        orderNo = orderConfirmationPage.getOrderNo();

        // Assertion on Actual & Expected values
        storeOrderTax = orderConfirmationPage.getTaxCostFromUI();
        assertEquals(storeOrderTax, orderConfirmationPage.calculateIndividualPercentBaseTaxesAndDoAddition(EpiDataCommon.TaxRates.ZERO_TAX.tax, EpiDataCommon.TaxRates.ZERO_TAX.tax));

        // Sign-out from customer's login
        ordersPage = new EpiOrdersPage(driver);
        ordersPage.signOutOfEpiServerStore();

        // Login to storefront
        storeLoginPage.loginToEpiStore(EpiDataCommon.EpiCredentials.EPI_324_STORE_USER.text, EpiDataCommon.EpiCredentials.EPI_324_STORE_PASS.text);
        storeFrontHomePage.navigateToHomePage();

        // navigate to Order Management option
        adminHomePage = new EpiAdminHomePage(driver);
        adminHomePage.clickOnMainMenu(NavigationAndMenuOptions.SiteNavigationOptions.COMMERCE.text);
        adminHomePage.clickOnSubMenu(NavigationAndMenuOptions.CommerceMenuOptions.ORDER_MANAGEMENT.text);

        orderManagementHomePage = new EpiOrderManagementHomePage(driver);
        orderManagementHomePage.goToTab(NavigationAndMenuOptions.OrderManagementTabs.ORDERS.text);

        ordersPage = new EpiOrdersPage(driver);
        ordersPage.openOrderAndSelectTab(orderNo, NavigationAndMenuOptions.OrderDetailsTabs.SUMMARY.text);

        // Assertion on Order Status & Total Tax
        assertTrue(ordersPage.verifyOrderStatus(EpiDataCommon.OrderStatus.IN_PROGRESS.text));
        assertEquals(ordersPage.getOrderTotalTaxesFromUI(), storeOrderTax);

        // Open order page & select Details Tab
        ordersPage.selectTab(NavigationAndMenuOptions.OrderDetailsTabs.FORM_DETAILS.text);

        // Release shipment, Add to Picklist & complete the order
        ordersPage.releaseOrderAddToPickListAndCompleteOrder();

        // Select Summary Tab
        ordersPage.selectTab(NavigationAndMenuOptions.OrderDetailsTabs.SUMMARY.text);

        // Assertion on Order Status & Total Tax
        assertTrue(ordersPage.verifyOrderStatus(EpiDataCommon.OrderStatus.COMPLETED.text));
        assertEquals(ordersPage.getOrderTotalTaxesFromUI(), storeOrderTax);
    }
}
