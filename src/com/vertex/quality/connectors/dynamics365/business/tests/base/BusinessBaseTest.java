package com.vertex.quality.connectors.dynamics365.business.tests.base;

import com.vertex.quality.common.enums.DBConnectorNames;
import com.vertex.quality.common.enums.DBEnvironmentDescriptors;
import com.vertex.quality.common.enums.DBEnvironmentNames;
import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pojos.EnvironmentCredentials;
import com.vertex.quality.common.pojos.EnvironmentInformation;
import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.common.utils.SQLConnection;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.properties.CommonDataProperties;
import com.vertex.quality.common.utils.properties.ReadProperties;
import com.vertex.quality.connectors.dynamics365.business.pages.*;
import com.vertex.quality.connectors.dynamics365.business.pages.accountpayable.*;
import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessSalesBasePage;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.TimeoutException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;

/**
 * base test class for business central environment
 *
 * @author osabha, cgajes
 */
@Test(groups = { "D365_Business_Central" })
public abstract class BusinessBaseTest extends VertexUIBaseTest<BusinessAdminHomePage> {
	private final String CONFIG_DETAILS_FILE_PATH = CommonDataProperties.CONFIG_DETAILS_FILE_PATH;
	protected EnvironmentInformation environmentInformation;
	protected EnvironmentCredentials environmentCredentials;
	protected String username;
	protected String password;
	protected String environmentURL;
	protected String trustedId;
	protected String companyCode;

	protected DBConnectorNames connectorName = DBConnectorNames.D365_BUSINESS_CENTRAL;

	protected DBEnvironmentNames environmentName = DBEnvironmentNames.QA;
	protected DBEnvironmentDescriptors environmentDescriptor = DBEnvironmentDescriptors.D365_BUSINESS_CENTRAL;

	Date date = new Date(System.currentTimeMillis());
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
	String transactionDate = sdf.format(date);

	@Override
	protected BusinessAdminHomePage loadInitialTestPage()
	{
		BusinessAdminHomePage homePage = null;

		getCredentials();

		homePage = signInToHomePage();
		VertexLogger.log("Current window size: "+ driver.manage().window().getSize());
		if (driver.manage().window().getSize().width <= 1294 )
		{
			driver.manage().window().setSize(new Dimension(1616, 876));
		}
		driver.manage().window().setSize(new Dimension(1616, 876));
		homePage.checkForCautionPopup();
		homePage.waitForLoadingScreen();

		return homePage;
	}

	/**
	 * Helper method to initialize test page and sign on to application
	 * includes waiting for loading screen to pass
	 *
	 * @return the admin homepage
	 */
	protected BusinessAdminHomePage initializeTestPageAndSignOn()
	{
		BusinessAdminHomePage homePage = testStartPage;
		VertexLogger.log("Current window size: "+ driver.manage().window().getSize());
		if (driver.manage().window().getSize().width <= 1294 )
		{
			driver.manage().window().setSize(new Dimension(1616, 876));
		}
		homePage.checkForCautionPopup();
		homePage.waitForLoadingScreen();
		return homePage;
	}

	/**
	 * Gets credentials for the connector from the database
	 */
	protected void getCredentials()
	{
		try {
			ReadProperties readConfigDetails = new ReadProperties(CONFIG_DETAILS_FILE_PATH);
			username = readConfigDetails.getProperty("TEST.VERTEX.D365.USERNAME");;
			password = readConfigDetails.getProperty("TEST.VERTEX.D365.PASSWORD");;
			environmentURL = "https://businesscentral.dynamics.com/dfc1b5c3-94fc-4abc-b8fb-09484816c011/VertexSandbox1";
			String sandbox1Env = environmentURL;
			String sandbox2Env = "https://businesscentral.dynamics.com/dfc1b5c3-94fc-4abc-b8fb-09484816c011/VertexSandbox2";
			String sandbox3Env = "https://businesscentral.dynamics.com/dfc1b5c3-94fc-4abc-b8fb-09484816c011/Vertex_BC22";
//			String envToRun = "BC22";
			String envToRun = System.getProperty("url");
			if(envToRun != null) {
				if (envToRun.contains("two")) {
					environmentURL = sandbox2Env;
				} else if (envToRun.contains("BC22")) {
					environmentURL = sandbox3Env;
				}
			}
			else
			{
				environmentURL=sandbox1Env;
			}
			//environmentURL = "";
		} catch (Exception e) {
			VertexLogger.log("unable to load the environment information/credentials for a d365 business central test",
					VertexLogLevel.ERROR);
			e.printStackTrace();
		}
	}

