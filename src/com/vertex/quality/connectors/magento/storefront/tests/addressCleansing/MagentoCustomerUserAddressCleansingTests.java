package com.vertex.quality.connectors.magento.storefront.tests.addressCleansing;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.enums.State;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminOrderViewInfoPage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminOrdersPage;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import com.vertex.quality.connectors.magento.common.enums.MagentoUSAddresses;
import com.vertex.quality.connectors.magento.storefront.pages.*;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

/**
 * CCMMER2-665 Magento O-Series - Test Automation - Storefront - Part IV
 * Magento M2 Storefront - Address Cleansing tests
 *
 * @author Shivam.Soni
 */
public class MagentoCustomerUserAddressCleansingTests extends MagentoAdminBaseTest {

    M2StorefrontLoginPage loginPage;
    M2StorefrontHomePage homePage;
    M2StorefrontBannerPage storefrontBannerPage;
    M2StorefrontSearchResultPage searchResultPage;
    M2StorefrontShippingInfoPage shippingPage;
    M2StorefrontPaymentMethodPage paymentPage;
    M2StorefrontThankYouPage thankYouPage;
    M2AdminOrdersPage ordersPage;
    M2AdminOrderViewInfoPage orderInfoPage;

    String cleansedAddress;
    String storeShippingAddress;
    String orderNo;
    String adminBillingAddress;
    String adminShippingAddress;

    /**
     * CMAGM2-1233
     * CMAG - Test Case - Validate Sales Order with Address Cleansing invalid state address
     */
    @Test(groups = {"magento_regression", "magento_smoke"})
    public void addressCleansingWithInvalidStateTest() {
        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        loginPage = homePage.clickSignInButton();
        loginPage.signInToMagentoTap244Store(MagentoData.MAGENTO_2_0_STORE_USER.data, MagentoData.MAGENTO_2_0_STORE_PASSWORD.data);
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(true, false, MagentoUSAddresses.Bothell.addressLine1,
                MagentoUSAddresses.Bothell.country.fullName, State.WA.fullName,
                MagentoUSAddresses.Bothell.city, MagentoUSAddresses.Bothell.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Read the cleansed address & assert for Address Cleansing
        cleansedAddress = shippingPage.getVertexCleansedAddress();
        assertTrue(cleansedAddress.contains(MagentoUSAddresses.Bothell.cleansedAddressLine1));
        assertTrue(cleansedAddress.contains(MagentoUSAddresses.Bothell.country.fullName));
        assertTrue(cleansedAddress.contains(MagentoUSAddresses.Bothell.state.fullName));
        assertTrue(cleansedAddress.contains(MagentoUSAddresses.Bothell.city));
        assertTrue(cleansedAddress.contains(MagentoUSAddresses.Bothell.zip9));
        shippingPage.updateCleansedAddress();

        // Read the shipping address & assert for Address Cleansing
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.setSameBillingShippingAddress();
        storeShippingAddress = paymentPage.getShippingAddress();
        assertTrue(storeShippingAddress.contains(MagentoUSAddresses.Bothell.cleansedAddressLine1));
        assertTrue(storeShippingAddress.contains(MagentoUSAddresses.Bothell.country.fullName));
        assertTrue(storeShippingAddress.contains(MagentoUSAddresses.Bothell.state.fullName));
        assertTrue(storeShippingAddress.contains(MagentoUSAddresses.Bothell.city));
        assertTrue(storeShippingAddress.contains(MagentoUSAddresses.Bothell.zip9));

        // Assert Cleansed address Vs Shipping Address
        assertTrue(storeShippingAddress.contains(cleansedAddress));

        // Validate the Tax & Place order & Save order number
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculateIndividualPercentBasedTax(true, true, 9.3));
        thankYouPage = paymentPage.clickPlaceOrderButton();
        orderNo = thankYouPage.getOrderNumber();
        assertFalse(orderNo.isEmpty());

        // Sign out of Storefront
        storefrontBannerPage.signOutFromStore();

        // Login to Admin panel, Search for Order number
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);

