package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.connectors.oraclecloud.pages.*;
import com.vertex.quality.connectors.oraclecloud.tests.base.OracleCloudBaseTest;
import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;

/**
 * Runs the standard one-off OM tests that should be
 * performed for each patch
 *
 * @author Tanmay Mody
 */

public class OracleCloudOmOneOffTests extends OracleCloudBaseTest{

    final String busUnitInput = "VTX_US_BU";
    final String billToAccount = "57004";

    /**
     * Create an Order in UI with Line 1 = $1000, ship-to-PA
     *
     * Jira Test Case: COERPC-3223
     */
    @Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_om_single" })
    public void omCreateOrderShipToPaTest( )
    {
        final String customer = "MCC Test Customer";
        final String item = "Item1000";
        final String expectedTotal = "1,060.00";

        loadInitialTestPage_ecogdev1();

        signInToHomePage();

        OracleCloudCreateOrderPage createOrderPage = navigateToCreateOrderPage();

        orderHeaderRequiredInfo(createOrderPage,customer,busUnitInput, billToAccount);
        createOrderPage.addItem(item);
        createOrderPage.clickSaveButton();

        String calculatedTotal = createOrderPage.getTotalTransTax();

        assertTrue(expectedTotal.equals(calculatedTotal));
    }

    /**
     * Create an Order in UI having price and qty up to 5 decimals
     *
     * Jira Test Case: COERPC-3231
     */
    @Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_om_single" })
    public void omCreateOrderUpToFiveDecimalsTest( )
    {
        final String customer = "MCC Test Customer";
        final String item = "Item100787878";
        final String quantity = "11.98657";
        final String expectedTotal = "12.80";

        loadInitialTestPage_ecogdev1();

        signInToHomePage();

        OracleCloudCreateOrderPage createOrderPage = navigateToCreateOrderPage();

        orderHeaderRequiredInfo(createOrderPage,customer,busUnitInput, billToAccount);
        createOrderPage.addItem(item);
        createOrderPage.editQuantity(quantity);
        createOrderPage.clickSaveButton();

        String calculatedTotal = createOrderPage.getTotalTransTax();

        assertTrue(expectedTotal.equals(calculatedTotal));
    }

    /**
     * Create an Order in UI with 2 lines one for $100 and other having price and qty up to 5 decimals
     *
     * Jira Test Case: COERPC-3234
     */
    @Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_om_single" })
    public void omCreateOrderTwoLinesTest( )
    {
        final String customer = "MCC Test Customer";
        final String item1 = "Item100787878";
        final String item2 = "Item1000";
        final String quantity = "11.98657";
        final String expectedTotal = "1,072.80";

        loadInitialTestPage_ecogdev1();

        signInToHomePage();

        OracleCloudCreateOrderPage createOrderPage = navigateToCreateOrderPage();

        orderHeaderRequiredInfo(createOrderPage,customer,busUnitInput, billToAccount);
        createOrderPage.addItem(item1);
        createOrderPage.editQuantity(quantity);
        createOrderPage.addItem(item2);
        createOrderPage.clickSaveButton();

        String calculatedTotal = createOrderPage.getTotalTransTax();

        assertTrue(expectedTotal.equals(calculatedTotal));
    }

    /**
     * Create an Order with Bad Ship To Address
     *
     * Jira Test Case: COERPC-3222
     */
    @Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_om_single" })
    public void omCreateOrderBadShipToAddressTest( )
    {
        final String customer = "MCC Test Customer";
        final String item = "Item1000";
        final String expectedTotal = "0.00";
        final String badAddress = "This is a bad";

        loadInitialTestPage_ecogdev1();

        signInToHomePage();

        OracleCloudCreateOrderPage createOrderPage = navigateToCreateOrderPage();

        orderHeaderRequiredInfo(createOrderPage,customer,busUnitInput, billToAccount);
        createOrderPage.editShipToAddress(badAddress);
        createOrderPage.addItem(item);
        createOrderPage.clickSaveButton();

        String calculatedTotal = createOrderPage.getTotalTransTax();

        assertTrue(expectedTotal.equals(calculatedTotal));
    }

    /**
     * Edit an Order in UI and Add Line 2
     *
     * Jira Test Case: COERPC-3225
     */
    @Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_om_single" })
    public void omEditOrderAddLineTest( )
    {
        final String customer = "MCC Test Customer";
        final String item = "Item1000";
        final String quantity = "4";
        final String expectedTotal = "1,060.00";
        final String expectedFinalTotal = "5,300.00";

        loadInitialTestPage_ecogdev1();

        signInToHomePage();

        OracleCloudCreateOrderPage createOrderPage = navigateToCreateOrderPage();

        orderHeaderRequiredInfo(createOrderPage,customer,busUnitInput, billToAccount);
        createOrderPage.addItem(item);
        createOrderPage.clickSaveButton();

        String calculatedTotal = createOrderPage.getTotalTransTax();
        String orderNumber = createOrderPage.getOrderNumber();

        assertTrue(expectedTotal.equals(calculatedTotal));
        signOffPage();

        loadInitialTestPage_ecogdev1();
        signInToHomePage();
        OracleCloudManageOrdersPage manageOrdersPage = navigateToManageOrdersPage();
        manageOrdersPage.searchOrderNumber(orderNumber);
        manageOrdersPage.editOrder();
        manageOrdersPage.addItem(item);
        manageOrdersPage.editQuantity(quantity);
        manageOrdersPage.clickSaveButton();

        String calculatedFinalTotal = manageOrdersPage.getTotalTransTax();

        assertTrue(expectedFinalTotal.equals(calculatedFinalTotal));
    }

