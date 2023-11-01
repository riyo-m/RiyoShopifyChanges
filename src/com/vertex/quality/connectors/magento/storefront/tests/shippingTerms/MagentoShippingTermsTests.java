package com.vertex.quality.connectors.magento.storefront.tests.shippingTerms;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.magento.admin.pages.*;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import com.vertex.quality.connectors.magento.storefront.pages.*;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * CCMMER2-442 Magento O-Series - Test Automation - Storefront - Part II
 * Magento M2 Storefront - Shipping Terms & Incoterms tests
 *
 * @author Shivam.Soni
 */
public class MagentoShippingTermsTests extends MagentoAdminBaseTest {

    M2AdminConfigPage configPage;
    M2AdminSalesTaxConfigPage newTaxConfigPage;
    M2StorefrontLoginPage loginPage;
    M2StorefrontHomePage homePage;
    M2StorefrontBannerPage storefrontBannerPage;
    M2StorefrontSearchResultPage searchResultPage;
    M2StorefrontShippingInfoPage shippingPage;
    M2StorefrontPaymentMethodPage paymentPage;
    M2StorefrontThankYouPage thankYouPage;
    M2AdminOrdersPage ordersPage;
    M2AdminOrderViewInfoPage orderInfoPage;
    M2AdminNewInvoicePage invoicePage;
    String orderNo;

