package com.vertex.quality.connectors.bigcommerce.ui.pages.storefront;

import com.vertex.quality.connectors.bigcommerce.ui.dialogs.BigCommerceStoreCartDeletionDialog;
import com.vertex.quality.connectors.bigcommerce.ui.pages.storefront.base.BigCommerceStorePreCheckoutBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * the page on Big Commerce's Storefront website which lists the products that have been selected for purchase
 * in the current order
 *
 * @author ssalisbury
 */
public class BigCommerceStoreCartPage extends BigCommerceStorePreCheckoutBasePage
{
	protected final By cartItemsTable = By.className("cart");
	protected final By cartItemsTableBody = By.className("cart-list");
	protected final By cartItem = By.className("cart-item");
	protected final By itemDeleteButton = By.className("cart-remove");

	public BigCommerceStoreCartPage( final WebDriver driver )
	{
		super(driver);
	}

	/**
	 * deletes all items in the cart
	 */
	public void clearCart( )
	{
		WebElement cartItemsContainer = wait.waitForElementDisplayed(cartItemsTableBody);
		List<WebElement> cartItemElems = wait.waitForAnyElementsDisplayed(cartItem, cartItemsContainer);
		int initialCartSize = cartItemElems.size();

		for ( int c = 0 ; c < initialCartSize ; c++ )
		{
			cartItemsContainer = wait.waitForElementDisplayed(cartItemsTableBody);
			WebElement firstCartItemElem = wait.waitForElementDisplayed(cartItem, cartItemsContainer);

			WebElement itemDeleteButtonElem = wait.waitForElementEnabled(itemDeleteButton, firstCartItemElem);
			click.clickElement(itemDeleteButtonElem);
			BigCommerceStoreCartDeletionDialog confirmDialog = initializePageObject(
				BigCommerceStoreCartDeletionDialog.class, this);
			confirmDialog.confirmDeletion();
		}
	}
}
