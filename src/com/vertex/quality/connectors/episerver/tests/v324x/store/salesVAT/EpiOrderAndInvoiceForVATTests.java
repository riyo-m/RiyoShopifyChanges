package com.vertex.quality.connectors.episerver.tests.v324x.store.salesVAT;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.enums.TaxRate;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.episerver.common.enums.EpiDataCommon;
import com.vertex.quality.connectors.episerver.common.enums.NavigationAndMenuOptions;
import com.vertex.quality.connectors.episerver.common.enums.OSeriesData;
import com.vertex.quality.connectors.episerver.common.enums.PaymentMethodType;
import com.vertex.quality.connectors.episerver.pages.epiCommon.*;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiAdminHomePage;
import com.vertex.quality.connectors.episerver.pages.v324x.EpiAdministrationHomePage;
import com.vertex.quality.connectors.episerver.pages.v324x.EpiOrderManagementHomePage;
import com.vertex.quality.connectors.episerver.pages.v324x.EpiOrdersPage;
import com.vertex.quality.connectors.episerver.pages.v324x.EpiWarehousePage;
import com.vertex.quality.connectors.episerver.tests.base.EpiBaseTest;
import com.vertex.quality.connectors.oseriesfinal.ui.pages.taxpayers.OSeriesLoginPage;
import com.vertex.quality.connectors.oseriesfinal.ui.pages.taxpayers.OSeriesTaxpayers;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Test class that contains all the tests related to Sales Order VAT & Invoice for VAT
 *
 * @author Shivam.Soni
 */
public class EpiOrderAndInvoiceForVATTests extends EpiBaseTest {

    OSeriesLoginPage oSeriesLogin;
    OSeriesTaxpayers oSeriesTax;
    EpiAdminHomePage adminHomePage;
    EpiAdministrationHomePage administrationHomePage;
    EpiWarehousePage warehousePage;
    EpiStoreLoginPage storeLoginPage;
    EpiStoreFrontHomePage storeFrontHomePage;
    EpiCartPage cartPage;
    EpiCheckoutPage checkoutPage;
    EpiAddressPage addressPage;
    EpiOrderManagementHomePage orderManagementHomePage;
    EpiOrdersPage ordersPage;
    EpiOrderConfirmationPage orderConfirmationPage;
    String addressName;
    String orderNo;
    double storeOrderTax;

    /**
     * CEPI-306 Test Case -Consignment Sales Order Invoice for VAT (DE FR)
     */
    @Test(groups = {"epi_smoke_v324", "epi_regression_v324"})
    public void salesOrderInvoiceDEFRVATTest() {
        addressName = Address.Marseille.city + ", " + Address.Marseille.country.iso2code;
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
        warehousePage.setWarehouseAddress(Address.Berlin.addressLine1, Address.Berlin.city, Address.Berlin.city, Address.Berlin.country.iso2code,
                Address.Berlin.country.fullName, Address.Berlin.zip5, Address.Berlin.country.iso2code);

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
            addressPage.addNewAddress(addressName, Address.Marseille.addressLine1, Address.Marseille.zip5, Address.Marseille.city, Address.Marseille.country.iso2code, Address.Marseille.country.fullName);
        }

        // Added below condition to add alternate or shipping address to avoid failures in Automation.
        // We can add billing & shipping as same but sometime hidden elements represents different shipping address & hence we got some discrepancies in Actual Vs Expected tax calculations.
        checkoutPage.clickOnAddAlternateAddress();
        if (checkoutPage.verifyExistingShippingAddress(addressName)) {
            checkoutPage.selectExistingShippingAddressByName(addressName);
        } else {
            addressPage.addNewAddress(addressName, Address.Marseille.addressLine1, Address.Marseille.zip5, Address.Marseille.city, Address.Marseille.country.iso2code, Address.Marseille.country.fullName);
        }

        // select payment type
        checkoutPage.choosePaymentMethod(PaymentMethodType.CASH_ON_DELIVERY.text);

        // Place order
        orderConfirmationPage = checkoutPage.clickPlaceOrderButton();

        // Storing order Number to validate on Commerce Manager
        orderNo = orderConfirmationPage.getOrderNo();

