package com.vertex.quality.connectors.accumatica.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.accumatica.pages.base.AcumaticaBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Business Accounts page actions/methods
 *
 * @author saidulu kodadala
 */
public class AcumaticaBusinessAccountsPage extends AcumaticaBasePage
{
	protected By NEW_RECORD_PLUS_ICON = By.cssSelector("[icon='AddNew']");
	protected By BUSINESS_ACCOUNT = By.id("ctl00_phF_form_edAcctCD_text");
	protected By BUSINESS_ACCOUNT_NAME = By.id("ctl00_phF_form_edAcctName");
	protected By STATUS = By.cssSelector("[id='ctl00_phF_form_edStatus_dd'] td[text='Active']");
	protected By STATUS_DROPDOWN = By.cssSelector("[id*='ctl00_phF_form_edStatus'] div[icon='DropDownN']");
	protected By DEFAULT_STATUS = By.cssSelector("[id=ctl00_phF_form_edStatus_text]+span+span");
	protected By COMPANY_NAME = By.id("ctl00_phG_tab_t0_frmDefContact_edFullName");
	protected By MAIL = By.id("ctl00_phG_tab_t0_frmDefContact_edEMail_text");
	protected By ACS_CHECK_BOX = By.id("ctl00_phG_tab_t0_frmDefAddress_chkIsValidated");
	protected By ADDRESS_LINE1 = By.id("ctl00_phG_tab_t0_frmDefAddress_edAddressLine1");
	protected By CITY = By.id("ctl00_phG_tab_t0_frmDefAddress_edCity");
	protected By COUNTRY = By.id("ctl00_phG_tab_t0_frmDefAddress_edCountryID_text");
	protected By STATE = By.id("ctl00_phG_tab_t0_frmDefAddress_edState_text");
	protected By ZIPCODE = By.id("ctl00_phG_tab_t0_frmDefAddress_edPostalCode");
	protected By ADDRESS_LINE2 = By.id("ctl00_phG_tab_t0_frmDefAddress_edAddressLine2");
	protected By INDUSTRY_VALUE = By.cssSelector("[text='Industry']+td[class*='GridRow']");
	protected By INDUSTRY_VALUE_TEXT = By.id("_ctl00_phG_tab_t2_PXGridAnswers_lv0_ec_text");
	protected By BUSINESS_ACCOUNT_SEARCH_BOX = By.id("ctl00_phDS_ds_ToolBar_fb_text");

	public AcumaticaBusinessAccountsPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Click on new record icon
	 */
	public void clickOnNewRecordIcon( )
	{
		element.isElementDisplayed(NEW_RECORD_PLUS_ICON);
		click.clickElement(NEW_RECORD_PLUS_ICON);
		waitForPageLoad();
	}

	/**
	 * Enter business account
	 *
	 * @param businessAccount
	 */
	public void setBusinessAccount( String businessAccount )
	{
		element.isElementDisplayed(BUSINESS_ACCOUNT);
		text.selectAllAndInputText(BUSINESS_ACCOUNT, businessAccount);
		waitForPageLoad();
	}

	/**
	 * Enter business account name
	 *
	 * @param businessAccountName
	 */
	public void setBusinessAccountName( String businessAccountName )
	{
		element.isElementDisplayed(BUSINESS_ACCOUNT_NAME);
		text.selectAllAndInputText(BUSINESS_ACCOUNT_NAME, businessAccountName);
		waitForPageLoad();
	}

	/***
	 * Select active check box status
	 * @param status
	 */
	public void selectStatus( final String status )
	{
		element.isElementDisplayed(STATUS_DROPDOWN);
		String actualStatus = attribute.getElementAttribute(DEFAULT_STATUS, "text");
		if ( status.equalsIgnoreCase(actualStatus) )
		{
			VertexLogger.log(String.format("It is in Active status"));
		}
		else
		{
			click.clickElement(STATUS_DROPDOWN);
			element.isElementDisplayed(STATUS);
			click.clickElement(STATUS);
		}
	}

	/**
	 * Get company name
	 *
	 * @return
	 */
	public String getCompanyName( )
	{
		element.isElementDisplayed(COMPANY_NAME);
		String companyName = attribute.getElementAttribute(COMPANY_NAME, "value");
		return companyName;
	}

