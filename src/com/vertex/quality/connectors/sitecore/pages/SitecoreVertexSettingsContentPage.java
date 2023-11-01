package com.vertex.quality.connectors.sitecore.pages;

import com.vertex.quality.connectors.sitecore.pages.base.SitecoreBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Site-core login page class (common for both administration & store-front
 * applications)
 *
 * @author Shiva Mothkula, Daniel Bondi
 */

public class SitecoreVertexSettingsContentPage extends SitecoreBasePage
{

	//TODO remove xcode
	String COMMON_XPATH = "//td[*[@class='scEditorFieldLabel' and text()='%s']]/*/input[@class='scContentControl']";

	By companyName = By.xpath(String.format(COMMON_XPATH, "CompanyName:"));
	By address1 = By.xpath(String.format(COMMON_XPATH, "Address1:"));
	By address2 = By.xpath(String.format(COMMON_XPATH, "Address2:"));
	By city = By.xpath(String.format(COMMON_XPATH, "City:"));
	By region = By.xpath(String.format(COMMON_XPATH, "Region:"));
	By postalCode = By.xpath(String.format(COMMON_XPATH, "PostalCode:"));
	By country = By.xpath(String.format(COMMON_XPATH, "Country:"));

	public SitecoreVertexSettingsContentPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * get company name
	 *
	 * @return company name
	 */
	public String getCompanyName( )
	{
		final String companyNameText = text.retrieveTextFieldContents(companyName);
		return companyNameText;
	}

	/**
	 * set company name
	 *
	 * @param companyNameText company to set
	 */
	public void setCompanyName( final String companyNameText )
	{
		text.enterText(companyName, companyNameText);
	}

	/**
	 * get addressLine1
	 *
	 * @return addressLine1
	 */
	public String getAddressLine1( )
	{
		final String address1Text = text.retrieveTextFieldContents(address1);
		return address1Text;
	}

	/**
	 * set addressLine1
	 *
	 * @param addressLine1Text address to set
	 */
	public void setAddressLine1( final String addressLine1Text )
	{
		text.enterText(address1, addressLine1Text);
	}

	/**
	 * get address line 2
	 *
	 * @return address line 2
	 */
	public String getAddressLine2( )
	{
		final String address2Text = text.retrieveTextFieldContents(address2);
		return address2Text;
	}

	/**
	 * set address line 2
	 *
	 * @param address2Text address line 2 to set
	 */
	public void setAddressLine2( final String address2Text )
	{
		text.enterText(address2, address2Text);
	}

	/**
	 * get city
	 *
	 * @return city
	 */
	public String getCity( )
	{
		final String cityText = text.retrieveTextFieldContents(city);
		return cityText;
	}

	/**
	 * set city
	 *
	 * @param cityText city to set
	 */
	public void setCity( final String cityText )
	{
		text.enterText(city, cityText);
	}

	/**
	 * get state or province
	 *
	 * @return state or province
	 */
	public String getRegion( )
	{
		final String regionText = text.retrieveTextFieldContents(region);
		return regionText;
	}

	/**
	 * set state or province
	 *
	 * @param regionText state or province to set
	 */
	public void setRegion( final String regionText )
	{
		text.enterText(region, regionText);
	}

	/**
	 * get postal code
	 *
	 * @return postal code
	 */
	public String getPostalCode( )
	{
		final String postalCode = text.retrieveTextFieldContents(city);
		return postalCode;
	}

	/**
	 * set postal code
	 *
	 * @param postalCodeText postal code to set
	 */
	public void setPostalCode( final String postalCodeText )
	{
		text.enterText(postalCode, postalCodeText);
	}

	/**
	 * get country
	 *
	 * @return country
	 */
	public String getCountry( )
	{
		final String countryText = text.retrieveTextFieldContents(country);
		return countryText;
	}

	/**
	 * set country
	 *
	 * @param countryText country to set
	 */
	public void setCountry( final String countryText )
	{
		text.enterText(country, countryText);
	}

	/**
	 * set company details
	 *
	 * @param companyName company name to set
	 * @param address1    set address line 1
	 * @param address2    set address line 2
	 * @param city        set address city
	 * @param region      set state or province
	 * @param postalCode  postal code to set
	 * @param countryText country to set
	 */
	public void setCompanyDetails( final String companyName, final String address1, final String address2,
		final String city, final String region, final String postalCode, final String countryText )
	{

		setCompanyName(companyName);
		setAddressLine1(address1);
		setAddressLine2(address2);
		setCity(city);
		setRegion(region);
		setPostalCode(postalCode);
		setCountry(countryText);
	}
}
