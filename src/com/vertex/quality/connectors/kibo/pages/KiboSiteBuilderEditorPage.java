package com.vertex.quality.connectors.kibo.pages;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * this class represents the site builder editor page
 * which allows user to go to the live view of the maxine front store
 * contains the methods to navigate to the live store
 *
 * @author osabha
 */
public class KiboSiteBuilderEditorPage extends VertexPage
{
	protected By viewButtonLoc = By.className("taco-siteview-dropdown");
	protected By viewLiveButtonLoc = By.className("x-menu-item-text");

	public KiboSiteBuilderEditorPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * getter method to locate the view button
	 *
	 * @return WebElement of the view button
	 */
	protected WebElement getViewButton( )
	{
		WebElement viewButton = wait.waitForElementPresent(viewButtonLoc);
		return viewButton;
	}

	/**
	 * uses the getter method to locate the view button and then clicks on it
	 */
	public void clickViewButton( )
	{
		WebElement viewButton = getViewButton();
		viewButton.click();
	}

	/**
	 * getter method to locate the live view button from the view list
	 *
	 * @return live view button WebElement
	 */
	protected WebElement getViewLiveButton( )
	{
		WebElement viewLiveButton = null;
		String expectedText = "View Live";

		List<WebElement> viewLiveButtonContainers = wait.waitForAllElementsPresent(viewLiveButtonLoc);
		viewLiveButton = element.selectElementByText(viewLiveButtonContainers, expectedText);

		return viewLiveButton;
	}

	/**
	 * uses the getter method to locate the view live button and then clicks on it
	 *
	 * @return new instance of the Kibo maxine live page class
	 */
	public KiboStoreFrontPage clickViewLiveButton( )
	{
		WebElement viewLiveButton = getViewLiveButton();

		viewLiveButton.click();

		String parentHandle = driver.getWindowHandle();
		for ( String handle : driver.getWindowHandles() )
		{
			if ( !parentHandle.equals(handle) )
			{
				driver
					.switchTo()
					.window(handle);
			}
		}

		return new KiboStoreFrontPage(driver);
	}
}
