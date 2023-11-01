package com.vertex.quality.connectors.netsuite.common.tests.base;

import com.vertex.quality.common.enums.DBConnectorNames;
import com.vertex.quality.common.enums.DBEnvironmentDescriptors;
import com.vertex.quality.common.enums.DBEnvironmentNames;
import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.pojos.EnvironmentCredentials;
import com.vertex.quality.common.pojos.EnvironmentInformation;
import com.vertex.quality.common.pojos.OSeriesConfiguration;
import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.common.utils.SQLConnection;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.selenium.VertexElementUtilities;
import com.vertex.quality.common.utils.selenium.VertexWaitUtilities;
import com.vertex.quality.connectors.netsuite.common.components.NetsuiteTotpGenerator;
import com.vertex.quality.connectors.netsuite.common.enums.*;
import com.vertex.quality.connectors.netsuite.common.interfaces.NetsuiteBasicOrder;
import com.vertex.quality.connectors.netsuite.common.interfaces.NetsuiteOrderWithGiftCertificate;
import com.vertex.quality.connectors.netsuite.common.interfaces.NetsuiteOrderWithShipping;
import com.vertex.quality.connectors.netsuite.common.interfaces.NetsuiteOrderWithTax;
import com.vertex.quality.connectors.netsuite.common.pages.*;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteCustomerPage;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuitePage;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteTransactions;
import com.vertex.quality.connectors.netsuite.common.pages.customers.NetsuiteCustomerClassListPage;
import com.vertex.quality.connectors.netsuite.common.pages.customers.NetsuiteCustomerClassPage;
import com.vertex.quality.connectors.netsuite.common.pages.customers.NetsuiteCustomerListPage;
import com.vertex.quality.connectors.netsuite.common.pages.factories.NetsuitePageFactory;
import com.vertex.quality.connectors.netsuite.common.pages.transactions.NetsuiteTransactionsPage;
import com.vertex.quality.connectors.netsuite.common.pojos.*;
import com.vertex.quality.connectors.netsuite.scis.pages.NetsuiteSCISLoginPage;
import com.vertex.quality.connectors.netsuite.scis.pages.NetsuiteSCISStorePage;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.List;

import static org.testng.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Contains navigation functions for tests in Netsuite
 *
 * @author hho
 */
@Test(groups = { "netsuite" })
public class NetsuiteBaseTest extends VertexUIBaseTest<NetsuiteAuthenticationPage>
{
	protected EnvironmentInformation environmentInformation;
	protected EnvironmentCredentials environmentCredentials;
	protected OSeriesConfiguration oSeriesConfiguration;
	protected String username;
	protected String password;
	protected String authenticationAnswer1;
	protected String authenticationAnswer2;
	protected String authenticationAnswer3;
	protected String environmentURL;
	protected String trustedId;
	protected String companyCode;
	protected String addressServiceURL;
	protected String taxServiceURL;
	public VertexWaitUtilities wait;
	protected String defaultTaxCode = "Vertex";
	protected By unrolled_view_locator = By.xpath("//a[@title='Unrolled view on']");
	private VertexElementUtilities element;
	public static String netsuiteFlavor;
	public static NetsuitePageFactory pageFactory;
	public NetsuiteHomepage homepage;
	public Stack<String> recordRecyclingBin = new Stack<>();

	protected final boolean isDriverServiceProvisioned = Boolean.getBoolean("services.webdriver.provisioned");
	protected final boolean isDriverHeadlessMode = Boolean.getBoolean("services.webdriver.headless");
	protected final boolean isSeleniumHost = Boolean.getBoolean("services.webdriver.host.selenium");

	/**
	 * Used to be able to set connector name from the child base test
	 *
	 * @return the connector name based on the base test
	 */
	protected DBConnectorNames getConnectorName( )
	{
		return DBConnectorNames.NETSUITE_SINGLE_COMPANY;
	}

	/**
	 * Set Connector name using it's connector ID
	 * Grab properties from Jenkins CMD line
	 * if no property is set pass -1
	 * -1 is not a valid Connetor ID and will result
	 * in the default getConnector method being called
	 * This will return the desired connector or the default connector
	 *
	 * @param thisConnectorId
	 *
	 * @return
	 */
	protected DBConnectorNames getConnectorName( int thisConnectorId )
	{
		int thatConnectorId;
		for ( DBConnectorNames connector : DBConnectorNames.values() )
		{
			thatConnectorId = connector.getId();
			if ( thisConnectorId == thatConnectorId )
			{
				return connector;
			}
		}
		return getConnectorName();
	}

	/**
	 * Used to be able to set environment name from the child base test
	 *
	 * @return the environment name based on the base test
	 */
	protected DBEnvironmentNames getEnvironmentName( )
	{
		return DBEnvironmentNames.QA;
	}

	/**
	 * Used to be able to set environment descriptor from the child base test
	 *
	 * @return the environment descriptor based on the base test
	 */
	protected DBEnvironmentDescriptors getEnvironmentDescriptor( )
	{
		return DBEnvironmentDescriptors.NETSUITE_LEGACY;
	}

	/**
	 * Set test run's window dimensions. This prevents test failure whenever
	 * the test runs in headless mode
	 */
	protected void setWindowSize( )
	{
		Dimension d = new Dimension(1920, 1200);
		driver
			.manage()
			.window()
			.setSize(d);
		return;
	}

	@Override
	/**
	 *
	 * @return
	 */ protected void setupTestRun( )
	{
		super.setupTestRun();
	}

	@Override
	/**
	 *
	 */ protected NetsuiteHomepage loadInitialTestPage( )
	{
		super.loadInitialTestPage();
		//Starting point of all test in Netsuite
		NetsuiteHomepage authPage = null;
		//You MUST set the screen size when running in headless mode to avoid an unknown error
		setWindowSize();

		//Set Tax Calculation URL
		getCredentials(getConnectorId());
		netsuiteFlavor = getConnectorName().getConnectorName();

		authPage = new NetsuiteHomepage(driver);
		pageFactory = new NetsuitePageFactory(driver, authPage);
		return authPage;
	}