	/**
	 * opens the application's sign on page
	 *
	 * @return the sign on page
	 */
	protected BusinessSignOnPage initializeSignOnPage() {
		driver.get(environmentURL);
		BusinessSignOnPage signOnPage = new BusinessSignOnPage(driver);
		return signOnPage;
	}

	/**
	 * this method does the whole sign in process to business central environment
	 * does the sign in process over three different pages
	 * in case the application asks for account security verification methods, also
	 * handles that page to continue signing in
	 *
	 * @return new instance of the admin home page
	 */
	public BusinessAdminHomePage signInToHomePage()
	{
		BusinessSignOnPage signOnPage = initializeSignOnPage();
		//signOnPage.clickStartFreeButton();
		signOnPage.enterEmailAddress(username);
		signOnPage.enterPassword(password);
		BusinessAdminHomePage homePage = signOnPage.clickDoNotStayLoggedIn();
		String title = homePage.getPageTitle();
		if (title.equals("don't lose access to your account!")) {
			BusinessSecureAccountPage secureAccountPage = new BusinessSecureAccountPage(driver);
			signOnPage = secureAccountPage.clickCancel();
			signOnPage.clickDoNotStayLoggedIn();
		}
		return homePage;
	}

	/**
	 * Returns the current date in m/d/yyyy format
	 *
	 * @return transaction date
	 */
	protected String getTransactionDate()
	{
		return transactionDate;
	}

	/**
	 * Searches for an extension on the extensions page (list is extremely long and gets cut off)
	 * and then selects the extension found
	 *
	 * @param page
	 * @param extensionToFind
	 */
	protected void searchForAndSelectExtension(BusinessExtensionsPage page, String extensionToFind) {
		page.searchForExtension(extensionToFind);
		page.clickVertexExtension();
	}

	/**
	 * Opens the vertex tax details window on sales edit pages
	 *
	 * @param page
	 */
	protected void openVertexTaxDetailsWindow(BusinessSalesBasePage page) {
		page.waitForPageLoad();

		try {
			page.salesEditNavMenu.clickOnEllipsisIcon();
		} catch (NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
			// if More Options has already been opened
		}

		page.waitForPageLoad();

		try {
			page.salesEditNavMenu.clickMoreOptions();
		} catch (NoSuchElementException | TimeoutException e) {
			// if More Options has already been opened
		}

		page.waitForPageLoad();

		page.salesEditNavMenu.selectRelated();
		page.salesEditNavMenu.selectVertexTaxDetails();

		page.waitForPageLoad();
	}




	/**
	 * Opens the vertex tax details window on sales invoice pages
	 * @param page
	 * @author bhikshapathi
	 */
	protected  void openVertexTaxDetailsWindowFromInvoice(BusinessSalesBasePage page)
	{
		page.waitForPageLoad();
		try {
			page.salesEditNavMenu.clickMoreOptions();
		} catch (NoSuchElementException | TimeoutException e) {
			// if More Options has already been opened
		}
		page.salesEditNavMenu.selectNavigateFromInvoice();
		page.salesEditNavMenu.selectVertexTaxDetails();
	}
	/**
	 * Opens the vertex tax details window on sales invoice pages
	 * @param page
	 * @author bhikshapathi
	 */
	protected  void openVertexTaxDetailsWindowFromReturnOrder(BusinessSalesBasePage page)
	{
		page.waitForPageLoad();
		try {
			page.salesEditNavMenu.clickMoreOptions();
		} catch (NoSuchElementException | TimeoutException e) {
			// if More Options has already been opened
		}
		try {
			page.salesEditNavMenu.clickOnEllipsisIcon();
		} catch (NoSuchElementException | TimeoutException e) {
			// if More Options has already been opened
		}
		page.salesEditNavMenu.selectRelated();
		page.salesEditNavMenu.selectVertexTaxDetails();
	}

	/**
	 * clicks the recalculate tax option
	 *
	 * @param page
	 */
	protected void recalculateTax(BusinessSalesBasePage page) {
		try {
			page.salesEditNavMenu.clickMoreOptions();
		} catch (NoSuchElementException | TimeoutException e) {
			// if More Options has already been opened
		}

		try {
			page.salesEditNavMenu.clickOnEllipsisIcon();
		} catch (NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
			// if More Options has already been opened
		}
		page.salesEditNavMenu.clickMoreOptionsActionsButton();
		page.salesEditNavMenu.selectRecalculateTaxButton();
	}

