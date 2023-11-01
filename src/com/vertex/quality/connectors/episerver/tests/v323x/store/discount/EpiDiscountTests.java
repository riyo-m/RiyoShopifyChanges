package com.vertex.quality.connectors.episerver.tests.v323x.store.discount;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.enums.TaxRate;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.episerver.common.enums.CommerceManagerNavigationOptions;
import com.vertex.quality.connectors.episerver.common.enums.EpiDataCommon;
import com.vertex.quality.connectors.episerver.common.enums.NavigationAndMenuOptions;
import com.vertex.quality.connectors.episerver.common.enums.PaymentMethodType;
import com.vertex.quality.connectors.episerver.pages.epiCommon.*;
import com.vertex.quality.connectors.episerver.pages.v323x.*;
import com.vertex.quality.connectors.episerver.tests.base.EpiBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Test class that contains all tests related to Epi-server's discount functionality
 *
 * @author Shivam.Soni
 */
public class EpiDiscountTests extends EpiBaseTest {

    EpiCommerceManagerLoginPage commerceManagerLoginPage;
    EpiCommerceManagerHomePage commerceManagerHomePage;
    EpiCommerceManagerAdministration commerceManagerAdministration;
    EpiCommerceManagerOrderManagement commerceManagerOrderManagement;
    EpiStoreLoginPage storeLoginPage;
    EpiAdminHomePage adminHomePage;
    EpiCommercePage commercePage;
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
     * CEPI-274 EPI - Test Case -Create Sales Order with Discount - Multi line order with Discount Line Percentage
     */
    @Test(groups = {"epi_smoke", "epi_regression"})
    public void salesOrderLineItemPercentDiscountTest() {
        addressName = Address.LosAngeles.city + ", " + Address.LosAngeles.state.abbreviation;
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

            // Launch Epi-Server's store
            launchEpiStorePage();

            // Login to storefront
            storeLoginPage = new EpiStoreLoginPage(driver);
            storeLoginPage.loginToEpiStore(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            adminHomePage = new EpiAdminHomePage(driver);
            adminHomePage.clickOnMainMenu(NavigationAndMenuOptions.SiteNavigationOptions.COMMERCE.text);
            adminHomePage.clickOnSubMenu(NavigationAndMenuOptions.CommerceMenuOptions.MARKETING.text);

            commercePage = new EpiCommercePage(driver);
            commercePage.selectLeftPanelOption(NavigationAndMenuOptions.CommerceSubmenuOptions.CAMPAIGN_STATUS.text, NavigationAndMenuOptions.CommerceSubmenuOptions.CampaignStatusOptions.ALL.text);
            commercePage.expandMainGrid();
            if (commercePage.checkIfCouponExists(EpiDataCommon.Coupons.FIVE_PERCENT_ITEM.text)) {
                commercePage.createItemBasedDiscountCoupon(true, EpiDataCommon.Coupons.FIVE_PERCENT_ITEM.text, EpiDataCommon.CouponAmountOrPercent.VALUE_FIVE.text);
            }

            launchEpiStorePage();
            storeFrontHomePage = new EpiStoreFrontHomePage(driver);
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
            assertEquals(orderConfirmationPage.calculatePercentageBasedTax(TaxRate.LosAngeles.tax), orderConfirmationPage.getTaxCostFromUI());
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
     * CEPI-273 EPI - Test Case -Create Sales Order with Discount - Multi line order with Discount line Amount
     */
    @Test(groups = {"epi_regression"})
    public void salesOrderLineItemAmountDiscountTest() {
        addressName = Address.LosAngeles.city + ", " + Address.LosAngeles.state.abbreviation;
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

            // Launch Epi-Server's store
            launchEpiStorePage();

            // Login to storefront
            storeLoginPage = new EpiStoreLoginPage(driver);
            storeLoginPage.loginToEpiStore(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            adminHomePage = new EpiAdminHomePage(driver);
            adminHomePage.clickOnMainMenu(NavigationAndMenuOptions.SiteNavigationOptions.COMMERCE.text);
            adminHomePage.clickOnSubMenu(NavigationAndMenuOptions.CommerceMenuOptions.MARKETING.text);

            commercePage = new EpiCommercePage(driver);
            commercePage.selectLeftPanelOption(NavigationAndMenuOptions.CommerceSubmenuOptions.CAMPAIGN_STATUS.text, NavigationAndMenuOptions.CommerceSubmenuOptions.CampaignStatusOptions.ALL.text);
            commercePage.expandMainGrid();
            if (commercePage.checkIfCouponExists(EpiDataCommon.Coupons.TEN_DOLLAR_ITEM.text)) {
                commercePage.createItemBasedDiscountCoupon(false, EpiDataCommon.Coupons.TEN_DOLLAR_ITEM.text, EpiDataCommon.CouponAmountOrPercent.VALUE_TEN.text);
            }

            launchEpiStorePage();
            storeFrontHomePage = new EpiStoreFrontHomePage(driver);
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
            assertEquals(orderConfirmationPage.calculatePercentageBasedTax(TaxRate.LosAngeles.tax), orderConfirmationPage.getTaxCostFromUI());
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
     * CEPI-272 EPI - Test Case -Create Sales Order with Discount - Multi Line Order with Discount Shipping Amount
     */
    @Test(groups = {"epi_regression"})
    public void salesOrderShippingAmountDiscountTest() {
        addressName = Address.LosAngeles.city + ", " + Address.LosAngeles.state.abbreviation;
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

            // Launch Epi-Server's store
            launchEpiStorePage();

            // Login to storefront
            storeLoginPage = new EpiStoreLoginPage(driver);
            storeLoginPage.loginToEpiStore(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            adminHomePage = new EpiAdminHomePage(driver);
            adminHomePage.clickOnMainMenu(NavigationAndMenuOptions.SiteNavigationOptions.COMMERCE.text);
            adminHomePage.clickOnSubMenu(NavigationAndMenuOptions.CommerceMenuOptions.MARKETING.text);

            commercePage = new EpiCommercePage(driver);
            commercePage.selectLeftPanelOption(NavigationAndMenuOptions.CommerceSubmenuOptions.CAMPAIGN_STATUS.text, NavigationAndMenuOptions.CommerceSubmenuOptions.CampaignStatusOptions.ALL.text);
            commercePage.expandMainGrid();
            if (commercePage.checkIfCouponExists(EpiDataCommon.Coupons.ONE_OFF_SHIP.text)) {
                commercePage.createShippingBasedDiscountCoupon(true, EpiDataCommon.Coupons.ONE_OFF_SHIP.text, EpiDataCommon.CouponAmountOrPercent.VALUE_ONE.text);
            }

            launchEpiStorePage();
            storeFrontHomePage = new EpiStoreFrontHomePage(driver);
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
            assertEquals(orderConfirmationPage.calculatePercentageBasedTax(TaxRate.LosAngeles.tax), orderConfirmationPage.getTaxCostFromUI());
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
     * CEPI-271 EPI - Test Case - Create Sale Order with a Discount Shipping for VAT (Intra EU DE-DE) and Invoice
     */
    @Test(groups = {"epi_regression"})
    public void salesOrderShippingAmountDiscountVATTest() {
        addressName = Address.BerlinAlternate.city + ", " + Address.BerlinAlternate.country.iso2code;
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
            commerceManagerAdministration.setWarehouseAddress(Address.BerlinAlternate.addressLine1, Address.BerlinAlternate.city, Address.BerlinAlternate.city, Address.BerlinAlternate.country.iso2code,
                    Address.BerlinAlternate.country.fullName, Address.BerlinAlternate.zip5, Address.BerlinAlternate.country.iso2code);

            // Launch Epi-Server's store
            launchEpiStorePage();

            // Login to storefront
            storeLoginPage = new EpiStoreLoginPage(driver);
            storeLoginPage.loginToEpiStore(EPI_SERVER_ADMIN_USERNAME, EPI_SERVER_ADMIN_PASSWORD);

            adminHomePage = new EpiAdminHomePage(driver);
            adminHomePage.clickOnMainMenu(NavigationAndMenuOptions.SiteNavigationOptions.COMMERCE.text);
            adminHomePage.clickOnSubMenu(NavigationAndMenuOptions.CommerceMenuOptions.MARKETING.text);

            commercePage = new EpiCommercePage(driver);
            commercePage.selectLeftPanelOption(NavigationAndMenuOptions.CommerceSubmenuOptions.CAMPAIGN_STATUS.text, NavigationAndMenuOptions.CommerceSubmenuOptions.CampaignStatusOptions.ALL.text);
            commercePage.expandMainGrid();
            if (commercePage.checkIfCouponExists(EpiDataCommon.Coupons.TEN_DOLLAR_ITEM.text)) {
                commercePage.createItemBasedDiscountCoupon(false, EpiDataCommon.Coupons.TEN_DOLLAR_ITEM.text, EpiDataCommon.CouponAmountOrPercent.VALUE_TEN.text);
            }

            launchEpiStorePage();
            storeFrontHomePage = new EpiStoreFrontHomePage(driver);
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
