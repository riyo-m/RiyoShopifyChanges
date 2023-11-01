package com.vertex.quality.connectors.dynamics365.business.pages;

import com.vertex.quality.connectors.dynamics365.business.components.BusinessVertexExtensionDialog;
import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * represents the Extensions Page and all the necessary methods to interact with it
 *
 * @author osabha, cgajes
 */
public class BusinessExtensionsPage extends BusinessBasePage
{
	public BusinessVertexExtensionDialog vertexExtensionDialog;

	public BusinessExtensionsPage( WebDriver driver )
	{
		super(driver);
		this.vertexExtensionDialog = new BusinessVertexExtensionDialog(driver, this);
	}

	protected By searchContainerLoc = By.className("ms-SearchBox");
	protected By searchBarLoc = By.className("ms-SearchBox-field");
	protected By vertexExtensionLoc = By.xpath("//div[text()='Vertex Tax Links for Dynamics 365 Business Central']");
	protected By resultsListLoc = By.xpath("//div[contains(@class,'ms-nav-listform')]");
	protected By extensionContainerLoc = By.className("brick-2-fields");
	protected By extensionTitleLoc = By.className("brick-field-2");
	protected By extensionLinkLoc = By.className("brick-field-1");

	protected By extensionsDialogLoc = By.className("ms-nav-cardform");

	/**
	 * Click search button to activate search bar, return search bar
	 * If bar is already active, clicking button has no effect
	 *
	 * @return search bar
	 */
	public WebElement getSearchBar( )
	{
		WebElement openSearchBar = wait.waitForElementDisplayed(searchContainerLoc);
		click.clickElement(openSearchBar);

		WebElement searchBar = wait.waitForElementEnabled(searchBarLoc);

		return searchBar;
	}

	/**
	 * Use the search bar to search for an extension or extensions,
	 * by title
	 *
	 * @param searchInput
	 */
	public void searchForExtension( String searchInput )
	{
		WebElement searchBar = getSearchBar();

		text.enterText(searchBar, searchInput);

		text.pressEnter(searchBar);

		waitForPageLoad();
	}

	/**
	 * finds vertex extension on the extensions page
	 *
	 * @return WebElement of the vertex extension
	 */
	public WebElement getVertexExtension( )
	{
		String expectedText = "Vertex Tax Links for Dynamics 365 Business Central";
		WebElement vertexExtension = null;
		WebElement listContainer = wait.waitForElementPresent(resultsListLoc);
		wait.waitForAllElementsPresent(extensionContainerLoc, listContainer);
		List<WebElement> extensionList = wait.waitForAllElementsEnabled(extensionContainerLoc, listContainer);
		for ( WebElement extension : extensionList )
		{
			String extensionText = extension.getText();
			if ( extensionText.contains(expectedText) )
			{
				WebElement extensionLink = wait.waitForElementEnabled(extensionLinkLoc, extension);
				vertexExtension = extensionLink;
				break;
			}
		}
		return vertexExtension;
	}

	/**
	 * uses the get vertex extension method to locate vertex extension and then clicks on it
	 */
	public void clickVertexExtension( )
	{
		WebElement vertexExtension = wait.waitForElementDisplayed(vertexExtensionLoc);
		click.clickElementIgnoreExceptionAndRetry(vertexExtension);

		wait.waitForElementDisplayed(extensionsDialogLoc);
	}
}
