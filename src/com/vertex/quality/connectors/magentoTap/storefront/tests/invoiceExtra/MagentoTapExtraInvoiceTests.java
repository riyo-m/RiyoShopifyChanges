package com.vertex.quality.connectors.magentoTap.storefront.tests.invoiceExtra;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.magentoTap.admin.pages.*;
import com.vertex.quality.connectors.magentoTap.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magentoTap.common.enums.MagentoData;
import com.vertex.quality.connectors.magentoTap.common.enums.MagentoTapEUAddresses;
import com.vertex.quality.connectors.magentoTap.storefront.pages.*;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * CCMMER2-69
 * MagentoTap invoice tests to some extra EU addresses
 *
 * @author Shivam.Soni
 */
public class MagentoTapExtraInvoiceTests extends MagentoAdminBaseTest {

    M2AdminConfigPage configPage;
    M2AdminShippingSettingsPage shippingSettings;
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
     * CMTAP-46 CMTAP - Test Case - Create Sales Order for VAT for Belgium (US-EU) and Invoice
     */
    @Test(groups = {"magentoTap_regression"})
    public void invoiceUSToBelgiumTest() {
        // Go to Admin console & Set Ship From address
        configPage = navigateToMagento244Config();
        configPage.clickSalesTab();
        shippingSettings = configPage.goToShippingSettingsTab();
        shippingSettings.setShippingOrigin(Address.UniversalCity.country.fullName, Address.UniversalCity.state.fullName, Address.UniversalCity.zip5, Address.UniversalCity.city, Address.UniversalCity.addressLine1);
        configPage.saveConfigurations();

        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        loginPage = homePage.clickSignInButton();
        loginPage.signInToMagentoTap244Store(MagentoData.MAGENTO_244_STORE_AUTOMATION_USER.data, MagentoData.MAGENTO_244_STORE_AUTOMATION_PASSWORD.data);
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(true, false, MagentoTapEUAddresses.BELGIUM_ADDRESS.addressLine1,
                MagentoTapEUAddresses.BELGIUM_ADDRESS.country, MagentoTapEUAddresses.BELGIUM_ADDRESS.region,
                MagentoTapEUAddresses.BELGIUM_ADDRESS.city, MagentoTapEUAddresses.BELGIUM_ADDRESS.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        assertEquals(paymentPage.calculatePercentBasedTax(21), paymentPage.getTaxFromUI());
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();

        // Login to Admin panel, Search for Order number & Submit Invoice
        quitDriver();
        createChromeDriver();
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMTAP-71 CMTAP - Test Case - Create Sales Order for VAT for Germany to Belgium (EU-EU) and Invoice
     */
    @Test(groups = {"magentoTap_regression"})
    public void invoiceGermanyToBelgiumTest() {
        // Go to Admin console & Set Ship From address
        configPage = navigateToMagento244Config();
        configPage.clickSalesTab();
        shippingSettings = configPage.goToShippingSettingsTab();
        shippingSettings.setShippingOrigin(Address.Berlin.country.fullName, Address.Berlin.city, Address.Berlin.zip5, Address.Berlin.city, Address.Berlin.addressLine1);
        configPage.saveConfigurations();

        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        loginPage = homePage.clickSignInButton();
        loginPage.signInToMagentoTap244Store(MagentoData.MAGENTO_244_STORE_AUTOMATION_USER.data, MagentoData.MAGENTO_244_STORE_AUTOMATION_PASSWORD.data);
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(true, false, MagentoTapEUAddresses.BELGIUM_ADDRESS.addressLine1,
                MagentoTapEUAddresses.BELGIUM_ADDRESS.country, MagentoTapEUAddresses.BELGIUM_ADDRESS.region,
                MagentoTapEUAddresses.BELGIUM_ADDRESS.city, MagentoTapEUAddresses.BELGIUM_ADDRESS.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        assertEquals(paymentPage.calculatePercentBasedTax(21), paymentPage.getTaxFromUI());
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();

        // Login to Admin panel, Search for Order number & Submit Invoice
        quitDriver();
        createChromeDriver();
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMTAP-47 CMTAP - Test Case - Create Sales Order for VAT for Czechia (US-EU) and Invoice
     */
    @Test(groups = {"magentoTap_regression"})
    public void invoiceUSToCzechTest() {
        // Go to Admin console & Set Ship From address
        configPage = navigateToMagento244Config();
        configPage.clickSalesTab();
        shippingSettings = configPage.goToShippingSettingsTab();
        shippingSettings.setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);
        configPage.saveConfigurations();

        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        loginPage = homePage.clickSignInButton();
        loginPage.signInToMagentoTap244Store(MagentoData.MAGENTO_244_STORE_AUTOMATION_USER.data, MagentoData.MAGENTO_244_STORE_AUTOMATION_PASSWORD.data);
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(true, false, MagentoTapEUAddresses.CZECH_ADDRESS.addressLine1,
                MagentoTapEUAddresses.CZECH_ADDRESS.country, MagentoTapEUAddresses.CZECH_ADDRESS.region,
                MagentoTapEUAddresses.CZECH_ADDRESS.city, MagentoTapEUAddresses.CZECH_ADDRESS.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        assertEquals(paymentPage.calculatePercentBasedTax(21), paymentPage.getTaxFromUI());
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();

        // Login to Admin panel, Search for Order number & Submit Invoice
        quitDriver();
        createChromeDriver();
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMTAP-72 CMTAP - Test Case - Create Sales Order for VAT for France to Czechia (EU-EU) and Invoice
     */
    @Test(groups = {"magentoTap_regression"})
    public void invoiceFranceToCzechTest() {
        // Go to Admin console & Set Ship From address
        configPage = navigateToMagento244Config();
        configPage.clickSalesTab();
        shippingSettings = configPage.goToShippingSettingsTab();
        shippingSettings.setShippingOrigin(Address.Paris.country.fullName, Address.Paris.city, Address.Paris.zip5, Address.Paris.city, Address.Paris.addressLine1);
        configPage.saveConfigurations();

        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        loginPage = homePage.clickSignInButton();
        loginPage.signInToMagentoTap244Store(MagentoData.MAGENTO_244_STORE_AUTOMATION_USER.data, MagentoData.MAGENTO_244_STORE_AUTOMATION_PASSWORD.data);
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(true, false, MagentoTapEUAddresses.CZECH_ADDRESS.addressLine1,
                MagentoTapEUAddresses.CZECH_ADDRESS.country, MagentoTapEUAddresses.CZECH_ADDRESS.region,
                MagentoTapEUAddresses.CZECH_ADDRESS.city, MagentoTapEUAddresses.CZECH_ADDRESS.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        assertEquals(paymentPage.calculatePercentBasedTax(21), paymentPage.getTaxFromUI());
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();

        // Login to Admin panel, Search for Order number & Submit Invoice
        quitDriver();
        createChromeDriver();
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMTAP-48 CMTAP - Test Case - Create Sales Order for VAT for France to Denmark (EU-EU) and Invoice
     */
    @Test(groups = {"magentoTap_regression"})
    public void invoiceParisToDenmarkTest() {
        // Go to Admin console & Set Ship From address
        configPage = navigateToMagento244Config();
        configPage.clickSalesTab();
        shippingSettings = configPage.goToShippingSettingsTab();
        shippingSettings.setShippingOrigin(Address.Paris.country.fullName, Address.Paris.city, Address.Paris.zip5, Address.Paris.city, Address.Paris.addressLine1);

        configPage.saveConfigurations();

        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        loginPage = homePage.clickSignInButton();
        loginPage.signInToMagentoTap244Store(MagentoData.MAGENTO_244_STORE_AUTOMATION_USER.data, MagentoData.MAGENTO_244_STORE_AUTOMATION_PASSWORD.data);
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(true, false, MagentoTapEUAddresses.DENMARK_ADDRESS.addressLine1,
                MagentoTapEUAddresses.DENMARK_ADDRESS.country, MagentoTapEUAddresses.DENMARK_ADDRESS.region,
                MagentoTapEUAddresses.DENMARK_ADDRESS.city, MagentoTapEUAddresses.DENMARK_ADDRESS.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        assertEquals(paymentPage.calculatePercentBasedTax(25), paymentPage.getTaxFromUI());
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();

        // Login to Admin panel, Search for Order number & Submit Invoice
        quitDriver();
        createChromeDriver();
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMTAP-73 CMTAP - Test Case - Create Sales Order for VAT for Denmark to France (EU-EU) and Invoice
     */
    @Test(groups = {"magentoTap_regression"})
    public void invoiceDenmarkToParisTest() {
        // Go to Admin console & Set Ship From address
        configPage = navigateToMagento244Config();
        configPage.clickSalesTab();
        shippingSettings = configPage.goToShippingSettingsTab();
        shippingSettings.setShippingOrigin(MagentoTapEUAddresses.DENMARK_ADDRESS.country, MagentoTapEUAddresses.DENMARK_ADDRESS.region, MagentoTapEUAddresses.DENMARK_ADDRESS.zip5, MagentoTapEUAddresses.DENMARK_ADDRESS.city, MagentoTapEUAddresses.DENMARK_ADDRESS.addressLine1);

        configPage.saveConfigurations();

        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        loginPage = homePage.clickSignInButton();
        loginPage.signInToMagentoTap244Store(MagentoData.MAGENTO_244_STORE_AUTOMATION_USER.data, MagentoData.MAGENTO_244_STORE_AUTOMATION_PASSWORD.data);
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(true, false, Address.Paris.addressLine1,
                Address.Paris.country.fullName, Address.Paris.city,
                Address.Paris.city, Address.Paris.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        assertEquals(paymentPage.calculatePercentBasedTax(20), paymentPage.getTaxFromUI());
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();

        // Login to Admin panel, Search for Order number & Submit Invoice
        quitDriver();
        createChromeDriver();
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMTAP-49 CMTAP - Test Case - Create Sales Order for VAT for Germany to Finland (EU-EU) and Invoice
     */
    @Test(groups = {"magentoTap_regression"})
    public void invoiceBerlinToFinlandTest() {
        // Go to Admin console & Set Ship From address
        configPage = navigateToMagento244Config();
        configPage.clickSalesTab();
        shippingSettings = configPage.goToShippingSettingsTab();
        shippingSettings.setShippingOrigin(Address.Berlin.country.fullName, Address.Berlin.city, Address.Berlin.zip5, Address.Berlin.city, Address.Berlin.addressLine1);

        configPage.saveConfigurations();

        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        loginPage = homePage.clickSignInButton();
        loginPage.signInToMagentoTap244Store(MagentoData.MAGENTO_244_STORE_AUTOMATION_USER.data, MagentoData.MAGENTO_244_STORE_AUTOMATION_PASSWORD.data);
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(true, false, MagentoTapEUAddresses.FINLAND_ADDRESS.addressLine1,
                MagentoTapEUAddresses.FINLAND_ADDRESS.country, MagentoTapEUAddresses.FINLAND_ADDRESS.region,
                MagentoTapEUAddresses.FINLAND_ADDRESS.city, MagentoTapEUAddresses.FINLAND_ADDRESS.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        assertEquals(paymentPage.calculatePercentBasedTax(24), paymentPage.getTaxFromUI());
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();

        // Login to Admin panel, Search for Order number & Submit Invoice
        quitDriver();
        createChromeDriver();
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMTAP-74 CMTAP - Test Case - Create Sales Order for VAT for Finland to Germany (EU-EU) and Invoice
     */
    @Test(groups = {"magentoTap_regression"})
    public void invoiceFinlandToGermanyTest() {
        // Go to Admin console & Set Ship From address
        configPage = navigateToMagento244Config();
        configPage.clickSalesTab();
        shippingSettings = configPage.goToShippingSettingsTab();
        shippingSettings.setShippingOrigin(MagentoTapEUAddresses.FINLAND_ADDRESS.country, MagentoTapEUAddresses.FINLAND_ADDRESS.region, MagentoTapEUAddresses.FINLAND_ADDRESS.zip5, MagentoTapEUAddresses.FINLAND_ADDRESS.city, MagentoTapEUAddresses.FINLAND_ADDRESS.addressLine1);

        configPage.saveConfigurations();

        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        loginPage = homePage.clickSignInButton();
        loginPage.signInToMagentoTap244Store(MagentoData.MAGENTO_244_STORE_AUTOMATION_USER.data, MagentoData.MAGENTO_244_STORE_AUTOMATION_PASSWORD.data);
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

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        assertEquals(paymentPage.calculatePercentBasedTax(19), paymentPage.getTaxFromUI());
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();

        // Login to Admin panel, Search for Order number & Submit Invoice
        quitDriver();
        createChromeDriver();
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMTAP-50 CMTAP - Test Case - Create Sales Order for VAT for Ireland (US-EU) and Invoice
     */
    @Test(groups = {"magentoTap_regression"})
    public void invoiceUSToIrelandTest() {
        // Go to Admin console & Set Ship From address
        configPage = navigateToMagento244Config();
        configPage.clickSalesTab();
        shippingSettings = configPage.goToShippingSettingsTab();
        shippingSettings.setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);
        configPage.saveConfigurations();

        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        loginPage = homePage.clickSignInButton();
        loginPage.signInToMagentoTap244Store(MagentoData.MAGENTO_244_STORE_AUTOMATION_USER.data, MagentoData.MAGENTO_244_STORE_AUTOMATION_PASSWORD.data);
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(true, false, MagentoTapEUAddresses.IRELAND_ADDRESS.addressLine1,
                MagentoTapEUAddresses.IRELAND_ADDRESS.country, MagentoTapEUAddresses.IRELAND_ADDRESS.region,
                MagentoTapEUAddresses.IRELAND_ADDRESS.city, MagentoTapEUAddresses.IRELAND_ADDRESS.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        assertEquals(paymentPage.calculatePercentBasedTax(23), paymentPage.getTaxFromUI());
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();

        // Login to Admin panel, Search for Order number & Submit Invoice
        quitDriver();
        createChromeDriver();
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMTAP-75 CMTAP - Test Case - Create Sales Order for VAT for Germany to Ireland (EU-EU) and Invoice
     */
    @Test(groups = {"magentoTap_regression"})
    public void invoiceGermanyToIrelandTest() {
        // Go to Admin console & Set Ship From address
        configPage = navigateToMagento244Config();
        configPage.clickSalesTab();
        shippingSettings = configPage.goToShippingSettingsTab();
        shippingSettings.setShippingOrigin(Address.BerlinAlternate.country.fullName, Address.BerlinAlternate.city, Address.BerlinAlternate.zip5, Address.BerlinAlternate.city, Address.BerlinAlternate.addressLine1);
        configPage.saveConfigurations();

        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        loginPage = homePage.clickSignInButton();
        loginPage.signInToMagentoTap244Store(MagentoData.MAGENTO_244_STORE_AUTOMATION_USER.data, MagentoData.MAGENTO_244_STORE_AUTOMATION_PASSWORD.data);
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(true, false, MagentoTapEUAddresses.IRELAND_ADDRESS.addressLine1,
                MagentoTapEUAddresses.IRELAND_ADDRESS.country, MagentoTapEUAddresses.IRELAND_ADDRESS.region,
                MagentoTapEUAddresses.IRELAND_ADDRESS.city, MagentoTapEUAddresses.IRELAND_ADDRESS.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        assertEquals(paymentPage.calculatePercentBasedTax(23), paymentPage.getTaxFromUI());
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();

        // Login to Admin panel, Search for Order number & Submit Invoice
        quitDriver();
        createChromeDriver();
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMTAP-51 CMTAP - Test Case - Create Sales Order for VAT for Italy (US-EU) and Invoice
     */
    @Test(groups = {"magentoTap_regression"})
    public void invoiceUSToItalyTest() {
        // Go to Admin console & Set Ship From address
        configPage = navigateToMagento244Config();
        configPage.clickSalesTab();
        shippingSettings = configPage.goToShippingSettingsTab();
        shippingSettings.setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);
        configPage.saveConfigurations();

        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        loginPage = homePage.clickSignInButton();
        loginPage.signInToMagentoTap244Store(MagentoData.MAGENTO_244_STORE_AUTOMATION_USER.data, MagentoData.MAGENTO_244_STORE_AUTOMATION_PASSWORD.data);
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(true, false, MagentoTapEUAddresses.ITALY_ADDRESS.addressLine1,
                MagentoTapEUAddresses.ITALY_ADDRESS.country, MagentoTapEUAddresses.ITALY_ADDRESS.region,
                MagentoTapEUAddresses.ITALY_ADDRESS.city, MagentoTapEUAddresses.ITALY_ADDRESS.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        assertEquals(paymentPage.calculatePercentBasedTax(22), paymentPage.getTaxFromUI());
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();

        // Login to Admin panel, Search for Order number & Submit Invoice
        quitDriver();
        createChromeDriver();
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMTAP-76 CMTAP - Test Case - Create Sales Order for VAT for Paris to Italy (EU-EU) and Invoice
     */
    @Test(groups = {"magentoTap_regression"})
    public void invoiceParisToItalyTest() {
        // Go to Admin console & Set Ship From address
        configPage = navigateToMagento244Config();
        configPage.clickSalesTab();
        shippingSettings = configPage.goToShippingSettingsTab();
        shippingSettings.setShippingOrigin(Address.Paris.country.fullName, Address.Paris.city, Address.Paris.zip5, Address.Paris.city, Address.Paris.addressLine1);
        configPage.saveConfigurations();

        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        loginPage = homePage.clickSignInButton();
        loginPage.signInToMagentoTap244Store(MagentoData.MAGENTO_244_STORE_AUTOMATION_USER.data, MagentoData.MAGENTO_244_STORE_AUTOMATION_PASSWORD.data);
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(true, false, MagentoTapEUAddresses.ITALY_ADDRESS.addressLine1,
                MagentoTapEUAddresses.ITALY_ADDRESS.country, MagentoTapEUAddresses.ITALY_ADDRESS.region,
                MagentoTapEUAddresses.ITALY_ADDRESS.city, MagentoTapEUAddresses.ITALY_ADDRESS.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        assertEquals(paymentPage.calculatePercentBasedTax(22), paymentPage.getTaxFromUI());
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();

        // Login to Admin panel, Search for Order number & Submit Invoice
        quitDriver();
        createChromeDriver();
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMTAP-52 CMTAP - Test Case - Create Sales Order for VAT for the Netherlands (US-EU) and Invoice
     */
    @Test(groups = {"magentoTap_regression"})
    public void invoiceUSToNetherlandsTest() {
        // Go to Admin console & Set Ship From address
        configPage = navigateToMagento244Config();
        configPage.clickSalesTab();
        shippingSettings = configPage.goToShippingSettingsTab();
        shippingSettings.setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);
        configPage.saveConfigurations();

        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        loginPage = homePage.clickSignInButton();
        loginPage.signInToMagentoTap244Store(MagentoData.MAGENTO_244_STORE_AUTOMATION_USER.data, MagentoData.MAGENTO_244_STORE_AUTOMATION_PASSWORD.data);
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(true, false, MagentoTapEUAddresses.NETHERLANDS_ADDRESS.addressLine1,
                MagentoTapEUAddresses.NETHERLANDS_ADDRESS.country, MagentoTapEUAddresses.NETHERLANDS_ADDRESS.region,
                MagentoTapEUAddresses.NETHERLANDS_ADDRESS.city, MagentoTapEUAddresses.NETHERLANDS_ADDRESS.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        assertEquals(paymentPage.calculatePercentBasedTax(21), paymentPage.getTaxFromUI());
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();

        // Login to Admin panel, Search for Order number & Submit Invoice
        quitDriver();
        createChromeDriver();
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMTAP-77 CMTAP - Test Case - Create Sales Order for VAT for the Greece to the Netherlands (EU-EU) and Invoice
     */
    @Test(groups = {"magentoTap_regression"})
    public void invoiceGreeceToNetherlandsTest() {
        // Go to Admin console & Set Ship From address
        configPage = navigateToMagento244Config();
        configPage.clickSalesTab();
        shippingSettings = configPage.goToShippingSettingsTab();
        shippingSettings.setShippingOrigin(MagentoData.GR_SHIPPING_COUNTRY.data, MagentoData.GR_SHIPPING_STATE.data, MagentoData.GR_SHIPPING_CITY.data, MagentoData.GR_SHIPPING_ZIP.data, MagentoData.GR_SHIPPING_STREET0.data);
        configPage.saveConfigurations();

        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        loginPage = homePage.clickSignInButton();
        loginPage.signInToMagentoTap244Store(MagentoData.MAGENTO_244_STORE_AUTOMATION_USER.data, MagentoData.MAGENTO_244_STORE_AUTOMATION_PASSWORD.data);
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(true, false, MagentoTapEUAddresses.NETHERLANDS_ADDRESS.addressLine1,
                MagentoTapEUAddresses.NETHERLANDS_ADDRESS.country, MagentoTapEUAddresses.NETHERLANDS_ADDRESS.region,
                MagentoTapEUAddresses.NETHERLANDS_ADDRESS.city, MagentoTapEUAddresses.NETHERLANDS_ADDRESS.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        assertEquals(paymentPage.calculatePercentBasedTax(21), paymentPage.getTaxFromUI());
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();

        // Login to Admin panel, Search for Order number & Submit Invoice
        quitDriver();
        createChromeDriver();
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMTAP-53 CMTAP - Test Case - Create Sales Order for VAT for France to Poland (EU-EU) and Invoice
     */
    @Test(groups = {"magentoTap_regression"})
    public void invoiceParisToPolandTest() {
        // Go to Admin console & Set Ship From address
        configPage = navigateToMagento244Config();
        configPage.clickSalesTab();
        shippingSettings = configPage.goToShippingSettingsTab();
        shippingSettings.setShippingOrigin(Address.Paris.country.fullName, Address.Paris.city, Address.Paris.zip5, Address.Paris.city, Address.Paris.addressLine1);
        configPage.saveConfigurations();

        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        loginPage = homePage.clickSignInButton();
        loginPage.signInToMagentoTap244Store(MagentoData.MAGENTO_244_STORE_AUTOMATION_USER.data, MagentoData.MAGENTO_244_STORE_AUTOMATION_PASSWORD.data);
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(true, false, MagentoTapEUAddresses.POLAND_ADDRESS.addressLine1,
                MagentoTapEUAddresses.POLAND_ADDRESS.country, MagentoTapEUAddresses.POLAND_ADDRESS.region,
                MagentoTapEUAddresses.POLAND_ADDRESS.city, MagentoTapEUAddresses.POLAND_ADDRESS.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        assertEquals(paymentPage.calculatePercentBasedTax(23), paymentPage.getTaxFromUI());
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();

        // Login to Admin panel, Search for Order number & Submit Invoice
        quitDriver();
        createChromeDriver();
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMTAP-78 CMTAP - Test Case - Create Sales Order for VAT for Poland to France (EU-EU) and Invoice
     */
    @Test(groups = {"magentoTap_regression"})
    public void invoicePolandToParisTest() {
        // Go to Admin console & Set Ship From address
        configPage = navigateToMagento244Config();
        configPage.clickSalesTab();
        shippingSettings = configPage.goToShippingSettingsTab();
        shippingSettings.setShippingOrigin(MagentoTapEUAddresses.POLAND_ADDRESS.country, MagentoTapEUAddresses.POLAND_ADDRESS.region, MagentoTapEUAddresses.POLAND_ADDRESS.zip5, MagentoTapEUAddresses.POLAND_ADDRESS.city, MagentoTapEUAddresses.POLAND_ADDRESS.addressLine1);
        configPage.saveConfigurations();

        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        loginPage = homePage.clickSignInButton();
        loginPage.signInToMagentoTap244Store(MagentoData.MAGENTO_244_STORE_AUTOMATION_USER.data, MagentoData.MAGENTO_244_STORE_AUTOMATION_PASSWORD.data);
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(true, false, Address.Paris.addressLine1,
                Address.Paris.country.fullName, Address.Paris.city,
                Address.Paris.city, Address.Paris.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        assertEquals(paymentPage.calculatePercentBasedTax(20), paymentPage.getTaxFromUI());
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();

        // Login to Admin panel, Search for Order number & Submit Invoice
        quitDriver();
        createChromeDriver();
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMTAP-54 CMTAP - Test Case - Create Sales Order for VAT for Germany to Portugal (EU-EU) and Invoice
     */
    @Test(groups = {"magentoTap_regression"})
    public void invoiceGermanyToPortugueseTest() {
        // Go to Admin console & Set Ship From address
        configPage = navigateToMagento244Config();
        configPage.clickSalesTab();
        shippingSettings = configPage.goToShippingSettingsTab();
        shippingSettings.setShippingOrigin(Address.BerlinAlternate.country.fullName, Address.BerlinAlternate.city, Address.BerlinAlternate.zip5, Address.BerlinAlternate.city, Address.BerlinAlternate.addressLine1);
        configPage.saveConfigurations();

        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        loginPage = homePage.clickSignInButton();
        loginPage.signInToMagentoTap244Store(MagentoData.MAGENTO_244_STORE_AUTOMATION_USER.data, MagentoData.MAGENTO_244_STORE_AUTOMATION_PASSWORD.data);
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(true, false, MagentoTapEUAddresses.PORTUGAL_ADDRESS.addressLine1,
                MagentoTapEUAddresses.PORTUGAL_ADDRESS.country, MagentoTapEUAddresses.PORTUGAL_ADDRESS.region,
                MagentoTapEUAddresses.PORTUGAL_ADDRESS.city, MagentoTapEUAddresses.PORTUGAL_ADDRESS.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        assertEquals(paymentPage.calculatePercentBasedTax(23), paymentPage.getTaxFromUI());
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();

        // Login to Admin panel, Search for Order number & Submit Invoice
        quitDriver();
        createChromeDriver();
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMTAP-79 CMTAP - Test Case - Create Sales Order for VAT for Portugal to Germany (EU-EU) and Invoice
     */
    @Test(groups = {"magentoTap_regression"})
    public void invoicePortugalToGermanyTest() {
        // Go to Admin console & Set Ship From address
        configPage = navigateToMagento244Config();
        configPage.clickSalesTab();
        shippingSettings = configPage.goToShippingSettingsTab();
        shippingSettings.setShippingOrigin(MagentoTapEUAddresses.PORTUGAL_ADDRESS.country, MagentoTapEUAddresses.PORTUGAL_ADDRESS.region, MagentoTapEUAddresses.PORTUGAL_ADDRESS.zip5, MagentoTapEUAddresses.PORTUGAL_ADDRESS.city, MagentoTapEUAddresses.PORTUGAL_ADDRESS.addressLine1);
        configPage.saveConfigurations();

        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        loginPage = homePage.clickSignInButton();
        loginPage.signInToMagentoTap244Store(MagentoData.MAGENTO_244_STORE_AUTOMATION_USER.data, MagentoData.MAGENTO_244_STORE_AUTOMATION_PASSWORD.data);
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

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        assertEquals(paymentPage.calculatePercentBasedTax(19), paymentPage.getTaxFromUI());
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();

        // Login to Admin panel, Search for Order number & Submit Invoice
        quitDriver();
        createChromeDriver();
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMTAP-55 CMTAP - Test Case - Create Sales Order for VAT for Spain (US-EU) and Invoice
     */
    @Test(groups = {"magentoTap_regression"})
    public void invoiceUSToSpainTest() {
        // Go to Admin console & Set Ship From address
        configPage = navigateToMagento244Config();
        configPage.clickSalesTab();
        shippingSettings = configPage.goToShippingSettingsTab();
        shippingSettings.setShippingOrigin(Address.LosAngeles.country.fullName, Address.LosAngeles.state.fullName, Address.LosAngeles.zip5, Address.LosAngeles.city, Address.LosAngeles.addressLine1);
        configPage.saveConfigurations();

        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        loginPage = homePage.clickSignInButton();
        loginPage.signInToMagentoTap244Store(MagentoData.MAGENTO_244_STORE_AUTOMATION_USER.data, MagentoData.MAGENTO_244_STORE_AUTOMATION_PASSWORD.data);
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(true, false, MagentoTapEUAddresses.SPAIN_ADDRESS.addressLine1,
                MagentoTapEUAddresses.SPAIN_ADDRESS.country, MagentoTapEUAddresses.SPAIN_ADDRESS.region,
                MagentoTapEUAddresses.SPAIN_ADDRESS.city, MagentoTapEUAddresses.SPAIN_ADDRESS.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        assertEquals(paymentPage.calculatePercentBasedTax(21), paymentPage.getTaxFromUI());
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();

        // Login to Admin panel, Search for Order number & Submit Invoice
        quitDriver();
        createChromeDriver();
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMTAP-80 CMTAP - Test Case - Create Sales Order for VAT for France to Spain (EU-EU) and Invoice
     */
    @Test(groups = {"magentoTap_regression"})
    public void invoiceFranceToSpainTest() {
        // Go to Admin console & Set Ship From address
        configPage = navigateToMagento244Config();
        configPage.clickSalesTab();
        shippingSettings = configPage.goToShippingSettingsTab();
        shippingSettings.setShippingOrigin(Address.Paris.country.fullName, Address.Paris.city, Address.Paris.zip5, Address.Paris.city, Address.Paris.addressLine1);
        configPage.saveConfigurations();

        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        loginPage = homePage.clickSignInButton();
        loginPage.signInToMagentoTap244Store(MagentoData.MAGENTO_244_STORE_AUTOMATION_USER.data, MagentoData.MAGENTO_244_STORE_AUTOMATION_PASSWORD.data);
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(true, false, MagentoTapEUAddresses.SPAIN_ADDRESS.addressLine1,
                MagentoTapEUAddresses.SPAIN_ADDRESS.country, MagentoTapEUAddresses.SPAIN_ADDRESS.region,
                MagentoTapEUAddresses.SPAIN_ADDRESS.city, MagentoTapEUAddresses.SPAIN_ADDRESS.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        assertEquals(paymentPage.calculatePercentBasedTax(21), paymentPage.getTaxFromUI());
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();

        // Login to Admin panel, Search for Order number & Submit Invoice
        quitDriver();
        createChromeDriver();
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMTAP-81 CMTAP - Test Case - Create Sales Order for VAT for Spain to France (EU-EU) and Invoice
     */
    @Test(groups = {"magentoTap_regression"})
    public void invoiceSpainToFranceTest() {
        // Go to Admin console & Set Ship From address
        configPage = navigateToMagento244Config();
        configPage.clickSalesTab();
        shippingSettings = configPage.goToShippingSettingsTab();
        shippingSettings.setShippingOrigin(MagentoTapEUAddresses.SPAIN_ADDRESS.country, MagentoTapEUAddresses.SPAIN_ADDRESS.region, MagentoTapEUAddresses.SPAIN_ADDRESS.zip5, MagentoTapEUAddresses.SPAIN_ADDRESS.city, MagentoTapEUAddresses.SPAIN_ADDRESS.addressLine1);
        configPage.saveConfigurations();

        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        loginPage = homePage.clickSignInButton();
        loginPage.signInToMagentoTap244Store(MagentoData.MAGENTO_244_STORE_AUTOMATION_USER.data, MagentoData.MAGENTO_244_STORE_AUTOMATION_PASSWORD.data);
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(true, false, Address.Paris.addressLine1,
                Address.Paris.country.fullName, Address.Paris.city,
                Address.Paris.city, Address.Paris.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        assertEquals(paymentPage.calculatePercentBasedTax(20), paymentPage.getTaxFromUI());
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();

        // Login to Admin panel, Search for Order number & Submit Invoice
        quitDriver();
        createChromeDriver();
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMTAP-56 CMTAP - Test Case - Create Sales Order for VAT for Greece to Sweden (EU-EU) and Invoice
     */
    @Test(groups = {"magentoTap_regression"})
    public void invoiceGreeceToSwedenTest() {
        // Go to Admin console & Set Ship From address
        configPage = navigateToMagento244Config();
        configPage.clickSalesTab();
        shippingSettings = configPage.goToShippingSettingsTab();
        shippingSettings.setShippingOrigin(MagentoData.GR_SHIPPING_COUNTRY.data, MagentoData.GR_SHIPPING_STATE.data, MagentoData.GR_SHIPPING_ZIP.data, MagentoData.GR_SHIPPING_CITY.data, MagentoData.GR_SHIPPING_STREET0.data);
        configPage.saveConfigurations();

        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        loginPage = homePage.clickSignInButton();
        loginPage.signInToMagentoTap244Store(MagentoData.MAGENTO_244_STORE_AUTOMATION_USER.data, MagentoData.MAGENTO_244_STORE_AUTOMATION_PASSWORD.data);
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(true, false, MagentoTapEUAddresses.SPAIN_ADDRESS.addressLine1,
                MagentoTapEUAddresses.SPAIN_ADDRESS.country, MagentoTapEUAddresses.SPAIN_ADDRESS.region,
                MagentoTapEUAddresses.SPAIN_ADDRESS.city, MagentoTapEUAddresses.SPAIN_ADDRESS.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        assertEquals(paymentPage.calculatePercentBasedTax(21), paymentPage.getTaxFromUI());
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();

        // Login to Admin panel, Search for Order number & Submit Invoice
        quitDriver();
        createChromeDriver();
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMTAP-82 CMTAP - Test Case - Create Sales Order for VAT for Sweden to Greece (EU-EU) and Invoice
     */
    @Test(groups = {"magentoTap_regression"})
    public void invoiceSwedenToGreeceTest() {
        // Go to Admin console & Set Ship From address
        configPage = navigateToMagento244Config();
        configPage.clickSalesTab();
        shippingSettings = configPage.goToShippingSettingsTab();
        shippingSettings.setShippingOrigin(MagentoTapEUAddresses.SPAIN_ADDRESS.country, MagentoTapEUAddresses.SPAIN_ADDRESS.region, MagentoTapEUAddresses.SPAIN_ADDRESS.zip5, MagentoTapEUAddresses.SPAIN_ADDRESS.city, MagentoTapEUAddresses.SPAIN_ADDRESS.addressLine1);
        configPage.saveConfigurations();

        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        loginPage = homePage.clickSignInButton();
        loginPage.signInToMagentoTap244Store(MagentoData.MAGENTO_244_STORE_AUTOMATION_USER.data, MagentoData.MAGENTO_244_STORE_AUTOMATION_PASSWORD.data);
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(true, false, MagentoData.GR_SHIPPING_STREET0.data,
                MagentoData.GR_SHIPPING_COUNTRY.data, MagentoData.GR_SHIPPING_STATE.data,
                MagentoData.GR_SHIPPING_CITY.data, MagentoData.GR_SHIPPING_ZIP.data);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        assertEquals(paymentPage.calculatePercentBasedTax(24), paymentPage.getTaxFromUI());
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();

        // Login to Admin panel, Search for Order number & Submit Invoice
        quitDriver();
        createChromeDriver();
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMTAP-83 CMTAP - Test Case - Create Sales Order for VAT for US to Sweden (US-EU) and Invoice
     */
    @Test(groups = {"magentoTap_regression"})
    public void invoiceUSToSwedenTest() {
        // Go to Admin console & Set Ship From address
        configPage = navigateToMagento244Config();
        configPage.clickSalesTab();
        shippingSettings = configPage.goToShippingSettingsTab();
        shippingSettings.setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);
        configPage.saveConfigurations();

        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        loginPage = homePage.clickSignInButton();
        loginPage.signInToMagentoTap244Store(MagentoData.MAGENTO_244_STORE_AUTOMATION_USER.data, MagentoData.MAGENTO_244_STORE_AUTOMATION_PASSWORD.data);
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(true, false, MagentoTapEUAddresses.SPAIN_ADDRESS.addressLine1,
                MagentoTapEUAddresses.SPAIN_ADDRESS.country, MagentoTapEUAddresses.SPAIN_ADDRESS.region,
                MagentoTapEUAddresses.SPAIN_ADDRESS.city, MagentoTapEUAddresses.SPAIN_ADDRESS.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        assertEquals(paymentPage.calculatePercentBasedTax(21), paymentPage.getTaxFromUI());
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();

        // Login to Admin panel, Search for Order number & Submit Invoice
        quitDriver();
        createChromeDriver();
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMTAP-57 CMTAP - Test Case - Create Sales Order for VAT for Austria to Slovenia (EU-EU) and Invoice
     */
    @Test(groups = {"magentoTap_regression"})
    public void invoiceAustriaToSloveniaTest() {
        // Go to Admin console & Set Ship From address
        configPage = navigateToMagento244Config();
        configPage.clickSalesTab();
        shippingSettings = configPage.goToShippingSettingsTab();
        shippingSettings.setShippingOrigin(MagentoData.AT_SHIPPING_COUNTRY.data, MagentoData.AT_SHIPPING_STATE.data, MagentoData.AT_SHIPPING_ZIP.data, MagentoData.AT_SHIPPING_CITY.data, MagentoData.AT_SHIPPING_STREET0.data);
        configPage.saveConfigurations();

        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        loginPage = homePage.clickSignInButton();
        loginPage.signInToMagentoTap244Store(MagentoData.MAGENTO_244_STORE_AUTOMATION_USER.data, MagentoData.MAGENTO_244_STORE_AUTOMATION_PASSWORD.data);
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(true, false, MagentoTapEUAddresses.SLOVENIA_ADDRESS.addressLine1,
                MagentoTapEUAddresses.SLOVENIA_ADDRESS.country, MagentoTapEUAddresses.SLOVENIA_ADDRESS.region,
                MagentoTapEUAddresses.SLOVENIA_ADDRESS.city, MagentoTapEUAddresses.SLOVENIA_ADDRESS.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        assertEquals(paymentPage.calculatePercentBasedTax(22), paymentPage.getTaxFromUI());
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();

        // Login to Admin panel, Search for Order number & Submit Invoice
        quitDriver();
        createChromeDriver();
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMTAP-85 CMTAP - Test Case - Create Sales Order for VAT for US to Slovenia (EU-EU) and Invoice
     */
    @Test(groups = {"magentoTap_regression"})
    public void invoiceUSToSloveniaTest() {
        // Go to Admin console & Set Ship From address
        configPage = navigateToMagento244Config();
        configPage.clickSalesTab();
        shippingSettings = configPage.goToShippingSettingsTab();
        shippingSettings.setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);
        configPage.saveConfigurations();

        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        loginPage = homePage.clickSignInButton();
        loginPage.signInToMagentoTap244Store(MagentoData.MAGENTO_244_STORE_AUTOMATION_USER.data, MagentoData.MAGENTO_244_STORE_AUTOMATION_PASSWORD.data);
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(true, false, MagentoTapEUAddresses.SLOVENIA_ADDRESS.addressLine1,
                MagentoTapEUAddresses.SLOVENIA_ADDRESS.country, MagentoTapEUAddresses.SLOVENIA_ADDRESS.region,
                MagentoTapEUAddresses.SLOVENIA_ADDRESS.city, MagentoTapEUAddresses.SLOVENIA_ADDRESS.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        assertEquals(paymentPage.calculatePercentBasedTax(22), paymentPage.getTaxFromUI());
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();

        // Login to Admin panel, Search for Order number & Submit Invoice
        quitDriver();
        createChromeDriver();
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMTAP-84 CMTAP - Test Case - Create Sales Order for VAT for Slovenia to Austria (EU-EU) and Invoice
     */
    @Test(groups = {"magentoTap_regression"})
    public void invoiceSloveniaToAustriaTest() {
        // Go to Admin console & Set Ship From address
        configPage = navigateToMagento244Config();
        configPage.clickSalesTab();
        shippingSettings = configPage.goToShippingSettingsTab();
        shippingSettings.setShippingOrigin(MagentoTapEUAddresses.SLOVENIA_ADDRESS.country, MagentoTapEUAddresses.SLOVENIA_ADDRESS.region, MagentoTapEUAddresses.SLOVENIA_ADDRESS.zip5, MagentoTapEUAddresses.SLOVENIA_ADDRESS.city, MagentoTapEUAddresses.SLOVENIA_ADDRESS.addressLine1);
        configPage.saveConfigurations();

        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        loginPage = homePage.clickSignInButton();
        loginPage.signInToMagentoTap244Store(MagentoData.MAGENTO_244_STORE_AUTOMATION_USER.data, MagentoData.MAGENTO_244_STORE_AUTOMATION_PASSWORD.data);
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(true, false, MagentoData.AT_SHIPPING_STREET0.data,
                MagentoData.AT_SHIPPING_COUNTRY.data, MagentoData.AT_SHIPPING_STATE.data,
                MagentoData.AT_SHIPPING_CITY.data, MagentoData.AT_SHIPPING_ZIP.data);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        assertEquals(paymentPage.calculatePercentBasedTax(20), paymentPage.getTaxFromUI());
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();

        // Login to Admin panel, Search for Order number & Submit Invoice
        quitDriver();
        createChromeDriver();
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }
}
