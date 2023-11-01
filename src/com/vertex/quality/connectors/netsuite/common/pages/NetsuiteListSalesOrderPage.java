package com.vertex.quality.connectors.netsuite.common.pages;

import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteCreateNewListPage;
import org.openqa.selenium.WebDriver;

/**
 * Represents the list page for Sales Orders
 *
 * @author hho
 */
public class NetsuiteListSalesOrderPage extends NetsuiteCreateNewListPage
{
	public NetsuiteListSalesOrderPage( final WebDriver driver )
	{
		super(driver);
	}
}
