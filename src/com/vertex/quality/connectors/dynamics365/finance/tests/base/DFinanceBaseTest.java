package com.vertex.quality.connectors.dynamics365.finance.tests.base;

import com.vertex.quality.common.enums.DBConnectorNames;
import com.vertex.quality.common.enums.DBEnvironmentDescriptors;
import com.vertex.quality.common.enums.DBEnvironmentNames;
import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pojos.EnvironmentCredentials;
import com.vertex.quality.common.pojos.EnvironmentInformation;
import com.vertex.quality.common.pojos.OSeriesConfiguration;
import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.common.utils.SQLConnection;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.properties.CommonDataProperties;
import com.vertex.quality.common.utils.properties.ReadProperties;
import com.vertex.quality.connectors.dynamics365.finance.enums.*;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import org.openqa.selenium.Dimension;
import org.testng.annotations.Test;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * collects setup actions, constants, utility functions, etc which are shared by the test classes for D365 Finance
 *
 * @author ssalisbury
 */
@Test(groups = "d365")
public abstract class DFinanceBaseTest extends VertexUIBaseTest<DFinanceHomePage>
{
	protected final DBConnectorNames connectorName = DBConnectorNames.D365_FINANCE_AND_OPERATIONS;
	protected final DBEnvironmentNames environmentName = DBEnvironmentNames.QA;

	protected final DBEnvironmentDescriptors dfinancePrimaryEnvironmentDescriptor
		= DBEnvironmentDescriptors.D365_FINANCE_AND_OPERATIONS;
	private final String CONFIG_DETAILS_FILE_PATH = CommonDataProperties.CONFIG_DETAILS_FILE_PATH;

	protected final String defaultCustomerAccount = "004021";
	protected final String defaultSalesTaxGroup = "VertexAR";
	protected final String defaultDiscountCode = "DISC20OFF";
	protected final String defaultDiscountCode1 = "";
	protected final String DelawareDeliveryCustomerAccount = "DelawareAccount";
	protected final String PittsburghCustomerAccount = "D_US_PA1";
	protected final String DelawareBillingCustomerAccount = "BIll_Ship";
	protected final String AlaskaDeliveryCustomerAccount = "AK Customer";

	public String DFINANCE_USERNAME = null;
	public String DFINANCE_PASSWORD = null;
	public String DFINANCE_URL = null;

	public String DFINANCE_DEFAULT_TRUSTED_ID = null;
	public String DFINANCE_PO_TRUSTED_ID = null;
	public String DFINANCE_TAX_CALCULATION_URL = null;
	public String DFINANCE_ADDRESS_VALIDATION_URL = null;

