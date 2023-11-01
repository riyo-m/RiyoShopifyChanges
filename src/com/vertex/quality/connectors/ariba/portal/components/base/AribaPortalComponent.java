package com.vertex.quality.connectors.ariba.portal.components.base;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.WebDriver;

/**
 * Top-level class reresenting a component n the Ariba system
 */
public class AribaPortalComponent extends VertexComponent
{
	public AribaPortalComponent( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}
}
