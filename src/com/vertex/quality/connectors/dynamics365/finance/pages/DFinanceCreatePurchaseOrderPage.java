package com.vertex.quality.connectors.dynamics365.finance.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinanceBasePage;

import com.vertex.quality.connectors.salesforce.enums.NavigateMenu;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create Purchase Order page common methods and object declaration page
 *
 * @author Shiva Mothkula
 */
public class DFinanceCreatePurchaseOrderPage extends DFinanceBasePage
{
	protected By OK_BUTTON = By.cssSelector("[type='button'][name*='OK']");
	protected By HEADER_LINK = By.cssSelector("[for*='SystemDefinedDetailsTransactionRadio_option_1']");
	protected By HEADER_LINK_NEW = By.xpath("//li[@data-dyn-controlname='HeaderView_header']");
	protected By HEADER_ELLIPSIS = By.xpath("(//div[@class='appBar-toolbar']//div[@title='More'])[2]");
	protected By VENDOR_INVOICE_HEADER_LINK = By.xpath("(//label[contains(@for,'SystemDefinedDetailsTransactionRadio_option_1')])[2]");
	protected By SAVE_BUTTON = By.name("SystemDefinedSaveButton");
	protected By SAVE_CHARGES = By.xpath("//form[@data-dyn-form-name='MarkupTrans']//button[@name='SystemDefinedSaveButton']");

	protected By VENDOR_HEADING = By.cssSelector("header[id*=VendorTab_header]");
	protected By VENDOR_ACCOUNT_INPUT = By.cssSelector("input[id*='OrderAccount'][id*='PurchCreateOrder']");
	protected By INVOICE_ACCOUNT_INPUT = By.name("PurchParmTable_InvoiceAccount");
	protected By VENDOR_CONTACT_INPUT = By.cssSelector("input[id*='ContactPersonName'][id*='PurchCreateOrder']");
	protected By VENDOR_DELIVERY_NAME = By.cssSelector("textarea[id*='DeliveryName'][role='textbox']");
	protected By POSTAL_ADDRESS_TEXT_AREA = By.cssSelector(
		"textarea[id*='PostalAddress'][id*='PurchCreateOrder'][role='textbox']");

	protected By PURCHASE_ORDER_NUMBER_INPUT = By.cssSelector("input[id*='PurchTable_PurchId']");
	protected By PURCHASE_TYPE_INPUT = By.cssSelector("input[id*='PurchaseType'][id*='PurchCreateOrder'][type='text']");
	protected By RMA_NUMBER_INPUT = By.name("PurchTable_ReturnItemNum");
	protected By CURRENCY_INPUT = By.cssSelector("input[id*='Currency'][id*='PurchCreateOrder'][type='text']");

	public By SETUP_HEADER = By.xpath("//div[contains(@id,'TabHeaderSetup_header')]/button");
	protected By SALES_TAX_GROUP = By.name("PurchTable_TaxGroup");

	protected By LINES_LINK = By.cssSelector("[for*='SystemDefinedDetailsTransactionRadio_option_0']");
	protected By LINES_LINK_NEW = By.xpath("//li[@data-dyn-controlname='LineView_header']");
	protected By ADD_NEW_LINE = By.cssSelector("[id$='LineStripNew_label']");
	protected By ADD_NEW_LINE_PURCHASE_REQUISITION = By.cssSelector("[id$='PurchReqNewLine']");

	protected By SALES_TAX_LINK = By.xpath("//button[@data-dyn-controlname='TaxTransSource']");
	protected By FINANCIAL_SALES_TAX_LINK = By.xpath("(//button[@data-dyn-controlname='TaxTransSource'])[2]");
	protected By CONFIRMATION_LINK = By.cssSelector("*.button-label[id*='UpdatePurchaseOrder']");
	protected By CONFIRM_LINK = By.cssSelector("*.button-label[id*='buttonConfirm_label']");
	protected By PRODUCT_RECEIPT_LINK = By.cssSelector("*.button-label[id*='UpdatePackingSlip']");
	protected By PRODUCT_RECEIPT_INPUT = By.xpath("//input[@aria-label='Product receipt']");

	protected By INVOICE_LINK = By.xpath("//button[@data-dyn-controlname='buttonUpdateInvoice']");
	protected By INVOICE_NUMBER_INPUT = By.name("PurchParmTable_Num");
	protected By UPDATE_MATCH_STATUS_LINK = By.cssSelector(".button-label[id*='UpdateMatchStatus']");

	protected By MATCH_STATUS_INPUT = By.cssSelector("input[id*='MatchVariance']");
	protected By MATCH_STATUS_INPUT_2 = By.cssSelector("input[id*='gridParmTable_MatchVariance']");
	protected By APPLY_PREPAYMENT_LINK = By.cssSelector(".button-label[id*='ButtonApplyPrepayments']");
	protected By INVOICE_POST_BUTTON = By.cssSelector("button[id*='VendEditInvoice'][id*='OK']");

	final String TMP_TRXN_ROW_LOCATOR = "(//div[contains(@id, 'TmpTaxWorkTrans_TaxCode') and contains(@data-dyn-focus, 'input')])";
	protected By TMP_TRXN_ROW_BY = By.cssSelector("input[id*='TmpTaxWorkTrans_TaxCode']");

	protected By TOTAL_CALCULATED_SALES_TAX_AMOUNT = By.cssSelector("input[id*='TaxAmountCurTotal'][type='text']");
	protected By TOTAL_ACTUAL_SALES_TAX_AMOUNT = By.xpath("//input[contains(@id,'TaxAmountCurTotal')]");
	protected By UPDATE_ACTUAL_SALES_TAX_AMOUNT = By.xpath("//input[contains(@id,'TaxRegulationTotal_input')]");
	protected By TOTAL_ACCRUED_TAX_AMOUNT = By.name("VTXUseTaxTotal");
	protected By SALES_TAX_AMOUNT_INVOICE_JOURNAL = By.xpath("//input[contains(@id,'VendInvoiceJour_VAT')]");

	protected By APPLY_PREPAYMENTS_BUTTON = By.cssSelector("*.button-label[id*='ApplyPrepayments']");
	protected By ADDRESS_HEADER = By.xpath("//div[@data-dyn-controlname='TabHeaderAddress']/div/button");
	protected By DELIVERY_ADDRESS = By.xpath ("//input[@name='PurchTable_DeliveryPostalAddress_Location_Description']");
	protected By CLOSE_BUTTON = By.name("CloseButton");
	protected By NO_BUTTON = By.name("No");
	protected By DELETE_BUTTON = By.name("SystemDefinedDeleteButton");
	protected By YES_BUTTON = By.xpath("//button[@data-dyn-controlname='Yes']");
	protected By MAINTAIN_CHARGE = By.name("MarkupTrans_HeadingPurchTable");
	protected By ALLOCATE_CHARGE = By.name("AllocateMarkup");
	protected By CHARGE_CODE = By.xpath("//input[contains(@id,'MarkupTrans_MarkupCode')]");
	protected By CATEGORY= By.xpath("//input[contains(@id,'MarkupTrans_MarkupCategory')]");
	protected By SELECT_PROPORTIONAL = By.xpath("//li[contains(text(), 'Proportional')]");
	protected By CHARGE_VALUE = By.xpath("//input[contains(@id,'MarkupTrans_Value')]");
	protected By ITEM_SALES_TAXGRP = By.xpath("//input[contains(@id,'SalesTax_TaxItemGroup')]");
	protected By CLOSE_WINDOW = By.xpath("//*[contains(@id, 'MarkupTrans')]//button[@name='SystemDefinedCloseButton']");

