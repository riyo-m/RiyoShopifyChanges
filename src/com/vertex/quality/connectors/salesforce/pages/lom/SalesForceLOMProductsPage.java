package com.vertex.quality.connectors.salesforce.pages.lom;

import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

/**
 * Common functions for anything related to Salesforce Basic Products Page.
 *
 * @author
 */
public class SalesForceLOMProductsPage extends SalesForceBasePage
{
	protected By BUTTON_NEW = By.xpath(".//ul[contains(@class, 'branding-actions')]/li/a/div[@title = 'New']");
	protected By PRODUCT_NAME = By.xpath(".//span[text()='Product Name']/../../input");
	protected By PRODUCT_CODE = By.xpath(".//span[text()='Product Code']/../../input");
	protected By PRODUCT_CLASS = By.xpath(".//span[text()='VTX_ProductClass']/../../input");
	protected By PRODUCT_DESCRIPTION = By.xpath(".//span[text()='Product Description']/../../textarea");
	protected By ACTIVE_CHECKBOX = By.xpath(".//span[text()='Active']/../../input");

	protected By USAGE_CLASS = By.xpath(".//span[text()='VTX_UsageClass']/../../input");
	protected By USAGE_CODE = By.xpath(".//span[text()='VTX_UsageCode']/../../input");
	protected By COMMODITY_CODE = By.xpath(".//span[text()='VTX_CommCode']/../../input");

	protected By PROD_SAVE_BUTTON = By.xpath(".//button[@title='Save']");
	protected By ADD_BUTTON = By.cssSelector("input[name='add']");
	protected By EDIT_BUTTON = By.cssSelector("#topButtonRow input[name='edit']");
	protected By ADD_TO_PRICE_BOOK_BUTTON = By.cssSelector("input[title='Add to Price Book']");

	protected By DELETE_BUTTON = By.cssSelector("#topButtonRow input[value='Delete']");

	protected By CREATED_PRODUCT_NAME = By.id("Name_ileinner");
	protected By CREATED_PRODUCT_CODE = By.id("ProductCode_ileinner");
	protected By CREATED_PRODUCT_CLASS = By.id("00N6A0000097vq9_ileinner");

	protected By TAB_RELATED = By.xpath(
		".//h1/div[text()='Product']/../../../../../../../../../..//div/ul/li/a/span[text()='Related']");
	protected By BUTTON_ADD_STANDARD = By.xpath("//ul/li/a/div[@title='Add Standard Price']");
	protected By LIST_PRICE = By.xpath(".//span[text()='List Price']/../../input");

	protected By DROPDOWN_PRODUCT_LIST = By.xpath(
		".//force-highlights-icon/div/img[@title='Products']/../../../../..//li[@class='oneActionsDropDown']");
	protected By DROPDOWN_PRODUCT_NEW = By.xpath(".//div/ul/li/a[@title='New']");

	protected By PRODUCT_ID = By.xpath(".//span[text()='Product Id']/../..//span/span[@class='uiOutputTextArea']");

	protected By DROPDOWN_PRODUCT_OPTIONS = By.xpath(
		".//h1/div[text()='Product']/../../../..//li[contains(@class,'oneActionsDropDown')]");
	protected By DROPDOWN_PRODUCT_DELETE = By.xpath(".//div/ul/li/a[@title='Delete']");

	protected By BUTTON_DELETE = By.xpath(
		".//body/div[4]/div[2]/div/div[2]/div/div[3]/div/button[2]/span[text()='Delete']");
	protected By SELECT_LIST_VIEW = By.xpath(".//*[contains(@title, 'Select a List View')]");
	protected By ALL_PRODUCTS_VIEW = By.xpath(".//div/div/ul/li/a/span[text()='All Products']");

	protected By SEARCH_LIST_TEXTBOX = By.xpath(".//input[@name='Product2-search-input']");

	SalesForceLOMPostLogInPage postLogInPage = new SalesForceLOMPostLogInPage(driver);

	public SalesForceLOMProductsPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * change view to see all accounts
	 */
	public void switchToViewAllProducts( )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(SELECT_LIST_VIEW);
		click.clickElement(SELECT_LIST_VIEW);

