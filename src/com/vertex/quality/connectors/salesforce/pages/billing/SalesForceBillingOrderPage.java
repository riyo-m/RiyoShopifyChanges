package com.vertex.quality.connectors.salesforce.pages.billing;

import com.vertex.quality.common.utils.misc.VertexCurrencyUtils;
import com.vertex.quality.connectors.salesforce.enums.Constants;
import com.vertex.quality.connectors.salesforce.enums.NavigateMenu;
import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import com.vertex.quality.connectors.salesforce.pages.crm.SalesForceCRMPostLogInPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * Common functions for anything related to Salesforce Order Page in Billing package.
 *
 */
public class SalesForceBillingOrderPage extends SalesForceBasePage
{
	protected By LOADING_IMAGE = By.cssSelector("#lineItemView_loading[style*='block']");
	protected By NEW_NEXT_TO_RECENT_OPORTUNITIES = By.cssSelector("input[name = 'new']");

	protected By ACTIVATE_BUTTON = By.xpath("//input[@title='Activate']");
	protected By DEACTIVATE_BUTTON = By.xpath("//input[@title='Deactivate']");
	protected By ACCOUNT_NAME = By.xpath(
		"//label[text()='Account Name']/../following-sibling::td//span[@class='lookupInput']//input");
	protected By BILLING_ACCOUNT_NAME = By.xpath(
		"//label[text()='Billing Account']/../following-sibling::td//span[@class='lookupInput']//input");
	protected By CONTRACT_NUMBER = By.xpath(
		"//label[text()='Contract Number']/../following-sibling::td//span[@class='lookupInput']//input");
	protected By SUBSCRIPTION_TERM = By.xpath("//label[text()='Subscription Term']/../following-sibling::td//input");
	protected By QUOTE_NAME = By.xpath(
		"//label[text()='Quote']/../following-sibling::td//span[@class='lookupInput']//input");
	protected By DISCOUNT_PERCENT_INPUT = By.xpath(
			"//label[contains(text(),'DiscountPercent')]//parent::td/following-sibling::td/input");
	protected By DISCOUNT_AMOUNT_INPUT = By.xpath(
			"//label[contains(text(),'DiscountAmount')]//parent::td/following-sibling::td/input");
	protected By BILL_NOW_CHECKBOX = By.xpath("//label[text()='Bill Now']/../following-sibling::td//input");
	protected By ORDER_NUMBER = By.xpath("//div[@id='OrderNumber_ileinner']");
	protected By ESTIMATED_TAX = By.xpath(
		"//div[@class='pbSubsection']/table/tbody/tr/td/span[text()='Estimated Tax']/../following-sibling::td/div");

	protected By ORDER_PRODUCT_TABLE = By.xpath("//div[@class='bRelatedList first']/div//div[@class='pbBody']/table");
	protected By ORDER_PRODUCT_TABLE_HEADER_ROW = By.xpath("//div[@class='bRelatedList first']/div//div[@class='pbBody']/table/tbody/tr[@class='headerRow']");

	protected By ORDER_SAVE_BUTTON = By.xpath("//div[@class = 'pbHeader']//input[@title = 'Save']");
	protected By ORDER_EDIT_BUTTON = By.xpath("//div[@class = 'pbHeader']//input[@title = 'Edit']");
	protected By ORDER_DELETE_BUTTON = By.xpath("//div[@class = 'pbHeader']//input[@title = 'Delete']");

	protected By SHIP_TO_CONTACT_INPUT = By.id("ShipToContact");
	protected By SHIP_TO_STREET_INPUT = By.id("ShippingAddressstreet");
	protected By SHIP_TO_CITY_INPUT = By.id("ShippingAddresscity");
	protected By SHIP_TO_STATE_INPUT = By.id("ShippingAddressstate");
	protected By SHIP_TO_POSTAL_CODE_INPUT = By.id("ShippingAddresszip");
	protected By SHIP_TO_COUNTRY_INPUT = By.id("ShippingAddresscountry");

