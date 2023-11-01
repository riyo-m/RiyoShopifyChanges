package com.vertex.quality.connectors.dynamics365.finance.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinanceBasePage;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * Flex field page common methods and object declaration page
 *
 * @author Sgupta
 */

public class DFinanceFlexFieldsPage extends DFinanceBasePage
{
	protected By SALES_ORDER_SOURCE_TABLE = By.xpath("//li[@data-dyn-controlname='SalesOrder_header']");
	protected By SELECT_SOURCE_TABLE = By.xpath("//input[@value='Product attributes']");
	protected By SELECT_SALES_ORDER = By.xpath("//li[contains(text(),'Sales orders')]");
	protected By SOURCE_FIELD = By.cssSelector("input[id*='VTXFlexFields_SOString_SourceField']");
	protected By SELECT_SOURCE_FIELD = By.xpath("//input[@value='Auction Start Price']");
	protected By POPUP_MESSAGE = By.cssSelector("[class*='notificationPopup-message']");
	protected By SAVE_BUTTON = By.cssSelector("[class*='button-commandRing Save-symbol']");
	protected By DATE_FIELD = By.xpath("//div[@data-dyn-controlname='DateFields_SalesOrder']/div/button");
	protected By DATE_SOURCE_FIELD = By.cssSelector("input[id*='VTXFlexFields_SODate_SourceField']");
	protected By SELECT_DATE_FIELD = By.xpath("//input[@title='Created date and time']");
	protected By RETAIL_TRANSACTION = By.xpath("//li[@data-dyn-controlname='RetailTransaction_header']");
	protected By STRING_TAB = By.xpath("//div[@data-dyn-controlname='StringFields_RetailTransaction']/div");
	protected By STRING_ALL_Flex = By.xpath("//div[contains(@id,\"RetailTransactionStringFields\")]//div[contains(@id,'header')]/div[text()='Flex field Id']");
	protected By DATE_TAB = By.xpath("//div[@data-dyn-controlname='DateFields_RetailTransaction']/div[@tabindex='0']");
	protected By DATE_ALL_FLEX = By.xpath("//div[contains(@id,\"RetailTransactionDateFields\")]//div[contains(@id,'header')]/div[text()='Flex field Id']");
	protected By NUMERIC_TAB = By.xpath("//div[@data-dyn-controlname='NumericFields_RetailTransaction']/div[@tabindex='0']");
	protected By NUMERIC_ALL_FLEX = By.xpath("//div[contains(@id,\"RetailTransactionNumericFields\")]//div[contains(@id,'header')]/div[text()='Flex field Id']/..");
	protected By FILTER_INPUT = By.xpath("//input[contains(@id,'FilterField_VTXFlexFields_RTString_FieldId_FieldId_Input')]");
	protected By DATE_FILTER_INPUT = By.xpath("//input[contains(@id,'FilterField_VTXFlexFields_RTDate_FieldId_FieldId_Input')]");
	protected By NUMERIC_FILTER_INPUT = By.xpath("//input[contains(@id,'FilterField_VTXFlexFields_RTNumeric_FieldId_FieldId_Input')]");
	protected By APPLY = By.xpath("//button[contains(@id,'VTXFlexFields_RTString_FieldId_ApplyFilters')]");
	Actions action = new Actions(driver);
	public DFinanceFlexFieldsPage( WebDriver driver )
	{
		super(driver);
	}

	/**
		* Click On Sales source table to select a new value
	 */

	public void selectSalesOrderSourceTable ()
	{
	wait.waitForElementDisplayed(SALES_ORDER_SOURCE_TABLE);
	click.clickElement(SALES_ORDER_SOURCE_TABLE);
	wait.waitForElementEnabled(SELECT_SOURCE_TABLE);
	click.clickElement(SELECT_SOURCE_TABLE);
	}

	public void selectSalesOrderFromSourceTable ()
	{
		wait.waitForElementDisplayed(SALES_ORDER_SOURCE_TABLE);
		click.clickElement(SALES_ORDER_SOURCE_TABLE);
		wait.waitForElementDisplayed(SELECT_SOURCE_TABLE);
		click.clickElement(SELECT_SOURCE_TABLE);
	}

	/**
	 * Click On Source field to select a new value
	 */

	public void selectSourceField ()
	{
		wait.waitForElementDisplayed(SOURCE_FIELD);
		click.moveToElementAndClick(SOURCE_FIELD);
		WebElement popUp = wait.waitForElementPresent(POPUP_MESSAGE);
		if (popUp.isDisplayed())
		{
			System.out.println("This Pop Up Message Should not be Displayed");
		}
		else {
		click.clickElement(SELECT_SOURCE_FIELD); }
	}

	/**
	 * Click On Save button to save the selected values
	 */

	public void clickSaveButton ()
	{
		wait.waitForElementPresent(SAVE_BUTTON);
		click.clickElement(SAVE_BUTTON);
	}

	/**
	 * Select Retail Transactions from the left Menu
	 */

	public void selectRetail ()
	{
		wait.waitForElementPresent(RETAIL_TRANSACTION);
		click.clickElement(RETAIL_TRANSACTION);
	}

	/**
	 * Click On Date Fields to expand
	 */

	public void expandDateFieldSection( )
	{
		expandHeader(DATE_FIELD);
	}

	/**
	 * Select the Created Date and Time value from Source table
	 */

	public void clickDateFields ()
	{
		wait.waitForElementPresent(DATE_SOURCE_FIELD);
		click.clickElementIgnoreExceptionAndRetry(DATE_SOURCE_FIELD);
		wait.waitForElementPresent(SELECT_DATE_FIELD);
		click.clickElement(SELECT_DATE_FIELD);
		clickSaveButton();
	}

