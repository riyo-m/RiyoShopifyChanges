package com.vertex.quality.connectors.netsuite.common.pages;

import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuitePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NetsuiteRolePage extends NetsuitePage
{

	protected By SCISRole = By.xpath("//label[contains(text(), 'Vertex SCIS/SCA QA (TSTDRV2366937)')]");

	public NetsuiteRolePage( final WebDriver driver )
	{
		super(driver);
	}

	public NetsuiteHomepage signInAsSCIS( )
	{
		click.clickElement(SCISRole);
		return initializePageObject(NetsuiteHomepage.class);
	}
}