	/**
	 * Fills in the general info when creating a sales memo
	 *
	 * @param memoPage
	 * @param customerCode
	 */
	protected void fillInSalesMemoGeneralInfo(BusinessSalesCreditMemoPage memoPage, String customerCode) {
		memoPage.enterCustomerCode(customerCode);
		memoPage.setPostingDate();
		memoPage.setDueDate();
	}

	/**
	 * Fills in the general info when creating a sales invoice
	 *
	 * @param invoicePage
	 * @param customerCode
	 */
	protected void fillInSalesInvoiceGeneralInfo(BusinessSalesInvoicePage invoicePage, String customerCode) {
		invoicePage.enterCustomerCode(customerCode);
		invoicePage.setPostingDate();
		invoicePage.setDueDate();
	}

	/**
	 * Fills in the general info when creating a sales order
	 *
	 * @param orderPage
	 * @param customerCode
	 */
	protected void fillInSalesOrderGeneralInfo(BusinessSalesOrderPage orderPage, String customerCode) {
		orderPage.enterCustomerCode(customerCode);
		orderPage.setPostingDate();
		orderPage.setOrderDate();
		orderPage.setDueDate();
	}
	/**
	 * Fills in the general info when creating a sales order
	 *
	 * @param orderPage
	 * @param customerCode
	 * @author bhikshapathi
	 */
	protected void fillInSalesReturnOrderGeneralInfo(BusinessSalesReturnOrderPage orderPage, String customerCode) {
		orderPage.enterCustomerCode(customerCode);
		orderPage.setPostingDate();
		orderPage.setOrderDate();
	}

	/**
	 * Fills in the general info when creating a sales quote
	 *
	 * @param quotePage
	 * @param customerCode
	 */
	protected void fillInSalesQuoteGeneralInfo(BusinessSalesQuotesPage quotePage, String customerCode) {
		quotePage.enterCustomerCode(customerCode);
		quotePage.setDueDate();
	}

	/**
	 * Fills in the items table when creating a sales order/quote/memo/invoice
	 *
	 * @param address
	 * @param city
	 * @param state
	 * @param zipCode
	 * @author bhikshapathi
	 */
	protected void fillInCustomAddressInShippingAndBilling(String address, String city, String state, String zipCode) {
		fillInCustomAddressInShippingAndBilling(address, city, state, zipCode, "US");
	}


	/**
	 * Fills in the items table when creating a sales order/quote/memo/invoice
	 *
	 * @param address
	 * @param city
	 * @param state
	 * @param zipCode
	 * @param country
	 * @author bhikshapathi
	 */
	protected void fillInCustomAddressInShippingAndBilling(String address, String city, String state, String zipCode, String country) {
		BusinessSalesBasePage salesPage = new BusinessSalesBasePage(driver);
		salesPage.enterAddress(address);
		salesPage.enterCityName(city);
		salesPage.enterState(state);
		salesPage.enterZipCodeValue(zipCode);
		salesPage.enterCountry(country);
	}


	/**
	 * Fills line leval location code in the items table when creating a sales order/quote/memo/invoice
	 * @param locationCode
	 * @param itemIndex
	 * @author bhikshapathi
	 */
	protected void addLineLevelLocationCode(String locationCode, int itemIndex)
	{
		int itemNumber = itemIndex - 1;
		BusinessSalesBasePage salesPage = new BusinessSalesBasePage(driver);
		salesPage.waitForPageLoad();
		salesPage.enterLocationCode(locationCode, itemNumber);

	}
	/**
	 * Delete the line items in Purchase Documents
*/
	 protected void deletingLine(int rowIndex){
		int rowNumber=rowIndex-1;
		BusinessSalesBasePage salesPage = new BusinessSalesBasePage(driver);
		salesPage.deleteLine(rowNumber);
	}
	/**
	 * Fills line leval location code in the items table when creating a sales order/quote/memo/invoice
	 * @param quantity
	 * @param itemIndex
	 * @author bhikshapathi
	 */
	protected void updateQuantity(String quantity, int itemIndex)
	{
		int itemNumber = itemIndex - 1;
		BusinessSalesBasePage salesPage = new BusinessSalesBasePage(driver);
		salesPage.enterQuantity(quantity, itemNumber);
	}

	/**
	 * Updated Line Amount the items table when creating a sales order/quote/memo/invoice
	 * @param amount
	 * @param itemIndex
	 * @author bhikshapathi
	 */
	protected void changedLineAmount(String amount, int itemIndex)
	{
		int itemNumber = itemIndex - 1;
		BusinessSalesBasePage salesPage = new BusinessSalesBasePage(driver);
		salesPage.enterLineAmount(amount, itemNumber);
	}