	/**
	 * Check if the String field tab is expanded
	 */

	public void clickStringFlexTab ()
	{
		wait.waitForElementPresent(STRING_TAB);
		String isExpanded = attribute.getElementAttribute(STRING_TAB, "aria-expanded");

		if ( !isExpanded.equalsIgnoreCase("true") )
		{
			click.clickElementCarefully(STRING_TAB);
		}
	}

	/**
	 * Validate all the String flex field are loaded
	 */

	public void validateStringFlexFields ()
	{
		WebElement FILTER_ELE = wait.waitForElementPresent(STRING_ALL_Flex);
		action.moveToElement(FILTER_ELE).click(FILTER_ELE).perform();
		text.enterText(FILTER_INPUT,"25");
		click.clickElementCarefully(APPLY_FILTER);
		wait.waitForElementDisplayed(LAST_FLEX_FIELD);
		String isSTLoaded = attribute.getElementAttribute(LAST_FLEX_FIELD, "title");
		if ( isSTLoaded.contains("25") )
		{
			VertexLogger.log("All the String Flex Fields Are Loaded ");
		} else
			VertexLogger.log("String Flex Fields Can Not Be Loaded ");

	}

	/**
	 * Check if the Date field tab is expanded
	 * @param dateFieldFlexType
	 */

	public void clickDateFlexTab (String dateFieldFlexType)
	{
		wait.waitForElementPresent(By.xpath("//div[@data-dyn-controlname='DateFields_"+dateFieldFlexType+"']/div[@tabindex='0']//div[@class='section-page-arrow']"));
		String isExpanded = attribute.getElementAttribute(By.xpath("//div[@data-dyn-controlname='DateFields_"+dateFieldFlexType+"']/div[@tabindex='0']//div[@class='section-page-arrow']/../../div[1]"), "aria-expanded");

		if ( !isExpanded.equalsIgnoreCase("true") )
		{
			click.clickElementCarefully(By.xpath("//div[@data-dyn-controlname='DateFields_"+dateFieldFlexType+"']/div[@tabindex='0']//div[@class='section-page-arrow']"));
		}
	}

	/**
	 * Validate all the Date flex field are loaded
	 */

	public void validateDateFlexFields ()
	{
		WebElement FILTER_ELE = wait.waitForElementEnabled(DATE_ALL_FLEX);
		action.moveToElement(FILTER_ELE).click(FILTER_ELE).perform();
		text.enterText(DATE_FILTER_INPUT,"5");
		click.javascriptClick(APPLY_DATE_FILTER);
		wait.waitForElementDisplayed(LAST_DATE_FIELD);
		String isDTLoaded = attribute.getElementAttribute(LAST_DATE_FIELD, "title");

		if ( isDTLoaded.contains("5") )
		{
			VertexLogger.log("All the Data Flex Fields Are Loaded ");
		} else
			VertexLogger.log("Data Flex Fields Can Not Be Loaded ");

	}

	/**
	 * Check if the Numeric field tab is expanded
	 */

	public void clickNumericFlexTab ()
	{
		wait.waitForElementPresent(NUMERIC_TAB);
		String isExpanded = attribute.getElementAttribute(NUMERIC_TAB, "aria-expanded");

		if ( !isExpanded.equalsIgnoreCase("true") )
		{
			click.clickElementCarefully(NUMERIC_TAB);
		}
	}

	/**
	 * Validate all the Numeric flex field are loaded
	 */

	public void validateNumericFlexFields ()
	{
		WebElement FILTER_ELE = wait.waitForElementEnabled(NUMERIC_ALL_FLEX);
		hover.hoverOverElement(FILTER_ELE);
		action.moveToElement(FILTER_ELE).click(FILTER_ELE).perform();
		text.enterText(NUMERIC_FILTER_INPUT,"10");
		click.javascriptClick(APPLY_NUM_FILTER);
		wait.waitForElementDisplayed(LAST_NUM_FIELD);
		String isLoaded = attribute.getElementAttribute(LAST_NUM_FIELD, "title");

		if ( isLoaded.contains("10") )
		{
			VertexLogger.log("All the Numeric Flex Fields Are Loaded ");
		} else
			VertexLogger.log("Numeric Flex Fields Can Not Be Loaded ");
	}

	/**
	 * Selects the Source field table and edits it's value
	 * @param sourceFieldType
	 * @param sourceFieldLength
	 */
	public void editSourceFieldValue(String sourceFieldType, int sourceFieldLength){
		String sourceField = String.format("(//input[contains(@id, 'VTXFlexFields_%s_SourceField')])[%s]", sourceFieldType, sourceFieldLength);
		WebElement sourceFieldList = wait.waitForElementDisplayed(By.xpath(sourceField));

		String output = DFinanceBaseTest.getCurrentDateSlashFormat();

		waitForPageLoad();
		click.clickElementCarefully(sourceFieldList);
		sourceFieldList.sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.DELETE));
		sourceFieldList.sendKeys(output);
	}

	/**
	 * Gets the Source Field value
	 * @param sourceFieldType
	 * @param sourceFieldLength
	 * @return sourceFieldValue
	 */
	public String getSourceFieldValue(String sourceFieldType, int sourceFieldLength){
		String sourceField = String.format("(//input[contains(@id, 'VTXFlexFields_%s_SourceField')])[%s]", sourceFieldType, sourceFieldLength);
		WebElement sourceFieldList = wait.waitForElementDisplayed(By.xpath(sourceField));

		String sourceFieldValue = attribute.getElementAttribute(sourceFieldList,"value");

		return sourceFieldValue;
	}
}