	@Override
	/**
	 * Runs clean up tasks after every Test
	 */ protected void handleLastTestPage( ITestResult res )
	{
		//Remove records here
		//emptyBin();

		super.handleLastTestPage(res);

		return;
	}

	/**
	 * Remove from Bin
	 */
	protected void emptyBin( )
	{
		String recordId;

		while ( !recordRecyclingBin.isEmpty() )
		{
			recordId = recordRecyclingBin.pop();

			NetsuitePage recordPage = new NetsuitePage(driver) { };

			//Put recordId into Search Bar
			//Hit Edit button
			recordPage.editRecord();
			//Find the page's delete button ex. "Delete" or "Actions" -> "Delete"
			recordPage.deleteRecord();
		}
	}

	/**
	 * Gets "Connector Id" or returns -1
	 * This is used to set the URL endpoint
	 * and other company specific info
	 */
	protected int getConnectorId( )
	{
		int connectorId = -1;
		String connectorIdInput = System.getProperty("connectorId");

		if ( connectorIdInput != null )
		{
			connectorId = Integer.parseInt(connectorIdInput);
			VertexLogger.log(connectorIdInput);
		}
		return connectorId;
	}

	/**
	 * Gets credentials for the connector from the database
	 */
	protected void getCredentials( int connectorId )
	{
		try
		{
			//oSeriesConfiguration = SQLConnection.getOSeriesConfiguration(getConnectorName(connectorId));
			//environmentInformation = SQLConnection.getEnvironmentInformation(getConnectorName(connectorId), getEnvironmentName(),
			//	getEnvironmentDescriptor());
			//environmentCredentials = SQLConnection.getEnvironmentCredentials(environmentInformation);

			username = "ConnectorTestAutomation@vertexinc.com";
			System.out.println(username);
			password = "SuiteTeam22$$";
			System.out.println(password);
			authenticationAnswer1 = "grade";
			authenticationAnswer2 = "name";
			authenticationAnswer3 = "job";
			environmentURL = "https://system.netsuite.com/pages/customerlogin.jsp?country=US";
			trustedId = "VTXTST123";
			companyCode = "TSTtaxpayer";
			addressServiceURL = "https://oseries9-final.vertexconnectors.com/vertex-ws/services/LookupTaxAreas70";
			taxServiceURL = "https://oseries9-final.vertexconnectors.com/vertex-ws/services/CalculateTax70";
			VertexLogger.log("Tax Url: " + taxServiceURL, VertexLogLevel.INFO);
			VertexLogger.log("Address Url: " + addressServiceURL, VertexLogLevel.INFO);
		}
		catch ( Exception e )
		{
			VertexLogger.log("failed to retrieve credentials for a Netsuite test", VertexLogLevel.ERROR);
			e.printStackTrace();
		}
	}

	/**
	 * Signs into the netsuite homepage as whatever role desired
	 *
	 * @return the homepage for that role
	 */
	protected List<NetsuiteHomepage> signIntoHomepageAs( )
	{
		ArrayList<NetsuiteHomepage> pages = new ArrayList<NetsuiteHomepage>();
		NetsuiteHomepage homePage;
		NetsuiteHomepage specificHomePage = null;

		signInToAuthenticationPage();
		homePage = authenticateIntoHomepage(); // Legacy Method
		//homePage = signInToHomePage();

		//Check input from cmd line if !Null select that roll
		String roleSelectInput = System.getProperty("netsuiteEnvironment");
		if ( roleSelectInput != null )
		{
			specificHomePage = homePage.navigationPane.signInAs(roleSelectInput);
		}

		String pageTitle = driver.getTitle();
		if ( pageTitle.contains("login challenge") )
		{
			//Do 2FA stuff here
			By input = By.xpath("//input[@placeholder='6-digit code']");
			wait.waitForElementDisplayed(input, 10);
			WebElement twoFAInput = driver.findElement(input);
			String totpCode = NetsuiteTotpGenerator
				.getInstance()
				.getTwoFactorCode();
			twoFAInput.sendKeys(totpCode);

			By submitBtn = By.xpath("//div[@aria-label='Submit']");
			Actions actions = new Actions(driver);
			actions.moveToElement(driver.findElement(submitBtn), 0, 0);
			actions
				.click()
				.perform();
		}

		pages.add(homePage);
		pages.add(specificHomePage);

		return pages;
	}

	/**
	 * Signs into the netsuite homepage as the single company role
	 *
	 * @return the homepage for that role
	 */
	protected NetsuiteHomepage signIntoHomepageAsSingleCompany( )
	{
		List<NetsuiteHomepage> pages = signIntoHomepageAs();
		NetsuiteHomepage homePage = pages.get(0);
		if ( pages.get(1) != null )
			return pages.get(1);

		NetsuiteHomepage singleCompanyHomePage = homePage.navigationPane.signInAsSingleCompany();
		return singleCompanyHomePage;
	}

	/**
	 * Signs into the netsuite homepage as one world role
	 *
	 * @return the homepage for one world
	 */
	protected NetsuiteHomepage signintoHomepageAsOneWorld( )
	{
		List<NetsuiteHomepage> pages = signIntoHomepageAs();
		NetsuiteHomepage homePage = pages.get(0);
		if ( pages.get(1) != null )
			return pages.get(1);

		NetsuiteHomepage oneWorldHomePage = homePage.navigationPane.signInAsOneWorld();

		return oneWorldHomePage;
	}

	/**
	 * Signs into the netsuite homepage as one world role
	 *
	 * @return the homepage for one world
	 */
	protected NetsuiteHomepage signintoHomepageAsOneWorldRP( )
	{
		List<NetsuiteHomepage> pages = signIntoHomepageAs();
		NetsuiteHomepage homePage = pages.get(0);
		if ( pages.get(1) != null )
			return pages.get(1);

		NetsuiteHomepage oneWorldHomePage = homePage.navigationPane.signInAsOneWorldRP();

		return oneWorldHomePage;
	}