	/**
	 * Update line discount for a product
	 * @param discountPercentage
	 * @param itemIndex
	 */
	protected void changeLineDiscount(String discountPercentage, int itemIndex){
		int itemNumber=itemIndex-1;
		BusinessSalesBasePage salesPage = new BusinessSalesBasePage(driver);
		salesPage.enterDiscountPercent(discountPercentage, itemNumber);
	}
	/**
	 * Update price for a line item
	 * @param price
	 * @param itemIndex
	 */
	protected void updatePrice(String price, int itemIndex)
	{
		BusinessSalesBasePage salesPage = new BusinessSalesBasePage(driver);
		salesPage.enterUnitPrice(price, itemIndex);
	}
	/**
	 * Update tax group code for a line item
	 * @param code
	 * @param rowIndex
	 */
	protected void updateTaxGroupCode(String code, int rowIndex){
		BusinessSalesBasePage salesPage = new BusinessSalesBasePage(driver);
		salesPage.enterTaxGroupCode(code,rowIndex);
	}

	/**
	 * Update tax area code for a line item
	 * @param code
	 * @param rowIndex
	 */
	protected void updateTaxAreaCode(String code, int rowIndex){
		BusinessSalesBasePage salesPage = new BusinessSalesBasePage(driver);
		salesPage.enterTaxAreaCode(code,rowIndex);
	}

	/**
	 * Update tax group code for a line item
	 * @param itemNo
	 * @param itemIndex
	 */
	protected void updateItemNumber(String itemNo, int itemIndex)
	{
		int itemNumber = itemIndex - 1;
		BusinessSalesBasePage salesPage = new BusinessSalesBasePage(driver);
		salesPage.enterItemNumber(itemNo, itemNumber);
	}
	/**
	 * clear Location code From Shipping and Billing
	 * @param locationCode
	 * @param itemIndex
	 * @author bhikshapathi
	 */
	protected void clearLocationcodeFromShippingandBilling(String locationCode, int itemIndex)
	{
		int itemNumber = itemIndex - 1;
		BusinessSalesBasePage salesPage = new BusinessSalesBasePage(driver);
		salesPage.enterLocationCode(locationCode, itemNumber);
	}
	/**
	 * open Recalculate Tax Details Window
	 * @param page
	 * @author bhikshapathi
	 */
	protected void openRecalculateTaxDetailsWindow(BusinessSalesBasePage page) {
		page.waitForPageLoad();
		try {
			page.salesEditNavMenu.clickMoreOptions();
		} catch (NoSuchElementException | TimeoutException e) {
			// if More Options has already been opened
		}
		page.salesEditNavMenu.selectActions();
		page.salesEditNavMenu.selectRecalculateTax();
	}
	/**
	 * open functions from Actions
	 * @param page
	 */
	protected void openFunctionsFromActions(BusinessSalesBasePage page) {
		page.waitForPageLoad();
		page.salesEditNavMenu.clickMoreOptionsActionsButton();
		page.salesEditNavMenu.selectFunctions();
		page.salesEditNavMenu.selectOtherFunctionsButton();

	}
	/**
	 * Modify item type then fill in the items table when creating a sales order/quote/memo/invoice
	 * @param itemType
	 * @param itemCode
	 * @param itemDescription
	 * @param locationCode
	 * @param itemQuantity
	 * @param price
	 * @param itemIndex
	 */
	protected void fillInItemsTableInfo( String itemType, String itemCode, String itemDescription, String locationCode,String itemQuantity,
										 String price, int itemIndex )
	{
		int itemNumber = itemIndex - 1;
		BusinessSalesBasePage salesPage = new BusinessSalesBasePage(driver);
		salesPage.waitForPageLoad();
		salesPage.setItemType(itemType, itemNumber);
		salesPage.enterItemNumber(itemCode, itemNumber);
		if ( null != itemDescription )
		{
			salesPage.enterItemDescription(itemDescription, itemNumber);
		}
		if (null != locationCode) {
			salesPage.enterLocationCode(locationCode, itemNumber);
		}
		salesPage.waitForPageLoad();
		salesPage.enterQuantity(itemQuantity, itemNumber);

		if ( null != price )
		{
			salesPage.enterUnitPrice(price,itemIndex);
		}
	}

