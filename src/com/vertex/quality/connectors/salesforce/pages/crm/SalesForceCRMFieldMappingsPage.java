package com.vertex.quality.connectors.salesforce.pages.crm;

import com.vertex.quality.common.enums.PageScrollDestination;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.testng.Assert.assertEquals;

/**
 * Common functions for anything related to Salesforce Basic Field Mapping Page.
 *
 * @author
 */
public class SalesForceCRMFieldMappingsPage extends SalesForceBasePage
{
	protected By MAPPING_NAME_OLD = By.xpath(
		"//div[contains(@class,'default') and contains(@class,'show')]//input[contains(@id,'mappingName')]");
	protected By MAPPING_NAME = By.xpath(".//input[contains(@name,'MappingName')]");
	protected By VERTEX_XML_MESSAGE_OLD = By.xpath(
		"//div[contains(@class,'default') and contains(@class,'show')]//select[contains(@id,'xmlMessage')]");
	protected By VERTEX_XML_MESSAGE = By.xpath(".//button[contains(@name,'VertexXMLMessage')]");
	protected By TRANSACTION_TYPE_OLD = By.xpath(
		"//div[contains(@class,'default') and contains(@class,'show')]//select[contains(@id,'transactionType')]");
	protected By TRANSACTION_TYPE = By.xpath(".//button[contains(@name,'TransactionType')]");
	protected By TRANSACTION_OBJECT_OLD = By.xpath(
		"//div[contains(@class,'default') and contains(@class,'show')]//input[contains(@id,'parentObjName')]");
	protected By TRANSACTION_OBJECT = By.xpath(".//input[contains(@name,'TransactionObject')]");

	protected By LINE_TRANS_DETAIL_MAP = By.xpath(".//button[starts-with(@name,'TaxDetails')]");
	protected By LINE_OBJECT = By.xpath(".//button[starts-with(@name,'LineObject')]");
	protected By LINE_OBJECT_OLD = By.xpath(
		"//div[contains(@class,'default') and contains(@class,'show')]//select[contains(@id,'childObjPicklist')]");
	protected By TRANSACTION_DATE = By.xpath(".//button[contains(@name,'Transaction Date')]");
	protected By TRANSACTION_DATE_OLD = By.xpath(
		"//div[contains(@class,'default') and contains(@class,'show')]//select[contains(@id,'transactionDate')]");
	protected By DOCUMENT_NUMBER = By.xpath(".//button[contains(@name,'DocumentNumber')]");
	protected By DOCUMENT_NUMBER_OLD = By.xpath(
		"//div[contains(@class,'default') and contains(@class,'show')]//select[contains(@id,'docNumber')]");
	protected By LINE_TAX_AMOUNT = By.xpath(".//button[contains(@name,'LineTaxAmount')]");
	protected By LINE_TAX_AMOUNT_OLD = By.xpath(
		"//div[contains(@class,'default') and contains(@class,'show')]//select[contains(@id,'lineTaxAmount')]");
	protected By EXTENDED_PRICE = By.xpath(".//button[contains(@name,'ExtendedPrice')]");
	protected By EXTENDED_PRICE_OLD = By.xpath(
		"//div[contains(@class,'default') and contains(@class,'show')]//select[contains(@id,'extPrice')]");
	protected By QUANTITY = By.xpath(".//button[contains(@name,'Quantity')]");
	protected By QUANTITY_OLD = By.xpath(
		"//div[contains(@class,'default') and contains(@class,'show')]//select[contains(@id,'quantity')]");

	protected By SAVE_BUTTON = By.xpath(".//*[@data-id='saveButton']");
	protected By CLONE_BUTTON = By.cssSelector(".//*[@data-id='saveButton']/.././/*[text()='Clone']");
	protected By DELETE_BUTTON = By.xpath(".//*[@data-id='saveButton']/.././/*[text()='Delete']");
	protected By CONFIRM_DELETE = By.xpath(".//button[text()='OK']");
	protected By CANCEL_BUTTON = By.cssSelector("input[value='Cancel']");

	protected By SAVE_ERROR_MESSAGE = By.xpath(".//*[@data-id='saveErrorMessage-']");

	protected By NEW_MAPPING_BUTTON = By.xpath(
		".//*[contains(text(),'New Mapping') and not(ancestor::*[contains(@style,'display:none')]) and not(ancestor::*[contains(@style,'display: none')])]");

