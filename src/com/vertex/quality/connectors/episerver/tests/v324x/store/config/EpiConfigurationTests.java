package com.vertex.quality.connectors.episerver.tests.v324x.store.config;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.enums.TaxRate;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.episerver.common.enums.EpiDataCommon;
import com.vertex.quality.connectors.episerver.common.enums.NavigationAndMenuOptions;
import com.vertex.quality.connectors.episerver.common.enums.PaymentMethodType;
import com.vertex.quality.connectors.episerver.pages.epiCommon.*;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiAdminHomePage;
import com.vertex.quality.connectors.episerver.pages.v324x.*;
import com.vertex.quality.connectors.episerver.tests.base.EpiBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Test class that contains all the tests related to Epi-server's different configuration.
 *
 * @author Shivam.Soni
 */
public class EpiConfigurationTests extends EpiBaseTest {

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
     * CEPI-296 EPI - Test Case -Create Sales Order with Invoice OFF (v3.2.4)
     */
    @Test(groups = {"epi_smoke_v324", "epi_regression_v324"})
    public void invoiceOffTest() {
        addressName = Address.LosAngeles.city + ", " + Address.LosAngeles.state.abbreviation;
        try {
            // Launch Epi-Server's store
            launch324EpiStorePage();

            // Login to storefront
            storeLoginPage = new EpiStoreLoginPage(driver);
            storeLoginPage.loginToEpiStore(EpiDataCommon.EpiCredentials.EPI_324_STORE_USER.text, EpiDataCommon.EpiCredentials.EPI_324_STORE_PASS.text);
            storeFrontHomePage = new EpiStoreFrontHomePage(driver);
            storeFrontHomePage.navigateToHomePage();

            // navigate to Vertex O Series Page & disable invoice
            adminHomePage = new EpiAdminHomePage(driver);
            adminHomePage.clickOnMainMenu(NavigationAndMenuOptions.SiteNavigationOptions.CMS_EDIT.text);
            adminHomePage.clickOnSubMenu(NavigationAndMenuOptions.CmsMenuOptions.VERTEX_O_SERIES.text);
            epiOSeries = new EpiOSeriesPage(driver);
            epiOSeries.goToTab(NavigationAndMenuOptions.VertexOSeriesTabs.INVOICING.text);
            epiOSeries.enableOrDisableInvoicing(false);
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

            // navigate to Vertex O Series Page & enable invoice
            adminHomePage = new EpiAdminHomePage(driver);
            adminHomePage.clickOnMainMenu(NavigationAndMenuOptions.SiteNavigationOptions.CMS_EDIT.text);
            adminHomePage.clickOnSubMenu(NavigationAndMenuOptions.CmsMenuOptions.VERTEX_O_SERIES.text);
            epiOSeries = new EpiOSeriesPage(driver);
            epiOSeries.goToTab(NavigationAndMenuOptions.VertexOSeriesTabs.INVOICING.text);
            epiOSeries.enableOrDisableInvoicing(true);
            epiOSeries.saveVertexOSeriesChanges();
        }
    }
}
