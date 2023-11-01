package com.vertex.quality.connectors.concur.pages.settings;

import org.openqa.selenium.WebDriver;

/**
 * represents the settings tab in workflow settings page for SAP Concur
 *
 * @author alewis
 */
public class ConcurSettingsWorkflowInvoiceSettingsPage extends ConcurBaseWorkflowInvoiceSettingsPage
{
	public ConcurSettingsWorkflowInvoiceSettingsPage( final WebDriver driver )
	{
		super(driver);
	}

	/**
	 * changes exception threshold in workflow settings tab
	 *
	 * @param exceptionThreshold the exception level to input
	 *
	 * @return exception level in text field
	 */
	public String changeExceptionLevel( final String exceptionThreshold )
	{
		//navigate to settings tab
		String settingsTabHeader = "Settings";
		navigateToWorkflowTabs(settingsTabHeader);

		//enter 99 to get rid of image error
		wait.waitForElementEnabled(threshold);
		text.enterText(threshold, exceptionThreshold);
		wait.waitForElementEnabled(saveButton);
		click.clickElement(saveButton);

		String thresholdNumber = text.retrieveTextFieldContents(threshold);
		return thresholdNumber;
	}
}