        // Assertion on Actual & Expected values
        storeOrderTax = orderConfirmationPage.getTaxCostFromUI();
        assertEquals(storeOrderTax, orderConfirmationPage.calculatePercentageBasedTax(EpiDataCommon.TaxRates.DE_FR_TAX.tax));

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
     * CEPI-307 Test Case - Create Sale Order for VAT (EU-US) and Invoice
     */
    @Test(groups = {"epi_regression_v324"})
    public void salesOrderInvoiceEUUSVATTest() {
        addressName = Address.UniversalCity.city + ", " + Address.UniversalCity.state.abbreviation;
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
        warehousePage.setWarehouseAddress(Address.Berlin.addressLine1, Address.Berlin.city, Address.Berlin.city, Address.Berlin.country.iso2code,
                Address.Berlin.country.fullName, Address.Berlin.zip5, Address.Berlin.country.iso2code);

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
            addressPage.addNewAddress(addressName, Address.UniversalCity.addressLine1, Address.UniversalCity.zip5, Address.UniversalCity.city, Address.UniversalCity.state.fullName, Address.UniversalCity.country.fullName);
        }

        // Added below condition to add alternate or shipping address to avoid failures in Automation.
        // We can add billing & shipping as same but sometime hidden elements represents different shipping address & hence we got some discrepancies in Actual Vs Expected tax calculations.
        checkoutPage.clickOnAddAlternateAddress();
        if (checkoutPage.verifyExistingShippingAddress(addressName)) {
            checkoutPage.selectExistingShippingAddressByName(addressName);
        } else {
            addressPage.addNewAddress(addressName, Address.UniversalCity.addressLine1, Address.UniversalCity.zip5, Address.UniversalCity.city, Address.UniversalCity.state.fullName, Address.UniversalCity.country.fullName);
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
     * CEPI-308 Test Case - Create Sale Order for VAT (US-EU) and Invoice
     */
    @Test(groups = {"epi_regression_v324"})
    public void salesOrderInvoiceUSEUVATTest() {
        addressName = Address.Paris.city + ", " + Address.Paris.country.iso2code;
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
            addressPage.addNewAddress(addressName, Address.Paris.addressLine1, Address.Paris.zip5, Address.Paris.city, Address.Paris.country.iso2code, Address.Paris.country.fullName);
        }

        // Added below condition to add alternate or shipping address to avoid failures in Automation.
        // We can add billing & shipping as same but sometime hidden elements represents different shipping address & hence we got some discrepancies in Actual Vs Expected tax calculations.
        checkoutPage.clickOnAddAlternateAddress();
        if (checkoutPage.verifyExistingShippingAddress(addressName)) {
            checkoutPage.selectExistingShippingAddressByName(addressName);
        } else {
            addressPage.addNewAddress(addressName, Address.Paris.addressLine1, Address.Paris.zip5, Address.Paris.city, Address.Paris.country.iso2code, Address.Paris.country.fullName);
        }

        // select payment type
        checkoutPage.choosePaymentMethod(PaymentMethodType.CASH_ON_DELIVERY.text);

        // Place order
        orderConfirmationPage = checkoutPage.clickPlaceOrderButton();

        // Storing order Number to validate on Commerce Manager
        orderNo = orderConfirmationPage.getOrderNo();

        // Assertion on Actual & Expected values
        storeOrderTax = orderConfirmationPage.getTaxCostFromUI();
        assertEquals(storeOrderTax, orderConfirmationPage.calculatePercentageBasedTax(EpiDataCommon.TaxRates.US_FR_TAX.tax));

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
     * CEPI-309 Test Case - Create Sale Order for VAT (Intra EU FR-DE) and Invoice
     */
    @Test(groups = {"epi_regression_v324"})
    public void salesOrderInvoiceFRDEVATTest() {
        addressName = Address.Berlin.city + ", " + Address.Berlin.country.iso2code;
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
        warehousePage.setWarehouseAddress(Address.Paris.addressLine1, Address.Paris.city, Address.Paris.city, Address.Paris.country.iso2code,
                Address.Paris.country.fullName, Address.Paris.zip5, Address.Paris.country.iso2code);

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

        // select payment type
        checkoutPage.choosePaymentMethod(PaymentMethodType.CASH_ON_DELIVERY.text);

        // Place order
        orderConfirmationPage = checkoutPage.clickPlaceOrderButton();

        // Storing order Number to validate on Commerce Manager
        orderNo = orderConfirmationPage.getOrderNo();

        // Assertion on Actual & Expected values
        storeOrderTax = orderConfirmationPage.getTaxCostFromUI();
        assertEquals(storeOrderTax, orderConfirmationPage.calculatePercentageBasedTax(EpiDataCommon.TaxRates.FR_DE_TAX.tax));

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
     * CEPI-310 Test Case - Create Sale Order for VAT (Greek Territory) and Invoice
     */
    @Test(groups = {"epi_regression_v324"})
    public void salesOrderInvoiceGreekTerritoryVATTest() {
        addressName = Address.Analipsi.city + ", " + Address.Analipsi.country.iso2code;
        try {
            // Select SUP shipping term in O-Series 9
            oSeriesLogin = new OSeriesLoginPage(driver);
            oSeriesLogin.loadOSeriesPage();
            oSeriesLogin.loginToOSeries(OSeriesData.O_SERIES_USER.data, OSeriesData.O_SERIES_PASSWORD.data);
            oSeriesTax = new OSeriesTaxpayers(driver);
            oSeriesTax.updateShippingTerms(true, OSeriesData.SHIP_TERM_SUP.data);

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
            warehousePage.setWarehouseAddress(Address.Paris.addressLine1, Address.Paris.city, Address.Paris.city, Address.Paris.country.iso2code,
                    Address.Paris.country.fullName, Address.Paris.zip5, Address.Paris.country.iso2code);

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
                addressPage.addNewAddress(addressName, Address.Analipsi.addressLine1, Address.Analipsi.zip5, Address.Analipsi.city, Address.Analipsi.country.iso2code, Address.Analipsi.country.fullName);
            }

            // Added below condition to add alternate or shipping address to avoid failures in Automation.
            // We can add billing & shipping as same but sometime hidden elements represents different shipping address & hence we got some discrepancies in Actual Vs Expected tax calculations.
            checkoutPage.clickOnAddAlternateAddress();
            if (checkoutPage.verifyExistingShippingAddress(addressName)) {
                checkoutPage.selectExistingShippingAddressByName(addressName);
            } else {
                addressPage.addNewAddress(addressName, Address.Analipsi.addressLine1, Address.Analipsi.zip5, Address.Analipsi.city, Address.Analipsi.country.iso2code, Address.Analipsi.country.fullName);
            }

            // select payment type
            checkoutPage.choosePaymentMethod(PaymentMethodType.CASH_ON_DELIVERY.text);

            // Place order
            orderConfirmationPage = checkoutPage.clickPlaceOrderButton();

            // Storing order Number to validate on Commerce Manager
            orderNo = orderConfirmationPage.getOrderNo();

            // Assertion on Actual & Expected values
            storeOrderTax = orderConfirmationPage.getTaxCostFromUI();
            assertEquals(storeOrderTax, orderConfirmationPage.calculatePercentageBasedTax(EpiDataCommon.TaxRates.FR_GR_SUP_TAX.tax));

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
        } catch (Exception e) {
            e.printStackTrace();
            VertexLogger.log("Test failed due to error/exception, kindly check logs for more details");
            Assert.fail();
        } finally {
            // Set default address of Warehouse
            setDefaultWarehouseAddress324(EpiDataCommon.WarehouseNames.NEW_YORK_STORE.text);

            // Restore default shipping term from O-Series 9
            oSeriesLogin = new OSeriesLoginPage(driver);
            oSeriesLogin.loadOSeriesPage();
            oSeriesLogin.loginToOSeries(OSeriesData.O_SERIES_USER.data, OSeriesData.O_SERIES_PASSWORD.data);
            if (oSeriesLogin.verifyLoginPage()) {
                oSeriesLogin.loginToOSeries(OSeriesData.O_SERIES_USER.data, OSeriesData.O_SERIES_PASSWORD.data);
            }
            oSeriesTax = new OSeriesTaxpayers(driver);
            oSeriesTax.updateShippingTerms(true, OSeriesData.SHIP_TERM_SUP.data);
        }
    }

    /**
     * CEPI-311 Test Case - Create Sale Order for VAT (Austrian Sub-Division) and Invoice
     */
    @Test(groups = {"epi_regression_v324"})
    public void salesOrderInvoiceAustrianDivisionVATTest() {
        addressName = Address.Mittelberg.city + ", " + Address.Mittelberg.country.iso2code;
        try {
            // Select SUP shipping term in O-Series 9
            oSeriesLogin = new OSeriesLoginPage(driver);
            oSeriesLogin.loadOSeriesPage();
            oSeriesLogin.loginToOSeries(OSeriesData.O_SERIES_USER.data, OSeriesData.O_SERIES_PASSWORD.data);
            oSeriesTax = new OSeriesTaxpayers(driver);
            oSeriesTax.updateShippingTerms(true, OSeriesData.SHIP_TERM_SUP.data);

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
            warehousePage.setWarehouseAddress(Address.Berlin.addressLine1, Address.Berlin.city, Address.Berlin.city, Address.Berlin.country.iso2code,
                    Address.Berlin.country.fullName, Address.Berlin.zip5, Address.Berlin.country.iso2code);

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
                addressPage.addNewAddress(addressName, Address.Mittelberg.addressLine1, Address.Mittelberg.zip5, Address.Mittelberg.city, Address.Mittelberg.country.iso2code, Address.Mittelberg.country.fullName);
            }

            // Added below condition to add alternate or shipping address to avoid failures in Automation.
            // We can add billing & shipping as same but sometime hidden elements represents different shipping address & hence we got some discrepancies in Actual Vs Expected tax calculations.
            checkoutPage.clickOnAddAlternateAddress();
            if (checkoutPage.verifyExistingShippingAddress(addressName)) {
                checkoutPage.selectExistingShippingAddressByName(addressName);
            } else {
                addressPage.addNewAddress(addressName, Address.Mittelberg.addressLine1, Address.Mittelberg.zip5, Address.Mittelberg.city, Address.Mittelberg.country.iso2code, Address.Mittelberg.country.fullName);
            }

            // select payment type
            checkoutPage.choosePaymentMethod(PaymentMethodType.CASH_ON_DELIVERY.text);

            // Place order
            orderConfirmationPage = checkoutPage.clickPlaceOrderButton();

            // Storing order Number to validate on Commerce Manager
            orderNo = orderConfirmationPage.getOrderNo();

            // Assertion on Actual & Expected values
            storeOrderTax = orderConfirmationPage.getTaxCostFromUI();
            assertEquals(storeOrderTax, orderConfirmationPage.calculatePercentageBasedTax(EpiDataCommon.TaxRates.DE_AT_SUP_TAX.tax));

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
        } catch (Exception e) {
            e.printStackTrace();
            VertexLogger.log("Test failed due to error/exception, kindly check logs for more details");
            Assert.fail();
        } finally {
            // Set default address of Warehouse
            setDefaultWarehouseAddress324(EpiDataCommon.WarehouseNames.NEW_YORK_STORE.text);

            // Restore default shipping term from O-Series 9
            oSeriesLogin = new OSeriesLoginPage(driver);
            oSeriesLogin.loadOSeriesPage();
            oSeriesLogin.loginToOSeries(OSeriesData.O_SERIES_USER.data, OSeriesData.O_SERIES_PASSWORD.data);
            if (oSeriesLogin.verifyLoginPage()) {
                oSeriesLogin.loginToOSeries(OSeriesData.O_SERIES_USER.data, OSeriesData.O_SERIES_PASSWORD.data);
            }
            oSeriesTax = new OSeriesTaxpayers(driver);
            oSeriesTax.updateShippingTerms(true, OSeriesData.SHIP_TERM_SUP.data);
        }
    }

