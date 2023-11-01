package com.vertex.quality.connectors.ariba.portal.components.catalog;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.ariba.portal.components.base.AribaPortalComponent;
import com.vertex.quality.connectors.ariba.portal.pages.catalog.AribaPortalCatalogHomePage;
import com.vertex.quality.connectors.ariba.portal.pages.catalog.AribaPortalCatalogProductsListPage;
import com.vertex.quality.connectors.ariba.portal.pages.catalog.AribaPortalCatalogShoppingCartDetailsPage;
import com.vertex.quality.connectors.ariba.portal.pages.requisition.AribaPortalRequisitionCheckoutPage;
import org.openqa.selenium.*;

/**
 * handles interaction with a header bar on all pages in the Catalog section of the Ariba Portal
 * site.
 * It primarily deals with a search field for products in the catalog & a button to access/modify
 * the shopping cart
 *
 * @author ssalisbury dgorecki osabha
 */
public class AribaPortalCatalogTopMenuBar extends AribaPortalComponent
{
	protected final By cartSummaryCloseButtonLoc = By.cssSelector("a[aria-label='Close']");
	protected final By cartSummaryConLoc = By.className("cartMenuSize");
	protected final By menuBarCartIcon = By.className("a-cat-cart");
	protected final By menuBarCartCount = By.className("a-cart-nb");
	protected final By menuBarReviewCartButton = By.cssSelector("a[title='Review Cart']");
	protected final By menuBarProceedToCheckoutButton = By.cssSelector("a[title='Proceed to Checkout']");
	protected final By menuBarSearchField = By.className("w-chInput");
	protected final By updateLoadingPopupLoc = By.id("awwaitMessage");
	protected final By menuBarSearchButton = By.className("a-cat-srch-submit");
	protected final By recentSearchBlocksLoc = By.className("cat_section_title");

	public AribaPortalCatalogTopMenuBar( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}

	/**
	 * opens the cart dropdown panel & through that opens the checkout page
	 *
	 * @return the catalog checkout page
	 *
	 * @author dgorecki, ssalisbury
	 */
	public AribaPortalRequisitionCheckoutPage proceedToCheckout( )
	{
		parent.refreshPage();
		try
		{
			WebElement cartIcon = wait.waitForElementEnabled(menuBarCartIcon);
			click.clickElement(cartIcon);
		}
		catch ( StaleElementReferenceException e )
		{
			WebElement cartIcon = wait.waitForElementEnabled(menuBarCartIcon);
			click.clickElement(cartIcon);
		}

		wait.waitForElementPresent(menuBarProceedToCheckoutButton);
		WebElement proceedToCheckoutButton = wait.waitForElementEnabled(menuBarProceedToCheckoutButton);
		click.clickElement(proceedToCheckoutButton);

		AribaPortalRequisitionCheckoutPage checkoutPage = initializePageObject(
			AribaPortalRequisitionCheckoutPage.class);

		return checkoutPage;
	}

	/**
	 * opens the cart dropdown panel & through that opens the catalog's shopping card details page
	 *
	 * @return the catalog's shopping card details page
	 *
	 * @author dgorecki, ssalisbury
	 */
	public AribaPortalCatalogShoppingCartDetailsPage openCart( )
	{
		waitForPageLoad();
		WebElement cartIcon = wait.waitForElementEnabled(menuBarCartIcon);
		click.clickElement(cartIcon);

		WebElement reviewCartButton = wait.waitForElementEnabled(menuBarReviewCartButton);
		click.clickElement(reviewCartButton);

		return initializePageObject(AribaPortalCatalogShoppingCartDetailsPage.class);
	}

	/**
	 * retrieves the number of items in the shopping cart
	 *
	 * @return the number of items in the shopping cart
	 *
	 * @author dgorecki, ssalisbury
	 */
	public int getNumberOfItemsInCart( )
	{
		String cartCount = "";
		waitForPageLoad();
		try
		{
			WebElement cartCountElement = wait.waitForElementPresent(menuBarCartCount);

			cartCount = text.getHiddenText(cartCountElement);
		}
		catch ( StaleElementReferenceException e )
		{
			WebElement cartCountElement = wait.waitForElementPresent(menuBarCartCount);

			cartCount = text.getHiddenText(cartCountElement);
		}

		int cartInt = Integer.parseInt(cartCount);

		return cartInt;
	}

