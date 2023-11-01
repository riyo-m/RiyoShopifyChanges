package com.vertex.quality.connectors.ariba.portal.pages.config;

import com.vertex.quality.connectors.ariba.portal.pages.common.AribaPortalPostLoginBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * contains all the methods necessary to interact with the page
 *
 * @author osabha
 */
@SuppressWarnings("Duplicates")
public class AribaPortalIntegrationManagerPage extends AribaPortalPostLoginBasePage
{
	public AribaPortalIntegrationManagerPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * locates and clicks on the integration Configuration link
	 *
	 * @return new instance of the integration configuration page
	 */
	public AribaPortalIntegrationConfigurationPage goToIntegrationConfig( )
	{
		waitForUpdate();
		String expectedText = "Integration Configuration";
		WebElement integrationConfig = null;
		WebElement parentContainer = wait.waitForElementDisplayed(By.id("contentAlley"));
		WebElement container = wait.waitForElementPresent(By.className("flexContainer"), parentContainer);
		List<WebElement> integrationConfigConts = wait.waitForAllElementsPresent(By.className("vaT"), container);
		for ( WebElement thisContainer : integrationConfigConts )
		{
			WebElement element = wait.waitForElementPresent(By.tagName("a"), thisContainer);
			String elementText = element.getText();
			if ( expectedText.equals(elementText) )
			{
				integrationConfig = element;
				break;
			}
		}

		click.clickElementCarefully(integrationConfig);

		return initializePageObject(AribaPortalIntegrationConfigurationPage.class);
	}
}
