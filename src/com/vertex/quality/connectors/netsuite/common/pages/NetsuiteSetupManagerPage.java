package com.vertex.quality.connectors.netsuite.common.pages;

import com.vertex.quality.connectors.netsuite.common.components.NetsuiteNavigationPane;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuitePage;
import org.openqa.selenium.WebDriver;

/**
 * Represents the netsuite setup manager page
 *
 * @author hho
 */
public class NetsuiteSetupManagerPage extends NetsuitePage
{
	public NetsuiteNavigationPane navigationPane;

	public NetsuiteSetupManagerPage( WebDriver driver )
	{
		super(driver);
		navigationPane = new NetsuiteNavigationPane(driver, this);
	}
}
