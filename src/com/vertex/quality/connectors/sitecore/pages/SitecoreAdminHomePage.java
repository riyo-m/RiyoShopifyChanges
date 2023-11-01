package com.vertex.quality.connectors.sitecore.pages;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.sitecore.common.enums.SitecoreIconName;
import com.vertex.quality.connectors.sitecore.pages.base.SitecoreBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Sitecore Administration home page - contains all navigation and other
 * re-usable methods specific to this page.
 *
 * @author Shiva Mothkula, Daniel Bondi
 */
public class SitecoreAdminHomePage extends SitecoreBasePage
{

	protected By sitecoreLogo = By.className("sc-global-logo");
	protected By logoutLink = By.className("logout");
	protected By icon = By.className("sc-launchpad-item");

	public SitecoreAdminHomePage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * This method is used to navigate to specific page/module of site-core
	 * administration
	 *
	 * @param iconName - name of the icon to navigate
	 */
	private boolean clickIconToNavigate( final String iconName )
	{

		List<WebElement> iconsList = wait.waitForAllElementsPresent(icon);
		boolean flag = false;
		for ( WebElement iconElement : iconsList )
		{

			String iconText = text.getElementText(iconElement);

			if ( iconText != null )
			{
				if ( iconText
					.trim()
					.equalsIgnoreCase(iconName) )
				{
					flag = true;
					click.clickElement(iconElement);
					VertexLogger.log(String.format("Icon with text: %s is clicked", iconName),
						SitecoreAdminHomePage.class);
					break;
				}
			}
		}

		if ( !flag )
		{
			VertexLogger.log(String.format("Icon with text: %s is not found", iconName), VertexLogLevel.ERROR,
				getClass());
		}
		waitForPageLoad();
		return flag;
	}

	/**
	 * This method is used to navigate to "User Manager" page
	 *
	 * @return UserManagerPage object after successful navigation
	 */
	public SitecoreUserManagerPage navigateToUserManagerPage( )
	{

		SitecoreUserManagerPage userManagerPage = null;
		boolean successfulNavigation = clickIconToNavigate(SitecoreIconName.USER_MANAGER.getText());
		if ( successfulNavigation )
		{
			userManagerPage = initializePageObject(SitecoreUserManagerPage.class);
		}

		return userManagerPage;
	}

	/**
	 * This method is used to navigate to "Vertex O Series Connector" page
	 *
	 * @return VertexOSeriesConnectorPage object after successful navigation
	 */
	public SitecoreVertexOSeriesConnectorPage navigateToVertexOSeriesConnectorPage( )
	{

		SitecoreVertexOSeriesConnectorPage oSeriesPage = null;

		if ( clickIconToNavigate(SitecoreIconName.VERTEX_O_SERIES_CONNECTOR.getText()) )
		{
			oSeriesPage = initializePageObject(SitecoreVertexOSeriesConnectorPage.class);
		}
		return oSeriesPage;
	}

	/**
	 * This method is used to navigate to "Content Editor" page
	 *
	 * @return ContentEditorPage object after successful navigation
	 */
	public SitecoreContentEditorPage navigateToContentEditorPage( )
	{

		SitecoreContentEditorPage contentEditorPage = null;

		if ( clickIconToNavigate(SitecoreIconName.CONTENT_EDITOR.getText()) )
		{
			contentEditorPage = initializePageObject(SitecoreContentEditorPage.class);
		}
		return contentEditorPage;
	}

	/**
	 * This is method is used to click the logout link of Sitecore admin site
	 */
	public void clickLogoutLink( )
	{
		WebElement logoutElement = wait.waitForElementDisplayed(logoutLink);
		click.javascriptClick(logoutElement);
		waitForPageLoad();
	}

	/**
	 * This method is used to move to the site-core administration home page.
	 */
	public void navigateToSitecoreHomePage( )
	{
		wait.waitForElementEnabled(sitecoreLogo);
		click.clickElement(sitecoreLogo);
		alert.acceptAlert(FIVE_SECOND_TIMEOUT);
		waitForPageTitleContains("Sitecore Launchpad");
		waitForPageLoad();
	}

	/**
	 * This method is used logout from the administration site if any user is
	 * already logged in
	 */
	public void logoutFromAdminIfAlreadyLoggedIn( )
	{
		if ( element.isElementDisplayed(logoutLink) )
		{
			clickLogoutLink();
		}
	}
}