	protected By ADD_TRANS_LABEL_DROPDOWN = By.xpath(".//*[contains(@data-id,'transLabelPicklist')]");
	protected By ADD_TRANS_SOURCE_DROPDOWN = By.xpath(".//*[contains(@data-id,'transSrcPicklist')]");
	protected By ADD_TRANS_MAP_BUTTON = By.xpath(".//*[@value='transaction' and text()='Add']");
	protected By ADD_LINE_LABEL_DROPDOWN = By.xpath(".//*[contains(@data-id,'lineLabelPicklist')]");
	protected By ADD_LINE_SOURCE_DROPDOWN = By.xpath(".//*[contains(@data-id,'lineSrcPicklist')]");
	protected By ADD_LINE_MAP_BUTTON = By.xpath(".//*[@value='Line' and text()='Add']");

	public SalesForceCRMFieldMappingsPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * To click on tab for mapping
	 *
	 * @param tabName
	 */
	public void clickFieldTabs( String tabName )
	{
		By tabLocator = By.xpath(String.format(".//c-config-mapping/.//span[@title='%s']", tabName));
		wait.waitForElementDisplayed(tabLocator);
		click.clickElement(tabLocator);
		waitForSalesForceLoaded();
	}

	/**
	 * Get Map name
	 *
	 * @return mapName
	 */
	public String getMappingName( )
	{
		wait.waitForElementDisplayed(MAPPING_NAME);
		String mapName = attribute.getElementAttribute(MAPPING_NAME, "value");
		return mapName;
	}

	/**
	 * click save button
	 */
	public void clickSaveButton( )
	{
		wait.waitForElementDisplayed(SAVE_BUTTON);
		click.clickElement(SAVE_BUTTON);
		waitForSalesForceLoaded();
	}

	/**
	 * click delete button
	 */
	public void clickDeleteButton( )
	{
		waitForSalesForceLoaded();
		wait.waitForElementDisplayed(DELETE_BUTTON);
		click.clickElement(DELETE_BUTTON);
		wait.waitForElementDisplayed(CONFIRM_DELETE);
		click.clickElement(CONFIRM_DELETE);
		waitForSalesForceLoaded();
	}

	/**
	 * click new button
	 */
	public void clickNewMappingButton( )
	{
		wait.waitForElementDisplayed(NEW_MAPPING_BUTTON);
		click.clickElement(NEW_MAPPING_BUTTON);
		waitForSalesForceLoaded();
	}

	/**
	 * set Mapping name
	 *
	 * @param mappingName
	 */
	public void setMappingName( String mappingName )
	{
		text.enterText(MAPPING_NAME, mappingName);
	}

	/**
	 * select vertex message by xml
	 *
	 * @param vertexXMLMessage
	 */
	public void selectVertexXmlMessage( String vertexXMLMessage )
	{
		By uiElement = VERTEX_XML_MESSAGE;
		if ( !element.isElementPresent(uiElement) )
		{
			uiElement = VERTEX_XML_MESSAGE_OLD;
		}

		try
		{
			dropdown.selectDropdownByDisplayName(uiElement, vertexXMLMessage);
		}
		catch ( Exception ex )
		{
			click.clickElement(uiElement);
			String xpathSelector = String.format(".//*[@data-value='%s']", vertexXMLMessage);
			wait.waitForElementDisplayed(By.xpath(xpathSelector));
			click.javascriptClick(By.xpath(xpathSelector));
		}
	}

	/**
	 * select transaction type
	 *
	 * @param transactionType
	 */
	public void selectTransactionType( String transactionType )
	{
		By uiElement = TRANSACTION_TYPE;
		if ( !element.isElementPresent(uiElement) )
		{
			uiElement = TRANSACTION_TYPE_OLD;
		}
		try
		{
			dropdown.selectDropdownByDisplayName(uiElement, transactionType);
		}
		catch ( Exception ex )
		{
			click.clickElement(uiElement);
			String xpathSelector = String.format(".//*[@data-value='%s']", transactionType);
			wait.waitForElementDisplayed(By.xpath(xpathSelector));
			click.javascriptClick(By.xpath(xpathSelector));
		}
	}

