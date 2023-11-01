package com.vertex.quality.connectors.netsuite.common.pages.base;

import com.vertex.quality.connectors.netsuite.common.components.NetsuiteNavigationPane;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Parent search page for Netsuite
 *
 * @author hho
 */
public abstract class NetsuiteSearchPage extends NetsuitePage
{
	public NetsuiteNavigationPane navigationPane;

	protected By submitButton = By.id("submitter");

	public NetsuiteSearchPage( final WebDriver driver )
	{
		super(driver);
		navigationPane = new NetsuiteNavigationPane(driver, this);
	}

	/**
	 * Resets the search
	 */
	public void resetSearch( )
	{
		By resetButton = By.id("resetter");
		click.clickElement(resetButton);
	}

	/**
	 * Submits the search
	 *
	 * @return the search results page
	 */
	public abstract <T extends NetsuitePage> T submitSearch( );
}
