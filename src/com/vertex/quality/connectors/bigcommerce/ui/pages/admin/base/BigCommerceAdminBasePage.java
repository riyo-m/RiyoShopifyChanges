package com.vertex.quality.connectors.bigcommerce.ui.pages.admin.base;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.WebDriver;

/**
 * a generic representation of a page on big commerce's admin site
 *
 * @author ssalisbury
 */
public abstract class BigCommerceAdminBasePage extends VertexPage
{
	public BigCommerceAdminBasePage( final WebDriver driver )
	{
		super(driver);
	}
}
