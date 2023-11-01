package com.vertex.quality.connectors.dynamics365.finance.pages;

import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceLeftMenuNames;
import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinancePostSignOnPage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Home page common methods and object declaration page
 *
 * @author Saidulu Kodadala ssalisbu sgupta
 */
public class DFinanceHomePage extends DFinancePostSignOnPage
{
	protected By EXPANDED_MODULE = By.className("CaretDownRight-symbol");
	protected By MODULES = By.cssSelector("[oldtitle='Modules']");
	protected By MODULES_DROPDOWN = By.cssSelector("[title='Modules'][class*='CaretDownRight-symbol']");
	protected String MODULES_OPTION = ("[class*='modulesPane-module'][data-dyn-title='%s']");
	protected String MODULES_OPTION_SELECTED = ("[data-dyn-title='%s']");
	protected final By modulePanelCategory = By.className("modulesFlyout-LinkGroup");
	protected final By modulePanelLink = By.cssSelector("[class*='modulesFlyout-linkText']");
	protected final By  WORKSPACE_PANEL_LINK = By.cssSelector("[class*='tile-text'][aria-hidden]");
	protected By COLLAPSE_ALL = By.className("modulesFlyout-CollapseAll");

	protected By CUSTOMER_HEADER = By.cssSelector("[id$=CustomerTab_header]");
	protected By TEXT_CUSTOMER_ACCOUNT = By.name("SalesTable_CustAccount");
	public By DELIVERY_NAME = By.name("SalesTable_DeliveryName");
	public By DELIVERY_ADDRESS = By.name("LogisticsPostalAddress_Address");
	public By ORDER_TYPE = By.name("SalesTable_CurrencyCode");
	protected String SALES_ORDER_MENU = "//form[@data-dyn-form-name='SalesTable']//span[text()='%S']";
	public By SELL_BUTTON = By.cssSelector("[id$=Sell_button]");
	public By HEADER_BUTTON = By.className("radioButton-label");
	public By GENERAL_HEADER = By.className("section-page-header");
	public By PRICE_AND_DISCOUNT_HEADER = By.cssSelector("[id$=TabHeaderPriceCalc_header]");
	public By CUSTOMER_ACCOUNT = By.name("CustAccount");
	public By HEADER_CURRENCY = By.name("Currency_CurrencyCode");
	public By ADDRESS_HEADER = By.cssSelector("[id$=TabHeaderAddress_header]");
	public By HEADER_DELIVERY = By.name("ReferenceGroup_Location_Description");
	public By HEADER_ADDRESS = By.name("LogisticsPostalAddressDeliveryHeader_Address1");
	protected By CANCEL_BUTTON = By.name("Cancel");
	protected By CANCEL_BUTTON_ADDRESS = By.name("CancelButton");
	protected By DELETE_BUTTON = By.id("salestablelistpage_2_SystemDefinedDeleteButton_label");
	protected By YES_BUTTON = By.name("Yes");
	protected By MORE_OPTIONS = By.cssSelector("button[name=\"MoreOptions\"]");
	protected By REMOVE = By.xpath("//button[@*='Remove'][not(@*='disabled')]");
	protected By CUSTOMER_SEARCH_BOX = By.name("QuickFilterControl_Input");
	protected By SELECT_ACCOUNT = By.xpath("//li[@class='quickFilter-listItem flyout-menuItem'][1]");
	protected By CLICK_CUSTOMER_NAME = By.xpath("//input[contains(@id, 'CustTable_Name')]");
	protected By CUSTOMER_DROPDOWN = By.cssSelector("[data-dyn-index*='0']");
	public By ADDRESS_HEADER_CUSTOMER_PAGE = By.cssSelector("[id*=TabAddress_header]");
	protected By LOCATION_NAME = By.xpath("//input[contains(@id, 'LocationDescription')]");
	protected By ADDRESS_TAB = By.xpath("//div[@data-dyn-controlname='TabAddress']/div[@role='button']");
	protected By ADDRESS_DESCRIPTION = By.xpath("//textarea[@aria-label='Address']");
	protected By FILTER_SEARCH_BOX = By.xpath("//input[@name='QuickFilterControl_Input']");
	protected By All_Sales_Order = By.cssSelector(
		"[data-dyn-title='All sales orders'][class*='modulesFlyout-linkText']");
	protected By All_Custmers = By.cssSelector("[data-dyn-title='All customers'][class*='modulesFlyout-linkText']");
	Actions action = new Actions(driver);
	public DFinanceHomePage( WebDriver driver )
	{
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Navigate to modules menu options
	 *
	 * @param leftMenu
	 */
	public void selectLeftMenuModule( final String leftMenu )
	{
		waitForPageLoad();
		navPanel.expandLeftMenuModuleGroup(defaultLeftMenuModuleGroup);
		By OPTION;
		try {
			OPTION = By.cssSelector(String.format(MODULES_OPTION, leftMenu));
		}
		catch(Exception ex)
		{
			OPTION = By.cssSelector(String.format(MODULES_OPTION_SELECTED, leftMenu));
		}
		wait.waitForElementDisplayed(driver.findElement(OPTION));

		String classAttribute = attribute.getElementAttribute(OPTION, "class");

		if ( !classAttribute.contains("isActive") )
		{
			click.javascriptClick(OPTION);
		}
	}

	/**
	 * Navigate to modules menu options AND select collapse all
	 */
	public void collapseAll()
	{
		wait.waitForElementDisplayed(COLLAPSE_ALL);
		click.clickElement(COLLAPSE_ALL);
	}

	/**
	 * expand module category
	 *
	 * @param string module category to expand
	 */
	public void expandModuleCategory( String string )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(modulePanelCategory);
		List<WebElement> modules = wait.waitForAllElementsPresent(modulePanelCategory);
		int k = 0;
		int numFinal = 0;
		for ( WebElement module : modules )
		{
			if ( attribute
					 .getElementAttribute(module, "data-dyn-title")
					 .equals(string) && module
					 .getAttribute("aria-expanded")
					 .equals("false") )
			{
				numFinal = k;
				click.clickElementCarefully(modules.get(numFinal));
				break;
			}
			k++;
		}
	}

