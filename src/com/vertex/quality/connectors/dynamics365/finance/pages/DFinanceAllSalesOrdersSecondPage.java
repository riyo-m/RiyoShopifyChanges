package com.vertex.quality.connectors.dynamics365.finance.pages;

import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinanceBasePage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinanceBasePage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * All sales orders page common methods and object declaration page
 * First page has become very long so created this one and all new method will be created here related to sales
 *
 * @author SGupta
 */
public class DFinanceAllSalesOrdersSecondPage extends DFinanceBasePage
{
	public DFinanceAllSalesOrdersSecondPage( WebDriver driver )
	{
		super(driver);
	}

	protected By LATEST_SALES_ORDER = By.xpath(".//div[contains(@style, 'visible')]/div//div//div[contains(@class,'dyn-activeRowCell')]//input[contains(@id,'SalesTable_SalesIdAdvanced')]");
	protected By SET_QUANTITY = By.name("specQty");
	protected By HEADER_ELLIPSE = By.xpath("(//div[@class='appBar-toolbar']//div[@title='More'])[2]");
	protected By POPUP_NO = By.name("No");
	protected By SELECT_ALL = By.xpath("//li[contains(@id, 'specQty_list_item1')]");
	protected By OK_BUTTON = By.xpath("//button[@data-dyn-controlname='OK']");
	protected By INVOICE_TAB = By.xpath("//div[@data-dyn-controlname='ActionPaneHeader']//div[@data-dyn-controlname='Invoice']");
	protected By INVOICE = By.xpath("//div[@data-dyn-controlname='InvoiceGenerate']//button[@name='buttonUpdateInvoice']");
	protected By PARAMETER_TAB = By.xpath("//div[@data-dyn-controlname='tabPageParameters']/div");
	protected By SELECT_QUANTITY = By.name("specQty");
	protected By POST_BUTTON = By.xpath("//button[@data-dyn-controlname='Ok']");
	protected By COMPLETE_MSG = By.xpath("//span[@title='Operation completed']");
	protected By FIND_SALES_ORDER = By.xpath("//div[starts-with(@id, 'ReturnTable')]//span[contains(@id, 'ReturnFindSalesOrder_label')]");
	protected By INVOICES_TAB = By.xpath("//button[contains(@id, 'TabPageInvoice_caption')]");
	protected By SELECT_HEADER = By.xpath("(//div[@data-dyn-controlname='InvoiceMarkAll']//div[@tabindex='-1']//div//span)[last()]");
	protected By CLICK_OK = By.name("OK");
	protected By RETURN_ORDER_HEADER = By.xpath("//div[contains(@id, 'LineViewHeader_header')]/button");
	protected By LINE_DETAILS_HEADER = By.xpath("//div[contains(@id, 'LineViewLineDetails_header')]/button");
	protected By RETURN_REASON = By.name("ReturnOrder_ReturnReasonCodeId");
	protected By DISPOSITION_CODE = By.name("Return_ReturnDispositionCodeId");
	protected By SAVE_BUTTON = By.name("SystemDefinedSaveButton");
	protected By INVOICE_JOURNAL = By.name("buttonJournalInvoice");
	protected By POSTED_TAX = By.name("TaxTransactions");
	protected By SELECT_SALES_ORDER = By.xpath("//div[@data-dyn-rowid='1']//input[@name='SalesTable_SalesIdAdvanced']");
	protected By ROW_DESCRIPTION = By.name("VTXVertexTaxDescription");
	protected By TOGGLE_VALUE = By.xpath("//span[contains(@id,'TaxGroup_toggle')]");
	protected By TOGGLE = By.xpath("//span[contains(@id,'TaxGroup_toggle')]");
	protected By OK = By.xpath("//span[text()='OK']");
	protected By DISCOUNT = By.xpath("//div[@data-dyn-controlname='SalesLine_LineDiscGrid']//span[@title]");
	protected By SALES_ORDER_LINE = By.xpath("//span[text()='Sales order line']");
	protected By SET_UP = By.xpath("//li[@data-dyn-controlname='TabLineSetup_header']");
	protected By VENDOR_INVOICE_ENTRY_SET_UP = By.xpath("//li[@data-dyn-controlname='AdditionalLineDetails_header']");
	protected By PRODUCT_TAB = By.xpath("//li[@data-dyn-controlname='TabPageProduct_header']");
	protected By COMMODITY_CODE_INPUT = By.name("ForeignTrade_IntrastatCommodity_Code");
	protected By ITEM_SALES_TAX_GROUP = By.xpath("//input[contains(@name,'SalesTax_TaxItemGroup')]");
	protected By DELIVERY_HEADER = By.cssSelector("[id$=TabLineDelivery_header]");
	protected By MODE_OF_DELIVERY = By.name("SalesLineDeliveryGroup_DlvMode");
	protected By UNDER_DELIVERY = By.xpath("//div[@data-dyn-controlname=\"SalesLineDeliveryGroup_UnderDeliveryPct\"]//input");
	protected By PRICE_AND_DISCOUNT = By.xpath("//span[text()='Price and discount']");
	protected By MULTILINE_DISCOUNT_PERCENTAGE = By.name("SalesLine_MultilinePurchasePercent");
	protected By REASON_CODE = By.name("RetailSalesLine_PriceOverrideReasonCode");
	protected By PRICE_DETAILS= By.xpath("//span[text()='Price details']");
	protected By DELIVERY = By.xpath("//li[@data-dyn-controlname='TabLineDelivery_header']");
	protected By RETAIL_DISCOUNTS = By.cssSelector("[id$=TabRetailDiscounts]");
	protected By DISCOUNT_CODES = By.xpath("//input[@name='PeriodicDiscountOfferId']");
	protected By CLOSE = By.cssSelector("button[id*='MCRPriceHistory'][command='PreviousView']");
	protected By COMPLETE = By.cssSelector("span[id*='Complete_label']");
	protected By BALANCE = By.cssSelector("input[id*='Balance']");
	protected By PAYMENTS = By.xpath("//div[contains(@id,'SalesOrderSummaryPayments_header')]/button");
	protected By ADD = By.cssSelector("span[id*='AddBtn_label']");
	protected By PAYMENT_METHOD = By.cssSelector("[id*='Identification_TenderTypeId_input']");
	protected By PAYMENT_AMOUNT = By.cssSelector("[id*='Identification_Amount_input']");
	protected By SUBMIT = By.xpath("(//span[text()='Submit'])");
	protected By CONFIRM_SALES_ORDER = By.xpath("//span[text()='Confirm sales order']");
	protected By SALES_ORDER_HEADER = By.cssSelector("[data-dyn-controlname='LineViewHeader']");
	protected By DOM_STATUS = By.cssSelector("[id*='DOMStatus_input']");
	protected By qtyLines = By.xpath("//div[@id='lb-SalesLineGrid']//div[@class='listBody']/div");
	protected By INV_TAB = By.xpath("(//div[@data-dyn-controlname='Invoice'])[2]//span[text()='Invoice']");
	protected By GENERATE_INVOICE = By.xpath("//div[@data-dyn-controlname='InvoiceGenerate']//button[@data-dyn-controlname='buttonUpdateInvoice']");
	protected By JOURNAL_INVOICE = By.xpath("//div[contains(@id,'InvoiceJournals')]//button[@data-dyn-controlname='buttonJournalInvoice']");
	protected By SELECT_JOURNAL_INVOICE = By.cssSelector("input[id*='CustInvoiceJour_InvoiceNum_Grid']");
	protected By POSTED_SALES_TAX = By.xpath("//span[text()='Posted sales tax']");
	protected By SUMMARY_ORDER = By.name("SumBy");
	protected By INVOICE_ACCOUNT = By.xpath("//li[text()='Invoice account']");
	protected By ARRANGE_INVOICE = By.xpath("//span[text()='Arrange']");
	protected By ADD_ANOTHER_INVOICE = By.xpath("//button[@data-dyn-controlname='AddHeaderButton']");
	protected By CURRENT_INVOICE_VALUE = By.xpath("//div[@data-dyn-controlname='SalesParmTable_SalesId']//input[contains(@id, 'SalesParmTable_SalesId')]");
	protected By NEW_INVOICE_VALUE = By.xpath("(//div[@data-dyn-controlname='SalesParmTable_SalesId']//input[contains(@id, 'SalesParmTable_SalesId')])[2]");
	protected By NEW_INVOICE_SELECT_VALUE = By.xpath("//input[@data-dyn-controlname='Sel']");
	protected By SELECT_QTY = By.name("specQty");
	protected By OK_BTN = By.name("OK");
	protected By DELIVERY_ADDRESS_DROPDOWN = By.xpath("//input[@name='SalesLine_DeliveryPostalAddress_Location_Description']");
	protected By INVOICE_LINK = By.cssSelector("span[id*='buttonJournalInvoice_label'][id*='SalesTable']");
	protected By ADDRESS_TAB = By.xpath("//span[contains(text(),'Address')]");
	protected By LINE_DETAILS = By.xpath("//*[contains(text(),'Line details')]");
	protected By ADD_NEW_LINE = By.xpath("//span[text()='Add line']");
	protected By LATEST_ORDER = By.xpath(".//div[contains(@style, 'visible')]/div/div/div/div/div//div[contains(@class,'dyn-activeRowCell')]//input[contains(@id,'SalesTable_SalesIdAdvanced')]");
	protected By RADIO_BUTTON = By.xpath("//div[contains(@class,'dyn-activeRowCell')]//div[@class=\"dyn-hoverMarkingColumn\"]");
	protected By HORIZONTAL_SCROLLBAR = By.xpath("//div[contains(@class,'faceHorizontal')]");

