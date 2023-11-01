package com.vertex.quality.connectors.netsuite.suiteTax.tests;

import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteVendor;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.suiteTax.pages.transactions.NetsuiteAPIInvoicePage;
import com.vertex.quality.connectors.netsuite.suiteTax.pages.transactions.NetsuiteAPIPurchaseOrderPage;
import com.vertex.quality.connectors.netsuite.suiteTax.pages.transactions.NetsuiteAPISalesOrderPage;
import com.vertex.quality.connectors.netsuite.suiteTax.tests.base.NetsuiteBaseAPITest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

public class NetsuiteAsiaPacific extends NetsuiteBaseAPITest {

    /**
     * Create a Basic Sales order and an Invoice for ASIA PACIFIC(INDIA) CGST,SGST intra-state in Suite Tax &
     * validate the xml response for tax details
     * @author ravunuri
     * CNSAPI-675 //Currently Out of scope for VAT
     */
    @Ignore
    @Test(groups = {"netsuite_vat"})
    protected void CreateSalesOrderInvoiceApacIntraStateTest ( )  {

        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_APAC_CGST_SGST;
        String customerId = "AsiaPacific_CGST_SGST";
        String expectedTaxResult = "Success";
        String totalAmount = "<Total>118.0</Total>";
        String totalTax = "<TotalTax>18.0</TotalTax>";
        String taxRate = "9.0";
        String country = "IN";
        String intraCountryTaxCode = "<InvoiceTextCode>21</InvoiceTextCode>";
        String taxType = "VAT";
        String CGST = "<Imposition impositionId=\"15\">CGST</Imposition>";
        String SGST = "<Imposition impositionId=\"4\">SGST</Imposition>";
        String MainDivision_State = "<MainDivision>Maharashtra</MainDivision>";

        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("100.00")
                .location("AsiaPacific_CGST_SGST")
                .build();

        //Create SALES ORDER and Validate XML Response
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        //Navigate to Enter Sales Order menu
        NetsuiteAPISalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
        setupBasicOrder(salesOrderPage, customer, itemOne);

        //Save Sales Order
        NetsuiteAPISalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();

        //Verify the “Vertex Tax Error code” field is showing 'Success' status.
        String actualMessage = savedSalesOrderPage.getVertexTaxErrorCode();
        assertEquals(actualMessage, expectedTaxResult);

        //Click on the call details and verify customer details
        NetsuiteAPISalesOrderPage previousSalesOrderPage = checkDocumentLogs(savedSalesOrderPage, customerId,
                country, MainDivision_State, totalAmount, totalTax, taxRate, intraCountryTaxCode, taxType, CGST, SGST);

        //Delete Sales order
        deleteDocument(previousSalesOrderPage);


        //------Create a basic INVOICE and Validate XML Response-----
        NetsuiteNavigationMenus InvoiceMenu = getCreateInvoiceMenu();
        NetsuiteAPIInvoicePage invoicePage = setupManagerPage.navigationPane.navigateThrough(InvoiceMenu);
        setupBasicOrder(invoicePage, customer, itemOne);

        //Save an Invoice
        NetsuiteAPIInvoicePage savedInvoicePage = invoicePage.saveOrder();

        //Verify the “Vertex Tax Error code” field is showing 'Success' status.
        String actualInvoiceResult = savedInvoicePage.getVertexTaxErrorCode();
        assertEquals(actualInvoiceResult, expectedTaxResult);

        //Click on the call details and verify customer details
        checkDocumentLogs(savedInvoicePage, customerId, country, MainDivision_State, totalAmount, totalTax, taxRate,
                intraCountryTaxCode, taxType, CGST, SGST);

        //Delete Invoice
        savedInvoicePage.editOrder();
        savedInvoicePage.deleteOrder();
    }


