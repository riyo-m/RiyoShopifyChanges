package com.vertex.quality.connectors.accumatica.pages;

import com.vertex.quality.connectors.accumatica.pages.base.AcumaticaPostSignOnPage;
import org.openqa.selenium.WebDriver;

/**
 * This is the default page for the site-section under the 'Communication' submenu tab, which is
 * under
 * the 'ORGANIZATION' global menu tab
 */
public class AcumaticaCommunicationHomePage extends AcumaticaPostSignOnPage
{
	public AcumaticaCommunicationHomePage( final WebDriver driver )
	{
		super(driver);
	}
}
