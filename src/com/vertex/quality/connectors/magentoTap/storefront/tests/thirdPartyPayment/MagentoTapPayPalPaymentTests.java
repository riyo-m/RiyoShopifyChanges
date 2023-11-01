package com.vertex.quality.connectors.magentoTap.storefront.tests.thirdPartyPayment;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.magentoTap.admin.pages.M2AdminConfigPage;
import com.vertex.quality.connectors.magentoTap.admin.pages.M2AdminShippingSettingsPage;
import com.vertex.quality.connectors.magentoTap.common.enums.MagentoData;
import com.vertex.quality.connectors.magentoTap.storefront.pages.*;
import com.vertex.quality.connectors.magentoTap.storefront.tests.base.M2StorefrontBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

/**
 * CCMMER2-351 Magento TAP - Automation testing - new functionality (3rd party Payment)
 *
 * @author Shivam.Soni
 */
public class MagentoTapPayPalPaymentTests extends M2StorefrontBaseTest {

    M2AdminConfigPage configPage;
    M2AdminShippingSettingsPage shippingSettings;
    M2StorefrontLoginPage loginPage;
    M2StorefrontHomePage homePage;
    M2StorefrontBannerPage storefrontBannerPage;
    M2StorefrontSearchResultPage searchResultPage;
    M2StorefrontShippingInfoPage shippingPage;
    M2StorefrontPaymentMethodPage paymentPage;
    M2StorefrontThankYouPage thankYouPage;

    double magentoSubtotal;
    double cartSubtotal;
    double actualTax;
    String customerName = MagentoData.CUSTOMER_FIRST_NAME.data + " " + MagentoData.CUSTOMER_LAST_NAME.data;

    /**
     * CMTAP-64 CMTAP - Test Case - Validate taxation for discounted orders with PayPal payment
     */
    @Test(groups = {"magentoTap_regression"})
    public void checkTaxAmountWithPayPalDiscountTest() {
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
        assertEquals(paymentPage.calculatePercentBasedTax(20), paymentPage.getTaxFromUI());
        paymentPage.applyDiscount(MagentoData.FIVE_PERCENT_ORDER.data);
        assertEquals(paymentPage.calculatePercentBasedTax(20), paymentPage.getTaxFromUI());
        magentoSubtotal = paymentPage.getGrandTotalFromUI();
        assertTrue(paymentPage.verifyDetailsOnPaypalAndPayOrderWithPaypal(false, customerName,
                magentoSubtotal, Address.Paris.addressLine1, Address.Paris.city, Address.Paris.zip5));

        // Get Order ID.
        thankYouPage = new M2StorefrontThankYouPage(driver);
        assertFalse(thankYouPage.getOrderNumber().isEmpty());
    }

    /**
     * CMTAP-65 CMTAP - Test Case - Validate order placing for Virtual Product with PayPal payment
     */
    @Test(groups = {"magentoTap_regression"})
    public void checkTaxAmountWithPayPalVirtualProductTest() {
        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        loginPage = homePage.clickSignInButton();
        loginPage.signInToMagentoTap244Store(MagentoData.MAGENTO_244_STORE_AUTOMATION_USER.data, MagentoData.MAGENTO_244_STORE_AUTOMATION_PASSWORD.data);
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.QA_VIRTUAL_PRODUCT.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.QA_VIRTUAL_PRODUCT.data);
        storefrontBannerPage.proceedToCheckout();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        assertFalse(paymentPage.isTaxFieldVisibleOnUI());
        assertFalse(paymentPage.isShippingFieldVisibleOnUI());
        magentoSubtotal = paymentPage.getGrandTotalFromUI();
        assertTrue(paymentPage.verifyDetailsOnPaypalAndPayOrderWithPaypal(true, customerName,
                magentoSubtotal, Address.Paris.addressLine1, Address.Paris.city, Address.Paris.zip5));

