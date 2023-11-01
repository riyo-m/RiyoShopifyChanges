package com.vertex.quality.connectors.salesforce.pages.lb2b;

import com.vertex.quality.connectors.salesforce.enums.Constants;
import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * Common functions for anything related to Salesforce Basic Address Page.
 *
 * @author brendaj
 */
public class SalesForceB2BAddressesPage extends SalesForceBasePage
{
	protected By ADDRESSES_TAB = By.cssSelector("li[title='Addresses']");
	protected By ADDRESS1_INPUT = By.xpath(".//*[starts-with(@id, 'Address1')]");
	protected By ADDRESS2_INPUT = By.xpath(".//*[starts-with(@id, 'Address2')]");
	protected By CITY_INPUT = By.xpath(".//input[starts-with(@id, 'city') or starts-with(@id, 'City')]");
	protected By STATE_INPUT = By.xpath(".//*[starts-with(@id, 'state') or starts-with(@id, 'State')]");
	protected By POSTAL_CODE_INPUT = By.xpath(".//*[starts-with(@id, 'postalCode') or starts-with(@id, 'PostalCode')]");
	protected By COUNTRY_INPUT = By.xpath(".//*[starts-with(@id, 'country') or starts-with(@id, 'Country')]");
	protected By TAX_AREA_ID = By.xpath(".//*[starts-with(@id, 'taxAreaID') or starts-with(@id, 'TaxAreaID')]");

	protected By EDIT_SAVE_BUTTON = By.xpath(
		"//div[contains(@class,'modal')]/fieldset[not(contains(@style,'display:none'))]//button[contains(text(),'Save') and not(ancestor::*[contains(@style,'display:none')]) and not(ancestor::*[contains(@style,'display: none')])]");
	protected By CHANGE_ADDRESS_BUTTON = By.xpath("//button[contains(text(),'Change to this address')]");
	protected By ASSIGN_TAX_ID_TO_ADDRESS_BUTTON = By.xpath(
		"//button[contains(text(),'Assign Tax ID to Current Address')]");
	protected By NEW_ADDRESS_BUTTON = By.xpath("//button[contains(text(),'New Address')]");
	protected By VALIDATE_ADDRESS_BUTTON = By.xpath(
		"//button[contains(text(),'Validate Address') and not(contains(@class, 'hide')) and not(ancestor::*[contains(@style,'display:none')]) and not(ancestor::*[contains(@style,'display: none')])]");
	protected By CANCEL_ADDRESS_VALIDATION_BUTTON = By.xpath(
		"//div[contains(@class,'modal')]/.//button[contains(text(),'Cancel') and not(ancestor::*[contains(@style,'display:none')]) and not(ancestor::*[contains(@style,'display: none')])]");
	protected By ADDRESS_VALIDATION_ERROR_MESSAGE = By.xpath(".//label[contains(@id, 'errorMsgDisplay')]");

	public SalesForceB2BAddressesPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Click on button related to its section
	 *
	 * @param addressSection
	 * @param buttonName
	 */
	public void clickButtonsOnAddressTab( String addressSection, String buttonName )
	{
		String buttonLocator = String.format(
			"//legend[contains(text(),'%s')]/parent::fieldset//div//button[contains(text(),'%s')]", addressSection,
			buttonName);
		By addressButton = By.xpath(buttonLocator);
		wait.waitForElementDisplayed(addressButton);
		click.javascriptClick(element.getWebElement(addressButton));
		waitForPageLoad();
	}

	/**
	 * Set Address 1 value
	 *
	 * @param address1Value
	 */
	public void setAddress1( String address1Value )
	{
		wait.waitForElementDisplayed(ADDRESS1_INPUT);
		text.enterText(ADDRESS1_INPUT, address1Value);
	}

	/**
	 * Set Address 2 value
	 *
	 * @param address2Value
	 */
	public void setAddress2( String address2Value )
	{
		wait.waitForElementDisplayed(ADDRESS2_INPUT);
		text.enterText(ADDRESS2_INPUT, address2Value);
	}

	/**
	 * Set City Value
	 *
	 * @param cityValue
	 */
	public void setCity( String cityValue )
	{
		wait.waitForElementDisplayed(CITY_INPUT);
		text.enterText(CITY_INPUT, cityValue);
	}

	/**
	 * Set State Value
	 *
	 * @param stateValue
	 */
	public void setState( String stateValue )
	{
		wait.waitForElementDisplayed(STATE_INPUT);
		text.enterText(STATE_INPUT, stateValue);
	}

	/**
	 * Set ZIP Value
	 *
	 * @param zipValue
	 */
	public void setZip( String zipValue )
	{
		wait.waitForElementDisplayed(POSTAL_CODE_INPUT);
		text.enterText(POSTAL_CODE_INPUT, zipValue);
	}