	/**
	 * Signs into the netsuite homepage as Signle Company role
	 *
	 * @return the homepage for one world
	 */
	protected NetsuiteHomepage signintoHomepageAsSingleRP( )
	{
		List<NetsuiteHomepage> pages = signIntoHomepageAs();
		NetsuiteHomepage homePage = pages.get(0);
		if ( pages.get(1) != null )
			return pages.get(1);

		NetsuiteHomepage singleCompanyHomePage = homePage.navigationPane.signInAsSingleCompanyRP();

		return singleCompanyHomePage;
	}

	/**
	 * Signs into the netsuite homepage as Netsuite API
	 *
	 * @return the homepage for Netsuite API
	 */
	protected NetsuiteHomepage signintoHomepageAsNetsuiteAPI( )
	{
		List<NetsuiteHomepage> pages = signIntoHomepageAs();
		NetsuiteHomepage homePage = pages.get(0);
		if ( pages.get(1) != null )
			return pages.get(1);

		NetsuiteHomepage netsuiteAPIHomePage = homePage.navigationPane.signInAsNetsuiteAPI();

		return netsuiteAPIHomePage;
	}

	/**
	 * signs into the authentication check
	 *
	 * @return the authentication page
	 */
	private NetsuiteAuthenticationPage signInToAuthenticationPage( )
	{
		Actions actor = new Actions((driver));
		driver.get(environmentURL);

		NetsuiteLoginPage loginPage = new NetsuiteLoginPage(driver);
		wait = new VertexWaitUtilities(loginPage, driver);
		System.out.println("Logging into Netsuite Homepage");

		loginPage.enterUsername(username);
		loginPage.enterPassword(password);

		NetsuiteAuthenticationPage authPage = loginPage.loginToAuthPage();
		System.out.println("Logged into Netsuite");
		// Ensure an initial User Role is selected each time we attempt to log in
		//Wait till present
		String pageTitle = driver.getTitle();
		if ( pageTitle.contains(
			"The system was not able to select a login role for you based on your usual NetSuite usage.") )
		{
			WebElement userRole = wait.waitForElementDisplayed(By.xpath("//td[normalize-space( text() )='Vertex QA - OneWorld (TSTDRV1505402 )']/../td[last()]/a[1]"),
				5);

			actor
				.moveToElement(userRole)
				.click()
				.perform();
		}

		return authPage;
	}

	/**
	 * Sign in directly to the homepage bypassing authentication
	 *
	 * @return the Home Page
	 */
	private NetsuiteHomepage signInToHomePage( )
	{
		driver.get(environmentURL);

		NetsuiteLoginPage loginPage = new NetsuiteLoginPage(driver);
		wait = new VertexWaitUtilities(loginPage, driver);

		takeScreenshot("-- Before Login");
		loginPage.enterUsername(username);
		loginPage.enterPassword(password);
		//TODO Authorization page or quickLogin
		NetsuiteHomepage homepage = loginPage.quickLogin();

		return homepage;
	}

	/**
	 * Answers the authentication check to go to the homepage
	 *
	 * @return the Netsuite homepage
	 */
	private NetsuiteHomepage authenticateIntoHomepage( )
	{
		NetsuiteAuthenticationPage authPage = new NetsuiteAuthenticationPage(driver);
		System.out.println("Refreshing...");
		driver
			.navigate()
			.refresh();
		System.out.println("Going to Auth Screen");
		authPage.answerAuthenticationQuestion("grade", "name", "job");
		System.out.println("clicking submit button");
		NetsuiteHomepage homepage = authPage.loginToHomepage(driver);
		return homepage;
	}

	/**
	 * Setup a basic order
	 *
	 * @param orderPage the current order page
	 * @param customer  the customer to use
	 * @param items     the list of items to add
	 *
	 * @return the order page in case additional changes need to be made
	 */
	protected void setupBasicOrder( NetsuiteTransactions orderPage, NetsuiteCustomer customer, NetsuiteItem... items )
	{
		// Click on "Unrolled View" Button to change the display of transaction page tabs to a single page
		boolean isUnrolledView_Button_Visible = orderPage.element.isElementDisplayed(unrolled_view_locator);
		if ( isUnrolledView_Button_Visible )
		{
			orderPage.click.clickElement(unrolled_view_locator);
		}

		orderPage.selectCustomer(customer);
		orderPage.getItemSection();
		int itemIndex = 1;
		for ( NetsuiteItem item : items )
		{
			orderPage.addItemToTransaction(item, itemIndex);
			itemIndex++;
		}
	}

	/**
	 * Create an Expense report
	 *
	 * @param expensePage the current order page
	 * @param employee    the customer to use
	 * @param expenses    the list of items to add
	 *
	 * @return the order page in case additional changes need to be made
	 */
	protected void createExpenseReport( NetsuiteTransactions expensePage, NetsuiteEmployee employee, NetsuiteCustomer customer,
		NetsuiteExpense... expenses )
	{
		// Click on "Unrolled View" Button to change the display of transaction page tabs to a single page
		boolean isUnrolledView_Button_Visible = expensePage.element.isElementDisplayed(unrolled_view_locator);
		if ( isUnrolledView_Button_Visible )
		{
			expensePage.click.clickElement(unrolled_view_locator);
		}
		// Select Employee from drop down list
		expensePage.selectEmployee(employee);
		// Create Expense/s
		int expenseIndex = 1;
		for ( NetsuiteExpense expense : expenses )
		{
			expensePage.addDropDownExpenseCategory(expense, expenseIndex, customer.getCustomerName());
			expenseIndex++;
		}
	}

	/**
	 * Setup a basic order with item as drop down list
	 *
	 * @param orderPage the current order page
	 * @param customer  the customer to use
	 * @param items     the list of items to add from drop down list
	 *
	 * @return the order page in case additional changes need to be made
	 */
	protected void setupBasicOrderItemList( NetsuiteTransactions orderPage, NetsuiteCustomer customer, NetsuiteItem... items )
	{
		// Click on "Unrolled View" Button to change the display of transaction page tabs to a single page
		boolean isUnrolledView_Button_Visible = orderPage.element.isElementDisplayed(unrolled_view_locator);
		if ( isUnrolledView_Button_Visible )
		{
			orderPage.click.clickElement(unrolled_view_locator);
		}

		orderPage.selectCustomer(customer);
		orderPage.getItemSection();
		int itemIndex = 1;
		for ( NetsuiteItem item : items )
		{
			orderPage.addDropDownItemToTransaction(item, itemIndex);
			itemIndex++;
		}
	}

