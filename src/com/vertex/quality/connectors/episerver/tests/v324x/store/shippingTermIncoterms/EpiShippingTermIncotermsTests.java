package com.vertex.quality.connectors.episerver.tests.v324x.store.shippingTermIncoterms;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.enums.TaxRate;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.episerver.common.enums.*;
import com.vertex.quality.connectors.episerver.pages.epiCommon.*;
import com.vertex.quality.connectors.episerver.pages.v323x.*;
import com.vertex.quality.connectors.episerver.pages.v324x.EpiAdministrationHomePage;
import com.vertex.quality.connectors.episerver.pages.v324x.EpiOrderManagementHomePage;
import com.vertex.quality.connectors.episerver.pages.v324x.EpiOrdersPage;
import com.vertex.quality.connectors.episerver.pages.v324x.EpiWarehousePage;
import com.vertex.quality.connectors.oseriesfinal.ui.pages.taxpayers.OSeriesLoginPage;
import com.vertex.quality.connectors.oseriesfinal.ui.pages.taxpayers.OSeriesTaxpayers;
import com.vertex.quality.connectors.episerver.tests.base.EpiBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Test class that contains all test-cases of Shipping Term / Incoterms functionality.
 *
 * @author Shivam.Soni
 */
public class EpiShippingTermIncotermsTests extends EpiBaseTest {

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
     * CEPI-297 Test Case - Create Sales Invoice with Shipping - change shipping address (v3.2.4)
     */
    @Test(groups = {"epi_smoke_v324", "epi_regression_v324"})
    public void salesOrderWithShippingChangeShippingTest() {
        addressName = Address.Berwyn.city + ", " + Address.Berwyn.state.abbreviation;
        String address2 = Address.Washington.city + ", " + Address.Washington.state.abbreviation;
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
                addressPage.addNewAddress(addressName, Address.Berwyn.addressLine1, Address.Berwyn.zip5, Address.Berwyn.city, Address.Berwyn.state.fullName, Address.Berwyn.country.fullName);
            }

            // Added below condition to add alternate or shipping address to avoid failures in Automation.
            // We can add billing & shipping as same but sometime hidden elements represents different shipping address & hence we got some discrepancies in Actual Vs Expected tax calculations.
            checkoutPage.clickOnAddAlternateAddress();
            if (checkoutPage.verifyExistingShippingAddress(addressName)) {
                checkoutPage.selectExistingShippingAddressByName(addressName);
            } else {
                addressPage.addNewAddress(addressName, Address.Berwyn.addressLine1, Address.Berwyn.zip5, Address.Berwyn.city, Address.Berwyn.state.fullName, Address.Berwyn.country.fullName);
            }

            // select payment type
            checkoutPage.choosePaymentMethod(PaymentMethodType.CASH_ON_DELIVERY.text);

            // Assertion on Taxation before placing an order
            assertEquals(checkoutPage.getTaxFromUIBeforeOrderPlaced(), checkoutPage.calculatePercentageBasedTaxBeforeOrderPlaced(EpiDataCommon.TaxRates.PA_TAX.tax));

            // check existing address & if exists then select or add new address
            if (checkoutPage.verifyExistingBillingAddress(address2)) {
                checkoutPage.selectExistingBillingAddressByName(address2);
            } else {
                addressPage.addNewAddress(address2, Address.Washington.addressLine1, Address.Washington.zip5, Address.Washington.city, Address.Washington.state.fullName, Address.Washington.country.fullName);
            }

            // Added below condition to add alternate or shipping address to avoid failures in Automation.
            // We can add billing & shipping as same but sometime hidden elements represents different shipping address & hence we got some discrepancies in Actual Vs Expected tax calculations.
            checkoutPage.clickOnAddAlternateAddress();
            if (checkoutPage.verifyExistingShippingAddress(address2)) {
                checkoutPage.selectExistingShippingAddressByName(address2);
            } else {
                addressPage.addNewAddress(address2, Address.Washington.addressLine1, Address.Washington.zip5, Address.Washington.city, Address.Washington.state.fullName, Address.Washington.country.fullName);
            }

            // Place order
            orderConfirmationPage = checkoutPage.clickPlaceOrderButton();

            // Storing order Number to validate on Commerce Manager
            orderNo = orderConfirmationPage.getOrderNo();

            // Assertion on Actual & Expected values
            storeOrderTax = orderConfirmationPage.getTaxCostFromUI();
            assertEquals(storeOrderTax, orderConfirmationPage.calculatePercentageBasedTax(EpiDataCommon.TaxRates.PA_WA_TAX.tax));

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
     * CEPI-298 Test Case - Create Sales Order with shipping (v3.2.4)
     */
    @Test(groups = {"epi_regression_v324"})
    public void salesOrderWithShippingTest() {
        addressName = Address.Berwyn.city + ", " + Address.Berwyn.state.abbreviation;
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
                addressPage.addNewAddress(addressName, Address.Berwyn.addressLine1, Address.Berwyn.zip5, Address.Berwyn.city, Address.Berwyn.state.fullName, Address.Berwyn.country.fullName);
            }

