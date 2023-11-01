package com.vertex.quality.connectors.accumatica.pages;

import com.vertex.quality.connectors.accumatica.enums.AcumaticaCombobox;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaCreditVerification;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaMainPanelTab;
import com.vertex.quality.connectors.accumatica.pages.base.AcumaticaPostSignOnPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Customers page actions/methods
 *
 * @author saidulu kodadala
 */
public class AcumaticaCustomersPage extends AcumaticaPostSignOnPage
{
	protected By NEW_RECORD_PLUS_ICON = By.cssSelector("[icon='AddNew']");
	protected By CUSTOMER_ID = By.id("ctl00_phF_BAccount_edAcctCD_text");
	protected By CUSTOMER_NAME = By.id("ctl00_phF_BAccount_edAcctName");
	protected By STATUS_DROPDOWN = By.cssSelector("[id*='phF_BAccount_edStatus'] div[icon='DropDownN']");
	protected By DEFAULT_STATUS = By.cssSelector("[id=ctl00_phF_BAccount_edStatus_text]+span+span");
	protected By STATUS = By.xpath("//table[@id='ctl00_phF_BAccount_edStatus_dd']//td[text()='Active']");
	protected By COMPANY_NAME = By.cssSelector("[id$='_DefContact_edFullName']");

	protected By CUSTOMER_CLASS = By.cssSelector("[id$='_edCustomerClassID_text']");
	protected By EMAIL = By.cssSelector("[id$='_DefContact_edEMail_text']");
	protected By APPLY_OVERDUE_CHARGERS_CHECK_BOX = By.cssSelector("[id$='_chkFinChargeApply']");
	protected By MAIN_ACS_CHECK_BOX = By.cssSelector("[id$='_DefAddress_edIsValidated']");
	protected By DELIVERY_ACS_CHECK_BOX = By.cssSelector("[id$='_DefLocation_edIsValidated']");
	protected By ADDRESS_LINE1 = By.cssSelector("[id$='_DefAddress_edAddressLine1']");
	protected By CITY = By.cssSelector("[id$='_DefAddress_edCity']");
	protected By ZIPCODE = By.cssSelector("[id$='_DefAddress_edPostalCode']");
	protected By SHIPPING_ADDRESS_SAME_AS_MAIN_CHECK_BOX = By.cssSelector("[id$='_DefLocation_chkIsMain']");
	protected By ACS_CHECK_BOX_UNDER_SHIPPING_ADDRESS = By.cssSelector("[id$='_DefLocation_edIsValidated']");
	protected By ADDRESS_LINE1_DELEVERY_SETTINGS_SECTION = By.cssSelector("[id$='_DefLocation_edAddressLine1']");
	protected By ADDRESS_LINE2_DELEVERY_SETTINGS_SECTION = By.cssSelector("[id$='_DefLocation_edAddressLine2']");
	protected By CITY_DELEVERY_SETTINGS_SECTION = By.cssSelector("[id$='_DefLocation_edCity']");
	protected By COUNTRY_DELEVERY_SETTINGS_SECTION = By.cssSelector("[id$='_DefLocation_edCountryID_text']");
	protected By STATE_DELEVERY_SETTINGS_SECTION = By.cssSelector("[id$='_DefLocation_edState_text']");
	protected By ZIPCODE_DELEVERY_SETTINGS_SECTION = By.cssSelector("[id$='_DefLocation_edPostalCode']");
	protected By SHIP_VIA = By.cssSelector("[id$='_DefLocation_edCarrierID_text']");
	private static final String SUBMENU_LOCATOR = "//td[contains(@id,'ctl00_phG_tab_tab') and contains(text(),'%s')]";
	protected static final String CREDIT_VERIFICATION_OPTION = "//td[text()='%s']";

	protected By creditVerificationDropdown = By.cssSelector("[id$='_edCreditRule']");
	protected By creditVerificationOptionsContainer = By.cssSelector("[id$='_edCreditRule_dd']");
	protected By creditVerificationOption = By.className("ddItem");

	protected By CLOSE_VERTEX_POSTAL_ADDRESS_POPUP = By.cssSelector("td[id='ctl00_phG_PXSmartPanel13_cap'] div");
	protected By OK_BUTTON = By.id("ctl00_phG_PXSmartPanel13_PXButton11");

	public AcumaticaCustomersPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * this page's main-content iframe blocks access to the navigation panes after you press Save
	 * (because it has multiple main panel tabs?)
	 */
	@Override
	public void clickSaveButton( )
	{
		super.clickSaveButton();

		window.switchToDefaultContent();
	}

	/***
	 * Click on 'Add New Record'
	 */
	public void clickAddNewRecord( )
	{
		wait.waitForElementEnabled(CUSTOMER_ID);
		click.clickElement(NEW_RECORD_PLUS_ICON);
		waitForPageLoad();
		wait.waitForElementDisplayed(CUSTOMER_ID);
	}

	/***
	 * Enter customer id
	 * @param customerId
	 */
	public void setCustomerId( final String customerId )
	{
		text.setTextFieldCarefully(CUSTOMER_ID, customerId, false);
	}

