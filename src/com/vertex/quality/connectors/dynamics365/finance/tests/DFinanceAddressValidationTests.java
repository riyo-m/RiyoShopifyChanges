package com.vertex.quality.connectors.dynamics365.finance.tests;

import com.vertex.quality.connectors.dynamics365.finance.components.DFinanceTaxValidation;
import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.dynamics365.finance.enums.*;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * @author Saidulu Kodadala
 * Address Validation Tests / Address Cleansing Tests
 */
@Listeners(TestRerunListener.class)
public class DFinanceAddressValidationTests extends DFinanceBaseTest
{
	final String VertexTaxParameters = DFinanceLeftMenuNames.VERTEX_TAX_PARAMETERS.getData();
	final String addressValidation = DFinanceVertexTaxParametersLeftMenuNames.ADDRESS_VALIDATION.getData();

	@BeforeMethod(alwaysRun = true)
	public void setupTest( )
	{
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
		//================script implementation========================
		//Navigate to Vertex Settings page
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(tax);
		homePage.expandModuleCategory(setup);
		homePage.expandModuleCategory(vertex);
		settingsPage = homePage.selectModuleTabLink(VertexTaxParameters);

		//Click on "Address Validation" from the left pane
		settingsPage.selectSettingsPage(addressValidation);

		settingsPage.toggleAddressCleansingButton(true);
		settingsPage.clickOnSaveButton();

	}

	@AfterMethod(alwaysRun = true)
	public void teardownTest( )
	{

	}

