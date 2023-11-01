package com.vertex.quality.connectors.dynamics365.finance.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinanceBasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

/**
 * All purchase orders page common methods and object declaration page
 *
 * @author Shiva Mothkula
 */
public class DFinanceOpenVendorInvoicesPage extends DFinanceBasePage
{
	protected By SEARCH_INPUT = By.cssSelector(
		"input[name='QuickFilterControl_Input'][id*='vendopeninvoiceslistpage']");
	protected By VOUCHER_TAB = By.xpath("//button[contains(@data-dyn-controlname, 'LedgerTransact')]");
	protected By SALES_TAX_CODE = By.cssSelector("input[aria-label*='Sales tax code']");
	protected By SELECT_INVOICE_FROM_DROPDOWN = By.xpath("(//li[contains(@class, 'quickFilter-listItem')])[1]");
	protected By ACCOUNT_NAME = By.cssSelector("input[aria-label*='Account name']");
	protected By NEW_INVOICE = By.name("NewInvoice");
	protected By VENDOR_ACCOUNT = By.name("PurchParmTable_InvoiceAccount");
	protected By LINES_LINK = By.cssSelector("[for*='SystemDefinedDetailsTransactionRadio_option_0']");
	protected By ADD_NEW_LINE_VENDOR_INVOICE = By.cssSelector("[id$='MenuItemButtonAdd_label']");
	protected By ADD_NEW_LINE = By.cssSelector("[id$='LineStripNew_label']");
	protected By RELATED_PURCHASE_ORDER = By.xpath("//input[contains(@id, 'PurchParmTable_PurchId_input')]");
	protected By REFRESH_LINK = By.xpath("//*[contains(@id,\"RefreshButton\")]/div/span[1]");

	public DFinanceOpenVendorInvoicesPage( WebDriver driver )
	{
		super(driver);
	}

	public boolean searchVendorInvoice(String invoiceType, String invoiceNumber)
	{
		WebElement filterEle = wait.waitForElementDisplayed(By.cssSelector("input[name='QuickFilterControl_Input'][id*='"+invoiceType+"']"));
		text.setTextFieldCarefully(filterEle, invoiceNumber + Keys.ENTER,false);
		waitForPageLoad();

		By searchResultBy = getInvoiceNumberBy(invoiceNumber);
		boolean result = element.isElementDisplayed(searchResultBy);
		filterEle.sendKeys(Keys.ENTER);
		waitForPageLoad();

		return result;
	}

	public void clickInvoiceNumber( String invoiceNumber )
	{
		By invoiceNumberBy = getInvoiceNumberBy(invoiceNumber);

		if(element.isElementDisplayed(SELECT_INVOICE_FROM_DROPDOWN)){
			click.clickElementCarefully(SELECT_INVOICE_FROM_DROPDOWN);
		}

		WebElement invoiceNumberField = wait.waitForElementEnabled(invoiceNumberBy);

		String keysPressed = Keys.chord(Keys.CONTROL, Keys.RETURN, Keys.ENTER);
		invoiceNumberField.sendKeys(keysPressed);
		waitForPageLoad();

	}

	private By getInvoiceNumberBy( String invoiceNumber )
	{
		By invoiceNumberBy = By.cssSelector(String.format("[title*='%s']", invoiceNumber));
		return invoiceNumberBy;
	}

	public void clickVouchersTab( )
	{
		wait.waitForElementDisplayed(VOUCHER_TAB);
		click.clickElement(VOUCHER_TAB);
		waitForGridToLoad();
	}

	/**
	 * Clicks the Posted Sales Tax button
	 * @param buttonLocation
	 */
	public void clickPostedSalesTax(String buttonLocation)
	{
		driver.manage().window().maximize();
		WebElement clickPostedSalesTax = wait.waitForElementEnabled(By.xpath("//*[@data-dyn-controlname='TaxTransactions']/.//span[text()='Posted sales tax']"));
		wait.waitForElementDisplayed(clickPostedSalesTax);
		try{
			click.clickElementCarefully(clickPostedSalesTax);
		}catch (Exception e){
			click.javascriptClick(clickPostedSalesTax);
		}
		driver.manage().window().setSize(new Dimension(1024,768));
		waitForPageLoad();
	}

