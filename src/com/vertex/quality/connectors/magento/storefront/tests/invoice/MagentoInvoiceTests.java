package com.vertex.quality.connectors.magento.storefront.tests.invoice;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.enums.TaxRate;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminHomepage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminNewInvoicePage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminOrderViewInfoPage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminOrdersPage;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import com.vertex.quality.connectors.magento.storefront.pages.*;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * CCMMER2-441 Magento O-Series - Test Automation - Storefront - Part I
 *
 * @author Shivam.Soni
 */
public class MagentoInvoiceTests extends MagentoAdminBaseTest {

    M2AdminHomepage adminHomepage;
    M2StorefrontLoginPage loginPage;
    M2StorefrontHomePage homePage;
    M2StorefrontBannerPage storefrontBannerPage;
    M2StorefrontSearchResultPage searchResultPage;
    M2StorefrontShoppingCartPage cartPage;
    M2StorefrontShippingInfoPage shippingPage;
    M2StorefrontPaymentMethodPage paymentPage;
    M2StorefrontThankYouPage thankYouPage;
    M2AdminOrdersPage ordersPage;
    M2AdminOrderViewInfoPage orderInfoPage;
    M2AdminNewInvoicePage invoicePage;

    String orderNo;

    /**
     * CMAGM2-1226 Test Case - Create Sales Invoice change location
     */
    @Test(groups = {"magento_regression", "magento_smoke"})
    public void checkTaxRateCreateNewOrderChangeLocTest() {
        // Set ship from address
        setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);
        adminHomepage = new M2AdminHomepage(driver);
        adminHomepage.signOutFromAdminPanel();

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
        shippingPage.enterShippingDetails(true, false, Address.LosAngeles.addressLine1,
                Address.LosAngeles.country.fullName, Address.LosAngeles.state.fullName,
                Address.LosAngeles.city, Address.LosAngeles.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();
        shippingPage.ignoreCleansedAddress();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.setSameBillingShippingAddress();
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculateIndividualPercentBasedTax(true, true, TaxRate.LosAngeles.tax));
        paymentPage.clickPlaceOrderButton();

        // Change address & validate the tax
        storefrontBannerPage.clickOnStoreLogo();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();
        shippingPage.enterShippingDetails(true, false, Address.Louisiana.addressLine1,
                Address.Louisiana.country.fullName, Address.Louisiana.state.fullName,
                Address.Louisiana.city, Address.Louisiana.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();
        shippingPage.ignoreCleansedAddress();
        paymentPage.setSameBillingShippingAddress();
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculateIndividualPercentBasedTax(true, true, 9.45));
        paymentPage.clickPlaceOrderButton();

        // Again change address & validate the tax
        storefrontBannerPage.clickOnStoreLogo();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();
        shippingPage.enterShippingDetails(true, false, Address.Colorado.addressLine1,
                Address.Colorado.country.fullName, Address.Colorado.state.fullName,
                Address.Colorado.city, Address.Colorado.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();
        shippingPage.ignoreCleansedAddress();
        paymentPage.setSameBillingShippingAddress();
        assertTrue(paymentPage.verifyRetailDeliveryFeeForColorado());
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculatePercentBasedTaxForColorado(5, 0));

        // Save order number
        thankYouPage = paymentPage.clickPlaceOrderButton();
        orderNo = thankYouPage.getOrderNumber();
        assertFalse(orderNo.isEmpty());

        // Sign out of Storefront
        storefrontBannerPage.signOutFromStore();

        // Login to Admin panel, Search for Order number & Submit Invoice
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMAGM2-1225 CMAG - Test Case - End to End Test Create Sales Order and add/delete lines and change quantity and location
     */
    @Test(groups = {"magento_regression"})
    public void changeLocationUpdateQtyEndToEndTest() {
        setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);
        adminHomepage = new M2AdminHomepage(driver);
        adminHomepage.signOutFromAdminPanel();

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
        shippingPage.enterShippingDetails(true, false, Address.LosAngeles.addressLine1,
                Address.LosAngeles.country.fullName, Address.LosAngeles.state.fullName,
                Address.LosAngeles.city, Address.LosAngeles.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();
        shippingPage.ignoreCleansedAddress();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.setSameBillingShippingAddress();
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculateIndividualPercentBasedTax(true, true, TaxRate.LosAngeles.tax));
        paymentPage.clickPlaceOrderButton();