	/**
	 * Change item type to Comment then fill in items table for the comment row
	 *
	 * @param itemCode
	 * @param description
	 * @param itemIndex
	 */
	protected void fillInItemsTableComment( String itemCode, String description, int itemIndex )
	{
		int itemNumber = itemIndex - 1;
		BusinessSalesBasePage salesPage = new BusinessSalesBasePage(driver);
		salesPage.setItemType("Comment", itemNumber);
		if ( null != itemCode )
		{
			salesPage.setItemNumberInfo(itemCode, itemNumber);
		}
		if ( null != description )
		{
			salesPage.enterItemDescription(description, itemNumber);
		}
	}

	/**
	 * Fills in the address when creating a customer
	 *
	 * @param addrLineOne
	 * @param addrLineTwo
	 * @param city
	 * @param state
	 * @param zipCode
	 * @param country
	 */
	protected void  fillInCustomerAddressInfo( String addrLineOne, String addrLineTwo, String city, String state,
		String zipCode, String country )
	{
		BusinessCustomerCardPage customerCardPage = new BusinessCustomerCardPage(driver);
		customerCardPage.enterAddressLineOne(addrLineOne);
		if ( null != addrLineTwo )
		{
			customerCardPage.enterAddressLineTwo(addrLineTwo);
		}
		customerCardPage.enterAddressCountry(country);
		customerCardPage.enterAddressCity(city);
		customerCardPage.enterAddressState(state);
		if(null!=zipCode) {
			customerCardPage.enterAddressZip(zipCode);
		}
	}


	/**
	 * Fills in the address when creating a customer
	 *
	 * @param custName
	 * @param addrLineTwo
	 * @param city
	 * @param state
	 * @param zipCode
	 * @param country
	 */
	protected void createNewCustomer( String custName,String addrLineOne, String addrLineTwo, String city, String state,
									  String zipCode, String country,String taxCode, String custCode )
	{
		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessCustomerCardPage customerCardPage = new BusinessCustomerCardPage(driver);
		customerCardPage.clickNew();
		customerCardPage.selectBTOBCustomer();
		customerCardPage.enterCustName(custName);
		customerCardPage.enterAddressLineOne(addrLineOne);
		if ( null != addrLineTwo )
		{
			customerCardPage.enterAddressLineTwo(addrLineTwo);
		}
		customerCardPage.enterAddressCountry(country);
		customerCardPage.enterAddressCity(city);
		customerCardPage.enterAddressState(state);
		if(null!=zipCode) {
			customerCardPage.enterAddressZip(zipCode);
		}
		customerCardPage.openInvoicingCategory();
		customerCardPage.enterTaxAreaCode(taxCode);
		customerCardPage.updateCustomerCode(custCode);
		customerCardPage.acceptTheChange();
		customerCardPage.clickBackAndSaveArrow();
	}
	/**
	 * Fills in the address when creating a customer
	 *
	 * @param custName
	 * @param addrLineTwo
	 * @param city
	 * @param state
	 * @param zipCode
	 * @param country
	 */
	protected void createNewCustomerForSecondTime( String custName,String addrLineOne, String addrLineTwo, String city, String state,
												   String zipCode, String country,String taxCode,String custCode )
	{
		BusinessCustomerCardPage customerCardPage = new BusinessCustomerCardPage(driver);
		customerCardPage.clickNew();
		customerCardPage.selectBTOBCustomer();
		customerCardPage.enterCustName(custName);
		customerCardPage.enterAddressLineOne(addrLineOne);
		if ( null != addrLineTwo )
		{
			customerCardPage.enterAddressLineTwo(addrLineTwo);
		}
		customerCardPage.enterAddressCountry(country);
		customerCardPage.enterAddressCity(city);
		customerCardPage.enterAddressState(state);
		if(null!=zipCode) {
			customerCardPage.enterAddressZip(zipCode);
		}
		customerCardPage.enterTaxAreaCode(taxCode);
		customerCardPage.updateCustomerCode(custCode);
		customerCardPage.acceptTheChange();
	}

	/**
	 * Create new Tax group code
	 *
	 * @param code
	 * @param description
	 * @author bhikshapathi
	 */
	protected void createTaxGroup(String code, String description)
	{
		BusinessCustomerCardPage customerCardPage = new BusinessCustomerCardPage(driver);
		customerCardPage.clickNew();
		customerCardPage.enterTaxGCode(code);
		customerCardPage.enterDescription(description);
	}
	/**
	 * Fills in the required info when creating a new flex field
	 *
	 * @param flexSource
	 * @param flexType
	 * @param flexId
	 * @param flexValue
	 */
	protected void fillInNewFlexFieldInfo( String flexSource, String flexType, String flexId, String flexValue )
	{
		BusinessFlexFieldsPage flexFieldsPage = new BusinessFlexFieldsPage(driver);
		flexFieldsPage.selectFlexFieldSource(flexSource);
		flexFieldsPage.selectFlexFieldType(flexType);
		flexFieldsPage.inputFlexFieldId(flexId);
		flexFieldsPage.enterFlexValue(flexSource, flexType, flexValue);
	}

