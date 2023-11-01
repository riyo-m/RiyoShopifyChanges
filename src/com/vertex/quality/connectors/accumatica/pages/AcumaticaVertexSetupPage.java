package com.vertex.quality.connectors.accumatica.pages;

import com.vertex.quality.connectors.accumatica.pages.base.AcumaticaPostSignOnPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Vertex Setup page actions/methods
 *
 * @author saidulu kodadala
 */
public class AcumaticaVertexSetupPage extends AcumaticaPostSignOnPage
{
	protected By ACCOUNT = By.id("ctl00_phF_form_edAccount");
	protected By VERTEX_SETUP_PASSWORD = By.id("ctl00_phF_form_edPassword");
	protected By TRUSTEDID = By.id("ctl00_phF_form_edLicence");
	protected By TAX_CALCULATION_END_POINT_URL = By.id("ctl00_phF_form_PXTextEdit1");
	protected By TAX_AREA_LOOKUP_END_POINT_URL = By.id("ctl00_phF_form_edUrl");
	protected By VERTEX_TAX_ZONE_ID = By.xpath(
		"//label[text()='Vertex Tax Zone ID:']/..//following-sibling::div//input");
	protected By ACUMATICA_TAX_ZONE_ID = By.xpath(
		"//label[text()='Acumatica Tax Zone ID:']/..//following-sibling::div//input");
	protected By VERTEX_TAX_AGENCY = By.xpath("//label[text()='Vertex Tax Agency:']/..//following-sibling::div//input");
	protected By CONNECTION_SETTINGS_ACTIVE_CHECK_BOX = By.id("ctl00_phF_form_chkIsActive");
	protected By ALWAYS_CHECK_ADDRESS_BEFORE_CALCULATING_TAX_CHECK_BOX = By.xpath(
		"//label[text()='Always Check Address Before Calculating Tax']//preceding-sibling::input");
	protected By AUTO_ACCEPT_ACS_CHECK_BOX = By.xpath("//label[text()='Auto Accept ACS']//preceding-sibling::input");
	protected By ENABLE_LOGGING_CHECK_BOX = By.xpath("//label[text()='Enable Logging']//preceding-sibling::input");
	protected By BRANCH = By.cssSelector(
		"[id*=phF_form_PXGridMapping_dataT]+table td[class='GridRow GridActiveRow GridActiveCell ellipsis']");
	protected By COMPANY_CODE = By.cssSelector(
		"[id*=phF_form_PXGridMapping_dataT]+table td[class='GridRow GridActiveRow ellipsis']");
	protected By TAX_CALUCULATION_TEST_CONNECTION_BUTTON = By.id("ctl00_phF_form_shopRates");
	protected By TAX_AREA_LOOKUP_TEST_CONNECTION_BUTTON = By.id("ctl00_phF_form_PXButton1");
	protected By ERROR_MESSAGE = By.id("frmBottom_lblErrCode");

	public AcumaticaVertexSetupPage( WebDriver driver )
	{
		super(driver);
	}

	public String TAX_AREA_LOOKUP_ENDPOINT_URL = null;
	public String TAX_CALCULATION_ENDPOINT_URL = null;
	public String USERNAME = null;
	public String PASSWORD = null;
	public String TRUSTED_ID = null;
	public String VERTEX_TAXZONEID = null;

	/**
	 * Verify 'active' check box status
	 *
	 * @param expectedState whether the checkbox should be checked
	 */
	public boolean setConnectorActiveCheckbox( boolean expectedState )
	{
		wait.waitForElementDisplayed(CONNECTION_SETTINGS_ACTIVE_CHECK_BOX);
		boolean isChecked = checkbox.isCheckboxChecked(CONNECTION_SETTINGS_ACTIVE_CHECK_BOX);
		if ( isChecked != expectedState )
		{
			click.clickElementCarefully(CONNECTION_SETTINGS_ACTIVE_CHECK_BOX);
		}
		boolean resultStatus = checkbox.isCheckboxChecked(CONNECTION_SETTINGS_ACTIVE_CHECK_BOX);
		return resultStatus;
	}

