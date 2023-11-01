package com.vertex.quality.connectors.dynamics365.business.components;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.dynamics365.business.enums.BusinessPageTitles;
import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessBasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * this class represents the main navigation menu on the home page
 *
 * @author osabha, cgajes
 */
public class BusinessHomePageMainNavMenu extends VertexComponent
{
	protected By subMenuParentConLoc = By.className("navigation-bar-secondary-group-over-expanded");
	protected By subMenuConLoc = By.className("horizontal-container--1YXMnNmkMLMjmLEal1LgT_");
	protected By mainMenuParentConLoc = By.className("nav-bar-content");
	protected By mainNavTabLoc = By.className("caption--20PrttGVw_09n9_mM8YKgm");
	protected By mainFrameLoc = By.className("designer-client-frame");
	protected By navbarCaption = By.className("ms-navbar-node-caption");
	protected By buttonSubTextContainer = By.className("ms-Button-textContainer");
	protected By navbarCaptionTab = By.xpath("//span[@data-automationid='splitbuttonprimary']");
	ArrayList<String> ext = new ArrayList<>(Arrays.asList("Posted Sales Credit Memos","Posted Sales Invoices","Posted Sales Return Receipts"));

	public BusinessHomePageMainNavMenu( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}

	/**
	 * navigates to the first layer navigation menus
	 *
	 * @param tabTitle name of the first menu to navigate to
	 */
	public void goToNavTab( String tabTitle )
	{
		waitForPageLoad();
		List<WebElement> navTabs = null;
		String xpath = "//span[@aria-label='%s']";

		try
		{
			navTabs = wait.waitForAllElementsPresent(By.xpath(String.format(xpath, tabTitle)),20);
				}
		catch ( TimeoutException e )
		{
			new WebDriverWait(driver, 10)
					.ignoring(TimeoutException.class)
					.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(mainFrameLoc));
			navTabs = wait.waitForAllElementsPresent(By.xpath(String.format(xpath, tabTitle)),20);
		}

		click.clickElementIgnoreExceptionAndRetry(navTabs.get(navTabs.size() - 1));

	}

	/**
	 * navigates to the first layer navigation menus
	 *
	 * @param tabTitle name of the first menu to navigate to
	 */
	public void goToNavTab1( String tabTitle )
	{
		List<WebElement> navTabs = wait.waitForAllElementsPresent(navbarCaptionTab);
		for ( WebElement tab : navTabs )
		{
			String tabText = tab.getText();
			if ( tabText.equals(tabTitle) )
			{
				click.clickElementCarefully(tab);
				break;
			}
		}
	}

	/**
	 *This Method is for "Posted Invoice", "Posted Memo"
	 *
	 * @param tabTitle name of the first menu to navigate to
	 */
	public void goToSubNavTabFromDocumentType( String tabTitle )
	{
		List<WebElement> navTabs = null;
		String xpath = "//span[@aria-label='%s']";

		try
		{
			navTabs = wait.waitForAllElementsPresent(By.xpath(String.format(xpath, tabTitle)),20);
		}
		catch ( TimeoutException e )
		{
			new WebDriverWait(driver, 10)
					.ignoring(TimeoutException.class)
					.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(mainFrameLoc));
		}

		click.clickElementIgnoreExceptionAndRetry(navTabs.get(navTabs.size()-1));
	}

	/**
	 * navigates to a sub menu from a main one in the main nav panel
	 *
	 * @param tabTitle       name of tab whose submenu to open
	 * @param subNavTabTitle name of navigation link to click in submenu
	 *
	 * @return the class of the page accessed after the navigation
	 */
	public <T extends BusinessBasePage> T goToSubNavTab( String tabTitle, String subNavTabTitle )
	{
		goToNavTab(tabTitle);

		List<WebElement> subMenuTabsButtons = wait.waitForAllElementsPresent(buttonSubTextContainer);
		for (WebElement subMenuTabButton : subMenuTabsButtons) {
			String subMenuText = subMenuTabButton.getText();
			if (subMenuText.equals(subNavTabTitle)) {
				click.clickElementCarefully(subMenuTabButton);
				break;
			}
		}

			jsWaiter.waitForLoadAll();
			jsWaiter.sleep(5000);
		return initializePageObject(getPage().getPageClass());
	}
	/**
	 * navigates to a sub menu from a main one in the main nav panel
	 *
	 * @param tabTitle       name of tab whose submenu to open
	 * @param subNavTabTitle name of navigation link to click in submenu
	 *
	 * @return the class of the page accessed after the navigation
	 */
	public <T extends BusinessBasePage> void goToSubNavTab1(String tabTitle, String subNavTabTitle )
	{
		goToNavTab1(tabTitle);

		By subNavTab = By.cssSelector(String.format("[aria-label='%s']", subNavTabTitle));

		wait.waitForElementDisplayed(subNavTab);
		click.clickElementIgnoreExceptionAndRetry(subNavTab);

		waitForPageLoad();
	}

	/**
	 * navigates to a sub menu from a main one in the main nav panel
	 *
	 * @param documentType   name of tab whose submenu to open
	 * @param subTabTitle Sub Tab Title
	 * @param subNavTabTitle name of navigation link to click in submenu
	 *
	 * @return the class of the page accessed after the navigation
	 */
	public <T extends BusinessBasePage> void goToChildSubNavTab1(String documentType, String subTabTitle,  String subNavTabTitle )
	{
		goToSubNavTabFromDocumentType(subTabTitle);

		List<WebElement> navTabs = null;
		String xpath = "//span[@aria-label='%s']";
		try
		{
			navTabs = wait.waitForAllElementsPresent(By.xpath(String.format(xpath, subNavTabTitle)),20);
		}
		catch ( TimeoutException e )
		{
			new WebDriverWait(driver, 10)
					.ignoring(TimeoutException.class)
					.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(mainFrameLoc));
		}
		click.clickElementCarefully(navTabs.get(navTabs.size()-1));
		waitForPageLoad();
	}
	/**
	 * Gets the page title enum
	 *
	 * @return the page title enum
	 */
	private BusinessPageTitles getPage( )
	{
		BusinessPageTitles page = null;
		String CurrentPageTitle = parent.getPageTitle();
		for ( BusinessPageTitles pageTitle : BusinessPageTitles.values() )
		{
			if ( pageTitle
				.getPageTitle()
				.equals(CurrentPageTitle) )
			{
				page = pageTitle;
			}
		}
		return page;
	}

	/**
	 * It is Getting By object for Given Page Name
	 * @param pageName
	 * @return By obj of Posted page
	 */
	public By getByObjOfPostedPage(String pageName)
	{
		String tabByObj = String.format("//form[contains(@aria-label,'%s')]//span[@data-automationid='splitbuttonprimary']",pageName);
		return By.xpath(tabByObj);
	}
}