	/**
	 * Enter customer name
	 *
	 * @param customerName
	 */
	public void setCustomerName( final String customerName )
	{
		text.setTextFieldCarefully(CUSTOMER_NAME, customerName, false);
	}

	/**
	 * Select active check box status
	 *
	 * @param status
	 */
	public void selectStatus( final String status )
	{
		wait.waitForElementDisplayed(STATUS_DROPDOWN);
		String actualStatus = text.getElementText(DEFAULT_STATUS);
		if ( !status.equalsIgnoreCase(actualStatus) )
		{
			click.clickElementCarefully(STATUS_DROPDOWN);
			click.clickElementCarefully(STATUS);
		}
	}

	/***
	 * Select - Customer Class pop-up should be displayed
	 * Customer Class = DEFAULT - Customer within NY Area
	 * @param status
	 */
	public void customerClassStatus( final String status )
	{
		wait.waitForElementDisplayed(CUSTOMER_CLASS);
		String actualStatus = text.getElementText(CUSTOMER_CLASS);
		if ( !status.equalsIgnoreCase(actualStatus) )
		{
			text.setTextFieldCarefully(CUSTOMER_CLASS, status, false);
		}
	}

	/***
	 * Get company name
	 * @return
	 */
	public String getCompanyName( )
	{
		wait.waitForElementDisplayed(COMPANY_NAME);
		String companyName = attribute.getElementAttribute(COMPANY_NAME, "value");
		return companyName;
	}

	/***
	 * Enter mail
	 * @param email
	 */
	public void setEmail( final String email )
	{
		text.setTextFieldCarefully(EMAIL, email, false);
	}

	/***
	 * Select 'apply override charges ' check box
	 * @param flag
	 */
	public void isApplyOverdueChargesChecked( final boolean flag )
	{
		wait.waitForElementDisplayed(EMAIL);
		wait.waitForElementDisplayed(APPLY_OVERDUE_CHARGERS_CHECK_BOX);
		boolean status = checkbox.isCheckboxChecked(APPLY_OVERDUE_CHARGERS_CHECK_BOX);
		if ( status != flag )
		{
			click.clickElementCarefully(APPLY_OVERDUE_CHARGERS_CHECK_BOX);
		}
	}

	/**
	 * Select 'ACS' check box
	 *
	 * @param flag
	 */
	public boolean isMainAcsChecked( final boolean flag )
	{
		wait.waitForElementDisplayed(MAIN_ACS_CHECK_BOX);
		boolean status = checkbox.isCheckboxChecked(MAIN_ACS_CHECK_BOX);
		if ( status != flag )
		{
			click.clickElementCarefully(MAIN_ACS_CHECK_BOX);
		}
		return status;
	}

	/**
	 * Select 'ACS' check box
	 *
	 * @param flag
	 */
	public boolean isDeliveryAcsChecked( final boolean flag )
	{
		wait.waitForElementDisplayed(DELIVERY_ACS_CHECK_BOX);
		boolean status = checkbox.isCheckboxChecked(DELIVERY_ACS_CHECK_BOX);
		if ( status != flag )
		{
			click.clickElementCarefully(DELIVERY_ACS_CHECK_BOX);
		}
		return status;
	}

	/***
	 * Enter address line1
	 * @param addressLine1
	 */
	public void setAddressLine1( final String addressLine1 )
	{
		text.setTextFieldCarefully(ADDRESS_LINE1, addressLine1);
	}

	/***
	 * Enter city
	 * @param city
	 */
	public void setCity( final String city )
	{
		text.setTextFieldCarefully(CITY, city);
	}

	/***
	 * Enter country
	 * @param country
	 */
	public void setCountry( final String country )
	{
		setCombobox(AcumaticaCombobox.CUSTOMER_COUNTRY, country);
	}

	/***
	 * Enter state
	 * @param state
	 */
	public void setState( final String state )
	{
		setCombobox(AcumaticaCombobox.CUSTOMER_STATE, state);
	}

	/***
	 * Enter zip code
	 * @param zipcode
	 */
	public void setZipCode( final String zipcode )
	{
		text.setTextFieldCarefully(ZIPCODE, zipcode, false);
	}

	/***
	 * Enter tax zone id
	 * @param taxZoneId
	 */
	public void setTaxZoneId( final String taxZoneId )
	{
		setCombobox(AcumaticaCombobox.CUSTOMER_DEFAULT_TAX_ZONE, taxZoneId);
	}

	/***
	 * Select 'same as main' check box
	 * @param flag
	 */
	public boolean setShippingAddressSameAsMainChecked( final boolean flag )
	{
		wait.waitForElementDisplayed(SHIPPING_ADDRESS_SAME_AS_MAIN_CHECK_BOX);
		boolean status = checkbox.isCheckboxChecked(SHIPPING_ADDRESS_SAME_AS_MAIN_CHECK_BOX);
		if ( status != flag )
		{
			click.clickElementCarefully(SHIPPING_ADDRESS_SAME_AS_MAIN_CHECK_BOX);
		}
		return status;
	}

