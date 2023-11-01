package com.vertex.quality.connectors.commercetools.ui.pages;

import com.vertex.quality.connectors.commercetools.ui.pages.CommerceToolsBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * generic representation of vertex service page on commerce tools
 *
 * @author Mayur.Kumbhar
 */
public class CommerceToolsVertexServicePage extends CommerceToolsBasePage
{
	public CommerceToolsVertexServicePage( final WebDriver driver )
	{
		super(driver);
	}

	private final By trustedIDTextBox= By.xpath("//input[@name='trustedId']");
	private final By usernameTextBox=By.xpath("//input[@name='username']");
	private final By passwordTextBox=By.xpath("//input[@name='password']");
	private final By addressCleansingTextBox=By.xpath("//input[@name='addressCleansingURL']");
	private final By taxServiceTextBox=By.xpath("//input[@name='taxCalculationEndpointURL']");
	private final By addressCleansingCheckBox=By.xpath("//input[@name='addressCleansingEnabled']");
	private final By autoInvoiceCheckBox=By.xpath("//input[@name='autoInvoicingEnabled']");
	private final By operatorLogCheckBox=By.xpath("//input[@name='loggingEnabled']");
	private final By companyCodeTextBox=By.xpath("//input[@name='companyCode']");
	private final By dispatcherStreetAddres1TextBox=By.xpath("//input[@id='text-field-6']");
	private final By dispatcherCityTextBox=By.xpath("//input[@id='text-field-8']");
	private final By dispatcherUSCountryCodeTextBox=By.xpath("//input[@id='text-field-9']");
	private final By dispatcherUSCountyTextBox=By.xpath("//input[@id='text-field-10']");
	private final By dispatcherPostalCodeTextBox =By.xpath("//input[@id='text-field-11']");
	private final By dispatcherCountryTextBox=By.xpath("//input[@id='select-field-1']");
	private final By sellerStreetAddress1TextBox=By.xpath("//input[@id='text-field-12']");
	private final By sellerCityTextBox=By.xpath("//input[@id='text-field-14']");
	private final By sellerUSCountryCode=By.xpath("//input[@id='text-field-15']");
	private final By sellerUSCountyCodeTextBox=By.xpath("//input[@id='text-field-16']");
	private final By sellerPostalCodeTextBox=By.xpath("//input[@id='text-field-17']");
	private final By sellerCountryTextBox=By.xpath("//input[@id='select-field-2']");
	private final By hostTextBox=By.xpath("//input[@name='host']");
	private final By clientIDTextBox=By.xpath("//input[@name='clientId']");
	private final By clientSecretTextBox=By.xpath("//input[@name='clientSecret']");
	private final By activeCheckBox=By.xpath("//input[@name='active']");
	private final By saveButton=By.xpath("//*[contains(text(),'Save')]");
	private final By cancelButton=By.xpath("//*[contains(text(),'Cancel')]");
	protected final By vertexLogoIcon= By.xpath("//*[contains(text(),'Settings')]//following::div[5]");

	/**
	 * enter trusted ID
	 * @param trustedID
	 */
	public void enterTrustedID(final String trustedID)
	{
		WebElement trustedIDField=wait.waitForElementDisplayed(trustedIDTextBox);
		text.enterText(trustedIDField,trustedID);

	}

	/**
	 * enter vertex username
	 * @param vertexUserName
	 */
	public void enterVertexUserName(final String vertexUserName)
	{
		WebElement usernameField=wait.waitForElementDisplayed(usernameTextBox);
		text.enterText(usernameField,vertexUserName);
	}

	/**
	 * enter vertex password.
	 * @param vertexPassword
	 */
	public void enterVertexPassword(final String vertexPassword)
	{
		WebElement passwordField=wait.waitForElementDisplayed(passwordTextBox);
		text.enterText(passwordField,vertexPassword);
	}

	/**
	 * enter address cleansing URL
	 * @param addressCleansingURL
	 */
	public void enterAddressCleansingEndpoint(final String addressCleansingURL)
	{
		WebElement addressCleanseField=wait.waitForElementDisplayed(addressCleansingTextBox);
		text.enterText(addressCleanseField,addressCleansingURL);
	}

	/**
	 * enter tax service endpoint
	 * @param taxEndpoint
	 */
	public void enterTaxServiceEndpoint(final String taxEndpoint)
	{
		WebElement taxEndpointField=wait.waitForElementDisplayed(taxServiceTextBox);
		text.enterText(taxEndpointField,taxEndpoint);
	}

	/**
	 * activate the address cleansing checkbox
	 */
	public void activateAddressCleansing()
	{
		WebElement addressCleansingField=wait.waitForElementDisplayed(addressCleansingCheckBox);
		click.moveToElementAndClick(addressCleansingField);
	}

	/**
	 * activate autoInvoice checkbox
	 */
	public void activateAutoInvoice()
	{
		WebElement autoInvoiceField=wait.waitForElementDisplayed(autoInvoiceCheckBox);
		click.moveToElementAndClick(autoInvoiceField);
	}

	/**
	 * activate operator log checkbox
	 */
	public void activateOperatorLog()
	{
		WebElement operatorLogField=wait.waitForElementDisplayed(operatorLogCheckBox);
		click.moveToElementAndClick(operatorLogField);
	}
	/**
	 * click vertex logo Icon.
	 */
	public void clickVertexLogoIcon()
	{
		WebElement operatorLogField=wait.waitForElementDisplayed(vertexLogoIcon);
		click.moveToElementAndClick(operatorLogField);
	}

