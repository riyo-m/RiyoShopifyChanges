package com.vertex.quality.connectors.bigcommerce.ui.pages.refund.oseries;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * this class represents the O-Series tax journal export page and contains all the methods necessary to interact with
 * it.
 *
 * @author rohit-mogane
 */
public class OSeriesTaxJournalExportPage extends OSeriesBasePage
{
	protected final By dataManagementButton = By.xpath("//*[@id='system/data-management']");
	protected final By taxJournalExportLink = By.xpath("//*[contains(text(),'Tax Journal Export')]");
	protected final By runButton = By.xpath("//*[@id='tax_journal_export_run_btn_button']");
	protected final By filterName = By.xpath("//input[@id='filterName_input']");
	protected final By descriptionTextArea = By.xpath("//*[@id='description_textarea']");
	protected final By purgeDataAfterExportCheckBox = By.xpath("//*[contains(text(),'Purge Data After Export')]");

	public OSeriesTaxJournalExportPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * locates and clicks the data management option
	 *
	 * @return whether the data management field is present and displayed
	 */
	public void clickDataManagementButton( )
	{
		WebElement dataManagementButtonField = wait.waitForElementEnabled(dataManagementButton);
		click.moveToElementAndClick(dataManagementButtonField);
	}

	/**
	 * locates and clicks the Data Management link
	 *
	 * @return whether the Data Management field is present and displayed
	 */
	public boolean isDataManagementButtonDisplayed( )
	{
		boolean isDataManagementButtonField = element.isElementDisplayed(dataManagementButton);
		return isDataManagementButtonField;
	}

	/**
	 * locates and clicks the Purge Data Export  option
	 *
	 * @return whether the Purge Data Export field is present and displayed
	 */
	public void purgeDataAfterExport( )
	{
		WebElement purgeDataAfterExportField = wait.waitForElementEnabled(purgeDataAfterExportCheckBox);
		click.moveToElementAndClick(purgeDataAfterExportField);
	}

	/**
	 * locates and clicks the tax journal export option
	 *
	 * @return whether the tax journal export field is present and displayed
	 */
	public void clickTaxJournalExportLink( )
	{
		WebElement taxJournalPurgeLinkField = wait.waitForElementEnabled(taxJournalExportLink);
		click.moveToElementAndClick(taxJournalPurgeLinkField);
	}

	/**
	 * locates and clicks the run button
	 *
	 * @return whether the run button field is present and displayed
	 */
	public void clickRunButton( )
	{
		WebElement runButtonField = wait.waitForElementEnabled(runButton);
		click.moveToElementAndClick(runButtonField);
	}

	/**
	 * locates and enters the filter name
	 *
	 * @return whether the filter name field is present and displayed
	 */
	public void enterFilterName( String Name )
	{
		WebElement exportFilterNameField = wait.waitForElementEnabled(filterName);
		text.enterText(exportFilterNameField, Name);
	}
}
