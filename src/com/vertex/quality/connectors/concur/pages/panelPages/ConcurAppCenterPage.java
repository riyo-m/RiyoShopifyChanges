package com.vertex.quality.connectors.concur.pages.panelPages;

import com.vertex.quality.connectors.concur.pages.base.ConcurBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * represents the app center page for SAP Concur
 *
 * @author alewis
 */
public class ConcurAppCenterPage extends ConcurBasePage
{
	protected final By cardLocator = By.className("card-text");
	protected final By appCenterTitle = By.id("ac-search-btn");
	protected final By searchBar = By.className("ac-search-bar form-control input-lg");
	protected final By finalLoadLocator = By.id("connections-section");
	protected final By collectionLocator = By.id("popular-collection");
	protected final By fieldLocator = By.className("input-lg");
	protected final By searchLocator = By.className("btn-default");

	public ConcurAppCenterPage( final WebDriver driver )
	{
		super(driver);
	}

	/**
	 * looks for card in the 'App Center' based on card title
	 *
	 * @param cardTitle title of card being searched for
	 *
	 * @return title of card if found, if not returns null
	 */
	public String findCard( final String cardTitle )
	{
		String cardText = null;
		String correctCard = null;
		WebElement findApps = wait.waitForElementDisplayed(fieldLocator);
		text.enterText(findApps,"Vertex");
		text.pressEnter(findApps);

		wait.waitForElementDisplayed(appCenterTitle);

		List<WebElement> cards = wait.waitForAllElementsPresent(cardLocator);

		for ( WebElement aCard : cards )
		{
			cardText = text.getElementText(aCard);
			if ( cardText != null && cardText.contains(cardTitle) )
			{
				correctCard = cardText;
			}
		}
		return correctCard;
	}
}
