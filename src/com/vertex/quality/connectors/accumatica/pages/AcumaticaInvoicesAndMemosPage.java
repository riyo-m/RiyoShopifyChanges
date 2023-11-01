package com.vertex.quality.connectors.accumatica.pages;

import com.vertex.quality.connectors.accumatica.pages.base.AcumaticaBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author Saidulu kodadala
 * Invoices and memos page actions/methods
 */
public class AcumaticaInvoicesAndMemosPage extends AcumaticaBasePage
{
	protected By CUSTOMER = By.id("ctl00_phF_form_edCustomerID_text");
	protected By TYPE = By.id("ctl00_phF_form_edDocType_text");
	protected By REFERENCE_NBR = By.id("ctl00_phF_form_edRefNbr_text");
	protected By BRANCH = By.id("_ctl00_phG_tab_t0_grid_lv0_edBranchID_text");
	protected By INVENTORY = By.id("_ctl00_phG_tab_t0_grid_lv0_edInventoryID_text");
	protected By QUANTITY = By.id("_ctl00_phG_tab_t0_grid_lv0_edQty");
	protected By UNIT_PRICE = By.id("_ctl00_phG_tab_t0_grid_lv0_edCuryUnitPrice");
	protected By FINANANCE_DETAILS_BRANCH = By.id("ctl00_phG_tab_t1_edBranchID_text");
	protected By CUSTOMER_TAX_ZONE = By.id("ctl00_phG_tab_t1_edTaxZoneID_text");
	protected By OVERRIDE_ADDRESS_CHECK_BOX = By.id("ctl00_phG_tab_t2_Billing_Address_chkOverrideAddress");
	protected By ACS_CHECK_BOX = By.id("ctl00_phG_tab_t2_Billing_Address_edIsValidated");
	protected By ADDRESS_LINE_1 = By.id("ctl00_phG_tab_t2_Billing_Address_edAddressLine1");
	protected By ADDRESS_LINE_2 = By.id("ctl00_phG_tab_t2_Billing_Address_edAddressLine2");
	protected By CITY = By.id("ctl00_phG_tab_t2_Billing_Address_edCity");
	protected By COUNTRY = By.id("ctl00_phG_tab_t2_Billing_Address_edCountryID_text");
	protected By STATE = By.id("ctl00_phG_tab_t2_Billing_Address_edState_text");
	protected By ZIPCODE = By.id("ctl00_phG_tab_t2_Billing_Address_edPostalCode");

	public AcumaticaInvoicesAndMemosPage( WebDriver driver )
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
	 * Get reference NBR
	 */
	public String getReferenceNBR( )
	{
		String referenceNBR = attribute.getElementAttribute(REFERENCE_NBR, "value");
		return referenceNBR;
	}

	/**
	 * Get branch
	 */
	public String getBranch( )
	{
		String branch = attribute.getElementAttribute(BRANCH, "value");
		text.pressTab(BRANCH);
		return branch;
	}

	/**
	 * Enter inventory value
	 *
	 * @param inventory
	 */
	public void setInventory( String inventory )
	{
		text.enterText(INVENTORY, inventory);
		text.pressTab(INVENTORY);
	}

	/**
	 * Enter quantity value
	 *
	 * @param quantity
	 */
	public void setQuantity( String quantity )
	{
		text.enterText(QUANTITY, quantity);
	}

	/**
	 * Enter Unit price value
	 *
	 * @param unitPrice
	 */
	public void setUnitPrice( String unitPrice )
	{
		text.enterText(UNIT_PRICE, unitPrice);
	}

	/**
	 * Get branch value from Finance Details tab
	 *
	 * @return
	 */
	public String getFinanceDetailBranch( )
	{
		String branch = attribute.getElementAttribute(FINANANCE_DETAILS_BRANCH, "value");
		return branch;
	}

	/**
	 * Get Customer tax zone from Finance Details tab
	 *
	 * @return
	 */
	public String getCustomerTaxZone( )
	{
		String customerTaxZone = attribute.getElementAttribute(CUSTOMER_TAX_ZONE, "value");
		return customerTaxZone;
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
