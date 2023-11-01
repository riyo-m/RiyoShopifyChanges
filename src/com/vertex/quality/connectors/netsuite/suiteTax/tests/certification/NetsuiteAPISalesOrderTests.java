package com.vertex.quality.connectors.netsuite.suiteTax.tests.certification;


import com.vertex.quality.common.utils.selenium.VertexClickUtilities;
import com.vertex.quality.connectors.netsuite.suiteTax.pages.transactions.NetsuiteAPIInvoicePage;
import com.vertex.quality.connectors.netsuite.suiteTax.pages.transactions.NetsuiteAPISalesOrderPage;
import com.vertex.quality.connectors.netsuite.suiteTax.tests.base.NetsuiteBaseAPITest;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.oneworld.pages.transactions.NetsuiteOWInvoicePage;
import com.vertex.quality.connectors.netsuite.oneworld.pages.transactions.NetsuiteOWSalesOrderPage;
import com.vertex.quality.connectors.netsuite.singlecompany.pages.transactions.NetsuiteSCSalesOrderPage;
import org.openqa.selenium.By;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Tests products and product classes
 *
 * @author mwilliams, ravunuri
 */
public class NetsuiteAPISalesOrderTests extends NetsuiteBaseAPITest {


    /**
     * Checks that the document's XML contains the product's product class
     * CNSAPI-426
     */
    @Test(groups = {"netsuite_suite_cert","netsuite_suite_smoke"})
    protected void lineItemShippingTest() {

        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_SUPPLY_CA;
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
        NetsuiteAPISalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
        //Check the “Enable Item Line Shipping” checkbox under items Subtab
        salesOrderPage.enableLineItemShipping();
        //Enter few items and select shipping address on each item and save the order
        setupBasicOrder(salesOrderPage, customer, itemOne, itemTwo);
        salesOrderPage.calculateShippingCost();
        salesOrderPage.saveOrder();
        //Verify there is a tax associated with the order
        checkOrderHasTax(salesOrderPage);
    }

    /**
     * Checks that the document's XML contains the product's product class
     * CNSAPI-427
     */
    @Test(groups = {"netsuite_suite_cert","netsuite_suite_smoke"})
    protected void salesOrdertoInvoiceTest() {
        //Create a sales order
        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_SUPPLY_CA;
        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("100.00")
                .build();
        //Get menus
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        NetsuiteAPISalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
        //Enter item and save the order
        setupBasicOrder(salesOrderPage, customer, itemOne);
        NetsuiteAPIInvoicePage invoicePage = salesOrderPage.fulfillSalesOrderBill();
        invoicePage.saveOrder();
    }

    /**
     * Checks that the document's XML contains the product's product class
     * CNSAPI-428
     */
    @Test(groups = {"netsuite_suite_cert","netsuite_suite_smoke"})
    protected void taxJournalTest() {
        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("100.00")
                .build();

        //Get menus
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        //Select a customer, make sure a valid address is populated
        NetsuiteAPISalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
        //Check the “Enable Item Line Shipping” checkbox under items section
        salesOrderPage.enableLineItemShipping();
        //Enter few items and select shipping address on each item and save the order
        setupBasicOrder(salesOrderPage, customer, itemOne);
        salesOrderPage.calculateShippingCost();
        salesOrderPage.saveOrder();
        //Verify there is a tax associated with the order
        checkOrderHasTax(salesOrderPage);
    }

    /**
     * Checks that the document's XML contains the product's product class
     * CNSAPI-429
     */
    @Test(groups = {"netsuite_suite_cert","netsuite_suite_smoke"})
    protected void canadaShippingTest (){
        //Define Customers and Items
        //Select a Canadian customer with a Quebec City address
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_SUPPLY_CA;
        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("100.00")
                .build();

        //Get menus
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        //Select a customer, make sure a valid address is populated
        NetsuiteAPISalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);

        //Enter items selecting the tax code as Vertex Tax-CAN
        setupBasicOrder(salesOrderPage, customer, itemOne);

        salesOrderPage.calculateShippingCost();
        salesOrderPage.saveOrder();
        //Verify there is a tax associated with the order
        checkOrderHasTax(salesOrderPage);
    }

    /**
     * Checks that the document's XML contains the product's product class
     * CNSAPI-430
     */
    @Test(groups = {"netsuite_suite_cert","netsuite_suite_smoke"})
    protected void taxQuoteTest (){
        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_SUPPLY_CA;
        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("100.00")
                .build();
        //Get menus
        NetsuiteNavigationMenus quoteMenu = getQuoteMenu();
        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        //Select a customer, make sure a valid address is populated
        NetsuiteAPISalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(quoteMenu);
        //Enter few items and select shipping address on each item and save the order
        setupBasicOrder(salesOrderPage, customer, itemOne);
        salesOrderPage.calculateShippingCost();
        salesOrderPage.saveOrder();
        salesOrderPage.getVertexTaxErrorCode();
        checkOrderHasTax(salesOrderPage);
    }

    /**
     * Checks Tax Override feature
     * CNSAPI-1061
     */
    @Test(groups = {"netsuite_suite_cert","netsuite_suite_smoke"})
    protected void taxOverrideFeatureTest (){

        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_SUPPLIES_PA;
        String expectedTaxResult = " ";
        String shippingCost = "10.00";
        String taxNewAmt = "10.00";
        String expectedCallDetails = "No records to show.";
        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("100.00")
                .build();
        //Get menus
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        //Select a customer, make sure a valid address is populated
        NetsuiteAPISalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);

        //Enter few items and select shipping address on each item and save the order
        setupBasicOrder(salesOrderPage, customer, itemOne);

        //Enter Shipping Cost
        salesOrderPage.enterShippingCost(shippingCost);
        //Click Preview Tax and retrieve the values for validation
        salesOrderPage.previewTaxes();
        // Set 'TAX DETAILS OVERRIDE' Checkbox
        salesOrderPage.taxOverrideCheckbox();
        // Enter your tax details under Tas details>Taxes section and Save Sales Order
        salesOrderPage.enterTaxDetails(taxNewAmt);
        salesOrderPage.saveOrder();

        // Verify tax entered is not changed
        String taxDetails =  salesOrderPage.gettaxDetailstext();
        assertTrue(taxDetails.contains(taxNewAmt));

        //Click on the call details and verify no tax calls are made to Vertex
        String vertexCallDetailsText = salesOrderPage.getCallDetailstext();
        assertEquals(vertexCallDetailsText, expectedCallDetails);

        //Verify the “Vertex Tax Result” field has 'no text', which indicates no calls to VERTEX.
        String actualInvoiceResult = salesOrderPage.getVertexTaxErrorCode();
        assertEquals(actualInvoiceResult, expectedTaxResult);
    }
}
