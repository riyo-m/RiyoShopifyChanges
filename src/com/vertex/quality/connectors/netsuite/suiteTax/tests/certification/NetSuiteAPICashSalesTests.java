package com.vertex.quality.connectors.netsuite.suiteTax.tests.certification;

import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.suiteTax.pages.transactions.NetsuiteAPIInvoicePage;
import com.vertex.quality.connectors.netsuite.suiteTax.tests.base.NetsuiteBaseAPITest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Tests Cash Sale Negative amount posting transaction classes
 *
 * @author ravunuri
 */
public class NetSuiteAPICashSalesTests extends NetsuiteBaseAPITest {
    /**
     * Checks that the deleted Cash Sale Vertex log shows Negative amount posting transaction
     * CNSAPI-1386
     */
    @Test(groups = {"netsuite_suite_cert","netsuite_suite_smoke"})
    protected void cashSaleDeleteNegativeAmountTest()
    {
        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
        String expectedTaxResult = "Success";
        String negSubTotal = "<SubTotal>-100.0</SubTotal>";
        String negTotalAmount = "<Total>-109.5</Total>";
        String negTotalTax = "<TotalTax>-9.5</TotalTax>";
        String negExtendedPrice = "<ExtendedPrice>-100.0</ExtendedPrice>";
        String searchText = "Search: Vertex Log Search for debugging";

        NetsuiteItem item = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .amount("100.00")
                .location("01: San Francisco")
                .build();

        //Get menus - Cash Sales
        NetsuiteNavigationMenus cashMenu = getCashSaleMenu();
        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();

        NetsuiteAPIInvoicePage cashSalePage = setupManagerPage.navigationPane.navigateThrough(cashMenu);
        //Enter few items and save the cashSale
        setupBasicOrder(cashSalePage, customer, item);

        //Preview Tax and retrieve the values
        cashSalePage.saveOrder();

        //Verify the “Vertex Tax Error code” field is showing 'SUCCESS' status.
        String actualMessage = cashSalePage.getVertexTaxErrorCode();
        assertEquals(actualMessage, expectedTaxResult);

        //Delete a cash Sale record
        cashSalePage.editOrder();
        cashSalePage.deleteOrder();

        //Search & access 'Vertex log search for debugging' page to validate Negative amounts in the request
        cashSalePage.searchFieldLocator(searchText);

        String actualLogDetails = cashSalePage.showVertexLogDetails();
        assertTrue(actualLogDetails.contains(negExtendedPrice));
        assertTrue(actualLogDetails.contains(negTotalAmount));
        assertTrue(actualLogDetails.contains(negSubTotal));
        assertTrue(actualLogDetails.contains(negTotalTax));

    }


    /**
     * Checks that the Cash Sale REFUND document's XML contains Negative amount posting transaction
     * CNSAPI-1387
     */
    @Test(groups = {"netsuite_suite_cert","netsuite_suite_smoke"})
    protected void cashSaleRefundNegativeAmountTest()
    {
        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
        String expectedTaxResult = "Success";
        String negSubTotal = "<SubTotal>-100.0</SubTotal>";
        String negTotalAmount = "<Total>-109.5</Total>";
        String negTotalTax = "<TotalTax>-9.5</TotalTax>";
        String negExtendedPrice = "<ExtendedPrice>-100.0</ExtendedPrice>";

        NetsuiteItem item = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .amount("100.00")
                .location("01: San Francisco")
                .build();

        //Get menus - Cash Sales
        NetsuiteNavigationMenus cashMenu = getCashSaleMenu();
        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();

        NetsuiteAPIInvoicePage cashSalePage = setupManagerPage.navigationPane.navigateThrough(cashMenu);
        //Enter few items and save the cashSale
        setupBasicOrder(cashSalePage, customer, item);

        //Save order
        cashSalePage.saveOrder();

        //Verify the “Vertex Tax Error code” field is showing 'SUCCESS' status.
        String actualMessage = cashSalePage.getVertexTaxErrorCode();
        assertEquals(actualMessage, expectedTaxResult);

        //Click REFUND button on Cash Sale page and save refund record
        cashSalePage.refundSale();
        cashSalePage.saveOrder();

        //Click on the call details to verify refund cash sale XML response contains Negative amount posting transaction
        checkDocumentLogs(cashSalePage, negExtendedPrice, negTotalAmount, negSubTotal, negTotalTax);

    }
}
