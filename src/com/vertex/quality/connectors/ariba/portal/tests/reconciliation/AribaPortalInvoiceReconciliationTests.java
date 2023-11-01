package com.vertex.quality.connectors.ariba.portal.tests.reconciliation;

import com.vertex.quality.connectors.ariba.portal.enums.AribaPortalManageListPage;
import com.vertex.quality.connectors.ariba.portal.pages.common.AribaPortalPostLoginBasePage;
import com.vertex.quality.connectors.ariba.portal.pages.procurement.AribaPortalBeforeReconciliationPage;
import com.vertex.quality.connectors.ariba.portal.pages.procurement.AribaPortalReconciliationPage;
import com.vertex.quality.connectors.ariba.portal.pages.procurement.AribaPortalToDoPage;
import com.vertex.quality.connectors.ariba.portal.tests.base.AribaTwoXPortalBaseTest;
import org.testng.annotations.Test;

/**
 * contains test cases for invoice reconciliation on the buyer site
 *
 * @author osabha
 */
public class AribaPortalInvoiceReconciliationTests extends AribaTwoXPortalBaseTest
{
	//Work in Progress
	//This method is a template test steps to be incorporated with other test classes to run a complete Ariba Transaction,
	// Starting from the Buy, to Supplier and back to the buyer for invoice reconciliation.
	@Test(groups = { "ariba_ui","ariba_regression"})
	public void reconcileInvoiceTest( )
	{
		AribaPortalPostLoginBasePage loggedInDashboardPage = signInToTwoXPortal(testStartPage);
		AribaPortalToDoPage toDoPage = loggedInDashboardPage.navigateToManageListPages(
			AribaPortalManageListPage.MY_TODO);
		toDoPage.searchForInvoice("2019");
		AribaPortalBeforeReconciliationPage openInvoicePage = toDoPage.openInvoice("2019");
		AribaPortalReconciliationPage reconciliationPage = openInvoicePage.openInvoiceToReconcile();
	}
}
