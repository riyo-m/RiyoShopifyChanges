package com.vertex.quality.connectors.ariba.portal.enums;

import com.vertex.quality.connectors.ariba.portal.pages.catalog.AribaPortalCatalogHomePage;
import com.vertex.quality.connectors.ariba.portal.pages.common.AribaPortalHomePage;
import com.vertex.quality.connectors.ariba.portal.pages.common.AribaPortalPostLoginBasePage;
import com.vertex.quality.connectors.ariba.portal.pages.invoice.AribaPortalInvoicingHomePage;
import com.vertex.quality.connectors.ariba.portal.pages.procurement.AribaPortalProcurementHomePage;
import org.openqa.selenium.By;

/**
 * describes the tabs located towards the left end of the header in each of
 * ariba's portal's primary menus. Those tabs in turn link to those primary
 * menus.
 *
 * @author ssalisbury
 */
public enum AribaPortalDashboardTab
{
	// function like hybrid of AribaUiNavPanel & AribaUiFooterPane code
	// @formatter:off
	//		the tab's text		locator for element on tab's page		text of that element on tab's page		class representing the tab's page
	HOME(		"HOME",			Constants.commonActionsPanelLabelClass,	Constants.commonActionsPanelLabelText,	AribaPortalHomePage.class),
	PROCUREMENT("PROCUREMENT",	Constants.commonActionsPanelLabelClass,	Constants.commonActionsPanelLabelText,	AribaPortalProcurementHomePage.class),
	INVOICING(	"INVOICING",	Constants.commonActionsPanelLabelClass,	Constants.commonActionsPanelLabelText,	AribaPortalInvoicingHomePage.class),
	CATALOG(	"CATALOG",		By.className("a-cat-home-top-row"),	"Catalog Home",							AribaPortalCatalogHomePage.class);
	// @formatter:on

	// this'll be returned by getDefaultTestText()
	public String tabText;

	public By tabLoadedIndicatorLoc;
	public String tabLoadedIndicatorText;
	public Class<? extends AribaPortalPostLoginBasePage> tabPageType;

	AribaPortalDashboardTab( final String tabText, final By loc, final String indicatorText,
		final Class<? extends AribaPortalPostLoginBasePage> tabClass )
	{
		this.tabText = tabText;
		this.tabLoadedIndicatorLoc = loc;
		this.tabLoadedIndicatorText = indicatorText;
		this.tabPageType = tabClass;
	}

	static class Constants
	{
		public static final By commonActionsPanelLabelClass = By.className("portletTitle");
		public static final String commonActionsPanelLabelText = "Common Actions";
	}
}
