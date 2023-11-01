package com.vertex.quality.connectors.ariba.portal.pages.common;
//TODO fix the portal.pages.common package name- it should be portal.pages.base, because common refers to things
// shared between different parts of the connector (like the portal site vs the connector ui vs the supplier site vs api testing)

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.WebDriver;

//TODO add javadoc
public abstract class AribaPortalBasePage extends VertexPage
{
	public AribaPortalBasePage( WebDriver driver )
	{
		super(driver);
	}
}
