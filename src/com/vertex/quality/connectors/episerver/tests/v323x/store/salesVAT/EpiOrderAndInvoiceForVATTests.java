package com.vertex.quality.connectors.episerver.tests.v323x.store.salesVAT;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.enums.TaxRate;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.episerver.common.enums.CommerceManagerNavigationOptions;
import com.vertex.quality.connectors.episerver.common.enums.EpiDataCommon;
import com.vertex.quality.connectors.episerver.common.enums.OSeriesData;
import com.vertex.quality.connectors.episerver.common.enums.PaymentMethodType;
import com.vertex.quality.connectors.episerver.pages.epiCommon.*;
import com.vertex.quality.connectors.episerver.pages.oseries.OSeriesLoginPage;
import com.vertex.quality.connectors.episerver.pages.oseries.OSeriesTaxpayers;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiCommerceManagerAdministration;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiCommerceManagerHomePage;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiCommerceManagerLoginPage;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiCommerceManagerOrderManagement;
import com.vertex.quality.connectors.episerver.tests.base.EpiBaseTest;
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
    EpiCommerceManagerLoginPage commerceManagerLoginPage;
    EpiCommerceManagerHomePage commerceManagerHomePage;
    EpiCommerceManagerAdministration commerceManagerAdministration;
    EpiCommerceManagerOrderManagement commerceManagerOrderManagement;
    EpiStoreLoginPage storeLoginPage;
    EpiStoreFrontHomePage storeFrontHomePage;
    EpiCartPage cartPage;
    EpiCheckoutPage checkoutPage;
    EpiAddressPage addressPage;
    EpiOrderConfirmationPage orderConfirmationPage;
    String addressName;
    String orderNo;
    String pickListName;
    String trackingNo;
    double storeOrderTax;

    /**
     * CEPI-243 Test Case -Consignment Sales Order Invoice for VAT (DE FR)
     */
    @Test(groups = {"epi_smoke", "epi_regression"})
    public void salesOrderInvoiceDEFRVATTest() {
        addressName = Address.Marseille.city + ", " + Address.Marseille.country.iso2code;
        try {
            // launch episerver commerce manager
            launchCommerceManagerPage();

            // login to Episerver commerce manager
            commerceManagerLoginPage = new EpiCommerceManagerLoginPage(driver);
            commerceManagerLoginPage.loginToCommerceManager(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            // navigate to administration option from left panel
            commerceManagerHomePage = new EpiCommerceManagerHomePage(driver);
            commerceManagerHomePage.navigateToLeftPanelOption(CommerceManagerNavigationOptions.LeftPanelOptions.ADMINISTRATION.text);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.AdministrationMenu.CATALOG_SYSTEM.text, CommerceManagerNavigationOptions.AdministrationMenu.CatalogSystemOptions.WAREHOUSES.text);
            commerceManagerAdministration = new EpiCommerceManagerAdministration(driver);
            commerceManagerAdministration.selectWarehouse(EpiDataCommon.WarehouseNames.NEW_YORK_STORE.text);
            commerceManagerAdministration.setWarehouseAddress(Address.Berlin.addressLine1, Address.Berlin.city, Address.Berlin.city, Address.Berlin.country.iso2code,
                    Address.Berlin.country.fullName, Address.Berlin.zip5, Address.Berlin.country.iso2code);

            // Sign Out from Commerce Manager
            commerceManagerHomePage.signOutFromCommerceManager();

            // Launch Epi-Server's store
            launchEpiStorePage();

            // Login to storefront
            storeLoginPage = new EpiStoreLoginPage(driver);
            storeLoginPage.loginToEpiStore(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);
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
            assertEquals(storeOrderTax, orderConfirmationPage.calculatePercentageBasedTax(EpiDataCommon.TaxRates.DE_FR_TAX.tax, false));

            // launch episerver commerce manager
            launchCommerceManagerPage();

            // login to Episerver commerce manager
            commerceManagerLoginPage.loginToCommerceManager(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            // navigate to order management option from left panel
            commerceManagerHomePage.navigateToLeftPanelOption(CommerceManagerNavigationOptions.LeftPanelOptions.ORDER_MANAGEMENT.text);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.PURCHASE_ORDERS.text, CommerceManagerNavigationOptions.OrderManagementCommonSubmenu.TODAY.text);

            // Open order page & select Summary Tab
            commerceManagerOrderManagement = new EpiCommerceManagerOrderManagement(driver);
            commerceManagerOrderManagement.openOrderAndSelectTab(orderNo, CommerceManagerNavigationOptions.OrderPageTabNames.SUMMARY.text);

            // Assertion on Order Status
            assertTrue(commerceManagerOrderManagement.verifyOrderStatus(EpiDataCommon.OrderStatus.IN_PROGRESS.text));

            // Open order page & select Details Tab
            commerceManagerOrderManagement.selectTab(CommerceManagerNavigationOptions.OrderPageTabNames.DETAILS.text);

            // Release shipment
            commerceManagerOrderManagement.clickReleaseShipment();

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.SHIPPING_RECEIVING.text,
                    CommerceManagerNavigationOptions.ShippingReceiving.SHIPMENTS.text, CommerceManagerNavigationOptions.ShippingReceiving.RELEASED_FOR_SHIPPING.text);

            // Release order shipment and save pick list name
            pickListName = commerceManagerOrderManagement.addShipmentToRelease(orderNo);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.SHIPPING_RECEIVING.text,
                    CommerceManagerNavigationOptions.ShippingReceiving.SHIPMENTS.text, CommerceManagerNavigationOptions.ShippingReceiving.PICK_LISTS.text);

            // Complete order
            trackingNo = generateTrackingNo();
            commerceManagerOrderManagement.selectRecordAndCompleteOrder(pickListName, orderNo, trackingNo);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.PURCHASE_ORDERS.text, CommerceManagerNavigationOptions.OrderManagementCommonSubmenu.TODAY.text);

            // Open order page & select Summary Tab
            commerceManagerOrderManagement.openOrderAndSelectTab(orderNo, CommerceManagerNavigationOptions.OrderPageTabNames.SUMMARY.text);

            // Assertion on Order Status
            assertTrue(commerceManagerOrderManagement.verifyOrderStatus(EpiDataCommon.OrderStatus.COMPLETED.text));
            assertEquals(storeOrderTax, commerceManagerOrderManagement.getOrderTotalTaxesFromUI());

            // Sign Out from Commerce Manager
            commerceManagerHomePage.signOutFromCommerceManager();
        } catch (Exception e) {
            e.printStackTrace();
            VertexLogger.log("Test failed due to error/exception, kindly check logs for more details");
            Assert.fail();
        } finally {
            quitDriver();
            createChromeDriver();

            // launch episerver commerce manager
            launchCommerceManagerPage();

            // login to Episerver commerce manager
            commerceManagerLoginPage = new EpiCommerceManagerLoginPage(driver);
            commerceManagerLoginPage.loginToCommerceManager(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            // navigate to administration option from left panel
            commerceManagerHomePage = new EpiCommerceManagerHomePage(driver);
            commerceManagerHomePage.navigateToLeftPanelOption(CommerceManagerNavigationOptions.LeftPanelOptions.ADMINISTRATION.text);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.AdministrationMenu.CATALOG_SYSTEM.text, CommerceManagerNavigationOptions.AdministrationMenu.CatalogSystemOptions.WAREHOUSES.text);

            // Set default address of Warehouse
            commerceManagerAdministration = new EpiCommerceManagerAdministration(driver);
            commerceManagerAdministration.setDefaultAddressOfWarehouse(EpiDataCommon.WarehouseNames.NEW_YORK_STORE.text);

            // Sign Out from Commerce Manager
            commerceManagerHomePage.signOutFromCommerceManager();
        }
    }

    /**
     * CEPI-242 Test Case - Create Sale Order for VAT (EU-US) and Invoice
     */
    @Test(groups = {"epi_regression"})
    public void salesOrderInvoiceEUUSVATTest() {
        addressName = Address.UniversalCity.city + ", " + Address.UniversalCity.state.abbreviation;
        try {
            // launch episerver commerce manager
            launchCommerceManagerPage();

            // login to Episerver commerce manager
            commerceManagerLoginPage = new EpiCommerceManagerLoginPage(driver);
            commerceManagerLoginPage.loginToCommerceManager(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            // navigate to administration option from left panel
            commerceManagerHomePage = new EpiCommerceManagerHomePage(driver);
            commerceManagerHomePage.navigateToLeftPanelOption(CommerceManagerNavigationOptions.LeftPanelOptions.ADMINISTRATION.text);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.AdministrationMenu.CATALOG_SYSTEM.text, CommerceManagerNavigationOptions.AdministrationMenu.CatalogSystemOptions.WAREHOUSES.text);
            commerceManagerAdministration = new EpiCommerceManagerAdministration(driver);
            commerceManagerAdministration.selectWarehouse(EpiDataCommon.WarehouseNames.NEW_YORK_STORE.text);
            commerceManagerAdministration.setWarehouseAddress(Address.Berlin.addressLine1, Address.Berlin.city, Address.Berlin.city, Address.Berlin.country.iso2code,
                    Address.Berlin.country.fullName, Address.Berlin.zip5, Address.Berlin.country.iso2code);

            // Sign Out from Commerce Manager
            commerceManagerHomePage.signOutFromCommerceManager();

            // Launch Epi-Server's store
            launchEpiStorePage();

            // Login to storefront
            storeLoginPage = new EpiStoreLoginPage(driver);
            storeLoginPage.loginToEpiStore(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);
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

            // launch episerver commerce manager
            launchCommerceManagerPage();

            // login to Episerver commerce manager
            commerceManagerLoginPage.loginToCommerceManager(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            // navigate to order management option from left panel
            commerceManagerHomePage.navigateToLeftPanelOption(CommerceManagerNavigationOptions.LeftPanelOptions.ORDER_MANAGEMENT.text);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.PURCHASE_ORDERS.text, CommerceManagerNavigationOptions.OrderManagementCommonSubmenu.TODAY.text);

            // Open order page & select Summary Tab
            commerceManagerOrderManagement = new EpiCommerceManagerOrderManagement(driver);
            commerceManagerOrderManagement.openOrderAndSelectTab(orderNo, CommerceManagerNavigationOptions.OrderPageTabNames.SUMMARY.text);

            // Assertion on Order Status
            assertTrue(commerceManagerOrderManagement.verifyOrderStatus(EpiDataCommon.OrderStatus.IN_PROGRESS.text));

            // Open order page & select Details Tab
            commerceManagerOrderManagement.selectTab(CommerceManagerNavigationOptions.OrderPageTabNames.DETAILS.text);

            // Release shipment
            commerceManagerOrderManagement.clickReleaseShipment();

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.SHIPPING_RECEIVING.text,
                    CommerceManagerNavigationOptions.ShippingReceiving.SHIPMENTS.text, CommerceManagerNavigationOptions.ShippingReceiving.RELEASED_FOR_SHIPPING.text);

            // Release order shipment and save pick list name
            pickListName = commerceManagerOrderManagement.addShipmentToRelease(orderNo);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.SHIPPING_RECEIVING.text,
                    CommerceManagerNavigationOptions.ShippingReceiving.SHIPMENTS.text, CommerceManagerNavigationOptions.ShippingReceiving.PICK_LISTS.text);

            // Complete order
            trackingNo = generateTrackingNo();
            commerceManagerOrderManagement.selectRecordAndCompleteOrder(pickListName, orderNo, trackingNo);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.PURCHASE_ORDERS.text, CommerceManagerNavigationOptions.OrderManagementCommonSubmenu.TODAY.text);

            // Open order page & select Summary Tab
            commerceManagerOrderManagement.openOrderAndSelectTab(orderNo, CommerceManagerNavigationOptions.OrderPageTabNames.SUMMARY.text);

            // Assertion on Order Status
            assertTrue(commerceManagerOrderManagement.verifyOrderStatus(EpiDataCommon.OrderStatus.COMPLETED.text));
            assertEquals(storeOrderTax, commerceManagerOrderManagement.getOrderTotalTaxesFromUI());

            // Sign Out from Commerce Manager
            commerceManagerHomePage.signOutFromCommerceManager();
        } catch (Exception e) {
            e.printStackTrace();
            VertexLogger.log("Test failed due to error/exception, kindly check logs for more details");
            Assert.fail();
        } finally {
            quitDriver();
            createChromeDriver();

            // launch episerver commerce manager
            launchCommerceManagerPage();

            // login to Episerver commerce manager
            commerceManagerLoginPage = new EpiCommerceManagerLoginPage(driver);
            commerceManagerLoginPage.loginToCommerceManager(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            // navigate to administration option from left panel
            commerceManagerHomePage = new EpiCommerceManagerHomePage(driver);
            commerceManagerHomePage.navigateToLeftPanelOption(CommerceManagerNavigationOptions.LeftPanelOptions.ADMINISTRATION.text);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.AdministrationMenu.CATALOG_SYSTEM.text, CommerceManagerNavigationOptions.AdministrationMenu.CatalogSystemOptions.WAREHOUSES.text);

            // Set default address of Warehouse
            commerceManagerAdministration = new EpiCommerceManagerAdministration(driver);
            commerceManagerAdministration.setDefaultAddressOfWarehouse(EpiDataCommon.WarehouseNames.NEW_YORK_STORE.text);

            // Sign Out from Commerce Manager
            commerceManagerHomePage.signOutFromCommerceManager();
        }
    }

    /**
     * CEPI-241 Test Case - Create Sale Order for VAT (US-EU) and Invoice
     */
    @Test(groups = {"epi_regression"})
    public void salesOrderInvoiceUSEUVATTest() {
        addressName = Address.Paris.city + ", " + Address.Paris.country.iso2code;
        try {
            // launch episerver commerce manager
            launchCommerceManagerPage();

            // login to Episerver commerce manager
            commerceManagerLoginPage = new EpiCommerceManagerLoginPage(driver);
            commerceManagerLoginPage.loginToCommerceManager(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            // navigate to administration option from left panel
            commerceManagerHomePage = new EpiCommerceManagerHomePage(driver);
            commerceManagerHomePage.navigateToLeftPanelOption(CommerceManagerNavigationOptions.LeftPanelOptions.ADMINISTRATION.text);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.AdministrationMenu.CATALOG_SYSTEM.text, CommerceManagerNavigationOptions.AdministrationMenu.CatalogSystemOptions.WAREHOUSES.text);
            commerceManagerAdministration = new EpiCommerceManagerAdministration(driver);
            commerceManagerAdministration.selectWarehouse(EpiDataCommon.WarehouseNames.NEW_YORK_STORE.text);
            commerceManagerAdministration.setWarehouseAddress(Address.Gettysburg.addressLine1, Address.Gettysburg.city, Address.Gettysburg.state.fullName, Address.Gettysburg.country.iso2code,
                    Address.Gettysburg.country.fullName, Address.Gettysburg.zip5, Address.Gettysburg.state.abbreviation);

            // Sign Out from Commerce Manager
            commerceManagerHomePage.signOutFromCommerceManager();

            // Launch Epi-Server's store
            launchEpiStorePage();

            // Login to storefront
            storeLoginPage = new EpiStoreLoginPage(driver);
            storeLoginPage.loginToEpiStore(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);
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
            assertEquals(storeOrderTax, orderConfirmationPage.calculatePercentageBasedTax(EpiDataCommon.TaxRates.ZERO_TAX.tax));

            // launch episerver commerce manager
            launchCommerceManagerPage();

            // login to Episerver commerce manager
            commerceManagerLoginPage.loginToCommerceManager(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            // navigate to order management option from left panel
            commerceManagerHomePage.navigateToLeftPanelOption(CommerceManagerNavigationOptions.LeftPanelOptions.ORDER_MANAGEMENT.text);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.PURCHASE_ORDERS.text, CommerceManagerNavigationOptions.OrderManagementCommonSubmenu.TODAY.text);

            // Open order page & select Summary Tab
            commerceManagerOrderManagement = new EpiCommerceManagerOrderManagement(driver);
            commerceManagerOrderManagement.openOrderAndSelectTab(orderNo, CommerceManagerNavigationOptions.OrderPageTabNames.SUMMARY.text);

            // Assertion on Order Status
            assertTrue(commerceManagerOrderManagement.verifyOrderStatus(EpiDataCommon.OrderStatus.IN_PROGRESS.text));

            // Open order page & select Details Tab
            commerceManagerOrderManagement.selectTab(CommerceManagerNavigationOptions.OrderPageTabNames.DETAILS.text);

            // Release shipment
            commerceManagerOrderManagement.clickReleaseShipment();

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.SHIPPING_RECEIVING.text,
                    CommerceManagerNavigationOptions.ShippingReceiving.SHIPMENTS.text, CommerceManagerNavigationOptions.ShippingReceiving.RELEASED_FOR_SHIPPING.text);

            // Release order shipment and save pick list name
            pickListName = commerceManagerOrderManagement.addShipmentToRelease(orderNo);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.SHIPPING_RECEIVING.text,
                    CommerceManagerNavigationOptions.ShippingReceiving.SHIPMENTS.text, CommerceManagerNavigationOptions.ShippingReceiving.PICK_LISTS.text);

            // Complete order
            trackingNo = generateTrackingNo();
            commerceManagerOrderManagement.selectRecordAndCompleteOrder(pickListName, orderNo, trackingNo);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.PURCHASE_ORDERS.text, CommerceManagerNavigationOptions.OrderManagementCommonSubmenu.TODAY.text);

            // Open order page & select Summary Tab
            commerceManagerOrderManagement.openOrderAndSelectTab(orderNo, CommerceManagerNavigationOptions.OrderPageTabNames.SUMMARY.text);

            // Assertion on Order Status
            assertTrue(commerceManagerOrderManagement.verifyOrderStatus(EpiDataCommon.OrderStatus.COMPLETED.text));
            assertEquals(storeOrderTax, commerceManagerOrderManagement.getOrderTotalTaxesFromUI());

            // Sign Out from Commerce Manager
            commerceManagerHomePage.signOutFromCommerceManager();
        } catch (Exception e) {
            e.printStackTrace();
            VertexLogger.log("Test failed due to error/exception, kindly check logs for more details");
            Assert.fail();
        } finally {
            quitDriver();
            createChromeDriver();

            // launch episerver commerce manager
            launchCommerceManagerPage();

            // login to Episerver commerce manager
            commerceManagerLoginPage = new EpiCommerceManagerLoginPage(driver);
            commerceManagerLoginPage.loginToCommerceManager(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            // navigate to administration option from left panel
            commerceManagerHomePage = new EpiCommerceManagerHomePage(driver);
            commerceManagerHomePage.navigateToLeftPanelOption(CommerceManagerNavigationOptions.LeftPanelOptions.ADMINISTRATION.text);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.AdministrationMenu.CATALOG_SYSTEM.text, CommerceManagerNavigationOptions.AdministrationMenu.CatalogSystemOptions.WAREHOUSES.text);

            // Set default address of Warehouse
            commerceManagerAdministration = new EpiCommerceManagerAdministration(driver);
            commerceManagerAdministration.setDefaultAddressOfWarehouse(EpiDataCommon.WarehouseNames.NEW_YORK_STORE.text);

            // Sign Out from Commerce Manager
            commerceManagerHomePage.signOutFromCommerceManager();
        }
    }

    /**
     * CEPI-261 Test Case - Create Sale Order for VAT (Intra EU FR-DE) and Invoice
     */
    @Test(groups = {"epi_regression"})
    public void salesOrderInvoiceFRDEVATTest() {
        addressName = Address.Berlin.city + ", " + Address.Berlin.country.iso2code;
        try {
            // launch episerver commerce manager
            launchCommerceManagerPage();

            // login to Episerver commerce manager
            commerceManagerLoginPage = new EpiCommerceManagerLoginPage(driver);
            commerceManagerLoginPage.loginToCommerceManager(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            // navigate to administration option from left panel
            commerceManagerHomePage = new EpiCommerceManagerHomePage(driver);
            commerceManagerHomePage.navigateToLeftPanelOption(CommerceManagerNavigationOptions.LeftPanelOptions.ADMINISTRATION.text);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.AdministrationMenu.CATALOG_SYSTEM.text, CommerceManagerNavigationOptions.AdministrationMenu.CatalogSystemOptions.WAREHOUSES.text);
            commerceManagerAdministration = new EpiCommerceManagerAdministration(driver);
            commerceManagerAdministration.selectWarehouse(EpiDataCommon.WarehouseNames.NEW_YORK_STORE.text);
            commerceManagerAdministration.setWarehouseAddress(Address.Paris.addressLine1, Address.Paris.city, Address.Paris.city, Address.Paris.country.iso2code,
                    Address.Paris.country.fullName, Address.Paris.zip5, Address.Paris.country.iso2code);

            // Sign Out from Commerce Manager
            commerceManagerHomePage.signOutFromCommerceManager();

            // Launch Epi-Server's store
            launchEpiStorePage();

            // Login to storefront
            storeLoginPage = new EpiStoreLoginPage(driver);
            storeLoginPage.loginToEpiStore(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);
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
            assertEquals(storeOrderTax, orderConfirmationPage.calculatePercentageBasedTax(EpiDataCommon.TaxRates.FR_DE_TAX.tax, false));

            // launch episerver commerce manager
            launchCommerceManagerPage();

            // login to Episerver commerce manager
            commerceManagerLoginPage.loginToCommerceManager(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            // navigate to order management option from left panel
            commerceManagerHomePage.navigateToLeftPanelOption(CommerceManagerNavigationOptions.LeftPanelOptions.ORDER_MANAGEMENT.text);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.PURCHASE_ORDERS.text, CommerceManagerNavigationOptions.OrderManagementCommonSubmenu.TODAY.text);

            // Open order page & select Summary Tab
            commerceManagerOrderManagement = new EpiCommerceManagerOrderManagement(driver);
            commerceManagerOrderManagement.openOrderAndSelectTab(orderNo, CommerceManagerNavigationOptions.OrderPageTabNames.SUMMARY.text);

            // Assertion on Order Status
            assertTrue(commerceManagerOrderManagement.verifyOrderStatus(EpiDataCommon.OrderStatus.IN_PROGRESS.text));

            // Open order page & select Details Tab
            commerceManagerOrderManagement.selectTab(CommerceManagerNavigationOptions.OrderPageTabNames.DETAILS.text);

            // Release shipment
            commerceManagerOrderManagement.clickReleaseShipment();

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.SHIPPING_RECEIVING.text,
                    CommerceManagerNavigationOptions.ShippingReceiving.SHIPMENTS.text, CommerceManagerNavigationOptions.ShippingReceiving.RELEASED_FOR_SHIPPING.text);

            // Release order shipment and save pick list name
            pickListName = commerceManagerOrderManagement.addShipmentToRelease(orderNo);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.SHIPPING_RECEIVING.text,
                    CommerceManagerNavigationOptions.ShippingReceiving.SHIPMENTS.text, CommerceManagerNavigationOptions.ShippingReceiving.PICK_LISTS.text);

            // Complete order
            trackingNo = generateTrackingNo();
            commerceManagerOrderManagement.selectRecordAndCompleteOrder(pickListName, orderNo, trackingNo);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.PURCHASE_ORDERS.text, CommerceManagerNavigationOptions.OrderManagementCommonSubmenu.TODAY.text);

            // Open order page & select Summary Tab
            commerceManagerOrderManagement.openOrderAndSelectTab(orderNo, CommerceManagerNavigationOptions.OrderPageTabNames.SUMMARY.text);

            // Assertion on Order Status
            assertTrue(commerceManagerOrderManagement.verifyOrderStatus(EpiDataCommon.OrderStatus.COMPLETED.text));
            assertEquals(storeOrderTax, commerceManagerOrderManagement.getOrderTotalTaxesFromUI());

            // Sign Out from Commerce Manager
            commerceManagerHomePage.signOutFromCommerceManager();
        } catch (Exception e) {
            e.printStackTrace();
            VertexLogger.log("Test failed due to error/exception, kindly check logs for more details");
            Assert.fail();
        } finally {
            quitDriver();
            createChromeDriver();

            // launch episerver commerce manager
            launchCommerceManagerPage();

            // login to Episerver commerce manager
            commerceManagerLoginPage = new EpiCommerceManagerLoginPage(driver);
            commerceManagerLoginPage.loginToCommerceManager(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            // navigate to administration option from left panel
            commerceManagerHomePage = new EpiCommerceManagerHomePage(driver);
            commerceManagerHomePage.navigateToLeftPanelOption(CommerceManagerNavigationOptions.LeftPanelOptions.ADMINISTRATION.text);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.AdministrationMenu.CATALOG_SYSTEM.text, CommerceManagerNavigationOptions.AdministrationMenu.CatalogSystemOptions.WAREHOUSES.text);

            // Set default address of Warehouse
            commerceManagerAdministration = new EpiCommerceManagerAdministration(driver);
            commerceManagerAdministration.setDefaultAddressOfWarehouse(EpiDataCommon.WarehouseNames.NEW_YORK_STORE.text);

            // Sign Out from Commerce Manager
            commerceManagerHomePage.signOutFromCommerceManager();
        }
    }

    /**
     * CEPI-262 Test Case - Create Sale Order for VAT (Greek Territory) and Invoice
     */
    @Test(groups = {"epi_regression"})
    public void salesOrderInvoiceGreekTerritoryVATTest() {
        addressName = Address.Analipsi.city + ", " + Address.Analipsi.country.iso2code;
        try {
            // Select SUP shipping term in O-Series 9
            oSeriesLogin = new OSeriesLoginPage(driver);
            oSeriesLogin.loadOSeriesPage();
            oSeriesLogin.loginToOSeries();
            oSeriesTax = new OSeriesTaxpayers(driver);
            oSeriesTax.updateShippingTerms(true, OSeriesData.SHIP_TERM_SUP.data);

            // launch episerver commerce manager
            launchCommerceManagerPage();

            // login to Episerver commerce manager
            commerceManagerLoginPage = new EpiCommerceManagerLoginPage(driver);
            commerceManagerLoginPage.loginToCommerceManager(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            // navigate to administration option from left panel
            commerceManagerHomePage = new EpiCommerceManagerHomePage(driver);
            commerceManagerHomePage.navigateToLeftPanelOption(CommerceManagerNavigationOptions.LeftPanelOptions.ADMINISTRATION.text);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.AdministrationMenu.CATALOG_SYSTEM.text, CommerceManagerNavigationOptions.AdministrationMenu.CatalogSystemOptions.WAREHOUSES.text);
            commerceManagerAdministration = new EpiCommerceManagerAdministration(driver);
            commerceManagerAdministration.selectWarehouse(EpiDataCommon.WarehouseNames.NEW_YORK_STORE.text);
            commerceManagerAdministration.setWarehouseAddress(Address.Paris.addressLine1, Address.Paris.city, Address.Paris.city, Address.Paris.country.iso2code,
                    Address.Paris.country.fullName, Address.Paris.zip5, Address.Paris.country.iso2code);

            // Sign Out from Commerce Manager
            commerceManagerHomePage.signOutFromCommerceManager();

            // Launch Epi-Server's store
            launchEpiStorePage();

            // Login to storefront
            storeLoginPage = new EpiStoreLoginPage(driver);
            storeLoginPage.loginToEpiStore(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);
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

            // launch episerver commerce manager
            launchCommerceManagerPage();

            // login to Episerver commerce manager
            commerceManagerLoginPage.loginToCommerceManager(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            // navigate to order management option from left panel
            commerceManagerHomePage.navigateToLeftPanelOption(CommerceManagerNavigationOptions.LeftPanelOptions.ORDER_MANAGEMENT.text);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.PURCHASE_ORDERS.text, CommerceManagerNavigationOptions.OrderManagementCommonSubmenu.TODAY.text);

            // Open order page & select Summary Tab
            commerceManagerOrderManagement = new EpiCommerceManagerOrderManagement(driver);
            commerceManagerOrderManagement.openOrderAndSelectTab(orderNo, CommerceManagerNavigationOptions.OrderPageTabNames.SUMMARY.text);

            // Assertion on Order Status
            assertTrue(commerceManagerOrderManagement.verifyOrderStatus(EpiDataCommon.OrderStatus.IN_PROGRESS.text));

            // Open order page & select Details Tab
            commerceManagerOrderManagement.selectTab(CommerceManagerNavigationOptions.OrderPageTabNames.DETAILS.text);

            // Release shipment
            commerceManagerOrderManagement.clickReleaseShipment();

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.SHIPPING_RECEIVING.text,
                    CommerceManagerNavigationOptions.ShippingReceiving.SHIPMENTS.text, CommerceManagerNavigationOptions.ShippingReceiving.RELEASED_FOR_SHIPPING.text);

            // Release order shipment and save pick list name
            pickListName = commerceManagerOrderManagement.addShipmentToRelease(orderNo);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.SHIPPING_RECEIVING.text,
                    CommerceManagerNavigationOptions.ShippingReceiving.SHIPMENTS.text, CommerceManagerNavigationOptions.ShippingReceiving.PICK_LISTS.text);

            // Complete order
            trackingNo = generateTrackingNo();
            commerceManagerOrderManagement.selectRecordAndCompleteOrder(pickListName, orderNo, trackingNo);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.PURCHASE_ORDERS.text, CommerceManagerNavigationOptions.OrderManagementCommonSubmenu.TODAY.text);

            // Open order page & select Summary Tab
            commerceManagerOrderManagement.openOrderAndSelectTab(orderNo, CommerceManagerNavigationOptions.OrderPageTabNames.SUMMARY.text);

            // Assertion on Order Status
            assertTrue(commerceManagerOrderManagement.verifyOrderStatus(EpiDataCommon.OrderStatus.COMPLETED.text));
            assertEquals(storeOrderTax, commerceManagerOrderManagement.getOrderTotalTaxesFromUI());

            // Sign Out from Commerce Manager
            commerceManagerHomePage.signOutFromCommerceManager();
        } catch (Exception e) {
            e.printStackTrace();
            VertexLogger.log("Test failed due to error/exception, kindly check logs for more details");
            Assert.fail();
        } finally {
            // Remove shipping term from O-Series 9
            oSeriesLogin = new OSeriesLoginPage(driver);
            oSeriesLogin.loadOSeriesPage();
            if (oSeriesLogin.verifyLoginPage()) {
                oSeriesLogin.loginToOSeries();
            }
            oSeriesTax = new OSeriesTaxpayers(driver);
            oSeriesTax.updateShippingTerms(false, "");

            quitDriver();
            createChromeDriver();

            // launch episerver commerce manager
            launchCommerceManagerPage();

            // login to Episerver commerce manager
            commerceManagerLoginPage = new EpiCommerceManagerLoginPage(driver);
            commerceManagerLoginPage.loginToCommerceManager(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            // navigate to administration option from left panel
            commerceManagerHomePage = new EpiCommerceManagerHomePage(driver);
            commerceManagerHomePage.navigateToLeftPanelOption(CommerceManagerNavigationOptions.LeftPanelOptions.ADMINISTRATION.text);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.AdministrationMenu.CATALOG_SYSTEM.text, CommerceManagerNavigationOptions.AdministrationMenu.CatalogSystemOptions.WAREHOUSES.text);

            // Set default address of Warehouse
            commerceManagerAdministration = new EpiCommerceManagerAdministration(driver);
            commerceManagerAdministration.setDefaultAddressOfWarehouse(EpiDataCommon.WarehouseNames.NEW_YORK_STORE.text);

            // Sign Out from Commerce Manager
            commerceManagerHomePage.signOutFromCommerceManager();
        }
    }

    /**
     * CEPI-263 Test Case - Create Sale Order for VAT (Austrian Sub-Division) and Invoice
     */
    @Test(groups = {"epi_regression"})
    public void salesOrderInvoiceAustrianDivisionVATTest() {
        addressName = Address.Mittelberg.city + ", " + Address.Mittelberg.country.iso2code;
        try {
            // Select SUP shipping term in O-Series 9
            oSeriesLogin = new OSeriesLoginPage(driver);
            oSeriesLogin.loadOSeriesPage();
            oSeriesLogin.loginToOSeries();
            oSeriesTax = new OSeriesTaxpayers(driver);
            oSeriesTax.updateShippingTerms(true, OSeriesData.SHIP_TERM_SUP.data);

            // launch episerver commerce manager
            launchCommerceManagerPage();

            // login to Episerver commerce manager
            commerceManagerLoginPage = new EpiCommerceManagerLoginPage(driver);
            commerceManagerLoginPage.loginToCommerceManager(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            // navigate to administration option from left panel
            commerceManagerHomePage = new EpiCommerceManagerHomePage(driver);
            commerceManagerHomePage.navigateToLeftPanelOption(CommerceManagerNavigationOptions.LeftPanelOptions.ADMINISTRATION.text);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.AdministrationMenu.CATALOG_SYSTEM.text, CommerceManagerNavigationOptions.AdministrationMenu.CatalogSystemOptions.WAREHOUSES.text);
            commerceManagerAdministration = new EpiCommerceManagerAdministration(driver);
            commerceManagerAdministration.selectWarehouse(EpiDataCommon.WarehouseNames.NEW_YORK_STORE.text);
            commerceManagerAdministration.setWarehouseAddress(Address.Berlin.addressLine1, Address.Berlin.city, Address.Berlin.city, Address.Berlin.country.iso2code,
                    Address.Berlin.country.fullName, Address.Berlin.zip5, Address.Berlin.country.iso2code);

            // Sign Out from Commerce Manager
            commerceManagerHomePage.signOutFromCommerceManager();

            // Launch Epi-Server's store
            launchEpiStorePage();

            // Login to storefront
            storeLoginPage = new EpiStoreLoginPage(driver);
            storeLoginPage.loginToEpiStore(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);
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

            // launch episerver commerce manager
            launchCommerceManagerPage();

            // login to Episerver commerce manager
            commerceManagerLoginPage.loginToCommerceManager(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            // navigate to order management option from left panel
            commerceManagerHomePage.navigateToLeftPanelOption(CommerceManagerNavigationOptions.LeftPanelOptions.ORDER_MANAGEMENT.text);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.PURCHASE_ORDERS.text, CommerceManagerNavigationOptions.OrderManagementCommonSubmenu.TODAY.text);

            // Open order page & select Summary Tab
            commerceManagerOrderManagement = new EpiCommerceManagerOrderManagement(driver);
            commerceManagerOrderManagement.openOrderAndSelectTab(orderNo, CommerceManagerNavigationOptions.OrderPageTabNames.SUMMARY.text);

            // Assertion on Order Status
            assertTrue(commerceManagerOrderManagement.verifyOrderStatus(EpiDataCommon.OrderStatus.IN_PROGRESS.text));

            // Open order page & select Details Tab
            commerceManagerOrderManagement.selectTab(CommerceManagerNavigationOptions.OrderPageTabNames.DETAILS.text);

            // Release shipment
            commerceManagerOrderManagement.clickReleaseShipment();

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.SHIPPING_RECEIVING.text,
                    CommerceManagerNavigationOptions.ShippingReceiving.SHIPMENTS.text, CommerceManagerNavigationOptions.ShippingReceiving.RELEASED_FOR_SHIPPING.text);

            // Release order shipment and save pick list name
            pickListName = commerceManagerOrderManagement.addShipmentToRelease(orderNo);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.SHIPPING_RECEIVING.text,
                    CommerceManagerNavigationOptions.ShippingReceiving.SHIPMENTS.text, CommerceManagerNavigationOptions.ShippingReceiving.PICK_LISTS.text);

            // Complete order
            trackingNo = generateTrackingNo();
            commerceManagerOrderManagement.selectRecordAndCompleteOrder(pickListName, orderNo, trackingNo);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.PURCHASE_ORDERS.text, CommerceManagerNavigationOptions.OrderManagementCommonSubmenu.TODAY.text);

            // Open order page & select Summary Tab
            commerceManagerOrderManagement.openOrderAndSelectTab(orderNo, CommerceManagerNavigationOptions.OrderPageTabNames.SUMMARY.text);

            // Assertion on Order Status
            assertTrue(commerceManagerOrderManagement.verifyOrderStatus(EpiDataCommon.OrderStatus.COMPLETED.text));
            assertEquals(storeOrderTax, commerceManagerOrderManagement.getOrderTotalTaxesFromUI());

            // Sign Out from Commerce Manager
            commerceManagerHomePage.signOutFromCommerceManager();
        } catch (Exception e) {
            e.printStackTrace();
            VertexLogger.log("Test failed due to error/exception, kindly check logs for more details");
            Assert.fail();
        } finally {
            // Remove shipping term from O-Series 9
            oSeriesLogin = new OSeriesLoginPage(driver);
            oSeriesLogin.loadOSeriesPage();
            if (oSeriesLogin.verifyLoginPage()) {
                oSeriesLogin.loginToOSeries();
            }
            oSeriesTax = new OSeriesTaxpayers(driver);
            oSeriesTax.updateShippingTerms(false, "");

            quitDriver();
            createChromeDriver();

            // launch episerver commerce manager
            launchCommerceManagerPage();

            // login to Episerver commerce manager
            commerceManagerLoginPage = new EpiCommerceManagerLoginPage(driver);
            commerceManagerLoginPage.loginToCommerceManager(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            // navigate to administration option from left panel
            commerceManagerHomePage = new EpiCommerceManagerHomePage(driver);
            commerceManagerHomePage.navigateToLeftPanelOption(CommerceManagerNavigationOptions.LeftPanelOptions.ADMINISTRATION.text);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.AdministrationMenu.CATALOG_SYSTEM.text, CommerceManagerNavigationOptions.AdministrationMenu.CatalogSystemOptions.WAREHOUSES.text);

            // Set default address of Warehouse
            commerceManagerAdministration = new EpiCommerceManagerAdministration(driver);
            commerceManagerAdministration.setDefaultAddressOfWarehouse(EpiDataCommon.WarehouseNames.NEW_YORK_STORE.text);

            // Sign Out from Commerce Manager
            commerceManagerHomePage.signOutFromCommerceManager();
        }
    }

    /**
     * CEPI-264 Test Case - Create Sale Order for APAC (India) and Invoice
     */
    @Test(groups = {"epi_regression"})
    public void salesOrderInvoiceAPACIndiaTest() {
        addressName = Address.NewRamdaspeth.city + ", " + Address.NewRamdaspeth.state.abbreviation;
        try {
            // launch episerver commerce manager
            launchCommerceManagerPage();

            // login to Episerver commerce manager
            commerceManagerLoginPage = new EpiCommerceManagerLoginPage(driver);
            commerceManagerLoginPage.loginToCommerceManager(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            // navigate to administration option from left panel
            commerceManagerHomePage = new EpiCommerceManagerHomePage(driver);
            commerceManagerHomePage.navigateToLeftPanelOption(CommerceManagerNavigationOptions.LeftPanelOptions.ADMINISTRATION.text);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.AdministrationMenu.CATALOG_SYSTEM.text, CommerceManagerNavigationOptions.AdministrationMenu.CatalogSystemOptions.WAREHOUSES.text);
            commerceManagerAdministration = new EpiCommerceManagerAdministration(driver);
            commerceManagerAdministration.selectWarehouse(EpiDataCommon.WarehouseNames.NEW_YORK_STORE.text);
            commerceManagerAdministration.setWarehouseAddress(Address.Chandigarh.addressLine1, Address.Chandigarh.city, Address.Chandigarh.state.fullName, Address.Chandigarh.country.iso2code,
                    Address.Chandigarh.country.fullName, Address.Chandigarh.zip5, Address.Chandigarh.state.abbreviation);

            // Sign Out from Commerce Manager
            commerceManagerHomePage.signOutFromCommerceManager();

            // Launch Epi-Server's store
            launchEpiStorePage();

            // Login to storefront
            storeLoginPage = new EpiStoreLoginPage(driver);
            storeLoginPage.loginToEpiStore(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);
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
            assertEquals(storeOrderTax, orderConfirmationPage.calculatePercentageBasedTax(EpiDataCommon.TaxRates.INDIA_GST.tax, false));

            // launch episerver commerce manager
            launchCommerceManagerPage();

            // login to Episerver commerce manager
            commerceManagerLoginPage.loginToCommerceManager(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            // navigate to order management option from left panel
            commerceManagerHomePage.navigateToLeftPanelOption(CommerceManagerNavigationOptions.LeftPanelOptions.ORDER_MANAGEMENT.text);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.PURCHASE_ORDERS.text, CommerceManagerNavigationOptions.OrderManagementCommonSubmenu.TODAY.text);

            // Open order page & select Summary Tab
            commerceManagerOrderManagement = new EpiCommerceManagerOrderManagement(driver);
            commerceManagerOrderManagement.openOrderAndSelectTab(orderNo, CommerceManagerNavigationOptions.OrderPageTabNames.SUMMARY.text);

            // Assertion on Order Status
            assertTrue(commerceManagerOrderManagement.verifyOrderStatus(EpiDataCommon.OrderStatus.IN_PROGRESS.text));

            // Open order page & select Details Tab
            commerceManagerOrderManagement.selectTab(CommerceManagerNavigationOptions.OrderPageTabNames.DETAILS.text);

            // Release shipment
            commerceManagerOrderManagement.clickReleaseShipment();

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.SHIPPING_RECEIVING.text,
                    CommerceManagerNavigationOptions.ShippingReceiving.SHIPMENTS.text, CommerceManagerNavigationOptions.ShippingReceiving.RELEASED_FOR_SHIPPING.text);

            // Release order shipment and save pick list name
            pickListName = commerceManagerOrderManagement.addShipmentToRelease(orderNo);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.SHIPPING_RECEIVING.text,
                    CommerceManagerNavigationOptions.ShippingReceiving.SHIPMENTS.text, CommerceManagerNavigationOptions.ShippingReceiving.PICK_LISTS.text);

            // Complete order
            trackingNo = generateTrackingNo();
            commerceManagerOrderManagement.selectRecordAndCompleteOrder(pickListName, orderNo, trackingNo);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.PURCHASE_ORDERS.text, CommerceManagerNavigationOptions.OrderManagementCommonSubmenu.TODAY.text);

            // Open order page & select Summary Tab
            commerceManagerOrderManagement.openOrderAndSelectTab(orderNo, CommerceManagerNavigationOptions.OrderPageTabNames.SUMMARY.text);

            // Assertion on Order Status
            assertTrue(commerceManagerOrderManagement.verifyOrderStatus(EpiDataCommon.OrderStatus.COMPLETED.text));
            assertEquals(storeOrderTax, commerceManagerOrderManagement.getOrderTotalTaxesFromUI());

            // Sign Out from Commerce Manager
            commerceManagerHomePage.signOutFromCommerceManager();
        } catch (Exception e) {
            e.printStackTrace();
            VertexLogger.log("Test failed due to error/exception, kindly check logs for more details");
            Assert.fail();
        } finally {
            quitDriver();
            createChromeDriver();

            // launch episerver commerce manager
            launchCommerceManagerPage();

            // login to Episerver commerce manager
            commerceManagerLoginPage = new EpiCommerceManagerLoginPage(driver);
            commerceManagerLoginPage.loginToCommerceManager(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            // navigate to administration option from left panel
            commerceManagerHomePage = new EpiCommerceManagerHomePage(driver);
            commerceManagerHomePage.navigateToLeftPanelOption(CommerceManagerNavigationOptions.LeftPanelOptions.ADMINISTRATION.text);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.AdministrationMenu.CATALOG_SYSTEM.text, CommerceManagerNavigationOptions.AdministrationMenu.CatalogSystemOptions.WAREHOUSES.text);

            // Set default address of Warehouse
            commerceManagerAdministration = new EpiCommerceManagerAdministration(driver);
            commerceManagerAdministration.setDefaultAddressOfWarehouse(EpiDataCommon.WarehouseNames.NEW_YORK_STORE.text);

            // Sign Out from Commerce Manager
            commerceManagerHomePage.signOutFromCommerceManager();
        }
    }

    /**
     * CEPI-265 Test Case - Create Sale Order for APAC (Hong Kong no tax) and Invoice
     */
    @Test(groups = {"epi_regression"})
    public void salesOrderInvoiceHKGNoTaxTest() {
        addressName = Address.ShuenWan.city + ", " + Address.ShuenWan.country.iso2code;
        try {
            // launch episerver commerce manager
            launchCommerceManagerPage();

            // login to Episerver commerce manager
            commerceManagerLoginPage = new EpiCommerceManagerLoginPage(driver);
            commerceManagerLoginPage.loginToCommerceManager(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            // navigate to administration option from left panel
            commerceManagerHomePage = new EpiCommerceManagerHomePage(driver);
            commerceManagerHomePage.navigateToLeftPanelOption(CommerceManagerNavigationOptions.LeftPanelOptions.ADMINISTRATION.text);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.AdministrationMenu.CATALOG_SYSTEM.text, CommerceManagerNavigationOptions.AdministrationMenu.CatalogSystemOptions.WAREHOUSES.text);
            commerceManagerAdministration = new EpiCommerceManagerAdministration(driver);
            commerceManagerAdministration.selectWarehouse(EpiDataCommon.WarehouseNames.NEW_YORK_STORE.text);
            commerceManagerAdministration.setWarehouseAddress(Address.MongKok.addressLine1, Address.MongKok.city, Address.MongKok.city, Address.MongKok.country.iso2code,
                    Address.MongKok.country.fullName, Address.MongKok.zip5, Address.MongKok.country.iso2code);

            // Sign Out from Commerce Manager
            commerceManagerHomePage.signOutFromCommerceManager();

            // Launch Epi-Server's store
            launchEpiStorePage();

            // Login to storefront
            storeLoginPage = new EpiStoreLoginPage(driver);
            storeLoginPage.loginToEpiStore(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);
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

            // launch episerver commerce manager
            launchCommerceManagerPage();

            // login to Episerver commerce manager
            commerceManagerLoginPage.loginToCommerceManager(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            // navigate to order management option from left panel
            commerceManagerHomePage.navigateToLeftPanelOption(CommerceManagerNavigationOptions.LeftPanelOptions.ORDER_MANAGEMENT.text);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.PURCHASE_ORDERS.text, CommerceManagerNavigationOptions.OrderManagementCommonSubmenu.TODAY.text);

            // Open order page & select Summary Tab
            commerceManagerOrderManagement = new EpiCommerceManagerOrderManagement(driver);
            commerceManagerOrderManagement.openOrderAndSelectTab(orderNo, CommerceManagerNavigationOptions.OrderPageTabNames.SUMMARY.text);

            // Assertion on Order Status
            assertTrue(commerceManagerOrderManagement.verifyOrderStatus(EpiDataCommon.OrderStatus.IN_PROGRESS.text));

            // Open order page & select Details Tab
            commerceManagerOrderManagement.selectTab(CommerceManagerNavigationOptions.OrderPageTabNames.DETAILS.text);

            // Release shipment
            commerceManagerOrderManagement.clickReleaseShipment();

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.SHIPPING_RECEIVING.text,
                    CommerceManagerNavigationOptions.ShippingReceiving.SHIPMENTS.text, CommerceManagerNavigationOptions.ShippingReceiving.RELEASED_FOR_SHIPPING.text);

            // Release order shipment and save pick list name
            pickListName = commerceManagerOrderManagement.addShipmentToRelease(orderNo);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.SHIPPING_RECEIVING.text,
                    CommerceManagerNavigationOptions.ShippingReceiving.SHIPMENTS.text, CommerceManagerNavigationOptions.ShippingReceiving.PICK_LISTS.text);

            // Complete order
            trackingNo = generateTrackingNo();
            commerceManagerOrderManagement.selectRecordAndCompleteOrder(pickListName, orderNo, trackingNo);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.PURCHASE_ORDERS.text, CommerceManagerNavigationOptions.OrderManagementCommonSubmenu.TODAY.text);

            // Open order page & select Summary Tab
            commerceManagerOrderManagement.openOrderAndSelectTab(orderNo, CommerceManagerNavigationOptions.OrderPageTabNames.SUMMARY.text);

            // Assertion on Order Status
            assertTrue(commerceManagerOrderManagement.verifyOrderStatus(EpiDataCommon.OrderStatus.COMPLETED.text));
            assertEquals(storeOrderTax, commerceManagerOrderManagement.getOrderTotalTaxesFromUI());

            // Sign Out from Commerce Manager
            commerceManagerHomePage.signOutFromCommerceManager();
        } catch (Exception e) {
            e.printStackTrace();
            VertexLogger.log("Test failed due to error/exception, kindly check logs for more details");
            Assert.fail();
        } finally {
            quitDriver();
            createChromeDriver();

            // launch episerver commerce manager
            launchCommerceManagerPage();

            // login to Episerver commerce manager
            commerceManagerLoginPage = new EpiCommerceManagerLoginPage(driver);
            commerceManagerLoginPage.loginToCommerceManager(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            // navigate to administration option from left panel
            commerceManagerHomePage = new EpiCommerceManagerHomePage(driver);
            commerceManagerHomePage.navigateToLeftPanelOption(CommerceManagerNavigationOptions.LeftPanelOptions.ADMINISTRATION.text);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.AdministrationMenu.CATALOG_SYSTEM.text, CommerceManagerNavigationOptions.AdministrationMenu.CatalogSystemOptions.WAREHOUSES.text);

            // Set default address of Warehouse
            commerceManagerAdministration = new EpiCommerceManagerAdministration(driver);
            commerceManagerAdministration.setDefaultAddressOfWarehouse(EpiDataCommon.WarehouseNames.NEW_YORK_STORE.text);

            // Sign Out from Commerce Manager
            commerceManagerHomePage.signOutFromCommerceManager();
        }
    }

    /**
     * CEPI-266 Test Case - Create Sale Order for LATAM (BZ-BZ) and Invoice
     */
    @Test(groups = {"epi_regression"})
    public void salesOrderInvoiceLATAMBZBZTest() {
        addressName = Address.SanIgnacio.city + ", " + Address.SanIgnacio.country.iso2code;
        try {
            // launch episerver commerce manager
            launchCommerceManagerPage();

            // login to Episerver commerce manager
            commerceManagerLoginPage = new EpiCommerceManagerLoginPage(driver);
            commerceManagerLoginPage.loginToCommerceManager(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            // navigate to administration option from left panel
            commerceManagerHomePage = new EpiCommerceManagerHomePage(driver);
            commerceManagerHomePage.navigateToLeftPanelOption(CommerceManagerNavigationOptions.LeftPanelOptions.ADMINISTRATION.text);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.AdministrationMenu.CATALOG_SYSTEM.text, CommerceManagerNavigationOptions.AdministrationMenu.CatalogSystemOptions.WAREHOUSES.text);
            commerceManagerAdministration = new EpiCommerceManagerAdministration(driver);
            commerceManagerAdministration.selectWarehouse(EpiDataCommon.WarehouseNames.NEW_YORK_STORE.text);
            commerceManagerAdministration.setWarehouseAddress(Address.BelizeCity.addressLine1, Address.BelizeCity.city, Address.BelizeCity.city, Address.BelizeCity.country.iso2code,
                    Address.BelizeCity.country.fullName, Address.BelizeCity.zip5, Address.BelizeCity.country.iso2code);

            // Sign Out from Commerce Manager
            commerceManagerHomePage.signOutFromCommerceManager();

            // Launch Epi-Server's store
            launchEpiStorePage();

            // Login to storefront
            storeLoginPage = new EpiStoreLoginPage(driver);
            storeLoginPage.loginToEpiStore(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);
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
            assertEquals(storeOrderTax, orderConfirmationPage.calculatePercentageBasedTax(EpiDataCommon.TaxRates.LATAM_BZ_BZ_TAX.tax, false));

            // launch episerver commerce manager
            launchCommerceManagerPage();

            // login to Episerver commerce manager
            commerceManagerLoginPage.loginToCommerceManager(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            // navigate to order management option from left panel
            commerceManagerHomePage.navigateToLeftPanelOption(CommerceManagerNavigationOptions.LeftPanelOptions.ORDER_MANAGEMENT.text);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.PURCHASE_ORDERS.text, CommerceManagerNavigationOptions.OrderManagementCommonSubmenu.TODAY.text);

            // Open order page & select Summary Tab
            commerceManagerOrderManagement = new EpiCommerceManagerOrderManagement(driver);
            commerceManagerOrderManagement.openOrderAndSelectTab(orderNo, CommerceManagerNavigationOptions.OrderPageTabNames.SUMMARY.text);

            // Assertion on Order Status
            assertTrue(commerceManagerOrderManagement.verifyOrderStatus(EpiDataCommon.OrderStatus.IN_PROGRESS.text));

            // Open order page & select Details Tab
            commerceManagerOrderManagement.selectTab(CommerceManagerNavigationOptions.OrderPageTabNames.DETAILS.text);

            // Release shipment
            commerceManagerOrderManagement.clickReleaseShipment();

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.SHIPPING_RECEIVING.text,
                    CommerceManagerNavigationOptions.ShippingReceiving.SHIPMENTS.text, CommerceManagerNavigationOptions.ShippingReceiving.RELEASED_FOR_SHIPPING.text);

            // Release order shipment and save pick list name
            pickListName = commerceManagerOrderManagement.addShipmentToRelease(orderNo);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.SHIPPING_RECEIVING.text,
                    CommerceManagerNavigationOptions.ShippingReceiving.SHIPMENTS.text, CommerceManagerNavigationOptions.ShippingReceiving.PICK_LISTS.text);

            // Complete order
            trackingNo = generateTrackingNo();
            commerceManagerOrderManagement.selectRecordAndCompleteOrder(pickListName, orderNo, trackingNo);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.PURCHASE_ORDERS.text, CommerceManagerNavigationOptions.OrderManagementCommonSubmenu.TODAY.text);

            // Open order page & select Summary Tab
            commerceManagerOrderManagement.openOrderAndSelectTab(orderNo, CommerceManagerNavigationOptions.OrderPageTabNames.SUMMARY.text);

            // Assertion on Order Status
            assertTrue(commerceManagerOrderManagement.verifyOrderStatus(EpiDataCommon.OrderStatus.COMPLETED.text));
            assertEquals(storeOrderTax, commerceManagerOrderManagement.getOrderTotalTaxesFromUI());

            // Sign Out from Commerce Manager
            commerceManagerHomePage.signOutFromCommerceManager();
        } catch (Exception e) {
            e.printStackTrace();
            VertexLogger.log("Test failed due to error/exception, kindly check logs for more details");
            Assert.fail();
        } finally {
            quitDriver();
            createChromeDriver();

            // launch episerver commerce manager
            launchCommerceManagerPage();

            // login to Episerver commerce manager
            commerceManagerLoginPage = new EpiCommerceManagerLoginPage(driver);
            commerceManagerLoginPage.loginToCommerceManager(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            // navigate to administration option from left panel
            commerceManagerHomePage = new EpiCommerceManagerHomePage(driver);
            commerceManagerHomePage.navigateToLeftPanelOption(CommerceManagerNavigationOptions.LeftPanelOptions.ADMINISTRATION.text);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.AdministrationMenu.CATALOG_SYSTEM.text, CommerceManagerNavigationOptions.AdministrationMenu.CatalogSystemOptions.WAREHOUSES.text);

            // Set default address of Warehouse
            commerceManagerAdministration = new EpiCommerceManagerAdministration(driver);
            commerceManagerAdministration.setDefaultAddressOfWarehouse(EpiDataCommon.WarehouseNames.NEW_YORK_STORE.text);

            // Sign Out from Commerce Manager
            commerceManagerHomePage.signOutFromCommerceManager();
        }
    }

    /**
     * CEPI-267 Test Case - Create Sale Order for LATAM (CR-CO) and Invoice
     */
    @Test(groups = {"epi_regression"})
    public void salesOrderInvoiceLATAMCRCOTest() {
        addressName = Address.Borgota.city + ", " + Address.Borgota.country.iso2code;
        try {
            // launch episerver commerce manager
            launchCommerceManagerPage();

            // login to Episerver commerce manager
            commerceManagerLoginPage = new EpiCommerceManagerLoginPage(driver);
            commerceManagerLoginPage.loginToCommerceManager(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            // navigate to administration option from left panel
            commerceManagerHomePage = new EpiCommerceManagerHomePage(driver);
            commerceManagerHomePage.navigateToLeftPanelOption(CommerceManagerNavigationOptions.LeftPanelOptions.ADMINISTRATION.text);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.AdministrationMenu.CATALOG_SYSTEM.text, CommerceManagerNavigationOptions.AdministrationMenu.CatalogSystemOptions.WAREHOUSES.text);
            commerceManagerAdministration = new EpiCommerceManagerAdministration(driver);
            commerceManagerAdministration.selectWarehouse(EpiDataCommon.WarehouseNames.NEW_YORK_STORE.text);
            commerceManagerAdministration.setWarehouseAddress(Address.SanJose.addressLine1, Address.SanJose.city, Address.SanJose.city, Address.SanJose.country.iso2code,
                    Address.SanJose.country.fullName, Address.SanJose.zip5, Address.SanJose.country.iso2code);

            // Sign Out from Commerce Manager
            commerceManagerHomePage.signOutFromCommerceManager();

            // Launch Epi-Server's store
            launchEpiStorePage();

            // Login to storefront
            storeLoginPage = new EpiStoreLoginPage(driver);
            storeLoginPage.loginToEpiStore(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);
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
            assertEquals(storeOrderTax, orderConfirmationPage.calculatePercentageBasedTax(EpiDataCommon.TaxRates.ZERO_TAX.tax));

            // launch episerver commerce manager
            launchCommerceManagerPage();

            // login to Episerver commerce manager
            commerceManagerLoginPage.loginToCommerceManager(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            // navigate to order management option from left panel
            commerceManagerHomePage.navigateToLeftPanelOption(CommerceManagerNavigationOptions.LeftPanelOptions.ORDER_MANAGEMENT.text);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.PURCHASE_ORDERS.text, CommerceManagerNavigationOptions.OrderManagementCommonSubmenu.TODAY.text);

            // Open order page & select Summary Tab
            commerceManagerOrderManagement = new EpiCommerceManagerOrderManagement(driver);
            commerceManagerOrderManagement.openOrderAndSelectTab(orderNo, CommerceManagerNavigationOptions.OrderPageTabNames.SUMMARY.text);

            // Assertion on Order Status
            assertTrue(commerceManagerOrderManagement.verifyOrderStatus(EpiDataCommon.OrderStatus.IN_PROGRESS.text));

            // Open order page & select Details Tab
            commerceManagerOrderManagement.selectTab(CommerceManagerNavigationOptions.OrderPageTabNames.DETAILS.text);

            // Release shipment
            commerceManagerOrderManagement.clickReleaseShipment();

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.SHIPPING_RECEIVING.text,
                    CommerceManagerNavigationOptions.ShippingReceiving.SHIPMENTS.text, CommerceManagerNavigationOptions.ShippingReceiving.RELEASED_FOR_SHIPPING.text);

            // Release order shipment and save pick list name
            pickListName = commerceManagerOrderManagement.addShipmentToRelease(orderNo);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.SHIPPING_RECEIVING.text,
                    CommerceManagerNavigationOptions.ShippingReceiving.SHIPMENTS.text, CommerceManagerNavigationOptions.ShippingReceiving.PICK_LISTS.text);

            // Complete order
            trackingNo = generateTrackingNo();
            commerceManagerOrderManagement.selectRecordAndCompleteOrder(pickListName, orderNo, trackingNo);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.PURCHASE_ORDERS.text, CommerceManagerNavigationOptions.OrderManagementCommonSubmenu.TODAY.text);

            // Open order page & select Summary Tab
            commerceManagerOrderManagement.openOrderAndSelectTab(orderNo, CommerceManagerNavigationOptions.OrderPageTabNames.SUMMARY.text);

            // Assertion on Order Status
            assertTrue(commerceManagerOrderManagement.verifyOrderStatus(EpiDataCommon.OrderStatus.COMPLETED.text));
            assertEquals(storeOrderTax, commerceManagerOrderManagement.getOrderTotalTaxesFromUI());

            // Sign Out from Commerce Manager
            commerceManagerHomePage.signOutFromCommerceManager();
        } catch (Exception e) {
            e.printStackTrace();
            VertexLogger.log("Test failed due to error/exception, kindly check logs for more details");
            Assert.fail();
        } finally {
            quitDriver();
            createChromeDriver();

            // launch episerver commerce manager
            launchCommerceManagerPage();

            // login to Episerver commerce manager
            commerceManagerLoginPage = new EpiCommerceManagerLoginPage(driver);
            commerceManagerLoginPage.loginToCommerceManager(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            // navigate to administration option from left panel
            commerceManagerHomePage = new EpiCommerceManagerHomePage(driver);
            commerceManagerHomePage.navigateToLeftPanelOption(CommerceManagerNavigationOptions.LeftPanelOptions.ADMINISTRATION.text);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.AdministrationMenu.CATALOG_SYSTEM.text, CommerceManagerNavigationOptions.AdministrationMenu.CatalogSystemOptions.WAREHOUSES.text);

            // Set default address of Warehouse
            commerceManagerAdministration = new EpiCommerceManagerAdministration(driver);
            commerceManagerAdministration.setDefaultAddressOfWarehouse(EpiDataCommon.WarehouseNames.NEW_YORK_STORE.text);

            // Sign Out from Commerce Manager
            commerceManagerHomePage.signOutFromCommerceManager();
        }
    }

    /**
     * CEPI-268 Test Case - Create Sale Order for APAC (India) CGST, SGST and Invoice
     */
    @Test(groups = {"epi_regression"})
    public void salesOrderInvoiceAPACIndiaCGSTSGSTTest() {
        addressName = Address.NewRamdaspeth.city + ", " + Address.NewRamdaspeth.state.abbreviation;
        try {
            // launch episerver commerce manager
            launchCommerceManagerPage();

            // login to Episerver commerce manager
            commerceManagerLoginPage = new EpiCommerceManagerLoginPage(driver);
            commerceManagerLoginPage.loginToCommerceManager(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            // navigate to administration option from left panel
            commerceManagerHomePage = new EpiCommerceManagerHomePage(driver);
            commerceManagerHomePage.navigateToLeftPanelOption(CommerceManagerNavigationOptions.LeftPanelOptions.ADMINISTRATION.text);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.AdministrationMenu.CATALOG_SYSTEM.text, CommerceManagerNavigationOptions.AdministrationMenu.CatalogSystemOptions.WAREHOUSES.text);
            commerceManagerAdministration = new EpiCommerceManagerAdministration(driver);
            commerceManagerAdministration.selectWarehouse(EpiDataCommon.WarehouseNames.NEW_YORK_STORE.text);
            commerceManagerAdministration.setWarehouseAddress(Address.NewRamdaspethAlternate.addressLine1, Address.NewRamdaspethAlternate.city, Address.NewRamdaspethAlternate.state.fullName, Address.NewRamdaspethAlternate.country.iso2code,
                    Address.NewRamdaspethAlternate.country.fullName, Address.NewRamdaspethAlternate.zip5, Address.NewRamdaspethAlternate.state.abbreviation);

            // Sign Out from Commerce Manager
            commerceManagerHomePage.signOutFromCommerceManager();

            // Launch Epi-Server's store
            launchEpiStorePage();

            // Login to storefront
            storeLoginPage = new EpiStoreLoginPage(driver);
            storeLoginPage.loginToEpiStore(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);
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
            assertEquals(storeOrderTax, orderConfirmationPage.calculatePercentageBasedTax(EpiDataCommon.TaxRates.INDIA_GST.tax, false));

            // launch episerver commerce manager
            launchCommerceManagerPage();

            // login to Episerver commerce manager
            commerceManagerLoginPage.loginToCommerceManager(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            // navigate to order management option from left panel
            commerceManagerHomePage.navigateToLeftPanelOption(CommerceManagerNavigationOptions.LeftPanelOptions.ORDER_MANAGEMENT.text);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.PURCHASE_ORDERS.text, CommerceManagerNavigationOptions.OrderManagementCommonSubmenu.TODAY.text);

            // Open order page & select Summary Tab
            commerceManagerOrderManagement = new EpiCommerceManagerOrderManagement(driver);
            commerceManagerOrderManagement.openOrderAndSelectTab(orderNo, CommerceManagerNavigationOptions.OrderPageTabNames.SUMMARY.text);

            // Assertion on Order Status
            assertTrue(commerceManagerOrderManagement.verifyOrderStatus(EpiDataCommon.OrderStatus.IN_PROGRESS.text));

            // Open order page & select Details Tab
            commerceManagerOrderManagement.selectTab(CommerceManagerNavigationOptions.OrderPageTabNames.DETAILS.text);

            // Release shipment
            commerceManagerOrderManagement.clickReleaseShipment();

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.SHIPPING_RECEIVING.text,
                    CommerceManagerNavigationOptions.ShippingReceiving.SHIPMENTS.text, CommerceManagerNavigationOptions.ShippingReceiving.RELEASED_FOR_SHIPPING.text);

            // Release order shipment and save pick list name
            pickListName = commerceManagerOrderManagement.addShipmentToRelease(orderNo);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.SHIPPING_RECEIVING.text,
                    CommerceManagerNavigationOptions.ShippingReceiving.SHIPMENTS.text, CommerceManagerNavigationOptions.ShippingReceiving.PICK_LISTS.text);

            // Complete order
            trackingNo = generateTrackingNo();
            commerceManagerOrderManagement.selectRecordAndCompleteOrder(pickListName, orderNo, trackingNo);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.PURCHASE_ORDERS.text, CommerceManagerNavigationOptions.OrderManagementCommonSubmenu.TODAY.text);

            // Open order page & select Summary Tab
            commerceManagerOrderManagement.openOrderAndSelectTab(orderNo, CommerceManagerNavigationOptions.OrderPageTabNames.SUMMARY.text);

            // Assertion on Order Status
            assertTrue(commerceManagerOrderManagement.verifyOrderStatus(EpiDataCommon.OrderStatus.COMPLETED.text));
            assertEquals(storeOrderTax, commerceManagerOrderManagement.getOrderTotalTaxesFromUI());

            // Sign Out from Commerce Manager
            commerceManagerHomePage.signOutFromCommerceManager();
        } catch (Exception e) {
            e.printStackTrace();
            VertexLogger.log("Test failed due to error/exception, kindly check logs for more details");
            Assert.fail();
        } finally {
            quitDriver();
            createChromeDriver();

            // launch episerver commerce manager
            launchCommerceManagerPage();

            // login to Episerver commerce manager
            commerceManagerLoginPage = new EpiCommerceManagerLoginPage(driver);
            commerceManagerLoginPage.loginToCommerceManager(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            // navigate to administration option from left panel
            commerceManagerHomePage = new EpiCommerceManagerHomePage(driver);
            commerceManagerHomePage.navigateToLeftPanelOption(CommerceManagerNavigationOptions.LeftPanelOptions.ADMINISTRATION.text);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.AdministrationMenu.CATALOG_SYSTEM.text, CommerceManagerNavigationOptions.AdministrationMenu.CatalogSystemOptions.WAREHOUSES.text);

            // Set default address of Warehouse
            commerceManagerAdministration = new EpiCommerceManagerAdministration(driver);
            commerceManagerAdministration.setDefaultAddressOfWarehouse(EpiDataCommon.WarehouseNames.NEW_YORK_STORE.text);

            // Sign Out from Commerce Manager
            commerceManagerHomePage.signOutFromCommerceManager();
        }
    }

    /**
     * CEPI-269 Test Case - Create Sales Order for APAC (India) and Invoice - InterState
     */
    @Test(groups = {"epi_regression"})
    public void salesOrderInvoiceAPACIndiaInterStateTest() {
        addressName = Address.NewRamdaspeth.city + ", " + Address.NewRamdaspeth.state.abbreviation;
        try {
            // launch episerver commerce manager
            launchCommerceManagerPage();

            // login to Episerver commerce manager
            commerceManagerLoginPage = new EpiCommerceManagerLoginPage(driver);
            commerceManagerLoginPage.loginToCommerceManager(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            // navigate to administration option from left panel
            commerceManagerHomePage = new EpiCommerceManagerHomePage(driver);
            commerceManagerHomePage.navigateToLeftPanelOption(CommerceManagerNavigationOptions.LeftPanelOptions.ADMINISTRATION.text);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.AdministrationMenu.CATALOG_SYSTEM.text, CommerceManagerNavigationOptions.AdministrationMenu.CatalogSystemOptions.WAREHOUSES.text);
            commerceManagerAdministration = new EpiCommerceManagerAdministration(driver);
            commerceManagerAdministration.selectWarehouse(EpiDataCommon.WarehouseNames.NEW_YORK_STORE.text);
            commerceManagerAdministration.setWarehouseAddress(Address.PortBlair.addressLine1, Address.PortBlair.city, Address.PortBlair.state.fullName, Address.PortBlair.country.iso2code,
                    Address.PortBlair.country.fullName, Address.PortBlair.zip5, Address.PortBlair.state.abbreviation);

            // Sign Out from Commerce Manager
            commerceManagerHomePage.signOutFromCommerceManager();

            // Launch Epi-Server's store
            launchEpiStorePage();

            // Login to storefront
            storeLoginPage = new EpiStoreLoginPage(driver);
            storeLoginPage.loginToEpiStore(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);
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
            assertEquals(storeOrderTax, orderConfirmationPage.calculatePercentageBasedTax(EpiDataCommon.TaxRates.INDIA_GST.tax, false));

            // launch episerver commerce manager
            launchCommerceManagerPage();

            // login to Episerver commerce manager
            commerceManagerLoginPage.loginToCommerceManager(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            // navigate to order management option from left panel
            commerceManagerHomePage.navigateToLeftPanelOption(CommerceManagerNavigationOptions.LeftPanelOptions.ORDER_MANAGEMENT.text);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.PURCHASE_ORDERS.text, CommerceManagerNavigationOptions.OrderManagementCommonSubmenu.TODAY.text);

            // Open order page & select Summary Tab
            commerceManagerOrderManagement = new EpiCommerceManagerOrderManagement(driver);
            commerceManagerOrderManagement.openOrderAndSelectTab(orderNo, CommerceManagerNavigationOptions.OrderPageTabNames.SUMMARY.text);

            // Assertion on Order Status
            assertTrue(commerceManagerOrderManagement.verifyOrderStatus(EpiDataCommon.OrderStatus.IN_PROGRESS.text));

            // Open order page & select Details Tab
            commerceManagerOrderManagement.selectTab(CommerceManagerNavigationOptions.OrderPageTabNames.DETAILS.text);

            // Release shipment
            commerceManagerOrderManagement.clickReleaseShipment();

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.SHIPPING_RECEIVING.text,
                    CommerceManagerNavigationOptions.ShippingReceiving.SHIPMENTS.text, CommerceManagerNavigationOptions.ShippingReceiving.RELEASED_FOR_SHIPPING.text);

            // Release order shipment and save pick list name
            pickListName = commerceManagerOrderManagement.addShipmentToRelease(orderNo);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.SHIPPING_RECEIVING.text,
                    CommerceManagerNavigationOptions.ShippingReceiving.SHIPMENTS.text, CommerceManagerNavigationOptions.ShippingReceiving.PICK_LISTS.text);

            // Complete order
            trackingNo = generateTrackingNo();
            commerceManagerOrderManagement.selectRecordAndCompleteOrder(pickListName, orderNo, trackingNo);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.OrderManagementMenu.PURCHASE_ORDERS.text, CommerceManagerNavigationOptions.OrderManagementCommonSubmenu.TODAY.text);

            // Open order page & select Summary Tab
            commerceManagerOrderManagement.openOrderAndSelectTab(orderNo, CommerceManagerNavigationOptions.OrderPageTabNames.SUMMARY.text);

            // Assertion on Order Status
            assertTrue(commerceManagerOrderManagement.verifyOrderStatus(EpiDataCommon.OrderStatus.COMPLETED.text));
            assertEquals(storeOrderTax, commerceManagerOrderManagement.getOrderTotalTaxesFromUI());

            // Sign Out from Commerce Manager
            commerceManagerHomePage.signOutFromCommerceManager();
        } catch (Exception e) {
            e.printStackTrace();
            VertexLogger.log("Test failed due to error/exception, kindly check logs for more details");
            Assert.fail();
        } finally {
            quitDriver();
            createChromeDriver();

            // launch episerver commerce manager
            launchCommerceManagerPage();

            // login to Episerver commerce manager
            commerceManagerLoginPage = new EpiCommerceManagerLoginPage(driver);
            commerceManagerLoginPage.loginToCommerceManager(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            // navigate to administration option from left panel
            commerceManagerHomePage = new EpiCommerceManagerHomePage(driver);
            commerceManagerHomePage.navigateToLeftPanelOption(CommerceManagerNavigationOptions.LeftPanelOptions.ADMINISTRATION.text);

            // select submenu option
            commerceManagerHomePage.selectSubmenuOption(CommerceManagerNavigationOptions.AdministrationMenu.CATALOG_SYSTEM.text, CommerceManagerNavigationOptions.AdministrationMenu.CatalogSystemOptions.WAREHOUSES.text);

            // Set default address of Warehouse
            commerceManagerAdministration = new EpiCommerceManagerAdministration(driver);
            commerceManagerAdministration.setDefaultAddressOfWarehouse(EpiDataCommon.WarehouseNames.NEW_YORK_STORE.text);

            // Sign Out from Commerce Manager
            commerceManagerHomePage.signOutFromCommerceManager();
        }
    }
}
