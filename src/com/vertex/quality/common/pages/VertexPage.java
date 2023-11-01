package com.vertex.quality.common.pages;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;

/**
 * A page in one of our supported applications
 *
 * @author dgorecki
 */
public abstract class VertexPage extends VertexAutomationObject
{
	public static final String DOWNLOAD_DIRECTORY_PATH = System.getProperty(
		"services.webdriver.downloadDirectoryPath",
	    FileUtils.getUserDirectoryPath() + File.separator + "SeleniumTestDownloads"
	);
	public VertexPage( WebDriver driver )
	{
		super(driver);
		this.window.setPage(this);

		waitForPageLoad();
	}

	/**
	 * Get a string representing the current URL that the browser is looking at.
	 *
	 * @return The URL of the page currently loaded in the browser
	 */
	public String getCurrentUrl( )
	{
		String url = driver.getCurrentUrl();

		return url;
	}

	/**
	 * refreshPage Function to refresh the browser
	 */
	public void refreshPage( )
	{
		waitForPageLoad();

		driver
			.navigate()
			.refresh();

		waitForPageLoad();
	}

	/**
	 * Waits for the page title to contain a specified string
	 *
	 * @param title the expected title of the resulting page
	 */
	public void waitForPageTitleContains( final String title )
	{
		final WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
		wait.until(ExpectedConditions.titleContains(title));
	}

	/**
	 * retrieves the title of the current page Specifically, the title is the string
	 * that's displayed in the page's tab in the browser
	 *
	 * Note- the full title should show up as hovertext if you mouse over the page's tab, even
	 * if the page's tab is too short to display the whole title normally
	 *
	 * @return the title of the current page
	 *
	 * @author ssalisbury
	 */
	public String getPageTitle( )
	{
		final String pageTitle = driver.getTitle();
		return pageTitle;
	}
}
