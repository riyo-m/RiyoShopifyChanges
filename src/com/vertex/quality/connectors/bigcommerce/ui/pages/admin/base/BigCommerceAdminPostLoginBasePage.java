package com.vertex.quality.connectors.bigcommerce.ui.pages.admin.base;

import com.vertex.quality.connectors.bigcommerce.ui.components.BigCommerceAdminLeftNavBar;
import org.openqa.selenium.WebDriver;

/**
 * a generic representation of a page on big commerce's admin site other than the login page
 *
 * @author ssalisbury
 */
public class BigCommerceAdminPostLoginBasePage extends BigCommerceAdminBasePage
{
	public BigCommerceAdminLeftNavBar navBar;

	public BigCommerceAdminPostLoginBasePage( final WebDriver driver )
	{
		super(driver);

		this.navBar = initializePageObject(BigCommerceAdminLeftNavBar.class, this);
	}
}
