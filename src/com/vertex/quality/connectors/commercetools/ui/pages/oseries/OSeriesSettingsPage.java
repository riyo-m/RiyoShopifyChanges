package com.vertex.quality.connectors.commercetools.ui.pages.oseries;

import com.vertex.quality.connectors.commercetools.ui.pages.CommerceToolsBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 *
 * @author Mayur.Kumbhar
 */
public class OSeriesSettingsPage extends CommerceToolsBasePage
{
	public OSeriesSettingsPage( final WebDriver driver )
	{
		super(driver);
	}

	protected final By systemLink=By.xpath("//button[@id='system']");
	protected final By administrationLink=By.xpath("//button[@id='system/administration']");
	protected final By settingsLink=By.xpath("//a[text()='Settings']");
	protected final By nextButton=By.xpath("//button[@ref='btNext']");
	protected final By searchTextBox=By.xpath("//input[@id='filter']");
	protected final By confidenceIndicator=By.xpath("//input[@id='amountCellEditor_0']");
	protected final By calcOverrideDropdown =By.xpath("//*[contains(text(),'Calc Override')]//following::div[1]");
	protected final By settingValueFalse = By.xpath("//*[contains(text(),'false')]");
	protected final By settingValueUseTaxGIS = By.xpath("//*[contains(text(),'Use TaxGIS Value')]");
	protected final By saveButton=By.xpath("//button[@id='button_save_button']");
	protected final By searchClearButton = By.xpath("//*[contains(text(),'Search')]//following::button[1]");

	/**
	 * method to click on system link in o series
	 */
	public void clickOnSystemLink()
	{
		WebElement systemLinkField=wait.waitForElementEnabled(systemLink);
		click.performDoubleClick(systemLinkField);
	}

	/**
	 * method to click on administration link in o series
	 */
	public void clickOnAdministrationLink()
	{
		WebElement administrationLinkField=wait.waitForElementEnabled(administrationLink);
		click.moveToElementAndClick(administrationLinkField);
	}

	/**
	 * method to click on settings link in o series
	 */
	public void clickSettingsLink()
	{
		WebElement settingsField=wait.waitForElementEnabled(settingsLink);
		click.moveToElementAndClick(settingsField);
	}

	/**
	 * method to click on next button on settings page
	 */
	public void clickOnNextButton()
	{
		WebElement nextButtonField=wait.waitForElementEnabled(nextButton);
		click.moveToElementAndClick(nextButtonField);
	}

	/**
	 * method to set Continue If Address Cleansing Unavailable (Calc Override)
	 */
	public void selectCalcOverride()
	{
		WebElement calcOverrideField=wait.waitForElementEnabled(calcOverrideDropdown);
		click.moveToElementAndClick(calcOverrideField);
	}

	/**
	 * method to set Continue If Address Cleansing Unavailable to false
	 * @param settingName
	 */
	public void changeOseriesSettingsToFalse(String settingName)
	{
		enterSearchText(settingName);
		WebElement addressCleansingOffField = wait.waitForElementPresent(calcOverrideDropdown);
		WebElement saveButtonField = wait.waitForElementPresent(saveButton);
		click.moveToElementAndClick(addressCleansingOffField);
		WebElement settingValueFalseField = wait.waitForElementPresent(settingValueFalse);
		click.moveToElementAndClick(settingValueFalseField);
		if(saveButtonField.isEnabled())
		{
			clickOnSaveButton();
		}

	}

	/**
	 * method to clean settings for OSeries
	 * @param settingName
	 */
	public void resetSettingsForOSeries(String settingName)
	{
		enterSearchText(settingName);
		WebElement addressCleansingOffField = wait.waitForElementPresent(calcOverrideDropdown);
		WebElement saveButtonField = wait.waitForElementPresent(saveButton);
		click.moveToElementAndClick(addressCleansingOffField);
		WebElement settingValueUseTaxGISField = wait.waitForElementPresent(settingValueUseTaxGIS);
		click.moveToElementAndClick(settingValueUseTaxGISField);
		if(saveButtonField.isEnabled())
		{
			clickOnSaveButton();
		}

	}
	/**
	 * method to click on save button
	 */
	public void clickOnSaveButton()
	{
		WebElement saveButtonField=wait.waitForElementEnabled(saveButton);
		click.moveToElementAndClick(saveButtonField);
	}

	/**
	 * method to enter the search text
	 * @param searchText
	 */
	public void enterSearchText(final String searchText)
	{
		WebElement searchField=wait.waitForElementPresent(searchTextBox);
		WebElement searchClearButtonUI = wait.waitForElementPresent(searchClearButton);
		click.moveToElementAndClick(searchClearButtonUI);
		text.enterText(searchField,searchText);
	}

	/**
	 * method to set confidence indicator
	 * @param amount
	 */
	public void setConfidenceIndicator(final String amount)
	{
		WebElement confidenceIndicatorField=wait.waitForElementEnabled(confidenceIndicator);
		text.clearText(confidenceIndicatorField);
		text.enterText(confidenceIndicatorField,amount);
	}

}

