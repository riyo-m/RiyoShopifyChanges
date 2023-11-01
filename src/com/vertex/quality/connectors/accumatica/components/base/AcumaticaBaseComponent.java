package com.vertex.quality.connectors.accumatica.components.base;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.WebDriver;

public class AcumaticaBaseComponent extends VertexComponent
{
	public AcumaticaBaseComponent( final WebDriver driver, final VertexPage parent )
	{
		super(driver, parent);
	}

	@Override
	public void waitForPageLoad( )
	{
		parent.waitForPageLoad();
	}
}
