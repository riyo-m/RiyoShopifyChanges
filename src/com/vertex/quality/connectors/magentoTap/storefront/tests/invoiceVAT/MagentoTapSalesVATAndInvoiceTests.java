package com.vertex.quality.connectors.magentoTap.storefront.tests.invoiceVAT;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.magentoTap.admin.pages.*;
import com.vertex.quality.connectors.magentoTap.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magentoTap.common.enums.MagentoData;
import com.vertex.quality.connectors.magentoTap.storefront.pages.*;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * MagentoTap Invoice tess for VAT
 *
 * @author Shivam.Soni
 */
public class MagentoTapSalesVATAndInvoiceTests extends MagentoAdminBaseTest {
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
     * CMTAP-45 - CMTAP - Test Case - Create Sale Order for VAT (US-EU) and Invoice
     */
    @Test(groups = {"magentoTap_regression"})
    public void salesVATInvoiceUSToEUTest() {
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
     * CMTAP-44 - CMTAP - Test Case - Create Sale Order for VAT (Intra EU FR-DE) and Invoice
     */
    @Test(groups = {"magentoTap_regression"})
    public void salesVATInvoiceFRToDETest() {
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
     * CMTAP-43 - CMTAP - Test Case - Create Sale Order for VAT (Greek Territory) and Invoice
     */
    @Test(groups = {"magentoTap_regression"})
    public void salesVATInvoiceFRToGRTest() {
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
        shippingPage.enterShippingDetails(true, false, MagentoData.GR_SHIPPING_STREET0.data,
                MagentoData.GR_SHIPPING_COUNTRY.data, MagentoData.GR_SHIPPING_STATE.data,
                MagentoData.GR_SHIPPING_STATE.data, MagentoData.GR_SHIPPING_ZIP.data);
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
     * CMTAP-42 - CMTAP - Test Case - Create Sale Order for VAT (Austrian Sub-Division) and Invoice
     */
    @Test(groups = {"magentoTap_regression"})
    public void salesVATInvoiceDEToAUTTest() {
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