	protected String TAB_LOCATOR = "*[type='button'][id*='%s'][id*='purchtable'][class*='header']";
	protected String INVOICE_TAB_LOCATOR = "*[type='button'][id*='%s'][id*='VendEdit'][class*='header']";
	protected By salesTaxAmount = By.xpath("//input[@name='TaxAmountCurTotal']");
	final String PURCHASE_TXT = "Purchase";
	final String RECEIVE_TXT = "Receive";
	final String INVOICE_TXT = "Invoice";

	protected By COMPLETE_MSG = By.xpath("//span[contains(@title, 'The vendor invoice posting process is complete for vendor')]");
	protected By ADJUSTMENT_TAB = By.xpath("//span[text()='Adjustment']");
	protected By APPLY_ACTUAL_AMOUNTS = By.xpath("//span[text()='Apply actual amounts']");
	protected By VENDOR_EXCHANGE_RATE = By.name("Vertex_VTXVendorExchangeRate1");
	protected By CLICK_CALENDER_BUTTON=By.xpath("//div[@data-dyn-controlname='PurchParmTable_DocumentDate']//div[@class='lookupButton']");
	protected By SELECT_TODAY_DATE=By.xpath("//table[contains(@class,'ui-datepicker-calendar')]//a[contains(@aria-label, 'Today')]");
	protected By POSTING_DATE_FIELD=By.xpath("//input[@name='PurchParmTable_TransDate']");
	protected By INVOICE_DATE_FIELD=By.xpath("//input[@name='PurchParmTable_DocumentDate']");
	protected By TOTALS_BUTTON = By.name("ParmTableTotals");
	protected By WORK_FLOW_BUTTON = By.name("VendorInvoiceHeaderWorkflowDropDialog");
	protected By WORK_FLOW_STATUS = By.name("ApprovalStatus");
	protected By APPROVE_WORKFLOW = By.xpath("//button[@name='PromotedAction1']");
	protected By APPROVE_WORKFLOW_SUBMIT = By.xpath("//button[@name='Action']");
	protected By REQUEST_CHANGE = By.xpath("//span[text()='Request change']");
	protected By TOTALS_INPUT_FIELDS = By.xpath("//input[contains(@id, 'VendEditInvoiceTotals')]");
	protected By OVERFLOW_BUTTON = By.xpath("//div[contains(@class,'appBar')][@data-dyn-role='OverflowButton'][not(@aria-hidden)]");
	protected By WORKFLOW_RADIO_BUTTON = By.xpath("//div[@title='Select or unselect row']");
	protected By VERSIONS_LINK = By.xpath("//span[text()='Versions']");
	protected By WORKFLOW_STATUS_BUTTON = By.name("ActiveButton");
	protected By WORKFLOW_STATUS_LABEL = By.xpath("//span[contains(@id,'ActiveButton_label')]");
	protected By PURCHASE_REQUISITION_NAME = By.name("PurchReqTable_PurchReqName");
	protected By PURCHASE_ORDER_NUMBER = By.name("References_PurchId");
	protected By FINANCIALS = By.xpath("//button[contains(@id,'LineGridFinancialsMenuButton_button')]");
	protected By WORKFLOW_PURCHASE_REQUISITION = By.xpath("(//button[@name='PurchReqTableWorkflowDropDialog'])[2]");
	protected By RESPONSIVE_BUTTON = By.xpath("(//div[@data-dyn-controlname='ActionPaneLinesActions']//div[@data-dyn-role='OverflowButton'][not(@aria-hidden)])");
	protected By SALES_TAX_FINANCIALS = By.xpath("//button[@aria-label='Tax Sales tax']");
	protected By CREATE_PURCHASE_ORDER_FOR_PURCHASE_REQUISITION = By.name("PurchaseOrderMenuItemButton");
	protected By VENDOR_ACCOUNT_INPUT_PURCHASE_REQUISITION = By.xpath("(//input[@aria-label='Vendor account' and contains(@aria-required, 'true')])");
	protected By PURCHASE_ORDER_NUMBER_FROM_CONFIRMATION = By.xpath("(//span[@class='messageBar-message messageBar-detailLink'])[1]");
	protected By FILTER_SEARCH_BOX_PURCHASE_ORDER = By.name("GridFilter_Input");
	protected By FILTER_PURCHASE_ORDER_OPTION = By.xpath("//span[@class='quickFilter-listFieldName' and text()='Purchase order']");
	protected By SELECT_PURCHASE_ORDER_NUMBER = By.xpath("//input[@aria-label='Purchase order']");
	protected By PRODUCT_TAB = By.xpath("//li[@data-dyn-controlname='TabLineProduct_header']");
	protected By ACTIVATE_CHANGE_MANAGEMENT = By.xpath("//label[@data-dyn-controlname='ChangeRequest_ChangeRequestEnabled']//div//span[contains(@id, 'ChangeRequestEnabled')]");
	protected By QUANTITY = By.xpath("(//input[contains(@id,'PurchLine_PurchQtyGrid')])[1]");
	protected By leadTimeChanged = By.xpath("//span[text()='Lead time changed, recalculate ship and receipt dates?']");
	protected By CLICK_PREPAYMENT = By.xpath("//button[@name='PrepayPurchTableHeading']");
	protected By ENTER_PREPAYMENT_VALUE = By.xpath("//input[@name='PurchPrepayTable_Value']");
	protected By ENTER_PREPAYMENT_CATEGORY_ID = By.xpath("//input[@name='PurchPrepayTable_PrepayCategoryId_Name']");
	protected By CLICK_PREPAYMENT_INVOICE = By.xpath("//button[@name='buttonUpdatePrepayInvoice']");
	Actions action = new Actions(driver);

	public DFinanceCreatePurchaseOrderPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Clicks and enters the vendor account
	 * @param vendorAccount
	 */
	public void setVendorAccountNumber( String vendorAccount )
	{
		//expandHeader(VENDOR_HEADING);
		wait.waitForElementDisplayed(VENDOR_ACCOUNT_INPUT);
		text.enterText(VENDOR_ACCOUNT_INPUT, vendorAccount + Keys.TAB);
		waitForPageLoad();
	}

	/**
	 * Clicks and enters the invoice account
	 * @param invoiceAccount
	 */
	public void setInvoiceAccountNumber( String invoiceAccount )
	{
		wait.waitForElementDisplayed(INVOICE_ACCOUNT_INPUT);
		text.enterText(INVOICE_ACCOUNT_INPUT, invoiceAccount + Keys.TAB);
		waitForPageLoad();
	}

	/**
	 * Clicks and enters the purchase order type
	 * @param purchaseOrderType
	 */
	public void setPurchaseOrderType( String purchaseOrderType )
	{
		wait.waitForElementDisplayed(PURCHASE_TYPE_INPUT);
		click.clickElementCarefully(PURCHASE_TYPE_INPUT);
		WebElement purchaseOrderValue = wait.waitForElementDisplayed(By.xpath("//li[text()='"+purchaseOrderType+"']"));
		click.clickElementCarefully(purchaseOrderValue);
		waitForPageLoad();
	}