	protected By BILL_TO_CONTACT_INPUT = By.id("BillToContact");
	protected By BILL_TO_STREET_INPUT = By.id("BillingAddressstreet");
	protected By BILL_TO_CITY_INPUT = By.id("BillingAddresscity");
	protected By BILL_TO_STATE_INPUT = By.id("BillingAddressstate");
	protected By BILL_TO_POSTAL_CODE_INPUT = By.id("BillingAddresszip");
	protected By BILL_TO_COUNTRY_INPUT = By.id("BillingAddresscountry");


	protected By VIEW_GO_BUTTON = By.xpath(".//form/div/span/span/input[@name='go']");

	protected By ORDER_NUMBER_LINK = By.xpath("//td[text()='Order']/following-sibling::td/div/a");
	protected By INVOICE_LINK = By.xpath("//th/a[contains(text(), 'INV')]");

	protected By SORT_ORDER_NUMBER_DESC = By.xpath(".//td[contains(@class, 'ORDER_NUMBER DESC')]");
	protected By SORT_ORDER_NUMBER_ASC = By.xpath(".//td[contains(@class, 'ORDER_NUMBER ASC')]");
	protected By SWITCH_SORT_ORDER_BUTTON = By.xpath(".//div[contains(@class, 'ORDERS_ORDER_NUMBER')]/img");

	SalesForceCRMPostLogInPage postLogInPage = new SalesForceCRMPostLogInPage(driver);

	public SalesForceBillingOrderPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Click on New button
	 */
	public void clickOnNewButton( )
	{
		wait.waitForElementDisplayed(NEW_NEXT_TO_RECENT_OPORTUNITIES);
		click.clickElement(NEW_NEXT_TO_RECENT_OPORTUNITIES);
		waitForPageLoad();
	}

	/**
	 * Click on Activate button
	 */
	public void clickOnActivateButton( )
	{
		wait.waitForElementDisplayed(ACTIVATE_BUTTON);
		click.clickElement(ACTIVATE_BUTTON);
		alert.acceptAlert(DEFAULT_TIMEOUT);
		waitForPageLoad();
	}

	/**
	 * Click on Deactivate button
	 */
	public void clickOnDeactivateButton( )
	{
		wait.waitForElementDisplayed(DEACTIVATE_BUTTON);
		click.clickElement(DEACTIVATE_BUTTON);
		alert.acceptAlert(DEFAULT_TIMEOUT);
		waitForPageLoad();
	}

	/**
	 * Select Available Account Name
	 *
	 * @param account_name
	 */
	public void selectAccountName( String account_name )
	{
		wait.waitForElementDisplayed(ACCOUNT_NAME);
		text.enterText(ACCOUNT_NAME, account_name);
	}

	/**
	 * Select Available Account Name
	 *
	 * @param account_name
	 */
	public void selectBillingAccountName( String account_name )
	{
		wait.waitForElementDisplayed(BILLING_ACCOUNT_NAME);
		text.enterText(BILLING_ACCOUNT_NAME, account_name);
	}

	/**
	 * Select contract number
	 *
	 * @param contract_number
	 */
	public void setContractNumber( String contract_number )
	{
		wait.waitForElementDisplayed(CONTRACT_NUMBER);
		text.enterText(CONTRACT_NUMBER, contract_number);
	}

	/**
	 * Select Subscription Term
	 *
	 * @param term
	 */
	public void SetSubscriptionTerm( String term )
	{
		wait.waitForElementDisplayed(SUBSCRIPTION_TERM);
		text.enterText(SUBSCRIPTION_TERM, term);
	}

	/**
	 * Select Available Quote Name
	 *
	 * @param quote_name
	 */
	public void selectQuoteName( String quote_name )
	{
		wait.waitForElementDisplayed(QUOTE_NAME);
		text.enterText(QUOTE_NAME, quote_name);
	}

	/**
	 * Check Bill Now Checkbox
	 */
	public void selectBillNow( )
	{
		wait.waitForElementDisplayed(BILL_NOW_CHECKBOX);
		click.clickElement(BILL_NOW_CHECKBOX);
	}