        // Change address & validate the tax
        storefrontBannerPage.clickOnStoreLogo();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();
        shippingPage.enterShippingDetails(true, false, Address.Louisiana.addressLine1,
                Address.Louisiana.country.fullName, Address.Louisiana.state.fullName,
                Address.Louisiana.city, Address.Louisiana.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();
        shippingPage.ignoreCleansedAddress();
        paymentPage.setSameBillingShippingAddress();
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculateIndividualPercentBasedTax(true, true, 9.45));
        paymentPage.clickPlaceOrderButton();

        // Again change address & validate the tax
        storefrontBannerPage.clickOnStoreLogo();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();
        shippingPage.enterShippingDetails(true, false, Address.Colorado.addressLine1,
                Address.Colorado.country.fullName, Address.Colorado.state.fullName,
                Address.Colorado.city, Address.Colorado.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();
        shippingPage.ignoreCleansedAddress();
        paymentPage.setSameBillingShippingAddress();
        assertTrue(paymentPage.verifyRetailDeliveryFeeForColorado());
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculatePercentBasedTaxForColorado(5, 0));

        // Save order number
        thankYouPage = paymentPage.clickPlaceOrderButton();
        orderNo = thankYouPage.getOrderNumber();
        assertFalse(orderNo.isEmpty());

        // Sign out of Storefront
        storefrontBannerPage.signOutFromStore();

        // Login to Admin panel, Search for Order number & Submit Invoice
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMAGM2-1224 CMAGM2 - Test Case -Create Sales Order with Invoice
     */
    @Test(groups = {"magento_regression"})
    public void salesOrderWithInvoiceTest() {
        setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);
        adminHomepage = new M2AdminHomepage(driver);
        adminHomepage.signOutFromAdminPanel();

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
        shippingPage.enterShippingDetails(true, false, Address.LosAngeles.addressLine1,
                Address.LosAngeles.country.fullName, Address.LosAngeles.state.fullName,
                Address.LosAngeles.city, Address.LosAngeles.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();
        shippingPage.ignoreCleansedAddress();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.setSameBillingShippingAddress();
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculateIndividualPercentBasedTax(true, true, TaxRate.LosAngeles.tax));
        // Save order number
        thankYouPage = paymentPage.clickPlaceOrderButton();
        orderNo = thankYouPage.getOrderNumber();
        assertFalse(orderNo.isEmpty());

        // Sign out of Storefront
        storefrontBannerPage.signOutFromStore();

