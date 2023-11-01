package com.vertex.quality.connectors.netsuite.singlecompany.tests.certification;

import com.vertex.quality.connectors.netsuite.NetsuiteCertification.SingleCompanyCertification.tests.SCCertBasicTests;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuitePrices;
import com.vertex.quality.connectors.netsuite.singlecompany.pages.transactions.NetsuiteSCInvoicePage;
import com.vertex.quality.connectors.netsuite.singlecompany.pages.transactions.NetsuiteSCQuotePage;
import com.vertex.quality.connectors.netsuite.singlecompany.pages.transactions.NetsuiteSCSalesOrderPage;
import com.vertex.quality.connectors.netsuite.singlecompany.tests.base.SCCertBaseTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

public class SCCertSalesOrderTests extends SCCertBaseTest {

    /**
     * Certifies Line item shipping works properly
     * CNSL-924
     */
    @Test(groups = { "netsuite_cert_sc"})
    protected void lineItemShippingTests (){

        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_SUPPLY_CA;
        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.BLACKBERRY_PLAYBOOK)
                .quantity("1")
                .amount("499.00")
                .taxCode("CA-STATE")
                .build();
        NetsuiteItem itemTwo = NetsuiteItem
                .builder(NetsuiteItemName.BLACKBERRY_PLAYBOOK)
                .quantity("1")
                .amount("499.00")
                .taxCode("CA-STATE")
                .build();

        //Get menus
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        //Select a customer, make sure a valid address is populated
        NetsuiteSCSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
        //Check the “Enable Item Line Shipping” checkbox under items Subtab
        salesOrderPage.enableLineItemShipping();
        //Enter few items and select shipping address on each item and save the order
        setupBasicOrderItemList(salesOrderPage, customer, itemOne, itemTwo);
        salesOrderPage.calculateShippingCost();
        salesOrderPage.saveOrder();
        //Verify there is a tax associated with the order
        checkOrderHasTax(salesOrderPage);
        //Click on the call details and verify the total tax on the response is same as the tax on the sales order.
        //Verify the “Vertex Tax Error code” field is showing Success.

        //Test Clean-up
        deleteDocument(salesOrderPage);
    }

    /**
     * Certifies Sales order to Invoice function works properly
     * CNSL-925
     */
    @Test(groups = { "netsuite_cert_sc"})
        protected void salesOrdertoInvoiceTests (){
        //Create a sales order
        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_SUPPLY_CA;
        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.CHLOROFORM_ITEM)
                .quantity("1")
                .amount("100.00")
                .taxCode(defaultTaxCode)
                .build();
        //Get menus
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        NetsuiteSCSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
        //Enter item and save the order
        setupBasicOrderItemList(salesOrderPage, customer, itemOne);
        //Click on the “Fulfill” button. This will take you to the fulfillment page
        //Click on the “Save and Bill” button. This will save the fulfillment and open the invoice page
        NetsuiteSCInvoicePage invoicePage = salesOrderPage.fulfillSalesOrder();
        //Check the “VERTEX POST TO VERTEX” checkbox to post it real-time
        invoicePage.setVertexPostToVertex();
        //Save the invoice
        invoicePage.saveOrder();
        //Make sure tax is showing correctly.
        //Verify the “VERTEX TAX ERROR CODE” field is showing Success
        invoicePage.editOrder();
        invoicePage.deleteOrder();
    }

    /**
     * Certifies Tax journal works properly
     * CNSL-926
     */
    @Test(groups = { "netsuite_cert_sc"})
    protected void taxJournalTests (){

        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.CHLOROFORM_ITEM)
                .quantity("1")
                .amount("100.00")
                .build();

        //Get menus
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        //Select a customer, make sure a valid address is populated
        NetsuiteSCSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
        //Check the “Enable Item Line Shipping” checkbox under items Subtab
        salesOrderPage.enableLineItemShipping();
        //Enter few items and select shipping address on each item and save the order
        setupBasicOrder(salesOrderPage, customer, itemOne);
        salesOrderPage.calculateShippingCost();
        salesOrderPage.saveOrder();
        //Verify there is a tax associated with the order
        checkOrderHasTax(salesOrderPage);
        //Click on the call details and verify the total tax on the response is same as the tax on the sales order.
        //Verify the “Vertex Tax Error code” field is showing Success.
        salesOrderPage.editOrder();
        salesOrderPage.deleteOrder();
    }

    /**
     * Certifies Canadian shipping works properly
     * CNSL-927
     */
    @Test(groups = { "netsuite_cert_sc"})
    protected void canadaShippingTests (){
        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_SUPPLY_CA;
        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.CHLOROFORM_ITEM)
                .quantity("1")
                .amount("100.00")
                .taxCode(caliTaxCode)
                .build();
        NetsuitePrices ExpectedValueOne = NetsuitePrices.builder()
                .itemTaxRate("7.0%")
                .subtotal("107.00")
                .taxAmount("7.00")
                .total("107.00").build();

        //Get menus
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        //Select a customer, make sure a valid address is populated
        NetsuiteSCSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);

        //Enter items selecting the tax code as Vertex Tax-CAN
        setupBasicOrderItemList(salesOrderPage, customer, itemOne);

        salesOrderPage.calculateShippingCost();
        salesOrderPage.saveOrder();
        //Verify there is a tax associated with the order
        checkOrderHasTax(salesOrderPage);

        salesOrderPage.editOrder();
        salesOrderPage.deleteOrder();
    }

    /**
     * Certifies Tax Quotes work properly
     * CNSL-928
     */
    @Ignore
    @Test(groups = { "netsuite_cert_sc"})
    protected void taxQuoteTests (){
        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_SUPPLY_CA;
        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.CHLOROFORM_ITEM)
                .quantity("1")
                .amount("100.00")
                .build();
        //Get menus
        NetsuiteNavigationMenus quoteMenu = getQuoteMenu();
        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        //Select a customer, make sure a valid address is populated
        NetsuiteSCQuotePage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(quoteMenu);
        //Enter few items and select shipping address on each item and save the order
        setupBasicOrder(salesOrderPage, customer, itemOne);
        //salesOrderPage.calculateShippingCost();
        salesOrderPage.saveOrder();
        salesOrderPage.getVertexTaxErrorCode();
        checkOrderHasTax(salesOrderPage);

        deleteDocument(salesOrderPage);
    }

    /**
     * Certifies Single Shipping works properly
     * CNSL-929
     */
    @Test(groups = { "netsuite_cert_sc"})
    protected void singleShippingTests (){

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
        //salesOrderPage.calculateShippingCost();
        salesOrderPage.saveOrder();
        //Verify there is a tax associated with the order
        checkOrderWithTax(salesOrderPage, ExpectedValueOne, itemOne);
        //Verify the “Vertex Tax Error code” field is showing Success.

        //Test Clean-up
        deleteDocument(salesOrderPage);
    }
}
