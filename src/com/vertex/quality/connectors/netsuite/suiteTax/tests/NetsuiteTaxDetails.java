package com.vertex.quality.connectors.netsuite.suiteTax.tests;

import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteTestItems;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteHomepage;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.suiteTax.pages.transactions.NetsuiteAPIInvoicePage;
import com.vertex.quality.connectors.netsuite.suiteTax.pages.transactions.NetsuiteAPISalesOrderPage;
import com.vertex.quality.connectors.netsuite.suiteTax.tests.base.NetsuiteBaseAPITest;
import com.vertex.quality.connectors.netsuite.suiteTax.tests.base.NetsuiteBaseVatTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class NetsuiteTaxDetails extends NetsuiteBaseVatTest {

    /**
     * Create a Basic Sales order and an Invoice for ASIA PACIFIC (INDIA) in Suite Tax and
     * validate the RateClass, Invoice Code and Tax Structure in tax details
     * @author ravunuri
     * CNSAPI-1388
     */
    @Test(groups = {"netsuite_vat"})
    protected void SalesOrderInvoiceTaxDetailsTest ( )  {

		//Define Customers and Items
		NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_APAC_INTERSTATE;
		String expectedTaxResult = "Success";
		String taxRate = "$18";
		String invoiceTextCode = "InvoiceTextCode: 21";
		String rateClass = "Rate Class: Standard Rate";
		String taxStructure = "Tax Structure: SINGLE_RATE";
		String taxCode = "India Country";
		String taxType = "IN Sales";

		NetsuiteItem itemOne = NetsuiteItem
			.builder(NetsuiteItemName.ACC00002_ITEM)
			.quantity("1")
			.amount("100.00")
			.location("AsiaPacific_InterState")
			.build();
		try {
			//Create SALES ORDER and Validate RateClass, Invoice Code and Tax Structure in tax details
			NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
			NetsuiteSetupManagerPage setupManagerPage = configureSettings();
			NetsuiteHomepage homepage = activateSubsidiary();

			//Navigate to Enter Sales Order menu
			NetsuiteAPISalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
			setupBasicOrder(salesOrderPage, customer, itemOne);

			//Save Sales Order
			NetsuiteAPISalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();

			//Verify the “Vertex Tax Error code” field is showing 'Success' status.
			String actualMessage = savedSalesOrderPage.getVertexTaxErrorCode();
			assertEquals(actualMessage, expectedTaxResult);

			//Validate the RateClass, Invoice Code and Tax Structure in tax details
			String actualTaxDetails = savedSalesOrderPage.gettaxDetailstext();
			assertTrue(actualTaxDetails.contains(taxRate));
			assertTrue(actualTaxDetails.contains(invoiceTextCode));
			assertTrue(actualTaxDetails.contains(rateClass));
			assertTrue(actualTaxDetails.contains(taxStructure));
			assertTrue(actualTaxDetails.contains(taxCode));
			assertTrue(actualTaxDetails.contains(taxType));

			//Delete Sales order
			deleteDocument(savedSalesOrderPage);

			//------Create a basic INVOICE and Validate RateClass, Invoice Code and Tax Structure in tax details
			NetsuiteNavigationMenus InvoiceMenu = getCreateInvoiceMenu();
			NetsuiteAPIInvoicePage invoicePage = setupManagerPage.navigationPane.navigateThrough(InvoiceMenu);
			setupBasicOrder(invoicePage, customer, itemOne);

			//Save an Invoice
			NetsuiteAPIInvoicePage savedInvoicePage = invoicePage.saveOrder();

			//Verify the “Vertex Tax Error code” field is showing 'Success' status.
			String actualInvoiceResult = savedInvoicePage.getVertexTaxErrorCode();
			assertEquals(actualInvoiceResult, expectedTaxResult);

			//Validate the RateClass, Invoice Code and Tax Structure in tax details
			assertTrue(actualTaxDetails.contains(taxRate));
			assertTrue(actualTaxDetails.contains(invoiceTextCode));
			assertTrue(actualTaxDetails.contains(rateClass));
			assertTrue(actualTaxDetails.contains(taxStructure));
			assertTrue(actualTaxDetails.contains(taxCode));
			assertTrue(actualTaxDetails.contains(taxType));
		}
		finally {
			clearValues();
		}
    }

    /**
     * Create a Basic Sales order for EU/UK in Suite Tax and validate VAT tax details in XML response
     * Domestic: UK to UK, Customer and Taxpayer addresses in UK. Both are registered in UK [Subsidiary is UK].
     * @author ravunuri
     * CNSAPI-1389
     */
    @Test(groups = {"netsuite_suite_smoke"})
    protected void SalesOrderTaxDetailsUKUKTest ( ) {
        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_UK;
		country = "UK";

		String taxDetails = "UNITED KINGDOM VAT :$20\n" + "InvoiceTextCode: 21\n" + "Rate Class: Standard Rate\n" +
							"Tax Structure: SINGLE_RATE";
        String expectedTaxResult = "Success";
        String taxRate = "<EffectiveRate>0.2</EffectiveRate>";
        String calculatedTax = "<CalculatedTax>20.0</CalculatedTax>";
        String jurisdiction = "UNITED KINGDOM";
        String invoiceTextCode = "<InvoiceTextCode>21</InvoiceTextCode>";
        String rateClass = "Standard Rate";
        String taxStructure = "SINGLE_RATE";
		String customerRegistrationNum = "<TaxRegistrationNumber>123456789</TaxRegistrationNumber>";
		String sellerRegistrationNum = "<TaxRegistrationNumber>222222222</TaxRegistrationNumber>";
		String taxResult = "TAXABLE";
        String taxType = "VAT";
        String taxCode = "OGSTC";
		String isService_XML_response = "isService=\"true\"";

        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM).quantity("1").amount("100.00")
                .build();

		try {
			//Create SALES ORDER and Validate RateClass, Invoice Code and Tax Structure in tax details
			NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
			NetsuiteSetupManagerPage setupManagerPage = configureSettings();
			NetsuiteHomepage homepage = activateSubsidiary();

			//Navigate to Enter Sales Order menu
			NetsuiteAPISalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
			setupBasicOrder(salesOrderPage, customer, itemOne);

			//Save Sales Order
			NetsuiteAPISalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();

			//Verify the “Vertex Tax Error code” field is showing 'Success' status.
			String actualMessage = savedSalesOrderPage.getVertexTaxErrorCode();
			assertEquals(actualMessage, expectedTaxResult);

			//Validate the RateClass, Invoice Code and Tax Structure in tax details
			String actualTaxDetails = savedSalesOrderPage.gettaxDetailstext();
			System.out.println(actualTaxDetails);
			assertTrue(actualTaxDetails.contains(taxDetails));

			//Verify customer(buyer) and seller TaxRegistrationNumbers in the XML Request
			checkDocumentLogsXmlRequest(savedSalesOrderPage, customerRegistrationNum, sellerRegistrationNum);

			//Verify customer and tax details in the XML Response
			checkDocumentLogs(savedSalesOrderPage, taxRate, invoiceTextCode, rateClass, taxStructure, taxCode, taxType,
				calculatedTax, jurisdiction, customerRegistrationNum, sellerRegistrationNum, taxResult);
		}
		finally {
		clearValues();
		}
	}

    /**
     * Create a Basic INVOICE for EU/UK in Suite Tax and validate VAT tax details in XML response
     * Domestic: UK to UK, Customer and Taxpayer addresses in UK. Only TaxPayer registered in UK and Customer is Not.
     * @author ravunuri
     * CNSAPI-1390
     */
    @Test(groups = {"netsuite_suite_smoke"})
    protected void InvoiceTaxDetailsInvoiceUKUKTest ( )  {

        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_UK_NOT_REG;
        String expectedTaxResult = "Success";
        String taxRate = "<EffectiveRate>0.2</EffectiveRate>";
        String calculatedTax = "<CalculatedTax>20.0</CalculatedTax>";
        String jurisdiction = "UNITED KINGDOM";
        String invoiceTextCode = "<InvoiceTextCode>21</InvoiceTextCode>";
        String rateClass = "Standard Rate";
        String taxStructure = "SINGLE_RATE";
        String sellerRegistrationId = "<SellerRegistrationId>GB123456789</SellerRegistrationId>";
        String taxResult="TAXABLE";
        String taxType="VAT";
        String taxCode="OGSTC";

        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM).quantity("1").amount("100.00")
				.build();

        //------Create a basic INVOICE and Validate RateClass, Invoice Code and Tax Structure in tax details
        NetsuiteNavigationMenus InvoiceMenu = getCreateInvoiceMenu();
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        NetsuiteAPIInvoicePage invoicePage = setupManagerPage.navigationPane.navigateThrough(InvoiceMenu);
        setupBasicOrder(invoicePage, customer, itemOne);

        //Save an Invoice
        NetsuiteAPIInvoicePage savedInvoicePage = invoicePage.saveOrder();

        //Verify the “Vertex Tax Error code” field is showing 'Success' status.
        String actualInvoiceResult = savedInvoicePage.getVertexTaxErrorCode();
        assertEquals(actualInvoiceResult, expectedTaxResult);

        //Verify the “Vertex Tax Error code” field is showing 'Success' status.
        String actualMessage = savedInvoicePage.getVertexTaxErrorCode();
        assertEquals(actualMessage, expectedTaxResult);

        //Click on the call details and verify customer details
        checkDocumentLogs(savedInvoicePage, taxRate, invoiceTextCode, rateClass, taxStructure, taxCode, taxType,
                calculatedTax, jurisdiction, taxResult, sellerRegistrationId);
    }

    /**
     * Create a Basic Sales Order for a VAT transaction and validate the xml response for tax details
     * Domestic: UK to UK, Registration: Customer and TaxPayer both Not registered, ItemType: Goods
     * @author ravunuri
     * CNSAPI-1391
     */
    @Test(groups = {"netsuite_vat","suite_tax_regression"})
    protected void UkIntraStateBothNotRegGoodsVatTest ( )  {
        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_UK_NOT_REG;

        //List of XML response validations
        String expectedTaxResult = "Success";
        String transDate = "9/15/2016";
        String total = "<Total>120.0</Total>";
        String taxRate = "<EffectiveRate>0.2</EffectiveRate>";
        String calculatedTax = "<CalculatedTax>20.0</CalculatedTax>";
        String jurisdiction = "<Jurisdiction jurisdictionLevel=\"COUNTRY\" jurisdictionId=\"78295\">UNITED KINGDOM</Jurisdiction>";
        String invoiceTextCode = "<InvoiceTextCode>21</InvoiceTextCode>";
        String rateClass = "rateClassification=\"Standard Rate\"";
        String taxStructure = "taxStructure=\"SINGLE_RATE\"";
        String taxResult="taxResult=\"TAXABLE\"";
        String taxType="taxType=\"VAT\"";
        String country="<Country>GB</Country>";

        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM).quantity("1").amount("100.00").build();

        //------Create a basic INVOICE and Validate RateClass, Invoice Code and Tax Structure in tax details
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();

        //Navigate to Enter Sales Order menu
        NetsuiteAPISalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);

        //Enter transDate older than current date
        salesOrderPage.enterTransDate(transDate);

        //setupBasicOrder(salesOrderPage, customer, itemOne);
        setupBasicOrder(salesOrderPage, customer, itemOne);
        NetsuiteAPISalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();

        //Verify the “Vertex Tax Error code” field is showing 'Success' status.
        String actualMessage = savedSalesOrderPage.getVertexTaxErrorCode();
        assertEquals(actualMessage, expectedTaxResult);

        //Click on the call details and verify customer details
        checkDocumentLogs(savedSalesOrderPage, taxRate, invoiceTextCode, rateClass, taxStructure, taxType,
			calculatedTax, jurisdiction, total, country, taxResult);
    }

    /**
     * Create a Basic Sales Order with a discount and check for
     * @author mwilliams
     * CNSAPI-536  (Story: CNSAPI-1141)
     */
	@Test(groups = {"netsuite_suite_smoke"})
    public void discountElementXmlTest ()
    {
        itemOne = NetsuiteTestItems.testItem25off.getItem();
        price = 75.0;
        taxRate = 0.095;
        country = "USA";
        coupon = "25";
        super.baseTest();
        super.postTest();
    }

	/**
	 * Create a Sales Order and verify that the currency from the transaction record is used instead of the currency from
	 * the customer record to post to Vertex.
	 * @author ravunuri
	 * CNSAPI-1381
	 */
	@Test(groups = {"netsuite_suite_smoke"})
	public void CurrencyEnhancementTest ()
	{
		customer = NetsuiteCustomer.CUSTOMER_CURRENCY;
		itemOne = NetsuiteItem.builder(NetsuiteItemName.ACC00002_ITEM).build();
		currency = "Canadian dollar";
		taxRate = 0.06;
		//List of XML response validations
		tokenList.add("isoCurrencyCodeAlpha=\"CAD\"");
		super.baseTest();
		super.postTest();
	}

	/**
	 * Create a Sales Order with Customer Code/ID Exemption and verify that the tax is exempted (0% Tax) from Vertex.
	 * @author ravunuri
	 * CNSAPI-252
	 */
	@Test(groups = {"suite_tax_regression"})
	public void VertexCustomerCodeExemptionTest ()
	{
		customer = NetsuiteCustomer.CUSTOMER_CODE_EXEMPT;
		itemOne = NetsuiteItem.builder(NetsuiteItemName.ACC00002_ITEM).build();
		taxRate = 0.0;
		//List of XML response validations
		tokenList.add("<CustomerCode isBusinessIndicator=\"true\">VTXCCode</CustomerCode>");
		super.baseTest();
		super.postTest();
	}

	/**
	 * Create a Sales Order with Customer Class Exemption and verify that the tax is exempted (0% Tax) from Vertex.
	 * @author ravunuri
	 * CNSAPI-261
	 */
	@Test(groups = {"suite_tax_regression"})
	public void VertexCustomerClassExemptionTest ()
	{
		customer = NetsuiteCustomer.CUSTOMER_CLASS_EXEMPT;
		itemOne = NetsuiteItem.builder(NetsuiteItemName.ACC00002_ITEM).build();
		taxRate = 0.0;
		//List of XML response validations
		tokenList.add("<CustomerCode classCode=\"VTXCCLASS\" isBusinessIndicator=\"true\">");
		super.baseTest();
		super.postTest();
	}
}
