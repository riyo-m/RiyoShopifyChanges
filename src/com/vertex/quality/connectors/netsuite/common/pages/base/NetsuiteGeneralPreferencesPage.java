package com.vertex.quality.connectors.netsuite.common.pages.base;

import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Represents the general preferences page
 * Allows the user to configure settings such as turning on or off Vertex
 * Integration
 *
 * @author hho
 */
public abstract class NetsuiteGeneralPreferencesPage extends NetsuitePage
{
	protected By installFlagCheckbox = By.id("custscript_vtinstallflag_vt_fs_inp");
	protected By oneWorldFlagCheckbox = By.id("custscript_oneworldflag_vt_fs_inp");
	protected By canadianLicensingFlagCheckbox = By.id("custscript_canlicense_vt_fs");
	protected By customPreferencesTab = By.id("customlnk");
	protected By trustedIdTextField = By.id("custscript_trustedid_vt");
	protected By companyCodeTextField = By.id("custscript_companycode_vt");
	protected By taxServiceUrlTextField = By.id("custscript_taxserviceurl_vt");
	protected By addressServiceUrlTextField = By.id("custscript_addressserviceurl_vt");
	protected By cutThresholdAmountTextField = By.id("custscript_cut_threshold_amount_vt");
	protected By defaultTaxCodeTextField = By.id("inpt_custscript_taxcode_vt22");
	protected By saveButton = By.id("submitter");

	public NetsuiteGeneralPreferencesPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Clicks on the custom preferences tab
	 */
	public void openCustomPreferencesTab( )
	{
		click.clickElement(customPreferencesTab);
	}

	/**
	 * Unselects the vertex install flag checkbox
	 */
	public void unselectInstallFlag( )
	{
		WebElement installCheckbox = wait.waitForElementPresent(installFlagCheckbox);
		if ( checkbox.isCheckboxChecked(installCheckbox) )
		{
			click.javascriptClick(installCheckbox);
		}
	}

	/**
	 * Selects the vertex install flag checkbox
	 */
	public void selectInstallFlag( )
	{
		WebElement installCheckbox = wait.waitForElementPresent(installFlagCheckbox);
		if ( !checkbox.isCheckboxChecked(installCheckbox) )
		{
			click.javascriptClick(installCheckbox);
		}
	}

	/**
	 * Selects the one world flag checkbox
	 */
	public void selectOneWorldFlag( )
	{
		WebElement oneWorldElement = wait.waitForElementPresent(oneWorldFlagCheckbox);
		if ( !checkbox.isCheckboxChecked(oneWorldElement) )
		{
			click.javascriptClick(oneWorldElement);
		}
	}

	/**
	 * Unselects the vertex one world flag checkbox
	 */
	public void unselectOneWorldFlag( )
	{
		WebElement oneWorldElement = wait.waitForElementPresent(oneWorldFlagCheckbox);
		if ( checkbox.isCheckboxChecked(oneWorldElement) )
		{
			click.javascriptClick(oneWorldElement);
		}
	}

	/**
	 * Unselects the vertex Canadian licensing checkbox
	 */
	public void unselectCanadianLicensing( )
	{
		WebElement canadianLicensingElement = wait.waitForElementPresent(canadianLicensingFlagCheckbox);
		if ( checkbox.isCheckboxChecked(canadianLicensingElement) )
		{
			click.javascriptClick(canadianLicensingElement);
		}
	}

	/**
	 * Selects the vertex Canadian licensing checkbox
	 */
	public void selectCanadianLicensing( )
	{
		WebElement canadianLicensingElement = wait.waitForElementPresent(canadianLicensingFlagCheckbox);
		if ( !checkbox.isCheckboxChecked(canadianLicensingElement) )
		{
			click.javascriptClick(canadianLicensingElement);
		}
	}

	/**
	 * Inputs a trusted id into the trusted id textfield
	 *
	 * @param trustedId the trusted id to input
	 */
	public void enterTrustedId( String trustedId )
	{
		text.enterText(trustedIdTextField, trustedId);
	}

	/**
	 * Inputs a company code into the company code textfield
	 *
	 * @param companyCode the company code to input
	 */
	public void enterCompanyCode( String companyCode )
	{
		text.enterText(companyCodeTextField, companyCode);
	}

	/**
	 * Inputs a taxRate service url into the textfield
	 *
	 * @param taxServiceUrl url to input
	 */
	public void enterTaxServiceUrl( String taxServiceUrl )
	{
		text.enterText(taxServiceUrlTextField, taxServiceUrl);
	}

	/**
	 * Inputs an address service url into the textfield
	 *
	 * @param addressServiceUrl url to input
	 */
	public void enterAddressServiceUrl( String addressServiceUrl )
	{
		text.enterText(addressServiceUrlTextField, addressServiceUrl);
	}

	/**
	 * Enter new Cut Threshold Amount into the textfield
	 *
	 * @param cutThresholdAmount to input
	 */
	public void enterCutThresholdAmount( String cutThresholdAmount )
	{
		text.enterText(cutThresholdAmountTextField, cutThresholdAmount);
	}

	/**
	 * Selects a taxRate code in the default taxRate code dropdown
	 *
	 * @param defaultTaxCode taxRate code to select
	 */
	public void setDefaultTaxCode( String defaultTaxCode )
	{
		setDropdownToValue(defaultTaxCodeTextField, defaultTaxCode);
	}

	/**
	 * Saves the current preferences
	 *
	 * @return the setup manager page
	 */
	public NetsuiteSetupManagerPage savePreferences( )
	{
		click.clickElement(saveButton);
		waitForPageLoad();
		return initializePageObject(NetsuiteSetupManagerPage.class);
	}
}
