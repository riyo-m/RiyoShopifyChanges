package com.vertex.quality.connectors.magento.storefront.tests.refunds;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.magento.admin.pages.*;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import com.vertex.quality.connectors.magento.storefront.pages.*;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * CCMMER2-735 Magento O-Series - Test Automation - Storefront - Part V
 * Magento M2 Storefront - Refund/ Adjustment tests
 *
 * @author Shivam.Soni
 */
public class MagentoRefundTests extends MagentoAdminBaseTest {

    M2AdminHomepage adminHomepage;
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
    M2AdminOrderViewInfoPage newViewInfoPage;

    double calculatedTaxAmount;
    double grandTotalAmount;
    String orderNo;

    /**
     * CMAGM2-1333 CMAG - Test Case - Return Sales order - full order with shipping
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
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(true, false, MagentoData.PR_SHIPPING_STREET0.data,
                MagentoData.PR_SHIPPING_COUNTRY.data, MagentoData.PR_SHIPPING_STATE.data,
                MagentoData.PR_SHIPPING_CITY.data, MagentoData.PR_SHIPPING_ZIP.data);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();
        shippingPage.ignoreCleansedAddress();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        calculatedTaxAmount = paymentPage.getTaxFromUI();
        grandTotalAmount = paymentPage.getGrandTotalFromUI();
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculateIndividualPercentBasedTax(true, true, 11.5));
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();
        storefrontBannerPage.signOutFromStore();

        // Login to Admin panel, Submit Invoice & Process refund
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
        creditMemoPage = navigateToNewMemoPageWithoutInvoice();
        creditMemoPage.clickRefundOfflineButton();

        creditMemoPage = new M2AdminCreditMemoPage(driver);
        assertEquals(orderInfoPage.getRefundedTaxFromUI(), calculatedTaxAmount);
        assertEquals(orderInfoPage.getRefundedAmountFromUI(), grandTotalAmount);
    }

    /**
     * CMAGM2-1330 CMAG - Test Case - Return Sales order - partial amount returned
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxForRefundPartialAmountTest() {
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
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

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
        calculatedTaxAmount = paymentPage.getTaxFromUI();
        grandTotalAmount = paymentPage.getGrandTotalFromUI();
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculateIndividualPercentBasedTax(true, true, 9.5));
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();
        storefrontBannerPage.signOutFromStore();

        // Login to Admin panel, Submit Invoice & Process refund
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
        creditMemoPage = navigateToNewMemoPageWithoutInvoice();
        creditMemoPage.removeShippingFromRefund();
        newViewInfoPage = creditMemoPage.enterAdjustedFee(MagentoData.PARTIAL_RETURN_AMOUNT.data);
        creditMemoPage.clickRefundOfflineButton();

        assertEquals(newViewInfoPage.getRefundedTaxFromUI(), calculatedTaxAmount);
    }

    /**
     * CMAGM2-1334 CMAG - Test Case - Return Sale Order with Shipping for VAT (Intra EU DE-DE) and Invoice
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxForRefundPartialForVATWithShippingTest() {
        // Set ship from address
        setShippingOrigin(Address.Berlin.country.fullName, Address.Berlin.city, Address.Berlin.zip5, Address.Berlin.city, Address.Berlin.addressLine1);
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
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
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
        calculatedTaxAmount = paymentPage.getTaxFromUI();
        grandTotalAmount = paymentPage.getGrandTotalFromUI();
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculateIndividualPercentBasedTax(true, true, 19));
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();
        storefrontBannerPage.signOutFromStore();

        // Login to Admin panel, Submit Invoice & Process refund
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
        creditMemoPage = navigateToNewMemoPageWithoutInvoice();
        creditMemoPage.removeShippingFromRefund();
        newViewInfoPage = creditMemoPage.addAdjustmentRefund(MagentoData.quantity_one.data, null, null, MagentoData.FULL_RETURN_AMOUNT.data);
        assertEquals(creditMemoPage.getTaxFromUI(), creditMemoPage.calculatePercentBasedTax(19));
        creditMemoPage.clickRefundOfflineButton();
        assertEquals(newViewInfoPage.getRefundedTaxFromUI(), calculatedTaxAmount);
    }

    /**
     * CMAGM2-1331 CMAG - Test Case - Return Sales order - partial quantity returned
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxForRefundPartialQuantityTest() {
        // Set ship from address
        setShippingOrigin(Address.Edison.country.fullName, Address.Edison.state.fullName, Address.Edison.zip5, Address.Edison.city, Address.Edison.addressLine1);
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
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(true, false, Address.Dallas.addressLine1,
                Address.Dallas.country.fullName, Address.Dallas.state.fullName,
                Address.Dallas.city, Address.Dallas.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();
        shippingPage.ignoreCleansedAddress();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        calculatedTaxAmount = paymentPage.getTaxFromUI();
        grandTotalAmount = paymentPage.getGrandTotalFromUI();
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculateIndividualPercentBasedTax(true, true, 8.25));
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();
        storefrontBannerPage.signOutFromStore();

        // Login to Admin panel, Submit Invoice & Process refund
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
        creditMemoPage = navigateToNewMemoPageWithoutInvoice();
        creditMemoPage.removeShippingFromRefund();
        newViewInfoPage = creditMemoPage.addAdjustmentRefund(MagentoData.quantity_one.data, null, null, MagentoData.FULL_RETURN_AMOUNT.data);
        assertEquals(creditMemoPage.getTaxFromUI(), creditMemoPage.calculatePercentBasedTax(8.25));
        creditMemoPage.clickRefundOfflineButton();
        assertEquals(newViewInfoPage.getRefundedTaxFromUI(), calculatedTaxAmount);
    }

    /**
     * CMAGM2-1332 CMAG - Test Case - Return Sales order - partial order with shipping
     */
    @Test(groups = {"magento_regression"})
    public void checkTaxForRefundPartialWithShippingTest() {
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
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage.loadMaximumRecords();
        searchResultPage.addProductToTheCart(MagentoData.FUSION_BACKPACK.data);
        storefrontBannerPage.proceedToCheckout();

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
        calculatedTaxAmount = paymentPage.getTaxFromUI();
        grandTotalAmount = paymentPage.getGrandTotalFromUI();
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculateIndividualPercentBasedTax(true, true, 9.5));
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();
        storefrontBannerPage.signOutFromStore();

        // Login to Admin panel, Submit Invoice & Process refund
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
        creditMemoPage = navigateToNewMemoPageWithoutInvoice();
        newViewInfoPage = creditMemoPage.enterAdjustedFee(MagentoData.PARTIAL_RETURN_AMOUNT.data);
        creditMemoPage.clickRefundOfflineButton();

        assertEquals(newViewInfoPage.getRefundedTaxFromUI(), calculatedTaxAmount);
        assertEquals(newViewInfoPage.getRefundedAmountFromUI(), grandTotalAmount - Double.parseDouble(MagentoData.PARTIAL_RETURN_AMOUNT.data));
    }
}
