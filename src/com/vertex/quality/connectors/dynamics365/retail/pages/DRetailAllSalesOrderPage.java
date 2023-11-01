package com.vertex.quality.connectors.dynamics365.retail.pages;


import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinanceBasePage;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Sales order page common methods and object declaration page
 *
 * @author Sgupta,dpatel
 */

public class DRetailAllSalesOrderPage extends DFinanceBasePage
{
	protected By ADD_BUTTON = By.id("addProductButton");
	protected By ADD_CUSTOMER = By.id("addCustomerButton");
	protected By ADD_CUSTOMER_BUTTON = By.id("tab-button-CustomerPanel");
	protected By ADD_CUSTOMER_TO_SALE = By.id("CustomerSearch_addSelectedCustomerToCartCommand");
	protected By FASHION_SUNGLASSES = By.xpath("//img[@aria-label='Fashion Sunglasses']");
	protected By PINK_SUNGLASSES = By.xpath("//div[@title='Pink Thick Rimmed Sunglasses']");
	protected By ADD_ITEM_BUTTON = By.xpath("//button[@class='primaryButton']");
	protected By TRANSANCTION_BUTTON = By.xpath("//button[@aria-label='Transaction']");
	protected By TRANSACTION_OPTIONS_BUTTON = By.xpath("//button[contains(@alt, 'Transaction options')]");
	protected By BACK_ARROW_FOR_TRANSACTION_OPTIONS = By.xpath("(//div[contains(@class, 'buttonsContainer')]//button)[16]");
	protected By OVERRIDE_LINE_PRODUCT_TAX_LIST = By.xpath("//button[contains(@title, 'Override line product tax from list')]");
	protected By EXEMPT_ITEM = By.xpath("//div[text()='ExemptItem']");
	protected By GIFT_CARD = By.xpath("//button[contains(*,'Gift card')]");
	protected By ISSUE_GIFT_CARD = By.xpath("//button[@title='Issue gift card']");
	protected By GIFT_CARD_NO = By.xpath("//input[@aria-label='Card number']");
	protected By ENTER_BUTTON = By.xpath("//div[@class='flexGrow50 flexRow row-enter']//button[@value='enter']");
	protected By ENTER_BUTTON_SET_QUANTITY = By.xpath("//div[@class='flexGrow75 flexRow row-enter']//button[@value='enter']");
	protected By ENTER_AMOUNT = By.xpath("//input[@aria-label='Amount']");
	protected By LINE_COMMENT_BUTTON = By.xpath("//button[@title='Line comment']");
	protected By SET_QUANTITY = By.xpath("//button[@title='Set quantity']");
	protected By ENTER_QUANTITY = By.xpath("//input[@aria-label='Quantity']");
	protected By TAX_AMOUNT = By.xpath("//div[@id='TaxField']//div[@class='h4']");
	protected By SUBTOTAL_AMOUNT = By.xpath("//div[@id='SubtotalField']//div[@class='h4']");
	protected By CHARGES_AMOUNT = By.xpath("//div[@id='ChargesField']//div[@class='h4']");
	protected By PRODUCT_PAGE_BUTTON = By.xpath("//button[@aria-label='Products']");
	protected By VOIDS = By.xpath("//button[*='Voids']");
	protected By VOID_LINE = By.xpath("//button[*='Void line']");
	protected By VOID_TRANSACTION = By.xpath("//button[*='Void transaction']");
	protected By BACK_BUTTON = By.xpath("//div[@id='ButtonGrid1']//button[@aria-label=' ']");
	protected By RETURN_PRODUCT_BUTTON = By.xpath("//button[@title='Return product']");
	protected By RETURN_PRODUCT_LINE = By.xpath("//div[contains(@aria-label,'PRODUCT')]/../../../..");
	protected By OK_BUTTON = By.xpath("//button[text()='OK']");
	protected By ENABLE_ORDER_DATE_START = By.xpath("(//div[@class='win-toggleswitch-track'])[1]");
	protected By APPLY_BUTTON = By.xpath("//button[text()='Apply']");
	protected By WATCH = By.xpath("//*[text()='Watches']");
	protected By SILVER_WATCH = By.xpath("//div[@title='Bronze Pocketwatch']");
	protected By MENS_SHOES = By.xpath("//img[@aria-label='Mens Shoes']");
	protected By SUEDO_SHOES = By.xpath("//div[@title='Suede Sneaker']");
	protected By SEARCH_BOX = By.xpath("//input[@type='search']");
	protected By SEARCH_CUSTOMER = By.xpath("//button[contains(text(), 'Customers')]");
	protected By SELECT_CUSTOMER = By.xpath("//div[@class='column5']/div[@aria-label='CUSTOMER ID 004021']");
	protected By ORDER_LIST = By.xpath("//div[@aria-label='Collection of orders']//div[@class='win-item']");
	protected By PICK_UP_ITEM_LIST = By.xpath("//div[@aria-label='Line items']//div[@class='win-item']");
	protected By RETURN_BUTTON = By.id("ShowJournalView_returnTransactionCommand");
	protected By RETURN_ORDER_BUTTON = By.xpath("//button[contains(@id,'returnOrder')]");
	protected By SELECT_RETURN_ORDER_ITEMS = By.xpath(".//div[contains(@class, 'win-surface win-selectionmode win-listlayout win-nocssgrid')]/.//div[@class='itemContainer']//div[@class='dataListLine']//div[@role='row']/div[starts-with(@aria-label, 'NUMBER')]");
	protected By SELECTED_RETURN_ORDER_ITEMS = By.xpath(".//div[contains(@class, 'win-surface win-selectionmode win-listlayout win-nocssgrid')]/.//div[contains(@role, 'checkbox')]");
	protected By SELECT_RETURN_ORDER_REASON_OPTION = By.xpath("//*[contains(@title,'reason')]");
	protected By SELECT_RETURN_ORDER_REASON = By.xpath("//option[text()='Obsolete']");
	protected By CHECKBOX = By.xpath("//div[@aria-label='List of sales lines for an order']//div[@class='listViewLine expandable']");
	protected By RETURN_BUTTON_AGAIN = By.id("returnButton");
	protected By RETURN = By.id("return");
	protected By MENS_PANTS = By.xpath("//img[@aria-label='Pants']");
	protected By SELECT_PANTS = By.xpath("(//img[@alt='Straight Leg Pants'])[2]");
	protected By SELECT_SIZE = By.xpath("//select[@aria-label='Select a size']");
	protected By SELECT_STYLE = By.xpath("//select[@aria-label='Select a style']");
	protected By SELECT_ORDERS = By.xpath("//button[contains(@data-ax-bubble,'tab_orders')]");
	protected By SELECT_DISCOUNTS = By.xpath("//button[contains(@data-ax-bubble,'tab_discounts')]");
	protected By SELECT_PRODUCTS = By.xpath("//button[contains(@data-ax-bubble,'tab_products')]");
	protected By BROWN_AVIATOR_BUTTON = By.xpath("//button[@alt='Brown Aviator']");
	protected By PINK_THICK_RIMMED_BUTTON = By.xpath("//button[@alt='Pink Thick Rimmed']");
	protected By BROWN_LEOPARDPRINT_BUTTON = By.xpath("//button[@alt='Brown Leopardprint']");
	protected By BLACK_THICK_RIMMED_BUTTON = By.xpath("//button[@alt='Black Thick Rimmed']");
	protected By BLACK_BOLD_FRAMED_BUTTON = By.xpath("//button[@alt='Black Bold Framed']");
	protected By RECALL_ORDER = By.xpath("//button[@title='Recall order']");
	protected By SEARCH_ORDERS_BUTTON = By.xpath("//button[text()='Search orders']");
	protected By ADD_FILTER_BUTTON = By.xpath("//button[@aria-label='Add filter']");
	protected By FILTER_VALUE_INPUT = By.id("textInputDialogContent");
	protected By CUSTOMER_NAME_INPUT = By.xpath("//input[@aria-label='Add filter Customer name ']");
	protected By KIND_OF_ORDER_CUSTOMER = By.xpath("//*[@id=\"dialogContainer\"]/.//form/.//div[text()='Customer order']");
	protected By ORDER_HAS_CHANGED = By.xpath("//*[@id=\"dialogContainer\"]//h3[text()='Cart has changed']/../../..//div/button[text()='OK']");
	protected By BUTTON_SHIP_ALL = By.xpath("//button[@title='Ship all']");
	protected By BUTTON_SHIP_SELECTED = By.xpath("//button[@title='Ship selected']");
	protected By PICKUP_SELECTED_BUTTON = By.xpath("//button[@title='Pick up selected']");
	protected By HOUSTON_STORE_PICKUP = By.xpath("//a[text()='Houston']");
	protected By CARRY_OUT_SELECTED = By.xpath("//button[@title='Carry out selected']");
	protected By CUSTOMER_CARRY_OUT = By.xpath("//div[contains(@class, 'SelectedDeliveryField')]//div[text()='Customer carry out']");
	protected By CUSTOMER_ORDER = By.xpath("//div[contains(text(), 'Customer order')]");
	protected By CREATE_CUSTOMER_ORDER = By.xpath("//div[contains(text(), 'Create customer order')]");
	protected By PICK_UP_ALL = By.xpath("//div[contains(text(), 'Pick up all')]");
	protected By PICK_UP_ALL_HOUSTON = By.xpath("//a[contains(text(), 'Houston')]");
	protected By PICK_UP = By.xpath("//button[@data-ax-bubble='pickUpView_pickUpButton']");
	protected By PICKING_AND_PACKING = By.xpath("//button[@aria-label='Picking and packing']");
	protected By STANDARD_SHIP = By.xpath("//div[text()= 'Standard']");
	protected By STANDARD_OVERNIGHT_SHIP = By.xpath("//div[contains(text(), 'Standard overnight')]");
	protected By SELECT_DAY = By.xpath("//select[@aria-label='Select Day']");
	protected By SHIP_OK_BUTTON = By.xpath("//button[@data-ax-bubble='messageDialog_okButtonClick']");
	protected By VALIDATE_SHIP = By.xpath("//div[contains(@class,'tillLayout-SelectedDeliveryFields')]//div[@class='tillLayout-DeliveryMethodField']//div[@class='h5']");
	protected By DELIVERY_BUTTON = By.xpath("//button[@data-ax-bubble='grid_delivery']");
	protected By PAY_CASH_BUTTON = By.xpath("//button[@alt='Pay cash' or @data-action='200']");
	protected By CASH_ACCEPTED = By.xpath("//div[@data-ax-bubble='paymentView_totalAmountNumpad']//button[@value='enter']");
	protected By PAY_BALANCE_LATER = By.xpath("//div[contains(text(), 'Pay the balance later')]");
	protected By CLOSE_BUTTON = By.xpath("//button[contains(text(), 'Close')]");
	protected By CHANGE_UNIT_OF_MEASURE = By.xpath("//div[text()='Change unit of measure']");
	protected By EACH = By.xpath("(//div[text()='Each'])[1]");
	protected By PIECES_UNIT_OPTION = By.xpath("//div[text()='Pieces']");
	protected By ACTION = By.xpath("//div[contains(@class, 'text') and text()='Actions']");
	protected By ITEMS = By.xpath("//div[contains(@class,'ProductNameField')]");
	protected By DATE_DAY = By.xpath("//select[@aria-label='Week days drop down']");
	protected By DATE_MONTH = By.xpath("//select[@aria-label='Months drop down']");
	protected By YES_BUTTON = By.xpath("//button[text()='Yes']");
	protected By DEPOSIT_OVERRIDE = By.xpath("//button[@title='Deposit override']");
	protected By MAXIMUM_OVERRIDE_AMOUNT = By.xpath("//a[@data-ax-bubble='depositOverrideView_setMaximumOverrideAmountLink']");
	protected By DEPOSIT_OVERRIDE_ACCEPTED = By.xpath("//div[@data-ax-bubble='depositOverrideView_depositAmountNumpad']//button[@value='enter']");
	protected By DEPOSIT_DUE = By.xpath("//div[@data-ax-bubble='cartView_depositeDueField']//div[@class='h4']");
	protected By EDIT_ORDER_BUTTON = By.xpath("//button[@id='editOrder']");

