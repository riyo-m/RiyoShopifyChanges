package com.vertex.quality.connectors.dynamics365.finance.pages;

import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinanceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * All purchase orders page common methods and object declaration page
 *
 * @author Shiva Mothkula
 */
public class DFinanceApplyPrepaymentsPage extends DFinanceBasePage
{
	protected By INVOICE_NUMBER = By.name("InvoiceNumber");
	protected By CLOSE_BUTTON = By.cssSelector("[name='SystemDefinedCloseButton'][type='button'][id*='VendAdvance']");

	public DFinanceApplyPrepaymentsPage( WebDriver driver )
	{
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public String getInvoiceNumber( )
	{
		wait.waitForElementDisplayed(INVOICE_NUMBER);
		String invoiceNumber = attribute.getElementAttribute(INVOICE_NUMBER, "title");

		if ( invoiceNumber != null )
		{
			invoiceNumber = invoiceNumber.trim();
		}

		return invoiceNumber;
	}

	public DFinanceCreatePurchaseOrderPage closeApplyPrepayment( )
	{
		wait.waitForElementDisplayed(CLOSE_BUTTON);
		click.clickElement(CLOSE_BUTTON);
		waitForPageLoad();

		DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = initializePageObject(
			DFinanceCreatePurchaseOrderPage.class);

		return createPurchaseOrderPage;
	}
}
