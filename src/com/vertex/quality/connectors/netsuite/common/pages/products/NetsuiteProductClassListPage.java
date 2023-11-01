package com.vertex.quality.connectors.netsuite.common.pages.products;

import com.vertex.quality.connectors.netsuite.common.components.NetsuiteNavigationPane;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteClassListPage;
import org.openqa.selenium.WebDriver;

/**
 * Represents the product class list page
 *
 * @author hho
 */
public class NetsuiteProductClassListPage extends NetsuiteClassListPage
{
	public NetsuiteNavigationPane navigationPane;

	public NetsuiteProductClassListPage( final WebDriver driver )
	{
		super(driver);
		navigationPane = new NetsuiteNavigationPane(driver, this);
	}
}
