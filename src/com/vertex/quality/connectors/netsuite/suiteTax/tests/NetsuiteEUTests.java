package com.vertex.quality.connectors.netsuite.suiteTax.tests;

import com.vertex.quality.common.enums.Country;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteAddresses;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteVendor;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteAddress;
import com.vertex.quality.connectors.netsuite.suiteTax.pages.transactions.NetsuiteAPIInvoicePage;
import com.vertex.quality.connectors.netsuite.suiteTax.pages.transactions.NetsuiteAPIPurchaseOrderPage;
import com.vertex.quality.connectors.netsuite.suiteTax.tests.base.NetsuiteBaseAPITest;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.suiteTax.pages.transactions.NetsuiteAPISalesOrderPage;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class NetsuiteEUTests extends NetsuiteBaseAPITest {

    /**
     * Return a Consignment Sales order invoice for VAT (DE FR) and validate the xml
     * @author mwilliams
     * CNSAPI-663 //Currently Out of scope for VAT
     */
	@Ignore
	@Test(groups = {"netsuite_vat"})
    protected void consignmentSalesOrderTest( )
    {   //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_GERMANY;
        String customerId = "German Company";
        String expectedTaxResult = "Success";
        String totalAmount = "<Total>95.20</Total>";
        String totalTax = "15.2";
        String taxRate = "0.19";
        String country = "DE";
        String intraCountryTaxCode = "<InvoiceTextCode>2</InvoiceTextCode>";
        String taxType = "VAT";

        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("80.00")
                .build();

        //Navigate to Enter Sales Order menu
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();

        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        NetsuiteAPISalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
        setupBasicOrder(salesOrderPage, customer, itemOne);

        //Save Sales Order
        NetsuiteAPISalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();

        //Verify the “Vertex Tax Error code” field is showing 'Success' status.
        String actualMessage = savedSalesOrderPage.getVertexTaxErrorCode();
        assertEquals(actualMessage, expectedTaxResult);

        //Click on the call details and verify customer details
        NetsuiteAPISalesOrderPage previousSalesOrderPage = checkDocumentLogs(savedSalesOrderPage, customerId,
                country, totalAmount, totalTax, taxRate, intraCountryTaxCode, taxType);

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
        checkDocumentLogs(savedInvoicePage, customerId, country, totalAmount, totalTax,
                taxRate, intraCountryTaxCode, taxType);

        //Delete Invoice
        savedInvoicePage.editOrder();
        savedInvoicePage.deleteOrder();
    }

    /**
     * Return a Triangulation Sales order for VAT (FR BE DK), invoice and validate the xml
     * This will test a Belgian customer
     * @author mwilliams
	 * CNSAPI-664 //Currently Out of scope for VAT
	 */
	@Ignore
	@Test(groups = {"netsuite_vat"})
    protected void triangulationSalesOrderTest( )
    {   //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_BELGIUM;
        String expectedTaxResult = "Success";
        String totalAmount = "<Total>96.0</Total>";
        String totalTax = "15.2";
        String taxRate = "0.19";
        String country = "BE";
        String intraCountryTaxCode = "<InvoiceTextCode>21</InvoiceTextCode>";
        String taxType = "VAT";

        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("80.00")
                .build();

        //Navigate to Enter Sales Order menu
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();

        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        NetsuiteAPISalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
        setupBasicOrder(salesOrderPage, customer, itemOne);

        //Save Sales Order
        NetsuiteAPISalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();

        //Verify the “Vertex Tax Error code” field is showing 'Success' status.
        String actualMessage = savedSalesOrderPage.getVertexTaxErrorCode();
        assertEquals(actualMessage, expectedTaxResult);

        //Click on the call details and verify customer details
        NetsuiteAPISalesOrderPage previousSalesOrderPage = checkDocumentLogs(savedSalesOrderPage,
                country, totalAmount, totalTax, taxRate, intraCountryTaxCode, taxType);

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
        checkDocumentLogs(savedInvoicePage, country, totalAmount, totalTax,
                taxRate, intraCountryTaxCode, taxType);

        //Delete Invoice
        savedInvoicePage.editOrder();
        savedInvoicePage.deleteOrder();
    }

    /**
     * Create a Basic Sales order for VAT (EU-US), invoice and validate
     * @author mwilliams
	 * CNSAPI-665 //Currently Out of scope for VAT
	 */
	@Ignore
	@Test(groups = {"netsuite_vat"})
    protected void basicVatSalesOrderEUUSTest( )
    {   //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_GERMANY;
        String expectedTaxResult = "Success";
        String totalAmount = "<Total>84.8</Total>";
        String totalTax = "4.8";
        String taxRate = "0.06";
        String country = "PA";
        String intraCountryTaxCode = "<InvoiceTextCode>1</InvoiceTextCode>";
        String intraCountryTaxCode2 = "<InvoiceTextCode>3</InvoiceTextCode>";
        String taxType = "VAT";
        NetsuiteAddress shiptToAddress = NetsuiteAddresses.PA_ADDRESS.getAddress();

        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("80.00")
                .build();

        //Navigate to Enter Sales Order menu
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();

        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        NetsuiteAPISalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
        setupBasicOrder(salesOrderPage, customer,shiptToAddress, itemOne);

        //Save Sales Order
        NetsuiteAPISalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();

        //Verify the “Vertex Tax Error code” field is showing 'Success' status.
        String actualMessage = savedSalesOrderPage.getVertexTaxErrorCode();
        assertEquals(actualMessage, expectedTaxResult);

        //Click on the call details and verify customer details
        NetsuiteAPISalesOrderPage previousSalesOrderPage = checkDocumentLogs(savedSalesOrderPage,
                country, totalAmount, totalTax, taxRate, intraCountryTaxCode,intraCountryTaxCode2, taxType);

        //Delete Sales order
        deleteDocument(previousSalesOrderPage);

        //------Create a basic INVOICE and Validate XML Response-----
        NetsuiteNavigationMenus InvoiceMenu = getCreateInvoiceMenu();
        NetsuiteAPIInvoicePage invoicePage = setupManagerPage.navigationPane.navigateThrough(InvoiceMenu);
        setupBasicOrder(invoicePage, customer,shiptToAddress, itemOne);

        //Save an Invoice
        NetsuiteAPIInvoicePage savedInvoicePage = invoicePage.saveOrder();

        //Verify the “Vertex Tax Error code” field is showing 'Success' status.
        String actualInvoiceResult = savedInvoicePage.getVertexTaxErrorCode();
        assertEquals(actualInvoiceResult, expectedTaxResult);

        //Click on the call details and verify customer details
        checkDocumentLogs(savedInvoicePage, country, totalAmount, totalTax,
                taxRate, intraCountryTaxCode, taxType);

        //Delete Invoice
        savedInvoicePage.editOrder();
        savedInvoicePage.deleteOrder();
    }

    /**
     * Create a Basic Sales order for VAT (US-EU), invoice and validate the xml
     * @author mwilliams
	 * CNSAPI-666 //Currently Out of scope for VAT
	 */
	@Ignore
	@Test(groups = {"netsuite_vat"})
    protected void basicVatSalesOrderUSEUTest( )
    {   //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_SUPPLY_PA;
        //TODO make enum for this data
        String expectedTaxResult = "Success";
        String totalAmount = "<Total>100.0</Total>";
        String totalTax = "<TotalTax>0.0</TotalTax>";
        String taxRate = "<EffectiveRate>0.0</EffectiveRate>";
        String country = "<Country>USA</Country>";
        String taxType = "NONE";
        NetsuiteAddress shipToAddress = NetsuiteAddresses.FR_ADDRESS.getAddress();

        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("100.00")
                .build();

        //Navigate to Enter Sales Order menu
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();

        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        NetsuiteAPISalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
        setupBasicOrder(salesOrderPage, customer, shipToAddress, itemOne);

        //Save Sales Order
        NetsuiteAPISalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();

        //Verify the “Vertex Tax Error code” field is showing 'Success' status.
        String actualMessage = savedSalesOrderPage.getVertexTaxErrorCode();
        assertEquals(actualMessage, expectedTaxResult);

        //Click on the call details and verify customer details
        checkDocumentLogs(savedSalesOrderPage, country, totalAmount, totalTax, taxRate, taxType);

        //Delete Sales order
        deleteDocument(savedSalesOrderPage);

        //------Create a basic INVOICE and Validate XML Response-----
        NetsuiteNavigationMenus InvoiceMenu = getCreateInvoiceMenu();
        NetsuiteAPIInvoicePage invoicePage = setupManagerPage.navigationPane.navigateThrough(InvoiceMenu);
        setupBasicOrder(invoicePage, customer, shipToAddress, itemOne);

        //Save an Invoice
        NetsuiteAPIInvoicePage savedInvoicePage = invoicePage.saveOrder();

        //Verify the “Vertex Tax Error code” field is showing 'Success' status.
        String actualInvoiceResult = savedInvoicePage.getVertexTaxErrorCode();
        assertEquals(actualInvoiceResult, expectedTaxResult);

        //Click on the call details and verify customer details
        checkDocumentLogs(savedInvoicePage, country, totalAmount, totalTax,
                taxRate, taxType);

        //Delete Invoice
        savedInvoicePage.editOrder();
        savedInvoicePage.deleteOrder();
    }

    /**
     * Create a Basic Sales order for VAT (EU FR-DE), invoice and validate the xml
     * @author mwilliams
	 * CNSAPI-667 //Currently Out of scope for VAT
	 */
	@Ignore
	@Test(groups = {"netsuite_vat"})
    protected void basicVatSalesOrderIntraEUTest( )
    {   //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_FRANCE;
        String expectedTaxResult = "Success";
        String totalAmount = "<Total>96.0</Total>";
        String totalTax = "16.0";
        String taxRate = "0.2";
        String country = "FR";
        String taxType = "VAT";

        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("80.00")
                .build();
        NetsuiteAddress shipTo = NetsuiteAddresses.DE_ADDRESS.getAddress();

        //Navigate to Enter Sales Order menu
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();

        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        NetsuiteAPISalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
        setupBasicOrder(salesOrderPage, customer, shipTo, itemOne);

        //Save Sales Order
        NetsuiteAPISalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();

        //Verify the “Vertex Tax Error code” field is showing 'Success' status.
        String actualMessage = savedSalesOrderPage.getVertexTaxErrorCode();
        assertEquals(actualMessage, expectedTaxResult);

        //Click on the call details and verify customer details
        NetsuiteAPISalesOrderPage previousSalesOrderPage = checkDocumentLogs(savedSalesOrderPage,
                country, totalAmount, totalTax, taxRate, taxType);

        //Delete Sales order
        deleteDocument(previousSalesOrderPage);

    }

    /**
     * Create a Basic Sales order for VAT (Greek Territory) invoice and validate the xml
     * @author mwilliams
	 * CNSAPI-668 //Currently Out of scope for VAT
	 */
	@Ignore
	@Test(groups = {"netsuite_vat"})
    protected void basicSalesOrderGRTest( )
    {   //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_GREECE;
        String expectedTaxResult = "Success";
        String totalAmount = "<Total>80.0</Total>";
        String totalTax = "15.2";
        String taxRate = "0.19";
        String country = "GR";
        String intraCountryTaxCode = "<InvoiceTextCode>21</InvoiceTextCode>";
        String taxType = "VAT";

        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("80.00")
                .build();

        //Navigate to Enter Sales Order menu
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();

        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        NetsuiteAPISalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
        setupBasicOrder(salesOrderPage, customer, itemOne);

        //Save Sales Order
        NetsuiteAPISalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();

        //Verify the “Vertex Tax Error code” field is showing 'Success' status.
        String actualMessage = savedSalesOrderPage.getVertexTaxErrorCode();
        assertEquals(actualMessage, expectedTaxResult);

        //Click on the call details and verify customer details
        NetsuiteAPISalesOrderPage previousSalesOrderPage = checkDocumentLogs(savedSalesOrderPage,
                country, totalAmount, totalTax, taxRate, intraCountryTaxCode, taxType);

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
        checkDocumentLogs(savedInvoicePage, country, totalAmount, totalTax,
                taxRate, intraCountryTaxCode, taxType);

        //Delete Invoice
        savedInvoicePage.editOrder();
        savedInvoicePage.deleteOrder();
    }

    /**
     * Create a Basic Sales order for VAT (Austrian(AT) Sub-Division) invoice and validate the xmlCreate
     * Sale Order for Vat (Austrian Sub-Division) and Invoice* Create an Order same billing and shipping address
     * @author mwilliams
	 * CNSAPI-669 //Currently Out of scope for VAT
	 */
	@Ignore
	@Test(groups = {"netsuite_vat"})
    protected void basicSalesOrderATTest( )
    {   //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_AUSTRIA;
        String expectedTaxResult = "Success";
        String totalAmount = "<Total>80.0</Total>";
        String totalTax = "15.2";
        String taxRate = "0.19";
        String country = "AT";
        String intraCountryTaxCode = "<InvoiceTextCode>21</InvoiceTextCode>";
        String taxType = "VAT";

        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("80.00")
                .build();

        //Navigate to Enter Sales Order menu
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();

        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        NetsuiteAPISalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
        setupBasicOrder(salesOrderPage, customer, itemOne);

        //Save Sales Order
        NetsuiteAPISalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();

        //Verify the “Vertex Tax Error code” field is showing 'Success' status.
        String actualMessage = savedSalesOrderPage.getVertexTaxErrorCode();
        assertEquals(actualMessage, expectedTaxResult);

        //Click on the call details and verify customer details
        NetsuiteAPISalesOrderPage previousSalesOrderPage = checkDocumentLogs(savedSalesOrderPage,
                country, totalAmount, totalTax, taxRate, intraCountryTaxCode, taxType);

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
        checkDocumentLogs(savedInvoicePage, country, totalAmount, totalTax,
                taxRate, intraCountryTaxCode, taxType);

        //Delete Invoice
        savedInvoicePage.editOrder();
        savedInvoicePage.deleteOrder();
    }

    /**
     * Create a Basic Sales order with currency conversion for VAT (EU FR-DE), invoice and validate the xml
     * @author mwilliams
	 * CNSAPI-670 //Currently Out of scope for VAT
	 */
	@Ignore
	@Test(groups = {"netsuite_vat"})
    protected void currencyConversionTest( )
    {   //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_FRANCE;
        String expectedTaxResult = "Success";
        String totalAmount = "<Total>80.0</Total>";
        String totalTax = "15.2";
        String taxRate = "0.19";
        String country = "FR";
        String intraCountryTaxCode = "<InvoiceTextCode>21</InvoiceTextCode>";
        String taxType = "VAT";

        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("80.00")
                .build();

        //Navigate to Enter Sales Order menu
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();

        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        NetsuiteAPISalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
        setupBasicOrder(salesOrderPage, customer, itemOne);

        //Save Sales Order
        NetsuiteAPISalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();

        //Verify the “Vertex Tax Error code” field is showing 'Success' status.
        String actualMessage = savedSalesOrderPage.getVertexTaxErrorCode();
        assertEquals(actualMessage, expectedTaxResult);

        //Click on the call details and verify customer details
        NetsuiteAPISalesOrderPage previousSalesOrderPage = checkDocumentLogs(savedSalesOrderPage,
                country, totalAmount, totalTax, taxRate, intraCountryTaxCode, taxType);

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
        checkDocumentLogs(savedInvoicePage, country, totalAmount, totalTax,
                taxRate, intraCountryTaxCode, taxType);

        //Delete Invoice
        savedInvoicePage.editOrder();
        savedInvoicePage.deleteOrder();
    }

    /**
     * Create a Basic Sales order for VAT [Greek Territory - FR(France)-GR(Greece)] and validate the xml
     * @author ravunuri
	 * CNSAPI-656 //Currently Out of scope for VAT
	 */
	@Ignore
	@Test(groups = {"netsuite_vat"})
    protected void createPurchaseOrderGRFRTest( )
    {   //Define Vendors and Items
        NetsuiteVendor vendor = NetsuiteVendor.VENDOR_GREECE;
        String expectedTaxResult = "Success";
        String subsidiary = "France_sub";
        String totalAmount = "<Total>100.0</Total>";
        String totalTax = "<TotalTax>0.0</TotalTax>";
        String taxRate = "<EffectiveRate>0.0</EffectiveRate>";
        String country_from = "<Country>FR</Country>";
        String country_to = "<Country>GR</Country>";
        String taxType = "taxType=\"VAT\"";
        String shipToState = "<MainDivision>Thessalonkiki</MainDivision>";
        String CalculatedTax = "<CalculatedTax>0.0</CalculatedTax>";
        String EffectiveRate = "<EffectiveRate>0.0</EffectiveRate>";
        String BuyerRegistrationId = "<BuyerRegistrationId>FR12345678901</BuyerRegistrationId>";

        NetsuiteItem item = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("100.00")
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
        NetsuiteAPIPurchaseOrderPage previousPurchaseOrderPage = checkDocumentLogs(savedPurchaseOrderPage, country_from,
                country_to, shipToState, totalAmount, totalTax, taxRate, taxType, CalculatedTax,
                EffectiveRate, BuyerRegistrationId);

        //Delete Purchase order
        deleteDocument(previousPurchaseOrderPage);
    }

    /**
     * Create a Purchase order for VAT (US-EU) and validate the xml response
     * @author ravunuri
	 * CNSAPI-654 //Currently Out of scope for VAT
	 */
	@Ignore
	@Test(groups = {"netsuite_vat"})
    protected void createPurchaseOrderUSEUTest( )
    {   //Define Vendors and Items
        NetsuiteVendor vendor = NetsuiteVendor.VENDOR_PA;
        String expectedTaxResult = "Success";
        String subsidiary = "Honeycomb Mfg.FRA";
        String totalAmount = "<Total>50.0</Total>";
        String totalTax = "<TotalTax>0.0</TotalTax>";
        String taxRate = "<EffectiveRate>0.0</EffectiveRate>";
        String country_from = "<Country>FR</Country>";
        String country_to = "<Country>USA</Country>";
        String taxType = "taxType=\"NONE\"";
        String shipFromState = "<MainDivision>PA</MainDivision>";
        String shipToState = "<MainDivision>FRANCE</MainDivision>";
        String CalculatedTax = "<CalculatedTax>0.0</CalculatedTax>";
        String EffectiveRate = "<EffectiveRate>0.0</EffectiveRate>";

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
        NetsuiteAPIPurchaseOrderPage previousPurchaseOrderPage = checkDocumentLogs(savedPurchaseOrderPage, country_from,
                country_to, shipToState, totalAmount, totalTax, taxRate, taxType, CalculatedTax,
                EffectiveRate, shipFromState);

        //Delete Purchase order
        deleteDocument(previousPurchaseOrderPage);
    }

    /**
     * Create a Basic Sales order for VAT (EU-US) and validate
     * @author ravunuri
	 * CNSAPI-653 //Currently Out of scope for VAT
	 */
	@Ignore
	@Test(groups = {"netsuite_vat"})
    protected void createPurchaseOrderEUUSTest( )
    {   //Define Vendors and Items
        NetsuiteVendor vendor = NetsuiteVendor.VENDOR_FRANCE;
        String expectedTaxResult = "Success";
        String subsidiary = "Honeycomb Holdings Inc.";
        String totalAmount = "<Total>54.75</Total>";
        String totalTax = "<TotalTax>4.75</TotalTax>";
        String taxRate = "<EffectiveRate>0.06</EffectiveRate>";
        String country_from = "<Country>FR</Country>";
        String country_to = "<Country>USA</Country>";
        String taxType = "taxType=\"VAT\"";
        String shipToState = "<MainDivision>CA</MainDivision>";
        String BuyerRegistrationId = "<BuyerRegistrationId>AT123456789</BuyerRegistrationId>";

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
        NetsuiteAPIPurchaseOrderPage previousPurchaseOrderPage = checkDocumentLogs(savedPurchaseOrderPage, country_from,
                country_to, shipToState, totalAmount, totalTax, taxRate, taxType, BuyerRegistrationId);

        //Delete Purchase order
        deleteDocument(previousPurchaseOrderPage);
    }

    /**
     * Return a Triangulation Purchase order for VAT (DK BE FR) and validate the xml
     * This will test a Belgian customer
     * @author ravunuri
	 * CNSAPI-652 //Currently Out of scope for VAT
	 */
	@Ignore
	@Test(groups = {"netsuite_vat"})
    protected void triangulationPurchaseOrderTest( )
    {   //Define Vendors and Items
        NetsuiteVendor vendor = NetsuiteVendor.VENDOR_BELGIUM;
        String expectedTaxResult = "Success";
        String subsidiary = "Honeycomb Holdings Inc.";
        String totalAmount = "<Total>35.35</Total>";
        String totalTax = "<TotalTax>0.0</TotalTax>";
        String taxRate = "<EffectiveRate>0.0</EffectiveRate>";
        String administration_origin = "<Country>BE</Country>";
        String Destination_country = "<Country>FR</Country>";
        String physical_origin = "<Country>DK</Country>";
        String taxType = "taxType=\"VAT\"";
        String intraCountryTaxCode1 = "<InvoiceTextCode>2</InvoiceTextCode>";
        String intraCountryTaxCode2 = "<InvoiceTextCode>3</InvoiceTextCode>";
        String BuyerRegistrationId = "<BuyerRegistrationId>FR12345678901</BuyerRegistrationId>";

        NetsuiteItem item = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("35.35")
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
        NetsuiteAPIPurchaseOrderPage previousPurchaseOrderPage = checkDocumentLogs(savedPurchaseOrderPage,
                administration_origin, Destination_country, physical_origin, totalAmount, totalTax, taxRate, taxType,
                BuyerRegistrationId, intraCountryTaxCode1, intraCountryTaxCode2);

        //Delete Purchase order
        deleteDocument(previousPurchaseOrderPage);
    }

    /**
     * Create a Intra-UK sales order and validate results
     * @author mwilliams
	 * CNSAPI-1058 //Currently Out of scope for VAT
	 */
	@Ignore
	@Test(groups = {"netsuite_vat"})
    protected void intraStateUKSalesOrderTest( )
    {
        //Define Vendors and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_UK;
        String expectedTaxResult = "Success";
        String subsidiary = "UK";
        String totalAmount = "<Total>120.0</Total>";
        String totalTax = "<TotalTax>20.0</TotalTax>";
        String taxRate = "<EffectiveRate>0.2</EffectiveRate>";
        String physical_origin = "<Country>GB</Country>";
        String taxType = "taxType=\"VAT\"";
        String intraCountryTaxCode1 = "<InvoiceTextCode>21</InvoiceTextCode>";

        NetsuiteItem item = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("100.00")
                .build();

        //Create Sales Order and Validate its XML Response
        NetsuiteNavigationMenus SalesOrderMenu = getSalesOrderMenu();
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        //Navigate to Enter Sales Order menu
        NetsuiteAPISalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(SalesOrderMenu);
        setupBasicOrder(salesOrderPage, customer, item);
        //Save Sales Order
        NetsuiteAPISalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();

        //Verify the “Vertex Tax Error code” field is showing 'Success' status.
        String actualMessage = savedSalesOrderPage.getVertexTaxErrorCode();
        assertEquals(actualMessage, expectedTaxResult);

        //Click on the call details and verify customer details
        NetsuiteAPISalesOrderPage previousPurchaseOrderPage = checkDocumentLogs(savedSalesOrderPage, physical_origin, totalAmount, totalTax, taxRate, taxType, intraCountryTaxCode1);

        //Delete Purchase order
        previousPurchaseOrderPage.editOrder();
        previousPurchaseOrderPage.deleteOrder();
    }

    /**
     * Create a Inter-UK to EU sales order and validate results
     * @author mwilliams
	 * CNSAPI-*** //Currently Out of scope for VAT
	 */
	@Ignore
	@Test(groups = {"netsuite_vat"})
    protected void interstateUKEUSalesOrderTest( )
    {
        //Define Vendors and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_DE_IMPORTS;
        String expectedTaxResult = "Success";
        String subsidiary = "UK";
        String totalAmount = "<Total>80.0</Total>";
        String totalTax = "<TotalTax>0.0</TotalTax>";
        String taxRate = "<EffectiveRate>0.0</EffectiveRate>";
        String administration_origin = "<Country>GB</Country>";
        String Destination_country = "<Country>DE</Country>";
        String physical_origin = "<Country>GB</Country>";
        String taxType = "taxType=\"VAT\"";
        String intraCountryTaxCode1 = "<InvoiceTextCode>1</InvoiceTextCode>";

        NetsuiteItem item = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("80.00")
                .build();

        //Create Purchase Order and Validate its XML Response
        NetsuiteNavigationMenus SalesOrderMenu = getSalesOrderMenu();
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        //Navigate to Enter Purchase Order menu
        NetsuiteAPISalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(SalesOrderMenu);
        setupBasicOrder(salesOrderPage, customer, item);
        //Save Purchase Order
        NetsuiteAPISalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();

        //Verify the “Vertex Tax Error code” field is showing 'Success' status.
        String actualMessage = savedSalesOrderPage.getVertexTaxErrorCode();
        assertEquals(actualMessage, expectedTaxResult);

        //Click on the call details and verify customer details
        NetsuiteAPISalesOrderPage previousPurchaseOrderPage = checkDocumentLogs(savedSalesOrderPage,
                administration_origin, Destination_country, physical_origin, totalAmount, totalTax, taxRate, taxType, intraCountryTaxCode1);

        //Delete Purchase order
        previousPurchaseOrderPage.editOrder();
        previousPurchaseOrderPage.deleteOrder();
    }

    /**
     * Create a UK to Ireland sales order and validate results
     * @author mwilliams
	 * CNSAPI-*** //Currently Out of scope for VAT
	 */
	@Ignore
	@Test(groups = {"netsuite_vat"})
    protected void interstateUKIrelandSalesOrderTest( )
    {
        //Define Vendors and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_IR_IMPORTS;
        String expectedTaxResult = "Success";
        String subsidiary = "UK";
        String totalAmount = "<Total>80.0</Total>";
        String totalTax = "<TotalTax>0.0</TotalTax>";
        String taxRate = "<EffectiveRate>0.0</EffectiveRate>";
        String administration_origin = "<Country>GB</Country>";
        String Destination_country = "<Country>IE</Country>";
        String physical_origin = "<Country>GB</Country>";
        String taxType = "taxType=\"VAT\"";
        String intraCountryTaxCode1 = "<InvoiceTextCode>1</InvoiceTextCode>";

        NetsuiteItem item = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("80.00")
                .build();

        //Create Purchase Order and Validate its XML Response
        NetsuiteNavigationMenus SalesOrderMenu = getSalesOrderMenu();
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        //Navigate to Enter Purchase Order menu
        NetsuiteAPISalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(SalesOrderMenu);
        setupBasicOrder(salesOrderPage, customer, item);
        //Save Purchase Order
        NetsuiteAPISalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();

        //Verify the “Vertex Tax Error code” field is showing 'Success' status.
        String actualMessage = savedSalesOrderPage.getVertexTaxErrorCode();
        assertEquals(actualMessage, expectedTaxResult);

        //Click on the call details and verify customer details
        NetsuiteAPISalesOrderPage previousSalesOrderPage = checkDocumentLogs(savedSalesOrderPage,
                administration_origin, Destination_country, physical_origin, totalAmount, totalTax, taxRate, taxType, intraCountryTaxCode1);

        //Delete Purchase order
        previousSalesOrderPage.editOrder();
        previousSalesOrderPage.deleteOrder();
    }

    /**
     * Create a UK to US sales order and validate results
     * @author mwilliams
	 * CNSAPI-*** //Currently Out of scope for VAT
	 */
	@Ignore
	@Test(groups = {"netsuite_vat"})
    protected void interstateUKUSSalesOrderTest( )
    {
        //Define Vendors and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_US_IMPORTS;
        String expectedTaxResult = "Success";
        String subsidiary = "UK";
        String totalAmount = "<Total>120.00</Total>";
        String totalTax = "<TotalTax>20.0</TotalTax>";
        String taxRate = "<EffectiveRate>20.0</EffectiveRate>";
        String administration_origin = "<Country>GB</Country>";
        String Destination_country = "<Country>US</Country>";
        String physical_origin = "<Country>GB</Country>";
        String taxType = "taxType=\"VAT\"";
        String intraCountryTaxCode1 = "<InvoiceTextCode>21</InvoiceTextCode>";

        NetsuiteItem item = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("100.00")
                .build();

        //Create Purchase Order and Validate its XML Response
        NetsuiteNavigationMenus SalesOrderMenu = getSalesOrderMenu();
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        //Navigate to Enter Purchase Order menu
        NetsuiteAPISalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(SalesOrderMenu);
        setupBasicOrder(salesOrderPage, customer, item);
        //Save Purchase Order
        NetsuiteAPISalesOrderPage savedPurchaseOrderPage = salesOrderPage.saveOrder();

        //Verify the “Vertex Tax Error code” field is showing 'Success' status.
        String actualMessage = savedPurchaseOrderPage.getVertexTaxErrorCode();
        assertEquals(actualMessage, expectedTaxResult);

        //Click on the call details and verify customer details
        NetsuiteAPISalesOrderPage previousPurchaseOrderPage = checkDocumentLogs(savedPurchaseOrderPage,
                administration_origin, Destination_country, physical_origin, totalAmount, totalTax, taxRate, taxType, intraCountryTaxCode1);

        //Delete Purchase order
        deleteDocument(previousPurchaseOrderPage);
    }

    /**
     * Create a Austria to Portugal sales order and validate results
     * @author mwilliams
	 * CNSAPI-*** //Currently Out of scope for VAT
	 */
	@Ignore
	@Test(groups = {"netsuite_vat"})
    protected void interstateATPTSalesOrderTest( )
    {
        //Define Vendors and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_AUS_PORT;
        String expectedTaxResult = "Success";
        String subsidiary = "UK";
        String totalAmount = "<Total>109.50</Total>";
        String totalTax = "<TotalTax>9.50</TotalTax>";
        String taxRate = "<EffectiveRate>9.5</EffectiveRate>";
        String administration_origin = "<Country>AT</Country>";
        String Destination_country = "<Country>PT</Country>";
        String physical_origin = "<Country>AT</Country>";
        String taxType = "taxType=\"VAT\"";
        String intraCountryTaxCode1 = "<InvoiceTextCode>21</InvoiceTextCode>";

        NetsuiteItem item = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("100.00")
                .build();

        //Create Purchase Order and Validate its XML Response
        NetsuiteNavigationMenus SalesOrderMenu = getSalesOrderMenu();
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        //Navigate to Enter Purchase Order menu
        NetsuiteAPISalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(SalesOrderMenu);
        setupBasicOrder(salesOrderPage, customer, item);
        //Save Purchase Order
        NetsuiteAPIPurchaseOrderPage savedPurchaseOrderPage = salesOrderPage.saveOrder();

        //Verify the “Vertex Tax Error code” field is showing 'Success' status.
        String actualMessage = savedPurchaseOrderPage.getVertexTaxErrorCode();
        assertEquals(actualMessage, expectedTaxResult);

        //Click on the call details and verify customer details
        NetsuiteAPIPurchaseOrderPage previousPurchaseOrderPage = checkDocumentLogs(savedPurchaseOrderPage,
                administration_origin, Destination_country, physical_origin, totalAmount, totalTax, taxRate, taxType, intraCountryTaxCode1);

        //Delete Purchase order
        deleteDocument(previousPurchaseOrderPage);
    }
}