	/**
	 * Enter Email under "MAIN CONTACT" section on the same screen
	 *
	 * @param mail
	 */
	public void setMail( String mail )
	{
		element.isElementDisplayed(MAIL);
		text.enterText(MAIL, mail);
		waitForPageLoad();
	}

	/***
	 * Select 'ACS' check box
	 * @param flag
	 */
	public boolean isAcsChecked( final boolean flag )
	{
		element.isElementDisplayed(MAIL);
		boolean status = checkbox.isCheckboxChecked(ACS_CHECK_BOX);
		if ( status != flag )
		{
			click.clickElement(ACS_CHECK_BOX);
			waitForPageLoad();
		}
		return status;
	}

	/***
	 * Enter address line1 
	 * @param addressLine1
	 */
	public void setAddressLine1( final String addressLine1 )
	{
		element.isElementDisplayed(ADDRESS_LINE1);
		text.enterText(ADDRESS_LINE1, addressLine1);
	}

	/***
	 * Get address line1
	 */
	public String getAddressLine1( )
	{
		element.isElementDisplayed(ADDRESS_LINE1);
		String addressLine1 = attribute.getElementAttribute(ADDRESS_LINE1, "value");
		return addressLine1;
	}

	/***
	 * Enter address line2 
	 * @param addressLine2
	 */
	public void setAddressLine2( final String addressLine2 )
	{
		element.isElementDisplayed(ADDRESS_LINE2);
		text.enterText(ADDRESS_LINE2, addressLine2);
	}

	/***
	 * Enter city
	 * @param city
	 */
	public void setCity( final String city )
	{
		element.isElementDisplayed(CITY);
		text.enterText(CITY, city);
		text.pressTab(CITY);
	}

	/***
	 * Get city
	 */
	public String getCity( )
	{
		element.isElementDisplayed(CITY);
		String city = attribute.getElementAttribute(CITY, "value");
		return city;
	}

	/***
	 * Enter country
	 * @param country
	 */
	public void setCountry( final String country )
	{
		element.isElementDisplayed(COUNTRY);
		text.enterText(COUNTRY, country);
		text.pressTab(COUNTRY);
	}

	/***
	 * Get country
	 */
	public String getCountry( )
	{
		element.isElementDisplayed(COUNTRY);
		String country = attribute.getElementAttribute(COUNTRY, "value");
		return country;
	}

	/***
	 * Enter state
	 * @param state
	 */
	public void setState( final String state )
	{
		element.isElementDisplayed(STATE);
		text.enterText(STATE, state);
		text.pressTab(STATE);
	}

	/***
	 * Get state
	 */
	public String getState( )
	{
		element.isElementDisplayed(STATE);
		String country = attribute.getElementAttribute(STATE, "value");
		return country;
	}

	/***
	 * Enter zip code
	 * @param zipcode
	 */
	public void setZipCode( final String zipcode )
	{
		element.isElementDisplayed(ZIPCODE);
		text.enterText(ZIPCODE, zipcode);
		text.pressTab(ZIPCODE);
	}

	/***
	 * Get zip code
	 */
	public String getZipCode( )
	{
		element.isElementDisplayed(ZIPCODE);
		String zipcode = attribute.getElementAttribute(ZIPCODE, "value");
		return zipcode;
	}

	/**
	 * Double click on "Value" column for the row item - Industry ( under Attribute column)
	 * Select the drop down option for "Value" column
	 */
	public void setIndustryValue( String value )
	{
		element.isElementDisplayed(INDUSTRY_VALUE);
		WebElement industryValueElem = driver.findElement(INDUSTRY_VALUE);
		click.performDoubleClick(industryValueElem);
		waitForPageLoad();
		element.isElementDisplayed(INDUSTRY_VALUE_TEXT);
		text.enterText(INDUSTRY_VALUE_TEXT, value);
		text.pressTab(INDUSTRY_VALUE_TEXT);
	}

	/**
	 * Set Business account input search box
	 *
	 * @param businessAccount
	 */
	public void setBusinessAccountInSearchBox( String businessAccount )
	{
		element.isElementDisplayed(BUSINESS_ACCOUNT_SEARCH_BOX);
		text.enterText(BUSINESS_ACCOUNT_SEARCH_BOX, businessAccount);
		waitForPageLoad();
	}
}