	public String salesTaxGroupVertexAP = DFinanceConstantDataResource.VERTEXAP.getData();
	public String allItemSalesTaxGroup = DFinanceConstantDataResource.ALL.getData();
	public String vtxpClassItemSalesTaxGroup = DFinanceConstantDataResource.VTXPCLASS.getData();
	public String usrtCompany = DFinanceConstantDataResource.US_RETAIL_COMPANY_USRT.getData();
	public String usmfCompany = DFinanceConstantDataResource.US_COMPANY_USMF.getData();
	public String germanCompanyDEMF = DFinanceConstantDataResource.GERMAN_COMPANY_DEMF.getData();
	public String britishCompanyGSBI = DFinanceConstantDataResource.BRITISH_COMPANY_GBSI.getData();
	public String germanyVendor = DFinanceConstantDataResource.GERMANY_VENDOR_ACCOUNT_1.getData();
	public String britishVendor = DFinanceConstantDataResource.BRITISH_VENDOR_ACCOUNT_1.getData();
	public String franceVendor = DFinanceConstantDataResource.FRANCE_VENDOR_ACCOUNT_1.getData();
	public String purchaseOrderRequest = DFinanceConstantDataResource.PURCHASE_ORDER_REQUEST_TYPE.getData();
	public String accrualRequest = DFinanceConstantDataResource.ACCRUAL_REQUEST_TYPE.getData();
	public String vertexAdvancedTaxGroup = DFinanceConstantDataResource.VERTEXADVA.getData();
	public String vertexPartialTaxGroup = DFinanceConstantDataResource.VTXPARTIAL.getData();
	public String vertexNonRecoverable = DFinanceConstantDataResource.VTXNORECOV.getData();
	public String vatCodeSalesTaxGroup = DFinanceConstantDataResource.VAT_CODE_SALES_TAX_CODE.getData();
	public String vtxvCodeSalesTaxGroup = DFinanceConstantDataResource.VTXVCODE.getData();
	public String useTaxReceivableAccountName = DFinanceConstantDataResource.USE_TAX_RECEIVABLE.getData();
	public String vatTaxPayableAccountName = DFinanceConstantDataResource.VAT_TAX_PAYABLE.getData();
	public String otherReceivablesAccountName = DFinanceConstantDataResource.OTHER_RECEIVABLES.getData();
	public String vsdoSalesTaxCode = DFinanceConstantDataResource.V_SDO_19_G_SALES_TAX_CODE.getData();
	public String vrcgSalesTaxCode = DFinanceConstantDataResource.V_RC_G.getData();
	public String assertionTotalCalculatedSalesTaxAmount = DFinanceConstantDataResource.ASSERTION_TOTAL_CALCULATED_TAX_AMOUNT_IS_NOT_EXPECTED.getData();
	public String assertionTotalActualSalesTaxAmount = DFinanceConstantDataResource.ASSERTION_TOTAL_ACTUAL_TAX_AMOUNT_IS_NOT_EXPECTED.getData();
	public String assertionTotalAccrualSalesTaxAmount = DFinanceConstantDataResource.ASSERTION_TOTAL_ACCRUAL_TAX_IS_NOT_EXPECTED.getData();
	public String purchaseOrderNumber = DFinanceConstantDataResource.PURCHASE_ORDER_NUMBER.getData();
	public String purchaseOrderConfirmationFailed = DFinanceConstantDataResource.PO_CONFIRMATION_FAILED.getData();
	public String productReceiptNumberString = DFinanceConstantDataResource.PRODUCT_RECEIPT_NUMBER.getData();
	public String matchStatusString = DFinanceConstantDataResource.MATCH_STATUS.getData();
	public String invoicePostingFailed = DFinanceConstantDataResource.INVOICE_POSTING_FAILED.getData();
	public String invoiceNumberNotFound = DFinanceConstantDataResource.INVOICE_NUMBER_NOT_FOUND.getData();
	public String expectedInvoiceNumber = DFinanceConstantDataResource.EXPECTED_INVOICE_NUMBER.getData();
	public String documentTypeNotExpected = DFinanceConstantDataResource.DOCUMENT_TYPE_IS_NOT_EXPECTED.getData();
	public String requestTypeNotExpected = DFinanceConstantDataResource.REQUEST_TYPE_IS_NOT_EXPECTED.getData();
	public String D0001 = DFinanceConstantDataResource.ITEM1_D0001.getData();
	public String D0003 = DFinanceConstantDataResource.ITEM2_D0003.getData();
	public String item1000 = DFinanceConstantDataResource.ITEM3_1000.getData();
	public String tax = DFinanceLeftMenuNames.TAX.getData();
	public String setup = DFinanceLeftMenuNames.SETUP.getData();
	public String vertex = DFinanceLeftMenuNames.VERTEX.getData();
	public String taxParametersData = DFinanceLeftMenuNames.VERTEX_TAX_PARAMETERS.getData();
	final String taxGroupSettings = DFinanceVertexTaxParametersLeftMenuNames.TAX_GROUP_SETTINGS.getData();
	public String vendorAccount1001 = DFinanceConstantDataResource.VENDOR_ACCOUNT_NO.getData();
	public String accountsPayables = DFinanceLeftMenuNames.ACCOUNTS_PAYABLES.getData();
	public String accountReceivable = DFinanceLeftMenuNames.ACCOUNTS_RECEIVABLE.getData();
	public String customers = DFinanceLeftMenuNames.CUSTOMERS.getData();
	public String allCustomers = DFinanceLeftMenuNames.ALL_CUSTOMERS.getData();
	public String vendors = DFinanceLeftMenuNames.VENDORS.getData();
	public String allVendors = DFinanceLeftMenuNames.ALL_VENDORS.getData();
	public String operationCompleted = DFinanceConstantDataResource.OPERATION_COMPLETED_MESSAGE.getData();
	public String commodityCodeTruncated = DFinanceConstantDataResource.COMMODITY_CODE_TRUNCATED.getData();

