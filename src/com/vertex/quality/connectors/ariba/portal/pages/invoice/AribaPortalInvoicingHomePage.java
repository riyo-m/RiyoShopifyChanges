package com.vertex.quality.connectors.ariba.portal.pages.invoice;

import com.vertex.quality.connectors.ariba.portal.components.invoice.AribaPortalInvoiceLeftMenuPane;
import com.vertex.quality.connectors.ariba.portal.pages.common.AribaPortalPostLoginBasePage;
import org.openqa.selenium.WebDriver;

/**
 * the representation of the main/starting page for invoice management in Ariba Portal
 *
 * @author legacyAribaProgrammer ssalisbury
 */
public class AribaPortalInvoicingHomePage extends AribaPortalPostLoginBasePage
{
	public AribaPortalInvoiceLeftMenuPane leftMenu;

	public AribaPortalInvoicingHomePage( WebDriver driver )
	{
		super(driver);

		this.leftMenu = initializePageObject(AribaPortalInvoiceLeftMenuPane.class, this);
	}
}