    /**
     * CEPI-312 Test Case - Create Sale Order for APAC (India) and Invoice
     */
    @Test(groups = {"epi_regression_v324"})
    public void salesOrderInvoiceAPACIndiaTest() {
        addressName = Address.NewRamdaspeth.city + ", " + Address.NewRamdaspeth.state.abbreviation;
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
        warehousePage.setWarehouseAddress(Address.Chandigarh.addressLine1, Address.Chandigarh.city, Address.Chandigarh.state.fullName, Address.Chandigarh.country.iso2code,
                Address.Chandigarh.country.fullName, Address.Chandigarh.zip5, Address.Chandigarh.state.abbreviation);

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
            addressPage.addNewAddress(addressName, Address.NewRamdaspeth.addressLine1, Address.NewRamdaspeth.zip5, Address.NewRamdaspeth.city, Address.NewRamdaspeth.state.fullName, Address.NewRamdaspeth.country.fullName);
        }

        // Added below condition to add alternate or shipping address to avoid failures in Automation.
        // We can add billing & shipping as same but sometime hidden elements represents different shipping address & hence we got some discrepancies in Actual Vs Expected tax calculations.
        checkoutPage.clickOnAddAlternateAddress();
        if (checkoutPage.verifyExistingShippingAddress(addressName)) {
            checkoutPage.selectExistingShippingAddressByName(addressName);
        } else {
            addressPage.addNewAddress(addressName, Address.NewRamdaspeth.addressLine1, Address.NewRamdaspeth.zip5, Address.NewRamdaspeth.city, Address.NewRamdaspeth.state.fullName, Address.NewRamdaspeth.country.fullName);
        }

