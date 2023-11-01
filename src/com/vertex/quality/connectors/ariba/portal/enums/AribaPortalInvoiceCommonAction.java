package com.vertex.quality.connectors.ariba.portal.enums;

import com.vertex.quality.connectors.ariba.portal.components.invoice.AribaPortalInvoiceLeftMenuPane;
import com.vertex.quality.connectors.ariba.portal.pages.common.AribaPortalPostLoginBasePage;
import com.vertex.quality.connectors.ariba.portal.pages.invoice.AribaPortalInvoiceCreationPage;

/**
 * the common actions available in the left-hand menu pane on the Invoicing home page of Ariba's
 * Portal Site
 * used by {@link AribaPortalInvoiceLeftMenuPane}
 *
 * @author {ssalisbury}
 */
public enum AribaPortalInvoiceCommonAction
{
	CREATE_INVOICE("Invoice", AribaPortalInvoiceCreationPage.class);

	private String actionText;

	private Class<? extends AribaPortalPostLoginBasePage> resultingPageClass;

	AribaPortalInvoiceCommonAction( final String text, final Class<? extends AribaPortalPostLoginBasePage> nextPage )
	{
		this.actionText = text;
		this.resultingPageClass = nextPage;
	}

	public String getActionText( )
	{
		return actionText;
	}

	public Class<? extends AribaPortalPostLoginBasePage> getResultingPageClass( )
	{
		return resultingPageClass;
	}
}