	protected final int defaultTableRow = 1;

	@Override
	public DFinanceHomePage loadInitialTestPage( )
	{
		initSetup();

		DFinanceHomePage homePage = logInAsAdminUser();
		driver.manage().window().setSize(new Dimension(1024,768));
		return homePage;
	}

	/**
	 * Get the data from resources folder -> property file
	 */
	protected void initSetup( )
	{
		try
		{
			String oldEnv = "https://vtxauto10p335fa3f037d2a974b7aos.cloudax.dynamics.com";
			String cloudEnv = "https://vtxcloud34a96596021f89d6devaos.axcloud.dynamics.com";
			String newEnv = "https://vtxnewauto10p40326431b415193b50aos.cloudax.dynamics.com";
			String nextEnv = "https://vtx-nextgen5f481fbd71acc5eddevaos.axcloud.dynamics.com";
			String featEnv = "https://vtx-feature7c7832bed25699aadevaos.axcloud.dynamics.com/";
			String hempEnv = "https://vtx-qa-hempelfccd41715f7b76bddevaos.axcloud.dynamics.com";
			String matFirEnv = "https://vtx-qa-mtrsfd778d118d5f02078devaos.axcloud.dynamics.com";
			String consultEnv = "https://vtxconsult10p45e53b3dc968032701aos.axcloud.dynamics.com";
			//String envToRun = "old";
			String envToRun = System.getProperty("url");
			if(envToRun != null)
			{
				if(envToRun.startsWith("https"))
					DFINANCE_URL=envToRun;
				else if (envToRun.contains("new"))
					DFINANCE_URL=newEnv;
				else if(envToRun.contains("next"))
					DFINANCE_URL=nextEnv;
				else if(envToRun.contains("feature"))
					DFINANCE_URL=featEnv;
				else if(envToRun.contains("hemp"))
					DFINANCE_URL=hempEnv;
				else if(envToRun.contains("matFir"))
					DFINANCE_URL=matFirEnv;
				else if(envToRun.contains("cloud"))
					DFINANCE_URL=cloudEnv;
				else
					DFINANCE_URL=oldEnv;
			}
			else
			{
				DFINANCE_URL=oldEnv;
			}
			ReadProperties readConfigDetails = new ReadProperties(CONFIG_DETAILS_FILE_PATH);

			DFINANCE_USERNAME = readConfigDetails.getProperty("TEST.VERTEX.D365.USERNAME");
			DFINANCE_PASSWORD = readConfigDetails.getProperty("TEST.VERTEX.D365.PASSWORD");

			String oSeriesConfigEnv = System.getProperty("oseries");
			if(oSeriesConfigEnv == null)
				oSeriesConfigEnv = "vod";

			DFINANCE_DEFAULT_TRUSTED_ID = readConfigDetails.getProperty("TEST.VERTEX.D365.VOD.TRUSTED_ID");
			DFINANCE_TAX_CALCULATION_URL = readConfigDetails.getProperty("TEST.VERTEX.D365.VOD.TAX_CALCULATION_ENDPOINT_URL");
			DFINANCE_ADDRESS_VALIDATION_URL = readConfigDetails.getProperty("TEST.VERTEX.D365.VOD.TAX_AREA_LOOKUP_ENDPOINT_URL");

			if((envToRun != null && (envToRun.contains("cloud") || envToRun.contains("matFir"))))
			{
				if(oSeriesConfigEnv.contains("classicCloud"))
				{
					VertexLogger.log("Changing to utilize Oseries Classic Cloud URL");
					DFINANCE_DEFAULT_TRUSTED_ID = "1028430868245546";
					DFINANCE_TAX_CALCULATION_URL = "https://calcconnect.vertexsmb.com/vertex-ws/services/CalculateTax70";
					DFINANCE_ADDRESS_VALIDATION_URL = "https://calcconnect.vertexsmb.com/vertex-ws/services/LookupTaxAreas70";
				}
				else
				{
					VertexLogger.log("Changing to utilize new Oseries Cloud URL");
					DFINANCE_DEFAULT_TRUSTED_ID = "2362603504577460";
					DFINANCE_TAX_CALCULATION_URL = "https://calcqaconnect.vertexsmb.com/vertex-ws/services/CalculateTax70";
					DFINANCE_ADDRESS_VALIDATION_URL = "https://calcqaconnect.vertexsmb.com/vertex-ws/services/LookupTaxAreas70";
				}
			}
			else if(oSeriesConfigEnv.contains("classicCloud"))
			{
				VertexLogger.log("Changing to utilize Oseries Classic Cloud URL");
				DFINANCE_DEFAULT_TRUSTED_ID = "1028430868245546";
				DFINANCE_TAX_CALCULATION_URL = "https://calcconnect.vertexsmb.com/vertex-ws/services/CalculateTax70";
				DFINANCE_ADDRESS_VALIDATION_URL = "https://calcconnect.vertexsmb.com/vertex-ws/services/LookupTaxAreas70";
			}
			else if(oSeriesConfigEnv.contains("cloud"))
			{
				VertexLogger.log("Changing to utilize new Oseries Cloud URL");
				DFINANCE_DEFAULT_TRUSTED_ID = "2362603504577460";
				DFINANCE_TAX_CALCULATION_URL = "https://calcqaconnect.vertexsmb.com/vertex-ws/services/CalculateTax70";
				DFINANCE_ADDRESS_VALIDATION_URL = "https://calcqaconnect.vertexsmb.com/vertex-ws/services/LookupTaxAreas70";
			}
			else if ( new File(CONFIG_DETAILS_FILE_PATH).exists() )
			{
				DFINANCE_PO_TRUSTED_ID = readConfigDetails.getProperty("TEST.VERTEX.D365.PO.TRUSTED_ID");
			}
			else
			{
				VertexLogger.log("Configuration details properties file is not found", VertexLogLevel.ERROR);
			}
		}
		catch ( Exception e )
		{
			VertexLogger.log("Failure initializing test data");
			e.printStackTrace();
		}
	}

