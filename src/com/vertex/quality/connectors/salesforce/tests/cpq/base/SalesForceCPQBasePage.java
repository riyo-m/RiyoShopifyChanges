package com.vertex.quality.connectors.salesforce.tests.cpq.base;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.WebDriver;

public abstract class SalesForceCPQBasePage extends VertexPage
{
	public SalesForceCPQBasePage( WebDriver driver )
	{
		super(driver);

		waitForPageLoad();
	}
}