	/**
	 * Setup a basic order
	 *
	 * @param orderPage the current order page
	 * @param customer  the customer to use
	 * @param address   the address WE WANT TO SHIP TO
	 * @param items     the list of items to add
	 *
	 * @return the order page in case additional changes need to be made
	 */
	protected void setupBasicOrder( NetsuiteTransactions orderPage, NetsuiteCustomer customer, NetsuiteAddress address,
		NetsuiteItem... items )
	{
		setupBasicOrder(orderPage, customer, items);
		if ( !orderPage.selectExistingShipToAddress(address) )
		{
			orderPage.createNewShipToAddress(address);
			assertTrue(orderPage.isAddressVerified());
		}
		assertTrue(orderPage.isCleansedShippingAddressDisplayed(address));
	}

	/**
	 * Setup a basic order with item as drop down list
	 *
	 * @param orderPage the current order page
	 * @param customer  the customer to use
	 * @param address   the address WE WANT TO SHIP TO
	 * @param items     the list of items to add
	 *
	 * @return the order page in case additional changes need to be made
	 */
	protected void setupBasicOrderItemList( NetsuiteTransactions orderPage, NetsuiteCustomer customer, NetsuiteAddress address,
		NetsuiteItem... items )
	{
		setupBasicOrderItemList(orderPage, customer, items);
		if ( !orderPage.selectExistingShipToAddress(address) )
		{
			orderPage.createNewShipToAddress(address);
			assertTrue(orderPage.isAddressVerified());
		}
		assertTrue(orderPage.isCleansedShippingAddressDisplayed(address));
	}

	/**
	 * Setup a basic blanket order
	 *
	 * @param orderPage the current order page
	 * @param customer  the customer to use
	 * @param items     the list of items to add
	 *
	 * @return the order page in case additional changes need to be made
	 */
	protected void setupBasicBlanketOrder( NetsuiteTransactions orderPage, NetsuiteCustomer customer, NetsuiteItem... items )
	{
		orderPage.selectCustomer(customer);
		orderPage.selectItemsTab();
		for ( NetsuiteItem item : items )
		{
			orderPage.addItemToTransaction(item);
		}
	}

	/**
	 * Setup a basic purchase order
	 *
	 * @param orderPage  the current order page
	 * @param vendor     the customer to use
	 * @param items      the list of items to add
	 * @param subsidiary the subsidiary to select
	 *
	 * @return the order page in case additional changes need to be made
	 */
	protected void setupBasicPurchaseOrder( NetsuiteTransactions orderPage, NetsuiteVendor vendor, String subsidiary,
		NetsuiteItem... items )
	{
		// Click on "Unrolled View" Button to change the display of transaction page tabs to a single page
		boolean isUnrolledView_Button_Visible = orderPage.element.isElementDisplayed(unrolled_view_locator);
		if ( isUnrolledView_Button_Visible )
		{
			orderPage.click.clickElement(unrolled_view_locator);
		}

		orderPage.selectVendor(vendor);
		orderPage.selectSubsidiary(subsidiary);
		orderPage.getItemSection();
		int itemIndex = 1;
		for ( NetsuiteItem item : items )
		{
			orderPage.addItemToPurchaseOrder(item, itemIndex);
			itemIndex++;
		}
	}

	/**
	 * Checks if the basic expected order amounts equals the actual amounts
	 *
	 * @param orderPage       the order page
	 * @param expectedAmounts the expected amounts
	 */
	protected void checkBasicOrderAmounts( NetsuiteBasicOrder orderPage, NetsuitePrices expectedAmounts )
	{
		String actualSubtotal = orderPage.getOrderSubtotal();
		VertexLogger.log("actualSubtotal = " + actualSubtotal);

		String actualTotal = orderPage.getOrderTotal();
		VertexLogger.log("actualTotal = " + actualTotal);

		String expectedSubtotal = expectedAmounts.getSubtotal();
		VertexLogger.log("expectedSubtotal = " + expectedSubtotal);

		String expectedTotal = expectedAmounts.getTotal();
		VertexLogger.log("expectedTotal = " + expectedTotal);

		assertEquals(expectedSubtotal, actualSubtotal);
		assertEquals(expectedTotal, actualTotal);
	}

	/**
	 * Checks if the gift certificate amount equals the actual amount
	 *
	 * @param orderPage       the order page
	 * @param expectedAmounts the expected amounts
	 */
	protected void checkOrderWithGiftCertificate( NetsuiteOrderWithGiftCertificate orderPage,
		NetsuitePrices expectedAmounts )
	{
		String actualGiftCertificateAmount = orderPage.getGiftCertificateAmount();
		System.out.println("actualGiftCertificateAmount = " + actualGiftCertificateAmount);
		String expectedGiftCertificateAmount = expectedAmounts.getGiftCertificateAmount();
		System.out.println("expectedGiftCertificateAmount = " + expectedGiftCertificateAmount);
		assertEquals(expectedGiftCertificateAmount, actualGiftCertificateAmount);
	}

	/**
	 * Checks if the tax amounts equals the actual amounts
	 *
	 * @param orderPage the order page
	 */
	protected void checkOrderHasTax( NetsuiteOrderWithTax orderPage )
	{
		String actualTax = orderPage.getOrderTaxAmount();
		System.out.println(actualTax);
		assertNotEquals(actualTax, "0.00");
	}