	/**
	 * Launch Dynamics365 F&O login page
	 */
	protected DFinanceLoginPage launchLoginPage( )
	{
		DFinanceLoginPage loginPage = null;

		VertexLogger.log(String.format("Launching Dynamics365 Finance & Operations URL - %s", DFINANCE_URL),
			VertexLogLevel.TRACE);
		driver.get(DFINANCE_URL);

		loginPage = new DFinanceLoginPage(driver);
		return loginPage;
	}

	/**
	 * Common method for enter valid user name and password ,login into Dynamics365 Finance &
	 * Operations application.
	 *
	 * @return the home page of the d365 finance website after signing in
	 */
	protected DFinanceHomePage logInAsAdminUser( )
	{
		DFinanceHomePage homePage = null;

		DFinanceLoginPage loginPage = this.launchLoginPage();
		homePage = this.logInAsUser(loginPage, DFINANCE_USERNAME, DFINANCE_PASSWORD);
		VertexLogger.log("Signed in to D365 F&O", VertexLogLevel.TRACE);
		return homePage;
	}

	/**
	 * Enter user name , password and click on 'login' button
	 *
	 * @param loginPage the page where the test signs into the site
	 * @param username  the test account's username
	 * @param password  the test account's password
	 *
	 * @return the home page of the d365 finance site after signing in
	 */
	protected DFinanceHomePage logInAsUser( final DFinanceLoginPage loginPage, final String username,
		final String password )
	{
		DFinanceHomePage homePage = null;

		loginPage.setUsername(username);
		loginPage.clickNextButton();
		loginPage.setPassword(password);
		loginPage.clickLoginButton();
		homePage = loginPage.clickYesButton();

		return homePage;
	}

