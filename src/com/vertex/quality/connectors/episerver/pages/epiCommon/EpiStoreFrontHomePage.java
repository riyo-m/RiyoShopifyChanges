package com.vertex.quality.connectors.episerver.pages.epiCommon;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.episerver.pages.EpiOrderHistoryPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EpiStoreFrontHomePage extends VertexPage
{
	public EpiStoreFrontHomePage( WebDriver driver )
	{
		super(driver);
	}

	protected By HOME_LINK = By.linkText("Home");
	protected By USER_ICON = By.cssSelector("[class*='glyphicon glyphicon-user']");
	protected By USER_ICON_TAB = By.xpath("//span[@class='glyphicon glyphicon-user']/parent::a");
	protected By ADDRESS_BOOK_LINK = By.linkText("Address book");
	protected By ORDER_HISTORY = By.linkText("Order history");
	protected By SIGNOUT_LINK = By.linkText("Sign out");

	/**
	 * This method is used to navigate to Episerver Customer Portal Home page
	 */
	public void navigateToHomePage( )
	{
		wait.waitForElementDisplayed(HOME_LINK);
		VertexLogger.log("element present");
		click.clickElement(HOME_LINK);
		waitForPageLoad();
	}

	/**
	 * This method is used to click on profile or user icon to perform other options
	 * available under User Icon.
	 */
	public void clickUserIcon( )
	{
		String is_expanded = attribute.getElementAttribute(USER_ICON_TAB, "aria-expanded");
		if ( is_expanded.equals("false") )
		{
			click.clickElement(USER_ICON);
			waitForPageLoad();
		}
		else
		{
			VertexLogger.log("User Icon is already clicked");
		}
	}

	/**
	 * This method is used to Click on Address Book Page from UserIcon
	 */
	public EpiAddressPage clickAddressBookLink( )
	{
		EpiAddressPage addresspage = new EpiAddressPage(driver);
		waitForPageLoad();
		wait.waitForElementDisplayed(ADDRESS_BOOK_LINK);
		click.clickElement(ADDRESS_BOOK_LINK);
		waitForPageLoad();
		return addresspage;
	}

	/**
	 * This method is used to Click on Order History Page from UserIcon
	 */
	public EpiOrderHistoryPage clickOrderHistory( )
	{
		EpiOrderHistoryPage orderhistorypage = new EpiOrderHistoryPage(driver);
		waitForPageLoad();
		wait.waitForElementDisplayed(ORDER_HISTORY);
		click.clickElement(ORDER_HISTORY);
		waitForPageLoad();
		return orderhistorypage;
	}

	/**
	 * This method is used to click on User Icon and expand its section
	 */
	public void clickPortalUserIcon( )
	{
		click.clickElement(USER_ICON);
		waitForPageLoad();
	}

	/**
	 * This method is used to logout from Episerver portal
	 */
	public void portallogout( )
	{
		window.switchToDefaultContent();
		clickPortalUserIcon();
		wait.waitForElementDisplayed(SIGNOUT_LINK);
		click.clickElement(SIGNOUT_LINK);
		waitForPageLoad();

		if ( element.isElementDisplayed(SIGNOUT_LINK) )
		{
			click.clickElement(SIGNOUT_LINK);
		}
		waitForPageLoad();
	}
}
