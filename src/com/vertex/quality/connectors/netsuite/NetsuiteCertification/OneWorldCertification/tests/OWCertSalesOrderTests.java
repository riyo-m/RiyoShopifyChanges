package com.vertex.quality.connectors.netsuite.NetsuiteCertification.OneWorldCertification.tests;

import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuitePrices;
import com.vertex.quality.connectors.netsuite.oneworld.pages.transactions.NetsuiteOWSalesOrderPage;
import org.testng.annotations.Test;

public class OWCertSalesOrderTests extends OWCertBasicTests {

    /**
     * Certifies Line item shipping works properly
     * CNSL-911
     */
    @Test(groups = { "netsuite_cert"})
    protected void lineItemShippingTests (){

        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("100.00")
                .build();
        NetsuiteItem itemTwo = NetsuiteItem
                .builder(NetsuiteItemName.ACC00003_ITEM)
                .quantity("1")
                .amount("175.00")
                .build();

        //Get menus
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        //Select a customer, make sure a valid address is populated
        NetsuiteOWSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
        //Check the “Enable Item Line Shipping” checkbox under items Subtab
        salesOrderPage.enableLineItemShipping();
        //Enter few items and select shipping address on each item and save the order
        setupBasicOrder(salesOrderPage, customer, itemOne, itemTwo );
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
     * CNSL-913
     */
    @Test(groups = { "netsuite_cert"})
    protected void canadaShippingTests (){
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
     * Certifies Tax Journal works properly
     * CNSL-912
     */
    @Test(groups = { "netsuite_cert"})
    protected void taxJournalTests (){

        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("100.00")
                .build();
        NetsuiteItem itemTwo = NetsuiteItem
                .builder(NetsuiteItemName.ACC00003_ITEM)
                .quantity("1")
                .amount("175.00")
                .build();

        //Get menus
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
//      Select a customer, make sure a valid address is populated
        NetsuiteOWSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
//      Check the “Enable Item Line Shipping” checkbox under items Subtab
        salesOrderPage.enableLineItemShipping();
//      Enter few items and select shipping address on each item and save the order
        setupBasicOrder(salesOrderPage, customer, itemOne, itemTwo );
        salesOrderPage.calculateShippingCost();
        salesOrderPage.saveOrder();
//      Verify there is a tax associated with the order
        checkOrderHasTax(salesOrderPage);
//      Click on the call details and verify the total tax on the response is same as the tax on the sales order.
//      Verify the “Vertex Tax Error code” field is showing Success.
        salesOrderPage.editOrder();
        salesOrderPage.deleteOrder();
    }

    /**
     * Certifies single shipping works properly
     * CNSL-914
     */
    @Test(groups = { "netsuite_cert"})
    protected void singleShippingTests (){

        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("100.00")
                .build();
        NetsuiteItem itemTwo = NetsuiteItem
                .builder(NetsuiteItemName.ACC00003_ITEM)
                .quantity("1")
                .amount("175.00")
                .build();
        NetsuitePrices ExpectedValueOne = NetsuitePrices.builder()
                .itemTaxRate("8.25%")
                .itemTaxCode(defaultTaxCode)
                .subtotal("100.00")
                .taxAmount("19.08")
                .total("108.50").build();
        NetsuitePrices ExpectedValueTwo = NetsuitePrices.builder()
                .itemTaxRate("8.2514%")
                .itemTaxCode(defaultTaxCode)
                .subtotal("131.25")
                .taxAmount("19.08")
                .total("142.08").build();

        //Get menus
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        //Select a customer, make sure a valid address is populated
        NetsuiteOWSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
        //Check the “Enable Item Line Shipping” checkbox under items Subtab
        salesOrderPage.enableLineItemShipping();
        //Enter few items and select shipping address on each item and save the order
        setupBasicOrder(salesOrderPage, customer, itemOne, itemTwo );
        salesOrderPage.calculateShippingCost();
        salesOrderPage.saveOrder();
        //Verify there is a tax associated with the order
        checkOrderWithTax(salesOrderPage, ExpectedValueOne, itemOne);
        checkOrderWithTax(salesOrderPage, ExpectedValueTwo, itemTwo);
        //Verify the “Vertex Tax Error code” field is showing Success.
        salesOrderPage.editOrder();
        salesOrderPage.deleteOrder();
    }
}
