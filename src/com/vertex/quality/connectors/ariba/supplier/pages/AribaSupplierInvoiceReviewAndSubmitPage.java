package com.vertex.quality.connectors.ariba.supplier.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * this class represents the supplier page that has the review and submit information for the invoiced PO
 * it contains all the methods necessary to interact with the page to create test cases
 *
 * @author osabha
 */
public class AribaSupplierInvoiceReviewAndSubmitPage extends AribaSupplierBasePage
{
	protected final By submitButtonLoc = By.cssSelector("button[title='Submit']");

	public AribaSupplierInvoiceReviewAndSubmitPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * locates and clicks the submit button
	 *
	 * @return final review page of submitted invoice
	 */
	public AribaSupplierInvoicingConfirmationPage clickSubmitButton( )
	{
		WebElement submitButton = wait.waitForElementPresent(submitButtonLoc);

		click.clickElementCarefully(submitButton);

		return new AribaSupplierInvoicingConfirmationPage(driver);
	}
}
