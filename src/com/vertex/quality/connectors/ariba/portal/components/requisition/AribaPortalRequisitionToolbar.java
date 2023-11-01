package com.vertex.quality.connectors.ariba.portal.components.requisition;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.connectors.ariba.portal.enums.AribaPortalToolbarActions;
import com.vertex.quality.connectors.ariba.portal.pages.catalog.AribaPortalCatalogHomePage;
import com.vertex.quality.connectors.ariba.portal.pages.common.AribaPortalPostLoginBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Component representing the toolbar on the Requisition page
 *
 * @author ssalisbury, dgorecki
 */
public class AribaPortalRequisitionToolbar extends VertexComponent
{
	protected By TOOLBAR_BUTTONS_CONTAINER = By.className("a-non-cat-itm-act-btn");
	protected By TOOLBAR_BUTTON = By.className("w-btn");

	public AribaPortalRequisitionToolbar( WebDriver driver, AribaPortalPostLoginBasePage parent )
	{
		super(driver, parent);
	}

	/**
	 * Clicks the button to update requisition taxes
	 *
	 * @author ssalisbury, dgorecki
	 */
	public void updateRequisitionTaxes( )
	{
		clickToolbarButton(AribaPortalToolbarActions.UPDATE_TAXES);
		((AribaPortalPostLoginBasePage) parent).waitForUpdate();
	}

	/**
	 * Submits a requisition
	 *
	 * @return the resulting page after submission is complete
	 *
	 * @author ssalisbury, dgorecki
	 */
	public AribaPortalCatalogHomePage submitRequisition( )
	{
		clickToolbarButton(AribaPortalToolbarActions.SUBMIT);
		return initializePageObject(AribaPortalCatalogHomePage.class);
	}

	/**
	 * Clicks the specified toolbar button by text
	 *
	 * @param expectedButtonText the utton to click
	 *
	 * @author ssalisbury, dgorecki, osabha
	 */
	protected void clickToolbarButton( AribaPortalToolbarActions expectedButtonText )
	{
		waitForPageLoad();
		WebElement container = wait.waitForElementDisplayed(TOOLBAR_BUTTONS_CONTAINER);
		List<WebElement> toolbarButtons = wait.waitForAllElementsDisplayed(By.tagName("button"), container);
		WebElement toolbarButton = element.selectElementByText(toolbarButtons,
			expectedButtonText.getActionOptionLabel());
		click.clickElementCarefully(toolbarButton);
	}
}