	/**
	 * Copies a sales document to a credit memo, then posts the copied memo
	 *
	 * @param page
	 * @param documentType
	 * @param documentNumber
	 */
	protected void copySalesDocumentToMemo( BusinessSalesBasePage page, String documentType, String documentNumber )
	{
		page.activateRow(1);
		page.salesEditNavMenu.clickPrepareButton();
		page.salesEditNavMenu.selectCopyDocument();

		page.fillOutCopyDocumentInformation(documentType, documentNumber);
		page.dialogBoxClickOk();
	}
	/**
	 * Copies a purchase document to a credit memo
	 *
	 * @param page
	 * @param documentType
	 * @param documentNumber
	 */
	protected void copySalesDocumentInPurchaseCreditMemo( BusinessSalesBasePage page, String documentType, String documentNumber )
	{
		page.salesEditNavMenu.clickReleaseTab();
		page.salesEditNavMenu.selectCopyDocument();
		page.fillOutCopyDocumentInformation(documentType, documentNumber);
		page.dialogBoxClickOk();
	}

	/**
	 * When posting a sales order, selects the Ship and Invoice option
	 * then posts the order as an invoice
	 *
	 * @param page
	 *
	 * @return the posted invoice
	 */
	protected BusinessSalesInvoicePage salesOrderSelectShipAndInvoiceThenPost( BusinessSalesOrderPage page )
	{
		page.salesEditNavMenu.clickPostingTab();
		page.salesEditNavMenu.selectPostButton();
		page.selectShipAndInvoicePosting();

		BusinessSalesInvoicePage postedInvoice = page.goToInvoice();

		return postedInvoice;
	}

	/**
	 * Posts any sales document as an invoice
	 *
	 * @param page
	 *
	 * @return posted sales document
	 */
	protected BusinessSalesInvoicePage salesDocumentPostInvoice( BusinessSalesBasePage page )
	{
		page.salesEditNavMenu.clickPostingButton();
		page.salesEditNavMenu.selectPostButton();
		page.dialogBoxClickYes();
		BusinessSalesInvoicePage postedInvoice = page.goToInvoice();

		return postedInvoice;
	}


	/**
	 * Posts sales document
	 *
	 * @param page
	 *
	 * @return posted sales document
	 */
	protected BusinessSalesInvoicePage postOrderInvoiceWithCustomAddress(BusinessSalesOrderPage page)
	{
		page.salesEditNavMenu.clickPostingButton();
		page.salesEditNavMenu.selectPostButton();
		page.dialogBoxClickYes();
		page.selectShipAndInvoicePosting();
		BusinessSalesInvoicePage postedInvoice = page.goToInvoice();

		return postedInvoice;
	}

	/**
	 * Posts a credit memo as a memo
	 *
	 * @param page
	 *
	 * @return the posted memo
	 */
	protected BusinessSalesCreditMemoPage postCreditMemo( BusinessSalesCreditMemoPage page )
	{
		page.salesEditNavMenu.clickPostingTab();
		page.salesEditNavMenu.selectPostButton();
		page.dialogBoxClickYes();

		BusinessSalesCreditMemoPage postedMemo = page.goToPostedMemo();

		return postedMemo;
	}

	/**
	 * Posting a Sales Return Order
	 *
	 * @return Sales Credit memo
	 */
	protected BusinessSalesCreditMemoPage postSalesReturnOrder( )
	{
		BusinessSalesCreditMemoPage page = new BusinessSalesCreditMemoPage(driver);
		page.salesEditNavMenu.clickPostingTab();
		page.salesEditNavMenu.selectPostButton();
		page.dialogBoxClickOk();
		BusinessSalesCreditMemoPage postedMemo = page.goToPostedMemo();

		return postedMemo;
	}

	/**
	 * Posting a Service Order
	 *
	 * @return Service Invoice
	 */
	public BusinessServiceInvoicePage postServiceOrder() {
		BusinessServiceOrdersPage page = new BusinessServiceOrdersPage(driver);
		page.salesEditNavMenu.clickPostingTab();
		page.salesEditNavMenu.selectPostButton();
		page.dialogBoxClickOk();

		page.goToInvoice();

		return new BusinessServiceInvoicePage(driver);
	}

