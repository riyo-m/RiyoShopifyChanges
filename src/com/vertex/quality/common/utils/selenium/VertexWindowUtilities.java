package com.vertex.quality.common.utils.selenium;

import com.vertex.quality.common.pages.VertexAutomationObject;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.selenium.base.VertexSeleniumUtilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashSet;
import java.util.Set;

/**
 * Common functions for interacting with new tabs. This should be declared as an instance
 * variable in VertexAutomationObject
 *
 * @author hho, osabha
 */
public class VertexWindowUtilities extends VertexSeleniumUtilities
{
	protected VertexPage page = null;

	public VertexWindowUtilities( final VertexAutomationObject pageObject, final WebDriver driver )
	{
		super(pageObject, driver);
	}

	public void setPage( final VertexPage pageInstance )
	{
		this.page = pageInstance;
	}

	/**
	 * collects unique identifiers for each window or tab that the driver currently has open
	 *
	 * @return unique identifiers for each window or tab that the driver currently has open
	 *
	 * @author ssalisbury
	 */
	public Set<String> getWindowHandles( )
	{
		Set<String> handles = driver.getWindowHandles();
		return handles;
	}

	/**
	 * gets the id of the current window
	 *
	 * @return the id of the current window
	 */
	public String getCurrentWindowHandle( )
	{
		String currHandle = driver.getWindowHandle();
		return currHandle;
	}

	/**
	 * Attempts to wait for and then switch to the frame - this is used in certain instances where
	 * switching to the frame and attempting to interact with any of the elements inside the frame
	 * just causes a wait and then times out
	 *
	 * @param frameLocator the locator for the iFrame
	 *
	 * @author hho
	 */
	public void waitForAndSwitchToFrame( final By frameLocator )
	{
		base.wait.waitForElementDisplayed(frameLocator);

		final WebElement iFrame = driver.findElement(frameLocator);

		driver
			.switchTo()
			.frame(iFrame);
	}

	/**
	 * Overloads {@link #waitForAndSwitchToNewWindowHandle(Set)}
	 * to assume that there's only one existing window handle
	 */
	public void waitForAndSwitchToNewWindowHandle( final String existingWindowHandle )
	{
		Set<String> existingWindowHandles = new HashSet<>();
		existingWindowHandles.add(existingWindowHandle);
		waitForAndSwitchToNewWindowHandle(existingWindowHandles);
	}

	/**
	 * Overloads {@link #waitForAndSwitchToNewWindowHandle(Set, int)}
	 * to assume that there's only one existing window handle
	 */
	public void waitForAndSwitchToNewWindowHandle( final String existingWindowHandle, final int timeout )
	{
		Set<String> existingWindowHandles = new HashSet<>();
		existingWindowHandles.add(existingWindowHandle);
		waitForAndSwitchToNewWindowHandle(existingWindowHandles, timeout);
	}

	/**
	 * Overloads {@link #waitForAndSwitchToNewWindowHandle(Set, int)}
	 * to use the default value DEFAULT_TIMEOUT for the parameter timeoutInSeconds
	 */
	public void waitForAndSwitchToNewWindowHandle( final Set<String> existingWindowHandles )
	{
		waitForAndSwitchToNewWindowHandle(existingWindowHandles, VertexAutomationObject.DEFAULT_TIMEOUT);
	}

	/**
	 * waits for the new window or tab that's being opened (by some action taken
	 * right before this function is called) to come into existence and then
	 * switches the driver's focus/control/etc. to that window or tab
	 *
	 * @param existingWindowHandles the identifiers of the windows and tabs that existed before the
	 *                              action was taken that produced the expected tab/window (the one
	 *                              that's being switched to)
	 * @param timeoutInSeconds      how long to wait for one new window handle to load
	 *
	 * @author ssalisbury
	 */
	public void waitForAndSwitchToNewWindowHandle( final Set<String> existingWindowHandles, int timeoutInSeconds )
	{
		base.wait.waitForNewWindowHandle(existingWindowHandles, timeoutInSeconds);

		//filter the set to leave only the new window handle(s)
		final Set<String> newHandles = getWindowHandles();
		newHandles.removeAll(existingWindowHandles);
		assert (newHandles.size() ==
				1);//making sure that we don't get confused/lost for ages when some freaky edge case involves multiple new windows/tabs appearing at once
		String newHandle = newHandles
			.iterator()
			.next();
		driver
			.switchTo()
			.window(newHandle);
	}