	protected By FIND_SALES_ORDER_RETURN_ORDER = By.xpath("(//span[text()='Find sales order'])[2]");
	protected By RETAIL_RETURN_REASON_CODE = By.name("RetailSalesLine_ReturnReasonCodeId");
	protected By SELECT_RETURN_ITEM = By.xpath("//input[contains(@id, 'CustInvoiceTrans_ItemId')]//..//..//..//..//..//..//..//..//div//span");
	protected By RETURNED_QUANTITY_FIELD = By.xpath("//input[@aria-label='Quantity to return' and contains(@id, '0_input')]");
	protected By CLICK_CALENDER_BUTTON = By.xpath("//div[@data-dyn-controlname='SalesParmTable_Transdate']//div[@class='lookupButton']");

	protected By STATUS = By.xpath(".//div[text()='Status']");
	protected By QUANTITY_LIST = By.xpath("//input[@name='PurchLine_PurchQtyGrid']");
	protected By SELECTED_INVOICE_ITEMS = By.xpath("//div[@aria-label='Sales order line - update table']//..//..//div[@class='dyn-hoverMarkingColumn']");
	protected By DELETE_ITEMS = By.name("DeleteLineButton");
	protected By SELECTED_INVOICE_NUMBER = By.xpath("//input[contains(@id, 'InvoiceNum')]");
	protected By POSTED_SALES_TAX_TAX_LINES = By.xpath("//input[@aria-label='Actual sales tax amount']");
	Actions action = new Actions(driver);
	String quantityUpdatedStringPurchaseOrder = "(//div[@data-dyn-controlname='PurchLine_PurchQtyGrid'])[%s]//input";
	String getQuantityUpdatedStringSalesOrder = "(//div[@data-dyn-controlname='SalesLine_SalesQty'])[%s]//input";
	String unitPriceUpdatedStringPurchaseOrder = "(//div[@data-dyn-controlname='PurchLine_PurchPriceGrid'])[%s]//input";
	String unitPriceUpdatedStringSalesOrder = "(//div[@data-dyn-controlname='SalesLine_SalesPrice'])[%s]//input";
	String siteUpdatedString = "(//div[@data-dyn-controlname='InventoryDimensionsGrid_InventSiteId']//div//input)[%s]";
	String wareHouseUpdatedString = "(//div[@data-dyn-controlname='InventoryDimensionsGrid_InventLocationId']//div//input)[%s]";
	String discountPercentageUpdatedStringPurchaseOrder = "(//div[@data-dyn-controlname='PurchLine_LinePercentGrid'])[%s]//input";
	String discountPercentageUpdatedStringSalesOrder = "(//div[@data-dyn-controlname='SalesLine_LinePercentGrid'])[%s]//input";
	String enterProductType = "InventoryDimensions_Invent%sId";
	/**
	 * validate sales order status
	 */

	public String salesOrderValidation( )
	{
		WebElement scrollBar = wait.waitForElementDisplayed(By.xpath("//div[contains(@class,'faceHorizontal')]"));
		click.clickElement(scrollBar);
		action.moveToElement(scrollBar).clickAndHold();
		action.moveByOffset(0,0).release().perform();
		//wait.waitForElementDisplayed(LATEST_SALES_ORDER);
		String statusText = driver.findElement(By.xpath("//div[@data-dyn-value='1']//input[@title='Open order']")).getAttribute("title");
		System.out.println(statusText);
		return statusText;
	}

