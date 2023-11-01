package com.vertex.quality.connectors.ariba.portal.pages.procurement;

import com.vertex.quality.connectors.ariba.portal.pages.common.AribaPortalPostLoginBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * represents the page that details of the invoice opened
 * contains the only method needed from it, which is to open that invoice to reconcile it
 *
 * @author osabha
 */
public class AribaPortalBeforeReconciliationPage extends AribaPortalPostLoginBasePage
{
	public AribaPortalBeforeReconciliationPage( final WebDriver driver )
	{
		super(driver);
	}

	protected final By commandButtonsCont = By.className("cmdButtons");
	protected final String editButtonTitle = "Edit this request";
	protected final String openButtonTitle = "Open Invoice Reconciliation";

	/**
	 * locates and clicks on the open invoice button
	 *
	 * @return new instance of the invoice reconciliation page
	 */
	public AribaPortalReconciliationPage openInvoiceToReconcile( )
	{
		WebElement buttonContainer = wait.waitForElementPresent(commandButtonsCont);
		WebElement button = null;

		List<WebElement> commandButtons = wait.waitForAllElementsEnabled(By.tagName("button"), buttonContainer);

		for ( WebElement thisButton : commandButtons )
		{
			String buttonText = thisButton.getAttribute("title");
			if ( buttonText.equals(openButtonTitle) || buttonText.equals(editButtonTitle) )
			{
				button = thisButton;
				break;
			}
		}

		click.clickElementCarefully(button);
		AribaPortalReconciliationPage reconciliationPage = initializePageObject(AribaPortalReconciliationPage.class);
		return reconciliationPage;
	}
}
