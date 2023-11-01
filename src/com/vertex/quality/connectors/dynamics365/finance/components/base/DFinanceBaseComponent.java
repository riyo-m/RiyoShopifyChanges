package com.vertex.quality.connectors.dynamics365.finance.components.base;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinancePostSignOnPage;
import org.openqa.selenium.WebDriver;

/**
 * template for components in D365 Finance
 *
 * @author ssalisbury
 */
public class DFinanceBaseComponent extends VertexComponent
{
	protected final DFinancePostSignOnPage dFinanceParent;

	public DFinanceBaseComponent( final WebDriver driver, final VertexPage parent )
	{
		super(driver, parent);
		this.dFinanceParent = (DFinancePostSignOnPage) this.parent;
	}
}