	protected Actions actions = new Actions(driver);

	public DRetailAllSalesOrderPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * add product to the order
	 */

	public void addProduct( )
	{
		wait.waitForElementDisplayed(ADD_BUTTON);
		click.clickElementIgnoreExceptionAndRetry(ADD_BUTTON);
	}

	/**
	 * add a customer to the order
	 * @param customerAccount
	 */

	public void addCustomer( String customerAccount)
	{
		if(element.isElementDisplayed(ADD_CUSTOMER_BUTTON))
			click.clickElement(ADD_CUSTOMER_BUTTON);
		wait.waitForElementDisplayed(ADD_CUSTOMER);
		click.clickElementCarefully(ADD_CUSTOMER);
		wait.waitForElementDisplayed(SEARCH_BOX);
		click.clickElementCarefully(SEARCH_BOX);
		waitForPageLoad();
		text.enterText(SEARCH_BOX, customerAccount);
		text.pressEnter(SEARCH_BOX);
		wait.waitForElementDisplayed(SEARCH_CUSTOMER);
		click.clickElementIgnoreExceptionAndRetry(SEARCH_CUSTOMER);
		text.pressEnter(SEARCH_BOX);
		waitForPageLoad();
		if(!element.isElementDisplayed(ADD_CUSTOMER_TO_SALE))
		{
			wait.waitForElementDisplayed(SEARCH_BOX);
			click.clickElementCarefully(SEARCH_BOX);
			text.enterText(SEARCH_BOX, customerAccount);
			text.pressEnter(SEARCH_BOX);
			wait.waitForElementDisplayed(SEARCH_CUSTOMER);
			click.clickElementCarefully(SEARCH_CUSTOMER);

		}
		wait.waitForElementDisplayed(ADD_CUSTOMER_TO_SALE);
		click.clickElementCarefully(ADD_CUSTOMER_TO_SALE);
		waitForPageLoad();
	}