	/**
	 * Set ship to contact
	 */
	public void setShipToContact(String shipToContact)
	{
		wait.waitForElementDisplayed(SHIP_TO_CONTACT_INPUT);
		text.enterText(SHIP_TO_CONTACT_INPUT, shipToContact);
	}

	/**
	 * Set bill to contact
	 */
	public void setBillToContact(String billToContact)
	{
		wait.waitForElementDisplayed(BILL_TO_CONTACT_INPUT);
		text.enterText(BILL_TO_CONTACT_INPUT, billToContact);
	}

	/**
	 * Set discount percent
	 * @param discountPercent
	 */
	public void setDiscountPercent(String discountPercent)
	{
		if(!discountPercent.equals(""))
		{
			wait.waitForElementDisplayed(DISCOUNT_PERCENT_INPUT);
			text.enterText(DISCOUNT_PERCENT_INPUT, discountPercent);
		}
	}

	/**
	 * Set discount amount
	 * @param discountAmount
	 */
	public void setDiscountAmount(String discountAmount)
	{
		if(!discountAmount.equals(""))
		{
			wait.waitForElementDisplayed(DISCOUNT_AMOUNT_INPUT);
			text.enterText(DISCOUNT_AMOUNT_INPUT, discountAmount);
		}
	}

	/**
	 * CLick on Edit button
	 */
	public void clickEditButton()
	{
		wait.waitForElementDisplayed(ORDER_EDIT_BUTTON);
		click.clickElement(ORDER_EDIT_BUTTON);
		waitForSalesForceLoaded();
	}
	/**
	 * Click on Save button
	 */
	public void clickSaveButton( )
	{
		wait.waitForElementDisplayed(ORDER_SAVE_BUTTON);
		click.clickElement(ORDER_SAVE_BUTTON);
		waitForSalesForceLoaded();
	}

	/**
	 * Get order number
	 *
	 * @return order number of current order
	 */
	public String getOrderNumber( )
	{
		String currentOrderNumber;
		wait.waitForElementDisplayed(ORDER_NUMBER);
		currentOrderNumber = text.getElementText(ORDER_NUMBER);
		waitForSalesForceLoaded();

		return currentOrderNumber;
	}

	/**
	 * To get the estimated tax of current order
	 */
	public String getEstimatedTax( )
	{
		String estimatedTax;
		refreshPage();
		wait.waitForElementDisplayed(ESTIMATED_TAX);
		estimatedTax = text.getElementText(ESTIMATED_TAX);
		waitForSalesForceLoaded();

		return estimatedTax;
	}

	/**
	 * To Create new order with below parameters
	 *
	 * @param accountName
	 * @param quoteName
	 */
	public void createNewOrder( String accountName, String quoteName )
	{
		postLogInPage.clickSalesPageATab(NavigateMenu.Sales.ORDERS_TAB.text);
		clickOnNewButton();
		waitForSalesForceLoaded();
		waitForPageLoad();
		selectAccountName(accountName);
		selectQuoteName(quoteName);
		selectBillNow();
		clickSaveButton();
		waitForSalesForceLoaded();
	}

	/**
	 * To edit order with below parameters
	 *
	 * @param accountName
	 * @param contractNumber
	 */
	public void editOrder( String accountName, String contractNumber )
	{
		editOrder(accountName, contractNumber, "");
	}

	/**
	 * To edit order with below parameters
	 *
	 * @param accountName
	 * @param contractNumber
	 * @param billAccountName
	 */
	public void editOrder( String accountName, String contractNumber, String billAccountName )
	{
		editOrder(accountName, contractNumber, billAccountName, "", "");
	}

	/**
	 * To edit order with below parameters
	 *
	 * @param accountName
	 * @param contractNumber
	 * @param billAccountName
	 */
	public void editOrder( String accountName, String contractNumber, String billAccountName, String discountPercent, String discountAmount )
	{
		waitForSalesForceLoaded();
		selectAccountName(accountName);
		selectBillingAccountName(billAccountName);
		setContractNumber(contractNumber);
		setDiscountPercent(discountPercent);
		setDiscountAmount(discountAmount);
		clickSaveButton();
		waitForSalesForceLoaded();
	}

