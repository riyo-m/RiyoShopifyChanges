package com.vertex.quality.connectors.ariba.portal.dialogs.base;

import com.vertex.quality.common.dialogs.VertexDialog;
import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.WebDriver;

/**
 * @author ssalisbury dgorecki osabha
 */
public class AribaPortalDialog extends VertexDialog
{
	public AribaPortalDialog( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}
}
