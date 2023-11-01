package com.vertex.quality.connectors.episerver.tests.v324x.store.discount;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.enums.TaxRate;
import com.vertex.quality.connectors.episerver.common.enums.EpiDataCommon;
import com.vertex.quality.connectors.episerver.common.enums.NavigationAndMenuOptions;
import com.vertex.quality.connectors.episerver.common.enums.PaymentMethodType;
import com.vertex.quality.connectors.episerver.pages.epiCommon.*;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiAdminHomePage;
import com.vertex.quality.connectors.episerver.pages.v324x.EpiAdministrationHomePage;
import com.vertex.quality.connectors.episerver.pages.v324x.EpiOrderManagementHomePage;
import com.vertex.quality.connectors.episerver.pages.v324x.EpiOrdersPage;
import com.vertex.quality.connectors.episerver.pages.v324x.EpiWarehousePage;
import com.vertex.quality.connectors.episerver.tests.base.EpiBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Test class that contains all tests related to Epi-server's discount functionality
 *
 * @author Shivam.Soni
 */
public class EpiDiscountTests extends EpiBaseTest {

    EpiAdministrationHomePage administrationHomePage;
    EpiWarehousePage warehousePage;
    EpiOrdersPage ordersPage;
    EpiStoreLoginPage storeLoginPage;
    EpiAdminHomePage adminHomePage;
    EpiStoreFrontHomePage storeFrontHomePage;
    EpiCartPage cartPage;
    EpiCheckoutPage checkoutPage;
    EpiAddressPage addressPage;
    EpiOrderConfirmationPage orderConfirmationPage;
    String addressName;
    String orderNo;
    double storeOrderTax;

    /**
     * CEPI-330 EPI - Test Case -Create Sales Order with Discount - Multi line order with Discount Line Percentage
     */
    @Test(groups = {"epi_smoke_v324", "epi_regression_v324"})
    public void salesOrderLineItemPercentDiscountTest() {
        addressName = Address.LosAngeles.city + ", " + Address.LosAngeles.state.abbreviation;
        // Launch Epi-Server's store
        launch324EpiStorePage();

        // Login to storefront
        storeLoginPage = new EpiStoreLoginPage(driver);
        storeLoginPage.loginToEpiStore(EpiDataCommon.EpiCredentials.EPI_324_STORE_USER.text, EpiDataCommon.EpiCredentials.EPI_324_STORE_PASS.text);
        storeFrontHomePage = new EpiStoreFrontHomePage(driver);
        storeFrontHomePage.navigateToHomePage();

        // navigate to administration option from left panel
        adminHomePage = new EpiAdminHomePage(driver);
        adminHomePage.clickOnMainMenu(NavigationAndMenuOptions.SiteNavigationOptions.COMMERCE.text);
        adminHomePage.clickOnSubMenu(NavigationAndMenuOptions.CommerceMenuOptions.ADMINISTRATION.text);

        // Goto warehouse setting & update warehouse address or Physical origin or Ship From Address
        administrationHomePage = new EpiAdministrationHomePage(driver);
        administrationHomePage.goToTab(NavigationAndMenuOptions.AdministrationTabs.WAREHOUSES.text);
        warehousePage = new EpiWarehousePage(driver);
        warehousePage.selectWarehouse(EpiDataCommon.WarehouseNames.NEW_YORK_STORE.text);
        warehousePage.setWarehouseAddress(Address.Gettysburg.addressLine1, Address.Gettysburg.city, Address.Gettysburg.state.fullName, Address.Gettysburg.country.iso2code,
                Address.Gettysburg.country.fullName, Address.Gettysburg.zip5, Address.Gettysburg.state.abbreviation);


        // Launch Epi-Server's store
        launch324EpiStorePage();
        storeFrontHomePage.navigateToHomePage();

        // Clear all items from Cart
        cartPage = new EpiCartPage(driver);
        cartPage.clearAllItemsInCart();

        // Search for Product and Add to Cart
        cartPage.searchAndAddProductToCart(EpiDataCommon.ProductNames.FADED_GLORY_MENS_SHOES.text);
        cartPage.searchAndAddProductToCart(EpiDataCommon.ProductNames.CLASSIC_MID_HEELED.text);

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

        // Apply coupon & verify applied coupon
        checkoutPage.applyCoupon(EpiDataCommon.Coupons.FIVE_PERCENT_ITEM.text);
        assertTrue(checkoutPage.verifyAppliedCoupon(EpiDataCommon.Coupons.FIVE_PERCENT_ITEM.text));

        // select payment type
        checkoutPage.choosePaymentMethod(PaymentMethodType.CASH_ON_DELIVERY.text);

        // Place order
        orderConfirmationPage = checkoutPage.clickPlaceOrderButton();

        // Assertion on Actual & Expected values
        assertEquals(orderConfirmationPage.getTaxCostFromUI(), orderConfirmationPage.calculatePercentageBasedTax(TaxRate.LosAngeles.tax));
    }

