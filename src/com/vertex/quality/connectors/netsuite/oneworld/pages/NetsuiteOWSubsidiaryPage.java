package com.vertex.quality.connectors.netsuite.oneworld.pages;

import com.vertex.quality.connectors.netsuite.common.components.NetsuiteAddressComponent;
import com.vertex.quality.connectors.netsuite.common.components.NetsuiteNavigationPane;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuitePage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteAddress;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents the subsidiary page in One World
 *
 * @author hho
 */
public class NetsuiteOWSubsidiaryPage extends NetsuitePage
{
	public NetsuiteNavigationPane navigationPane;
	protected NetsuiteAddressComponent addressComponent;

	protected By vertexTaxServiceURLTextfieldLocator = By.id("custrecord_taxserviceurl_vt");
	protected By vertexDefaultTaxCodeDropdownLocator = By.id("inpt_custrecord_defaulttaxcode_vt1");
	protected By vertexCompanyCodeDropdownLocator = By.id("custrecord_companycode_vt");
	protected By vertexAddressServiceURLTextfieldLocator = By.id("custrecord_addressserviceurl_vt");
	protected By trustedIDTextfieldLocator = By.id("custrecord_trustedid_vt");
	protected By stateProvinceDropdownLocator = By.id("inpt_dropdownstate4");
	protected By saveButtonLocator = By.id("btn_multibutton_submitter");
	protected By addressFrameLocator = By.id("childdrecord_frame");
	protected By editShippingAddressButtonLocator = By.id("shippingaddress_helper_popup");
	protected By shippingAddressDisplayLocator = By.id("shippingaddress_text");

	public NetsuiteOWSubsidiaryPage( final WebDriver driver )
	{
		super(driver);
		navigationPane = new NetsuiteNavigationPane(driver, this);
		addressComponent = new NetsuiteAddressComponent(driver, this);
	}

	/**
	 * Enters the tax service url
	 *
	 * @param taxServiceURL the tax service url
	 */
	public void enterVertexTaxServiceURL( String taxServiceURL )
	{
		text.enterText(vertexTaxServiceURLTextfieldLocator, taxServiceURL);
	}

	/**
	 * Selects the default tax code
	 *
	 * @param defaultTaxCode the tax code
	 */
	public void selectVertexDefaultTaxCode( String defaultTaxCode )
	{
		setDropdownToValue(vertexDefaultTaxCodeDropdownLocator, defaultTaxCode);
	}

	/**
	 * enters the company code
	 *
	 * @param companyCode the company code
	 */
	public void enterVertexCompanyCode( String companyCode )
	{
		text.enterText(vertexCompanyCodeDropdownLocator, companyCode);
	}

	/**
	 * enters the address service url
	 *
	 * @param addressServiceURL the url
	 */
	public void enterVertexAddressServiceURL( String addressServiceURL )
	{
		text.enterText(vertexAddressServiceURLTextfieldLocator, addressServiceURL);
	}

	/**
	 * Enters the trusted ID
	 *
	 * @param trustedID the trusted ID
	 */
	public void enterTrustedID( String trustedID )
	{
		text.enterText(trustedIDTextfieldLocator, trustedID);
	}

	/**
	 * Selects the state/province
	 *
	 * @param stateProvince the state/province
	 */
	public void selectStateProvince( String stateProvince )
	{
		setDropdownToValue(stateProvinceDropdownLocator, stateProvince);
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
	 * @return the subsidiaries list page
	 */
	public NetsuiteOWSubsidiariesListPage save( )
	{
		click.clickElement(saveButtonLocator);
		return initializePageObject(NetsuiteOWSubsidiariesListPage.class);
	}
}