        // select payment type
        checkoutPage.choosePaymentMethod(PaymentMethodType.CASH_ON_DELIVERY.text);

        // Place order
        orderConfirmationPage = checkoutPage.clickPlaceOrderButton();

        // Storing order Number to validate on Commerce Manager
        orderNo = orderConfirmationPage.getOrderNo();

        // Assertion on Actual & Expected values
        storeOrderTax = orderConfirmationPage.getTaxCostFromUI();
        assertEquals(storeOrderTax, orderConfirmationPage.calculatePercentageBasedTax(EpiDataCommon.TaxRates.INDIA_GST.tax));

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
     * CEPI-313 Test Case - Create Sale Order for APAC (Hong Kong no tax) and Invoice
     */
    @Test(groups = {"epi_regression_v324"})
    public void salesOrderInvoiceHKGNoTaxTest() {
        addressName = Address.ShuenWan.city + ", " + Address.ShuenWan.country.iso2code;
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
        warehousePage.setWarehouseAddress(Address.MongKok.addressLine1, Address.MongKok.city, Address.MongKok.city, Address.MongKok.country.iso2code,
                Address.MongKok.country.fullName, Address.MongKok.zip5, Address.MongKok.country.iso2code);

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
            addressPage.addNewAddress(addressName, Address.ShuenWan.addressLine1, Address.ShuenWan.zip5, Address.ShuenWan.city, Address.ShuenWan.country.iso2code, Address.ShuenWan.country.fullName);
        }

