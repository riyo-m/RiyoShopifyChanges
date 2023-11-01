package com.vertex.quality.connectors.ariba.portal.pages.procurement;

import com.vertex.quality.connectors.ariba.portal.pages.common.AribaPortalPostLoginBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * this class represents the My to do page, contains search box to look for PR's or invoices
 * contains all the methods necessary to interact with it
 *
 * @author osabha
 */
public class AribaPortalToDoPage extends AribaPortalPostLoginBasePage
{
	public AribaPortalToDoPage( WebDriver driver ) {super(driver);}

	protected final By searchResultRowLoc = By.className("firstRow");
	protected final By pageHeadLoc = By.className("pageHead");
	protected final By searchBoxContLoc = By.className("searchBoxBody");
	protected final By idFieldContLoc = By.className("w-form-row");
	protected final By searchButtonLoc = By.cssSelector("button[title='Run this search']");

	/**
	 * locates the id field in the search box and enters the invoice id into it
	 * then it locates the search button and clicks on it
	 *
	 * @param invoiceId invoice id we are looking for
	 */
	public void searchForInvoice( String invoiceId )
	{
		wait.waitForElementDisplayed(pageHeadLoc);// this is just to make sure that the page loaded fine.
		String idFieldName = "ID:";
		WebElement fieldCont = null;
		WebElement idFieldParentCont = wait.waitForElementPresent(searchBoxContLoc);
		List<WebElement> idFieldConts = wait.waitForAllElementsPresent(idFieldContLoc, idFieldParentCont);

		fieldCont = element.selectElementByNestedLabel(idFieldConts, By.tagName("label"), idFieldName);

		WebElement idField = wait.waitForElementEnabled(By.tagName("input"), fieldCont);
		text.enterText(idField, invoiceId);
		WebElement searchButton = wait.waitForElementEnabled(searchButtonLoc);
		click.clickElementCarefully(searchButton);
	}

	/**
	 * clicks on the found invoice to see it's details
	 *
	 * @param invoiceId id of the invoice we searched for
	 *
	 * @return new instance of the before reconciliation page class
	 */
	public AribaPortalBeforeReconciliationPage openInvoice( String invoiceId )
	{
		waitForUpdate();
		WebElement foundInvoice = null;
		WebElement invoiceLine = wait.waitForElementDisplayed(searchResultRowLoc);
		List<WebElement> invoiceLineButtons = wait.waitForAllElementsPresent(By.tagName("a"), invoiceLine);
		for ( WebElement button : invoiceLineButtons )
		{
			String buttonText = button.getText();
			if ( buttonText.contains(invoiceId) )
			{
				foundInvoice = button;
			}
		}

		click.clickElementCarefully(foundInvoice);
		return initializePageObject(AribaPortalBeforeReconciliationPage.class);
	}
}
