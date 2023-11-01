package com.vertex.quality.connectors.concur.tests.healthcheck;

import com.vertex.quality.connectors.concur.pages.misc.ConcurHomePage;
import com.vertex.quality.connectors.concur.pages.settings.ConcurBaseWorkflowInvoiceSettingsPage;
import com.vertex.quality.connectors.concur.pages.settings.ConcurInvoiceSettingsPage;
import com.vertex.quality.connectors.concur.pages.settings.ConcurSettingsWorkflowInvoiceSettingsPage;
import com.vertex.quality.connectors.concur.tests.base.ConcurUIBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * tests functionality of settings page
 *
 * @author alewis
 */
public class ConcurSettingsTests extends ConcurUIBaseTest
{
	/**
	 * tests if image invoice requirement can be turned off. Should run before creating new invoice test
	 */
	@Test(groups = { "config","CONN-14" })
	public void turnOffImageInvoiceRequirementTest( )
	{
		ConcurHomePage homePage = signIntoConcur(testStartPage);
		String invoiceHeaderTab = "Invoice";
		ConcurInvoiceSettingsPage settingsPage = homePage.clickAdmin(invoiceHeaderTab);
		ConcurBaseWorkflowInvoiceSettingsPage workflowPage = settingsPage.navigateToWorkFlowSettings();
		String invoiceSettingsTab = "Settings";
		ConcurSettingsWorkflowInvoiceSettingsPage workflowSettingsPage = workflowPage.navigateToWorkflowTabs(
			invoiceSettingsTab);
		String expectedThresholdNumber = "99";
		String actualThresholdNumber = workflowSettingsPage.changeExceptionLevel(expectedThresholdNumber);
		assertEquals(expectedThresholdNumber, actualThresholdNumber);
	}
}
