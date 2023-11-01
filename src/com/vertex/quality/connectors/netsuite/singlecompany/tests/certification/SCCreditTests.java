package com.vertex.quality.connectors.netsuite.singlecompany.tests.certification;

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
import com.vertex.quality.connectors.netsuite.singlecompany.pages.transactions.NetsuiteSCCashSalePage;
import com.vertex.quality.connectors.netsuite.singlecompany.pages.transactions.NetsuiteSCInvoicePage;
import com.vertex.quality.connectors.netsuite.singlecompany.pages.transactions.NetsuiteSCSalesOrderPage;
import com.vertex.quality.connectors.netsuite.singlecompany.tests.base.SCCertBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class SCCreditTests extends SCCertBaseTest {

    /**
     * Certifies Invoices, Credit Memos, Cash sales, and Refunds
     * CNSL-900
     */
    @Test(groups = {"netsuite_cert_sc"})
    protected void multiFeatureTests ()
    {
        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_SUPPLY_CA;
        NetsuiteItem item = NetsuiteItem
                .builder(NetsuiteItemName.CHLOROFORM_ITEM)
                .quantity("1")
                .amount("100.00")
                .taxCode(defaultTaxCode)
                .location("San Francisco")
                .build();
        NetsuiteItem item2 = NetsuiteItem
                .builder(NetsuiteItemName.CHLOROFORM_ITEM)
                .quantity("1")
                .amount("100.00")
                .taxCode(defaultTaxCode)
                .location("San Francisco")
                .build();
        NetsuiteAddress address = NetsuiteAddress
                .builder("94134")
                .fullAddressLine1("100 University St")
                .addressLine1("100 University St")
                .city("San Francisco")
                .state(State.CA)
                .country(Country.USA)
                .zip9("94134-1302")
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
        NetsuiteSCInvoicePage invoicePage = setupManagerPage.navigationPane.navigateThrough(invoiceMenu);
        //Enter few items and select shipping address on each item and save the invoice
        setupBasicOrderItemList(invoicePage, customer, address, item);
        //Set post to Vertex
        invoicePage.setVertexPostToVertex();
        //Preview Tax and retrieve the values
        invoicePage.saveOrder();
        invoicePage.getVertexTaxErrorCode();
        invoicePage.editOrder();
        invoicePage.deleteOrder();

        // Cash Sales
        NetsuiteSCCashSalePage cashSalePage;
        cashSalePage = setupManagerPage.navigationPane.navigateThrough(cashMenu);
        //Enter few items and select shipping address on each item and save the invoice
        setupBasicOrderItemList(cashSalePage, customer, address, item);
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
     * CNSL-919
     */
    @Test(groups = { "netsuite_cert_sc"})
    protected void discountInvoiceTests ()
    {
        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_SUPPLY_CA;
        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.CHLOROFORM_ITEM)
                .quantity("1")
                .amount("100.00")
                .build();
        NetsuiteItem itemTwo = NetsuiteItem
                .builder(NetsuiteItemName.BLACKBERRY_PLAYBOOK)
                .quantity("1")
                .amount("499.00")
                .build();
        NetsuitePrices ExpectedValueOne = NetsuitePrices.builder()
                .itemTaxRate("7.0%")
                .subtotal("599.00")
                .taxAmount("41.93")
                .total("640.93").build();
        NetsuitePrices ExpectedValueTwo = NetsuitePrices.builder()
                .itemTaxRate("7.0%")
                .subtotal("599.00")
                .taxAmount("41.93")
                .total("640.93").build();

        //Get menus
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        //Select a customer, make sure a valid address is populated
        NetsuiteSCSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
        //Enter few items and select shipping address on each item and save the order
        setupBasicOrder(salesOrderPage, customer, itemOne, itemTwo );
        //salesOrderPage.calculateShippingCost();
        salesOrderPage.selectPromotion("10PercentOff");
        salesOrderPage.saveOrder();
        //Verify there is a tax associated with the order
        checkOrderWithTax(salesOrderPage, ExpectedValueOne, itemOne);
        //Verify the “Vertex Tax Error code” field is showing Success.
        salesOrderPage.getVertexTaxErrorCode();
        //Test Clean-up
        deleteDocument(salesOrderPage);

    }

    /**
     * Certify Class and Product exemptions
     * CNSL-920
     */
    @Test(groups = { "netsuite_cert_sc"})
    protected void classAndProductExemptions ()
    {
        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_SUPPLY_CA;
        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.CHLOROFORM_ITEM)
                .quantity("1")
                .amount("100.00")
                .build();
        NetsuiteItem itemTwo = NetsuiteItem
                .builder(NetsuiteItemName.BLACKBERRY_PLAYBOOK)
                .quantity("1")
                .amount("499.00")
                .build();
        NetsuitePrices ExpectedValueOne = NetsuitePrices.builder()
                .itemTaxRate("7.0%")
                .subtotal("599.00")
                .taxAmount("41.93")
                .total("640.93").build();


        //Get menus
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        //Select a customer, make sure a valid address is populated
        NetsuiteSCSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
        //Enter few items and select shipping address on each item and save the order
        setupBasicOrder(salesOrderPage, customer, itemOne, itemTwo );
        //"Resale"
        salesOrderPage.selectCustomer(NetsuiteCustomer.CUSTOMER_SUPPLY_CA);
        salesOrderPage.saveOrder();
        //Verify there is a tax associated with the order
        checkOrderWithTax(salesOrderPage, ExpectedValueOne, itemOne);
        //Verify the “Vertex Tax Error code” field is showing Success.
        salesOrderPage.getVertexTaxErrorCode();
        //Test Clean-up
        deleteDocument(salesOrderPage);

        salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
        setupBasicOrder(salesOrderPage, customer, itemOne, itemTwo );
        //"Prosthetics"
        salesOrderPage.selectCustomer(NetsuiteCustomer.CUSTOMER_SUPPLY_CA);
        salesOrderPage.saveOrder();
        //Verify there is a tax associated with the order
        checkOrderWithTax(salesOrderPage, ExpectedValueOne, itemOne);
        //Verify the “Vertex Tax Error code” field is showing Success.
        salesOrderPage.getVertexTaxErrorCode();
        //Test Clean-up
        deleteDocument(salesOrderPage);
    }

    /**
     * Certify Batch Jobs
     * CNSL-899
     */
    @Test(groups = { "netsuite_cert_sc"})
    protected void batchTests ()
    {
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_SUPPLY_CA;
        // This item WILL be get processed by the Batch Script
        NetsuiteItem processedItem = NetsuiteItem
                .builder(NetsuiteItemName.CHLOROFORM_ITEM)
                .quantity("1")
                .amount("100.00")
                .taxCode("-Not Taxable-")
                .location("San Francisco")
                .build();
        // This Item will NOT get processed by the Batch Script
        NetsuiteItem nonProcessedItem = NetsuiteItem
                .builder(NetsuiteItemName.BLACKBERRY_PLAYBOOK)
                .quantity("1")
                .amount("100.00")
                .taxCode("Vertex")
                .location("San Francisco")
                .build();

        NetsuiteAddress addressOne = NetsuiteAddress
                .builder("94134")
                .fullAddressLine1("100 University St")
                .addressLine1("100 University St")
                .city("San Francisco")
                .state(State.CA)
                .country(Country.USA)
                .zip9("94134-1302")
                .build();

        NetsuiteNavigationMenus InvoiceMenu = getCreateInvoiceMenu();
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        NetsuiteSCInvoicePage invoicePage = setupManagerPage.navigationPane.navigateThrough(InvoiceMenu);
        setupBasicOrderItemList(invoicePage, customer, addressOne, processedItem);
        NetsuiteSCInvoicePage savedProcessedInvoicePage = invoicePage.saveOrder();

        //String code = invoicePage.getVertexTaxErrorCode();

        savedProcessedInvoicePage =savedProcessedInvoicePage.editOrder();
        NetsuiteTransactionsPage transactionsPage = savedProcessedInvoicePage.deleteOrder();
        assertTrue(transactionsPage.isOrderDeleted());

//        invoicePage = setupManagerPage.navigationPane.navigateThrough(InvoiceMenu);
//        setupBasicInvoice(invoicePage, customer, addressOne, nonProcessedItem);
//        NetsuiteSCInvoicePage savedUnprocessedInvoicePage = invoicePage.saveOrder();
//
//        //code = invoicePage.getVertexTaxErrorCode();
//
//        savedUnprocessedInvoicePage = savedUnprocessedInvoicePage.editOrder();
//        transactionsPage = savedUnprocessedInvoicePage.deleteOrder();
//        assertTrue(transactionsPage.isOrderDeleted());
    }

}

