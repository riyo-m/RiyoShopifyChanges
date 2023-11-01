package com.vertex.quality.connectors.netsuite.common.pages.customers;

import com.vertex.quality.connectors.netsuite.common.components.NetsuiteNavigationPane;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteClassListPage;
import org.openqa.selenium.WebDriver;

/**
 * Represents the customer class list page
 *
 * @author hho
 */
public class NetsuiteCustomerClassListPage extends NetsuiteClassListPage
{
	public NetsuiteNavigationPane navigationPane;

	public NetsuiteCustomerClassListPage( final WebDriver driver )
	{
		super(driver);
		navigationPane = new NetsuiteNavigationPane(driver, this);
	}
}
