package com.vertex.quality.connectors.ariba.portal.components.invoice;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.ariba.portal.components.base.AribaPortalComponent;
import org.openqa.selenium.WebDriver;

/**
 * menu box for common actions like creating or managing various
 * invoicing-related entities
 *
 * @author ssalisbury dgorecki@author ssalisbury
 */
public class AribaPortalInvoiceLeftMenuPane extends AribaPortalComponent
{
	public AribaPortalInvoiceLeftMenuPane( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}
}
