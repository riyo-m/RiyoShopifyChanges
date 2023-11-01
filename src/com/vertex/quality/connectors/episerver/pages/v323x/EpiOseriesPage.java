package com.vertex.quality.connectors.episerver.pages.v323x;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.episerver.pages.EpiOseriesInvocingPage;
import com.vertex.quality.connectors.episerver.pages.EpiOseriesSettingsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Episerver Administration Oseries Common page - contains all re-usable methods
 * specific to this page.
 */
public class EpiOseriesPage extends VertexPage
{
	public EpiOseriesPage( WebDriver driver )
	{
		super(driver);
	}

	protected By INVOICING_TAB = By.linkText("Invoicing");
	protected By SETTINGS_TAB = By.linkText("Settings");
	protected By CONNECTOR_CIRCLE = By.id("FullRegion_SpanStatus");
	protected By REGION = By.id("FullRegion_InfoFrame");
	protected By REFRESH_STATUS_BUTTON = By.cssSelector("input[class*='Refresh'][type='submit']");
	protected By CORE_VERSION_NUMBER = By.id("FullRegion_CoreVersionLabel");
	protected By ADAPTER_VERSION_NUMBER = By.id("FullRegion_AdapterVersionLabel");
	protected By TAX_CALC_URL_INPUT = By.id("FullRegion_CalculationEndpointTextBox");
	protected By ADDRESS_URL_INPUT = By.id("FullRegion_AddressEndpointTextBox");
	protected By SAVE_SETTING_BUTTON = By.id("FullRegion_VertexConfigurationSaveButton");
	protected By SUCCESS_MESSAGE = By.id("FullRegion_SystemMessage");
    protected By GENERATE_INVOICE_CHECKBOX = By.id("FullRegion_AutomaticInvoicingCheckbox");
	protected By SAVE_INVOICE_BUTTON = By.id("FullRegion_SaveInvoicingButton");
	protected By COMPANY_NAME_TEXT_BOX = By.xpath(".//input[@id='FullRegion_CompanyNameTextBox']");
	protected By SAVE_COMPANY_BUTTON = By.xpath("(.//input[@value='Save'])[1]");

	/**
	 * This method is used to switch to Region frame
	 */
	public void switchToRegionFrame( )
	{
		driver
			.switchTo()
			.defaultContent();
		wait.waitForElementPresent(REGION, 30);
		window.switchToFrame(REGION, 60);
	}

	/**
	 * This method is used to validate the connector tool tip
	 */
	public void validateConnectorTooltip( String region )
	{
		this.switchToRegionFrame();
		wait.waitForElementPresent(CONNECTOR_CIRCLE);
		String tool_tip_text = attribute.getElementAttribute(CONNECTOR_CIRCLE, "title");
		if ( tool_tip_text.contains(region) )
		{
			VertexLogger.log("Connector Information is as Expected");
		}
		else
		{
			VertexLogger.log(
				String.format("Connector Information is not as Expected, but displaying as  '%s'", tool_tip_text));
		}
	}

	/**
	 * This method is used to retrieve connector status
	 *
	 * @return Connector status
	 */
	public String getVertexOseriesConnectorStatus( )
	{
		this.switchToRegionFrame();
		wait.waitForElementPresent(CONNECTOR_CIRCLE);
		return attribute.getElementAttribute(CONNECTOR_CIRCLE, "class");
	}

	/**
	 * This method is used to retrieve Core/Adapter Version
	 *
	 * @return Corresponding Connector's "Core/Adapter" Version
	 */
	public String getVersion( String version_type )
	{
		String version = null;
		this.switchToRegionFrame();
		if ( version_type == "Core Version" )
		{
			wait.waitForElementDisplayed(CORE_VERSION_NUMBER);
			version = text.getElementText(CORE_VERSION_NUMBER);
		}
		else if ( version_type == "Adapter Version" )
		{
			wait.waitForElementDisplayed(ADAPTER_VERSION_NUMBER);
			version = text.getElementText(ADAPTER_VERSION_NUMBER);
		}
		else
		{
			VertexLogger.log(String.format("Provided Version Type %s is not valid", version_type));
		}

		return version;
	}

	/**
	 * This method is used to click refresh button to check the connector status
	 */
	public void clickRefreshStatusButton( )
	{
		this.switchToRegionFrame();
		waitForPageLoad();
		wait.waitForElementDisplayed(REFRESH_STATUS_BUTTON, 120);
		click.clickElement(REFRESH_STATUS_BUTTON);
		waitForPageLoad();
	}