	/**
	 * select transaction object
	 *
	 * @param transactionObject
	 */
	public void setTransactionObject( String transactionObject )
	{
		By uiElement = TRANSACTION_OBJECT;
		if ( !element.isElementPresent(uiElement) )
		{
			uiElement = TRANSACTION_OBJECT_OLD;
			text.enterText(uiElement, transactionObject);
			text.pressTab(uiElement);
			waitForSalesForceLoaded();
		}
		else
		{
			text.enterText(uiElement, transactionObject);
			text.pressTab(uiElement);
			String xpathSelector = String.format(".//div/ul/li/.//*[starts-with(text(),'%s')]", transactionObject);
			wait.waitForElementDisplayed(By.xpath(xpathSelector));
			click.javascriptClick(By.xpath(xpathSelector));
		}
	}

	/**
	 * select line object
	 *
	 * @param lineObject
	 */
	public void selectLineObject( String lineObject )
	{
		waitForSalesForceLoaded();
		By uiElement = LINE_OBJECT;
		if ( !element.isElementPresent(uiElement) )
		{
			uiElement = LINE_OBJECT_OLD;
		}
		try
		{
			dropdown.selectDropdownByDisplayName(uiElement, lineObject);
		}
		catch ( Exception ex )
		{
			click.clickElement(uiElement);
			String xpathSelector = String.format(".//*[starts-with(text(),'%s')]", lineObject);
			wait.waitForElementDisplayed(By.xpath(xpathSelector));
			click.javascriptClick(By.xpath(xpathSelector));
		}
	}

	/**
	 * select line object
	 *
	 * @param lineObjectTransMap
	 */
	public void selectLineObjectTransDetailMap( String lineObjectTransMap )
	{
		waitForSalesForceLoaded();
		By uiElement = LINE_TRANS_DETAIL_MAP;
		try
		{
			dropdown.selectDropdownByDisplayName(uiElement, lineObjectTransMap);
		}
		catch ( Exception ex )
		{
			click.clickElement(uiElement);
			waitForSalesForceLoaded();
			String xpathSelector = String.format(".//*[starts-with(text(),'%s')]", lineObjectTransMap);
			wait.waitForElementDisplayed(By.xpath(xpathSelector));
			click.javascriptClick(By.xpath(xpathSelector));
		}
	}


	/**
	 * select transaction date
	 *
	 * @param transactionDate
	 */
	public void selectTransactionDate( String transactionDate )
	{
		By uiElement = TRANSACTION_DATE;
		if ( !element.isElementPresent(uiElement) )
		{
			uiElement = TRANSACTION_DATE_OLD;
		}
		try
		{
			dropdown.selectDropdownByDisplayName(uiElement, transactionDate);
		}
		catch ( Exception ex )
		{
			click.clickElement(uiElement);
			String xpathSelector = String.format(
				".//button[contains(@name,'Transaction Date')]/../..//*[@role='option']/.//*[@title='%s']",
				transactionDate);
			wait.waitForElementDisplayed(By.xpath(xpathSelector));
			click.javascriptClick(By.xpath(xpathSelector));
		}
	}

	/**
	 * select document number
	 *
	 * @param documentNumber
	 */
	public void selectDocumentNumber( String documentNumber )
	{
		By uiElement = DOCUMENT_NUMBER;
		if ( !element.isElementPresent(uiElement) )
		{
			uiElement = DOCUMENT_NUMBER_OLD;
		}
		try
		{
			dropdown.selectDropdownByDisplayName(uiElement, documentNumber);
		}
		catch ( Exception ex )
		{
			click.clickElement(uiElement);
			String xpathSelector = String.format(
				".//button[contains(@name,'DocumentNumber')]/../..//*[@role='option']/.//*[@title='%s']",
				documentNumber);
			wait.waitForElementDisplayed(By.xpath(xpathSelector));
			click.javascriptClick(By.xpath(xpathSelector));
		}
	}

	/**
	 * select line tax amount
	 *
	 * @param lineTaxAmount
	 */
	public void selectLineTaxAmount( String lineTaxAmount )
	{
		waitForSalesForceLoaded();
		By uiElement = LINE_TAX_AMOUNT;
		if ( !element.isElementPresent(uiElement) )
		{
			uiElement = LINE_TAX_AMOUNT_OLD;
		}
		try
		{
			dropdown.selectDropdownByDisplayName(uiElement, lineTaxAmount);
		}
		catch ( Exception ex )
		{
			click.clickElement(uiElement);
			String xpathSelector = String.format(
				".//button[contains(@name,'LineTaxAmount')]/../..//*[@role='option']/.//*[@title='%s']", lineTaxAmount);
			wait.waitForElementDisplayed(By.xpath(xpathSelector));
			click.javascriptClick(By.xpath(xpathSelector));
		}
	}