        // Added below condition to add alternate or shipping address to avoid failures in Automation.
        // We can add billing & shipping as same but sometime hidden elements represents different shipping address & hence we got some discrepancies in Actual Vs Expected tax calculations.
        checkoutPage.clickOnAddAlternateAddress();
        if (checkoutPage.verifyExistingShippingAddress(addressName)) {
            checkoutPage.selectExistingShippingAddressByName(addressName);
        } else {
            addressPage.addNewAddress(addressName, Address.ShuenWan.addressLine1, Address.ShuenWan.zip5, Address.ShuenWan.city, Address.ShuenWan.country.iso2code, Address.ShuenWan.country.fullName);
        }

        // select payment type
        checkoutPage.choosePaymentMethod(PaymentMethodType.CASH_ON_DELIVERY.text);

        // Place order
        orderConfirmationPage = checkoutPage.clickPlaceOrderButton();

        // Storing order Number to validate on Commerce Manager
        orderNo = orderConfirmationPage.getOrderNo();

        // Assertion on Actual & Expected values
        storeOrderTax = orderConfirmationPage.getTaxCostFromUI();
        assertEquals(storeOrderTax, orderConfirmationPage.calculatePercentageBasedTax(EpiDataCommon.TaxRates.ZERO_TAX.tax));

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
     * CEPI-314 Test Case - Create Sale Order for LATAM (BZ-BZ) and Invoice
     */
    @Test(groups = {"epi_regression_v324"})
    public void salesOrderInvoiceLATAMBZBZTest() {
        addressName = Address.SanIgnacio.city + ", " + Address.SanIgnacio.country.iso2code;
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
        warehousePage.setWarehouseAddress(Address.BelizeCity.addressLine1, Address.BelizeCity.city, Address.BelizeCity.city, Address.BelizeCity.country.iso2code,
                Address.BelizeCity.country.fullName, Address.BelizeCity.zip5, Address.BelizeCity.country.iso2code);

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
            addressPage.addNewAddress(addressName, Address.SanIgnacio.addressLine1, Address.SanIgnacio.zip5, Address.SanIgnacio.city, Address.SanIgnacio.country.iso2code, Address.SanIgnacio.country.fullName);
        }

