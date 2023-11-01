package com.vertex.quality.connectors.episerver.pages;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Episerver Administration Oseries Company Address page - contains all
 * re-usable methods specific to this page.
 */
public class EpiOseriesCompanyAddressPage extends VertexPage
{
	public EpiOseriesCompanyAddressPage( WebDriver driver )
	{
		super(driver);
	}

	protected By SUCCESS_MESSAGE = By.id("FullRegion_SystemMessage");
	protected By FAILURE_MESSAGE = By.id("FullRegion_ValidationSummary");
	protected By COMPANY_NAME = By.id("FullRegion_CompanyNameTextBox");
	protected By ADDRESS_LINE_1 = By.id("FullRegion_Line1TextBox");
	protected By COMPANY_CITY = By.id("FullRegion_CityTextBox");
	protected By REGION = By.id("FullRegion_RegionTextBox");
	protected By POSTAL_CODE = By.id("FullRegion_PostalCodeTextBox");
	protected By COUNTRY = By.id("FullRegion_CountryTextBox");
	protected By CONFIG_SAVE_BUTTON = By.cssSelector("input[class*='Save']");

	/**
	 * This method is used to switch to Region frame
	 */
	public void switchToRegionFrame( )
	{
		driver
			.switchTo()
			.defaultContent();
		By region = By.id("FullRegion_InfoFrame");
		wait.waitForElementPresent(region);
		window.switchToFrame(region, 60);
	}

	/**
	 * This method is used to set company name
	 */
	public void setCompanyName( String company_name )
	{
		this.switchToRegionFrame();
		wait.waitForElementDisplayed(COMPANY_NAME);
		text.enterText(COMPANY_NAME, company_name);
		waitForPageLoad();
	}

	/**
	 * This method is used to set company Address Line1
	 */
	public void setCompanyAddressLine1( String address_line1 )
	{
		text.enterText(ADDRESS_LINE_1, address_line1);
		waitForPageLoad();
	}

	/**
	 * This method is used to set company City
	 */
	public void setCompanyCity( String city )
	{
		text.enterText(COMPANY_CITY, city);
		waitForPageLoad();
	}

	/**
	 * This method is used to set company Region
	 */
	public void setCompanyRegion( String region )
	{
		text.enterText(REGION, region);
		waitForPageLoad();
	}

	/**
	 * This method is used to set company Postalcode
	 */
	public void setCompanyPostalCode( String postal_code )
	{
		text.enterText(POSTAL_CODE, postal_code);
		waitForPageLoad();
	}

	/**
	 * This method is used to set company Country
	 */
	public void setCompanyCountry( String country )
	{
		text.enterText(COUNTRY, country);
		waitForPageLoad();
	}

	/**
	 * This method is used to Save Configuration Changes
	 */
	public void clickOnBasicConfSaveButton( )
	{
		click.clickElement(CONFIG_SAVE_BUTTON);
		waitForPageLoad();
	}

	/**
	 * This method is used to retrieve confirmation message
	 *
	 * @return Confirmation message
	 */
	public String getBasicConfigurationSaveConfirmationMsg( )
	{
		wait.waitForElementDisplayed(SUCCESS_MESSAGE);
		String msg = text.getElementText(SUCCESS_MESSAGE);
		return msg;
	}

	/**
	 * This method is used to retrieve confirmation message
	 *
	 * @return Confirmation message
	 */
	public String getBasicConfigurationSaveConfirmationFailMsg( )
	{
		wait.waitForElementDisplayed(FAILURE_MESSAGE);
		String msg = text.getElementText(FAILURE_MESSAGE);
		return msg;
	}

	/**
	 * This method is used to set Configuration Page Attributes/fields
	 */
	public void fillConfiguartionAddress( String company_name, String address_line1, String city, String region,
		String postal_code, String country )
	{
		this.setCompanyName(company_name);
		this.setCompanyAddressLine1(address_line1);
		this.setCompanyCity(city);
		this.setCompanyRegion(region);
		this.setCompanyPostalCode(postal_code);
		this.setCompanyCountry(country);
		this.clickOnBasicConfSaveButton();
	}
}