	/**
	 * allow page to refresh until the Grid has loaded to display based on F&O processes
	 */
	public void waitForGridToLoad()
	{
		waitForPageLoad();

		By NoElementDisplayed = By.xpath("//*[contains(@id,\"EmptyGridIcon\")]/div");
		int i = 0;
		while(element.isElementDisplayed(NoElementDisplayed) && i<360)
		{
			jsWaiter.sleep(10000);
			click.javascriptClick(REFRESH_LINK);
			i++;
		}
		if (element.isElementDisplayed(NoElementDisplayed)){
			VertexLogger.log("Grid still not loaded");
		}
	}

	/**
	 * Gets the sales tax code value
	 * @param salesTaxCodeLine
	 * @return
	 */
	public String getSalesTaxCode(int salesTaxCodeLine)
	{
		List<WebElement> rows = wait.waitForAllElementsPresent(SALES_TAX_CODE);
		WebElement salesTaxCodeEle = rows.get(salesTaxCodeLine);
		String salesTaxCode = attribute.getElementAttribute(salesTaxCodeEle, "value");
		if ( salesTaxCode != null )
		{
			salesTaxCode = salesTaxCode.trim();
		}
		return salesTaxCode;
	}

	/**
	 * Finds the Account Name Type of a voucher
	 * @param accountName
	 * @param accountInCurrency
	 * @return - a string of the the Account Name Type value
	 */
	public String getAccountNameValue(String accountName, String accountInCurrency)
	{
		String accountNameType = attribute.getElementAttribute(By.xpath("(//input[@title='"+accountName+"']//..//..//input[@title='"+accountInCurrency+"'])[1]"), "title");

		if ( accountNameType != null )
		{
			accountNameType = accountNameType.trim();
		}
		return accountNameType;
	}

	/**
	 * Clicks to create a new invoice and selects the type of invoice
	 * @param invoiceType
	 */
	public void createNewInvoice(String invoiceType){
		wait.waitForElementDisplayed(NEW_INVOICE);
		click.clickElementCarefully(NEW_INVOICE);

		WebElement invoiceTypeSelected = wait.waitForElementDisplayed(By.xpath("//button[@name='"+invoiceType+"']"));
		click.clickElementCarefully(invoiceTypeSelected);
	}

	/**
	 * Enters the vendor account
	 * @param vendorAccount
	 */
	public void setVendorInvoiceVendorAccountNumber(String vendorAccount){
		wait.waitForElementDisplayed(VENDOR_ACCOUNT);
		text.enterText(VENDOR_ACCOUNT, vendorAccount + Keys.TAB);
	}

	/**
	 * Add a line item to the Vendor Invoice
	 *
	 * @param item
	 * @param qty
	 * @param site
	 * @param warehouse
	 */
	public void addItemLine( String item, String qty, String site, String warehouse,int itemIndex)
	{
		waitForPageLoad();
		if (element.isElementDisplayed(LINES_LINK))
			click.javascriptClick(LINES_LINK);
		wait.waitForElementDisplayed(ADD_NEW_LINE_VENDOR_INVOICE);
		click.javascriptClick(ADD_NEW_LINE_VENDOR_INVOICE);
		int itemNumber = itemIndex + 2;
		itemIndex = itemIndex+1;
		DFinanceBasePage purchasePage = new DFinanceBasePage(driver);
		purchasePage.enterItem(item, itemIndex);
		purchasePage.enterQuantity(qty, itemNumber);
		purchasePage.enterSite(site, itemNumber);
		purchasePage.enterWarehouse(warehouse, itemNumber);
	}

	/**
	 * Enter the PO connected to the Open Vendor Invoice
	 * @param purchaseOrderNum
	 */
	public void enterRelatedPurchaseOrderNumber(String purchaseOrderNum){
		WebElement relatedPurchaseOrder = wait.waitForElementDisplayed(RELATED_PURCHASE_ORDER);
		text.clickElementAndEnterText(RELATED_PURCHASE_ORDER, purchaseOrderNum);
		relatedPurchaseOrder.sendKeys(Keys.TAB);
	}
}