	/**
	 * Checks if the tax amounts equals the actual amounts
	 *
	 * @param orderPage       the order page
	 * @param expectedAmounts the expected amounts
	 */
	protected void checkOrderWithTax( NetsuiteOrderWithTax orderPage, NetsuitePrices expectedAmounts,
		NetsuiteItem... items )
	{
		String actualTax = orderPage.getOrderTaxAmount();
		VertexLogger.log("actualTax = " + actualTax);
		String expectedTax = expectedAmounts.getTaxAmount();
		VertexLogger.log("expectedTax = " + expectedTax);
		assertEquals(expectedTax, actualTax);

		List<String> expectedItemTaxRates = expectedAmounts.getItemTaxRates();
		List<String> expectedItemTaxCodes = expectedAmounts.getItemTaxCodes();

		for ( int i = 0 ; i < expectedItemTaxRates.size() ; i++ )
		{
			String actualItemTaxRate = orderPage.getItemTaxRate(items[i]);
			VertexLogger.log("actualItemTaxRate = " + actualItemTaxRate);
			String expectedItemTaxRate = expectedItemTaxRates.get(i);
			VertexLogger.log("expectedItemTaxRate = " + expectedItemTaxRate);
			assertEquals(expectedItemTaxRate, actualItemTaxRate);
		}

		for ( int i = 0 ; i < expectedItemTaxCodes.size() ; i++ )
		{
			String actualItemTaxCode = orderPage.getItemTaxCode(items[i]);
			VertexLogger.log("actualItemTaxCode = " + actualItemTaxCode);
			String expectedItemTaxCode = expectedItemTaxCodes.get(i);
			System.out.println("expectedItemTaxCode = " + expectedItemTaxCode);
			assertEquals(expectedItemTaxCode, actualItemTaxCode);
		}
	}

	/**
	 * Checks if the shipping amounts equals the actual amounts
	 *
	 * @param orderPage       the order page
	 * @param expectedAmounts the expected amounts
	 */
	protected void checkOrderWithShipping( NetsuiteOrderWithShipping orderPage, NetsuitePrices expectedAmounts )
	{
		String actualShipping = orderPage.getOrderShippingAmount();
		System.out.println("actualShipping = " + actualShipping);
		String actualHandling = orderPage.getOrderHandlingAmount();
		System.out.println("actualHandling = " + actualHandling);
		String expectedShipping = expectedAmounts.getShippingCost();
		System.out.println("expectedShipping = " + expectedShipping);
		String expectedHandling = expectedAmounts.getHandlingCost();
		System.out.println("expectedHandling = " + expectedHandling);
		assertEquals(expectedShipping, actualShipping);
		assertEquals(expectedHandling, actualHandling);
	}

	/**
	 * Setup a basic Invoice
	 *
	 * @param invoicePage the current invoice page
	 * @param customer    the customer to use
	 * @param address     the address to use
	 * @param items       the list of items to add
	 *
	 * @return the order page in case additional changes need to be made
	 */
	protected void setupBasicInvoice( NetsuiteTransactions invoicePage, NetsuiteCustomer customer, NetsuiteAddress address, NetsuiteItem... items )
	{
		invoicePage.selectCustomer(customer);
		invoicePage.setLocation(address.getCity());
		for ( NetsuiteItem item : items )
		{
			invoicePage.addItemToTransaction(item);
		}
	}

	/**
	 * Setup a basic Invoice
	 *
	 * @param invoicePage the current invoice page
	 * @param customer    the customer to use
	 * @param address     the address to use
	 * @param items       the list of items to add
	 *
	 * @return the order page in case additional changes need to be made
	 */
	protected void setupBasicCashSale( NetsuiteTransactions invoicePage, NetsuiteCustomer customer, NetsuiteAddress address, NetsuiteItem... items )
	{
		invoicePage.selectCustomer(customer);
		invoicePage.setLocation(address.getCity());

		for ( NetsuiteItem item : items )
		{
			invoicePage.addItemToTransaction(item);
		}
	}

	/**
	 * Add a Tax Detail to a basic Invoice
	 *
	 * @param invoicePage the current invoice page
	 * @param items       the list of items to add
	 *
	 * @return the order page in case additional changes need to be made
	 */
	protected void addTaxDetailToInvoice( NetsuiteTransactions invoicePage, NetsuiteItem... items )
	{
		for ( NetsuiteItem item : items )
		{
			invoicePage.addTaxDetailToTransaction(item, 0);
		}
	}

	/**
	 * Setup a basic Credit Memo
	 *
	 * @param memoPage the current Memo page
	 * @param customer the customer to use
	 * @param address  the address to use
	 * @param items    the list of items to add
	 *
	 * @return the Memo page in case additional changes need to be made
	 */
	protected void setupBasicCreditMemo( NetsuiteTransactions memoPage, NetsuiteCustomer customer, NetsuiteAddress address,
		NetsuiteItem... items )
	{

		memoPage.selectCustomer(customer);

		int itemIndex = 1;
		for ( NetsuiteItem item : items )
		{
			memoPage.setLocation(item.getMemoLocation());
			memoPage.addItemToTransaction(item, itemIndex);
			itemIndex++;
		}
		if ( address != null )
		{
			if ( !memoPage.selectExistingShipToAddress(address) )
			{
				memoPage.createNewShipToAddress(address);
				assertTrue(memoPage.isAddressVerified());
			}
			assertTrue(memoPage.isCleansedShippingAddressDisplayed(address));
		}
	}

	/**
	 * Sets up a basic customer and saves the customer
	 *
	 * @param customerPage        the current customer page
	 * @param customerName        the customer's name
	 * @param vertexCustomerClass the Vertex customer class
	 * @param vertexCustomerCode  the Vertex customer Id
	 * @param contact             the contact
	 * @param address             the default address for the customer
	 *
	 * @return the saved customer page
	 */
	protected void setupCustomer( NetsuiteCustomerPage customerPage, NetsuiteCustomer customerName,
		String vertexCustomerClass, String vertexCustomerCode, String contact, NetsuiteAddress address )
	{
		customerPage.uncheckAutoNameCheckbox();
		customerPage.enterCustomerId(customerName.getCustomerName());
		customerPage.enterCompanyName(customerName.getCustomerName());
		if ( vertexCustomerClass != null && !vertexCustomerClass.isEmpty() )
		{
			customerPage.createNewVertexCustomerClass(vertexCustomerClass);
		}
		if ( vertexCustomerCode != null && !vertexCustomerCode.isEmpty() )
		{
			customerPage.enterVertexCustomerId(vertexCustomerCode);
		}
		customerPage.selectInformationContactsTab();
		customerPage.enterContactName(contact);
		customerPage.selectAddressTab();
		customerPage.createNewAddress(address);
		assertTrue(customerPage.isAddressVerified());
	}

