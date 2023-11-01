package com.vertex.quality.connectors.orocommerce.tests.storeFront_v4_1;

import com.vertex.quality.connectors.orocommerce.enums.OroAddresses;
import com.vertex.quality.connectors.orocommerce.pages.storefront.OroCheckoutPage;
import com.vertex.quality.connectors.orocommerce.pages.storefront.OroProductSearchResultsPage;
import com.vertex.quality.connectors.orocommerce.pages.storefront.OroShoppingListPage;
import com.vertex.quality.connectors.orocommerce.pages.storefront.OroStoreFrontHomePage;
import com.vertex.quality.connectors.orocommerce.tests.base.OroStoreFrontBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * @author alewis
 */
public class OroStoreInvoiceOrdersTests extends OroStoreFrontBaseTest {

    /**
     * COROCOM-535
     * Create an Invoice Only and validate the tax calculation
     */
    @Test(groups = {"oroCommerce", "WedTests"})
    public void californiaInvoiceTest() {
        String expectedTaxAmount = "$0.00";
        String productName = "Men’s Basic V-neck, 1-Pocket Light Blue Scrub Top";
        OroStoreFrontHomePage homePage = signInToStoreFront(testStartPage);
        OroStoreFrontHomePage homePage1 = homePage.startNewOrder();
        OroProductSearchResultsPage searchResultsPage = homePage1.headerMiddleBar.searchForProduct(productName);
        searchResultsPage.selectProductFromList(productName);
        OroShoppingListPage shoppingListPage = searchResultsPage.goToShoppingListPage();
        OroCheckoutPage checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_CA_LOS_ANGELES);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        String actualTaxAmount = checkoutPage.orderSummary.getTaxAmount();
        boolean isTaxCorrect = expectedTaxAmount.equals(actualTaxAmount);
        assertTrue(isTaxCorrect);
    }

    /**
     * COROCOM-554
     */
    @Test(groups = {"oroCommerce", "WedTests"})
    public void changeLocationInvoiceTest() {
        String expectedTaxAmount = "$0.00";
        String productName = "Men’s Basic V-neck, 1-Pocket Light Blue Scrub Top";
        OroStoreFrontHomePage homePage = signInToStoreFront(testStartPage);
        OroStoreFrontHomePage homePage1 = homePage.startNewOrder();
        OroProductSearchResultsPage searchResultsPage = homePage1.headerMiddleBar.searchForProduct(productName);
        searchResultsPage.selectProductFromList(productName);
        OroShoppingListPage shoppingListPage = searchResultsPage.goToShoppingListPage();
        OroCheckoutPage checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_CA_LOS_ANGELES);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        String actualTaxAmount = checkoutPage.orderSummary.getTaxAmount();
        boolean isTaxCorrect = expectedTaxAmount.equals(actualTaxAmount);
        assertTrue(isTaxCorrect);

    }
}