	/**
	 * Clicks and enters the RMA number
	 * @param rmaNumber
	 */
	public void setRmaNumber( String rmaNumber )
	{
		wait.waitForElementDisplayed(RMA_NUMBER_INPUT);
		text.enterText(RMA_NUMBER_INPUT, rmaNumber + Keys.TAB);
		waitForPageLoad();
	}

	/**
	 * Enter Delivery Address
	 * @Author Vishwa
	 * @param deliveryAddress
	 */
	public void enterDeliveryAddress( String deliveryAddress )
	{
		if(element.isElementDisplayed(ADDRESS_HEADER))
			expandHeader(ADDRESS_HEADER);
		waitForPageLoad();
		scroll.scrollElementIntoView(DELIVERY_ADDRESS);
		WebElement DAddress = wait.waitForElementDisplayed(DELIVERY_ADDRESS);
		//wait.waitForElementDisplayed(DAddress);
		text.clearText(DAddress);
		text.enterText(DAddress, deliveryAddress + Keys.TAB);
		waitForPageLoad();
		DAddress.sendKeys(Keys.ENTER);
		waitForPageLoad();
	}

	public void clickOkButton( )
	{
		wait.waitForElementEnabled(OK_BUTTON);
		try
		{
			click.clickElement(OK_BUTTON);
		}
		catch(Exception ex)
		{
			VertexLogger.log(ex.toString());
			if (element.isElementPresent(OK_BUTTON))
				click.javascriptClick(OK_BUTTON);
		}
		waitForPageLoad();
	}

	/**
	 * Click on Header
	 */
	public void clickOnHeader( )
	{
		if(element.isElementDisplayed(HEADER_LINK)) {
			wait.waitForElementDisplayed(element.getWebElement(HEADER_LINK));
			click.clickElement(HEADER_LINK);
		}else if (element.isElementDisplayed(HEADER_LINK_NEW)) {
			wait.waitForElementDisplayed(element.getWebElement(HEADER_LINK_NEW));
			click.clickElement(HEADER_LINK_NEW);
		}
		waitForPageLoad();
	}

	/**
	 * Click on header in vendor invoice
	 */
	public void clickOnVendorInvoiceHeader( )
	{
		wait.waitForElementDisplayed(element.getWebElement(VENDOR_INVOICE_HEADER_LINK));
		click.clickElement(VENDOR_INVOICE_HEADER_LINK);
		waitForPageLoad();
	}

	/**
	 * Get Sales Order Number
	 *
	 * @return
	 */
	public String getPurchaseOrderNumber( )
	{
		wait.waitForElementDisplayed(PURCHASE_ORDER_NUMBER_INPUT);
		String purchaseOrderNumber = attribute.getElementAttribute(PURCHASE_ORDER_NUMBER_INPUT, "title");
		String [] purchaseOrderNumberArray = purchaseOrderNumber.split("\\s+");
		return purchaseOrderNumberArray[0];
	}

	/**
	 * set sales order tax group
	 *
	 * @param 'salesTaxGroup' to type in sales group element
	 */
	public void setSalesOrderTaxGroup( String salesTaxGroup )
	{
		expandHeader(SETUP_HEADER);
		text.enterText(SALES_TAX_GROUP, salesTaxGroup + Keys.TAB);
		waitForPageLoad();
	}

	/**
	 * Click on "Lines" option
	 */
	public void clickOnLines( )
	{
		if(element.isElementDisplayed(LINES_LINK)) {
			wait.waitForElementDisplayed(element.getWebElement(LINES_LINK));
			click.clickElement(LINES_LINK);
		}else if (element.isElementDisplayed(LINES_LINK_NEW)) {
			wait.waitForElementDisplayed(element.getWebElement(LINES_LINK_NEW));
			click.clickElement(LINES_LINK_NEW);
		}
	}

	/**
	 * Add a line item to the PO
	 *  @param item
	 * @param qty
	 * @param site
	 * @param warehouse
	 * @param unitPrice
	 * @param itemIndex
	 */
	public void addItemLine( String item, String qty, String site, String warehouse, String unitPrice,int itemIndex )
	{
		waitForPageLoad();
		if(itemIndex>0)
		{
			wait.waitForElementDisplayed(ADD_NEW_LINE);
			click.clickElementCarefully(ADD_NEW_LINE);
		}
		int itemNumber = itemIndex + 2;
		itemIndex = itemIndex+1;
		DFinanceBasePage purchasePage = new DFinanceBasePage(driver);
		purchasePage.enterItem(item, itemIndex);
		purchasePage.enterQuantity(qty, itemNumber);
		purchasePage.enterSite(site, itemNumber);
		purchasePage.enterWarehouse(warehouse, itemNumber);
		purchasePage.enterunitPrice(unitPrice, itemNumber);
		waitForPageLoad();
	}

	/**
	 * clicks on the very first cell in a row to make it interactable
	 *
	 * @param rowIndex
	 */
	public void activateRow( int rowIndex )
	{
		rowIndex--;
		WebElement activateCell = getRowActivationCell(rowIndex);
		try
		{
			click.clickElement(activateCell);
		}
		catch ( ElementNotInteractableException e )
		{
			click.javascriptClick(activateCell);
		}
	}

	/**
	 * Add multiple line item to the PO
	 * @Author Vishwa
	 * @param item
	 * @param qty
	 * @param site
	 * @param warehouse
	 * @param itemIndex
	 */
	public void fill1STItemsInfo ( String item, String site, String warehouse, String qty, int itemIndex ) {

		waitForPageLoad();
		int itemNumber = itemIndex + 2;
		itemIndex = itemIndex+1;
		DFinanceBasePage purchasePage = new DFinanceBasePage(driver);

		purchasePage.enterItem(item, itemIndex);
		purchasePage.enterQuantity(qty, itemNumber);
		purchasePage.enterSite(site, itemNumber);
		purchasePage.enterWarehouse(warehouse, itemNumber);
	}

	/**
	 * Add multiple line item to the PO
	 * @Author Vishwa
	 * @param item
	 * @param qty
	 * @param site
	 * @param warehouse
	 * @param itemIndex
	 */
	public void fillItemsInfo ( String item, String site, String warehouse, String qty, int itemIndex ) {

		wait.waitForElementDisplayed(ADD_NEW_LINE);
		click.javascriptClick(ADD_NEW_LINE);

		int itemNumber = itemIndex + 1;
		DFinanceBasePage purchasePage = new DFinanceBasePage(driver);

		purchasePage.enterItem(item, itemIndex);
		purchasePage.enterSite(site, itemNumber);
		purchasePage.enterWarehouse(warehouse, itemNumber);
		purchasePage.enterQuantity(qty, itemNumber);
	}

	/**
	 * Add a line item to the PR
	 *  @param procurementCategory
	 * @param productName
	 * @param qty
	 * @param unit
	 * @param unitPrice
	 * @param itemIndex
	 */
	public void addItemLineForPurchaseRequisition( String procurementCategory, String productName, String qty, String unit, String unitPrice, int itemIndex )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(ADD_NEW_LINE_PURCHASE_REQUISITION);
		click.clickElementCarefully(ADD_NEW_LINE_PURCHASE_REQUISITION);

