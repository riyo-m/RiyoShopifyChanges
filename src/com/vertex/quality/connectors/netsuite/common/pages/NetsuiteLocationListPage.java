package com.vertex.quality.connectors.netsuite.common.pages;

import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteCreateNewListPage;
import org.openqa.selenium.WebDriver;

/**
 * Represents the list page for locations
 *
 * @author hho
 */
public class NetsuiteLocationListPage extends NetsuiteCreateNewListPage
{
	public NetsuiteLocationListPage( final WebDriver driver )
	{
		super(driver);
	}
}
