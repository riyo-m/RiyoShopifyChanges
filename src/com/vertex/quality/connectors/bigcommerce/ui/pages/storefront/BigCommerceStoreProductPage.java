package com.vertex.quality.connectors.bigcommerce.ui.pages.storefront;

import com.vertex.quality.connectors.bigcommerce.ui.dialogs.BigCommerceStoreCartAdditionDialog;
import com.vertex.quality.connectors.bigcommerce.ui.pages.storefront.base.BigCommerceStorePreCheckoutBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * representation of the page on the Big Commerce Storefront site
 * which displays the details of some item in the store's catalog
 *
 * @author ssalisbury
 */
public class BigCommerceStoreProductPage extends BigCommerceStorePreCheckoutBasePage
{
	protected final By addToCartButton = By.id("form-action-addToCart");

	public BigCommerceStoreCartAdditionDialog itemAddedDialog = null;

	public BigCommerceStoreProductPage( final WebDriver driver )
	{
		super(driver);
	}

	/**
	 * adds the current page's item to the shopping cart, which loads a dialog for previewing the cart with the newly
	 * added item
	 */
	public void addItemToCart( )
	{
		WebElement addToCartButtonElem = wait.waitForElementEnabled(addToCartButton);
		click.clickElement(addToCartButtonElem);

		this.itemAddedDialog = initializePageObject(BigCommerceStoreCartAdditionDialog.class, this);
	}
}