	/**
	 * @param transactionPage the current document page
	 * @param values          the list of values to check for in the log
	 *
	 * @return the document page after checking the log
	 */
	protected <T extends NetsuiteTransactions> T checkDocumentLogs( NetsuiteTransactions transactionPage,
		String... values )
	{
		transactionPage.refreshPage();
		NetsuiteVertexCallDetailsPage vertexCallDetailsPage = transactionPage.showXMLLog();
		for ( String value : values )
		{
			assertTrue(vertexCallDetailsPage.doesVertexResponseContains(value),
				"Response does not contain [ " + value + " ] or it's value is incorrect.");
		}
		return vertexCallDetailsPage.goBack();
	}

	/**
	 * @param transactionPage the current document page
	 * @param values          the list of values to check for in the log (XML Request)
	 *
	 * @return the document page after checking the log
	 */
	protected <T extends NetsuiteTransactions> T checkDocumentLogsXmlRequest( NetsuiteTransactions transactionPage,
		String... values )
	{
		transactionPage.refreshPage();
		NetsuiteVertexCallDetailsPage vertexCallDetailsPage = transactionPage.showXMLLog();
		for ( String value : values )
		{
			assertTrue(vertexCallDetailsPage.doesVertexRequestContains(value),
				"Response does not contain [ " + value + " ] or it's value is incorrect.");
		}
		return vertexCallDetailsPage.goBack();
	}

	/**
	 * Deletes the transaction document
	 *
	 * @param transactionPage the transaction page
	 *
	 * @return the transactions page
	 */
	protected NetsuiteTransactionsPage deleteDocument( NetsuiteTransactions transactionPage )
	{
		NetsuiteTransactions editedTransactionPage = transactionPage.editOrder();
		return editedTransactionPage.deleteOrder();
	}

	/**
	 * Deletes the customer
	 *
	 * @param customerListPage the customer search page
	 * @param customer         the customer to delete
	 * @param contact          the contact to delete from the customer
	 *
	 * @return the customer list page
	 */
	protected NetsuiteCustomerListPage deleteCustomer( NetsuiteCustomerListPage customerListPage,
		NetsuiteCustomer customer, String contact )
	{
		customerListPage.sortBy(NetsuiteSortCriteria.RECENTLY_MODIFIED);
		NetsuiteCustomerPage foundCustomerPage = customerListPage.edit(customer.getCustomerName());
		NetsuiteContactPopupPage contactPopupPage = foundCustomerPage.editContact(contact);
		contactPopupPage.delete();
		NetsuiteCustomerListPage finishedCustomerListPage = foundCustomerPage.delete();
		return finishedCustomerListPage;
	}

	/**
	 * Deletes the Vertex Customer Class
	 *
	 * @param customListsPage     the custom lists page
	 * @param vertexCustomerClass the Vertex Customer class to delete
	 *
	 * @return the customer class list page
	 */
	protected NetsuiteCustomerClassListPage deleteVertexCustomerClass( NetsuiteCustomListsPage customListsPage,
		String vertexCustomerClass )
	{
		NetsuiteCustomerClassListPage customerClassListPage = customListsPage.list(
			NetsuiteCustomList.VERTEX_CUSTOMER_CLASS);
		customerClassListPage.sortBy(NetsuiteSortCriteria.RECENTLY_MODIFIED);
		NetsuiteCustomerClassPage customerClassPage = customerClassListPage.edit(vertexCustomerClass);
		NetsuiteCustomerClassListPage finishedCustomerClassListPage = customerClassPage.delete();
		return finishedCustomerClassListPage;
	}

	/**
	 * Gets Distributed Tax
	 *
	 * @param invoicePage    the invoice page
	 * @param expectedAmount the tax amount expected
	 *
	 * @return tax amount
	 */
	protected void getDistributedTaxValue( NetsuiteTransactions invoicePage, String expectedAmount )
	{
		String value = invoicePage.getDistributedTax();
		assertEquals(value, expectedAmount);
	}

	/**
	 * Get sales order menu
	 *
	 * @return
	 */
	protected NetsuiteNavigationMenus getSalesOrderMenu( )
	{
		return NetsuiteNavigationMenus
			.builder()
			.menu(NetsuiteNavigationMenuTitles.TRANSACTIONS)
			.menu(NetsuiteNavigationMenuTitles.SALES)
			.menu(NetsuiteNavigationMenuTitles.ENTER_SALES_ORDER)
			.build();
	}

	/**
	 * Get purchase order menu
	 *
	 * @return
	 */
	protected NetsuiteNavigationMenus getPurchaseOrderMenu( )
	{
		return NetsuiteNavigationMenus
			.builder()
			.menu(NetsuiteNavigationMenuTitles.TRANSACTIONS)
			.menu(NetsuiteNavigationMenuTitles.PURCHASES)
			.menu(NetsuiteNavigationMenuTitles.PURCHASE_ORDER)
			.build();
	}

	/**
	 * Get Transaction Search Menu
	 *
	 * @return
	 */
	protected NetsuiteNavigationMenus getTransactionSearchMenu( )
	{
		return NetsuiteNavigationMenus
			.builder()
			.menu(NetsuiteNavigationMenuTitles.TRANSACTIONS)
			.menu(NetsuiteNavigationMenuTitles.SALES)
			.menu(NetsuiteNavigationMenuTitles.ENTER_SALES_ORDER)
			.menu(NetsuiteNavigationMenuTitles.SEARCH)
			.build();
	}

	/**
	 * Get Credit Memo Transaction Search Menu
	 *
	 * @return
	 */
	protected NetsuiteNavigationMenus getCreditMemoTransactionSearchMenu( )
	{
		return NetsuiteNavigationMenus
			.builder()
			.menu(NetsuiteNavigationMenuTitles.TRANSACTIONS)
			.menu(NetsuiteNavigationMenuTitles.CUSTOMERS)
			.menu(NetsuiteNavigationMenuTitles.ISSUE_CREDIT_MEMOS)
			.menu(NetsuiteNavigationMenuTitles.SEARCH)
			.build();
	}

