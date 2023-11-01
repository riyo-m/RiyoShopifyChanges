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
public class NetsuiteAddressPage extends NetsuitePage
{
	private NetsuiteAddressComponent addressComponent;
	protected By saveNewShippingAddressButton = By.id("submitter");

	public NetsuiteAddressPage( final WebDriver driver )
	{
		super(driver);
		addressComponent = new NetsuiteAddressComponent(driver, this);
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
		window.switchToDefaultContent();
	}
}
