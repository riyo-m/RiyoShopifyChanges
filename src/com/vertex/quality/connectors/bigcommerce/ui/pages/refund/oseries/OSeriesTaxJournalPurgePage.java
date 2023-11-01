package com.vertex.quality.connectors.bigcommerce.ui.pages.refund.oseries;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * this class represents the O-Series tax journal purge page and contains all the methods necessary to interact with it.
 *
 * @author rohit-mogane
 */
public class OSeriesTaxJournalPurgePage extends OSeriesBasePage
{
	protected final By dataManagementButton = By.xpath("//*[@id='system/data-management']");
	protected final By taxJournalPurgeLink = By.xpath("//*[contains(text(),'Tax Journal Purge')]");
	protected final By purgeDataDatePicker = By.xpath("(//*[contains(text(),'Purge data')])[1]//following::input[1]");
	protected final By descriptionTextArea = By.xpath("//*[@id='description_textarea']");
	protected final By runButton = By.xpath("//*[@id='tax_journal_purge_run-btn_button']");
	protected final By startTimeColumnField = By.xpath("//*[contains(text(),'Start Time')]");

	public OSeriesTaxJournalPurgePage( final WebDriver driver )
	{
		super(driver);
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
	 * locates and clicks the data management option
	 *
	 * @return whether the data management field is present and displayed
	 */
	public void clickDataManagementButton( )
	{
		WebElement dataManagementButtonField = wait.waitForElementEnabled(dataManagementButton);
		click.performDoubleClick(dataManagementButtonField);
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
	 * locates and clicks the tax journal export option
	 *
	 * @return whether the tax journal export field is present and displayed
	 */
	public void clickTaxJournalPurgeLink( )
	{
		WebElement taxJournalPurgeLinkField = wait.waitForElementEnabled(taxJournalPurgeLink);
		click.moveToElementAndClick(taxJournalPurgeLinkField);
	}

	/**
	 * locates and clicks the run button
	 *
	 * @return whether the run button field is present and displayed
	 */
	public boolean isStartTimeColumnFieldDisplayed( )
	{
		boolean isStartTimeColumnFieldPresent = element.isElementDisplayed(startTimeColumnField);
		return isStartTimeColumnFieldPresent;
	}

	/**
	 * locates and enters the purge date
	 *
	 * @return whether the purge date field is present and displayed
	 */
	public void enterPurgeDate( String date )
	{
		WebElement purgeDataDateField = wait.waitForElementEnabled(purgeDataDatePicker);
		text.enterText(purgeDataDateField, date);
	}
}
