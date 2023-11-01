package com.vertex.quality.connectors.accumatica.pages;

import com.vertex.quality.connectors.accumatica.pages.base.AcumaticaBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Cash Sales page actions/methods
 *
 * @author Saidulu Kodadala
 */
public class AcumaticaCashSalesPage extends AcumaticaBasePage
{
	protected By TYPE = By.cssSelector("input+span[text='Cash Sale']");
	protected By REFERENCE_NBR = By.id("ctl00_phF_form_edRefNbr_text");
	protected By CUSTOMER = By.id("ctl00_phF_form_edCustomerID_text");
	protected By LOCATION = By.id("ctl00_phF_form_edCustomerLocationID_text");
	protected By NEW_ROW_PLUS_ICON = By.cssSelector("[id*='phG_tab_t0_grid_at_tlb'] [icon='RecordAdd']");
	protected By INVENTORY_ID = By.id("_ctl00_phG_tab_t0_grid_lv0_edInventoryID_text");
	protected By QUANTITY = By.id("_ctl00_phG_tab_t0_grid_lv0_edOrderQty");
	protected By UNIT_PRICE = By.id("_ctl00_phG_tab_t0_grid_lv0_edUnitPrice");
	protected By BRANCH = By.id("ctl00_phG_tab_t1_edBranchID_text");
	protected By CUSTOMER_TAX_ZONE = By.id("ctl00_phG_tab_t1_edTaxZoneID_text");
	protected By ADDRESS_LINE1 = By.id("ctl00_phG_tab_t2_Billing_Address_edAddressLine1");
	protected By ADDRESS_LINE2 = By.id("ctl00_phG_tab_t2_Billing_Address_edAddressLine2");
	protected By CITY = By.id("ctl00_phG_tab_t2_Billing_Address_edCity");
	protected By COUNTRY = By.id("ctl00_phG_tab_t2_Billing_Address_edCountryID_text");
	protected By STATE = By.id("ctl00_phG_tab_t2_Billing_Address_edState_text");
	protected By ZIPCODE = By.id("ctl00_phG_tab_t2_Billing_Address_edPostalCode");
	protected By OVERRIDE_ADDRESS_CHECKBOX = By.id("ctl00_phG_tab_t2_Billing_Address_chkOverrideAddress");
	protected By ACS_CHECKBOX = By.id("ctl00_phG_tab_t2_Billing_Address_edIsValidated");
	protected By DETAILS_TOTAL = By.id("ctl00_phF_form_edCuryLineTotal");
	protected By TAX_TOTAL = By.id("ctl00_phF_form_edCuryTaxTotal");
	protected By BALANCE = By.id("ctl00_phF_form_edCuryDocBal");
	protected By PAYMENT_AMOUNT = By.id("ctl00_phF_form_edCuryOrigDocAmt");

	public AcumaticaCashSalesPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Get type
	 *
	 * @return
	 */
	public String getType( )
	{
		element.isElementDisplayed(TYPE);
		String type = attribute.getElementAttribute(TYPE, "value");
		return type;
	}

	/**
	 * Get referenceNbr
	 *
	 * @return
	 */
	public String getReferenceNbr( )
	{
		element.isElementDisplayed(REFERENCE_NBR);
		String referenceNbr = attribute.getElementAttribute(REFERENCE_NBR, "value");
		return referenceNbr;
	}

	/**
	 * Set Customer
	 */
	public void setCustomer( String customer )
	{
		waitForPageLoad();
		element.isElementDisplayed(CUSTOMER);
		text.enterText(CUSTOMER, customer);
		text.pressTab(CUSTOMER);
	}

	/**
	 * Get Location
	 *
	 * @return
	 */
	public String getLocation( )
	{
		element.isElementDisplayed(LOCATION);
		String location = attribute.getElementAttribute(LOCATION, "value");
		return location;
	}

	/***
	 * Click plus icon for add new record
	 */
	public void clickPlusIconForAddNewRecord( )
	{
		element.isElementDisplayed(NEW_ROW_PLUS_ICON);
		click.clickElement(NEW_ROW_PLUS_ICON);
	}

	/***
	 * Enter inventory id
	 * @param inventoryId
	 */
	public void setInventoryId( final String inventoryId )
	{
		element.isElementDisplayed(INVENTORY_ID);
		text.enterText(INVENTORY_ID, inventoryId);
	}