	/**
	 * All sales order link
	 */
	public void clickOnAllCustomers( )
	{
		wait.waitForElementDisplayed(All_Custmers);
		click.clickElement(All_Custmers);
	}

	/**
	 * expand module tab
	 *
	 * @param pageLink module tab to expand
	 */
	public DFinanceSettingsPage selectWorkSpaceTabLink( String pageLink )
	{
		wait.waitForAllElementsPresent(WORKSPACE_PANEL_LINK);

		click.clickElementCarefully(By.xpath("//span[text()='"+pageLink+"']"));
		DFinanceSettingsPage settingPage = new DFinanceSettingsPage(driver);
		return settingPage;
	}

	/**
	 * Selects a link
	 * @param
	 */
	public DFinanceSettingsPage selectModuleTabLink( String string )
	{
		List<WebElement> modules = wait.waitForAllElementsPresent(modulePanelLink);
		waitForPageLoad();//todo
		int k = 0;
		int numFinal = 0;
		for ( WebElement module : modules )
		{
			if ( module
					.getAttribute("data-dyn-title")
					.equals(string) )
			{
				numFinal = k;
				break;
			}
			k++;
		}
		click.clickElementCarefully(modules.get(numFinal));//break this up if goesstale
		DFinanceSettingsPage settingPage = new DFinanceSettingsPage(driver);
		return settingPage;
	}

	//fixme make sense of this

	/**
	 * adds new address
	 *
	 * @param string customer account
	 *
	 * @return the resulting Sales Order page
	 */
	public DFinanceAllSalesOrdersPage newAddress( String string )
	{
		click.clickElement(NEW_BUTTON);
		String accountSwitches = String.format("%s\t", string);
		wait.waitForElementDisplayed(TEXT_CUSTOMER_ACCOUNT);
		this.typeInLocator(TEXT_CUSTOMER_ACCOUNT, accountSwitches);
		DFinanceAllSalesOrdersPage salesOrderPage = new DFinanceAllSalesOrdersPage(driver);
		return salesOrderPage;
	}

	/**
	 * expands header if not expanded.
	 *
	 * @param headerBy to search for web element header
	 */
	public void expandHeader( By headerBy )
	{
		waitForPageLoad();
		WebElement CUSTOMER_HEADER = driver.findElement(headerBy);
		if ( CUSTOMER_HEADER
			.getAttribute("aria-expanded")
			.equals("false") )
		{
			click.clickElement(CUSTOMER_HEADER);
		}
	}

