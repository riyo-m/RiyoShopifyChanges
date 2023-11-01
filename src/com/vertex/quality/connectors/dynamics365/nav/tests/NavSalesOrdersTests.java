package com.vertex.quality.connectors.dynamics365.nav.tests;

import com.vertex.quality.connectors.dynamics365.nav.pages.*;
import com.vertex.quality.connectors.dynamics365.nav.tests.base.NavBaseTest;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Test Class for doc Type: Sales Order
 * @author dpatel
 */
public class NavSalesOrdersTests  extends NavBaseTest
{
    /**
     * CDNAV-483
     * Create Sales Order - Quotation
     * @author bhikshapathi
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void createSalesOrderQuotationTest() {
        String expectedTaxAmount = "60.05";
        String expectedTaxRate = "6.00";
        String locationCode = "EAST";
        String customerCode = "Test PA";
        String itemNumber = "1896-S";
        String quantity = "1";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab("Sales Orders");
        NavSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        fillInItemsTableInfo("Item",itemNumber, null, locationCode, quantity, null, 1);

        //Updated Location code in Shipping and Billing
        newSalesOrder.openShippingAndBillingCategory();
        newSalesOrder.updateLocationCodeFromShippingandBilling(locationCode);

        openVertexTaxDetailsWindow(newSalesOrder);
        String actualTaxRate = newSalesOrder.getTaxRate();
        String actualTaxAmount = newSalesOrder.getTaxAmount();

        assertEquals(expectedTaxAmount, actualTaxAmount);
        assertEquals(expectedTaxRate, actualTaxRate);

        newSalesOrder.closeTaxDetailsDialog();
        String  SalesOrderNumber = newSalesOrder.getOrderNumber();

        salesOrderSelectShipAndInvoiceThenPost(newSalesOrder);

        //Select light bulb and type vertex (this sends you to Vertex Admin)
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();
        // Check XML
        adminPage.openXmlLogCategory();
        //Tax Calc Request and Response
        adminPage.filterDocuments(SalesOrderNumber);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<TotalTax>"+expectedTaxAmount+"</TotalTax>"), "Total Tax  Validation Failed");
        assertTrue(xmlStr.contains("<SubTotal>1000.8</SubTotal>"), "SubTotal Tax Validation Failed");
        assertTrue(xmlStr.contains("<EffectiveRate>0.06</EffectiveRate>"), "Effective Rate Validation Failed");

    }
    /**
     * CDNAV-453
     * creates Sales Order And Post Invoice With Quantity 10
     * @author bhikshapathi
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void createsSalesOrderAndPostInvoiceWithQuantity10Test() {
        String expectedTaxAmount = "611.88";
        List<String> expectedTaxRate = Arrays.asList("6.00", "6.00");
        String customerCode = "C01540";
        String itemNumber = "1896-S";
        String itemNumber1 = "1908-S";
        String quantity = "1";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab("Sales Orders");
        NavSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        fillInItemsTableInfo("Item",itemNumber, null, "MAIN", "10", null, 1);
        newSalesOrder.activateRow(2);
        fillInItemsTableInfo("Item",itemNumber1, null, "EAST",quantity, null, 2);

        //Updated Location code in Shipping and Billing
        newSalesOrder.openShippingAndBillingCategory();
        newSalesOrder.updateLocationCodeFromShippingandBilling("EAST");

        openVertexTaxDetailsWindow(newSalesOrder);
        List<String> actualTaxRate = newSalesOrder.getMultipleTaxRates();
        newSalesOrder.closeTaxDetailsDialog();
        String actualTaxAmount = newSalesOrder.getTotalTaxAmount();
        assertEquals(expectedTaxAmount, actualTaxAmount);
        assertEquals(actualTaxRate, expectedTaxRate);

        NavSalesInvoicePage postedInvoice=salesOrderSelectShipAndInvoiceThenPost(newSalesOrder);
        actualTaxAmount = newSalesOrder.getTotalTaxAmount();
        assertEquals(expectedTaxAmount, actualTaxAmount);
        String postedOrderNum = postedInvoice.getCurrentSalesNumber();
        System.out.println(postedOrderNum);

        //Select light bulb and type vertex (this sends you to Vertex Admin)
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();
        // Check XML
        adminPage.openXmlLogCategory();
        //Tax Calc Request and Response
        adminPage.filterDocuments(postedOrderNum);
        adminPage.filterxml("Invoice Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<Quantity>10.0</Quantity>"), "Quantity Validation Failed");
        assertTrue(xmlStr.contains("<EffectiveRate>0.06</EffectiveRate>"), "Effective Rate Validation Failed");
        assertTrue(xmlStr.contains("<TotalTax>"+expectedTaxAmount+"</TotalTax>"), "Total Tax  Validation Failed");
    }
    /**
     * CDNAV-482
     * Create Sales Order - Quotation
     * @author bhikshapathi
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void createSalesOrderWithCustomerCodeExemptionTest() {
        List<String> expectedTaxRate = Arrays.asList("0.00", "0.00","0.00");
        String customerCode = "Geo Mason";
        String itemNumber = "1896-S";
        String quantity = "1";
        String locationCode = "EAST";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab("Sales Orders");
        NavSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        fillInItemsTableInfo("Item",itemNumber, null, locationCode, quantity, null, 1);

        openVertexTaxDetailsWindow(newSalesOrder);
        List<String> actualTaxRate = newSalesOrder.getMultipleTaxRates();
        newSalesOrder.closeTaxDetailsDialog();

        assertEquals(expectedTaxRate, actualTaxRate);

        NavSalesInvoicePage postedInvoice=salesOrderSelectShipAndInvoiceThenPost(newSalesOrder);
        String postedOrderNum = postedInvoice.getCurrentSalesNumber();
        openVertexTaxDetailsWindowFromInvoice(postedInvoice);
        actualTaxRate = postedInvoice.getMultipleTaxRates();
        postedInvoice.closeTaxDetailsDialog();
        assertEquals(expectedTaxRate, actualTaxRate);

        //Select light bulb and type vertex (this sends you to Vertex Admin)
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();
        // Check XML
        adminPage.openXmlLogCategory();
        //Tax Calc Request and Response
        adminPage.filterDocuments(postedOrderNum);
        adminPage.filterxml("Invoice Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<TotalTax>0.0</TotalTax>"), "Tax Amount Validation Failed");
        assertTrue(xmlStr.contains("classCode=\"CUST_TEST\""), "Customer Class Validation Failed");
        assertTrue(xmlStr.contains("taxResult=\"EXEMPT\""), "Tax Exemption Validation failed");

    }
    /**
     * CDNAV-469
     * Create Sales Order with Invoice
     * @author bhikshapathi
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void createSalesOrderWithInvoiceTest() {
        String expectedTaxAmount = "60.05";
        String expectedTaxRate = "6.00";

        String customerCode = "Test PA";
        String itemNumber = "1896-S";
        String quantity = "1";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab("Sales Orders");
        NavSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item",itemNumber, null, null, quantity, null, 1);
        newSalesOrder.exitExpandTable();


        openVertexTaxDetailsWindow(newSalesOrder);
        String actualTaxRate = newSalesOrder.getTaxRate();
        String actualTaxAmount = newSalesOrder.getTaxAmount();

        assertEquals(expectedTaxAmount, actualTaxAmount);
        assertEquals(expectedTaxRate, actualTaxRate);

        newSalesOrder.closeTaxDetailsDialog();

        NavSalesInvoicePage postedInvoice = salesOrderSelectShipAndInvoiceThenPost(newSalesOrder);
        String postedOrderNum = postedInvoice.getCurrentSalesNumber();
        openVertexTaxDetailsWindowFromInvoice(postedInvoice);
        actualTaxAmount = postedInvoice.getTaxAmount();
        assertEquals(expectedTaxAmount, actualTaxAmount);
        postedInvoice.closeTaxDetailsDialog();

        //Select light bulb and type vertex (this sends you to Vertex Admin)
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();
        // Check XML
        adminPage.openXmlLogCategory();
        //Tax Calc Request and Response
        adminPage.filterDocuments(postedOrderNum);
        adminPage.filterxml("Invoice Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<TotalTax>"+expectedTaxAmount+"</TotalTax>"), "Total Tax  Validation Failed");
        assertTrue(xmlStr.contains("<Taxable>1000.8</Taxable>"), "SubTotal Tax Validation Failed");
        assertTrue(xmlStr.contains("<EffectiveRate>0.06</EffectiveRate>"), "Effective Rate Validation Failed");

    }
    /**
     * CDNAV-478
     * create sales order with customer exemption certificate
     * then verify tax calculation
     */
    @Test(groups = {  "D365_NAV_Regression" })
    public void SalesOrderWithTaxExemptCertificateTest() {
        String expectedTaxRate = "0.00";
        String expectedTaxAmount = "0.00";
        String itemNumber = "1896-S";
        String itemNumber1 = "1906-s";
        String quantity = "10";
        String customerCode = "CustomerExemptCertTX";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab("Sales Orders");
        NavSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        fillInItemsTableInfo("Item",itemNumber, null, "MAIN", quantity, null, 1);
        newSalesOrder.activateRow(2);
        fillInItemsTableInfo("Item",itemNumber1, null, "EAST","1", null, 2);

        //Updated Location code in Shipping and Billing
        newSalesOrder.openShippingAndBillingCategory();
        newSalesOrder.updateLocationCodeFromShippingandBilling("EAST");

        openVertexTaxDetailsWindow(newSalesOrder);
        String actualTaxRate = newSalesOrder.getTaxRate();
        String actualTaxAmount = newSalesOrder.getTaxAmount();

        assertEquals(expectedTaxAmount, actualTaxAmount);
        assertEquals(expectedTaxRate, actualTaxRate);

        newSalesOrder.closeTaxDetailsDialog();
        NavSalesInvoicePage postedInvoice = salesOrderSelectShipAndInvoiceThenPost(newSalesOrder);
        String postedOrderNum = postedInvoice.getCurrentSalesNumber();
        openVertexTaxDetailsWindowFromInvoice(postedInvoice);
        actualTaxAmount = postedInvoice.getTaxAmount();
        assertEquals(expectedTaxAmount, actualTaxAmount);
        postedInvoice.closeTaxDetailsDialog();

        //Select light bulb and type vertex (this sends you to Vertex Admin)
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();
        // Check XML
        adminPage.openXmlLogCategory();
        //Tax Calc Request and Response
        adminPage.filterDocuments(postedOrderNum);
        adminPage.filterxml("Invoice Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<TotalTax>"+expectedTaxAmount+"</TotalTax>"), "Total Tax  Validation Failed");
        assertTrue(xmlStr.contains("<EffectiveRate>0.0</EffectiveRate>"), "Effective Rate Validation Failed");
        assertTrue(xmlStr.contains("<Quantity>10.0</Quantity>"), "Effective Rate Validation Failed");
        assertTrue(xmlStr.contains("<Quantity>1.0</Quantity>"), "Effective Rate Validation Failed");
        assertTrue(xmlStr.contains("<Taxes taxResult=\"EXEMPT\" taxType=\"SELLER_USE\" rateClassification=\"Exempt\" situs=\"DESTINATION\" taxCollectedFromParty=\"BUYER\">"), "Tax Result Validation Failed");
        assertTrue(xmlStr.contains("<Jurisdiction jurisdictionLevel=\"STATE\" jurisdictionId=\"35763\">TEXAS</Jurisdiction>"), "State Validation Failed");

    }
    /**
     * CDNAV-468
     * Create Sales Order with Invoice OFF
     * @author bhikshapathi
     */
    @Test(groups = { "D365_NAV_Regression_Special" })
    public void createSalesOrderWithInvoiceOFFTest() {

        String customerCode = "Test PA";
        String itemNumber = "1896-S";
        String itemNumber1 = "1908-S";
        String quantity = "1";
        String expectedTaxableAmount = "1,262.36";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab("Sales Orders");
        NavSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();
        // Set Invoice request enabled: OFF
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();
        adminPage.invoiceRequestEnabledOn();
        adminPage.closeAdminSection();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        fillInItemsTableInfo("Item",itemNumber, null, "MAIN", quantity, null, 1);
        newSalesOrder.activateRow(2);
        fillInItemsTableInfo("Item",itemNumber1, null, "EAST",quantity, null, 2);

        // update Location Code From Shipping and Billing section
        newSalesOrder.openShippingAndBillingCategory();
        newSalesOrder.updateLocationCodeFromShippingandBilling("WEST");

        openVertexTaxDetailsWindow(newSalesOrder);
        newSalesOrder.closeTaxDetailsDialog();

        String actualTaxableAmount = newSalesOrder.getTaxableAmount();
        assertEquals(expectedTaxableAmount, actualTaxableAmount);
        String  SalesOrderNumber = newSalesOrder.getCurrentSalesNumber();
        System.out.println(SalesOrderNumber);
        //Select light bulb and type vertex (this sends you to Vertex Admin)
        homePage.searchAndNavigateToVertexAdminPage();;
        // Check XML
        adminPage.openXmlLogCategory();
        //Tax Calc Request and Response
        adminPage.filterDocuments(SalesOrderNumber);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<EffectiveRate>0.06</EffectiveRate>"), "Effective Rate Validation Failed");
        adminPage.closeAdminSection();

        NavSalesInvoicePage postedInvoice = salesOrderSelectShipAndInvoiceThenPost(newSalesOrder);

        openVertexTaxDetailsWindowFromInvoice(postedInvoice);
        String poptext=postedInvoice.noTaxDetailsDialog();
        assertTrue(poptext.contains("No tax details on current document"), "Tax details Validation Failed");
        String postedOrderNum = postedInvoice.getCurrentSalesNumber();

        //Select light bulb and type vertex (this sends you to Vertex Admin)
        homePage.searchAndNavigateToVertexAdminPage();
        adminPage.filterDocuments(postedOrderNum);
        adminPage.filterxml("Invoice Response");
        String noXmlStr = adminPage.checkNoXmlLog();
        assertTrue(noXmlStr.contains("(There is nothing to show in this view)"), "xml Validation Failed");

        // Set Invoice request enabled: ON
        adminPage.invoiceRequestEnabledOn();
    }
    /**
     * CDNAV-454
     * Create Invoice with Shipping - change shipping address
     * @author bhikshapathi
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void createInvoiceWithShippingChangeShippingAddressTest() {
        String customerCode = "Test PA";
        String itemNumber = "1896-S";
        String quantity = "1";
        String expectedTaxRate="6.00";
        List<String> expectedTaxRates = Arrays.asList("2.90", "1.23", "3.07", "1.00");

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab("Sales Orders");
        NavSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        fillInItemsTableInfo("Item",itemNumber, null, null, quantity, null, 1);

        openVertexTaxDetailsWindow(newSalesOrder);
        String actualTaxRate = newSalesOrder.getTaxRate();

        assertEquals(expectedTaxRate, actualTaxRate);
        String  SalesOrderNumber = newSalesOrder.getOrderNumber();
        newSalesOrder.closeTaxDetailsDialog();

        //Select light bulb and type vertex (this sends you to Vertex Admin)
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();
        // Check XML
        adminPage.openXmlLogCategory();
        //Tax Calc Request and Response
        adminPage.filterDocuments(SalesOrderNumber);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<EffectiveRate>0.06</EffectiveRate>"), "Effective Rate Validation Failed");
        adminPage.SavecloseAdminSection();

        //Change Shipping Address
        newSalesOrder.openShippingAndBillingCategory();
        newSalesOrder.selectCustomShipToAddress();
        fillInCustomAddressInShippingAndBilling("COL","1575 Space Center Drive","Colorado Springs","CO","80915");

        openVertexTaxDetailsWindow(newSalesOrder);

        actualTaxRate = newSalesOrder.getTaxRate();

        assertEquals(expectedTaxRate, actualTaxRate);
        newSalesOrder.closeTaxDetailsDialog();

        NavSalesInvoicePage postedInvoice = salesOrderSelectShipAndInvoiceThenPost(newSalesOrder);
        postedInvoice.reCalculateAfterChangeShipToAddress();

        openVertexTaxDetailsWindowFromInvoice(postedInvoice);
        List<String> actualTaxRateInInvoice = postedInvoice.getMultipleTaxRates();

        assertEquals(expectedTaxRates, actualTaxRateInInvoice);
        newSalesOrder.closeTaxDetailsDialog();
        String postedInvoiceNum = postedInvoice.getCurrentSalesNumber();
        System.out.println(postedInvoiceNum);

        //Select light bulb and type vertex (this sends you to Vertex Admin)
        homePage.searchAndNavigateToVertexAdminPage();
        //Check Xml Tax Calc Request and Response
        adminPage.filterDocuments(postedInvoiceNum);
        adminPage.filterxml("Invoice Response");
        String invoiceXmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(invoiceXmlStr.contains("<Destination taxAreaId=\"60410090\">\n" +
                "              <StreetAddress1>1575 Space Center Drive</StreetAddress1>\n" +
                "              <City>Colorado Springs</City>\n" +
                "              <MainDivision>CO</MainDivision>\n" +
                "              <PostalCode>80915</PostalCode>\n" +
                "              <Country>US</Country>\n" +
                "            </Destination>"), "Address Validation Failed");
    }
    /**
     * CDNAV-444
     * Create Order with Discount - Multi line order amount Change Discount amount
     * @author bhikshapathi
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void createAndPostSalesOrderWithDiscountTest() {
        String customerCode = "Test PA";
        String itemNumber = "1896-S";
        String quantity = "1";
        String expectedTaxAmount = "5.40";
        String expectedTaxAmount1 = "4.80";
        String expectedTaxRate = "6.00";
        String taxableAmount = "95.40";
        String taxableAmount1 = "84.80";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab("Sales Orders");
        NavSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item",itemNumber, null, "WEST", quantity, "90", 1);

        openVertexTaxDetailsWindow(newSalesOrder);
        String actualTaxRate = newSalesOrder.getTaxRate();
        String actualTaxAmount = newSalesOrder.getTaxAmount();

        assertEquals(expectedTaxAmount, actualTaxAmount);
        assertEquals(expectedTaxRate, actualTaxRate);

        newSalesOrder.closeTaxDetailsDialog();
        String actualTaxableAmount = newSalesOrder.getTaxableAmount();
        assertEquals(taxableAmount, actualTaxableAmount);

        changedLineAmount("80", 1);

        openVertexTaxDetailsWindow(newSalesOrder);
        actualTaxRate = newSalesOrder.getTaxRate();
        actualTaxAmount = newSalesOrder.getTaxAmount();

        assertEquals(expectedTaxRate, actualTaxRate);
        assertEquals(expectedTaxAmount1, actualTaxAmount);
        newSalesOrder.closeTaxDetailsDialog();

        actualTaxableAmount = newSalesOrder.getTaxableAmount();
        assertEquals(taxableAmount1, actualTaxableAmount);

        NavSalesInvoicePage postedInvoice = salesOrderSelectShipAndInvoiceThenPost(newSalesOrder);
        openVertexTaxDetailsWindowFromInvoice(postedInvoice);
        actualTaxRate = postedInvoice.getTaxRate();
        actualTaxAmount = postedInvoice.getTaxAmount();

        assertEquals(expectedTaxRate, actualTaxRate);
        assertEquals(expectedTaxAmount1, actualTaxAmount);
        newSalesOrder.closeTaxDetailsDialog();

        String postedOrderNum = postedInvoice.getCurrentSalesNumber();
        System.out.println(postedOrderNum);
        //Select light bulb and type vertex (this sends you to Vertex Admin)
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();
        // Check XML
        adminPage.openXmlLogCategory();
        //Tax Calc Request and Response
        adminPage.filterDocuments(postedOrderNum);
        adminPage.filterxml("Invoice Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<CalculatedTax>4.8</CalculatedTax>"), "Calculated Tax Validation Failed");
        assertTrue(xmlStr.contains("<EffectiveRate>0.06</EffectiveRate>"), "Effective Rate Validation Failed");
        assertTrue(xmlStr.contains("<Taxable>80.0</Taxable>"), "Taxable Amount Validation Failed");
        assertTrue(xmlStr.contains("<ExtendedPrice>80.0</ExtendedPrice>"), "ExtendedPrice Validation Failed");
    }
    /**
     * CDNAV-470
     * Create Sales Order with Discount - Multi line order with Discount Line Percentage
     * @author bhikshapathi
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void createSalesOrderWithMultilineOrderWithDiscountLinePercentageTest() {

        String customerCode = "Change Discount";
        String itemNumber = "1850";
        String quantity = "1";
        String expectedTaxableAmount = "98.47";
        String expectedTaxRate="6.00";
        String expectedTaxAmount="5.58";
        String expectedPostedTaxAmount="5.58";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab("Sales Orders");
        NavSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item",itemNumber, null, null, quantity, "105.56", 1);
        newSalesOrder.activateRow(2);

        //Verify tax on vertex tax details
        openVertexTaxDetailsWindow(newSalesOrder);
        String actualTaxRate=newSalesOrder.getTaxRate();
        String actualTaxAmount = newSalesOrder.getTaxAmount();
        assertEquals(actualTaxRate, expectedTaxRate);
        assertEquals(expectedTaxAmount, actualTaxAmount);
        newSalesOrder.closeTaxDetailsDialog();
        String actualTaxableAmount = newSalesOrder.getTaxableAmount();
        assertEquals(expectedTaxableAmount, actualTaxableAmount);
        NavSalesInvoicePage postedInvoice = salesOrderSelectShipAndInvoiceThenPost(newSalesOrder);

        openVertexTaxDetailsWindowFromInvoice(postedInvoice);
        String actualTaxRatePosted = newSalesOrder.getTaxRate();
        assertEquals(expectedTaxRate, actualTaxRatePosted);
        String actualPostedTaxAmount = newSalesOrder.getTaxAmount();
        assertEquals(expectedPostedTaxAmount, actualPostedTaxAmount);
        assertEquals(actualTaxRate, expectedTaxRate);
        newSalesOrder.closeTaxDetailsDialog();
        String postedOrderNum = postedInvoice.getCurrentSalesNumber();

        //Select light bulb and type vertex (this sends you to Vertex Admin)
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();
        // Check XML
        adminPage.openXmlLogCategory();
        //Tax Calc Request and Response
        adminPage.filterDocuments(postedOrderNum);
        adminPage.filterxml("Invoice Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<TotalTax>"+expectedTaxAmount+"</TotalTax>"), "Total Tax  Validation Failed");
        assertTrue(xmlStr.contains("<SubTotal>92.89</SubTotal>"), "SubTotal Tax Validation Failed");
        assertTrue(xmlStr.contains("<EffectiveRate>0.06</EffectiveRate>"), "Effective Rate Validation Failed");
        assertTrue(xmlStr.contains("<ExtendedPrice>92.89</ExtendedPrice>"), "ExtendedPrice Validation Failed");
        }
    /**
     * CDNAV-471
     * Create Sales Order with Discount - Multi line order with Discount line Amount
     * @author bhikshapathi
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void createSalesOrderWithMultilineOrderWithDiscountLineAmountTest() {

        String customerCode = "Change Discount";
        String itemNumber = "1896-S";
        String itemNumber1 = "1908-S";
        String locationCode = "WEST";
        String quantity = "1";
        List<String> expectedTaxAmount = Arrays.asList("60.06", "11.40");
        List<String> expectedTaxRate = Arrays.asList("6.00", "6.00");
        String expectedTaxableAmount = "1,262.36";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab("Sales Orders");
        NavSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        fillInItemsTableInfo("Item",itemNumber, null, locationCode, quantity, "850", 1);
        newSalesOrder.activateRow(2);
        fillInItemsTableInfo("Item",itemNumber1, null, locationCode, quantity, "99", 2);

        newSalesOrder.openShippingAndBillingCategory();
        newSalesOrder.clearLocationCodeFromShippingandBilling();

        openVertexTaxDetailsWindow(newSalesOrder);
        List<String> actualTaxAmount = newSalesOrder.getMultipleTaxAmount();
        List<String> actualTaxRate = newSalesOrder.getMultipleTaxRates();

        assertEquals(expectedTaxAmount, actualTaxAmount);
        assertEquals(actualTaxRate, expectedTaxRate);

        newSalesOrder.closeTaxDetailsDialog();
        String actualTaxableAmount = newSalesOrder.getTaxableAmount();
        assertEquals(expectedTaxableAmount, actualTaxableAmount);

        NavSalesInvoicePage postedInvoice = salesOrderSelectShipAndInvoiceThenPost(newSalesOrder);

        openVertexTaxDetailsWindowFromInvoice(postedInvoice);

        actualTaxAmount = newSalesOrder.getMultipleTaxAmount();
        actualTaxRate = newSalesOrder.getMultipleTaxRates();

        assertEquals(expectedTaxAmount, actualTaxAmount);
        assertEquals(actualTaxRate, expectedTaxRate);

        newSalesOrder.closeTaxDetailsDialog();

        String postedOrderNum = postedInvoice.getCurrentSalesNumber();

        //Select light bulb and type vertex (this sends you to Vertex Admin)
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();
        // Check XML
        adminPage.openXmlLogCategory();
        //Tax Calc Request and Response
        adminPage.filterDocuments(postedOrderNum);
        adminPage.filterxml("Invoice Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<TotalTax>"+expectedTaxAmount+"</TotalTax>"), "Total Tax  Validation Failed");
        assertTrue(xmlStr.contains("<SubTotal>1190.9</SubTotal>"), "SubTotal Tax Validation Failed");
        assertTrue(xmlStr.contains("<EffectiveRate>0.06</EffectiveRate>"), "Effective Rate Validation Failed");
        assertTrue(xmlStr.contains("<ExtendedPrice>1000.8</ExtendedPrice>"), "ExtendedPrice Validation Failed");
          }
    /**
     * CDNAV-452
     * Create Sales Order, Create Invoice, add/delete lines, change quantity and location
     * @author bhikshapathi
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void CreateInvoiceAndUpdateLinesAndChangeQuantityAndLocationTest() {
        String customerCode = "Test PA";
        String itemNumber1 = "1896-S";
        String itemNumber = "1908-S";
        String itemNumber2 = "1960-S";
        String itemNumber3 = "1972-S";
        String locationCode = "EAST";
        String quantity = "1";
        String expectedTaxRate="6.00";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab("Sales Orders");
        NavSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        fillInItemsTableInfo("Item",itemNumber, null, null, quantity, null, 1);
        newSalesOrder.activateRow(2);
        fillInItemsTableInfo("Item",itemNumber1, null, locationCode, "10", null, 2);
        newSalesOrder.activateRow(3);
        fillInItemsTableInfo("Item",itemNumber2, null, locationCode, quantity, null, 3);

        openVertexTaxDetailsWindow(newSalesOrder);
        String actualTaxRate = newSalesOrder.getTaxRate();
        assertEquals(expectedTaxRate, actualTaxRate);

        newSalesOrder.closeTaxDetailsDialog();
        String postedOrderNum = newSalesOrder.getCurrentSalesNumber();

        //Select light bulb and type vertex (this sends you to Vertex Admin)
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();
        // Check XML
        adminPage.openXmlLogCategory();
        //Tax Calc Request and Response
        adminPage.filterDocuments(postedOrderNum);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<EffectiveRate>0.06</EffectiveRate>"), "Effective Rate Validation Failed");
        adminPage.closeAdminSection();
      
        // change Lines
        //newSalesOrder.
        newSalesOrder.salesEditNavMenu.selectNavigate();
        newSalesOrder.deleteLine(1);
        updateQuantity("5",2);
        newSalesOrder.activateRow(3);
        fillInItemsTableInfo("Item",itemNumber3, null, "EAST", quantity, null, 3);

        NavSalesInvoicePage postedInvoice = salesOrderSelectShipAndInvoiceThenPost(newSalesOrder);

        openVertexTaxDetailsWindowFromInvoice(postedInvoice);

        actualTaxRate = postedInvoice.getTaxRate();

        assertEquals(actualTaxRate, expectedTaxRate);

        newSalesOrder.closeTaxDetailsDialog();

        String postedInvoiceNum = postedInvoice.getCurrentSalesNumber();

        //Select light bulb and type vertex (this sends you to Vertex Admin)
        homePage.searchAndNavigateToVertexAdminPage();
        // Check XML
        //Tax Calc Request and Response
        adminPage.filterDocuments(postedInvoiceNum);
        adminPage.filterxml("Invoice Response");
        String invoiceXmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(invoiceXmlStr.contains("<Quantity>1.0</Quantity>"), "Quantity Validation Failed");
        assertTrue(invoiceXmlStr.contains("<Quantity>5.0</Quantity>"), "Quantity Validation Failed");
        assertTrue(invoiceXmlStr.contains("<TotalTax>11.41</TotalTax>"), "Quantity Validation Failed");
        assertTrue(invoiceXmlStr.contains("<TotalTax>57.85</TotalTax>"), "TotalTax Validation Failed");
        assertTrue(invoiceXmlStr.contains("<TotalTax>11.4</TotalTax>"), "TotalTax Validation Failed");
        assertTrue(invoiceXmlStr.contains("<EffectiveRate>0.06</EffectiveRate>"), "Effective Rate Validation Failed");
    }
    /**
     * CDNAV-450
     * Create Sales Order with Different Shipping And Billing address
     * @author bhikshapathi
     */
    @Test(groups = {  "D365_NAV_Regression" })
    public void createSalesOrderDifferentShipAndBillTest() {
        String customerCode = "Test PA";
        String itemNumber = "1896-S";
        String quantity = "1";
        String expectedTaxRate="6.00";
        String expectedTaxAmount="101.08";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab("Sales Orders");
        NavSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item",itemNumber, null, "WEST", quantity, null, 1);

        openVertexTaxDetailsWindow(newSalesOrder);
        String actualTaxRate = newSalesOrder.getTaxRate();
        assertEquals(expectedTaxRate, actualTaxRate);
        newSalesOrder.closeTaxDetailsDialog();

        //Ship-to: Alternate Shipping
        newSalesOrder.openShippingAndBillingCategory();
        newSalesOrder.selectAlternateShipToAddress("WA");
        //Recalculate tax
        clickRecalculateTax(newSalesOrder);
        String actualTaxAmount = newSalesOrder.getTotalTaxAmount();
        assertEquals(expectedTaxAmount, actualTaxAmount);

        String  SalesOrderNumber = newSalesOrder.getCurrentSalesNumber();
        //Select light bulb and type vertex (this sends you to Vertex Admin)
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();
        //Check XML
        adminPage.openXmlLogCategory();
        //Tax Calc Request and Response
        adminPage.filterDocuments(SalesOrderNumber);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<TotalTax>"+expectedTaxAmount+"</TotalTax>"), "Total Tax  Validation Failed");
    }
    /**
     * CDNAV-449
     * Create Sales Order with Invoice multi-lines with different locations
     * @author bhikshapathi
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void createSalesOrderWithInvoiceMultiLinesWithDifferentLocationsTest() {
        String customerCode = "Test PA";
        String itemNumber = "1896-S";
        String itemNumber1 = "1908-S";
        String itemNumber2 = "1960-S";
        String quantity = "1";
        String expectedTaxRate="6.00";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab("Sales Orders");
        NavSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        fillInItemsTableInfo("Item",itemNumber, null, "WEST", quantity, null, 1);
        newSalesOrder.activateRow(2);
        fillInItemsTableInfo("Item",itemNumber1, null, "MAIN", "10", null, 2);
        newSalesOrder.activateRow(3);
        fillInItemsTableInfo("Item",itemNumber2, null, "EAST", quantity, null, 3);

        openVertexTaxDetailsWindow(newSalesOrder);
        String actualTaxRate = newSalesOrder.getTaxRate();

        assertEquals(expectedTaxRate, actualTaxRate);

        newSalesOrder.closeTaxDetailsDialog();

        NavSalesInvoicePage postedInvoice = salesOrderSelectShipAndInvoiceThenPost(newSalesOrder);

        openVertexTaxDetailsWindowFromInvoice(postedInvoice);

        actualTaxRate = postedInvoice.getTaxRate();

        assertEquals(actualTaxRate, expectedTaxRate);

        newSalesOrder.closeTaxDetailsDialog();

        String postedOrderNum = postedInvoice.getCurrentSalesNumber();

        //Select light bulb and type vertex (this sends you to Vertex Admin)
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();
        // Check XML
        adminPage.openXmlLogCategory();
        //Tax Calc Request and Response
        adminPage.filterDocuments(postedOrderNum);
        adminPage.filterxml("Invoice Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<LineItem lineItemNumber=\"10000\" locationCode=\"WEST\">"), "LineItem Validation Failed");
        assertTrue(xmlStr.contains("<LineItem lineItemNumber=\"20000\" locationCode=\"MAIN\">"), "LineItem Validation Failed");
        assertTrue(xmlStr.contains("<LineItem lineItemNumber=\"30000\" locationCode=\"EAST\">"), "LineItem Validation Failed");
        assertTrue(xmlStr.contains("<EffectiveRate>0.06</EffectiveRate>"), "Effective Rate Validation Failed");
    }
    /**
     * CDNAV-448
     * Create Sales Order with Customer shipping location
     * @author bhikshapathi
     */
    @Test(groups = {  "D365_NAV_Regression" })
    public void createSalesOrderWithCustomerShippingLocationTest() {
        String customerCode = "Test PR";
        String itemNumber = "1896-S";
        String quantity = "1";
        List<String> expectedTaxRate = Arrays.asList("10.50", "1.00");

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab("Sales Orders");
        NavSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        fillInItemsTableInfo("Item",itemNumber, null, null, quantity, "90", 1);

        openVertexTaxDetailsWindow(newSalesOrder);
        List<String> actualTaxRate = newSalesOrder.getMultipleTaxRates();

        assertEquals(expectedTaxRate, actualTaxRate);

        newSalesOrder.closeTaxDetailsDialog();
        addLineLevelLocationCode("EAST", 1);

        NavSalesInvoicePage postedInvoice = salesOrderSelectShipAndInvoiceThenPost(newSalesOrder);
        openVertexTaxDetailsWindowFromInvoice(postedInvoice);

        actualTaxRate = postedInvoice.getMultipleTaxRates();

        assertEquals(actualTaxRate, expectedTaxRate);

        newSalesOrder.closeTaxDetailsDialog();

        String postedOrderNum = postedInvoice.getCurrentSalesNumber();

        //Select light bulb and type vertex (this sends you to Vertex Admin)
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();
        // Check XML
        adminPage.openXmlLogCategory();
        //Tax Calc Request and Response
        adminPage.filterDocuments(postedOrderNum);
        adminPage.filterxml("Invoice Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<TotalTax>10.35</TotalTax>"), "Total tax Validation Failed");
        assertTrue(xmlStr.contains("<ExtendedPrice>90.0</ExtendedPrice>"), "ExtendedPrice Validation Failed");
        assertTrue(xmlStr.contains("<EffectiveRate>0.105</EffectiveRate>"), "Effective Rate Validation Failed");
        assertTrue(xmlStr.contains("<EffectiveRate>0.01</EffectiveRate>"), "Effective Rate Validation Failed");

    }
    /**
     * CDNAV-427
     * Creates a new sales order and verifies the tax for no State Tax,
     * then posts that sales order and verifies the order number and tax
     * @author bhikshapathi
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void createAndPostSalesOrderWithNoStateTaxTest() {
        String expectedTaxAmount = "0.00";
        String expectedTaxRate = "0.00";
        String customerCode = "Cust_DE";
        String itemNumber = "1896-S";
        String quantity = "1";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab("Sales Orders");
        NavSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        fillInItemsTableInfo("Item",itemNumber, null, null, quantity, null, 1);

        newSalesOrder.openShippingAndBillingCategory();
        newSalesOrder.clearLocationCodeFromShippingandBilling();

        openVertexTaxDetailsWindow(newSalesOrder);
        String actualTaxRate = newSalesOrder.getTaxRate();
        String actualTaxAmount = newSalesOrder.getTaxAmount();

        assertEquals(expectedTaxAmount, actualTaxAmount);
        assertEquals(expectedTaxRate, actualTaxRate);

        newSalesOrder.closeTaxDetailsDialog();
        addLineLevelLocationCode("EAST",1);

        NavSalesInvoicePage postedInvoice = salesOrderSelectShipAndInvoiceThenPost(newSalesOrder);
        String postedOrderNum = postedInvoice.getCurrentSalesNumber();
        openVertexTaxDetailsWindowFromInvoice(postedInvoice);
        actualTaxRate = postedInvoice.getTaxRate();
        actualTaxAmount = postedInvoice.getTaxAmount();

        assertEquals(expectedTaxRate, actualTaxRate);
        assertEquals(expectedTaxAmount, actualTaxAmount);
        newSalesOrder.closeTaxDetailsDialog();
        postedInvoice.clickBackAndSaveArrow();

        //Select light bulb and type vertex (this sends you to Vertex Admin)
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();
        // Check XML
        adminPage.openXmlLogCategory();
        //Tax Calc Request and Response
        adminPage.filterDocuments(postedOrderNum);
        adminPage.filterxml("Invoice Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<CalculatedTax>0.0</CalculatedTax>"), "Calculated Tax Validation Failed");
        assertTrue(xmlStr.contains("<EffectiveRate>0.0</EffectiveRate>"), "Effective Rate Validation Failed");
        assertTrue(xmlStr.contains("<TotalTax>"+expectedTaxAmount+"</TotalTax>"), "Total Tax  Validation Failed");
        assertTrue(xmlStr.contains("<MainDivision>DE</MainDivision>"), "Main Division Validation Failed");

    }
    /**
     * CDNAV-428
     * Creates a new sales order and verifies  no State Tax only City tax,
     * then posts that sales order and verifies the order number and tax
     * @author bhikshapathi
     */
    @Test(groups = {  "D365_NAV_Regression" })
    public void createAndPostSalesOrderWithNoStateTaxOnlyCityTaxTest() {

        String expectedTaxAmount = "50.04";
        List<String> expectedTaxRate = Arrays.asList("0.00", "0.00", "5.00");
        String customerCode = "Test C365BC 224";
        String itemNumber = "1896-S";
        String quantity = "1";
        String locationCode = "EAST";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab("Sales Orders");
        NavSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        fillInItemsTableInfo("Item",itemNumber, null, null, quantity, null, 1);

        newSalesOrder.openShippingAndBillingCategory();
        newSalesOrder.clearLocationCodeFromShippingandBilling();

        String actualTaxAmount = newSalesOrder.getTotalTaxAmount();

        openVertexTaxDetailsWindow(newSalesOrder);
        List<String> actualTaxRate = newSalesOrder.getMultipleTaxRates();

        assertEquals(expectedTaxAmount, actualTaxAmount);
        assertEquals(expectedTaxRate, actualTaxRate);

        newSalesOrder.closeTaxDetailsDialog();
        addLineLevelLocationCode(locationCode, 1);
        
        NavSalesInvoicePage postedInvoice = salesOrderSelectShipAndInvoiceThenPost(newSalesOrder);
        String postedOrderNum = postedInvoice.getCurrentSalesNumber();

        actualTaxAmount = postedInvoice.getTotalTaxAmount();

        openVertexTaxDetailsWindowFromInvoice(postedInvoice);
        List<String> actualTaxRateInInvoice = postedInvoice.getMultipleTaxRates();

        assertEquals(expectedTaxRate, actualTaxRateInInvoice);
        assertEquals(expectedTaxAmount, actualTaxAmount);
        newSalesOrder.closeTaxDetailsDialog();
        postedInvoice.clickBackAndSaveArrow();
        //Select light bulb and type vertex (this sends you to Vertex Admin)
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();
        // Check XML
        adminPage.openXmlLogCategory();
        //Tax Calc Request and Response
        adminPage.filterDocuments(postedOrderNum);
        adminPage.filterxml("Invoice Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<CalculatedTax>50.04</CalculatedTax>"), "Calculated Tax Validation Failed");
        assertTrue(xmlStr.contains("<EffectiveRate>0.05</EffectiveRate>"), "Effective Rate Validation Failed");
        assertTrue(xmlStr.contains("<TotalTax>"+expectedTaxAmount+"</TotalTax>"), "Total Tax  Validation Failed");

    }
    /**
     * CDNAV-472
     * Create Sales Order with Discount - Multi Line Order Payment Discount Amount
     * then verify tax calculation
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void SalesOrderWithWithDiscountMultiLineOrderPaymentDiscountAmountTest() {
        String expectedTaxRate = "6.00";
        String expectedTaxAmount = "60.06";
        String itemNumber = "1896-S";
        String itemNumber1 = "1908-S";
        String quantity = "1";
        String customerCode = "Test PA";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab("Sales Orders");
        NavSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        fillInItemsTableInfo("Item",itemNumber, null, "WEST", quantity, null, 1);
        newSalesOrder.activateRow(2);
        fillInItemsTableInfo("Item",itemNumber1, null, "WEST",quantity, null, 2);

        // Payment Discount %: 5
        newSalesOrder.selectPaymentDiscount("5");

        openVertexTaxDetailsWindow(newSalesOrder);
        String actualTaxRate = newSalesOrder.getTaxRate();
        String actualTaxAmount = newSalesOrder.getTaxAmount();

        assertEquals(expectedTaxAmount, actualTaxAmount);
        assertEquals(expectedTaxRate, actualTaxRate);

        newSalesOrder.closeTaxDetailsDialog();

        NavSalesInvoicePage postedInvoice = salesOrderSelectShipAndInvoiceThenPost(newSalesOrder);
        String postedOrderNum = postedInvoice.getCurrentSalesNumber();
        openVertexTaxDetailsWindowFromInvoice(postedInvoice);
        actualTaxAmount = postedInvoice.getTaxAmount();
        assertEquals(expectedTaxAmount, actualTaxAmount);
        postedInvoice.closeTaxDetailsDialog();

        //Select light bulb and type vertex (this sends you to Vertex Admin)
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();
        // Check XML
        adminPage.openXmlLogCategory();
        //Tax Calc Request and Response
        adminPage.filterDocuments(postedOrderNum);
        adminPage.filterxml("Invoice Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<TotalTax>"+expectedTaxAmount+"</TotalTax>"), "Total Tax  Validation Failed");
        assertTrue(xmlStr.contains("<Taxable>1000.8</Taxable>"), "Taxable amount Validation Failed");
        assertTrue(xmlStr.contains("<EffectiveRate>0.06</EffectiveRate>"), "Effective Rate Validation Failed");

    }
    /**
     * Tests creating a sales order with a customer with an exempt class and posting it to an invoice,
     * ensuring the tax is properly shown as 0 (exempt)
     *
     * CDNAV-481
     */
    @Test(groups = {  "D365_NAV_Regression" })
    public void exemptCustomerClassTest( )
    {
        String expectedTaxAmount = "0.00";
        String customerCode = "CustomerClassExemptPA ";
        String itemNumber = "1896-S";
        String itemNumber1 = "1908-S";
        String quantity = "1";
        String quantityFive = "5";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab("Sales Orders");
        NavSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        fillInItemsTableInfo("Item",itemNumber, null, "EAST", quantity, null, 1);
        newSalesOrder.activateRow(2);
        fillInItemsTableInfo("Item",itemNumber1, null, "EAST",quantityFive  , null, 2);

        newSalesOrder.openShippingAndBillingCategory();
        newSalesOrder.clearLocationCodeFromShippingandBilling();

        String actualTaxAmount = newSalesOrder.getTotalTaxAmount();
        assertEquals(actualTaxAmount, expectedTaxAmount);

        NavSalesInvoicePage postedInvoice = salesOrderSelectShipAndInvoiceThenPost(newSalesOrder);
        String postedOrderNum = postedInvoice.getCurrentSalesNumber();
        openVertexTaxDetailsWindowFromInvoice(postedInvoice);
        actualTaxAmount = postedInvoice.getTaxAmount();
        assertEquals(expectedTaxAmount, actualTaxAmount);
        postedInvoice.closeTaxDetailsDialog();

        //Select light bulb and type vertex (this sends you to Vertex Admin)
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();
        // Check XML
        adminPage.openXmlLogCategory();
        //Tax Calc Request and Response
        adminPage.filterDocuments(postedOrderNum);
        adminPage.filterxml("Invoice Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<TotalTax>"+expectedTaxAmount+"</TotalTax>"), "Total Tax  Validation Failed");
    }
    /**
     * Tests creating a sales order With Product Code Exemption and posting it to an invoice,
     * ensuring the tax is properly shown as 0 (exempt)
     *
     * CDNAV-480
     */
    @Test(groups = {  "D365_NAV_Regression" })
    public void SalesOrderWithProductCodeExemptionTest( )
    {
        String customerCode = "Test PA ";
        List<String> expectedTaxAmount = Collections.singletonList("0.00");
        List<String> expectedTaxRate = Collections.singletonList("0.00");
        String itemNumber = "1000";
        String quantity = "1";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab("Sales Orders");
        NavSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        fillInItemsTableInfo("Item",itemNumber, null, "EAST", quantity, null, 1);


        openVertexTaxDetailsWindow(newSalesOrder);
        List<String> actualTaxAmount = newSalesOrder.getMultipleTaxAmount();
        List<String> actualTaxRate = newSalesOrder.getMultipleTaxRates();

        assertEquals(actualTaxRate, expectedTaxRate);
        assertEquals(expectedTaxAmount, actualTaxAmount);
        newSalesOrder.closeTaxDetailsDialog();

        NavSalesInvoicePage postedInvoice = salesOrderSelectShipAndInvoiceThenPost(newSalesOrder);
        String postedOrderNum = postedInvoice.getCurrentSalesNumber();
        openVertexTaxDetailsWindowFromInvoice(postedInvoice);
        actualTaxAmount = postedInvoice.getMultipleTaxAmount();
        actualTaxRate = postedInvoice.getMultipleTaxRates();
        assertEquals(actualTaxRate, expectedTaxRate);
        assertEquals(expectedTaxAmount, actualTaxAmount);
        postedInvoice.closeTaxDetailsDialog();

        //Select light bulb and type vertex (this sends you to Vertex Admin)
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();
        // Check XML
        adminPage.openXmlLogCategory();
        //Tax Calc Request and Response
        adminPage.filterDocuments(postedOrderNum);
        adminPage.filterxml("Invoice Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<TotalTax>"+expectedTaxAmount+"</TotalTax>"), "Total Tax  Validation Failed");
    }
    /**
     * Tests creating a sales order With Product Class Exemption  and posting it to an invoice,
     * ensuring the tax is properly shown as 0 (exempt)
     * CDNAV-479
     */
    @Test(groups = {  "D365_NAV_Regression" })
    public void SalesOrderWithProductClassExemptionTest( )
    {
        List<String> expectedTaxAmount = Collections.singletonList("0.00");
        List<String> expectedTaxRate = Collections.singletonList("0.00");
        String customerCode = "Test PA ";
        String quantityFive = "5";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab("Sales Orders");
        NavSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        newSalesOrder.expandTable();
        fillInItemsTableInfo("G/L Account","12200", null, "EAST", quantityFive, "100", 1);
        newSalesOrder.exitExpandTable();

        openVertexTaxDetailsWindow(newSalesOrder);
        List<String> actualTaxAmount = newSalesOrder.getMultipleTaxAmount();
        List<String> actualTaxRate = newSalesOrder.getMultipleTaxRates();

        assertEquals(actualTaxRate, expectedTaxRate);
        assertEquals(expectedTaxAmount, actualTaxAmount);
        newSalesOrder.closeTaxDetailsDialog();

        NavSalesInvoicePage postedInvoice = salesOrderSelectShipAndInvoiceThenPost(newSalesOrder);
        String postedOrderNum = postedInvoice.getCurrentSalesNumber();
        openVertexTaxDetailsWindowFromInvoice(postedInvoice);
        actualTaxAmount = postedInvoice.getMultipleTaxAmount();
        actualTaxRate = postedInvoice.getMultipleTaxRates();
        assertEquals(actualTaxRate, expectedTaxRate);
        assertEquals(expectedTaxAmount, actualTaxAmount);
        postedInvoice.closeTaxDetailsDialog();

        //Select light bulb and type vertex (this sends you to Vertex Admin)
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();
        // Check XML
        adminPage.openXmlLogCategory();
        //Tax Calc Request and Response
        adminPage.filterDocuments(postedOrderNum);
        adminPage.filterxml("Invoice Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<EffectiveRate>0.0</EffectiveRate>"), "Effective Rate Validation Failed");
    }
    /**
     * CDNAV-429
     * Creates a new sales order and verifies  no State Tax only City tax,
     * then posts that sales order and verifies the order number and tax
     */
    @Test(groups = {  "D365_NAV_Regression" })
    public void createAndPostSalesOrderWithModifiedOriginStateTest() {
        String expectedTaxAmount = "95.08";
        List<String> expectedTaxRate = Arrays.asList("6.00", "1.25", "0.50", "0.50", "0.50", "0.50", "0.25");

        String customerCode = "ModifiedOriginState";
        String itemNumber = "1896-S";
        String quantity = "1";
        String locationCode = "EAST";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab("Sales Orders");
        NavSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
        fillInItemsTableInfo("Item",itemNumber, null, null, quantity, null, 1);

        newSalesOrder.openShippingAndBillingCategory();
        newSalesOrder.clearLocationCodeFromShippingandBilling();

        String actualTaxAmount = newSalesOrder.getTotalTaxAmount();

        openVertexTaxDetailsWindow(newSalesOrder);
        List<String> actualTaxRate = newSalesOrder.getMultipleTaxRates();

        assertEquals(expectedTaxAmount, actualTaxAmount);
        assertEquals(actualTaxRate, expectedTaxRate);

        newSalesOrder.closeTaxDetailsDialog();
        addLineLevelLocationCode(locationCode, 1);

        NavSalesInvoicePage postedInvoice = salesOrderSelectShipAndInvoiceThenPost(newSalesOrder);
        String postedOrderNum = postedInvoice.getCurrentSalesNumber();
        System.out.println(postedOrderNum);

        actualTaxAmount = postedInvoice.getTotalTaxAmount();

        openVertexTaxDetailsWindowFromInvoice(postedInvoice);
        List<String> actualTaxRateInInvoice = postedInvoice.getMultipleTaxRates();

        assertEquals(expectedTaxRate, actualTaxRateInInvoice);
        assertEquals(expectedTaxAmount, actualTaxAmount);
        newSalesOrder.closeTaxDetailsDialog();


        //Select light bulb and type vertex (this sends you to Vertex Admin)
        NavVertexAdminPage adminPage = homePage.searchAndNavigateToVertexAdminPage();
        // Check XML
        adminPage.openXmlLogCategory();
        //Tax Calc Request and Response
        adminPage.filterDocuments(postedOrderNum);
        adminPage.filterxml("Invoice Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<CalculatedTax>60.07</CalculatedTax>"), "Calculated Tax Validation Failed");
        assertTrue(xmlStr.contains("<EffectiveRate>0.06</EffectiveRate>"), "Effective Rate Validation Failed");
        assertTrue(xmlStr.contains("<TotalTax>"+expectedTaxAmount+"</TotalTax>"), "Total Tax  Validation Failed");
    }
    /**
     * CDNAV-447
     * creates a new sales quote and verifies the calculated tax at the quote level
     * then converts the quote to a sales order and verifies the calculated tax at the order level
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void createSalesQuoteAndChangeToOrderTest() {
        String expectedTaxRate = "6.00";
        String expectedTaxAmount = "60.05";
        String quantity = "1";
        String itemNumber = "1896-S";
        String customerCode = "Test PA";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesQuotesListPage sales_quotes = homePage.mainNavMenu.goToSubNavTab("Sales Quotes");
        NavSalesQuotesPage newSalesQuotes = sales_quotes.pageNavMenu.clickNew();

        fillInSalesQuoteGeneralInfo(newSalesQuotes, customerCode);
        fillInItemsTableInfo("Item",itemNumber, null, null, quantity,null, 1);

        openVertexTaxDetailsFromQuote(newSalesQuotes);

        String actualTaxRate = newSalesQuotes.getTaxRate();
        String actualTaxAmount = newSalesQuotes.getTaxAmount();

        assertEquals(expectedTaxAmount, actualTaxAmount);
        assertEquals(expectedTaxRate, actualTaxRate);

        newSalesQuotes.closeTaxDetailsDialog();

        NavSalesOrderPage thisSalesOrder = newSalesQuotes.convertQuoteToOrder();

        openVertexTaxDetailsWindow(thisSalesOrder);
        actualTaxRate = thisSalesOrder.getTaxRate();
        actualTaxAmount = thisSalesOrder.getTaxAmount();

        assertEquals(expectedTaxAmount, actualTaxAmount);
        assertEquals(expectedTaxRate, actualTaxRate);
        thisSalesOrder.closeTaxDetailsDialog();
    }

    /**
     * CDNAV-513
     * Creates a new sales order for a nonVertex customer and verifies that the
     * Total Tax and the Statistics tax are the same
     * @author mariosaint-fleur
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void compareStatisticsTaxAmountForSalesOrderNonVertexTest(){
        String customerCode = "Cust_NoVertex";
        String quantity = "1";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesOrderListPage salesOrder = homePage.mainNavMenu.goToSubNavTab("Sales Orders");
        NavSalesOrderPage newSalesOrder = salesOrder.pageNavMenu.clickNew();
        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);

        newSalesOrder.expandTable();

        fillInItemsTableInfo("Item", "1896-S", null, null, quantity, null, 1);

        newSalesOrder.exitExpandTable();

        //Navigate to Statistics and get Sales Quote TaxAmount
        navigateToStatWindow();
        String actualTaxAmount1 = newSalesOrder.getNoVertexTaxAmount();
        newSalesOrder.closeTaxDetailsDialog();
        String actualTotalTaxAmount = newSalesOrder.getTotalTaxAmount();
        assertEquals(actualTotalTaxAmount,actualTaxAmount1);
    }
    /**
     * CDNAV-512
     * Tests Statistics Functionality for Sales Order TaxAmount
     * @author Shruti Jituri
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void checkSalesOrderTaxAmountInStatisticsTest(){
        String customerCode = "test1234";
        String quantity = "1";
        String itemNumber = "1896-S";
        String nontaxableCode="SUPPLIES";

        //Navigate to Sales order page
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab("Sales Orders");
        NavSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();
        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);

        //Add line items
        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item",itemNumber, null, null, quantity, null, 1);
        newSalesOrder.activateRow(2);
        fillInItemsTableInfo("G/L Account", "14100", null, null, quantity, "50", 2);
        newSalesOrder.activateRow(3);
        fillInItemsTableInfo("Resource", "MARY", null, null, quantity, null, 3);
        newSalesOrder.activateRow(4);
        fillInItemsTableInfo("Charge (Item)", "P-FREIGHT", null, null, quantity, "20", 4);

        newSalesOrder.exitExpandTable();
        //Validate if tax amount and vertex customer tax amount is same on statistics page
        String actualTotalTaxAmount = newSalesOrder.getTotalTaxAmount();
        navigateToStatWindow();
        String actualVertexTax= newSalesOrder.getVertexStatTaxAmount();
        newSalesOrder.closeTaxDetailsDialog();
        assertEquals(actualTotalTaxAmount, actualVertexTax);

    }
    /**
     * CDNAV-534
     * Validates if pop or alert appears on keeping Tax group code blank and user clicks Statistics Page
     * @author Shruti Jituri
     */
    @Test(groups = {"D365_Deprecated"})
    public void missingTaxGroupCodeValidationErrorForStatisticsTest() {
        String customerCode = "Test Phila";
        String itemNumber = "1900-S";
        String quantity="1";

        //Navigate to Sales order page
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab("Sales Orders");
        NavSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();
        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);

        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newSalesOrder.activateRow(2);
        fillInItemsTableInfo("Resource", "MARY", null, null, quantity, null, 2);
        newSalesOrder.activateRow(3);
        fillInItemsTableInfo("Charge (Item)", "P-FREIGHT", null, null, quantity, "20", 3);
        newSalesOrder.activateRow(4);
        fillInItemsTableInfo("Comment", null, null, null, null, null, 4);
        newSalesOrder.activateRow(5);
        fillInItemsTableInfo("G/L Account", "14100", null, null, quantity, "10", 5);
        newSalesOrder.exitExpandTable();

        String documentNum = newSalesOrder.getCurrentSalesNumber();
        navigateToStatWindow();

        String actualAlertMessage = newSalesOrder.getAlertMessageForTaxGroupCode();
        String lineNum = "10000";
        String expectedAlertMessage = newSalesOrder.createExpectedAlertMessageStrings("Order",documentNum,lineNum);
        assertEquals(actualAlertMessage,expectedAlertMessage,"Tax Group Code Statistics error validation Failed");

    }
    /**
     * CDNAV-539
     * Verifies that tax group code is not required to recalculate tax
     * @author Shruti Jituri
     */
    @Test(groups = {"D365_NAV_Regression"})
    public void missingTaxGroupCodeValidationRecalculateTest() {
        String customerCode = "test1234";
        String itemNumber = "1100";
        String quantity="1";
        String expectedTaxAmount="89.63";

        //Navigate to Sales order page
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab("Sales Orders");
        NavSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();
        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);

        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newSalesOrder.activateRow(2);
        fillInItemsTableInfo("G/L Account", "14400", null, null, quantity, "10", 2);
        newSalesOrder.activateRow(3);
        fillInItemsTableInfo("Resource", "MARK", null, null, quantity, null, 3);
        newSalesOrder.activateRow(4);
        fillInItemsTableInfo("Charge (Item)", "P-ALLOWANCE", null, null, quantity, "10", 4);

        //Change Shipping Address
        newSalesOrder.openShippingAndBillingCategory();
        newSalesOrder.selectCustomShipToAddress();
        fillInCustomAddressInShippingAndBilling("COL","1575 Space Center Drive","Colorado Springs","CO","80915");

        clickRecalculateTax(newSalesOrder);

        String actualTaxAmount = newSalesOrder.getTotalTaxAmount();
        assertEquals(expectedTaxAmount, actualTaxAmount);
    }
    /**
     * CDNAV-430
     * Verifies that NJ customer has no tax on clothing
     * @author Shruti Jituri
     */
    @Test(groups = {"D365_NAV_Regression"})
    public void createSalesOrderAndVerifyNoTaxTest() {
        String customerCode = "Test_NJ";
        String itemNumber = "1400";
        String quantity = "1";
        String expectedTaxAmount = "0.00";
        String expectedTaxRate = "0.00";
       // String taxGroupCode = "CLOTHESEXEMPT";

        //Navigate to Sales order page
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab("Sales Orders");
        NavSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();
        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);

        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        newSalesOrder.activateRow(2);

        //Verify the tax amount and tax rate
        String actualTotalTaxAmount = newSalesOrder.getTotalTaxAmount();
        assertEquals(expectedTaxAmount,actualTotalTaxAmount);
        openVertexTaxDetailsWindow(newSalesOrder);
        String actualTaxRate = newSalesOrder.getTaxRate();
        assertEquals(expectedTaxRate,actualTaxRate);
        }

    /**
     * CDNAV-426
     * Verifies that tax is 0 for non-registered taxpayer
     * @author Shruti Jituri
     */
    @Test(groups = {"D365_NAV_Regression"})
    public void createSalesOrderForNonRegisteredTaxPayersTest() {
        String customerCode = "Test_AZ";
        String itemNumber = "1896-S";
        String quantity = "1";
        String expectedTaxRate="0.00";
        String expectedTaxAmount="0.00";

        //Navigate to Sales Order page
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab("Sales Orders");
        NavSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();
        fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);

        newSalesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null,quantity, null, 1);
        newSalesOrder.activateRow(2);

        openVertexTaxDetailsWindow(newSalesOrder);
        String actualTaxRate = newSalesOrder.getTaxRate();
        assertEquals(expectedTaxRate,actualTaxRate);
        String actualTotalTaxAmount = newSalesOrder.getTotalTaxAmount();
        assertEquals(expectedTaxAmount,actualTotalTaxAmount);
     }

    /**
     * CDNAV-649
     * Verifies that Total tax UI field is refreshed on adding 'Nontaxable' item and selecting 'Alternate address'
     * @author Shruti Jituri
     */
    @Test(groups = {"D365_NAV_Regression"})
    public void verifyTotalTaxOnSelectingAlternateAddressTest(){
        String customerCode = "test1234";
        String itemNumber = "1896-S";
        String quantity="1";
        String expectedTax="60.05";
        String expectedTaxAmount="95.08";

        //Navigate to Sales Order page
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesOrderListPage salesOrders = homePage.mainNavMenu.goToSubNavTab("Sales Orders");
        NavSalesOrderPage salesOrder = salesOrders.pageNavMenu.clickNew();
        fillInSalesOrderGeneralInfo(salesOrder, customerCode);

        salesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        salesOrder.activateRow(2);
        fillInItemsTableInfo("G/L Account", "11200", null, null, quantity, "50", 2);
        String actualTotalTaxAmount = salesOrder.getTotalTaxAmount();
        assertEquals(expectedTax,actualTotalTaxAmount);

        //Select Alternate Shipping
        salesOrder.openShippingAndBillingCategory();
        salesOrder.selectAlternateShipToAddress("CA");

        //Recalculate tax
        clickRecalculateTax(salesOrder);
        String actualTaxAmount = salesOrder.getTotalTaxAmount();
        assertEquals(expectedTaxAmount, actualTaxAmount);

        //Post Invoice and verify Total tax UI field
        salesOrderSelectShipAndInvoiceThenPost(salesOrder);
        String actualPostedTaxAmount = salesOrder.getTotalTaxAmount();
        assertEquals(expectedTaxAmount, actualPostedTaxAmount);
    }

    /**
     * CDNAV-213
     * Verify document number in Vertex tax details window
     *
     * @author Shruti Jituri
     */
    @Test(groups = {"D365_NAV_Regression"})
    public void verifyDocNoOnVertexTaxDetailsTest(){
        String customerCode = "test1234";
        String itemNumber = "1896-S";
        String quantity="1";
        //Navigate to Sales Order page
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesOrderListPage salesOrders = homePage.mainNavMenu.goToSubNavTab("Sales Orders");
        NavSalesOrderPage salesOrder = salesOrders.pageNavMenu.clickNew();
        fillInSalesOrderGeneralInfo(salesOrder, customerCode);
        //fill in items table and navigate to vertex tax details window
        salesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber, null, null, quantity, null, 1);
        salesOrder.activateRow(2);
        //Compare orderNo
        String  expectedSalesOrderNumber = salesOrder.getOrderNumber();
        openVertexTaxDetailsWindow(salesOrder);
        String actualVertexOrderNo=salesOrder.getVertexDetailsOrderNumber();
        assertEquals(expectedSalesOrderNumber,actualVertexOrderNo);
        }

    /**
     * CDNAV-207
     * Validates if 0 priced items do not result in tax calculation errors
     * @author Shruti Jituri
     */
    @Test(groups = {"D365_NAV_Regression"})
    public void verifyTaxOnZeroPricedItemTest(){
        String customerCode = "test1234";
        String itemNumber1 = "1900-S";
        String itemNumber2 = "1906-S";
        String quantity="1";
        String expectedTotalTax="43.59";
        String expectedTotalTaxUI="37.59";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavSalesOrderListPage salesOrders = homePage.mainNavMenu.goToSubNavTab("Sales Orders");
        NavSalesOrderPage salesOrder = salesOrders.pageNavMenu.clickNew();
        fillInSalesOrderGeneralInfo(salesOrder, customerCode);
        salesOrder.expandTable();
        fillInItemsTableInfo("Item", itemNumber1, null, null, quantity, null, 1);
        salesOrder.activateRow(2);
        fillInItemsTableInfo("Item", itemNumber2, null, null, quantity, null, 2);
        salesOrder.activateRow(3);
        fillInItemsTableInfo("Resource", "MARK", null, null, quantity, "100", 3);
        salesOrder.activateRow(4);
        String actualTotalTaxAmount = salesOrder.getTotalTaxAmount();
        assertEquals(expectedTotalTax,actualTotalTaxAmount);
        //update price and verify tax
        changedLineAmount("0",3);
        String actualTotalTaxUI = salesOrder.getTotalTaxAmount();
        assertEquals(expectedTotalTaxUI,actualTotalTaxUI);
        clickRecalculateTax(salesOrder);
        String actualRecalculatedTaxAmount = salesOrder.getTotalTaxAmount();
        assertEquals(expectedTotalTaxUI, actualRecalculatedTaxAmount);
    }
    }
