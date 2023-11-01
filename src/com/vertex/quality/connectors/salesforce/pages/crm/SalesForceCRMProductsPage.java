package com.vertex.quality.connectors.salesforce.pages.crm;

import com.vertex.quality.common.utils.misc.VertexCurrencyUtils;
import com.vertex.quality.connectors.salesforce.enums.NavigateMenu;
import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.text.DecimalFormat;

/**
 * Common functions for anything related to Salesforce Basic Products Page.
 *
 * @author
 */
public class SalesForceCRMProductsPage extends SalesForceBasePage
{
	protected By PRODUCT_NAME = By.id("Name");
	protected By PRODUCT_CODE = By.id("ProductCode");
	protected By PRODUCT_CLASS = By.id("00N6A0000097vq9");
	protected By PRODUCT_DESCRIPTION = By.id("Description");
	protected By ACTIVE_CHECKBOX = By.id("IsActive");

	protected By USAGE_CLASS = By.id("00N3s00000PUqpD");
	protected By USAGE_CODE = By.id("00N3s00000PUqpI");
	protected By COMMODITY_CODE = By.id("00N6A00000NsEnC");

	protected By PROD_SAVE_BUTTON = By.cssSelector(".pbBottomButtons>table>tbody>tr>td>input[title='Save']");
	protected By PRICE_SAVE_BUTTON = By.cssSelector("input[title='Save']");
	protected By ADD_BUTTON = By.cssSelector("input[name='add']");
	protected By EDIT_BUTTON = By.cssSelector("#topButtonRow input[name='edit']");
	protected By ADD_TO_PRICE_BOOK_BUTTON = By.cssSelector("input[title='Add to Price Book']");

	protected By STANDARD_PRICE_INPUT = By.cssSelector("input[title= 'Unit Price']");
	protected By STANDARD_CHECKBOX = By.cssSelector("input[name= 'ids']");
	protected By SELECT_BUTTON = By.cssSelector("input[name= 'edit']");

	protected By LIST_PRICE_INPUT = By.cssSelector("input[title= 'List Price 1']");
	protected By DELETE_BUTTON = By.cssSelector("#topButtonRow input[value='Delete']");

	protected By CREATED_PRODUCT_NAME = By.id("Name_ileinner");
	protected By CREATED_PRODUCT_CODE = By.id("ProductCode_ileinner");
	protected By CREATED_PRODUCT_CLASS = By.id("00N6A0000097vq9_ileinner");

	protected By CREATED_COMMODITY_CODE = By.id("00N6A00000NsEnC_ileinner");
	protected By CREATED_USAGE_CLASS = By.id("00N3s00000PUqpD_ileinner");
	protected By CREATED_USAGE_CODE = By.id("00N3s00000PUqpI_ileinner");

	protected By STANDARD_PRICE = By.xpath(
		"//div[contains(@id, 'RelatedStandardPriceList_body')]//td/following-sibling::th");
	protected By STATUS_COLUMN = By.xpath(
		"//div[contains(@id, 'RelatedStandardPriceList')]//th/following-sibling::td[contains(@class,'boolean')]/img");
	protected By LIST_PRICE = By.xpath(
		"//div[contains(@id, 'RelatedPricebookEntryList_body')]//th/following-sibling::td[contains(@class,'Currency')]");
	protected By PRICE_BOOK_NAME = By.xpath("//div[contains(@id, 'RelatedPricebookEntryList_body')]//th/a");

	SalesForceCRMOpportunityPage opportunityPage = new SalesForceCRMOpportunityPage(driver);
	SalesForceCRMPostLogInPage postLogInPage = new SalesForceCRMPostLogInPage(driver);

