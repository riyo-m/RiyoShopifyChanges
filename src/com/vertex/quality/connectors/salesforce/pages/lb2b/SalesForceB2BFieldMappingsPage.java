package com.vertex.quality.connectors.salesforce.pages.lb2b;

import com.vertex.quality.common.enums.PageScrollDestination;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Common functions for anything related to Salesforce Basic Field Mapping Page.
 *
 * @author brendaj
 */
public class SalesForceB2BFieldMappingsPage extends SalesForceBasePage
{
	protected final By pageBodyIframe = By.xpath(".//iframe");
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
	protected By DELETE_BUTTON = By.xpath(".//form/div/div[2]/div[3]/span/div[4]/span/div[2]/input[@value='Delete']");

	protected By SAVE_ERROR_MESSAGE = By.cssSelector("[id$=saveErrorMessage-");

	protected By NEW_MAPPING_BUTTON = By.xpath(
			".//*[contains(text(),'New Mapping') and not(ancestor::*[contains(@style,'display:none')]) and not(ancestor::*[contains(@style,'display: none')])]");

	protected By ADD_TRANS_LABEL_DROPDOWN = By.xpath(".//*[contains(@data-id,'transLabelPicklist')]");
	protected By ADD_TRANS_SOURCE_DROPDOWN = By.xpath(".//*[contains(@data-id,'transSrcPicklist')]");
	protected By ADD_TRANS_MAP_BUTTON = By.xpath(".//*[@value='transaction' and text()='Add']");
	protected By ADD_LINE_LABEL_DROPDOWN = By.xpath(".//*[contains(@data-id,'lineLabelPicklist')]");
	protected By ADD_LINE_SOURCE_DROPDOWN = By.xpath(".//*[contains(@data-id,'lineSrcPicklist')]");
	protected By ADD_LINE_MAP_BUTTON = By.xpath(".//*[@value='Line' and text()='Add']");

	public SalesForceB2BFieldMappingsPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * navigate to the field tab
	 *
	 * @param tabName
	 */
	public void clickFieldTabs( String tabName )
	{
		By tabLocator = By.xpath(String.format("//*[@id='fieldMappingsTabList']//li[@title='%s']", tabName));
		wait.waitForElementDisplayed(tabLocator);
		click.clickElement(tabLocator);
		waitForPageLoad();
	}

