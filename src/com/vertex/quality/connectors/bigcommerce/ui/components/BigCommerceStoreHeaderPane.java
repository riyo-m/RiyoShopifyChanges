package com.vertex.quality.connectors.bigcommerce.ui.components;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.bigcommerce.ui.pages.storefront.BigCommerceStoreCartPage;
import com.vertex.quality.connectors.bigcommerce.ui.pages.storefront.BigCommerceStoreSearchResultsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * handles interactions with elements which are at the top of many pages on the Big Commerce Storefront website
 *
 * @author ssalisbury
 */
public class BigCommerceStoreHeaderPane extends VertexComponent
{
	final By navPagesListConLoc = By.className("navPages-container");
	final By navPagesListLoc = By.className("navPages-action");

	protected final By searchToggleButton = By.className("navUser-action--quickSearch");
	protected final By searchField = By.id("search_query");

	protected final By cartContainer = By.className("navUser-item--cart");
	protected final By cartButton = By.className("navUser-item-cartLabel");
	protected final By cartPreviewPane = By.id("cart-preview-dropdown");

	protected final By cartLoadingOverlay = By.className("loadingOverlay");

	protected final By emptyCartPreview = By.className("previewCart-emptyBody");

	protected final By cartPreviewCheckoutButton = By.className("previewCartAction-checkout");
	protected final By cartPreviewCartLink = By.className("previewCartAction-viewCart");

	public BigCommerceStoreHeaderPane( final WebDriver driver, final VertexPage parent )
	{
		super(driver, parent);
	}

	/**
	 * opens the Shop All tab from the main menu
	 *
	 * @author osabha
	 */
	public void clickShopAll( )
	{
		final String expectedText = "Shop All";
		WebElement shopAllButton = null;
		WebElement navListCont = wait.waitForElementDisplayed(navPagesListConLoc);
		List<WebElement> navTabs = wait.waitForAllElementsPresent(navPagesListLoc, navListCont);
		for ( WebElement thisTab : navTabs )
		{
			String tabText = thisTab.getText();
			if ( expectedText.equalsIgnoreCase(tabText) )
			{
				shopAllButton = thisTab;
				break;
			}
		}
		if ( shopAllButton != null )
		{
			shopAllButton.click();
		}
	}

	/**
	 * searches the storefront for a product with the given name
	 *
	 * @param productName the name of the desired product
	 *
	 * @return a page of products with names which are similar to the given name
	 */
	public BigCommerceStoreSearchResultsPage searchForProduct( final String productName )
	{
		WebElement searchToggleElem = wait.waitForElementDisplayed(searchToggleButton);

		boolean searchFieldNotDisplayed = !element.isElementDisplayed(searchField);
		if ( searchFieldNotDisplayed )
		{
			wait.waitForElementEnabled(searchToggleElem);
			click.clickElement(searchToggleElem);
		}

		WebElement searchFieldElem = wait.waitForElementEnabled(searchField);
		text.enterText(searchFieldElem, productName);
		text.enterText(searchFieldElem, Keys.ENTER, false);

		BigCommerceStoreSearchResultsPage resultsPage = initializePageObject(BigCommerceStoreSearchResultsPage.class);
		return resultsPage;
	}

	/**
	 * checks whether there are any items in the cart
	 *
	 * @return whether there are any items in the cart
	 */
	public boolean isCartEmpty( )
	{
		boolean isCartEmpty;

		WebElement cartPreviewPaneElem = wait.waitForElementPresent(cartPreviewPane);
		wait.waitForElementNotDisplayed(cartLoadingOverlay, cartPreviewPaneElem);

		boolean cartPreviewInitiallyHidden = !element.isElementDisplayed(cartPreviewPaneElem);
		if ( cartPreviewInitiallyHidden )
		{
			WebElement cartButtonElem = wait.waitForElementEnabled(cartButton);
			click.clickElement(cartButtonElem);
		}
		cartPreviewPaneElem = wait.waitForElementPresent(cartPreviewPane);

		WebElement previewPane = wait.waitForElementDisplayed(cartPreviewPaneElem);
		wait.waitForElementNotDisplayed(cartLoadingOverlay, cartPreviewPaneElem);

		isCartEmpty = element.isElementDisplayed(emptyCartPreview, previewPane);

		//collapses the cart preview pane if this function had opened it so this function leaves minimal side effects
		if ( cartPreviewInitiallyHidden )
		{
			WebElement cartButtonElem = wait.waitForElementEnabled(cartButton);
			click.clickElement(cartButtonElem);

			wait.waitForElementNotDisplayed(cartLoadingOverlay, cartPreviewPaneElem);
		}

		return isCartEmpty;
	}

	/**
	 * opens the cart page
	 *
	 * @return the cart page
	 */
	public BigCommerceStoreCartPage navigateToCartPage( )
	{
		BigCommerceStoreCartPage cartPage;

		boolean cartPreviewInitiallyHidden = !element.isElementDisplayed(cartPreviewPane);
		if ( cartPreviewInitiallyHidden )
		{
			WebElement cartButtonElem = wait.waitForElementEnabled(cartButton);
			click.clickElement(cartButtonElem);
		}

		WebElement previewPane = wait.waitForElementDisplayed(cartPreviewPane);
		WebElement cartLinkElem = wait.waitForElementDisplayed(cartPreviewCartLink, previewPane);

		wait.waitForElementEnabled(cartLinkElem);
		click.clickElement(cartLinkElem);

		cartPage = initializePageObject(BigCommerceStoreCartPage.class);

		return cartPage;
	}
}
