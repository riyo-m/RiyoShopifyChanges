package com.vertex.quality.connectors.dynamics365.business.pages;

import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessSalesBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * represents the sales invoice page
 *
 * @author cgajes
 */
public class BusinessSalesInvoicePage extends BusinessSalesBasePage
{
	protected By postingDateFieldLoc = By.xpath("//div[@controlname='Posting Date']//input");
	protected By popupYes = By.xpath("//form[@controlname='Dialog']//span[contains(.,'Yes')]");

	Date date = new Date(System.currentTimeMillis());
	SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
	String transactionDate = sdf.format(date);

	public BusinessSalesInvoicePage( WebDriver driver ) { super(driver); }

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
	 * locates the order posting date field and enters given date in it.
	 * @param date - String in format M/d/yyyy
	 */
	public void setPostingDate(String date)
	{
		WebElement postDateField = wait.waitForElementPresent(postingDateFieldLoc);
		text.enterText(postDateField, date);
		text.enterText(postDateField, date);
	}

	/**
	 * Click on Yes when popup "Saved but not posted" present
	 */
	public void acceptSavedButNotPostedPopup()
	{
		WebElement yes = wait.waitForElementDisplayed(popupYes);
		click.clickElementCarefully(yes);
		wait.waitForElementNotDisplayedOrStale(yes,shortTimeout);
	}
}