		wait.waitForElementDisplayed(ALL_PRODUCTS_VIEW);
		click.clickElement(ALL_PRODUCTS_VIEW);
	}

	/**
	 * set search window to search string
	 *
	 * @param stringToSearch
	 */
	public void searchFor( String stringToSearch )
	{
		wait.waitForElementDisplayed(SEARCH_LIST_TEXTBOX);
		text.clearText(SEARCH_LIST_TEXTBOX);
		text.enterText(SEARCH_LIST_TEXTBOX, stringToSearch + Keys.ENTER);
	}

	/**
	 * select existing product from list
	 *
	 * @param productName
	 */
	public void selectExistingProduct( String productName )
	{
		waitForSalesForceLoaded();
		waitForPageLoad();
		String selectAccount = String.format(".//div/ul/li//a/div[1]/span[1]/span[text()=\"%s\"]",
				productName);
		wait.waitForElementDisplayed(By.xpath(selectAccount));
		click.clickElement(By.xpath(selectAccount));
		waitForSalesForceLoaded();
	}

	/**
	 * Click To Create New product
	 */
	public void clickNewButton( )
	{
		try
		{
			wait.waitForElementDisplayed(DROPDOWN_PRODUCT_LIST);
			click.clickElement(DROPDOWN_PRODUCT_LIST);
			wait.waitForElementDisplayed(DROPDOWN_PRODUCT_NEW);
			click.javascriptClick(DROPDOWN_PRODUCT_NEW);
		}
		catch ( Exception e )
		{
			wait.waitForElementDisplayed(BUTTON_NEW);
			click.clickElement(BUTTON_NEW);
		}
		waitForSalesForceLoaded();
	}

	/**
	 * To Set Product Name
	 *
	 * @param productName
	 */
	private void setProductName( String productName )
	{
		wait.waitForElementDisplayed(PRODUCT_NAME);
		text.enterText(PRODUCT_NAME, productName);
	}

	/**
	 * To set Product Code
	 *
	 * @param productCode
	 */
	private void setProductCode( String productCode )
	{
		wait.waitForElementDisplayed(PRODUCT_CODE);
		text.enterText(PRODUCT_CODE, productCode);
	}

	/**
	 * To Set Product Class
	 *
	 * @param productClass
	 */
	private void setProductClass( String productClass )
	{
		wait.waitForElementDisplayed(PRODUCT_CLASS);
		text.enterText(PRODUCT_CLASS, productClass);
	}

	/**
	 * To Set Commodity Code
	 *
	 * @param commodityCode
	 */
	private void setCommodityCode( String commodityCode )
	{
		wait.waitForElementDisplayed(COMMODITY_CODE);
		text.enterText(COMMODITY_CODE, commodityCode);
	}

	/**
	 * To Set Usage Code
	 *
	 * @param usageCode
	 */
	private void setUsageCode( String usageCode )
	{
		wait.waitForElementDisplayed(USAGE_CODE);
		text.enterText(USAGE_CODE, usageCode);
	}

	/**
	 * To Set Usage Class
	 *
	 * @param usageClass
	 */
	private void setUsageClass( String usageClass )
	{
		wait.waitForElementDisplayed(USAGE_CLASS);
		text.enterText(USAGE_CLASS, usageClass);
	}

	/**
	 * To set Product Description
	 *
	 * @param productDescription
	 */
	private void setProductDescription( String productDescription )
	{
		wait.waitForElementDisplayed(PRODUCT_DESCRIPTION);
		text.enterText(PRODUCT_DESCRIPTION, productDescription);
	}

	private void setProductListPrice( String price )
	{

		wait.waitForElementDisplayed(LIST_PRICE);
		text.enterText(LIST_PRICE, price);
	}

	/**
	 * To Check/uncheck Active Checkbox
	 *
	 * @param check
	 */
	public void setActiveCheckbox( boolean check )
	{
		boolean ischecked = checkbox.isCheckboxChecked(ACTIVE_CHECKBOX);
		if ( !check == ischecked )
		{
			click.clickElement(ACTIVE_CHECKBOX);
		}
	}

	/**
	 * Click on Save Button on product page
	 */
	public void clickProductSaveButton( )
	{
		click.clickElement(PROD_SAVE_BUTTON);
		waitForPageLoad();
	}

	/**
	 * Click on Add button
	 */
	public void clickAddButton( )
	{
		click.clickElement(ADD_BUTTON);
		waitForPageLoad();
	}

	/**
	 * Click on Delete button
	 */
	public void clickDeleteButton( )
	{
		wait.waitForElementDisplayed(DELETE_BUTTON);
		click.clickElement(DELETE_BUTTON);
		alert.acceptAlert(DEFAULT_TIMEOUT);
		waitForSalesForceLoaded();
	}

	/**
	 * To get Product Name
	 *
	 * @return
	 */
	public String getProductName( )
	{
		String prodName = attribute.getElementAttribute(CREATED_PRODUCT_NAME, "textContent");
		return prodName;
	}

	/**
	 * To get Product class
	 *
	 * @return
	 */
	public String getProductClass( )
	{
		String prodClass = attribute.getElementAttribute(CREATED_PRODUCT_CLASS, "textContent");
		return prodClass;
	}

	/**
	 * To get Product Id
	 *
	 * @return
	 */
	public String getProductId( )
	{
		waitForSalesForceLoaded();
		wait.waitForElementDisplayed(PRODUCT_ID);
		return attribute.getElementAttribute(PRODUCT_ID, "textContent");
	}

	/**
	 * getExistingProductId
	 * @param productName
	 * @return
	 */
	public String getExistingProductId(String productName)
	{
		postLogInPage.closeOpenTabs();
		postLogInPage.switchToSubAppMenu("Products");
		switchToViewAllProducts();
		try{
			searchFor(productName);
			selectExistingProduct(productName);
		} catch (Exception e) {
			refreshPage();
			waitForPageLoad();
			searchFor(productName);
			selectExistingProduct(productName);
		}
		waitForSalesForceLoaded();

		return getProductId();
	}

	/**
	 * To create new product which will be added to Opportunity to calculate taxes
	 *
	 * @param productName
	 * @param productCode
	 * @param productClass       * @param commodityCode
	 * @param usageClass
	 * @param usageCode
	 * @param productDescription
	 * @param productDescription
	 */
	public void CreateProduct( String productName, String productCode, String productClass, String commodityCode,
		String usageClass, String usageCode, String productDescription )
	{
		postLogInPage.switchToSubAppMenu("Products");
		clickNewButton();
		setProductName(productName);
		setProductCode(productCode);
		setProductClass(productClass);
		setCommodityCode(commodityCode);
		setUsageClass(usageClass);
		setUsageCode(usageCode);
		setProductDescription(productDescription);
		setActiveCheckbox(true);
		clickProductSaveButton();
	}

	/**
	 * Click on Edit button
	 */
	public void clickEditButton( )
	{
		click.clickElement(EDIT_BUTTON);
		waitForPageLoad();
	}

	/**
	 * Add standard price to pricebook
	 *
	 * @param price
	 */
	public void addToStandardPriceBook( String price )
	{
		waitForSalesForceLoaded();
		wait.waitForElementDisplayed(TAB_RELATED);
		click.clickElement(TAB_RELATED);
		waitForPageLoad();
		wait.waitForElementDisplayed(BUTTON_ADD_STANDARD);
		click.clickElement(BUTTON_ADD_STANDARD);
		waitForPageLoad();
		setProductListPrice(price);
		clickProductSaveButton();
	}

	/**
	 * click on delete product and accept prompt
	 */
	public void deleteProduct( )
	{
		waitForSalesForceLoaded();

		wait.waitForElementDisplayed(DROPDOWN_PRODUCT_OPTIONS);
		click.clickElement(DROPDOWN_PRODUCT_OPTIONS);
		wait.waitForElementDisplayed(DROPDOWN_PRODUCT_DELETE);
		click.javascriptClick(DROPDOWN_PRODUCT_DELETE);

		wait.waitForElementDisplayed(BUTTON_DELETE);
		click.clickElement(BUTTON_DELETE);
	}
}