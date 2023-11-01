package com.vertex.quality.connectors.ariba.supplier.pages;

import com.vertex.quality.connectors.ariba.supplier.components.AribaSupplierCreateInvoiceLineItemsDetails;
import com.vertex.quality.connectors.ariba.supplier.components.AribaSupplierCreateInvoicePageTaxDetails;
import com.vertex.quality.connectors.ariba.supplier.components.AribaSupplierCreateInvoiceShippingDetails;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * this class represents the supplier page from which we create an invoice for PR's
 * it contains all the methods necessary to interact with the page to create test cases
 *
 * @author OmarS
 */
public class AribaSupplierCreateInvoicePage extends AribaSupplierBasePage
{
	public AribaSupplierCreateInvoicePageTaxDetails taxDetails;
	public AribaSupplierCreateInvoiceLineItemsDetails lineItemsDetails;
	public AribaSupplierCreateInvoiceShippingDetails shippingDetails;

	public AribaSupplierCreateInvoicePage( final WebDriver driver )
	{
		super(driver);
		this.taxDetails = initializePageObject(AribaSupplierCreateInvoicePageTaxDetails.class, this);
		this.lineItemsDetails = initializePageObject(AribaSupplierCreateInvoiceLineItemsDetails.class, this);
		this.shippingDetails = initializePageObject(AribaSupplierCreateInvoiceShippingDetails.class, this);
	}

	protected final By updatePopup = By.className("awwaitAlertDiv");
	protected final By updateButtonLoc = By.cssSelector("button[title='Update']");
	protected final By nextButtonLoc = By.cssSelector("button[title='Go to next step']");
	protected final By saveButtonLoc = By.cssSelector("button[title='Save']");
	protected final By parentContLoc = By.className("w-sec-box");

	/**
	 * locates the invoice number field and enters the current time stamp as invoice id
	 *
	 * @return the invoice number as a string
	 */
	public String enterInvoiceNumber( )
	{
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
		String invoiceNumber = String.format("IR%S", timeStamp);
		WebElement parentCont = wait.waitForElementPresent(parentContLoc);
		List<WebElement> allTablesRows = wait.waitForAllElementsPresent(By.tagName("tr"), parentCont);
		WebElement targetRow = element.selectElementByText(allTablesRows, "Invoice #:");
		WebElement invoiceNumberField = wait.waitForElementPresent(By.tagName("input"), targetRow);
		invoiceNumberField.clear();
		invoiceNumberField.sendKeys(timeStamp);
		return invoiceNumber;
	}

	/**
	 * locates and clicks on the update button
	 */
	public void clickUpdateButton( )
	{
		WebElement updateButton = wait.waitForElementPresent(updateButtonLoc);
		click.clickElementCarefully(updateButton);
	}

	/**
	 * locates and clicks on the next button on the page
	 *
	 * @return new instance of the invoice reviewing page
	 */
	public AribaSupplierInvoiceReviewAndSubmitPage clickNext( )
	{
		WebElement nextButton = wait.waitForElementPresent(nextButtonLoc);
		click.clickElementCarefully(nextButton);
		waitForUpdate();
		return new AribaSupplierInvoiceReviewAndSubmitPage(driver);
	}

	/**
	 * waits for a short time for an 'update' popup to be displayed, then waits for it to disappear
	 *
	 * @author ssalisbury
	 */
	public void waitForUpdate( )
	{
		try
		{
			waitForPageLoad();
			wait.waitForElementDisplayed(updatePopup, THREE_SECOND_TIMEOUT);
		}
		catch ( Exception e )
		{
		}

		wait.waitForElementNotDisplayed(updatePopup);
	}

	/**
	 * locates and clicks on the save Button
	 */
	public void clickSaveButton( )
	{
		WebElement saveButton = wait.waitForElementEnabled(saveButtonLoc);
		click.clickElementCarefully(saveButton);
	}

	/**
	 * located the add to header button, clicks on it and selects the charge type from the dropdown
	 *
	 * @param charge the charge type to add to the invoice header level
	 */
	public void clickAddToHeaderButton( String charge )
	{
		WebElement addToHeaderButton = null;
		List<WebElement> buttons = wait.waitForAnyElementsDisplayed(By.tagName("button"));
		addToHeaderButton = element.selectElementByText(buttons, "Add to Header");
		click.clickElement(addToHeaderButton);
		WebElement dropDownCont = wait.waitForElementEnabled(By.id(".groupxxxInvoiceHeaderTableActions.alt.null"));
		List<WebElement> dropDownOptions = wait.waitForAllElementsDisplayed(By.tagName("a"), dropDownCont);
		WebElement targetCharge = element.selectElementByText(dropDownOptions, charge);
	}
}
