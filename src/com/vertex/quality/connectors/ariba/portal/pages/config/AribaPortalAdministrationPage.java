package com.vertex.quality.connectors.ariba.portal.pages.config;

import com.vertex.quality.connectors.ariba.portal.pages.common.AribaPortalPostLoginBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * represents the Administration page on the buyer site,
 * contains all the methods necessary to interact with the page
 *
 * @author osabha
 */
@SuppressWarnings("Duplicates")
public class AribaPortalAdministrationPage extends AribaPortalPostLoginBasePage
{
	public AribaPortalAdministrationPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * locates and clicks on the integration manager tool link
	 *
	 * @return new instance of the integration manager page
	 */
	public AribaPortalIntegrationManagerPage goToIntegrationManager( )
	{
		waitForUpdate();
		String expectedText = "Integration Manager";
		WebElement integrationManager = null;
		WebElement parentContainer = wait.waitForElementDisplayed(By.className("w-page-wrapper-content"));
		WebElement container = wait.waitForElementPresent(By.className("flexContainer"), parentContainer);
		List<WebElement> integrationManagerConts = wait.waitForAllElementsPresent(By.className("vaT"), container);
		for ( WebElement thisContainer : integrationManagerConts )
		{
			WebElement element = wait.waitForElementPresent(By.tagName("a"), thisContainer);
			String elementText = element.getText();
			if ( expectedText.equals(elementText) )
			{
				integrationManager = element;
				break;
			}
		}
		click.clickElementCarefully(integrationManager);
		return initializePageObject(AribaPortalIntegrationManagerPage.class);
	}
}