	/**
	 * Get Invoice Menu
	 *
	 * @return
	 */
	protected NetsuiteNavigationMenus getCreateInvoiceMenu( )
	{
		return NetsuiteNavigationMenus
			.builder()
			.menu(NetsuiteNavigationMenuTitles.TRANSACTIONS)
			.menu(NetsuiteNavigationMenuTitles.SALES)
			.menu(NetsuiteNavigationMenuTitles.CREATE_INVOICES)
			.build();
	}

	/**
	 * Get Expense Reports Menu
	 *
	 * @return
	 */
	protected NetsuiteNavigationMenus getExpenseReportsMenu( )
	{
		return NetsuiteNavigationMenus
			.builder()
			.menu(NetsuiteNavigationMenuTitles.TRANSACTIONS)
			.menu(NetsuiteNavigationMenuTitles.EMPLOYEES)
			.menu(NetsuiteNavigationMenuTitles.ENTER_EXPENSE_REPORTS)
			.build();
	}

	/**
	 * Get Quote Menu
	 *
	 * @return
	 */
	protected NetsuiteNavigationMenus getQuoteMenu( )
	{
		return NetsuiteNavigationMenus
			.builder()
			.menu(NetsuiteNavigationMenuTitles.TRANSACTIONS)
			.menu(NetsuiteNavigationMenuTitles.SALES)
			.menu(NetsuiteNavigationMenuTitles.PREPARE_QUOTES)
			.build();
	}

	protected NetsuiteNavigationMenus getQuoteSearchMenu( )
	{
		return NetsuiteNavigationMenus
			.builder()
			.menu(NetsuiteNavigationMenuTitles.TRANSACTIONS)
			.menu(NetsuiteNavigationMenuTitles.SALES)
			.menu(NetsuiteNavigationMenuTitles.PREPARE_QUOTES)
			.menu(NetsuiteNavigationMenuTitles.SEARCH)
			.build();
	}

	protected NetsuiteNavigationMenus getBlanketOrderMenu( )
	{
		return NetsuiteNavigationMenus
			.builder()
			.menu(NetsuiteNavigationMenuTitles.TRANSACTIONS)
			.menu(NetsuiteNavigationMenuTitles.PURCHASES)
			.menu(NetsuiteNavigationMenuTitles.BLANKET_ORDER)
			.build();
	}

	/**
	 * Get Customer Menu
	 *
	 * @return
	 */
	protected NetsuiteNavigationMenus getCustomerMenu( )
	{
		return NetsuiteNavigationMenus
			.builder()
			.menu(NetsuiteNavigationMenuTitles.LISTS)
			.menu(NetsuiteNavigationMenuTitles.RELATIONSHIPS)
			.menu(NetsuiteNavigationMenuTitles.CUSTOMERS)
			.menu(NetsuiteNavigationMenuTitles.NEW)
			.build();
	}

	/**
	 * Get Comapany Information Menu
	 *
	 * @return
	 */
	protected NetsuiteNavigationMenus getCompanyInformationMenu( )
	{
		return NetsuiteNavigationMenus
			.builder()
			.menu(NetsuiteNavigationMenuTitles.SETUP_MENU)
			.menu(NetsuiteNavigationMenuTitles.COMPANY)
			.menu(NetsuiteNavigationMenuTitles.COMPANY_INFORMATION)
			.build();
	}

	/**
	 * Get General Pref Menu
	 *
	 * @return
	 */
	protected NetsuiteNavigationMenus getGeneralPreferencesMenu( )
	{
		return NetsuiteNavigationMenus
			.builder()
			.menu(NetsuiteNavigationMenuTitles.SETUP_MENU)
			.menu(NetsuiteNavigationMenuTitles.COMPANY)
			.menu(NetsuiteNavigationMenuTitles.GENERAL_PREFERENCES)
			.build();
	}

	/**
	 * Get Cash Sale Menu
	 *
	 * @return
	 */
	protected NetsuiteNavigationMenus getCashSaleMenu( )
	{
		return NetsuiteNavigationMenus
			.builder()
			.menu(NetsuiteNavigationMenuTitles.TRANSACTIONS)
			.menu(NetsuiteNavigationMenuTitles.SALES)
			.menu(NetsuiteNavigationMenuTitles.ENTER_CASH_SALES)
			.build();
	}

	/**
	 * Get Credit Memo Menu
	 *
	 * @return
	 */
	protected NetsuiteNavigationMenus getCreditMemoMenu( )
	{
		return NetsuiteNavigationMenus
			.builder()
			.menu(NetsuiteNavigationMenuTitles.TRANSACTIONS)
			.menu(NetsuiteNavigationMenuTitles.CUSTOMERS)
			.menu(NetsuiteNavigationMenuTitles.ISSUE_CREDIT_MEMOS)
			.build();
	}

	/**
	 * Get Custom List Menu
	 *
	 * @return
	 */
	protected NetsuiteNavigationMenus getCustomListsMenu( )
	{
		return NetsuiteNavigationMenus
			.builder()
			.menu(NetsuiteNavigationMenuTitles.CUSTOMIZATION_MENU)
			.menu(NetsuiteNavigationMenuTitles.LIST_RECORDS_FIELDS)
			.menu(NetsuiteNavigationMenuTitles.LISTS)
			.build();
	}

	/**
	 * Get Customer Search Menu
	 *
	 * @return
	 */
	protected NetsuiteNavigationMenus getCustomerSearchMenu( )
	{
		return NetsuiteNavigationMenus
			.builder()
			.menu(NetsuiteNavigationMenuTitles.LISTS)
			.menu(NetsuiteNavigationMenuTitles.RELATIONSHIPS)
			.menu(NetsuiteNavigationMenuTitles.CUSTOMERS)
			.menu(NetsuiteNavigationMenuTitles.SEARCH)
			.build();
	}

	/**
	 * Get Selected Item Type Menu
	 *
	 * @return
	 */
	protected NetsuiteNavigationMenus getSelectItemTypeMenu( )
	{
		return NetsuiteNavigationMenus
			.builder()
			.menu(NetsuiteNavigationMenuTitles.LISTS)
			.menu(NetsuiteNavigationMenuTitles.ACCOUNTING)
			.menu(NetsuiteNavigationMenuTitles.ITEMS)
			.menu(NetsuiteNavigationMenuTitles.NEW)
			.build();
	}

