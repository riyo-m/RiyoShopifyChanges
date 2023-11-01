package com.vertex.quality.connectors.netsuite.oneworld.pages;

import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteLocationPage;
import org.openqa.selenium.WebDriver;

/**
 * Represents the location page for One World
 *
 * @author hho
 */
public class NetsuiteOWLocationPage extends NetsuiteLocationPage
{
	public NetsuiteOWLocationPage( final WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Select the location's subsidiary
	 *
	 * @param subsidiary the subsidiary
	 */
	public void selectSubsidiary( String subsidiary )
	{
		setDropdownToValue(subsidiaryDropdownLocator, subsidiary);
	}
}