	/**
	 * type content into web element on screen, after clearing
	 *
	 * @param locator web element to type to
	 * @param type    string to write in locator
	 */
	public void typeInLocator( By locator, String type )
	{
		text.enterText(locator, type);
		waitForPageLoad();
	}

	/**
	 * gets attribute from web element
	 *
	 * @param 'locator'   locator to search for web elements
	 * @param 'attribute' attribute to find
	 *
	 * @return returns attribute found
	 */
	public String getAttribute( By locator, String contentType )
	{
		wait.waitForElementDisplayed(driver.findElement(locator));
		String orderType = attribute.getElementAttribute(locator, "value");
		return orderType;
	}

	/**
	 * click specific element from array of values element found by locator
	 *
	 * @param 'locator' to search for web elements
	 * @param 'num'     specifies web element that is clicked
	 */
	public void clickElementNum( By locator, int num )
	{
		List<WebElement> button = driver.findElements(locator);
		click.clickElement(button.get(num));
		waitForPageLoad();
	}

	/**
	 * Click on cancel button
	 */
	public void clickOnCancelButton( )
	{
		wait.waitForElementDisplayed(driver.findElement(CANCEL_BUTTON));
		wait.waitForElementEnabled(CANCEL_BUTTON);
		click.clickElement(CANCEL_BUTTON);
	}

	/**
	 * Cancel address button
	 */
	public void clickOnCancelAddressButton( )
	{
		wait.waitForElementDisplayed(driver.findElement(CANCEL_BUTTON_ADDRESS));
		wait.waitForElementEnabled(CANCEL_BUTTON_ADDRESS);
		click.clickElement(CANCEL_BUTTON_ADDRESS);
	}

	/**
	 * All sales order link
	 */
	public void clickOnAllSalesOrder( )
	{
		wait.waitForElementDisplayed(All_Sales_Order);
		click.clickElement(All_Sales_Order);
	}

	/**
	 * Click on delete button
	 */
	public void clickDeleteButton( )
	{
		wait.waitForElementDisplayed(DELETE_BUTTON);
		wait.waitForElementEnabled(DELETE_BUTTON);
		click.clickElement(DELETE_BUTTON);
	}

