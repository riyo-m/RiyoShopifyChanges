package com.vertex.quality.connectors.ariba.portal.pages.catalog;

import com.vertex.quality.common.enums.PageScrollDestination;
import com.vertex.quality.connectors.ariba.portal.components.catalog.AribaPortalCatalogTopMenuBar;
import com.vertex.quality.connectors.ariba.portal.components.requisition.AribaPortalRequisitionSubmittedNotification;
import com.vertex.quality.connectors.ariba.portal.pages.common.AribaPortalPostLoginBasePage;
import com.vertex.quality.connectors.ariba.portal.pages.requisition.AribaPortalRequisitionNonCatalogItemCreationPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * representation of the main/starting page for Ariba's product catalog
 *
 * @author dgorecki, ssalisbury
 */
public class AribaPortalCatalogHomePage extends AribaPortalPostLoginBasePage
{
	protected final By catalogContent = By.id("catContentId");
	protected final By addNonCatalogButton = By.className("w-btn-small");
	protected final By addNonCatalogButtonText = By.className("a-cat-home-non-cat-text");

	public AribaPortalCatalogTopMenuBar topMenuBar;

	protected String addNonCatalogItemText = "Add Non-Catalog Item";

	public AribaPortalCatalogHomePage( WebDriver driver )
	{
		super(driver);

		this.topMenuBar = new AribaPortalCatalogTopMenuBar(driver, this);
	}

	/**
	 * clicks the button for creating a non-catalog item that'll be added to the cart
	 *
	 * @return the page for creating a non-catalog item
	 *
	 * @author ssalisbury
	 */
	public AribaPortalRequisitionNonCatalogItemCreationPage addNonCatalogItem( )
	{
		WebElement addNonCatalogButton = findAddNonCatalogItemButton();

		click.clickElement(addNonCatalogButton, PageScrollDestination.VERT_CENTER);

		AribaPortalRequisitionNonCatalogItemCreationPage nonCatalogItemCreationPage = initializePageObject(
			AribaPortalRequisitionNonCatalogItemCreationPage.class);
		return nonCatalogItemCreationPage;
	}

	/**
	 * generates the popup that indicates that a PR has been successfully submitted
	 *
	 * @return the popup that indicates that a PR has been successfully submitted
	 *
	 * @author ssalisbury
	 */
	public AribaPortalRequisitionSubmittedNotification getSubmittedNotification( )
	{
		return initializePageObject(AribaPortalRequisitionSubmittedNotification.class, this);
	}

	/**
	 * retrieves the button for creating a non-catalog item that'll be added to the cart
	 *
	 * @return the button for creating a non-catalog item that'll be added to the cart
	 *
	 * @author ssalisbury
	 */
	protected WebElement findAddNonCatalogItemButton( )
	{
		WebElement addNonCatalogButtonElem = null;

		WebElement catalogContentContainer = wait.waitForElementPresent(catalogContent);

		List<WebElement> buttons = wait.waitForAllElementsPresent(addNonCatalogButton, catalogContentContainer);
		wait.waitForElementPresent(addNonCatalogButtonText);

		buttonSearch:
		for ( WebElement button : buttons )
		{
			List<WebElement> labels = button.findElements(addNonCatalogButtonText);
			for ( WebElement label : labels )
			{
				String buttonText = attribute.getElementAttribute(label, "innerText");
				if ( addNonCatalogItemText.equals(buttonText) )
				{
					addNonCatalogButtonElem = button;
					break buttonSearch;
				}
			}
		}

		return addNonCatalogButtonElem;
	}

	/**
	 * makes sure the shopping cart is empty
	 *
	 * @return the catalog home page that is loaded by the end of this action
	 *
	 * @author dgorecki ssalisbury
	 */
	public AribaPortalCatalogHomePage startNewRequisition( )
	{
		int numCartItems = topMenuBar.getNumberOfItemsInCart();
		if ( numCartItems > 0 )
		{
			topMenuBar.clearCart();
		}
		return this.initializePageObject(AribaPortalCatalogHomePage.class);
	}
}
