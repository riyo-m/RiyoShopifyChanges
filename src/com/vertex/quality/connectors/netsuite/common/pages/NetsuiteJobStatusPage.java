package com.vertex.quality.connectors.netsuite.common.pages;

import com.vertex.quality.connectors.netsuite.common.components.NetsuiteNavigationPane;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuitePage;
import org.openqa.selenium.WebDriver;

/**
 * Netsuite Job Status Page
 *
 * @author hho
 */
public class NetsuiteJobStatusPage extends NetsuitePage
{

	public NetsuiteNavigationPane navigationPane;

	public NetsuiteJobStatusPage( final WebDriver driver )
	{
		super(driver);
		navigationPane = new NetsuiteNavigationPane(driver, this);
	}
}
