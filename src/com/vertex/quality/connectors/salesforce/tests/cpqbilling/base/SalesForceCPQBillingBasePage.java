package com.vertex.quality.connectors.salesforce.tests.cpqbilling.base;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.WebDriver;

public abstract class SalesForceCPQBillingBasePage extends VertexPage
{
	public SalesForceCPQBillingBasePage( WebDriver driver )
	{
		super(driver);

		waitForPageLoad();
	}
}