	/***
	 * Enter account number
	 *
	 * @param account which account number to enter
	 */
	public void setAccountNumber( final String account )
	{
		text.setTextFieldCarefully(ACCOUNT, account);
	}

	/***
	 * Enter password
	 *
	 * @param password
	 */
	public void setPassword( final String password )
	{
		text.setTextFieldCarefully(VERTEX_SETUP_PASSWORD, password);
	}

	/***
	 * Enter trusted id
	 *
	 * @param trustedId
	 */
	public void setTrustedId( final String trustedId )
	{
		text.setTextFieldCarefully(TRUSTEDID, trustedId);
	}

	/***
	 * Enter tax calculation end point url
	 *
	 * @param TaxCalculationEndPointUrl
	 */
	public void setTaxCalculationEndPointUrl( final String TaxCalculationEndPointUrl )
	{
		text.setTextFieldCarefully(TAX_CALCULATION_END_POINT_URL, TaxCalculationEndPointUrl);
	}

	/***
	 * Enter tax area lookup end point url
	 *
	 * @param TaxAreaLookupEndPointUrl
	 */
	public void setTaxAreaLookupEndPointUrl( final String TaxAreaLookupEndPointUrl )
	{
		text.setTextFieldCarefully(TAX_AREA_LOOKUP_END_POINT_URL, TaxAreaLookupEndPointUrl);
	}

	//TODO combobox- maybe source of timing errors later unless rewritten to use setCombobox()

	/**
	 * Enter vertex tax zone id
	 *
	 * @param TaxZoneId
	 */
	public void setVertexTaxZoneId( final String TaxZoneId )
	{
		text.setTextFieldCarefully(VERTEX_TAX_ZONE_ID, TaxZoneId);
	}

	//TODO combobox- maybe source of timing errors later unless rewritten to use setCombobox()

	/**
	 * Enter acumatica tax zone id
	 *
	 * @param TaxZoneId
	 */
	public void setAcumaticaTaxZoneId( final String TaxZoneId )
	{
		text.setTextFieldCarefully(ACUMATICA_TAX_ZONE_ID, TaxZoneId);
	}

	//TODO combobox- maybe source of timing errors later unless rewritten to use setCombobox()

	/**
	 * Enter vertex tax agency
	 *
	 * @param TaxAgency
	 */
	public void setVertexTaxAgency( final String TaxAgency )
	{
		text.setTextFieldCarefully(VERTEX_TAX_AGENCY, TaxAgency);
	}

	/***
	 * Select 'Always check address before calculating tax' check box
	 *
	 * @param expectedState
	 */
	public boolean setAlwaysCheckAddressBeforeCalculatingTaxCheckbox( final boolean expectedState )
	{
		wait.waitForElementDisplayed(ALWAYS_CHECK_ADDRESS_BEFORE_CALCULATING_TAX_CHECK_BOX);
		boolean isChecked = checkbox.isCheckboxChecked(ALWAYS_CHECK_ADDRESS_BEFORE_CALCULATING_TAX_CHECK_BOX);
		if ( isChecked != expectedState )
		{
			click.clickElementCarefully(ALWAYS_CHECK_ADDRESS_BEFORE_CALCULATING_TAX_CHECK_BOX);
		}
		boolean resultStatus = checkbox.isCheckboxChecked(ALWAYS_CHECK_ADDRESS_BEFORE_CALCULATING_TAX_CHECK_BOX);
		return resultStatus;
	}

	/***
	 * Select 'auto accept acs' check box
	 *
	 * @param expectedState
	 */
	public boolean setAutoAcceptAcsCheckbox( final boolean expectedState )
	{
		wait.waitForElementDisplayed(AUTO_ACCEPT_ACS_CHECK_BOX);
		boolean isChecked = checkbox.isCheckboxChecked(AUTO_ACCEPT_ACS_CHECK_BOX);
		if ( isChecked != expectedState )
		{
			click.clickElementCarefully(AUTO_ACCEPT_ACS_CHECK_BOX);
		}
		boolean resultStatus = checkbox.isCheckboxChecked(AUTO_ACCEPT_ACS_CHECK_BOX);
		return resultStatus;
	}

