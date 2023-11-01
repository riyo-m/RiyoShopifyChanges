package com.vertex.quality.connectors.magento.storefront.tests.addressCleansing;

import com.vertex.quality.common.enums.Address;
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
public class MagentoAddressCleansingTests extends MagentoAdminBaseTest {

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
     * CMAGM2-1329 CMAG - Test Case - Validate addresses with address cleansing OFF - Shipping Address
     */
    @Test(groups = {"magento_regression", "magento_smoke"})
    public void physicalOriginWithCalcOffBlankZipTest() {
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
        shippingPage.enterShippingDetails(true, false, MagentoUSAddresses.Gettysburg.addressLine1,
                MagentoUSAddresses.Gettysburg.country.fullName, MagentoUSAddresses.Gettysburg.state.fullName,
                MagentoUSAddresses.Gettysburg.city, "blank");
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Read the cleansed address & assert for Address Cleansing
        cleansedAddress = shippingPage.getVertexCleansedAddress();
        assertTrue(cleansedAddress.contains(MagentoUSAddresses.Gettysburg.cleansedAddressLine1));
        assertTrue(cleansedAddress.contains(MagentoUSAddresses.Gettysburg.country.fullName));
        assertTrue(cleansedAddress.contains(MagentoUSAddresses.Gettysburg.state.fullName));
        assertTrue(cleansedAddress.contains(MagentoUSAddresses.Gettysburg.city));
        assertTrue(cleansedAddress.contains(MagentoUSAddresses.Gettysburg.zip9));
        shippingPage.updateCleansedAddress();

        // Read the shipping address & assert for Address Cleansing
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.setSameBillingShippingAddress();
        storeShippingAddress = paymentPage.getShippingAddress();
        assertTrue(storeShippingAddress.contains(MagentoUSAddresses.Gettysburg.cleansedAddressLine1));
        assertTrue(storeShippingAddress.contains(MagentoUSAddresses.Gettysburg.country.fullName));
        assertTrue(storeShippingAddress.contains(MagentoUSAddresses.Gettysburg.state.fullName));
        assertTrue(storeShippingAddress.contains(MagentoUSAddresses.Gettysburg.city));
        assertTrue(storeShippingAddress.contains(MagentoUSAddresses.Gettysburg.zip9));

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
        assertTrue(adminBillingAddress.contains(MagentoUSAddresses.Gettysburg.cleansedAddressLine1));
        assertTrue(adminBillingAddress.contains(MagentoUSAddresses.Gettysburg.country.fullName));
        assertTrue(adminBillingAddress.contains(MagentoUSAddresses.Gettysburg.state.fullName));
        assertTrue(adminBillingAddress.contains(MagentoUSAddresses.Gettysburg.city));
        assertTrue(adminBillingAddress.contains(MagentoUSAddresses.Gettysburg.zip9));

        // Get billing address from Admin panel & assert
        adminShippingAddress = orderInfoPage.getBillingAddress();
        assertTrue(adminShippingAddress.contains(MagentoUSAddresses.Gettysburg.cleansedAddressLine1));
        assertTrue(adminShippingAddress.contains(MagentoUSAddresses.Gettysburg.country.fullName));
        assertTrue(adminShippingAddress.contains(MagentoUSAddresses.Gettysburg.state.fullName));
        assertTrue(adminShippingAddress.contains(MagentoUSAddresses.Gettysburg.city));
        assertTrue(adminShippingAddress.contains(MagentoUSAddresses.Gettysburg.zip9));

        assertEquals(adminShippingAddress, adminBillingAddress);
    }

    /**
     * CMAGM2-1327 CMAG - Test Case - Address Cleansing OFF with Invalid State and Zip
     */
    @Test(groups = {"magento_regression"})
    public void physicalOriginWithCalcOffInvalidStateZipTest() {
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
                Address.Philadelphia.country.fullName, Address.ChesterInvalidAddress.state.fullName,
                Address.Philadelphia.city, Address.ChesterInvalidAddress.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Assertion for no area lookup
        assertEquals(MagentoData.ADDRESS_CORRECTION_MSG.data, shippingPage.getAddressSuggestionMessageFromUI());
    }

