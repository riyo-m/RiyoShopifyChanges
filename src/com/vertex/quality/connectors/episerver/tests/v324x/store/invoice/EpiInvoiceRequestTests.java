package com.vertex.quality.connectors.episerver.tests.v324x.store.invoice;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.enums.TaxRate;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.episerver.common.enums.EpiDataCommon;
import com.vertex.quality.connectors.episerver.common.enums.NavigationAndMenuOptions;
import com.vertex.quality.connectors.episerver.common.enums.OSeriesData;
import com.vertex.quality.connectors.episerver.common.enums.PaymentMethodType;
import com.vertex.quality.connectors.episerver.pages.epiCommon.*;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiAdminHomePage;
import com.vertex.quality.connectors.episerver.pages.v324x.*;
import com.vertex.quality.connectors.episerver.tests.base.EpiBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * CCMMER2-379 Episerver v3.2.4 regression-II
 * Invoice test-cases
 *
 * @author Shivam.Soni
 */
public class EpiInvoiceRequestTests extends EpiBaseTest {

    EpiAdminHomePage adminHomePage;
    EpiAdministrationHomePage administrationHomePage;
    EpiWarehousePage warehousePage;
    EpiOSeriesPage epiOSeries;
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
     * CEPI-318 Test Case -Create Sales Order with Invoice
     */
    @Test(groups = {"epi_smoke_v324", "epi_regression_v324"})
    public void salesOrderWithInvoiceTest() {
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
        assertEquals(storeOrderTax, orderConfirmationPage.calculatePercentageBasedTax(TaxRate.LosAngeles.tax));

        // Launch Epi-Server's store
        launch324EpiStorePage();
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

    /**
     * CEPI-319 Test Case -Create Sales Order with Invoice US to CAN
     */
    @Test(groups = {"epi_regression_v324"})
    public void salesOrderWithInvoiceUSToCANTest() {
        addressName = Address.Victoria.city + ", " + Address.Victoria.province.abbreviation;
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

        // Proceed to checkout
        checkoutPage = cartPage.proceedToCheckout();

        // check existing address & if exists then select or add new address
        addressPage = new EpiAddressPage(driver);
        if (checkoutPage.verifyExistingBillingAddress(addressName)) {
            checkoutPage.selectExistingBillingAddressByName(addressName);
        } else {
            addressPage.addNewAddress(addressName, Address.Victoria.addressLine1, Address.Victoria.zip5, Address.Victoria.city, Address.Victoria.province.fullName, Address.Victoria.country.fullName);
        }

        // Added below condition to add alternate or shipping address to avoid failures in Automation.
        // We can add billing & shipping as same but sometime hidden elements represents different shipping address & hence we got some discrepancies in Actual Vs Expected tax calculations.
        checkoutPage.clickOnAddAlternateAddress();
        if (checkoutPage.verifyExistingShippingAddress(addressName)) {
            checkoutPage.selectExistingShippingAddressByName(addressName);
        } else {
            addressPage.addNewAddress(addressName, Address.Victoria.addressLine1, Address.Victoria.zip5, Address.Victoria.city, Address.Victoria.province.fullName, Address.Victoria.country.fullName);
        }

        // select payment type
        checkoutPage.choosePaymentMethod(PaymentMethodType.CASH_ON_DELIVERY.text);

        // Place order
        orderConfirmationPage = checkoutPage.clickPlaceOrderButton();

        // Storing order Number to validate on Commerce Manager
        orderNo = orderConfirmationPage.getOrderNo();

        // Assertion on Actual & Expected values
        storeOrderTax = orderConfirmationPage.getTaxCostFromUI();
        assertEquals(storeOrderTax, orderConfirmationPage.calculatePercentageBasedTax(EpiDataCommon.TaxRates.CAN_VICTORIA_TAX.tax));

        // Launch Epi-Server's store
        launch324EpiStorePage();
        storeFrontHomePage = new EpiStoreFrontHomePage(driver);
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

    /**
     * CEPI-320 Test Case -Create Sales Order with Invoice CAN to US
     */
    @Test(groups = {"epi_regression_v324"})
    public void salesOrderWithInvoiceCANToUSTest() {
        addressName = Address.Gettysburg.city + ", " + Address.Gettysburg.state.abbreviation;
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
        warehousePage.setWarehouseAddress(Address.Victoria.addressLine1, Address.Victoria.city, Address.Victoria.province.fullName, Address.Victoria.country.iso2code,
                Address.Victoria.country.fullName, Address.Victoria.zip5, Address.Victoria.province.abbreviation);

        // Launch Epi-Server's store
        launch324EpiStorePage();
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
            addressPage.addNewAddress(addressName, Address.Gettysburg.addressLine1, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.state.fullName, Address.Gettysburg.country.fullName);
        }

        // Added below condition to add alternate or shipping address to avoid failures in Automation.
        // We can add billing & shipping as same but sometime hidden elements represents different shipping address & hence we got some discrepancies in Actual Vs Expected tax calculations.
        checkoutPage.clickOnAddAlternateAddress();
        if (checkoutPage.verifyExistingShippingAddress(addressName)) {
            checkoutPage.selectExistingShippingAddressByName(addressName);
        } else {
            addressPage.addNewAddress(addressName, Address.Gettysburg.addressLine1, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.state.fullName, Address.Gettysburg.country.fullName);
        }

        // select payment type
        checkoutPage.choosePaymentMethod(PaymentMethodType.CASH_ON_DELIVERY.text);

        // Place order
        orderConfirmationPage = checkoutPage.clickPlaceOrderButton();

        // Storing order Number to validate on Commerce Manager
        orderNo = orderConfirmationPage.getOrderNo();

        // Assertion on Actual & Expected values
        storeOrderTax = orderConfirmationPage.getTaxCostFromUI();
        assertEquals(storeOrderTax, orderConfirmationPage.calculatePercentageBasedTax(EpiDataCommon.TaxRates.PA_TAX.tax));

        // Launch Epi-Server's store
        launch324EpiStorePage();
        storeFrontHomePage = new EpiStoreFrontHomePage(driver);
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

    /**
     * CEPI-321 Test Case -Create Sales Order with Invoice CANNB to CANNB same Province (HST)
     */
    @Test(groups = {"epi_regression_v324"})
    public void salesOrderWithInvoiceCANNBToCANNBTest() {
        addressName = Address.Quispamsis.city + ", " + Address.Quispamsis.province.abbreviation;
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
        warehousePage.setWarehouseAddress(Address.GrandManan.addressLine1, Address.GrandManan.city, Address.GrandManan.province.fullName, Address.GrandManan.country.iso2code,
                Address.GrandManan.country.fullName, Address.GrandManan.zip5, Address.GrandManan.province.abbreviation);

        // Launch Epi-Server's store
        launch324EpiStorePage();
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
            addressPage.addNewAddress(addressName, Address.Quispamsis.addressLine1, Address.Quispamsis.zip5, Address.Quispamsis.city, Address.Quispamsis.province.fullName, Address.Quispamsis.country.fullName);
        }

        // Added below condition to add alternate or shipping address to avoid failures in Automation.
        // We can add billing & shipping as same but sometime hidden elements represents different shipping address & hence we got some discrepancies in Actual Vs Expected tax calculations.
        checkoutPage.clickOnAddAlternateAddress();
        if (checkoutPage.verifyExistingShippingAddress(addressName)) {
            checkoutPage.selectExistingShippingAddressByName(addressName);
        } else {
            addressPage.addNewAddress(addressName, Address.Quispamsis.addressLine1, Address.Quispamsis.zip5, Address.Quispamsis.city, Address.Quispamsis.province.fullName, Address.Quispamsis.country.fullName);
        }

        // select payment type
        checkoutPage.choosePaymentMethod(PaymentMethodType.CASH_ON_DELIVERY.text);

        // Place order
        orderConfirmationPage = checkoutPage.clickPlaceOrderButton();

        // Storing order Number to validate on Commerce Manager
        orderNo = orderConfirmationPage.getOrderNo();

        // Assertion on Actual & Expected values
        storeOrderTax = orderConfirmationPage.getTaxCostFromUI();
        assertEquals(storeOrderTax, orderConfirmationPage.calculatePercentageBasedTax(EpiDataCommon.TaxRates.CANNB_CANNB_TAX.tax));

        // Launch Epi-Server's store
        launch324EpiStorePage();
        storeFrontHomePage = new EpiStoreFrontHomePage(driver);
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

    /**
     * CEPI-322 Test Case -Create Sales Order with Invoice CANQC to CANBC different Province (GST/PST)
     */
    @Test(groups = {"epi_regression_v324"})
    public void salesOrderWithInvoiceCANQCToCANBCTest() {
        addressName = Address.Victoria.city + ", " + Address.Victoria.province.abbreviation;
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
        warehousePage.setWarehouseAddress(Address.QuebecCity.addressLine1, Address.QuebecCity.city, Address.QuebecCity.province.fullName, Address.QuebecCity.country.iso2code,
                Address.QuebecCity.country.fullName, Address.QuebecCity.zip5, Address.QuebecCity.province.abbreviation);

        // Launch Epi-Server's store
        launch324EpiStorePage();
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
            addressPage.addNewAddress(addressName, Address.Victoria.addressLine1, Address.Victoria.zip5, Address.Victoria.city, Address.Victoria.province.fullName, Address.Victoria.country.fullName);
        }

        // Added below condition to add alternate or shipping address to avoid failures in Automation.
        // We can add billing & shipping as same but sometime hidden elements represents different shipping address & hence we got some discrepancies in Actual Vs Expected tax calculations.
        checkoutPage.clickOnAddAlternateAddress();
        if (checkoutPage.verifyExistingShippingAddress(addressName)) {
            checkoutPage.selectExistingShippingAddressByName(addressName);
        } else {
            addressPage.addNewAddress(addressName, Address.Victoria.addressLine1, Address.Victoria.zip5, Address.Victoria.city, Address.Victoria.province.fullName, Address.Victoria.country.fullName);
        }

        // select payment type
        checkoutPage.choosePaymentMethod(PaymentMethodType.CASH_ON_DELIVERY.text);

        // Place order
        orderConfirmationPage = checkoutPage.clickPlaceOrderButton();

        // Storing order Number to validate on Commerce Manager
        orderNo = orderConfirmationPage.getOrderNo();

        // Assertion on Actual & Expected values
        storeOrderTax = orderConfirmationPage.getTaxCostFromUI();
        assertEquals(storeOrderTax, orderConfirmationPage.calculatePercentageBasedTax(EpiDataCommon.TaxRates.CANQC_CANBC_TAX.tax));

        // Launch Epi-Server's store
        launch324EpiStorePage();
        storeFrontHomePage = new EpiStoreFrontHomePage(driver);
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

    /**
     * CEPI-323 Test Case -Create Sales Order with Invoice CANBC to CANON different Province (GST/HST)
     */
    @Test(groups = {"epi_regression_v324"})
    public void salesOrderWithInvoiceCANBCToCANONTest() {
        addressName = Address.Ottawa.city + ", " + Address.Ottawa.province.abbreviation;
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
        warehousePage.setWarehouseAddress(Address.QuebecCity.addressLine1, Address.QuebecCity.city, Address.QuebecCity.province.fullName, Address.QuebecCity.country.iso2code,
                Address.QuebecCity.country.fullName, Address.QuebecCity.zip5, Address.QuebecCity.province.abbreviation);

        // Launch Epi-Server's store
        launch324EpiStorePage();
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
            addressPage.addNewAddress(addressName, Address.Ottawa.addressLine1, Address.Ottawa.zip5, Address.Ottawa.city, Address.Ottawa.province.fullName, Address.Ottawa.country.fullName);
        }

        // Added below condition to add alternate or shipping address to avoid failures in Automation.
        // We can add billing & shipping as same but sometime hidden elements represents different shipping address & hence we got some discrepancies in Actual Vs Expected tax calculations.
        checkoutPage.clickOnAddAlternateAddress();
        if (checkoutPage.verifyExistingShippingAddress(addressName)) {
            checkoutPage.selectExistingShippingAddressByName(addressName);
        } else {
            addressPage.addNewAddress(addressName, Address.Ottawa.addressLine1, Address.Ottawa.zip5, Address.Ottawa.city, Address.Ottawa.province.fullName, Address.Ottawa.country.fullName);
        }

        // select payment type
        checkoutPage.choosePaymentMethod(PaymentMethodType.CASH_ON_DELIVERY.text);

        // Place order
        orderConfirmationPage = checkoutPage.clickPlaceOrderButton();

        // Storing order Number to validate on Commerce Manager
        orderNo = orderConfirmationPage.getOrderNo();

        // Assertion on Actual & Expected values
        storeOrderTax = orderConfirmationPage.getTaxCostFromUI();
        assertEquals(storeOrderTax, orderConfirmationPage.calculatePercentageBasedTax(EpiDataCommon.TaxRates.CANBC_CANON_TAX.tax));

        // Launch Epi-Server's store
        launch324EpiStorePage();
        storeFrontHomePage = new EpiStoreFrontHomePage(driver);
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

    /**
     * CEPI-324 Test Case -Create Sales Order with Invoice CANBC to CANQC different Province (GST/QST)
     */
    @Test(groups = {"epi_regression_v324"})
    public void salesOrderWithInvoiceCANBCToCANQCTest() {
        addressName = Address.QuebecCity.city + ", " + Address.QuebecCity.province.abbreviation;
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
        warehousePage.setWarehouseAddress(Address.Victoria.addressLine1, Address.Victoria.city, Address.Victoria.province.fullName, Address.Victoria.country.iso2code,
                Address.Victoria.country.fullName, Address.Victoria.zip5, Address.Victoria.province.abbreviation);

        // Launch Epi-Server's store
        launch324EpiStorePage();
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
            addressPage.addNewAddress(addressName, Address.QuebecCity.addressLine1, Address.QuebecCity.zip5, Address.QuebecCity.city, Address.QuebecCity.province.fullName, Address.QuebecCity.country.fullName);
        }

        // Added below condition to add alternate or shipping address to avoid failures in Automation.
        // We can add billing & shipping as same but sometime hidden elements represents different shipping address & hence we got some discrepancies in Actual Vs Expected tax calculations.
        checkoutPage.clickOnAddAlternateAddress();
        if (checkoutPage.verifyExistingShippingAddress(addressName)) {
            checkoutPage.selectExistingShippingAddressByName(addressName);
        } else {
            addressPage.addNewAddress(addressName, Address.QuebecCity.addressLine1, Address.QuebecCity.zip5, Address.QuebecCity.city, Address.QuebecCity.province.fullName, Address.QuebecCity.country.fullName);
        }

        // select payment type
        checkoutPage.choosePaymentMethod(PaymentMethodType.CASH_ON_DELIVERY.text);

        // Place order
        orderConfirmationPage = checkoutPage.clickPlaceOrderButton();

        // Storing order Number to validate on Commerce Manager
        orderNo = orderConfirmationPage.getOrderNo();

        // Assertion on Actual & Expected values
        storeOrderTax = orderConfirmationPage.getTaxCostFromUI();
        assertEquals(storeOrderTax, orderConfirmationPage.calculatePercentageBasedTax(EpiDataCommon.TaxRates.CANBC_CANQC_TAX.tax));

        // Launch Epi-Server's store
        launch324EpiStorePage();
        storeFrontHomePage = new EpiStoreFrontHomePage(driver);
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

    /**
     * CEPI-325 Test Case - Create Invoice with quantity 10
     */
    @Test(groups = {"epi_regression_v324"})
    public void salesOrderWithBulkQuantityTest() {
        addressName = Address.Washington.city + ", " + Address.Washington.state.abbreviation;
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
        cartPage.searchAndAddProductToCart(EpiDataCommon.ProductNames.PUMA_RED_LEATHER_LOAFERS.text);

        // Proceed to checkout
        checkoutPage = cartPage.proceedToCheckout();
        cartPage.changeProductQuantity(EpiDataCommon.ProductNames.FADED_GLORY_MENS_SHOES.text, EpiDataCommon.DefaultAmounts.PRODUCT_QUANTITY_10.text);

        // check existing address & if exists then select or add new address
        addressPage = new EpiAddressPage(driver);
        if (checkoutPage.verifyExistingBillingAddress(addressName)) {
            checkoutPage.selectExistingBillingAddressByName(addressName);
        } else {
            addressPage.addNewAddress(addressName, Address.Washington.addressLine1, Address.Washington.zip5, Address.Washington.city, Address.Washington.state.fullName, Address.Washington.country.fullName);
        }

        // Added below condition to add alternate or shipping address to avoid failures in Automation.
        // We can add billing & shipping as same but sometime hidden elements represents different shipping address & hence we got some discrepancies in Actual Vs Expected tax calculations.
        checkoutPage.clickOnAddAlternateAddress();
        if (checkoutPage.verifyExistingShippingAddress(addressName)) {
            checkoutPage.selectExistingShippingAddressByName(addressName);
        } else {
            addressPage.addNewAddress(addressName, Address.Washington.addressLine1, Address.Washington.zip5, Address.Washington.city, Address.Washington.state.fullName, Address.Washington.country.fullName);
        }

        // select payment type
        checkoutPage.choosePaymentMethod(PaymentMethodType.CASH_ON_DELIVERY.text);

        // Place order
        orderConfirmationPage = checkoutPage.clickPlaceOrderButton();

        // Storing order Number to validate on Commerce Manager
        orderNo = orderConfirmationPage.getOrderNo();

        // Assertion on Actual & Expected values
        storeOrderTax = orderConfirmationPage.getTaxCostFromUI();
        assertEquals(orderConfirmationPage.calculatePercentageBasedTax(EpiDataCommon.TaxRates.PA_WA_TAX.tax), storeOrderTax);

        // Launch Epi-Server's store
        launch324EpiStorePage();
        storeFrontHomePage = new EpiStoreFrontHomePage(driver);
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

    /**
     * CEPI-326 Test Case -Create Sales Order different ship and bill
     */
    @Test(groups = {"epi_regression_v324"})
    public void salesOrderWithDifferentBillShipTest() {
        addressName = Address.Delaware.city + ", " + Address.Delaware.state.abbreviation;
        String alternateAddress = Address.LosAngeles.city + ", " + Address.LosAngeles.state.abbreviation;
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

        // Proceed to checkout
        checkoutPage = cartPage.proceedToCheckout();

        // check existing address & if exists then select or add new address
        addressPage = new EpiAddressPage(driver);
        if (checkoutPage.verifyExistingBillingAddress(addressName)) {
            checkoutPage.selectExistingBillingAddressByName(addressName);
        } else {
            addressPage.addNewAddress(addressName, Address.Delaware.addressLine1, Address.Delaware.zip5, Address.Delaware.city, Address.Delaware.state.fullName, Address.Delaware.country.fullName);
        }

        // Add different shipping or alternate address
        checkoutPage.clickOnAddAlternateAddress();
        if (checkoutPage.verifyExistingShippingAddress(alternateAddress)) {
            checkoutPage.selectExistingShippingAddressByName(alternateAddress);
        } else {
            addressPage.addNewAddress(addressName, Address.Delaware.addressLine1, Address.Delaware.zip5, Address.Delaware.city, Address.Delaware.state.fullName, Address.Delaware.country.fullName);
        }

        // select payment type
        checkoutPage.choosePaymentMethod(PaymentMethodType.CASH_ON_DELIVERY.text);

        // Place order
        orderConfirmationPage = checkoutPage.clickPlaceOrderButton();

        // Storing order Number to validate on Commerce Manager
        orderNo = orderConfirmationPage.getOrderNo();

        // Assertion on Actual & Expected values
        storeOrderTax = orderConfirmationPage.getTaxCostFromUI();
        assertEquals(storeOrderTax, orderConfirmationPage.calculatePercentageBasedTax(TaxRate.LosAngeles.tax));

        // Launch Epi-Server's store
        launch324EpiStorePage();
        storeFrontHomePage = new EpiStoreFrontHomePage(driver);
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

    /**
     * CEPI-327 Test Case - End-to-End Test Create Sales Order and add/delete lines and change quantity and location
     */
    @Test(groups = {"epi_regression_v324"})
    public void endToEndSalesOrderTest() {
        addressName = Address.LosAngeles.city + ", " + Address.LosAngeles.state.abbreviation;
        String address2 = Address.Louisiana.city + ", " + Address.Louisiana.state.abbreviation;
        String address3 = Address.ColoradoSprings.city + ", " + Address.ColoradoSprings.state.abbreviation;
        double taxBeforeOrderPlaced;
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

        // Assertion on Taxation before placing an order
        assertEquals(checkoutPage.getTaxFromUIBeforeOrderPlaced(), checkoutPage.calculatePercentageBasedTaxBeforeOrderPlaced(TaxRate.LosAngeles.tax));

        // Adding more product(s) in the cart
        cartPage.searchAndAddProductToCart(EpiDataCommon.ProductNames.CLASSIC_MID_HEELED.text);

        // Proceed to checkout
        checkoutPage = cartPage.proceedToCheckout();

        // check existing address & if exists then select or add new address
        if (checkoutPage.verifyExistingBillingAddress(address2)) {
            checkoutPage.selectExistingBillingAddressByName(address2);
        } else {
            addressPage.addNewAddress(address2, Address.Louisiana.addressLine1, Address.Louisiana.zip5, Address.Louisiana.city, Address.Louisiana.state.fullName, Address.Louisiana.country.fullName);
        }

        // Added below condition to add alternate or shipping address to avoid failures in Automation.
        // We can add billing & shipping as same but sometime hidden elements represents different shipping address & hence we got some discrepancies in Actual Vs Expected tax calculations.
        checkoutPage.clickOnAddAlternateAddress();
        if (checkoutPage.verifyExistingShippingAddress(address2)) {
            checkoutPage.selectExistingShippingAddressByName(address2);
        } else {
            addressPage.addNewAddress(address2, Address.Louisiana.addressLine1, Address.Louisiana.zip5, Address.Louisiana.city, Address.Louisiana.state.fullName, Address.Louisiana.country.fullName);
        }

        // select payment type
        checkoutPage.choosePaymentMethod(PaymentMethodType.CASH_ON_DELIVERY.text);

        // Assertion on Taxation before placing an order
        assertEquals(checkoutPage.getTaxFromUIBeforeOrderPlaced(), checkoutPage.calculatePercentageBasedTaxBeforeOrderPlaced(EpiDataCommon.TaxRates.PA_LA_TAX.tax));

        // Clear all the items from the cart
        cartPage.clearAllItemsInCart();

        // Search for Product and Add to Cart
        cartPage.searchAndAddProductToCart(EpiDataCommon.ProductNames.FADED_GLORY_MENS_SHOES.text);
        cartPage.searchAndAddProductToCart(EpiDataCommon.ProductNames.PUMA_BLACK_SNEAKERS.text);
        cartPage.searchAndAddProductToCart(EpiDataCommon.ProductNames.WOMEN_CLOSED_TOE_SHOES.text);
        cartPage.searchAndAddProductToCart(EpiDataCommon.ProductNames.WOMEN_HIGH_HEEL_SHOES.text);

        // Proceed to checkout
        checkoutPage = cartPage.proceedToCheckout();

        // check existing address & if exists then select or add new address
        if (checkoutPage.verifyExistingBillingAddress(address3)) {
            checkoutPage.selectExistingBillingAddressByName(address3);
        } else {
            addressPage.addNewAddress(address3, Address.ColoradoSprings.addressLine1, Address.ColoradoSprings.zip5, Address.ColoradoSprings.city, Address.ColoradoSprings.state.fullName, Address.ColoradoSprings.country.fullName);
        }

        // Added below condition to add alternate or shipping address to avoid failures in Automation.
        // We can add billing & shipping as same but sometime hidden elements represents different shipping address & hence we got some discrepancies in Actual Vs Expected tax calculations.
        checkoutPage.clickOnAddAlternateAddress();
        if (checkoutPage.verifyExistingShippingAddress(address3)) {
            checkoutPage.selectExistingShippingAddressByName(address3);
        } else {
            addressPage.addNewAddress(address3, Address.ColoradoSprings.addressLine1, Address.ColoradoSprings.zip5, Address.ColoradoSprings.city, Address.ColoradoSprings.state.fullName, Address.ColoradoSprings.country.fullName);
        }

        // select payment type
        checkoutPage.choosePaymentMethod(PaymentMethodType.CASH_ON_DELIVERY.text);

        // Assertion on Taxation before placing an order
        taxBeforeOrderPlaced = checkoutPage.getTaxFromUIBeforeOrderPlaced();
        assertEquals(taxBeforeOrderPlaced, checkoutPage.calculatePercentageBasedTaxBeforeOrderPlaced(EpiDataCommon.TaxRates.PA_CO_TAX.tax));

        // Place order
        orderConfirmationPage = checkoutPage.clickPlaceOrderButton();

        // Storing order Number to validate on Commerce Manager
        orderNo = orderConfirmationPage.getOrderNo();

        // Assertion on Actual & Expected values
        storeOrderTax = orderConfirmationPage.getTaxCostFromUI();
        assertEquals(storeOrderTax, taxBeforeOrderPlaced);
        assertEquals(storeOrderTax, orderConfirmationPage.calculatePercentageBasedTax(EpiDataCommon.TaxRates.PA_CO_TAX.tax));

        // Launch Epi-Server's store
        launch324EpiStorePage();
        storeFrontHomePage = new EpiStoreFrontHomePage(driver);
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

    /**
     * CEPI-328 EPI - Test Case -Create Sales Order with taxpayer not registered in O Series or the Financial System
     */
    @Test(groups = {"epi_regression_v324"})
    public void salesOrderWithTaxPayerNotRegisteredTest() {
        addressName = Address.Wisconsin.city + ", " + Address.Wisconsin.state.abbreviation;
        try {
            // Launch Epi-Server's store
            launch324EpiStorePage();

            // Login to storefront
            storeLoginPage = new EpiStoreLoginPage(driver);
            storeLoginPage.loginToEpiStore(EpiDataCommon.EpiCredentials.EPI_324_STORE_USER.text, EpiDataCommon.EpiCredentials.EPI_324_STORE_PASS.text);
            storeFrontHomePage = new EpiStoreFrontHomePage(driver);
            storeFrontHomePage.navigateToHomePage();

            // navigate to Vertex O Series Page
            adminHomePage = new EpiAdminHomePage(driver);
            adminHomePage.clickOnMainMenu(NavigationAndMenuOptions.SiteNavigationOptions.CMS_EDIT.text);
            adminHomePage.clickOnSubMenu(NavigationAndMenuOptions.CmsMenuOptions.VERTEX_O_SERIES.text);
            epiOSeries = new EpiOSeriesPage(driver);
            epiOSeries.goToTab(NavigationAndMenuOptions.VertexOSeriesTabs.COMPANY_ADDRESS.text);
            epiOSeries.changeCompanyName(OSeriesData.EPI_NOT_REGISTERED_TAX_PAYER.data);
            epiOSeries.saveVertexOSeriesChanges();

            // Launch Epi-Server's store
            launch324EpiStorePage();
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

            // Proceed to checkout
            checkoutPage = cartPage.proceedToCheckout();

            // check existing address & if exists then select or add new address
            addressPage = new EpiAddressPage(driver);
            if (checkoutPage.verifyExistingBillingAddress(addressName)) {
                checkoutPage.selectExistingBillingAddressByName(addressName);
            } else {
                addressPage.addNewAddress(addressName, Address.Wisconsin.addressLine1, Address.Wisconsin.zip5, Address.Wisconsin.city, Address.Wisconsin.state.fullName, Address.Wisconsin.country.fullName);
            }

            // Added below condition to add alternate or shipping address to avoid failures in Automation.
            // We can add billing & shipping as same but sometime hidden elements represents different shipping address & hence we got some discrepancies in Actual Vs Expected tax calculations.
            checkoutPage.clickOnAddAlternateAddress();
            if (checkoutPage.verifyExistingShippingAddress(addressName)) {
                checkoutPage.selectExistingShippingAddressByName(addressName);
            } else {
                addressPage.addNewAddress(addressName, Address.Wisconsin.addressLine1, Address.Wisconsin.zip5, Address.Wisconsin.city, Address.Wisconsin.state.fullName, Address.Wisconsin.country.fullName);
            }

            // select payment type
            checkoutPage.choosePaymentMethod(PaymentMethodType.CASH_ON_DELIVERY.text);

            // Place order
            orderConfirmationPage = checkoutPage.clickPlaceOrderButton();

            // Storing order Number to validate on Commerce Manager
            orderNo = orderConfirmationPage.getOrderNo();

            // Assertion on Actual & Expected values
            storeOrderTax = orderConfirmationPage.getTaxCostFromUI();
            assertEquals(storeOrderTax, orderConfirmationPage.calculateIndividualPercentBaseTaxesAndDoAddition(EpiDataCommon.TaxRates.ZERO_TAX.tax, EpiDataCommon.TaxRates.PA_WI_TAX.tax));

            // Launch Epi-Server's store
            launch324EpiStorePage();
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
        } catch (Exception e) {
            e.printStackTrace();
            VertexLogger.log("Exception / Error occurred, please check logs for more details.");
            fail();
        } finally {
            // Set default address of Warehouse
            setDefaultWarehouseAddress324(EpiDataCommon.WarehouseNames.NEW_YORK_STORE.text);

            // Launch Epi-Server's store
            launch324EpiStorePage();
            storeFrontHomePage = new EpiStoreFrontHomePage(driver);
            storeFrontHomePage.navigateToHomePage();

            // navigate to Vertex O Series Page
            adminHomePage = new EpiAdminHomePage(driver);
            adminHomePage.clickOnMainMenu(NavigationAndMenuOptions.SiteNavigationOptions.CMS_EDIT.text);
            adminHomePage.clickOnSubMenu(NavigationAndMenuOptions.CmsMenuOptions.VERTEX_O_SERIES.text);
            epiOSeries = new EpiOSeriesPage(driver);
            epiOSeries.goToTab(NavigationAndMenuOptions.VertexOSeriesTabs.COMPANY_ADDRESS.text);
            epiOSeries.changeCompanyName(OSeriesData.EPI_REGISTERED_TAX_PAYER.data);
            epiOSeries.saveVertexOSeriesChanges();
        }
    }

    /**
     * CEPI-329 EPI - Test Case - Create Sale Order where the Customer is registered in O Series and the Financial System
     */
    @Test(groups = {"epi_regression_v324"})
    public void salesOrderWithRegisteredCustomerTest() {
        addressName = Address.SaltLakeCity.city + ", " + Address.SaltLakeCity.state.abbreviation;
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
        warehousePage.setWarehouseAddress(Address.Berwyn.addressLine1, Address.Berwyn.city, Address.Berwyn.state.fullName, Address.Berwyn.country.iso2code,
                Address.Berwyn.country.fullName, Address.Berwyn.zip5, Address.Berwyn.state.abbreviation);

        // Launch Epi-Server's store
        launch324EpiStorePage();
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
            addressPage.addNewAddress(addressName, Address.SaltLakeCity.addressLine1, Address.SaltLakeCity.zip5, Address.SaltLakeCity.city, Address.SaltLakeCity.state.fullName, Address.SaltLakeCity.country.fullName);
        }

        // Add different shipping or alternate address
        checkoutPage.clickOnAddAlternateAddress();
        if (checkoutPage.verifyExistingShippingAddress(addressName)) {
            checkoutPage.selectExistingShippingAddressByName(addressName);
        } else {
            addressPage.addNewAddress(addressName, Address.SaltLakeCity.addressLine1, Address.SaltLakeCity.zip5, Address.SaltLakeCity.city, Address.SaltLakeCity.state.fullName, Address.SaltLakeCity.country.fullName);
        }

        // select payment type
        checkoutPage.choosePaymentMethod(PaymentMethodType.CASH_ON_DELIVERY.text);

        // Place order
        orderConfirmationPage = checkoutPage.clickPlaceOrderButton();

        // Storing order Number to validate on Commerce Manager
        orderNo = orderConfirmationPage.getOrderNo();

        // Assertion on Actual & Expected values
        storeOrderTax = orderConfirmationPage.getTaxCostFromUI();
        assertEquals(storeOrderTax, orderConfirmationPage.calculatePercentageBasedTax(EpiDataCommon.TaxRates.PA_UT_TAX.tax));

        // Launch Epi-Server's store
        launch324EpiStorePage();
        storeFrontHomePage = new EpiStoreFrontHomePage(driver);
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
