package com.vertex.quality.connectors.netsuite.suiteTax.tests.certification;

import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuitePrices;
import com.vertex.quality.connectors.netsuite.suiteTax.pages.transactions.NetsuiteAPIInvoicePage;
import com.vertex.quality.connectors.netsuite.suiteTax.pages.transactions.NetsuiteAPISalesOrderPage;
import com.vertex.quality.connectors.netsuite.suiteTax.tests.base.NetsuiteBaseAPITest;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.testng.Assert.assertEquals;

/**
 * Tests Vertex Flexible Fields on a Sales order and an Invoice Line items
 *
 * @author ravunuri
 */
public class NetSuiteFlexibleFieldsTests extends NetsuiteBaseAPITest {
    /**
     * Tests Vertex Flexible Fields on a Sales order and an Invoice Line items
     * CNSAPI-1360   Ref: CNSAPI-1034, CNSAPI-740
     */
    @Test(groups = {"netsuite_suite_cert","netsuite_suite_smoke"})
    protected void flexFieldsSalesOrderInvoiceTest()
    {
        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
        String expectedTaxResult = "Success";
        String flexFieldLocationCode = "<FlexibleCodeField fieldId=\"1\">01: San Francisco</FlexibleCodeField>";
        String flexFieldTranDate = "<FlexibleDateField fieldId=\"2\">";
        String flexFieldTotalAmtNumber = "<FlexibleNumericField fieldId=\"3\">180.00</FlexibleNumericField>";
        String flexFieldQtyNumber = "<FlexibleNumericField fieldId=\"4\">1</FlexibleNumericField>";
        String location = "01: San Francisco";
        //Generate today's date
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
		String todayDate = sdf.format(date);

        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1").amount("100.00")
				.location("01: San Francisco")
                .build();
        NetsuiteItem itemTwo = NetsuiteItem
                .builder(NetsuiteItemName.SERVICE_ITEM)
                .quantity("1").amount("80.00")
				.location("01: San Francisco")
                .build();
        NetsuitePrices ExpectedValueOne = NetsuitePrices.builder()
                .subtotal("180.00")
                .taxAmount("17.10")
                .total("197.10")
                .build();
        //Get menus
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        //Select a customer, make sure a valid address is populated
        NetsuiteAPISalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
        //Enter few items and select shipping address on each item and save the order
        setupBasicOrder(salesOrderPage, customer, itemOne, itemTwo);
        salesOrderPage.enterSalesEffectiveDate(todayDate);
        salesOrderPage.setLocation(location);
        salesOrderPage.saveOrder();
        //Verify there is a tax associated with the order
        checkOrderWithTax(salesOrderPage, ExpectedValueOne, itemOne);
        //Verify the “Vertex Tax Error code” field is showing Success.
        salesOrderPage.getVertexTaxErrorCode();

        //Verify the “Vertex Tax Error code” field is showing 'Success' status.
        String actualSalesOrderResult = salesOrderPage.getVertexTaxErrorCode();
        assertEquals(actualSalesOrderResult, expectedTaxResult);

        //Click on each call details record and verify XML request for Flex fields display
        checkDocumentLogsXmlRequest(salesOrderPage, flexFieldLocationCode, flexFieldTranDate,
			flexFieldTotalAmtNumber, flexFieldQtyNumber);


        //-------Create a basic INVOICE and Validate XML Request for Flex Fields---------
        NetsuiteNavigationMenus InvoiceMenu = getCreateInvoiceMenu();
        NetsuiteAPIInvoicePage invoicePage = setupManagerPage.navigationPane.navigateThrough(InvoiceMenu);
        setupBasicOrder(invoicePage, customer, itemOne, itemTwo);
		invoicePage.enterSalesEffectiveDate(todayDate);
		invoicePage.setLocation(location);

        //Save an Invoice
        NetsuiteAPIInvoicePage savedInvoicePage = invoicePage.saveOrder();

        //Verify the “Vertex Tax Error code” field is showing 'Success' status.
        String actualInvoiceResult = savedInvoicePage.getVertexTaxErrorCode();
        assertEquals(actualInvoiceResult, expectedTaxResult);

        //Click on each call details record and verify XML request for Flex fields display.
        checkDocumentLogsXmlRequest(savedInvoicePage, flexFieldLocationCode, flexFieldTranDate,
                flexFieldTotalAmtNumber, flexFieldQtyNumber);
    }
}