	/**
	 * Clicks the Transaction Options Button
	 */
	public void clickTransactionOptions(){
		wait.waitForElementDisplayed(TRANSACTION_OPTIONS_BUTTON);
		click.clickElementCarefully(TRANSACTION_OPTIONS_BUTTON);
	}

	/**
	 * Clicks the Back arrow for the transaction options
	 */
	public void clickBackArrowTransactionOptions(){
		wait.waitForElementDisplayed(BACK_ARROW_FOR_TRANSACTION_OPTIONS);
		click.clickElementCarefully(BACK_ARROW_FOR_TRANSACTION_OPTIONS);
	}

	/**
	 * Clicks the Override line product tax from list button in Transaction options
	 */
	public void clickOverrideLineProductTaxList(){
		wait.waitForElementDisplayed(OVERRIDE_LINE_PRODUCT_TAX_LIST);
		click.clickElementCarefully(OVERRIDE_LINE_PRODUCT_TAX_LIST);
		wait.waitForElementDisplayed(EXEMPT_ITEM);
		click.clickElementCarefully(EXEMPT_ITEM);
	}

	/**
	 * display all the items and accessories page
	 */

	public void productPage( )
	{
		wait.waitForElementDisplayed(TRANSANCTION_BUTTON, 60);
		wait.waitForElementEnabled(PRODUCT_PAGE_BUTTON);
		click.javascriptClick(PRODUCT_PAGE_BUTTON);
		waitForPageLoad();
	}

	/**
	 * select sun glass to the cart
	 */

	public void addSunglass( )
	{
		wait.waitForElementDisplayed(FASHION_SUNGLASSES);
		click.clickElementCarefully(FASHION_SUNGLASSES);
		waitForPageLoad();
	}

	/**
	 * select pink sun glass
	 */

	public void selectPinkSunglass( )
	{
		wait.waitForElementDisplayed(PINK_SUNGLASSES);
		wait.waitForElementEnabled(PINK_SUNGLASSES);
		click.clickElementCarefully(PINK_SUNGLASSES);
		waitForPageLoad();
	}

	/**
	 * click on add item button
	 */

	public void addItemButton( )
	{
		wait.waitForElementDisplayed(ADD_ITEM_BUTTON);
		click.clickElementCarefully(ADD_ITEM_BUTTON);
		jsWaiter.sleep(5000);
	}

	/**
	 * click on transaction button
	 */

	public void transactionButton( )
	{
		wait.waitForElementEnabled(TRANSANCTION_BUTTON, 60);
		waitForPageLoad();
		click.javascriptClick(TRANSANCTION_BUTTON);
		waitForPageLoad();
	}

	/**
	 * add gift card to the cart
	 */

	public void addGiftCard( )
	{
		wait.waitForElementDisplayed(GIFT_CARD);
		click.javascriptClick(GIFT_CARD);
		wait.waitForElementDisplayed(ISSUE_GIFT_CARD);
		click.javascriptClick(ISSUE_GIFT_CARD);
		wait.waitForElementDisplayed(GIFT_CARD_NO);
		click.javascriptClick(GIFT_CARD_NO);
		Random rand = new Random();
		int cardNo = rand.nextInt(900)+100;
		WebElement inputGC = driver.findElement(GIFT_CARD_NO);
		inputGC.sendKeys(String.valueOf(cardNo));
		click.clickElementCarefully(ENTER_BUTTON);
		wait.waitForElementDisplayed(ENTER_AMOUNT);
		click.javascriptClick(ENTER_AMOUNT);
		WebElement enterAmount = driver.findElement(ENTER_AMOUNT);
		enterAmount.sendKeys("25");
		click.javascriptClick(ENTER_BUTTON);
		waitForPageLoad();

	}

	/**
	 * validate tax amount
	 * @return - returns the value of the tax amount
	 */
	public String taxValidation( )
	{
		jsWaiter.sleep(5000);

		String taxVALUE = "";
			wait.waitForElementDisplayed(TAX_AMOUNT);
			String taxAmount = driver.findElement(TAX_AMOUNT).getText();
			taxVALUE = StringUtils.substringAfter(taxAmount, "$");
			if ( taxVALUE != null )
			{
				taxVALUE = taxVALUE.trim();
				if(taxVALUE.contains(")")){
					taxVALUE = StringUtils.substringBetween(taxAmount, "$", ")");
				}
			}

		return taxVALUE;
	}

