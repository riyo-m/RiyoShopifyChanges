package com.vertex.quality.connectors.ariba.portal.pages.catalog;

import com.vertex.quality.connectors.ariba.portal.components.catalog.AribaPortalCatalogTopMenuBar;
import com.vertex.quality.connectors.ariba.portal.pages.common.AribaPortalPostLoginBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * representation of the page which displays the contents of the shopping cart (of items from the
 * catalog)for review
 *
 * @author legacyAribaProgrammer ssalisbury osabha
 */
public class AribaPortalCatalogShoppingCartDetailsPage extends AribaPortalPostLoginBasePage
{
	// TODO add component like AribaPortalRequisitionLineItemsTable and add some of this code to that

	protected final By shoppingCartDetailsTable = By.className("a-cat-shopping-cart-edit-disp");
	protected final By selectAllCheckbox = By.cssSelector("input[bh='CHKGAT']");
	protected final By tableCheckboxContainer = By.className("selectColumnMarker");
	protected final By subTotalElementLoc = By.className("subtotal_item_number");
	public AribaPortalCatalogTopMenuBar topMenuBar;

	public AribaPortalCatalogShoppingCartDetailsPage( WebDriver driver )
	{
		super(driver);

		this.topMenuBar = initializePageObject(AribaPortalCatalogTopMenuBar.class, this);
	}

	/**
	 * deletes all catalog products currently stored in the shopping cart
	 * Note, this is surprisingly difficult to get working, I am aware the code
	 * is poor quality, but little alternative without digging into the Ariba
	 * application's javascript and it just isn't worth our time
	 *
	 * @author dgorecki ssalisbury osabha
	 */
	public AribaPortalCatalogShoppingCartDetailsPage deleteAllProductsFromCart( )
	{
		waitForPageLoad();

		wait.waitForElementPresent(shoppingCartDetailsTable);
		wait.waitForElementDisplayed(tableCheckboxContainer);

		waitForPageLoad();

		WebElement evilCheckox = wait.waitForElementPresent(selectAllCheckbox);

		waitForPageLoad();

		click.bringElementToFront(evilCheckox);

		waitForPageLoad();
		boolean isSelectAllChecked = checkbox.isCheckboxChecked(evilCheckox);
		waitForPageLoad();

		if ( !isSelectAllChecked )
		{
			click.clickElement(evilCheckox);
		}
		waitForPageLoad();

		try
		{
			Thread.sleep(3000);
		}
		catch ( InterruptedException e )
		{
			e.printStackTrace();
		}

		WebElement button = element.getButtonByText("Delete");
		click.clickElement(button);

		waitForPageLoad();

		try
		{
			wait.waitForTextInElement(subTotalElementLoc, "(0 item)");
		}
		catch ( StaleElementReferenceException e )
		{
			wait.waitForTextInElement(subTotalElementLoc, "(0 item)");
		}

		return initializePageObject(AribaPortalCatalogShoppingCartDetailsPage.class);
	}

	/**
	 * leaves the cart review page to browse the catalog more
	 *
	 * @return the catalog home page
	 *
	 * @author legacyAribaProgrammer ssalisbury osabha
	 */
	public AribaPortalCatalogHomePage continueShopping( )
	{
		AribaPortalCatalogHomePage newCatalogHomePage = null;
		WebElement button = element.getButtonByText("Continue Shopping");
		button.click();

		newCatalogHomePage = initializePageObject(AribaPortalCatalogHomePage.class);

		return newCatalogHomePage;
	}
}
