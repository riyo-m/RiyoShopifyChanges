package com.vertex.quality.connectors.netsuite.common.pages.products;

import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteCreateNewListPage;
import org.openqa.selenium.WebDriver;

/**
 * Represents the page that allows creation of an item
 *
 * @author hho
 */
public class NetsuiteItemListPage extends NetsuiteCreateNewListPage
{
	public NetsuiteItemListPage( final WebDriver driver )
	{
		super(driver);
	}
}