    /**
     * Edit an Order in UI and Add Unreferenced Item
     *
     * Jira Test Case: COERPC-3226
     */
    @Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_om_single" })
    public void omEditOrderAddUnreferencedLineItemTest( )
    {
        final String customer = "MCC Test Customer";
        final String item = "Item1000";
        final String quantity = "4";
        final String expectedTotal = "4,240.00";
        final String unreferencedItem = "Item500";
        final String expectedFinalTotal = "3,710.00";

        loadInitialTestPage_ecogdev1();

        signInToHomePage();

        OracleCloudCreateOrderPage createOrderPage = navigateToCreateOrderPage();

        orderHeaderRequiredInfo(createOrderPage,customer,busUnitInput, billToAccount);
        createOrderPage.addItem(item);
        createOrderPage.editQuantity(quantity);
        createOrderPage.clickSaveButton();

        String calculatedTotal = createOrderPage.getTotalTransTax();
        String orderNumber = createOrderPage.getOrderNumber();

        assertTrue(expectedTotal.equals(calculatedTotal));
        signOffPage();

        loadInitialTestPage_ecogdev1();
        signInToHomePage();
        OracleCloudManageOrdersPage manageOrdersPage = navigateToManageOrdersPage();
        manageOrdersPage.searchOrderNumber(orderNumber);
        manageOrdersPage.editOrder();
        manageOrdersPage.addItemAsUnreferenced(unreferencedItem, "1000");
        manageOrdersPage.clickSaveButton();

        String calculatedFinalTotal = manageOrdersPage.getTotalTransTax();

        assertTrue(expectedFinalTotal.equals(calculatedFinalTotal));
    }

    /**
     * Edit an Order in UI and Delete Unreferenced Item
     *
     * Jira Test Case: COERPC-3230
     */
    @Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_om_single" })
    public void omEditOrderDeleteUnreferencedLineItemTest( )
    {
        final String customer = "MCC Test Customer";
        final String item = "Item1000";
        final String quantity = "3";
        final String expectedTotal = "3,710.00";
        final String unreferencedItem = "Item500";
        final String expectedFinalTotal = "4,240.00";

        loadInitialTestPage_ecogdev1();

        signInToHomePage();

        OracleCloudCreateOrderPage createOrderPage = navigateToCreateOrderPage();

        orderHeaderRequiredInfo(createOrderPage,customer,busUnitInput, billToAccount);
        createOrderPage.addItem(item);
        createOrderPage.editQuantity(quantity);
        createOrderPage.addItem(item);
        createOrderPage.addItemAsUnreferenced(unreferencedItem, "1000");
        createOrderPage.clickSaveButton();

        String calculatedTotal = createOrderPage.getTotalTransTax();
        String orderNumber = createOrderPage.getOrderNumber();

        assertTrue(expectedTotal.equals(calculatedTotal));
        signOffPage();

        loadInitialTestPage_ecogdev1();
        signInToHomePage();
        OracleCloudManageOrdersPage manageOrdersPage = navigateToManageOrdersPage();
        manageOrdersPage.searchOrderNumber(orderNumber);
        manageOrdersPage.editOrder();
        manageOrdersPage.deleteItemAsUnreferenced();

        manageOrdersPage.clickSaveButton();

        String calculatedFinalTotal = manageOrdersPage.getTotalTransTax();

        assertTrue(expectedFinalTotal.equals(calculatedFinalTotal));
    }

    /**
     * Edit an Order in UI and Change Line Quantity
     *
     * Jira Test Case: COERPC-3224
     */
    @Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_om_single" })
    public void omEditOrderChangeLineQuantityTest( )
    {
        final String customer = "MCC Test Customer";
        final String item = "Item1000";
        final String quantity = "4";
        final String expectedTotal = "1,060.00";
        final String expectedFinalTotal = "4,240.00";

        loadInitialTestPage_ecogdev1();

        signInToHomePage();

        OracleCloudCreateOrderPage createOrderPage = navigateToCreateOrderPage();

        orderHeaderRequiredInfo(createOrderPage,customer,busUnitInput, billToAccount);
        createOrderPage.addItem(item);
        createOrderPage.clickSaveButton();

        String calculatedTotal = createOrderPage.getTotalTransTax();
        String orderNumber = createOrderPage.getOrderNumber();

        assertTrue(expectedTotal.equals(calculatedTotal));
        signOffPage();

        loadInitialTestPage_ecogdev1();
        signInToHomePage();
        OracleCloudManageOrdersPage manageOrdersPage = navigateToManageOrdersPage();
        manageOrdersPage.searchOrderNumber(orderNumber);
        manageOrdersPage.editOrder();
        manageOrdersPage.editQuantity(quantity);
        manageOrdersPage.clickSaveButton();

        String calculatedFinalTotal = manageOrdersPage.getTotalTransTax();

        assertTrue(expectedFinalTotal.equals(calculatedFinalTotal));
    }
}
