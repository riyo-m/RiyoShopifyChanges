package com.vertex.quality.connectors.ariba.supplier.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * this class represents the supplier page that shows the inbox with all the purchase requests
 * it contains all the methods necessary to interact with the page to create test cases
 *
 * @author osabha
 */
public class AribaSupplierInboxPage extends AribaSupplierBasePage
{
	public AribaSupplierInboxPage( final WebDriver driver )
	{
		super(driver);
	}

	protected final By createInvoiceButtonLoc = By.cssSelector(
		"button[title='Create an invoice against the purchase order']");
	protected final By invoiceListContLoc = By.id("poinvoiceMenu");
	protected final By poLoc = By.cssSelector("a[documentid]");
	protected final By poListContLoc = By.linkText("Orders and Releases");
	protected final By invoiceOptionLoc = By.className("w-pmi-item");

	/**
	 * locates the target purchase order element
	 *
	 * @param poNumber purchase order number
	 *
	 * @return webElement of the target purchase order
	 */
	protected WebElement getPurchaseOrder( String poNumber )
	{
		WebElement thisOrder = null;
		WebDriverWait fastWait = new WebDriverWait(driver, FIVE_SECOND_TIMEOUT);
		fastWait.until(ExpectedConditions.presenceOfElementLocated(poListContLoc));
		List<WebElement> purchaseOrders = wait.waitForAllElementsDisplayed(poLoc);
		thisOrder = element.selectElementByAttribute(purchaseOrders, poNumber, "documentid");

		return thisOrder;
	}

	/**
	 * locates the target pO and clicks on it
	 *
	 * @param poNumber the purchase order to select to issue invoice for
	 */
	public void selectTargetPo( final String poNumber )
	{
		WebElement targetPo = getPurchaseOrder(poNumber);
		click.clickElementCarefully(targetPo);
	}

	/**
	 * locates the create invoice button
	 *
	 * @return create invoice button WebElement
	 */
	protected WebElement getCreateInvoiceButton( )
	{
		WebElement createInvoiceButton = wait.waitForElementPresent(createInvoiceButtonLoc);

		return createInvoiceButton;
	}

	/**
	 * uses the get create invoice button to locate the element and then clicks on it
	 */
	public void clickCreateInvoiceButton( )
	{
		WebElement createInvoiceButton = getCreateInvoiceButton();
		createInvoiceButton.click();
	}

	/**
	 * selects standard invoice from the create invoice list
	 *
	 * @return new instance of the Ariba Supplier Create invoice page
	 */
	public AribaSupplierCreateInvoicePage selectStandardInvoice( )
	{
		String expectedText = "Standard Invoice";
		WebElement standardInvoiceOptionButton = null;
		WebElement invoiceMenuContainer = wait.waitForElementPresent(invoiceListContLoc);
		List<WebElement> invoiceMenuOptions = wait.waitForAllElementsPresent(invoiceOptionLoc, invoiceMenuContainer);

		for ( int i = 0 ; i < invoiceMenuOptions.size() && standardInvoiceOptionButton == null ; i++ )
		{
			WebElement thisInvoiceMenuOption = invoiceMenuOptions.get(i);
			String invoiceOptionText = thisInvoiceMenuOption.getText();
			if ( invoiceOptionText != null )
			{
				invoiceOptionText = invoiceOptionText.trim();
				if ( expectedText.equals(invoiceOptionText) )
				{
					standardInvoiceOptionButton = thisInvoiceMenuOption;

					standardInvoiceOptionButton.click();
				}
			}
		}

		return new AribaSupplierCreateInvoicePage(driver);
	}
}
