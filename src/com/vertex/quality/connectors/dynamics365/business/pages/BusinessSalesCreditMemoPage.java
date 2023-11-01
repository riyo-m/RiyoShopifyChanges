package com.vertex.quality.connectors.dynamics365.business.pages;

import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessSalesBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * represents the sales credit memo page
 *
 * @author cgajes
 */
public class BusinessSalesCreditMemoPage extends BusinessSalesBasePage
{
	protected By dialogBoxLoc = By.className("ms-nav-content-box");
	protected By orderDateFieldLoc = By.cssSelector("input[aria-label*='Order Date,']");
	protected By postingDateFieldLoc = By.xpath("//div[@controlname='Posting Date']//input");
	By salesInvoiceNoInput = By.xpath("//div[@controlname='VER_Original Sales Inv. No.']//input");

	Date date = new Date(System.currentTimeMillis());
	SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
	String transactionDate = sdf.format(date);

	public BusinessSalesCreditMemoPage( WebDriver driver ) { super(driver); }

	/**
	 * locates the order posting date field and enters today's date in it.
	 */
	public void setPostingDate( )
	{
		WebElement postDateField = wait.waitForElementPresent(postingDateFieldLoc);
		text.enterText(postDateField, transactionDate);
		text.enterText(postDateField, transactionDate);
	}

	/**
	 * Enter the Original Sales Invoice No. under Original Address section
	 * @param invoiceNo
	 */
	public void enterSalesInvoiceNo(String invoiceNo) {
		WebElement salesInvoiceNoField = wait.waitForElementDisplayed(salesInvoiceNoInput);
		scroll.scrollElementIntoView(salesInvoiceNoField);
		click.clickElementCarefully(salesInvoiceNoField);
		text.selectAllAndInputText(salesInvoiceNoField, invoiceNo);
		text.pressTab(salesInvoiceNoField);
	}
}