	/**
	 * returns the subtotal amount of customer order
	 * @return - returns the value of the subtotal amount
	 */
	public String getSubtotal( )
	{
		jsWaiter.sleep(5000);

		String taxVALUE = "";
		wait.waitForElementDisplayed(SUBTOTAL_AMOUNT);
		String taxAmount = driver.findElement(SUBTOTAL_AMOUNT).getText();
		taxVALUE = StringUtils.substringAfter(taxAmount, "$");
		if ( taxVALUE != null )
		{
			taxVALUE = taxVALUE.trim();
			if(taxVALUE.contains(")")){
				taxVALUE = StringUtils.substringBetween(taxAmount, "$", ")");
			}
		}

		return taxVALUE;
	}

	/**
	 * returns the charges amount of order
	 * @return - returns the value of the charges amount
	 */
	public String getCharges( )
	{
		jsWaiter.sleep(5000);

		String taxVALUE = "";
		wait.waitForElementDisplayed(CHARGES_AMOUNT);
		String taxAmount = driver.findElement(CHARGES_AMOUNT).getText();
		taxVALUE = StringUtils.substringAfter(taxAmount, "$");
		if ( taxVALUE != null )
		{
			taxVALUE = taxVALUE.trim();
			if(taxVALUE.contains(")")){
				taxVALUE = StringUtils.substringBetween(taxAmount, "$", ")");
			}
		}

		return taxVALUE;
	}

	/**
	 * validate amount due
	 * @param dueType
	 * @return - returns the value of the amount due
	 */
	public String amountDueValidation(String dueType)
	{
		jsWaiter.sleep(5000);

		String amountDueValue = "";
		wait.waitForElementDisplayed(By.xpath("//a[contains(@aria-label, '"+dueType+"')]"));
		String amountDue = driver.findElement(By.xpath("//a[contains(@aria-label, '"+dueType+"')]")).getText();
		amountDueValue = StringUtils.substringAfter(amountDue, "$");
		if ( amountDueValue != null )
		{
			amountDueValue = amountDueValue.trim();
		}

		return amountDueValue;
	}

	/**
	 * Voids the line that is currently selected
	 */
	public void voidLine(String productName) {
		wait.waitForElementDisplayed(VOIDS);
		click.clickElementIgnoreExceptionAndRetry(VOIDS);

		wait.waitForElementDisplayed(VOID_LINE);
		clickProductLine(productName);
		click.clickElementIgnoreExceptionAndRetry(VOID_LINE);
		jsWaiter.sleep(1000);

		wait.waitForElementDisplayed(BACK_BUTTON);
		click.clickElementIgnoreExceptionAndRetry(BACK_BUTTON);
		waitForPageLoad();
	}

	/**
	 * void the current transaction
	 */
	public void voidTransaction( )
	{
		wait.waitForElementDisplayed(BACK_BUTTON);
		click.javascriptClick(BACK_BUTTON);
		wait.waitForElementDisplayed(VOIDS);
		click.javascriptClick(VOIDS);
		wait.waitForElementDisplayed(VOID_TRANSACTION);
		click.javascriptClick(VOID_TRANSACTION);
		waitForPageLoad();
	}

	/**
	 * void the current transaction from transaction page
	 */

	public void deleteTransaction( )
	{
		wait.waitForElementDisplayed(VOIDS);
		click.clickElementCarefully(VOIDS);
		wait.waitForElementDisplayed(VOID_TRANSACTION);
		click.clickElementCarefully(VOID_TRANSACTION);
		waitForPageLoad();
	}

	/**
	 * return the item
	 */

	public void returnProduct ()
	{
		wait.waitForElementDisplayed(RETURN_PRODUCT_BUTTON);
		click.clickElementCarefully(RETURN_PRODUCT_BUTTON);
		wait.waitForElementDisplayed(OK_BUTTON);
		click.clickElementCarefully(OK_BUTTON);

	}

	/**
	 * add watch
	 */

	public void addWatch ()
	{
		wait.waitForElementDisplayed(WATCH);
		click.clickElementCarefully(WATCH);
		wait.waitForElementDisplayed(SILVER_WATCH);
		click.clickElement(SILVER_WATCH);
		waitForPageLoad();
	}

	/**
	 * add men's shoes to the art
	 */

	public void addMensShoes ()
	{
		wait.waitForElementDisplayed(MENS_SHOES);
		click.clickElementCarefully(MENS_SHOES);
		wait.waitForElementDisplayed(SUEDO_SHOES);
		click.clickElementCarefully(SUEDO_SHOES);
		wait.waitForElementDisplayed(SELECT_SIZE);
		click.clickElementCarefully(SELECT_SIZE);
		dropdown.selectDropdownByValue(SELECT_SIZE, "22565420932");
		waitForPageLoad();
	}

	/**
	 * add men's pants to the art
	 */

	public void addMensPants ()
	{
		wait.waitForElementDisplayed(MENS_PANTS);
		click.clickElementCarefully(MENS_PANTS);
		wait.waitForElementDisplayed(SELECT_PANTS);
		click.clickElementCarefully(SELECT_PANTS);
		wait.waitForElementDisplayed(SELECT_SIZE);
		click.clickElementCarefully(SELECT_SIZE);
		dropdown.selectDropdownByValue(SELECT_SIZE, "22565421223");
		wait.waitForElementDisplayed(SELECT_STYLE);
		click.clickElementCarefully(SELECT_STYLE);
		dropdown.selectDropdownByValue(SELECT_STYLE, "5637144583");
		waitForPageLoad();
	}

	/**
	 *  select exchange item and add to the cart
	 */

