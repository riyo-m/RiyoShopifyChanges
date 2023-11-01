package com.vertex.quality.connectors.dynamics365.finance.pages;

import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinanceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class DFinanceQuotationsPage extends DFinanceBasePage
{
	public DFinanceQuotationsPage( WebDriver driver )
	{
		super(driver);
	}

	protected By CUSTOMER_ACCOUNT = By.xpath("//div[@data-dyn-controlname='groupCustomerAccount']//input[@name='SalesQuotationTable_CustAccount']");
	protected By OK_BTN = By.cssSelector("[name$=OK]");
	protected By LINE_DETAILS = By.xpath("//div[@data-dyn-controlname='LineViewLineDetails']/div[1]");
	protected By SALES_TAX_GROUP = By.name("LineSalesTax_TaxGroup");
	protected By ITEM_SALES_TAX_GROUP = By.name("LineSalesTax_TaxItemGroup");
	protected By ADD_NEW_LINE = By.xpath("//button[contains(@data-dyn-controlname, 'salesQuotationLineNew')]");
	protected By ITEM_NUMBER = By.xpath("//input[@*='Item number']");
	protected By SITE = By.name("[name='InventoryDimensionsGrid_InventSiteId']");
	protected By WAREHOUSE = By.name("InventoryDimensionsGrid_InventLocationId");
	protected By UNIT_PRICE = By.xpath("//input[@*='Unit price']");
	protected By NEW_AMOUNT = By.name("SalesQuotationLine_LineAmount1");
	protected By TRANSACTION_TYPE = By.xpath("//input[@*='Transaction type']/..//div[@title='Open']");
	protected By SELECT_TYPE = By.xpath("//li[contains(text(),'Item')]");
	protected By SAVE_BUTTON = By.name("SystemDefinedSaveButton");
	protected By PROJ_CATEGORY = By.name("Group1_ProjCategoryId");
	protected By SALES_CATEGORY = By.name("SalesQuotationLine_SalesCategory_Name");
	protected By PROJ_ID = By.name("LineViewSalesQuoteTable_ProjIdRef");
	protected By SET_UP_TAB = By.xpath("//li[@data-dyn-controlname='TabLineSetup_header']/span");
	protected By DELIVERY_TAB = By.xpath("//li[@data-dyn-controlname='TabLineDelivery_header']/span");
	protected By SALES_TAX = By.name("TaxTransSource");
	protected By LINE_PROPERTY = By.name("Group1_ProjLinePropertyId");
	protected By SELECT_SITE = By.name("InventoryDimensions_InventSiteId");
	protected By TOTAL_CALCULATED_SALES_TAX_AMOUNT = By.name("TaxAmountCurTotal");
	Actions action = new Actions(driver);




	/**
	 * Enter "Customer account"
	 *
	 * @param customerAccount
	 */
	public void setCustomerAccount( String customerAccount )
	{
		wait.waitForElementEnabled(CUSTOMER_ACCOUNT);
		click.clickElementCarefully(CUSTOMER_ACCOUNT);
		text.setTextFieldCarefully(CUSTOMER_ACCOUNT, customerAccount);
		text.pressTab(CUSTOMER_ACCOUNT);
	}

	/**
	 * Click on "Ok" button to create quotation
	 *
	 * @return project quotation page
	 */
	public void clickOkBTN( )
	{
		wait.waitForElementDisplayed(OK_BTN);
		click.clickElement(OK_BTN);
		waitForPageLoad();
	}

	/**
	 * Click on Add New Line
	 */
	public void addNewLine( )
	{
		wait.waitForElementPresent(ADD_NEW_LINE);
		click.javascriptClick(ADD_NEW_LINE);
		jsWaiter.sleep(2000);
	}

	/**
	 * set sales order tax group
	 *
	 * @param group to type in sales group element
	 */
	public void setSalesItemTaxGroup( String group )
	{
		String isExpanded = attribute.getElementAttribute(LINE_DETAILS, "aria-expanded");

		if ( !isExpanded.equalsIgnoreCase("true") )
		{
			click.clickElementCarefully(LINE_DETAILS);
		}
		wait.waitForElementDisplayed(SET_UP_TAB);
		click.clickElementCarefully(SET_UP_TAB);
		wait.waitForElementDisplayed(ITEM_SALES_TAX_GROUP);
		text.setTextFieldCarefully(ITEM_SALES_TAX_GROUP, group);
	}

	/**
	 * Select site
	 */
	public void setSite(String site )
	{
		wait.waitForElementEnabled(DELIVERY_TAB);
		click.clickElement(DELIVERY_TAB);
		wait.waitForElementDisplayed(SELECT_SITE);
		text.setTextFieldCarefully(SELECT_SITE, site);
		text.pressEnter(SELECT_SITE);
		waitForPageLoad();
	}

	/**
	 * Click on "Sales Tax button"
	 */
	public void clickSalesTaxButton( )
	{
		wait.waitForElementEnabled(SALES_TAX);
		click.clickElement(SALES_TAX);
		waitForPageLoad();
	}

	/**
	 * Get calculated sales tax amount
	 * @return
	 */

	public String getCalculatedSalesTaxAmount( )
	{
		wait.waitForElementEnabled(TOTAL_CALCULATED_SALES_TAX_AMOUNT);
		String calculatedSalesTaxAmount = attribute.getElementAttribute(TOTAL_CALCULATED_SALES_TAX_AMOUNT, "value");
		return calculatedSalesTaxAmount;
	}

	/**
	 * Set Sales Tax Group
	 *
	 * @param salesTaxGroup
	 */
	public void setSalesTaxGroup( String salesTaxGroup )
	{
		wait.waitForElementEnabled(SALES_TAX_GROUP);
		text.enterText(SALES_TAX_GROUP, salesTaxGroup);
	}

	/**
	 * Click on Save button from sales tax groups page
	 */
	public void clickOnSaveButton( )
	{
		click.clickElement(SAVE_BUTTON);
		waitForPageLoad();
	}

	/**
	 * Enter PROJECT id
	 */
	public void enterProjID(String projIDNO )
	{
		wait.waitForElementDisplayed(PROJ_ID);
		click.clickElement(PROJ_ID);
		text.enterText(PROJ_ID, projIDNO);
	}

	/**
	 * Set item number
	 */
	public void setItemNO( String item)
	{
		wait.waitForElementEnabled(ITEM_NUMBER);
		click.clickElementCarefully(ITEM_NUMBER);
		text.setTextFieldCarefully(ITEM_NUMBER, item);
	}

	/**
	 * Set price
	 */
	public void setUnitPrice( String price)
	{
		WebElement UNIT_PRICE_ELE = wait.waitForElementPresent(UNIT_PRICE);
		action.click(UNIT_PRICE_ELE).perform();
		text.setTextFieldCarefully(UNIT_PRICE_ELE, price,false);
	}

	/**
	 * Set Transaction type
	 */
	public void setTransactionType( )
	{
		wait.waitForElementEnabled(TRANSACTION_TYPE);
		click.clickElementCarefully(TRANSACTION_TYPE);
		if (!element.isElementDisplayed(SELECT_TYPE))
			click.javascriptClick(TRANSACTION_TYPE);
		click.clickElementCarefully(SELECT_TYPE);
		text.pressTab(TRANSACTION_TYPE);
	}
}