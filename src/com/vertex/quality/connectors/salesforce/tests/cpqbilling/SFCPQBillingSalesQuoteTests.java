package com.vertex.quality.connectors.salesforce.tests.cpqbilling;

import com.vertex.quality.connectors.salesforce.enums.Constants;
import com.vertex.quality.connectors.salesforce.enums.NavigateMenu;
import com.vertex.quality.connectors.salesforce.pages.billing.SalesForceBillingOrderPage;
import com.vertex.quality.connectors.salesforce.pages.cpq.SalesForceCPQFieldMappingsPage;
import com.vertex.quality.connectors.salesforce.pages.cpq.SalesForceCPQPostLogInPage;
import com.vertex.quality.connectors.salesforce.pages.cpq.SalesForceCPQQuotePage;
import com.vertex.quality.connectors.salesforce.pages.cpq.SalesForceCPQSetupPage;
import com.vertex.quality.connectors.salesforce.pages.crm.SalesForceCRMAccountsPage;
import com.vertex.quality.connectors.salesforce.pages.crm.SalesForceCRMAddressesPage;
import com.vertex.quality.connectors.salesforce.pages.crm.SalesForceCRMConfigPage;
import com.vertex.quality.connectors.salesforce.pages.crm.SalesForceCRMOpportunityPage;
import com.vertex.quality.connectors.salesforce.tests.cpqbilling.base.SalesForceCPQBillingBaseTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SFCPQBillingSalesQuoteTests extends SalesForceCPQBillingBaseTest
{
	SalesForceCPQSetupPage setUpPage;
	SalesForceCPQPostLogInPage postLogInPage;
	SalesForceCPQFieldMappingsPage fieldMappingsPage;
	SalesForceCRMAddressesPage addressPage;
	SalesForceCRMAccountsPage accountPage;
	SalesForceCRMConfigPage configPage;
	SalesForceCPQQuotePage quotePage;
	SalesForceCRMOpportunityPage opportunityPage;
	SalesForceBillingOrderPage orderPage;
	String opportunityName;
	String quoteNumber;
	String orderNumber;
	String accountName;
	boolean activatedOrder = false;
	boolean quoteCreated = false;
	boolean opportunityCreated = false;
	boolean accountCreated = false;
	boolean logOpen = false;

	@BeforeMethod(alwaysRun = true)
	public void setupTest( )
	{
		setUpPage = new SalesForceCPQSetupPage(driver);
		postLogInPage = new SalesForceCPQPostLogInPage(driver);
		fieldMappingsPage = new SalesForceCPQFieldMappingsPage(driver);
		configPage = new SalesForceCRMConfigPage(driver);
		quotePage = new SalesForceCPQQuotePage(driver);
		opportunityPage = new SalesForceCRMOpportunityPage(driver);
		orderPage = new SalesForceBillingOrderPage(driver);
		accountPage = new SalesForceCRMAccountsPage(driver);
		addressPage = new SalesForceCRMAddressesPage(driver);

		// Log In to Sales force CPQ application
		postLogInPage = logInAsSalesForceBillingUser();
		// Switch to Vertex App menu, if any other menu item is selected
		postLogInPage.switchToCRMAppMenu(NavigateMenu.AppMenu.VERTEX.text);
		// Configure and Authenticate settings with Trusted Id and Uncheck Allow Address
		// Validation checkbox
		configPage.setAndValidateConfigSettings(SALESFORCE_TAX_LOOKUP_URL, SALESFORCE_TAX_CALCULATION_URL,
			CONFIG_USERNAME, CONFIG_PASSWORD, CONFIG_TRUSTEDID, Boolean.TRUE, CONFIG_TAX_PAYER_CODE, CONFIG_SEVERITY,
			CONFIG_MAX_ROWS);
		// Validate and Close authentication and Configuration success message
		validateAndCloseSuccessMessage(configPage);
	}

	@AfterMethod(alwaysRun = true)
	public void teardownTest( )
	{
		// Close the log pop up
        if (logOpen)
        {
			configPage.closeLogDialog();
		}

		// Switch to Billing App menu, if any other menu item is selected
		postLogInPage.switchToCRMAppMenu(NavigateMenu.AppMenu.BILLING.text);
		postLogInPage.closeLightningExperienceDialog();
		if ( activatedOrder )
		{
			postLogInPage.clickVertexPageTabMenu(NavigateMenu.Sales.ORDERS_TAB.text);
			orderPage.deleteOrder(orderNumber);
		}
		if ( quoteCreated )
		{
			postLogInPage.clickVertexPageTabMenu(NavigateMenu.Sales.QUOTES_TAB.text);
			quotePage.deleteQuote(quoteNumber);
		}
		if(opportunityCreated)
		{
			// Click on Opportunity from recent Items
			// Click on Delete button
			postLogInPage.clickSalesPageTab(NavigateMenu.Sales.OPPORTUNITIES_TAB.text);
			opportunityPage.clickOpportunityItemsFromRecentItems(opportunityName);
			postLogInPage.removeItemsFromRecentItems(opportunityName);
		}
		if ( accountCreated )
		{

			postLogInPage.clickSalesPageTab(NavigateMenu.Sales.ACCOUNTS_TAB.text);
			accountPage.deleteAccount(accountName);
		}
	}

	/**
	 * This test verifies tax callout happens with Basic Sales Order in Salesforce CPQ/Billing
	 * Creates new opportunity, quote, and order from quote
	 *
	 * CSFCPQ-515
	 *
	 * @author Erik Roeckel
	 */
	@Test(groups = { "sfcpqbilling_regression" })
	public void SalesForceCPQBillingBasicSalesQuoteTest( )
	{
		String physicalOriginSection = Constants.AddressSection.PHYSICAL_SECTION.text;
		String adminOriginSection = Constants.AddressSection.ADMIN_SECTION.text;
		opportunityName = "QaBasicSalesQuote";

		accountName = "QaAccountBasicSalesQuote";
		String quantity = "1";
		String productName = "Test Product";
		String description = "Adding product";
		String price = "100.00";
		double price1 = 100.00;
		double taxAmount = 9.50;

		// Click on Addresses tab
		postLogInPage.clickVertexPageTab(NavigateMenu.Vertex.ADDRESSES_TAB.text);
		// Edit Administrative Origin Address
		addressPage.editAddressInAddressesTab(adminOriginSection, "141 Filbert Street", "PA", "Chester", "19013", "USA",
			"");
		// Edit Default Physical Origin Address
		addressPage.editAddressInAddressesTab(physicalOriginSection, "1270 York Road", "PA", "Gettysburg", "17325",
			"USA", "");

		postLogInPage.clickVertexPageTabMenu(NavigateMenu.Vertex.VERTEX_BILLING_CONFIGURATION.text);

		// Switch to Billing App menu, if any other menu item is selected
		postLogInPage.switchToCRMAppMenu(NavigateMenu.AppMenu.BILLING.text);
		postLogInPage.closeLightningExperienceDialog();

		//Create Account
		accountPage.createAccount(accountName, "--None--", "", null, "");
		accountCreated = true;
		//Fill Billing Address
		accountPage.setAddress(Constants.AddressType.BILLING_ADDRESS.text, "5950 Broadway", "Los Angeles", "CA",
			"90030", "USA");
		//Fill Shipping Address
		accountPage.setAddress(Constants.AddressType.SHIPPING_ADDRESS.text, "5950 Broadway", "Los Angeles", "CA",
			"90030", "USA");
		accountPage.clickAccountSaveButton();

		// Create new opportunity
		opportunityPage.createCPQOpportunity(opportunityName, accountName, Constants.Stage.PROSPECT.text);
		opportunityCreated = true;
		opportunityPage.addProductToCPQOpportunity("Standard Price Book", productName, String.format("%s", price),
			String.format("%s", quantity), description);

		// Create new Quote
		quotePage.createNewQuote(opportunityName, accountName, "", "12");
		quoteCreated = true;
		double getQuoteTaxValue = quotePage.getProductTaxAmount(productName);
		assertEquals(getQuoteTaxValue, taxAmount);
		quotePage.clickPrdSaveButton();

		// Store quote number and validate quote line details are correct
		quoteNumber = quotePage.getEachQuoteDetails("Quote Number");
		quotePage.validateQuoteLineDetails(productName, 1, 10.00, price1, taxAmount);

		postLogInPage.switchToCRMAppMenu(NavigateMenu.AppMenu.VERTEX.text);
		postLogInPage.clickVertexPageTab(NavigateMenu.Vertex.LOGS_TAB.text);
		configPage.clickLogNameLink();
		logOpen = true;
		// Validate each log information on pop up
		String logName = configPage.getLogName();
		assertTrue(logName.startsWith(LOGNAME), "LogName verification Failed");

		String endpoint = configPage.getEndPoint();
		assertTrue(endpoint.equalsIgnoreCase(SALESFORCE_TAX_CALCULATION_URL), "Endpoint verification Failed");

		String responseStatusCode = configPage.getResponseStatusCode();
		assertEquals(responseStatusCode, RESPONSE_STATUS_CODE);

		String details = configPage.getLogDetailsSection();
		assertEquals(details, Constants.LogDetails.CALL_OUT.text);

		// Validate expected request xml
		String request = configPage.getLogRequestValue();
		assertTrue(request.contains("</urn:QuotationRequest>"));
		assertTrue(request.contains(
			"<urn:PhysicalOrigin><urn:StreetAddress1>1270 York Road</urn:StreetAddress1><urn:City>Gettysburg</urn:City><urn:MainDivision>PA</urn:MainDivision><urn:PostalCode>17325</urn:PostalCode><urn:Country>USA</urn:Country>"));
		assertTrue(request.contains(
			"<urn:Destination><urn:StreetAddress1>5950 Broadway</urn:StreetAddress1><urn:City>Los Angeles</urn:City><urn:MainDivision>CA</urn:MainDivision><urn:PostalCode>90030</urn:PostalCode><urn:Country>USA</urn:Country></urn:Destination>"));

		// Validate expected response xml
		String response = configPage.getLogResponseValue();
		assertTrue(response.contains("</QuotationResponse>"));
		assertTrue(response.contains(
			"<PhysicalOrigin taxAreaId=\"390010000\"><StreetAddress1>1270 York Road</StreetAddress1> <City>Gettysburg</City> <MainDivision>PA</MainDivision> <PostalCode>17325</PostalCode> <Country>USA</Country>"));
		assertTrue(response.contains(
			"<Destination taxAreaId=\"50371900\"><StreetAddress1>5950 Broadway</StreetAddress1> <City>Los Angeles</City> <MainDivision>CA</MainDivision> <PostalCode>90030</PostalCode> <Country>USA</Country> </Destination>"));
		assertTrue(response.contains(
			"<Jurisdiction jurisdictionLevel=\"STATE\" jurisdictionId=\"2398\">CALIFORNIA</Jurisdiction> <CalculatedTax>6.0</CalculatedTax> <EffectiveRate>0.06</EffectiveRate> <Taxable>100.0</Taxable>"));
		assertTrue(response.contains(
			"<Jurisdiction jurisdictionLevel=\"COUNTY\" jurisdictionId=\"2872\">LOS ANGELES</Jurisdiction> <CalculatedTax>1.25</CalculatedTax> <EffectiveRate>0.0125</EffectiveRate> <Taxable>100.0</Taxable>"));
		assertTrue(response.contains(
			"<Jurisdiction jurisdictionLevel=\"DISTRICT\" jurisdictionId=\"2873\">TRANSPORTATION COMMISSION (LATC)</Jurisdiction> <CalculatedTax>0.5</CalculatedTax> <EffectiveRate>0.005</EffectiveRate> <Taxable>100.0</Taxable>"));
		assertTrue(response.contains(
			"<Jurisdiction jurisdictionLevel=\"DISTRICT\" jurisdictionId=\"66710\">TRANSIT DISTRICT (LACT)</Jurisdiction> <CalculatedTax>0.5</CalculatedTax> <EffectiveRate>0.005</EffectiveRate> <Taxable>100.0</Taxable>"));
		assertTrue(response.contains(
			"<Jurisdiction jurisdictionLevel=\"DISTRICT\" jurisdictionId=\"88645\"><![CDATA[METROPOLITAN TRANSIT DISTRICT (LAMT)]]></Jurisdiction> <CalculatedTax>0.5</CalculatedTax> <EffectiveRate>0.005</EffectiveRate> <Taxable>100.0</Taxable>"));
		assertTrue(response.contains(
			"<Jurisdiction jurisdictionLevel=\"DISTRICT\" jurisdictionId=\"102864\">TRANSACTIONS AND USE TAX (LAMA)</Jurisdiction> <CalculatedTax>0.5</CalculatedTax> <EffectiveRate>0.005</EffectiveRate> <Taxable>100.0</Taxable>"));
		assertTrue(response.contains(
			"<Jurisdiction jurisdictionLevel=\"DISTRICT\" jurisdictionId=\"103278\"><![CDATA[MEASURE H TRANSACTIONS AND USE TAX (LACH)]]></Jurisdiction> <CalculatedTax>0.25</CalculatedTax> <EffectiveRate>0.0025</EffectiveRate> <Taxable>100.0</Taxable>"));
		// Close the log pop up
		configPage.closeLogDialog();
		logOpen = false;

		// Switch to Billing App menu, if any other menu item is selected
		postLogInPage.switchToCRMAppMenu(NavigateMenu.AppMenu.BILLING.text);

		// Create new order
		postLogInPage.clickVertexPageTabMenu(NavigateMenu.Sales.QUOTES_TAB.text);
		quotePage.selectQuote(quoteNumber);
		postLogInPage.selectItemsFromRecentItems(quoteNumber);
		quotePage.createOrder();
		orderPage.editOrder(accountName, "");
		// Activate order
		orderPage.clickOnActivateButton();
		activatedOrder = true;

		orderNumber = orderPage.getOrderNumber();
		orderPage.validateOrderProductDetails(productName, 1, price1, price1, taxAmount);

		postLogInPage.switchToCRMAppMenu(NavigateMenu.AppMenu.VERTEX.text);
		postLogInPage.clickVertexPageTab(NavigateMenu.Vertex.LOGS_TAB.text);
		configPage.clickLogNameLink();
		logOpen = true;
		// Validate each log information on pop up
		String logName1 = configPage.getLogName();
		assertTrue(logName1.startsWith(LOGNAME), "LogName verification Failed");

		String endpoint1 = configPage.getEndPoint();
		assertTrue(endpoint1.equalsIgnoreCase(SALESFORCE_TAX_CALCULATION_URL), "Endpoint verification Failed");

		String responseStatusCode1 = configPage.getResponseStatusCode();
		assertEquals(responseStatusCode1, RESPONSE_STATUS_CODE);

		String details1 = configPage.getLogDetailsSection();
		assertEquals(details1, Constants.LogDetails.CALL_OUT.text);

		// Validate expected request xml
		String request1 = configPage.getLogRequestValue();
		assertTrue(request1.contains("</urn:QuotationRequest>"));
		assertTrue(request1.contains(
			"<urn:PhysicalOrigin><urn:StreetAddress1>1270 York Road</urn:StreetAddress1><urn:City>Gettysburg</urn:City><urn:MainDivision>PA</urn:MainDivision><urn:PostalCode>17325</urn:PostalCode><urn:Country>USA</urn:Country>"));
		assertTrue(request1.contains(
			"<urn:Destination><urn:StreetAddress1>5950 Broadway</urn:StreetAddress1><urn:City>Los Angeles</urn:City><urn:MainDivision>CA</urn:MainDivision><urn:PostalCode>90030</urn:PostalCode><urn:Country>USA</urn:Country></urn:Destination>"));

		// Validate expected response xml
		String response1 = configPage.getLogResponseValue();
		assertTrue(response1.contains("</QuotationResponse>"));
		assertTrue(response1.contains(
			"<PhysicalOrigin taxAreaId=\"390010000\"><StreetAddress1>1270 York Road</StreetAddress1> <City>Gettysburg</City> <MainDivision>PA</MainDivision> <PostalCode>17325</PostalCode> <Country>USA</Country>"));
		assertTrue(response1.contains(
			"<Destination taxAreaId=\"50371900\"><StreetAddress1>5950 Broadway</StreetAddress1> <City>Los Angeles</City> <MainDivision>CA</MainDivision> <PostalCode>90030</PostalCode> <Country>USA</Country> </Destination>"));
		assertTrue(response1.contains(
			"<Jurisdiction jurisdictionLevel=\"STATE\" jurisdictionId=\"2398\">CALIFORNIA</Jurisdiction> <CalculatedTax>6.0</CalculatedTax> <EffectiveRate>0.06</EffectiveRate> <Taxable>100.0</Taxable>"));
		assertTrue(response1.contains(
			"<Jurisdiction jurisdictionLevel=\"COUNTY\" jurisdictionId=\"2872\">LOS ANGELES</Jurisdiction> <CalculatedTax>1.25</CalculatedTax> <EffectiveRate>0.0125</EffectiveRate> <Taxable>100.0</Taxable>"));
		assertTrue(response1.contains(
			"<Jurisdiction jurisdictionLevel=\"DISTRICT\" jurisdictionId=\"2873\">TRANSPORTATION COMMISSION (LATC)</Jurisdiction> <CalculatedTax>0.5</CalculatedTax> <EffectiveRate>0.005</EffectiveRate> <Taxable>100.0</Taxable>"));
		assertTrue(response1.contains(
			"<Jurisdiction jurisdictionLevel=\"DISTRICT\" jurisdictionId=\"66710\">TRANSIT DISTRICT (LACT)</Jurisdiction> <CalculatedTax>0.5</CalculatedTax> <EffectiveRate>0.005</EffectiveRate> <Taxable>100.0</Taxable>"));
		assertTrue(response1.contains(
			"<Jurisdiction jurisdictionLevel=\"DISTRICT\" jurisdictionId=\"88645\"><![CDATA[METROPOLITAN TRANSIT DISTRICT (LAMT)]]></Jurisdiction> <CalculatedTax>0.5</CalculatedTax> <EffectiveRate>0.005</EffectiveRate> <Taxable>100.0</Taxable>"));
		assertTrue(response1.contains(
			"<Jurisdiction jurisdictionLevel=\"DISTRICT\" jurisdictionId=\"102864\">TRANSACTIONS AND USE TAX (LAMA)</Jurisdiction> <CalculatedTax>0.5</CalculatedTax> <EffectiveRate>0.005</EffectiveRate> <Taxable>100.0</Taxable>"));
		assertTrue(response1.contains(
			"<Jurisdiction jurisdictionLevel=\"DISTRICT\" jurisdictionId=\"103278\"><![CDATA[MEASURE H TRANSACTIONS AND USE TAX (LACH)]]></Jurisdiction> <CalculatedTax>0.25</CalculatedTax> <EffectiveRate>0.0025</EffectiveRate> <Taxable>100.0</Taxable>"));
	}
}