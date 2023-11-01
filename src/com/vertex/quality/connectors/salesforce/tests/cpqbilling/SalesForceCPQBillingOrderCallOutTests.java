package com.vertex.quality.connectors.salesforce.tests.cpqbilling;

import com.vertex.quality.connectors.salesforce.data.TestInput;
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

/**
 * This test covers validating an order call in Salesforce which triggers call to Vertex Oseries
 *
 * @author brendaj
 */
public class SalesForceCPQBillingOrderCallOutTests extends SalesForceCPQBillingBaseTest
{
	SalesForceCPQSetupPage setUpPage;
	SalesForceCPQPostLogInPage postLogInPage;
	SalesForceCPQFieldMappingsPage fieldMappingsPage;
	SalesForceCRMConfigPage configPage;
	SalesForceCPQQuotePage quotePage;
	SalesForceCRMOpportunityPage opportunityPage;
	SalesForceBillingOrderPage orderPage;
	SalesForceCRMAccountsPage accountPage;
	SalesForceCRMAddressesPage addressPage;
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
	 * this test covers the steps to create an Order call out and validates log
	 * Includes setup of Setting O-series, Add Opportunity with Product, Add Quote, Add Order
	 *
	 * CSFCPQ-94
	 */
	@Test(groups = { "sfcpqbilling_smoke" })
	public void CreateCPQBillingOrderCallOutTest( )
	{
		String physicalOriginSection = Constants.AddressSection.PHYSICAL_SECTION.text;
		String adminOriginSection = Constants.AddressSection.ADMIN_SECTION.text;
		opportunityName = "QAOppCPQBillOrder";
		accountName = "QaAcntCPQBillOrder";
		String productName = "Test Product";
		String quantity = "1";
		String price = "10";
		String description = TestInput.DESCRIPTION;
		double price1 = 10.00;
		double taxAmount = 0.83;
		double taxAmount1 = 1.65;

		String quoteAddress1 = TestInput.DESTINATION_ADDRESS1;
		String quoteState = TestInput.DESTINATION_STATE;
		String quoteCity = TestInput.DESTINATION_CITY;
		String quoteZip = TestInput.DESTINATION_POSTALCODE;
		String quoteCountry = TestInput.DESTINATION_COUNTRY;

		// Click on Addresses tab
		postLogInPage.clickVertexPageTab(NavigateMenu.Vertex.ADDRESSES_TAB.text);
		// Edit Administrative Origin Address
		addressPage.editAddressInAddressesTab(adminOriginSection, "1270 York Road", "PA", "Gettysburg", "17325", "USA",
			"");
		// Edit Default Physical Origin Address
		addressPage.editAddressInAddressesTab(physicalOriginSection, "1270 York Road", "PA", "Gettysburg", "17325",
			"USA", "");

		postLogInPage.clickVertexPageTabMenu(NavigateMenu.Vertex.VERTEX_CPQ_CONFIGURATION.text);

		// Switch to Sales App menu, if any other menu item is selected
		postLogInPage.switchToCRMAppMenu(NavigateMenu.AppMenu.BILLING.text);
		postLogInPage.closeLightningExperienceDialog();
		// Create Account
		accountPage.createAccount(accountName, "--None--", "", null, "");
		accountCreated = true;
		// Fill Shipping Address
		accountPage.setAddress(Constants.AddressType.SHIPPING_ADDRESS.text, quoteAddress1, quoteCity, quoteState,
			quoteZip, quoteCountry);
		// Fill Billing Address
		accountPage.setAddress(Constants.AddressType.BILLING_ADDRESS.text, quoteAddress1, quoteCity, quoteState,
			quoteZip, quoteCountry);

		accountPage.clickAccountSaveButton();

		opportunityPage.createCPQOpportunity(opportunityName, accountName, Constants.Stage.PROSPECT.text);
		opportunityCreated = true;
		opportunityPage.addProductToCPQOpportunity("Standard Price Book", productName, String.format("%s", price),
			String.format("%s", quantity), description);

		// Create Quote
		quotePage.createNewQuote(opportunityName, accountName, "", "12");
		quoteCreated = true;
		double getQuoteTaxValue = quotePage.getProductTaxAmount(productName);
		assertEquals(getQuoteTaxValue, taxAmount);

		// Edit product quantity in Quote
		quotePage.updateProductInQuote(productName, String.format("%s", price), String.format("%s", 2));
		quotePage.clickQuoteCalculateButton();
		double getSecondQuoteTaxValue = quotePage.getProductTaxAmount(productName, false);
		assertEquals(getSecondQuoteTaxValue, taxAmount1);

		quotePage.clickQuoteCancelButton();
		quotePage.clickEditLines();
		double getThirdQuoteTaxValue = quotePage.getProductTaxAmount(productName);
		assertEquals(getThirdQuoteTaxValue, taxAmount);
		quotePage.clickPrdSaveButton();
		quoteNumber = quotePage.getEachQuoteDetails("Quote Number");

		// Create order
		quotePage.createOrder();
		orderPage.editOrder(accountName, "");
		orderPage.clickOnActivateButton();
		activatedOrder = true;

		// Validate tax is correct for order
		orderNumber = orderPage.getOrderNumber();
		orderPage.validateOrderProductDetails(productName, 1, price1, price1, taxAmount);

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

		String request = configPage.getLogRequestValue();
		assertTrue(request.contains(
			"<urn:PhysicalOrigin><urn:StreetAddress1>1270 York Road</urn:StreetAddress1><urn:City>Gettysburg</urn:City><urn:MainDivision>PA</urn:MainDivision><urn:PostalCode>17325</urn:PostalCode><urn:Country>USA</urn:Country></urn:PhysicalOrigin>"));
		assertTrue(request.contains(
			"<urn:Destination><urn:StreetAddress1>4505 Ridgeside Dr</urn:StreetAddress1><urn:City>Dallas</urn:City><urn:MainDivision>TX</urn:MainDivision><urn:PostalCode>75244-7524</urn:PostalCode><urn:Country>USA</urn:Country></urn:Destination>"));

		String response = configPage.getLogResponseValue();
		assertTrue(response.contains(
			"<PhysicalOrigin taxAreaId=\"390010000\"><StreetAddress1>1270 York Road</StreetAddress1> <City>Gettysburg</City> <MainDivision>PA</MainDivision> <PostalCode>17325</PostalCode> <Country>USA</Country> </PhysicalOrigin>"));
		assertTrue(response.contains(
			"<Destination taxAreaId=\"441130760\"><StreetAddress1>4505 Ridgeside Dr</StreetAddress1> <City>Dallas</City> <MainDivision>TX</MainDivision> <PostalCode>75244-7524</PostalCode> <Country>USA</Country> </Destination>"));
		assertTrue(response.contains(
			"<Jurisdiction jurisdictionLevel=\"STATE\" jurisdictionId=\"35763\">TEXAS</Jurisdiction> <CalculatedTax>0.63</CalculatedTax> <EffectiveRate>0.0625</EffectiveRate> <Taxable>10.0</Taxable>"));
		assertTrue(response.contains(
			"<Jurisdiction jurisdictionLevel=\"CITY\" jurisdictionId=\"77868\">DALLAS</Jurisdiction> <CalculatedTax>0.1</CalculatedTax> <EffectiveRate>0.01</EffectiveRate> <Taxable>10.0</Taxable>"));
		assertTrue(response.contains(
			"<Jurisdiction jurisdictionLevel=\"TRANSIT_DISTRICT\" jurisdictionId=\"78120\"><![CDATA[DALLAS METROPOLITAN TRANSIT AUTHORITY]]></Jurisdiction> <CalculatedTax>0.1</CalculatedTax> <EffectiveRate>0.01</EffectiveRate> <Taxable>10.0</Taxable>"));
	}
}