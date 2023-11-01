package com.vertex.quality.connectors.accumatica.pages;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Account Location page actions/methods implementation
 *
 * @author Saidulu kodadala
 */
public class AcumaticaAccountLocationsPage extends VertexPage
{
	protected By BUSINESS_ACCOUNT = By.id("ctl00_phF_frmHeader_edBAccountID_text");
	protected By ADDRESS_LINE1 = By.id("ctl00_phG_tab_t0_Address_edAddressLine1");
	protected By ADDRESS_LINE2 = By.id("ctl00_phG_tab_t0_Address_edAddressLine2");
	protected By CITY = By.id("ctl00_phG_tab_t0_Address_edCity");
	protected By COUNTRY = By.id("ctl00_phG_tab_t0_Address_edCountryID_text");
	protected By STATE = By.id("ctl00_phG_tab_t0_Address_edState_text");
	protected By ZIPCODE = By.id("ctl00_phG_tab_t0_Address_edPostalCode");

	public AcumaticaAccountLocationsPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Set Business Account
	 *
	 * @param businessAccount
	 */
	public void setBusinessAccount( String businessAccount )
	{
		wait.waitForElementDisplayed(driver.findElement(BUSINESS_ACCOUNT));
		text.enterText(BUSINESS_ACCOUNT, businessAccount);
		waitForPageLoad();
	}

	/**
	 * Get Address Line1
	 */
	public String getAddressLine1( )
	{
		wait.waitForElementDisplayed(driver.findElement(ADDRESS_LINE1));
		String addressLine1 = attribute.getElementAttribute(ADDRESS_LINE1, "value");
		waitForPageLoad();
		return addressLine1;
	}

	/**
	 * Get Address Line2
	 */
	public String getAddressLine2( )
	{
		wait.waitForElementDisplayed(driver.findElement(ADDRESS_LINE2));
		String addressLine2 = attribute.getElementAttribute(ADDRESS_LINE2, "value");
		waitForPageLoad();
		return addressLine2;
	}

	/**
	 * Get city
	 */
	public String getCity( )
	{
		wait.waitForElementDisplayed(driver.findElement(CITY));
		String city = attribute.getElementAttribute(CITY, "value");
		waitForPageLoad();
		return city;
	}

	/**
	 * Get country
	 */
	public String getCountry( )
	{
		wait.waitForElementDisplayed(driver.findElement(COUNTRY));
		String country = attribute.getElementAttribute(COUNTRY, "value");
		waitForPageLoad();
		return country;
	}

	/**
	 * Get state
	 */
	public String getState( )
	{
		wait.waitForElementDisplayed(driver.findElement(STATE));
		String state = attribute.getElementAttribute(STATE, "value");
		waitForPageLoad();
		return state;
	}

	/**
	 * Get zipCode
	 */
	public String getZipCode( )
	{
		wait.waitForElementDisplayed(driver.findElement(ZIPCODE));
		String zipCode = attribute.getElementAttribute(ZIPCODE, "value");
		waitForPageLoad();
		return zipCode;
	}
}
