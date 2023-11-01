package com.vertex.quality.connectors.netsuite.singlecompany.pages;

import com.vertex.quality.connectors.netsuite.common.components.NetsuiteNavigationPane;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteGeneralPreferencesPage;
import org.openqa.selenium.WebDriver;

/**
 * Single Company version of the general preferences page
 *
 * @author hho
 */
public class NetsuiteSCGeneralPreferencesPage extends NetsuiteGeneralPreferencesPage
{
	public NetsuiteNavigationPane navigationPane;

	public NetsuiteSCGeneralPreferencesPage( final WebDriver driver )
	{
		super(driver);
		navigationPane = new NetsuiteNavigationPane(driver, this);
	}
}