	/**
	 * This method is used to retrieve connector status and compare
	 *
	 * @return True or False
	 */
	public boolean validateVertexOSeriesConnectorStatus( )
	{
		this.clickRefreshStatusButton();
		String status = this.getVertexOseriesConnectorStatus();
		if ( status != null )
		{
			status = status
				.toLowerCase()
				.trim();
			if ( status.contains("good") )
			{
				VertexLogger.log("Vertex O Series Connector status is Good (i.e. Circle is in Green color)");
				return true;
			}
			else if ( status.contains("bad") )
			{
				VertexLogger.log("Vertex O Series Connector status is Bad (i.e. Circle is in Red color)");
			}
			else
			{
				VertexLogger.log(String.format("Vertex O Series Connector status: '%s'", status), VertexLogLevel.WARN);
			}
		}
		driver
			.switchTo()
			.defaultContent();
		return false;
	}

	/**
	 * Changes the Company Name
	 *
	 * @param companyName Company which is to be set
	 */
	public void changeCompanyName(String companyName) {
		waitForPageLoad();
		this.switchToRegionFrame();
		WebElement company = wait.waitForElementPresent(COMPANY_NAME_TEXT_BOX);
		text.enterText(company, companyName);
		text.pressTab(company);
	}

	/**
	 * Saves the company details for Epi-server
	 */
	public void saveCompanyDetails() {
		waitForPageLoad();
		this.switchToRegionFrame();
		click.moveToElementAndClick(wait.waitForElementPresent(SAVE_COMPANY_BUTTON));
	}

	/***
	 * This is a method for navigating to invoicing page and returning an object
	 *
	 */
	public EpiOseriesInvocingPage clickOnInvoicingTab( )
	{
		EpiOseriesInvocingPage invoicingpage = new EpiOseriesInvocingPage(driver);
		this.switchToRegionFrame();
		wait.waitForElementDisplayed(INVOICING_TAB, 120);
		click.clickElement(INVOICING_TAB);
		waitForPageLoad();
		return invoicingpage;
	}

    /**
     * Enable or Disable Generate Invoicing for Epi-Server
     *
     * @param isEnabled pass true to enable & pass false to disable
     */
	public void enableOrDisableInvoicing(boolean isEnabled) {
		waitForPageLoad();
		this.switchToRegionFrame();
		this.clickOnInvoicingTab();
        WebElement invoice = wait.waitForElementPresent(GENERATE_INVOICE_CHECKBOX);
		if (isEnabled && !checkbox.isCheckboxChecked(invoice)) {
            click.moveToElementAndClick(invoice);
        }
        if (!isEnabled && checkbox.isCheckboxChecked(invoice)) {
            click.moveToElementAndClick(invoice);
        }
	}

	/***
	 * This is a method for navigating to Settings page and returning an object
	 *
	 */
	public EpiOseriesSettingsPage clickOnSettingsTab( )
	{
		EpiOseriesSettingsPage settingspage = new EpiOseriesSettingsPage(driver);
		this.switchToRegionFrame();
		wait.waitForElementDisplayed(SETTINGS_TAB);
		click.clickElement(SETTINGS_TAB);
		waitForPageLoad();
		return settingspage;
	}

	/**
	 * Enter Vertex's Tax Calculation URL.
	 *
	 * @param url Vertex's taxation URL
	 */
	public void enterVertexTaxationURL(String url) {
		waitForPageLoad();
		this.switchToRegionFrame();
		WebElement taxUrl = wait.waitForElementPresent(TAX_CALC_URL_INPUT);
		text.enterText(taxUrl, url);
		text.pressTab(taxUrl);
	}

	/**
	 * Enter Vertex's Address Cleansing URL
	 *
	 * @param url Vertex address cleansing URL
	 */
	public void enterVertexAddressCleansingURL(String url) {
		waitForPageLoad();
		this.switchToRegionFrame();
		WebElement addressUrl = wait.waitForElementPresent(ADDRESS_URL_INPUT);
		text.enterText(addressUrl, url);
		text.pressTab(addressUrl);
	}

	/**
	 * Saves vertex's settings
	 */
	public void saveSettings() {
		waitForPageLoad();
		this.switchToRegionFrame();
		WebElement saveButton = wait.waitForElementPresent(SAVE_SETTING_BUTTON);
		click.moveToElementAndClick(saveButton);
		wait.waitForElementPresent(SUCCESS_MESSAGE);
		waitForPageLoad();
	}

	/**
	 * Saves vertex's invoicing setting
	 */
	public void saveInvoicing() {
		waitForPageLoad();
		this.switchToRegionFrame();
		WebElement saveButton = wait.waitForElementPresent(SAVE_INVOICE_BUTTON);
		click.moveToElementAndClick(saveButton);
		wait.waitForElementPresent(SUCCESS_MESSAGE);
		waitForPageLoad();
	}
}