	/***
	 * Enter quantity
	 * @param quantity
	 */
	public void setQuantity( final String quantity )
	{
		text.enterText(QUANTITY, quantity);
	}

	/***
	 * Enter unit price
	 * @param unitPrice
	 */
	public void setUnitPrice( final String unitPrice )
	{
		element.isElementDisplayed(UNIT_PRICE);
		text.enterText(UNIT_PRICE, unitPrice);
		waitForPageLoad();
	}

	/**
	 * Get branch from financial tab
	 */
	public String getBranch( )
	{
		waitForPageLoad();
		element.isElementDisplayed(BRANCH);
		String branch = attribute.getElementAttribute(BRANCH, "value");
		return branch;
	}

	/**
	 * Get Customer tax zone from financial tab
	 */
	public String getCustomerTaxZone( )
	{
		waitForPageLoad();
		element.isElementDisplayed(CUSTOMER_TAX_ZONE);
		String customerTaxZone = attribute.getElementAttribute(CUSTOMER_TAX_ZONE, "value");
		return customerTaxZone;
	}

	/**
	 * Get Address Line1
	 */
	public String getAddressLine1( )
	{
		waitForPageLoad();
		String addressLine1 = attribute.getElementAttribute(ADDRESS_LINE1, "value");
		return addressLine1;
	}

	/**
	 * Get Address Line2
	 */
	public String getAddressLine2( )
	{
		waitForPageLoad();
		String addressLine2 = attribute.getElementAttribute(ADDRESS_LINE2, "value");
		return addressLine2;
	}

	/**
	 * Get city
	 */
	public String getCity( )
	{
		waitForPageLoad();
		String city = attribute.getElementAttribute(CITY, "value");
		return city;
	}

	/**
	 * Get country
	 */
	public String getCountry( )
	{
		waitForPageLoad();
		String country = attribute.getElementAttribute(COUNTRY, "value");
		return country;
	}

	/**
	 * Get state
	 */
	public String getState( )
	{
		waitForPageLoad();
		String state = attribute.getElementAttribute(STATE, "value");
		return state;
	}

	/**
	 * Get zipCode
	 */
	public String getZipCode( )
	{
		waitForPageLoad();
		String zipCode = attribute.getElementAttribute(ZIPCODE, "value");
		return zipCode;
	}

	/***
	 * Verify 'Override' check box
	 * @param flag
	 */
	public boolean verifyOverrideFromBillingAddressTab( final boolean flag )
	{
		boolean status = checkbox.isCheckboxChecked(OVERRIDE_ADDRESS_CHECKBOX);
		if ( status != flag )
		{
			click.clickElement(OVERRIDE_ADDRESS_CHECKBOX);
			waitForPageLoad();
		}
		boolean resultStatus = checkbox.isCheckboxChecked(OVERRIDE_ADDRESS_CHECKBOX);
		return resultStatus;
	}

	/***
	 * Verify 'ACS' check box
	 * @param flag
	 */
	public boolean verifyAcsFromBillingAddressTab( final boolean flag )
	{
		boolean status = checkbox.isCheckboxChecked(ACS_CHECKBOX);
		if ( status != flag )
		{
			click.clickElement(ACS_CHECKBOX);
			waitForPageLoad();
		}
		boolean resultStatus = checkbox.isCheckboxChecked(ACS_CHECKBOX);
		return resultStatus;
	}

	/**
	 * Get Detail Total
	 *
	 * @return
	 */
	public String getDetailTotal( )
	{
		element.isElementDisplayed(DETAILS_TOTAL);
		String detailsTotal = attribute.getElementAttribute(DETAILS_TOTAL, "value");
		return detailsTotal;
	}

	/**
	 * Get Tax Total
	 *
	 * @return
	 */
	public String getTaxTotal( )
	{
		element.isElementDisplayed(TAX_TOTAL);
		String taxTotal = attribute.getElementAttribute(TAX_TOTAL, "value");
		return taxTotal;
	}

	/**
	 * Get Balance
	 *
	 * @return
	 */
	public String getBalance( )
	{
		element.isElementDisplayed(BALANCE);
		String balance = attribute.getElementAttribute(BALANCE, "value");
		return balance;
	}

	/**
	 * Get Payment Amount
	 *
	 * @return
	 */
	public String getPaymentAmount( )
	{
		element.isElementDisplayed(PAYMENT_AMOUNT);
		String paymentAmount = attribute.getElementAttribute(PAYMENT_AMOUNT, "value");
		return paymentAmount;
	}
}