	public SalesForceCRMProductsPage( WebDriver driver )
	{
		super(driver);
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
	 * To set Standard Price
	 *
	 * @param standardPrice
	 */
	public void setStandardPrice( String standardPrice )
	{
		wait.waitForElementDisplayed(STANDARD_PRICE_INPUT);
		text.enterText(STANDARD_PRICE_INPUT, standardPrice);
	}

	/**
	 * Click on Save button
	 */
	public void clickPriceSaveButton( )
	{
		click.clickElement(PRICE_SAVE_BUTTON);
		waitForPageLoad();
	}

	/**
	 * Click on Add To Price button
	 */
	public void clickAddToPriceBookButton( )
	{
		click.clickElement(ADD_TO_PRICE_BOOK_BUTTON);
		waitForPageLoad();
	}

	/**
	 * To Check/uncheck Standard Checkbox
	 *
	 * @param check
	 */
	public void setStandardCheckbox( boolean check )
	{
		boolean ischecked = checkbox.isCheckboxChecked(STANDARD_CHECKBOX);
		if ( !check == ischecked )
		{
			click.clickElement(STANDARD_CHECKBOX);
		}
	}

	/**
	 * Click on Select button
	 */
	public void clickSelectButton( )
	{
		click.clickElement(SELECT_BUTTON);
		waitForPageLoad();
	}

	/**
	 * To set List Price
	 *
	 * @param listPrice
	 */
	public void setListPrice( String listPrice )
	{
		wait.waitForElementDisplayed(LIST_PRICE_INPUT);
		text.enterText(LIST_PRICE_INPUT, listPrice);
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
	 * To get Product code
	 *
	 * @return
	 */
	public String getProductCode( )
	{
		String prodCode = attribute.getElementAttribute(CREATED_PRODUCT_CODE, "textContent");
		return prodCode;
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
	 * To get Usage code
	 *
	 * @return
	 */
	public String getUsageCode( )
	{
		String usageCode = attribute.getElementAttribute(CREATED_USAGE_CODE, "textContent");
		return usageCode;
	}

	/**
	 * To get Usage class
	 *
	 * @return
	 */
	public String getUsageClass( )
	{
		String usageClass = attribute.getElementAttribute(CREATED_USAGE_CLASS, "textContent");
		return usageClass;
	}

	/**
	 * To get Commodity code
	 *
	 * @return
	 */
	public String getCommodityCode( )
	{
		String commCode = attribute.getElementAttribute(CREATED_COMMODITY_CODE, "textContent");
		return commCode;
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
		postLogInPage.clickSalesPageTab(NavigateMenu.Sales.PRODUCTS_TAB.text);
		opportunityPage.clickOnNewButton();
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
	 * To get Standard price product added
	 *
	 * @return
	 */
	public String getStandardPrice( )
	{
		String standardPrice = text.getElementText(STANDARD_PRICE);
		double stdPrice = VertexCurrencyUtils.cleanseCurrencyString(standardPrice);
		DecimalFormat df = VertexCurrencyUtils.getTwoDigitDecimalFormatter();
		String strexpectedPrice = df.format(stdPrice);
		return strexpectedPrice;
	}

	/**
	 * To get status of product
	 *
	 * @return
	 */
	public String getActiveStatus( )
	{
		String status = attribute.getElementAttribute(STATUS_COLUMN, "title");
		return status;
	}

	/**
	 * To get List price of the product
	 *
	 * @return
	 */
	public String getListPrice( )
	{
		String listPrice = text.getElementText(LIST_PRICE);
		double lstPrice = VertexCurrencyUtils.cleanseCurrencyString(listPrice);
		DecimalFormat df = VertexCurrencyUtils.getTwoDigitDecimalFormatter();
		String strexpectedListPrice = df.format(lstPrice);
		return strexpectedListPrice;
	}

	/**
	 * To get price books Name
	 *
	 * @return
	 */
	public String getPriceBooks( )
	{
		String priceBookName = text.getElementText(PRICE_BOOK_NAME);
		return priceBookName;
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
	 * Select the Product name from most recently viewed items
	 */
	public void selectProductFromTable( String productName )
	{
		String productname = String.format("//a[text()='%s']", productName);
		click.clickElement(By.xpath(productname));
		waitForPageLoad();
	}

	/**
	 * get the Product name from most recently viewed items
	 */
	public boolean getProductFromTable( String productName )
	{
		String productname = String.format("//a[text()='%s']", productName);
		boolean isexists = element.isElementDisplayed(By.xpath(productname));
		return isexists;
	}
}