	/**
	 * enters a search query into the search field near the top of the center of a catalog page
	 * and executes the search, which loads a page that lists the products which match the query
	 *
	 * @param searchTerm a search query for products in the catalog
	 *
	 * @return a page that lists the products in the catalog which match the query
	 *
	 * @author dgorecki, ssalisbury
	 */
	public AribaPortalCatalogProductsListPage searchCatalog( final String searchTerm )
	{
		try
		{
			wait.waitForAllElementsPresent(recentSearchBlocksLoc, FIVE_SECOND_TIMEOUT);
		}
		catch ( TimeoutException e )
		{
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}

		wait.waitForElementDisplayed(menuBarSearchField);
		wait.waitForElementEnabled(menuBarSearchButton);

		try
		{
			enterSearchTextAndVerifyTextEntered(searchTerm);
		}
		catch ( StaleElementReferenceException e )
		{
			enterSearchTextAndVerifyTextEntered(searchTerm);
		}

		WebElement searchButton = wait.waitForElementEnabled(menuBarSearchButton);

		try
		{
			click.clickElement(searchButton);
		}
		catch ( StaleElementReferenceException e )
		{
			WebElement searchButton1 = wait.waitForElementEnabled(menuBarSearchButton);
			click.clickElement(searchButton1);
		}
		try
		{
			wait.waitForElementDisplayed(updateLoadingPopupLoc, FIVE_SECOND_TIMEOUT);
		}
		catch ( TimeoutException e )
		{
		}
		wait.waitForElementNotDisplayed(updateLoadingPopupLoc, DEFAULT_TIMEOUT);

		return initializePageObject(AribaPortalCatalogProductsListPage.class);
	}

	/**
	 * empties the shopping cart
	 *
	 * @return the catalog home page that is returned to at the end of this action
	 *
	 * @author dgorecki ssalisbury osabha
	 */
	public AribaPortalCatalogHomePage clearCart( )
	{
		AribaPortalCatalogShoppingCartDetailsPage cartPage = openCart();
		AribaPortalCatalogShoppingCartDetailsPage clearedCartPage = cartPage.deleteAllProductsFromCart();
		clearedCartPage.continueShopping();

		return initializePageObject(AribaPortalCatalogHomePage.class);
	}

	/**
	 * Waits for the popup to appear when a product is added to the cart so it can be verified
	 *
	 * @author dgorecki osabha
	 */
	public void waitForCartSummaryPopup( )
	{
		waitForPageLoad();
		wait.waitForElementPresent(menuBarCartIcon);
		wait.waitForElementPresent(menuBarProceedToCheckoutButton);
		wait.waitForElementDisplayed(menuBarProceedToCheckoutButton);
		waitForPageLoad();
	}

	/**
	 * locates and clicks on the close cart summary Popup up  button
	 * allows user to go back to the search bar to add more products
	 *
	 * @author osabha
	 */
	public void closeCartSummaryPopup( )
	{
		WebElement summaryCon = wait.waitForElementPresent(cartSummaryConLoc);
		WebElement closeCartSummaryPopupButton = wait.waitForElementEnabled(cartSummaryCloseButtonLoc, summaryCon);
		click.clickElement(closeCartSummaryPopupButton);
		waitForPageLoad();
	}

	/**
	 * helper method that enters search text and verifies it was entered correctly,
	 * if not retired to clear field and enter text again
	 *
	 * @param searchTerm text to enter in the search field
	 */
	public void enterSearchTextAndVerifyTextEntered( final String searchTerm )
	{
		typeInField(menuBarSearchField, searchTerm);
		WebElement textField = wait.waitForElementEnabled(menuBarSearchField);
		String fieldText = textField.getAttribute("value");
		if ( !searchTerm.equals(fieldText) )
		{
			waitForPageLoad();
			typeInField(menuBarSearchField, searchTerm);
		}
		else
		{
			System.out.println("Search Successful");
		}
	}

	/**
	 * Helper method to enter the search text letter by letter into the search field
	 *
	 * @param loc   search field
	 * @param value text to type in
	 */
	public void typeInField( final By loc, final String value )
	{
		WebElement elem = wait.waitForElementPresent(loc);
		elem.clear();

		for ( int i = 0 ; i < value.length() ; i++ )
		{
			char c = value.charAt(i);
			String s = new StringBuilder()
				.append(c)
				.toString();
			elem.sendKeys(s);
		}
		waitForPageLoad();
	}
}