	/**
	 * Click on remove button
	 *
	 * @return the resulting Sales Order page
	 */
	public DFinanceAllSalesOrdersPage clickOnRemove( )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(REMOVE);
		click.clickElement(REMOVE);
		DFinanceAllSalesOrdersPage salesOrders = new DFinanceAllSalesOrdersPage(driver);
		return salesOrders;
	}

	/**
	 * Click on more options
	 */
	public void clickOnMoreOptions( )
	{
		waitForPageLoad();
		WebElement moreOptions = wait.waitForElementEnabled(MORE_OPTIONS);
		action.click(moreOptions).perform();
	}

	/**
	 * Enter customer account
	 *
	 * @param customerAccount
	 */
	public void searchUsedCustomerAccount( String customerAccount )
	{
		waitForPageLoad();
		WebElement allCustomer = wait.waitForElementDisplayed(CUSTOMER_SEARCH_BOX);
		text.enterText(CUSTOMER_SEARCH_BOX, customerAccount);
		wait.waitForElementDisplayed(SELECT_ACCOUNT);
		click.clickElement(SELECT_ACCOUNT);
		//	allCustomer.sendKeys(Keys.ENTER);
		waitForPageLoad();
		WebElement element2 = driver.findElement(CLICK_CUSTOMER_NAME);
		String keysPressed = Keys.chord(Keys.CONTROL, Keys.RETURN);
		element2.sendKeys(keysPressed);
		waitForPageLoad();
	}

	/**
	 * find address based on location and address description
	 *
	 * @param 'location'   value to search location
	 * @param 'addressDes' value to search for address description
	 */
	public void findAddress( String location, String addresses ) {
		waitForPageLoad();
		String addressLocation = String.format("//input[@value='%s']",location);
		WebElement locationEle = wait.waitForElementPresent(By.xpath(addressLocation));
		click.moveToElementAndClick(locationEle);
		action.click(locationEle).perform();
		List<WebElement> name = element.getWebElements(LOCATION_NAME);
		List<WebElement> address = element.getWebElements(ADDRESS_DESCRIPTION);
		int i = 0;
		for ( WebElement find : name )
		{
			System.out.println(find
					.getAttribute("value"));
			if ( find
				.getAttribute("value")
				.equals(location) )
			{
				if ( address
					.get(i)
					.getAttribute("title")
					.equals(addresses) )
				{
					try
					{
						Thread.sleep(1000);
					}
					catch ( InterruptedException e )
					{
						e.printStackTrace();
					}
					action.click(address.get(i)).perform();
				}
			}
			i++;
		}
	}

	//fixme check whether this should be in this class

	/**
	 * Enter created sales order
	 *
	 * @param salesOrder
	 *
	 * @return
	 */
	public DFinanceAllSalesOrdersPage searchCreatedSalesOrder( String salesOrder )
	{
		waitForPageLoad();
		WebElement filterSearchBox = wait.waitForElementDisplayed(FILTER_SEARCH_BOX);
		final WebDriverWait shortWait = new WebDriverWait(driver, 15);
		shortWait
				.ignoring(InvalidElementStateException.class)
				.until(ExpectedConditions.elementToBeClickable(filterSearchBox));
		text.enterText(filterSearchBox, salesOrder);
		DFinanceAllSalesOrdersPage salesOrderPage = new DFinanceAllSalesOrdersPage(driver);
		return salesOrderPage;
	}

	/**
	 * Navigate to Accounts Payables --> Purchase Orders --> All Purchase Order page
	 *
	 * @return
	 */
	public DFinanceAllPurchaseOrdersPage navigateToAllPurchaseOrdersPage( )
	{
		String accountsPayables = DFinanceLeftMenuNames.ACCOUNTS_PAYABLES.getData();
		String purchaseOrders = DFinanceLeftMenuNames.PURCHASE_ORDERS.getData();
		String allPurchaseOrders = DFinanceLeftMenuNames.ALL_PURCHASE_ORDERS.getData();

		// Navigate to Accounts Payables --> Purchase Orders --> All Purchase Order page
		navigateToPageOfModule( accountsPayables, purchaseOrders, allPurchaseOrders);

		DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = initializePageObject(DFinanceAllPurchaseOrdersPage.class);

		return allPurchaseOrdersPage;
	}

	/**
	 * Navigate to Accounts Payables --> Sales Orders --> All Sales Order page
	 *
	 * @return
	 */
	public DFinanceAllSalesOrdersPage navigateToAllSalesOrdersPage( )
	{
		String accountReceivable = DFinanceLeftMenuNames.ACCOUNTS_RECEIVABLE.getData();
		String accountsPayables = DFinanceLeftMenuNames.ACCOUNTS_RECEIVABLE.getData();
		String orders = DFinanceLeftMenuNames.ORDERS.getData();
		String allPurchaseOrders = DFinanceLeftMenuNames.ALL_SALES_ORDERS.getData();

		// Navigate to Accounts Payables --> Purchase Orders --> All Purchase Order page
		clickOnNavigationPanel();
		selectLeftMenuModule(accountReceivable);
		selectLeftMenuModule(accountsPayables);
		expandModuleCategory(orders);
		selectModuleTabLink(allPurchaseOrders);

		DFinanceAllSalesOrdersPage allSalesOrdersPage = initializePageObject(DFinanceAllSalesOrdersPage.class);

		return allSalesOrdersPage;
	}
	/**
	 * Navigate to Accounts Payables --> Invoices --> Open Vendor Invoices page
	 *
	 * @return
	 */
	public DFinanceOpenVendorInvoicesPage navigateToOpenVendorInvoicesPage( )
	{
		String accountsPayables = DFinanceLeftMenuNames.ACCOUNTS_PAYABLES.getData();
		String invoices = DFinanceLeftMenuNames.INVOICES.getData();
		String openVendorInvoices = DFinanceLeftMenuNames.OPEN_VENDOR_INVOICES.getData();

		// Navigate to Accounts Payables --> Invoices --> Open Vendor Invoices page
		navigateToPageOfModule(accountsPayables, invoices, openVendorInvoices);

		DFinanceOpenVendorInvoicesPage openVendorInvoicesPage = initializePageObject(
			DFinanceOpenVendorInvoicesPage.class);

		return openVendorInvoicesPage;
	}

	/**
	 * Navigate to Accounts Payables --> Invoices --> Pending Vendor Invoices page
	 *
	 * @return
	 */
	public DFinanceOpenVendorInvoicesPage navigateToPendingVendorInvoicesPage( )
	{
		String accountsPayables = DFinanceLeftMenuNames.ACCOUNTS_PAYABLES.getData();
		String invoices = DFinanceLeftMenuNames.INVOICES.getData();
		String pendingVendorInvoices = DFinanceLeftMenuNames.PENDING_VENDOR_INVOICES.getData();

		// Navigate to Accounts Payables --> Invoices --> Pending Vendor Invoices page
		navigateToPageOfModule(accountsPayables, invoices, pendingVendorInvoices);

		DFinanceOpenVendorInvoicesPage pendingVendorInvoicesPage = initializePageObject(
				DFinanceOpenVendorInvoicesPage.class);

		return pendingVendorInvoicesPage;
	}

	/**
	 * Navigate to Accounts Payables --> Invoices --> Accounts Payable Workflows page
	 *
	 * @return
	 */
	public DFinanceCreatePurchaseOrderPage navigateToAccountsPayableWorkflowsPage( )
	{
		String accountsPayables = DFinanceLeftMenuNames.ACCOUNTS_PAYABLES.getData();
		String setup = DFinanceLeftMenuNames.SETUP.getData();
		String accountsPayableWorkflows = DFinanceLeftMenuNames.ACCOUNTS_PAYABLE_WORKFLOWS.getData();

		// Navigate to Accounts Payables --> Setup --> Accounts Payable Workflows
		navigateToPageOfModule(accountsPayables, setup, accountsPayableWorkflows);

		DFinanceCreatePurchaseOrderPage accountsPayableWorkflowsPage = initializePageObject(
				DFinanceCreatePurchaseOrderPage.class);

		return accountsPayableWorkflowsPage;
	}

	/**
	 * Naviage to <module> --> <subModule> --> <page>
	 *
	 * @param module
	 * @param subModule
	 * @param page
	 */
	public void navigateToPageOfModule( String module, String subModule, String page )
	{
		clickOnNavigationPanel();
		selectLeftMenuModule(module);
		collapseAll();
		expandModuleCategory(subModule);
		if(page.contains("Vendor invoice entry") || page.contains("Vendor payments")){
			selectWorkSpaceTabLink(page);
		}else {
			selectModuleTabLink(page);
		}
		waitForPageLoad();
	}

	/**
	 * Navigate to Accounts Sales & Marketing --> Customers --> All Customers page
	 *
	 * @return
	 */
	public DFinanceAllCustomersPage navigateToAllCustomersPage( )
	{
		String salesAndMarketing = DFinanceLeftMenuNames.SALES_AND_MARKETING.getData();
		String customers = DFinanceLeftMenuNames.CUSTOMERS.getData();
		String allCustomers = DFinanceLeftMenuNames.ALL_CUSTOMERS.getData();

		// Navigate to Accounts Sales & Marketing --> Customers --> All Customers page
		navigateToPageOfModule(salesAndMarketing, customers, allCustomers);

		DFinanceAllCustomersPage allCustomersPage = initializePageObject(DFinanceAllCustomersPage.class);

		return allCustomersPage;
	}

	/**
	 * Navigate to Accounts payable --> Vendors --> All Vendors page
	 *
	 * @return
	 */
	public DFinanceAllVendorsPage navigateToAllVendorsPage( )
	{
		String accountsPayable = DFinanceLeftMenuNames.ACCOUNTS_PAYABLES.getData();
		String vendors = DFinanceLeftMenuNames.VENDORS.getData();
		String allVendors = DFinanceLeftMenuNames.ALL_VENDORS.getData();

		navigateToPageOfModule(accountsPayable, vendors, allVendors);

		DFinanceAllVendorsPage allVendorsPage = initializePageObject(DFinanceAllVendorsPage.class);

		return allVendorsPage;
	}

	/**
	 * Navigate to Products Information Management --> Products --> Released Products page
	 *
	 * @return
	 */
	public DFinanceReleasedProductsPage navigateToReleasedProductsPage( )
	{
		String accountReceivable = DFinanceLeftMenuNames.PRODUCT_INFORMATION_MANAGEMENT.getData();
		String orders = DFinanceLeftMenuNames.PRODUCTS.getData();
		String allSalesOrders = DFinanceLeftMenuNames.RELEASED_PRODUCTS.getData();

		// Navigate to Products Information Management --> Products --> Released Products page
		navigateToPageOfModule(accountReceivable, orders, allSalesOrders);

		DFinanceReleasedProductsPage releasedProductsPage = initializePageObject(DFinanceReleasedProductsPage.class);

		return releasedProductsPage;
	}
}