	/**
	 * Get Product Search Menu
	 *
	 * @return
	 */
	protected NetsuiteNavigationMenus getProductSearchMenu( )
	{
		return NetsuiteNavigationMenus
			.builder()
			.menu(NetsuiteNavigationMenuTitles.LISTS)
			.menu(NetsuiteNavigationMenuTitles.ACCOUNTING)
			.menu(NetsuiteNavigationMenuTitles.ITEMS)
			.menu(NetsuiteNavigationMenuTitles.SEARCH)
			.build();
	}

	/**
	 * Get Installed Bundles Menu
	 *
	 * @return
	 */
	protected NetsuiteNavigationMenus getInstalledBundlesMenu( )
	{
		return NetsuiteNavigationMenus
			.builder()
			.menu(NetsuiteNavigationMenuTitles.CUSTOMIZATION_MENU)
			.menu(NetsuiteNavigationMenuTitles.SUITEBUNDLER)
			.menu(NetsuiteNavigationMenuTitles.SEARCH_AND_INSTALL_BUNDLES)
			.menu(NetsuiteNavigationMenuTitles.LIST)
			.build();
	}

	/**
	 * Get Subsioiary Menu
	 *
	 * @return
	 */
	protected NetsuiteNavigationMenus getSubsidiaryMenu( )
	{
		return NetsuiteNavigationMenus
			.builder()
			.menu(NetsuiteNavigationMenuTitles.SETUP_MENU)
			.menu(NetsuiteNavigationMenuTitles.COMPANY)
			.menu(NetsuiteNavigationMenuTitles.SUBSIDIARIES)
			.build();
	}

	/**
	 * Get Sustome List Menu
	 *
	 * @return
	 */
	protected NetsuiteNavigationMenus getCustomerListMenu( )
	{
		return NetsuiteNavigationMenus
			.builder()
			.menu(NetsuiteNavigationMenuTitles.LISTS)
			.menu(NetsuiteNavigationMenuTitles.RELATIONSHIPS)
			.menu(NetsuiteNavigationMenuTitles.CUSTOMERS)
			.build();
	}

	/**
	 * Get CVS Import Menu
	 *
	 * @return
	 */
	protected NetsuiteNavigationMenus getCSVImportMenu( )
	{
		return NetsuiteNavigationMenus
			.builder()
			.menu(NetsuiteNavigationMenuTitles.SETUP_MENU)
			.menu(NetsuiteNavigationMenuTitles.IMPORT_EXPORT)
			.menu(NetsuiteNavigationMenuTitles.IMPORT_CSV_RECORDS)
			.build();
	}

	/**
	 * Get Sales Order List Menu
	 *
	 * @return
	 */
	protected NetsuiteNavigationMenus getSalesOrderListMenu( )
	{
		return NetsuiteNavigationMenus
			.builder()
			.menu(NetsuiteNavigationMenuTitles.TRANSACTIONS)
			.menu(NetsuiteNavigationMenuTitles.SALES)
			.menu(NetsuiteNavigationMenuTitles.ENTER_SALES_ORDER)
			.menu(NetsuiteNavigationMenuTitles.LIST)
			.build();
	}

	protected NetsuiteSCISStorePage signIntoStorePage( )
	{
		driver
			.navigate()
			.to("https://tstdrv2366937.app.netsuite.com/c.tstdrv2366937/pos/main.ssp?whence=&n=4&fakewrapper=T");
		NetsuiteSCISLoginPage loginPage = new NetsuiteSCISLoginPage(driver);
		loginPage.enterEmail(username);
		loginPage.enterPassword(password);
		return loginPage.loginToStorePage();
	}

	/**
	 * Signs into the netsuite homepage as SCIS role
	 *
	 * @return the homepage for SCIS environment
	 */
	protected NetsuiteHomepage signIntoHomepageAsSCIS( )
	{
		List<NetsuiteHomepage> pages = signIntoHomepageAs();
		NetsuiteHomepage homePage = pages.get(0);
		if ( pages.get(1) != null )
			return pages.get(1);

		NetsuiteRolePage rolesPage = homePage.navigationPane.viewRolePage();
		NetsuiteHomepage homepage = rolesPage.signInAsSCIS();
		String pageTitle = driver.getTitle();
		if ( pageTitle.contains("login challenge") )
		{
			//Do 2FA stuff here
			By input = By.xpath("//input[@placeholder='6-digit code']");
			wait.waitForElementDisplayed(input, 2);
			WebElement twoFAInput = driver.findElement(input);
			String totpCode = NetsuiteTotpGenerator
				.getInstance()
				.getTwoFactorCode();
			twoFAInput.sendKeys(totpCode);

			By submitBtn = By.xpath("//div[@aria-label='Submit']");
			Actions actions = new Actions(driver);
			actions.moveToElement(driver.findElement(submitBtn), 0, 0);
			actions
				.click()
				.perform();
		}
		return homePage;
	}

	/**
	 * initializes the ChromeDriver which interacts with the browser
	 *
	 * @author dgorecki,dpatel
	 */
	@Override
	protected void createChromeDriver() {
		HashMap<String, Object> chromePrefs = new HashMap<>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", VertexPage.DOWNLOAD_DIRECTORY_PATH);

		// Add ChromeDriver-specific capabilities through ChromeOptions.
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		options.addArguments("--disable-infobars");
		if(isDriverHeadlessMode) {
			options.addArguments("--headless");
		}
		driver = isDriverServiceProvisioned
				 ? new RemoteWebDriver(getDriverServiceUrl(), options)
				 : new ChromeDriver(options);
	}

	private URL getDriverServiceUrl() {
		try {
			String hostname = "localhost";
			if(isSeleniumHost) {
				hostname = "selenium"; //instead of "localhost" for GitHub Actions
			}
			return new URL("http://" + hostname + ":4444/wd/hub");
		} catch ( MalformedURLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
