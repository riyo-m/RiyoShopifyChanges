package com.vertex.quality.connectors.ariba.connector.pages.misc;

import com.vertex.quality.common.enums.SpecialCharacter;
import com.vertex.quality.connectors.ariba.connector.pages.base.AribaConnBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * representation of a page that loads on this configuration site
 * after logging in but before any other particular webpage has been navigated to
 *
 * @author ssalisbury
 */
public class AribaConnHomePage extends AribaConnBasePage
{
	public AribaConnHomePage( WebDriver driver )
	{
		super(driver, "Vertex" + SpecialCharacter.REGISTERED_TRADEMARK + " Indirect Tax for Procurement with SAP" +
					  SpecialCharacter.REGISTERED_TRADEMARK + " Ariba" + SpecialCharacter.REGISTERED_TRADEMARK);
	}

	@Override
	protected By getContentTitleLoc( )
	{
		return loggedInHeader;
	}
}
