package com.vertex.quality.connectors.salesforce.pages.lb2b;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.WebDriver;

/**
 * Common functions for anything related to Base Page to init the driver.
 *
 * @author brenda.johnson
 */
public class SalesForceB2BBasePage extends VertexPage
{
	public SalesForceB2BBasePage( WebDriver driver )
	{
		super(driver);

		waitForPageLoad();
	}
}
