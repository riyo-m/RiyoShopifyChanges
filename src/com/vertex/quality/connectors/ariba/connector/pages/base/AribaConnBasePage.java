package com.vertex.quality.connectors.ariba.connector.pages.base;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.ariba.connector.components.AribaConnFooterPane;
import com.vertex.quality.connectors.ariba.connector.components.AribaConnHeaderPane;
import com.vertex.quality.connectors.ariba.connector.components.AribaConnNavPanel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Generic representation of a webpage on the connector's configuration site.
 *
 * @author ssalisbury
 */
public abstract class AribaConnBasePage extends VertexPage
{
	public final AribaConnHeaderPane header;
	public final AribaConnNavPanel navPanel;
	public final AribaConnFooterPane footer;

	protected final String contentTitle;

	protected final By contentDivLoc = By.id("replaceMe");

	//this only exists on the 'home page', which loads right after logging in
	protected final By loggedInHeader = By.id("logged-in-text");

	/**
	 * initializes a representation of a webpage on Ariba's Vertex Configuration website
	 *
	 * @param driver the selenium driver that allows interaction with the webpage
	 * @param title  the title of the page as it is listed in the webpage's central body of content
	 *
	 * @author ssalisbury
	 */
	public AribaConnBasePage( final WebDriver driver, final String title )
	{
		super(driver);

		header = new AribaConnHeaderPane(driver, this);
		navPanel = new AribaConnNavPanel(driver, this);
		footer = new AribaConnFooterPane(driver, this);

		this.contentTitle = title;
	}

	/**
	 * checks whether the test's driver is currently accessing a webpage that this Page class
	 * represents
	 * defaults to identifying the current webpage using titleTagLoc
	 *
	 * @return whether the test's driver is currently accessing a webpage that this Page class represents
	 *
	 * @author ssalisbury
	 */
	public boolean isCurrentPage( )
	{
		boolean isCurrPage = false;
		String currPageTitle = getCurrentContentTitle();
		isCurrPage = contentTitle.equals(currPageTitle);
		return isCurrPage;
	}

	/**
	 * retrieves the 'title' of the page from an element that's stored alongside the 'main' element
	 * in the div that represents the webpage's main body of content
	 *
	 * @return the value of the 'title' element that's stored alongside the 'main' element in the
	 * div that represents the webpage's central body of content
	 *
	 * @author ssalisbury
	 */
	protected String getCurrentContentTitle( )
	{
		String title = null;

		WebElement contentDivElem = wait.waitForElementPresent(contentDivLoc);

		final ExpectedCondition<WebElement> pageTitleLoaded = driver ->
		{
			WebElement titleElem = null;
			List<WebElement> titleElems = element.getWebElements(By.tagName("title"), contentDivElem);
			if ( titleElems.size() > 0 )
			{
				assert titleElems.size() == 1;
				titleElem = titleElems.get(0);
			}
			else
			{
				List<WebElement> justLoggedInHeaders = element.getWebElements(loggedInHeader);
				if ( justLoggedInHeaders.size() > 0 )
				{
					assert justLoggedInHeaders.size() == 1;
					titleElem = justLoggedInHeaders.get(0);
				}
			}
			return titleElem;
		};
		WebDriverWait waitEngine = new WebDriverWait(driver, DEFAULT_TIMEOUT);
		waitEngine.until(pageTitleLoaded);

		if ( element.isElementPresent(getContentTitleLoc(), contentDivElem) )
		{
			WebElement titleElem = element.getWebElement(getContentTitleLoc(), contentDivElem);

			title = text.getHiddenText(titleElem);
		}

		return title;
	}

	/**
	 * returns the locator for the element which displays the page's title
	 * This exists so that subclasses can have different locators for the content title on their pages but still have
	 * those locators work in this superclass method
	 *
	 * @return the locator for the element which displays the page's title
	 */
	protected By getContentTitleLoc( )
	{
		return By.tagName("title");
	}
}