	/**
	 * click invoice button
	 */
	public void clickInvoice( )
	{
		if (!element.isElementDisplayed(INVOICE_TAB))
		{
			//Navigate to "Invoice" Tab
			DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
			allSalesOrdersPage.clickOnTab("Invoice");
			waitForPageLoad();
			wait.waitForElementDisplayed(INVOICE);
			try {
				click.clickElementCarefully(INVOICE);
			}catch (Exception ex){}
		}
		else {
			wait.waitForElementPresent(INVOICE_TAB);
			click.clickElementCarefully(INVOICE_TAB);
			wait.waitForElementDisplayed(INVOICE);
			click.clickElementCarefully(INVOICE);
		}
		waitForPageLoad();
	}

	/**
	 * click ok button to post invoice
	 */
	public void clickOk( )
	{
		wait.waitForElementDisplayed(PARAMETER_TAB);
		wait.waitForElementDisplayed(SELECT_QUANTITY);
		click.clickElementCarefully(SELECT_QUANTITY);
		wait.waitForElementDisplayed(SELECT_ALL);
		click.clickElementCarefully(SELECT_ALL);
		wait.waitForElementDisplayed(OK_BUTTON);
		click.clickElementCarefully(OK_BUTTON);
		wait.waitForElementDisplayed(POST_BUTTON);
		click.clickElementCarefully(POST_BUTTON);
		waitForPageLoad();
		wait.waitForElementDisplayed(COMPLETE_MSG);
	}

	/**
	 * set quantity
	 */
	public void setQuantity( )
	{
		if (element.isElementDisplayed(POPUP_NO))
		{
			wait.waitForElementDisplayed(POPUP_NO);
			click.clickElementCarefully(POPUP_NO);
		}
		wait.waitForElementDisplayed(OK_BUTTON);
		click.clickElementIgnoreExceptionAndRetry(OK_BUTTON);
		wait.waitForElementDisplayed(POST_BUTTON);
		click.clickElementIgnoreExceptionAndRetry(POST_BUTTON);
		wait.waitForElementDisplayed(COMPLETE_MSG);
	}

	/**
	 * FIND SALES ORDER TO RETURN
	 */
	public void findReturnSalesOrder( )
	{
		wait.waitForElementDisplayed(FIND_SALES_ORDER);
		click.clickElementCarefully(FIND_SALES_ORDER);
		wait.waitForElementDisplayed(INVOICES_TAB);
		String isExpanded = attribute.getElementAttribute(INVOICES_TAB, "aria-expanded");
		if ( !isExpanded.equalsIgnoreCase("true") )
		{
			click.clickElementCarefully(INVOICES_TAB);
		}
		wait.waitForElementDisplayed(SELECT_HEADER);
		click.clickElementCarefully(SELECT_HEADER);
		wait.waitForElementDisplayed(CLICK_OK);
		click.clickElementCarefully(CLICK_OK);
		waitForPageLoad();
	}

	/**
	 * Enter return reason and disposition code
	 */
	public void returnReason(String reason, String code )
	{
		wait.waitForElementDisplayed(RETURN_ORDER_HEADER);
		String isExpanded = attribute.getElementAttribute(RETURN_ORDER_HEADER, "aria-expanded");
		if ( isExpanded == null || !isExpanded.equalsIgnoreCase("true") )
		{
			click.clickElementCarefully(RETURN_ORDER_HEADER);
		}
		waitForPageLoad();
		wait.waitForElementDisplayed(RETURN_REASON);
		click.clickElementCarefully(RETURN_REASON);
		text.enterText(RETURN_REASON, reason + Keys.ENTER);
		wait.waitForElementDisplayed(LINE_DETAILS_HEADER);
		String isExpandedLine = attribute.getElementAttribute(LINE_DETAILS_HEADER, "aria-expanded");
		if (isExpandedLine == null || !isExpandedLine.equalsIgnoreCase("true") )
		{
			click.clickElementCarefully(LINE_DETAILS_HEADER);
		}
		waitForPageLoad();
		wait.waitForElementDisplayed(DISPOSITION_CODE);
		click.clickElementCarefully(DISPOSITION_CODE);
		text.enterText(DISPOSITION_CODE, code + Keys.ENTER);
		wait.waitForElementDisplayed(SAVE_BUTTON);
		click.clickElementCarefully(SAVE_BUTTON);
		waitForPageLoad();
	}

	/**
	 * SELECT RETURNED SALES ORDER
	 * @param rowActive
	 */
	public void selectReturnedOrder(String rowActive)
	{
		WebElement selectRowEle = wait.waitForElementDisplayed(By.xpath(".//div[contains(@style, 'visible')]/div[@data-dyn-row-active='true']/.//input[contains(@id,'SalesTable_SalesIdAdvanced')]"));
		click.clickElementCarefully(selectRowEle);
		selectRowEle.sendKeys(Keys.ENTER);
		waitForPageLoad();
	}

	/**
	 * Count number of row displayed
	 */
	public void countRows( )
	{
		wait.waitForElementDisplayed(ROW_DESCRIPTION);
		List<WebElement> rows = driver.findElements(By.xpath("//input[@name='VTXVertexTaxDescription']"));
		SoftAssert softassert = new SoftAssert();
		softassert.assertEquals(rows.size(), "1");
	}

	/**
	 * Updates Toggle value
	 */
	public void updateSalesTaxGroupToggle()
	{
		WebElement toggleValue = wait.waitForElementPresent(TOGGLE_VALUE);
		if (attribute.getElementAttribute(toggleValue, "aria-checked").equalsIgnoreCase("false"))
		{
			click.clickElementCarefully(TOGGLE);
		}
	}

	/**
	 * Click on OK
	 */
	public void clickOnOK()
	{
		click.clickElementCarefully(OK);
	}

	/**
	 * Get Discount
	 */
	public String getDiscount()
	{
		return attribute.getElementAttribute(DISCOUNT, "title");
	}

	/**
	 * Click on update line from the Add line menu options
	 */
	public void clickOnSalesOrderLineTab( )
	{
		wait.waitForElementDisplayed(SALES_ORDER_LINE);
		click.javascriptClick(SALES_ORDER_LINE);
	}

	/**
	 * Clicks on the Setup Tab in the Line Details
	 */
	public void clickOnSetUpTab(){
		if(element.isElementPresent(SET_UP)){
			wait.waitForElementDisplayed(SET_UP);
			click.clickElementCarefully(SET_UP);
		}else if (element.isElementPresent(VENDOR_INVOICE_ENTRY_SET_UP)){
			wait.waitForElementDisplayed(VENDOR_INVOICE_ENTRY_SET_UP);
			click.javascriptClick(VENDOR_INVOICE_ENTRY_SET_UP);
		}

	}