        // Get Order ID.
        thankYouPage = new M2StorefrontThankYouPage(driver);
        assertFalse(thankYouPage.getOrderNumber().isEmpty());
    }

    /**
     * CMTAP-68 - CMTAP - Test Case - Validate order placing for Virtual + Physical Products with PayPal payment - Subtotal >= €150
     */
    @Test(groups = {"magentoTap_integration"})
    public void validateShippingAndTaxForVirtualAndPhysicalProdGreaterThan150Test() {
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
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.QA_VIRTUAL_PRODUCT.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.QA_VIRTUAL_PRODUCT.data);
        storefrontBannerPage.proceedToCheckout();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        magentoSubtotal = paymentPage.getGrandTotalFromUI();
        cartSubtotal = paymentPage.getSubTotalFromUI();
        assertFalse(paymentPage.isTaxFieldVisibleOnUI());
        assertFalse(paymentPage.isShippingFieldVisibleOnUI());
        assertEquals(cartSubtotal, magentoSubtotal);

        // Navigate to storefront home page & add virtual product
        storefrontBannerPage.clickOnStoreLogo();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.QA_COSTLY_PHYSICAL_PRODUCT.data);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.QA_COSTLY_PHYSICAL_PRODUCT.data);
        storefrontBannerPage.proceedToCheckout();

        // Set shipping
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(true, false, MagentoData.AT_SHIPPING_STREET0.data,
                MagentoData.AT_SHIPPING_COUNTRY.data, MagentoData.AT_SHIPPING_STATE.data,
                MagentoData.AT_SHIPPING_CITY.data, MagentoData.AT_SHIPPING_ZIP.data);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Pay for the order, Validate the Tax & Place order
        magentoSubtotal = paymentPage.getGrandTotalFromUI();
        assertFalse(paymentPage.isTaxFieldVisibleOnUI());
        assertTrue(paymentPage.isShippingFieldVisibleOnUI());
        assertTrue(paymentPage.getSubTotalFromUI() >= 150);
        assertTrue(paymentPage.verifyDetailsOnPaypalAndPayOrderWithPaypal(false, customerName, magentoSubtotal,
                MagentoData.AT_SHIPPING_STREET0.data, MagentoData.AT_SHIPPING_CITY.data, MagentoData.AT_SHIPPING_ZIP.data));

        // Get Order ID.
        thankYouPage = new M2StorefrontThankYouPage(driver);
        assertFalse(thankYouPage.getOrderNumber().isEmpty());
    }

    /**
     * CMTAP-69 - CMTAP - Test Case - Validate order placing for Virtual + Physical Products with PayPal payment - Subtotal < €150
     */
    @Test(groups = {"magentoTap_integration"})
    public void validateShippingAndTaxForVirtualAndPhysicalProdLessThan150Test() {
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

        // Validate tax for Physical product & save the tax amount
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        actualTax = paymentPage.getTaxFromUI();
        magentoSubtotal = paymentPage.getGrandTotalFromUI();
        cartSubtotal = paymentPage.getSubTotalFromUI();
        assertEquals(paymentPage.calculatePercentBasedTax(20), actualTax);
        assertTrue(cartSubtotal < 150);

        // Navigate to storefront home page & add virtual product
        storefrontBannerPage.clickOnStoreLogo();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.QA_VIRTUAL_PRODUCT.data);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.QA_VIRTUAL_PRODUCT.data);
        storefrontBannerPage.proceedToCheckout();

        // Select ship method & go next
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();

        // Pay for the order, Validate the Tax & Place order
        magentoSubtotal = paymentPage.getGrandTotalFromUI();
        assertEquals(actualTax, paymentPage.getTaxFromUI());
        assertTrue(paymentPage.getSubTotalFromUI() < 150);
        assertTrue(paymentPage.verifyDetailsOnPaypalAndPayOrderWithPaypal(false, customerName, magentoSubtotal,
                MagentoData.AT_SHIPPING_STREET0.data, MagentoData.AT_SHIPPING_CITY.data, MagentoData.AT_SHIPPING_ZIP.data));

        // Get Order ID.
        thankYouPage = new M2StorefrontThankYouPage(driver);
        assertFalse(thankYouPage.getOrderNumber().isEmpty());
    }
}