package com.vertex.quality.connectors.magento.storefront.tests.coloradoTests;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.magento.admin.pages.*;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import com.vertex.quality.connectors.magento.storefront.pages.*;
import org.testng.annotations.Test;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import static com.vertex.quality.connectors.magento.common.enums.MagentoCOAddresses.DENVER_ADDRESS;
import static com.vertex.quality.connectors.magento.common.enums.MagentoCOAddresses.ELIZABETH_ADDRESS;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Magento2 Tests for round off issue
 * CCMMER2-350 Magento O-Series - Automation testing - new functionality (round off & CO fee Decomposition)
 *
 * @author Shivam.Soni
 */
public class M2ColoradoTests extends MagentoAdminBaseTest {

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

    /**
     * CMAGM2-1207 CMAG - Test Case - Validate Taxation & Retail Delivery Fee for Colorado for Estimate
     */
    @Test(groups = {"magento_regression"})
    public void checkRetailDeliveryCOQuotationTest() {
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
        shippingPage.enterShippingDetails(true, false, ELIZABETH_ADDRESS.addressLine1,
                ELIZABETH_ADDRESS.country.fullName, ELIZABETH_ADDRESS.state.fullName,
                ELIZABETH_ADDRESS.city, ELIZABETH_ADDRESS.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();
        shippingPage.ignoreCleansedAddress();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.setSameBillingShippingAddress();
        assertTrue(paymentPage.verifyRetailDeliveryFeeForColorado());
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculatePercentBasedTaxForColorado(7.94, 0));
        thankYouPage = paymentPage.clickPlaceOrderButton();
    }

    /**
     * CMAGM2-1208 CMAG - Test Case - Validate Taxation & Retail Delivery Fee for Colorado for Invoice
     */
    @Test(groups = {"magento_regression"})
    public void checkRetailDeliveryCOInvoiceTest() {
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
        shippingPage.enterShippingDetails(true, false, Address.Colorado.addressLine1,
                Address.Colorado.country.fullName, Address.Colorado.state.fullName,
                Address.Colorado.city, Address.Colorado.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();
        shippingPage.ignoreCleansedAddress();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.setSameBillingShippingAddress();
        assertTrue(paymentPage.verifyRetailDeliveryFeeForColorado());
        assertEquals(paymentPage.getTaxFromUI(), paymentPage.calculatePercentBasedTaxForColorado(5, 0));
        thankYouPage = paymentPage.clickPlaceOrderButton();

        // Get Order ID.
        orderNo = thankYouPage.getOrderNumber();
        storefrontBannerPage.signOutFromStore();

        // Login to Admin panel, Submit Invoice & Process refund
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMAGM2-1209 CMAG - Test Case - Validate Taxation & Retail Delivery Fee for Colorado for Full Refund
     */
    @Test(groups = {"magento_regression"})
    public void checkRetailDeliveryCOFullRefundTest() {
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
        cartPage = storefrontBannerPage.clickViewAndEditCart();
        cartPage.updateItemQty(MagentoData.FUSION_BACKPACK.data, MagentoData.quantity_two.data);
        cartPage.clickProceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(true, false, DENVER_ADDRESS.addressLine1,
                DENVER_ADDRESS.country.fullName, DENVER_ADDRESS.state.fullName,
                DENVER_ADDRESS.city, DENVER_ADDRESS.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();
        shippingPage.ignoreCleansedAddress();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.setSameBillingShippingAddress();
        assertTrue(paymentPage.verifyRetailDeliveryFeeForColorado());
        calculatedTaxAmount = paymentPage.getTaxFromUI();
        grandTotalAmount = paymentPage.getGrandTotalFromUI();
        assertEquals(calculatedTaxAmount, paymentPage.calculatePercentBasedTaxForColorado(12.07, 5.4));
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

    /**
     * CMAGM2-1210 CMAG - Test Case - Validate Taxation & Retail Delivery Fee for Colorado for Partial Refund
     */
    @Test(groups = {"magento_regression"})
    public void checkRetailDeliveryCOPartialRefundTest() {
        double shippingUI;
        double refundShipping;

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
        cartPage = storefrontBannerPage.clickViewAndEditCart();
        cartPage.updateItemQty(MagentoData.FUSION_BACKPACK.data, MagentoData.quantity_two.data);
        cartPage.clickProceedToCheckout();

        // Set ship to address
        shippingPage = new M2StorefrontShippingInfoPage(driver);
        shippingPage.enterShippingDetails(true, false, ELIZABETH_ADDRESS.addressLine1,
                ELIZABETH_ADDRESS.country.fullName, ELIZABETH_ADDRESS.state.fullName,
                ELIZABETH_ADDRESS.city, ELIZABETH_ADDRESS.zip5);
        shippingPage.selectFlatRateForShippingMethod();
        shippingPage.clickNextButton();
        shippingPage.ignoreCleansedAddress();

        // Pay for the order, Validate the Tax & Place order
        paymentPage = new M2StorefrontPaymentMethodPage(driver);
        paymentPage.setSameBillingShippingAddress();
        assertTrue(paymentPage.verifyRetailDeliveryFeeForColorado());
        shippingUI = paymentPage.getShippingFromUI();
        calculatedTaxAmount = paymentPage.getTaxFromUI();
        grandTotalAmount = paymentPage.getGrandTotalFromUI();
        assertEquals(calculatedTaxAmount, paymentPage.calculatePercentBasedTaxForColorado(7.94, 0));
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
        creditMemoPage.addAdjustmentRefund(MagentoData.quantity_one.data, null, null, "");
        refundShipping = shippingUI / Double.parseDouble(MagentoData.quantity_two.data);
        creditMemoPage.enterRefundShipping(String.valueOf(refundShipping));
        creditMemoPage.clickUpdateTotals();
        orderInfoPage = creditMemoPage.clickRefundOfflineButton();

        assertEquals(orderInfoPage.getRefundedTaxFromUI(), calculatedTaxAmount);
        double number = grandTotalAmount / Double.parseDouble(MagentoData.quantity_two.data);
        DecimalFormat df = new DecimalFormat("###.##");
        df.setRoundingMode(RoundingMode.FLOOR);
        assertEquals(orderInfoPage.getRefundedAmountFromUI(), Double.parseDouble(df.format(number)));
    }
}