	/**
	 * suggested replacement to waitForAndSwitchToNewWindowHandle method above,
	 *
	 * @param expectedPageTitle the title of the page wanted to switch to
	 *
	 * @author osabha
	 */
	public void switchToNewWindowHandle( String expectedPageTitle )
	{
		String parentHandle = driver.getWindowHandle();
		for ( String handle : driver.getWindowHandles() )
		{
			if ( !parentHandle.equals(handle) )
			{
				String currTitle = driver.getTitle();
				if ( expectedPageTitle.equals(currTitle) )
				{
					driver
						.switchTo()
						.window(handle);
					break;
				}
			}
		}
	}

	/**
	 * switchToDefaultContent, Function to switch to Default content
	 * this method helps after switching to an I-frame
	 */
	public void switchToDefaultContent( )
	{
		driver
			.switchTo()
			.defaultContent();
	}

	/**
	 * switchToFrame, Function to switch to frame Currently does not throw an
	 * exception if the timeout expires, an additional check may be needed in the
	 * test to verify expected conditions
	 *
	 * @param locator of (By)
	 * @param timeout the number of seconds to wait for the condition to be met before
	 *                timing out
	 */
	public void switchToFrame( final By locator, final long timeout )
	{
		driver
			.switchTo()
			.defaultContent();
		final WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		final WebElement elem = driver.findElement(locator);
		driver
			.switchTo()
			.frame(elem);
	}

	public void switchToFrame( final By locator )
	{
		switchToFrame(locator, VertexAutomationObject.DEFAULT_TIMEOUT);
	}

	public void switchToInnerFrame( final By locator, final long timeout )
	{
		final WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		final WebElement elem = driver.findElement(locator);
		driver
			.switchTo()
			.frame(elem);
	}

	/**
	 * Switch to frame using index value
	 *
	 * @param index of (integer), frame number to switch
	 */
	public void switchToFrameByIndex( final int index )
	{
		driver
			.switchTo()
			.defaultContent();
		driver
			.switchTo()
			.frame(index);
	}

	/**
	 * switchToWindow
	 */
	public void switchToWindow( )
	{
		for ( final String handle : driver.getWindowHandles() )
		{
			driver
				.switchTo()
				.window(handle);
		}
	}

	/**
	 * switchToWindowTextInTitle Function to switch to window and validate the Text
	 * In Title
	 *
	 * @param title of (String)
	 */
	public void switchToWindowTextInTitle( final String title )
	{
		for ( final String handle : driver.getWindowHandles() )
		{
			driver
				.switchTo()
				.window(handle);
			if ( driver
				.getTitle()
				.contains(title) )
			{
				break;
			}
		}
	}

	/**
	 * switchToParentWindow Function to switch to Parent window
	 *
	 * @param handle of (String), window handle to switch
	 */
	public void switchToParentWindow( final String handle )
	{
		driver
			.switchTo()
			.window(handle);
	}

	/**
	 * opens a new browser window, loads a website with the given url in that window, then switches focus to that window
	 *
	 * @param newUrl           the website which should be opened in a new window
	 * @param newPageType      a specification of the type of page that's loaded on the website in the new window
	 * @param oldWindowHandles the id's of the windows/tabs that existed before this new window was opened
	 *
	 * @return a Page object representing the page that's loaded on the website in the new window
	 */
	public <T extends VertexPage> T openNewWindow( final String newUrl, final Class<T> newPageType,
		Set<String> oldWindowHandles )
	{
		base.executeJs("window.open(arguments[0])", newUrl);
		T newPage = base.initializePageObject(newPageType);
		waitForAndSwitchToNewWindowHandle(oldWindowHandles);
		return newPage;
	}

	/**
	 * change zoom of web page
	 *
	 * @param percentage the percent page size to set
	 *
	 * @author dbondi
	 */
	public void setZoomPercentage( int percentage )
	{
		String zoomScript = String.format("document.body.style.zoom=\"%d%%\"", percentage);
		base.executeJs(zoomScript);
	}
}
