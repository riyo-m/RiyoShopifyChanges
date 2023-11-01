package com.vertex.quality.connectors.bigcommerce.ui.pages.storefront;

import com.vertex.quality.connectors.bigcommerce.ui.pages.storefront.base.BigCommerceStorePreCheckoutBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * a representation of the page which displays the result of a search for products on the Big Commerce Storefront site
 *
 * @author ssalisbury
 */
public class BigCommerceStoreSearchResultsPage extends BigCommerceStorePreCheckoutBasePage
{
	protected final By resultsContainer = By.className("productGrid");

	protected final By resultItemTitle = By.className("card-title");

	public BigCommerceStoreSearchResultsPage( final WebDriver driver )
	{
		super(driver);
	}

	/**
	 * this finds the product in the search results with exactly the given name and opens that product's details page
	 *
	 * @param productName the exact name of the desired product
	 *
	 * @return the details page for that product
	 */
	public BigCommerceStoreProductPage openResultProductPage( final String productName )
	{
		BigCommerceStoreProductPage itemPage;

		WebElement resultsContainerElem = wait.waitForElementDisplayed(resultsContainer);
		List<WebElement> resultItemTitles = wait.waitForAnyElementsDisplayed(resultItemTitle, resultsContainerElem);

		WebElement desiredItemTitleElem = element.selectElementByText(resultItemTitles, productName);

		if ( desiredItemTitleElem == null )
		{
			//fixme I'm not sure what the best approach is in this failure case. I'm just ending the test for now
			throw new RuntimeException("Couldn't find the expected product in the search results");
		}

		WebElement desiredItemLinkElem = wait.waitForElementEnabled(By.tagName("a"), desiredItemTitleElem);
		click.clickElement(desiredItemLinkElem);

		itemPage = initializePageObject(BigCommerceStoreProductPage.class);

		return itemPage;
	}
}
