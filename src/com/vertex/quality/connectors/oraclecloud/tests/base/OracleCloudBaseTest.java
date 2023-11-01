package com.vertex.quality.connectors.oraclecloud.tests.base;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.oraclecloud.components.OracleCloudNavigationPanel;
import com.vertex.quality.connectors.oraclecloud.enums.*;
import com.vertex.quality.connectors.oraclecloud.pages.*;
import com.vertex.quality.connectors.oraclecloud.pages.base.OracleCloudBasePage;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Stores constants and utility functions used by most/all tests
 *
 * @author cgajes
 */
@Test(groups = { "oracle_erp_cloud" })
public abstract class OracleCloudBaseTest extends VertexUIBaseTest<OracleCloudSignOnPage>
{
	protected String username;
	protected String password;
	protected String environmentURL;
	public Path rootPath = Paths
		.get("")
		.toAbsolutePath();
	String textFilePath = File.separator + "resources" + File.separator + "textfiles" + File.separator + "taxlink";
	Path filePath = Paths.get(this.rootPath.toString() + textFilePath);
	private boolean isDriverServiceProvisioned;
	private boolean isDriverHeadlessMode;
	private boolean isSeleniumHost;
	/**
	 * General
	 * Gets credentials for the connector from the database
	 */
	@Override
	public OracleCloudSignOnPage loadInitialTestPage( )
	{
		try
		{
			username = "mcurry";
			password = "W3lc0m3!";
			//environmentURL = "https://ecog-test.fa.us2.oraclecloud.com";
			environmentURL = "https://ecog-dev1.fa.us2.oraclecloud.com";
			//environmentURL = "https://ehyg-test.fa.us6.oraclecloud.com";
			//environmentURL = "https://ecog-dev3.fa.us2.oraclecloud.com";
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}

		OracleCloudSignOnPage signOnPage = loadApplication();
		return signOnPage;
	}

	/**
	 * General
	 * Loads ehyg-test environment for OM Single Transactions
	 */

	public OracleCloudSignOnPage loadInitialTestPage_ecogdev1( )
	{
		try
		{
			username = "mcurry";
			password = "W3lc0m3!";
			environmentURL = "https://ecog-dev1.fa.us2.oraclecloud.com";
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}

		OracleCloudSignOnPage signOnPage = loadApplication();
		return signOnPage;
	}

	/**
	 * Loads ecog-dev3 env for tests TaxLink tests
	 *
	 * @return
	 */
	public OracleCloudSignOnPage loadInitialTestPage_ecogdev3( )
	{
		try
		{
			username = "mcurry";
			password = "W3lc0m3";
			environmentURL = "https://ecog-dev3.fa.us2.oraclecloud.com";
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}

		OracleCloudSignOnPage signOnPage = loadApplication();
		return signOnPage;
	}

	/**
	 * Loads ecog-dev3 UCM env for TaxLink tests
	 *
	 * @return
	 */
	public OracleCloudSignOnPage loadInitialUCMPage( )
	{
		try
		{
			username = "mcurry";
			password = "W3lc0m3";
			environmentURL = "https://ecog-dev3.fa.us2.oraclecloud.com/cs";
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}

		OracleCloudSignOnPage signOnPage = loadApplication();
		return signOnPage;
	}


	/**
	 * General
	 * loads and signs into the UCM Server
	 *
	 * @return a representation of the page that loads immediately after
	 * successfully signing in
	 */
	protected OracleCloudHomePage signIntoUCMServer( ) throws InterruptedException
	{
		OracleCloudSignOnPage signOnPage = new OracleCloudSignOnPage(driver);
		signOnPage.pressLoginButtonUCM();

		username = "mcurry";
		password = "W3lc0m3";
		signOnPage.enterUsername(username);
		signOnPage.enterPassword(password);

		OracleCloudHomePage homepage = signOnPage.pressLoginButton();

		return homepage;
	}

	/**
	 * General
	 * loads the login page of the application
	 *
	 * @return a representation of this site's login screen
	 */
	protected OracleCloudSignOnPage loadApplication( )
	{
		OracleCloudSignOnPage signOnPage;

		driver.get(this.environmentURL);
		driver
			.manage()
			.window()
			.maximize();
		VertexLogger.log(String.format("Application URL - %s", this.environmentURL), VertexLogLevel.DEBUG, getClass());

		signOnPage = new OracleCloudSignOnPage(driver);
		return signOnPage;
	}

	/**
	 * General
	 * loads and signs into the site
	 *
	 * @return a representation of the page that loads immediately after
	 * successfully signing in
	 */
	protected OracleCloudHomePage signInToHomePage( )
	{
		OracleCloudSignOnPage signOnPage = new OracleCloudSignOnPage(driver);

		signOnPage.enterUsername(username);
		signOnPage.enterPassword(password);

		OracleCloudHomePage homepage = signOnPage.pressLoginButton();

		return homepage;
	}

	/**
	 * General
	 * loads and signs into the Supplier Portal
	 *
	 * @return a representation of the page that loads immediately after
	 * successfully signing in
	 */
	protected OracleCloudHomePage signInToSupplierPortal( )
	{
		OracleCloudSignOnPage signOnPage = new OracleCloudSignOnPage(driver);
		username = "michelle.curry@vertexinc.com";
		password = "W3lc0m3!";
		signOnPage.enterUsername(username);
		signOnPage.enterPassword(password);

		OracleCloudHomePage homepage = signOnPage.pressLoginButton();

		return homepage;
	}

	/**
	 * General
	 * signs off from the site
	 *
	 * @return a representation of the page that loads immediately after
	 * successfully signing off
	 */
	protected void signOffPage( )
	{
		OracleCloudSignOnPage signOnPage = new OracleCloudSignOnPage(driver);
		signOnPage.logOut();
	}

	/**
	 * General
	 * signs off from the Supplier Portal site
	 *
	 * @return a representation of the page that loads immediately after
	 * successfully signing off
	 */
	protected void signOffPageSupplierPortal( )
	{
		OracleCloudSignOnPage signOnPage = new OracleCloudSignOnPage(driver);
		signOnPage.logOutSupplierPortal();
	}

