package com.vertex.quality.connectors.episerver.tests.v323x.store.productCodeClass;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.episerver.common.enums.CommerceManagerNavigationOptions;
import com.vertex.quality.connectors.episerver.common.enums.EpiDataCommon;
import com.vertex.quality.connectors.episerver.common.enums.PaymentMethodType;
import com.vertex.quality.connectors.episerver.pages.epiCommon.*;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiCommerceManagerAdministration;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiCommerceManagerHomePage;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiCommerceManagerLoginPage;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiCommerceManagerOrderManagement;
import com.vertex.quality.connectors.episerver.tests.base.EpiBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class EpiProductCodeClassTests extends EpiBaseTest {

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
     * CEPI-281 EPI - Test Case - Create Sales order with Product Class Exemption
     */
    @Test(groups = {"epi_regression"})
    public void salesOrderWithProductClassTest() {
        addressName = Address.Berwyn.city + ", " + Address.Berwyn.state.abbreviation;
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
            cartPage.searchAndAddProductToCart(EpiDataCommon.ProductNames.EXEMPTED_PRODUCT1.text);

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
            assertEquals(storeOrderTax, orderConfirmationPage.calculateIndividualPercentBaseTaxesAndDoAddition(EpiDataCommon.TaxRates.ZERO_TAX.tax, EpiDataCommon.TaxRates.PA_TAX.tax));

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