    /**
     * Create a Basic Sales order and an Invoice for ASIA PACIFIC (INDIA) inter-state in Suite Tax and
     * validate the xml response for tax details
     * @author ravunuri
     * CNSAPI-676
     */
	@Ignore //Currently Out of scope for VAT
	@Test(groups = {"netsuite_vat"})
    protected void CreateSalesOrderInvoiceApacInterStateTest ( )  {

        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_APAC_INTERSTATE;
        String customerId = "AsiaPacific_InterState";
        String expectedTaxResult = "Success";
        String totalAmount = "<Total>118.0</Total>";
        String totalTax = "<TotalTax>18.0</TotalTax>";
        String taxRate = "18.0";
        String country = "IN";
        String intraCountryTaxCode = "<InvoiceTextCode>21</InvoiceTextCode>";
        String taxType = "VAT";
        String taxIGST = "<Imposition impositionId=\"16\">IGST</Imposition>";
        String MainDivision_State = "<MainDivision>Maharashtra</MainDivision>";

        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("100.00")
                .location("AsiaPacific_InterState")
                .build();

        //Create SALES ORDER and Validate XML Response
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        //Navigate to Enter Sales Order menu
        NetsuiteAPISalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
        setupBasicOrder(salesOrderPage, customer, itemOne);

        //Save Sales Order
        NetsuiteAPISalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();

        //Verify the “Vertex Tax Error code” field is showing 'Success' status.
        String actualMessage = savedSalesOrderPage.getVertexTaxErrorCode();
        assertEquals(actualMessage, expectedTaxResult);

        //Click on the call details and verify customer details
        NetsuiteAPISalesOrderPage previousSalesOrderPage = checkDocumentLogs(savedSalesOrderPage, customerId,
                country, MainDivision_State, totalAmount, totalTax, taxRate, intraCountryTaxCode, taxType, taxIGST);

        //Delete Sales order
        deleteDocument(previousSalesOrderPage);


        //------Create a basic INVOICE and Validate XML Response-----
        NetsuiteNavigationMenus InvoiceMenu = getCreateInvoiceMenu();
        NetsuiteAPIInvoicePage invoicePage = setupManagerPage.navigationPane.navigateThrough(InvoiceMenu);
        setupBasicOrder(invoicePage, customer, itemOne);

        //Save an Invoice
        NetsuiteAPIInvoicePage savedInvoicePage = invoicePage.saveOrder();

        //Verify the “Vertex Tax Error code” field is showing 'Success' status.
        String actualInvoiceResult = savedInvoicePage.getVertexTaxErrorCode();
        assertEquals(actualInvoiceResult, expectedTaxResult);

        //Click on the call details and verify customer details
        checkDocumentLogs(savedInvoicePage, customerId, country, MainDivision_State, totalAmount, totalTax, taxRate,
                intraCountryTaxCode, taxType, taxIGST);

        //Delete Invoice
        savedInvoicePage.editOrder();
        savedInvoicePage.deleteOrder();
    }

    /**
     * Create a Basic Sales order and Basic Invoice for ASIA PACIFIC(INDIA) Inter-State Reverse Charge and
     * validate the xml response for tax details
     * @author ravunuri
     * CNSAPI-677
     */
	@Ignore //Currently Out of scope for VAT
	@Test(groups = {"netsuite_vat"})
    protected void CreateSalesOrderInvoiceApacInterStateReverseTest ( )  {

        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_APAC_INTERSTATE_REVERSE;
        String expectedTaxResult = "Success";
        String totalAmount = "<Total>118.0</Total>";
        String totalTax = "<TotalTax>18.0</TotalTax>";
        String taxRate = "18.0";
        String country = "IN";
        String intraCountryTaxCode = "<InvoiceTextCode>21</InvoiceTextCode>";
        String taxType = "VAT";
        String taxIGST = "<Imposition impositionId=\"16\">IGST</Imposition>";
        String MainDivision_State = "<MainDivision>Maharashtra</MainDivision>";

        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("100.00")
                .location("AsiaPacific_InterState")
                .build();

        //------Create a basic INVOICE and Validate XML Response-----
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        NetsuiteNavigationMenus InvoiceMenu = getCreateInvoiceMenu();
        NetsuiteAPIInvoicePage invoicePage = setupManagerPage.navigationPane.navigateThrough(InvoiceMenu);
        setupBasicOrder(invoicePage, customer, itemOne);

        //Save an Invoice
        NetsuiteAPIInvoicePage savedInvoicePage = invoicePage.saveOrder();

        //Verify the “Vertex Tax Error code” field is showing 'Success' status.
        String actualInvoiceResult = savedInvoicePage.getVertexTaxErrorCode();
        assertEquals(actualInvoiceResult, expectedTaxResult);

        //Reverse Charge - click 'Authorize Return' button on Invoice record
        NetsuiteAPIInvoicePage reverseChargeInvoice = savedInvoicePage.reverseCharge();

        //Save an Reverse Charges of an Invoice record
        reverseChargeInvoice.saveOrder();

        //Verify the “Vertex Tax Error code” field is showing 'Success' status for Reverse Invoice Charge.
        String reverseChargeResult = reverseChargeInvoice.getVertexTaxErrorCode();
        assertEquals(reverseChargeResult, expectedTaxResult);

        //Click on the call details and verify Tax details on Reverse Charge record
        checkDocumentLogs(reverseChargeInvoice, country, totalAmount, totalTax, taxRate, intraCountryTaxCode,
        taxType, taxIGST, MainDivision_State);

        //Delete Reverse Charge record
        reverseChargeInvoice.editOrder();
        reverseChargeInvoice.deleteOrder();
    }

