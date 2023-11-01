package com.vertex.quality.connectors.netsuite.common.pages;

import com.vertex.quality.connectors.netsuite.common.components.NetsuiteAddressComponent;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuitePage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteAddress;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents the address popup page in Netsuite
 *
 * @author hho
 */
public class NetsuiteAddressPopupPage extends NetsuitePage
{
	private NetsuiteAddressComponent addressComponent;
	protected String parentWindowHandle;
	protected By saveNewShippingAddressButton = By.id("submitter");
	protected By cancelLocator = By.id("cancel");

	public NetsuiteAddressPopupPage( final WebDriver driver, String parentWindowHandle )
	{
		super(driver);
		addressComponent = new NetsuiteAddressComponent(driver, this);
		this.parentWindowHandle = parentWindowHandle;
	}

	/**
	 * Fills in the address
	 *
	 * @param address the address
	 */
	public void fillInAddress( NetsuiteAddress address )
	{
		addressComponent.fillInAddress(address);
	}

	/**
	 * Sets the address line 1
	 *
	 * @param addressLine1 the address line 1
	 */
	public void setAddressLine1( String addressLine1 )
	{
		addressComponent.setAddressLine1(addressLine1);
	}

	/**
	 * Sets the address line 2
	 *
	 * @param addressLine2 the address line 2
	 */
	public void setAddressLine2( String addressLine2 )
	{
		addressComponent.setAddressLine2(addressLine2);
	}

	/**
	 * Sets the city
	 *
	 * @param city the city
	 */
	public void setCity( String city )
	{
		addressComponent.setCity(city);
	}

	/**
	 * Sets the state
	 *
	 * @param state the state
	 */
	public void setState( String state )
	{
		addressComponent.setState(state);
	}

	/**
	 * Sets the country
	 *
	 * @param country the country
	 */
	public void setCountry( String country )
	{
		addressComponent.setCountry(country);
	}

	/**
	 * Sets the zip code
	 *
	 * @param zip the zip code
	 */
	public void setZip5( String zip )
	{
		addressComponent.setZip(zip);
	}

	/**
	 * Saves the address
	 *
	 * @param address the address
	 */
	public void saveAddress( NetsuiteAddress address )
	{
		fillInAddress(address);
		click.javascriptClick(saveNewShippingAddressButton);
	}

	/**
	 * Cancels editing the address
	 */
	public void cancel( )
	{
		click.clickElement(cancelLocator);
		window.switchToParentWindow(parentWindowHandle);
	}

	/**
	 * Checks if the address can be verified
	 *
	 * @return if the address can be verified
	 */
	public boolean isAddressVerified( )
	{
		boolean isAddressVerified = addressComponent.isAddressVerified();
		if ( isAddressVerified )
		{
			window.switchToParentWindow(parentWindowHandle);
		}
		return isAddressVerified;
	}

	/**
	 * Fills out the currently open address form
	 *
	 * @param address the address to use
	 */
	public void editOpenAddressForm( NetsuiteAddress address )
	{
		addressComponent.saveAddress(address);
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
		boolean isAddressCleansed = addressComponent.isAddressCleansed(address);
		cancel();
		return isAddressCleansed;
	}
}
