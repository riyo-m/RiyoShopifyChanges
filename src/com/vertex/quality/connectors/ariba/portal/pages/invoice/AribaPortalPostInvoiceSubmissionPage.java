package com.vertex.quality.connectors.ariba.portal.pages.invoice;

import com.vertex.quality.connectors.ariba.portal.pages.common.AribaPortalPostLoginBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * represents the invoice summary page, after invoice creation page.
 * contains all the methods necessary to interact with it.
 *
 * @author osabha
 */
public class AribaPortalPostInvoiceSubmissionPage extends AribaPortalPostLoginBasePage
{
	public AribaPortalPostInvoiceSubmissionPage( WebDriver driver )
	{
		super(driver);
	}

	protected final By invoiceNumberContLoc = By.xpath("//*/div[@class='a-arc-inv-submit-msg']/font/b");
	protected final By invoiceNumberLoc = By.className("sectionHead");
	protected final By VIEW_INVOICE_LINK = By.xpath("//*/td/li/a[text()='View']");
	protected final By INVOICE_SUMMARY_TAB = By.xpath("//*/li/span/a[text()='Summary']");
	protected final By INVOICE_SUMMARY_DETAILS_TAX = By.xpath("//*/tr/td[contains(text(),'Tax:')]//following-sibling::td");
	protected final By INVOICE_SUMMARY_DETAILS_LINK = By.id("invsumrylink");
	protected final By INVOICE_SUMMARY_TOTAL = By.xpath("//*/span[contains(text(),'Invoice Summary')]//following-sibling::span");


	/**
	 * locates the assigned invoice number for the invoice,
	 * and retrieves it. Then adds IR (stands for invoice reconciliation) in front of it,
	 * because that's how the system names new invoice reconciliation requests.
	 *
	 * @return the formatted string of the invoice number with IR in front of it.
	 */
	public String getInvoiceNumber( )
	{
		String invoiceNumber;
		WebElement invoiceNumberElemCont = wait.waitForElementDisplayed(invoiceNumberContLoc);
		WebElement invoiceNumberElem = wait.waitForElementDisplayed(invoiceNumberLoc, invoiceNumberElemCont);
		String invoiceText = text.getElementText(invoiceNumberElem);
		invoiceNumber = String.format("IR%s", invoiceText);
		return invoiceNumber;
	}

	/**
	 * Selects the View invoice link after submitting an invoice
	 * */
	public void clickViewInvoiceStatus(){
		WebElement view = wait.waitForElementDisplayed(VIEW_INVOICE_LINK);
		click.clickElementCarefully(view);
	}

	/**
	 * Navigates to the summary tab for an invoice after submission
	 * */
	public void clickInvoiceSummaryTab(){
		WebElement summary = wait.waitForElementDisplayed(INVOICE_SUMMARY_TAB);
		click.clickElementCarefully(summary);
	}

	/**
	 * Clicks and gets the value for the details dropdown on the invoice summary tab
	 * */
	public String getInvoiceSummaryDetailsTax(){
		WebElement detailsLink = wait.waitForElementDisplayed(INVOICE_SUMMARY_DETAILS_LINK);
		click.clickElementCarefully(detailsLink);
		WebElement detailsTax = wait.waitForElementDisplayed(INVOICE_SUMMARY_DETAILS_TAX);
		String tax = detailsTax.getText();
		return tax.trim();
	}

	/**
	 * Gets the invoice total for a submitted invoice on the summary tab
	 * */
	public String getInvoiceSummaryDetailsTotal(){
		WebElement detailsTotal = wait.waitForElementDisplayed(INVOICE_SUMMARY_TOTAL);
		String total = detailsTotal.getText();
		return total.trim();
	}
}
