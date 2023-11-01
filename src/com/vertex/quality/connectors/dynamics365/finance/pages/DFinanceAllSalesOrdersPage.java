package com.vertex.quality.connectors.dynamics365.finance.pages;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.dynamics365.finance.dialogs.DFinanceCreateSalesOrderDialog;
import com.vertex.quality.connectors.dynamics365.finance.dialogs.DFinanceTransactionSalesTaxDialog;
import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinancePostSignOnPage;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Sales order page common methods and object declaration page
 *
 * @author Saidulu Kodadala
 */
public class DFinanceAllSalesOrdersPage extends DFinancePostSignOnPage
{
	public DFinanceCreateSalesOrderDialog salesOrderCreator;
	public DFinanceTransactionSalesTaxDialog salesTaxCalculator;

	public final String sellTabName = "Sell";
	public final String pickandpackTabName = "PickAndPack";
	public final String salesOrderTabName = "SalesOrder";
	//public final String invoiceTabName = "invoice";
	protected By HEADER_ELLIPSE = By.xpath("(//div[@class='appBar-toolbar']//div[@title='More'])[2]");
	protected By INVOICE_TAB = By.xpath("(//div[@data-dyn-controlname='Invoice'])[2]");
	protected By MODIFY_SALES_ORDER = By.xpath("//span[text()='Modify']");
	protected By SALES_TAX_LINK = By.name("TaxTransSource");
	protected By PICKING_LIST_LINK = By.xpath("//span[text()='Generate picking list']");
	protected By PICKING_LIST_REGISTRATION = By.xpath("//span[text()='Picking list registration']");
	protected By SELECTED_LINES = By.xpath("//div[@aria-label='Select']");
	protected By FUNCTIONS_DROPDOWN = By.xpath("(//span[contains(@id,'linesFunctions')])[1]");
	protected By UPDATES_MENU_BUTTON = By.xpath("//button[@name='UpdatesMenuButton']");
	protected By UPDATES_ALL = By.xpath("//button[@name='PickAllButton']");
	protected By QUANTITY_DROPDOWN = By.xpath("//input[@name='specQty']//..//..//div/div");
	protected By GENERATE_INVOICE = By.name("buttonUpdateInvoice");

	protected final String TMP_TRXN_ROW_LOCATOR = "(//div[contains(@id, 'TmpTaxWorkTrans_TaxCode') and contains(@data-dyn-focus, 'input')])";

	protected By TMP_TRXN_ROW_BY = By.xpath(TMP_TRXN_ROW_LOCATOR);
	protected By PROCESSING_REQUEST = By.cssSelector("#ShellProcessingDiv:not([style*='none'])");
	protected By PROCESSING_REQUEST_2 = By.cssSelector("#ShellBlockingDiv:not([style*='none'])");
	protected By OK_BUTTON_FOR_CREATE_SALES_ORDER = By.xpath("//div/span[contains(.,'OK')]");
	protected By ADD_VALID_OK = By.cssSelector("span[id*='Ok']");
	protected By SELECT_ROW = By.id("SalesTable_3_SalesLineGrid_RowTemplate_Row0");
	protected By DELETE_BUTTON = By.xpath("(//button[@name='SystemDefinedDeleteButton'])[2]");
	protected By GENERAL_HEADER = By.xpath("//div[contains(@id,'TabHeaderGeneral_header')]/button");
	protected By WAREHOUSE_HEADER_INPUT = By.xpath("//input[contains(@name, 'InventLocation')]");
	protected By WAREHOUSE_UPDATE_TOGGLE = By.cssSelector("[id='Dialog_4_InventLocationId_toggle']");
	protected By SETUP_HEADER = By.xpath("//div[contains(@id,'TabHeaderSetup_header')]/button");
	protected By DELIVERY = By.cssSelector("[id$=TabHeaderDelivery]");
	protected By CONFIRMED_RECEIPT_DATE = By.cssSelector("[id$=ReceiptDateConfirmed_input]");
	public By PRICE_AND_DISCOUNT = By.xpath("//*[contains(@id,\"TabHeaderPriceCalc_header\")]/button");
	protected By SALES_TAX_GROUP = By.xpath("//input[contains(@name, 'TaxGroup')]");
	protected By SALES_TAX_GROUP_EDIT = By.xpath("//div[@data-dyn-controlname='TaxGroup']//span[@title]");
	protected By CASH_DISCOUNT = By.name("Payment_CashDisc");
	protected By FILTER_SEARCH_BOX = By.name("QuickFilterControl_Input");
	protected By FILTER_INVOICE_NUMBER = By.name("filterInvoiceNumber");
	protected By FILTER_CUSTOMER_NAME = By.xpath("//span[@class='quickFilter-listFieldName' and text()='Customer name']");
	protected By FILTER_SALES_ORDER_OPTION = By.xpath("//span[@class='quickFilter-listFieldName' and text()='Sales order']");
	protected By SEARCH_BUTTON = By.cssSelector("[class*='quickFilter-listItem flyout-menuItem']");
    protected By SALES_ORDER_NUMBER = By.name("QuickFilterControl_Input");
    protected By SELECT_INVOICE_NUMBER = By.xpath("//input[contains(@id, 'CustInvoiceTable_InvoiceId')]");
	protected By SELECT_SALES_ORDER_NUMBER = By.xpath("//input[contains(@aria-label, 'Sales order')]");
	protected final By CLICK_SALES_ORDER = By.linkText("Click to follow link");
	protected String salesOrderDetailsTab = "//div//*[text()='%s']";
	protected final String salesOrderDetailsLinesTabName = "Lines";
	protected final String salesOrderDetailsHeaderTabName = "Header";
	protected By SALES_ORDER_ID = By.name("SalesTable_SalesId");
	public By SALES_ORDER = By.cssSelector("span.titleField.staticText.layout-ignoreArrange.fill-width");
	protected By INVOICE_NUMBER = By.name("CVHeader_InvoiceId");
	protected By POSTED_SALES_TAX_INVOICE_NUMBER = By.xpath("//input[contains(@id, 'CustInvoiceJour_InvoiceNum_Grid')]");
	protected By SALES_ORDER_LINES = By.cssSelector("[id$='LineViewLines_caption']");
	protected By ADD_NEW_LINE = By.cssSelector("[id$='LineStripNew_label']");
	protected By ITEM_NUMBER = By.xpath("SalesLine_ItemId");
	protected By SITE = By.name("InventoryDimensionsGrid_InventSiteId");
	protected By WAREHOUSE = By.name("InventoryDimensionsGrid_InventLocationId");
	protected By UNIT_PRICE = By.name("SalesLine_SalesPrice");
	protected By DISCOUNT = By.name("SalesLine_LineDiscGrid");
	protected By SAVE_SALES_TAX_GROUP = By.cssSelector("[id*='SystemDefinedSaveButton_label']");
	protected By ORDER_TYPE = By.xpath("//input[@title='Returned order']");
	protected By DELIVERY_NAME = By.xpath("//label[contains(text(), 'Delivery name')]");
	protected By DELIVERY_ADDRESS = By.xpath("//label[contains(text(), 'Delivery address')]");
	protected By CURRENCY = By.name("SalesTable_CurrencyCode");
	protected By ADD_ADDRESS = By.cssSelector("[name*='LogisticsPostalAddressNew']");
	protected By ADD_ADDRESS1 = By.xpath("//button[@name='DirPartyPostalAddressNewHeader']");
	protected By EXPAND_ADDRESS = By.xpath("//div[contains(@id,'TabHeaderAddress_header')]/button");
	protected By ADD_ADDRESS2 = By.cssSelector("[name*='DirPartyPostalAddressNewLine']");
	protected By LINE_DETAILS = By.xpath("//h2[contains(text(),'Line details')]");
	protected By ADDRESS_TAB = By.xpath("//span[contains(text(),'Address')]");
	protected By NEW_ADDRESS_DESCRIPTION = By.name("Details_Description");
	protected By ZIPCODE = By.name("LogisticsPostalAddress_ZipCode");
	protected By STREET = By.name("LogisticsPostalAddress_Street");
	protected By CITY = By.name("LogisticsPostalAddress_City");
	protected By STATE = By.name("LogisticsPostalAddress_State");
	protected By COUNTRY = By.name("LogisticsPostalAddress_CountryRegionId");
	protected By COUNTY = By.name("LogisticsPostalAddress_County");
	protected By GET_CLEANSED_ZIPCODE = By.xpath("//input[contains(@id, 'VTXAddressResultsTmp_Zip')]");
	protected By OK_ADDRESS_BUTTON = By.name("OKButton");
	protected By ADDRESS_POPUP = By.cssSelector("button[name='Yes']");
	protected By SALES_ORDER_OK_BTN = By.cssSelector("[name$=OK]");
	protected By ADDRESS_VALIDATION_OK = By.xpath("//button[@tabindex=-1]//span[contains(.,'OK')]");
	protected By RETURN_ADDRESS = By.xpath("//textarea[@name='LogisticsPostalAddressDeliveryHeader_Address1']");
	protected By NEW_ADDRESS_SALESORDER = By.name("LogisticsPostalAddressNew");
	protected By NEW_DELIVERY_ADDRESS_SALESORDER = By.name("DirPartyPostalAddressNewHeader");
	protected By UPDATE_STREET_LINE1 = By.xpath(
		"//div[@data-dyn-form-name='VTXStreetUpdate']//textarea[@name='VTXAddressResultsTmp_StreetLine1']");
	protected By UPDATE_STREET_LINE2 = By.xpath(
		"//div[@data-dyn-form-name='VTXStreetUpdate']//textarea[@name='VTXAddressResultsTmp_StreetLine2']");
	protected By EDIT_STREET = By.xpath("//span[contains(text(),'Edit street')]");
	protected By OK_UPDATE_STREET_BUTTON = By.xpath(
		"//div[@data-dyn-form-name='VTXStreetUpdate']//button[@name='OKButton']");
	protected By STREETLINE1_VALUE = By.xpath("(//div[contains(@id, 'VTXAddressResultsTmp_StreetLine1')])//div//textarea");
	protected By STREETLINE2_VALUE = By.xpath("(//div[contains(@id, 'VTXAddressResultsTmp_StreetLine2')])//div//textarea");
	protected By CONFIDENCE_VALUE = By.xpath("//input[contains(@id, 'VTXAddressResultsTmp_Confidence')]");
	protected By ACCEPT_POPUP = By.xpath("(//button[contains(.,'Yes')])");
	protected By ACCEPT_OK_POPUP = By.xpath("(//span[text()='OK'])[2]");
	protected By COMPLETE_MSG = By.xpath("//span[@title='Operation completed']");
	protected By EXPAND_MESSAGE = By.xpath("//div[@class='messageBar-collapseMessageRegion']/button[contains(@aria-label, 'Message')][@data-dyn-lastvisible='true']");
	protected By NO_TAXES_FOUND_POPUP = By.xpath("//span[contains(text(),'No tax areas were found during the lookup.')]");
	protected By POST_OK = By.cssSelector("span[id*='SysBoxForm'][id*='Ok_label']");
	protected By POST_BUTTON = By.xpath("//span[text()='Post']");
	protected By ERROR_ADDRESS_POPUP = By.id("titleField");
	protected By NEW_ADDRESS_ERROR = By.xpath("//span[contains(text(),'No tax areas were found during the lookup.')]");
	protected By POPUP_ADDRESS_ERROR = By.xpath("//*[@id=\"titleField\"]");
	protected By NO_BUTTON = By.xpath("//span[text()='No'][@class='button-label']");
	protected By YES_BUTTON = By.name("Yes");
	protected By CANCEL_BUTTON = By.name("CancelButton");
	protected By CANCEL = By.xpath("(//button[contains(@data-dyn-controlname, 'Cancel')])[2]");
	protected By ELLIPSIS = By.xpath("//div[contains(@id,'LinesActionPaneStrip')]//div[@title='More']");
	protected By OK_BUTTON = By.xpath("//span[text()='OK']");
	protected By UPDATE_LINE = By.xpath("//span[text()='Update line']");
	protected By UPDATE_LINE_RETURN_ORDER = By.name("Update");
	protected By UPDATE_REGISTRATION = By.name("InventTransRegister");
	protected By DISPOSITION_CODE = By.xpath("//div[contains(@class, 'dialog')]//label[text()='Disposition code']//..//input");
	protected By DISPOSITION_CODE_DROPDOWN = By.xpath("//div[contains(@id, 'SysGen_DispositionCodeId') and contains(@data-dyn-focus,'input')]");
	protected By ADD_REGISTRATION_LINE = By.xpath("//button[@data-dyn-controlname='AddRegistrationLinesButton']");
	protected By CONFIRM_REGISTRATION = By.name("ctrlUpdateButton");
	protected By SALES_ORDER_NUMBER_FOR_RETURN_ORDER = By.name("ReturnOrder_SalesId");
	protected By DELIVERY_REMAINDER = By.name("SalesUpdateRemain");
	protected By CHANGE_QUANTITY = By.name("RemainSalesPhysical");
	protected By CLICK_OK = By.name("CommandButtonOK");
	protected By ENTER_QUANTITY = By.name("SalesLine_SalesQty");
	protected By QUANTITY = By.xpath("(//input[contains(@id,'SalesLine_SalesQty')])[1]");