    /**
     * CEPI-331 EPI - Test Case -Create Sales Order with Discount - Multi line order with Discount line Amount
     */
    @Test(groups = {"epi_regression_v324"})
    public void salesOrderLineItemAmountDiscountTest() {
        addressName = Address.LosAngeles.city + ", " + Address.LosAngeles.state.abbreviation;
        // Launch Epi-Server's store
        launch324EpiStorePage();

        // Login to storefront
        storeLoginPage = new EpiStoreLoginPage(driver);
        storeLoginPage.loginToEpiStore(EpiDataCommon.EpiCredentials.EPI_324_STORE_USER.text, EpiDataCommon.EpiCredentials.EPI_324_STORE_PASS.text);
        storeFrontHomePage = new EpiStoreFrontHomePage(driver);
        storeFrontHomePage.navigateToHomePage();

        // navigate to administration option from left panel
        adminHomePage = new EpiAdminHomePage(driver);
        adminHomePage.clickOnMainMenu(NavigationAndMenuOptions.SiteNavigationOptions.COMMERCE.text);
        adminHomePage.clickOnSubMenu(NavigationAndMenuOptions.CommerceMenuOptions.ADMINISTRATION.text);

        // Goto warehouse setting & update warehouse address or Physical origin or Ship From Address
        administrationHomePage = new EpiAdministrationHomePage(driver);
        administrationHomePage.goToTab(NavigationAndMenuOptions.AdministrationTabs.WAREHOUSES.text);
        warehousePage = new EpiWarehousePage(driver);
        warehousePage.selectWarehouse(EpiDataCommon.WarehouseNames.NEW_YORK_STORE.text);
        warehousePage.setWarehouseAddress(Address.Gettysburg.addressLine1, Address.Gettysburg.city, Address.Gettysburg.state.fullName, Address.Gettysburg.country.iso2code,
                Address.Gettysburg.country.fullName, Address.Gettysburg.zip5, Address.Gettysburg.state.abbreviation);

        // Launch Epi-Server's store
        launch324EpiStorePage();
        storeFrontHomePage.navigateToHomePage();

        // Clear all items from Cart
        cartPage = new EpiCartPage(driver);
        cartPage.clearAllItemsInCart();

        // Search for Product and Add to Cart
        cartPage.searchAndAddProductToCart(EpiDataCommon.ProductNames.FADED_GLORY_MENS_SHOES.text);
        cartPage.searchAndAddProductToCart(EpiDataCommon.ProductNames.CLASSIC_MID_HEELED.text);

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

        // Apply coupon & verify applied coupon
        checkoutPage.applyCoupon(EpiDataCommon.Coupons.TEN_DOLLAR_ITEM.text);
        assertTrue(checkoutPage.verifyAppliedCoupon(EpiDataCommon.Coupons.TEN_DOLLAR_ITEM.text));

        // select payment type
        checkoutPage.choosePaymentMethod(PaymentMethodType.CASH_ON_DELIVERY.text);

        // Place order
        orderConfirmationPage = checkoutPage.clickPlaceOrderButton();

        // Assertion on Actual & Expected values
        assertEquals(orderConfirmationPage.getTaxCostFromUI(), orderConfirmationPage.calculatePercentageBasedTax(TaxRate.LosAngeles.tax));
    }

