package com.vertex.quality.connectors.ariba.portal.pages.invoice;

import com.vertex.quality.connectors.ariba.portal.components.catalog.AribaPortalCatalogTopMenuBar;
import com.vertex.quality.connectors.ariba.portal.pages.common.AribaPortalPostLoginBasePage;
import org.openqa.selenium.WebDriver;

public class AribaPortalInvoiceAddItemPage extends AribaPortalPostLoginBasePage
{
	public AribaPortalCatalogTopMenuBar topMenuBar;

	public AribaPortalInvoiceAddItemPage( WebDriver driver )
	{
		super(driver);
		this.topMenuBar = initializePageObject(AribaPortalCatalogTopMenuBar.class, this);
	}
}
