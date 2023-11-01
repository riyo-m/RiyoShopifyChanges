package com.vertex.quality.connectors.orocommerce.tests.storeFront_v5;

import com.vertex.quality.connectors.orocommerce.enums.OSeriesData;
import com.vertex.quality.connectors.orocommerce.enums.OroAddresses;
import com.vertex.quality.connectors.orocommerce.enums.OroTestData;
import com.vertex.quality.connectors.orocommerce.pages.admin.OroAdminCommercePage;
import com.vertex.quality.connectors.orocommerce.pages.oseries.OSeriesLoginPage;
import com.vertex.quality.connectors.orocommerce.pages.oseries.OSeriesTaxpayers;
import com.vertex.quality.connectors.orocommerce.pages.storefront.*;
import com.vertex.quality.connectors.orocommerce.tests.base.OroStoreFrontBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Test class which contains all test-cases related to ORO 5 version
 *
 * @author Shivam.Soni
 */
public class OroStoreFrontOrdersTests extends OroStoreFrontBaseTest {

    OSeriesLoginPage oSeriesLogin;
    OSeriesTaxpayers oSeriesTax;
    OroAdminCommercePage oroAdminPage;
    OroAdminCommercePage commercePage;
    OroStoreFrontLoginPage signOnPage;
    OroStoreFrontHomePage homePage;
    OroProductSearchResultsPage searchResultsPage;
    OroShoppingListPage shoppingListPage;
    OroCheckoutPage checkoutPage;

