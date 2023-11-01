package com.vertex.quality.connectors.netsuite.common.components;

import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuitePage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteAddress;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents the component that all address forms contains
 *
 * @author hho, jyareeda
 */
public class NetsuiteAddressComponent extends NetsuiteComponent
{
	protected By attentionField = By.id("attention");
	protected By addresseeField = By.id("addressee");
	protected By addressLine1TextField = By.id("addr1");
	protected By addressLine2TextField = By.id("addr2");
	protected By addressLine3TextField = By.id("addr3");
	protected By cityTextField = By.id("city");
	protected By stateDropdown = By.id("inpt_dropdownstate2");
	protected By countryDropdown = By.id("inpt_country1");
	protected By zipTextField = By.id("zip");
	protected By saveButton = By.id("ok");
	protected String verifiedAddressText = "Use the Vertex verified address?";
	protected By cancelButtonLocator = By.id("close");

	public NetsuiteAddressComponent( final WebDriver driver, final NetsuitePage parent )
	{
		super(driver, parent);
	}

	/**
	 * Fills in the address
	 *
	 * @param address the address
	 */
	public void fillInAddress( NetsuiteAddress address )
	{
		setCountry(address.getCountry().fullName);
		String fullAddressLine1 = address.getFullAddressLine1();
		if ( fullAddressLine1 == null || fullAddressLine1.isEmpty() )
		{
			fullAddressLine1 = address.getAddressLine1();
		}
		setAttention(address.getAttention());
		setAddressee(address.getAddressee());
		setAddressLine1(fullAddressLine1);
		setAddressLine2(address.getAddressLine2());
		setCity(address.getCity());
		setState(address.getState().fullName);
		String zip = address.getZip9();
		if ( zip == null || zip.isEmpty() )
		{
			zip = address.getZip5();
		}
		setZip(zip);
	}

	/**
	 * Sets the attention
	 *
	 * @param attention the attention
	 */
	public void setAttention( String attention )
	{
		text.enterText(attentionField, attention);
	}

	/**
	 * Sets the addressee
	 *
	 * @param addressee the addressee
	 */
	public void setAddressee( String addressee )
	{
		text.enterText(addresseeField, addressee);
	}

	/**
	 * Sets the address line 1
	 *
	 * @param addressLine1 the address line 1
	 */
	public void setAddressLine1( String addressLine1 )
	{
		text.enterText(addressLine1TextField, addressLine1);
	}

	/**
	 * Sets the address line 2
	 *
	 * @param addressLine2 the address line 2
	 */
	public void setAddressLine2( String addressLine2 )
	{
		text.enterText(addressLine2TextField, addressLine2);
	}

	/**
	 * Sets the city
	 *
	 * @param city the city
	 */
	public void setCity( String city )
	{
		text.enterText(cityTextField, city);
	}

	/**
	 * Sets the state
	 *
	 * @param state the state
	 */
	public void setState( String state )
	{
		setDropdownToValue(stateDropdown, state);
	}

	/**
	 * Sets the country
	 *
	 * @param country the country
	 */
	public void setCountry( String country )
	{
		setDropdownToValue(countryDropdown, country);
	}

	/**
	 * Sets the zip code
	 *
	 * @param zip the zip code
	 */
	public void setZip( String zip )
	{
		text.enterText(zipTextField, zip);
	}

	/**
	 * Enters and saves the address
	 *
	 * @param address the address
	 */
	public void saveAddress( NetsuiteAddress address )
	{
		fillInAddress(address);
		click.javascriptClick(saveButton);
	}

	/**
	 * Cancels editing the address
	 */
	public void cancel( )
	{
		click.clickElement(cancelButtonLocator);
	}

	/**
	 * Checks if the address can be verified
	 *
	 * @return if the address can be verified
	 */
	public boolean isAddressVerified( )
	{
		boolean isAddressCorrect = doesAlertContain(verifiedAddressText);
		if ( alert.waitForAlertPresent() )
		{
			alert.acceptAlert();
		}
//		if ( !isAddressCorrect )
//		{
//			cancel();
//		}

		return isAddressCorrect;
	}

	/**
	 * Gets the country
	 *
	 * @return the country
	 */
	public String getCountry( )
	{
		String country = attribute.getElementAttribute(countryDropdown, "value");
		return country;
	}

	/**
	 * Gets the address line 1
	 *
	 * @return the address line 1
	 */
	public String getAddressLine1( )
	{
		String addressLine1 = attribute.getElementAttribute(addressLine1TextField, "value");
		return addressLine1;
	}

	/**
	 * Gets the address line 2
	 *
	 * @return the address line 2
	 */
	public String getAddressLine2( )
	{
		String addressLine2 = attribute.getElementAttribute(addressLine2TextField, "value");
		return addressLine2;
	}

	/**
	 * Gets the address line 3
	 *
	 * @return the address line 3
	 */
	public String getAddressLine3( )
	{
		String addressLine3 = attribute.getElementAttribute(addressLine3TextField, "value");
		return addressLine3;
	}

	/**
	 * Gets the city
	 *
	 * @return the city
	 */
	public String getCity( )
	{
		String city = attribute.getElementAttribute(cityTextField, "value");
		return city;
	}

	/**
	 * Gets the state
	 *
	 * @return the state
	 */
	public String getState( )
	{
		String state = attribute.getElementAttribute(stateDropdown, "value");
		return state;
	}

	/**
	 * Gets the zip
	 *
	 * @return the zip
	 */
	public String getZip( )
	{
		String zip = attribute.getElementAttribute(zipTextField, "value");
		return zip;
	}

	/**
	 * Checks if the address was cleansed
	 *
	 * @param address the correct cleansed address
	 *
	 * @return if the address was cleansed
	 */
	public boolean isAddressCleansed( NetsuiteAddress address )
	{
		String addressLine1 = getAddressLine1();
		String cleansedAddressLine1 = address.getAddressLine1();
		String addressLine2 = getAddressLine2();
		String cleansedAddressLine2 = address.getAddressLine2();
		String addressLine3 = getAddressLine3();
		String cleansedAddressLine3 = address.getAddressLine3();
		String city = getCity();
		String cleansedCity = address.getCity();
		String state = getState();
		String cleansedState = address.getState().fullName;
		String zip = getZip();
		String cleansedZip = address.getZip9();
		String country = getCountry();
		String cleansedCountry = address.getCountry().fullName;
		boolean isAddressCleansed = StringUtils.equals(addressLine1, cleansedAddressLine1) && StringUtils.equals(
			addressLine2, cleansedAddressLine2) && StringUtils.equals(addressLine3, cleansedAddressLine3) &&
									StringUtils.equals(city, cleansedCity) && StringUtils.equals(state,
			cleansedState) && StringUtils.equals(zip, cleansedZip) && StringUtils.equals(country, cleansedCountry);

		return isAddressCleansed;
	}
}
