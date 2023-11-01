package com.vertex.quality.connectors.netsuite.oneworld.tests.certification;

import com.vertex.quality.common.enums.Country;
import com.vertex.quality.common.enums.State;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteAddress;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuitePrices;
import com.vertex.quality.connectors.netsuite.oneworld.pages.transactions.NetsuiteOWCreditMemoPage;
import com.vertex.quality.connectors.netsuite.oneworld.pages.transactions.NetsuiteOWInvoicePage;
import com.vertex.quality.connectors.netsuite.oneworld.pages.transactions.NetsuiteOWSalesOrderPage;
import com.vertex.quality.connectors.netsuite.oneworld.tests.base.OWCertBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class OWTaxTests extends OWCertBaseTest {

    /**
     * Certifies Tax Preview Feature
     * CNSL-901
     */
    @Test(groups = { "netsuite_cert_ow"})
    protected void taxPreviewFeatureTests (){

        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
        NetsuiteItem item = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("100.00")
                .location("01: San Francisco")
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
        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        //Select a customer, make sure a valid address is populated
        NetsuiteOWInvoicePage invoicePage = setupManagerPage.navigationPane.navigateThrough(invoiceMenu);
        //Enter few items and select shipping address on each item and save the invoice
        setupBasicInvoice(invoicePage, customer, address, item);
        //Set post to Vertex
        invoicePage.setVertexPostToVertex();
        //Preview Tax and retrieve the values
        String[] previewValues = invoicePage.previewTax();

        Assert.assertEquals(previewValues[0],ExpectedValue.getTaxAmount());
        Assert.assertEquals(previewValues[1],ExpectedValue.getTotal());
    }

    /**
     * Verifies Tax Preview Summary Feature
     * CNSL-1566
     */
    @Test(groups = { "netsuite_cert_ow"})
    protected void taxPreviewSummaryTests (){

        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
        NetsuiteItem item = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("100.00")
                .build();
        NetsuitePrices ExpectedValue = NetsuitePrices.builder()
                .itemTaxRate("8.25%")
                .itemTaxCode(defaultTaxCode)
                .subtotal("100.00")
                .taxAmount("8.25")
                .total("108.25").build();

        //Get menus
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        //Select a customer, make sure a valid address is populated
        NetsuiteOWSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
        //Enter few items and select shipping address on each item and save the invoice
        setupBasicOrder(salesOrderPage, customer, item);
        //Preview Tax and retrieve the values
        String[] previewValues = salesOrderPage.previewTaxSummary();

        Assert.assertEquals(previewValues[0],ExpectedValue.getTaxAmount());
        Assert.assertEquals(previewValues[1],ExpectedValue.getTotal());
    }

    /**
     * Verifies Non-tax able item Tax code works properly
     * CNSL-1491
     */
    @Test(groups = { "netsuite_cert_ow"})
    protected void nonTaxableItemCodeTests (){

        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
        NetsuiteItem item = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("100.00")
                .taxCode(defaultTaxCode)
                .build();
        NetsuiteItem item1 = NetsuiteItem
                .builder(NetsuiteItemName.BIC00001_ITEM)
                .quantity("1")
                .amount("310.00")
                .taxCode("-Not Taxable-")
                .build();
        NetsuitePrices ExpectedValue = NetsuitePrices.builder()
                .itemTaxRate("8.25%")
                .itemTaxCode(defaultTaxCode)
                .subtotal("100.00")
                .taxAmount("8.25")
                .total("418.25").build();

        //Get menus
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        //Select a customer, make sure a valid address is populated
        NetsuiteOWSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
        //Enter few items and select shipping address on each item and save the invoice
        setupBasicOrder(salesOrderPage, customer, item,item1);

        //Preview Tax and retrieve the values
        String[] previewValues = salesOrderPage.previewTaxSummary();

        Assert.assertEquals(previewValues[0],ExpectedValue.getTaxAmount());
        Assert.assertEquals(previewValues[1],ExpectedValue.getTotal());
    }

    /**
     * Certifies Tax Adjustment Feature
     * CNSL-902
     * Ensure Tax Only Adjustment Exist in Environment
     */
    @Test(groups = { "netsuite_cert_ow"})
    protected void taxAdjustmentTests (){

        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
        NetsuiteItem item = NetsuiteItem
                .builder(NetsuiteItemName.SALES_TAX_ONLY)
                .quantity("1")
                .amount("100.00")
                .taxCode(defaultTaxCode)
                .build();
        NetsuiteAddress address = NetsuiteAddress
                .builder("75244")
                .fullAddressLine1("3723 Valley View Ln")
                .addressLine1("3723 Valley View Ln")
                .city("Dallas")
                .state(State.TX)
                .country(Country.USA)
                .zip9("75244-4902")
                .build();
        NetsuitePrices ExpectedValue = NetsuitePrices.builder()
                .itemTaxRate("8.25%")
                .itemTaxCode(defaultTaxCode)
                .subtotal("100.00")
                .taxAmount("8.25")
                .total("108.25").build();

        String postingPeriod = "Dec 2018";
        String location = "01: San Francisco";
        String transactionNumber;

        NetsuitePrices expectedPricesBeforeSaving = NetsuitePrices
                .builder()
                .itemTaxRate("0.0%")
                .itemTaxCode(defaultTaxCode)
                .subtotal("100.00")
                .taxAmount("0.00")
                .total("100.00")
                .build();
        NetsuitePrices expectedPricesAfterSaving = NetsuitePrices
                .builder()
                .itemTaxRate("6.63%")
                .itemTaxCode(defaultTaxCode)
                .subtotal("100.00")
                .taxAmount("6.63")
                .total("106.63")
                .build();
        //Check if Tax Adjustment Item exists in the account by entering “Tax Only Adjustment” in the global search box. If the item exists, it will show up in the results.
        //If it doesn't exist, create a new Non-Inventory Item for Sale called Tax Only Adjustment. This will be used for US Tax adjustments

        //Get menus
        NetsuiteNavigationMenus creditMemoMenu = getCreditMemoMenu();
        NetsuiteNavigationMenus searchCreditMemoMenu = getTransactionSearchMenu();
        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        NetsuiteOWCreditMemoPage creditMemoPage = setupManagerPage.navigationPane.navigateThrough(creditMemoMenu);
        //Add Tax Adjustment Item and enter an amount
        transactionNumber = creditMemoPage.getTransactionNumber();
        setupBasicCreditMemo(creditMemoPage, customer, address, item);
        creditMemoPage.selectLocation(location);
        creditMemoPage.selectPostingPeriod(postingPeriod);
        //Verify the VERTEX DISTRIBUTE TAX checkbox is checked and a distributeTaxRequest is posted to Vertex.
        creditMemoPage.setVertexPostToVertex();
        creditMemoPage.setDistributeTax();

        checkBasicOrderAmounts(creditMemoPage, expectedPricesBeforeSaving);
        checkOrderWithTax(creditMemoPage, expectedPricesBeforeSaving, item);
        //Save the Credit Memo
        NetsuiteOWCreditMemoPage savedCreditMemoPage = creditMemoPage.saveOrder();
        //****Verify Distribute Tax request
        //checkBasicOrderAmounts(savedCreditMemoPage, expectedPricesAfterSaving);
        //checkOrderWithTax(savedCreditMemoPage, expectedPricesAfterSaving, item);


        NetsuiteOWCreditMemoPage editedCreditMemoPage = savedCreditMemoPage.editOrder();

        editedCreditMemoPage.deleteMemo();
    }

    /**
     * Certifies Tax Adjustment Feature
     * CNSL-903
     */
    @Test(groups = { "netsuite_cert_ow"})
    protected void distributeTaxTests (){

        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
        NetsuiteItem item = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("100.00")
                .location("01: San Francisco")
                .taxCode(defaultTaxCode)
                .build();
        NetsuiteAddress address = NetsuiteAddress
                .builder("75244")
                .fullAddressLine1("3723 Valley View Ln")
                .addressLine1("3723 Valley View Ln")
                .city("Dallas")
                .state(State.TX)
                .country(Country.USA)
                .zip9("75244-4902")
                .build();
        NetsuitePrices ExpectedValue = NetsuitePrices.builder()
                .itemTaxRate("8.25%")
                .itemTaxCode(defaultTaxCode)
                .subtotal("100.00")
                .taxAmount("8.25")
                .total("108.25").build();

        NetsuitePrices expectedPricesBeforeSaving = NetsuitePrices
                .builder()
                .itemTaxRate("0.0%")
                .itemTaxCode(defaultTaxCode)
                .subtotal("100.00")
                .taxAmount("0.00")
                .total("100.00")
                .build();
        NetsuitePrices expectedPricesAfterSaving = NetsuitePrices
                .builder()
                .itemTaxRate("6.63%")
                .itemTaxCode(defaultTaxCode)
                .subtotal("100.00")
                .taxAmount("6.63")
                .total("106.63")
                .build();

        //Get menus
        NetsuiteNavigationMenus InvoiceMenu = getCreateInvoiceMenu();
        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        NetsuiteOWInvoicePage InvoicePage = setupManagerPage.navigationPane.navigateThrough(InvoiceMenu);
        //Add Tax Adjustment Item and enter an amount
        setupBasicInvoice(InvoicePage, customer, address, item);
        InvoicePage.setLocation(item.getLocation());
        //Verify the VERTEX DISTRIBUTE TAX checkbox is checked and a distributeTaxRequest is posted to Vertex.
        InvoicePage.setVertexPostToVertex();
        InvoicePage.setDistributeTax();

        //Save the Invoice
        NetsuiteOWInvoicePage savedInvoicePage = InvoicePage.saveOrder();
        //****Verify Distribute Tax request
        NetsuiteOWInvoicePage editInvoicePage = savedInvoicePage.editOrder();

        savedInvoicePage.deleteOrder();
    }

	/**
	 * When a credit memo is created off an invoice the tax rate should match what is being sent from Vertex.
	 * Verifies there are no Tax rate inconsistencies in credit memo
	 * CNSL-1861
	 */
	@Test(groups = { "netsuite_cert_ow", "netsuite_ow_smoke"})
	protected void taxRateInconsistenciesInCreditMemoTest (){

		//Define Customers and Items
		NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
		NetsuiteItem item = NetsuiteItem
			.builder(NetsuiteItemName.ACC00002_ITEM)
			.quantity("1").amount("100.00").location("01: San Francisco")
			.build();

		NetsuiteAddress address = NetsuiteAddress
			.builder("91608")
			.fullAddressLine1("100 Universal City Plaza")
			.addressLine1("100 Universal City Plz")
			.city("San Francisco")
			.state(State.CA)
			.country(Country.USA)
			.zip9("91608-1002").build();

		String expectedTax = "8.25";

		//Get menus
		NetsuiteNavigationMenus invoiceMenu = getCreateInvoiceMenu();
		//Execute test
		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		//Select a customer, make sure a valid address is populated
		NetsuiteOWInvoicePage invoicePage = setupManagerPage.navigationPane.navigateThrough(invoiceMenu);
		//Enter few items and select shipping address on each item and save the invoice
		setupBasicInvoice(invoicePage, customer, address, item);

		//Save the Invoice
		NetsuiteOWInvoicePage savedInvoicePage = invoicePage.saveOrder();
		//Capture Total Tax of an invoice and Validate it matches the expected tax
		String invoiceActualTax = savedInvoicePage.getOrderTaxAmount();
		assertEquals(invoiceActualTax, expectedTax);

		//Create and Save the Credit Memo off an Invoice
		NetsuiteOWInvoicePage creditMemoPage = savedInvoicePage.creditOrder();
		NetsuiteOWInvoicePage savedCreditMemoPage = creditMemoPage.saveOrder();
		//Capture Total Tax of a Credit Memo and Validate it matches with Invoice tax
		String creditMemoActualTax = savedCreditMemoPage.getOrderTaxAmount();
		assertEquals(creditMemoActualTax, invoiceActualTax);

		NetsuiteOWInvoicePage editCreditMemoPage = savedCreditMemoPage.editOrder();
		editCreditMemoPage.deleteOrder();
	}
}