	/**
	 * Converts any sales document into an invoice
	 *
	 * @param page
	 *
	 * @return the converted invoice
	 */
	protected BusinessSalesInvoicePage salesDocumentMakeInvoice( BusinessSalesBasePage page )
	{   page.waitForPageLoad();
		page.salesEditNavMenu.clickProcessButton();
		page.salesEditNavMenu.selectMakeInvoiceButton();
		page.dialogBoxClickYes();

		BusinessSalesInvoicePage invoicePage = page.goToInvoice();

		return invoicePage;
	}

	/**
	 * Makes sure none of the flex field inputs are enabled on an existing flex field
	 *
	 * @param page
	 *
	 * @return true if all inputs disabled, false if not
	 */
	protected boolean checkAllFlexFieldInputsDisabled( BusinessFlexFieldsPage page )
	{
		boolean flexFieldInputsDisabled = true;
		try
		{
			page.selectFlexFieldSource("Constant Value");
			flexFieldInputsDisabled = false;
		}
		catch ( TimeoutException e )
		{

		}
		try
		{
			page.selectFlexFieldType("Text");
			flexFieldInputsDisabled = false;
		}
		catch ( TimeoutException e )
		{

		}
		try
		{
			page.inputFlexFieldId("10");
			flexFieldInputsDisabled = false;
		}
		catch ( TimeoutException e )
		{

		}
		try
		{
			page.inputFlexFieldValue("testing");
			flexFieldInputsDisabled = false;
		}
		catch ( TimeoutException e )
		{

		}

		return flexFieldInputsDisabled;
	}

	/**
	 * Fills in the general info when creating a purchase order
	 *
	 * @param purchaseOrderPage
	 * @param vendorCode
	 */
	protected void fillInPurchaseOrderGeneralInfo(BusinessPurchaseOrderPage purchaseOrderPage, String vendorCode){
		purchaseOrderPage.enterVendorCode(vendorCode);
	}

	/**
	 * Fills in the general info when creating a purchase quote
	 *
	 * @param purchaseQuotePage
	 * @param vendorCode
	 */
	protected void fillInPurchaseQuoteGeneralInfo(BusinessPurchaseQuotePage purchaseQuotePage, String vendorCode){
		purchaseQuotePage.enterVendorCode(vendorCode);
	}

	/**
	 * Fills in the general info when creating a purchase invoice
	 *
	 * @param purchaseInvoicePage
	 * @param vendorCode
	 */
	protected void fillInPurchaseInvoiceGeneralInfo(BusinessPurchaseInvoicePage purchaseInvoicePage, String vendorCode){
		purchaseInvoicePage.enterVendorCode(vendorCode);
			}
	/**
	 * Fills in the general info when creating a purchase credit memo
	 *
	 * @param purchaseCreditMemoPage
	 * @param vendorCode
	 */
	protected void fillInPurchaseCreditMemoGeneralInfo(BusinessPurchaseCreditMemoPage purchaseCreditMemoPage, String vendorCode){
		purchaseCreditMemoPage.enterVendorCode(vendorCode);
	}

	/**
	 * Fills in the general info when creating a purchase return order
	 *
	 * @param purchaseReturnOrderPage
	 * @param vendorCode
	 */
	protected void fillInPurchaseReturnOrderInfo(BusinessPurchaseReturnOrderPage purchaseReturnOrderPage, String vendorCode){
		purchaseReturnOrderPage.enterVendorCode(vendorCode);
	}

	/**
	 * Fills in the address when creating a Vendor
	 *
	 * @param addressLineOne
	 * @param addrLineTwo
	 * @param city
	 * @param state
	 * @param zipCode
	 * @param country
	 */
	protected void fillInVendorAddressInfo(String addressLineOne, String addrLineTwo, String city, String state,
										   String zipCode, String country){
		BusinessVendorPage vendorPage=new BusinessVendorPage(driver);
		vendorPage.enterAddressLineOne(addressLineOne);
		if(null != addrLineTwo){
			vendorPage.enterAddressLineTwo(addrLineTwo);
		}
		vendorPage.enterCountry(country);
		vendorPage.enterCity(city);
		vendorPage.enterStateInAddress(state);
		vendorPage.enterZip(zipCode);
	}