	/**
	 * select extended price
	 *
	 * @param extendedPrice
	 */
	public void selectExtendedPrice( String extendedPrice )
	{
		By uiElement = EXTENDED_PRICE;
		if ( !element.isElementPresent(uiElement) )
		{
			uiElement = EXTENDED_PRICE_OLD;
		}
		try
		{
			dropdown.selectDropdownByDisplayName(uiElement, extendedPrice);
		}
		catch ( Exception ex )
		{
			click.clickElement(uiElement);
			String xpathSelector = String.format(
				".//button[contains(@name,'ExtendedPrice')]/../.././/*[@role='option']/.//*[@title='%s']",
				extendedPrice);
			wait.waitForElementDisplayed(By.xpath(xpathSelector));
			click.javascriptClick(By.xpath(xpathSelector));
		}
	}

	/**
	 * select quantity
	 *
	 * @param quantity
	 */
	public void selectQuantity( String quantity )
	{
		By uiElement = QUANTITY;
		if ( !element.isElementPresent(uiElement) )
		{
			uiElement = QUANTITY_OLD;
		}
		try
		{
			dropdown.selectDropdownByDisplayName(uiElement, quantity);
		}
		catch ( Exception ex )
		{
			click.clickElement(uiElement);
			String xpathSelector = String.format(
				".//button[contains(@name,'Quantity')]/../.././/*[@role='option']/.//*[@title='%s']", quantity);
			wait.waitForElementDisplayed(By.xpath(xpathSelector));
			click.javascriptClick(By.xpath(xpathSelector));
		}
	}

	/**
	 * create New Mapping
	 *
	 * @param mappingName
	 * @param vertexXMLMessage
	 * @param transactionType
	 * @param transactionObject
	 * @param lineObject
	 * @param transactionDate
	 * @param documentNumber
	 * @param lineTaxAmount
	 * @param extendedPrice
	 * @param quantity
	 */
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