	/**
	 * Clicks on the Product Tab in the Line Details
	 */
	public void clickOnProductTab(){
		wait.waitForElementDisplayed(PRODUCT_TAB);
		click.clickElementCarefully(PRODUCT_TAB);
	}

	/**
	 * Clicks the Commodity Code dropdown and selects an option
	 * @param commodityCode
	 */
	public void clickCommodityCodeAndEnterOption(String commodityCode){
		wait.waitForElementDisplayed(COMMODITY_CODE_INPUT);
		text.setTextFieldCarefully(COMMODITY_CODE_INPUT, commodityCode);
	}

	/**
	 * Clicks on the size in the Product Tab
	 * @param productType
	 * @param productValue
	 */
	public void clickAndEnterProductValue(String productType, String productValue){
		WebElement selectProduct = wait.waitForElementDisplayed(By.name(String.format(enterProductType, productType)));
		text.clickElementAndEnterText(selectProduct, productValue);
	}

	/**
	 * Clicks on the Item Sales Tax Group under Setup and selects an option
	 * @param itemSalesGroupType
	 */
	public void selectItemSalesGroupType(String itemSalesGroupType){
		wait.waitForElementDisplayed(ITEM_SALES_TAX_GROUP);
		text.enterText(ITEM_SALES_TAX_GROUP, itemSalesGroupType);
		waitForPageLoad();
	}

	/**
	 * Change Delivery mode
	 * @param deliveryMode
	 */
	public void changeDeliveryMode(String deliveryMode)
	{
		this.expandHeader(DELIVERY_HEADER);
		WebElement modeOfDeliveryEle = wait.waitForElementDisplayed(MODE_OF_DELIVERY);
		text.clickElementAndEnterText(modeOfDeliveryEle,deliveryMode);
	}

	/**
	 * Change Underdelivery
	 * @param underDelivery
	 */
	public void changeUnderDelivery(String underDelivery){
		WebElement underDeliveryEle = wait.waitForElementDisplayed(UNDER_DELIVERY);
		text.clearText(underDeliveryEle);
		text.clickElementAndEnterText(underDeliveryEle,underDelivery);
	}

	/**
	 * Clicks on the Delivery tab in the Line Details
	 */
	public void clickOnDelivery()
	{
		WebElement deliveryEle = wait.waitForElementPresent(DELIVERY);
		scroll.scrollElementIntoView(deliveryEle);
		click.javascriptClick(deliveryEle);
		wait.waitForElementPresent(DELIVERY,10);
	}

	/**
	 * Clicks on Price Details
	 */
	public void clickOnPriceDetails()
	{
		WebElement priceDetailsEle = wait.waitForElementPresent(PRICE_DETAILS);
		scroll.scrollElementIntoView(priceDetailsEle);
		click.javascriptClick(priceDetailsEle);
		wait.waitForElementPresent(RETAIL_DISCOUNTS,10);
	}

	/**
	 * Clicks on the Price and discount tab in the line details
	 */
	public void clickPriceAndDiscount(){
		wait.waitForElementPresent(PRICE_AND_DISCOUNT);
		click.clickElementCarefully(PRICE_AND_DISCOUNT);
	}

	/**
	 * Enters in the multiline discount percentage
	 * @param discountPercentage
	 */
	public void multilineDiscountPercentage(String discountPercentage){
		wait.waitForElementPresent(MULTILINE_DISCOUNT_PERCENTAGE);
		text.clickElementAndEnterText(MULTILINE_DISCOUNT_PERCENTAGE, discountPercentage);
	}

	/**
	 * Enters the Reason Code
	 * @param reasonCode
	 */
	public void reasonCode(String reasonCode){
		wait.waitForElementPresent(REASON_CODE);
		text.clickElementAndEnterText(REASON_CODE, reasonCode);
	}

	/**
	 * Getting the discount codes in String Array
	 * @return
	 */
	public String[] getDiscountCodes()
	{
		List<String> listOfDiscountCodes = new ArrayList<>();
		String discountCodes;
		String[] discountCodesArr = new String[0];
		List<WebElement> discountCodesEle = wait.waitForAllElementsPresent(DISCOUNT_CODES);
		for (int i=0;i<discountCodesEle.size();i++)
		{
			discountCodes = attribute.getElementAttribute(discountCodesEle.get(i),"title");
			if(discountCodes.contains("Click to follow link"))
			{
				String [] array = discountCodes.split(" ");
				if(array[0].contains("\n"))
				{
					array = array[0].split("\n");
					discountCodes = array[0];
				}
			}
			listOfDiscountCodes.add(discountCodes);
		}
		return listOfDiscountCodes.toArray(new String[0]);
	}

	/**
	 * closing details window
	 */
	public void closeDetailsWindow()
	{
		click.clickElementCarefully(CLOSE);
	}

	/**
	 * Clicks on Complete
	 */
	public void clickOnComplete()
	{
		click.clickElementCarefully(COMPLETE);
	}

	/**
	 * Clicks on Submit Button
	 */
	public void clickOnSubmit()
	{
		try{
			click.clickElementCarefully(SUBMIT);
		}catch (Exception e){
			click.javascriptClick(SUBMIT);
		}
		jsWaiter.sleep(60000);
	}

	/**
	 * Clicks on the Confirm Sales Order Button
	 */
	public void clickOnConfirmSalesOrder(){
		waitForPageLoad();
		wait.waitForElementDisplayed(CONFIRM_SALES_ORDER);
		click.clickElementIgnoreExceptionAndRetry(CONFIRM_SALES_ORDER);
	}

	/**
	 * This Methods returns the Balance
	 * @return
	 */
	public String getBalance()
	{
		return attribute.getElementAttribute(BALANCE,"title");
	}

	/**
	 * This Method add the payment
	 * @param amount
	 * @param paymentType
	 */
	public void addPayment(String amount, String paymentType)
	{
		waitForPageLoad();
		if(!element.isElementDisplayed(ADD))
			this.expandHeader(PAYMENTS);
		waitForPageLoad();
		click.javascriptClick(ADD);
		waitForPageLoad();
		wait.waitForElementDisplayed(PAYMENT_METHOD);
		click.clickElementCarefully(PAYMENT_METHOD);

		click.clickElementCarefully(wait.waitForElementPresent(By.xpath("//*[contains(@id,'TenderTypeId')]/div/div")));

		click.clickElementCarefully(By.xpath("//input[@title='"+paymentType+"']"));

		text.clickElementAndEnterText(PAYMENT_AMOUNT,amount);
		clickOnOK();
		try{
			click.clickElementCarefully(SUBMIT);
		}catch(Exception e){
			click.javascriptClick(SUBMIT);
		}
	}