	/**
	 * To edit order with below parameters
	 *
	 * @param accountName
	 * @param contractNumber
	 * @param shipToName
	 * @param billToName
	 */
	public void editOrder( String accountName, String contractNumber, String shipToName, String billToName )
	{
		waitForSalesForceLoaded();
		selectAccountName(accountName);
		selectBillingAccountName(accountName);
		setContractNumber(contractNumber);
		setShipToContact(shipToName);
		setBillToContact(billToName);
		clickSaveButton();
		waitForSalesForceLoaded();
	}

	/**
	 * Method to get product details by passing column name and product name
	 *
	 * @param productName
	 * @param columnName
	 *
	 * @return relevant product details based on product name and column
	 */
	private String getOrderProductDetails( String productName, String columnName )
	{
		int columnIndex = getColumnIndex(columnName);
		String cellValue = getOrderProductTableCellValue(productName, columnIndex);
		return cellValue;
	}

	/**
	 * Method to validate various prices on order line item
	 *
	 * @param productName
	 * @param columnName
	 *
	 * @return relevant price value based on product name and column
	 */
	private double getPriceValues( String productName, String columnName )
	{
		String text = getOrderProductDetails(productName, columnName);
		String noCurrency = trimCurrencySubstring(text);
		double salesPrice = VertexCurrencyUtils.cleanseCurrencyString(noCurrency);
		return salesPrice;
	}

	/**
	 * To validate details related to order product line items on order page
	 *
	 * @param productName
	 * @param quantity
	 * @param unitPrice
	 * @param totalPrice
	 * @param estimatedTax
	 */
	public void validateOrderProductDetails(String productName, int quantity, double unitPrice, double totalPrice, double estimatedTax)
	{
		waitForPageLoad();
		waitForSalesForceLoaded();

		String taxStatus = getOrderProductDetails(productName, "Estimated Tax");
		int i = 0;
		while ( (taxStatus == "" || !taxStatus.contains(Double.toString(estimatedTax))) && i < 5)
		{
			refreshPage();
			taxStatus = getOrderProductDetails(productName, "Estimated Tax");
			i++;
		}

		String quantityValue = getOrderProductDetails(productName, "Quantity");
		quantityValue = quantityValue.replace(".00", "");

		int qty = Integer.parseInt(quantityValue);
		assertEquals(qty, quantity, "Fail. Actual Value: " + qty);

		double salesPriceValue = getPriceValues(productName, "Unit Price");
		assertEquals(salesPriceValue, unitPrice, "Fail. Actual Value: " + salesPriceValue);

		double totalPriceValue = getPriceValues(productName, "Total Price");
		assertEquals(totalPriceValue, totalPrice, "Fail. Actual Value: " + totalPriceValue);

		double taxAmountValue = getPriceValues(productName, "Estimated Tax");
		assertEquals(taxAmountValue, estimatedTax, "Fail. Actual Value: " + taxAmountValue);

		String productNameValue = getOrderProductDetails(productName, "Product");
		assertEquals(productNameValue, productName);
	}

	/**
	 * Method to get index of column based on column name
	 *
	 * @param columnName
	 *
	 * @return index of column
	 */
	private int getColumnIndex( String columnName )
	{
		waitForPageLoad();
		int columnIndex = -1;
		wait.waitForElementDisplayed(getOrderProductTable());
		WebElement tableContainer = getOrderProductTable();
		WebElement headerRow = tableContainer.findElement(ORDER_PRODUCT_TABLE_HEADER_ROW);
		wait.waitForElementDisplayed(headerRow);
		List<WebElement> headerCells = headerRow.findElements(By.tagName("th"));
		for ( int x = 0 ; x < headerCells.size() ; x++ )
		{
			WebElement column = headerCells.get(x);
			String current_column_label = column.getAttribute("innerText");
			if ( current_column_label.equals(columnName) )
			{
				columnIndex = x;
				break;
			}
		}
		return columnIndex;
	}

