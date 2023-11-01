package com.vertex.quality.connectors.netsuite.oneworld.tests.certification;

import com.vertex.quality.common.enums.Country;
import com.vertex.quality.common.enums.State;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pages.transactions.NetsuiteTransactionsPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteAddress;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuitePrices;
import com.vertex.quality.connectors.netsuite.oneworld.pages.transactions.NetsuiteOWCashSalePage;
import com.vertex.quality.connectors.netsuite.oneworld.tests.base.OWCertBaseTest;
import com.vertex.quality.connectors.netsuite.oneworld.pages.transactions.NetsuiteOWInvoicePage;
import com.vertex.quality.connectors.netsuite.oneworld.pages.transactions.NetsuiteOWSalesOrderPage;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class OWCreditTests extends OWCertBaseTest {

    /**
     * Certifies Invoices, Credit Memos, Cash sales, and Refunds
     * CNSL-905
     */
    @Test(groups = { "netsuite_cert_ow"})
    protected void creditTaxJournalTests ()
    {
        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
        NetsuiteItem item = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("100.00")
                .taxCode(defaultTaxCode)
                .location("01: San Francisco")
                .build();
        NetsuiteItem item2 = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("100.00")
                .taxCode(defaultTaxCode)
                .build();
        NetsuiteAddress address = NetsuiteAddress
                .builder("91608")
                .fullAddressLine1("100 Universal City Plaza")
                .addressLine1("100 Universal City Plz")
                .city("01: San Francisco")
                .state(State.CA)
                .country(Country.USA)
                .zip9("91608-1002")
                .build();
        NetsuitePrices ExpectedValue = NetsuitePrices.builder()
                .itemTaxRate("8.25%")
                .itemTaxCode(defaultTaxCode)
                .subtotal("100.00")
                .taxAmount("8.25")
                .total("108.25").build();

        //Get menus
        NetsuiteNavigationMenus invoiceMenu = getCreateInvoiceMenu();
        NetsuiteNavigationMenus cashMenu = getCashSaleMenu();
        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        //Select a customer, make sure a valid address is populated
        NetsuiteOWInvoicePage invoicePage = setupManagerPage.navigationPane.navigateThrough(invoiceMenu);
        //Enter few items and select shipping address on each item and save the invoice
        setupBasicInvoice(invoicePage, customer, address, item);
        //Set post to Vertex
        invoicePage.setVertexPostToVertex();
        //Preview Tax and retrieve the values
        invoicePage.saveOrder();
        invoicePage.getVertexTaxErrorCode();
        invoicePage.editOrder();
        invoicePage.deleteOrder();

        // Cash Sales
        NetsuiteOWCashSalePage cashSalePage;
        cashSalePage = setupManagerPage.navigationPane.navigateThrough(cashMenu);
        //Enter few items and select shipping address on each item and save the invoice
        setupBasicCashSale(cashSalePage, customer,address, item2);
        //Set post to Vertex
        cashSalePage.setVertexPostToVertex();
        //Preview Tax and retrieve the values
        cashSalePage.saveOrder();
        cashSalePage.getVertexTaxErrorCode();
        cashSalePage.editOrder();
        cashSalePage.deleteOrder();
    }

    /**
     * Certify discounts
     * CNSL-906
     */
    @Test(groups = { "netsuite_cert_ow"})
    protected void discountInvoiceTests ()
    {
        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("100.00")
                .build();
        NetsuiteItem itemTwo = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("100.00")
                .build();
        NetsuitePrices ExpectedValueOne = NetsuitePrices.builder()
                .itemTaxRate("8.2556%")
                .subtotal("90.00")
                .taxAmount("7.43")
                .total("97.43").build();

        //Get menus
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        //Select a customer, make sure a valid address is populated
        NetsuiteOWSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
        //Enter few items and select shipping address on each item and save the order
        setupBasicOrder(salesOrderPage, customer, itemOne, itemTwo );
        //salesOrderPage.calculateShippingCost();
        salesOrderPage.selectPromotion("10% AutoApply");
        salesOrderPage.saveOrder();
        //Verify there is a tax associated with the order
        checkOrderWithTax(salesOrderPage, ExpectedValueOne, itemOne);
        //Verify the “Vertex Tax Error code” field is showing Success.
        salesOrderPage.getVertexTaxErrorCode();
        //Test Clean-up
        salesOrderPage.editOrder();
        salesOrderPage.deleteOrder();
    }

    /**
     * Certify Class and Product exemptions
     * CNSL-907
     */
    @Test(groups = { "netsuite_cert_ow"})
    protected void classAndProductExemptions ()
    {
        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("100.00")
                .build();
        NetsuiteItem itemTwo = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("100.00")
                .build();
        NetsuitePrices ExpectedValueOne = NetsuitePrices.builder()
                .itemTaxRate("8.25%")
                .subtotal("100.00")
                .taxAmount("8.25")
                .total("108.25").build();

        //Get menus
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        //Select a customer, make sure a valid address is populated
        NetsuiteOWSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
        //Enter few items and select shipping address on each item and save the order
        setupBasicOrder(salesOrderPage, customer, itemOne, itemTwo );
        //"Resale"
        //salesOrderPage.selectCustomer(NetsuiteCustomer.CUSTOMER_3M);
        salesOrderPage.saveOrder();
        //Verify there is a tax associated with the order
        checkOrderWithTax(salesOrderPage, ExpectedValueOne, itemOne);
        //Verify the “Vertex Tax Error code” field is showing Success.
        salesOrderPage.getVertexTaxErrorCode();
        //Test Clean-up
        salesOrderPage.editOrder();
        salesOrderPage.deleteOrder();

        //Select a customer, make sure a valid address is populated
        salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
        setupBasicOrder(salesOrderPage, customer, itemOne, itemTwo );
        //"Prosthetics"
        //salesOrderPage.selectCustomer(NetsuiteCustomer.CUSTOMER_3M);
        salesOrderPage.saveOrder();
        //Verify there is a tax associated with the order
        checkOrderWithTax(salesOrderPage, ExpectedValueOne, itemOne);
        //Verify the “Vertex Tax Error code” field is showing Success.
        salesOrderPage.getVertexTaxErrorCode();
        //Test Clean-up
        salesOrderPage.editOrder();
        salesOrderPage.deleteOrder();
    }

    /**
     * Certify Canadian Subsidiaries
     * CNSL-915
     */
    @Test(groups = { "netsuite_cert_ow"})
    protected void CanadianSubsidiariesTests ()
    {
        //Define Customers and Items
        //Select a Canadian customer with a Quebec City address
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_CANADIAN;
        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("114.00")
                .taxCode("CA-S-ON")
                .build();

        //Get menus
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        //Select a customer, make sure a valid address is populated
        NetsuiteOWSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);

        //Enter items selecting the tax code as Vertex Tax-CAN
        setupBasicOrder(salesOrderPage, customer, itemOne);

        salesOrderPage.calculateShippingCost();
        salesOrderPage.saveOrder();
        //Verify there is a tax associated with the order
        checkOrderHasTax(salesOrderPage);

        salesOrderPage.editOrder();
        salesOrderPage.deleteOrder();
    }

    /**
     * Certify Batch Jobs
     * CNSL-904
     */
    @Test(groups = { "netsuite_cert_ow"})
    protected void batchTests ()
    {
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
        // This item WILL be get processed by the Batch Script
        NetsuiteItem processedItem = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("100.00")
                .location("01: San Francisco")
                .build();

        // This Item will NOT get processed by the Batch Script
        NetsuiteItem nonProcessedItem = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("100.00")
                .location("01: San Francisco")
                .build();

        NetsuiteAddress addressOne = NetsuiteAddress
                .builder("91608")
                .fullAddressLine1("100 Universal City Plaza")
                .addressLine1("100 Universal City Plz")
                .city("01: San Francisco")
                .state(State.CA)
                .country(Country.USA)
                .zip9("91608-1002")
                .build();

        NetsuiteNavigationMenus InvoiceMenu = getCreateInvoiceMenu();
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        NetsuiteOWInvoicePage invoicePage = setupManagerPage.navigationPane.navigateThrough(InvoiceMenu);

        setupBasicInvoice(invoicePage, customer, addressOne, processedItem);
        invoicePage.selectTaxCode(processedItem, "-Not Taxable-");
        NetsuiteOWInvoicePage savedProcessedInvoicePage = invoicePage.saveOrder();

        String code = invoicePage.getVertexTaxErrorCode();

        savedProcessedInvoicePage =savedProcessedInvoicePage.editOrder();
        NetsuiteTransactionsPage transactionsPage = savedProcessedInvoicePage.deleteOrder();
        assertTrue(transactionsPage.isOrderDeleted());

        invoicePage = setupManagerPage.navigationPane.navigateThrough(InvoiceMenu);
        setupBasicInvoice(invoicePage, customer, addressOne, nonProcessedItem);
        invoicePage.selectTaxCode(processedItem, "Vertex");
        NetsuiteOWInvoicePage savedUnprocessedInvoicePage = invoicePage.saveOrder();

        code = invoicePage.getVertexTaxErrorCode();

        savedUnprocessedInvoicePage = savedUnprocessedInvoicePage.editOrder();
        transactionsPage = savedUnprocessedInvoicePage.deleteOrder();
        assertTrue(transactionsPage.isOrderDeleted());
    }

}
