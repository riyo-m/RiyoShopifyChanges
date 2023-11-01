package com.vertex.quality.connectors.dynamics365.finance.pages.base;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

//TODO collapse all functions not on login page into DFinancePostSignOnPage

/**
 * @author Saidulu Kodadala ssalisbury
 */
public class DFinanceBasePage extends VertexPage
{
	protected By itemTableConLoc = By.className("listRootlist-hasRendered");
	//TODO common locators I pulled out and need to go in components
	protected By DELETE_YES_BUTTON = By.cssSelector(".button-label[id*='Yes']");
	protected By ITEM_FIELD = By.xpath("//input[contains(@aria-label,'Item number')]");
	protected By QUANTITY_FIELD = By.cssSelector("input[aria-label='Quantity']");
	protected By SITE_FIELD = By.name("InventoryDimensionsGrid_InventSiteId");
	protected By WAREHOUSE_FIELD = By.name("InventoryDimensionsGrid_InventLocationId");
	protected By UNITPRICE_FIELD =  By.xpath("(//input[@aria-label='Unit price'])");
	protected By DISCOUNT_FIELD = By.cssSelector("input[aria-label='Discount'][tabindex='0']");
	protected By MAIN_ACCOUNT_FIELD = By.xpath("//div[contains(@data-dyn-controlname, 'CustInvoiceLine_LedgerAccount')]//input[@tabindex='0']");
	protected By ITEM_SALES_GROUP_TAX_FIELD = By.cssSelector("input[aria-label='Item sales tax group AU/VI'][tabindex='0']");
	protected By DISCOUNT_PERCENTAGE_FIELD = By.cssSelector("input[aria-label='Discount percent'][tabindex='0']");
	protected By NEW_BUTTON = By.className("New-symbol");
	protected By addressDropDown = By.xpath("//button[contains(@id,'AddressTabPageFastTabExpand')]");
	protected By addAddressButton = By.xpath("//button[contains(@name, 'NewAddress')]");
	//TODO this locator must be improvable...
	protected By SAVE_BUTTON = By.cssSelector("[type='button'][name*='OK']");

	protected By EDIT_BUTTON = By.cssSelector("[class*='ViewEdit-symbol']");

	protected By ACCOUNT_TYPE = By.name("AccountType");
	protected By TEXT_CUSTOMER_ACCOUNT = By.xpath("//div[@data-dyn-controlname='MainTab']//div[contains(@class,'field-hasLookupButton')]//input[@name='SalesQuotationTable_CustAccount']");
	protected String TAB_LOCATOR = "*[type='button'][id*='%s'][id*='SalesQuotation'][id*='button']";
	protected By SALES_QUOTATION_LINE = By.xpath("//span[text()='Sales quotation line']");
	protected By MAINTAIN_CHARGES = By.xpath("//button[contains(@data-dyn-controlname, 'MarkupTrans_LineQuotation')]");
	protected By MAINTAIN_CHARGES_SALES_ORDER = By.xpath("//button[contains(@data-dyn-controlname, 'ButtonMarkupTransLine')]");
	protected By VENDOR_EXCHANGE_RATE_ENTRY_MSG = By.xpath("//span[contains(text(),'Vendor exchange rate')]");
	protected By VENDOR_EXCHANGE_RATE_CLOSE_MSG = By.xpath("(//button[@data-dyn-controlname='MessageBarClose'][@aria-label])[2]");
	protected By MESSAGE_BAR_CLOSE = By.xpath("//button[@data-dyn-controlname='MessageBarClose'][@aria-label]");
	protected By ORDER_ID = By.name("SourceQuickFilter_Input");
	protected By ORDER_ID_DROPDOWN = By.xpath("//div//li[contains(@class, 'flyout-menuItem')]");
	protected By FILTER = By.cssSelector("input[name*='Filter']");
	protected By FILTER_INPUT = By.cssSelector("input[name*='SalesStatusGrid_SalesStatus_Input']");
	protected By APPLY_FILTER = By.xpath("//span[text()='Apply']");
	protected By APPLY_DATE_FILTER = By.xpath("//span[contains(@id,'RTDate_FieldId_ApplyFilters')]");
	protected By APPLY_NUM_FILTER = By.xpath("//span[contains(@id,'RTNumeric_FieldId_ApplyFilters')]");
	protected By APPLY = By.name("LedgerJournalTrans_Voucher_ApplyFilters");
	protected By SELECT_ROW = By.xpath("//div[@class='dyn-hoverMarkingColumn']");
	protected By SITE_HEADER = By.xpath("//div[contains(@id,'InventoryDimensionsGrid_InventSiteId')]");
	protected By LAST_FLEX_FIELD = By.xpath("//input[contains(@id,'VTXFlexFields_RTString_FieldId')][not(@dyn-data-supertooltip)]");
	protected By LAST_DATE_FIELD = By.xpath("//input[contains(@id,'VTXFlexFields_RTDate_FieldId')][not(@dyn-data-supertooltip)]");
	protected By LAST_NUM_FIELD = By.xpath("//input[contains(@id,'VTXFlexFields_RTNumeric_FieldId')][not(@dyn-data-supertooltip)]");
	protected By HEADER_MAINTAIN_CHARGES = By.xpath("//button[@data-dyn-controlname='MarkupTrans_HeadingPurchTable']");
	protected By DELIVERY_ADDRESS_DROPDOWN = By.xpath("//input[contains(@name, 'Location_Description')]");
	protected By CLOSE_BUTTON = By.name("Close");
	protected By CLOSE_BUTTON_SALES_ORDER = By.name("CloseButton");
	protected By SCROLL_BAR_PATH_1 = By.xpath("(//div[contains(@class,'faceHorizontal')])[1]");
	protected By SCROLL_BAR_PATH_2 = By.xpath("(//div[contains(@class,'faceHorizontal')])[2]");
	protected By REFRESH_BUTTON = By.xpath("(//span[@class='button-commandRing Refresh-symbol'])[2]");

