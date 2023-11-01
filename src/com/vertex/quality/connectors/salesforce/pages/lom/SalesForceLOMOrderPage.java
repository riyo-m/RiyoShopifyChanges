package com.vertex.quality.connectors.salesforce.pages.lom;

import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SalesForceLOMOrderPage extends SalesForceBasePage
{
	protected By FIRST_ORDER_RECORD = By.xpath(".//div[1]/div/div/ul/li[1]/a");
	protected By ORDER_NUMBER = By.xpath("//div[contains(@class, 'entityNameTitle') and text()='Order']/../slot/*[starts-with(text(), '000')]");
	protected By DETAILS_TAB = By.xpath(
		".//h1/div[text()='Order']/../../../../../../../../../../../../../..//ul/li/a[contains(@id, 'detailTab')]");
	protected By RECENTLY_VIEWED = By.xpath(".//div/div/div/div/div/div/div/div/span[@title='Recently Viewed']");

	protected By ACTIVATED_BUTTON = By.xpath(".//h1/div[text()='Order']/../../../../../../../../../../../../../..//li[@data-name='Activated']");
	protected By MARK_AS_CURRENT_STATUS = By.xpath(".//div[1]/div[2]/button/span[text()='Mark as Current Status']");

	protected By ADJUSTMENT_GROUP_NEW_BUTTON = By.xpath("//span[@title='Order Adjustment Groups']/../../../../../following-sibling::div/div/ul/li/a[@title='New']");
	protected By ADJUSTMENT_GROUP_SAVE_BUTTON = By.xpath("//button[@name='SaveEdit']");
	protected By ADJUSTMENT_GROUP_RELATED_LIST = By.xpath("//span[@title='Order Adjustment Groups']");
	protected By ADJUSTMENT_GROUP_NAME = By.xpath("//label[text()= 'Name']/following-sibling::div/input");
	protected By ADJUSTMENT_GROUP_TYPE_DROPDOWN = By.xpath(".//label[text()='Type']/following-sibling::div//button[@role='combobox']");
	protected By HEADER_DROPDOWN_OPTION = By.xpath(".//lightning-base-combobox-item/span/span[@title='Header']");

	protected By ORDER_PRODUCT_ITEM = By.xpath("//span[@title='Order Products']/../../../../../following-sibling::div//table/tbody/tr/th/div/a");
	protected By ADJUSTMENT_LINE_ITEM_NEW_BUTTON = By.xpath("//span[@title='Order Product Adjustment Line Items']/../../../../following-sibling::div/div/ul/li/a[@title='New']");
	protected By ADJUSTMENT_LINE_ITEM_AMOUNT = By.xpath("//input[@name='Amount']");
	protected By ADJUSTMENT_LINE_ITEM_GROUP = By.xpath(".//input[contains(@placeholder, 'Search Order Adjustment Groups')]");
	protected By ADJUSTMENT_LINE_ITEM_GROUP_SEARCH = By.xpath(".//div/lightning-base-combobox-item[@data-value='actionAdvancedSearch']");

	protected By ORDER_DROPDOWN = By.xpath("//a[@title='New Case']/../..//div[@class='uiPopupTrigger']");
	protected By DROPDOWN_ORDER_EDIT_BUTTON = By.xpath("//div[contains(@class, 'DropDownMenuList')]//a[@title='Edit']");
	protected By ORDER_PRODUCTS_LINK = By.xpath("//span[@title='Order Products']");
	protected By DISCOUNT_PERCENT_INPUT = By.xpath(
			"//div/label/span[contains(text(), 'DiscountPercent')]/../following-sibling::input");
	protected By DISCOUNT_AMOUNT_INPUT = By.xpath(
			"//div/label/span[contains(text(), 'DiscountAmount')]/../following-sibling::input");

	protected By ORDER_PRODUCT_TAX_CODE = By.xpath("//div[contains(@class, 'slds-form-element')]/div[contains(@class, 'test-id__field-label-container') and span[text()='Tax Codes']]//following-sibling::div//span[@class='uiOutputText']");
	protected By ORDER_PRODUCT_VERTEX_TAX_CODE = By.xpath("//div[contains(@class, 'slds-form-element')]/div[contains(@class, 'test-id__field-label-container') and span[text()='Vertex Tax Codes']]//following-sibling::div//span[@class='uiOutputText']");
	protected By ORDER_PRODUCT_INVOICE_TEXT_CODE = By.xpath("//div[contains(@class, 'slds-form-element')]/div[contains(@class, 'test-id__field-label-container') and span[text()='Invoice Text Codes']]//following-sibling::div//span[@class='uiOutputText']");
	protected By DETAILS_ORDER_LINK = By.xpath("//*/div/span[contains(text(),'Order')][@class='test-id__field-label']//following::div/span/div/a");


	SalesForceLOMPostLogInPage postLogInPage;

	public SalesForceLOMOrderPage( WebDriver driver )
	{
		super(driver);
		postLogInPage = new SalesForceLOMPostLogInPage(driver);
	}

	/**
	 * Navigate to first order in All Orders
	 */
	public void navigateToFirstOrder( )
	{
		refreshPage();
		waitForSalesForceLoaded();
		wait.waitForElementDisplayed(FIRST_ORDER_RECORD);
		click.javascriptClick(FIRST_ORDER_RECORD);
		waitForSalesForceLoaded();
	}

	/**
	 * Navigate to Details of order
	 */
	public void navigateToDetails( )
	{
		waitForSalesForceLoaded();
		wait.waitForElementDisplayed(RECENTLY_VIEWED);
		waitForSalesForceLoaded();
		wait.waitForElementDisplayed(DETAILS_TAB);
		waitForSalesForceLoaded();
		click.clickElement(DETAILS_TAB);
		waitForSalesForceLoaded();
	}

	/**
	 * Navigate to Related tab for specified record
	 *
	 * @param recordType
	 */
	public void navigateToRelated(String recordType)
	{
		String element = String.format(".//h1/div[text()='%s']/../../../../../../../../../.././/div/div/div/div/ul/li/a/span[text()='Related']", recordType);
		By relatedTab = By.xpath(element);
		waitForSalesForceLoaded();
		wait.waitForElementDisplayed(RECENTLY_VIEWED);
		waitForSalesForceLoaded();
		wait.waitForElementDisplayed(relatedTab);
		waitForSalesForceLoaded();
		click.clickElement(relatedTab);
		waitForSalesForceLoaded();
	}

	/**
	 * Get order number from details
	 *
	 * @return Order Number
	 */
	public String getOrderNumber( )
	{
		String orderNum;
		wait.waitForElementDisplayed(ORDER_NUMBER);
		orderNum = text.getElementText(ORDER_NUMBER);
		return orderNum;
	}

	/**
	 * Get estimated tax field with currency hardcoded to 'USD'
	 *
	 * @return estimated tax
	 */
	public String getEstimatedTax(){
		return getEstimatedTax("USD");
	}

	/**
	 * get Estimated Tax field with specific currency code
	 * @param isoCurrencyCode currency used for Order
	 *
	 * @return estimated Tax
	 */
	public String getEstimatedTax(String isoCurrencyCode)
	{
		String totalTaxLocator = String.format(".//span[normalize-space()='Vertex Tax Total']/../..//*[contains(text(), '%s')]", isoCurrencyCode);
		By TOTAL_TAX = By.xpath(totalTaxLocator);
		waitForSalesForceLoaded();
		int i = 0;
		while (!element.isElementPresent(TOTAL_TAX) && i < 5)
		{
			refreshPage();
			navigateToDetails();
			waitForSalesForceLoaded();
			i++;
		}
		wait.waitForElementEnabled(TOTAL_TAX);
		String tax = text.getElementText(TOTAL_TAX);
		i = 0;
		while(tax.startsWith("$0") && i < 5)
		{
			refreshPage();
			navigateToDetails();
			waitForSalesForceLoaded();
			tax = text.getElementText(TOTAL_TAX);
			i++;
		}
		return tax;
	}

	/**
	 * get Total with Tax field with currency hardcoded to 'USD'
	 *
	 * @return estimated Total Tax
	 */
	public String getTotalWithTax()
	{
		return getTotalWithTax("USD");
	}


	/**
	 * get Total with Tax field based on specified currency
	 * @param isoCurrencyCode currency used for order
	 *
	 * @return estimated Total Tax
	 */
	public String getTotalWithTax(String isoCurrencyCode)
	{
		String totalWithTaxLocator = String.format(".//span[normalize-space()='Total with Tax']/../..//*[contains(text(), '%s')]", isoCurrencyCode);
		By TOTAL_WITH_TAX = By.xpath(totalWithTaxLocator);
		waitForPageLoad();
		wait.waitForElementDisplayed(TOTAL_WITH_TAX);
		wait.waitForElementEnabled(TOTAL_WITH_TAX);
		return text.getElementText(TOTAL_WITH_TAX);
	}

	/**
	 * set order as Activated
	 */
	public void setOrderActivated( )
	{
		wait.waitForElementDisplayed(ACTIVATED_BUTTON);
		click.javascriptClick(ACTIVATED_BUTTON);
		waitForSalesForceLoaded();
		wait.waitForElementDisplayed(MARK_AS_CURRENT_STATUS);
		click.javascriptClick(MARK_AS_CURRENT_STATUS);
		waitForPageLoad();
		waitForSalesForceLoaded();
	}

	/**
	 * Click new order adjustment group button
	 */
	public void clickOrderAdjustmentGroupNewButton( )
	{
		scroll.scrollBottom();
		wait.waitForElementDisplayed(ADJUSTMENT_GROUP_NEW_BUTTON);
		click.javascriptClick(ADJUSTMENT_GROUP_NEW_BUTTON);
	}

	/**
	 * Set Order Adjustment Group or Adjustment Line Name
	 *
	 * @param name
	 */
	public void setName(String name)
	{
		wait.waitForElementDisplayed(ADJUSTMENT_GROUP_NAME);
		text.enterText(ADJUSTMENT_GROUP_NAME, name);
	}

	/**
	 * Set Order Discount Amount
	 *
	 * @param discountAmount
	 */
	public void setDiscountAmount(String discountAmount)
	{
		if (!discountAmount.equals(""))
		{
			wait.waitForElementDisplayed(DISCOUNT_AMOUNT_INPUT);
			text.enterText(DISCOUNT_AMOUNT_INPUT, discountAmount);
		}
	}

	/**
	 * Set Order Discount Percent
	 *
	 * @param discountPercent
	 */
	public void setDiscountPercent(String discountPercent)
	{
		if (!discountPercent.equals(""))
		{
			wait.waitForElementDisplayed(DISCOUNT_PERCENT_INPUT);
			text.enterText(DISCOUNT_PERCENT_INPUT, discountPercent);
		}
	}

	/**
	 * Sets order adjustment group type to Header
	 */
	public void setOrderAdjustmentGroupType( )
	{
		wait.waitForElementDisplayed(ADJUSTMENT_GROUP_TYPE_DROPDOWN);
		click.clickElement(ADJUSTMENT_GROUP_TYPE_DROPDOWN);
		wait.waitForElementDisplayed(HEADER_DROPDOWN_OPTION);
		click.clickElement(HEADER_DROPDOWN_OPTION);
	}

	/**
	 * Click Save button
	 */
	public void clickSaveButton()
	{
		wait.waitForElementDisplayed(ADJUSTMENT_GROUP_SAVE_BUTTON);
		click.clickElement(ADJUSTMENT_GROUP_SAVE_BUTTON);
		waitForPageLoad();
		waitForSalesForceLoaded();
	}

	/**
	 * Click edit button in order dropdown menu
	 */
	public void clickOrderDropdownButton()
	{
		wait.waitForElementDisplayed(ORDER_DROPDOWN);
		click.clickElement(ORDER_DROPDOWN);
		waitForPageLoad();
		waitForSalesForceLoaded();
	}

	/**
	 * Click edit button on order dropdown
	 */
	public void clickDropdownEditButton( )
	{
		waitForSalesForceLoaded();
		clickOrderDropdownButton();
		wait.waitForElementDisplayed(DROPDOWN_ORDER_EDIT_BUTTON);
		click.javascriptClick(DROPDOWN_ORDER_EDIT_BUTTON);
		waitForSalesForceLoaded();
	}

	/**
	 * Set Order Adjustment Group Info
	 * @param name
	 */
	public void createNewOrderAdjustmentGroup(String name)
	{
		clickOrderAdjustmentGroupNewButton();
		setName(name);
		setOrderAdjustmentGroupType();
		clickSaveButton();
	}

	/**
	 * Click Order Product link
	 */
	public void navigateToOrderProduct( )
	{
		scroll.scrollBottom();
		scroll.scrollElementIntoView(ORDER_PRODUCTS_LINK);
		wait.waitForElementDisplayed(ORDER_PRODUCT_ITEM);
		click.javascriptClick(ORDER_PRODUCT_ITEM);
	}

	/**
	 * Click Order Product link
	 * @param productName
	 */
	public void navigateToOrderProduct(String productName)
	{
		waitForSalesForceLoaded(3000);
		wait.waitForElementDisplayed(ORDER_PRODUCTS_LINK);
		scroll.scrollElementIntoView(ORDER_PRODUCTS_LINK);

		String locator = String.format("//span[@title='Order Products']/../../../../../following-sibling::div//table/tbody/tr/th/div/a[contains(text(), '%s')]",
				productName);
		By path = By.xpath(locator);
		wait.waitForElementDisplayed(path);
		click.javascriptClick(path);
	}

	/**
	 * Click New Button for Order Product Adjustment Line Item
	 */
	public void clickOrderProductAdjustmentLineItemNewButton( )
	{
		wait.waitForElementDisplayed(ADJUSTMENT_LINE_ITEM_NEW_BUTTON);
		click.clickElement(ADJUSTMENT_LINE_ITEM_NEW_BUTTON);
	}

	/**
	 * Set Order Product Adjustment Line Item Amount
	 * @param amount
	 */
	public void setAdjustmentLineItemAmount(String amount)
	{
		wait.waitForElementDisplayed(ADJUSTMENT_LINE_ITEM_AMOUNT);
		text.enterText(ADJUSTMENT_LINE_ITEM_AMOUNT, amount);
	}

	/**
	 * Set Order Product Adjustment Line Item Group
	 * @param adjustmentGroup
	 */
	public void setAdjustmentLineItemAdjustmentGroup(String adjustmentGroup)
	{
		wait.waitForElementDisplayed(ADJUSTMENT_LINE_ITEM_GROUP);
		text.enterText(ADJUSTMENT_LINE_ITEM_GROUP, adjustmentGroup);
		click.clickElement(ADJUSTMENT_LINE_ITEM_GROUP_SEARCH);
		String locator = String.format(".//tbody/tr/td/a[text()='%s']", adjustmentGroup);
		By adjustmentGroupSelection = By.xpath(locator);
		wait.waitForElementDisplayed(adjustmentGroupSelection);
		click.clickElement(adjustmentGroupSelection);
	}

	/**
	 * Create new order product adjustment at line item
	 * @param name
	 * @param amount
	 * @param adjustmentGroup
	 */
	public void createNewOrderProductAdjustmentLineItem(String name, String amount, String adjustmentGroup)
	{
		navigateToOrderProduct();
		navigateToRelated("Order Product");
		clickOrderProductAdjustmentLineItemNewButton();
		setName(name);
		setAdjustmentLineItemAmount(amount);
		setAdjustmentLineItemAdjustmentGroup(adjustmentGroup);
		clickSaveButton();
	}

	/**
	 * Gets the value for the product Tax Code on the Order product summary page
	 * */
	public String getProductTaxCode() {
		WebElement taxCode = wait.waitForElementDisplayed(ORDER_PRODUCT_TAX_CODE);
		return taxCode.getText();
	}

	/**
	 * Gets the value for the product Vertex Tax Code on the Order product summary page
	 * */
	public String getProductVertexTaxCode() {
		WebElement taxCode = wait.waitForElementDisplayed(ORDER_PRODUCT_VERTEX_TAX_CODE);
		return taxCode.getText();
	}

	/**
	 * Gets the value for the product Invoice Text Code on the Order product page
	 * */
	public String getProductInvoiceTextCode() {
		WebElement taxCode = wait.waitForElementDisplayed(ORDER_PRODUCT_INVOICE_TEXT_CODE);
		return taxCode.getText();
	}

	/**
	 * Navigates back to the Order associated with an Order Product Summary
	 * */
	public void navigateToOrder() {
		wait.waitForElementEnabled(DETAILS_ORDER_LINK);
		click.clickElementCarefully(DETAILS_ORDER_LINK);
	}
}
