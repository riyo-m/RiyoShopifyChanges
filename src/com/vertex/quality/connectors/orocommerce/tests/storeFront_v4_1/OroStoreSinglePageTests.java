package com.vertex.quality.connectors.orocommerce.tests.storeFront_v4_1;

import com.vertex.quality.connectors.orocommerce.pages.storefront.OroCheckoutPage;
import com.vertex.quality.connectors.orocommerce.pages.storefront.OroProductSearchResultsPage;
import com.vertex.quality.connectors.orocommerce.pages.storefront.OroShoppingListPage;
import com.vertex.quality.connectors.orocommerce.pages.storefront.OroStoreFrontHomePage;
import com.vertex.quality.connectors.orocommerce.tests.base.OroStoreFrontBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Tests cases for single page checkout workflow
 *
 * @author alewis
 */
public class OroStoreSinglePageTests extends OroStoreFrontBaseTest {

    /**
     * COROCOM-690
     */
    @Test(groups = { "oroCommerce","WedTests" })
    public void singlePageSignOnTest( )
    {
        String expectedTaxAmount = "$2.00";
        String productName = "Physician’s 5-Pocket Lab Coat";
        OroStoreFrontHomePage homePage = signInToStoreFront(testStartPage);
        OroStoreFrontHomePage homePage1 = homePage.startNewOrder();
        OroProductSearchResultsPage searchResultsPage = homePage1.headerMiddleBar.searchForProduct(productName);
        searchResultsPage.selectProductFromList(productName);
        OroShoppingListPage shoppingListPage = searchResultsPage.goToShoppingListPage();
        OroCheckoutPage checkoutPage = shoppingListPage.clickCreateOrder();
        String text = checkoutPage.orderSummary.getTaxSinglePageCheckout();
        assertTrue(text.contains(expectedTaxAmount));
    }
}