	protected By ADD_LINES = By.xpath("//span[text()='Add lines']");
	protected By CANCEL = By.xpath("//span[contains(@id,'cancel')]");

	protected By ELLIPSE_BUTTON = By.xpath("(//div[@data-dyn-role='OverflowButton'])[4]");
	protected By FILTER_SEARCH_BOX = By.name("QuickFilterControl_Input");
	protected By OVERWRITE_OK_BUTTON = By.cssSelector("button[name='OkButton']");
	protected String OVERWRITE_FIELD_LOCATOR = "//label[text()='Unit price'][contains(@class,'checkBox')]/following-sibling::div//span[@class='toggle-box']";
	protected By APPLY_BUTTON = By.name("LedgerJournalTable_JournalNum_ApplyFilters");
	protected By DELIVERY_DATE_CONTROL = By.xpath("//input[@name='SalesLine_DeliveryDateControl']");

	protected By MANAGE_ADDRESSES_EDIT_BUTTON = By.xpath("//span[contains(@id, 'Postal') and contains(@id, 'EditButton') and not(@style)]");
	protected By MANAGE_ADDRESSES_SAVE_BUTTON = By.xpath("//span[contains(@id, 'Postal') and contains(@id, 'SaveButton') and not(@style)]");
	protected By CUSTOMS_STATUS_INPUT = By.xpath("//input[@name='LogisticsPostalAddress_VTXCustomsStatus']");
	protected By ADDRESS_MORE_OPTIONS = By.xpath("//span[contains(text(), 'More options') and contains(@id, 'Address')]");
	protected By ADDRESS_ADVANCED_BUTTON = By.xpath("//span[contains(text(), 'Advanced') and contains(@id, 'Address')]");

	Actions action = new Actions(driver);

	public DFinanceBasePage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * expands header if not expanded.
	 *
	 * @param headerBy 'locator' to search for web element header
	 */
	public void expandHeader( By headerBy )
	{
		WebElement headerElement = wait.waitForElementDisplayed(headerBy);

		String headerAttribute = headerElement.getAttribute("aria-expanded");
		boolean isExpanded = Boolean.parseBoolean(headerAttribute);

		if ( headerElement!=null || (headerAttribute!= null && !isExpanded ))
		{
			if(!isExpanded)
				click.clickElementCarefully(headerElement);
			headerAttribute = headerElement.getAttribute("aria-expanded");
			isExpanded = Boolean.parseBoolean(headerAttribute);

			if ( headerAttribute!= null && !isExpanded )
				click.clickElementIgnoreExceptionAndRetry(headerElement);
		}
	}

	/**
	 * finds the 'Yes' button in the confirmation dialog for deleting something,
	 * confirms the deletion by clicking that button, then waits for the page to
	 * reload with that thing deleted
	 *
	 * @author ssalisbury
	 */
	//TODO fix this to click the delete button and then click the yes button in the confirmation dialog
	public void clickDeleteYesButton( )
	{
		click.clickElementCarefully(DELETE_YES_BUTTON);
	}

	/**
	 * clicks the save button? then waits for the page to reload with something having been saved
	 *
	 * @author ? ssalisbury
	 */
	public void clickSaveButton( )
	{
		click.clickElementCarefully(SAVE_BUTTON);
	}

