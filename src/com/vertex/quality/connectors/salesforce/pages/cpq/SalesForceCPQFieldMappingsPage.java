package com.vertex.quality.connectors.salesforce.pages.cpq;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Common functions for anything related to Salesforce Basic Field Mapping Page.
 *
 * @author
 */
public class SalesForceCPQFieldMappingsPage extends SalesForceBasePage
{
	protected By MAPPING_NAME = By.xpath(
		"//div[contains(@class,'default') and contains(@class,'show')]//input[contains(@id,'mappingName')]");
	protected By VERTEX_XML_MASSAGE = By.xpath(
		"//div[contains(@class,'default') and contains(@class,'show')]//select[contains(@id,'xmlMessage')]");
	protected By TRANSACTION_TYPE = By.xpath(
		"//div[contains(@class,'default') and contains(@class,'show')]//select[contains(@id,'transactionType')]");
	protected By TRANSACTION_OBJECT = By.xpath(
		"//div[contains(@class,'default') and contains(@class,'show')]//input[contains(@id,'parentObjName')]");

	protected By LINE_OBJECT = By.xpath(
		"//div[contains(@class,'default') and contains(@class,'show')]//select[contains(@id,'childObjPicklist')]");
	protected By TRANSACTION_DATE = By.xpath(
		"//div[contains(@class,'default') and contains(@class,'show')]//select[contains(@id,'transactionDate')]");
	protected By DOCUMENT_NUMBER = By.xpath(
		"//div[contains(@class,'default') and contains(@class,'show')]//select[contains(@id,'docNumber')]");
	protected By LINE_TAX_AMOUNT = By.xpath(
		"//div[contains(@class,'default') and contains(@class,'show')]//select[contains(@id,'lineTaxAmount')]");
	protected By EXTENDED_PRICE = By.xpath(
		"//div[contains(@class,'default') and contains(@class,'show')]//select[contains(@id,'extPrice')]");
	protected By QUANTITY = By.xpath(
		"//div[contains(@class,'default') and contains(@class,'show')]//select[contains(@id,'quantity')]");

	protected By SAVE_BUTTON = By.xpath(
		"//div[contains(@class,'default') and contains(@class,'show')]//input[@value='Save']");
	protected By CLONE_BUTTON = By.cssSelector("input[value='Clone']");
	protected By DELETE_BUTTON = By.xpath("//*[contains(@id,'fieldMappingsSectionSub')]/div/input[@value='Delete']");
	protected By CANCEL_BUTTON = By.cssSelector("input[value='Cancel']");

	protected By SAVE_ERROR_MESSAGE = By.id("saveErrorMessage-");

	protected By NEW_MAPPING_BUTTON = By.xpath(
		"//*[@role='tabpanel' and contains(@class, 'show')]//li[contains(@style,'block') and contains(@title,'New Mapping')]");

	public SalesForceCPQFieldMappingsPage( WebDriver driver )
	{
		super(driver);
	}

	public void clickFieldTabs( String tabName )
	{
		By tabLocator = By.xpath(String.format("//*[@id='fieldMappingsTabList']//li[@title='%s']", tabName));
		wait.waitForElementDisplayed(tabLocator);
		click.clickElement(tabLocator);
		waitForSalesForceLoaded();
	}

	public String getMappingName( )
	{
		wait.waitForElementDisplayed(MAPPING_NAME);
		String mapName = attribute.getElementAttribute(MAPPING_NAME, "value");
		return mapName;
	}

	public void DeleteMapping( String mappingField )
	{
		String opportunityField = String.format(
			"//*[@role='tabpanel' and contains(@class, 'show')]/*[contains(@id, 'fieldMappingsSectionSub')]//table/tbody/tr[td[*[contains(@id,'%s')]]]",
			mappingField);

		if ( element.isElementDisplayed(By.xpath(opportunityField)) )
		{
			String deleteButton = opportunityField + "//input[@type='submit' and @value='Delete']";
			click.clickElement(By.xpath(deleteButton));
			alert.acceptAlert(DEFAULT_TIMEOUT);
			waitForSalesForceLoaded();
			clickSaveButton();
		}
		else
		{
			VertexLogger.log("There is no mapping Field with label" + mappingField, getClass());
		}
	}

	public void clickSaveButton( )
	{
		wait.waitForElementDisplayed(SAVE_BUTTON);
		click.clickElement(SAVE_BUTTON);
		waitForSalesForceLoaded();
	}

