package com.vertex.quality.connectors.magento.storefront.tests.roundOffTests;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.enums.TaxRate;
import com.vertex.quality.connectors.magento.admin.pages.*;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import com.vertex.quality.connectors.magento.storefront.pages.*;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Magento2 Tests for round off issue
 * CCMMER2-287 Magento: Fix - Rounding issues Order of operations
 * CCMMER2-350 Magento O-Series - Automation testing - new functionality (round off & CO fee Decomposition)
 *
 * @author Shivam.Soni
 */
public class M2RoundOffTests extends MagentoAdminBaseTest {

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
    M2AdminCreditMemoPage creditMemoPage;

    double calculatedTaxAmount;
    double grandTotalAmount;
    String orderNo;
    double expectedItemSubtotal;
    double cartItemSubtotal;

    /**
     * CMAGM2-1137 CMAG - Test Case - Validate sales order for rounded off product's price with normal order
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxInRoundOffPriceTest() {
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
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.QA_ROUND_OFF_PRODUCT.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.QA_ROUND_OFF_PRODUCT.data);
        cartPage = storefrontBannerPage.clickViewAndEditCart();
        cartPage.updateItemQty(MagentoData.QA_ROUND_OFF_PRODUCT.data, MagentoData.quantity_twelve.data);
        expectedItemSubtotal = cartPage.getItemBasePrice(MagentoData.QA_ROUND_OFF_PRODUCT.data) * Double.parseDouble(MagentoData.quantity_twelve.data);
        cartItemSubtotal = cartPage.getItemSubtotal(MagentoData.QA_ROUND_OFF_PRODUCT.data);
        assertEquals(expectedItemSubtotal, cartItemSubtotal);
        cartPage.clickProceedToCheckout();

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
        assertEquals(cartItemSubtotal, paymentPage.getSubTotalFromUI());
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculatePercentBasedTax(6));
        thankYouPage = paymentPage.clickPlaceOrderButton();
    }

    /**
     * CMAGM2-1142 CMAG - Test Case - Validate sales order for rounded-off product's price with normal order - Ship to US
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxInRoundOffPriceUStoUSTest() {
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
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.QA_ROUND_OFF_PRODUCT.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.QA_ROUND_OFF_PRODUCT.data);
        cartPage = storefrontBannerPage.clickViewAndEditCart();
        cartPage.updateItemQty(MagentoData.QA_ROUND_OFF_PRODUCT.data, MagentoData.quantity_twelve.data);
        expectedItemSubtotal = cartPage.getItemBasePrice(MagentoData.QA_ROUND_OFF_PRODUCT.data) * Double.parseDouble(MagentoData.quantity_twelve.data);
        cartItemSubtotal = cartPage.getItemSubtotal(MagentoData.QA_ROUND_OFF_PRODUCT.data);
        assertEquals(expectedItemSubtotal, cartItemSubtotal);
        cartPage.clickProceedToCheckout();

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
        assertEquals(cartItemSubtotal, paymentPage.getSubTotalFromUI());
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculateIndividualPercentBasedTax(true, true, TaxRate.LosAngeles.tax));
        thankYouPage = paymentPage.clickPlaceOrderButton();
    }

    /**
     * CMAGM2-1141 CMAG - Test Case - Validate sales order for rounded-off product's price with normal order - Ship to CAN
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxInRoundOffPriceUStoCATest() {
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
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.QA_ROUND_OFF_PRODUCT.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.QA_ROUND_OFF_PRODUCT.data);
        cartPage = storefrontBannerPage.clickViewAndEditCart();
        cartPage.updateItemQty(MagentoData.QA_ROUND_OFF_PRODUCT.data, MagentoData.quantity_twelve.data);
        expectedItemSubtotal = cartPage.getItemBasePrice(MagentoData.QA_ROUND_OFF_PRODUCT.data) * Double.parseDouble(MagentoData.quantity_twelve.data);
        cartItemSubtotal = cartPage.getItemSubtotal(MagentoData.QA_ROUND_OFF_PRODUCT.data);
        assertEquals(expectedItemSubtotal, cartItemSubtotal);
        cartPage.clickProceedToCheckout();

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
        assertEquals(cartItemSubtotal, paymentPage.getSubTotalFromUI());
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculatePercentBasedTax(12));
        thankYouPage = paymentPage.clickPlaceOrderButton();
    }

    /**
     * CMAGM2-1140 CMAG - Test Case - Validate sales order for rounded-off product's price with normal order - Ship to DE
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxInRoundOffPriceUStoDETest() {
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
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.QA_ROUND_OFF_PRODUCT.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.QA_ROUND_OFF_PRODUCT.data);
        cartPage = storefrontBannerPage.clickViewAndEditCart();
        cartPage.updateItemQty(MagentoData.QA_ROUND_OFF_PRODUCT.data, MagentoData.quantity_five.data);
        expectedItemSubtotal = cartPage.getItemBasePrice(MagentoData.QA_ROUND_OFF_PRODUCT.data) * Double.parseDouble(MagentoData.quantity_five.data);
        cartItemSubtotal = cartPage.getItemSubtotal(MagentoData.QA_ROUND_OFF_PRODUCT.data);
        assertEquals(expectedItemSubtotal, cartItemSubtotal);
        cartPage.clickProceedToCheckout();

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
        assertEquals(cartItemSubtotal, paymentPage.getSubTotalFromUI());
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculatePercentBasedTax(19));
        thankYouPage = paymentPage.clickPlaceOrderButton();
    }

    /**
     * CMAGM2-1139 CMAG - Test Case - Validate sales order for rounded off product's price with discounted order
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxInRoundOffPriceDiscountedOrderTest() {
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
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.QA_ROUND_OFF_PRODUCT.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.QA_ROUND_OFF_PRODUCT.data);
        cartPage = storefrontBannerPage.clickViewAndEditCart();
        cartPage.updateItemQty(MagentoData.QA_ROUND_OFF_PRODUCT.data, MagentoData.quantity_twelve.data);
        expectedItemSubtotal = cartPage.getItemBasePrice(MagentoData.QA_ROUND_OFF_PRODUCT.data) * Double.parseDouble(MagentoData.quantity_twelve.data);
        cartItemSubtotal = cartPage.getItemSubtotal(MagentoData.QA_ROUND_OFF_PRODUCT.data);
        assertEquals(expectedItemSubtotal, cartItemSubtotal);
        cartPage.clickProceedToCheckout();

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
        assertEquals(cartItemSubtotal, paymentPage.getSubTotalFromUI());
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculateIndividualPercentBasedTax(true, true, TaxRate.UniversalCity.tax));
        paymentPage.applyDiscount(MagentoData.TEN_DOLLAR_ORDER.data);
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculateIndividualPercentBasedTax(true, true, TaxRate.UniversalCity.tax));
        thankYouPage = paymentPage.clickPlaceOrderButton();
    }

    /**
     * CMAGM2-1138 CMAG - Test Case - Validate sales order for rounded off product's price with normal order - return/ refund order
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxInRoundOffPriceRefundedOrderTest() {
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
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.QA_ROUND_OFF_PRODUCT.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.QA_ROUND_OFF_PRODUCT.data);
        cartPage = storefrontBannerPage.clickViewAndEditCart();
        cartPage.updateItemQty(MagentoData.QA_ROUND_OFF_PRODUCT.data, MagentoData.quantity_twelve.data);
        expectedItemSubtotal = cartPage.getItemBasePrice(MagentoData.QA_ROUND_OFF_PRODUCT.data) * Double.parseDouble(MagentoData.quantity_twelve.data);
        cartItemSubtotal = cartPage.getItemSubtotal(MagentoData.QA_ROUND_OFF_PRODUCT.data);
        assertEquals(expectedItemSubtotal, cartItemSubtotal);
        cartPage.clickProceedToCheckout();

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
        assertEquals(cartItemSubtotal, paymentPage.getSubTotalFromUI());
        calculatedTaxAmount = paymentPage.getTaxFromUI();
        grandTotalAmount = paymentPage.getGrandTotalFromUI();
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculateIndividualPercentBasedTax(true, true, TaxRate.UniversalCity.tax));
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();
        storefrontBannerPage.signOutFromStore();

        // Login to Admin panel, Submit Invoice & Process refund
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
        orderInfoPage = submitCreditMemoWithRefund(MagentoData.quantity_twelve.data, null, null, MagentoData.FULL_RETURN_AMOUNT.data);

        creditMemoPage = new M2AdminCreditMemoPage(driver);
        assertEquals(orderInfoPage.getRefundedTaxFromUI(), calculatedTaxAmount);
        assertEquals(orderInfoPage.getRefundedAmountFromUI(), grandTotalAmount);
    }
}