    /**
     * CEPI-332 EPI - Test Case -Create Sales Order with Discount - Multi Line Order with Discount Shipping Amount
     */
    @Test(groups = {"epi_regression_v324"})
    public void salesOrderShippingAmountDiscountTest() {
        addressName = Address.LosAngeles.city + ", " + Address.LosAngeles.state.abbreviation;
        // Launch Epi-Server's store
        launch324EpiStorePage();

        // Login to storefront
        storeLoginPage = new EpiStoreLoginPage(driver);
        storeLoginPage.loginToEpiStore(EpiDataCommon.EpiCredentials.EPI_324_STORE_USER.text, EpiDataCommon.EpiCredentials.EPI_324_STORE_PASS.text);
        storeFrontHomePage = new EpiStoreFrontHomePage(driver);
        storeFrontHomePage.navigateToHomePage();

        // navigate to administration option from left panel
        adminHomePage = new EpiAdminHomePage(driver);
        adminHomePage.clickOnMainMenu(NavigationAndMenuOptions.SiteNavigationOptions.COMMERCE.text);
        adminHomePage.clickOnSubMenu(NavigationAndMenuOptions.CommerceMenuOptions.ADMINISTRATION.text);

        // Goto warehouse setting & update warehouse address or Physical origin or Ship From Address
        administrationHomePage = new EpiAdministrationHomePage(driver);
        administrationHomePage.goToTab(NavigationAndMenuOptions.AdministrationTabs.WAREHOUSES.text);
        warehousePage = new EpiWarehousePage(driver);
        warehousePage.selectWarehouse(EpiDataCommon.WarehouseNames.NEW_YORK_STORE.text);
        warehousePage.setWarehouseAddress(Address.Gettysburg.addressLine1, Address.Gettysburg.city, Address.Gettysburg.state.fullName, Address.Gettysburg.country.iso2code,
                Address.Gettysburg.country.fullName, Address.Gettysburg.zip5, Address.Gettysburg.state.abbreviation);

        // Launch Epi-Server's store
        launch324EpiStorePage();
        storeFrontHomePage.navigateToHomePage();

        // Clear all items from Cart
        cartPage = new EpiCartPage(driver);
        cartPage.clearAllItemsInCart();

        // Search for Product and Add to Cart
        cartPage.searchAndAddProductToCart(EpiDataCommon.ProductNames.FADED_GLORY_MENS_SHOES.text);
        cartPage.searchAndAddProductToCart(EpiDataCommon.ProductNames.CLASSIC_MID_HEELED.text);

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

        // Apply coupon & verify applied coupon
        checkoutPage.applyCoupon(EpiDataCommon.Coupons.ONE_OFF_SHIP.text);
        assertTrue(checkoutPage.verifyAppliedCoupon(EpiDataCommon.Coupons.ONE_OFF_SHIP.text));

        // select payment type
        checkoutPage.choosePaymentMethod(PaymentMethodType.CASH_ON_DELIVERY.text);

        // Place order
        orderConfirmationPage = checkoutPage.clickPlaceOrderButton();

        // Assertion on Actual & Expected values
        assertEquals(orderConfirmationPage.getTaxCostFromUI(), orderConfirmationPage.calculatePercentageBasedTax(TaxRate.LosAngeles.tax));
    }

