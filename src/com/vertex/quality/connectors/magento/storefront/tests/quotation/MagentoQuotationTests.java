package com.vertex.quality.connectors.magento.storefront.tests.quotation;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.enums.TaxRate;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminHomepage;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import com.vertex.quality.connectors.magento.storefront.pages.*;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

/**
 * CCMMER2-441 Magento O-Series - Test Automation - Storefront - Part I
 *
 * @author Shivam.Soni
 */
public class MagentoQuotationTests extends MagentoAdminBaseTest {

    M2AdminHomepage adminHomepage;
    M2StorefrontLoginPage loginPage;
    M2StorefrontHomePage homePage;
    M2StorefrontBannerPage storefrontBannerPage;
    M2StorefrontSearchResultPage searchResultPage;
    M2StorefrontShippingInfoPage shippingPage;
    M2StorefrontPaymentMethodPage paymentPage;
    M2StorefrontThankYouPage thankYouPage;

    /**
     * CMAGM2-1214 CMAGM2 - Test Case -Create Sales Order with no State Tax
     */
    @Test(groups = {"magento_regression", "magento_smoke"})
    public void salesOrderWithNoStateTaxTest() {
        // Set ship from address
        setShippingOrigin(Address.Colorado.country.fullName, Address.Colorado.state.fullName, Address.Colorado.zip5, Address.Colorado.city, Address.Colorado.addressLine1);
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
        paymentPage.setSameBillingShippingAddress();
        assertFalse(paymentPage.isTaxLabelPresent());
        thankYouPage = paymentPage.clickPlaceOrderButton();
    }

    /**
     * CMAGM2-1215 CMAGM2 - Test Case -Create Sales Order with Modified Origin State
     */
    @Test(groups = {"magento_regression"})
    public void salesOrderWithModifiedOriginStateTest() {
        // Set ship from address
        setShippingOrigin(Address.LosAngeles.country.fullName, Address.LosAngeles.state.fullName, Address.LosAngeles.zip5, Address.LosAngeles.city, Address.LosAngeles.addressLine1);
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
        shippingPage.enterShippingDetails(true, false, Address.UniversalCity.addressLine1,
                Address.UniversalCity.country.fullName, Address.UniversalCity.state.fullName,
                Address.UniversalCity.city, Address.UniversalCity.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();
        shippingPage.ignoreCleansedAddress();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.setSameBillingShippingAddress();
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculateIndividualPercentBasedTax(true, true, TaxRate.UniversalCity.tax));
        thankYouPage = paymentPage.clickPlaceOrderButton();
    }

    /**
     * CMAGM2-1216 CMAGM2 - Test Case -Create Sales Order with no State Tax, locally administered
     */
    @Test(groups = {"magento_regression", "magento_smoke"})
    public void salesOrderWithNoStateTaxLocallyAdministeredTest () {
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
        shippingPage.enterShippingDetails(true, false, Address.Juneau.addressLine1,
                Address.Juneau.country.fullName, Address.Juneau.state.fullName,
                Address.Juneau.city, Address.Juneau.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();
        shippingPage.ignoreCleansedAddress();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.setSameBillingShippingAddress();
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculatePercentBasedTax(5));
        thankYouPage = paymentPage.clickPlaceOrderButton();
    }
}