	/**
	 * Posts any sales document as an invoice
	 *
	 * @param page
	 *
	 * @return posted sales document
	 */
	protected BusinessPurchaseInvoicePage salesDocumentPostPurchaseInvoice( BusinessSalesBasePage page ) {
		page.salesEditNavMenu.clickPostingButton();
		page.salesEditNavMenu.selectPostButton();
		page.dialogBoxClickYes();
		BusinessPurchaseInvoicePage postedInvoice = page.goToPostedPurchaseInvoice();
		return postedInvoice;
	}
	/**
	 * Posts a credit memo as a memo
	 *
	 * @param page
	 *
	 * @return the posted memo
	 */
	protected BusinessPurchaseCreditMemoPage postPurchaseCreditMemo( BusinessPurchaseCreditMemoPage page )
	{
		page.salesEditNavMenu.clickPostingTab();
		page.salesEditNavMenu.selectPostButton();
		page.dialogBoxClickYes();

		BusinessPurchaseCreditMemoPage postedMemo = page.goToPostedPurchaseCreditMemo();

		return postedMemo;
	}
	/**
	 * then posts the purchase order as an invoice
	 * @param page
	 * @return the posted purchase invoice
	 */
	protected  BusinessPurchaseInvoicePage purchaseOrderPostTheInvoice(BusinessPurchaseOrderPage page){
		page.salesEditNavMenu.clickPostingTab();
		page.salesEditNavMenu.selectPostButton();
		page.dialogBoxClickOk();
		BusinessPurchaseInvoicePage postedPurchaseInvoice=page.goToPostedPurchaseInvoice();
		return postedPurchaseInvoice;
	}
	/**
	 * Fills in the general info when creating a service quote
	 *
	 * @param serviceQuote
	 * @param customerCode
	 */
	protected void fillInServiceQuotesGeneralInfo(BusinessServiceQuotesPage serviceQuote, String customerCode){
		serviceQuote.enterCustomerNo(customerCode);
	}
	/**
	 * Fills in the general info when creating a service order
	 *
	 * @param serviceOrder
	 * @param customerCode
	 */
	protected void fillInServiceOrdersGeneralInfo(BusinessServiceOrdersPage serviceOrder, String customerCode){
		serviceOrder.enterCustomerNo(customerCode);
	}

	/**
	 * Fills in the general info when creating a service credit memo
	 *
	 * @param serviceCreditMemo
	 * @param customerCode
	 */
	protected void fillInServiceCreditMemosGeneralInfo(BusinessServiceCreditMemoPage serviceCreditMemo, String customerCode){
		serviceCreditMemo.enterCustomerNo(customerCode);
	}

	/**
	 * Opens the vertex tax details window on service Mgmt
	 *
	 * @param page
	 */
	protected void serviceMgmtVertexTaxDetails(BusinessSalesBasePage page) {
		page.salesEditNavMenu.serviceMgmtClickMoreOptions();
		page.salesEditNavMenu.selectRelated();
		page.salesEditNavMenu.selectVertexTaxDetails();
	}
	/**
	 * Opens the vertex tax details window on service Mgmt
	 *
	 * @param page
	 */
	protected void serviceMgmtRecalculateTax(BusinessSalesBasePage page) {
		page.salesEditNavMenu.serviceMgmtClickMoreOptions();
		page.salesEditNavMenu.selectActions();
		page.salesEditNavMenu.selectRecalculateTax();
	}

	/**
	 * Clicks the Post, then posting, selects an option, clicks ok, selects yes
	 *
	 * @param page
	 * @param postingOption
	 */
	protected void postDocumentAndSelectPostingOption(BusinessSalesBasePage page, String postingOption) {
		page.waitForPageLoad();
		try {
			page.salesEditNavMenu.clickPostingTab();
		} catch (NoSuchElementException | TimeoutException e) {

		}
		try {
			page.salesEditNavMenu.clickPostButton();
		} catch (NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
			// if More Options has already been opened
		}
		page.salesEditNavMenu.selectPostingOption(postingOption);
	}

	BusinessAdminHomePage homePage;
	BusinessVertexAdminPage adminPage;
	BusinessVertexAdminConfigurationPage configPage;
    @BeforeMethod(alwaysRun = true)
    public void setupTest( )
    {
		homePage = new BusinessAdminHomePage(driver);
        adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        configPage = new BusinessVertexAdminConfigurationPage(driver);

           adminPage.openXmlLogAdmin();
           adminPage.updateLogARTaxRequestToOn();
           adminPage.updateLogARTaxResponseOn();
           adminPage.updateLogARInvoiceRequestOn();
           adminPage.updateLogARInvoiceResponseOn();
           adminPage.updateLogAPTaxRequestToOn();
           adminPage.updateLogAPTaxResponseOn();
           adminPage.updateLogAPInvoiceRequestOn();
           adminPage.updateLogAPInvoiceResponseOn();
           configPage.clickBackAndSaveArrow();
    }
	}