	/**
	 * Set Country Value
	 *
	 * @param countryValue
	 */
	public void setCountry( String countryValue )
	{
		wait.waitForElementDisplayed(COUNTRY_INPUT);
		text.enterText(COUNTRY_INPUT, countryValue);
	}

	/**
	 * Set Tax Area Id
	 *
	 * @param taxAreaId
	 */
	public void setTaxAreaId( String taxAreaId )
	{
		wait.waitForElementDisplayed(TAX_AREA_ID);
		text.enterText(TAX_AREA_ID, taxAreaId);
	}

	/**
	 * Click on Save button on Edit Address pop up
	 */
	public void clickEditSaveButton( )
	{
		WebElement element = driver.findElement(EDIT_SAVE_BUTTON);

		Actions actions = new Actions(driver);

		actions.moveToElement(element).click().perform();
		wait.waitForElementEnabled(NEW_ADDRESS_BUTTON);
		waitForSalesForceLoaded();
	}

	/**
	 * Get address row value by passing row value and section
	 *
	 * @param section    of Address in Addresses tab
	 * @param addressRow is of AddresLine or city or state or zip
	 *
	 * @return
	 */
	public String getEachSectionAddressDetails( String section, String addressRow )
	{
		waitForPageLoad();
		// There are few section Addresses with same controls and Same buttons.To click
		// on one single button,, we are differentiating by giving the section of
		// Address, It is String value
		String addressRowValue = String.format(
			"//legend[contains(text(),'%s')]/parent::fieldset//label[contains(text(), '%s')]/parent::div/child::div/span",
			section, addressRow);
		wait.waitForElementDisplayed(By.xpath(addressRowValue));
		String rowValue = attribute.getElementAttribute(By.xpath(addressRowValue), "textContent");
		return rowValue;
	}

	/**
	 * Click on ChangeToThisCurrentAddress button on address pop up
	 */
	public void clickChangeToThisAddressButton( )
	{
		wait.waitForElementDisplayed(CHANGE_ADDRESS_BUTTON);
		click.javascriptClick(element.getWebElement(CHANGE_ADDRESS_BUTTON));
		waitForPageLoad();
		jsWaiter.waitForLoadAll();
	}

	/**
	 * Click on AssignTaxId button on address pop up
	 */
	public void clickAssignTaxToAddressButton( )
	{
		click.clickElement(ASSIGN_TAX_ID_TO_ADDRESS_BUTTON);
		waitForPageLoad();
	}

	/**
	 * Click Cancel button on validate address pop up
	 */
	public void clickCancelButton( )
	{
		wait.waitForElementDisplayed(CANCEL_ADDRESS_VALIDATION_BUTTON);
		click.clickElement(CANCEL_ADDRESS_VALIDATION_BUTTON);
		waitForPageLoad();
	}

	/**
	 * Validate Address Button doesn't Exist
	 *
	 * @return boolean Validate Address Button Does not exits
	 */
	public boolean VerifyValidateAddressButton( )
	{
		boolean validateAddress;
		validateAddress = element.isElementPresent(VALIDATE_ADDRESS_BUTTON);
		return validateAddress;
	}

	/**
	 * Validate that salesforce error occurs with address validation
	 *
	 * @return error message in alert pop-up
	 */
	public String acceptAddressValidationErrorMessage( )
	{
		String errorMessage;
		errorMessage = alert.getAlertText();
		alert.acceptAlert();
		return errorMessage;
	}

	/**
	 * This method is to Edit any type of address in Address tab based on section
	 * provided.
	 *
	 * @param address1
	 * @param addressSection
	 * @param city
	 * @param country
	 * @param state
	 * @param taxAreaId
	 * @param zip
	 */
	public void editAddressInAddressesTab( String addressSection, String address1, String state, String city,
		String zip, String country, String taxAreaId )
	{
		clickButtonsOnAddressTab(addressSection, Constants.ButtonTitles.EDIT_BUTTON.text);
		setAddress1(address1);
		setAddress2("");
		setState(state);
		setCity(city);
		setZip(zip);
		setCountry(country);
		setTaxAreaId(taxAreaId);
		clickEditSaveButton();
	}

	/**
	 * Get address validation Error message
	 * @return error message on address validation modal
	 */
	public String getAddressValidationErrorMessage()
	{
		String errorMessage;
		wait.waitForElementDisplayed(ADDRESS_VALIDATION_ERROR_MESSAGE);
		errorMessage = text.getElementText(ADDRESS_VALIDATION_ERROR_MESSAGE);
		return errorMessage;
	}
}
