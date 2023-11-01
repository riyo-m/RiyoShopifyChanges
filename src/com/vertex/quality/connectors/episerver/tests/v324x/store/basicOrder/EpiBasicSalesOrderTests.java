package com.vertex.quality.connectors.episerver.tests.v324x.store.basicOrder;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.enums.TaxRate;
import com.vertex.quality.connectors.episerver.common.enums.EpiDataCommon;
import com.vertex.quality.connectors.episerver.common.enums.NavigationAndMenuOptions;
import com.vertex.quality.connectors.episerver.common.enums.PaymentMethodType;
import com.vertex.quality.connectors.episerver.common.enums.TaxPercentages;
import com.vertex.quality.connectors.episerver.pages.epiCommon.*;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiAdminHomePage;
import com.vertex.quality.connectors.episerver.pages.v324x.EpiAdministrationHomePage;
import com.vertex.quality.connectors.episerver.pages.v324x.EpiWarehousePage;
import com.vertex.quality.connectors.episerver.tests.base.EpiBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Test class that validates basic sales orders for Episerver
 *
 * @author Shivam.Soni
 */
public class EpiBasicSalesOrderTests extends EpiBaseTest {

    EpiStoreLoginPage storeLoginPage;
    EpiStoreFrontHomePage storeFrontHomePage;
    EpiAdminHomePage adminHomePage;
    EpiAdministrationHomePage administrationHomePage;
    EpiWarehousePage warehousePage;
    EpiCartPage cartPage;
    EpiCheckoutPage checkoutPage;
    EpiAddressPage addressPage;
    EpiOrderConfirmationPage orderConfirmationPage;
    String addressName;

    /**
     * CEPI-292 Test Case -Create Sales Order with no State Tax (v3.2.4)
     */
    @Test(groups = {"epi_smoke_v324", "epi_regression_v324"})
    public void salesOrderWithNoStateTax() {
        addressName = Address.Delaware.city + ", " + Address.Delaware.state.abbreviation;
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
        warehousePage.setWarehouseAddress(Address.Colorado.addressLine1, Address.Colorado.city, Address.Colorado.state.fullName, Address.Colorado.country.iso2code,
                Address.Colorado.country.fullName, Address.Colorado.zip5, Address.Colorado.state.abbreviation);

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
        // select payment type
        checkoutPage.choosePaymentMethod(PaymentMethodType.CASH_ON_DELIVERY.text);

        // Place order
        orderConfirmationPage = checkoutPage.clickPlaceOrderButton();

        // Assertion on Actual & Expected values
        assertEquals(orderConfirmationPage.calculatePercentageBasedTax(TaxPercentages.ZERO_PERCENT.taxPercent), orderConfirmationPage.getTaxCostFromUI());
    }

    /**
     * CEPI-291 Test Case -Create Sales Order with no State Tax, locally administered (v3.2.4)
     */
    @Test(groups = {"epi_regression_v324"})
    public void salesOrderWithNoStateTaxLocallyAdministered() {
        addressName = Address.Juneau.city + ", " + Address.Juneau.state.abbreviation;
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
            addressPage.addNewAddress(addressName, Address.Juneau.addressLine1, Address.Juneau.zip5, Address.Juneau.city, Address.Juneau.state.fullName, Address.Juneau.country.fullName);
        }
        // select payment type
        checkoutPage.choosePaymentMethod(PaymentMethodType.CASH_ON_DELIVERY.text);

        // Place order
        orderConfirmationPage = checkoutPage.clickPlaceOrderButton();

        // Assertion on Actual & Expected values
        assertEquals(orderConfirmationPage.calculatePercentageBasedTax(TaxRate.Juneau.tax), orderConfirmationPage.getTaxCostFromUI());
    }

    /**
     * CEPI-290 Test Case -Create Sales Order with Modified Origin State (v3.2.4)
     */
    @Test(groups = {"epi_smoke_v324", "epi_regression_v324"})
    public void salesOrderWithModifiedOriginState() {
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
        warehousePage.setWarehouseAddress(Address.LosAngeles.addressLine1, Address.LosAngeles.city, Address.LosAngeles.state.fullName, Address.LosAngeles.country.iso2code,
                Address.LosAngeles.country.fullName, Address.LosAngeles.zip5, Address.LosAngeles.state.abbreviation);

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
        // select payment type
        checkoutPage.choosePaymentMethod(PaymentMethodType.CASH_ON_DELIVERY.text);

        // Place order
        orderConfirmationPage = checkoutPage.clickPlaceOrderButton();

        // Assertion on Actual & Expected values
        assertEquals(orderConfirmationPage.calculatePercentageBasedTax(TaxRate.LosAngeles.tax), orderConfirmationPage.getTaxCostFromUI());
    }
}