        // Login to Admin panel, Search for Order number & Submit Invoice
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMAGM2-1223 CMAG - Test Case -Create Sales Order different ship and bill
     */
    @Test(groups = {"magento_regression"})
    public void salesOrderWithDifferentBillShipTest() {
        setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);
        adminHomepage = new M2AdminHomepage(driver);
        adminHomepage.signOutFromAdminPanel();

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
        shippingPage.enterShippingDetails(true, false, Address.Delaware.addressLine1,
                Address.Delaware.country.fullName, Address.Delaware.state.fullName,
                Address.Delaware.city, Address.Delaware.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();
        shippingPage.ignoreCleansedAddress();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.setSameBillingShippingAddress(false);
        assertFalse(paymentPage.isTaxLabelPresent());
        // Save order number
        thankYouPage = paymentPage.clickPlaceOrderButton();
        orderNo = thankYouPage.getOrderNumber();
        assertFalse(orderNo.isEmpty());

        // Sign out of Storefront
        storefrontBannerPage.signOutFromStore();

        // Login to Admin panel, Search for Order number & Submit Invoice
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMAGM2-1222 CMAG - Test Case -Create Sales Order with Invoice US to CAN
     */
    @Test(groups = {"magento_regression", "magento_smoke"})
    public void salesOrderUSToCANTest() {
        setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);
        adminHomepage = new M2AdminHomepage(driver);
        adminHomepage.signOutFromAdminPanel();

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
        shippingPage.enterShippingDetails(true, false, Address.Victoria.addressLine1,
                Address.Victoria.country.fullName, Address.Victoria.province.fullName,
                Address.Victoria.city, Address.Victoria.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();
        shippingPage.ignoreCleansedAddress();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.setSameBillingShippingAddress(true);
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculateIndividualPercentBasedTax(true, true, 12));
        // Save order number
        thankYouPage = paymentPage.clickPlaceOrderButton();
        orderNo = thankYouPage.getOrderNumber();
        assertFalse(orderNo.isEmpty());

        // Sign out of Storefront
        storefrontBannerPage.signOutFromStore();

        // Login to Admin panel, Search for Order number & Submit Invoice
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMAGM2-1221 CMAG - Test Case -Create Sales Order with Invoice CAN to US
     */
    @Test(groups = {"magento_regression", "magento_smoke"})
    public void salesOrderCANToUSTest() {
        setShippingOrigin(Address.QuebecCity.country.fullName, Address.QuebecCity.province.fullName, Address.QuebecCity.zip5, Address.QuebecCity.city, Address.QuebecCity.addressLine1);
        adminHomepage = new M2AdminHomepage(driver);
        adminHomepage.signOutFromAdminPanel();

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
        shippingPage.enterShippingDetails(true, false, Address.Gettysburg.addressLine1,
                Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName,
                Address.Gettysburg.city, Address.Gettysburg.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();
        shippingPage.ignoreCleansedAddress();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.setSameBillingShippingAddress(true);
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculateIndividualPercentBasedTax(true, true, 6));
        // Save order number
        thankYouPage = paymentPage.clickPlaceOrderButton();
        orderNo = thankYouPage.getOrderNumber();
        assertFalse(orderNo.isEmpty());

        // Sign out of Storefront
        storefrontBannerPage.signOutFromStore();

        // Login to Admin panel, Search for Order number & Submit Invoice
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMAGM2-1220 Test Case -Create Sales Order with Invoice CANNB to CANNB same Province (HST)
     */
    @Test(groups = {"magento_regression", "magento_smoke"})
    public void salesOrderCANNBToCANNBTest() {
        setShippingOrigin(Address.GrandManan.country.fullName, Address.GrandManan.province.fullName, Address.GrandManan.zip5, Address.GrandManan.city, Address.GrandManan.addressLine1);
        adminHomepage = new M2AdminHomepage(driver);
        adminHomepage.signOutFromAdminPanel();

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
        shippingPage.enterShippingDetails(true, false, Address.Quispamsis.addressLine1,
                Address.Quispamsis.country.fullName, Address.Quispamsis.province.fullName,
                Address.Quispamsis.city, Address.Quispamsis.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();
        shippingPage.ignoreCleansedAddress();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.setSameBillingShippingAddress(true);
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculateIndividualPercentBasedTax(true, true, 15));
        // Save order number
        thankYouPage = paymentPage.clickPlaceOrderButton();
        orderNo = thankYouPage.getOrderNumber();
        assertFalse(orderNo.isEmpty());

        // Sign out of Storefront
        storefrontBannerPage.signOutFromStore();

        // Login to Admin panel, Search for Order number & Submit Invoice
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMAGM2-1219 CMAG - Test Case -Create Sales Order with Invoice CANQC to CANBC different Province (GST/PST)
     */
    @Test(groups = {"magento_regression"})
    public void salesOrderCANQCToCANBCTest() {
        setShippingOrigin(Address.QuebecCity.country.fullName, Address.QuebecCity.province.fullName, Address.QuebecCity.zip5, Address.QuebecCity.city, Address.QuebecCity.addressLine1);
        adminHomepage = new M2AdminHomepage(driver);
        adminHomepage.signOutFromAdminPanel();

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
        shippingPage.enterShippingDetails(true, false, Address.Victoria.addressLine1,
                Address.Victoria.country.fullName, Address.Victoria.province.fullName,
                Address.Victoria.city, Address.Victoria.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();
        shippingPage.ignoreCleansedAddress();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.setSameBillingShippingAddress(true);
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculateIndividualPercentBasedTax(true, true, 12));
        // Save order number
        thankYouPage = paymentPage.clickPlaceOrderButton();
        orderNo = thankYouPage.getOrderNumber();
        assertFalse(orderNo.isEmpty());

        // Sign out of Storefront
        storefrontBannerPage.signOutFromStore();

        // Login to Admin panel, Search for Order number & Submit Invoice
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMAGM2-1218 CMAG - Test Case -Create Sales Order with Invoice CANBC to CANON different Province (GST/HST)
     */
    @Test(groups = {"magento_regression"})
    public void salesOrderCANBCToCANONTest() {
        setShippingOrigin(Address.Victoria.country.fullName, Address.Victoria.province.fullName, Address.Victoria.zip5, Address.Victoria.city, Address.Victoria.addressLine1);
        adminHomepage = new M2AdminHomepage(driver);
        adminHomepage.signOutFromAdminPanel();

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
        shippingPage.enterShippingDetails(true, false, Address.Ottawa.addressLine1,
                Address.Ottawa.country.fullName, Address.Ottawa.province.fullName,
                Address.Ottawa.city, Address.Ottawa.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();
        shippingPage.ignoreCleansedAddress();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.setSameBillingShippingAddress(true);
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculateIndividualPercentBasedTax(true, true, 13));
        // Save order number
        thankYouPage = paymentPage.clickPlaceOrderButton();
        orderNo = thankYouPage.getOrderNumber();
        assertFalse(orderNo.isEmpty());

        // Sign out of Storefront
        storefrontBannerPage.signOutFromStore();

        // Login to Admin panel, Search for Order number & Submit Invoice
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMAGM2-1217 CMAG - Test Case -Create Sales Order with Invoice CANBC to CANQC different Province (GST/QST)
     */
    @Test(groups = {"magento_regression"})
    public void salesOrderCANBCToCANQCTest() {
        setShippingOrigin(Address.Victoria.country.fullName, Address.Victoria.province.fullName, Address.Victoria.zip5, Address.Victoria.city, Address.Victoria.addressLine1);
        adminHomepage = new M2AdminHomepage(driver);
        adminHomepage.signOutFromAdminPanel();

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
        shippingPage.enterShippingDetails(true, false, Address.QuebecCity.addressLine1,
                Address.QuebecCity.country.fullName, Address.QuebecCity.province.fullName,
                Address.QuebecCity.city, Address.QuebecCity.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();
        shippingPage.ignoreCleansedAddress();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.setSameBillingShippingAddress(true);
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculateIndividualPercentBasedTax(true, true, 14.975));
        // Save order number
        thankYouPage = paymentPage.clickPlaceOrderButton();
        orderNo = thankYouPage.getOrderNumber();
        assertFalse(orderNo.isEmpty());

        // Sign out of Storefront
        storefrontBannerPage.signOutFromStore();

        // Login to Admin panel, Search for Order number & Submit Invoice
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMAGM2-1212 CMAG - Test Case - Create Invoice with quantity 10
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxRateCreateNewOrderQyt10Test() {
        setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);
        adminHomepage = new M2AdminHomepage(driver);
        adminHomepage.signOutFromAdminPanel();

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
        storefrontBannerPage.clickViewAndEditCart();

        // Go to cart & update quantity & proceed to checkout
        cartPage = new M2StorefrontShoppingCartPage(driver);
        cartPage.updateItemQty(MagentoData.FUSION_BACKPACK.data, MagentoData.quantity_ten.data);
        cartPage.clickProceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(true, false, Address.Washington.addressLine1,
                Address.Washington.country.fullName, Address.Washington.state.fullName,
                Address.Washington.city, Address.Washington.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();
        shippingPage.ignoreCleansedAddress();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.setSameBillingShippingAddress(true);
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculateIndividualPercentBasedTax(true, true, 10.1));
        // Save order number
        thankYouPage = paymentPage.clickPlaceOrderButton();
        orderNo = thankYouPage.getOrderNumber();
        assertFalse(orderNo.isEmpty());

        // Sign out of Storefront
        storefrontBannerPage.signOutFromStore();

        // Login to Admin panel, Search for Order number & Submit Invoice
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMAGM2-1211 CMAG - Test Case -Create Sales Order with NJ no tax on clothing, no shipping tax
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxRateSalesOrderNTaxpayerTest() {
        setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);
        adminHomepage = new M2AdminHomepage(driver);
        adminHomepage.signOutFromAdminPanel();

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
        shippingPage.enterShippingDetails(true, false, Address.Edison.addressLine1,
                Address.Edison.country.fullName, Address.Edison.state.fullName,
                Address.Edison.city, Address.Edison.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();
        shippingPage.ignoreCleansedAddress();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.setSameBillingShippingAddress(true);
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculateIndividualPercentBasedTax(true, true, 6.625));
        // Save order number
        thankYouPage = paymentPage.clickPlaceOrderButton();
        orderNo = thankYouPage.getOrderNumber();
        assertFalse(orderNo.isEmpty());

        // Sign out of Storefront
        storefrontBannerPage.signOutFromStore();

        // Login to Admin panel, Search for Order number & Submit Invoice
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }
}