        // Added below condition to add alternate or shipping address to avoid failures in Automation.
        // We can add billing & shipping as same but sometime hidden elements represents different shipping address & hence we got some discrepancies in Actual Vs Expected tax calculations.
        checkoutPage.clickOnAddAlternateAddress();
        if (checkoutPage.verifyExistingShippingAddress(addressName)) {
            checkoutPage.selectExistingShippingAddressByName(addressName);
        } else {
            addressPage.addNewAddress(addressName, Address.SanIgnacio.addressLine1, Address.SanIgnacio.zip5, Address.SanIgnacio.city, Address.SanIgnacio.country.iso2code, Address.SanIgnacio.country.fullName);
        }

        // select payment type
        checkoutPage.choosePaymentMethod(PaymentMethodType.CASH_ON_DELIVERY.text);

        // Place order
        orderConfirmationPage = checkoutPage.clickPlaceOrderButton();

        // Storing order Number to validate on Commerce Manager
        orderNo = orderConfirmationPage.getOrderNo();

        // Assertion on Actual & Expected values
        storeOrderTax = orderConfirmationPage.getTaxCostFromUI();
        assertEquals(storeOrderTax, orderConfirmationPage.calculatePercentageBasedTax(EpiDataCommon.TaxRates.LATAM_BZ_BZ_TAX.tax));

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
     * CEPI-315 Test Case - Create Sale Order for LATAM (CR-CO) and Invoice
     */
    @Test(groups = {"epi_regression_v324"})
    public void salesOrderInvoiceLATAMCRCOTest() {
        addressName = Address.Borgota.city + ", " + Address.Borgota.country.iso2code;
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
        warehousePage.setWarehouseAddress(Address.SanJose.addressLine1, Address.SanJose.city, Address.SanJose.city, Address.SanJose.country.iso2code,
                Address.SanJose.country.fullName, Address.SanJose.zip5, Address.SanJose.country.iso2code);

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
            addressPage.addNewAddress(addressName, Address.Borgota.addressLine1, Address.Borgota.zip5, Address.Borgota.city, Address.Borgota.country.iso2code, Address.Borgota.country.fullName);
        }

        // Added below condition to add alternate or shipping address to avoid failures in Automation.
        // We can add billing & shipping as same but sometime hidden elements represents different shipping address & hence we got some discrepancies in Actual Vs Expected tax calculations.
        checkoutPage.clickOnAddAlternateAddress();
        if (checkoutPage.verifyExistingShippingAddress(addressName)) {
            checkoutPage.selectExistingShippingAddressByName(addressName);
        } else {
            addressPage.addNewAddress(addressName, Address.Borgota.addressLine1, Address.Borgota.zip5, Address.Borgota.city, Address.Borgota.country.iso2code, Address.Borgota.country.fullName);
        }

        // select payment type
        checkoutPage.choosePaymentMethod(PaymentMethodType.CASH_ON_DELIVERY.text);

        // Place order
        orderConfirmationPage = checkoutPage.clickPlaceOrderButton();

        // Storing order Number to validate on Commerce Manager
        orderNo = orderConfirmationPage.getOrderNo();

        // Assertion on Actual & Expected values
        storeOrderTax = orderConfirmationPage.getTaxCostFromUI();
        assertEquals(storeOrderTax, orderConfirmationPage.calculatePercentageBasedTax(EpiDataCommon.TaxRates.CR_CO_TAX.tax));

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
     * CEPI-316 Test Case - Create Sale Order for APAC (India) CGST, SGST and Invoice
     */
    @Test(groups = {"epi_regression_v324"})
    public void salesOrderInvoiceAPACIndiaCGSTSGSTTest() {
        addressName = Address.NewRamdaspeth.city + ", " + Address.NewRamdaspeth.state.abbreviation;
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
        warehousePage.setWarehouseAddress(Address.NewRamdaspethAlternate.addressLine1, Address.NewRamdaspethAlternate.city, Address.NewRamdaspethAlternate.state.fullName, Address.NewRamdaspethAlternate.country.iso2code,
                Address.NewRamdaspethAlternate.country.fullName, Address.NewRamdaspethAlternate.zip5, Address.NewRamdaspethAlternate.state.abbreviation);

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
            addressPage.addNewAddress(addressName, Address.NewRamdaspeth.addressLine1, Address.NewRamdaspeth.zip5, Address.NewRamdaspeth.city, Address.NewRamdaspeth.state.fullName, Address.NewRamdaspeth.country.fullName);
        }

