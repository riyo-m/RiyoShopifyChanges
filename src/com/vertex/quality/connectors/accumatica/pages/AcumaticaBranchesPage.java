package com.vertex.quality.connectors.accumatica.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.accumatica.pages.base.AcumaticaBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Branches page actions/methods
 *
 * @author saidulu kodadala
 */
public class AcumaticaBranchesPage extends AcumaticaBasePage
{
	protected By BRANCH_ID = By.id("ctl00_phF_PXFormView1_edAcctCD_text");
	protected By BRANCH_NAME = By.id("ctl00_phF_PXFormView1_edAcctName");
	protected By ACTIVE_CHECK_BOX = By.cssSelector("[id*=phF_form_PXGridMapping_dataT]+table td input");
	protected By COMPANY_NAME = By.id("ctl00_phG_tab_t0_DefContact_edFullName");
	protected By DEFAULT_COUNTRY = By.id("ctl00_edBranchCountryID_text");
	protected By SAME_AS_MAIN_CHECK_BOX = By.id("ctl00_frmDefLocation_chkIsMain");
	protected By ACS_CHECK_BOX = By.id("ctl00_frmDefLocation_edIsValidated");
	protected By ADDRESS_CLEANSING_DISABLE_STATE = By.cssSelector(
		"[data-cmd='VertexAddressCleansing']  [class='toolBtnDisabled']");
	protected By ADDRESS_CLEANSING_ENABLE_STATE = By.cssSelector("[data-cmd='VertexAddressCleansing']");
	protected By ADDRESS_LINE_1_FROM_DELIVERY_SETTING = By.id("ctl00_frmDefLocation_edAddressLine1");
	protected By ADDRESS_LINE_2_FROM_DELIVERY_SETTING = By.id("ctl00_frmDefLocation_edAddressLine2");
	protected By TAX_ZONE_ID = By.id("ctl00_phG_tab_t1_frmDefLocation_edVTaxZoneID_text");
	protected By CITY = By.id("ctl00_phG_tab_t1_frmDefLocation_edCity");
	protected By COUNTRY = By.id("ctl00_phG_tab_t1_frmDefLocation_edCountryID_text");
	protected By STATE = By.id("ctl00_phG_tab_t1_frmDefLocation_edState_text");
	protected By ZIPCODE = By.id("ctl00_phG_tab_t1_frmDefLocation_edPostalCode");

	public AcumaticaBranchesPage( WebDriver driver )
	{
		super(driver);
	}

	/***
	 * Enter branch ID into text box
	 * @param branchId
	 */
	public void setBranchID( final String branchId )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(BRANCH_ID);
		text.enterText(BRANCH_ID, branchId);
		waitForPageLoad();
	}

	/***
	 * Enter branch name
	 * @param branchName
	 */
	public void setBranchName( final String branchName )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(BRANCH_NAME);
		text.enterText(BRANCH_NAME, branchName);
		waitForPageLoad();
	}

	/***
	 * Select active check box
	 * @param flag
	 */
	public void selectActiveCheckBox( final boolean flag )
	{
		boolean status = checkbox.isCheckboxChecked(ACTIVE_CHECK_BOX);
		if ( status != flag )
		{
			click.clickElement(ACTIVE_CHECK_BOX);
			waitForPageLoad();
		}
	}

	/***
	 * Get company name and it will return company name
	 * @return
	 */
	public String getCompanyName( )
	{
		String companyName = attribute.getElementAttribute(COMPANY_NAME, "value");
		return companyName;
	}

	/***
	 * Get Tax Zone ID
	 * @return
	 */
	public String getTaxZoneID( )
	{
		String taxZoneID = attribute.getElementAttribute(TAX_ZONE_ID, "value");
		return taxZoneID;
	}

	/***
	 * Enter default country
	 * @param country
	 */
	public void setDefaultCountry( final String country )
	{
		text.enterText(DEFAULT_COUNTRY, country);
	}

	/***
	 * Select 'same as main' check box
	 * @param flag
	 */
	public boolean isSameAsMainCheckedFromBranchesPage( final boolean flag )
	{
		boolean status = checkbox.isCheckboxChecked(SAME_AS_MAIN_CHECK_BOX);
		if ( status != flag )
		{
			click.clickElement(SAME_AS_MAIN_CHECK_BOX);
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

	/***
	 * Verify address cleansing
	 */
	public void verifyAddressCleansing( )
	{
		if ( element.isElementDisplayed(ADDRESS_CLEANSING_DISABLE_STATE) )
		{
			VertexLogger.log("Address cleansing option in disable state", AcumaticaBranchesPage.class);
		}
		else
		{
			VertexLogger.log("Address cleansing option in enable state", AcumaticaBranchesPage.class);
		}
	}

	/***
	 * Click on address cleansing option
	 */
	public void clickAddressCleansing( )
	{
		click.clickElement(ADDRESS_CLEANSING_ENABLE_STATE);
		waitForPageLoad();
	}

	/**
	 * Get Address Line1
	 */
	public String getAddressLine1( )
	{
		wait.waitForElementEnabled(ADDRESS_LINE_1_FROM_DELIVERY_SETTING);
		String addressLine1 = attribute.getElementAttribute(ADDRESS_LINE_1_FROM_DELIVERY_SETTING, "value");
		return addressLine1;
	}

	/**
	 * Get Address Line2
	 */
	public String getAddressLine2( )
	{
		wait.waitForElementEnabled(ADDRESS_LINE_2_FROM_DELIVERY_SETTING);
		String addressLine2 = attribute.getElementAttribute(ADDRESS_LINE_2_FROM_DELIVERY_SETTING, "value");
		return addressLine2;
	}

	/**
	 * Get city
	 */
	public String getCity( )
	{
		wait.waitForElementEnabled(CITY);
		String city = attribute.getElementAttribute(CITY, "value");
		return city;
	}

	/**
	 * Get country
	 */
	public String getCountry( )
	{
		wait.waitForElementEnabled(COUNTRY);
		String country = attribute.getElementAttribute(COUNTRY, "value");
		return country;
	}

	/**
	 * Get state
	 */
	public String getState( )
	{
		wait.waitForElementEnabled(STATE);
		String state = attribute.getElementAttribute(STATE, "value");
		return state;
	}

	/**
	 * Get zipCode
	 */
	public String getZipCode( )
	{
		wait.waitForElementEnabled(ZIPCODE);
		String zipCode = attribute.getElementAttribute(ZIPCODE, "value");
		return zipCode;
	}
}