    /**
     * COROCOM-1064
     * ORO 5 - Create Purchase Order for VAT (US-EU) and Invoice
     */
    @Test(groups = {"oroSmoke_v5"})
    public void orderUSToEUTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShippingOrigin(OroTestData.ADMIN_URL_VERSION_5.data, OroAddresses.US_PA_BERWYN);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = getStoreFrontSignOnPage(OroTestData.STOREFRONT_URL_VERSION_5.data);
        signOnPage.refreshPage();
        signOnPage.handleCookiesPopup(true);
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.createOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.FRANCE_PARIS);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(0), checkoutPage.orderSummary.getTaxFromUI());
    }

    /**
     * COROCOM-1065
     * ORO 5 - Create Purchase Order for VAT (EU-US) and Invoice
     */
    @Test(groups = {"oroSmoke_v5"})
    public void orderEUToUSTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShippingOrigin(OroTestData.ADMIN_URL_VERSION_5.data, OroAddresses.GERMANY_BERLIN_2);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = getStoreFrontSignOnPage(OroTestData.STOREFRONT_URL_VERSION_5.data);
        signOnPage.refreshPage();
        signOnPage.handleCookiesPopup(true);
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.createOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_CA_LOS_ANGELES);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(9.5), checkoutPage.orderSummary.getTaxFromUI());
    }

    /**
     * COROCOM-1035
     * ORO 5 Create Purchase Order with Invoice CAN to US
     */
    @Test(groups = {"oroSmoke_v5", "oroRegression_v5"})
    public void orderCANToUSTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShippingOrigin(OroTestData.ADMIN_URL_VERSION_5.data, OroAddresses.CANADA_QUEBEC);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = getStoreFrontSignOnPage(OroTestData.STOREFRONT_URL_VERSION_5.data);
        signOnPage.refreshPage();
        signOnPage.handleCookiesPopup(true);
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        OroProductSearchResultsPage searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.createOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_PA_GETTYSBURG);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(6).split("\\.")[0], checkoutPage.orderSummary.getTaxFromUI().split("\\.")[0]);
    }

    /**
     * COROCOM-1033
     * ORO 5 Create Purchase Order with Invoice CANBC to CANON different Province (GST/HST)
     */
    @Test(groups = {"oroSmoke_v5", "oroRegression_v5"})
    public void orderCANBCToCANONTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShippingOrigin(OroTestData.ADMIN_URL_VERSION_5.data, OroAddresses.CANADA_BRITISH_COLUMBIA);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = getStoreFrontSignOnPage(OroTestData.STOREFRONT_URL_VERSION_5.data);
        signOnPage.refreshPage();
        signOnPage.handleCookiesPopup(true);
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.createOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.CANADA_ONTARIO);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(13), checkoutPage.orderSummary.getTaxFromUI());
    }

    /**
     * COROCOM-1055
     * ORO 5 - Create Purchase Order with NJ no tax on clothing, no shipping tax
     */
    @Test(groups = {"oroSmoke_v5", "oroRegression_v5"})
    public void orderNJTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShippingOrigin(OroTestData.ADMIN_URL_VERSION_5.data, OroAddresses.US_CA_UNIVERSITY);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = getStoreFrontSignOnPage(OroTestData.STOREFRONT_URL_VERSION_5.data);
        signOnPage.refreshPage();
        signOnPage.handleCookiesPopup(true);
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.createOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.NJ_ADDRESS);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(6.625), checkoutPage.orderSummary.getTaxFromUI());
    }

    /**
     * COROCOM-1050
     * ORO 5 Create Purchase Order with no State Tax, locally administered
     */
    @Test(groups = {"oroSmoke_v5", "oroRegression_v5"})
    public void orderNoStateTaxTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShippingOrigin(OroTestData.ADMIN_URL_VERSION_5.data, OroAddresses.US_PA_GETTYSBURG);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = getStoreFrontSignOnPage(OroTestData.STOREFRONT_URL_VERSION_5.data);
        signOnPage.refreshPage();
        signOnPage.handleCookiesPopup(true);
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.createOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.ALASKA_ADDRESS);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(5), checkoutPage.orderSummary.getTaxFromUI());
    }

    /**
     * COROCOM-1037
     * ORO 5 test case - Create Purchase Order with Discounts and Invoice
     */
    @Test(groups = {"oroSmoke_v5", "oroRegression_v5"})
    public void orderDiscountTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShipFromAddress(OroAddresses.US_PA_GETTYSBURG);
        signOnPage = loadSignOnPage();
        signOnPage.refreshPage();
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();

        oroAdminPage = new OroAdminCommercePage(driver);
        oroAdminPage.addMultipleProductDetail(OroTestData.QUANTITY_FIVE.data, OroTestData.QUANTITY_ONE.data,
                OroTestData.QUANTITY_TWO.data, OroTestData.QUANTITY_ZERO.data, OroTestData.PRODUCT_NAME.data,
                OroTestData.PRODUCT_NAME1.data, OroTestData.PRODUCT_NAME2.data, "");
        searchResultsPage = new OroProductSearchResultsPage(driver);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_WA_SAMMAMISH);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.billingInfo.enterCoupon(OroTestData.TEN_PERCENT_ORDER.data);
        checkoutPage.billingInfo.enterSecondCoupon(OroTestData.TEN_DOLLAR_ITEM.data);
        checkoutPage.billingInfo.enterThirdCoupon(OroTestData.ONE_OFF_SHIP.data);
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        String actualTaxAmount = checkoutPage.orderSummary.getTaxFromUI();
        boolean isTaxCorrect = OroTestData.DISCOUNT_BULK_ORDER_TAX.data.equals(actualTaxAmount);
        assertTrue(isTaxCorrect);
    }

    /**
     * COROCOM-1044
     * ORO 5 test case - Create Purchase Order with Discount - Multi Line Order with Discount Order Amount
     */
    @Test(groups = {"oroRegression_v5"})
    public void multiLineOrderWithAmountDiscountTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShippingOrigin(OroTestData.ADMIN_URL_VERSION_5.data, OroAddresses.US_PA_GETTYSBURG);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = getStoreFrontSignOnPage(OroTestData.STOREFRONT_URL_VERSION_5.data);
        signOnPage.refreshPage();
        signOnPage.handleCookiesPopup(true);
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();

        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME1.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME1.data);

        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.createOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_CA_LOS_ANGELES);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.billingInfo.enterCoupon(OroTestData.FIVE_DOLLAR_ORDER.data);
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(9.5), checkoutPage.orderSummary.getTaxFromUI());
    }

    /**
     * COROCOM-1041
     * ORO 5 test case - Create Purchase Order with Discount - Multi line with Discount order Percent, Change Discount Order Percent
     */
    @Test(groups = {"oroRegression_v5"})
    public void multiLineOrderWithPercentDiscountTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShippingOrigin(OroTestData.ADMIN_URL_VERSION_5.data, OroAddresses.US_PA_GETTYSBURG);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = getStoreFrontSignOnPage(OroTestData.STOREFRONT_URL_VERSION_5.data);
        signOnPage.refreshPage();
        signOnPage.handleCookiesPopup(true);
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();

        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME1.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME1.data);

        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.createOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_CA_LOS_ANGELES);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.billingInfo.enterCoupon(OroTestData.TEN_PERCENT_ORDER.data);
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(9.5), checkoutPage.orderSummary.getTaxFromUI());
    }

    /**
     * COROCOM-1032
     * ORO 5 test case - Create Purchase Order with Discount - Multi Line Order with Discount Shipping Amount
     */
    @Test(groups = {"oroRegression_v5"})
    public void multiLineOrderWithShippingDiscountTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShippingOrigin(OroTestData.ADMIN_URL_VERSION_5.data, OroAddresses.US_PA_GETTYSBURG);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = getStoreFrontSignOnPage(OroTestData.STOREFRONT_URL_VERSION_5.data);
        signOnPage.refreshPage();
        signOnPage.handleCookiesPopup(true);
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();

        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME1.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME1.data);

        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.createOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_CA_LOS_ANGELES);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.billingInfo.enterCoupon(OroTestData.ONE_OFF_SHIP.data);
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(9.5), checkoutPage.orderSummary.getTaxFromUI());
    }

    /**
     * COROCOM-1049
     * ORO 5 End to End Test Create Purchase Order and add/delete lines and change quantity and location
     */
    @Test(groups = {"oroSmoke_v5", "oroRegression_v5"})
    public void orderChangeQuantityLineTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShippingOrigin(OroTestData.ADMIN_URL_VERSION_5.data, OroAddresses.US_PA_GETTYSBURG);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = getStoreFrontSignOnPage(OroTestData.STOREFRONT_URL_VERSION_5.data);
        signOnPage.refreshPage();
        signOnPage.handleCookiesPopup(true);
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();

        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME1.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME1.data);
        homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME2.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME2.data);
        homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME3.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME3.data);

        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.createOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_CA_LOS_ANGELES);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(9.5), checkoutPage.orderSummary.getTaxFromUI());
    }

    /**
     * COROCOM-1053
     * ORO 5 Create Purchase Order with Invoice CANBC to CANQC different Province (GST/QST)
     */
    @Test(groups = {"oroRegression_v5"})
    public void orderCANBCToCANQCTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShippingOrigin(OroTestData.ADMIN_URL_VERSION_5.data, OroAddresses.CANADA_BRITISH_COLUMBIA);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = getStoreFrontSignOnPage(OroTestData.STOREFRONT_URL_VERSION_5.data);
        signOnPage.refreshPage();
        signOnPage.handleCookiesPopup(true);
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.createOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.CANADA_QUEBEC);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(14.975), checkoutPage.orderSummary.getTaxFromUI());
    }

    /**
     * COROCOM-1034
     * ORO 5 Create Purchase Order (US-CAN) with Invoice
     */
    @Test(groups = {"oroRegression_v5"})
    public void orderUSToCANTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShippingOrigin(OroTestData.ADMIN_URL_VERSION_5.data, OroAddresses.US_PA_GETTYSBURG);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = getStoreFrontSignOnPage(OroTestData.STOREFRONT_URL_VERSION_5.data);
        signOnPage.refreshPage();
        signOnPage.handleCookiesPopup(true);
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.createOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.CANADA_BRITISH_COLUMBIA);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(7), checkoutPage.orderSummary.getTaxFromUI());
    }

    /**
     * COROCOM-1040
     * ORO 5 Create Purchase Order (CANQC-CANBC) with Invoice (GST/PST)
     */
    @Test(groups = {"oroRegression_v5"})
    public void orderCANQCToCANBCTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShippingOrigin(OroTestData.ADMIN_URL_VERSION_5.data, OroAddresses.CANADA_QUEBEC);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = getStoreFrontSignOnPage(OroTestData.STOREFRONT_URL_VERSION_5.data);
        signOnPage.refreshPage();
        signOnPage.handleCookiesPopup(true);
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.createOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.CANADA_BRITISH_COLUMBIA);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(12), checkoutPage.orderSummary.getTaxFromUI());
    }

    /**
     * COROCOM-1051
     * ORO 5 Create Purchase Order with different billing and shipping and Invoice
     */
    @Test(groups = {"oroRegression_v5"})
    public void orderWithDifferentShippingAndBillingTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShippingOrigin(OroTestData.ADMIN_URL_VERSION_5.data, OroAddresses.US_PA_GETTYSBURG);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = getStoreFrontSignOnPage(OroTestData.STOREFRONT_URL_VERSION_5.data);
        signOnPage.refreshPage();
        signOnPage.handleCookiesPopup(true);
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.createOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_DE_WILMINGTON);
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.shippingInfo.selectNewAddress();
        checkoutPage.shippingInfo.fillAddressFields(OroAddresses.US_CA_LOS_ANGELES);
        checkoutPage.shippingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(9.5), checkoutPage.orderSummary.getTaxFromUI());
    }

    /**
     * COROCOM-1047
     * ORO 5 Create Purchase Order for Invoice only
     */
    @Test(groups = {"oroRegression_v5"})
    public void orderForInvoiceTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShippingOrigin(OroTestData.ADMIN_URL_VERSION_5.data, OroAddresses.US_PA_GETTYSBURG);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = getStoreFrontSignOnPage(OroTestData.STOREFRONT_URL_VERSION_5.data);
        signOnPage.refreshPage();
        signOnPage.handleCookiesPopup(true);
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.createOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_CA_LOS_ANGELES);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(9.5), checkoutPage.orderSummary.getTaxFromUI());
    }

    /**
     * COROCOM-1043
     * ORO 5 Create Purchase Order with shipping on line item only
     */
    @Test(groups = {"oroRegression_v5"})
    public void orderWithLineItemTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShippingOrigin(OroTestData.ADMIN_URL_VERSION_5.data, OroAddresses.US_PA_GETTYSBURG);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = getStoreFrontSignOnPage(OroTestData.STOREFRONT_URL_VERSION_5.data);
        signOnPage.refreshPage();
        signOnPage.handleCookiesPopup(true);
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.createOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_CA_LOS_ANGELES);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(9.5), checkoutPage.orderSummary.getTaxFromUI());
    }

    /**
     * COROCOM-1054
     * ORO 5 Create Purchase Order with Invoice CANNB to CANNB same Province (HST)
     */
    @Test(groups = {"oroRegression_v5"})
    public void orderCANNBToCANNBTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShippingOrigin(OroTestData.ADMIN_URL_VERSION_5.data, OroAddresses.CANADA_GRAND_MANAN);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = getStoreFrontSignOnPage(OroTestData.STOREFRONT_URL_VERSION_5.data);
        signOnPage.refreshPage();
        signOnPage.handleCookiesPopup(true);
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.createOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.CANADA_QUISPAMSIS);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(15), checkoutPage.orderSummary.getTaxFromUI());
    }

    /**
     * COROCOM-1046
     * ORO 5 Create Purchase Order with Modified Origin State
     */
    @Test(groups = {"oroRegression_v5"})
    public void orderWithModifiedStateTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShippingOrigin(OroTestData.ADMIN_URL_VERSION_5.data, OroAddresses.US_CA_UNIVERSITY);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = getStoreFrontSignOnPage(OroTestData.STOREFRONT_URL_VERSION_5.data);
        signOnPage.refreshPage();
        signOnPage.handleCookiesPopup(true);
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.createOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_PA_GETTYSBURG);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(6).split("\\.")[0], checkoutPage.orderSummary.getTaxFromUI().split("\\.")[0]);
    }

    /**
     * COROCOM-1039
     * ORO 5 test case - Create Purchase Order for LATAM (CR-CO) and Invoice
     */
    @Test(groups = {"oroRegression_v5"})
    public void orderCRCOCalculateVATTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShippingOrigin(OroTestData.ADMIN_URL_VERSION_5.data, OroAddresses.CR_SAN_JOSE);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = getStoreFrontSignOnPage(OroTestData.STOREFRONT_URL_VERSION_5.data);
        signOnPage.refreshPage();
        signOnPage.handleCookiesPopup(true);
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.createOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.COL_BOGOTA);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(0), checkoutPage.orderSummary.getTaxFromUI());
    }

    /**
     * COROCOM-1045
     * ORO 5 Test case -Create Purchase Order with currency conversion for VAT (Intra EU FR-DE) and Invoice
     */
    @Test(groups = {"oroRegression_v5"})
    public void orderFREUCalculateVATTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShippingOrigin(OroTestData.ADMIN_URL_VERSION_5.data, OroAddresses.FRANCE_MARSEILLE);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = getStoreFrontSignOnPage(OroTestData.STOREFRONT_URL_VERSION_5.data);
        signOnPage.refreshPage();
        signOnPage.handleCookiesPopup(true);
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.createOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.GERMANY_BERLIN_2);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(20), checkoutPage.orderSummary.getTaxFromUI());
    }

    /**
     * COROCOM-1038
     * ORO 5 test cases - Create Purchase Order with Shipping for VAT (Intra EU DE-DE) and Invoice
     */
    @Test(groups = {"oroRegression_v5"})
    public void orderDEDECalculateVATTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShippingOrigin(OroTestData.ADMIN_URL_VERSION_5.data, OroAddresses.GERMANY_BERLIN_1);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = getStoreFrontSignOnPage(OroTestData.STOREFRONT_URL_VERSION_5.data);
        signOnPage.refreshPage();
        signOnPage.handleCookiesPopup(true);
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.createOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.GERMANY_BERLIN_2);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(19), checkoutPage.orderSummary.getTaxFromUI());
    }

    /**
     * COROCOM-1031
     * ORO 5 test cases - Create Purchase Order for VAT with Shipping Terms SUP and Invoice
     */
    @Test(groups = {"oroRegression_v5"})
    public void orderFREUCalculateVATSUPTermTest() {
        oSeriesLogin = new OSeriesLoginPage(driver);
        oSeriesTax = new OSeriesTaxpayers(driver);
        try {
            oSeriesLogin.loadOSeriesPage();
            oSeriesLogin.loginToOSeries();
            oSeriesTax.updateShippingTerms(true, OSeriesData.SHIP_TERM_SUP.data);

            commercePage = new OroAdminCommercePage(driver);
            commercePage.setShippingOrigin(OroTestData.ADMIN_URL_VERSION_5.data, OroAddresses.FRANCE_PARIS);
            commercePage.useShippingOriginForWarehouse();
            signOnPage = getStoreFrontSignOnPage(OroTestData.STOREFRONT_URL_VERSION_5.data);
            signOnPage.refreshPage();
            signOnPage.handleCookiesPopup(true);
            homePage = signInToStoreFront(testStartPage);
            homePage.startNewOrder();
            searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                    OroTestData.PRODUCT_NAME.data);
            searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
            shoppingListPage = searchResultsPage.goToShoppingListPage();
            checkoutPage = shoppingListPage.createOrder();
            checkoutPage.billingInfo.fillAddressFields(OroAddresses.GERMANY_BERLIN_2);
            checkoutPage.billingInfo.shipToBillingAddress();
            checkoutPage.billingInfo.clickContinue();
            checkoutPage.orderSummary.clickContinueButton();
            checkoutPage.orderSummary.clickContinueButton();
            assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(19), checkoutPage.orderSummary.getTaxFromUI());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            oSeriesLogin.loadOSeriesPage();
            if (oSeriesLogin.verifyLoginPage()) {
                oSeriesLogin.loginToOSeries();
            }
            oSeriesTax.updateShippingTerms(false, "");
        }
    }

    /**
     * COROCOM-1036
     * ORO 5 test case - Create Purchase Order for VAT with Shipping Terms CUS and Invoice
     */
    @Test(groups = {"oroRegression_v5"})
    public void orderFREUCalculateVATCUSTermTest() {
        oSeriesLogin = new OSeriesLoginPage(driver);
        oSeriesTax = new OSeriesTaxpayers(driver);
        try {
            oSeriesLogin.loadOSeriesPage();
            oSeriesLogin.loginToOSeries();
            oSeriesTax.updateShippingTerms(true, OSeriesData.SHIP_TERM_CUS.data);

            commercePage = new OroAdminCommercePage(driver);
            commercePage.setShippingOrigin(OroTestData.ADMIN_URL_VERSION_5.data, OroAddresses.FRANCE_PARIS);
            commercePage.useShippingOriginForWarehouse();
            signOnPage = getStoreFrontSignOnPage(OroTestData.STOREFRONT_URL_VERSION_5.data);
            signOnPage.refreshPage();
            signOnPage.handleCookiesPopup(true);
            homePage = signInToStoreFront(testStartPage);
            homePage.startNewOrder();
            searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                    OroTestData.PRODUCT_NAME4.data);
            searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME4.data);
            shoppingListPage = searchResultsPage.goToShoppingListPage();
            checkoutPage = shoppingListPage.createOrder();
            checkoutPage.billingInfo.fillAddressFields(OroAddresses.GERMANY_BERLIN_2);
            checkoutPage.billingInfo.shipToBillingAddress();
            checkoutPage.billingInfo.clickContinue();
            checkoutPage.orderSummary.clickContinueButton();
            checkoutPage.orderSummary.clickContinueButton();
            assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(20), checkoutPage.orderSummary.getTaxFromUI());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            oSeriesLogin.loadOSeriesPage();
            if (oSeriesLogin.verifyLoginPage()) {
                oSeriesLogin.loginToOSeries();
            }
            oSeriesTax.updateShippingTerms(false, "");
        }
    }

    /**
     * COROCOM-1048
     * ORO 5 test cases - Create Purchase Order with shipping terms SUP for APAC (SG-JP) and Invoice
     */
    @Test(groups = {"oroRegression_v5"})
    public void orderSGJPCalculateVATTest() {
        oSeriesLogin = new OSeriesLoginPage(driver);
        oSeriesTax = new OSeriesTaxpayers(driver);
        try {
            oSeriesLogin.loadOSeriesPage();
            oSeriesLogin.loginToOSeries();
            oSeriesTax.updateShippingTerms(true, OSeriesData.SHIP_TERM_SUP.data);

            commercePage = new OroAdminCommercePage(driver);
            commercePage.setShippingOrigin(OroTestData.ADMIN_URL_VERSION_5.data, OroAddresses.SG_CENTRAL_SG);
            commercePage.useShippingOriginForWarehouse();
            signOnPage = getStoreFrontSignOnPage(OroTestData.STOREFRONT_URL_VERSION_5.data);
            signOnPage.refreshPage();
            signOnPage.handleCookiesPopup(true);
            homePage = signInToStoreFront(testStartPage);
            homePage.startNewOrder();
            searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                    OroTestData.PRODUCT_NAME4.data);
            searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME4.data);
            shoppingListPage = searchResultsPage.goToShoppingListPage();
            checkoutPage = shoppingListPage.createOrder();
            checkoutPage.billingInfo.fillAddressFields(OroAddresses.JP_TOKYO);
            checkoutPage.billingInfo.shipToBillingAddress();
            checkoutPage.billingInfo.clickContinue();
            checkoutPage.orderSummary.clickContinueButton();
            checkoutPage.orderSummary.clickContinueButton();
            assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(0), checkoutPage.orderSummary.getTaxFromUI());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            oSeriesLogin.loadOSeriesPage();
            if (oSeriesLogin.verifyLoginPage()) {
                oSeriesLogin.loginToOSeries();
            }
            oSeriesTax.updateShippingTerms(false, "");
        }
    }

    /**
     * COROCOM-1052
     * ORO 5 Create Purchase Order for VAT (US-EU) and Invoice
     */
    @Test(groups = {"oroRegression_v5"})
    public void orderUSEUTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShippingOrigin(OroTestData.ADMIN_URL_VERSION_5.data, OroAddresses.US_PA_GETTYSBURG);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = getStoreFrontSignOnPage(OroTestData.STOREFRONT_URL_VERSION_5.data);
        signOnPage.refreshPage();
        signOnPage.handleCookiesPopup(true);
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME1.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME1.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.createOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.FRANCE_PARIS);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(0), checkoutPage.orderSummary.getTaxFromUI());
    }

    /**
     * COROCOM-1042
     * ORO 5 - Create Purchase Order for VAT (EU-US) and Invoice
     */
    @Test(groups = {"oroRegression_v5"})
    public void orderEUUSTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShippingOrigin(OroTestData.ADMIN_URL_VERSION_5.data, OroAddresses.GERMANY_BERLIN_2);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = getStoreFrontSignOnPage(OroTestData.STOREFRONT_URL_VERSION_5.data);
        signOnPage.refreshPage();
        signOnPage.handleCookiesPopup(true);
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.createOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_CA_UNIVERSITY);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(9.5), checkoutPage.orderSummary.getTaxFromUI());
    }

    /**
     * COROCOM-1056
     * ORO 5 Create Purchase Order Accrual with Invoice
     */
    @Test(groups = {"oroRegression_v5"})
    public void orderUSPATest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShippingOrigin(OroTestData.ADMIN_URL_VERSION_5.data, OroAddresses.US_PA_GETTYSBURG);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = getStoreFrontSignOnPage(OroTestData.STOREFRONT_URL_VERSION_5.data);
        signOnPage.refreshPage();
        signOnPage.handleCookiesPopup(true);
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.createOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_PA_BERWYN);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(6).split("\\.")[0], checkoutPage.orderSummary.getTaxFromUI().split("\\.")[0]);
    }

    /**
     * COROCOM-1057
     * ORO 5 Create Purchase Order for VAT where the Customer is not registered for O Series and is registered in the Financial System
     */
    @Test(groups = {"oroRegression_v5"})
    public void orderCustomerNotRegisteredTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShippingOrigin(OroTestData.ADMIN_URL_VERSION_5.data, OroAddresses.US_PA_GETTYSBURG);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = getStoreFrontSignOnPage(OroTestData.STOREFRONT_URL_VERSION_5.data);
        signOnPage.refreshPage();
        signOnPage.handleCookiesPopup(true);
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.createOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_CA_LOS_ANGELES);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(9.5), checkoutPage.orderSummary.getTaxFromUI());
    }
}