	/**
	 * enter company code
	 * @param companyCode
	 */
	public void enterCompanyCode(final String companyCode)
	{
		WebElement companyCodeField=wait.waitForElementDisplayed(companyCodeTextBox);
		text.enterText(companyCodeField,companyCode);
	}

	/**
	 * enter dispatcher address1 on merchant center
	 * @param address1
	 */
	public void enterDispatcherStreetAddress1(final String address1)
	{
		WebElement addressField=wait.waitForElementDisplayed(dispatcherStreetAddres1TextBox);
		text.enterText(addressField,address1);
	}

	/**
	 * enter the dispatcher city
	 * @param dispatcherCity
	 */
	public void enterDispatcherCity(final String dispatcherCity)
	{
		WebElement dispatcherCityField=wait.waitForElementDisplayed(dispatcherCityTextBox);
		text.enterText(dispatcherCityField,dispatcherCity);
	}

	/**
	 * enter dispatcher country code
	 * @param dispatcherCountryCode
	 */
	public void enterDispatcherCountryCode(final String dispatcherCountryCode)
	{
		WebElement dispatcherCountryCodeField=wait.waitForElementDisplayed(dispatcherUSCountryCodeTextBox);
		text.enterText(dispatcherCountryCodeField,dispatcherCountryCode);
	}

	/**
	 * enter dispatcher US county
	 * @param USCounty
	 */
	public void enterDispatcherUSCounty(final String USCounty)
	{
		WebElement USCountyField=wait.waitForElementDisplayed(dispatcherUSCountyTextBox);
		text.enterText(USCountyField,USCounty);
	}

	/**
	 * enter the dispatcher postal code
	 * @param PostalCode
	 */
	public void enterDispatcherPostalCode(final String PostalCode)
	{
		WebElement dispatcherPostalCodeField=wait.waitForElementDisplayed(dispatcherPostalCodeTextBox);
		text.enterText(dispatcherPostalCodeField,PostalCode);
	}

	/**
	 * enter the dispatcher country
	 * @param dispatcherCountry
	 */
	public void enterDispatcherCountry(final String dispatcherCountry)
	{
		WebElement dispatcherCountryField=wait.waitForElementDisplayed(dispatcherCountryTextBox);
		text.enterText(dispatcherCountryField,dispatcherCountry);
	}

	/**
	 * enter seller address1 on merchant center
	 * @param address1
	 */
	public void enterSellerAddress1(final String address1)
	{
		WebElement addressField=wait.waitForElementDisplayed(sellerStreetAddress1TextBox);
		text.enterText(addressField,address1);
	}

	/**
	 * enter the seller city
	 * @param sellerCity
	 */
	public void enterSellerCity(final String sellerCity)
	{
		WebElement sellerCityField=wait.waitForElementDisplayed(sellerCityTextBox);
		text.enterText(sellerCityField,sellerCity);
	}

	/**
	 * enter seller country code
	 * @param sellerCountryCode
	 */
	public void enterSellerCountryCode(final String sellerCountryCode)
	{
		WebElement sellerCountryCodeField=wait.waitForElementDisplayed(sellerUSCountryCode);
		text.enterText(sellerCountryCodeField,sellerCountryCode);
	}

	/**
	 * enter seller US county
	 * @param USCounty
	 */
	public void enterSellerUSCounty(final String USCounty)
	{
		WebElement USCountyField=wait.waitForElementDisplayed(sellerUSCountyCodeTextBox);
		text.enterText(USCountyField,USCounty);
	}

	/**
	 * enter the seller postal code
	 * @param PostalCode
	 */
	public void enterSellerPostalCode(final String PostalCode)
	{
		WebElement sellerPostalCodeField=wait.waitForElementDisplayed(sellerPostalCodeTextBox);
		text.enterText(sellerPostalCodeField,PostalCode);
	}

	/**
	 * enter the seller country
	 * @param sellerCountry
	 */
	public void enterSellerCountry(final String sellerCountry)
	{
		WebElement sellerCountryField=wait.waitForElementDisplayed(sellerCountryTextBox);
		text.enterText(sellerCountryField,sellerCountry);
	}

	/**
	 * enter host url
	 * @param hostURL
	 */
	public void enterHostURL(final String hostURL)
	{
		WebElement hostURLField=wait.waitForElementDisplayed(hostTextBox);
		text.enterText(hostURLField,hostURL);
	}

	/**
	 * enter API client ID
	 * @param clientID
	 */
	public void enterClientID(final String clientID)
	{
		WebElement clientIDField=wait.waitForElementDisplayed(clientIDTextBox);
		text.enterText(clientIDField,clientID);
	}

	/**
	 * enter API client secret
	 * @param clientSecret
	 */
	public void enterClientSecret(final String clientSecret)
	{
		WebElement clientSecretField=wait.waitForElementDisplayed(clientSecretTextBox);
		text.enterText(clientSecretField,clientSecret);
	}

	/**
	 * click on active check box
	 */
	public void clickActiveCheckBox()
	{
		WebElement activeCheckBoxField=wait.waitForElementDisplayed(activeCheckBox);
		click.moveToElementAndClick(activeCheckBoxField);
	}

	/**
	 * click on save button
	 */
	public void clickOnSaveButton()
	{
		WebElement saveButtonField=wait.waitForElementDisplayed(saveButton);
		click.moveToElementAndClick(saveButtonField);
	}

	/**
	 * click on cancel button
	 */
	public void clickOnCancelButton()
	{
		WebElement cancelButtonField=wait.waitForElementDisplayed(cancelButton);
		click.moveToElementAndClick(cancelButtonField);
	}



}