	protected By REFRESH_LINK = By.xpath("//*[contains(@id,\"RefreshButton\")]/div/span[1]");
	//create sales order elements/locators
	//fixme remove from here
	public By CUSTOMER_HEADER = By.xpath("//*[contains(@id,'CustomerTab_header')]/button");
	protected By TEXT_CUSTOMER_ACCOUNT = By.cssSelector("[id$=SalesTable_CustAccount_input]");
	protected By FREE_INVOICE_CUSTOMER_ACCOUNT = By.name("CVHeader_OrderAccount");
	protected By SETTLEMENT_PERIOD_SELECTION = By.xpath("//label[text()='Settlement period']//..//input");
	protected By FROM_DATE_SELECTION = By.xpath("//label[text()='From date']//..//input");
	protected By SALES_TAX_PAYMENT_VERSION_SELECTION = By.xpath("//label[text()='Sales tax payment version']//..//input");
	protected By LATEST_CORRECTIONS_SELECTION = By.xpath("//li[text()='Latest corrections']");
	protected By POSTED_SALES_TAX_VOUCHER = By.xpath("(//input[contains(@id, 'TaxTrans_Voucher')])[1]");
	protected By VOUCHER_LIST = By.className("listItem");
	protected By ACCOUNT_NAME_TYPE = By.name("AccountName");

	// Charges link
	protected By CHARGES_LINK = By.cssSelector(".button-label[id*='MarkupTransHeading'][id*='SalesTable']");
	protected By CHARGES_NEW_BUTTON = By.xpath(".//span[contains(@id,'NewButton')][starts-with(@id,'MarkupTrans')][not(contains(@style, 'hidden'))]");
	protected By CHARGES_SAVE_BUTTON = By.xpath(".//span[contains(@id,'SaveButton')][starts-with(@id,'MarkupTrans')][not(contains(@style, 'hidden'))]");
	protected By CHARGES_CLOSE_BUTTON = By.cssSelector("button[id^='MarkupTrans'][id*='CloseButton']");
	protected By KEEP_INPUT = By.xpath("//div[@data-dyn-controlname=\"MarkupTrans_Keep\"]//div[@title='No']");

	protected String LINE_ITEM_ROW = "//*[@aria-rowindex='2' and contains(@role, 'row')]";
	protected By REMOVE_ITEM_BUTTON = By.cssSelector(".button-label[id*='LineStripDelete']");
	protected By leadTimeChanged = By.xpath("//span[text()='Lead time changed, recalculate ship and receipt dates?']");

	//tab element
	protected String TAB_LOCATOR = "//form[@aria-hidden='false']/.//*[starts-with(@id, 'SalesTable')][contains(@id, '%s')][contains(@id, 'button')][@type='button']";
	protected By TAB_LOCATOR_SALES_TABLE_LOWERCASE = By.xpath("//form[@aria-hidden='false']/.//*[starts-with(@id, 'salestable')][contains(@id, 'Sell')][contains(@id, 'button')][@type='button']");
	protected String OVERWRITE_FIELD_LOCATOR = "//label[text()='%s'][contains(@class,'checkBox')]/following-sibling::div//span[@class='toggle-box']";

	protected By OVERWRITE_OK_BUTTON = By.cssSelector("button[name='OkButton']");
	protected By UPDATE_ORDER_LINE_RADIO_BUTTON = By.className("radioButton");

	protected final By popupContainer = By.className("rootContent-lightBox");
	protected final By popupButton = By.className("dynamicsButton");

	//Sales Order Details subpage >> Lines tab >> Sales Order Lines section
	protected final By salesOrderDetailsSection = By.className("section-page-noncollapsible");
	protected final By salesOrderDetailsSectionHeader = By.className("section-page-header");
	protected final String salesOrderLinesSectionName = "Sales order lines";

	protected final By tableContainer = By.cssSelector("[id$='_LineViewLines_container']");
	protected final By tableHeadersContainer = By.className("listAsTable-HeaderRow");
	protected final By tableHeader = By.className("columnHeader");
	protected final By tableBody = By.className("TabularView");
	protected final By tableRow = By.className("rowTemplate");
	protected final By tableCell = By.className("listCell");
	protected final String inactiveTableCellClass = "isInactiveCell";
	protected final By tableCellInput = By.className("textbox");
	protected final By siteinput = By.name("InventoryDimensionsGrid_InventSiteId");
	protected final By warehouseinput = By.name("InventoryDimensionsGrid_InventLocationId");
	protected final By unitprice = By.name("SalesLine_SalesPrice");
	protected final By discount = By.name("SalesLine_LineDiscGrid");
	protected final String RETAIL_STORE = "//span[text()='%s']";
	protected final By ADD_ARROW = By.name("Add");
	protected final By CALCULATE_STATEMENT = By.xpath("(//button[@name='RetailStatementCalculate']//span[text()='Calculate statement'])[2]");
	protected final By POST_STATEMENT = By.xpath("(//button[@name='TrickleFeedStatementPost']//span[text()='Post statement'])[2]");
	protected final By COUNTED_AMOUNT = By.name("statementLine_countedAmount");
	protected final By STATUS = By.xpath("//div[@aria-label=\"Status\"]");
	protected final By STATUS_INPUT = By.xpath("//input[contains(@id,'SalesStatus_Input')]");
	protected final By APPLY = By.xpath("//span[contains(@id,'SalesTable_SalesStatusGrid_ApplyFilters')]");
	protected final By FINANCIALS = By.xpath("//button[contains(@id,'LineStripFinancials_button')]");
	protected final By MAINTAIN_CHARGES_IN_FINANCIALS = By.xpath("//button[contains(@aria-label,'Maintain charges')]");
	protected final By NEW_STATEMENT = By.xpath("//button[@name='NewStatementDropdown']");
	protected final By CLOSE = By.xpath("//button[@data-dyn-controlname=\"MessageBarClose\" and (@aria-label)]");
	protected final By TAX_AREA_ID = By.xpath("//input[@aria-label='Tax Area ID']");
	protected final By SALES_TAX_VAT_REGISTRATION_ID_OPTION = By.xpath("//div[contains(@id, 'VTXSalesTaxVATRegistrationID')]//div[@class='lookupButton']");
	protected final By SALES_TAKER = By.xpath("//input[@name='Administration_WorkerSalesTaker_DirPerson_FK_Name']");
	Actions action = new Actions(driver);

	public DFinanceAllSalesOrdersPage( WebDriver driver )
	{
		super(driver);
	}

	//TODO split off the sales order configuration page as a subclass? this superclass keeps the main-content header-esque stuff?

	/**
	 * Click On button to create new sales order
	 *
	 * @return dialog for creating new sales order
	 */
	public void openNewSalesOrder( )
	{
		wait.waitForElementDisplayed(NEW_BUTTON);
		click.clickElement(NEW_BUTTON);
		this.salesOrderCreator = new DFinanceCreateSalesOrderDialog(driver, this);
	}

	/**
	 * Click On button to create new sales order
	 *
	 * @return dialog for creating new sales order
	 */
	public void openNewSalesOrderFromSalesOrderPage( )
	{
			wait.waitForElementDisplayed(NEW_BUTTON);
			click.clickElement(NEW_BUTTON);
			this.salesOrderCreator = new DFinanceCreateSalesOrderDialog(driver, this);
	}

	//fixme remove from here

	/**
	 * expands the customer section of the new sales order dialog
	 */
	public void expandCustomerSection( )
	{
		expandHeader(CUSTOMER_HEADER);
	}

	/**
	 * opens the setup section of the 'Header' tab of the sales order configuration page
	 */
	public void expandSetupHeaderSection( )
	{
		expandHeader(SETUP_HEADER);
	}

	//Sales Order Details subpage >> Lines tab >> Sales Order Lines section
	//TODO create 'sales order details' page class for this/other stuff

	/**
	 * finds the section for lines in the current sales order
	 *
	 * @return the section for lines in the current sales order
	 * returns null on error
	 */
	protected WebElement getSalesOrderLinesSection( )
	{
		WebElement linesContainer = null;

		List<WebElement> containers = wait.waitForAnyElementsDisplayed(salesOrderDetailsSection);
		linesContainer = element.selectElementByNestedLabel(containers, salesOrderDetailsSectionHeader,
			salesOrderLinesSectionName);
		return linesContainer;
	}

	/**
	 * finds the column header with the given name & returns the position of that column among the columns
	 *
	 * Note- this doesn't count the leftmost checkmark column
	 *
	 * @param columnName the name of the column whose position should be found
	 *
	 * @return the position of the desired column among the columns
	 * returns -1 on error
	 */
	protected int getSalesOrderLinesColumnIndex( final String columnName )
	{
		int columnIndex = -1;

		WebElement linesSection = getSalesOrderLinesSection();
		if ( linesSection != null )
		{
			final WebElement table = wait.waitForElementDisplayed(tableContainer, linesSection);

			final WebElement headersContainer = wait.waitForElementDisplayed(tableHeadersContainer, table);
			//TODO make waitForOnlyElementsDisplayed overload
			final List<WebElement> headers = wait.waitForAnyElementsDisplayed(tableHeader, headersContainer);
			//referencing innerText is necessary because every column header elements has its name in its innerText
			// attribute, but column headers which are off the screen return "" from text.getElementText()
			columnIndex = element.findElementPositionByAttribute(headers, "innerText", columnName);
		}

		return columnIndex;
	}

	/**
	 * collects the row elements which hold the lines of the current sales order
	 *
	 * @return the row elements which hold the lines of the current sales order
	 * throws a timeout exception on error
	 */
	protected List<WebElement> getSalesOrderLines( )
	{
		List<WebElement> rows = new ArrayList<>();

		WebElement linesSection = getSalesOrderLinesSection();
		if ( linesSection != null )
		{
			final WebElement table = wait.waitForElementDisplayed(tableContainer, linesSection);
			final WebElement tableBodyElem = wait.waitForElementDisplayed(tableBody, table);
			//TODO make waitForOnlyElementsDisplayed overload
			rows = wait.waitForAnyElementsDisplayed(tableRow, tableBodyElem);
		}

		return rows;
	}

