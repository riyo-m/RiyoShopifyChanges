package com.vertex.quality.connectors.concur.enums;

import com.vertex.quality.connectors.concur.pages.base.ConcurBasePage;
import com.vertex.quality.connectors.concur.pages.misc.ConcurHomePage;
import com.vertex.quality.connectors.concur.pages.panelPages.ConcurAppCenterPage;
import com.vertex.quality.connectors.concur.pages.panelPages.ConcurApprovalPage;
import com.vertex.quality.connectors.concur.pages.panelPages.ConcurExpensePage;
import com.vertex.quality.connectors.concur.pages.panelPages.ConcurInvoicePage;
import lombok.Getter;

/**
 * Represents the five different pages one can navigate to by click a panel at top the concur homepage
 *
 * @author alewis
 */
@Getter
public enum ConcurHeaderTab
{
	SAP_CONCUR("SAP Concur", ConcurHomePage.class),
	EXPENSE("Expense", ConcurExpensePage.class),
	INVOICE("Invoice", ConcurInvoicePage.class),
	APPROVALS("Approvals", ConcurApprovalPage.class),
	APP_CENTER("App Center", ConcurAppCenterPage.class);

	private final String title;
	private final Class<? extends ConcurBasePage> basePage;

	ConcurHeaderTab( final String title, final Class<? extends ConcurBasePage> basePage )
	{
		this.title = title;
		this.basePage = basePage;
	}
}