	public void exchangeItem ()
	{
		wait.waitForElementDisplayed(SEARCH_BOX);
		click.clickElementCarefully(SEARCH_BOX);
		text.enterText(SEARCH_BOX, "004021");
		text.pressEnter(SEARCH_BOX);
		wait.waitForElementDisplayed(SEARCH_CUSTOMER);
		click.clickElementCarefully(SEARCH_CUSTOMER);
		wait.waitForElementDisplayed(SELECT_CUSTOMER);
		click.clickElementCarefully(SELECT_CUSTOMER);
		WebDriverWait listWait = new WebDriverWait(driver,10);
		List<WebElement> ORDERS = listWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(ORDER_LIST));
		ORDERS.get(0).click();
		wait.waitForElementDisplayed(RETURN_BUTTON);
		click.clickElementCarefully(RETURN_BUTTON);
		wait.waitForElementDisplayed(CHECKBOX);
		click.clickElementCarefully(CHECKBOX);
		click.clickElementCarefully(RETURN_BUTTON_AGAIN);
		wait.waitForElementDisplayed(OK_BUTTON);
		click.clickElementCarefully(OK_BUTTON);
		waitForPageLoad();
	}

	/**
	 * Clicks Search orders button
	 */
	public void clickSearchOrdersButton() {
		wait.waitForElementDisplayed(SEARCH_ORDERS_BUTTON);
		click.clickElementIgnoreExceptionAndRetry(SEARCH_ORDERS_BUTTON);
	}

	/**
	 * Clicks the Add Filter Button
	 */
	public void clickAddFilterBtn(){
		wait.waitForElementDisplayed(ADD_FILTER_BUTTON);
		click.clickElementCarefully(ADD_FILTER_BUTTON);
	}

	/**
	 * Enter given value into input for filter
	 * @param value
	 */
	public void enterFilterValue(String value) {
		wait.waitForElementDisplayed(FILTER_VALUE_INPUT);
		text.selectAllAndInputText(FILTER_VALUE_INPUT, value);

		wait.waitForElementDisplayed(OK_BUTTON);
		click.clickElementIgnoreExceptionAndRetry(OK_BUTTON);
	}
	/**
	 * Searches for an order based on the customers name and clicks it
	 */
	public void enterCustomersName(){
		text.clickElementAndEnterText(CUSTOMER_NAME_INPUT, "test auto");
		wait.waitForElementDisplayed(OK_BUTTON);
		click.clickElementCarefully(OK_BUTTON);
	}

	/**
	 * Searches for an order based on the customers name and clicks it
	 */
	public void enterOrderDate(){
		wait.waitForElementDisplayed(ENABLE_ORDER_DATE_START);
		click.clickElementIgnoreExceptionAndRetry(ENABLE_ORDER_DATE_START);
		wait.waitForElementDisplayed(OK_BUTTON);
		click.clickElementIgnoreExceptionAndRetry(OK_BUTTON);
	}

	/**
	 * Selects an Order status filter option
	 * @param orderStatusOption
	 */
	public void selectOrderStatusFilterOption(String orderStatusOption){
		wait.waitForElementDisplayed(By.cssSelector("input[type='checkbox'][aria-label='"+orderStatusOption+"']"));
		WebElement selectFilterOption = driver.findElement(By.cssSelector("input[type='checkbox'][aria-label='"+orderStatusOption+"']"));
		click.javascriptClick(selectFilterOption);
		wait.waitForElementDisplayed(OK_BUTTON);
		click.clickElementIgnoreExceptionAndRetry(OK_BUTTON);
	}

	/**
	 * Selects the order with an Order Status of Invoiced
	 * @param filterOption
	 */
	public void clickOnFilterOption(String filterOption){

		wait.waitForElementDisplayed(By.xpath("//div[text()='"+filterOption+"']"));
		WebElement selectFilterOption = driver.findElement(By.xpath("//div[text()='"+filterOption+"']"));
		click.clickElementCarefully(selectFilterOption);
	}



	/**
	 * Click on Sales Order
	 */
	public void selectSalesOrder(){
		List<WebElement> salesOrderList =  wait.waitForAllElementsDisplayed(ORDER_LIST);

		click.clickElementIgnoreExceptionAndRetry(salesOrderList.get(0));
	}

	/**
	 * Click on Sales Order
	 * @param itemSelection
	 */
	public void selectPickUpProducts(int itemSelection){
		List<WebElement> salesOrderList =  wait.waitForAllElementsDisplayed(PICK_UP_ITEM_LIST);

		click.clickElementCarefully(salesOrderList.get(itemSelection));
	}

	/**
	 * Clicks Pick Up after selecting a pick up item
	 */
	public void clickPickUp(){
		wait.waitForElementDisplayed(PICK_UP);
		click.clickElementCarefully(PICK_UP);
	}

	/**
	 * Selects each of the items in the return order
	 */
	public void selectReturnOrderItems(){
		wait.waitForElementDisplayed(RETURN_ORDER_BUTTON);
		click.clickElementCarefully(RETURN_ORDER_BUTTON);
		click.clickElementCarefully(RETURN_BUTTON_AGAIN);

		DRetailReturnTransactionPage returnPage = new DRetailReturnTransactionPage(driver);
		List <WebElement> productsToReturn = wait.waitForAllElementsDisplayed(RETURN_PRODUCT_LINE);

		for (int i = 0; i < productsToReturn.size(); i++) {
			actions.moveToElement(wait.waitForAllElementsDisplayed(RETURN_PRODUCT_LINE).get(i)).click().perform();
			returnPage.selectReturnReason();
		}

		returnPage.clickOnReturnBarButton();
	}

	/**
	 * Clicks the Ok button
	 */
	public void clickOkBtn(){
		waitForPageLoad();
		if(element.isElementDisplayed(OK_BUTTON))
		{
			wait.waitForElementDisplayed(OK_BUTTON);
			click.clickElementIgnoreExceptionAndRetry(OK_BUTTON);
			jsWaiter.sleep(2000);
		}
	}


	/**
	 * Clicks the Apply Button in the Add Filter option to apply the filter(s)
	 */
	public void clickApplyFilterBtn(){
		wait.waitForElementDisplayed(APPLY_BUTTON);
		click.clickElementCarefully(APPLY_BUTTON);
	}

	/**
	 * Applies a filter based on the given filter type and value
	 * @param filterType
	 * @param filterValue
	 */
	public void applyFilter(String filterType, String filterValue) {
		clickAddFilterBtn();
		clickOnFilterOption(filterType);
		enterFilterValue(filterValue);
		clickApplyFilterBtn();
	}

	/**
	 * Select Orders
	 */
	public void clickOrders( )
	{
		wait.waitForElementDisplayed(SELECT_ORDERS);
		wait.waitForElementEnabled(SELECT_ORDERS);
		click.clickElementCarefully(SELECT_ORDERS);
		waitForPageLoad();
	}

	/**
	 * Click Discounts
	 */
	public void clickDiscounts() {
		wait.waitForElementDisplayed(SELECT_DISCOUNTS);
		click.clickElementIgnoreExceptionAndRetry(SELECT_DISCOUNTS);
		waitForPageLoad();
	}

	/**
	 * Select Products
	 */
	public void clickProducts() {
		wait.waitForElementDisplayed(SELECT_PRODUCTS);
		wait.waitForElementEnabled(SELECT_PRODUCTS);
		click.clickElementIgnoreExceptionAndRetry(SELECT_PRODUCTS);
		waitForPageLoad();
	}

	/**
	 * Clicks 'Brown Aviator' button under Products tab to quickly add Brown Aviator Sunglasses to transaction
	 */
	public void clickBrownAviatorButton() {
		wait.waitForElementDisplayed(BROWN_AVIATOR_BUTTON);
		wait.waitForElementEnabled(BROWN_AVIATOR_BUTTON);
		click.clickElementIgnoreExceptionAndRetry(BROWN_AVIATOR_BUTTON);
		waitForPageLoad();
	}

	/**
	 * Clicks 'Black Thick Rimmed Sunglasses' button under Products tab to quickly add product to transaction
	 */
	public void clickBlackThickRimmedButton() {
		wait.waitForElementDisplayed(BLACK_THICK_RIMMED_BUTTON);
		click.clickElementIgnoreExceptionAndRetry(BLACK_THICK_RIMMED_BUTTON);
		waitForPageLoad();
	}

	/**
	 * Clicks 'Pink Thick Rimmed Sunglasses' button under Products tab to quickly add product to transaction
	 */
	public void clickPinkThickRimmedButton() {
		wait.waitForElementDisplayed(PINK_THICK_RIMMED_BUTTON);
		click.clickElementIgnoreExceptionAndRetry(PINK_THICK_RIMMED_BUTTON);
		waitForPageLoad();
	}

	/**
	 * Clicks 'Brown Leopardprint Sunglasses' button under Products tab to quickly add product to transaction
	 */
	public void clickBrownLeopardprintButton() {
		wait.waitForElementDisplayed(BROWN_LEOPARDPRINT_BUTTON);
		click.clickElementIgnoreExceptionAndRetry(BROWN_LEOPARDPRINT_BUTTON);
		waitForPageLoad();
	}

	/**
	 * Clicks 'Black Bold Framed Sunglasses' button under Products tab to quickly add product to transaction
	 */
	public void clickBlackBoldFramedButton() {
		wait.waitForElementDisplayed(BLACK_BOLD_FRAMED_BUTTON);
		click.clickElementIgnoreExceptionAndRetry(BLACK_BOLD_FRAMED_BUTTON);
		waitForPageLoad();
	}


	/**
	 * Clicks on Recall Order
	 */
	public void clickRecallOrder(){
		wait.waitForElementDisplayed(RECALL_ORDER);
		click.javascriptClick(RECALL_ORDER);
	}

	/**
	 * Clicks the Pick and packing button
	 * @param optionType
	 */
	public void clickPickingAndPacking(String optionType){
		wait.waitForElementDisplayed(PICKING_AND_PACKING);
		click.clickElementCarefully(PICKING_AND_PACKING);

		WebElement selectPickingAndPacking = wait.waitForElementDisplayed(By.xpath("//span[text()='"+optionType+"']"));
		click.clickElementCarefully(selectPickingAndPacking);
	}

	/**
	 * Select Ship All
	 */
	public void selectShipAll( )
	{
		wait.waitForElementEnabled(BUTTON_SHIP_ALL);
		click.javascriptClick(BUTTON_SHIP_ALL);
		waitForPageLoad();
		if(handleCartMessage())
		{
			if(element.isElementEnabled(BUTTON_SHIP_ALL))
				try{click.clickElementIgnoreExceptionAndRetry(BUTTON_SHIP_ALL);}
			catch(Exception ex){}
		}
	}

	/**
	 * Click on Ship Selected
	 */
	public void selectShipSelected()
	{
		wait.waitForElementEnabled(BUTTON_SHIP_SELECTED);
		click.clickElementIgnoreExceptionAndRetry(BUTTON_SHIP_SELECTED);
		waitForPageLoad();
		if(handleCartMessage() && element.isElementDisplayed(BUTTON_SHIP_SELECTED))
		{
			wait.waitForElementEnabled(BUTTON_SHIP_SELECTED);
			click.clickElementIgnoreExceptionAndRetry(BUTTON_SHIP_SELECTED);
		}
	}

	/**
	 * Clicks on Pick up selected
	 */
	public void clickPickUpSelected() {
		wait.waitForElementEnabled(PICKUP_SELECTED_BUTTON);
		click.clickElementIgnoreExceptionAndRetry(PICKUP_SELECTED_BUTTON);
		waitForPageLoad();
		if(handleCartMessage()) {
			wait.waitForElementEnabled(PICKUP_SELECTED_BUTTON);
			click.clickElementIgnoreExceptionAndRetry(PICKUP_SELECTED_BUTTON);
		}
	}

	/**
	 * Clicks on Houston under Available stores
	 */
	public void selectHoustonStore() {
		wait.waitForElementDisplayed(HOUSTON_STORE_PICKUP);
		click.clickElementIgnoreExceptionAndRetry(HOUSTON_STORE_PICKUP);
		waitForPageLoad();
		wait.waitForElementEnabled(SHIP_OK_BUTTON);
		click.clickElementIgnoreExceptionAndRetry(SHIP_OK_BUTTON);
	}


	/**
	 * Select Customer Order
	 */
	public void selectCustomerOrder( )
	{
		wait.waitForElementEnabled(CUSTOMER_ORDER);
		click.clickElementIgnoreExceptionAndRetry(CUSTOMER_ORDER);
		waitForPageLoad();
	}

	/**
	 * Selects Create Customer Order
	 */
	public void clickCreateCustomerOrder(){
		wait.waitForElementDisplayed(CREATE_CUSTOMER_ORDER);
		click.javascriptClick(CREATE_CUSTOMER_ORDER);
		waitForPageLoad();
	}

	/**
	 * Clicks on the line of product name
	 * @param productName
	 */
	public void clickProductLine(String productName) {
		By lineTabLoc = By.xpath(String.format("//div[@aria-label='Lines']//div[text()='%s']", productName));

		wait.waitForElementDisplayed(lineTabLoc);
		click.clickElementIgnoreExceptionAndRetry(lineTabLoc);
	}

	/**
	 * Clicks Pick Up All
	 */
	public void clickPickUpAll(){
		wait.waitForElementDisplayed(PICK_UP_ALL);
		click.javascriptClick(PICK_UP_ALL);
		waitForPageLoad();
		if(handleCartMessage())
		{
			wait.waitForElementEnabled(PICK_UP_ALL);
			click.javascriptClick(PICK_UP_ALL);
		}
		wait.waitForElementDisplayed(PICK_UP_ALL_HOUSTON);
		click.clickElementCarefully(PICK_UP_ALL_HOUSTON);
		selectDate();
	}

	public void shipAllStandard()
	{
		selectShipAll();
		DRetailShippingPage shippingPage = new DRetailShippingPage(driver);
		shippingPage.shipStandard();
	}

	/**
	 * Select Standard Shipping
	 */
	public void shipSelectedStandardShipping( )
	{
		selectShipSelected();
		DRetailShippingPage shippingPage = new DRetailShippingPage(driver);
		shippingPage.shipStandard();
	}
	/**
	 * Select Standard Shipping
	 */
	public void standardShipping( )
	{
		wait.waitForElementDisplayed(STANDARD_SHIP);
		click.clickElementIgnoreExceptionAndRetry(STANDARD_SHIP);
		waitForPageLoad();
		if(handleCartMessage()) {
			wait.waitForElementEnabled(STANDARD_SHIP);
			click.clickElementIgnoreExceptionAndRetry(STANDARD_SHIP);
		}
		wait.waitForElementDisplayed(SHIP_OK_BUTTON);
		click.clickElementIgnoreExceptionAndRetry(SHIP_OK_BUTTON);
	}

	/***
	 * handle Cart Messages
	 * @return
	 */
	public boolean handleCartMessage()
	{
		if(element.isElementDisplayed(KIND_OF_ORDER_CUSTOMER)) {
			click.clickElement(KIND_OF_ORDER_CUSTOMER);
			clickOkBtn();
			waitForPageLoad();
			return true;
		}
//		if (element.isElementDisplayed(ORDER_HAS_CHANGED))
//		{
//			click.clickElement(ORDER_HAS_CHANGED);
//			return true;
//		}
		return false;
	}
	/**
	 * Selects the date to pickup or ship
	 */
	public void selectDate(){
		WebElement dateEle = wait.waitForElementDisplayed(DATE_DAY);
		Select s = new Select(dateEle);
		String Day = s.getFirstSelectedOption().getText();
		VertexLogger.log(Day);

		Date dt = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dt);
		calendar.add(Calendar.DATE, 1);
		dt = calendar.getTime();

		String tommorowsDateMonth = new SimpleDateFormat("M").format(dt);
		String tommorowsDateDay = new SimpleDateFormat("d").format(dt);

		if (tommorowsDateMonth != new SimpleDateFormat("M").format(LocalDate.now().getDayOfMonth()))
		{
			WebElement dateMonthEle = wait.waitForElementDisplayed(DATE_MONTH);
			Select sMonth = new Select(dateMonthEle);
			String Month = sMonth.getFirstSelectedOption().getText();
			VertexLogger.log(Month);
			sMonth.selectByValue(String.valueOf(Integer.valueOf(tommorowsDateMonth)-1));
		}
		s.selectByValue(tommorowsDateDay);
		wait.waitForElementDisplayed(SHIP_OK_BUTTON);
		click.clickElementCarefully(SHIP_OK_BUTTON);
	}

	/**
	 * Select Standard Overnight Shipping
	 */
	public void shipSelectedStandardOvernightShipping( )
	{
		selectShipSelected();
		DRetailShippingPage shippingPage = new DRetailShippingPage(driver);
		shippingPage.shipStandardOvernight();
	}

	/**
	 * Select Standard Overnight Shipping
	 */
	public void shipAllSelectedStandardOvernightShipping( )
	{
		selectShipAll();
		DRetailShippingPage shippingPage = new DRetailShippingPage(driver);
		shippingPage.shipStandardOvernight();
	}

	/**
	 * Select Standard Overnight Shipping
	 */
	public void standardOvernightShipping( ) {
		wait.waitForElementDisplayed(STANDARD_OVERNIGHT_SHIP);
		click.clickElementIgnoreExceptionAndRetry(STANDARD_OVERNIGHT_SHIP);
		waitForPageLoad();

		if(handleCartMessage()) {
			wait.waitForElementDisplayed(STANDARD_OVERNIGHT_SHIP);
			click.clickElementIgnoreExceptionAndRetry(STANDARD_OVERNIGHT_SHIP);
		}

		wait.waitForElementDisplayed(SHIP_OK_BUTTON);
		click.clickElementCarefully(SHIP_OK_BUTTON);
	}

	/**
	 * validate shipping method is displayed
	 */

	public String shipValidation( )
	{
		wait.waitForElementDisplayed(VALIDATE_SHIP);
		String shipText = driver.findElement(VALIDATE_SHIP).getText();
		VertexLogger.log(shipText);
		return shipText;
	}

	/**
	 * Clicks the Delivery Button at the top of the screen
	 */
	public void clickDeliveryBtn(){
		wait.waitForElementDisplayed(DELIVERY_BUTTON);
		click.clickElementCarefully(DELIVERY_BUTTON);
	}

	/**
	 * PAY CASH BUTTON
	 */
	public void selectPayCash( )
	{
		wait.waitForElementDisplayed(PAY_CASH_BUTTON);
		wait.waitForElementEnabled(PAY_CASH_BUTTON);
		click.clickElementIgnoreExceptionAndRetry(PAY_CASH_BUTTON);
	}

	/**
	 * CASH ACCEPTED/ ENTER BUTTON
	 */
	public void cashAccepted( )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(CASH_ACCEPTED);
		wait.waitForElementEnabled(CASH_ACCEPTED);
		click.clickElementCarefully(CASH_ACCEPTED);
	}

	/**
	 * PAY BALANCE LATER BUTTON
	 */
	public void payBalance( )
	{
		wait.waitForElementDisplayed(PAY_BALANCE_LATER);
		click.clickElementCarefully(PAY_BALANCE_LATER);
	}

	/**
	 * Close BUTTON
	 */
	public void closeButton( )
	{
		wait.waitForElementDisplayed(CLOSE_BUTTON);
		click.javascriptClick(CLOSE_BUTTON);
		waitForPageLoad();
	}

	/**
	 * Change Unit of Measure
	 */
	public void clickOnChangeUnitOfMeasure()
	{
		WebElement changeUnitOfMeasureEle = wait.waitForElementEnabled(CHANGE_UNIT_OF_MEASURE);
		click.clickElement(changeUnitOfMeasureEle);
	}

	/**
	 * Change Unit of Measure
	 */
	public void selectFirstEach()
	{
		WebElement eachEle = wait.waitForElementEnabled(EACH);
		click.clickElement(eachEle);
	}

	/**
	 * Select pieces option when changing unit of measure
	 */
	public void selectPieces(){
		wait.waitForElementDisplayed(PIECES_UNIT_OPTION);
		click.clickElementIgnoreExceptionAndRetry(PIECES_UNIT_OPTION);
	}

	/**
	 * Click on Action
	 */
	public void clickOnAction()
	{
		wait.waitForElementDisplayed(ACTION);
		wait.waitForElementEnabled(ACTION);
		click.clickElementCarefully(ACTION);
	}

	/**
	 * Clicks on Set Quantity, sets the Quantity and presses ok
	 * @param quantityAmount
	 */
	public void clickOnSetQuantity(String quantityAmount){
		wait.waitForElementDisplayed(SET_QUANTITY);
		click.clickElementCarefully(SET_QUANTITY);

		waitForPageLoad();
		WebElement enterAmount = wait.waitForElementEnabled(ENTER_QUANTITY);
		enterAmount.sendKeys(quantityAmount);
		click.clickElementCarefully(ENTER_BUTTON_SET_QUANTITY);
		waitForPageLoad();

	}

	/**
	 * Clicks on Line comment, enters given comment and clicks OK Button
	 * @param comment
	 */
	public void clickOnLineComment(String comment) {
		wait.waitForElementDisplayed(LINE_COMMENT_BUTTON);
		click.clickElementCarefully(LINE_COMMENT_BUTTON);

		wait.waitForElementDisplayed(FILTER_VALUE_INPUT);
		text.selectAllAndInputText(FILTER_VALUE_INPUT, comment);
		clickOkBtn();
	}

	/**
	 * Select Second Item
	 * @param itemSelected
	 */
	public void selectItem(String itemSelected)
	{
		By itemPicked = By.xpath("//div[contains(@class,'tillLayout-SelectedDeliveryFields')]//div[contains(@class,'tillLayout-ProductNameField')]//div[contains(text(),'"+itemSelected+"')]");
		click.clickElementCarefully(itemPicked);
	}

	/**
	 * Select First Item
	 * @param itemSelected
	 */
	public void selectFirstItem(String itemSelected)
	{
		By itemPicked = By.xpath("//div[contains(@class,'tillLayout-SelectedLinesFields')]//div[contains(@class,'tillLayout-ProductNameField')]//div[contains(text(),'"+itemSelected+"')]");
		click.clickElementCarefully(itemPicked);
	}

	/**
	 * Select Carryout selected
	 */
	public void selectCarryOutSelected()
	{
		wait.waitForElementDisplayed(CARRY_OUT_SELECTED);
		click.clickElementCarefully(CARRY_OUT_SELECTED);
		wait.waitForElementDisplayed(CUSTOMER_CARRY_OUT);
		waitForPageLoad();
	}

	/***
	 * Click on yes button
	 */
	public void clickYesButton( )
	{
		wait.waitForElementDisplayed(YES_BUTTON);
		click.clickElementCarefully(YES_BUTTON);
	}

	/**
	 * Clicks the Deposit Override button
	 */
	public void clickDepositOverride(){
		wait.waitForElementDisplayed(DEPOSIT_OVERRIDE);
		click.clickElementCarefully(DEPOSIT_OVERRIDE);
	}

	/**
	 * Clicks the Maximum Override Amount for the Deposit Override
	 */
	public void clickMaximumOverrideAmount(){
		wait.waitForElementDisplayed(MAXIMUM_OVERRIDE_AMOUNT);
		click.clickElementCarefully(MAXIMUM_OVERRIDE_AMOUNT);

		wait.waitForElementDisplayed(DEPOSIT_OVERRIDE_ACCEPTED);
		click.clickElementCarefully(DEPOSIT_OVERRIDE_ACCEPTED);
	}

	/**
	 * Clicks on the edit button to edit the order
	 */
	public void clickEditOrder(){
		wait.waitForElementDisplayed(EDIT_ORDER_BUTTON);
		click.clickElementIgnoreExceptionAndRetry(EDIT_ORDER_BUTTON);
	}

	/**
	 * Gets the Deposit Due Amount
	 * @return the Deposit Due Amount
	 */
	public String getDepositDue(){
		wait.waitForElementDisplayed(DEPOSIT_DUE);
		String depositDueText = driver.findElement(DEPOSIT_DUE).getText();
		return depositDueText;
	}

	/**
	 * Waits for the page to load
	 * @return true or false for if the page is loaded with the Deposit Due Amount
	 */
	public boolean isPageLoaded(){
		waitForPageLoad();
		wait.waitForElementDisplayed(DEPOSIT_DUE);
		boolean result = element.isElementDisplayed(DEPOSIT_DUE);
		return result;
	}
}