	/**
	 * finds the cell in the specified row under the column with the given name
	 *
	 * @param column   the column which the cell is under
	 * @param rowIndex which row the cell is in
	 *
	 * @return the cell in the specified position in the table
	 * returns null on error
	 */
	protected WebElement getSalesOrderLinesCell( final LineItemColumn column, final int rowIndex )
	{
		WebElement targetCell = null;

		final int columnIndex = getSalesOrderLinesColumnIndex(column.getName());
		if ( columnIndex >= 0 )
		{
			List<WebElement> rows = getSalesOrderLines();
			if ( rowIndex <= rows.size() )
			{
				final WebElement row = rows.get(rowIndex);

				//TODO waitForOnlyElementsDisplayed
				List<WebElement> cells = wait.waitForAnyElementsDisplayed(tableCell, row);
				if ( columnIndex <= cells.size() )
				{
					targetCell = cells.get(columnIndex);
				}
				else
				{
					final String highColumnIndexMessage = String.format(
						"requested column index %d is out of bounds for list of a row's cells of length %d",
						columnIndex, cells.size());
					VertexLogger.log(highColumnIndexMessage, VertexLogLevel.ERROR, getClass());
				}
			}
			else
			{
				final String highRowIndexMessage = String.format(
					"requested row index %d is out of bounds for rows list of length %d", rowIndex, rows.size());
				VertexLogger.log(highRowIndexMessage, VertexLogLevel.ERROR, getClass());
			}
		}
		return targetCell;
	}

	/**
	 * accesses the given text field cell in the Sales Orders Lines table
	 * and enters  the given text into it
	 *
	 * @param textCell the cell whose contents should be replaced
	 * @param newText  the text which the cell should be filled with
	 */
	public void setSalesOrderLinesTextCell( final WebElement textCell, final String newText )
	{
		final WebElement textCellInput = wait.waitForElementEnabled(tableCellInput, textCell);

		WebElement clickableTextCell = textCell;
		final String textCellClasses = attribute.getElementAttribute(textCell, "aria-label");
		final boolean isInactive = textCellClasses != null && textCellClasses.contains(inactiveTableCellClass);
		if ( isInactive )
		{
			clickableTextCell = textCellInput;
		}
		click.clickElement(clickableTextCell);

		text.setTextFieldCarefully(textCellInput, newText);
	}

	/**
	 * Sets the site for some line item in the current sales order
	 *
	 */
	public void newSetSite(final int row, final String newText )
	{
		wait.waitForElementDisplayed(siteinput);
		click.clickElementCarefully(siteinput);
		text.setTextFieldCarefully(siteinput, newText);
	}

	/**
	 * Sets the warehouse for some line item in the current sales order
	 *
	 */
	public void newWarehouse(final int row, final String newText )
	{
		wait.waitForElementDisplayed(warehouseinput);
		click.clickElementCarefully(warehouseinput);
		text.setTextFieldCarefully(warehouseinput, newText);
	}

	/**
	 * Sets the unit price for the item selected
	 *
	 */
	public void newUnitPrice(final int row, final String newText )
	{
		wait.waitForElementDisplayed(unitprice);
		click.clickElementCarefully(unitprice);
		text.setTextFieldCarefully(unitprice, newText);
	}

	/**
	 * Sets the discount for the item selected
	 *
	 */
	public void newDiscount(final int row, final String newText )
	{
		wait.waitForElementDisplayed(discount);
		click.clickElementCarefully(discount);
		text.setTextFieldCarefully(discount, newText);
	}

	/**
	 * Clicks on the Modify Link to modify a Sales Order
	 */
	public void modifySalesOrder(){
		wait.waitForElementPresent(MODIFY_SALES_ORDER);
		click.clickElementCarefully(MODIFY_SALES_ORDER);
		waitForPageLoad();
	}

	/**
	 * open the Sales tax results dialog
	 */
	public void openSalesTaxCalculation( )
	{
		waitForPageLoad();
		click.clickElementIgnoreExceptionAndRetry(SALES_TAX_LINK);
		waitForPageLoad();
		this.salesTaxCalculator = initializePageObject(DFinanceTransactionSalesTaxDialog.class, this);
	}

	/**
	 * open the Picking list dialog
	 */
	public void openPickingList( )
	{
		waitForPageLoad();
		click.javascriptClick(PICKING_LIST_LINK);
	}

	/**
	 * Clicks on Picking List Registration
	 */
	public void clickPickingListRegistration(){
		jsWaiter.sleep(10000);
		wait.waitForElementDisplayed(PICKING_LIST_REGISTRATION);
		click.clickElementCarefully(PICKING_LIST_REGISTRATION);
	}

	/**
	 * Selects the line item(s) for the picking registration
	 * @param lineToSelect
	 */
	public void selectLineItemPickingRegistration(int lineToSelect){
		List<WebElement> selectedLines = wait.waitForAllElementsDisplayed(SELECTED_LINES);

		for(int i = 0; i <= selectedLines.size() - 1; i++){
			click.clickElementCarefully(selectedLines.get(lineToSelect));
		}
	}

	/**
	 * Clicks Functions dropdown on the Picking Registration in the Lines section
	 * and selects an option
	 * @param dropDownSelection
	 */
	public void clickFunctionsAndSelectMenuOption(String dropDownSelection){
		wait.waitForElementDisplayed(FUNCTIONS_DROPDOWN);
		click.javascriptClick(FUNCTIONS_DROPDOWN);

		WebElement dropDownSelected = wait.waitForElementDisplayed(By.xpath("//button[@data-dyn-controlname='"+dropDownSelection+"']"));
		click.clickElementCarefully(dropDownSelected);
	}

	/**
	 * Clicks the Update Tab then clicks Update All
	 */
	public void clickUpdateAndUpdateAll(){
		wait.waitForElementDisplayed(UPDATES_MENU_BUTTON);
		click.clickElementCarefully(UPDATES_MENU_BUTTON);

		wait.waitForElementDisplayed(UPDATES_ALL);
		click.javascriptClick(UPDATES_ALL);
	}


	/**
	 * open the Post Packing slip dialog
	 * @param buttonLocation
	 */
	public void postPackingSlip(String buttonLocation)
	{
		waitForPageLoad();
		WebElement postPackingSlip = wait.waitForElementDisplayed(By.xpath("(//span[text()='Post packing slip'])["+buttonLocation+"]"));

		waitForPageLoad();
		click.javascriptClick(postPackingSlip);
	}

	/**
	 * Selects an option from the Quantity dropdown in the Packing Slip dialog
	 * @param quantityType
	 */
	public void selectQuantityValueFromDialog(String quantityType){
		wait.waitForElementDisplayed(QUANTITY_DROPDOWN);
		click.clickElementIgnoreExceptionAndRetry(QUANTITY_DROPDOWN);

		WebElement valueSelected = wait.waitForElementDisplayed(By.xpath("//ul[contains(@id,'specQty')]//li[text()='"+quantityType+"']"));
		click.clickElementCarefully(valueSelected);
	}

	/**
	 * Click on 'Tab'
	 *
	 * @param tab
	 */
	public void clickOnTab( String tab )
	{
		By TAB = By.xpath(String.format(TAB_LOCATOR, tab));
		String responsiveTab = "//*[starts-with(@id, 'SalesTable')][contains(@id, '%s')][contains(@id, 'button')][@type='button']";
		By tabEllipse = By.xpath(String.format(responsiveTab, tab));

		if(element.isElementPresent(HEADER_ELLIPSE) && !element.isElementDisplayed(By.xpath(String.format(TAB_LOCATOR, tab)))){
			click.clickElementCarefully(HEADER_ELLIPSE);
		}
		waitForPageLoad();
		if(element.isElementDisplayed(TAB)){
			String isExpanded = attribute.getElementAttribute(TAB, "aria-expanded");
			if ( isExpanded == null || !isExpanded.equalsIgnoreCase("true") )
			{
				wait.waitForElementDisplayed(TAB);
				click.clickElementCarefully(TAB);
				waitForPageLoad();
			}
		}else if(element.isElementDisplayed(tabEllipse)){
			wait.waitForElementDisplayed(tabEllipse);
			click.clickElementCarefully(tabEllipse);
			waitForPageLoad();
		}
		waitForPageLoad();
	}