    /**
     * Create a Basic Sales order for APAC (India), invoice and validate the xml
     * Create an Order same billing and shipping address
     * @author mwilliams
     * CNSAPI-671
     */
	@Ignore //Currently Out of scope for VAT
	@Test(groups = {"netsuite_vat"})
    protected void CreateSalesOrderInvoiceApac ( )  {

        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_APAC_CGST_SGST;
        String customerId = "AsiaPacific_CGST_SGST";
        String expectedTaxResult = "Success";
        String totalAmount = "<Total>118.0</Total>";
        String totalTax = "<TotalTax>18.0</TotalTax>";
        String taxRate = "18.0";
        String country = "IN";
        String intraCountryTaxCode = "<InvoiceTextCode>21</InvoiceTextCode>";
        String taxType = "VAT";
        String CGST = "<Imposition impositionId=\"15\">CGST</Imposition>";
        String SGST = "<Imposition impositionId=\"4\">SGST</Imposition>";
        String MainDivision_State = "<MainDivision>Maharashtra</MainDivision>";

        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("100.00")
                .location("AsiaPacific_CGST_SGST")
                .build();

        //Create SALES ORDER and Validate XML Response
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        //Navigate to Enter Sales Order menu
        NetsuiteAPISalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
        setupBasicOrder(salesOrderPage, customer, itemOne);

        //Save Sales Order
        NetsuiteAPISalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();

        //Verify the “Vertex Tax Error code” field is showing 'Success' status.
        String actualMessage = savedSalesOrderPage.getVertexTaxErrorCode();
        assertEquals(actualMessage, expectedTaxResult);

        //Click on the call details and verify customer details
        NetsuiteAPISalesOrderPage previousSalesOrderPage = checkDocumentLogs(savedSalesOrderPage, customerId,
                country, MainDivision_State, totalAmount, totalTax, taxRate, intraCountryTaxCode, taxType, CGST, SGST);

        //Delete Sales order
        deleteDocument(previousSalesOrderPage);


        //------Create a basic INVOICE and Validate XML Response-----
        NetsuiteNavigationMenus InvoiceMenu = getCreateInvoiceMenu();
        NetsuiteAPIInvoicePage invoicePage = setupManagerPage.navigationPane.navigateThrough(InvoiceMenu);
        setupBasicOrder(invoicePage, customer, itemOne);

        //Save an Invoice
        NetsuiteAPIInvoicePage savedInvoicePage = invoicePage.saveOrder();

        //Verify the “Vertex Tax Error code” field is showing 'Success' status.
        String actualInvoiceResult = savedInvoicePage.getVertexTaxErrorCode();
        assertEquals(actualInvoiceResult, expectedTaxResult);

        //Click on the call details and verify customer details
        checkDocumentLogs(savedSalesOrderPage, customerId, country, MainDivision_State, totalAmount, totalTax, taxRate,
                intraCountryTaxCode, taxType, CGST, SGST);

        //Delete Invoice
        savedInvoicePage.editOrder();
        savedInvoicePage.deleteOrder();
    }