	/**
	 * create New Mapping
	 *
	 * @param mappingName
	 * @param vertexXMLMessage
	 * @param transactionType
	 * @param transactionObject
	 * @param lineObject
	 * @param transactionDate
	 * @param documentNumber
	 * @param lineTaxAmount
	 * @param extendedPrice
	 * @param quantity
	 */
	public void updateMapping( String mappingName, String vertexXMLMessage, String transactionType,
								  String transactionObject, String lineObject, String transactionDate, String documentNumber,
								  String lineTaxAmount, String extendedPrice, String quantity )
	{
		waitForSalesForceLoaded();
		if(!tabExists(mappingName))
		{
			clickNewMappingButton();
			setMappingName(mappingName);
		}
		else
			selectMappingTab(mappingName);
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

	/**
	 * does tab exist
	 *
	 * @param tabName
	 */
	public boolean tabExists(String tabName )
	{
		By tabLocator = By.xpath(String.format(".//*[@title='%s']", tabName));
		boolean isExists = element.isElementDisplayed(tabLocator);
		return isExists;
	}

	/**
	 * isSaveErrorMessageDisplayed
	 *
	 * @return isExists
	 */
	public boolean isSaveErrorMessageDisplayed( )
	{
		waitForSalesForceLoaded();
		wait.waitForElementDisplayed(driver.findElement(SAVE_ERROR_MESSAGE));
		boolean isExists = element.isElementDisplayed(SAVE_ERROR_MESSAGE);
		return isExists;
	}

	/**
	 * getSectionText
	 *
	 * @param sectionName
	 *
	 * @return sectionText
	 */
	public String getSectionText( String sectionName )
	{
		waitForSalesForceLoaded();
		String sectionPath = String.format("//h3[contains(text(),'%s')]/following-sibling::div//span", sectionName);
		if(!element.isElementPresent(By.xpath(sectionPath)))
			sectionPath = String.format("//h3[contains(text(),'%s')]/following-sibling::div//div", sectionName);
		String sectionText = text.getElementText(By.xpath(sectionPath));
		return sectionText;
	}

	/**
	 * getSectionText
	 *
	 * @param sectionName
	 *
	 * @return sectionText
	 */
	public String getSectionTextFail( String sectionName )
	{
		waitForSalesForceLoaded();
		String sectionPath = String.format("//h3[contains(text(),'%s')]/following-sibling::div//div", sectionName);
		if(!element.isElementPresent(By.xpath(sectionPath)))
			sectionPath = String.format("//h3[contains(text(),'%s')]/following-sibling::div//span", sectionName);
		String sectionText = text.getElementText(By.xpath(sectionPath));
		return sectionText;
	}

	/**
	 * Select Tab by Name
	 *
	 * @param tabName
	 */
	public void selectMappingTab( String tabName )
	{
		waitForSalesForceLoaded();
		By tabLocator = By.xpath(String.format(".//*[contains(text(), 'Select a field mapping')]/..//div/lightning-tree-item/div/span/a/span[@title='%s']", tabName));
		click.clickElement(tabLocator);
		waitForSalesForceLoaded();
	}

	/**
	 * getCorrectMappingExists
	 *
	 * @param level
	 * @param label
	 * @param expectedMap
	 *
	 * @return correctMapExists
	 */
	public Boolean getCorrectMappingExists( String level, String label, String expectedMap )
	{
		waitForSalesForceLoaded();
		wait.waitForElementDisplayed(ADD_TRANS_LABEL_DROPDOWN);
		boolean correctMapExists = false;
		try
		{
			String currentMap = getMappingValue(level, label);
			if ( currentMap == expectedMap )
			{
				correctMapExists = true;
			}
			else
			{
				deleteMapping(level, label);
			}
		}
		catch ( Exception ex )
		{
			correctMapExists = false;
		}

		return correctMapExists;
	}

	/**
	 * getMappingValue
	 *
	 * @param label
	 * @param level
	 *
	 * @return mappedValue
	 */
	public String getMappingValue( String level, String label )
	{
		String sectionPath = String.format(
			".//*[contains(@data-id,'%sLabelPicklist')]/../../..//.//tr/td/p[contains(., '%s')]/../..//following-sibling::td/p",
			level, label);
		String mappedValue = text.getElementText(By.xpath(sectionPath));
		VertexLogger.log("Mapped Value: " + mappedValue);
		return mappedValue;
	}

	/**
	 * deleteMapping
	 *
	 * @param label
	 * @param level
	 */
	public void deleteMapping( String level, String label )
	{
		String sectionPath = String.format(
			".//*[contains(@data-id,'%sLabelPicklist')]/../../..//.//tr/td/p[contains(., '%s')]/../.././/*[contains(text(),'Delete')]",
			level, label);
		click.clickElement(By.xpath(sectionPath));
		waitForSalesForceLoaded();
	}

	/**
	 * Add Trans Mapping
	 * Remove mapping if already exists
	 *
	 * @param label
	 * @param sourceField
	 */
	public void addTransMapping( String label, String sourceField )
	{
		boolean mapAvailable = getTransLabelOptionExists(label);
		if ( !mapAvailable )
		{
			try {
				String fieldLineMap = getMappingValue("trans", label);
				if (fieldLineMap != sourceField) {
					//deleteMapping("trans", label);
					VertexLogger.log("Wrong Mapping Exists: label:" + label + "  fieldLineMap:" + fieldLineMap);
					return;
				} else {
					VertexLogger.log("Current Mapping Exists : label:" + label + "  fieldLineMap:" + fieldLineMap);
					return;
				}
			}
			catch(Exception ex){
				VertexLogger.log("unable to see Current Mapping for "+label);
				return;
			}
		}

		waitForSalesForceLoaded();
		setTransLabel(label);
		setTransSource(sourceField);
		clickTransAdd();
		waitForSalesForceLoaded();
		VertexLogger.log("Trans Mapped  : label:" + label + "  fieldLineMap:" + sourceField);
	}

	/**
	 * Add Line Mapping
	 * Remove mapping if already exists
	 *
	 * @param label
	 * @param sourceField
	 */
	public void addLineMapping( String label, String sourceField )
	{
		boolean mapAvailable = getLineLabelOptionExists(label);
		if ( !mapAvailable )
		{
			try {
				String fieldLineMap = getMappingValue("line", label);
				if (!fieldLineMap.startsWith(sourceField)) {
					//deleteMapping("line", label);
					VertexLogger.log("Wrong Mapping Exists: label:" + label + "  fieldLineMap:" + fieldLineMap);
					return;
				} else {
					VertexLogger.log("Current Mapping Exists : label:" + label + "  fieldLineMap:" + fieldLineMap);
					return;
				}
			}
			catch(Exception ex){
				VertexLogger.log("unable to see Current Mapping for " + label);
				return;
			}
		}

		waitForSalesForceLoaded();
		setLineLabel(label);
		setLineSource(sourceField);
		clickLineAdd();
		waitForSalesForceLoaded();
		VertexLogger.log("Line Mapped  : label:" + label + "  fieldLineMap:" + sourceField);
	}

	/**
	 * addTransMapping
	 *
	 * @param label
	 *
	 * @return boolean if option exists
	 */
	public Boolean getTransLabelOptionExists( String label )
	{
		wait.waitForElementDisplayed(ADD_TRANS_LABEL_DROPDOWN);
		click.clickElement(ADD_TRANS_LABEL_DROPDOWN);
		By uiElement = By.xpath(String.format(".//*[contains(@data-id,'transLabelPicklist')]/.//*[contains(@class, 'option')]/.//span[@title='%s']", label));
		click.clickElement(ADD_TRANS_LABEL_DROPDOWN);
		return element.isElementPresent(uiElement);
	}

	/**
	 * addLineMapping
	 *
	 * @param label
	 *
	 * @return boolean if option exists
	 */
	public Boolean getLineLabelOptionExists( String label )
	{
		wait.waitForElementDisplayed(ADD_LINE_LABEL_DROPDOWN);
		click.clickElement(ADD_LINE_LABEL_DROPDOWN);
		By uiElement = By.xpath(String.format(
			".//*[contains(@data-id,'lineLabelPicklist')]/.//*[contains(@class, 'option')]/.//span[@title='%s']",
			label));
		click.clickElement(ADD_LINE_LABEL_DROPDOWN);
		return element.isElementPresent(uiElement);
	}

	/**
	 * setTransLabel
	 *
	 * @param label
	 */
	public void setTransLabel( String label )
	{
		wait.waitForElementDisplayed(ADD_TRANS_LABEL_DROPDOWN);
		click.clickElement(ADD_TRANS_LABEL_DROPDOWN);
		By uiElement = By.xpath(String.format(
			".//*[contains(@data-id,'transLabelPicklist')]/.//*[contains(@class, 'option')]/.//span[@title='%s']",
			label));
		wait.waitForElementDisplayed(uiElement);
		click.javascriptClick(uiElement);
	}

	/**
	 * setTransSource
	 *
	 * @param source
	 */
	public void setTransSource( String source )
	{
		wait.waitForElementDisplayed(ADD_TRANS_SOURCE_DROPDOWN);
		click.clickElement(ADD_TRANS_SOURCE_DROPDOWN);
		By uiElement = By.xpath(String.format(
			".//*[contains(@data-id,'transSrcPicklist')]/.//*[contains(@class, 'option')]/.//span[@title='%s']",
			source));
		wait.waitForElementDisplayed(uiElement);
		click.javascriptClick(uiElement);
	}

	/**
	 * clickTransAdd
	 */
	public void clickTransAdd( )
	{
		wait.waitForElementDisplayed(ADD_TRANS_MAP_BUTTON);
		click.clickElement(ADD_TRANS_MAP_BUTTON);
		waitForSalesForceLoaded();
	}

	/**
	 * setLineLabel
	 *
	 * @param label
	 */
	public void setLineLabel( String label )
	{
		wait.waitForElementDisplayed(ADD_LINE_LABEL_DROPDOWN);
		click.clickElement(ADD_LINE_LABEL_DROPDOWN);
		By uiElement = By.xpath(String.format(
			".//*[contains(@data-id,'lineLabelPicklist')]/../.././/*[contains(@class, 'option')]/.//span[@title='%s']",
			label));
		wait.waitForElementDisplayed(uiElement);
		click.javascriptClick(uiElement);
	}

	/**
	 * setLineSource
	 *
	 * @param source
	 */
	public void setLineSource( String source )
	{
		wait.waitForElementDisplayed(ADD_LINE_SOURCE_DROPDOWN);
		click.clickElement(ADD_LINE_SOURCE_DROPDOWN);
		By uiElement = By.xpath(String.format(
			".//*[contains(@data-id,'lineSrcPicklist')]/.//*[contains(@class, 'option')]/.//span[@title='%s']",
			source));
		wait.waitForElementDisplayed(uiElement);
		click.javascriptClick(uiElement, PageScrollDestination.VERT_CENTER);
		waitForPageLoad();
	}

	/**
	 * clickLineAdd
	 */
	public void clickLineAdd( )
	{
		wait.waitForElementDisplayed(ADD_LINE_MAP_BUTTON);
		click.clickElement(ADD_LINE_MAP_BUTTON);
		waitForSalesForceLoaded();
	}
}