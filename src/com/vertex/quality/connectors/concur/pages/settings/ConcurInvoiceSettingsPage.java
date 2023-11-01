package com.vertex.quality.connectors.concur.pages.settings;

import com.vertex.quality.connectors.concur.pages.base.ConcurBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * represents the settings page for SAP Concur
 *
 * @author alewis
 */
public class ConcurInvoiceSettingsPage extends ConcurBasePage
{
	By settingsLinksContainer = By.id("links");

	public ConcurInvoiceSettingsPage( final WebDriver driver )
	{
		super(driver);
	}

	/**
	 * turns off image requirement in settings page
	 *
	 * @return return threshold set
	 */
	public ConcurBaseWorkflowInvoiceSettingsPage navigateToWorkFlowSettings( )
	{
		//click workflow tab
		WebElement linkContainerElement = element.getWebElement(settingsLinksContainer);
		List<WebElement> settingsLinks = wait.waitForAnyElementsDisplayed(By.tagName("a"), linkContainerElement);
		String linkHeaderText = "Workflows";
		WebElement workflowLink = element.selectElementByText(settingsLinks, linkHeaderText);
		wait.waitForElementEnabled(workflowLink);
		click.clickElement(workflowLink);

		ConcurBaseWorkflowInvoiceSettingsPage workflowSettingsPage = initializePageObject(
			ConcurBaseWorkflowInvoiceSettingsPage.class);
		return workflowSettingsPage;
	}
}
