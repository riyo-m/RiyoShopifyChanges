package com.vertex.quality.connectors.salesforce.tests.cpqbilling;

import com.vertex.quality.connectors.salesforce.pages.SalesForceLightingDeveloperConsole;
import com.vertex.quality.connectors.salesforce.pages.cpq.SalesForceCPQPostLogInPage;
import com.vertex.quality.connectors.salesforce.tests.cpqbilling.base.SalesForceCPQBillingBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class SFCPQBillingRunUnitTests extends SalesForceCPQBillingBaseTest
{
	SalesForceCPQPostLogInPage postLogInPage;
	SalesForceLightingDeveloperConsole developerConsole;

	/**
	 * This test covers running Unit Tests
	 *
	 * @author Brenda Johnson
	 */
	@Test(groups = { "sfcpqbilling_smoke" })
	public void SFCPQBillingUnitTest( )
	{
		postLogInPage = new SalesForceCPQPostLogInPage(driver);
		developerConsole = new SalesForceLightingDeveloperConsole(driver);

		//Log In to Sales force CRM application
		postLogInPage = logInAsSalesForceBillingUser();

		postLogInPage.closeLightningExperienceDialog();
		developerConsole.launchClassicDeveloperConsole();
		developerConsole.runAllTests();
		developerConsole.waitForTestComplete();
		String testStatus = developerConsole.getTestStatus();
		assertEquals(testStatus, "Completed");
		developerConsole.closeConsole();
	}
}