	/**
	 * This Method Expand Sales Order Header
	 */
	public void expandSalesOrderHeader()
	{
		this.expandHeader(SALES_ORDER_HEADER);
	}

	/**
	 * This Method Verifies DOM Status
	 * @return
	 */
	public boolean verifyDOMStatus()
	{
		WebElement domStatusEle = wait.waitForElementDisplayed(DOM_STATUS);
		return attribute.getElementAttribute(domStatusEle,"title").equalsIgnoreCase("Successful");
	}

	/**
	 * It verifies number of Quantity Lines
	 * @return
	 */
	public boolean verifyMoreThanOneQtyLines()
	{
		return wait.waitForAllElementsPresent(qtyLines).size() > 1;
	}

	/**
	 * open the Sales tax results dialog
	 * @author Vishwa
	 */
	public void clickOnInvoiceTab()
	{
		waitForPageLoad();
		if(element.isElementPresent(HEADER_ELLIPSE) && !element.isElementDisplayed(INV_TAB)){
			click.clickElementIgnoreExceptionAndRetry(HEADER_ELLIPSE);
			waitForPageLoad();
		}
		wait.waitForElementDisplayed(INV_TAB);
		click.javascriptClick(INV_TAB);
		waitForPageLoad();
	}

	/**
	 *  open the Invoice link in the Generate Column
	 * @author Vishwa
	 */
	public void generateInvoice( )
	{
		wait.waitForElementDisplayed(GENERATE_INVOICE);
		click.javascriptClick(GENERATE_INVOICE);
		waitForPageLoad();
	}

	/**
	 * Selects the amount of lines to generate invoice
	 * @param selectedInvoices
	 */
	public void selectLinesToGenerateInvoice(int selectedInvoices){
		List <WebElement> selectedLines = wait.waitForAllElementsDisplayed(SELECTED_INVOICE_ITEMS);
		click.clickElementCarefully(selectedLines.get(selectedInvoices));
	}

	/**
	 * Deletes the selected line
	 */
	public void deleteSelectedLine(){
		wait.waitForElementDisplayed(DELETE_ITEMS);
		click.clickElementCarefully(DELETE_ITEMS);
	}

	/**
	 * open the Invoice link in the Journal Column
	 * @author Mario Saint-Fleur
	 */
	public void journalInvoice( )
	{
		jsWaiter.sleep(5000);
		wait.waitForElementDisplayed(JOURNAL_INVOICE);
		click.javascriptClick(JOURNAL_INVOICE);
		waitForPageLoad();

		if(element.isElementPresent(SELECT_JOURNAL_INVOICE)) {
			wait.waitForElementDisplayed(SELECT_JOURNAL_INVOICE);
			click.clickElementCarefully(SELECT_JOURNAL_INVOICE);
			waitForPageLoad();
		}
	}

	/**
	 * Selects the invoice number
	 * @param selectedElement
	 */
	public void selectInvoiceNumberForInvoiceJournal(int selectedElement){
		wait.waitForElementDisplayed(JOURNAL_INVOICE);
		click.javascriptClick(JOURNAL_INVOICE);

		jsWaiter.sleep(10000);
		WebElement invoiceNumbers = wait.waitForElementDisplayed(By.xpath("(//input[contains(@id, 'InvoiceNum')])["+selectedElement+"]"));
		click.javascriptClick(invoiceNumbers);
		System.out.println(invoiceNumbers);
	}

	/**
	 * Select the Posted Sales Tax Tab
	 * @author Mario Saint-Fleur
	 */
	public void clickOnPostedSalesTax(){
		wait.waitForElementDisplayed(POSTED_SALES_TAX);
		click.clickElementCarefully(POSTED_SALES_TAX);
		waitForPageLoad();
	}


	/**
	 * Click on the Invoice account under Summary Order and
	 * Arrange Tab at the top of the Posting Invoice
	 * @author Mario Saint-Fleur
	 */
	public void postedInvoiceAccountAndArrange(){
		wait.waitForAllElementsDisplayed(SUMMARY_ORDER);
		click.clickElementCarefully(SUMMARY_ORDER);
		wait.waitForAllElementsDisplayed(INVOICE_ACCOUNT);
		click.clickElementCarefully(INVOICE_ACCOUNT);
		wait.waitForAllElementsDisplayed(ARRANGE_INVOICE);
		click.clickElementCarefully(ARRANGE_INVOICE);
	}

	/**
	 * In the Overview Section of the Posting Invoice
	 * Add the second most recent Invoice
	 * @author Mario Saint-Fleur
	 */
	public void addASecondInvoiceToPostingInvoice(){

		WebElement currentVal = driver.findElement(CURRENT_INVOICE_VALUE);

		String currentInvoiceVal = currentVal.getAttribute("title");

		String[] actualNum = currentInvoiceVal.split(" ");

		String invoiceNum = actualNum[0].substring(0, 6);

		int previousInvoice = Integer.parseInt(invoiceNum) - 1;

		String prevInvoice = "0" + String.valueOf(previousInvoice);

		wait.waitForAllElementsDisplayed(ADD_ANOTHER_INVOICE);
		click.clickElementCarefully(ADD_ANOTHER_INVOICE);

		wait.waitForAllElementsDisplayed(NEW_INVOICE_VALUE);
		click.clickElementCarefully(NEW_INVOICE_VALUE);

		WebElement newVal = driver.findElement(NEW_INVOICE_VALUE);

		click.clickElementCarefully(newVal);
		text.setTextFieldCarefully(newVal, prevInvoice, false);
		newVal.sendKeys(Keys.ENTER);

	}


	/**
	 * Enter the item quantity
	 * @author Vishwa
	 */
	public void selectAllInQuantity(String Quantity )
	{
		wait.waitForElementDisplayed(SELECT_QTY);
		click.clickElement(SELECT_QTY);
		text.pressEnter(SELECT_QTY);
		waitForPageLoad();
	}

