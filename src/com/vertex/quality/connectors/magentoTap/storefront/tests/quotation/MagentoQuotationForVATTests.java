package com.vertex.quality.connectors.magentoTap.storefront.tests.quotation;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.magentoTap.admin.pages.M2AdminConfigPage;
import com.vertex.quality.connectors.magentoTap.admin.pages.M2AdminShippingSettingsPage;
import com.vertex.quality.connectors.magentoTap.common.enums.MagentoData;
import com.vertex.quality.connectors.magentoTap.storefront.pages.*;
import com.vertex.quality.connectors.magentoTap.storefront.tests.base.M2StorefrontBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * CCMMER-1490 MagentoTap quotation tests
 *
 * @author Shivam.Soni
 */
public class MagentoQuotationForVATTests extends M2StorefrontBaseTest {

    M2AdminConfigPage configPage;
    M2AdminShippingSettingsPage shippingSettings;
    M2StorefrontHomePage homePage;
    M2StorefrontBannerPage storefrontBannerPage;
    M2StorefrontSearchResultPage searchResultPage;
    M2StorefrontShippingInfoPage shippingPage;
    M2StorefrontPaymentMethodPage paymentPage;

    /**
     * CMTAP-11 CMTAP - Test Case - Create Sale Order for VAT (Austrian Sub-Division) and Invoice
     */
    @Test(groups = {"magentoTap_regression", "magentoTap_smoke"})
    public void checkTaxAmountInCreateNewOrderAustrianTest() {
        // Go to Admin console & Set Ship From address
        configPage = navigateToMagento244Config();
        configPage.clickSalesTab();
        shippingSettings = configPage.goToShippingSettingsTab();
        shippingSettings.setShippingOrigin(Address.Berlin.country.fullName, Address.Berlin.city, Address.Berlin.zip5, Address.Berlin.city, Address.Berlin.addressLine1);
        configPage.saveConfigurations();

        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(MagentoData.AT_SHIPPING_STREET0.data,
                MagentoData.AT_SHIPPING_COUNTRY.data, MagentoData.AT_SHIPPING_STATE.data,
                MagentoData.AT_SHIPPING_CITY.data, MagentoData.AT_SHIPPING_ZIP.data);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        assertEquals(paymentPage.calculatePercentBasedTax(20), paymentPage.getTaxFromUI());
        paymentPage.placeTheOrderCreditCard();
    }

    /**
     * CMTAP-12 CMTAP - Test Case -Consignment Sales Order Invoice for VAT (DE FR)
     */
    @Test(groups = {"magentoTap_regression", "magentoTap_smoke"})
    public void checkTaxAmountInCreateNewOrderDEFRTest() {
        // Go to Admin console & Set Ship From address
        configPage = navigateToMagento244Config();
        configPage.clickSalesTab();
        shippingSettings = configPage.goToShippingSettingsTab();
        shippingSettings.setShippingOrigin(Address.Berlin.country.fullName, Address.Berlin.city, Address.Berlin.zip5, Address.Berlin.city, Address.Berlin.addressLine1);
        configPage.saveConfigurations();

        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(Address.Paris.addressLine1,
                Address.Paris.country.fullName, Address.Paris.city,
                Address.Paris.city, Address.Paris.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        assertEquals(paymentPage.calculatePercentBasedTax(20), paymentPage.getTaxFromUI());
        paymentPage.placeTheOrderCreditCard();
    }

    /**
     * CMTAP-10 CMTAP - Test Case - Create Sale Order for VAT (Greek Territory) and Invoice
     */
    @Test(groups = {"magentoTap_regression", "magentoTap_smoke"})
    public void checkTaxAmountInCreateNewOrderGreekTest() {
        // Go to Admin console & Set Ship From address
        configPage = navigateToMagento244Config();
        configPage.clickSalesTab();
        shippingSettings = configPage.goToShippingSettingsTab();
        shippingSettings.setShippingOrigin(Address.Paris.country.fullName, Address.Paris.city, Address.Paris.zip5, Address.Paris.city, Address.Paris.addressLine1);
        configPage.saveConfigurations();

        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(MagentoData.GR_SHIPPING_STREET0.data,
                MagentoData.GR_SHIPPING_COUNTRY.data, MagentoData.GR_SHIPPING_STATE.data,
                MagentoData.GR_SHIPPING_CITY.data, MagentoData.GR_SHIPPING_ZIP.data);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        assertEquals(paymentPage.calculatePercentBasedTax(24), paymentPage.getTaxFromUI());
        paymentPage.placeTheOrderCreditCard();
    }

    /**
     * CMTAP-13 CMTAP - Test Case - Create Sale Order for VAT (Intra EU FR-DE) and Invoice
     */
    @Test(groups = {"magentoTap_regression", "magentoTap_smoke"})
    public void checkTaxAmountInCreateNewOrderIntraFRDETest() {
        // Go to Admin console & Set Ship From address
        configPage = navigateToMagento244Config();
        configPage.clickSalesTab();
        shippingSettings = configPage.goToShippingSettingsTab();
        shippingSettings.setShippingOrigin(Address.Paris.country.fullName, Address.Paris.city, Address.Paris.zip5, Address.Paris.city, Address.Paris.addressLine1);
        configPage.saveConfigurations();

        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(Address.Berlin.addressLine1,
                Address.Berlin.country.fullName, Address.Berlin.city,
                Address.Berlin.city, Address.Berlin.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        assertEquals(paymentPage.calculatePercentBasedTax(19), paymentPage.getTaxFromUI());
        paymentPage.placeTheOrderCreditCard();
    }

    /**
     * CMTAP-9 CMTAP - Test Case - Create Sale Order for VAT (US-EU) and Invoice
     */
    @Test(groups = {"magentoTap_regression", "magentoTap_smoke"})
    public void checkTaxAmountInCreateNewOrderUSEUTest() {
        // Go to Admin console & Set Ship From address
        configPage = navigateToMagento244Config();
        configPage.clickSalesTab();
        shippingSettings = configPage.goToShippingSettingsTab();
        shippingSettings.setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);
        configPage.saveConfigurations();

        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(Address.Paris.addressLine1,
                Address.Paris.country.fullName, Address.Paris.city,
                Address.Paris.city, Address.Paris.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        assertEquals(paymentPage.calculatePercentBasedTax(20), paymentPage.getTaxFromUI());
        paymentPage.placeTheOrderCreditCard();
    }

    /**
     * CMTAP-8 CMTAP - Test Case - Create Sale Order for VAT (EU-US) and Invoice
     */
    @Test(groups = {"magentoTap_regression", "magentoTap_smoke"})
    public void checkTaxAmountInCreateNewOrderPABETest() {
        // Go to Admin console & Set Ship From address
        configPage = navigateToMagento244Config();
        configPage.clickSalesTab();
        shippingSettings = configPage.goToShippingSettingsTab();
        shippingSettings.setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);
        configPage.saveConfigurations();

        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(Address.Berlin.addressLine1,
                Address.Berlin.country.fullName, Address.Berlin.city,
                Address.Berlin.city, Address.Berlin.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        assertEquals(paymentPage.calculatePercentBasedTax(19), paymentPage.getTaxFromUI());
        paymentPage.placeTheOrderCreditCard();
    }
}
