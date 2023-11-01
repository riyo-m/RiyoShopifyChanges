package com.vertex.quality.connectors.magentoTap.admin.tests.integrationTests;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.magentoTap.admin.pages.M2AdminConfigPage;
import com.vertex.quality.connectors.magentoTap.admin.pages.M2AdminSalesTaxConfigPage;
import com.vertex.quality.connectors.magentoTap.admin.pages.M2AdminShippingSettingsPage;
import com.vertex.quality.connectors.magentoTap.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magentoTap.common.enums.MagentoData;
import com.vertex.quality.connectors.magentoTap.storefront.pages.*;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.*;
import static org.testng.AssertJUnit.assertEquals;

/**
 * MagentoTap tests that are added specific to particular features & that were manual & now converted to Automated
 * CCMMER2-72
 *
 * @author Shivam.Soni
 */
public class MagentoTapIntegrationTests extends MagentoAdminBaseTest {

    M2AdminSalesTaxConfigPage taxSettingsPage;
    M2AdminConfigPage configPage;
    M2AdminShippingSettingsPage shippingSettings;
    M2StorefrontLoginPage loginPage;
    M2StorefrontHomePage homePage;
    M2StorefrontBannerPage storefrontBannerPage;
    M2StorefrontSearchResultPage searchResultPage;
    M2StorefrontShippingInfoPage shippingPage;
    M2StorefrontPaymentMethodPage paymentPage;
    M2StorefrontThankYouPage thankYouPage;

    List<String> expectedCountries;
    List<String> actualCountries;
    String orderNo;
    double actualTax;
    double cartSubtotal;
    double orderTotal;

    /**
     * CMTAP-14 CMTAP - Test Case - Validate EU countries as Tax eligible countries
     */
    @Test(groups = {"magentoTap_integration"})
    public void validateOnlyEUTaxEnabledCountriesTest() {
        taxSettingsPage = navigateToSalesTaxConfig();
        taxSettingsPage.clickOnTaxamoTab();
        expectedCountries = new ArrayList<>();
        expectedCountries.addAll(Arrays.asList(MagentoData.EU_COUNTRIES.countries));
        actualCountries = taxSettingsPage.getAllEUCountriesName();
        Collections.sort(expectedCountries);
        Collections.sort(actualCountries);
        assertEquals(expectedCountries, actualCountries);
    }

    /**
     * CMTAP-23 CMTAP - Test Case - Validate & test API token with Invalid Token
     */
    @Test(groups = {"magentoTap_integration"})
    public void validateTestAPITokenWithInvalidTokenTest() {
        try {
            taxSettingsPage = navigateToSalesTaxConfig();
            taxSettingsPage.clickOnTaxamoTab();
            taxSettingsPage.enterAPIToken(MagentoData.INVALID_API_TOKEN.data);
            taxSettingsPage.clickSaveAndTestCredentialsButton();
            assertEquals(taxSettingsPage.getHealthCheckMSGFromUI(), MagentoData.INVALID_HEALTH_CHECK_MSG.data);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed due to Error/ Exception, Kindly check the logs.");
        } finally {
            quitDriver();
            createChromeDriver();

            taxSettingsPage = navigateToSalesTaxConfig();
            taxSettingsPage.clickOnTaxamoTab();
            taxSettingsPage.enterAPIToken(MagentoData.VALID_API_TOKEN.data);
            taxSettingsPage.clickSaveAndTestCredentialsButton();
        }
    }

    /**
     * CMTAP-22 CMTAP - Test Case - Validate & test API token with Valid Token
     */
    @Test(groups = {"magentoTap_integration"})
    public void validateTestAPITokenWithValidTokenTest() {
        taxSettingsPage = navigateToSalesTaxConfig();
        taxSettingsPage.clickOnTaxamoTab();
        taxSettingsPage.enterAPIToken(MagentoData.VALID_API_TOKEN.data);
        taxSettingsPage.clickSaveAndTestCredentialsButton();

        assertEquals(taxSettingsPage.getHealthCheckMSGFromUI(), MagentoData.VALID_HEALTH_CHECK_MSG.data);
    }

    /**
     * CMTAP-24 CMTAP - Test Case - Validate order breakup including tax with guest user
     */
    @Test(groups = {"magentoTap_integration"})
    public void validateBreakupAndTaxWithGuestUserTest() {
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
     * CMTAP-31 - CMTAP - Test Case - Validate Shipping and tax amount for only Virtual products in the same basket.
     */
    @Test(groups = {"magentoTap_integration"})
    public void validateTaxAndShippingOnlyForVirtualProductTest() {
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
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.QA_VIRTUAL_PRODUCT.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.QA_VIRTUAL_PRODUCT.data);
        storefrontBannerPage.proceedToCheckout();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        assertFalse(paymentPage.isTaxFieldVisibleOnUI());
        assertFalse(paymentPage.isShippingFieldVisibleOnUI());
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();
    }

    /**
     * CMTAP-29 - CMTAP - Test Case - Validate Shipping and tax amount for only Physical products in the same basket (physical product price < €150)
     */
    @Test(groups = {"magentoTap_integration"})
    public void validateShippingAndTaxForOnlyPhysicalProdLessThan150Test() {
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
        assertTrue(paymentPage.getSubTotalFromUI() < 150);
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();
    }

    /**
     * CMTAP-30 - CMTAP - Test Case - Validate Shipping and tax amount for only Physical products in the same basket (physical product price >= €150)
     */
    @Test(groups = {"magentoTap_integration"})
    public void validateShippingAndTaxForOnlyPhysicalProdGreaterThan150Test() {
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
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.QA_COSTLY_PHYSICAL_PRODUCT.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.QA_COSTLY_PHYSICAL_PRODUCT.data);
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
        assertFalse(paymentPage.isTaxFieldVisibleOnUI());
        assertTrue(paymentPage.isShippingFieldVisibleOnUI());
        assertTrue(paymentPage.getSubTotalFromUI() >= 150);
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();
    }

    /**
     * CMTAP-28 - CMTAP - Test Case - Validate Shipping and tax amount for Physical & Virtual products in the same basket. (physical product price >= €150)
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
        orderTotal = paymentPage.getGrandTotalFromUI();
        cartSubtotal = paymentPage.getSubTotalFromUI();
        assertFalse(paymentPage.isTaxFieldVisibleOnUI());
        assertFalse(paymentPage.isShippingFieldVisibleOnUI());
        assertEquals(cartSubtotal, orderTotal);

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
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        assertTrue(paymentPage.getGrandTotalFromUI() > orderTotal);
        assertTrue(paymentPage.getSubTotalFromUI() > cartSubtotal);
        assertFalse(paymentPage.isTaxFieldVisibleOnUI());
        assertTrue(paymentPage.isShippingFieldVisibleOnUI());
        assertTrue(paymentPage.getSubTotalFromUI() >= 150);
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();
    }

    /**
     * CMTAP-27 - CMTAP - Test Case - Validate Shipping and tax amount for Physical & Virtual products in the same basket (physical product price < €150)
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
        orderTotal = paymentPage.getGrandTotalFromUI();
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
        paymentPage.selectCheckMoneyOrderPaymentMethod();
        assertTrue(paymentPage.getGrandTotalFromUI() > orderTotal);
        assertTrue(paymentPage.getSubTotalFromUI() > cartSubtotal);
        assertEquals(actualTax, paymentPage.getTaxFromUI());
        assertTrue(paymentPage.getSubTotalFromUI() < 150);
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();
    }
}