	/**
	 * General
	 * Clicks on the navigation panel button to open it
	 * If it was already open (and is now closed), click on it again
	 *
	 * @return navigation panel's web element
	 */
	protected WebElement openNavPanel( )
	{
		OracleCloudNavPage page = new OracleCloudNavPage(driver);
		OracleCloudNavigationPanel nav = page.initializePageObject(OracleCloudNavigationPanel.class, page);

		WebElement navPanel = nav.clickNavigationButton();

		if ( navPanel == null )
		{
			navPanel = nav.clickNavigationButton();
		}

		return navPanel;
	}

	/**
	 * General
	 * Clicks a link on the navigation panel to navigate to a specific page
	 *
	 * @param goToPage enum of the page desired
	 * @param <T>      class type of the page desired
	 *
	 * @return the page navigated to
	 */
	protected <T extends OracleCloudBasePage> T navigateToPage( OracleCloudPageNavigationData goToPage )
	{
		OracleCloudNavPage page = new OracleCloudNavPage(driver);
		OracleCloudNavigationPanel nav = page.initializePageObject(OracleCloudNavigationPanel.class, page);

		T newPage = nav.clickPanelLink(goToPage);

		return newPage;
	}

	/**
	 * General
	 * Ensures the input in the value attribute matches the expected input
	 *
	 * @param field         field to check
	 * @param expectedInput the expected input for the field
	 *
	 * @return whether the Strings match
	 */
	protected boolean checkInput( WebElement field, String expectedInput )
	{
		boolean correct = false;

		String actualInput = field.getAttribute("value");

		if ( expectedInput.equals(actualInput) )
		{
			correct = true;
		}

		return correct;
	}

	/**
	 * AR Tests
	 * Navigates to the create transaction page from the homepage,
	 * after signing in
	 *
	 * @return the Create Transaction page
	 */
	protected OracleCloudCreateTransactionPage navigateToCreateTransactionPage( )
	{
		openNavPanel();
		OracleCloudReceivablesBillingPage billingPage = navigateToPage(
			OracleCloudPageNavigationData.RECEIVABLES_BILLING_PAGE);

		WebElement menu = billingPage.openTasksTab();
		OracleCloudCreateTransactionPage createTransactionPage;
		createTransactionPage = billingPage.clickMenuLink(OracleCloudPageNavigationData.CREATE_TRANSACTION_PAGE, menu);

		return createTransactionPage;
	}

	/**
	 * Method to create a text file, if doesn't exists already
	 * and write data into it
	 *
	 * @param text
	 */

