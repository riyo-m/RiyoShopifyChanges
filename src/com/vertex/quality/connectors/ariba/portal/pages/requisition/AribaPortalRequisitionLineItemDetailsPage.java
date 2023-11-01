package com.vertex.quality.connectors.ariba.portal.pages.requisition;

import com.vertex.quality.connectors.ariba.portal.dialogs.requisition.AribaPortalEditLineItemContactDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * the representation of the page where the details of a line item in a PR are filled in
 *
 * @author legacyAribaProgrammer ssalisbury, osabha
 */
public class AribaPortalRequisitionLineItemDetailsPage extends AribaPortalRequisitionCatalogEditItemDetailsPage
{
	protected final By okButtonLoc = By.cssSelector(".w-btn.w-btn-primary>span");
	protected final By pageTitle = By.className("w-sh-title");
	public AribaPortalEditLineItemContactDialog contactDialog;

	public AribaPortalRequisitionLineItemDetailsPage( final WebDriver driver )
	{
		super(driver);
		this.contactDialog = new AribaPortalEditLineItemContactDialog(driver, this);
	}

	/**
	 * saves the changes to the current line item's details
	 *
	 * @return the page for checking out a shopping cart of products from a catalog & so finalizing
	 * a PR
	 *
	 * @author legacyAribaProgrammer ssalisbury, osabha
	 */
	public AribaPortalRequisitionCheckoutPage clickOkButton( )
	{
		waitForUpdate();
		WebElement okButton = wait.waitForElementEnabled(okButtonLoc);
		scroll.scrollElementIntoView(okButton);
		waitForPageLoad();
		click.clickElementCarefully(okButtonLoc);
		AribaPortalRequisitionCheckoutPage checkoutPage = initializePageObject(
			AribaPortalRequisitionCheckoutPage.class);
		wait.waitForElementNotPresent(pageTitle);
		return checkoutPage;
	}
}
