package com.vertex.quality.connectors.concur.pages.settings;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * represents the workflow settings page for SAP Concur
 *
 * @author alewis
 */
public class ConcurBaseWorkflowInvoiceSettingsPage extends ConcurInvoiceSettingsPage
{
	By settingsTabLocator = By.className("x-tab-strip-text");
	By threshold = By.name("ExceptionLevelThreshold");
	By saveButton = By.className("c-save-icon");

	public ConcurBaseWorkflowInvoiceSettingsPage( final WebDriver driver )
	{
		super(driver);
	}

	/**
	 * navigate to selected workflow settings tab
	 *
	 * @param tab to selected
	 *
	 * @return ConcurBaseWorkflowInvoiceSettingsPage class which is inherited by other possible workflow tab class
	 */
	public <T extends ConcurBaseWorkflowInvoiceSettingsPage> T navigateToWorkflowTabs( final String tab )
	{
		Class<? extends ConcurBaseWorkflowInvoiceSettingsPage> returnPageClass = null;
		WebElement workflowTab = null;
		final String workflowTabHeader = "Workflows";
		final String approvalStatusTabHeader = "Approval Status";
		final String emailNotificationTabHeader = "Email Notifications";
		final String confirmationAgreementsTabHeader = "Confirmation Agreements";
		final String authorizedApproversTabHeader = "Authorized Approvers";
		final String settingsTabHeader = "Settings";
		final String reasonCategoryAndCodeTabHeader = "Reason Category and Codes";

		switch ( tab )
		{
			case workflowTabHeader:
				//click settings tab
				workflowTab = element.selectElementByText(settingsTabLocator, workflowTabHeader);
				wait.waitForElementEnabled(workflowTab);
				click.clickElement(workflowTab);
				returnPageClass = ConcurBaseWorkflowInvoiceSettingsPage.class;
				break;

			case approvalStatusTabHeader:
				//click settings tab
				workflowTab = element.selectElementByText(settingsTabLocator, approvalStatusTabHeader);
				wait.waitForElementEnabled(workflowTab);
				click.clickElement(workflowTab);
				break;

			case emailNotificationTabHeader:
				//click settings tab
				workflowTab = element.selectElementByText(settingsTabLocator, emailNotificationTabHeader);
				wait.waitForElementEnabled(workflowTab);
				click.clickElement(workflowTab);
				break;

			case confirmationAgreementsTabHeader:
				//click settings tab
				workflowTab = element.selectElementByText(settingsTabLocator, confirmationAgreementsTabHeader);
				wait.waitForElementEnabled(workflowTab);
				click.clickElement(workflowTab);
				break;

			case authorizedApproversTabHeader:
				//click settings tab
				workflowTab = element.selectElementByText(settingsTabLocator, authorizedApproversTabHeader);
				wait.waitForElementEnabled(workflowTab);
				click.clickElement(workflowTab);
				break;

			case settingsTabHeader:
				//click settings tab
				workflowTab = element.selectElementByText(settingsTabLocator, settingsTabHeader);
				wait.waitForElementEnabled(workflowTab);
				click.clickElement(workflowTab);
				returnPageClass = ConcurSettingsWorkflowInvoiceSettingsPage.class;
				break;

			case reasonCategoryAndCodeTabHeader:
				//click settings tab
				workflowTab = element.selectElementByText(settingsTabLocator, reasonCategoryAndCodeTabHeader);
				wait.waitForElementEnabled(workflowTab);
				click.clickElement(workflowTab);
				break;
		}

		T initializedReturnPage = initializePageObject(returnPageClass);
		return initializedReturnPage;
	}
}
