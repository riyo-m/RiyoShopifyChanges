package com.vertex.quality.connectors.bigcommerce.ui.dialogs;

import com.vertex.quality.common.dialogs.VertexDialog;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.bigcommerce.ui.pages.storefront.BigCommerceStoreCheckoutPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * a dialog on the Big Commerce Storefront site which appears when an item is added to the cart
 *
 * @author ssalisbury
 */
public class BigCommerceStoreCartAdditionDialog extends VertexDialog
{
	protected final By dialogContainer = By.id("previewModal");
	protected final By checkoutButton = By.className("button--primary");

	public BigCommerceStoreCartAdditionDialog( final WebDriver driver, final VertexPage parent )
	{
		super(driver, parent);
	}

	/**
	 * opens the checkout page
	 *
	 * @return the checkout page
	 */
	public BigCommerceStoreCheckoutPage proceedToCheckoutPage( )
	{
		WebElement dialogContainerElem = wait.waitForElementDisplayed(dialogContainer);
		WebElement checkoutButtonElem = wait.waitForElementEnabled(checkoutButton, dialogContainerElem);

		click.clickElement(checkoutButtonElem);

		BigCommerceStoreCheckoutPage checkoutPage = initializePageObject(BigCommerceStoreCheckoutPage.class);
		return checkoutPage;
	}
}