	/**
	 * get Map name
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
	 * delete mapping
	 *
	 * @param mappingField
	 */
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
			waitForPageLoad();
			clickSaveButton();
		}
		else
		{
			VertexLogger.log("There is no mapping Field with label" + mappingField, getClass());
		}
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
		wait.waitForElementDisplayed(DELETE_BUTTON);
		click.clickElement(DELETE_BUTTON);
		alert.acceptAlert(DEFAULT_TIMEOUT);
		waitForSalesForceLoaded();
	}

	/**
	 * click new mapping button
	 */
	public void clickNewMappingButton( )
	{
		wait.waitForElementDisplayed(NEW_MAPPING_BUTTON);
		click.clickElement(NEW_MAPPING_BUTTON);
		waitForSalesForceLoaded();
	}

	/**
	 * set Map name
	 *
	 * @param mappingName
	 */
	public void setMappingName( String mappingName )
	{
		text.enterText(MAPPING_NAME, mappingName);
	}

	/**
	 * set Vertex XML Message
	 *
	 * @param vertexXMLMessage
	 */
	public void selectVertexXmlMessage( String vertexXMLMessage )
	{
		dropdown.selectDropdownByDisplayName(VERTEX_XML_MASSAGE, vertexXMLMessage);
	}

	/**
	 * select Transaction Type
	 *
	 * @param transactionType
	 */
	public void selectTransactionType( String transactionType )
	{
		dropdown.selectDropdownByDisplayName(TRANSACTION_TYPE, transactionType);
	}

	/**
	 * set Transaction Object
	 *
	 * @param transactionObject
	 */
	public void setTransactionObject( String transactionObject )
	{
		text.enterText(TRANSACTION_OBJECT, transactionObject);
		text.pressTab(TRANSACTION_OBJECT);
		waitForPageLoad();
	}

	/**
	 * select Line Object
	 *
	 * @param lineObject
	 */
	public void selectLineObject( String lineObject )
	{
		try
		{
			Thread.sleep(3000);
		}
		catch ( InterruptedException e )
		{
			e.printStackTrace();
		}
		waitForPageLoad();
		dropdown.selectDropdownByDisplayName(LINE_OBJECT, lineObject);
	}

	/**
	 * select Transaction Date
	 *
	 * @param transactionDate
	 */
	public void selectTransactionDate( String transactionDate )
	{
		dropdown.selectDropdownByDisplayName(TRANSACTION_DATE, transactionDate);
	}

	/**
	 * select document Number
	 *
	 * @param documentNumber
	 */
	public void selectDocumentNumber( String documentNumber )
	{
		dropdown.selectDropdownByDisplayName(DOCUMENT_NUMBER, documentNumber);
	}

	/**
	 * select Line Tax Amount field map
	 *
	 * @param lineTaxAmount
	 */
	public void selectLineTaxAmount( String lineTaxAmount )
	{
		waitForSalesForceLoaded();
		dropdown.selectDropdownByDisplayName(LINE_TAX_AMOUNT, lineTaxAmount);
	}

	/**
	 * select Extended Price
	 *
	 * @param extendedPrice
	 */
	public void selectExtendedPrice( String extendedPrice )
	{
		dropdown.selectDropdownByDisplayName(EXTENDED_PRICE, extendedPrice);
	}

	/**
	 * select Quantity map
	 *
	 * @param quantity
	 */
	public void selectQuantity( String quantity )
	{
		dropdown.selectDropdownByDisplayName(QUANTITY, quantity);
	}

	/**
	 * create new mapping
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
		window.switchToFrame(pageBodyIframe);
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
	 * get if new tab exists
	 *
	 * @param tabName
	 *
	 * @return doesTabExist
	 */
	public boolean tabExists( String tabName )
	{
		waitForSalesForceLoaded();
		By tabLocator = By.xpath(String.format("//*[@id='fieldMappingsTabList']//li[@title='%s']", tabName));
		boolean isExists = element.isElementDisplayed(tabLocator);
		return isExists;
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
	 * is save error message displayed
	 *
	 * @return elementExists
	 */
	public boolean isSaveErrorMessageDisplayed( )
	{
		waitForSalesForceLoaded();
		wait.waitForElementDisplayed(driver.findElement(SAVE_ERROR_MESSAGE));
		boolean isExists = element.isElementDisplayed(SAVE_ERROR_MESSAGE);
		return isExists;
	}

	/**
	 * get Section name
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
		String sectionPath = String.format("//h3[contains(text(),'%s')]/following-sibling::div//div", sectionName);
		if(!element.isElementPresent(By.xpath(sectionPath)))
			sectionPath = String.format("//h3[contains(text(),'%s')]/following-sibling::div//span", sectionName);
		String sectionText = text.getElementText(By.xpath(sectionPath));
		return sectionText;
	}

	/**
	 * get Section name with failure
	 *
	 * @param sectionName
	 * @param mappingName
	 *
	 * @return sectionText
	 */
	public String getSectionTextFail( String sectionName, String mappingName )
	{
		waitForSalesForceLoaded();
		String sectionPath = String.format("//h3[contains(text(),'%s')]/following-sibling::div//span", sectionName);
		if(!element.isElementPresent(By.xpath(sectionPath)))
			sectionPath = String.format("//h3[contains(text(),'%s')]/following-sibling::div//div", sectionName);
		String sectionText = text.getElementText(By.xpath(sectionPath));
		return sectionText;
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
		By uiElement = By.xpath(String.format(
				".//*[contains(@data-id,'transLabelPicklist')]/.//*[contains(@class, 'option')]/.//span[@title='%s']",
				label));
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

		setTransLabel(label);
		setTransSource(sourceField);
		clickTransAdd();
		VertexLogger.log("Trans Mapped  : label:" + label + "  fieldLineMap:" + sourceField);
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
			{
				VertexLogger.log("unable to see Current Mapping for " + label);
				return;
			}}
		}

		setLineLabel(label);
		setLineSource(sourceField);
		clickLineAdd();
		waitForPageLoad();
		VertexLogger.log("Line Mapped  : label:" + label + "  fieldLineMap:" + sourceField);
	}
}
