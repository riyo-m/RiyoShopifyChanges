package com.vertex.quality.connectors.bigcommerce.ui.dialogs;

import com.vertex.quality.common.dialogs.VertexDialog;
import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * a dialog on the Big Commerce Storefront site which requires the user to confirm before deleting an item
 *
 * @author ssalisbury
 */
public class BigCommerceStoreCartDeletionDialog extends VertexDialog
{
	protected final By dialogContainer = By.className("swal2-show");
	protected final By confirmButton = By.className("swal2-confirm");

	public BigCommerceStoreCartDeletionDialog( final WebDriver driver, final VertexPage parent )
	{
		super(driver, parent);
	}

	/**
	 * closes the dialog by confirming that the deletion of some item should proceed
	 */
	public void confirmDeletion( )
	{
		WebElement dialogContainerElem = wait.waitForElementDisplayed(dialogContainer);
		WebElement confirmButtonElem = wait.waitForElementEnabled(confirmButton, dialogContainerElem);

		click.clickElement(confirmButtonElem);
		waitForPageLoad();
	}
}