	/***
	 * Select enable logging ' check box
	 *
	 * @param expectedState
	 */
	public boolean setEnableLoggingCheckbox( final boolean expectedState )
	{
		wait.waitForElementDisplayed(ENABLE_LOGGING_CHECK_BOX);
		boolean isChecked = checkbox.isCheckboxChecked(ENABLE_LOGGING_CHECK_BOX);
		if ( isChecked != expectedState )
		{
			click.clickElementCarefully(ENABLE_LOGGING_CHECK_BOX);
		}
		boolean resultStatus = checkbox.isCheckboxChecked(ENABLE_LOGGING_CHECK_BOX);
		return resultStatus;
	}

	/***
	 * Get mapping branch
	 *
	 * @return
	 */
	public String getMappingBranch( )
	{
		wait.waitForElementDisplayed(BRANCH);
		String branch = attribute.getElementAttribute(BRANCH, "textContent");
		return branch;
	}

	/***
	 * Get mapping company code
	 *
	 * @return
	 */
	public String getMappingCompanyCode( )
	{
		wait.waitForElementDisplayed(COMPANY_CODE);
		String companyCode = attribute.getElementAttribute(COMPANY_CODE, "textContent");
		return companyCode;
	}

	/***
	 * Verify 'tax calculation enp point url'
	 */
	public void taxCalculationEndPointUrlTestConnection( )
	{
		click.clickElementCarefully(TAX_CALUCULATION_TEST_CONNECTION_BUTTON, true);
	}

	/***
	 * Verify 'tax area lookup end point url '
	 */
	public void taxAreaLookUpTestConnection( )
	{
		click.clickElementCarefully(TAX_AREA_LOOKUP_TEST_CONNECTION_BUTTON, true);
	}

	/**
	 * Get error message
	 *
	 * @return
	 */
	public String getErrorMessage( )
	{
		wait.waitForElementDisplayed(ERROR_MESSAGE);
		String errorMessage = attribute.getElementAttribute(ERROR_MESSAGE, "text");
		return errorMessage;
	}

	//TODO wrt return value, pojo over string array? or is that overengineering?

	/**
	 * common method for predefined settings
	 *
	 * @param isActive
	 * @param acumaticaTaxZoneId
	 * @param isSelectAlwaysCheckAddressBeforeCalculatingTax
	 * @param isSelectAutoAcceptAcs
	 * @param isSelectEnableLogging
	 *
	 * @return
	 */
	public String[] predefinedSettingsFromVertexSetupPage( boolean isActive, String acumaticaTaxZoneId,
		boolean isSelectAlwaysCheckAddressBeforeCalculatingTax, boolean isSelectAutoAcceptAcs,
		boolean isSelectEnableLogging )
	{
		this.setConnectorActiveCheckbox(isActive);
		this.setAccountNumber(USERNAME);
		this.setPassword(PASSWORD);
		this.setTrustedId(TRUSTED_ID);
		this.setTaxCalculationEndPointUrl(TAX_CALCULATION_ENDPOINT_URL);
		this.setTaxAreaLookupEndPointUrl(TAX_AREA_LOOKUP_ENDPOINT_URL);
		this.setVertexTaxZoneId(VERTEX_TAXZONEID);
		this.setAcumaticaTaxZoneId(acumaticaTaxZoneId);
		this.setVertexTaxAgency(VERTEX_TAXZONEID);
		this.setAlwaysCheckAddressBeforeCalculatingTaxCheckbox(isSelectAlwaysCheckAddressBeforeCalculatingTax);
		this.setAutoAcceptAcsCheckbox(isSelectAutoAcceptAcs);
		this.setEnableLoggingCheckbox(isSelectEnableLogging);
		String branch = this.getMappingBranch();
		String companyCode = this.getMappingCompanyCode();
		String[] array = { branch, companyCode };
		clickSaveButton();
		this.performHealthCheckToValidateURLs();
		return array;
	}

	/**
	 * common method for perform Health Check To Validate URLs
	 */
	public void performHealthCheckToValidateURLs( )
	{
		this.taxCalculationEndPointUrlTestConnection();
		this.taxAreaLookUpTestConnection();
		window.switchToDefaultContent();
	}
}
