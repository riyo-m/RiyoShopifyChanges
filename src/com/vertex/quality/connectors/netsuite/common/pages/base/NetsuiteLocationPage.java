package com.vertex.quality.connectors.netsuite.common.pages.base;

import com.vertex.quality.connectors.netsuite.common.components.NetsuiteAddressComponent;
import com.vertex.quality.connectors.netsuite.common.components.NetsuiteNavigationPane;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteLocationListPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteAddress;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Represents the location page
 *
 * @author hho
 */
public abstract class NetsuiteLocationPage extends NetsuiteLocation
{
	public NetsuiteNavigationPane navigationPane;
	protected NetsuiteAddressComponent addressComponent;
	protected By saveButtonLocator = By.id("btn_multibutton_submitter");
	protected By cancelButtonLocator = By.id("_cancel");
	protected By subsidiaryDropdownLocator = By.name("inpt_subsidiary");
	protected By locationTypeDropdownLocator = By.name("inpt_locationtype");
	protected By editAddressButtonLocator = By.id("mainaddress_helper_popup");
	protected By addressFrameLocator = By.id("childdrecord_frame");
	protected By addressTextareaLocator = By.id("mainaddress_text");
	protected By editButtonLocator = By.id("edit");
	protected By actionsMenu = By.id("spn_ACTIONMENU_d1");
	protected By actionsSubmenu = By.id("div_ACTIONMENU_d1");

	protected String delete = "Delete";

	public NetsuiteLocationPage( final WebDriver driver )
	{
		super(driver);
		navigationPane = new NetsuiteNavigationPane(driver, this);
		addressComponent = new NetsuiteAddressComponent(driver, this);
	}

	/**
	 * Saves the location
	 *
	 * @return the saved location page
	 */
	public <T extends NetsuitePage> T save( )
	{
		click.clickElement(saveButtonLocator);
		return initializePageObject(getPageClass(getPageTitle()));
	}

	/**
	 * Cancel the creation of the new location
	 *
	 * @return the location list page
	 */
	public NetsuiteLocationListPage cancel( )
	{
		click.clickElement(cancelButtonLocator);
		return initializePageObject(NetsuiteLocationListPage.class);
	}

	/**
	 * Select the location's type
	 *
	 * @param locationType the location's type
	 */
	public void selectLocationType( String locationType )
	{
		setDropdownToValue(locationTypeDropdownLocator, locationType);
	}

	/**
	 * Edits the address and fills it out with the given address
	 *
	 * @param address the given address
	 */
	public void editAddress( NetsuiteAddress address )
	{
		click.clickElement(editAddressButtonLocator);
		window.switchToFrame(addressFrameLocator);
		addressComponent.saveAddress(address);
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

	/**
	 * Checks if the cleansed address is displayed
	 *
	 * @param address the address
	 *
	 * @return if the cleansed address is displayed
	 */
	public boolean isCleansedAddressDisplayed( NetsuiteAddress address )
	{
		String cleansedAddress = address.getCleansedAddress();
		String actualAddress = attribute.getElementAttribute(addressTextareaLocator, "value");
		boolean isAddressCleansed = actualAddress != null && (cleansedAddress.equals(actualAddress) ||
															  actualAddress.contains(cleansedAddress));
		return isAddressCleansed;
	}

	/**
	 * Edits the current location page
	 *
	 * @return the edit location page
	 */
	public <T extends NetsuiteLocationPage> T edit( )
	{
		click.clickElement(editButtonLocator);
		return initializePageObject(getPageClass(getPageTitle()));
	}

	/**
	 * Deletes the current location
	 *
	 * @return the location list page
	 */
	public NetsuiteLocationListPage delete( )
	{
		WebElement deleteButton = getElementInHoverableMenu(actionsMenu, actionsSubmenu, delete);
		click.clickElement(deleteButton);
		if ( alert.waitForAlertPresent(TWO_SECOND_TIMEOUT) )
		{
			alert.acceptAlert();
		}
		return initializePageObject(NetsuiteLocationListPage.class);
	}
}