    /**
     * CMAGM2-1326 CMAG - Test Case - Address Cleansing OFF with No Zip
     */
    @Test(groups = {"magento_regression"})
    public void physicalOriginWithCalcOffEmptyZipTest() {
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
                Address.Philadelphia.city, "empty");
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
     * CMAGM2-1328 CMAG - Test Case - Validate Sales Order with Address Cleansing No Zip
     */
    @Test(groups = {"magento_regression"})
    public void physicalOriginWithCalcOffNullZipTest() {
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
        shippingPage.enterShippingDetails(true, false, MagentoUSAddresses.LosAngeles.addressLine1,
                MagentoUSAddresses.LosAngeles.country.fullName, MagentoUSAddresses.LosAngeles.state.fullName,
                MagentoUSAddresses.LosAngeles.city, "null");
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Read the cleansed address & assert for Address Cleansing
        cleansedAddress = shippingPage.getVertexCleansedAddress();
        assertTrue(cleansedAddress.contains(MagentoUSAddresses.LosAngeles.cleansedAddressLine1));
        assertTrue(cleansedAddress.contains(MagentoUSAddresses.LosAngeles.country.fullName));
        assertTrue(cleansedAddress.contains(MagentoUSAddresses.LosAngeles.state.fullName));
        assertTrue(cleansedAddress.contains(MagentoUSAddresses.LosAngeles.city));
        assertTrue(cleansedAddress.contains(MagentoUSAddresses.LosAngeles.zip9));
        shippingPage.updateCleansedAddress();

        // Read the shipping address & assert for Address Cleansing
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.setSameBillingShippingAddress();
        storeShippingAddress = paymentPage.getShippingAddress();

        assertTrue(storeShippingAddress.contains(MagentoUSAddresses.LosAngeles.cleansedAddressLine1));
        assertTrue(storeShippingAddress.contains(MagentoUSAddresses.LosAngeles.country.fullName));
        assertTrue(storeShippingAddress.contains(MagentoUSAddresses.LosAngeles.state.fullName));
        assertTrue(storeShippingAddress.contains(MagentoUSAddresses.LosAngeles.city));
        assertTrue(storeShippingAddress.contains(MagentoUSAddresses.LosAngeles.zip9));

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
        assertTrue(adminBillingAddress.contains(MagentoUSAddresses.LosAngeles.cleansedAddressLine1));
        assertTrue(adminBillingAddress.contains(MagentoUSAddresses.LosAngeles.country.fullName));
        assertTrue(adminBillingAddress.contains(MagentoUSAddresses.LosAngeles.state.fullName));
        assertTrue(adminBillingAddress.contains(MagentoUSAddresses.LosAngeles.city));
        assertTrue(adminBillingAddress.contains(MagentoUSAddresses.LosAngeles.zip9));

        // Get billing address from Admin panel & assert
        adminShippingAddress = orderInfoPage.getBillingAddress();
        assertTrue(adminShippingAddress.contains(MagentoUSAddresses.LosAngeles.cleansedAddressLine1));
        assertTrue(adminShippingAddress.contains(MagentoUSAddresses.LosAngeles.country.fullName));
        assertTrue(adminShippingAddress.contains(MagentoUSAddresses.LosAngeles.state.fullName));
        assertTrue(adminShippingAddress.contains(MagentoUSAddresses.LosAngeles.city));
        assertTrue(adminShippingAddress.contains(MagentoUSAddresses.LosAngeles.zip9));

        assertEquals(adminShippingAddress, adminBillingAddress);
    }

    /**
     * CMAGM2-1324 CMAG - Test Case - Address Cleansing OFF with Invalid City and Zip
     */
    @Test(groups = {"magento_regression"})
    public void physicalOriginInvalidCityAndZipTest() {
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
                Address.ChesterInvalidAddress.city, Address.ChesterInvalidAddress.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Assertion for no area lookup
        assertEquals(MagentoData.ADDRESS_CORRECTION_MSG.data, shippingPage.getAddressSuggestionMessageFromUI());
    }

    /**
     * CMAGM2-1325 CMAG - Test Case - Address Cleansing OFF with Invalid Zip
     */
    @Test(groups = {"magento_regression"})
    public void shippingAddressCleanseWithInvalidZipTest() {
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
                Address.Philadelphia.city, Address.ChesterInvalidAddress.zip5);
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
}