	public void clickDeleteButton( )
	{
		wait.waitForElementDisplayed(DELETE_BUTTON);
		click.clickElement(DELETE_BUTTON);
		alert.acceptAlert(DEFAULT_TIMEOUT);
		waitForSalesForceLoaded();
	}

	public void clickNewMappingButton( )
	{
		wait.waitForElementDisplayed(NEW_MAPPING_BUTTON);
		click.clickElement(NEW_MAPPING_BUTTON);
		waitForSalesForceLoaded();
	}

	public void setMappingName( String mappingName )
	{
		text.enterText(MAPPING_NAME, mappingName);
	}

	public void selectVertexXmlMessage( String vertexXMLMessage )
	{
		dropdown.selectDropdownByDisplayName(VERTEX_XML_MASSAGE, vertexXMLMessage);
	}

	public void selectTransactionType( String transactionType )
	{
		dropdown.selectDropdownByDisplayName(TRANSACTION_TYPE, transactionType);
	}

	public void setTransactionObject( String transactionObject )
	{
		text.enterText(TRANSACTION_OBJECT, transactionObject);
		text.pressTab(TRANSACTION_OBJECT);
		waitForSalesForceLoaded();
	}

	public void selectLineObject( String lineObject )
	{
		waitForSalesForceLoaded();
		dropdown.selectDropdownByDisplayName(LINE_OBJECT, lineObject);
	}

	public void selectTransactionDate( String transactionDate )
	{
		dropdown.selectDropdownByDisplayName(TRANSACTION_DATE, transactionDate);
	}

	public void selectDocumentNumber( String documentNumber )
	{
		dropdown.selectDropdownByDisplayName(DOCUMENT_NUMBER, documentNumber);
	}

	public void selectLineTaxAmount( String lineTaxAmount )
	{
		waitForSalesForceLoaded();
		dropdown.selectDropdownByDisplayName(LINE_TAX_AMOUNT, lineTaxAmount);
	}

	public void selectExtendedPrice( String extendedPrice )
	{
		dropdown.selectDropdownByDisplayName(EXTENDED_PRICE, extendedPrice);
	}

	public void selectQuantity( String quantity )
	{
		dropdown.selectDropdownByDisplayName(QUANTITY, quantity);
	}

	public void createNewMapping( String mappingName, String vertexXMLMessage, String transactionType,
		String transactionObject, String lineObject, String transactionDate, String documentNumber,
		String lineTaxAmount, String extendedPrice, String quantity )
	{
		waitForSalesForceLoaded();
		setMappingName(mappingName);
		selectVertexXmlMessage(vertexXMLMessage);
		selectTransactionType(transactionType);
		setTransactionObject(transactionObject);
		selectLineObject(lineObject);
		selectTransactionDate(transactionDate);
		selectDocumentNumber(documentNumber);
		if ( lineTaxAmount != null )
		{
			selectLineTaxAmount(lineTaxAmount);
		}
		waitForSalesForceLoaded();
		selectExtendedPrice(extendedPrice);
		waitForSalesForceLoaded();
		selectQuantity(quantity);
		clickSaveButton();
		waitForSalesForceLoaded();
	}

	public boolean isNewTabExists( String tabName )
	{
		By tabLocator = By.xpath(String.format("//*[@id='fieldMappingsTabList']//li[@title='%s']", tabName));
		boolean isExists = element.isElementDisplayed(tabLocator);
		return isExists;
	}

	public boolean isSaveErrorMessageDisplayed( )
	{
		waitForSalesForceLoaded();
		wait.waitForElementDisplayed(driver.findElement(SAVE_ERROR_MESSAGE));
		boolean isExists = element.isElementDisplayed(SAVE_ERROR_MESSAGE);
		return isExists;
	}

	public String getSectionText( String sectionName )
	{
		waitForSalesForceLoaded();
		String sectionPath = String.format("//h3[contains(text(),'%s')]/following-sibling::div//span", sectionName);
		if(!element.isElementPresent(By.xpath(sectionPath)))
			sectionPath = String.format("//h3[contains(text(),'%s')]/following-sibling::div//div", sectionName);
		String sectionText = text.getElementText(By.xpath(sectionPath));
		return sectionText;
	}

	public String getSectionTextFail( String sectionName )
	{
		waitForSalesForceLoaded();
		String sectionPath = String.format("//h3[contains(text(),'%s')]/following-sibling::div//span", sectionName);
		if(!element.isElementPresent(By.xpath(sectionPath)))
			sectionPath = String.format("//h3[contains(text(),'%s')]/following-sibling::div//div", sectionName);
		String sectionText = text.getElementText(By.xpath(sectionPath));
		return sectionText;
	}
}
