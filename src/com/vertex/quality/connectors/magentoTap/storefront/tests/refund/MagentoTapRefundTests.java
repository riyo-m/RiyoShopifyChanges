package com.vertex.quality.connectors.magentoTap.storefront.tests.refund;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.magentoTap.admin.pages.*;
import com.vertex.quality.connectors.magentoTap.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magentoTap.common.enums.MagentoData;
import com.vertex.quality.connectors.magentoTap.storefront.pages.*;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * MagentoTap refund test-cases
 *
 * @author Shivam.Soni
 */
public class MagentoTapRefundTests extends MagentoAdminBaseTest {
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
    M2AdminCreditMemoPage creditMemoPage;

    double calculatedTaxAmount;
    double grandTotalAmount;
    String orderNo;

    /**
     * CMTAP-38 CMTAP - Test Case - Return Sale Order with Shipping for VAT (Intra EU DE-DE) and Invoice
     */
    @Test(groups = {"magentoTap_regression"})
    public void returnSalesOrderWithShippingDEToDETest() {
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
        shippingPage.enterShippingDetails(true, false, Address.Berlin.addressLine1,
                Address.Berlin.country.fullName, Address.Berlin.city,
                Address.Berlin.city, Address.Berlin.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        calculatedTaxAmount = paymentPage.getTaxFromUI();
        grandTotalAmount = paymentPage.getGrandTotalFromUI();
        assertEquals(paymentPage.calculatePercentBasedTax(19), paymentPage.getTaxFromUI());
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();

        // Login to Admin panel, Submit Invoice & Process refund
        quitDriver();
        createChromeDriver();
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
        orderInfoPage = submitCreditMemoWithRefund(MagentoData.quantity_one.data, null, null, MagentoData.PARTIAL_RETURN_AMOUNT.data);

        creditMemoPage = new M2AdminCreditMemoPage(driver);
        assertEquals(orderInfoPage.getRefundedTaxFromUI(), calculatedTaxAmount);
        assertEquals(orderInfoPage.getRefundedAmountFromUI(), grandTotalAmount - Double.parseDouble(MagentoData.PARTIAL_RETURN_AMOUNT.data));
    }

    /**
     * CMTAP-40 CMTAP - Test Case - Full Return Sale Order for VAT (EU DE-FR) and Invoice
     */
    @Test(groups = {"magentoTap_regression"})
    public void returnSalesOrderWithShippingDEToFRTest() {
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
        shippingPage.enterShippingDetails(true, false, Address.Marseille.addressLine1,
                Address.Marseille.country.fullName, MagentoData.MARSEILLE_STATE.data,
                Address.Marseille.city, Address.Marseille.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        calculatedTaxAmount = paymentPage.getTaxFromUI();
        grandTotalAmount = paymentPage.getGrandTotalFromUI();
        assertEquals(paymentPage.calculatePercentBasedTax(20), paymentPage.getTaxFromUI());
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();

        // Login to Admin panel, Submit Invoice & Process refund
        quitDriver();
        createChromeDriver();
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
        orderInfoPage = submitCreditMemoWithRefund(MagentoData.quantity_one.data, null, null, MagentoData.FULL_RETURN_AMOUNT.data);

        creditMemoPage = new M2AdminCreditMemoPage(driver);
        assertEquals(orderInfoPage.getRefundedTaxFromUI(), calculatedTaxAmount);
        assertEquals(orderInfoPage.getRefundedAmountFromUI(), grandTotalAmount);
    }

    /**
     * CMTAP-41 CMTAP - Test Case - Full Return Sale Order with for VAT (EU FR-DE) and Invoice
     */
    @Test(groups = {"magentoTap_regression"})
    public void returnSalesOrderWithShippingFRToDETest() {
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
        calculatedTaxAmount = paymentPage.getTaxFromUI();
        grandTotalAmount = paymentPage.getGrandTotalFromUI();
        assertEquals(paymentPage.calculatePercentBasedTax(19), paymentPage.getTaxFromUI());
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();

        // Login to Admin panel, Submit Invoice & Process refund
        quitDriver();
        createChromeDriver();
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
        orderInfoPage = submitCreditMemoWithRefund(MagentoData.quantity_one.data, null, null, MagentoData.PARTIAL_RETURN_AMOUNT.data);

        creditMemoPage = new M2AdminCreditMemoPage(driver);
        assertEquals(orderInfoPage.getRefundedTaxFromUI(), calculatedTaxAmount);
        assertEquals(orderInfoPage.getRefundedAmountFromUI(), grandTotalAmount - Double.parseDouble(MagentoData.PARTIAL_RETURN_AMOUNT.data));
    }
}
