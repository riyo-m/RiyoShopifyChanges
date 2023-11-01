package com.vertex.quality.connectors.ariba.portal.pages.procurement;

import com.vertex.quality.connectors.ariba.portal.components.procurment.AribaReconciliationExceptionsTab;
import com.vertex.quality.connectors.ariba.portal.components.procurment.AribaReconciliationLineViewTab;
import com.vertex.quality.connectors.ariba.portal.pages.common.AribaPortalPostLoginBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * this class represents the page on which the reconciliation process takes place
 * contains all the methods necessary to interact with the page
 *
 * @author osabha
 */
public class AribaPortalReconciliationPage extends AribaPortalPostLoginBasePage
{
	public AribaReconciliationExceptionsTab exceptionsTab;
	public AribaReconciliationLineViewTab lineViewTab;

	public AribaPortalReconciliationPage( WebDriver driver )
	{
		super(driver);
		this.exceptionsTab = new AribaReconciliationExceptionsTab(driver, this);
		this.lineViewTab = new AribaReconciliationLineViewTab(driver, this);
	}

	protected final By submitButtonLoc = By.cssSelector(
		"button[title='Submit your changes to this invoice reconciliation']");

	/**
	 * locates and clicks on the submit button, to send the reconciled invoice with the changes to posting, if needed.
	 */
	public void submitReconciledInvoice( )
	{
		WebElement submitButton = wait.waitForElementEnabled(submitButtonLoc);
		click.clickElementCarefully(submitButton);
	}

	/**
	 * locates and clicks on the line view tab button
	 */
	public void clickOnLineViewTab( )
	{
		List<WebElement> aTags = wait.waitForAnyElementsDisplayed(By.tagName("a"));
		WebElement lineViewTabButton = element.selectElementByText(aTags, "Line View");
		click.clickElementCarefully(lineViewTabButton);
	}
}