    /**
     * CEPI-333 EPI - Test Case - Create Sale Order with a Discount Shipping for VAT (Intra EU DE-DE) and Invoice
     */
    @Test(groups = {"epi_regression_v324"})
    public void salesOrderShippingAmountDiscountVATTest() {
        addressName = Address.BerlinAlternate.city + ", " + Address.BerlinAlternate.country.iso2code;
        // Launch Epi-Server's store
        launch324EpiStorePage();

        // Login to storefront
        storeLoginPage = new EpiStoreLoginPage(driver);
        storeLoginPage.loginToEpiStore(EpiDataCommon.EpiCredentials.EPI_324_STORE_USER.text, EpiDataCommon.EpiCredentials.EPI_324_STORE_PASS.text);
        storeFrontHomePage = new EpiStoreFrontHomePage(driver);
        storeFrontHomePage.navigateToHomePage();

        // navigate to administration option from left panel
        adminHomePage = new EpiAdminHomePage(driver);
        adminHomePage.clickOnMainMenu(NavigationAndMenuOptions.SiteNavigationOptions.COMMERCE.text);
        adminHomePage.clickOnSubMenu(NavigationAndMenuOptions.CommerceMenuOptions.ADMINISTRATION.text);

        // Goto warehouse setting & update warehouse address or Physical origin or Ship From Address
        administrationHomePage = new EpiAdministrationHomePage(driver);
        administrationHomePage.goToTab(NavigationAndMenuOptions.AdministrationTabs.WAREHOUSES.text);
        warehousePage = new EpiWarehousePage(driver);
        warehousePage.selectWarehouse(EpiDataCommon.WarehouseNames.NEW_YORK_STORE.text);
        warehousePage.setWarehouseAddress(Address.BerlinAlternate.addressLine1, Address.BerlinAlternate.city, Address.BerlinAlternate.city, Address.BerlinAlternate.country.iso2code,
                Address.BerlinAlternate.country.fullName, Address.BerlinAlternate.zip5, Address.BerlinAlternate.country.iso2code);

        // Launch Epi-Server's store
        launch324EpiStorePage();
        storeFrontHomePage.navigateToHomePage();

        // Clear all items from Cart
        cartPage = new EpiCartPage(driver);
        cartPage.clearAllItemsInCart();

        // Search for Product and Add to Cart
        cartPage.searchAndAddProductToCart(EpiDataCommon.ProductNames.FADED_GLORY_MENS_SHOES.text);
        cartPage.searchAndAddProductToCart(EpiDataCommon.ProductNames.CLASSIC_MID_HEELED.text);
        cartPage.searchAndAddProductToCart(EpiDataCommon.ProductNames.PUMA_RED_LEATHER_LOAFERS.text);

        // Proceed to checkout
        checkoutPage = cartPage.proceedToCheckout();

        // check existing address & if exists then select or add new address
        addressPage = new EpiAddressPage(driver);
        if (checkoutPage.verifyExistingBillingAddress(addressName)) {
            checkoutPage.selectExistingBillingAddressByName(addressName);
        } else {
            addressPage.addNewAddress(addressName, Address.Berlin.addressLine1, Address.Berlin.zip5, Address.Berlin.city, Address.Berlin.country.iso2code, Address.Berlin.country.fullName);
        }

        // Added below condition to add alternate or shipping address to avoid failures in Automation.
        // We can add billing & shipping as same but sometime hidden elements represents different shipping address & hence we got some discrepancies in Actual Vs Expected tax calculations.
        checkoutPage.clickOnAddAlternateAddress();
        if (checkoutPage.verifyExistingShippingAddress(addressName)) {
            checkoutPage.selectExistingShippingAddressByName(addressName);
        } else {
            addressPage.addNewAddress(addressName, Address.Berlin.addressLine1, Address.Berlin.zip5, Address.Berlin.city, Address.Berlin.country.iso2code, Address.Berlin.country.fullName);
        }

        // Apply coupon & verify applied coupon
        checkoutPage.applyCoupon(EpiDataCommon.Coupons.TEN_DOLLAR_ITEM.text);
        assertTrue(checkoutPage.verifyAppliedCoupon(EpiDataCommon.Coupons.TEN_DOLLAR_ITEM.text));

        // select payment type
        checkoutPage.choosePaymentMethod(PaymentMethodType.CASH_ON_DELIVERY.text);

        // Place order
        orderConfirmationPage = checkoutPage.clickPlaceOrderButton();

        // Storing order Number to validate on Commerce Manager
        orderNo = orderConfirmationPage.getOrderNo();

        // Assertion on Actual & Expected values
        storeOrderTax = orderConfirmationPage.getTaxCostFromUI();
        assertEquals(storeOrderTax, orderConfirmationPage.calculatePercentageBasedTax(EpiDataCommon.TaxRates.DE_DE_TAX.tax));

        // Launch Epi-Server's store
        launch324EpiStorePage();
        storeFrontHomePage = new EpiStoreFrontHomePage(driver);
        storeFrontHomePage.navigateToHomePage();

        // navigate to Order Management option
        adminHomePage = new EpiAdminHomePage(driver);
        adminHomePage.clickOnMainMenu(NavigationAndMenuOptions.SiteNavigationOptions.COMMERCE.text);
        adminHomePage.clickOnSubMenu(NavigationAndMenuOptions.CommerceMenuOptions.ORDER_MANAGEMENT.text);

        EpiOrderManagementHomePage orderManagementHomePage = new EpiOrderManagementHomePage(driver);
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
