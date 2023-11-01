package com.vertex.quality.connectors.netsuite.common.pages;

import com.vertex.quality.connectors.netsuite.common.components.NetsuiteAddressComponent;
import com.vertex.quality.connectors.netsuite.common.components.NetsuiteNavigationPane;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuitePage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteAddress;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents the company information page in Netsuite
 *
 * @author hho
 */
public class NetsuiteCompanyInformationPage extends NetsuitePage
{
	public NetsuiteNavigationPane navigationPane;
	protected NetsuiteAddressComponent addressComponent;

	protected By editShippingAddressButtonLocator = By.id("shippingaddress_helper_popup");
	protected By addressFrameLocator = By.id("childdrecord_frame");
	protected By shippingAddressDisplayLocator = By.id("shippingaddress_text");
	protected By saveButtonLocator = By.id("submitter");
	protected By editBillingAddressButtonLocator = By.id("mainaddress_helper_popup");
	protected By billingAddressDisplayLocator = By.id("mainaddress_text");

	public NetsuiteCompanyInformationPage( final WebDriver driver )
	{
		super(driver);
		navigationPane = new NetsuiteNavigationPane(driver, this);
		addressComponent = new NetsuiteAddressComponent(driver, this);
	}

	/**
	 * Changes the shipping address to the given address
	 *
	 * @param address the shipping address to change to
	 */
	public void editShippingAddress( NetsuiteAddress address )
	{
		click.clickElement(editShippingAddressButtonLocator);
		window.switchToFrame(addressFrameLocator);
		addressComponent.saveAddress(address);
	}

	/**
	 * Checks if the cleansed shipping address is displayed
	 *
	 * @param address the address
	 *
	 * @return if the cleansed shipping address is displayed
	 */
	public boolean isCleansedShippingAddressDisplayed( NetsuiteAddress address )
	{
		String cleansedAddress = address.getCleansedAddress();
		String actualAddress = attribute.getElementAttribute(shippingAddressDisplayLocator, "value");
		boolean isAddressCleansed = actualAddress != null && (cleansedAddress.equals(actualAddress) ||
															  actualAddress.contains(cleansedAddress));
		return isAddressCleansed;
	}

	/**
	 * Saves the company info page
	 *
	 * @return the setup manager page
	 */
	public NetsuiteSetupManagerPage save( )
	{
		click.clickElement(saveButtonLocator);
		return initializePageObject(NetsuiteSetupManagerPage.class);
	}

	/**
	 * Changes the billing address to the given address
	 *
	 * @param address the billing address to change to
	 */
	public void editBillingAddress( NetsuiteAddress address )
	{
		click.clickElement(editBillingAddressButtonLocator);
		window.switchToFrame(addressFrameLocator);
		addressComponent.saveAddress(address);
	}

	/**
	 * Checks if the cleansed billing address is displayed
	 *
	 * @param address the address
	 *
	 * @return if the cleansed billing address is displayed
	 */
	public boolean isCleansedBillingAddressDisplayed( NetsuiteAddress address )
	{
		String cleansedAddress = address.getCleansedAddress();
		String actualAddress = attribute.getElementAttribute(billingAddressDisplayLocator, "value");
		boolean isAddressCleansed = actualAddress != null && (cleansedAddress.equals(actualAddress) ||
															  actualAddress.contains(cleansedAddress));
		return isAddressCleansed;
	}

	/**
	 * Checks if the address can be verified
	 *
	 * @return if the address can be verified
	 */
	public boolean isAddressVerified( )
	{
		return addressComponent.isAddressVerified();
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
}
