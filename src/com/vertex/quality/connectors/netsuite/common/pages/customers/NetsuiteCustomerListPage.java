package com.vertex.quality.connectors.netsuite.common.pages.customers;

import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteCreateNewListPage;
import org.openqa.selenium.WebDriver;

/**
 * Represents the customer list page in Netsuite
 *
 * @author hho
 */
public class NetsuiteCustomerListPage extends NetsuiteCreateNewListPage
{
	public NetsuiteCustomerListPage( final WebDriver driver )
	{
		super(driver);
	}
}
