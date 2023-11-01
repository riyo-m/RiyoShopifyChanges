package com.vertex.quality.connectors.netsuite.common.pages.base;

import org.openqa.selenium.WebDriver;

/**
 * Parent Search results page for Netsuite
 *
 * @author hho
 */
public abstract class NetsuiteSearchResultsPage extends NetsuiteListPage
{
	public NetsuiteSearchResultsPage( final WebDriver driver )
	{
		super(driver);
	}
}
