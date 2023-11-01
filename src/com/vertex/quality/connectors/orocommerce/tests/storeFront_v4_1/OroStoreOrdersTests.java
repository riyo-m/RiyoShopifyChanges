package com.vertex.quality.connectors.orocommerce.tests.storeFront_v4_1;

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
 * @author alewis
 */
public class OroStoreOrdersTests extends OroStoreFrontBaseTest {

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
     * COROCOM-842
     * Oro 4.1 - Create Purchase Order for VAT (US-EU) and Invoice
     */
    @Test(groups = {"oroSmoke_v4_1"})
    public void orderUSToEUTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShipFromAddress(OroAddresses.US_PA_BERWYN);
        signOnPage = loadSignOnPage();
        signOnPage.refreshPage();
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME1.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFieldsEU(OroAddresses.FRANCE_PARIS, OroTestData.VAT_REG_NO.data);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        String actualTaxAmount = checkoutPage.orderSummary.getTaxAmount();
        boolean isTaxCorrect = OroTestData.US_EU_TAX.data.equals(actualTaxAmount);
        assertTrue(isTaxCorrect);
    }

    /**
     * COROCOM-911
     * Oro 4.1 - Create Purchase Order with Invoice CAN to US
     */
    @Test(groups = {"oroSmoke_v4_1", "oroRegression_v4_1"})
    public void orderCANToUSTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShipFromAddress(OroAddresses.CANADA_QUEBEC);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = loadSignOnPage();
        signOnPage.refreshPage();
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_PA_GETTYSBURG);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(6), checkoutPage.orderSummary.getTaxAmount());
    }

    /**
     * COROCOM-914
     * Oro 4.1 - Create Purchase Order with Invoice CANBC to CANON different Province (GST/HST)
     */
    @Test(groups = {"oroSmoke_v4_1", "oroRegression_v4_1"})
    public void orderCANBCToCANONTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShipFromAddress(OroAddresses.CANADA_BRITISH_COLUMBIA);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = loadSignOnPage();
        signOnPage.refreshPage();
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.CANADA_ONTARIO);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(13), checkoutPage.orderSummary.getTaxAmount());
    }

    /**
     * COROCOM-908
     * Oro 4.1 - Create Purchase Order with NJ no tax on clothing, no shipping tax
     */
    @Test(groups = {"oroSmoke_v4_1", "oroRegression_v4_1"})
    public void orderNJTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShipFromAddress(OroAddresses.US_CA_UNIVERSITY);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = loadSignOnPage();
        signOnPage.refreshPage();
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.NJ_ADDRESS);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        String actualTaxAmount = checkoutPage.orderSummary.getTaxAmount();
        boolean isTaxCorrect = OroTestData.NJ_TAX.data.equals(actualTaxAmount);
        assertTrue(isTaxCorrect);
    }

    /**
     * COROCOM-906
     * Oro 4.1 - Create Purchase Order with no State Tax, locally administered
     */
    @Test(groups = {"oroSmoke_v4_1", "oroRegression_v4_1"})
    public void orderNoStateTaxTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShipFromAddress(OroAddresses.US_PA_GETTYSBURG);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = loadSignOnPage();
        signOnPage.refreshPage();
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.ALASKA_ADDRESS);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(5), checkoutPage.orderSummary.getTaxAmount());
    }

    /**
     * COROCOM-843
     * Oro 4.1 - Create Purchase Order with Discounts and Invoice order
     */
    @Test(groups = {"oroSmoke_v4_1", "oroRegression_v4_1"})
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
        String actualTaxAmount = checkoutPage.orderSummary.getTaxAmount();
        boolean isTaxCorrect = OroTestData.DISCOUNT_BULK_ORDER_TAX.data.equals(actualTaxAmount);
        assertTrue(isTaxCorrect);
    }

    /**
     * COROCOM-931
     * ORO 4.1 test case - Create Purchase Order with Discount - Multi Line Order with Discount Order Amount
     */
    @Test(groups = {"oroRegression_v4_1"})
    public void multiLineOrderWithAmountDiscountTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShipFromAddress(OroAddresses.US_PA_GETTYSBURG);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = loadSignOnPage();
        signOnPage.refreshPage();
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();

        oroAdminPage = new OroAdminCommercePage(driver);
        oroAdminPage.addMultipleProductDetail(OroTestData.QUANTITY_FIVE.data, OroTestData.QUANTITY_ONE.data,
                OroTestData.QUANTITY_ZERO.data, OroTestData.QUANTITY_ZERO.data, OroTestData.PRODUCT_NAME.data,
                OroTestData.PRODUCT_NAME1.data, "", "");
        searchResultsPage = new OroProductSearchResultsPage(driver);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_CA_LOS_ANGELES);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.billingInfo.enterCoupon(OroTestData.FIVE_DOLLAR_ORDER.data);
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(9.5), checkoutPage.orderSummary.getTaxAmount());
    }

    /**
     * COROCOM-932
     * ORO test case - Create Purchase Order with Discount - Multi line with Discount order Percent, Change Discount Order Percent
     */
    @Test(groups = {"oroRegression_v4_1"})
    public void multiLineOrderWithPercentDiscountTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShipFromAddress(OroAddresses.US_PA_GETTYSBURG);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = loadSignOnPage();
        signOnPage.refreshPage();
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();

        oroAdminPage = new OroAdminCommercePage(driver);
        oroAdminPage.addMultipleProductDetail(OroTestData.QUANTITY_FIVE.data, OroTestData.QUANTITY_ONE.data,
                OroTestData.QUANTITY_ZERO.data, OroTestData.QUANTITY_ZERO.data, OroTestData.PRODUCT_NAME.data,
                OroTestData.PRODUCT_NAME1.data, "", "");
        searchResultsPage = new OroProductSearchResultsPage(driver);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_CA_LOS_ANGELES);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.billingInfo.enterCoupon(OroTestData.TEN_PERCENT_ORDER.data);
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(9.5), checkoutPage.orderSummary.getTaxAmount());
    }

    /**
     * COROCOM-933
     * ORO test case - Create Purchase Order with Discount - Multi Line Order with Discount Shipping Amount
     */
    @Test(groups = {"oroRegression_v4_1"})
    public void multiLineOrderWithShippingDiscountTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShipFromAddress(OroAddresses.US_PA_GETTYSBURG);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = loadSignOnPage();
        signOnPage.refreshPage();
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();

        oroAdminPage = new OroAdminCommercePage(driver);
        oroAdminPage.addMultipleProductDetail(OroTestData.QUANTITY_FIVE.data, OroTestData.QUANTITY_ONE.data,
                OroTestData.QUANTITY_ZERO.data, OroTestData.QUANTITY_ZERO.data, OroTestData.PRODUCT_NAME.data,
                OroTestData.PRODUCT_NAME1.data, "", "");
        searchResultsPage = new OroProductSearchResultsPage(driver);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_CA_LOS_ANGELES);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.billingInfo.enterCoupon(OroTestData.ONE_OFF_SHIP.data);
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(9.5), checkoutPage.orderSummary.getTaxAmount());
    }

    /**
     * COROCOM-898
     * Oro 4.1 - End to End Test Create Purchase Order and add/delete lines and change quantity
     */
    @Test(groups = {"oroSmoke_v4_1", "oroRegression_v4_1"})
    public void orderChangeQuantityLineTest() {
        OroAdminCommercePage commercePage = new OroAdminCommercePage(driver);
        commercePage.setShipFromAddress(OroAddresses.US_PA_GETTYSBURG);
        commercePage.useShippingOriginForWarehouse();
        OroStoreFrontLoginPage signOnPage = loadSignOnPage();
        signOnPage.refreshPage();
        OroStoreFrontHomePage homePage = signInToStoreFront(testStartPage);
        OroStoreFrontHomePage homePage1 = homePage.startNewOrder();

        OroAdminCommercePage shoppingListPage = new OroAdminCommercePage(driver);
        shoppingListPage.addMultipleProductDetail(OroTestData.QUANTITY_ONE.data, OroTestData.QUANTITY_TEN.data,
                OroTestData.QUANTITY_ONE.data, OroTestData.QUANTITY_ZERO.data, OroTestData.PRODUCT_NAME.data,
                OroTestData.PRODUCT_NAME1.data, OroTestData.PRODUCT_NAME3.data, "");
        OroProductSearchResultsPage searchResultsPage = new OroProductSearchResultsPage(driver);
        OroShoppingListPage shoppingL = searchResultsPage.goToShoppingListPage();
        OroProductSearchResultsPage shippingProduct3 = homePage1.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        OroShoppingListPage shoppingListPage1 = searchResultsPage.goToShoppingListPage();
        OroCheckoutPage checkoutPage = shoppingListPage1.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_CA_LOS_ANGELES);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(9.5), checkoutPage.orderSummary.getTaxAmount());
    }

    /**
     * COROCOM-913
     * ORO 4.1 Create Purchase Order with Invoice CANBC to CANQC different Province (GST/QST)
     */
    @Test(groups = {"oroRegression_v4_1"})
    public void orderCANBCToCANQCTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShipFromAddress(OroAddresses.CANADA_BRITISH_COLUMBIA);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = loadSignOnPage();
        signOnPage.refreshPage();
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.CANADA_QUEBEC);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(14.975), checkoutPage.orderSummary.getTaxFromUI());
    }

    /**
     * COROCOM-903
     * ORO 4.1 Create Purchase Order (US-CAN) with Invoice
     */
    @Test(groups = {"oroRegression_v4_1"})
    public void orderUSToCANTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShipFromAddress(OroAddresses.US_PA_GETTYSBURG);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = loadSignOnPage();
        signOnPage.refreshPage();
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.CANADA_BRITISH_COLUMBIA);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(7), checkoutPage.orderSummary.getTaxFromUI());
    }

    /**
     * COROCOM-902
     * ORO 4.1 Create Purchase Order (CANQC-CANBC) with Invoice (GST/PST)
     */
    @Test(groups = {"oroRegression_v4_1"})
    public void orderCANQCToCANBCTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShipFromAddress(OroAddresses.CANADA_QUEBEC);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = loadSignOnPage();
        signOnPage.refreshPage();
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.CANADA_BRITISH_COLUMBIA);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(12), checkoutPage.orderSummary.getTaxFromUI());
    }

    /**
     * COROCOM-901
     * ORO 4.1 Create Purchase Order with different billing and shipping and Invoice
     */
    @Test(groups = {"oroRegression_v4_1"})
    public void orderWithDifferentShippingAndBillingTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShipFromAddress(OroAddresses.US_PA_GETTYSBURG);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = loadSignOnPage();
        signOnPage.refreshPage();
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.clickCreateOrder();
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
     * COROCOM-899
     * ORO 4.1 Create Purchase Order for Invoice only
     */
    @Test(groups = {"oroRegression_v4_1"})
    public void orderForInvoiceTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShipFromAddress(OroAddresses.US_PA_GETTYSBURG);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = loadSignOnPage();
        signOnPage.refreshPage();
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_CA_LOS_ANGELES);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(9.5), checkoutPage.orderSummary.getTaxFromUI());
    }

    /**
     * COROCOM-909
     * ORO 4.1 Create Purchase Order with shipping on line item only
     */
    @Test(groups = {"oroRegression_v4_1"})
    public void orderWithLineItemTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShipFromAddress(OroAddresses.US_PA_GETTYSBURG);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = loadSignOnPage();
        signOnPage.refreshPage();
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_CA_LOS_ANGELES);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(9.5), checkoutPage.orderSummary.getTaxFromUI());
    }

    /**
     * COROCOM-912
     * ORO 4.1 Create Purchase Order with Invoice CANNB to CANNB same Province (HST)
     */
    @Test(groups = {"oroRegression_v4_1"})
    public void orderCANNBToCANNBTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShipFromAddress(OroAddresses.CANADA_GRAND_MANAN);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = loadSignOnPage();
        signOnPage.refreshPage();
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.CANADA_QUISPAMSIS);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(15), checkoutPage.orderSummary.getTaxFromUI());
    }

    /**
     * COROCOM-907
     * ORO 4.1 Create Purchase Order with Modified Origin State
     */
    @Test(groups = {"oroRegression_v4_1"})
    public void orderWithModifiedStateTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShipFromAddress(OroAddresses.US_CA_UNIVERSITY);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = loadSignOnPage();
        signOnPage.refreshPage();
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_PA_GETTYSBURG);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(6), checkoutPage.orderSummary.getTaxFromUI());
    }

    /**
     * COROCOM-946
     * ORO 4.1 test case - Create Purchase Order for LATAM (CR-CO) and Invoice
     */
    @Test(groups = {"oroRegression_v4_1"})
    public void orderCRCOCalculateVATTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShipFromAddress(OroAddresses.CR_SAN_JOSE);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = loadSignOnPage();
        signOnPage.refreshPage();
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.COL_BOGOTA);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(0), checkoutPage.orderSummary.getTaxFromUI());
    }

    /**
     * COROCOM-945
     * ORO 4.1 Test case -Create Purchase Order with currency conversion for VAT (Intra EU FR-DE) and Invoice
     */
    @Test(groups = {"oroRegression_v4_1"})
    public void orderFREUCalculateVATTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShipFromAddress(OroAddresses.FRANCE_MARSEILLE);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = loadSignOnPage();
        signOnPage.refreshPage();
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.GERMANY_BERLIN_2);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        String actualTaxAmount = checkoutPage.orderSummary.getTaxAmount();
        boolean isTaxCorrect = OroTestData.MARSEILLES_BERLIN_TAX.data.equals(actualTaxAmount);
        assertTrue(isTaxCorrect);
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(20), checkoutPage.orderSummary.getTaxFromUI());
    }

    /**
     * COROCOM-949
     * ORO 4.1 test cases - Create Purchase Order with Shipping for VAT (Intra EU DE-DE) and Invoice
     */
    @Test(groups = {"oroRegression_v4_1"})
    public void orderDEDECalculateVATTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShipFromAddress(OroAddresses.GERMANY_BERLIN_1);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = loadSignOnPage();
        signOnPage.refreshPage();
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.GERMANY_BERLIN_2);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(19), checkoutPage.orderSummary.getTaxFromUI());
    }

    /**
     * COROCOM-948
     * ORO 4.1 test cases - Create Purchase Order for VAT with Shipping Terms SUP and Invoice
     */
    @Test(groups = {"oroRegression_v4_1"})
    public void orderFREUCalculateVATSUPTermTest() {
        oSeriesLogin = new OSeriesLoginPage(driver);
        oSeriesTax = new OSeriesTaxpayers(driver);
        try {
            oSeriesLogin.loadOSeriesPage();
            oSeriesLogin.loginToOSeries();
            oSeriesTax.updateShippingTerms(true, OSeriesData.SHIP_TERM_SUP.data);

            commercePage = new OroAdminCommercePage(driver);
            commercePage.setShipFromAddress(OroAddresses.FRANCE_PARIS);
            commercePage.useShippingOriginForWarehouse();
            signOnPage = loadSignOnPage();
            signOnPage.refreshPage();
            homePage = signInToStoreFront(testStartPage);
            homePage.startNewOrder();
            searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                    OroTestData.PRODUCT_NAME.data);
            searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
            shoppingListPage = searchResultsPage.goToShoppingListPage();
            checkoutPage = shoppingListPage.clickCreateOrder();
            checkoutPage.billingInfo.fillAddressFields(OroAddresses.GERMANY_BERLIN_2);
            checkoutPage.billingInfo.shipToBillingAddress();
            checkoutPage.billingInfo.clickContinue();
            checkoutPage.orderSummary.clickContinueButton();
            checkoutPage.orderSummary.clickContinueButton();
            String actualTaxAmount = checkoutPage.orderSummary.getTaxAmount();
            boolean isTaxCorrect = OroTestData.FR_EU_SUP_TAX.data.equals(actualTaxAmount);
            assertTrue(isTaxCorrect);
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
     * COROCOM-947
     * ORO 4.1 test case - Create Purchase Order for VAT with Shipping Terms CUS and Invoice
     */
    @Test(groups = {"oroRegression_v4_1"})
    public void orderFREUCalculateVATCUSTermTest() {
        oSeriesLogin = new OSeriesLoginPage(driver);
        oSeriesTax = new OSeriesTaxpayers(driver);
        try {
            oSeriesLogin.loadOSeriesPage();
            oSeriesLogin.loginToOSeries();
            oSeriesTax.updateShippingTerms(true, OSeriesData.SHIP_TERM_CUS.data);

            commercePage = new OroAdminCommercePage(driver);
            commercePage.setShipFromAddress(OroAddresses.FRANCE_PARIS);
            commercePage.useShippingOriginForWarehouse();
            signOnPage = loadSignOnPage();
            signOnPage.refreshPage();
            homePage = signInToStoreFront(testStartPage);
            homePage.startNewOrder();
            searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                    OroTestData.PRODUCT_NAME.data);
            searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
            shoppingListPage = searchResultsPage.goToShoppingListPage();
            checkoutPage = shoppingListPage.clickCreateOrder();
            checkoutPage.billingInfo.fillAddressFields(OroAddresses.GERMANY_BERLIN_2);
            checkoutPage.billingInfo.shipToBillingAddress();
            checkoutPage.billingInfo.clickContinue();
            checkoutPage.orderSummary.clickContinueButton();
            checkoutPage.orderSummary.clickContinueButton();
            String actualTaxAmount = checkoutPage.orderSummary.getTaxAmount();
            boolean isTaxCorrect = OroTestData.FR_EU_CUS_TAX.data.equals(actualTaxAmount);
            assertTrue(isTaxCorrect);
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
     * COROCOM-950
     * ORO 4.1 test cases - Create Purchase Order with shipping terms SUP for APAC (SG-JP) and Invoice
     */
    @Test(groups = {"oroRegression_v4_1"})
    public void orderSGJPCalculateVATTest() {
        oSeriesLogin = new OSeriesLoginPage(driver);
        oSeriesTax = new OSeriesTaxpayers(driver);
        try {
            oSeriesLogin.loadOSeriesPage();
            oSeriesLogin.loginToOSeries();
            oSeriesTax.updateShippingTerms(true, OSeriesData.SHIP_TERM_SUP.data);

            commercePage = new OroAdminCommercePage(driver);
            commercePage.setShipFromAddress(OroAddresses.SG_CENTRAL_SG);
            commercePage.useShippingOriginForWarehouse();
            signOnPage = loadSignOnPage();
            signOnPage.refreshPage();
            homePage = signInToStoreFront(testStartPage);
            homePage.startNewOrder();
            searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                    OroTestData.PRODUCT_NAME.data);
            searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
            shoppingListPage = searchResultsPage.goToShoppingListPage();
            checkoutPage = shoppingListPage.clickCreateOrder();
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
     * COROCOM-968
     * ORO 4.1 Create Purchase Order for VAT (US-EU) and Invoice
     */
    @Test(groups = {"oroRegression_v4_1"})
    public void orderUSEUTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShipFromAddress(OroAddresses.US_PA_GETTYSBURG);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = loadSignOnPage();
        signOnPage.refreshPage();
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME1.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFieldsEU(OroAddresses.FRANCE_PARIS, OroTestData.VAT_REG_NO.data);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(0), checkoutPage.orderSummary.getTaxFromUI());
    }

    /**
     * COROCOM-967
     * ORO 4.1 - Create Purchase Order for VAT (EU-US) and Invoice
     */
    @Test(groups = {"oroRegression_v4_1"})
    public void orderEUUSTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShipFromAddress(OroAddresses.GERMANY_BERLIN_2);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = loadSignOnPage();
        signOnPage.refreshPage();
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_CA_UNIVERSITY);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(9.5), checkoutPage.orderSummary.getTaxFromUI());
    }

    /**
     * COROCOM-972
     * ORO 4.1 Create Purchase Order Accrual with Invoice
     */
    @Test(groups = {"oroRegression_v4_1"})
    public void orderUSPATest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShipFromAddress(OroAddresses.US_PA_GETTYSBURG);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = loadSignOnPage();
        signOnPage.refreshPage();
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_PA_BERWYN);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(6), checkoutPage.orderSummary.getTaxFromUI());
    }

    /**
     * COROCOM-965
     * ORO 4.1 - Create Purchase Order for VAT where the Customer is not registered for O Series and is registered in the Financial System
     */
    @Test(groups = {"oroRegression_v4_1"})
    public void orderCustomerNotRegisteredTest() {
        commercePage = new OroAdminCommercePage(driver);
        commercePage.setShipFromAddress(OroAddresses.US_PA_GETTYSBURG);
        commercePage.useShippingOriginForWarehouse();
        signOnPage = loadSignOnPage();
        signOnPage.refreshPage();
        homePage = signInToStoreFront(testStartPage);
        homePage.startNewOrder();
        searchResultsPage = homePage.headerMiddleBar.searchForProduct(
                OroTestData.PRODUCT_NAME.data);
        searchResultsPage.selectProductFromList(OroTestData.PRODUCT_NAME.data);
        shoppingListPage = searchResultsPage.goToShoppingListPage();
        checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_CA_LOS_ANGELES);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        assertEquals(checkoutPage.orderSummary.calculatePercentBaseTax(6), checkoutPage.orderSummary.getTaxFromUI());
    }

    /**
     * COROCOM-529
     */
    @Test(groups = {"oroCommerce"})
    public void orderToCaliforniaTest() {
        String expectedTaxAmount = "$4.45";
        String productName = "Physician’s 5-Pocket Lab Coat";
        OroStoreFrontHomePage homePage = signInToStoreFront(testStartPage);
        OroStoreFrontHomePage homePage1 = homePage.startNewOrder();
        OroProductSearchResultsPage searchResultsPage = homePage1.headerMiddleBar.searchForProduct(productName);
        searchResultsPage.selectProductFromList(productName);
        OroShoppingListPage shoppingListPage = searchResultsPage.goToShoppingListPage();
        OroCheckoutPage checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_CA_LOS_ANGELES);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        String actualTaxAmount = checkoutPage.orderSummary.getTaxAmount();
        boolean isTaxCorrect = expectedTaxAmount.equals(actualTaxAmount);
        assertTrue(isTaxCorrect);
    }

    /**
     * COROCOM-524
     */
    @Test(groups = {"oroCommerce"})
    public void orderToDelawareTest() {
        String expectedTaxAmount = "$0.00";
        String productName = "Physician’s 5-Pocket Lab Coat";
        OroStoreFrontHomePage homePage = signInToStoreFront(testStartPage);
        OroStoreFrontHomePage homePage1 = homePage.startNewOrder();
        OroProductSearchResultsPage searchResultsPage = homePage1.headerMiddleBar.searchForProduct(productName);
        searchResultsPage.selectProductFromList(productName);
        OroShoppingListPage shoppingListPage = searchResultsPage.goToShoppingListPage();
        OroCheckoutPage checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_DE_NEWARK);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        String actualTaxAmount = checkoutPage.orderSummary.getTaxAmount();
        boolean isTaxCorrect = expectedTaxAmount.equals(actualTaxAmount);
        assertTrue(isTaxCorrect);
    }

    /**
     * COROCOM-523
     */
    @Test(groups = {"oroCommerce"})
    public void orderToKOPTest() {
        String expectedTaxAmount = "$2.66";
        String productName = "Physician’s 5-Pocket Lab Coat";
        OroStoreFrontHomePage homePage = signInToStoreFront(testStartPage);
        OroStoreFrontHomePage homePage1 = homePage.startNewOrder();
        OroProductSearchResultsPage searchResultsPage = homePage1.headerMiddleBar.searchForProduct(productName);
        searchResultsPage.selectProductFromList(productName);
        OroShoppingListPage shoppingListPage = searchResultsPage.goToShoppingListPage();
        OroCheckoutPage checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_PA_KOP);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        String actualTaxAmount = checkoutPage.orderSummary.getTaxAmount();
        boolean isTaxCorrect = expectedTaxAmount.equals(actualTaxAmount);
        assertTrue(isTaxCorrect);
    }

    /**
     * COROCOM-525
     */
    @Test(groups = {"oroCommerce"})
    public void orderToLouisianaTest() {
        String expectedTaxAmount = "$4.42";
        String productName = "Physician’s 5-Pocket Lab Coat";
        OroStoreFrontHomePage homePage = signInToStoreFront(testStartPage);
        OroStoreFrontHomePage homePage1 = homePage.startNewOrder();
        OroProductSearchResultsPage searchResultsPage = homePage1.headerMiddleBar.searchForProduct(productName);
        searchResultsPage.selectProductFromList(productName);
        OroShoppingListPage shoppingListPage = searchResultsPage.goToShoppingListPage();
        OroCheckoutPage checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_LA_NEW_ORLEANS);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        String actualTaxAmount = checkoutPage.orderSummary.getTaxAmount();
        boolean isTaxCorrect = expectedTaxAmount.equals(actualTaxAmount);
        assertTrue(isTaxCorrect);
    }

    /**
     * COROCOM-526
     */
    @Test(groups = {"oroCommerce"})
    public void orderToPuertoRicoTest() {
        String expectedTaxAmount = "$5.38";
        String productName = "Physician’s 5-Pocket Lab Coat";
        OroStoreFrontHomePage homePage = signInToStoreFront(testStartPage);
        OroStoreFrontHomePage homePage1 = homePage.startNewOrder();
        OroProductSearchResultsPage searchResultsPage = homePage1.headerMiddleBar.searchForProduct(productName);
        searchResultsPage.selectProductFromList(productName);
        OroShoppingListPage shoppingListPage = searchResultsPage.goToShoppingListPage();
        OroCheckoutPage checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_PR_PONCE);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        String actualTaxAmount = checkoutPage.orderSummary.getTaxAmount();
        boolean isTaxCorrect = expectedTaxAmount.equals(actualTaxAmount);
        assertTrue(isTaxCorrect);
    }

    /**
     * COROCOM-530
     */
    @Test(groups = {"oroCommerce"})
    public void orderToCanadaTest() {
        String expectedTaxAmount = "$0.00";
        String productName = "Physician’s 5-Pocket Lab Coat";
        OroStoreFrontHomePage homePage = signInToStoreFront(testStartPage);
        OroStoreFrontHomePage homePage1 = homePage.startNewOrder();
        OroProductSearchResultsPage searchResultsPage = homePage1.headerMiddleBar.searchForProduct(productName);
        searchResultsPage.selectProductFromList(productName);
        OroShoppingListPage shoppingListPage = searchResultsPage.goToShoppingListPage();
        OroCheckoutPage checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.CANADA_BRITISH_COLUMBIA);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        String actualTaxAmount = checkoutPage.orderSummary.getTaxAmount();
        boolean isTaxCorrect = expectedTaxAmount.equals(actualTaxAmount);
        assertTrue(isTaxCorrect);
    }

    /**
     * COROCOM-597
     */
    @Test(groups = {"oroCommerce", "OroVat"})
    public void orderPAToFranceTest() {
        String expectedTaxAmount = "$9.36";
        String productName = "Physician’s 5-Pocket Lab Coat";
        OroStoreFrontHomePage homePage = signInToStoreFront(testStartPage);
        OroStoreFrontHomePage homePage1 = homePage.startNewOrder();
        OroProductSearchResultsPage searchResultsPage = homePage1.headerMiddleBar.searchForProduct(productName);
        searchResultsPage.selectProductFromList(productName);
        OroShoppingListPage shoppingListPage = searchResultsPage.goToShoppingListPage();
        OroCheckoutPage checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFieldsEU(OroAddresses.FRANCE_PARIS, "FR1234");
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        String actualTaxAmount = checkoutPage.orderSummary.getTaxAmount();
        boolean isTaxCorrect = expectedTaxAmount.equals(actualTaxAmount);
        assertTrue(isTaxCorrect);
    }

    /**
     * COROCOM-598
     */
    @Test(groups = {"oroCommerce", "OroVat"})
    public void orderFranceToGermanyTest() {
        String expectedTaxAmount = "$0.00";
        String productName = "Physician’s 5-Pocket Lab Coat";
        OroStoreFrontHomePage homePage = signInToStoreFront(testStartPage);
        OroStoreFrontHomePage homePage1 = homePage.startNewOrder();
        OroProductSearchResultsPage searchResultsPage = homePage1.headerMiddleBar.searchForProduct(productName);
        searchResultsPage.selectProductFromList(productName);
        OroShoppingListPage shoppingListPage = searchResultsPage.goToShoppingListPage();
        OroCheckoutPage checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFieldsEU(OroAddresses.GERMANY_BERLIN_2, "DE1234");
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        String actualTaxAmount = checkoutPage.orderSummary.getTaxAmount();
        boolean isTaxCorrect = expectedTaxAmount.equals(actualTaxAmount);
        assertTrue(isTaxCorrect);
    }

    /**
     * COROCOM-604
     */
    @Test(groups = {"oroCommerce", "OroVat"})
    public void orderGermanyTest() {
        String expectedTaxAmount = "$0.00";
        String productName = "Physician’s 5-Pocket Lab Coat";
        OroStoreFrontHomePage homePage = signInToStoreFront(testStartPage);
        OroStoreFrontHomePage homePage1 = homePage.startNewOrder();
        OroProductSearchResultsPage searchResultsPage = homePage1.headerMiddleBar.searchForProduct(productName);
        searchResultsPage.selectProductFromList(productName);
        OroShoppingListPage shoppingListPage = searchResultsPage.goToShoppingListPage();
        OroCheckoutPage checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFieldsCarefully(OroAddresses.GERMANY_BERLIN_2);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        String actualTaxAmount = checkoutPage.orderSummary.getTaxAmount();
        boolean isTaxCorrect = expectedTaxAmount.equals(actualTaxAmount);
        assertTrue(isTaxCorrect);
    }

    /**
     * COROCOM-602
     */
    @Test(groups = {"oroCommerce", "OroVat"})
    public void orderAustriaTest() {
        String expectedTaxAmount = "$0.00";
        String productName = "Physician’s 5-Pocket Lab Coat";
        OroStoreFrontHomePage homePage = signInToStoreFront(testStartPage);
        OroStoreFrontHomePage homePage1 = homePage.startNewOrder();
        OroProductSearchResultsPage searchResultsPage = homePage1.headerMiddleBar.searchForProduct(productName);
        searchResultsPage.selectProductFromList(productName);
        OroShoppingListPage shoppingListPage = searchResultsPage.goToShoppingListPage();
        OroCheckoutPage checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFieldsCarefully(OroAddresses.AUSTRIA_MITTELBERG);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        String actualTaxAmount = checkoutPage.orderSummary.getTaxAmount();
        boolean isTaxCorrect = expectedTaxAmount.equals(actualTaxAmount);
        assertTrue(isTaxCorrect);
    }

    /**
     * COROCOM-601
     */
    @Test(groups = {"OroVat"})
    public void orderGreeceTest() {
        String expectedTaxAmount = "$0.00";
        String productName = "Physician’s 5-Pocket Lab Coat";
        OroStoreFrontHomePage homePage = signInToStoreFront(testStartPage);
        OroStoreFrontHomePage homePage1 = homePage.startNewOrder();
        OroProductSearchResultsPage searchResultsPage = homePage1.headerMiddleBar.searchForProduct(productName);
        searchResultsPage.selectProductFromList(productName);
        OroShoppingListPage shoppingListPage = searchResultsPage.goToShoppingListPage();
        OroCheckoutPage checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFieldsCarefully(OroAddresses.GREECE_ANALIPSI);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        String actualTaxAmount = checkoutPage.orderSummary.getTaxAmount();
        boolean isTaxCorrect = expectedTaxAmount.equals(actualTaxAmount);
        assertTrue(isTaxCorrect);
    }

    /**
     * COROCOM-596
     */
    @Test(groups = {"oroCommerce", "OroVat"})
    public void orderEUtoUSTest() {
        String expectedTaxAmount = "$0.00";
        String productName = "Physician’s 5-Pocket Lab Coat";
        OroStoreFrontHomePage homePage = signInToStoreFront(testStartPage);
        OroStoreFrontHomePage homePage1 = homePage.startNewOrder();
        OroProductSearchResultsPage searchResultsPage = homePage1.headerMiddleBar.searchForProduct(productName);
        searchResultsPage.selectProductFromList(productName);
        OroShoppingListPage shoppingListPage = searchResultsPage.goToShoppingListPage();
        OroCheckoutPage checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFieldsCarefully(OroAddresses.US_CA_UNIVERSITY);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        String actualTaxAmount = checkoutPage.orderSummary.getTaxAmount();
        boolean isTaxCorrect = expectedTaxAmount.equals(actualTaxAmount);
        assertTrue(isTaxCorrect);
    }

    /**
     * COROCOM-583
     */
    @Test(groups = {"oroCommerce"})
    public void couponCodeOrderTest() {
        String expectedTaxAmount = "$4.18";
        String productName = "Physician’s 5-Pocket Lab Coat";
        OroStoreFrontHomePage homePage = signInToStoreFront(testStartPage);
        OroStoreFrontHomePage homePage1 = homePage.startNewOrder();
        OroProductSearchResultsPage searchResultsPage = homePage1.headerMiddleBar.searchForProduct(productName);
        searchResultsPage.selectProductFromList(productName);
        OroShoppingListPage shoppingListPage = searchResultsPage.goToShoppingListPage();
        OroCheckoutPage checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_WA_SAMMAMISH);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.billingInfo.enterCoupon("TestCoupon");
        String actualTaxAmount = checkoutPage.orderSummary.getTaxAmount();
        boolean isTaxCorrect = expectedTaxAmount.equals(actualTaxAmount);
        assertTrue(isTaxCorrect);
    }

    /**
     * COROCOM-557
     */
    @Test(groups = {"oroCommerce", "oroChange"})
    public void couponCodeChangeAmountTest() {
        String expectedTaxAmount = "$4.08";
        String secondExpectedTaxAmount = "$4.25";
        String productName = "Physician’s 5-Pocket Lab Coat";
        OroStoreFrontHomePage homePage = signInToStoreFront(testStartPage);
        OroStoreFrontHomePage homePage1 = homePage.startNewOrder();
        OroProductSearchResultsPage searchResultsPage = homePage1.headerMiddleBar.searchForProduct(productName);
        searchResultsPage.selectProductFromList(productName);
        OroShoppingListPage shoppingListPage = searchResultsPage.goToShoppingListPage();
        OroCheckoutPage checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_LA_NEW_ORLEANS);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.billingInfo.enterCoupon("10PercentOrderCoupon");
        String actualTaxAmount = checkoutPage.orderSummary.getTaxAmount();
        boolean isTaxCorrect = expectedTaxAmount.equals(actualTaxAmount);
        assertTrue(isTaxCorrect);

        checkoutPage.billingInfo.deleteCoupon();
        checkoutPage.billingInfo.enterCoupon("5PercentOrderCoupon");
        String secondTaxAmount = checkoutPage.orderSummary.getTaxAmount();
        boolean secondIsTaxCorrect = secondExpectedTaxAmount.equals(secondTaxAmount);
        assertTrue(secondIsTaxCorrect);
    }

    /**
     * COROCOM-561
     */
    @Test(groups = {"oroCommerce"})
    public void shippingDiscountTest() {
        String expectedTaxAmount = "$4.45";
        String productName = "Physician’s 5-Pocket Lab Coat";
        OroStoreFrontHomePage homePage = signInToStoreFront(testStartPage);
        OroStoreFrontHomePage homePage1 = homePage.startNewOrder();
        OroProductSearchResultsPage searchResultsPage = homePage1.headerMiddleBar.searchForProduct(productName);
        searchResultsPage.selectProductFromList(productName);
        OroShoppingListPage shoppingListPage = searchResultsPage.goToShoppingListPage();
        OroCheckoutPage checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_CA_LOS_ANGELES);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.billingInfo.enterCoupon("1Dollar-Shipping-Discount");
        String actualTaxAmount = checkoutPage.orderSummary.getTaxAmount();
        boolean isTaxCorrect = expectedTaxAmount.equals(actualTaxAmount);
        assertTrue(isTaxCorrect);
    }

    /**
     * COROCOM-605
     */
    @Test(groups = {"oroCommerce"})
    public void shippingDiscountGermanyTest() {
        String expectedTaxAmount = "$6.99";
        String productName = "Physician’s 5-Pocket Lab Coat";
        OroStoreFrontHomePage homePage = signInToStoreFront(testStartPage);
        OroStoreFrontHomePage homePage1 = homePage.startNewOrder();
        OroProductSearchResultsPage searchResultsPage = homePage1.headerMiddleBar.searchForProduct(productName);
        searchResultsPage.selectProductFromList(productName);
        OroShoppingListPage shoppingListPage = searchResultsPage.goToShoppingListPage();
        OroCheckoutPage checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFieldsEU(OroAddresses.GERMANY_BERLIN_2, "DE1234");
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.billingInfo.enterCoupon("1Dollar-Shipping-Discount");
        String actualTaxAmount = checkoutPage.orderSummary.getTaxAmount();
        boolean isTaxCorrect = expectedTaxAmount.equals(actualTaxAmount);
        assertTrue(isTaxCorrect);
    }

    /**
     * COROCOM-560
     */
    @Test(groups = {"oroCommerce"})
    public void discountAmountCaliforniaTest() {
        String expectedTaxAmount = "$4.27";
        String productName = "Physician’s 5-Pocket Lab Coat";
        OroStoreFrontHomePage homePage = signInToStoreFront(testStartPage);
        OroStoreFrontHomePage homePage1 = homePage.startNewOrder();
        OroProductSearchResultsPage searchResultsPage = homePage1.headerMiddleBar.searchForProduct(productName);
        searchResultsPage.selectProductFromList(productName);
        OroShoppingListPage shoppingListPage = searchResultsPage.goToShoppingListPage();
        OroCheckoutPage checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_CA_LOS_ANGELES);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.billingInfo.enterCoupon("5PercentOrderCoupon");
        String actualTaxAmount = checkoutPage.orderSummary.getTaxAmount();
        boolean isTaxCorrect = expectedTaxAmount.equals(actualTaxAmount);
        assertTrue(isTaxCorrect);
    }

    /**
     * COROCOM-562
     */
    @Test(groups = {"oroCommerce", "couponHorse"})
    public void multipleDiscountsTest() {
        String expectedTaxAmount = "$4.10";
        String productName = "Physician’s 5-Pocket Lab Coat";
        OroStoreFrontHomePage homePage = signInToStoreFront(testStartPage);
        OroStoreFrontHomePage homePage1 = homePage.startNewOrder();
        OroProductSearchResultsPage searchResultsPage = homePage1.headerMiddleBar.searchForProduct(productName);
        searchResultsPage.selectProductFromList(productName);
        OroShoppingListPage shoppingListPage = searchResultsPage.goToShoppingListPage();
        OroCheckoutPage checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_CA_LOS_ANGELES);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.billingInfo.enterCoupon("10PercentOrderCoupon");
        checkoutPage.billingInfo.enterSecondCoupon("1Dollar-Shipping-Discount");
        String actualTaxAmount = checkoutPage.orderSummary.getTaxAmount();
        boolean isTaxCorrect = expectedTaxAmount.equals(actualTaxAmount);
        assertTrue(isTaxCorrect);
    }

    @Test(groups = {"oroCommerce", "oroExemption"})
    public void customerExemptionTest() {
        String expectedTaxAmount = "$0.00";
        String productName = "Physician’s 5-Pocket Lab Coat";
        OroStoreFrontHomePage homePage = signInToStorefrontGovernment(testStartPage);
        OroStoreFrontHomePage homePage1 = homePage.startNewOrder();
        OroProductSearchResultsPage searchResultsPage = homePage1.headerMiddleBar.searchForProduct(productName);
        searchResultsPage.selectProductFromList(productName);
        OroShoppingListPage shoppingListPage = searchResultsPage.goToShoppingListPage();
        OroCheckoutPage checkoutPage = shoppingListPage.clickCreateOrder();
        //		checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_WA_SAMMAMISH);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        checkoutPage.orderSummary.clickContinueButton();
        checkoutPage.orderSummary.clickContinueButton();
        String actualTaxAmount = checkoutPage.orderSummary.getTaxAmount();
        boolean isTaxCorrect = expectedTaxAmount.equals(actualTaxAmount);
        assertTrue(isTaxCorrect);
    }

    @Test(groups = {"oroCommerce"})
    public void productCodeTest() {
        String expectedTaxAmount = "$4.45";
        String productName = "NFC Credit Card Reader";
        OroStoreFrontHomePage homePage = signInToStoreFront(testStartPage);
        OroStoreFrontHomePage homePage1 = homePage.startNewOrder();
        OroProductSearchResultsPage searchResultsPage = homePage1.headerMiddleBar.searchForProduct(productName);
        searchResultsPage.selectProductFromList(productName);
        OroShoppingListPage shoppingListPage = searchResultsPage.goToShoppingListPage();
        OroCheckoutPage checkoutPage = shoppingListPage.clickCreateOrder();
        checkoutPage.billingInfo.fillAddressFields(OroAddresses.US_LA_NEW_ORLEANS);
        checkoutPage.billingInfo.shipToBillingAddress();
        checkoutPage.billingInfo.clickContinue();
        String actualTaxAmount = checkoutPage.orderSummary.getTaxAmount();
        boolean isTaxCorrect = expectedTaxAmount.equals(actualTaxAmount);
        assertTrue(isTaxCorrect);
    }
}
