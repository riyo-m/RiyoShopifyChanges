package com.vertex.quality.connectors.ariba.connector.components;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.ariba.connector.enums.forcomponents.AribaConnExternalVertexLinkOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * handles interaction with items in the footer element of Ariba's Vertex
 * Configuration page
 * The enum AribaUiExternalVertexLinkOption is handled by this PageObject
 *
 * @author ssalisbury
 */
public class AribaConnFooterPane extends VertexComponent
{
	protected final By footerPane = By.className("site-footer");
	protected final By legalNotice = By.className("legal__item");
	protected final String copyrightNoticeEnding = "Vertex Inc. All Rights Reserved";

	public AribaConnFooterPane( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}

	/**
	 * opens one of the Vertex website pages linked to in the configuration site's footer
	 *
	 * @param linkOption which Vertex website page's link you are clicking on
	 *
	 * @author ssalisbury
	 */
	public void clickExternalVertexLink( final AribaConnExternalVertexLinkOption linkOption )
	{
		WebElement externalVertexLinkButton = wait.waitForElementEnabled(linkOption.linkLoc);

		externalVertexLinkButton.click();

		if ( linkOption.opensInNewTab )
		{
			window.switchToNewWindowHandle(linkOption.linkedPageTitle);
		}

		waitForPageLoad();
	}

	/**
	 * Determines if an external link is present in the footer
	 *
	 * @param link the link to look for
	 *
	 * @return true if present, false otherwise
	 *
	 * @author dgorecki
	 */
	public boolean isLinkPresent( final AribaConnExternalVertexLinkOption link )
	{
		WebElement externalVertexLinkButton = wait.waitForElementEnabled(link.linkLoc);
		return element.isElementDisplayed(externalVertexLinkButton);
	}

	/**
	 * retrieves the notification of Vertex's copyright on the connector,
	 * which is displayed on the far right end of the footer of the connector UI website
	 *
	 * @return the notification of Vertex's copyright on the connector
	 */
	public String getCopyrightNotice( )
	{
		String copyrightNotice = null;

		final WebElement footerPaneElem = wait.waitForElementPresent(footerPane);

		List<WebElement> legalNoticeElems = wait.waitForAllElementsDisplayed(legalNotice, footerPaneElem);

		for ( WebElement noticeElem : legalNoticeElems )
		{
			String elemText = text.getElementText(noticeElem);
			if ( elemText != null && elemText.endsWith(copyrightNoticeEnding) )
			{
				copyrightNotice = elemText;
				break;
			}
		}

		return copyrightNotice;
	}
}
