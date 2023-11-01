package com.vertex.quality.connectors.netsuite.suiteTax.tests.certification;

import com.vertex.quality.common.enums.Country;
import com.vertex.quality.common.enums.State;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteVendor;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteAddress;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuitePrices;
import com.vertex.quality.connectors.netsuite.suiteTax.pages.transactions.NetsuiteAPIInvoicePage;
import com.vertex.quality.connectors.netsuite.suiteTax.pages.transactions.NetsuiteAPIPurchaseOrderPage;
import com.vertex.quality.connectors.netsuite.suiteTax.pages.transactions.NetsuiteAPISalesOrderPage;
import com.vertex.quality.connectors.netsuite.suiteTax.tests.base.NetsuiteBaseAPITest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Tests products and product classes
 *
 * @author mwilliams, ravunuri
 */
public class NetSuiteAPIProductTests extends NetsuiteBaseAPITest {
    /**
     * Checks that the document's XML contains the product's product class
     * CNSAPI-133
     */
    @Ignore
    @Test(groups = {"netsuite_suite_cert","netsuite_suite_smoke" })
    protected void verifyProductClassOnBlanketOrdersTest( )
    {
        String productClass = "BLANKET ORDER PRODUCT CLASS";
        NetsuiteVendor vendor = NetsuiteVendor.VENDOR_PA;
		String subsidiary = "Honeycomb Mfg.";
        NetsuiteItem item = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .amount("10.00")
                .quantity("1")
                .build();

        NetsuiteNavigationMenus BlanketOrderMenu = getBlanketOrderMenu();
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        NetsuiteAPIPurchaseOrderPage blanketOrderPage = setupManagerPage.navigationPane.navigateThrough(BlanketOrderMenu);
        setupBasicPurchaseOrder(blanketOrderPage, vendor, subsidiary, item);

		NetsuiteAPIPurchaseOrderPage savedSalesOrderPage = blanketOrderPage.saveOrder();
		NetsuiteAPIPurchaseOrderPage previousSalesOrderPage = checkDocumentLogs(savedSalesOrderPage, productClass);
    }

    /**
     * CNSAPI-414
     */
    @Test(groups = {"netsuite_suite_cert","netsuite_suite_smoke"})
    protected void creditTaxJournalTest ()
    {
        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
        NetsuiteItem item = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .amount("100.00")
                .location("01: San Francisco")
                .build();
        NetsuiteAddress address = NetsuiteAddress
                .builder("91608")
                .fullAddressLine1("100 Universal City Plaza")
                .addressLine1("100 Universal City Plz")
                .city("Universal City")
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
        NetsuiteAPIInvoicePage invoicePage = setupManagerPage.navigationPane.navigateThrough(invoiceMenu);
        //Enter few items and select shipping address on each item and save the invoice
        setupBasicOrder(invoicePage, customer, address, item);
        //invoicePage.setVertexPostToVertex();
        invoicePage.saveOrder();
        invoicePage.getVertexTaxErrorCode();
        //deleteDocument(invoicePage);

        // Cash Sales
        invoicePage = setupManagerPage.navigationPane.navigateThrough(cashMenu);
        //Enter few items and save the invoice
        setupBasicOrder(invoicePage, customer, item);
        //invoicePage.setVertexPostToVertex();
        invoicePage.saveOrder();
        invoicePage.getVertexTaxErrorCode();
        //deleteDocument(invoicePage);
    }

    /**
     * CNSAPI-415
     */
    @Test(groups = {"netsuite_suite_cert","netsuite_suite_smoke"})
    protected void discountInvoiceTest ()
    {
        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1").amount("100.00")
                .build();
        NetsuiteItem itemTwo = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1").amount("100.00")
                .location("01: San Francisco")
                .build();
		String taxAmt = "17.10"; String total = "197.10";

        //Get menus
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        //Select a customer, make sure a valid address is populated
        NetsuiteAPISalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
        //Enter few items and select shipping address on each item and save the order
        setupBasicOrder(salesOrderPage, customer, itemOne, itemTwo);
        //salesOrderPage.calculateShippingCost();
        salesOrderPage.selectPromotion("10% AutoApply");
        salesOrderPage.saveOrder();
		// Verify total and tax amount is correct
		String totalTaxDetails =  salesOrderPage.getTotallingTableSummaryText();
		assertTrue(totalTaxDetails.contains(total));
		assertTrue(totalTaxDetails.contains(taxAmt));
        //Verify the “Vertex Tax Error code” field is showing Success.
        salesOrderPage.getVertexTaxErrorCode();
    }

    /**
     * CNSAPI-416
     */
    @Test(groups = {"netsuite_suite_cert","netsuite_suite_smoke"})
    protected void classAndProductExemptionsTest ()
    {
        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_SUPPLY_CA;
        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1").amount("100.00")
                .build();
        NetsuiteItem itemTwo = NetsuiteItem
                .builder(NetsuiteItemName.BLACKBERRY_PLAYBOOK)
                .quantity("1").amount("499.00")
                .build();
		String taxAmt = "61.66"; String total = "710.66"; String subtotal = "599.00";

        //Get menus
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        NetsuiteAPISalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
        //Enter few items and select shipping address on each item and save the order
        salesOrderPage.selectCustomer(NetsuiteCustomer.CUSTOMER_SUPPLY_CA);
		setupBasicOrder(salesOrderPage, customer, itemOne, itemTwo );
		salesOrderPage.saveOrder();
		// Verify total and tax amount is correct
		String totalTaxDetails =  salesOrderPage.getTotallingTableSummaryText();
		assertTrue(totalTaxDetails.contains(total));
		assertTrue(totalTaxDetails.contains(subtotal));
		assertTrue(totalTaxDetails.contains(taxAmt));
        //Verify the “Vertex Tax Error code” field is showing Success.
        salesOrderPage.getVertexTaxErrorCode();
    }
}
