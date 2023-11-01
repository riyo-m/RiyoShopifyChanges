package com.vertex.quality.connectors.dynamics365.finance.dialogs.base;

import com.vertex.quality.common.dialogs.VertexDialog;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinancePostSignOnPage;
import org.openqa.selenium.WebDriver;

/**
 * template for dialogs in D365 Finance\
 *
 * @author ssalisbury
 */
public class DFinanceBaseDialog extends VertexDialog
{
	protected final DFinancePostSignOnPage dFinanceParent;

	public DFinanceBaseDialog( final WebDriver driver, final VertexPage parent )
	{
		super(driver, parent);
		this.dFinanceParent = (DFinancePostSignOnPage) this.parent;
	}
}