		int itemNumber = itemIndex + 2;
		itemIndex = itemIndex+1;
		DFinanceBasePage purchasePage = new DFinanceBasePage(driver);
		purchasePage.enterProcurementCategory(procurementCategory, itemIndex);
		purchasePage.enterProductName(productName, itemIndex);
		purchasePage.enterUnit(unit, itemIndex);
		purchasePage.enterQuantity(qty, itemNumber);
		purchasePage.enterunitPrice(unitPrice, itemNumber);
	}

	/**
	 * Add line item using Item Number for PR
	 * @param itemNumber
	 * @param rowIndex
	 */
	public void addItemUsingItemNumberPurchaseRequisition(String itemNumber, int rowIndex){
		wait.waitForElementDisplayed(ADD_NEW_LINE_PURCHASE_REQUISITION);
		click.clickElementCarefully(ADD_NEW_LINE_PURCHASE_REQUISITION);

		String lineXPath =String.format("(//input[contains(@aria-label,'Item')])[%s]",rowIndex);
		WebElement itemNumberField = wait.waitForElementPresent(By.xpath(lineXPath));
		action.moveToElement(itemNumberField).click(itemNumberField).sendKeys(itemNumber).sendKeys(Keys.TAB).perform();
	}

	/**
	 * Click on 'Tab'
	 *
	 * @param tab
	 */
	public void clickOnTab( String tab )
	{
		By TAB = By.cssSelector(String.format(TAB_LOCATOR, tab));
		String isExpanded = attribute.getElementAttribute(TAB, "aria-expanded");

		if ( !isExpanded.equalsIgnoreCase("true") )
		{
			click.javascriptClick(TAB);
			waitForPageLoad();
		}
	}

	/**
	 * Click on 'Tab'
	 *
	 * @param tab
	 */
	public void clickOnInvoiceTab( String tab )
	{
		waitForPageLoad();
		By TAB = By.cssSelector(String.format(INVOICE_TAB_LOCATOR, tab));
		String isExpanded = attribute.getElementAttribute(TAB, "aria-expanded");

		if(!element.isElementDisplayed(TAB))
		{
			click.javascriptClick(OVERFLOW_BUTTON);
			if ( !isExpanded.equalsIgnoreCase("true") )
			{
				click.clickElementCarefully(TAB);
				waitForPageLoad();
			}
		}
	}

	/**
	 * This method is used to navigate to Purchase --> Tax --> Sales Tax page
	 */
	public void navigateToSalesTaxPage( )
	{
		waitForPageLoad();
		boolean isChargesLinkDisplayed = element.isElementDisplayed(SALES_TAX_LINK);

		if ( !isChargesLinkDisplayed )
		{
			// click Purchase tab
			clickOnTab(PURCHASE_TXT);
			wait.waitForElementDisplayed(SALES_TAX_LINK);
		}
		// click Sales Tax link
		waitForPageLoad();
		wait.waitForElementDisplayed(SALES_TAX_LINK);
		click.javascriptClick(SALES_TAX_LINK);
		waitForPageLoad();
	}

	/** Click Prepayment in the Purchase Tab **/
	public void clickPrepayment(){
		wait.waitForElementDisplayed(CLICK_PREPAYMENT);
		click.clickElementCarefully(CLICK_PREPAYMENT);
	}

	/** Enters the prepayment value
	 	@param enterPrepaymentValue
	 **/
	public void enterPrepaymentValue(String enterPrepaymentValue){
		wait.waitForElementDisplayed(ENTER_PREPAYMENT_VALUE);
		text.setTextFieldCarefully(ENTER_PREPAYMENT_VALUE, enterPrepaymentValue, false);
	}

	/** Enters the prepayment category id
	 * @param enterPrepaymentCategoryID
	 **/
	public void enterPrepaymentCategoryID(String enterPrepaymentCategoryID){
		wait.waitForElementDisplayed(ENTER_PREPAYMENT_CATEGORY_ID);
		text.setTextFieldCarefully(ENTER_PREPAYMENT_CATEGORY_ID, enterPrepaymentCategoryID, false);
	}

	/** Clicks the Prepayment Invoice button **/
	public void clickPrepaymentInvoice(){
		wait.waitForElementDisplayed(CLICK_PREPAYMENT_INVOICE);
		click.clickElementCarefully(CLICK_PREPAYMENT_INVOICE);
	}

	/**
	 * This method is used to navigate to Financials --> Tax --> Sales Tax page
	 */
	public void navigateToFinancialSalesTaxPage( )
	{
		boolean isChargesLinkDisplayed = element.isElementDisplayed(FINANCIAL_SALES_TAX_LINK);

		if ( !isChargesLinkDisplayed )
		{
			// click Purchase tab
			clickOnTab(PURCHASE_TXT);
			waitForPageLoad();
			wait.waitForElementDisplayed(FINANCIAL_SALES_TAX_LINK);
		}
		// click Sales Tax link
		waitForPageLoad();
		wait.waitForElementDisplayed(FINANCIAL_SALES_TAX_LINK);
		click.clickElementCarefully(FINANCIAL_SALES_TAX_LINK);
		waitForPageLoad();
	}

	/**
	 * This method is used to navigate to Purchase --> GENERATE --> Confirmation page
	 */
	public void navigateToConfirmationPage( )
	{
		boolean isChargesLinkDisplayed = element.isElementDisplayed(CONFIRMATION_LINK);

		if ( !isChargesLinkDisplayed )
		{
			// click Purchase tab
			clickOnTab(PURCHASE_TXT);
			wait.waitForElementDisplayed(CONFIRMATION_LINK);
		}
		// click Confirmation link
		wait.waitForElementEnabled(CONFIRMATION_LINK);
		click.clickElement(CONFIRMATION_LINK);
		waitForPageLoad();
	}

	/**
	 * This method is used to navigate to Purchase --> GENERATE --> Confirm page
	 */
	public void navigateToConfirmPage( )
	{
		boolean isChargesLinkDisplayed = element.isElementDisplayed(CONFIRM_LINK);

		if ( !isChargesLinkDisplayed )
		{
			// click Purchase tab
			clickOnTab(PURCHASE_TXT);
			wait.waitForElementDisplayed(CONFIRM_LINK);
		}
		// click Confirm link
		wait.waitForElementEnabled(CONFIRM_LINK);
		click.javascriptClick(CONFIRM_LINK);
		waitForPageLoad();
	}

	/**
	 * This method is used to navigate to Receive --> GENERATE --> ProductReceipt page
	 */
	public void navigateToProductReceiptPage( )
	{
		boolean isChargesLinkDisplayed = element.isElementDisplayed(PRODUCT_RECEIPT_LINK);

		if ( !isChargesLinkDisplayed )
		{
			// click RECEIVE tab
			clickOnTab(RECEIVE_TXT);
			wait.waitForElementDisplayed(PRODUCT_RECEIPT_LINK);
		}
		// click Product Receipt link
		wait.waitForElementEnabled(PRODUCT_RECEIPT_LINK);
		click.clickElement(PRODUCT_RECEIPT_LINK);
		waitForPageLoad();
	}

	/**
	 * Enters the Product Receipt Number into the field
	 * @param productReceiptNumber
	 */
	public void setProductReceiptNumber( String productReceiptNumber )
	{
		wait.waitForElementDisplayed(PRODUCT_RECEIPT_INPUT);
		text.enterText(PRODUCT_RECEIPT_INPUT, productReceiptNumber + Keys.TAB);
	}

	/**
	 * This method is used to navigate to Invoice --> GENERATE --> Invoice page
	 */
	public void navigateToInvoicePage( )
	{
		boolean isChargesLinkDisplayed = element.isElementDisplayed(INVOICE_LINK);

		if ( !isChargesLinkDisplayed )
		{
			// click Invoice tab
			clickOnTab(INVOICE_TXT);
			wait.waitForElementDisplayed(INVOICE_LINK);
		}
		// click Invoice link
		wait.waitForElementEnabled(INVOICE_LINK);
		click.clickElement(INVOICE_LINK);
		waitForPageLoad();
	}

	/**
	 * Entering the Invoice Indentification Number into the field
	 * @param invoiceIdentificationNumber
	 */
	public void setInvoiceIdentificationNumber( String invoiceIdentificationNumber )
	{
		wait.waitForElementDisplayed(INVOICE_NUMBER_INPUT);
		text.enterText(INVOICE_NUMBER_INPUT, invoiceIdentificationNumber);
	}

	/**
	 * Click the Match Status update button
	 */
	public void clickUpdateMatchStatusButton( )
	{
		wait.waitForElementDisplayed(UPDATE_MATCH_STATUS_LINK);
		click.javascriptClick(UPDATE_MATCH_STATUS_LINK);
		waitForPageLoad();
	}

	/**
	 * Get the Match Status
	 * @return matchStatus
	 */
	public String getMatchStatus( )
	{
		String matchStatus = "";

		if(element.isElementPresent(MATCH_STATUS_INPUT)){
			wait.waitForElementPresent(MATCH_STATUS_INPUT);
			matchStatus = attribute.getElementAttribute(MATCH_STATUS_INPUT, "value");
		}else if(element.isElementPresent(MATCH_STATUS_INPUT_2)){
			wait.waitForElementPresent(MATCH_STATUS_INPUT_2);
			matchStatus = attribute.getElementAttribute(MATCH_STATUS_INPUT_2, "value");
		}

		if ( matchStatus != null )
		{
			matchStatus = matchStatus.trim();
		}

		return matchStatus;
	}

	public void clickApplyPrepaymentOption( )
	{
		wait.waitForElementDisplayed(APPLY_PREPAYMENT_LINK);
		click.clickElement(APPLY_PREPAYMENT_LINK);
		waitForPageLoad();
	}

	/**
	 * Clicks the Post Tab and posts the Order
	 */
	public void clickPostOption( )
	{
		waitForPageLoad();
		if(element.isElementPresent(HEADER_ELLIPSIS)) {
			click.clickElementCarefully(HEADER_ELLIPSIS);
		}
		wait.waitForElementDisplayed(INVOICE_POST_BUTTON);
		click.javascriptClick(INVOICE_POST_BUTTON);
		waitForPageLoad();
		if(element.isElementPresent(COMPLETE_MSG)){
			wait.waitForElementDisplayed(COMPLETE_MSG);
		}
		waitForPageLoad();
	}

	/**
	 * Checks to see if an invoice is posted successfully
	 * @param vendorAccount
	 * @param invoiceNumber
	 * @return
	 */
	public boolean isInvoicePostedSuccessfully( String vendorAccount, String invoiceNumber )
	{
		By INVOICE_POST_SUCCESS_MSG = By.cssSelector(
			String.format("[title*='The vendor invoice posting process is complete for vendor %s, invoice %s.']",
				vendorAccount, invoiceNumber));

		waitForPageLoad();
		wait.waitForElementPresent(INVOICE_POST_SUCCESS_MSG,180);
		boolean result = element.isElementPresent(INVOICE_POST_SUCCESS_MSG);

		return result;
	}

	/**
	 * Checks to see if the Please Wait message is gone
	 * @return
	 */
	public boolean isPleaseWaitProcessed()
	{

		boolean result = element.isElementPresent(By.xpath("//span[contains(@id, 'blockingMessage')]"));

		if (element.isElementPresent(By.xpath("//span[contains(@id, 'blockingMessage')]"))) {
			wait.waitForElementNotDisplayed(By.xpath("//span[contains(@id, 'blockingMessage')]"), 120);
		}


		return result;
	}

	/**
	 * To get the Temporary Sales Tax transaction lines count
	 *
	 * @return
	 */
	public int getTempSalesTaxTransactionLinesCount( )
	{
		waitForPageLoad();
		int linesCount = element
			.getWebElements(TMP_TRXN_ROW_BY)
			.size();

		return linesCount;
	}

	public Map<String, String> getTransactionLineData( String lineNumber )
	{
		Map<String, String> lineDataMap = new HashMap<String, String>();

		By SALES_TAX_CODE = By.xpath(
			String.format("(//div[contains(@id,'TaxCode')][@data-dyn-focus='input']//div//input[@aria-label='Sales tax code'])[%s]", lineNumber));
		By PERCENT = By.xpath(
			String.format("(//div[contains(@id,'showTaxValue')][@data-dyn-focus='input']//div//input[@aria-label='Percent'])[%s]", lineNumber));
		By ACTUAL_SALES_TAX_AMOUNT = By.xpath(
			String.format("(//div[contains(@id,'SourceRegulateAmountCur')][@data-dyn-focus='input']//div//input[@aria-label='Actual sales tax amount'])[%s]", lineNumber));
		By SALES_TAX_DIRECTION = By.xpath(
			String.format("(//div[contains(@id,'TaxDirection')][@data-dyn-focus='input']//div//input[@aria-label='Sales tax direction'])[%s]", lineNumber));
		By VERTEX_TAX_DESCRIPTION = By.xpath(
			String.format("(//div[contains(@id,'VTXVertexTaxDescription')][@data-dyn-focus='input']//div//input[@aria-label='Vertex tax description'])[%s]", lineNumber));
		By DESCRIPTION = By.xpath(
			String.format("(//div[contains(@id,'TmpTaxWorkTrans_Txt')][@data-dyn-focus='input']//div//input[@aria-label='Description'])[%s]", lineNumber));

		String salesTaxCode = attribute.getElementAttribute(SALES_TAX_CODE, "value");
		String percent = attribute.getElementAttribute(PERCENT, "value");
		String actualSalesTaxAmount = attribute.getElementAttribute(ACTUAL_SALES_TAX_AMOUNT, "value");
		String salesTaxDirection = attribute.getElementAttribute(SALES_TAX_DIRECTION, "value");
		String vertexTaxDesc = attribute.getElementAttribute(VERTEX_TAX_DESCRIPTION, "value");
		String description = attribute.getElementAttribute(DESCRIPTION, "value");

		lineDataMap.put("Sales tax code", salesTaxCode);
		lineDataMap.put("Percent", percent);
		lineDataMap.put("Actual sales tax amount", actualSalesTaxAmount);
		lineDataMap.put("Sales tax direction", salesTaxDirection);
		lineDataMap.put("Vertex tax description", vertexTaxDesc);
		lineDataMap.put("Description", description);

		return lineDataMap;
	}

	/**
	 * To get the Sales Tax Amount from each line item
	 * @Author Vishwa
	 * @return
	 */

	public Map<String, String> getSalesTaxAmount( String lineNumber ) {

		Map<String, String> lineDataMap = new HashMap<String, String>();

		By SALES_TAX_CODE = By.xpath("(//input[contains(@id,'TmpTaxWorkTrans_TaxCode')])["+lineNumber+"]");
		By PERCENT = By.xpath("(//input[@aria-label='Percent'])["+lineNumber+"]");
		By ACTUAL_SALES_TAX_AMOUNT = By.xpath("(//input[@aria-label='Actual sales tax amount'])["+lineNumber+"]");
		By SALES_TAX_DIRECTION = By.xpath("(//input[contains(@id,'TmpTaxWorkTrans_TaxDirection')])["+lineNumber+"]");
		By VERTEX_TAX_DESCRIPTION = By.xpath("(//input[contains(@id,'VTXVertexTaxDescription')])["+lineNumber+"]");
		By DESCRIPTION = By.xpath("(//input[@aria-label='Description'])["+lineNumber+"]");

		String salesTaxCode = attribute.getElementAttribute(SALES_TAX_CODE, "value");
		String percent = attribute.getElementAttribute(PERCENT, "value");
		String actualSalesTaxAmount = attribute.getElementAttribute(ACTUAL_SALES_TAX_AMOUNT, "value");
		String salesTaxDirection = attribute.getElementAttribute(SALES_TAX_DIRECTION, "value");
		String vertexTaxDesc = attribute.getElementAttribute(VERTEX_TAX_DESCRIPTION, "value");
		String description = attribute.getElementAttribute(DESCRIPTION, "value");

		lineDataMap.put("Sales tax code", salesTaxCode);
		lineDataMap.put("Percent", percent);
		lineDataMap.put("Actual sales tax amount", actualSalesTaxAmount);
		lineDataMap.put("Sales tax direction", salesTaxDirection);
		lineDataMap.put("Vertex tax description", vertexTaxDesc);
		lineDataMap.put("Description", description);

		return lineDataMap;
	}

	/**
	 * Verify the "Total calculated sales tax amount" value
	 *
	 * @return calculatedSalesTaxAmount
	 */
	public String getCalculatedSalesTaxAmount( )
	{
		waitForPageLoad();
		wait.waitForElementEnabled(TOTAL_CALCULATED_SALES_TAX_AMOUNT);
		String calculatedSalesTaxAmount = attribute.getElementAttribute(TOTAL_CALCULATED_SALES_TAX_AMOUNT, "value");
		return calculatedSalesTaxAmount;
	}

	/**
	 * Update the "Total Actual sales tax amount" value
	 *
	 * @param updateTaxAmount
	 */
	public void updateActualSalesTaxAmount(String updateTaxAmount)
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(UPDATE_ACTUAL_SALES_TAX_AMOUNT);
		WebElement taxAmountField = wait.waitForElementEnabled(UPDATE_ACTUAL_SALES_TAX_AMOUNT);
		text.clearText(taxAmountField);
		text.clickElementAndEnterText(taxAmountField, updateTaxAmount);
		taxAmountField.sendKeys(Keys.TAB);

	}

	/**
	 * Verify the "Sales Tax Amount" of the invoice journal
	 *
	 * @return calculatedSalesTaxAmount
	 */
	public String getSalesTaxAmount( )
	{
		waitForPageLoad();
		wait.waitForElementPresent(SALES_TAX_AMOUNT_INVOICE_JOURNAL);
		String salesTaxAmount = attribute.getElementAttribute(SALES_TAX_AMOUNT_INVOICE_JOURNAL, "value");
		return salesTaxAmount;
	}

	/**
	 * Clicks the adjustment tab in the sales tax
	 */
	public void clickAdjustmentSalesTax(){
		wait.waitForElementDisplayed(ADJUSTMENT_TAB);
		click.javascriptClick(ADJUSTMENT_TAB);
	}

	/**
	 * Clicks apply actual amounts option
	 */
	public void clickApplyActualAmounts(){
		wait.waitForElementDisplayed(APPLY_ACTUAL_AMOUNTS);
		click.clickElementCarefully(APPLY_ACTUAL_AMOUNTS);
	}

	/**
	 * get the "Accrual sales tax amount" value
	 *
	 * @return totalAccruedTaxAmount
	 */
	public String getTotalAccruedTaxAmount(){
		jsWaiter.sleep(10000);
		wait.waitForElementEnabled(TOTAL_ACCRUED_TAX_AMOUNT);
		String totalAccruedTaxAmount = attribute.getElementAttribute(TOTAL_ACCRUED_TAX_AMOUNT, "value");
		Double accruedTaxValue = Double.parseDouble(totalAccruedTaxAmount);

		return (element.isElementEnabled(TOTAL_ACCRUED_TAX_AMOUNT)  && accruedTaxValue >= 0) ? totalAccruedTaxAmount : null;
	}

	/**
	 * Verify the "Total actual sales tax amount" value
	 *
	 * @return
	 */
	public String getActualSalesTaxAmount( )
	{
		wait.waitForElementPresent(TOTAL_ACTUAL_SALES_TAX_AMOUNT);
		String actualSalesTaxAmount = attribute.getElementAttribute(TOTAL_ACTUAL_SALES_TAX_AMOUNT, "value");
		return actualSalesTaxAmount;
	}

	/**
	 * Verify the "Total actual sales tax amount" value
	 *
	 * @return
	 */
	public String getUpdatedActualSalesTaxAmount( )
	{
		wait.waitForElementPresent(UPDATE_ACTUAL_SALES_TAX_AMOUNT);
		String actualSalesTaxAmount = attribute.getElementAttribute(UPDATE_ACTUAL_SALES_TAX_AMOUNT, "value");
		return actualSalesTaxAmount;
	}

	/**
	 * Click on "OK" button
	 *
	 * @return the resulting Home page
	 */
	public DFinanceHomePage clickOnSalesTaxOkButton( )
	{
		waitForPageLoad();
		wait.waitForElementEnabled(OK_BUTTON);
		click.javascriptClick(OK_BUTTON);
		waitForPageLoad();
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		return homePage;
	}

	/**
	 * Checks to see if Vendor Exchange Rate Entry dialog is present
	 * @return result
	 */
	public boolean isVendorExchangeRatePresent( )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(VENDOR_EXCHANGE_RATE_ENTRY_MSG);
		boolean result = element.isElementDisplayed(VENDOR_EXCHANGE_RATE_ENTRY_MSG);
		if(result)
		{
			wait.waitForElementDisplayed(VENDOR_EXCHANGE_RATE_CLOSE_MSG);
			click.clickElementCarefully(VENDOR_EXCHANGE_RATE_CLOSE_MSG);
		}
		return result;
	}

	/**
	 * Clicks the repayment button
	 * @return applyPrepaymentsPage
	 */
	public DFinanceApplyPrepaymentsPage applyPrepayments( )
	{
		wait.waitForElementDisplayed(APPLY_PREPAYMENTS_BUTTON);
		click.javascriptClick(APPLY_PREPAYMENTS_BUTTON);
		waitForPageLoad();

		DFinanceApplyPrepaymentsPage applyPrepaymentsPage = initializePageObject(DFinanceApplyPrepaymentsPage.class);

		return applyPrepaymentsPage;
	}

	/**
	 * Remove the address and save
	 */
	public void removeDeliveryAddress( )
	{
		expandHeader(ADDRESS_HEADER);
		wait.waitForElementDisplayed(DELIVERY_ADDRESS);
		click.clickElementCarefully(DELIVERY_ADDRESS);
		wait.waitForElementDisplayed(CLOSE_BUTTON);
		click.clickElementCarefully(CLOSE_BUTTON);
		text.clearText(DELIVERY_ADDRESS);
		text.pressTab(DELIVERY_ADDRESS);
		click.clickElementCarefully(SAVE_BUTTON);
		click.clickElementCarefully(SAVE_BUTTON);
		waitForPageLoad();
	}

	/**
	 * Delete Button
	 */
	public void deleteButton( )
	{
		wait.waitForElementDisplayed(DELETE_BUTTON);
		click.clickElementCarefully(DELETE_BUTTON);
		wait.waitForElementDisplayed(YES_BUTTON);
		click.clickElementCarefully(YES_BUTTON);
		waitForPageLoad();
	}

	/**
	 * Save Button
	 */
	public void saveButton( )
	{
		wait.waitForElementDisplayed(SAVE_BUTTON);
		click.clickElementCarefully(SAVE_BUTTON);
		waitForPageLoad();
	}

	/**
	 * Enters the Vendor Exchange Rate
	 * @param exchangeRate
	 */
	public void enterVendorExchangeRate(String exchangeRate){
		wait.waitForElementDisplayed(VENDOR_EXCHANGE_RATE);
		text.clearText(VENDOR_EXCHANGE_RATE);
		text.clickElementAndEnterText(VENDOR_EXCHANGE_RATE, exchangeRate);
	}

	/**
	 * Set invoice date
	 */
	public void setInvoiceDateForVendorInvoice()
	{
		hover.hoverOverElement(CLICK_CALENDER_BUTTON);
		click.clickElementCarefully(CLICK_CALENDER_BUTTON);
		click.clickElementCarefully(SELECT_TODAY_DATE);
		waitForPageLoad();
	}

	/**
	 * Gets the posting date for the vendor invoice
	 * @return posting date
	 */
	public String getPostingDate(){
		WebElement postingDateField = wait.waitForElementPresent(POSTING_DATE_FIELD);
		String dateValue = attribute.getElementAttribute(postingDateField,"title");
		String[] newDate = dateValue.split("/");
		String output = newDate[2] + "-" + "0" + newDate[0] + "-" + newDate[1];
		return output;
	}

	/**
	 * Gets the invoice date for the vendor invoice
	 * @return posting date
	 */
	public String getInvoiceDate(){
		WebElement invoiceDateField = wait.waitForElementPresent(INVOICE_DATE_FIELD);
		String invoiceDateValue = attribute.getElementAttribute(invoiceDateField,"title");
		String[] newDate = invoiceDateValue.split("/");
		String invoiceDateVal = newDate[2] + "-" + "0" + newDate[0] + "-" + newDate[1];
		return invoiceDateVal;
	}

	/**
	 * Clicks the Totals Tab after generating the invoice
	 */
	public void clickOnTotalsButton(){
		if (!element.isElementDisplayed(TOTALS_BUTTON))
		{
			driver.manage().window().maximize();
			waitForPageLoad();
		}
		else
		wait.waitForElementDisplayed(TOTALS_BUTTON);
		click.clickElementCarefully(TOTALS_BUTTON);
		driver.manage().window().setSize(new Dimension(1024,768));
		waitForPageLoad();
	}

	/**
	 * Gets and returns the value of the input field
	 * @param inputField
	 * @return
	 */
	public String getTotalsInputFieldValue(int inputField){
		List<WebElement> totalsInputFields = wait.waitForAllElementsDisplayed(TOTALS_INPUT_FIELDS);
		String inputFieldValue = attribute.getElementAttribute(totalsInputFields.get(inputField), "value");

		return inputFieldValue;
	}

	/**
	 * Clicks the Workflow button
	 * @param elementLocation
	 */
	public void clickWorkFlow(String elementLocation){

		WebElement workFlow = wait.waitForElementDisplayed(By.xpath("(//button[contains(@id, 'WorkflowDropDialog')])["+elementLocation+"]"));
		if(!element.isElementDisplayed(workFlow))
		{
			driver.manage().window().maximize();
			workFlow = wait.waitForElementDisplayed(By.xpath("(//button[contains(@id, 'WorkflowDropDialog')])["+elementLocation+"]"));
		}
		if(element.isElementDisplayed(workFlow))
		{
			click.clickElementCarefully(workFlow);
		}
	}

	/**
	 * Approves the Workflow
	 */
	public void clickApproveWorkflow(){
		if(element.isElementDisplayed(APPROVE_WORKFLOW)){
			click.clickElementCarefully(APPROVE_WORKFLOW);
			waitForPageLoad();
			wait.waitForElementDisplayed(APPROVE_WORKFLOW_SUBMIT);
			click.clickElementCarefully(APPROVE_WORKFLOW_SUBMIT);
		}
	}

	/**
	 * Validates whether the Workflow is approved or in draft
	 */
	public String validateWorkflowStatus(){
		wait.waitForElementPresent(WORK_FLOW_STATUS,90);
		String status = attribute.getElementAttribute(WORK_FLOW_STATUS, "value");

		return status;
	}

	/**
	 * Clicks the Request Change button
	 */
	public void clickRequestChange(){
		wait.waitForElementDisplayed(REQUEST_CHANGE);
		click.clickElementCarefully(REQUEST_CHANGE);
	}

	/**
	 * Makes the Accounts Payable Workflow active or inactive
	 * @param statusState
	 */
	public void updateWorkFlowStatus(boolean statusState){
		wait.waitForElementDisplayed(WORKFLOW_RADIO_BUTTON);
		click.clickElementCarefully(WORKFLOW_RADIO_BUTTON);

		wait.waitForElementDisplayed(VERSIONS_LINK);
		click.clickElementCarefully(VERSIONS_LINK);

		WebElement statusLabel = wait.waitForElementDisplayed(WORKFLOW_STATUS_LABEL);
		String status = statusLabel.getText();

		if(statusState == true && status.equals("Make active")){
			wait.waitForElementDisplayed(WORKFLOW_STATUS_BUTTON);
			click.javascriptClick(WORKFLOW_STATUS_BUTTON);
		}else if(statusState == false && status.equals("Make inactive")){
			wait.waitForElementDisplayed(WORKFLOW_STATUS_BUTTON);
			click.clickElementCarefully(WORKFLOW_STATUS_BUTTON);
		}

	}

	/**
	 * Clicks and enters the Purchase Requisition Name
	 * @param purchaseRequisitionName
	 */
	public void setPurchaseRequisitionName(String purchaseRequisitionName){
		wait.waitForElementDisplayed(PURCHASE_REQUISITION_NAME);
		text.enterText(PURCHASE_REQUISITION_NAME, purchaseRequisitionName);
	}

	/**
	 * Gets the Purchase Order number from the Purchase Requisition
	 * @return - the purchase order number
	 */
	public String getPurchaseOrderNumberFromPurchaseRequisition(){
		waitForPageLoad();
		wait.waitForElementDisplayed(PURCHASE_ORDER_NUMBER);
		String purchaseOrderNumber = attribute.getElementAttribute(PURCHASE_ORDER_NUMBER, "value");

		return purchaseOrderNumber;
	}

	/**
	 * Click on Financials from the Add line menu options for Purchase Requisition
	 */
	public void clickOnFinancials(){
		if(!element.isElementDisplayed(FINANCIALS)){
			wait.waitForElementDisplayed(RESPONSIVE_BUTTON);
				click.clickElementCarefully(RESPONSIVE_BUTTON);
			waitForPageLoad();
			if(!element.isElementDisplayed(FINANCIALS)) {
				click.javascriptClick(RESPONSIVE_BUTTON);
			}
		}
		WebElement financialEle = wait.waitForElementDisplayed(FINANCIALS);
		click.javascriptClick(financialEle);
		waitForPageLoad();
	}

	/**
	 * Clicks on the Item Sales Tax Group under Setup and selects an option for Purchase Requisition
	 * @param taxGroupType
	 * @param taxGroupValue
	 */
	public void selectItemSalesGroupType(String taxGroupType, String taxGroupValue){
		WebElement taxGroupSelected = wait.waitForElementDisplayed(By.xpath("//input[contains(@name,'SalesTaxGroup_"+taxGroupType+"')]"));
		text.enterText(taxGroupSelected, taxGroupValue+Keys.ENTER);
		waitForPageLoad();
	}

	/**
	 * Clicks the Workflow button for Purchase Requisition
	 */
	public void clickWorkflowForPurchaseRequisition(){
		wait.waitForElementDisplayed(WORKFLOW_PURCHASE_REQUISITION);
		click.clickElementCarefully(WORKFLOW_PURCHASE_REQUISITION);
	}

	/**
	 * Clicks sales tax link from financial dropdown
	 */
	public void clickSalesTaxPurchaseRequisition(){
		waitForPageLoad();
		wait.waitForElementDisplayed(SALES_TAX_FINANCIALS);
		click.clickElementCarefully(SALES_TAX_FINANCIALS);
		waitForPageLoad();
	}

	/**
	 * Creates a PO for the Purchase Requisition
	 */
	public void clickPurchaseOrderForPurchaseRequisition(){
		wait.waitForElementDisplayed(CREATE_PURCHASE_ORDER_FOR_PURCHASE_REQUISITION);
		click.clickElementIgnoreExceptionAndRetry(CREATE_PURCHASE_ORDER_FOR_PURCHASE_REQUISITION);
	}

	/**
	 * Clicks and enters the vendor account for Purchase Order Requisition
	 * @param vendorAccount
	 */
	public void setVendorAccountNumberForPurchaseOrderRequisition( String vendorAccount )
	{
		//expandHeader(VENDOR_HEADING);
		wait.waitForElementDisplayed(VENDOR_ACCOUNT_INPUT_PURCHASE_REQUISITION);
		text.enterText(VENDOR_ACCOUNT_INPUT_PURCHASE_REQUISITION, vendorAccount + Keys.TAB);
		waitForPageLoad();
	}

	/**
	 * Gets the purchase order number from the confirmation
	 */
	public String getPurchaseOrderNumberFromConfirmation(){
		WebElement confirmation = driver.findElement(PURCHASE_ORDER_NUMBER_FROM_CONFIRMATION);

		String confirmationVal = confirmation.getAttribute("title");

		String[] splitString = confirmationVal.split(" ");

		String purchaseOrderNum = splitString[5];

		System.out.println(purchaseOrderNum);

		return purchaseOrderNum;
	}

	/**
	 * Enter created Purchase Order
	 *
	 * @param purchaseOrder
	 */
	public void searchCreatedPurchaseOrder( String purchaseOrder )
	{
		WebElement searchBox = wait.waitForElementEnabled(FILTER_SEARCH_BOX_PURCHASE_ORDER);
		text.enterText(searchBox, purchaseOrder);
		wait.waitForElementDisplayed(FILTER_PURCHASE_ORDER_OPTION);
		click.clickElementCarefully(FILTER_PURCHASE_ORDER_OPTION);

		waitForPageLoad();
	}

	/**
	 * Click on displayed purchase order number
	 */
	public void clickOnDisplayedPurchaseOrderNumber( )
	{
		try
		{
			Thread.sleep(2000);
		}
		catch ( InterruptedException e )
		{
			e.printStackTrace();
		}
		wait.waitForAllElementsDisplayed(SELECT_PURCHASE_ORDER_NUMBER);
		WebElement element2 = element.getWebElement(SELECT_PURCHASE_ORDER_NUMBER);
		String keysPressed = Keys.chord(Keys.CONTROL, Keys.RETURN);
		element2.sendKeys(keysPressed);
		waitForPageLoad();
	}

	/**
	 * Clicks on the Product Tab in the Line Details
	 */
	public void clickOnProductTab(){
		waitForPageLoad();
		wait.waitForElementDisplayed(PRODUCT_TAB);
		click.clickElementIgnoreExceptionAndRetry(PRODUCT_TAB);
	}

	/**
	 * Clicks on a input in the Product Tab
	 * @param productType
	 * @param productValue
	 */
	public void clickAndEnterProductValue(String productType, String productValue){
		WebElement selectProduct = wait.waitForElementDisplayed(By.xpath("//input[contains(@name,'"+productType+"')]"));
		text.clickElementAndEnterText(selectProduct, productValue);
	}

	/**
	 * Toggles the Activate Change Management button
	 * @param statusState
	 */
	public void activateChangeManagementStatus(boolean statusState){

		WebElement statusValue = driver.findElement(ACTIVATE_CHANGE_MANAGEMENT);

		if((statusState == true && statusValue
				.getAttribute("aria-checked")
				.equals("false"))){
			wait.waitForElementDisplayed(ACTIVATE_CHANGE_MANAGEMENT);
			click.javascriptClick(ACTIVATE_CHANGE_MANAGEMENT);
		}else if(statusState == false && (statusValue
				.getAttribute("aria-checked")
				.equals("true"))){
			wait.waitForElementDisplayed(ACTIVATE_CHANGE_MANAGEMENT);
			click.clickElementCarefully(ACTIVATE_CHANGE_MANAGEMENT);
		}
	}

	/** Updates quantity for an item **/
	public void updateItemQuantity( String itemNumber, String updateQuantity )
	{
		wait.waitForElementDisplayed(ADD_NEW_LINE);
		click.clickElementCarefully(ADD_NEW_LINE);
		WebElement quantityField = wait.waitForElementPresent(QUANTITY);
		action.click(quantityField).perform();
		wait.waitForElementEnabled(quantityField);
		try
		{
			quantityField.click();
		}
		catch ( ElementNotInteractableException e )
		{
			click.javascriptClick(quantityField);
		}
		text.setTextFieldCarefully(quantityField, updateQuantity, false);

		// do not overwrite Unit price
		overwriteFieldValue("Unit price", false);
		clickOverwriteOkButton();
		if(element.isElementDisplayed(leadTimeChanged))
		{
			clickOnNoButton();
		}
	}

	/**
	 * Click On No
	 */
	public void clickOnNoButton( )
	{
		click.clickElement(NO_BUTTON);
	}
}