    /**
     * Create a Basic Sales order for APAC (Hong Kong - no tax), invoice and validate the xml
     * Create an Order same billing and shipping address
     * @author mwilliams
     * CNSAPI-672
     */
	@Ignore //Currently Out of scope for VAT
	@Test(groups = {"netsuite_vat"})
    protected void CreateSalesOrderInvoiceApacHK ( )  {

        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_HONG_KONG;
        String customerId = customer.getCustomerName();
        String expectedTaxResult = "Success";
        String totalAmount = "<Total>118.0</Total>";
        String totalTax = "<TotalTax>18.0</TotalTax>";
        String taxRate = "0.0";
        String country = "HK";
        String intraCountryTaxCode = "<InvoiceTextCode>21</InvoiceTextCode>";
        String taxType = "VAT";
        String MainDivision_State = "<MainDivision>Maharashtra</MainDivision>";

        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("100.00")
                .location(customer.getCustomerName())
                .build();

        //Create SALES ORDER and Validate XML Response
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        //Navigate to Enter Sales Order menu
        NetsuiteAPISalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
        setupBasicOrder(salesOrderPage, customer, itemOne);

        //Save Sales Order
        NetsuiteAPISalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();

        //Verify the “Vertex Tax Error code” field is showing 'Success' status.
        String actualMessage = savedSalesOrderPage.getVertexTaxErrorCode();
        assertEquals(actualMessage, expectedTaxResult);

        //Click on the call details and verify customer details
        NetsuiteAPISalesOrderPage previousSalesOrderPage = checkDocumentLogs(savedSalesOrderPage, customerId,
                country, MainDivision_State, totalAmount, totalTax, taxRate, intraCountryTaxCode, taxType);

        //Delete Sales order
        deleteDocument(previousSalesOrderPage);


        //------Create a basic INVOICE and Validate XML Response-----
        NetsuiteNavigationMenus InvoiceMenu = getCreateInvoiceMenu();
        NetsuiteAPIInvoicePage invoicePage = setupManagerPage.navigationPane.navigateThrough(InvoiceMenu);
        setupBasicOrder(invoicePage, customer, itemOne);

        //Save an Invoice
        NetsuiteAPIInvoicePage savedInvoicePage = invoicePage.saveOrder();

        //Verify the “Vertex Tax Error code” field is showing 'Success' status.
        String actualInvoiceResult = savedInvoicePage.getVertexTaxErrorCode();
        assertEquals(actualInvoiceResult, expectedTaxResult);

        //Click on the call details and verify customer details
        checkDocumentLogs(savedSalesOrderPage, customerId, country, MainDivision_State, totalAmount, totalTax, taxRate,
                intraCountryTaxCode, taxType);

        //Delete Invoice
        savedInvoicePage.editOrder();
        savedInvoicePage.deleteOrder();
    }

    /**
     * Create a Basic Purchase order for APAC (Hong Kong - no tax) and validate the xml
     * Create an Order same billing and shipping address
     * @author ravunuri
     * CNSAPI-658
     */
	@Ignore //Currently Out of scope for VAT
	@Test(groups = {"netsuite_vat"})
    protected void CreatePurchaseOrderApacHKTest( ) {

        //Define Vendors and Items
        NetsuiteVendor vendor = NetsuiteVendor.VENDOR_HONG_KONG;
        String expectedTaxResult = "Success";
        String subsidiary = "AsiaPacific_HongKong";
        String totalAmount = "<Total>50.0</Total>";
        String totalTax = "<TotalTax>0.0</TotalTax>";
        String taxRate = "<EffectiveRate>0.0</EffectiveRate>";
        String country = "<Country>HK</Country>";
        String intraCountryTaxCode = "<InvoiceTextCode>21</InvoiceTextCode>";
        String taxType = "taxType=\"NONE\"";
        String shipFromState = "<MainDivision>Mong Kok</MainDivision>";
        String shipToState = "<MainDivision>Shuen Wan</MainDivision>";
        String CalculatedTax = "<CalculatedTax>0.0</CalculatedTax>";
        String EffectiveRate = "<EffectiveRate>0.0</EffectiveRate>";
        String Taxable = "<Taxable>0.0</Taxable>";

        NetsuiteItem item = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("50.00")
                .build();

        //Create Purchase Order and Validate its XML Response
        NetsuiteNavigationMenus PurchaseOrderMenu = getPurchaseOrderMenu();
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        //Navigate to Enter Purchase Order menu
        NetsuiteAPIPurchaseOrderPage purchaseOrderPage = setupManagerPage.navigationPane.navigateThrough(PurchaseOrderMenu);
        setupBasicPurchaseOrder(purchaseOrderPage, vendor, subsidiary, item);
        //Save Purchase Order
        NetsuiteAPIPurchaseOrderPage savedPurchaseOrderPage = purchaseOrderPage.saveOrder();

        //Verify the “Vertex Tax Error code” field is showing 'Success' status.
        String actualMessage = savedPurchaseOrderPage.getVertexTaxErrorCode();
        assertEquals(actualMessage, expectedTaxResult);

        //Click on the call details and verify customer details
        NetsuiteAPIPurchaseOrderPage previousPurchaseOrderPage = checkDocumentLogs(savedPurchaseOrderPage, country,
                shipFromState, shipToState, totalAmount, totalTax, taxRate, intraCountryTaxCode, taxType,
                CalculatedTax, EffectiveRate, Taxable);

        //Delete Purchase order
        deleteDocument(previousPurchaseOrderPage);
    }