	/**
	 * To verify the "Address Cleansing" functionality, when same address is used for different
	 * "Acceptable Confidence Level settings" Also, to verify the messages when user "Cancels" the
	 * Vertex
	 * recommended addresses as part of vertex validation service
	 * CD365-1380 CD365-550
	 */
	@Test(groups = { "FO_General_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void AddressCleansing_AcceptableConfidenceLevelTest( )
	{
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceAllSalesOrdersPage salesOrdersPage;
		DFinanceTaxValidation dFinanceTaxValidation = new DFinanceTaxValidation();
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);

		//================Data Declaration ===========================
		final String tax = DFinanceLeftMenuNames.TAX.getData();
		final String setup = DFinanceLeftMenuNames.SETUP.getData();
		final String vertex = DFinanceLeftMenuNames.VERTEX.getData();
		final String accountReceivable = DFinanceLeftMenuNames.ACCOUNTS_RECEIVABLE.getData();
		final String orders = DFinanceLeftMenuNames.ORDERS.getData();
		final String allSalesOrders = DFinanceLeftMenuNames.ALL_SALES_ORDERS.getData();
		final String vertexAR = DFinanceConstantDataResource.VERTEXAR.getData();
		final String description = DFinanceConstantDataResource.DESCRIPTION.getData();
		final String zipCode = DFinanceConstantDataResource.ZIPCODE.getData();
		final String street = DFinanceConstantDataResource.STREET.getData();
		final String city = DFinanceConstantDataResource.CITY.getData();
		final String state = DFinanceConstantDataResource.STATE.getData();
		final String country = DFinanceConstantDataResource.COUNTRY.getData();

		//Set the Acceptable confidence level and Set Always list all address results
		settingsPage.setConfidenceAndResults("75", true);

		//Navigate to  All Sales Orders page
		salesOrdersPage = homePage.navigateToAllSalesOrdersPage();

		//Create sales order
		homePage.newAddress(defaultCustomerAccount);

		//Validate delivery name
		String deliveryName = salesOrdersPage.getDeliveryName();
		dFinanceTaxValidation.textValidation(deliveryName, "Delivery Name");

		//Validate delivery address
		String deliveryAddress = salesOrdersPage.getDeliveryAddress();
		dFinanceTaxValidation.textValidation(deliveryAddress, "Delivery Address");

		//Go to "Address" section and click on "+" icon available at the "Delivery address" field to enter a new address
		salesOrdersPage.clickPlusIconForAddAddress();

		//Enter "Description"
		salesOrdersPage.setDescription(description);

		//Enter 5-digit ZIP/Postal Code
		salesOrdersPage.setZipCode(zipCode);

		//Enter "Street"
		salesOrdersPage.setStreet(street);

		//Click on "Ok" button
		salesOrdersPage.clickOk();
		salesOrdersPage.clickOnAddValidOk();
		//Click on "OK"  button on "Create Sales Order" screen
		salesOrdersPage.finishCreatingSalesOrder();
		salesOrdersPage.openHeaderTab();
		String salesOrderNumber = salesOrdersPage.getSalesOrderNumber();
		//Get Address for verify zipcode
		String address = salesOrdersPage.getAddress();

		//Check the presence of 9-digit zip code is triggered in the New address
		salesOrdersPage.verifyZipCode(address);

		//Navigate to Vertex Settings page
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(tax);
		settingsPage = homePage.selectModuleTabLink(VertexTaxParameters);

		//Click on "Address Validation" from the left pane
		settingsPage.selectSettingsPage(addressValidation);

		//Set the Acceptable confidence level
		homePage = settingsPage.setConfidenceAndResults("100", true);

		//Navigate to  All Sales Orders page
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(accountReceivable);
		homePage.clickOnAllSalesOrder();

		//Search created sales order
		salesOrdersPage = homePage.searchCreatedSalesOrder(salesOrderNumber);
		salesOrdersPage.clickOnSearchButton();
		salesOrdersPage.clickOnDisplayedSalesOrderNumber();

		//Click on "Header" option
		salesOrdersPage.openHeaderTab();

		//Go to "Address" section and click on "+" icon available at the "Delivery address" field to enter a new address
		salesOrdersPage.clickPlusIconForAddAddress1();

		//Enter "Description"
		salesOrdersPage.setDescription(description);

		//Enter 5-digit ZIP/Postal Code
		salesOrdersPage.setZipCode(zipCode);

		//Enter "Street"
		salesOrdersPage.setStreet(street);

		//Click on "Ok" button
		salesOrdersPage.clickOk();

		//navigate to Vertex Settings page
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(tax);
		settingsPage = homePage.selectModuleTabLink(VertexTaxParameters);

		//Click on "Address Validation" from the left pane
		settingsPage.selectSettingsPage(addressValidation);

		//Set the Acceptable confidence level
		homePage = settingsPage.setConfidenceAndResults("75", false);

		//click on save button
		settingsPage.clickOnSaveButton();

		//Navigate to  All Sales Orders page
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(accountReceivable);
		homePage.clickOnAllSalesOrder();

		//Search created sales order
		salesOrdersPage.searchCreatedSalesOrder(salesOrderNumber);
		salesOrdersPage.clickOnDisplayedSalesOrderNumber();

		//Click on "Delete" option from the menu
		salesOrdersPage.clickDeleteButton();
		//Click on "Yes" option from the pop-up
		salesOrdersPage.clickDeleteYesButton();

	}

	/** @TestCase CD365-265
	 * @Description - Validate administrative origin with no zip returns valid address
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_General_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void validateAdministrativeOriginWithNoZipReturnsValidAddressTest(){
		final String itemNumber = "1000";
		final String quantity = "30";
		final String unitPrice = "2450";
		final String site = "2";
		final String wareHouse = "22";
		final String street = Address.Anaheim.addressLine1;
		final String city = Address.Anaheim.city;
		final String state = Address.Anaheim.state.abbreviation;
		final String customerCountry = Address.Anaheim.country.iso3code;
		final String customerDescription = "No zip";
		final String zipcode = "";
		final String expectedTaxAmount = "5,696.25";

		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
		DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

		settingsPage.selectCompany(usmfCompany);

		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		allSalesOrdersPage.openNewSalesOrder();

		//Add sales line item
		allSalesOrdersPage.setCustomerAccount(defaultCustomerAccount);
		allSalesOrdersPage.clickOkBTN();
		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();
		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, wareHouse, quantity, unitPrice, 0);

		//Click on the "Line Details Tab" and update address
		allSalesOrdersPage.updateAddress();
		allSalesOrdersPage.setDescription(customerDescription);
		allSalesOrdersPage.setCountry(customerCountry);
		allSalesOrdersPage.setStreet(street);
		allSalesOrdersPage.setCity(city);
		allSalesOrdersPage.setState(state);
		allSalesOrdersPage.clickOk();
		allSalesOrdersPage.clickOnAddValidOk();

		//Verify sales tax
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();
		String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(calculatedSalesTaxAmount.contains(expectedTaxAmount),
				assertionTotalCalculatedSalesTaxAmount);
		String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
		assertTrue(actualSalesTaxAmount.contains(expectedTaxAmount),
				assertionTotalActualSalesTaxAmount);
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		//Validate administrative origin and tax
		settingsPage.navigateToDashboardPage();
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);
		xmlInquiryPage.getDocumentID(salesOrderNumber);
		xmlInquiryPage.clickResponse();
		String response = xmlInquiryPage.getLogResponseValue();
		assertTrue(response.contains("<Customer>\n" +
				"\t\t\t<CustomerCode classCode=\"10\" isBusinessIndicator=\"true\">004021</CustomerCode>\n" +
				"\t\t\t<Destination locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t<StreetAddress1>395 College St W</StreetAddress1>\n" +
				"\t\t\t\t<City>Abbeville</City>\n" +
				"\t\t\t\t<MainDivision>GA</MainDivision>\n" +
				"\t\t\t\t<PostalCode>31001-4231</PostalCode>\n" +
				"\t\t\t\t<Country>USA</Country>\n" +
				"\t\t\t\t<CurrencyConversion isoCurrencyName=\"US Dollar\" isoCurrencyCodeAlpha=\"USD\" isoCurrencyCodeNum=\"840\">1.0</CurrencyConversion>\n" +
				"\t\t\t</Destination>"
		));
		assertTrue(response.contains("<TotalTax>5696.25</TotalTax>"));
	}

	/** @TestCase CD365-263
	 * @Description - Validate destination shipping with no zip returns valid address
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_General_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void validateDestinationShippingWithNoZipReturnsValidAddressTest(){
		final String itemNumber = "1000";
		final String quantity = "30";
		final String unitPrice = "2450";
		final String site = "2";
		final String wareHouse = "22";
		final String street = Address.Birmingham.addressLine1;
		final String city = Address.Birmingham.city;
		final String state = Address.Birmingham.state.abbreviation;
		final String customerCountry = Address.Birmingham.country.iso3code;
		final String zipcode = "";
		final String customerDescription = "No zip";
		final String expectedTaxAmount = "5,880.00";

		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
		DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

		settingsPage.selectCompany(usmfCompany);

		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		allSalesOrdersPage.openNewSalesOrder();

		//All sales line item
		allSalesOrdersPage.setCustomerAccount(defaultCustomerAccount);
		allSalesOrdersPage.clickOkBTN();
		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();
		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, wareHouse, quantity, unitPrice, 0);

		//Click on the "Line Details Tab" and update address
		allSalesOrdersPage.updateAddress();
		allSalesOrdersPage.setDescription(customerDescription);
		allSalesOrdersPage.setCountry(customerCountry);
		//allSalesOrdersPage.setZipCode(zipcode);
		allSalesOrdersPage.setStreet(street);
		allSalesOrdersPage.setCity(city);
		allSalesOrdersPage.setState(state);
		allSalesOrdersPage.clickOk();
		allSalesOrdersPage.clickOnAddValidOk();

		//Verify sales tax
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();
		String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(calculatedSalesTaxAmount.contains(expectedTaxAmount),
				assertionTotalCalculatedSalesTaxAmount);
		String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
		assertTrue(actualSalesTaxAmount.contains(expectedTaxAmount),
				assertionTotalActualSalesTaxAmount);
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		//Post the invoice
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.generateInvoice();
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Validate destination address and tax
		settingsPage.navigateToDashboardPage();
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);
		xmlInquiryPage.getDocumentID(salesOrderNumber);
		xmlInquiryPage.clickResponse();
		String response = xmlInquiryPage.getLogResponseValue();
		assertTrue(response.contains("<Customer>\n" +
				"\t\t\t<CustomerCode classCode=\"10\" isBusinessIndicator=\"true\">004021</CustomerCode>\n" +
				"\t\t\t<Destination locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t<StreetAddress1>395 College St W</StreetAddress1>\n" +
				"\t\t\t\t<City>Abbeville</City>\n" +
				"\t\t\t\t<MainDivision>GA</MainDivision>\n" +
				"\t\t\t\t<PostalCode>31001-4231</PostalCode>\n" +
				"\t\t\t\t<Country>USA</Country>\n" +
				"\t\t\t\t<CurrencyConversion isoCurrencyName=\"US Dollar\" isoCurrencyCodeAlpha=\"USD\" isoCurrencyCodeNum=\"840\">1.0</CurrencyConversion>\n" +
				"\t\t\t</Destination>\n" +
				"\t\t\t<AdministrativeDestination taxAreaId=\"10730057\" locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t<StreetAddress1>2473 Hackworth Rd Apt 2</StreetAddress1>\n" +
				"\t\t\t\t<City>Birmingham</City>\n" +
				"\t\t\t\t<MainDivision>AL</MainDivision>\n" +
				"\t\t\t\t<PostalCode>35214-1909</PostalCode>\n" +
				"\t\t\t\t<Country>USA</Country>\n" +
				"\t\t\t\t<CurrencyConversion isoCurrencyName=\"US Dollar\" isoCurrencyCodeAlpha=\"USD\" isoCurrencyCodeNum=\"840\">1.0</CurrencyConversion>\n" +
				"\t\t\t</AdministrativeDestination>\n" +
				"\t\t</Customer>"
		));
		assertTrue(response.contains("<TotalTax>5880.0</TotalTax>"));
	}
}