	/**
	 * Method to get the text value of specific cell in the order product table
	 *
	 * @param productName
	 * @param columnIndex
	 *
	 * @return text in cell located by productName and columnIndex
	 */
	private String getOrderProductTableCellValue(String productName, int columnIndex )
	{
		WebElement cell = null;
		WebElement row = getOrderProductTableRowByProductName(productName);
		List<WebElement> cells = row.findElements(By.tagName("td"));
		if ( columnIndex == 1){
			cell = row.findElement(By.tagName("th"));
		}
		else
		{
			columnIndex = columnIndex - 1;
			cell = cells.get(columnIndex);
		}

		String cellValue = cell.getText();
		return cellValue;
	}

	/**
	 * Method to return order product table as web element
	 *
	 * @return order product table as a WebElement
	 */
	private WebElement getOrderProductTable( )
	{
		WebElement tableContainer = element.getWebElement(ORDER_PRODUCT_TABLE);
		return tableContainer;
	}

	/**
	 * Method to find table row associated with specific product
	 *
	 * @param productName
	 *
	 * @return table row with relevant product as a WebElement
	 */
	private WebElement getOrderProductTableRowByProductName(String productName )
	{
		String orderRow = String.format(".//tbody/tr/*/a[contains(text(),'%s')]/../parent::tr", productName);
		WebElement tableRow = element.getWebElement(By.xpath(orderRow));
		return tableRow;
	}

	/**
	 * Edit Address on order page based on Address Type
	 *
	 * @param street
	 * @param city
	 * @param state
	 * @param postalCode
	 * @param country
	 * @param addressType
	 */
	public void editOrderAddress( String street, String city, String state, String postalCode, String country,
								  String addressType )
	{
		clickEditButton();
		waitForPageLoad();
		setStreet(street, addressType);
		setCity(city, addressType);
		setState(state, addressType);
		setPostalCode(postalCode, addressType);
		setCountry(country, addressType);
		clickSaveButton();
	}

	/**
	 * Set Street Value based on AddressType
	 *
	 * @param street
	 * @param addressType
	 */
	private void setStreet( String street, String addressType )
	{
		if ( addressType.equals(Constants.AddressType.BILLING_ADDRESS.text) )
		{
			text.enterText(BILL_TO_STREET_INPUT, street);
		}
		else
		{
			text.enterText(SHIP_TO_STREET_INPUT, street);
		}
	}

	/**
	 * Set City Value based on AddressType
	 *
	 * @param city
	 * @param addressType
	 */
	private void setCity( String city, String addressType )
	{
		if ( addressType.equals(Constants.AddressType.BILLING_ADDRESS.text) )
		{
			text.enterText(BILL_TO_CITY_INPUT, city);
		}
		else
		{
			text.enterText(SHIP_TO_CITY_INPUT, city);
		}
	}

	/**
	 * Set State Value based on AddressType
	 *
	 * @param state
	 * @param addressType
	 */
	private void setState( String state, String addressType )
	{
		if ( addressType.equals(Constants.AddressType.BILLING_ADDRESS.text) )
		{
			text.enterText(BILL_TO_STATE_INPUT, state);
		}
		else
		{
			text.enterText(SHIP_TO_STATE_INPUT, state);
		}
	}

	/**
	 * Set Postal Code Value based on AddressType
	 *
	 * @param postalCode
	 * @param addressType
	 */
	private void setPostalCode( String postalCode, String addressType )
	{
		if ( addressType.equals(Constants.AddressType.BILLING_ADDRESS.text) )
		{
			text.enterText(BILL_TO_POSTAL_CODE_INPUT, postalCode);
		}
		else
		{
			text.enterText(SHIP_TO_POSTAL_CODE_INPUT, postalCode);
		}
	}

