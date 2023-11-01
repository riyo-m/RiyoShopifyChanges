package com.vertex.quality.connectors.bigcommerce.ui.pages.devcloud;

import com.vertex.quality.common.utils.VertexLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

/**
 * this class represents the configuration and methods for new company on vertex cloud
 *
 * @author rohit.mogane
 */
public class OseriesCloudConfigureCompanyPage extends OseriesCloudBasePage
{
	public OseriesCloudConfigureCompanyPage( WebDriver driver )
	{
		super(driver);
	}

	protected final By registrationLink = By.partialLinkText("href=\"#/companies");
	protected final By configureLink = By.xpath("//a[@href='/config']");
	protected final By countryDropDown = By.xpath("//select[@id='country']");
	protected final By companyCodeText = By.xpath("//input[@id='company-code']");
	protected final By federalNumberText = By.xpath("//input[@id='federal-id']");
	protected final By companyNameText = By.xpath("//input[@id='company-name']");
	protected final By firstNameText = By.xpath("//input[@id='statetempfirstName']");
	protected final By lastNameText = By.xpath("//input[@id='statetemplastName']");
	protected final By jobTitleText = By.xpath("//input[@id='title']");
	protected final By emailAddressText = By.xpath("//input[@id='email']");
	protected final By phoneText = By.xpath("//input[@id='phone']");
	protected final By companyStreetAddressText = By.xpath("//input[@id='address1']");
	protected final By companyCityText = By.xpath("//input[@id='city']");
	protected final By companyStateDropDown = By.xpath("//select[@id='state']");
	protected final By companyZipCode = By.xpath("//input[@id='zip123']");
	protected final By addCompanyButton = By.xpath("//button[@type='submit']");
	protected final By confirmCompanyAddress = By.xpath("//a[@class='btn btn-primary ng-scope']");
	protected final By serverUnavailable = By.xpath("//h2[contains(text(),'Service Unavailable')]");

	/**
	 * this method click on configure link to configure new company information and address
	 */
	public void clickConfigureLink( )
	{
		WebElement clickConfigure = wait.waitForElementDisplayed(configureLink);
		click.clickElement(clickConfigure);
	}

	/**
	 * this method click on registration link to edit new company information and address
	 */
	public void clickRegistrationLink( )
	{
		WebElement registerLinkField = wait.waitForElementEnabled(registrationLink);
		click.clickElement(registerLinkField);
	}

	/**
	 * this method select country drop down on configure link to configure new company information.
	 */
	public void selectCountryDropDown( final String countryName )
	{
		WebElement countryDropDownField = wait.waitForElementDisplayed(countryDropDown);
		dropdown.selectDropdownByDisplayName(countryDropDownField, countryName);
	}

	/**
	 * locates the company code field and then clears it and types in the company code
	 *
	 * @param companyCode the code for company, string which will be entered in the company information.
	 */
	public void enterCompanyCode( final String companyCode )
	{
		WebElement companyCodeField = wait.waitForElementDisplayed(companyCodeText);
		text.enterText(companyCodeField, companyCode);
	}

	/**
	 * locates the company federal number field and then clears it and types in the company federal number
	 *
	 * @param federalIDNumber the federal number  which will be entered in the configure company page
	 */
	public void enterFederalIDNumber( final String federalIDNumber )
	{
		WebElement federalIDField = wait.waitForElementDisplayed(federalNumberText);
		text.enterText(federalIDField, federalIDNumber);
	}

	/**
	 * locates the company name field and then clears it and types in the company federal number
	 *
	 * @param companyName the name of company  which will be entered in the configure company page
	 */
	public void enterCompanyName( final String companyName )
	{
		WebElement companyNameField = wait.waitForElementDisplayed(companyNameText);
		text.enterText(companyNameField, companyName);
	}

	/**
	 * locates the first name field and then clears it and types in the first name
	 *
	 * @param firstName the first name of company  which will be entered in the configure company page
	 */
	public void enterFirstName( final String firstName )
	{
		WebElement firstNameField = wait.waitForElementDisplayed(firstNameText);
		text.enterText(firstNameField, firstName);
	}

	/**
	 * locates the last name field and then clears it and types in the last name
	 *
	 * @param lastName the last name of company  which will be entered in the configure company page
	 */
	public void enterLastName( final String lastName )
	{
		WebElement lastNameField = wait.waitForElementDisplayed(lastNameText);
		text.enterText(lastNameField, lastName);
	}

	/**
	 * locates the address field and then clears it and types in the address
	 *
	 * @param address the address of company which will be entered in the configure company page
	 */
	public void enterStreetAddress( final String address )
	{
		WebElement addressField = wait.waitForElementDisplayed(companyStreetAddressText);
		text.enterText(addressField, address);
	}

	/**
	 * locates the city field and then clears it and types in the city
	 *
	 * @param city the city of company which will be entered in the configure company page
	 */
	public void enterCity( final String city )
	{
		WebElement cityField = wait.waitForElementDisplayed(companyCityText);
		text.enterText(cityField, city);
	}

	/**
	 * selects the state dropdown and then clears it and select state
	 *
	 * @param state the city of company which will be entered in the configure company page
	 */
	public void selectState( final String state )
	{
		WebElement stateField = wait.waitForElementDisplayed(companyStateDropDown);
		dropdown.selectDropdownByDisplayName(stateField, state);
	}

	/**
	 * locates the zip code field and then clears it and types in the zip code
	 *
	 * @param zipCode the zip code of company which will be entered in the configure company page
	 */
	public void enterZipCode( final String zipCode )
	{
		WebElement zipCodeField = wait.waitForElementDisplayed(companyZipCode);
		text.enterText(zipCodeField, zipCode);
		text.pressTab(zipCodeField);
	}

	/**
	 * click on the add company button to add the new company information.
	 */
	public void clickAddCompanyButton( )
	{
		WebElement companyButtonField = wait.waitForElementDisplayed(addCompanyButton);
		click.clickElement(companyButtonField);
	}

	/**
	 * click on confirm company address to add the new company address
	 */
	public void clickOnConfirmCompanyAddress( )
	{
		WebElement confirmCompanyAddressField = wait.waitForElementDisplayed(confirmCompanyAddress);
		click.clickElement(confirmCompanyAddressField);
	}

	/**
	 * stops the test if server is unavailable
	 */
	public boolean getServerUnavailableText( )
	{
		WebElement serverUnavailableField = wait.waitForElementDisplayed(serverUnavailable);
		VertexLogger.log(serverUnavailableField.getText());
		Assert.fail();
		return true;
	}
}