	/**
	 * locates the exact cell from the items table
	 * @Author Vishwa
	 * @param rowIndex    the row the cell is on
	 * @param columnIndex the column the cell is on
	 *
	 * @return cell, as WebElement
	 */
	public WebElement getTableCell( int rowIndex, int columnIndex )
	{
		WebElement cell = null;

		List<WebElement> tableCons = wait.waitForAllElementsPresent(itemTableConLoc);
		WebElement tableParentCon = tableCons.get(tableCons.size() - 1);
		WebElement tableCon = wait.waitForElementPresent(By.tagName("tbody"), tableParentCon);
		List<WebElement> tableRows = wait.waitForAllElementsPresent(By.tagName("tr"), tableCon);
		findingCell:
		for ( WebElement row : tableRows )
		{
			row = tableRows.get(rowIndex);
			List<WebElement> rowColumns = wait.waitForAllElementsPresent(By.tagName("td"), row);
			for ( WebElement column : rowColumns )
			{
				column = rowColumns.get(columnIndex);
				try
				{
					cell = column.findElement(By.tagName("input"));
				}
				catch ( org.openqa.selenium.NoSuchElementException e )
				{
					cell = column;
				}
				break findingCell;
			}
		}
		return cell;
	}

	/**
	 * when activating a row, get the correct cell to click on
	 *@Author Vishwa
	 * @param rowIndex row cell is located on
	 *
	 * @return the cell on the specified row
	 */
	public WebElement getRowActivationCell( int rowIndex )
	{
		WebElement cell = null;
		int columnIndex = 0;

		WebElement tableParentCon = wait.waitForElementPresent(itemTableConLoc);
		WebElement tableCon = wait.waitForElementPresent(By.tagName("tbody"), tableParentCon);
		List<WebElement> tableRows = wait.waitForAllElementsPresent(By.tagName("tr"), tableCon);
		findingCell:
		for ( WebElement row : tableRows )
		{
			row = tableRows.get(rowIndex);
			List<WebElement> rowColumns = wait.waitForAllElementsPresent(By.tagName("td"), row);
			cell = rowColumns.get(columnIndex);
		}

		return cell;
	}