	/**
	 * Set Country Value based on AddressType
	 *
	 * @param country
	 * @param addressType
	 */
	private void setCountry( String country, String addressType )
	{
		if ( addressType.equals(Constants.AddressType.BILLING_ADDRESS.text) )
		{
			text.enterText(BILL_TO_COUNTRY_INPUT, country);
		}
		else
		{
			text.enterText(SHIP_TO_COUNTRY_INPUT, country);
		}
	}

	/**
	 * Generates invoice for order by selecting bill now on order
	 */
	public void generateInvoice()
	{
		clickEditButton();
		selectBillNow();
		clickSaveButton();
		waitForSalesForceLoaded();
	}

	/**
	 * Clicks invoice link that takes to Invoice page
	 */
	public void clickInvoiceLink()
	{
		wait.waitForElementDisplayed(INVOICE_LINK);
		click.clickElement(INVOICE_LINK);
	}

	/**
	 * Click delete button
	 */
	public void clickDeleteButton( )
	{
		wait.waitForElementDisplayed(ORDER_DELETE_BUTTON);
		click.clickElement(ORDER_DELETE_BUTTON);
		alert.acceptAlert(DEFAULT_TIMEOUT);
		waitForPageLoad();
	}

	/**
	 * Clicks go button to search for order
	 */
	public void clickGoButton( )
	{
		wait.waitForElementDisplayed(VIEW_GO_BUTTON);
		click.clickElement(VIEW_GO_BUTTON);
		waitForPageLoad();
	}



	/**
	 * To check if order exists
	 *
	 * @param orderNum
	 *
	 * @return true is order exists and false if not
	 */
	public boolean checkForOrder( String orderNum )
	{
		String orderLookup = String.format("//table/tbody/tr/td/div/a/span[text()='%s']", orderNum);
		sortByOrderNumberDescending();
		try
		{
			wait.waitForElementDisplayed(By.xpath(orderLookup), 10);
			click.clickElement(By.xpath(orderLookup));
			waitForPageLoad();
		}
		catch ( Exception e )
		{
			return false;
		}
		return true;
	}

	/**
	 * Deletes order
	 *
	 * @param orderNum
	 */
	public void deleteOrder( String orderNum )
	{
		clickGoButton();
		if ( checkForOrder(orderNum) )
		{
			// Deactivates order so it can be deleted
			clickOnDeactivateButton();
			clickDeleteButton();
		}
	}

	/**
	 * Deletes non-active order
	 *
	 * @param orderNum
	 */
	public void deleteDeactivatedOrder( String orderNum )
	{
		clickGoButton();
		if ( checkForOrder(orderNum) )
		{
			clickDeleteButton();
		}
	}

	/**
	 * Navigates to Order based on Order Number
	 */
	public void navigateToOrder(String orderNum)
	{
		clickGoButton();
		checkForOrder(orderNum);
	}

	/**
	 * Navigates to Order Product based on product name
	 */
	public void navigateToOrderProduct(String productName)
	{
		waitForSalesForceLoaded();
		WebElement row = getOrderProductTableRowByProductName(productName);
		WebElement productLink = row.findElement(By.tagName("th"));
		wait.waitForElementDisplayed(productLink);
		click.clickElement(productLink);

	}

	/**
	 * Navigates back to Order by clicking order number link
	 */
	public void navigateBackToOrder()
	{
		wait.waitForElementDisplayed(ORDER_NUMBER_LINK);
		click.clickElement(ORDER_NUMBER_LINK);
		waitForPageLoad();
		waitForSalesForceLoaded();
	}

	/**
	 * Sort list of orders in descending order
	 * Makes sure most recent order is at top of list
	 */
	public void sortByOrderNumberDescending()
	{
		waitForSalesForceLoaded(1000);
		if(element.isElementPresent(SORT_ORDER_NUMBER_ASC)){
			wait.waitForElementDisplayed(SWITCH_SORT_ORDER_BUTTON);
			click.clickElement(SWITCH_SORT_ORDER_BUTTON);
		}
		waitForPageLoad();
		waitForSalesForceLoaded();
	}
}