	/**
	 * Click on "OK" button to close the dialog
	 * @author Vishwa
	 */
	public void clickOnOk()
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(OK_BTN);
		click.clickElementCarefully(OK_BTN);
		waitForPageLoad();
	}

	/**
	 * Add multiple line item to the PO
	 * @Author Vishwa
	 * @param item
	 * @param qty
	 * @param site
	 * @param warehouse
	 * @param unitPrice
	 * @param itemIndex
	 */
	public void fillFirstItemsInfo ( String item, String site, String warehouse, String qty, String unitPrice, int itemIndex ) {

		waitForPageLoad();
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
	 * Add multiple line item to the PO
	 * @Author Vishwa
	 * @param item
	 * @param qty
	 * @param site
	 * @param warehouse
	 * @param unitPrice
	 * @param itemIndex
	 */
	public void fillItemsInfo ( String item, String site, String warehouse, String qty, String unitPrice, int itemIndex ) {

		wait.waitForElementDisplayed(ADD_NEW_LINE);
		click.javascriptClick(ADD_NEW_LINE);

		int itemNumber = itemIndex + 2;
		itemIndex = itemIndex+1;
		DFinanceBasePage purchasePage = new DFinanceBasePage(driver);

		purchasePage.enterItem(item, itemIndex);
		purchasePage.enterQuantity(qty, itemNumber);
		purchasePage.enterSite(site, itemNumber);
		purchasePage.enterWarehouse(warehouse, itemNumber);
		purchasePage.enterunitPrice(unitPrice, itemNumber);
		if(element.isElementPresent(OVERWRITE_OK_BUTTON))
		{
			clickOverwriteOkButton();
		}
	}

	/**
	 * Add multiple line item to the PO
	 * @Author Mario Saint-Fleur
	 * @param item
	 * @param qty
	 * @param site
	 * @param warehouse
	 * @param unitPrice
	 * @param discount
	 * @param itemIndex
	 */
	public void fillItemsInfo ( String item, String site, String warehouse, String qty, String unitPrice, String discount, int itemIndex ) {

		wait.waitForElementDisplayed(ADD_NEW_LINE);
		click.javascriptClick(ADD_NEW_LINE);

		int itemNumber = itemIndex + 2;
		itemIndex = itemIndex+1;
		DFinanceBasePage purchasePage = new DFinanceBasePage(driver);

		purchasePage.enterItem(item, itemIndex);
		purchasePage.enterQuantity(qty, itemNumber);
		purchasePage.enterSite(site, itemNumber);
		purchasePage.enterWarehouse(warehouse, itemNumber);
		purchasePage.enterunitPrice(unitPrice, itemNumber);
		purchasePage.enterDiscount(discount, itemNumber);
	}

	/**
	 * Add multiple line item to the PO
	 * @Author Mario Saint-Fleur
	 * @param item
	 * @param qty
	 * @param itemIndex
	 */
	public void fillItemsInfoForUSRT ( String item, String qty, int itemIndex ) {

		WebElement newLine = wait.waitForElementDisplayed(ADD_NEW_LINE);
		action.moveToElement(newLine).click(newLine).perform();

		int itemNumber = itemIndex + 2;
		itemIndex = itemIndex+1;
		DFinanceBasePage purchasePage = new DFinanceBasePage(driver);

		purchasePage.enterItem(item, itemIndex);
		purchasePage.enterQuantity(qty, itemNumber);
	}

	/**
	 * Add multiple line item to the PO
	 * @Author Mario Saint-Fleur
	 * @param item
	 * @param qty
	 * @param site
	 * @param warehouse
	 * @param unitPrice
	 * @param discountPercentage
	 * @param itemIndex
	 */
	public void fillItemsInfoWithDiscountPercentage ( String item, String site, String warehouse, String qty, String unitPrice, String discountPercentage, int itemIndex ) {

		wait.waitForElementDisplayed(ADD_NEW_LINE);
		click.javascriptClick(ADD_NEW_LINE);

		int itemNumber = itemIndex + 2;
		itemIndex = itemIndex+1;
		DFinanceBasePage purchasePage = new DFinanceBasePage(driver);

		purchasePage.enterItem(item, itemIndex);
		purchasePage.enterQuantity(qty, itemNumber);
		purchasePage.enterSite(site, itemNumber);
		purchasePage.enterWarehouse(warehouse, itemNumber);
		purchasePage.enterunitPrice(unitPrice, itemNumber);
		purchasePage.enterDiscountPercentage(discountPercentage, itemNumber);
	}

	/**
	 * Add line items for All Free Invoice
	 * @Author Mario Saint-Fleur
	 * @param mainAccount
	 * @param itemSalesTaxGroup
	 * @param unitPrice
	 */
	public void fillItemsInfoForFreeTextInvoice ( String mainAccount, String itemSalesTaxGroup, String unitPrice, int itemIndex ) {

		wait.waitForElementDisplayed(ADD_NEW_LINE);
		click.javascriptClick(ADD_NEW_LINE);

		int itemNumber = itemIndex + 2;
		DFinanceBasePage purchasePage = new DFinanceBasePage(driver);

		purchasePage.enterMainAccount(mainAccount, itemNumber);
		purchasePage.enterItemSalesTaxGroup(itemSalesTaxGroup, itemNumber);
		purchasePage.enterunitPrice(unitPrice, itemNumber);
	}




	/**
	 * This method is used to update delivery address for second line item
	 * @param updateDeliveryAddress
	 *
	 * @author Vishwa
	 */
	public void updateDeliveryAddress(String updateDeliveryAddress)
	{
		wait.waitForElementDisplayed(LINE_DETAILS);
		scroll.scrollElementIntoView(LINE_DETAILS);
		wait.waitForElementDisplayed(ADDRESS_TAB);
		click.clickElementCarefully(ADDRESS_TAB);
		waitForPageLoad();
		WebElement deliveryDropdown = wait.waitForElementDisplayed(DELIVERY_ADDRESS_DROPDOWN);
		click.clickElementCarefully(deliveryDropdown);
		if(element.isElementPresent(CLOSE_BUTTON_SALES_ORDER)){
			click.clickElementCarefully(CLOSE_BUTTON_SALES_ORDER);
		}
		text.clearText(deliveryDropdown);
		text.enterText(deliveryDropdown, updateDeliveryAddress);
		waitForPageLoad();
		WebElement deliveryOption = wait.waitForElementPresent(By.xpath("//input[@value='"+updateDeliveryAddress+"']"));
		click.clickElementIgnoreExceptionAndRetry(deliveryOption);
		waitForPageLoad();
	}

	/**
	 * open the Invoice tax results dialog
	 * @author Vishwa
	 */
	public void clickOnInvoice()
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(INVOICE_LINK);
		click.clickElementCarefully(INVOICE_LINK);
		waitForPageLoad();
	}

	/**
	 * Click on latest Sales Order Created to open it
	 */
	public void clickOnLatestOrderCreated()
	{
		waitForPageLoad();
		if (element.isElementDisplayed(HORIZONTAL_SCROLLBAR)) {
			WebElement scrollBar = wait.waitForElementDisplayed(HORIZONTAL_SCROLLBAR);
			click.clickElement(scrollBar);
			action.moveToElement(scrollBar).clickAndHold().perform();
			action.moveByOffset(-220, 0).release().perform();
		}
		WebElement latestOrderEle = wait.waitForElementPresent(LATEST_ORDER);
		click.clickElementIgnoreExceptionAndRetry(latestOrderEle);
		latestOrderEle.sendKeys(Keys.ENTER);
		waitForPageLoad();
	}

	/**
	 * Filter with Order type
	 */
	public void applyFilterOnStatus(String status)
	{
		WebElement statusEle = wait.waitForElementPresent(STATUS);
		action.moveToElement(statusEle).perform();
		action.moveToElement(statusEle).click(statusEle).perform();
		if (!element.isElementDisplayed(FILTER_INPUT)) {
			click.clickElementIgnoreExceptionAndRetry(STATUS);
		}
		wait.waitForElementPresent(FILTER_INPUT);
		text.enterText(FILTER_INPUT,status);
		click.clickElementCarefully(APPLY_FILTER);
	}

	/**
	 * Clicks the Find sales Order link
	 */
	public void findSalesOrder(){
		wait.waitForElementDisplayed(FIND_SALES_ORDER_RETURN_ORDER);
		click.clickElementCarefully(FIND_SALES_ORDER_RETURN_ORDER);
	}

	/**
	 * Finds the most recent Sales Order and selects all its items
	 * @param salesOrderNumber
	 */
	public void selectRecentSalesOrder(String salesOrderNumber){
		By salesOrderCheckBox = By.xpath("//input[contains(@value, "+salesOrderNumber+")]//..//..//..//..//..//..//..//..//div//span");
		wait.waitForElementPresent(salesOrderCheckBox);
		click.clickElementCarefully(salesOrderCheckBox);
	}

	/**
	 * Finds the list of items of a return order and deselects an item
	 * @param deselectItem
	 * @param check
	 */
	public void deselectItemsInReturnOrder(int deselectItem, boolean check){

		List<WebElement> returnItemList = wait.waitForAllElementsDisplayed(SELECT_RETURN_ITEM);
		boolean isChecked = checkbox.isCheckboxChecked(returnItemList.get(deselectItem));

		if(!check == isChecked){
			click.clickElementCarefully(returnItemList.get(deselectItem));
		}

	}

	/**
	 * Updates the quantity to be returned in the return order
	 * @param quantityAmount
	 */
	public void updateReturnedQuantityAmount(String quantityAmount){
		waitForPageLoad();
		WebElement quantityField = wait.waitForElementPresent(RETURNED_QUANTITY_FIELD);
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
		text.setTextFieldCarefully(quantityField, quantityAmount, false);
		text.enterText(quantityField, quantityAmount);
		text.pressTab(quantityField);
	}

	/**
	 * Enters the Retail return reason code in the Line details
	 */
	public void retailReturnReasonCode(){
		if(element.isElementPresent(RETAIL_RETURN_REASON_CODE)){
			wait.waitForElementPresent(RETAIL_RETURN_REASON_CODE);
			click.javascriptClick(RETAIL_RETURN_REASON_CODE);
			text.enterText(RETAIL_RETURN_REASON_CODE, "11"+Keys.ENTER);
		}else if(element.isElementPresent(RETURN_REASON)){
			wait.waitForElementPresent(RETURN_REASON);
			waitForPageLoad();
			click.clickElementCarefully(RETURN_REASON);
			text.clickElementAndEnterText(RETURN_REASON, "11"+Keys.ENTER);
		}
	}

	/**
	 * Clicks on Complete
	 */
	public void clickOnCompleteJavaScript()
	{
		wait.waitForElementDisplayed(COMPLETE);
		click.javascriptClick(COMPLETE);
	}

	/**
	 * Updates the Quantity amount for an item
	 *
	 * @param quantityAmount
	 * @param itemLine
	 */
	public void updateQuantityAmount(String quantityAmount, int itemLine){
		By quantityUpdatedPurchaseOrder = By.xpath(String.format(quantityUpdatedStringPurchaseOrder, itemLine));
		By quantityUpdatedSalesOrder = By.xpath(String.format(getQuantityUpdatedStringSalesOrder, itemLine));
		wait.waitForElementDisplayed(ADD_NEW_LINE);
		click.clickElementCarefully(ADD_NEW_LINE);

		if(element.isElementPresent(quantityUpdatedPurchaseOrder)) {
			scroll.scrollElementIntoView(quantityUpdatedPurchaseOrder);
			wait.waitForElementEnabled(quantityUpdatedPurchaseOrder);
			click.clickElementCarefully(quantityUpdatedPurchaseOrder);
			WebElement quantityItem = wait.waitForElementEnabled(quantityUpdatedPurchaseOrder);
			quantityItem.sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END), quantityAmount);
			quantityItem.sendKeys(Keys.TAB);
			if(element.isElementPresent(OK)){
				wait.waitForElementDisplayed(OK);
				click.javascriptClick(OK);
			}
		}else if(element.isElementPresent(quantityUpdatedSalesOrder)){
			WebElement ele = wait.waitForElementPresent(quantityUpdatedSalesOrder);
			action.moveToElement(ele).click(ele).perform();
			wait.waitForElementEnabled(quantityUpdatedSalesOrder);
			click.clickElementCarefully(quantityUpdatedSalesOrder);
			WebElement quantityItem = wait.waitForElementEnabled(quantityUpdatedSalesOrder);
			quantityItem.sendKeys(Keys.HOME,Keys.chord(Keys.SHIFT,Keys.END),quantityAmount);
			quantityItem.sendKeys(Keys.TAB);
		}
	}

	/**
	 * Updates the Site value for an item
	 *
	 * @param unitPrice
	 * @param itemLine
	 */
	public void updateUnitPrice(String unitPrice, int itemLine){
		By unitPriceUpdated = By.xpath(String.format(unitPriceUpdatedStringPurchaseOrder, itemLine));
		By unitPriceUpdatedSalesOrder = By.xpath(String.format(unitPriceUpdatedStringSalesOrder, itemLine));

		if(element.isElementPresent(unitPriceUpdated)){
			scroll.scrollElementIntoView(unitPriceUpdated);
			wait.waitForElementEnabled(unitPriceUpdated);
			click.clickElementCarefully(unitPriceUpdated);
			WebElement siteItem = wait.waitForElementEnabled(unitPriceUpdated);
			siteItem.sendKeys(Keys.HOME,Keys.chord(Keys.SHIFT,Keys.END), unitPrice);
			siteItem.sendKeys(Keys.TAB);
		}else if(element.isElementPresent(unitPriceUpdatedSalesOrder)){
			scroll.scrollElementIntoView(unitPriceUpdatedSalesOrder);
			wait.waitForElementEnabled(unitPriceUpdatedSalesOrder);
			click.javascriptClick(unitPriceUpdatedSalesOrder);
			WebElement siteItem = wait.waitForElementEnabled(unitPriceUpdatedSalesOrder);
			siteItem.sendKeys(Keys.HOME,Keys.chord(Keys.SHIFT,Keys.END), unitPrice);
			siteItem.sendKeys(Keys.TAB);
		}
	}

	/**
	 * Updates the Site value for an item
	 *
	 * @param site
	 * @param itemLine
	 */
	public void updateSite(String site, int itemLine){
		By siteUpdated = By.xpath(String.format(siteUpdatedString, itemLine));

		driver.manage().window().maximize();
		scroll.scrollElementIntoView(siteUpdated);
		wait.waitForElementEnabled(siteUpdated);
		click.javascriptClick(siteUpdated);
		WebElement siteItem = wait.waitForElementEnabled(siteUpdated);
		text.setTextFieldCarefully(siteUpdated, site, false);
		siteItem.sendKeys(Keys.TAB);
		if(element.isElementPresent(OK)){
			wait.waitForElementDisplayed(OK);
			click.javascriptClick(OK);
		}
	}

	/**
	 * Select Line Number
	 * @param lineNum
	 */
	public void selectLine(int lineNum)
	{
		String lineXPath = String.format("(//div[contains(@id,'SalesLineGrid')]//div[@role=\"checkbox\"][@title])[%s]",lineNum);
		click.clickElementIgnoreExceptionAndRetry(By.xpath(lineXPath));
	}

	/**
	 * Updates the WareHouse value for an item
	 *
	 * @param wareHouse
	 * @param itemLine
	 */
	public void updateWareHouse(String wareHouse, int itemLine){
		By wareHouseUpdated = By.xpath(String.format(wareHouseUpdatedString, itemLine));

		scroll.scrollElementIntoView(wareHouseUpdated);
		wait.waitForElementEnabled(wareHouseUpdated);

		try{
			if(element.isElementPresent(OK)){
				hover.hoverOverElement(OK);
				click.moveToElementAndClick(OK);
			}
			click.clickElementCarefully(wareHouseUpdated);
		}catch(Exception e){
			click.javascriptClick(wareHouseUpdated);
		}
		WebElement siteItem = wait.waitForElementEnabled(wareHouseUpdated);
		siteItem.sendKeys(Keys.HOME,Keys.chord(Keys.SHIFT,Keys.END), wareHouse);
		siteItem.sendKeys(Keys.TAB);
	}

	/**
	 * Updates the Discount Percentage value for an item
	 *
	 * @param discountPercentage
	 * @param itemLine
	 */
	public void updateDiscountPercentage(String discountPercentage, int itemLine){
		By discountPercentageUpdated = By.xpath(String.format(discountPercentageUpdatedStringPurchaseOrder, itemLine));
		By discountPercentageUpdatedSalesOrder = By.xpath(String.format(discountPercentageUpdatedStringSalesOrder, itemLine));

		driver.manage().window().maximize();
		if(element.isElementPresent(discountPercentageUpdated)){
			scroll.scrollElementIntoView(discountPercentageUpdated);
			wait.waitForElementEnabled(discountPercentageUpdated);
			click.clickElementCarefully(discountPercentageUpdated);
			WebElement siteItem = wait.waitForElementEnabled(discountPercentageUpdated);
			siteItem.sendKeys(Keys.HOME,Keys.chord(Keys.SHIFT,Keys.END), discountPercentage);
			siteItem.sendKeys(Keys.TAB);
		}else if(element.isElementPresent(discountPercentageUpdatedSalesOrder)){
			scroll.scrollElementIntoView(discountPercentageUpdatedSalesOrder);
			wait.waitForElementEnabled(discountPercentageUpdatedSalesOrder);
			click.clickElementCarefully(discountPercentageUpdatedSalesOrder);
			WebElement siteItem = wait.waitForElementEnabled(discountPercentageUpdatedSalesOrder);
			siteItem.sendKeys(Keys.HOME,Keys.chord(Keys.SHIFT,Keys.END), discountPercentage);
			siteItem.sendKeys(Keys.TAB);
		}
		driver.manage().window().setSize(new Dimension(1024,768));
	}


	/**
	 * Updates the Quantity, Site, Warehouse, and Unit Price of a line
	 *
	 * @param site
	 * @param wareHouse
	 * @param qty
	 * @param unitPrice
	 * @param itemIndex
	 */
	public void updateLineItemsInfo (String site, String wareHouse, String qty, String unitPrice, String discountPercentage, int itemIndex ) {

			updateQuantityAmount(qty, itemIndex);
			updateSite(site, itemIndex);
			updateWareHouse(wareHouse, itemIndex);
			updateUnitPrice(unitPrice, itemIndex);
			updateDiscountPercentage(discountPercentage, itemIndex);
	}

	/**
	 * Enters the Invoice Date when generating invoice
	 */
	public void enterInvoiceDate(){
		hover.hoverOverElement(CLICK_CALENDER_BUTTON);
		click.clickElementCarefully(CLICK_CALENDER_BUTTON);

		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		int day = calendar.get(Calendar.DATE) + 2;
		String enterDay = String.valueOf(day);

		click.clickElementCarefully(By.xpath("//table[contains(@class,'ui-datepicker-calendar')]//a[contains(@id, 'idDay-"+enterDay+"')]"));
	}

	/**
	 * Validates if multiple tax lines are similar for Posted Sales Tax
	 */
	public boolean verifyPostedSalesTaxLines(){
		List <WebElement> postSalesTaxLines = wait.waitForAllElementsPresent(POSTED_SALES_TAX_TAX_LINES);

		boolean valuesEqual = false;

		for(int i = 0; i <= postSalesTaxLines.size() - 1; i++){
			if(postSalesTaxLines.indexOf(i) == postSalesTaxLines.indexOf(i + 1)){
				valuesEqual = false;
			}
			valuesEqual = true;
		}

		return valuesEqual;
	}
}