        // Added below condition to add alternate or shipping address to avoid failures in Automation.
        // We can add billing & shipping as same but sometime hidden elements represents different shipping address & hence we got some discrepancies in Actual Vs Expected tax calculations.
        checkoutPage.clickOnAddAlternateAddress();
        if (checkoutPage.verifyExistingShippingAddress(addressName)) {
            checkoutPage.selectExistingShippingAddressByName(addressName);
        } else {
            addressPage.addNewAddress(addressName, Address.NewRamdaspeth.addressLine1, Address.NewRamdaspeth.zip5, Address.NewRamdaspeth.city, Address.NewRamdaspeth.state.fullName, Address.NewRamdaspeth.country.fullName);
        }

        // select payment type
        checkoutPage.choosePaymentMethod(PaymentMethodType.CASH_ON_DELIVERY.text);

        // Place order
        orderConfirmationPage = checkoutPage.clickPlaceOrderButton();

        // Storing order Number to validate on Commerce Manager
        orderNo = orderConfirmationPage.getOrderNo();

        // Assertion on Actual & Expected values
        storeOrderTax = orderConfirmationPage.getTaxCostFromUI();
        assertEquals(storeOrderTax, orderConfirmationPage.calculatePercentageBasedTax(EpiDataCommon.TaxRates.INDIA_GST.tax));

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
     * CEPI-317 Test Case - Create Sales Order for APAC (India) and Invoice - InterState
     */
    @Test(groups = {"epi_regression_v324"})
    public void salesOrderInvoiceAPACIndiaInterStateTest() {
        addressName = Address.NewRamdaspeth.city + ", " + Address.NewRamdaspeth.state.abbreviation;
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
        warehousePage.setWarehouseAddress(Address.PortBlair.addressLine1, Address.PortBlair.city, Address.PortBlair.state.fullName, Address.PortBlair.country.iso2code,
                Address.PortBlair.country.fullName, Address.PortBlair.zip5, Address.PortBlair.state.abbreviation);

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
            addressPage.addNewAddress(addressName, Address.NewRamdaspeth.addressLine1, Address.NewRamdaspeth.zip5, Address.NewRamdaspeth.city, Address.NewRamdaspeth.state.fullName, Address.NewRamdaspeth.country.fullName);
        }

        // Added below condition to add alternate or shipping address to avoid failures in Automation.
        // We can add billing & shipping as same but sometime hidden elements represents different shipping address & hence we got some discrepancies in Actual Vs Expected tax calculations.
        checkoutPage.clickOnAddAlternateAddress();
        if (checkoutPage.verifyExistingShippingAddress(addressName)) {
            checkoutPage.selectExistingShippingAddressByName(addressName);
        } else {
            addressPage.addNewAddress(addressName, Address.NewRamdaspeth.addressLine1, Address.NewRamdaspeth.zip5, Address.NewRamdaspeth.city, Address.NewRamdaspeth.state.fullName, Address.NewRamdaspeth.country.fullName);
        }

        // select payment type
        checkoutPage.choosePaymentMethod(PaymentMethodType.CASH_ON_DELIVERY.text);

        // Place order
        orderConfirmationPage = checkoutPage.clickPlaceOrderButton();

        // Storing order Number to validate on Commerce Manager
        orderNo = orderConfirmationPage.getOrderNo();

        // Assertion on Actual & Expected values
        storeOrderTax = orderConfirmationPage.getTaxCostFromUI();
        assertEquals(storeOrderTax, orderConfirmationPage.calculatePercentageBasedTax(EpiDataCommon.TaxRates.INDIA_GST.tax));

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