        // Get billing address from Admin panel & assert
        adminBillingAddress = orderInfoPage.getBillingAddress();
        assertTrue(adminBillingAddress.contains(MagentoUSAddresses.Bothell.cleansedAddressLine1));
        assertTrue(adminBillingAddress.contains(MagentoUSAddresses.Bothell.country.fullName));
        assertTrue(adminBillingAddress.contains(MagentoUSAddresses.Bothell.state.fullName));
        assertTrue(adminBillingAddress.contains(MagentoUSAddresses.Bothell.city));
        assertTrue(adminBillingAddress.contains(MagentoUSAddresses.Bothell.zip9));

        // Get billing address from Admin panel & assert
        adminShippingAddress = orderInfoPage.getBillingAddress();
        assertTrue(adminShippingAddress.contains(MagentoUSAddresses.Bothell.cleansedAddressLine1));
        assertTrue(adminShippingAddress.contains(MagentoUSAddresses.Bothell.country.fullName));
        assertTrue(adminShippingAddress.contains(MagentoUSAddresses.Bothell.state.fullName));
        assertTrue(adminShippingAddress.contains(MagentoUSAddresses.Bothell.city));
        assertTrue(adminShippingAddress.contains(MagentoUSAddresses.Bothell.zip9));

        assertEquals(adminShippingAddress, adminBillingAddress);
    }

    /**
     * CMAGM2-1231
     * CMAG - Test Case - Validate Sales Order with Address Cleansing invalid street address
     */
    @Test(groups = {"magento_regression"})
    public void addressCleansingWithInvalidStreetTest() {
        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        loginPage = homePage.clickSignInButton();
        loginPage.signInToMagentoTap244Store(MagentoData.MAGENTO_2_0_STORE_USER.data, MagentoData.MAGENTO_2_0_STORE_PASSWORD.data);
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetailsWithStreet2(true, false, MagentoUSAddresses.SanGabriel.addressLine1,
                MagentoUSAddresses.SanGabriel.addressLine2, MagentoUSAddresses.SanGabriel.country.fullName, MagentoUSAddresses.SanGabriel.state.fullName,
                MagentoUSAddresses.SanGabriel.city, MagentoUSAddresses.SanGabriel.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Read the cleansed address & assert for Address Cleansing
        cleansedAddress = shippingPage.getVertexCleansedAddress();
        assertTrue(cleansedAddress.contains(MagentoUSAddresses.SanGabriel.cleansedAddressLine1));
        assertTrue(cleansedAddress.contains(MagentoUSAddresses.SanGabriel.country.fullName));
        assertTrue(cleansedAddress.contains(MagentoUSAddresses.SanGabriel.state.fullName));
        assertTrue(cleansedAddress.contains(MagentoUSAddresses.SanGabriel.city));
        assertTrue(cleansedAddress.contains(MagentoUSAddresses.SanGabriel.zip9));
        shippingPage.updateCleansedAddress();

        // Read the shipping address & assert for Address Cleansing
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.setSameBillingShippingAddress();
        storeShippingAddress = paymentPage.getShippingAddress();
        assertTrue(storeShippingAddress.contains(MagentoUSAddresses.SanGabriel.cleansedAddressLine1));
        assertTrue(storeShippingAddress.contains(MagentoUSAddresses.SanGabriel.country.fullName));
        assertTrue(storeShippingAddress.contains(MagentoUSAddresses.SanGabriel.state.fullName));
        assertTrue(storeShippingAddress.contains(MagentoUSAddresses.SanGabriel.city));
        assertTrue(storeShippingAddress.contains(MagentoUSAddresses.SanGabriel.zip9));

        // Assert Cleansed address Vs Shipping Address
        assertTrue(storeShippingAddress.contains(cleansedAddress));

        // Validate the Tax & Place order & Save order number
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculateIndividualPercentBasedTax(true, true, 10.25));
        thankYouPage = paymentPage.clickPlaceOrderButton();
        orderNo = thankYouPage.getOrderNumber();
        assertFalse(orderNo.isEmpty());

        // Sign out of Storefront
        storefrontBannerPage.signOutFromStore();

        // Login to Admin panel, Search for Order number
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);

        // Get billing address from Admin panel & assert
        adminBillingAddress = orderInfoPage.getBillingAddress();
        assertTrue(adminBillingAddress.contains(MagentoUSAddresses.SanGabriel.cleansedAddressLine1));
        assertTrue(adminBillingAddress.contains(MagentoUSAddresses.SanGabriel.country.fullName));
        assertTrue(adminBillingAddress.contains(MagentoUSAddresses.SanGabriel.state.fullName));
        assertTrue(adminBillingAddress.contains(MagentoUSAddresses.SanGabriel.city));
        assertTrue(adminBillingAddress.contains(MagentoUSAddresses.SanGabriel.zip9));

        // Get billing address from Admin panel & assert
        adminShippingAddress = orderInfoPage.getBillingAddress();
        assertTrue(adminShippingAddress.contains(MagentoUSAddresses.SanGabriel.cleansedAddressLine1));
        assertTrue(adminShippingAddress.contains(MagentoUSAddresses.SanGabriel.country.fullName));
        assertTrue(adminShippingAddress.contains(MagentoUSAddresses.SanGabriel.state.fullName));
        assertTrue(adminShippingAddress.contains(MagentoUSAddresses.SanGabriel.city));
        assertTrue(adminShippingAddress.contains(MagentoUSAddresses.SanGabriel.zip9));

        assertEquals(adminShippingAddress, adminBillingAddress);
    }

    /**
     * CMAGM2-1232
     * CMAG - Test Case - Validate Sales Order with Address Cleansing invalid city address
     */
    @Test(groups = {"magento_regression"})
    public void addressCleansingWithInvalidCityTest() {
        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        loginPage = homePage.clickSignInButton();
        loginPage.signInToMagentoTap244Store(MagentoData.MAGENTO_2_0_STORE_USER.data, MagentoData.MAGENTO_2_0_STORE_PASSWORD.data);
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(true, false, Address.Philadelphia.addressLine1,
                Address.Philadelphia.country.fullName, Address.Philadelphia.state.fullName,
                Address.LosAngeles.city, Address.Philadelphia.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Read the cleansed address & assert for Address Cleansing
        cleansedAddress = shippingPage.getVertexCleansedAddress();
        assertTrue(cleansedAddress.contains(Address.Philadelphia.addressLine1));
        assertTrue(cleansedAddress.contains(Address.Philadelphia.country.fullName));
        assertTrue(cleansedAddress.contains(Address.Philadelphia.state.fullName));
        assertTrue(cleansedAddress.contains(Address.Philadelphia.city));
        assertTrue(cleansedAddress.contains(Address.Philadelphia.zip9));
        shippingPage.updateCleansedAddress();

        // Read the shipping address & assert for Address Cleansing
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.setSameBillingShippingAddress();
        storeShippingAddress = paymentPage.getShippingAddress();
        assertTrue(storeShippingAddress.contains(Address.Philadelphia.addressLine1));
        assertTrue(storeShippingAddress.contains(Address.Philadelphia.country.fullName));
        assertTrue(storeShippingAddress.contains(Address.Philadelphia.state.fullName));
        assertTrue(storeShippingAddress.contains(Address.Philadelphia.city));
        assertTrue(storeShippingAddress.contains(Address.Philadelphia.zip9));

        // Assert Cleansed address Vs Shipping Address
        assertTrue(storeShippingAddress.contains(cleansedAddress));

        // Validate the Tax & Place order & Save order number
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculateIndividualPercentBasedTax(true, true, 6));
        thankYouPage = paymentPage.clickPlaceOrderButton();
        orderNo = thankYouPage.getOrderNumber();
        assertFalse(orderNo.isEmpty());

        // Sign out of Storefront
        storefrontBannerPage.signOutFromStore();

        // Login to Admin panel, Search for Order number
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);

        // Get billing address from Admin panel & assert
        adminBillingAddress = orderInfoPage.getBillingAddress();
        assertTrue(adminBillingAddress.contains(Address.Philadelphia.addressLine1));
        assertTrue(adminBillingAddress.contains(Address.Philadelphia.country.fullName));
        assertTrue(adminBillingAddress.contains(Address.Philadelphia.state.fullName));
        assertTrue(adminBillingAddress.contains(Address.Philadelphia.city));
        assertTrue(adminBillingAddress.contains(Address.Philadelphia.zip9));

        // Get billing address from Admin panel & assert
        adminShippingAddress = orderInfoPage.getBillingAddress();
        assertTrue(adminShippingAddress.contains(Address.Philadelphia.addressLine1));
        assertTrue(adminShippingAddress.contains(Address.Philadelphia.country.fullName));
        assertTrue(adminShippingAddress.contains(Address.Philadelphia.state.fullName));
        assertTrue(adminShippingAddress.contains(Address.Philadelphia.city));
        assertTrue(adminShippingAddress.contains(Address.Philadelphia.zip9));

        assertEquals(adminShippingAddress, adminBillingAddress);
    }

    /**
     * CMAGM2-1234
     * CMAG - Test Case - Validate Sales Order with Address Cleansing invalid zip address
     */
    @Test(groups = {"magento_regression"})
    public void addressCleansingWithInvalidZipTest() {
        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        loginPage = homePage.clickSignInButton();
        loginPage.signInToMagentoTap244Store(MagentoData.MAGENTO_2_0_STORE_USER.data, MagentoData.MAGENTO_2_0_STORE_PASSWORD.data);
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(true, false, Address.UniversalCity.addressLine1,
                Address.UniversalCity.country.fullName, Address.UniversalCity.state.fullName,
                Address.UniversalCity.city, "00000");
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Read the cleansed address & assert for Address Cleansing
        cleansedAddress = shippingPage.getVertexCleansedAddress();
        assertTrue(cleansedAddress.contains(Address.UniversalCity.cleansedAddressLine1));
        assertTrue(cleansedAddress.contains(Address.UniversalCity.country.fullName));
        assertTrue(cleansedAddress.contains(Address.UniversalCity.state.fullName));
        assertTrue(cleansedAddress.contains(Address.UniversalCity.city));
        assertTrue(cleansedAddress.contains(Address.UniversalCity.zip9));
        shippingPage.updateCleansedAddress();

        // Read the shipping address & assert for Address Cleansing
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.setSameBillingShippingAddress();
        storeShippingAddress = paymentPage.getShippingAddress();
        assertTrue(storeShippingAddress.contains(Address.UniversalCity.cleansedAddressLine1));
        assertTrue(storeShippingAddress.contains(Address.UniversalCity.country.fullName));
        assertTrue(storeShippingAddress.contains(Address.UniversalCity.state.fullName));
        assertTrue(storeShippingAddress.contains(Address.UniversalCity.city));
        assertTrue(storeShippingAddress.contains(Address.UniversalCity.zip9));

        // Assert Cleansed address Vs Shipping Address
        assertTrue(storeShippingAddress.contains(cleansedAddress));

        // Validate the Tax & Place order & Save order number
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculateIndividualPercentBasedTax(true, true, 9.5));
        thankYouPage = paymentPage.clickPlaceOrderButton();
        orderNo = thankYouPage.getOrderNumber();
        assertFalse(orderNo.isEmpty());

        // Sign out of Storefront
        storefrontBannerPage.signOutFromStore();

        // Login to Admin panel, Search for Order number
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);

        // Get billing address from Admin panel & assert
        adminBillingAddress = orderInfoPage.getBillingAddress();
        assertTrue(adminBillingAddress.contains(Address.UniversalCity.cleansedAddressLine1));
        assertTrue(adminBillingAddress.contains(Address.UniversalCity.country.fullName));
        assertTrue(adminBillingAddress.contains(Address.UniversalCity.state.fullName));
        assertTrue(adminBillingAddress.contains(Address.UniversalCity.city));
        assertTrue(adminBillingAddress.contains(Address.UniversalCity.zip9));

        // Get billing address from Admin panel & assert
        adminShippingAddress = orderInfoPage.getBillingAddress();
        assertTrue(adminShippingAddress.contains(Address.UniversalCity.cleansedAddressLine1));
        assertTrue(adminShippingAddress.contains(Address.UniversalCity.country.fullName));
        assertTrue(adminShippingAddress.contains(Address.UniversalCity.state.fullName));
        assertTrue(adminShippingAddress.contains(Address.UniversalCity.city));
        assertTrue(adminShippingAddress.contains(Address.UniversalCity.zip9));

        assertEquals(adminShippingAddress, adminBillingAddress);
    }

    /**
     * CMAGM2-1235
     * CMAG - Test Case - Validate Sales Order with Address Cleansing invalid zip & unformatted street1 address
     */
    @Test(groups = {"magento_regression"})
    public void addressCleansingWithUnformattedStreetAndInvalidZipTest() {
        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        loginPage = homePage.clickSignInButton();
        loginPage.signInToMagentoTap244Store(MagentoData.MAGENTO_2_0_STORE_USER.data, MagentoData.MAGENTO_2_0_STORE_PASSWORD.data);
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(true, false, MagentoUSAddresses.Durham.addressLine1,
                MagentoUSAddresses.Durham.country.fullName, MagentoUSAddresses.Durham.state.fullName,
                MagentoUSAddresses.Durham.city, "00000");
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Read the cleansed address & assert for Address Cleansing
        cleansedAddress = shippingPage.getVertexCleansedAddress();
        assertTrue(cleansedAddress.contains(MagentoUSAddresses.Durham.cleansedAddressLine1));
        assertTrue(cleansedAddress.contains(MagentoUSAddresses.Durham.country.fullName));
        assertTrue(cleansedAddress.contains(MagentoUSAddresses.Durham.state.fullName));
        assertTrue(cleansedAddress.contains(MagentoUSAddresses.Durham.city));
        assertTrue(cleansedAddress.contains(MagentoUSAddresses.Durham.zip9));
        shippingPage.updateCleansedAddress();

        // Read the shipping address & assert for Address Cleansing
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.setSameBillingShippingAddress();
        storeShippingAddress = paymentPage.getShippingAddress();
        assertTrue(storeShippingAddress.contains(MagentoUSAddresses.Durham.cleansedAddressLine1));
        assertTrue(storeShippingAddress.contains(MagentoUSAddresses.Durham.country.fullName));
        assertTrue(storeShippingAddress.contains(MagentoUSAddresses.Durham.state.fullName));
        assertTrue(storeShippingAddress.contains(MagentoUSAddresses.Durham.city));
        assertTrue(storeShippingAddress.contains(MagentoUSAddresses.Durham.zip9));

        // Assert Cleansed address Vs Shipping Address
        assertTrue(storeShippingAddress.contains(cleansedAddress));

        // Validate the Tax & Place order & Save order number
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculateIndividualPercentBasedTax(true, true, 7.5));
        thankYouPage = paymentPage.clickPlaceOrderButton();
        orderNo = thankYouPage.getOrderNumber();
        assertFalse(orderNo.isEmpty());

        // Sign out of Storefront
        storefrontBannerPage.signOutFromStore();

        // Login to Admin panel, Search for Order number
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);

        // Get billing address from Admin panel & assert
        adminBillingAddress = orderInfoPage.getBillingAddress();
        assertTrue(adminBillingAddress.contains(MagentoUSAddresses.Durham.cleansedAddressLine1));
        assertTrue(adminBillingAddress.contains(MagentoUSAddresses.Durham.country.fullName));
        assertTrue(adminBillingAddress.contains(MagentoUSAddresses.Durham.state.fullName));
        assertTrue(adminBillingAddress.contains(MagentoUSAddresses.Durham.city));
        assertTrue(adminBillingAddress.contains(MagentoUSAddresses.Durham.zip9));

        // Get billing address from Admin panel & assert
        adminShippingAddress = orderInfoPage.getBillingAddress();
        assertTrue(adminShippingAddress.contains(MagentoUSAddresses.Durham.cleansedAddressLine1));
        assertTrue(adminShippingAddress.contains(MagentoUSAddresses.Durham.country.fullName));
        assertTrue(adminShippingAddress.contains(MagentoUSAddresses.Durham.state.fullName));
        assertTrue(adminShippingAddress.contains(MagentoUSAddresses.Durham.city));
        assertTrue(adminShippingAddress.contains(MagentoUSAddresses.Durham.zip9));

        assertEquals(adminShippingAddress, adminBillingAddress);
    }
}