            // Added below condition to add alternate or shipping address to avoid failures in Automation.
            // We can add billing & shipping as same but sometime hidden elements represents different shipping address & hence we got some discrepancies in Actual Vs Expected tax calculations.
            checkoutPage.clickOnAddAlternateAddress();
            if (checkoutPage.verifyExistingShippingAddress(addressName)) {
                checkoutPage.selectExistingShippingAddressByName(addressName);
            } else {
                addressPage.addNewAddress(addressName, Address.Berwyn.addressLine1, Address.Berwyn.zip5, Address.Berwyn.city, Address.Berwyn.state.fullName, Address.Berwyn.country.fullName);
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
     * CEPI-299 Test Case - Create Sales Order with shipping on a line item only (v3.2.4)
     */
    @Test(groups = {"epi_regression_v324"})
    public void salesOrderWithShippingOnLineItemTest() {
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
     * CEPI-300 Test Case - Create Sale Order for VAT with Shipping Terms CUS and Invoice
     */
    @Test(groups = {"epi_regression_v324"})
    public void salesOrderInvoiceCUSTermTest() {
        addressName = Address.Berlin.city + ", " + Address.Berlin.country.iso2code;
        try {
            // Select SUP shipping term in O-Series 9
            oSeriesLogin = new OSeriesLoginPage(driver);
            oSeriesLogin.loadOSeriesPage();
            oSeriesLogin.loginToOSeries(OSeriesData.O_SERIES_USER.data, OSeriesData.O_SERIES_PASSWORD.data);
            oSeriesTax = new OSeriesTaxpayers(driver);
            oSeriesTax.updateShippingTerms(true, OSeriesData.SHIP_TERM_CUS.data);

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
            assertEquals(storeOrderTax, orderConfirmationPage.calculatePercentageBasedTax(EpiDataCommon.TaxRates.SW_DE_CUS_TAX.tax, false));

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
     * CEPI-301 Test Case - Create Sale Order for VAT with Shipping Terms CUS and Invoice
     */
    @Test(groups = {"epi_regression_v324"})
    public void salesOrderInvoiceSUPTermTest() {
        addressName = Address.Berlin.city + ", " + Address.Berlin.country.iso2code;
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
            assertEquals(storeOrderTax, orderConfirmationPage.calculatePercentageBasedTax(EpiDataCommon.TaxRates.FR_DE_SUP_TAX.tax));

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
     * CEPI-302 Test Case - Create Sale Order for VAT with Shipping Terms EXW and Invoice
     */
    @Test(groups = {"epi_regression_v324"})
    public void salesOrderInvoiceEXWTermTest() {
        addressName = Address.Berlin.city + ", " + Address.Berlin.country.iso2code;
        try {
            // Select SUP shipping term in O-Series 9
            oSeriesLogin = new OSeriesLoginPage(driver);
            oSeriesLogin.loadOSeriesPage();
            oSeriesLogin.loginToOSeries(OSeriesData.O_SERIES_USER.data, OSeriesData.O_SERIES_PASSWORD.data);
            oSeriesTax = new OSeriesTaxpayers(driver);
            oSeriesTax.updateShippingTerms(true, OSeriesData.SHIP_TERM_EXW.data);

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
            assertEquals(storeOrderTax, orderConfirmationPage.calculatePercentageBasedTax(EpiDataCommon.TaxRates.FR_DE_EXW_TAX.tax));

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
     * CEPI-303 Test Case - Create Sale Order for VAT with Shipping Terms DDP and Invoice
     */
    @Test(groups = {"epi_regression_v324"})
    public void salesOrderInvoiceDDPTermTest() {
        addressName = Address.Berlin.city + ", " + Address.Berlin.country.iso2code;
        try {
            // Select SUP shipping term in O-Series 9
            oSeriesLogin = new OSeriesLoginPage(driver);
            oSeriesLogin.loadOSeriesPage();
            oSeriesLogin.loginToOSeries(OSeriesData.O_SERIES_USER.data, OSeriesData.O_SERIES_PASSWORD.data);
            oSeriesTax = new OSeriesTaxpayers(driver);
            oSeriesTax.updateShippingTerms(true, OSeriesData.SHIP_TERM_DDP.data);

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
            assertEquals(storeOrderTax, orderConfirmationPage.calculatePercentageBasedTax(EpiDataCommon.TaxRates.FR_DE_DDP_TAX.tax));

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
     * CEPI-304 Test Case - Create Sale Order with Shipping DDP for VAT (Intra EU DE-DE) and Invoice
     */
    @Test(groups = {"epi_regression_v324"})
    public void salesOrderInvoiceDDPTermDEDETest() {
        addressName = Address.BerlinAlternate.addressLine1 + ", " + Address.BerlinAlternate.city + ", " + Address.BerlinAlternate.country.iso2code;
        try {
            // Select SUP shipping term in O-Series 9
            oSeriesLogin = new OSeriesLoginPage(driver);
            oSeriesLogin.loadOSeriesPage();
            oSeriesLogin.loginToOSeries(OSeriesData.O_SERIES_USER.data, OSeriesData.O_SERIES_PASSWORD.data);
            oSeriesTax = new OSeriesTaxpayers(driver);
            oSeriesTax.updateShippingTerms(true, OSeriesData.SHIP_TERM_DDP.data);

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
                addressPage.addNewAddress(addressName, Address.BerlinAlternate.addressLine1, Address.BerlinAlternate.zip5, Address.BerlinAlternate.city, Address.BerlinAlternate.country.iso2code, Address.BerlinAlternate.country.fullName);
            }

            // Added below condition to add alternate or shipping address to avoid failures in Automation.
            // We can add billing & shipping as same but sometime hidden elements represents different shipping address & hence we got some discrepancies in Actual Vs Expected tax calculations.
            checkoutPage.clickOnAddAlternateAddress();
            if (checkoutPage.verifyExistingShippingAddress(addressName)) {
                checkoutPage.selectExistingShippingAddressByName(addressName);
            } else {
                addressPage.addNewAddress(addressName, Address.BerlinAlternate.addressLine1, Address.BerlinAlternate.zip5, Address.BerlinAlternate.city, Address.BerlinAlternate.country.iso2code, Address.BerlinAlternate.country.fullName);
            }

            // select payment type
            checkoutPage.choosePaymentMethod(PaymentMethodType.CASH_ON_DELIVERY.text);

            // Place order
            orderConfirmationPage = checkoutPage.clickPlaceOrderButton();

            // Storing order Number to validate on Commerce Manager
            orderNo = orderConfirmationPage.getOrderNo();

            // Assertion on Actual & Expected values
            storeOrderTax = orderConfirmationPage.getTaxCostFromUI();
            assertEquals(storeOrderTax, orderConfirmationPage.calculatePercentageBasedTax(EpiDataCommon.TaxRates.DE_DE_DDP_TAX.tax));

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
     * CEPI-305 Test Case - Create Sale Order with shipping terms SUP for APAC (SG-JP) and Invoice
     */
    @Test(groups = {"epi_regression_v324"})
    public void salesOrderInvoiceSUPTermAPACTest() {
        addressName = Address.Japan.city + ", " + Address.Japan.country.iso2code;
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
            warehousePage.setWarehouseAddress(Address.Singapore.addressLine1, Address.Singapore.city, Address.Singapore.city, Address.Singapore.country.iso2code,
                    Address.Singapore.country.fullName, Address.Singapore.zip5, Address.Singapore.country.iso2code);

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
                addressPage.addNewAddress(addressName, Address.Japan.addressLine1, Address.Japan.zip5, Address.Japan.city, Address.Japan.country.iso2code, Address.Japan.country.fullName);
            }

            // Added below condition to add alternate or shipping address to avoid failures in Automation.
            // We can add billing & shipping as same but sometime hidden elements represents different shipping address & hence we got some discrepancies in Actual Vs Expected tax calculations.
            checkoutPage.clickOnAddAlternateAddress();
            if (checkoutPage.verifyExistingShippingAddress(addressName)) {
                checkoutPage.selectExistingShippingAddressByName(addressName);
            } else {
                addressPage.addNewAddress(addressName, Address.Japan.addressLine1, Address.Japan.zip5, Address.Japan.city, Address.Japan.country.iso2code, Address.Japan.country.fullName);
            }

            // select payment type
            checkoutPage.choosePaymentMethod(PaymentMethodType.CASH_ON_DELIVERY.text);

            // Place order
            orderConfirmationPage = checkoutPage.clickPlaceOrderButton();

            // Storing order Number to validate on Commerce Manager
            orderNo = orderConfirmationPage.getOrderNo();

            // Assertion on Actual & Expected values
            storeOrderTax = orderConfirmationPage.getTaxCostFromUI();
            assertEquals(storeOrderTax, orderConfirmationPage.calculatePercentageBasedTax(EpiDataCommon.TaxRates.SG_JP_SUP_TAX.tax));

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
}