	/**
	 * sets up the standard configuration of the connector's connection to Vertex O Series
	 * and then tests whether the connection works with that configuration
	 * Note- this doesn't quite follow POM. It navigates back to the page which it started at, but then it leaves the
	 * test case to continue using the HomePage object which represented the home page which was loaded before this
	 * function ran
	 *
	 * @param homePage             the page on D365 Finance which the test is starting from before configures the
	 *                             connection to Vertex servers
	 * @param trustedID            the authentication to use when connecting to Vertex connector servers
	 * @param taxCalculationURL    the connector server address which handles tax calculation requests
	 * @param addressValidationURL the connector server address which handles address validation requests
	 *
	 * @return the concatenation of all log messages produced by the attempted connections
	 */
	protected String configureVertexConnection( final DFinanceHomePage homePage, final String trustedID,
		final String taxCalculationURL, final String addressValidationURL )
	{
		// Navigate to Vertex Settings page
		DFinanceSettingsPage settingsPage = homePage.navigateToPage(DFinanceLeftMenuModule.TAX,
			DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
			DFinanceModulePanelLink.VERTEX_TAX_PARAMETERS);

		// set Vertex Tax Parameter Details
		settingsPage.setTrustedId(trustedID);
		settingsPage.setTrustedTaxURL(taxCalculationURL);
		settingsPage.setTrustedAddressURL(addressValidationURL);

		// Test connection
		settingsPage.clickOnTestConnection();

		// check connection status
		final boolean linkStatus = settingsPage.clickOnMessageDetailsLink();

		String validationMessage = null;
		if ( linkStatus )
		{
			validationMessage = settingsPage.getValidationMessage();
		}

		DFinanceHomePage postConfigurationHomePage = settingsPage.navigateToDashboardPage();

		return validationMessage;
	}

	/**
	 * adds a new sales order to the system while only populating the customer field of that sales order
	 *
	 * @param salesOrdersPage the page from which a new sales order can be created
	 * @param customer        the customer that the new sales order should be for
	 */
	protected void createSalesOrder( final DFinanceAllSalesOrdersPage salesOrdersPage, final String customer )
	{
		//Click on "New" option
		salesOrdersPage.openNewSalesOrder();

		//Enter "Customer account" -- 'US-004'
		salesOrdersPage.salesOrderCreator.expandCustomerSection();
		salesOrdersPage.salesOrderCreator.setCustomerAccount(customer);

		//Click on "OK"  button on "Create Sales Order" screen
		salesOrdersPage.salesOrderCreator.finishCreatingSalesOrder();
	}

	/**
	 * Get the wanted date
	 * @param numDays
	 * @return the date desired
	 */
	public String getDesiredDate(int numDays)
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime dateDesired = LocalDateTime.now().plusDays(numDays);
		return dtf.format(dateDesired);
	}

	/**
	 * Get the current date in slash format month, day and year
	 * @return output
	 */
	public static String getCurrentDateSlashFormat(){
		LocalDate currentDate = LocalDate.now();
		String value = String.valueOf(currentDate);
		String[] newDate = value.split("-");
		SimpleDateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
		String output = "";

		try {
			output = dateFormat.format(dateFormat.parse(newDate[1] + "/" + newDate[2] + "/" + newDate[0]));
		}catch (Exception e){
			e.printStackTrace();
		}

		return output;
	}

	/**
	 * Takes a date and sets it as year, month, and day
	 * @params date
	 * @params outputDateFormat
	 * @return output
	 */
	public String setDateFormat(String date, String outputDateFormat){
		String[] newDate = date.split("/");

		String output = newDate[2] + outputDateFormat + newDate[0] + outputDateFormat + newDate[1];

		return output;

	}


	/**
	 * Get Future's date
	 */
	public String getFutureDate(int daysToAdd)
	{
		return LocalDate.now().plusDays(daysToAdd).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

	/**
	 * Verify the appropriate number of Request/Responses
	 * to be returned based on document type id
	 * @param documentNo
	 * @param expectedNumber
	 */
	public boolean verifyNumberOfResponsesAndRequests(String documentNo, int expectedNumber){
		boolean isVerified = false;

		DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
		xmlInquiryPage.getDocumentID(documentNo);

		int actualNumber = xmlInquiryPage.getNumberOfInquiries();

		if(expectedNumber == actualNumber) {
			isVerified = true;
		}

		return isVerified;
	}

	/**
	 * Enable Advance Tax group
	 */
	protected void toggleONOFFAdvanceTaxGroup(Boolean isON)
	{
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage ;
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(tax);
		homePage.collapseAll();
		homePage.expandModuleCategory(setup);
		homePage.expandModuleCategory(vertex);
		settingsPage = homePage.selectModuleTabLink(taxParametersData);
		settingsPage.selectSettingsPage(taxGroupSettings);
		settingsPage.toggleAdvancedTaxGroup(isON);
	}
}
