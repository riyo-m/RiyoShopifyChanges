package com.vertex.quality.connectors.magentoTap.storefront.tests.discount;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.enums.CreditCard;
import com.vertex.quality.connectors.magentoTap.admin.pages.M2AdminConfigPage;
import com.vertex.quality.connectors.magentoTap.admin.pages.M2AdminShippingSettingsPage;
import com.vertex.quality.connectors.magentoTap.common.enums.MagentoData;
import com.vertex.quality.connectors.magentoTap.storefront.pages.*;
import com.vertex.quality.connectors.magentoTap.storefront.tests.base.M2StorefrontBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * CCMMER2-74 MagentoTap discount tests
 *
 * @author Shivam.Soni
 */
public class MagentoDiscountForVATTests extends M2StorefrontBaseTest {

    M2AdminConfigPage configPage;
    M2AdminShippingSettingsPage shippingSettings;
    M2StorefrontLoginPage loginPage;
    M2StorefrontHomePage homePage;
    M2StorefrontBannerPage storefrontBannerPage;
    M2StorefrontSearchResultPage searchResultPage;
    M2StorefrontShippingInfoPage shippingPage;
    M2StorefrontPaymentMethodPage paymentPage;
    M2StorefrontThankYouPage thankYouPage;

    String orderNo;

    /**
     * CMTAP-34 CMTAP - Test Case - Create Sale Order with a Discount Shipping for VAT (Intra EU DE-DE) and Invoice
     */
    @Test(groups = {"magentoTap_regression", "magentoTap_smoke"})
    public void discountOrderPercentForDEToDETest() {
        // Go to Admin console & Set Ship From address
        configPage = navigateToMagento244Config();
        configPage.clickSalesTab();
        shippingSettings = configPage.goToShippingSettingsTab();
        shippingSettings.setShippingOrigin(Address.BerlinAlternate.country.fullName, Address.BerlinAlternate.city, Address.BerlinAlternate.zip5, Address.BerlinAlternate.city, Address.BerlinAlternate.addressLine1);
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
        paymentPage.enterCreditCardDetails(CreditCard.NUMBER.text, CreditCard.EXPIRY_MONTH.text + CreditCard.EXPIRY_YEAR.text, CreditCard.CODE.text);
        assertEquals(paymentPage.calculatePercentBasedTax(19), paymentPage.getTaxFromUI());
        paymentPage.applyDiscount(MagentoData.TEN_PERCENT_ORDER.data);
        assertEquals(paymentPage.calculatePercentBasedTax(19), paymentPage.getTaxFromUI());
        paymentPage.placeTheOrderCreditCard();
        paymentPage.enterOTP(paymentPage.getOTPFromUI());

        // Get Order ID.
        thankYouPage = new M2StorefrontThankYouPage(driver);
        orderNo = thankYouPage.getOrderNumberGuest();
    }

    /**
     * CMTAP-35 CMTAP - Test Case - Create Sale Order with a Discount Shipping for VAT (Intra EU DE-FR) and Invoice
     */
    @Test(groups = {"magentoTap_regression"})
    public void discountOrderAmountForDEToFRTest() {
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
        shippingPage.enterShippingDetails(true, false, Address.Paris.addressLine1,
                Address.Paris.country.fullName, Address.Paris.city,
                Address.Paris.city, Address.Paris.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.enterCreditCardDetails(CreditCard.NUMBER.text, CreditCard.EXPIRY_MONTH.text + CreditCard.EXPIRY_YEAR.text, CreditCard.CODE.text);
        assertEquals(paymentPage.calculatePercentBasedTax(20), paymentPage.getTaxFromUI());
        paymentPage.applyDiscount(MagentoData.TEN_DOLLAR_ORDER.data);
        assertEquals(paymentPage.calculatePercentBasedTax(20), paymentPage.getTaxFromUI());
        paymentPage.placeTheOrderCreditCard();
        paymentPage.enterOTP(paymentPage.getOTPFromUI());

        // Get Order ID.
        thankYouPage = new M2StorefrontThankYouPage(driver);
        orderNo = thankYouPage.getOrderNumberGuest();
    }

    /**
     * CMTAP-36 CMTAP - Test Case - Create Sale Order with an Order Amount Discount for VAT (EU DE-AUT) and Invoice
     */
    @Test(groups = {"magentoTap_regression"})
    public void discountOrderAmountForDEToAUTTest() {
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
        paymentPage.applyDiscount(MagentoData.FIVE_DOLLAR_ORDER.data);
        assertEquals(paymentPage.calculatePercentBasedTax(20), paymentPage.getTaxFromUI());
        thankYouPage=paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumberGuest();
    }

    /**
     * CMTAP-37 CMTAP - Test Case - Create Sale Order with a Percent Discount on Order for VAT (EU FR-GR) and Invoice
     */
    @Test(groups = {"magentoTap_regression"})
    public void discountOrderPercentForFRToGRTest() {
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
        shippingPage.enterShippingDetails(true, false, MagentoData.GREECE_ADDRESS1.data,
                MagentoData.GREECE_COUNTRY.data, MagentoData.GREECE_STATE.data,
                MagentoData.GREECE_CITY.data, MagentoData.GREECE_POSTAL.data);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        assertEquals(paymentPage.calculatePercentBasedTax(24), paymentPage.getTaxFromUI());
        paymentPage.applyDiscount(MagentoData.FIVE_PERCENT_ORDER.data);
        assertEquals(paymentPage.calculatePercentBasedTax(24), paymentPage.getTaxFromUI());
        thankYouPage=paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumberGuest();
    }
}
