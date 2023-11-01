package com.vertex.quality.connectors.accumatica.pages;

import com.vertex.quality.connectors.accumatica.pages.base.AcumaticaBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Shipment page actions/methods
 *
 * @author Saidulu kodadala
 */
public class AcumaticaShipmentsPage extends AcumaticaBasePage
{
	protected By CUSTOMER = By.id("ctl00_phF_form_edCustomerID_text");
	protected By TYPE = By.id("ctl00_phF_form_edShipmentType_text");
	protected By SHIPMENT_NBR = By.id("ctl00_phF_form_edShipmentNbr_text");
	protected By WAREHOUSE = By.id("ctl00_phF_form_edSiteID_text");
	protected By OVERRIDE_ADDRESS_CHECK_BOX = By.id("ctl00_phG_tab_t2_formB_chkOverrideAddress");
	protected By ACS_CHECK_BOX = By.id("ctl00_phG_tab_t2_formB_chkIsValidated");
	protected By ADDRESS_LINE_1 = By.id("ctl00_phG_tab_t2_formB_edAddressLine1");
	protected By ADDRESS_LINE_2 = By.id("ctl00_phG_tab_t2_formB_edAddressLine2");
	protected By CITY = By.id("ctl00_phG_tab_t2_formB_edCity");
	protected By COUNTRY = By.id("ctl00_phG_tab_t2_formB_edCountryID_text");
	protected By STATE = By.id("ctl00_phG_tab_t2_formB_edState_text");
	protected By ZIPCODE = By.id("ctl00_phG_tab_t2_formB_edPostalCode");

	public AcumaticaShipmentsPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Set customer which we created in customer page
	 *
	 * @param customer
	 */
	public void setCustomer( String customer )
	{
		text.enterText(CUSTOMER, customer);
		waitForPageLoad();
	}

	/**
	 * Get order type status
	 *
	 * @return
	 */
	public String getOrderType( )
	{
		String type = attribute.getElementAttribute(TYPE, "value");
		return type;
	}

	/**
	 * Get Shipment NBR
	 */
	public String getShipmentNBR( )
	{
		String shipmentNBR = attribute.getElementAttribute(SHIPMENT_NBR, "value");
		return shipmentNBR;
	}

	/**
	 * Set warehouse
	 *
	 * @param warehouse
	 */
	public void setWarehouse( String warehouse )
	{
		text.enterText(WAREHOUSE, warehouse);
		waitForPageLoad();
	}

	/***
	 * Select 'Override Address' check box
	 * @param flag
	 */
	public boolean isOverrideAddressCheckedFromBranchesPage( final boolean flag )
	{
		boolean status = checkbox.isCheckboxChecked(OVERRIDE_ADDRESS_CHECK_BOX);
		if ( status != flag )
		{
			click.clickElement(OVERRIDE_ADDRESS_CHECK_BOX);
			waitForPageLoad();
		}
		return status;
	}

	/***
	 * Select 'ACS' check box
	 * @param flag
	 */
	public boolean isACSCheckedFromBranchesPage( final boolean flag )
	{
		boolean status = checkbox.isCheckboxChecked(ACS_CHECK_BOX);
		if ( status != flag )
		{
			click.clickElement(ACS_CHECK_BOX);
			waitForPageLoad();
		}
		return status;
	}

	/**
	 * Get Address Line1 from Billing Address tab
	 */
	public String getAddressLine1( )
	{
		waitForPageLoad();
		String addressLine1 = attribute.getElementAttribute(ADDRESS_LINE_1, "value");
		waitForPageLoad();
		return addressLine1;
	}

	/**
	 * Get Address Line2 from Billing Address tab
	 */
	public String getAddressLine2( )
	{
		waitForPageLoad();
		String addressLine2 = attribute.getElementAttribute(ADDRESS_LINE_2, "value");
		waitForPageLoad();
		return addressLine2;
	}

	/**
	 * Get city from Billing Address tab
	 */
	public String getCity( )
	{
		waitForPageLoad();
		String city = attribute.getElementAttribute(CITY, "value");
		waitForPageLoad();
		return city;
	}

	/**
	 * Get country from Billing Address tab
	 */
	public String getCountry( )
	{
		waitForPageLoad();
		String country = attribute.getElementAttribute(COUNTRY, "value");
		waitForPageLoad();
		return country;
	}

	/**
	 * Get state from Billing Address tab
	 */
	public String getState( )
	{
		waitForPageLoad();
		String state = attribute.getElementAttribute(STATE, "value");
		waitForPageLoad();
		return state;
	}

	/**
	 * Get zipCode from Billing Address tab
	 */
	public String getZipCode( )
	{
		waitForPageLoad();
		String zipCode = attribute.getElementAttribute(ZIPCODE, "value");
		waitForPageLoad();
		return zipCode;
	}
}