	protected void writeToFile( String text ) throws IOException
	{

		Path rootPath = Paths
			.get("")
			.toAbsolutePath();

		String textFilePath = File.separator + "resources" + File.separator + "textfiles" + File.separator + "taxlink";

		Path filePath = Paths.get(rootPath.toString() + textFilePath);
		Path fileCreatePath = filePath.resolve("taxlink_OracleERP.txt");
		VertexLogger.log("File created in path: " + fileCreatePath);

		try
		{
			if ( !Files.exists(fileCreatePath, LinkOption.NOFOLLOW_LINKS) )
			{
				Files.createFile(fileCreatePath);
				Files.write(fileCreatePath, text.getBytes());
			}
			else
			{
				Files.write(fileCreatePath, text.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
			}
		}
		catch ( FileAlreadyExistsException ignored )
		{
		}
	}

	/**
	 * Take the invoice number from the taxlink_OracleERP.txt file,
	 * write it to the number field
	 *
	 * @param page
	 *
	 * @return invoice number
	 */
	protected String passInvoiceNumberFromTextFile( OracleCloudCreateInvoicePage page )
	{
		String taxlinkRuleID = String.valueOf(getFileReadPath().get(0));
		page.writeToHeaderField(OracleCloudCreateInvoicePageFieldData.NUMBER, taxlinkRuleID);

		return taxlinkRuleID;
	}

	/**
	 * General tests, pass argument as per Page Enum
	 * Navigates to the Page passed as argument from homepage,
	 * after signing in
	 *
	 * @return
	 */
	protected OracleCloudSetupAndMaintenancePage navigateToLegalEntityPage( OracleCloudPageNavigationData page )
	{
		openNavPanel();
		OracleCloudSetupAndMaintenancePage setupPage = navigateToPage(
			OracleCloudPageNavigationData.ENTERPRISE_SETUP_MAINTENANCE);

		setupPage.openTasksTab();
		setupPage.clickMenuLink_LegalEntity(page);

		return setupPage;
	}

	/**
	 * General tests, pass argument as per Page Enum
	 * Navigates to the Page passed as argument from homepage,
	 * after signing in
	 *
	 * @return
	 */
	protected OracleCloudSetupAndMaintenancePage navigateToBusinessUnitPage( OracleCloudPageNavigationData page )
	{
		openNavPanel();
		OracleCloudSetupAndMaintenancePage setupPage = navigateToPage(
			OracleCloudPageNavigationData.ENTERPRISE_SETUP_MAINTENANCE);

		setupPage.openTasksMenu();
		setupPage.clickMenuLink_BusinessUnit(page);

		return setupPage;
	}

	/**
	 * General tests, pass argument as per Page Enum
	 * Navigates to the Page passed as argument from homepage,
	 * after signing in
	 *
	 * @param page
	 *
	 * @return
	 */
	protected OracleCloudSetupAndMaintenancePage navigateToPayablesLookupsPage( OracleCloudPageNavigationData page )
	{
		openNavPanel();
		OracleCloudSetupAndMaintenancePage setupPage = navigateToPage(
			OracleCloudPageNavigationData.ENTERPRISE_SETUP_MAINTENANCE);

		setupPage.openTasksMenu();
		setupPage.clickMenuLink_ManagePayablesLookups(page);

		return setupPage;
	}

	/**
	 * General tests, pass argument as per Page Enum
	 * Navigates to the Page passed as argument from homepage,
	 * after signing in
	 *
	 * @return
	 */
	protected OracleCloudSetupAndMaintenancePage navigateToTransactionTypePage( OracleCloudPageNavigationData page )
	{
		openNavPanel();
		OracleCloudSetupAndMaintenancePage setupPage = navigateToPage(
			OracleCloudPageNavigationData.ENTERPRISE_SETUP_MAINTENANCE);

		setupPage.openTasksMenu();
		setupPage.clickMenuLink_TransactionType(page);

		return setupPage;
	}

	/**
	 * General tests, pass argument as per Page Enum
	 * Navigates to the Page passed as argument from homepage,
	 * after signing in
	 *
	 * @return
	 */
	protected OracleCloudSetupAndMaintenancePage navigateToTransactionSourcePage( OracleCloudPageNavigationData page )
	{
		openNavPanel();
		OracleCloudSetupAndMaintenancePage setupPage = navigateToPage(
			OracleCloudPageNavigationData.ENTERPRISE_SETUP_MAINTENANCE);

		setupPage.openTasksMenu();
		setupPage.clickMenuLink_TransactionSource(page);

		return setupPage;
	}

	/**
	 * Method to Read file on the given path and return
	 * String text from file amd the Path of the file
	 *
	 * @return
	 */
	public List<Object> getFileReadPath( )
	{
		Path fileCreatePath = filePath.resolve("taxlink_OracleERP.txt");

		String textRead = null;
		try
		{
			textRead = Files
				.lines(fileCreatePath)
				.collect(Collectors.joining(System.lineSeparator()));
			VertexLogger.log("Reading Text: " + textRead);
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}

		List<Object> fileDetails = Arrays.asList(textRead, fileCreatePath);
		return fileDetails;
	}

	/**
	 * Method to delete file on the given path
	 */
	protected void deleteFile( ) throws IOException
	{
		Path filePathToDelete = Paths.get(filePath + File.separator + "taxlink_OracleERP.txt");
		Files.delete(filePathToDelete);
		VertexLogger.log("The text file has been deleted!!");
	}

	/**
	 * Method to get the file download path
	 */
	public Path getFileDownloadPath( )
	{
		Path resourcePath = Paths.get(String.valueOf(filePath));
		VertexLogger.log(String.valueOf(resourcePath));

		return resourcePath;
	}

	/**
	 * Method to check service provisioning
	 * if URL belongs to remote web driver
	 *
	 * @return URL
	 */
	private URL getDriverServiceUrl( )
	{
		try
		{
			String hostname = "localhost"; 
            if(isSeleniumHost) {
                hostname = "selenium"; //instead of "localhost" for GitHub Actions
            }
            return new URL("http://" + hostname + ":4444/wd/hub");
		}
		catch ( MalformedURLException e )
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Overridden Method to create chrome browser and
	 * assign default download path for Export to CSV
	 * in TaxLink URL
	 */
	protected void createChromeDriver( )
	{
		isDriverServiceProvisioned = Boolean.getBoolean("services.webdriver.provisioned");
		isDriverHeadlessMode = Boolean.getBoolean("services.webdriver.headless");
		isSeleniumHost = Boolean.getBoolean("services.webdriver.host.selenium");

		String fileDownloadPath = String.valueOf(getFileDownloadPath());
		HashMap<String, Object> chromePrefs = new HashMap<>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", fileDownloadPath);

		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		options.addArguments("window-size=1920,1080");
		options.addArguments("--disable-infobars");
		if(!isDriverServiceProvisioned) {
			options.addArguments("--incognito");
            options.addArguments("--remote-debugging-port=9022");
		}
		if ( isDriverHeadlessMode )
		{
			options.addArguments("--headless");
		}
		driver = isDriverServiceProvisioned ? new RemoteWebDriver(getDriverServiceUrl(), options) : new ChromeDriver(
			options);
	}

	/**
	 * AR Tests
	 * Navigates to the mana transaction page from the homepage,
	 * after signing in
	 *
	 * @return the Create Transaction page
	 */
	protected OracleCloudManageTransactionsPage navigateToManageTransactionsPage( )
	{
		openNavPanel();
		OracleCloudReceivablesBillingPage billingPage = navigateToPage(
			OracleCloudPageNavigationData.RECEIVABLES_BILLING_PAGE);

		WebElement menu = billingPage.openTasksTab();
		OracleCloudManageTransactionsPage manageTransactionsPage;
		manageTransactionsPage = billingPage.clickMenuLink(OracleCloudPageNavigationData.MANAGE_TRANSACTION_PAGE, menu);

		return manageTransactionsPage;
	}

	/**
	 * AR Tests
	 * Validates the total tax by comparing it to the expected tax
	 *
	 * @param page
	 * @param expectedTax
	 *
	 * @return true if taxes match, false if they don't
	 */
	protected boolean validateTax( OracleCloudReviewTransactionPage page, String expectedTax )
	{
		saveAndWaitForTaxUpdate(false);

		String totalTax = page.getTotalTaxAmount();

		VertexLogger.log("Total tax calculated: " + totalTax);

		boolean validated = expectedTax.equals(totalTax);

		return validated;
	}

	/**
	 * AR Tests
	 * Waits for the default tax amount (0.00) to change to the calculated
	 * amount after the invoice is saved,
	 * if needed
	 *
	 * @param zeroTaxExpected whether the displayed tax should be 0.00
	 */
	protected void saveAndWaitForTaxUpdate( boolean zeroTaxExpected )
	{
		OracleCloudReviewTransactionPage page = new OracleCloudReviewTransactionPage(driver);
		page.clickSaveButton();

		page.waitForPageLoad();

		try
		{
			page.closeWindow();
		}
		catch ( TimeoutException | StaleElementReferenceException e )
		{

		}

		boolean zeroTaxDisplayed = page.getDisplayedTax();
		while ( !zeroTaxExpected && zeroTaxDisplayed )
		{
			page.waitForPageLoad();
			zeroTaxDisplayed = page.getDisplayedTax();
		}
	}

	/**
	 * AR Tests
	 * Selects the transaction class for the AR invoice
	 * Waits for the page to properly refresh
	 *
	 * @param page
	 * @param transClass
	 * @param expectedHeader
	 */
	protected void changeARInvoiceTransactionClass( OracleCloudCreateTransactionPage page, String transClass,
		String expectedHeader )
	{
		page.selectFromDropdown(OracleCloudCreateTransactionPageFieldData.TRANSACTION_CLASS, transClass);
		page.waitForLoadedPage(expectedHeader);
	}

	/**
	 * AR Tests
	 * Input all required header information for the accounts receivable invoice
	 *
	 * @param busUnit
	 * @param transSource
	 * @param transType
	 * @param currency    is generally null, since it autofills
	 */
	protected void invoiceGeneralInformationRequiredInfo( OracleCloudCreateTransactionPage page, String busUnit,
		String transSource, String transType, String currency )
	{
		page.inputAndCheck(OracleCloudCreateTransactionPageFieldData.BUSINESS_UNIT_INVOICECREDIT, busUnit);
		page.jsWaiter.sleep(10000);
		page.selectAutoSuggest(busUnit);

		if ( page.autoSuggestFailure() )
		{
			page.inputAndCheck(OracleCloudCreateTransactionPageFieldData.BUSINESS_UNIT_INVOICECREDIT, busUnit);
		}

		if ( null != currency )
		{
			page.waitForAutomaticInput(OracleCloudCreateTransactionPageFieldData.CURRENCY, true);
			page.selectFromDropdown(OracleCloudCreateTransactionPageFieldData.CURRENCY, currency);
		}
		else
		{
			//TODO Michael Salomone - investigate why waits are breaking tests
			//			page.waitForAutomaticInput(OracleCloudCreateTransactionPageFieldData.CURRENCY, true);
		}

		page.writeToGeneralField(OracleCloudCreateTransactionPageFieldData.TRANSACTION_SOURCE, transSource);

		page.writeToGeneralField(OracleCloudCreateTransactionPageFieldData.TRANSACTION_TYPE, transType);
	}

	/**
	 * AR Tests
	 * Set the bill-to name and ensure the ship-to name autofills to match
	 * the input
	 *
	 * @param page
	 * @param searchInput
	 */
	protected void setBillToAndShipTo( OracleCloudCreateTransactionPage page, String searchInput )
	{
		page.inputAndCheck(OracleCloudCreateTransactionPageFieldData.BILL_TO_NAME, searchInput);

		page.waitForAutomaticInput(OracleCloudCreateTransactionPageFieldData.SHIP_TO_NAME, true);

		page.checkInput(OracleCloudCreateTransactionPageFieldData.SHIP_TO_NAME, searchInput);
	}

	/**
	 * AR Tests
	 * Search for and select the bill-to name, automatically filling out the ship-to name
	 * and the bill and ship to sites
	 *
	 * @param page
	 * @param searchButtonTitle
	 * @param fieldToSearch
	 * @param searchInput
	 * @param desiredResult
	 */
	protected void useSearchForBillToAndShipTo( OracleCloudCreateTransactionPage page, String searchButtonTitle,
		String fieldToSearch, String searchInput, String desiredResult )
	{
		WebElement searchMenuPopup = page.searchOnPopupOpenMenu(searchButtonTitle);
		WebElement searchField = page.searchOnPopupWriteToField(searchMenuPopup, fieldToSearch, searchInput);
		while ( !checkInput(searchField, searchInput) )
		{
			page.searchOnPopupWriteToField(searchMenuPopup, fieldToSearch, searchInput);
		}
		page.searchOnPopupClickSearch(searchMenuPopup);
		page.searchMccAllStates(searchMenuPopup);
		page.searchOnPopupClickOk(searchMenuPopup);

		page.waitForAutomaticInput(OracleCloudCreateTransactionPageFieldData.SHIP_TO_NAME, true);

		page.checkInput(OracleCloudCreateTransactionPageFieldData.SHIP_TO_NAME, desiredResult);
	}

	/**
	 * AR Tests
	 * Add required information to the invoice line item
	 *
	 * @param page
	 * @param lineNum
	 * @param item
	 * @param description
	 * @param uom
	 * @param quantity
	 * @param price
	 */
	protected void addInvoiceLineItem( OracleCloudCreateTransactionPage page, int lineNum, String item,
		String description, String uom, String quantity, String price, String transBusCategory )
	{
		WebElement line = page.selectInvoiceOrMemoLine(lineNum);

		if ( null != item )
		{
			page.inputAndCheck(OracleCloudCreateTransactionPageFieldData.ITEM, item, line);
		}

		if ( null != description )
		{
			page.inputAndCheck(OracleCloudCreateTransactionPageFieldData.DESCRIPTION, description, line);
		}
		else
		{
			//TODO Michael Salomone - investigate why waits are breaking tests
			//			page.waitForAutomaticInput(OracleCloudCreateTransactionPageFieldData.DESCRIPTION, line, true);
		}

		if ( null != uom )
		{
			page.inputAndCheck(OracleCloudCreateTransactionPageFieldData.UOM, uom, line);
			try
			{
				page.selectUomFromPopup(uom);
			}
			catch ( TimeoutException toe ) {
				VertexLogger.log("Unit popup box did not appear. Continuing with filling in line " +
					"information.", VertexLogLevel.INFO);
			}
		}

		page.inputAndCheck(OracleCloudCreateTransactionPageFieldData.QUANTITY_INVOICECREDIT, quantity, line);

		page.inputAndCheck(OracleCloudCreateTransactionPageFieldData.UNIT_PRICE_INVOICECREDIT, price, line);

		if ( null != transBusCategory )
		{
			page.inputAndCheck(OracleCloudCreateTransactionPageFieldData.TRANSACTION_BUSINESS_CATEGORY,
				transBusCategory, line);
		}
	}

	/**
	 * AR Tests
	 * Add required information to a tax only invoice line
	 *
	 * @param page
	 * @param lineNum
	 * @param description
	 * @param memoLine
	 */
	protected void addInvoiceLineTaxOnly( OracleCloudCreateTransactionPage page, int lineNum, String description,
		String memoLine )
	{
		WebElement line = page.selectInvoiceOrMemoLine(lineNum);

		page.inputAndCheck(OracleCloudCreateTransactionPageFieldData.DESCRIPTION, description, line);

		page.inputAndCheck(OracleCloudCreateTransactionPageFieldData.MEMO_LINE, memoLine, line);
	}

	/**
	 * AR Tests
	 * Modify the information of an invoice line item
	 *
	 * @param page
	 * @param lineNum
	 * @param item
	 * @param description
	 * @param uom
	 * @param quantity
	 * @param price
	 * @param transBusCategory
	 */
	protected void editInvoiceLineItem( OracleCloudCreateTransactionPage page, int lineNum, String item,
		String description, String uom, String quantity, String price, String transBusCategory )
	{
		WebElement line = page.selectInvoiceOrMemoLine(lineNum);

		if ( null != item )
		{
			page.inputAndCheck(OracleCloudCreateTransactionPageFieldData.ITEM, item, line);
		}

		if ( null != description )
		{
			page.inputAndCheck(OracleCloudCreateTransactionPageFieldData.DESCRIPTION, description, line);
		}

		if ( null != uom )
		{
			page.inputAndCheck(OracleCloudCreateTransactionPageFieldData.UOM, uom, line);
		}

		if ( null != quantity )
		{
			page.inputAndCheck(OracleCloudCreateTransactionPageFieldData.QUANTITY_INVOICECREDIT, quantity, line);
		}

		if ( null != price )
		{
			page.inputAndCheck(OracleCloudCreateTransactionPageFieldData.UNIT_PRICE_INVOICECREDIT, price, line);
		}

		if ( null != transBusCategory )
		{
			page.inputAndCheck(OracleCloudCreateTransactionPageFieldData.TRANSACTION_BUSINESS_CATEGORY,
				transBusCategory, line);
		}
	}

	/**
	 * AR Tests
	 * Click the details button for an invoice line item to
	 * navigate to the details page for that item
	 *
	 * @param page
	 * @param lineNum
	 * @param expectedHeader
	 */
	protected void navigateToInvoiceItemDetails( OracleCloudCreateTransactionPage page, int lineNum,
		String expectedHeader )
	{
		WebElement line = page.selectInvoiceOrMemoLine(lineNum);

		page.clickDetailsButton(line);

		page.waitForLoadedPage(expectedHeader + lineNum);
	}

	/**
	 * AR Tests
	 * Click the save and close button when on the invoice line detail page
	 *
	 * @param page
	 * @param expectedHeader
	 */
	protected void saveAndCloseInvoiceItemDetails( OracleCloudCreateTransactionPage page, String expectedHeader )
	{
		page.clickDetailsSaveAndCloseButton();

		page.waitForLoadedPage(expectedHeader);
	}

	/**
	 * AR Tests
	 * Edit information for an invoice line item while on the details page
	 *
	 * @param page
	 * @param item
	 * @param description
	 * @param uom
	 * @param quantity
	 * @param price
	 */
	protected void editInvoiceLineDetails( OracleCloudCreateTransactionPage page, String item, String description,
		String uom, String quantity, String price )
	{

		if ( null != item )
		{
			page.inputAndCheck(OracleCloudCreateTransactionPageFieldData.ITEM, item);
		}

		if ( null != description )
		{
			page.inputAndCheck(OracleCloudCreateTransactionPageFieldData.DESCRIPTION, description);
		}

		if ( null != uom )
		{
			page.inputAndCheck(OracleCloudCreateTransactionPageFieldData.UOM, uom);
		}

		if ( null != quantity )
		{
			page.inputAndCheck(OracleCloudCreateTransactionPageFieldData.QUANTITY_INVOICECREDIT, quantity);
		}

		if ( null != price )
		{
			page.inputAndCheck(OracleCloudCreateTransactionPageFieldData.UNIT_PRICE_INVOICECREDIT, price);
		}
	}

	/**
	 * AP Tests
	 * Navigates to the create invoice page from the homepage,
	 * after signing in
	 *
	 * @return the Create Invoice page
	 */
	protected OracleCloudCreateInvoicePage navigateToCreateInvoicePage( )
	{
		openNavPanel();
		OracleCloudPayablesInvoicesPage invoicesPage = navigateToPage(OracleCloudPageNavigationData.PAYABLES_INVOICES);

		WebElement menu = invoicesPage.openTasksTab();
		OracleCloudCreateInvoicePage createInvoicePage = invoicesPage.clickMenuLink(
			OracleCloudPageNavigationData.CREATE_INVOICE_PAGE, menu);

		return createInvoicePage;
	}

	/**
	 * AP Tests
	 * Navigates to the manage invoices page from the homepage,
	 * after signing in
	 *
	 * @return the Create Invoice page
	 */
	protected OracleCloudManageInvoicesPage navigateToManageInvoicesPage( )
	{
		openNavPanel();
		OracleCloudPayablesInvoicesPage invoicesPage = navigateToPage(OracleCloudPageNavigationData.PAYABLES_INVOICES);

		WebElement menu = invoicesPage.openTasksTab();
		OracleCloudManageInvoicesPage manageInvoicesPage = invoicesPage.clickMenuLink(
			OracleCloudPageNavigationData.MANAGE_INVOICES_PAGE, menu);

		return manageInvoicesPage;
	}

	/**
	 * AP Tests
	 * Navigates to the create invoice without PO page from the Supplier Portal,
	 * after signing in
	 *
	 * @return the Create Invoice Without PO page
	 */
	protected OracleCloudiSupplierInvoicePage navigateToCreateInvoiceWithoutPOPage( )
	{
		OracleCloudiSupplierInvoicePage invoicesPage = new OracleCloudiSupplierInvoicePage(driver);
		invoicesPage.clickHome();
		invoicesPage.clickSupplierPortal();
		invoicesPage.clickCreateInvoiceWithoutPo();

		return invoicesPage;
	}

	/**
	 * AP Tests
	 * Navigates to the manage payments page
	 *
	 * @return the Manage Payments page
	 */
	protected OracleCloudPayablesPaymentsPage navigateToManagePaymentsPage( )
	{
		openNavPanel();
		OracleCloudPayablesPaymentsPage paymentsPage = navigateToPage(OracleCloudPageNavigationData.PAYABLES_PAYMENTS);

		WebElement menu = paymentsPage.openTasksTab();
		paymentsPage.clickManagePaymentLink();

		return paymentsPage;
	}

	/**
	 * AP Tests
	 * Navigates to the payments dashboard page
	 *
	 * @return the Payments Dashboard page
	 */
	protected OracleCloudPayablesPaymentsDashboardPage navigateToPayablesPaymentsDashboardPage( )
	{
		openNavPanel();
		OracleCloudPayablesPaymentsDashboardPage paymentsDashboardPage = navigateToPage(OracleCloudPageNavigationData.PAYABLES_PAYMENTS_DASHBOARD);

		return paymentsDashboardPage;
	}

	/**
	 * PO Tests
	 * Navigates to Procurement -> Purchase Orders -> Create Order.
	 *
	 * @return a Create Purchase Orders page object.
	 */
	protected OracleCloudCreatePurchaseOrderPage navigateToCreatePurchaseOrdersPage( )
	{
		openNavPanel();
		OracleCloudProcurementPurchaseOrdersPage procurementPoPage = navigateToPage(
			OracleCloudPageNavigationData.PROCUREMENT_PURCHASE_ORDERS);

		WebElement menu = procurementPoPage.openTasksTab();
		OracleCloudCreatePurchaseOrderPage createPoPage = procurementPoPage.clickMenuLink(
			OracleCloudPageNavigationData.CREATE_PURCHASE_ORDER_PAGE, menu);

		return createPoPage;
	}

	/**
	 * PO Tests
	 * Navigates to Procurement -> Purchase Orders -> Manage Orders.
	 *
	 * @return a Manage Purchase Orders page object.
	 */
	protected OracleCloudManagePurchaseOrdersPage navigateToManagePurchaseOrdersPage( )
	{
		openNavPanel();
		OracleCloudProcurementPurchaseOrdersPage procurementPoPage = navigateToPage(
			OracleCloudPageNavigationData.PROCUREMENT_PURCHASE_ORDERS);

		WebElement menu = procurementPoPage.openTasksTab();
		OracleCloudManagePurchaseOrdersPage managePoPage = procurementPoPage.clickMenuLink(
			OracleCloudPageNavigationData.MANAGE_PURCHASE_ORDERS_PAGE, menu);

		return managePoPage;
	}

	/**
	 * Requisition Tests
	 * Navigates to Procurement -> Purchase Requisitions -> Manage Orders.
	 *
	 * @return a Manage Requisitions page object.
	 */
	protected OracleCloudManageReqsPage navigateToManageReqsPage( )
	{
		openNavPanel();
		OracleCloudProcurementPurchaseReqsPage procurementReqPage = navigateToPage(
			OracleCloudPageNavigationData.PROCUREMENT_PURCHASE_REQUISITIONS);

		OracleCloudManageReqsPage manageReqsPage = procurementReqPage.clickManageReqsButton(
			OracleCloudPageNavigationData.MANAGE_REQUISITIONS_PAGE);

		return manageReqsPage;
	}

	/**
	 * AP Tests
	 * Input all required header information for the accounts payable invoice
	 *
	 * @param page
	 * @param busUnit
	 * @param supplier
	 * @param amtUnit      autofilled, but may need to be changed
	 * @param amount
	 * @param orderType    autofilled, but may need to be changed
	 * @param paymentTerms autofilled, but may need to be changed
	 */
	protected void invoiceHeaderRequiredInfo( OracleCloudCreateInvoicePage page, String busUnit, String supplier,
		String amtUnit, String amount, String orderType, String date, String paymentTerms )
	{
		page.inputAndCheck(OracleCloudCreateInvoicePageFieldData.BUSINESS_UNIT, busUnit);

		if ( null != amtUnit )
		{
			page.waitForAutomaticInput(OracleCloudCreateInvoicePageFieldData.AMOUNT_UNIT, false);
			page.selectFromDropdown(OracleCloudCreateInvoicePageFieldData.AMOUNT_UNIT, amtUnit);
		}
		else
		{
			page.waitForAutomaticInput(OracleCloudCreateInvoicePageFieldData.AMOUNT_UNIT, false);
		}

		page.inputAndCheck(OracleCloudCreateInvoicePageFieldData.SUPPLIER, supplier);

		page.waitForAutomaticInput(OracleCloudCreateInvoicePageFieldData.SUPPLIER_SITE, true);

		if ( null != orderType )
		{
			page.selectFromDropdown(OracleCloudCreateInvoicePageFieldData.INV_TYPE, orderType);
		}

		page.inputAndCheck(OracleCloudCreateInvoicePageFieldData.AMOUNT_NUMBER, amount);

		if ( null != date )
		{
			page.inputAndCheck(OracleCloudCreateInvoicePageFieldData.DATE, date);
		}

		if ( null != paymentTerms )
		{
			page.inputAndCheck(OracleCloudCreateInvoicePageFieldData.PAYMENT_TERMS, paymentTerms);
		}
	}

	/**
	 * AP Tests
	 * Input all required header information for the accounts payable invoice without PO
	 *
	 * @param page
	 * @param supplierSite
	 * @param date
	 * @param email
	 */
	protected void invoiceWithoutPOHeaderRequiredInfo( OracleCloudiSupplierInvoicePage page, String supplierSite,
		String date, String email )
	{
		page.inputAndCheck(OracleCloudCreateInvoicePageFieldData.SUPPLIER_SITE_PO, supplierSite);

		if ( null != date )
		{
			page.inputAndCheck(OracleCloudCreateInvoicePageFieldData.DATE_PO, date);
		}

		page.inputAndCheck(OracleCloudCreateInvoicePageFieldData.EMAIL_PO, email);
	}

	/**
	 * AP Tests
	 * After generating the invoice number,
	 * write it to the number field
	 *
	 * @param page
	 * @param testName
	 *
	 * @return invoice number
	 */
	protected String generateAndWriteInvoiceNumber( OracleCloudCreateInvoicePage page, String testName )
	{
		String number = page.generateInvoiceNumber(testName);

		page.writeToHeaderField(OracleCloudCreateInvoicePageFieldData.NUMBER, number);

		return number;
	}

	/**
	 * AP Tests
	 * After generating the invoice number,
	 * write it to the number field
	 *
	 * NOTE: ONLY FOR INVOICE WITHOUT PO
	 *
	 * @param page
	 * @param testName
	 *
	 * @return invoice number
	 */
	protected String generateAndWriteInvoiceNumberPO( OracleCloudiSupplierInvoicePage page, String testName )
	{
		String number = page.generateInvoiceNumber(testName);

		page.writeToHeaderField(OracleCloudCreateInvoicePageFieldData.NUMBER_PO, number);

		return number;
	}

	/**
	 * AP Tests
	 * Add invoice line item with required information
	 *
	 * @param page
	 * @param lineNum
	 * @param itemType
	 * @param amount
	 * @param date
	 * @param shipToLocation
	 */
	protected void addInvoiceLineItem( OracleCloudCreateInvoicePage page, int lineNum, String itemType, String amount,
		String distributionSet, String distributionCombo, String date, String shipToLocation, boolean explicitlySelect )
	{
		WebElement line;
		if ( !explicitlySelect )
		{
			line = page.selectInvoiceLine(lineNum);
		}
		else
		{
			line = null;
			page.selectInvoiceLineNotDisplayed();
		}

		//if(null != itemType)
		//page.selectFromDropdown(OracleCloudCreateInvoicePageFieldData.LINE_TYPE, itemType);

		page.writeToLine(OracleCloudCreateInvoicePageFieldData.LINE_AMOUNT, amount, line);

		if ( null != distributionSet )
		{
			page.writeToLine(OracleCloudCreateInvoicePageFieldData.DISTRIBUTION_SET, distributionSet, line);
		}

		if ( null != distributionCombo )
		{
			page.writeToLine(OracleCloudCreateInvoicePageFieldData.DISTRIBUTION_COMBINATION, distributionCombo, line);
		}

		page.writeToLine(OracleCloudCreateInvoicePageFieldData.ACCOUNTING_DATE, date, line);

		page.writeToLine(OracleCloudCreateInvoicePageFieldData.SHIPTO_LOC, shipToLocation, line);
	}

	/**
	 * AP Tests
	 * Add invoice line item with required information on the Edit Invoice page.
	 * Edit Invoice Page is typically navigated to through the Manage Invoices page.
	 *
	 * @param page
	 * @param lineNum
	 * @param itemType
	 * @param amount
	 * @param date
	 * @param shipToLocation
	 */
	protected void addInvoiceLineItem( OracleCloudManageInvoicesPage page, int lineNum, String itemType, String amount,
		String distributionSet, String distributionCombo, String date, String shipToLocation, boolean explicitlySelect )
	{
		WebElement line;
		if ( !explicitlySelect )
		{
			line = page.selectInvoiceLine(lineNum);
		}
		else
		{
			line = null;
			page.selectInvoiceLineNotDisplayed();
		}

		page.writeToLine(OracleCloudCreateInvoicePageFieldData.LINE_AMOUNT, amount, line);

		if ( null != distributionSet )
		{
			page.writeToLine(OracleCloudCreateInvoicePageFieldData.DISTRIBUTION_SET, distributionSet, line);
		}

		if ( null != distributionCombo )
		{
			page.writeToLine(OracleCloudCreateInvoicePageFieldData.DISTRIBUTION_COMBINATION, distributionCombo, line);
		}

		page.writeToLine(OracleCloudCreateInvoicePageFieldData.ACCOUNTING_DATE, date, line);

		page.writeToLine(OracleCloudCreateInvoicePageFieldData.SHIPTO_LOC, shipToLocation, line);
	}

	/**
	 * AP Tests
	 * Modify information for an invoice line item
	 *
	 * @param page
	 * @param lineNum
	 * @param itemType
	 * @param amount
	 * @param distributionSet
	 * @param date
	 * @param shipToLocation
	 */
	protected void editInvoiceLineItem( OracleCloudCreateInvoicePage page, int lineNum, String itemType, String amount,
		String distributionSet, String date, String shipToLocation )
	{
		WebElement line = invoiceLineSelect(page, 1);

		//if(null != itemType)
		//page.selectFromDropdown(OracleCloudCreateInvoicePageFieldData.LINE_TYPE, itemType);

		if ( null != amount )
		{
			page.writeToLine(OracleCloudCreateInvoicePageFieldData.LINE_AMOUNT, amount, line);
		}

		if ( null != distributionSet )
		{
			page.writeToLine(OracleCloudCreateInvoicePageFieldData.DISTRIBUTION_SET, distributionSet, line);
		}

		if ( null != date )
		{
			page.writeToLine(OracleCloudCreateInvoicePageFieldData.ACCOUNTING_DATE, date, line);
		}

		if ( null != shipToLocation )
		{
			page.writeToLine(OracleCloudCreateInvoicePageFieldData.SHIPTO_LOC, shipToLocation, line);
		}
	}

	// TODO Michael Salomone method no longer needed. Deprecate as AP tests are refactored.

	/**
	 * AP Tests
	 * Helper method
	 * Click on an invoice line, wait for it to become active,
	 * select and return that line
	 *
	 * @param page    the create invoice page
	 * @param lineNum invoice line to select
	 *
	 * @return the selected line
	 */
	protected WebElement invoiceLineSelect( OracleCloudCreateInvoicePage page, int lineNum )
	{
		page.clickInvoiceLine(lineNum);
		WebElement line = page.selectActiveInvoiceLine();
		return line;
	}

	/**
	 * AP Tests
	 * Adds a row for tax information and checks whether or not it is a tax only line
	 *
	 * @param page
	 * @param taxPopup
	 * @param taxOnly
	 */
	protected void addTaxRow( OracleCloudCreateInvoicePage page, WebElement taxPopup, boolean taxOnly )
	{
		page.taxPopupClickAddRow(taxPopup);

		if ( taxOnly )
		{
			page.taxPopupCheckTaxOnly(taxPopup);
		}
	}

	/**
	 * AP Tests
	 * Modify information on the tax row
	 *
	 * @param page
	 * @param taxPopup
	 * @param rateNameInput
	 * @param rateCodeInput
	 * @param taxRegimeInput
	 * @param taxNameInput
	 * @param statusNameInput
	 * @param taxJurisdictionInput
	 * @param amountInput
	 */
	protected void editTaxRowInformation( OracleCloudCreateInvoicePage page, WebElement taxPopup, String rateNameInput,
		String rateCodeInput, String taxRegimeInput, String taxNameInput, String statusNameInput,
		String taxJurisdictionInput, String amountInput )
	{
		WebElement searchRateNamePopup = page.taxPopupSearchName(taxPopup);
		page.rateNameSearchInputFields(searchRateNamePopup, rateNameInput, rateCodeInput, taxRegimeInput, taxNameInput,
			statusNameInput, taxJurisdictionInput);

		int rowNum = 0;

		if ( environmentURL.equalsIgnoreCase("https://ecog-test.fa.us2.oraclecloud.com") )
		{
			rowNum = 1;
		}
		if ( environmentURL.equalsIgnoreCase("https://ecog-dev1.fa.us2.oraclecloud.com") )
		{
			rowNum = 2;
		}
		if(taxRegimeInput.contains("VERTEX CA")){
			rowNum = 0;
		}

		page.rateNameSearchSelectResult(searchRateNamePopup, rowNum);

		page.taxPopupWriteAmount(taxPopup, amountInput, 1);
	}

	/**
	 * OM Tests
	 * Navigates to the create order page from the homepage,
	 * after signing in
	 *
	 * @return the Create Order page
	 */
	protected OracleCloudCreateOrderPage navigateToCreateOrderPage( )
	{
		openNavPanel();
		OracleCloudOrderManagementPage orderManagementPage = navigateToPage(
			OracleCloudPageNavigationData.ORDERMANAGEMENT_OM);

		OracleCloudCreateOrderPage createOrderPage = orderManagementPage.clickCreateOrder(
			OracleCloudPageNavigationData.CREATE_ORDER_PAGE);

		return createOrderPage;
	}

	/**
	 * OM Tests
	 * Navigates to the Manage orders page from the homepage,
	 * after signing in
	 *
	 * @return the Manage Orders page
	 */
	protected OracleCloudManageOrdersPage navigateToManageOrdersPage( )
	{
		openNavPanel();
		OracleCloudOrderManagementPage orderManagementPage = navigateToPage(
			OracleCloudPageNavigationData.ORDERMANAGEMENT_OM);

		OracleCloudManageOrdersPage manageOrdersPage = orderManagementPage.navigateToManageOrders(
			OracleCloudPageNavigationData.MANAGE_ORDERS_PAGE);

		return manageOrdersPage;
	}

	/**
	 * OM Tests
	 * Input all required header information for the order to be created
	 *
	 * @param page
	 * @param customer
	 * @param busUnit
	 */
	protected void orderHeaderRequiredInfo( OracleCloudCreateOrderPage page, String customer, String busUnit,
		String billToAcc )
	{
		try
		{
			page.selectFromDropdown(OracleCloudCreateOrderFieldData.BUSINESS_UNIT, busUnit);
		}
		catch ( TimeoutException toe )
		{
			VertexLogger.log("Business Unit dropdown not available. This is likely due to the fusion instance" +
							 " already having a static business unit value set.", VertexLogLevel.WARN);
		}

		page.jsWaiter.sleep(10000);

		page.inputAndCheck(OracleCloudCreateOrderFieldData.CUSTOMER, customer);
		page.clickOkSelectCustomer();
	}

	/**
	 * OM Tests
	 * Inputs customer data for creating an order.
	 *
	 * @param page
	 * @param customer
	 */
	protected void orderHeaderRequiredInfoNoBusUnit( OracleCloudCreateOrderPage page, String customer )
	{
		page.inputAndCheck(OracleCloudCreateOrderFieldData.CUSTOMER, customer);
		page.clickOkSelectCustomer();
	}

	/**
	 * PO tests.
	 * Handles input for all fields appearing in the Purchase Order
	 * create order popup page.
	 */
	protected void inputCreatePoInfo( OracleCloudCreatePurchaseOrderPage page, String style, String procurementBu,
		String requisitioningBu, String supplier, String defaultShipToLocation )
	{
		page.inputOrderDetails(style, procurementBu, requisitioningBu, supplier, defaultShipToLocation);
	}

	/**
	 * PO tests.
	 * Handles input for all fields appearing in the header of
	 * Purchase Order manage orders page.
	 */
	protected void inputManagePoHeaderInfo( OracleCloudManagePurchaseOrdersPage page, String orderNum )
	{
		page.inputAndTab(OracleCloudPoPagesFieldData.ORDER, orderNum);
	}

	/**
	 * Requisition tests.
	 * Handles input for all fields appearing in the search section
	 * of the Manage Requisitions page.
	 *
	 * @param page   A Manage Requisitions page object.
	 * @param reqNum The requisition number to search.
	 */
	protected void inputManageReqSearchInfo( OracleCloudManageReqsPage page, String reqNum )
	{
		page.inputAndTab(OracleCloudReqPagesFieldData.REQUISITION, reqNum);
	}

	protected boolean isFieldEmpty( WebElement field )
	{
		String content = field.getText();

		boolean empty = content.isEmpty();

		return empty;
	}
}