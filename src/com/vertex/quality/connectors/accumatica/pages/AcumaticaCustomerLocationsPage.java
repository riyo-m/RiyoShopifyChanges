package com.vertex.quality.connectors.accumatica.pages;

import com.vertex.quality.connectors.accumatica.pages.base.AcumaticaBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Customer Locations page actions/methods.
 *
 * @author Saidulu Kodadala
 */
public class AcumaticaCustomerLocationsPage extends AcumaticaBasePage
{
	protected By CUSTOMER_ID = By.id("ctl00_phF_frmHeader_edBAccountID_text");
	protected By SAME_AS_MAIN_CHECK_BOX = By.id("ctl00_phG_tab_t0_chkIsAddressSameAsMain");
	protected By ACS_CHECK_BOX = By.id("ctl00_phG_tab_t0_Address_edIsValidated");
	protected By ADDRESS_LINE1 = By.id("ctl00_phG_tab_t0_Address_edAddressLine1");
	protected By ADDRESS_LINE2 = By.id("ctl00_phG_tab_t0_Address_edAddressLine2");
	protected By CITY = By.id("ctl00_phG_tab_t0_Address_edCity");
	protected By COUNTRY = By.id("ctl00_phG_tab_t0_Address_edCountryID_text");
	protected By STATE = By.id("ctl00_phG_tab_t0_Address_edState_text");
	protected By ZIPCODE = By.id("ctl00_phG_tab_t0_Address_edPostalCode");

	public AcumaticaCustomerLocationsPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Set Customer Id
	 *
	 * @param customerId
	 */
	public void setCustomerId( String customerId )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(CUSTOMER_ID);
		text.enterText(CUSTOMER_ID, customerId);
		waitForPageLoad();
	}

	/***
	 * Select 'same as main' check box
	 * @param flag
	 */
	public boolean isSameAsMainChecked( final boolean flag )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(SAME_AS_MAIN_CHECK_BOX);
		boolean status = checkbox.isCheckboxChecked(SAME_AS_MAIN_CHECK_BOX);
		if ( status != flag )
		{
			click.clickElement(SAME_AS_MAIN_CHECK_BOX);
			waitForPageLoad();
		}
		return status;
	}

	/***
	 * Select 'ACS' check box
	 * @param flag
	 */
	public boolean isAcsChecked( final boolean flag )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(ACS_CHECK_BOX);
		boolean status = checkbox.isCheckboxChecked(ACS_CHECK_BOX);
		if ( status != flag )
		{
			click.clickElement(ACS_CHECK_BOX);
			waitForPageLoad();
		}
		return status;
	}

	/**
	 * Get Address Line1
	 */
	public String getAddressLine1( )
	{
		waitForPageLoad();
		String addressLine1 = attribute.getElementAttribute(ADDRESS_LINE1, "value");
		waitForPageLoad();
		return addressLine1;
	}

	/**
	 * Get Address Line2
	 */
	public String getAddressLine2( )
	{
		waitForPageLoad();
		String addressLine2 = attribute.getElementAttribute(ADDRESS_LINE2, "value");
		waitForPageLoad();
		return addressLine2;
	}

	/**
	 * Get city
	 */
	public String getCity( )
	{
		waitForPageLoad();
		String city = attribute.getElementAttribute(CITY, "value");
		waitForPageLoad();
		return city;
	}

	/**
	 * Get country
	 */
	public String getCountry( )
	{
		waitForPageLoad();
		String country = attribute.getElementAttribute(COUNTRY, "value");
		waitForPageLoad();
		return country;
	}

	/**
	 * Get state
	 */
	public String getState( )
	{
		waitForPageLoad();
		String state = attribute.getElementAttribute(STATE, "value");
		waitForPageLoad();
		return state;
	}

	/**
	 * Get zipCode
	 */
	public String getZipCode( )
	{
		waitForPageLoad();
		String zipCode = attribute.getElementAttribute(ZIPCODE, "value");
		waitForPageLoad();
		return zipCode;
	}
}
