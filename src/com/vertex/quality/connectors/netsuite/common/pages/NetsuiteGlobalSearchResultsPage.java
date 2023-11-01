package com.vertex.quality.connectors.netsuite.common.pages;

import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteListPage;
import org.openqa.selenium.WebDriver;

/**
 * Represents the search results page for the search in the navigation bar
 *
 * @author hho
 */
public class NetsuiteGlobalSearchResultsPage extends NetsuiteListPage
{
	public NetsuiteGlobalSearchResultsPage( final WebDriver driver )
	{
		super(driver);
	}
}