	/***
	 * Select 'ACS' check box from shipping address section
	 * @param flag
	 */
	public void isAcsCheckedUnderShippingAddressSection( final boolean flag )
	{
		wait.waitForElementEnabled(ACS_CHECK_BOX_UNDER_SHIPPING_ADDRESS);
		boolean status = checkbox.isCheckboxChecked(ACS_CHECK_BOX_UNDER_SHIPPING_ADDRESS);
		if ( status != flag )
		{
			click.clickElementCarefully(ACS_CHECK_BOX_UNDER_SHIPPING_ADDRESS);
		}
	}

	/***
	 * Get address line1
	 * @return
	 */
	public String getAddressLine1( )
	{
		String addressLine1 = attribute.getElementAttribute(ADDRESS_LINE1_DELEVERY_SETTINGS_SECTION, "value");
		return addressLine1;
	}

	/***
	 * Get address line2
	 * @return
	 */
	public String getAddressLine2( )
	{
		String addressLine2 = attribute.getElementAttribute(ADDRESS_LINE2_DELEVERY_SETTINGS_SECTION, "value");
		return addressLine2;
	}

	/***
	 * Get city
	 * @return
	 */
	public String getCity( )
	{
		String city = attribute.getElementAttribute(CITY_DELEVERY_SETTINGS_SECTION, "value");
		return city;
	}

	/***
	 * Get country
	 * @return
	 */
	public String getCountry( )
	{
		String country = attribute.getElementAttribute(COUNTRY_DELEVERY_SETTINGS_SECTION, "value");
		return country;
	}

	/***
	 * Get state
	 * @return
	 */
	public String getState( )
	{
		String state = attribute.getElementAttribute(STATE_DELEVERY_SETTINGS_SECTION, "value");
		return state;
	}

	/***
	 * Get zip code
	 * @return
	 */
	public String getZipCode( )
	{
		String zipCode = attribute.getElementAttribute(ZIPCODE_DELEVERY_SETTINGS_SECTION, "value");
		return zipCode;
	}

	/***
	 * Click on 'OK' button
	 */
	public void clickOKButton( )
	{
		click.clickElementCarefully(OK_BUTTON);
	}

	/***
	 * Close vertex postal address pop up
	 */
	public void closeVertexPostalAddressPopup( )
	{
		click.clickElementCarefully(CLOSE_VERTEX_POSTAL_ADDRESS_POPUP);
	}

	/***
	 * Select credit verification option
	 * @param option
	 */
	public void selectCreditVerification( final String option )
	{
		By CREDIT = By.xpath(String.format(CREDIT_VERIFICATION_OPTION, option));
		click.clickElementCarefully(CREDIT);
		click.clickElementCarefully(CREDIT);
	}

	/**
	 * selects a credit verification option
	 *
	 * @param verificationOption which credit verification option to select
	 */
	public void selectCreditVerification( final AcumaticaCreditVerification verificationOption )
	{
		WebElement verificationDropdown = wait.waitForElementEnabled(creditVerificationDropdown);
		click.clickElementCarefully(verificationDropdown);
		WebElement creditVerificationOption = getCreditVerificationOption(verificationOption);
		click.clickElementCarefully(creditVerificationOption);
	}

	/**
	 * retrieves the given verification method's option in the dropdown of credit verification methods
	 *
	 * @param verificationMethod which credit verification's option to retrieve
	 *
	 * @return the given verification method's option in the dropdown
	 */
	protected WebElement getCreditVerificationOption( final AcumaticaCreditVerification verificationMethod )
	{
		WebElement verificationElem = null;

		WebElement optionsContainer = wait.waitForElementPresent(creditVerificationOptionsContainer);

		List<WebElement> options = wait.waitForAllElementsEnabled(creditVerificationOption, optionsContainer);

		verificationElem = element.selectElementByText(options, verificationMethod.getValue());

		return verificationElem;
	}

	/***
	 * Enter ship via
	 * @param shipVia
	 */
	public void setShipVia( final String shipVia )
	{
		text.setTextFieldCarefully(SHIP_VIA, shipVia);
	}

	/***
	 * click sub menu option (which means selecting tab with in the page)
	 * @param menuOption
	 */
	public void clickSubMenu( final AcumaticaMainPanelTab menuOption )
	{
		By MENU = By.xpath(String.format(SUBMENU_LOCATOR, menuOption));
		wait.waitForElementDisplayed(MENU);
		//TODO make generic method
		executeJs("scroll(0, -250);");

		click.clickElementCarefully(MENU);
	}

	/***
	 * click sub menu option (which means selecting tab with in the page)
	 * @param menuOption
	 */
	public void clickSubMenu( String menuOption )
	{
		//TODO get rid of this method

		By MENU = By.xpath(String.format(SUBMENU_LOCATOR, menuOption));
		wait.waitForElementDisplayed(MENU);
		//TODO make generic method
		executeJs("scroll(0, -250);");

		click.clickElementCarefully(MENU);
	}
}
