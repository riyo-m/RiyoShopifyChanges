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

public class NetsuiteLatinAmerica extends NetsuiteBaseAPITest {

    /**
     * Create a Basic Sales order and an Invoice for LATIN AMERICA (BZ-BZ) in Suite Tax and validate the xml response
     * @author ravunuri
     * CNSAPI-673 //Currently Out of scope for VAT
     */
    @Ignore
    @Test(groups = {"netsuite_vat"})
    protected void CreateSalesOrderInvoiceLatamBzbzTest ( )  {

        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_LATAM_BZBZ;
        String customerId = "LatinAmerica_BZBZ";
        String expectedTaxResult = "Success";
        String totalAmount = "<Total>112.5</Total>";
        String totalTax = "<TotalTax>12.5</TotalTax>";
        String taxRate = "<EffectiveRate>0.125</EffectiveRate>";
        String country = "BZ";
        String intraCountryTaxCode = "<InvoiceTextCode>21</InvoiceTextCode>";
        String salesTax = "<Imposition impositionId=\"1\">General Sales Tax</Imposition>";
        String shipFromState = "<MainDivision>San Ignacio</MainDivision>";
        String shipToState = "<MainDivision>CAYO</MainDivision>";
        String taxType = "VAT";

        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("100.00")
                .location("LatinAmerica_BZBZ")
                .build();

        //------Create SALES ORDER and Validate XML Response------
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
                country, shipFromState, shipToState, salesTax, totalAmount, totalTax, taxRate, intraCountryTaxCode,
                taxType);

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
        checkDocumentLogs(savedInvoicePage, customerId, country, shipFromState, shipToState, totalAmount, totalTax,
                taxRate, intraCountryTaxCode, taxType);

        //Delete Invoice
        savedInvoicePage.editOrder();
        savedInvoicePage.deleteOrder();
    }

    /**
     * Create a Basic Sales order and an Invoice for LATIN AMERICA (CR-CO) in Suite Tax and
     * validate the xml response for tax details
     * @author ravunuri
	 * CNSAPI-674 //Currently Out of scope for VAT
	 */
	@Ignore
	@Test(groups = {"netsuite_vat"})
    protected void CreateSalesOrderInvoiceLatamCrcoTest ( )  {

        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_LATAM_CRCO;
        String customerId = "LatinAmerica_CRCO";
        String expectedTaxResult = "Success";
        String totalAmount = "<Total>100.0</Total>";
        String totalTax = "<TotalTax>0.0</TotalTax>";
        String taxRate = "<EffectiveRate>0.0</EffectiveRate>";
        String shipFromState = "<MainDivision>San José</MainDivision>";
        String shipToState = "<MainDivision>Borgota</MainDivision>";
        String shipFromCountry = "<Country>CR</Country>";
        String shipToCountry = "<Country>CO</Country>";
        String intraCountryTaxCode = "<InvoiceTextCode>1</InvoiceTextCode>";
        String taxType = "taxType=\"NONE\"";

        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("100.00")
                .location("LatinAmerica_CRCO")
                .build();

        //------Create a SALES ORDER and Validate XML Response-----
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
                shipFromCountry, shipToCountry, shipFromState, shipToState, totalAmount, totalTax, taxRate,
                intraCountryTaxCode, taxType);

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
        checkDocumentLogs(savedInvoicePage, customerId, shipFromCountry, shipToCountry, shipFromState, shipToState,
                totalAmount, totalTax, taxRate, intraCountryTaxCode, taxType);

        //Delete Invoice
        savedInvoicePage.editOrder();
        savedInvoicePage.deleteOrder();
    }

    /**
     * Create a Basic Purchase Order for LATIN AMERICA (BZ-BZ) in Suite Tax and validate the xml response
     * @author ravunuri
	 * CNSAPI-659 //Currently Out of scope for VAT
	 */
	@Ignore
	@Test(groups = {"netsuite_vat"})
    protected void CreatePurchaseOrderLatamBzbzTest ( )  {

        //Define Vendors and Items
        NetsuiteVendor vendor = NetsuiteVendor.VENDOR_LATAM_BZBZ;
        String subsidiary = "LatinAmerica_BZBZ";
        String expectedTaxResult = "Success";
        String totalAmount = "<Total>56.25</Total>";
        String totalTax = "<TotalTax>6.25</TotalTax>";
        String taxRate = "<EffectiveRate>0.125</EffectiveRate>";
        String country = "<Country>BZ</Country>";
        String intraCountryTaxCode = "<InvoiceTextCode>21</InvoiceTextCode>";
        String impositionId = "<Imposition impositionId=\"1\">General Sales Tax</Imposition>";
        String shipFromState = "<MainDivision>San Ignacio</MainDivision>";
        String shipToState = "<MainDivision>CAYO</MainDivision>";
        String taxType = "VAT";

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
                impositionId, shipFromState, shipToState, totalAmount, totalTax, taxRate, intraCountryTaxCode, taxType);

        //Delete Purchase order
        deleteDocument(previousPurchaseOrderPage);
    }

    /**
     * Create a Basic Purchase Order for LATIN AMERICA (CR-CO) in Suite Tax and validate the xml response
     * @author ravunuri
	 * CNSAPI-660 //Currently Out of scope for VAT
	 */
	@Ignore
	@Test(groups = {"netsuite_vat"})
    protected void CreatePurchaseOrderLatamCrcoTest ( )  {

        //Define Vendors and Items
        NetsuiteVendor vendor = NetsuiteVendor.VENDOR_LATAM_CRCO;
        String subsidiary = "LatinAmerica_CRCO";
        String expectedTaxResult = "Success";
        String totalAmount = "<Total>50.0</Total>";
        String totalTax = "<TotalTax>0.0</TotalTax>";
        String taxRate = "<EffectiveRate>0.0</EffectiveRate>";
        String shipFromState = "<MainDivision>San José</MainDivision>";
        String shipToState = "<MainDivision>Borgota</MainDivision>";
        String shipFromCountry = "<Country>CR</Country>";
        String shipToCountry = "<Country>CO</Country>";
        String intraCountryTaxCode = "<InvoiceTextCode>1</InvoiceTextCode>";
        String taxType = "taxType=\"VAT\"";
        String rate_situs = "rateClassification=\"Zero Rate\" situs=\"PHYSICAL_ORIGIN\"";

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
        NetsuiteAPIPurchaseOrderPage previousPurchaseOrderPage = checkDocumentLogs(savedPurchaseOrderPage, shipFromCountry,
                shipToCountry, shipFromState, shipToState, totalAmount, totalTax, taxRate, intraCountryTaxCode,
                rate_situs, taxType);

        //Delete Purchase order
        deleteDocument(previousPurchaseOrderPage);
    }

}