	/**
	 * locates the item number field and enters the item number in it.
	 * @Author Vishwa
	 * @param item desired item number as a string
	 * @param rowIndex   which row to write to
	 */
	public void enterItem( String item, int rowIndex )
	{
		waitForPageLoad();
		String lineXPath =String.format("(//input[contains(@aria-label,'Item')])[%s]",rowIndex);
		WebElement itemNumberField = wait.waitForElementPresent(By.xpath(lineXPath));

		try {
			click.clickElementCarefully(itemNumberField);
		}
		catch(Exception ex)
		{
			VertexLogger.log(ex.toString());
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,400)");
			if(rowIndex>1)
			{
				click.clickElementCarefully(ADD_LINES);
				click.clickElementCarefully(CANCEL);
			}
		}
		action.moveToElement(itemNumberField).click(itemNumberField).sendKeys(item).sendKeys(Keys.TAB).perform();
	}

	/**
	 * locates the Procurement Category field and enters the value in it.
	 * @Author Mario Saint-Fleur
	 * @param procurementCategory desired procurement category as a string
	 * @param rowIndex   which row to write to
	 */
	public void enterProcurementCategory( String procurementCategory, int rowIndex )
	{
		waitForPageLoad();
		String lineXPath =String.format("(//input[contains(@id,'ProcurementCategory')])[%s]",rowIndex);
		WebElement itemNumberField = wait.waitForElementPresent(By.xpath(lineXPath));
		action.moveToElement(itemNumberField).click(itemNumberField).sendKeys(procurementCategory).sendKeys(Keys.TAB).perform();
	}

	/**
	 * locates the Product Name field and enters the value in it.
	 * @Author Mario Saint-Fleur
	 * @param productName desired product name as a string
	 * @param rowIndex   which row to write to
	 */
	public void enterProductName( String productName, int rowIndex )
	{
		waitForPageLoad();
		String lineXPath =String.format("(//input[contains(@aria-label,'Product name')])[%s]",rowIndex);
		WebElement itemNumberField = wait.waitForElementPresent(By.xpath(lineXPath));
		action.moveToElement(itemNumberField).click(itemNumberField).sendKeys(productName).sendKeys(Keys.TAB).perform();
	}

	/**
	 * locates the Project Id field and enters the value in it
	 * @Author Mario Saint-Fleur
	 * @param projectId desired project id as a string
	 * @param rowIndex   which row to write to
	 */
	public void enterProjectId( String projectId, int rowIndex )
	{
		waitForPageLoad();
		String lineXPath =String.format("(//input[contains(@aria-label,'Project ID')])[%s]",rowIndex);
		WebElement itemNumberField = wait.waitForElementPresent(By.xpath(lineXPath));
		action.moveToElement(itemNumberField).click(itemNumberField).sendKeys(projectId).sendKeys(Keys.TAB).perform();
	}

	/**
	 * locates the Unit field and enters the value in it.
	 * @Author Mario Saint-Fleur
	 * @param unit desired unit as a string
	 * @param rowIndex   which row to write to
	 */
	public void enterUnit( String unit, int rowIndex )
	{
		waitForPageLoad();
		String lineXPath =String.format("(//input[contains(@aria-label,'Unit')])[%s]",rowIndex);
		WebElement itemNumberField = wait.waitForElementPresent(By.xpath(lineXPath));
		action.moveToElement(itemNumberField).click(itemNumberField).sendKeys(unit).sendKeys(Keys.TAB).perform();
	}

	/**
	 * locates the quantity field and enters the quantity.
	 * @Author Vishwa
	 * @param qty quantity of the item selected as a string
	 */
	public void enterQuantity( String qty, int rowIndex )
	{
		waitForPageLoad();
		rowIndex = rowIndex-1;
		String siteXPATH = String.format("(//input[@*='Quantity'])[%s]",rowIndex);
		WebElement siteField = wait.waitForElementPresent(By.xpath(siteXPATH));
		action.moveToElement(siteField).click(siteField).perform();
		wait.waitForElementEnabled(siteField);
		try
		{
			siteField.click();
		}
		catch ( ElementNotInteractableException e )
		{
			click.javascriptClick(siteField);
		}
		text.setTextFieldCarefully(siteField, qty, false);
		if(element.isElementDisplayed(SCROLL_BAR_PATH_1)){
			WebElement scrollBar = wait.waitForElementDisplayed(SCROLL_BAR_PATH_1);
			action.moveToElement(scrollBar).clickAndHold();
			action.moveByOffset(-250,0).release().perform();
		}

		if(element.isElementDisplayed(SCROLL_BAR_PATH_2)){
			WebElement scrollBar = wait.waitForElementDisplayed(SCROLL_BAR_PATH_2);
			action.moveToElement(scrollBar).clickAndHold();
			action.moveByOffset(-250,0).release().perform();
		}
	}

	/**
	 * locates the quantity field and enters the quantity.
	 * @Author Vishwa
	 * @param site quantity of the item selected as a string
	 */
	public void enterSite( String site, int rowIndex )
	{
		rowIndex = rowIndex-1;
		String siteXPATH = String.format("(//input[contains(@id,'InventSiteId')])[%s]",rowIndex);
		WebElement siteField = wait.waitForElementPresent(By.xpath(siteXPATH));
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
		text.setTextFieldCarefully(siteField, site, false);
	}

	/**
	 * locates the quantity field and enters the quantity.
	 * @Author Vishwa
	 * @param Warehouse quantity of the item selected as a string
	 */
	public void enterWarehouse( String Warehouse, int rowIndex )
	{
		waitForPageLoad();
		WebElement WarehouseField = wait.waitForElementDisplayed(By.xpath("(//input[contains(@aria-label, 'Warehouse')])["+(rowIndex - 1)+"]"));
		scroll.scrollElementIntoView(WarehouseField);
		wait.waitForElementEnabled(WarehouseField);
		try
		{
			WarehouseField.click();
		}
		catch ( ElementNotInteractableException e )
		{
			click.javascriptClick(WarehouseField);
		}
		text.setTextFieldCarefully(WarehouseField, Warehouse, false);
	}

	/**
	 * locates the quantity field and enters the quantity.
	 * @Author Vishwa
	 * @param UnitPrice quantity of the item selected as a string
	 * @param rowIndex the row where the value is entered
	 */
	public void enterunitPrice( String UnitPrice, int rowIndex )
	{
		waitForPageLoad();
		rowIndex = rowIndex-1;
		String siteXPATH = String.format("(//input[@*='Unit price'])[%s]",rowIndex);

		WebElement UnitPriceField = wait.waitForElementPresent(By.xpath(siteXPATH));
		scroll.scrollElementIntoView(UnitPriceField);
		wait.waitForElementEnabled(UnitPriceField);
		try
		{
			UnitPriceField.click();
		}
		catch ( ElementNotInteractableException e )
		{
			click.javascriptClick(UnitPriceField);
		}
		action.moveToElement(UnitPriceField).click(UnitPriceField).perform();
		text.setTextFieldCarefully(UnitPriceField, UnitPrice, false);
	}

	/**
	 * locates the Unit Price field and enters the unit price.
	 * @Author Mario Saint-Fleur
	 * @param UnitPrice quantity of the item selected as a string
	 * @param rowIndex the row where the value is entered
	 */
	public void enterUnitPriceFreeTextInvoice( String UnitPrice, int rowIndex )
	{
		waitForPageLoad();
		WebElement UnitPriceField = wait.waitForElementDisplayed(By.xpath("(//input[@aria-label='Unit price'])["+rowIndex+"]"));
		scroll.scrollElementIntoView(UnitPriceField);
		wait.waitForElementEnabled(UnitPriceField);
		try
		{
			UnitPriceField.click();
		}
		catch ( ElementNotInteractableException e )
		{
			click.javascriptClick(UnitPriceField);
		}
		text.setTextFieldCarefully(UnitPriceField,UnitPrice,false);
	}

	/**
	 * locates the discount field and enters the discount.
	 * @Author Mario Saint-Fleur
	 * @param discount amount of the item selected as a string
	 */
	public void enterDiscount( String discount, int rowIndex )
	{
		waitForPageLoad();
		WebElement DiscountField = wait.waitForElementDisplayed(By.xpath("(//input[@aria-label='Discount'])["+(rowIndex - 1)+"]"));
		scroll.scrollElementIntoView(DiscountField);
		wait.waitForElementEnabled(DiscountField);
		try
		{
			DiscountField.click();
		}
		catch ( ElementNotInteractableException e )
		{
			click.javascriptClick(DiscountField);
		}
		text.clearText(DiscountField);
		text.enterText(DiscountField, discount);
		text.pressTab(DiscountField);
	}

	/**
	 * locates the discount field and enters the discount.
	 * @Author Mario Saint-Fleur
	 * @param discountPercentage amount of the item selected as a string
	 */
	public void enterDiscountPercentage( String discountPercentage, int rowIndex )
	{
		waitForPageLoad();
		WebElement DiscountPercentageField = wait.waitForElementDisplayed(By.xpath("(//input[@aria-label='Discount percent'])["+(rowIndex - 1)+"]"));
		scroll.scrollElementIntoView(DiscountPercentageField);
		wait.waitForElementEnabled(DiscountPercentageField);
		try
		{
			DiscountPercentageField.click();
		}
		catch ( ElementNotInteractableException e )
		{
			click.javascriptClick(DiscountPercentageField);
		}
		text.clearText(DiscountPercentageField);
		text.enterText(DiscountPercentageField, discountPercentage);
		text.pressTab(DiscountPercentageField);
	}

	/**
	 * locates the Main Account Field and enters the value
	 * @param mainAccount
	 * @param rowIndex
	 */
	public void enterMainAccount( String mainAccount, int rowIndex )
	{
		waitForPageLoad();
		WebElement mainAccountField = wait.waitForElementDisplayed(By.xpath("//div[contains(@aria-rowindex, '"+rowIndex+"')]//input[@aria-label='Main account']"));
		scroll.scrollElementIntoView(mainAccountField);
		wait.waitForElementEnabled(mainAccountField);
		try
		{
			mainAccountField.click();
		}
		catch ( ElementNotInteractableException e )
		{
			click.javascriptClick(mainAccountField);
		}
		text.clearText(mainAccountField);
		text.enterText(mainAccountField, mainAccount);
		text.pressTab(mainAccountField);
	}

	/**
	 * locates the Item Sales Tax Group and enters the value
	 * @param itemSalesTaxGroup
	 * @param rowIndex
	 */
	public void enterItemSalesTaxGroup( String itemSalesTaxGroup, int rowIndex )
	{
		waitForPageLoad();
		WebElement itemSalesGroupTaxField = wait.waitForElementDisplayed(By.xpath("//div[contains(@aria-rowindex, '"+rowIndex+"')]//input[@aria-label='Item sales tax group']"));
		scroll.scrollElementIntoView(itemSalesGroupTaxField);
		wait.waitForElementEnabled(itemSalesGroupTaxField);
		try
		{
			itemSalesGroupTaxField.click();
		}
		catch ( ElementNotInteractableException e )
		{
			click.javascriptClick(itemSalesGroupTaxField);
		}
		text.setTextFieldCarefully(itemSalesGroupTaxField,itemSalesTaxGroup,false);
		text.pressTab(itemSalesGroupTaxField);
	}

	/**
	 * Clicks the address dropdown button
	 * @author Mario Saint-Fleur
	 */
	public void clickAddressDropDown(){
		wait.waitForElementDisplayed(driver.findElement(addressDropDown));
		click.clickElementCarefully(addressDropDown);
		wait.waitForElementDisplayed(driver.findElement(addressDropDown));
		click.clickElementCarefully(addressDropDown);
	}

	/**
	 * Clicks the Add button to add a new address
	 * @author Mario Saint-Fleur
	 */
	public void clickAddNewAddress(){
		wait.waitForElementDisplayed(driver.findElement(addAddressButton));
		click.clickElementCarefully(addAddressButton);
	}

	/**
	 * Select the Account Type
	 *
	 * @param accountType
	 */
	public void setAccountType(String accountType){
		wait.waitForElementDisplayed(ACCOUNT_TYPE);
		click.clickElementCarefully(ACCOUNT_TYPE);

		By accountTypes = By.xpath("(//li[contains(text(), '"+accountType+"')])[3]");
		wait.waitForElementDisplayed(accountTypes);
		click.clickElementCarefully(accountTypes);
	}


	/**
	 * Enter "Customer account"
	 *
	 * @param customerAccount
	 */
	public void setCustomerAccount( String customerAccount )
	{
		wait.waitForElementEnabled(TEXT_CUSTOMER_ACCOUNT);
		text.clickElementAndEnterText(TEXT_CUSTOMER_ACCOUNT, customerAccount);
		text.pressTab(TEXT_CUSTOMER_ACCOUNT);
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
			click.clickElementIgnoreExceptionAndRetry(TAB);
		}
	}

	/**
	 * Click on "Edit" option
	 */
	public void clickOnEditButton( )
	{
		wait.waitForElementDisplayed(EDIT_BUTTON);
		click.clickElementCarefully(EDIT_BUTTON);
	}
	/**
	 * Click the Sales quotation line dropdown menu
	 */
	public void clickSalesQuotationLine(){
		wait.waitForElementDisplayed(SALES_QUOTATION_LINE);
		click.javascriptClick(SALES_QUOTATION_LINE);
	}

	/**
	 * Click the Maintain Charges link in the line dropdown menu
	 */
	public void clickMaintainCharges(){
		/** TODO Fix clicking of maintain charges for Sales Quotation test**/
		waitForPageLoad();
		if(element.isElementPresent(MAINTAIN_CHARGES)) {
			wait.waitForElementDisplayed(MAINTAIN_CHARGES);
			click.clickElementIgnoreExceptionAndRetry(MAINTAIN_CHARGES);
		}else  {
			wait.waitForElementDisplayed(MAINTAIN_CHARGES_SALES_ORDER);
			click.clickElementIgnoreExceptionAndRetry(MAINTAIN_CHARGES_SALES_ORDER);
		}
	}

	/**
	 * Checks to see if the message bar has been displayed
	 * @param messageBar
	 * @return result
	 */
	public boolean messageBarConfirmation(String messageBar)
	{
		waitForPageLoad();
		WebElement messageBarConfirmation = wait.waitForElementDisplayed(By.xpath(".//span[contains(@title,'"+messageBar+"')]"));
		boolean result = element.isElementDisplayed(messageBarConfirmation);
		if(result)
		{
			wait.waitForElementDisplayed(MESSAGE_BAR_CLOSE);
			click.clickElementIgnoreExceptionAndRetry(MESSAGE_BAR_CLOSE);
		}
		return result;
	}

	/**
	 * Filters Order by ID
	 * @param filterType
	 * @param orderID
	 */
	public void getOrderByID(String filterType, String orderID)
	{
		String filterXPath = String.format("//div[contains(text(),'%s')]",filterType);
		WebElement ele = wait.waitForElementPresent(By.xpath(filterXPath));
		action.moveToElement(ele).click(ele).perform();
		WebElement filterEle = wait.waitForElementEnabled(FILTER);
		text.enterText(filterEle,orderID);
		click.clickElementCarefully(APPLY);
		wait.waitForElementDisplayed(SELECT_ROW);
		click.clickElementCarefully(SELECT_ROW);
	}

	/**
	 * Clicks the recalculate tax button
	 * @param recalculateType
	 * @param clickElement
	 * @param elementLocation
	 */
	public void clickRecalculateTax(String recalculateType, String elementLocation, boolean clickElement){
		waitForPageLoad();
		WebElement recalculateOrderType;
		try {
			recalculateOrderType = wait.waitForElementDisplayed(By.xpath("(//span[text()='" + recalculateType + "'])[" + elementLocation + "]"));
		}
		catch(Exception ex)
		{
			recalculateOrderType = wait.waitForElementDisplayed(By.xpath("(//span[text()='" + recalculateType + "'])"));
		}
		if(element.isElementEnabled(recalculateOrderType) && clickElement == true ){
			click.javascriptClick(recalculateOrderType);
		}
		waitForPageLoad();
	}

	/**
	 * Clicks the maintain charges at the header level
	 */
	public void clickHeaderMaintainCharges(){
		wait.waitForElementDisplayed(HEADER_MAINTAIN_CHARGES);
		click.clickElementCarefully(HEADER_MAINTAIN_CHARGES);
	}

	/**
	 * Clicks the desired Line Details tab
	 * @param clickTab
	 */
	public void clickLineDetailsTab(String clickTab){
		wait.waitForElementDisplayed(By.xpath("//li[contains(@data-dyn-controlname, '"+clickTab+"_header')]"));
		click.javascriptClick(By.xpath("//li[contains(@data-dyn-controlname, '"+clickTab+"_header')]"));
	}

	/**
	 * Adds the business unit
	 * @param businessUnitLocation
	 * @param businessUnitType
	 */
	public void addBusinessUnit(String businessUnitLocation, String businessUnitType){
		WebElement businessUnitElement = wait.waitForElementDisplayed(By.name(businessUnitLocation));
		text.setTextFieldCarefully(businessUnitElement, businessUnitType, false);
	}

	/**
	 * Adds the cost center
	 * @param costCenterLocation
	 * @param costCenterType
	 */
	public void addCostCenter(String costCenterLocation, String costCenterType){
		WebElement costCenterElement = wait.waitForElementDisplayed(By.name(costCenterLocation));
		text.setTextFieldCarefully(costCenterElement, costCenterType, false);
	}

	/**
	 * Adds the department
	 * @param departmentLocation
	 * @param departmentType
	 */
	public void addDepartment(String departmentLocation, String departmentType){
		WebElement departmentElement = wait.waitForElementDisplayed(By.name(departmentLocation));
		text.setTextFieldCarefully(departmentElement, departmentType, false);
	}

	/**
	 * Adds the itemGroup
	 * @param itemGroupLocation
	 * @param itemGroupType
	 */
	public void addItemGroup(String itemGroupLocation, String itemGroupType){
		WebElement itemGroupElement = wait.waitForElementDisplayed(By.name(itemGroupLocation));
		text.setTextFieldCarefully(itemGroupElement, itemGroupType, false);
	}

	/**
	 * Enters the appropriate value into the Delivery Date Control Field
	 * @param deliveryControlType
	 */
	public void selectDeliveryControlDate(String deliveryControlType){
		wait.waitForElementDisplayed(DELIVERY_DATE_CONTROL);
		text.clearText(DELIVERY_DATE_CONTROL);
		text.setTextFieldCarefully(DELIVERY_DATE_CONTROL, deliveryControlType, false);
	}

	/**
	 * Clicks and adds a delivery address
	 * @param deliveryAddress
	 */
	public void updateDeliveryAddress(String deliveryAddress){
		WebElement deliveryDropdown = wait.waitForElementDisplayed(DELIVERY_ADDRESS_DROPDOWN);
		click.clickElement(deliveryDropdown);
		text.clearText(deliveryDropdown);
		text.enterText(deliveryDropdown, deliveryAddress);
		deliveryDropdown.sendKeys(Keys.TAB);
		waitForPageLoad();
	}

	/**
	 * Clicks the close button
	 */
	public void clickCloseBtn(){
		if(element.isElementDisplayed(CLOSE_BUTTON)){
			wait.waitForElementDisplayed(CLOSE_BUTTON);
			click.clickElementCarefully(CLOSE_BUTTON);
		}else if(element.isElementDisplayed(CLOSE_BUTTON_SALES_ORDER)){
			wait.waitForElementDisplayed(CLOSE_BUTTON_SALES_ORDER);
			click.clickElementCarefully(CLOSE_BUTTON_SALES_ORDER);
		}
	}

	/**
	 * Clicks the Refresh Button
	 * @param elementLocation
	 */
	public void clickRefreshButton(String elementLocation){
		jsWaiter.sleep(60000);
		WebElement refreshButton = wait.waitForElementDisplayed(By.xpath("(//span[@class='button-commandRing Refresh-symbol'])["+elementLocation+"]"));
		click.clickElementCarefully(refreshButton);
		waitForPageLoad();
	}

	/**
	 * Clicks the select post button
	 * @param postButtonLocation
	 */
	public void clickPostButton(String postButtonLocation){
		By postButtonLoc =By.xpath("(//span[text()='Post'])["+postButtonLocation+"]");
		WebElement postButton = wait.waitForElementDisplayed(By.xpath("(//span[text()='Post'])["+postButtonLocation+"]"));
		click.performDoubleClick(postButton);
	}

	/**
	 * Get the document number
	 * @param buttonLocation
	 *
	 * @return
	 */
	public String getDocumentNumber(String buttonLocation)
	{
		wait.waitForElementDisplayed(By.xpath("(//span[contains(@id, 'HeaderTitle')])["+buttonLocation+"]"));
		WebElement purchaseRequisitionNumber = wait.waitForElementDisplayed(By.xpath("(//span[contains(@id, 'HeaderTitle')])["+buttonLocation+"]"));
		String purchaseRequisitionNum = purchaseRequisitionNumber.getText();
		String [] purchaseOrderNumberArray = purchaseRequisitionNum.split(" ");

		return purchaseOrderNumberArray[0];
	}

	/**
	 * Set sales order tax group
	 *
	 * @param 'salesTaxGroup' to type in sales group element
	 * @param salesTaxType
	 */
	public void setSalesOrderTaxGroup( String salesTaxGroup, String salesTaxType)
	{
		System.out.println(By.xpath("//input[@name='"+salesTaxType+"_TaxGroup']"));
		WebElement salesTaxGroupEle = wait.waitForElementDisplayed(By.xpath("//input[@name='"+salesTaxType+"_TaxGroup']"));
		text.enterText(salesTaxGroupEle, salesTaxGroup + Keys.TAB);
		waitForPageLoad();
	}

	/**
	 * Clicks the ellipse button
	 */
	public void clickOnEllipse(){
		if(element.isElementDisplayed(ELLIPSE_BUTTON)) {
			WebElement ellipseElement = wait.waitForElementDisplayed(ELLIPSE_BUTTON);
			click.performDoubleClick(ellipseElement);
		}
	}

	/**
	 * Enter created order and order type
	 *
	 * @param orderType
	 * @param orderNum
	 */
	public void searchCreatedOrder( String orderType, String orderNum )
	{
		WebElement searchBox = wait.waitForElementEnabled(FILTER_SEARCH_BOX);
		text.enterText(searchBox, orderNum);
		WebElement orderTypeSelected = wait.waitForElementDisplayed(By.xpath("//span[@class='quickFilter-listFieldName' and text()='"+orderType+"']"));
		click.clickElementCarefully(orderTypeSelected);

		waitForPageLoad();
	}

	/**
	 * Click on displayed order number
	 * @param orderNumberVal
	 */
	public void clickOnDisplayedOrderNumber(String orderNumberVal)
	{
		WebElement orderNumber = wait.waitForElementDisplayed(By.name("QuickFilterControl_Input"));
		try
		{
			Thread.sleep(2000);
		}
		catch ( InterruptedException e )
		{
			e.printStackTrace();
		}
		click.clickElementCarefully(orderNumber);
		wait.waitForAllElementsDisplayed(By.xpath("//input[contains(@value, '"+orderNumberVal+"')]"));
		WebElement element2 = element.getWebElement(By.xpath("//input[contains(@value, '"+orderNumberVal+"')]"));
		String keysPressed = Keys.chord(Keys.CONTROL, Keys.RETURN);
		element2.sendKeys(keysPressed);
		waitForPageLoad();
	}

	/**
	 * Clicks on the Ok for overwriting button
	 */
	public void clickOverwriteOkButton( )
	{
		wait.waitForElementDisplayed(OVERWRITE_OK_BUTTON);
		click.clickElement(OVERWRITE_OK_BUTTON);
		waitForPageLoad();
	}

	/**
	 * Overwrites the intended fields value
	 * @param fieldName
	 * @param flag
	 */
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

	/**
	 * Clicks the apply button
	 */
	public void clickApplyButton(){
		wait.waitForElementDisplayed(APPLY_BUTTON);
		click.clickElementCarefully(APPLY_BUTTON);
	}

	/**
	 * Selects the Customs Status for an address
	 * @param status
	 */
	public void selectCustomsStatus(String status){
		wait.waitForElementDisplayed(MANAGE_ADDRESSES_EDIT_BUTTON);
		click.clickElementIgnoreExceptionAndRetry(MANAGE_ADDRESSES_EDIT_BUTTON);

		waitForPageLoad();

		wait.waitForElementDisplayed(CUSTOMS_STATUS_INPUT);
		click.clickElementCarefully(CUSTOMS_STATUS_INPUT);

		WebElement statusOption = wait.waitForElementDisplayed(By.xpath("//li[text()='"+status+"']"));
		click.clickElementCarefully(statusOption);

		wait.waitForElementDisplayed(MANAGE_ADDRESSES_SAVE_BUTTON);
		click.clickElementIgnoreExceptionAndRetry(MANAGE_ADDRESSES_SAVE_BUTTON);
	}

	/**
	 * Clicks 'More options' on Address tab
	 */
	public void clickAddressMoreOptions() {
		wait.waitForElementDisplayed(ADDRESS_MORE_OPTIONS);
		click.clickElementIgnoreExceptionAndRetry(ADDRESS_MORE_OPTIONS);
		waitForPageLoad();
	}

	/**
	 * Clicks 'Advanced' on Address tab
	 */
	public void clickAddressAdvancedButton() {
		WebElement advancedButton = wait.waitForElementDisplayed(ADDRESS_ADVANCED_BUTTON);
		click.clickElementIgnoreExceptionAndRetry(ADDRESS_ADVANCED_BUTTON);
		waitForPageLoad();

		if(element.isElementDisplayed(ADDRESS_ADVANCED_BUTTON)) {
			action.click(advancedButton).perform();
		}
	}
}