    /**
     * Create a Basic Purchase order for ASIA PACIFIC(INDIA) CGST,SGST intra-state in Suite Tax &
     * validate the xml response for tax details
     * @author ravunuri
     * CNSAPI-662
     */
	@Ignore //Currently Out of scope for VAT
	@Test(groups = {"netsuite_vat"})
    protected void createPurchaseOrderApacIntraStateTest ( ) {

        //Define Customers and Items
        NetsuiteVendor vendor = NetsuiteVendor.VENDOR_APAC_CGST_SGST;
        String subsidiary = "AsiaPacific_CGST_SGST";
        String expectedTaxResult = "Success";
        String totalAmount = "<Total>59.0</Total>";
        String totalTax = "<TotalTax>9.0</TotalTax>";
        String taxRate = "<EffectiveRate>0.09</EffectiveRate>";
        String country = "<Country>IN</Country>";
        String intraCountryTaxCode = "<InvoiceTextCode>21</InvoiceTextCode>";
        String taxType = "VAT";
        String CGST = "<Imposition impositionId=\"15\">CGST</Imposition>";
        String SGST = "<Imposition impositionId=\"4\">SGST</Imposition>";
        String MainDivision_State = "<MainDivision>Maharashtra</MainDivision>";

        NetsuiteItem item = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("50.00")
                .build();

        //Create Purchase Order and Validate its XML Response
        NetsuiteNavigationMenus PurchaseOrderMenu = getPurchaseOrderMenu();
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        //Navigate to Enter Purchase Order menu
        NetsuiteAPIPurchaseOrderPage purchaseOrderPage = setupManagerPage.navigationPane.navigateThrough(PurchaseOrderMenu);
        setupBasicPurchaseOrder(purchaseOrderPage, vendor, subsidiary, item);
        //Save Purchase Order
        NetsuiteAPIPurchaseOrderPage savedPurchaseOrderPage = purchaseOrderPage.saveOrder();

        //Verify the “Vertex Tax Error code” field is showing 'Success' status.
        String actualMessage = savedPurchaseOrderPage.getVertexTaxErrorCode();
        assertEquals(actualMessage, expectedTaxResult);

        //Click on the call details and verify customer details
        NetsuiteAPIPurchaseOrderPage previousPurchaseOrderPage = checkDocumentLogs(savedPurchaseOrderPage, country,
                MainDivision_State, totalAmount, totalTax, taxRate, intraCountryTaxCode, taxType, CGST, SGST);

        //Delete Purchase order
        deleteDocument(previousPurchaseOrderPage);
    }

    /**
     * Create a Basic Purchase Order for ASIA PACIFIC (INDIA) inter-state in Suite Tax and
     * validate the xml response for tax details
     * @author ravunuri
     * CNSAPI-676
     */
	@Ignore //Currently Out of scope for VAT
	@Test(groups = {"netsuite_vat"})
    protected void CreatePurchaseOrderApacInterStateTest ( )  {

        //Define Customers and Items
        NetsuiteVendor vendor = NetsuiteVendor.VENDOR_APAC_INTERSTATE;
        String subsidiary = "AsiaPacific_InterState";
        String expectedTaxResult = "Success";
        String totalAmount = "<Total>59.0</Total>";
        String totalTax = "<TotalTax>9.0</TotalTax>";
        String taxRate = "<EffectiveRate>0.18</EffectiveRate>";
        String country = "<Country>IN</Country>";
        String intraCountryTaxCode = "<InvoiceTextCode>21</InvoiceTextCode>";
        String taxType = "VAT";
        String taxIGST = "<Imposition impositionId=\"16\">IGST</Imposition>";
        String MainDivision_State = "<MainDivision>Maharashtra</MainDivision>";

        NetsuiteItem item = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("50.00")
                .build();

        //Create Purchase Order and Validate its XML Response
        NetsuiteNavigationMenus PurchaseOrderMenu = getPurchaseOrderMenu();
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        //Navigate to Enter Purchase Order menu
        NetsuiteAPIPurchaseOrderPage purchaseOrderPage = setupManagerPage.navigationPane.navigateThrough(PurchaseOrderMenu);
        setupBasicPurchaseOrder(purchaseOrderPage, vendor, subsidiary, item);
        //Save Purchase Order
        NetsuiteAPIPurchaseOrderPage savedPurchaseOrderPage = purchaseOrderPage.saveOrder();

        //Verify the “Vertex Tax Error code” field is showing 'Success' status.
        String actualMessage = savedPurchaseOrderPage.getVertexTaxErrorCode();
        assertEquals(actualMessage, expectedTaxResult);

        //Click on the call details and verify customer details
        NetsuiteAPIPurchaseOrderPage previousPurchaseOrderPage = checkDocumentLogs(savedPurchaseOrderPage, country,
                MainDivision_State, totalAmount, totalTax, taxRate, intraCountryTaxCode, taxType, taxIGST);

        //Delete Purchase order
        deleteDocument(previousPurchaseOrderPage);
    }

}