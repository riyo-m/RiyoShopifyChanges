package com.vertex.quality.connectors.dynamics365.business.components;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.dynamics365.business.enums.BusinessEditSalesPageTitles;
import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessSalesBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * this class represents the navigation menu on the first page accessed after the main navigation is done
 *
 * @author osabha
 */
public class BusinessPagesNavMenu extends VertexComponent
{
	protected By newButtonLoc = By.cssSelector("button[aria-label='New'][title='Create a new entry.']");
	protected By searchButtonLoc = By.className("ms-SearchBox");
	protected By searchFieldLoc = By.cssSelector("input[aria-label='Search Customers']");
	protected By alertOkButtonLoc = By.className("walkme-custom-balloon-button");
	protected final int shortTimeout = 5;

	public BusinessPagesNavMenu( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}

	/**
	 * clicks on the +New button
	 *
	 * @return the class of the page accesses by the click.
	 */
	public <T extends BusinessSalesBasePage> T clickNew( )
	{
		waitForPageLoad();
		List<WebElement> buttonList = wait.waitForAllElementsPresent(newButtonLoc);
		WebElement newButton = buttonList.get(buttonList.size() - 1);
		click.clickElementIgnoreExceptionAndRetry(newButton);
		waitForPageLoad();
		handlePopUpMessage(alertOkButtonLoc, shortTimeout);
		return initializePageObject(getPage().getPageClass());
	}

	/**
	 * clicks the search button and inputs a document name or number to search for
	 *
	 * @param docNameOrNumber
	 */
	public void searchForDocument( String docNameOrNumber )
	{
		List<WebElement> buttonList = wait.waitForAllElementsPresent(searchButtonLoc);
		WebElement newButton = buttonList.get(buttonList.size() - 1);
		click.clickElement(newButton);

		WebElement inputField = wait.waitForElementDisplayed(searchFieldLoc);
		text.enterText(inputField, docNameOrNumber);
		text.pressEnter(inputField);
	}

	/**
	 * Gets the page title enum
	 *
	 * @return the page title enum
	 */
	private BusinessEditSalesPageTitles getPage( )
	{
		BusinessEditSalesPageTitles page = null;
		String pageTitle = parent.getPageTitle();
		for ( BusinessEditSalesPageTitles title : BusinessEditSalesPageTitles.values() )
		{
			if ( title
				.getPageTitle()
				.equals(pageTitle) )
			{
				page = title;
			}
		}
		return page;
	}
}
