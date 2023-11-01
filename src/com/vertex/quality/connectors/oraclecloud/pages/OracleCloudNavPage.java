package com.vertex.quality.connectors.oraclecloud.pages;

import com.vertex.quality.connectors.oraclecloud.components.OracleCloudNavigationPanel;
import com.vertex.quality.connectors.oraclecloud.pages.base.OracleCloudBasePage;
import org.openqa.selenium.WebDriver;

/**
 * Usage of nav panel
 *
 * @author cgajes
 */
public class OracleCloudNavPage extends OracleCloudBasePage
{
	public OracleCloudNavigationPanel navPanel;

	public OracleCloudNavPage( WebDriver driver )
	{
		super(driver);
		navPanel = new OracleCloudNavigationPanel(driver, this);
	}
}