	/**
	 * Click on 'Tab'
	 */
	public void clickOnSellTab()
	{
		wait.waitForElementDisplayed(TAB_LOCATOR_SALES_TABLE_LOWERCASE);
		String isExpanded = attribute.getElementAttribute(TAB_LOCATOR_SALES_TABLE_LOWERCASE, "aria-expanded");

		if ( !isExpanded.equalsIgnoreCase("true") )
		{
			click.clickElementCarefully(TAB_LOCATOR_SALES_TABLE_LOWERCASE);
		}
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

	/**
	 * Verify Sales Tax Transaction Lines Count
	 *
	 * @param expCount
	 */
	public void verifyTempSalesTaxTrxnLinesCount( int expCount )
	{
		int rowCount = getTempSalesTaxTransactionLinesCount();
		if ( rowCount == expCount )
		{
			VertexLogger.log("Sales Tax Transaction Lines Count : 1", VertexLogLevel.INFO);
		}
		else
		{
			VertexLogger.log("Sales Tax Transaction Lines Count is not equals to 1", VertexLogLevel.ERROR);
		}
	}

	public Map<String, String> getTransactionLineData( String lineNumber )
	{
		Map<String, String> lineDataMap = new HashMap<String, String>();

		By SALES_TAX_CODE = By.xpath(
			String.format("%s[%s]//div//input",
				TMP_TRXN_ROW_LOCATOR, lineNumber));
		By PERCENT = By.xpath(
			String.format("(//input[contains(@id, 'showTaxValue')])[%s]",
				lineNumber));
		By ACTUAL_SALES_TAX_AMOUNT = By.xpath(
			String.format("(//input[contains(@id, 'SourceRegulateAmountCur')])[%s]",
				lineNumber));
		By SALES_TAX_DIRECTION = By.xpath(
			String.format("(//input[contains(@id, 'TmpTaxWorkTrans_TaxDirection')])[%s]",
				lineNumber));
		By VERTEX_TAX_DESCRIPTION = By.xpath(
			String.format("(//input[contains(@id, 'VTXVertexTaxDescription')])[%s]",
				lineNumber));
		By DESCRIPTION = By.xpath(
			String.format("(//input[contains(@id, 'TmpTaxWorkTrans_Txt')])[%s]",
				lineNumber));

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
	 * Verify the FIRST tax line item
	 *
	 * @param lineNumber
	 * @param expSalesTaxCode
	 * @param expPercent
	 * @param expActualSalesTaxAmount
	 * @param expSalesTaxDirection
	 * @param expVertexTaxDesc
	 * @param expDescription
	 */
	public void verifyLineData( String lineNumber, String expSalesTaxCode, String expPercent,
		String expActualSalesTaxAmount, String expSalesTaxDirection, String expVertexTaxDesc, String expDescription )
	{
		Map<String, String> lineDataMap = getTransactionLineData(lineNumber);

		String salesTaxCode = lineDataMap.get("Sales tax code");
		String percent = lineDataMap.get("Percent");
		String actualSalesTaxAmount = lineDataMap.get("Actual sales tax amount");
		String salesTaxDirection = lineDataMap.get("Sales tax direction");
		String vertexTaxDesc = lineDataMap.get("Vertex tax description");
		String description = lineDataMap.get("Description");

		if ( salesTaxCode.equalsIgnoreCase(expSalesTaxCode) )
		{
			VertexLogger.log("Expected Sales Tax Code:" + salesTaxCode, VertexLogLevel.INFO);
		}
		else
		{
			VertexLogger.log("It's not expected Sales Tax Code:" + salesTaxCode, VertexLogLevel.ERROR);
		}

		if ( percent.equalsIgnoreCase(expPercent) )
		{
			VertexLogger.log("Expected percent:" + expPercent, VertexLogLevel.INFO);
		}
		else
		{
			VertexLogger.log("It's not expected percent :" + expPercent, VertexLogLevel.ERROR);
		}

		if ( actualSalesTaxAmount.equalsIgnoreCase(expActualSalesTaxAmount) )
		{
			VertexLogger.log("Actual sales tax amount" + actualSalesTaxAmount, VertexLogLevel.INFO);
		}
		else
		{
			VertexLogger.log("Actual sales tax amount:" + actualSalesTaxAmount, VertexLogLevel.ERROR);
		}

		if ( salesTaxDirection.equalsIgnoreCase(expSalesTaxDirection) )
		{
			VertexLogger.log("Sales tax direction" + salesTaxDirection, VertexLogLevel.INFO);
		}
		else
		{
			VertexLogger.log("Sales tax direction:" + salesTaxDirection, VertexLogLevel.ERROR);
		}

		if ( vertexTaxDesc.equalsIgnoreCase(expVertexTaxDesc) )
		{
			VertexLogger.log("Vertex tax description :" + vertexTaxDesc, VertexLogLevel.INFO);
		}
		else
		{
			VertexLogger.log("Vertex tax description :" + vertexTaxDesc, VertexLogLevel.ERROR);
		}

		if ( description.equalsIgnoreCase(expDescription) )
		{
			VertexLogger.log("Expected Description:" + description, VertexLogLevel.INFO);
		}
		else
		{
			VertexLogger.log("Expected Description :" + description, VertexLogLevel.ERROR);
		}
	}

	/**
	 * allow page to refresh until the Grid has loaded to display based on F&O processes
	 */
	public void waitForGridToLoad()
	{
		waitForPageLoad();

		By NoElementDisplayed = By.xpath("//*[contains(@id,\"EmptyGridIcon\")]/div");
		int i = 0;
		while(element.isElementDisplayed(NoElementDisplayed) && i<60)
		{
			jsWaiter.sleep(10000);
			click.javascriptClick(REFRESH_LINK);
			i++;
		}
	}

	/**
	 * Enter created sales order
	 *
	 * @param salesOrder
	 */
	public void searchCreatedSalesOrder( String salesOrder )
	{
		waitForGridToLoad();
		WebElement searchBox = wait.waitForElementEnabled(FILTER_SEARCH_BOX);
		text.enterText(searchBox, salesOrder);
		wait.waitForElementDisplayed(FILTER_SALES_ORDER_OPTION);
		click.clickElementCarefully(FILTER_SALES_ORDER_OPTION);

		waitForPageLoad();
	}

	/**
	 * Enter created sales order
	 *
	 * @param customerName
	 */
	public void searchSalesOrderByCustomerName(String customerName)
	{
		WebElement searchBox = wait.waitForElementEnabled(FILTER_SEARCH_BOX);
		text.enterText(searchBox, customerName);
		wait.waitForElementDisplayed(FILTER_CUSTOMER_NAME);
		click.clickElementCarefully(FILTER_CUSTOMER_NAME);
		waitForPageLoad();
	}

	/**
	 * Filter Open Order
	 */
	public void filterStatus(String status)
	{
		click.moveToElementAndClick(STATUS);
		wait.waitForElementPresent(STATUS_INPUT);
		text.clickElementAndEnterText(STATUS_INPUT,status);
		wait.waitForElementEnabled(APPLY);
		click.moveToElementAndClick(APPLY);
		waitForPageLoad();
	}

	/**
	 * Enter created All Free Text Invoice
	 *
	 * @param allFreeInvoice
	 */
	public void searchCreatedAllFreeInvoiceOrder( String allFreeInvoice )
	{
		WebElement invoiceFilter = wait.waitForElementEnabled(FILTER_INVOICE_NUMBER);
		text.enterText(invoiceFilter, allFreeInvoice);
		invoiceFilter.sendKeys(Keys.ENTER);
		invoiceFilter.sendKeys(Keys.ENTER);

		waitForPageLoad();
	}

	/**
	 * Click on Search button
	 */
	public void clickOnSearchButton( )
	{
		waitForPageLoad();
	    wait.waitForElementDisplayed(SEARCH_BUTTON);
		click.clickElement(SEARCH_BUTTON);
	}

	/**
	 * Click on displayed sales order number
	 */
	public void clickOnDisplayedSalesOrderNumber( )
	{
		wait.waitForElementDisplayed(SALES_ORDER_NUMBER);
		try
		{
			Thread.sleep(2000);
		}
		catch ( InterruptedException e )
		{
			e.printStackTrace();
		}
		click.clickElementCarefully(SALES_ORDER_NUMBER);
		wait.waitForAllElementsDisplayed(SELECT_SALES_ORDER_NUMBER);
		WebElement element2 = element.getWebElement(SELECT_SALES_ORDER_NUMBER);
		String keysPressed = Keys.chord(Keys.CONTROL, Keys.RETURN);
		element2.sendKeys(keysPressed);
		waitForPageLoad();
	}

	/**
	 * Click on displayed All Free Text Invoice number
	 */
	public void clickOnDisplayedAllFreeInvoiceNumber( )
	{

		wait.waitForElementDisplayed(FILTER_INVOICE_NUMBER);
		try
		{
			Thread.sleep(2000);
		}
		catch ( InterruptedException e )
		{
			e.printStackTrace();
		}
		click.clickElementCarefully(FILTER_INVOICE_NUMBER);
		wait.waitForAllElementsDisplayed(SELECT_INVOICE_NUMBER);
		WebElement element2 = element.getWebElement(SELECT_INVOICE_NUMBER);
		String keysPressed = Keys.chord(Keys.CONTROL, Keys.RETURN);
		element2.sendKeys(keysPressed);
		waitForPageLoad();
	}

	/**
	 * Click on Header
	 */
	public void openHeaderTab( )
	{
		WebElement headerLink = getHeaderLink();
		if ( headerLink != null )
		{
			click.clickElementCarefully(headerLink);
		}
	}

	/**
	 * Get Sales Order Number
	 *
	 * @return sales order number from flyout
	 */
	public String getSalesOrderNumber( )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(SALES_ORDER);
		scroll.scrollElementIntoView(SALES_ORDER);
		String salesOrderNumber = text.getElementText(SALES_ORDER);
		String SALES_ORDER_NUMBER = StringUtils.substringBefore(salesOrderNumber, ":");

		if ( SALES_ORDER_NUMBER != null )
		{
			SALES_ORDER_NUMBER = SALES_ORDER_NUMBER.trim();
		}
		System.out.println(SALES_ORDER_NUMBER);
		return SALES_ORDER_NUMBER;
	}

	/**
	 * Get the All Free Text Invoice Number
	 *
	 * @return sales order number from invoice
	 */
	public String getInvoiceNumber( )
	{
		jsWaiter.sleep(15000);
		waitForPageLoad();
		wait.waitForElementDisplayed(INVOICE_NUMBER);
		scroll.scrollElementIntoView(INVOICE_NUMBER);
		WebElement invoiceNumberVal = wait.waitForElementEnabled(INVOICE_NUMBER);
		String invoiceNumber = invoiceNumberVal.getAttribute("value");

		if ( invoiceNumber != null )
		{
			invoiceNumber = invoiceNumber.trim();
		}
		System.out.println(invoiceNumber);
		return invoiceNumber;
	}

	/**
	 * Get the Posted Sales Tax Invoice Number
	 * @param selectedInvoiceLine
	 * @return invoice number from posted sales tax
	 */
	public String getPostedSalesTaxInvoiceNumber(String selectedInvoiceLine)
	{
		jsWaiter.sleep(15000);
		waitForPageLoad();
		wait.waitForElementPresent(By.xpath("(//input[contains(@id, 'CustInvoiceJour_InvoiceNum_Grid')])["+selectedInvoiceLine+"]"));
		WebElement invoiceNumberVal = wait.waitForElementPresent(By.xpath("(//input[contains(@id, 'CustInvoiceJour_InvoiceNum_Grid')])["+selectedInvoiceLine+"]"));
		String invoiceNumber = invoiceNumberVal.getAttribute("value");

		if ( invoiceNumber != null )
		{
			invoiceNumber = invoiceNumber.trim();
		}
		System.out.println(invoiceNumber);
		return invoiceNumber;
	}

	/**
	 * Get Sales Order Number
	 * @Vishwa
	 * @return sales order number from sales order page
	 */
	public String getSalesOrderId( )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(SALES_ORDER_ID);
		scroll.scrollElementIntoView(SALES_ORDER_ID);
		String salesOrderNumber = attribute.getElementAttribute(SALES_ORDER_ID, "title");
		String SALES_ORDER_NUMBER = StringUtils.substringAfter(salesOrderNumber, ":");

		if ( SALES_ORDER_NUMBER != null )
		{
			SALES_ORDER_NUMBER = SALES_ORDER_NUMBER.trim();
		}
		return SALES_ORDER_NUMBER;
	}

