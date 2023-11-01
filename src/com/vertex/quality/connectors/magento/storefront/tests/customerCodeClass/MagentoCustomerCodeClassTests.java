package com.vertex.quality.connectors.magento.storefront.tests.customerCodeClass;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminHomepage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminNewInvoicePage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminOrderViewInfoPage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminOrdersPage;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import com.vertex.quality.connectors.magento.storefront.pages.*;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;

/**
 * CCMMER2-442 Magento O-Series - Test Automation - Storefront - Part II
 * Magento M2 Storefront - Customer Code & Customer Class tests
 *
 * @author Shivam.Soni
 */
public class MagentoCustomerCodeClassTests extends MagentoAdminBaseTest {

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
    String orderNo;

    /**
     * CMAGM2-1254 CMAG - Test Case - Create Sales order with Customer Class Exemption
     */
    @Test(groups = {"magento_regression"})
    public void checkCustomerClassExemptionTest() {
        setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);
        adminHomepage = new M2AdminHomepage(driver);
        adminHomepage.signOutFromAdminPanel();

        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        loginPage = homePage.clickSignInButton();
        loginPage.signInToMagentoTap244Store(MagentoData.MAGENTO_2_0_CUSTOMER_CLASS.data, MagentoData.MAGENTO_2_0_CUSTOMER_CODE_CLASS_PW.data);
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
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
        paymentPage.setSameBillingShippingAddress();
        assertFalse(paymentPage.isTaxLabelPresent());

        // Save order number
        thankYouPage = paymentPage.clickPlaceOrderButton();
        orderNo = thankYouPage.getOrderNumber();
        assertFalse(orderNo.isEmpty());

        // Sign out of Storefront
        storefrontBannerPage.signOutFromStore();

        // Login to Admin panel, Search for Order number & Submit Invoice
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }

    /**
     * CMAGM2-1255 CMAG - Test Case - Create Sales order with Customer Code Exemption
     */
    @Test(groups = {"magento_regression"})
    public void checkCustomerCodeExemptionTest() {
        setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);
        adminHomepage = new M2AdminHomepage(driver);
        adminHomepage.signOutFromAdminPanel();

        // Open the store, clear the cart, search product & add product(s) to the cart.
        homePage = openStorefrontHomepage();
        loginPage = homePage.clickSignInButton();
        loginPage.signInToMagentoTap244Store(MagentoData.MAGENTO_2_0_CUSTOMER_CODE.data, MagentoData.MAGENTO_2_0_CUSTOMER_CODE_CLASS_PW.data);
        storefrontBannerPage = new M2StorefrontBannerPage(driver);
        storefrontBannerPage.clearTheCart();
        storefrontBannerPage.searchFromEntireStoreSearch(MagentoData.FUSION_BACKPACK.data);
        searchResultPage = new M2StorefrontSearchResultPage(driver);
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
        paymentPage.setSameBillingShippingAddress();
        assertFalse(paymentPage.isTaxLabelPresent());

        // Save order number
        thankYouPage = paymentPage.clickPlaceOrderButton();
        orderNo = thankYouPage.getOrderNumber();
        assertFalse(orderNo.isEmpty());

        // Sign out of Storefront
        storefrontBannerPage.signOutFromStore();

        // Login to Admin panel, Search for Order number & Submit Invoice
        ordersPage = navigateToOrders();
        orderInfoPage = ordersPage.clickOrder(orderNo);
        invoicePage = orderInfoPage.clickInvoiceButton();
        invoicePage.clickSubmitInvoiceButton();
    }
}