    /**
     * CMAGM2-1244 CMAG - Test Case - Create Sales Invoice with Shipping - change shipping address
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInChangeShippingAddressTest() {
        configPage = setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);
        newTaxConfigPage = configPage.clickTaxTab();
        newTaxConfigPage.changeGlobalDeliveryTermField(MagentoData.SHIP_TERM_CUS.data);
        newTaxConfigPage.saveConfig();
        newTaxConfigPage.signOutFromAdminPanel();

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
        shippingPage.enterShippingDetails(true, false, Address.Berwyn.addressLine1,
                Address.Berwyn.country.fullName, Address.Berwyn.state.fullName,
                Address.Berwyn.city, Address.Berwyn.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();
        shippingPage.ignoreCleansedAddress();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.setSameBillingShippingAddress();
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
     * CMAGM2-1245 CMAG - Test Case - Create Sales Order with shipping
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInChangeShippingTermTest() {
        try {
            configPage = setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);
            newTaxConfigPage = configPage.clickTaxTab();
            newTaxConfigPage.changeGlobalDeliveryTermField(MagentoData.SHIP_TERM_CUS.data);
            newTaxConfigPage.saveConfig();
            newTaxConfigPage.signOutFromAdminPanel();

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
            shippingPage.enterShippingDetails(true, false, Address.Berwyn.addressLine1,
                    Address.Berwyn.country.fullName, Address.Berwyn.state.fullName,
                    Address.Berwyn.city, Address.Berwyn.zip5);
            shippingPage.selectFlatRateForShippingMethod();
            shippingPage.clickNextButton();
            shippingPage.ignoreCleansedAddress();

            // Pay for the order, Validate the Tax & Place order
            paymentPage = new M2StorefrontPaymentMethodPage(driver);
            paymentPage.setSameBillingShippingAddress();
            assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculateIndividualPercentBasedTax(true, true, 6));

            // Save order number
            thankYouPage = paymentPage.clickPlaceOrderButton();
            orderNo = thankYouPage.getOrderNumber();
            assertFalse(orderNo.isEmpty());

            // Sign out of Storefront
            storefrontBannerPage.signOutFromStore();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed due to error/ exception, Kindly check the logs");
        } finally {
            // Restoring default setting for Shipping terms
            configPage = navigateToConfig();
            configPage.clickSalesTab();
            newTaxConfigPage = configPage.clickTaxTab();
            newTaxConfigPage.changeGlobalDeliveryTermField(MagentoData.SHIP_TERM_SUP.data);
            newTaxConfigPage.saveConfig();
        }
    }

    /**
     * CMAGM2-1246 CMAG - Test Case - Create Sale Order for VAT with Shipping Terms CUS and Invoice
     */
    @Test(groups = {"magento_regression", "magento_smoke"})
    public void checkTaxAmountInCUSShippingTermTest() {
        try {
            configPage = setShippingOrigin(Address.Paris.country.fullName, Address.Paris.city, Address.Paris.zip5, Address.Paris.city, Address.Paris.addressLine1);
            newTaxConfigPage = configPage.clickTaxTab();
            newTaxConfigPage.changeGlobalDeliveryTermField(MagentoData.SHIP_TERM_CUS.data);
            newTaxConfigPage.saveConfig();
            newTaxConfigPage.signOutFromAdminPanel();

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
            shippingPage.enterShippingDetails(true, false, Address.Berlin.addressLine1,
                    Address.Berlin.country.fullName, Address.Berlin.city,
                    Address.Berlin.city, Address.Berlin.zip5);
            shippingPage.selectFlatRateForShippingMethod();
            shippingPage.clickNextButton();
            shippingPage.ignoreCleansedAddress();

            // Pay for the order, Validate the Tax & Place order
            paymentPage = new M2StorefrontPaymentMethodPage(driver);
            paymentPage.setSameBillingShippingAddress();
            assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculateIndividualPercentBasedTax(true, true, 20));

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
            invoicePage.signOutFromAdminPanel();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed due to error/ exception, Kindly check the logs");
        } finally {
            // Restoring default setting for Shipping terms
            configPage = navigateToConfig();
            configPage.clickSalesTab();
            newTaxConfigPage = configPage.clickTaxTab();
            newTaxConfigPage.changeGlobalDeliveryTermField(MagentoData.SHIP_TERM_SUP.data);
            newTaxConfigPage.saveConfig();
        }
    }

    /**
     * CMAGM2-1247 CMAG - Test Case - Create Sale Order for VAT with Shipping Terms DDP and Invoice
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInDDPShippingTermTest() {
        try {
            configPage = setShippingOrigin(Address.Paris.country.fullName, Address.Paris.city, Address.Paris.zip5, Address.Paris.city, Address.Paris.addressLine1);
            newTaxConfigPage = configPage.clickTaxTab();
            newTaxConfigPage.changeGlobalDeliveryTermField(MagentoData.SHIP_TERM_DDP.data);
            newTaxConfigPage.saveConfig();
            newTaxConfigPage.signOutFromAdminPanel();

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
            shippingPage.enterShippingDetails(true, false, Address.Berlin.addressLine1,
                    Address.Berlin.country.fullName, Address.Berlin.city,
                    Address.Berlin.city, Address.Berlin.zip5);
            shippingPage.selectFlatRateForShippingMethod();
            shippingPage.clickNextButton();
            shippingPage.ignoreCleansedAddress();

            // Pay for the order, Validate the Tax & Place order
            paymentPage = new M2StorefrontPaymentMethodPage(driver);
            paymentPage.setSameBillingShippingAddress();
            assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculateIndividualPercentBasedTax(true, true, 19));

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
            invoicePage.signOutFromAdminPanel();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed due to error/ exception, Kindly check the logs");
        } finally {
            // Restoring default setting for Shipping terms
            configPage = navigateToConfig();
            configPage.clickSalesTab();
            newTaxConfigPage = configPage.clickTaxTab();
            newTaxConfigPage.changeGlobalDeliveryTermField(MagentoData.SHIP_TERM_SUP.data);
            newTaxConfigPage.saveConfig();
        }
    }

    /**
     * CMAGM2-1248 CMAG - Test Case - Create Sale Order with Shipping DDP for VAT (Intra EU DE-DE) and Invoice
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInDDPVATTest() {
        try {
            configPage = setShippingOrigin(Address.Berlin.country.fullName, Address.Berlin.city, Address.Berlin.zip5, Address.Berlin.city, Address.Berlin.addressLine1);
            newTaxConfigPage = configPage.clickTaxTab();
            newTaxConfigPage.changeGlobalDeliveryTermField(MagentoData.SHIP_TERM_DDP.data);
            newTaxConfigPage.saveConfig();
            newTaxConfigPage.signOutFromAdminPanel();

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
            shippingPage.enterShippingDetails(true, false, Address.BerlinAlternate.addressLine1,
                    Address.BerlinAlternate.country.fullName, Address.BerlinAlternate.city,
                    Address.BerlinAlternate.city, Address.BerlinAlternate.zip5);
            shippingPage.selectFlatRateForShippingMethod();
            shippingPage.clickNextButton();
            shippingPage.ignoreCleansedAddress();

            // Pay for the order, Validate the Tax & Place order
            paymentPage = new M2StorefrontPaymentMethodPage(driver);
            paymentPage.setSameBillingShippingAddress();
            assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculateIndividualPercentBasedTax(true, true, 19));

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
            invoicePage.signOutFromAdminPanel();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed due to error/ exception, Kindly check the logs");
        } finally {
            // Restoring default setting for Shipping terms
            configPage = navigateToConfig();
            configPage.clickSalesTab();
            newTaxConfigPage = configPage.clickTaxTab();
            newTaxConfigPage.changeGlobalDeliveryTermField(MagentoData.SHIP_TERM_SUP.data);
            newTaxConfigPage.saveConfig();
        }
    }

    /**
     * CMAGM2-1249 CMAG - Test Case - Create Sale Order for VAT with Shipping Terms EXW and Invoice
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInEXWShippingTermTest() {
        try {
            configPage = setShippingOrigin(Address.Paris.country.fullName, Address.Paris.city, Address.Paris.zip5, Address.Paris.city, Address.Paris.addressLine1);
            newTaxConfigPage = configPage.clickTaxTab();
            newTaxConfigPage.changeGlobalDeliveryTermField(MagentoData.SHIP_TERM_EXW.data);
            newTaxConfigPage.saveConfig();
            newTaxConfigPage.signOutFromAdminPanel();

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
            shippingPage.enterShippingDetails(true, false, Address.BerlinAlternate.addressLine1,
                    Address.BerlinAlternate.country.fullName, Address.BerlinAlternate.city,
                    Address.BerlinAlternate.city, Address.BerlinAlternate.zip5);
            shippingPage.selectFlatRateForShippingMethod();
            shippingPage.clickNextButton();
            shippingPage.ignoreCleansedAddress();

            // Pay for the order, Validate the Tax & Place order
            paymentPage = new M2StorefrontPaymentMethodPage(driver);
            paymentPage.setSameBillingShippingAddress();
            assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculateIndividualPercentBasedTax(true, true, 20));

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
            invoicePage.signOutFromAdminPanel();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed due to error/ exception, Kindly check the logs");
        } finally {
            // Restoring default setting for Shipping terms
            configPage = navigateToConfig();
            configPage.clickSalesTab();
            newTaxConfigPage = configPage.clickTaxTab();
            newTaxConfigPage.changeGlobalDeliveryTermField(MagentoData.SHIP_TERM_SUP.data);
            newTaxConfigPage.saveConfig();
        }
    }

    /**
     * CMAGM2-1250 CMAG - Test Case - Create Sale Order with shipping terms SUP for APAC (SG-JP) and Invoice
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInSUPShippingTermAPACTest() {
        configPage = setShippingOrigin(Address.Singapore.country.fullName, Address.Singapore.city, Address.Singapore.zip5, Address.Singapore.city, Address.Singapore.addressLine1);
        newTaxConfigPage = configPage.clickTaxTab();
        newTaxConfigPage.changeGlobalDeliveryTermField(MagentoData.SHIP_TERM_SUP.data);
        newTaxConfigPage.saveConfig();
        newTaxConfigPage.signOutFromAdminPanel();

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
        shippingPage.enterShippingDetails(true, false, Address.Japan.addressLine1,
                Address.Japan.country.fullName, Address.Japan.city,
                Address.Japan.city, Address.Japan.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();
        shippingPage.ignoreCleansedAddress();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.setSameBillingShippingAddress();
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculateIndividualPercentBasedTax(true, true, 10));

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
     * CMAGM2-1251 CMAG - Test Case - Create Sale Order for VAT with Shipping Terms SUP and Invoice
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInSUPShippingTest() {
        configPage = setShippingOrigin(Address.Paris.country.fullName, Address.Paris.city, Address.Paris.zip5, Address.Paris.city, Address.Paris.addressLine1);
        newTaxConfigPage = configPage.clickTaxTab();
        newTaxConfigPage.changeGlobalDeliveryTermField(MagentoData.SHIP_TERM_SUP.data);
        newTaxConfigPage.saveConfig();

        newTaxConfigPage.signOutFromAdminPanel();

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
        shippingPage.enterShippingDetails(true, false, Address.Berlin.addressLine1,
                Address.Berlin.country.fullName, Address.Berlin.city,
                Address.Berlin.city, Address.Berlin.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();
        shippingPage.ignoreCleansedAddress();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.setSameBillingShippingAddress();
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculateIndividualPercentBasedTax(true, true, 19));

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
     * CMAGM2-1252 Test Case - Create Sale Order for VAT (Intra EU DE-DE) with product code exemption, virtual product and Shipping
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxAmountInVirtualProductTest() {
        try {
            configPage = setShippingOrigin(Address.BerlinAlternate.country.fullName, Address.BerlinAlternate.city, Address.BerlinAlternate.zip5, Address.BerlinAlternate.city, Address.BerlinAlternate.addressLine1);
            newTaxConfigPage = configPage.clickTaxTab();
            newTaxConfigPage.changeGlobalDeliveryTermField(MagentoData.SHIP_TERM_CUS.data);
            newTaxConfigPage.saveConfig();
            newTaxConfigPage.signOutFromAdminPanel();

            // Open the store, clear the cart, search product & add product(s) to the cart.
            homePage = openStorefrontHomepage();
            loginPage = homePage.clickSignInButton();
            loginPage.signInToMagentoTap244Store(MagentoData.MAGENTO_2_0_STORE_USER.data, MagentoData.MAGENTO_2_0_STORE_PASSWORD.data);
            storefrontBannerPage = new M2StorefrontBannerPage(driver);
            storefrontBannerPage.clearTheCart();
            storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.QA_EXEMPTED_VIRTUAL_PRODUCT.data);
            searchResultPage = new M2StorefrontSearchResultPage(driver);
            searchResultPage.loadMaximumRecords();
            searchResultPage.addProductToTheCart(MagentoData.QA_EXEMPTED_VIRTUAL_PRODUCT.data);
            storefrontBannerPage.proceedToCheckout();

            // Set ship to address
            shippingPage = new M2StorefrontShippingInfoPage(driver);
            shippingPage.enterShippingDetails(true, false, Address.Berlin.addressLine1,
                    Address.Berlin.country.fullName, Address.Berlin.city,
                    Address.Berlin.city, Address.Berlin.zip5);
            shippingPage.selectFlatRateForShippingMethod();
            shippingPage.clickNextButton();
            shippingPage.ignoreCleansedAddress();

            // Pay for the order, Validate the Tax & Place order
            paymentPage = new M2StorefrontPaymentMethodPage(driver);
            paymentPage.setSameBillingShippingAddress();
            assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculateIndividualPercentBasedTax(true, true, 19));

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
            invoicePage.signOutFromAdminPanel();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed due to error/ exception, Kindly check the logs");
        } finally {
            // Restoring default setting for Shipping terms
            configPage = navigateToConfig();
            configPage.clickSalesTab();
            newTaxConfigPage = configPage.clickTaxTab();
            newTaxConfigPage.changeGlobalDeliveryTermField(MagentoData.SHIP_TERM_SUP.data);
            newTaxConfigPage.saveConfig();
        }
    }
}