	/**
	 * click on OK button to complete creation of new sales order
	 */
	public DFinanceHomePage finishCreatingSalesOrder( )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(OK_BUTTON_FOR_CREATE_SALES_ORDER);
		click.clickElement(OK_BUTTON_FOR_CREATE_SALES_ORDER);
		waitForPageLoad();
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		return homePage;
	}

	/**
	 * Click on Add Validation OK
	 */
	public void clickOnAddValidOk()
	{
		waitForPageLoad();
		scroll.scrollElementIntoView(ADDRESS_VALIDATION_OK);
		wait.waitForElementDisplayed(ADDRESS_VALIDATION_OK);
		click.clickElementCarefully(ADDRESS_VALIDATION_OK);
		waitForPageLoad();
	}

	public void clickDeleteYesButton( )
	{
		WebElement deleteYes = wait.waitForElementEnabled(DELETE_YES_BUTTON);
		click.clickElement(deleteYes);
		waitForPageLoad();
	}

	/**
	 * Click on delete button
	 */
	public void clickDeleteButton( )
	{
		wait.waitForElementDisplayed(DELETE_BUTTON);
		click.clickElementCarefully(DELETE_BUTTON);
		waitForPageLoad();
	}

	/**
	 * closes a confirmation popup (e.g. for deleting something) by pressing yes
	 * so that the operation which needed confirmation is performed
	 */
	public void agreeToConfirmationPopup( )
	{
		WebElement popup = wait.waitForElementEnabled(popupContainer);
		List<WebElement> buttons = wait.waitForAnyElementsDisplayed(popupButton, popup);
		final WebElement popupYesButton = element.selectElementByAttribute(buttons, "Yes", "innerText", true);
		if ( popupYesButton != null )
		{
			wait.waitForElementEnabled(popupYesButton);
			click.clickElement(popupYesButton);
		}
	}

	/**
	 * set warehouse under Header tab and update site and warehouse in lines
	 *
	 * @param warehouse to type in warehouse element
	 */
	public void setWarehouseForHeader( String warehouse )
	{
		WebElement headerElement = wait.waitForElementDisplayed(GENERAL_HEADER);
		final String headerAttribute = headerElement.getAttribute("aria-expanded");
		final boolean isExpanded = Boolean.parseBoolean(headerAttribute);

		if (headerAttribute!= null && !isExpanded) {
			click.clickElementCarefully(GENERAL_HEADER);
		}

		if (!element.isElementDisplayed(WAREHOUSE_HEADER_INPUT)) {
			clickOnEditButton();
		}

		text.setTextFieldCarefully(WAREHOUSE_HEADER_INPUT, warehouse);
		clickOnSaveButton();

		wait.waitForElementDisplayed(WAREHOUSE_UPDATE_TOGGLE);
		click.clickElementIgnoreExceptionAndRetry(WAREHOUSE_UPDATE_TOGGLE);
	}

	/**
	 * set sales order tax group
	 *
	 * @param group to type in sales group element
	 */
	public void setSalesOrderTaxGroup( String group )
	{
		WebElement headerElement = wait.waitForElementDisplayed(SETUP_HEADER);
		final String headerAttribute = headerElement.getAttribute("aria-expanded");
		final boolean isExpanded = Boolean.parseBoolean(headerAttribute);

		if ( headerAttribute!= null && !isExpanded )
		{
			click.clickElementCarefully(SETUP_HEADER);
		}
		text.setTextFieldCarefully(SALES_TAX_GROUP, group);
	}

	/**
	 * set sales order tax group
	 *
	 * @param group to type in sales group element
	 */
	public void setVendorInvoiceTaxGroup( String group )
	{
		text.setTextFieldCarefully(SALES_TAX_GROUP, group);
	}

	/**
	 * It sets Confirmed Receipt date
	 * @param dayToAdd
	 */
	public void setConfirmedReceiptDate(int dayToAdd)
	{
		this.expandHeader(DELIVERY);
		String date = LocalDate.now().plusDays(dayToAdd).format(DateTimeFormatter.ofPattern("MMddyyyy"));
		text.clickElementAndEnterText(CONFIRMED_RECEIPT_DATE,date);
	}

	/**
	 * It gets Confirmed Shipping date
	 * @param confirmedType - passes in whether the confirmed date is for receipt or shipping date
	 * @return the confirmed Ship in the proper format
	 */
	public String getConfirmedShipOrReceiptDate(String confirmedType) {
		wait.waitForElementDisplayed(By.cssSelector("[id$="+confirmedType+"DateConfirmed_input]"));
		String shipDate = attribute.getElementAttribute(By.cssSelector("[id$="+confirmedType+"DateConfirmed_input]"), "value");
		String[] newDate = shipDate.split("/");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		String outputText = "";

		try {
			outputText = dateFormat.format(dateFormat.parse(newDate[2] + "-" + newDate[0] + "-" + newDate[1]));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return outputText;
	}

	/**
	 * Click on "Sales Tax Group" drop down field
	 */
	public void clickAndSetSalesTaxGroup(String group)
	{
		waitForPageLoad();
		this.expandHeader(SETUP_HEADER);
		try {
			hover.hoverOverElement(By.xpath("//div[@name=\"TaxGroup\"]"));
		} catch(Exception ex){}
		click.clickElementCarefully(SALES_TAX_GROUP_EDIT);
		wait.waitForElementEnabled(SALES_TAX_GROUP);
		click.clickElement(SALES_TAX_GROUP);
		text.enterText(SALES_TAX_GROUP, group);
	}

	/**
	 * Set Sales Tax Group
	 *
	 * @param salesTaxGroup
	 */
	public void setSalesTaxGroup( String salesTaxGroup )
	{
		text.enterText(SALES_TAX_GROUP, salesTaxGroup);
	}

	//fixme remove from here

	/**
	 * Enter "Customer account"
	 *
	 * @param customerAccount
	 */
	public void setCustomerAccount( String customerAccount )
	{
		waitForPageLoad();
		wait.waitForElementEnabled(TEXT_CUSTOMER_ACCOUNT);
		text.enterText(TEXT_CUSTOMER_ACCOUNT, customerAccount);
		text.pressTab(TEXT_CUSTOMER_ACCOUNT);
	}

	/**
	 * Enter "Customer account" for All free text invoice
	 * @param customerAccount
	 */
	public void setCustomerAccountForAllFreeInvoice(String customerAccount){
		wait.waitForElementEnabled(FREE_INVOICE_CUSTOMER_ACCOUNT);
		text.enterText(FREE_INVOICE_CUSTOMER_ACCOUNT, customerAccount);
		text.pressTab(FREE_INVOICE_CUSTOMER_ACCOUNT);
	}

	/**
	 * Settles and posts the sales tax
	 */
	public void settleAndPostSalesTax(){
		wait.waitForElementDisplayed(SETTLEMENT_PERIOD_SELECTION);
		click.clickElementCarefully(SETTLEMENT_PERIOD_SELECTION);
		text.clickElementAndEnterText(SETTLEMENT_PERIOD_SELECTION,"gen");

		wait.waitForElementDisplayed(FROM_DATE_SELECTION);
		click.clickElementCarefully(FROM_DATE_SELECTION);
		Format f = new SimpleDateFormat("MM/dd/yyyy");
		String strDate = f.format(new Date());

		text.clickElementAndEnterText(FROM_DATE_SELECTION, strDate);

		wait.waitForElementDisplayed(SALES_TAX_PAYMENT_VERSION_SELECTION);
		click.clickElementCarefully(SALES_TAX_PAYMENT_VERSION_SELECTION);
		wait.waitForElementDisplayed(LATEST_CORRECTIONS_SELECTION);
		click.clickElementCarefully(LATEST_CORRECTIONS_SELECTION);
	}

	/**
	 * Click on the Voucher number in the posted sales tax
	 */
	public void clickOnVoucher(){
		wait.waitForElementDisplayed(POSTED_SALES_TAX_VOUCHER);
		click.clickElementCarefully(POSTED_SALES_TAX_VOUCHER);
		click.clickElementCarefully(POSTED_SALES_TAX_VOUCHER);
		waitForPageLoad();
	}

	/**
	 * gets Account Name
	 *
	 * @return returns attribute found
	 */
	public String getAccountName()
	{
		List <WebElement> voucherList = wait.waitForAllElementsPresent(VOUCHER_LIST);

		String accountNameType = "";
		for(int i = voucherList.size() - 3; i <= voucherList.size(); i++){
			wait.waitForElementDisplayed(driver.findElement(ACCOUNT_NAME_TYPE));
			accountNameType = attribute.getElementAttribute(ACCOUNT_NAME_TYPE, "value");
		}
		return accountNameType;
	}

	/**
	 * Click on "Lines" option
	 */
	public void openLinesTab( )
	{
		WebElement linesLink = getLinesLink();
		if ( linesLink != null )
		{
			click.clickElementCarefully(linesLink);
		}
	}

	/**
	 * Go to "Sales order lines" section
	 */
	public void mouseOverOnSalesOrderLines( )
	{
		click.moveToElementAndClick(SALES_ORDER_LINES);
	}

	/**
	 * Click on Add New Line
	 */
	public void addLineItem( )
	{
		wait.waitForElementDisplayed(ADD_NEW_LINE);
		click.clickElement(ADD_NEW_LINE);
	}

	/**
	 * Set Item Number
	 */
	public void clickOnItemNumber( )
	{
		wait.waitForElementEnabled(ITEM_NUMBER);
		click.clickElementCarefully(ITEM_NUMBER);
	}

	/**
	 * Set Site
	 */
	public void clickOnSite( )
	{
		wait.waitForElementEnabled(SITE);
		click.clickElementCarefully(SITE);
	}

	/**
	 * writes the given value to some cell in the table of line items under the current sales order
	 *
	 * @param column which column the cell to be edited is under
	 * @param row    which row contains the cell whose contents should be replaced
	 *               note- this is a 1-based index because it's called in the test suite
	 * @param value  the text which the cell will hold
	 */
	public void setLineItemField( final LineItemColumn column, final int row, final String value )
	{
		final int actualRowIndex = row - 1;
		WebElement tableCell = getSalesOrderLinesCell(column, actualRowIndex);
		setSalesOrderLinesTextCell(tableCell, value);
	}

	/**
	 * Sets the Item Number for some line item in the current sales order
	 *
	 * @param row        which line item to change the item id of
	 * @param itemNumber the id of some line item in the sales order
	 */
	public void setItemNumber( final int row, final String itemNumber )
	{
		setLineItemField(LineItemColumn.ITEM_NUMBER, row, itemNumber);
	}

	/**
	 * Sets the site for some line item in the current sales order
	 *
	 * @param row  which line item to change the site of
	 * @param site the site of some line item in the sales order
	 */
	public void setSite( final int row, final String site )
	{
		setLineItemField(LineItemColumn.SITE, row, site);
	}

	/**
	 * Sets the warehouse for some line item in the current sales order
	 *
	 * @param row       which line item to change the warehouse of
	 * @param warehouse the warehouse of some line item in the sales order
	 */
	public void setWarehouse( final int row, final String warehouse )
	{
		setLineItemField(LineItemColumn.WAREHOUSE, row, warehouse);
	}

	/**
	 * Sets the unit price for some line item in the current sales order
	 *
	 * @param row       which line item to change the unit price of
	 * @param unitPrice the unit price of some line item in the sales order
	 */
	public DFinanceHomePage setUnitPrice( final int row, final String unitPrice )
	{
		setLineItemField(LineItemColumn.UNIT_PRICE, row, unitPrice);
		//fixme ??? eliminate this
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		return homePage;
	}

	/**
	 * Sets the discount for some line item in the current sales order
	 *
	 * @param row      which line item to change the discount of
	 * @param discount the discount of some line item in the sales order
	 */
	public void setDiscount( final int row, final String discount )
	{
		setLineItemField(LineItemColumn.DISCOUNT, row, discount);
	}

	/**
	 * Click on Save button from sales tax groups page
	 */
	public void clickOnSaveButton( )
	{
		waitForPageLoad();
		click.clickElement(SAVE_SALES_TAX_GROUP);
	}

	/**
	 * Enter the item quantity
	 */
	public void enterLineQuantity(String itemQuantity )
	{
		wait.waitForElementDisplayed(ENTER_QUANTITY);
		click.clickElement(ENTER_QUANTITY);
		text.clearText(ENTER_QUANTITY);
		text.enterText(ENTER_QUANTITY, itemQuantity);
	}

	/**
	 * It Click "No" on lead time Changed Popup
	 */
	public void clickNOOnLeadTimePopup()
	{
		if(element.isElementDisplayed(leadTimeChanged))
		{
			clickOnNoButton();
		}
	}

	/**
	 * Click on update line from the Add line menu options
	 */
	public void clickOnUpdateLine( )
	{
		if(element.isElementDisplayed(UPDATE_LINE)) {
			wait.waitForElementDisplayed(UPDATE_LINE);
			click.clickElement(UPDATE_LINE);
		}else if(element.isElementPresent(UPDATE_LINE_RETURN_ORDER)){
			click.clickElementCarefully(UPDATE_LINE_RETURN_ORDER);
		}else if(!element.isElementDisplayed(UPDATE_LINE)){
				click.clickElementCarefully(ELLIPSIS);
				wait.waitForElementDisplayed(UPDATE_LINE);
				click.clickElement(UPDATE_LINE);
		}
	}

	/**
	 * Click on Registration in the Update Line item option
	 * @param dispositionCode - the code to assign to the return line
	 */
	public void clickOnRegistration(String dispositionCode){
		wait.waitForElementDisplayed(UPDATE_REGISTRATION);
		click.clickElementCarefully(UPDATE_REGISTRATION);
		text.clickElementAndEnterText(DISPOSITION_CODE, dispositionCode);
		wait.waitForElementDisplayed(DISPOSITION_CODE_DROPDOWN);
		click.javascriptClick(DISPOSITION_CODE_DROPDOWN);
	}

	/**
	 * Click on the Add Registration line
	 */
	public void clickOnAddRegistrationLine(){
		wait.waitForElementDisplayed(ADD_REGISTRATION_LINE);
		click.clickElementCarefully(ADD_REGISTRATION_LINE);
	}

	/**
	 * Click on the Add Registration line
	 */
	public void clickOnConfirmRegistration(){
		wait.waitForElementDisplayed(CONFIRM_REGISTRATION);
		click.clickElementCarefully(CONFIRM_REGISTRATION);
	}

	/**
	 * Click on the Sales Order Number
	 * return - value of the Sales Order Number
	 */
	public String getSalesOrderNumberForReturnOrder(){
		wait.waitForElementDisplayed(SALES_ORDER_NUMBER_FOR_RETURN_ORDER);
		String salesOrderNumber = attribute.getElementAttribute(SALES_ORDER_NUMBER_FOR_RETURN_ORDER, "value");
		return salesOrderNumber;
	}

	/**
	 * Select delivery remainder from the menu option
	 */
	public void selectDeliveryRemainder( )
	{
		wait.waitForElementDisplayed(DELIVERY_REMAINDER);
		click.clickElement(DELIVERY_REMAINDER);

	}

	/**
	 * Change the item quantity
	 */
	public void changeQuantity(String quantity )
	{
		wait.waitForElementDisplayed(CHANGE_QUANTITY);
		click.javascriptClick(CHANGE_QUANTITY);
		text.clearText(CHANGE_QUANTITY);
		text.enterText(CHANGE_QUANTITY, quantity);
	}

	/**
	 * gets attribute from web element
	 *
	 * @return returns attribute found
	 */
	public String getDeliveryName( )
	{
		wait.waitForElementDisplayed(driver.findElement(DELIVERY_NAME));
		String deliveryName = attribute.getElementAttribute(DELIVERY_NAME, "title");
		return deliveryName;
	}

	/**
	 * gets attribute from web element
	 *
	 * @return returns attribute found
	 */
	public String getDeliveryAddress( )
	{
		wait.waitForElementDisplayed(driver.findElement(DELIVERY_ADDRESS));
		String deliveryAddress = attribute.getElementAttribute(DELIVERY_ADDRESS, "title");
		return deliveryAddress;
	}

	/**
	 * gets currency
	 *
	 * @return returns attribute found
	 */
	public String getCurrency( )
	{
		wait.waitForElementDisplayed(driver.findElement(CURRENCY));
		String currency = attribute.getElementAttribute(CURRENCY, "value");
		return currency;
	}

	/**
	 * gets Order Type
	 *
	 * @return returns attribute found
	 */
	public String getOrderType( )
	{
		wait.waitForElementDisplayed(driver.findElement(ORDER_TYPE));
		String orderType = attribute.getElementAttribute(ORDER_TYPE, "value");
		return orderType;
	}

	/**
	 * Clear cash discount
	 */
	public void clearCashDiscount( )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(CASH_DISCOUNT);
		wait.waitForElementEnabled(CASH_DISCOUNT);
		click.moveToElementAndClick(CASH_DISCOUNT);
		text.clearText(CASH_DISCOUNT);
	}

	/**
	 * Click on "X" to close the Sales Order Screen
	 * @param closeButtonLocation
	 */
	public void clickOnCloseSalesOrderPage(String closeButtonLocation)
	{
		System.out.println(By.xpath("(//button[@name='SystemDefinedCloseButton'])['"+closeButtonLocation+"']"));
		WebElement closeButton = wait.waitForElementEnabled(By.xpath("(//button[@name='SystemDefinedCloseButton'])["+closeButtonLocation+"]"));
		wait.waitForElementDisplayed(closeButton);
		click.clickElement(closeButton);
	}

	/**
	 * click on "+" icon available at the "Delivery address" field to enter a new address
	 */
	public void clickPlusIconForAddAddress( )
	{
		wait.waitForElementEnabled(ADD_ADDRESS);
		wait.waitForElementDisplayed(ADD_ADDRESS);
		click.clickElement(ADD_ADDRESS);
	}

	/**
	 * Go to Line Details and select Address, than click on "+" icon available at the "Delivery address" field to update address
	 */
	public void updateAddress( )
	{
		wait.waitForElementDisplayed(ADDRESS_TAB);
		click.clickElementIgnoreExceptionAndRetry(ADDRESS_TAB);
		wait.waitForElementEnabled(ADD_ADDRESS2);
		wait.waitForElementDisplayed(ADD_ADDRESS2);
		click.clickElementIgnoreExceptionAndRetry(ADD_ADDRESS2);
	}

	/**
	 * click on "+" icon available at the "Delivery address" field to update address
	 */
	public void clickPlusIconForAddAddress1()
	{
		wait.waitForElementDisplayed(EXPAND_ADDRESS);
		String isExpanded = attribute.getElementAttribute(EXPAND_ADDRESS, "aria-expanded");

		if ( !isExpanded.equalsIgnoreCase("true") )
		{
			click.clickElementCarefully(EXPAND_ADDRESS);
		}
		wait.waitForElementEnabled(ADD_ADDRESS1);
		wait.waitForElementDisplayed(ADD_ADDRESS1);
		click.clickElement(ADD_ADDRESS1);
	}

	/**
	 * Enter "Description"
	 *
	 * @param description
	 */
	public void setDescription( String description )
	{
		wait.waitForElementEnabled(NEW_ADDRESS_DESCRIPTION);
		wait.waitForElementDisplayed(NEW_ADDRESS_DESCRIPTION);
		text.enterText(NEW_ADDRESS_DESCRIPTION, description);
	}

	/**
	 * Enter 5-digit ZIP/Postal Code
	 *
	 * @param zipCode
	 */
	public void setZipCode( String zipCode )
	{
		wait.waitForElementEnabled(ZIPCODE);
		wait.waitForElementDisplayed(ZIPCODE);
		text.enterText(ZIPCODE, zipCode);
	}

	/**
	 * Enter "Street"
	 *
	 * @param street
	 */
	public void setStreet( String street )
	{
		wait.waitForElementEnabled(STREET);
		wait.waitForElementDisplayed(STREET);
		text.enterText(STREET, street);
	}

	/**
	 * Enter "City"
	 *
	 * @param city
	 */
	public void setCity( String city )
	{
		wait.waitForElementEnabled(CITY);
		wait.waitForElementDisplayed(CITY);
		text.enterText(CITY, city);
	}

	/**
	 * Enter "State"
	 *
	 * @param state
	 */
	public void setState( String state )
	{
		wait.waitForElementEnabled(STATE);
		wait.waitForElementDisplayed(STATE);
		text.enterText(STATE, state);
	}

	/**
	 * Enter "Country"
	 *
	 * @param country
	 */
	public void setCountry( String country )
	{
		wait.waitForElementEnabled(COUNTRY);
		wait.waitForElementDisplayed(COUNTRY);
		text.enterText(COUNTRY, country);
	}

	/**
	 * Enter "County"
	 *
	 * @param county
	 */
	public void setCounty( String county )
	{
		wait.waitForElementEnabled(COUNTY);
		wait.waitForElementDisplayed(COUNTY);
		text.enterText(COUNTY, county);
		action.sendKeys(Keys.ENTER).perform();
	}

	/**
     * Gets the Zip Code of the Cleansed Address
     */
    public String getZipCode(){
        wait.waitForElementDisplayed(driver.findElement(GET_CLEANSED_ZIPCODE));
        String zipCode = attribute.getElementAttribute(GET_CLEANSED_ZIPCODE, "value");
        return zipCode;
    }

    /**
     * Click on Cancel button to cancel Vertex Address Validation Results
     */
    public void clickOnCancelVertexAddressValidation( )
    {
        wait.waitForElementEnabled(CANCEL);
        click.clickElement(CANCEL);
    }

	/**
	 * Click on "Ok" button
	 *
	 * @return
	 */
	public void clickOk( )
	{
		wait.waitForElementPresent(OK_ADDRESS_BUTTON);
		wait.waitForElementEnabled(OK_ADDRESS_BUTTON);
		wait.waitForElementDisplayed(OK_ADDRESS_BUTTON);
		scroll.scrollElementIntoView(OK_ADDRESS_BUTTON);
		click.clickElementIgnoreExceptionAndRetry(OK_ADDRESS_BUTTON);
		waitForPageLoad();
	}

	/**
	 * Click on the Post button to post the Free text invoice
	 */
	public void clickPostBtn(){
		wait.waitForElementDisplayed(POST_BUTTON);
		click.clickElementCarefully(POST_BUTTON);
	}

	/**
	 * Click on "yes" at pop up
	 *
	 */
	public void clickYes( )
	{
		wait.waitForElementDisplayed(ADDRESS_POPUP);
		click.clickElement(ADDRESS_POPUP);
	}

	/**
	 * Click on "Ok" button on edit dialogue window
	 *
	 * @return homepage
	 */
	public DFinanceHomePage clickOkBTN( )
	{
		wait.waitForElementEnabled(SALES_ORDER_OK_BTN);
		wait.waitForElementDisplayed(SALES_ORDER_OK_BTN);
		click.clickElementIgnoreExceptionAndRetry(SALES_ORDER_OK_BTN);
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		return homePage;
	}

	/**
	 * Get address to verify zip code
	 *
	 * @return
	 */
	public String getAddress( )
	{
		wait.waitForElementPresent(RETURN_ADDRESS);
		String address = attribute.getElementAttribute(RETURN_ADDRESS, "title");
		return address;
	}

	/**
	 * verify zip code by splitting address
	 *
	 * @param 'addressReturn' result address to be verified
	 */
	public void verifyZipCode( String addressReturn )
	{
		int position = addressReturn.lastIndexOf(" ");
		System.out.println(position);
		String zipCodeAddress = addressReturn.substring(position - 0, position + 6);
		if ( zipCodeAddress.length() == 10 )
		{
			String cleansedZipCodeMessage = String.format("Zip code is cleansed(i.e. %s)", zipCodeAddress);
			VertexLogger.log(cleansedZipCodeMessage);
		}
		else
		{
			String failedZipCodeCleanseMessage = String.format("Zip code is NOT cleansed(i.e. %s)", zipCodeAddress);
			VertexLogger.log(failedZipCodeCleanseMessage, VertexLogLevel.ERROR);
		}
	}

	/**
	 * Click on New Address Sales Order
	 */
	public void newAddressSalesOrder( )
	{
		wait.waitForElementEnabled(NEW_ADDRESS_SALESORDER);
		click.clickElement(NEW_ADDRESS_SALESORDER);
	}

	/**
	 * Click on Add a New Delivery Address Sales Order
	 */
	public void newDeliveryAddressSalesOrder( )
	{
		wait.waitForElementEnabled(NEW_DELIVERY_ADDRESS_SALESORDER);
		click.clickElement(NEW_DELIVERY_ADDRESS_SALESORDER);
	}

	/**
	 * Delete the #303 from Street Line-1 or Enter 'Street Line-1   =  3104 E Palouse Hwy Ste A '
	 *
	 * @param streetLine1
	 */
	public void updateStreetLine1( String streetLine1 )
	{
		wait.waitForElementDisplayed(UPDATE_STREET_LINE1);
		text.enterText(UPDATE_STREET_LINE1, streetLine1);
	}

	/**
	 * Enter APT#303 in Street line-2 or Enter 'Street Line-2  =    APT#303'
	 *
	 * @param streetLine2
	 */
	public void updateStreetLine2( String streetLine2 )
	{
		wait.waitForElementDisplayed(UPDATE_STREET_LINE2);
		text.enterText(UPDATE_STREET_LINE2, streetLine2);
	}

	/**
	 * Check the presence of "Edit Street" option above "Recommended Addresses" table on the address
	 * pop-up
	 *
	 * @return status of street displayed
	 */
	public boolean verifyEditStreet( )
	{
		boolean status = false;
		wait.waitForElementDisplayed(EDIT_STREET);
		if ( element.isElementDisplayed(EDIT_STREET) )
		{
			status = true;
		}
		return status;
	}

	/**
	 * Click on "Edit Street" option from the pop-up ("Vertex Street Update" pop-up should be
	 * displayed with the vertex returned address in the "Street Line-1" section)
	 */
	public void clickOnEditStreet( )
	{
		wait.waitForElementDisplayed(EDIT_STREET);
		click.clickElement(EDIT_STREET);
	}

	/**
	 * Click on "OK" button from the "Vertex Street Update" pop-up
	 */
	public void clickOnOKButton( )
	{
		WebElement clickOkAgain = wait.waitForElementDisplayed(OK_UPDATE_STREET_BUTTON);
		click.clickElement(OK_UPDATE_STREET_BUTTON);
	}

	/**
	 * Check the "Street Line-1"
	 *
	 * @param
	 *
	 * @return streetline1 text
	 */
	public String validateStreetLine1( )
	{
		wait.waitForElementDisplayed(STREETLINE1_VALUE);
		waitForPageLoad();
		String streetLine1 = driver
			.findElement(STREETLINE1_VALUE)
			.getAttribute("title");

		System.out.println(streetLine1);
		return streetLine1;
	}

	/**
	 * Check the "Street Line-2"
	 *
	 * @param
	 *
	 * @return streetline2 text
	 */
	public String validateStreetLine2( )
	{
		WebElement line1Start = driver.findElement(STREETLINE2_VALUE);
		String streetLine2 = line1Start.getAttribute("title");
		return streetLine2;
	}

	/**
	 * Check the presence of confidence percentage for the addresses on the screen
	 *
	 * @return confidence
	 */
	public String verifyConfidenceValueStatus( )
	{
		String confidence = attribute.getElementAttribute(CONFIDENCE_VALUE, "value");
		return confidence;
	}

	/**
	 * Click on Yes
	 *
	 * @return homepage
	 */
	public DFinanceHomePage clickOnYesPopUp( )
	{
		final WebDriverWait shortWait = new WebDriverWait(driver, 10);
		shortWait
				.ignoring(NoSuchElementException.class)
				.until(ExpectedConditions.elementToBeClickable(ACCEPT_POPUP));
		click.clickElement(ACCEPT_POPUP);
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		return homePage;
	}

	/**
	 * Click on Ok
	 *
	 * @return homepage
	 */
	public DFinanceHomePage clickOnOKPopUp( )
	{
		final WebDriverWait shortWait = new WebDriverWait(driver, 10);
		shortWait
				.ignoring(NoSuchElementException.class)
				.until(ExpectedConditions.elementToBeClickable(ACCEPT_OK_POPUP));
		click.clickElementIgnoreExceptionAndRetry(ACCEPT_OK_POPUP);
		waitForPageLoad();
		wait.waitForElementDisplayed(COMPLETE_MSG);
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		waitForPageLoad();
		return homePage;
	}

	/**
	 * Click on Ok for document post popup
	 */
	public void clickOkForPostWithoutPrinting()
	{
		final WebDriverWait shortWait = new WebDriverWait(driver, 10);
		shortWait
				.ignoring(NoSuchElementException.class)
				.until(ExpectedConditions.elementToBeClickable(POST_OK));
		click.clickElement(POST_OK);
	}

	/**
	 * Get Error message
	 *
	 * @return error message
	 */
	public String getErrorMessageFromAddressPopup( )
	{
		wait.waitForElementDisplayed(driver.findElement(ERROR_ADDRESS_POPUP));
		String errorMessage = attribute.getElementAttribute(ERROR_ADDRESS_POPUP, "text");
		return errorMessage;
	}

	/**
	 * Get Error message regarding no tax areas being found
	 *
	 * @return error message
	 */
	public String getErrorMessageNoTaxAreaFoundPopup( )
	{
		if(element.isElementDisplayed(EXPAND_MESSAGE))
			click.clickElementIgnoreExceptionAndRetry(EXPAND_MESSAGE);
		wait.waitForElementDisplayed(driver.findElement(NO_TAXES_FOUND_POPUP));
		String errorMessage = attribute.getElementAttribute(NO_TAXES_FOUND_POPUP, "title");
		return errorMessage;
	}

	/**
	 * get error message text
	 *
	 * @return returns element text
	 */
	public String getErrorMessage( )
	{
		if(element.isElementDisplayed(EXPAND_MESSAGE))
			click.clickElementIgnoreExceptionAndRetry(EXPAND_MESSAGE);
		WebElement errorMessages;
		String getTitle;
		if(element.isElementDisplayed(NEW_ADDRESS_ERROR))
		{
			wait.waitForElementDisplayed(NEW_ADDRESS_ERROR);
			errorMessages = element.getWebElement(NEW_ADDRESS_ERROR);
			getTitle = attribute.getElementAttribute(errorMessages, "title");
		}
		else
		{
			wait.waitForElementDisplayed(POPUP_ADDRESS_ERROR);
			errorMessages = element.getWebElement(POPUP_ADDRESS_ERROR);
			getTitle = element.getWebElement(POPUP_ADDRESS_ERROR).getText();
		}
		return getTitle;
	}

	/**
	 * Click On No
	 */
	public void clickOnNoButton( )
	{
		click.clickElement(NO_BUTTON);
	}

	/**
	 * Click on Yes button
	 *
	 * @return home page
	 */
	public DFinanceHomePage clickOnYes( )
	{
		wait.waitForElementDisplayed(YES_BUTTON);
		click.clickElement(YES_BUTTON);
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		return homePage;
	}

	/**
	 * Click on Cancel button to cancel Sales Order
	 *
	 * @return home page
	 */
	public DFinanceHomePage clickOnCancel( )
	{
		wait.waitForElementEnabled(CANCEL_BUTTON);
		click.clickElement(CANCEL_BUTTON);
		DFinanceHomePage homePage = initializePageObject(DFinanceHomePage.class);
		return homePage;
	}

	/**
	 * Click on Ok button to close dialog box
	 */
	public void clickOnOKBtn() {
		wait.waitForElementEnabled(OK_BUTTON);
		click.clickElementIgnoreExceptionAndRetry(OK_BUTTON);
		jsWaiter.sleep(15000);
	}

	/**
	 * Close Confirmation
	 */
	public void closeConfirmationWindow()
	{
		WebElement ele = wait.waitForElementDisplayed(CLOSE);
		click.clickElementCarefully(ele);
	}
	/**
	 * Click on Ok button to close dialog box
	 * And retrieves the Invoice number
	 */
	public void clickOnOKBtnForAllFreeTextInvoiceOrderPosting() {
		wait.waitForElementEnabled(OK_BUTTON);
		click.javascriptClick(OK_BUTTON);
		jsWaiter.sleep(15000);
	}

	/**
	 * This method is used to navigate to SELL --> CHARGES --> Charges page
	 */
	public void navigateToChargesPage( )
	{
		boolean isChargesLinkDisplayed = element.isElementDisplayed(CHARGES_LINK);

		if ( !isChargesLinkDisplayed )
		{
			String sell = sellTabName;

			// click SELL tab
			clickOnTab(sell);
			wait.waitForElementDisplayed(CHARGES_LINK);
		}
		// click Charges link
		click.javascriptClick(CHARGES_LINK);
		waitForPageLoad();
	}

	/**
	 * TODO
	 *
	 * Add Charges line to the SO
	 *
	 * @param chargeCode
	 * @param category
	 * @param chargeValue
	 * @param salesTaxGroup
	 * @param itemSalesTaxGroup
	 */
	public void addCharges( String chargeCode, boolean keepValue, String category, String chargeValue, String salesTaxGroup, String itemSalesTaxGroup )
	{
		// click New button to add charge value
		waitForPageLoad();
		wait.waitForElementDisplayed(CHARGES_NEW_BUTTON);
		wait.waitForElementEnabled(CHARGES_NEW_BUTTON);
		click.javascriptClick(CHARGES_NEW_BUTTON);
		waitForPageLoad();

		String ROW_LOCATOR = "(//div[@aria-rowindex='2'])[2]";
		wait.waitForElementDisplayed(By.xpath(ROW_LOCATOR));

		String CELL_INPUT_LOCATOR = "(//div[@aria-rowindex='2'])[2]//input[contains(@aria-label,'%s')]";
		String KEEP_CELL_INPUT_LOCATOR = "(//div[@aria-rowindex='2'])[2]//div[contains(@aria-label,'%s')]";

		By CHARGES_CODE_INPUT = By.xpath(String.format(CELL_INPUT_LOCATOR, "Charges code"));
		By CATEGORY_VALUE_INPUT = By.xpath(String.format(CELL_INPUT_LOCATOR+"/../div", "Category"));
		By KEEP_INPUT = By.xpath(String.format(KEEP_CELL_INPUT_LOCATOR+"/../div", "Keep"));
		By CHARGES_VALUE_INPUT = By.xpath(String.format(CELL_INPUT_LOCATOR, "Charges value"));
		By SALES_TAX_GROUP_INPUT = By.xpath(String.format(CELL_INPUT_LOCATOR, "Sales tax group"));
		By ITEM_SALES_TAX_GROUP_INPUT = By.xpath(
			String.format(CELL_INPUT_LOCATOR, "Item sales tax group"));

		wait.waitForElementEnabled(CHARGES_CODE_INPUT);
		click.clickElement(CHARGES_CODE_INPUT);
		text.setTextFieldCarefully(CHARGES_CODE_INPUT, chargeCode, false);

		if(keepValue == true) {
			wait.waitForElementEnabled(KEEP_INPUT);
			click.javascriptClick(KEEP_INPUT);
		}

		click.javascriptClick(CATEGORY_VALUE_INPUT);
		By categoryType = By.xpath("//li[contains(@id, 'MarkupTrans_MarkupCategory1') and contains(text(), '"+category+"')]");

		waitForPageLoad();
		if(!element.isElementDisplayed(categoryType))
			click.moveToElementAndClick(CATEGORY_VALUE_INPUT);
			wait.waitForElementDisplayed(categoryType);
			click.clickElementIgnoreExceptionAndRetry(categoryType);

		text.setTextFieldCarefully(CHARGES_VALUE_INPUT, chargeValue, false);
		WebElement SALES_TAX_GROUP_INPUT_ELE = wait.waitForElementPresent(SALES_TAX_GROUP_INPUT);
		action.click(SALES_TAX_GROUP_INPUT_ELE).perform();
		text.setTextFieldCarefully(SALES_TAX_GROUP_INPUT, salesTaxGroup + Keys.ENTER, false);
		text.setTextFieldCarefully(ITEM_SALES_TAX_GROUP_INPUT, itemSalesTaxGroup, false);

		wait.waitForElementDisplayed(CHARGES_SAVE_BUTTON);
		click.clickElement(CHARGES_SAVE_BUTTON);
		waitForPageLoad();
	}

	/**
	 * Close the Charges page.
	 */
	public void closeCharges( )
	{
		wait.waitForElementDisplayed(CHARGES_CLOSE_BUTTON);
		click.clickElement(CHARGES_CLOSE_BUTTON);
		waitForPageLoad();
	}

	/**
	 * TODO
	 *
	 * Add a line item to the SO
	 *
	 * @param item
	 * @param qty
	 * @param site
	 * @param warehouse
	 * @param unitPrice
	 */
	public void addItemLine( String item, String qty, String site, String warehouse, String unitPrice )
	{
		WebElement linesLink = getLinesLink();
		wait.waitForElementDisplayed(linesLink);
		click.javascriptClick(linesLink);

		String rowLocator = LINE_ITEM_ROW;
		int rowCount = element
			.getWebElements(By.xpath(rowLocator))
			.size();
		if(element.isElementDisplayed(ADD_NEW_LINE))
		{
			click.clickElementCarefully(ADD_NEW_LINE);
		}
		wait.waitForElementDisplayed(By.xpath(rowLocator));
		int lastRowNumber = rowCount;

		while ( (rowCount == lastRowNumber) && (rowCount >1))
		{
			System.out.println(lastRowNumber);
			waitForPageLoad();
			lastRowNumber = element
					.getWebElements(By.xpath(rowLocator))
					.size();
		}
		String cellInputLocator = "//div[@aria-rowindex='"+(rowCount + 1)+"']//input[contains(@aria-label,'%s')]";

		By itemNumberInput = By.xpath(String.format(cellInputLocator,"Item number"));
		By siteInput = By.xpath(String.format(cellInputLocator,"Site"));
		By quantityInput = By.xpath(String.format(cellInputLocator, "Quantity"));
		By warehouseInput = By.xpath(String.format(cellInputLocator, "Warehouse"));
		By unitPriceInput = By.xpath(String.format(cellInputLocator, "Unit price"));

		text.clickElementAndEnterText(itemNumberInput, item + Keys.TAB);
		WebElement SALES_TAX_GROUP_INPUT_ELE = wait.waitForElementPresent(quantityInput);
		action.click(SALES_TAX_GROUP_INPUT_ELE).perform();
		text.setTextFieldCarefully(SALES_TAX_GROUP_INPUT_ELE, qty, false);
		WebElement SITE_INPUT_ELE = wait.waitForElementPresent(siteInput);
		action.click(SITE_INPUT_ELE).perform();
		text.setTextFieldCarefully(SITE_INPUT_ELE, site, false);
		WebElement WAREHOUSE_INPUT_ELE = wait.waitForElementPresent(warehouseInput);
		action.click(WAREHOUSE_INPUT_ELE).perform();
		text.setTextFieldCarefully(WAREHOUSE_INPUT_ELE, warehouse, false);
		WebElement UNIT_PRICE_INPUT_ELE = wait.waitForElementPresent(unitPriceInput);
		action.click(UNIT_PRICE_INPUT_ELE).perform();
		text.setTextFieldCarefully(UNIT_PRICE_INPUT_ELE, unitPrice, false);
	}

	public void removeLineItem( String itemNumber )
	{
		// select Item row
		selectItemRow(itemNumber);
		// remove item
		clickRemoveLineItemButton();
	}

	public void clickRemoveLineItemButton( )
	{
		wait.waitForElementDisplayed(REMOVE_ITEM_BUTTON);
		click.clickElementIgnoreExceptionAndRetry(REMOVE_ITEM_BUTTON);
		waitForPageLoad();
		clickOnYesPopUp();
	}

	public void updateItemQuantity( String itemNumber, String updateQuantity )
	{
		wait.waitForElementDisplayed(ADD_NEW_LINE);
		click.clickElementCarefully(ADD_NEW_LINE);
		WebElement siteField = wait.waitForElementPresent(QUANTITY);
		action.click(siteField).perform();
		wait.waitForElementEnabled(siteField);
		try
		{
			siteField.click();
		}
		catch ( ElementNotInteractableException e )
		{
			click.javascriptClick(siteField);
		}
		text.setTextFieldCarefully(siteField, updateQuantity, false);

		waitForPageLoad();
		// do not overwrite Discount percentage or Unit price
		if(element.isElementPresent(By.xpath(String.format(OVERWRITE_FIELD_LOCATOR, "Discount present")))){
			overwriteFieldValue("Discount percent", false);
		}
		if(element.isElementPresent(By.xpath(String.format(OVERWRITE_FIELD_LOCATOR, "Discount present"))))
		{
			overwriteFieldValue("Unit price", false);
			clickOverwriteOkButton();
		}
		if(element.isElementDisplayed(leadTimeChanged))
		{
			clickOnNoButton();
		}
	}

	public void selectItemRow( String itemNumber )
	{
		String itemRowLocator = String.format(
			"//input[contains(@value, '%s')]", itemNumber);
		// select required Item row
		WebElement row = wait.waitForElementPresent(By.xpath(itemRowLocator));
		scroll.scrollElementIntoView(row);
		try {
			click.performDoubleClick(row);
		} catch(Exception ex){}
		try {
			click.clickElementCarefully(row);
		} catch(Exception ex){}
	}

	public void overwriteFieldValue( String fieldName, boolean flag )
	{
		By overwriteFieldBy = By.xpath(String.format(OVERWRITE_FIELD_LOCATOR, fieldName));

		wait.waitForElementEnabled(overwriteFieldBy,10);
		String isAriaChecked = attribute.getElementAttribute(overwriteFieldBy, "aria-checked");

		if ( isAriaChecked == null )
		{
			isAriaChecked = "false";
		}

		boolean isAriaCheckedBool = Boolean.parseBoolean(isAriaChecked);

		if ( isAriaCheckedBool && flag )
		{
			VertexLogger.log(String.format("Filed: %s is already in expected state (i.e. %s)", fieldName, flag));
		}
		else
		{
			click.clickElementCarefully(overwriteFieldBy);
		}
	}

	public void clickOverwriteOkButton( )
	{
		wait.waitForElementDisplayed(OVERWRITE_OK_BUTTON);
		click.clickElement(OVERWRITE_OK_BUTTON);
		waitForPageLoad();
	}

	public void updateAllLines( )
	{
		List<WebElement> allRadioButtons = element.getWebElements(UPDATE_ORDER_LINE_RADIO_BUTTON);

		boolean flag = false;
		for ( WebElement radioButton : allRadioButtons )
		{
			String radioButtonLabel = radioButton.getText();

			if ( radioButtonLabel.contains("Update all lines") )
			{
				click.clickElement(radioButton);
				VertexLogger.log("Update all lines radio button clicked");
				flag = true;
			}
		}
		if ( !flag )
		{
			VertexLogger.log("Update all lines radio button not found", VertexLogLevel.ERROR);
		}

		clickOverwriteOkButton();
	}

	/**
	 * finds the link to switch to the Lines tab in the sales order details page
	 *
	 * @return the link to switch to the Lines tab in the sales order details page
	 * returns null on error
	 */
	protected WebElement getLinesLink( )
	{
		WebElement linesLink = getTabLink(salesOrderDetailsLinesTabName);

		return linesLink;
	}

	/**
	 * finds the link to switch to the Header tab in the sales order details page
	 *
	 * @return the link to switch to the Header tab in the sales order details page
	 * returns null on error
	 */
	protected WebElement getHeaderLink( )
	{
		WebElement headerLink = getTabLink(salesOrderDetailsHeaderTabName);

		return headerLink;
	}

	/**
	 * finds the link to switch to a tab in the sales order details page
	 *
	 * @param tabName which tab to switch to
	 *
	 * @return the link to switch to a tab in the sales order details page
	 * returns null on error
	 */
	protected WebElement getTabLink( final String tabName )
	{
		WebElement tabLink = null;

		List<WebElement> links = wait.waitForAnyElementsDisplayed(By.xpath(String.format(salesOrderDetailsTab, tabName)));
		tabLink = element.selectElementByText(links, tabName);
		return tabLink;
	}

	@Getter
	public enum LineItemColumn
	{
		ITEM_NUMBER("Item number"),
		WAREHOUSE("Warehouse"),
		UNIT_PRICE("Unit price"),
		DISCOUNT("Discount"),
		SITE("Site");

		private final String name;

		LineItemColumn( final String columnName )
		{
			this.name = columnName;
		}
	}

	/**
	 * Checks to see if the close button is present and clicks it
	 * @param buttonLocation
	 */
	public void clickOnCloseBtn(String buttonLocation){
		By closeBtn = By.xpath("(//button[@name='SystemDefinedCloseButton'])["+buttonLocation+"]");

		if(element.isElementPresent(closeBtn)){
			click.clickElementCarefully(closeBtn);
		}
	}

	/**
	 * Validate the retail store
	 * @param retailStore
	 */
	public void validateRetailStore(String retailStore){

		By retailStoreName = By.xpath(String.format(RETAIL_STORE, retailStore));

		wait.waitForElementDisplayed(retailStoreName);
		click.clickElementCarefully(retailStoreName);

		wait.waitForElementDisplayed(ADD_ARROW);
		click.clickElementCarefully(ADD_ARROW);

	}

	/**
	 * Clicks on the Calculate Statement button
	 */
	public void clickCalculateStatement(){
		wait.waitForElementDisplayed(CALCULATE_STATEMENT);
		click.clickElementCarefully(CALCULATE_STATEMENT);
		wait.waitForElementDisplayed(YES_BUTTON);
		click.clickElementCarefully(YES_BUTTON);
	}

	/**
	 * Clicks on the Post statement button
	 */
	public void clickPostStatement(){
		wait.waitForElementDisplayed(POST_STATEMENT);
		click.javascriptClick(POST_STATEMENT);
		waitForPageLoad();
		if(element.isElementDisplayed(YES_BUTTON))
			click.clickElement(YES_BUTTON);
		jsWaiter.sleep(60000);
	}

	/**cd
	 * Clicks and enters the Counted Amount
	 */
	public void enterCountedAmount(){

		WebElement differenceAmount = driver.findElement(By.name("statementLine_differenceAmount"));
		String countAmounted = differenceAmount.getAttribute("title");

		wait.waitForElementDisplayed(COUNTED_AMOUNT);
		text.clickElementAndEnterText(COUNTED_AMOUNT, countAmounted.substring(1, countAmounted.length() - 0));
	}

	/**
	 * Click on Financials from the Add line menu options
	 */
	public void clickOnFinancials(){
		if(!element.isElementDisplayed(FINANCIALS)){
			click.clickElementCarefully(ELLIPSIS);
		}
		WebElement financialEle = wait.waitForElementPresent(FINANCIALS);
		action.moveToElement(financialEle).perform();
		click.javascriptClick(financialEle);
	}

	/**
	 * Select Maintain Charges from the Financials menu option of Sales Order Line
	 */
	public void selectMaintainCharges( )
	{
		wait.waitForElementDisplayed(MAINTAIN_CHARGES_IN_FINANCIALS);
		click.clickElement(MAINTAIN_CHARGES_IN_FINANCIALS);
	}

	/**
	 * Clicks the New button and the dropdown for calculating a new statement
	 * @param newStatementType
	 */
	public void clickNewOrderAndSelectNewStatementType(String newStatementType){
		wait.waitForElementDisplayed(NEW_STATEMENT);
		click.clickElementCarefully(NEW_STATEMENT);
		WebElement statementType = wait.waitForElementDisplayed(By.xpath("//button[@name='"+newStatementType+"']"));
		click.clickElementCarefully(statementType);
	}

	/**
	 * Verifies and sees that the tax area id appears the expected number of times in the sales tax
	 * @param expectedTaxAreaIdAmount
	 */
	public boolean verifyTaxAreaIdLineAmount(int expectedTaxAreaIdAmount){
		List<WebElement> taxAreaIdLineAmount = wait.waitForAllElementsPresent(TAX_AREA_ID);

		int actualAmount = taxAreaIdLineAmount.size();
		boolean totalAmount = false;

		if(expectedTaxAreaIdAmount == actualAmount){
			totalAmount = true;
		}

		return totalAmount;
	}

	/**
	 * Selects the Invoice Or Customer Account option for the Sales Tax/VAT Registration ID
	 * @param invoiceOrCustomerAccount
	 */
	public void selectInvoiceOrCustomerAccountOption(String invoiceOrCustomerAccount){
		wait.waitForElementDisplayed(SALES_TAX_VAT_REGISTRATION_ID_OPTION);
		click.clickElementCarefully(SALES_TAX_VAT_REGISTRATION_ID_OPTION);

		WebElement accountOption = wait.waitForElementDisplayed(By.xpath("//li[text()='"+invoiceOrCustomerAccount+"']"));
		click.clickElementCarefully(accountOption);
	}

	/**
	 * Set the Sales Taker in the Sales Header
	 * @param salesTaker
	 */
	public void setSalesTaker(String salesTaker){
		wait.waitForElementDisplayed(SALES_TAKER);
		text.setTextFieldCarefully(SALES_TAKER, salesTaker, false);
	}
}
