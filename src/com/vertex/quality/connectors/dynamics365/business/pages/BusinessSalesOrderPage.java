package com.vertex.quality.connectors.dynamics365.business.pages;

import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessSalesBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * represents the sales order page (dialog looking page)
 *
 * @author osabha, cgajes, bhikshapathi
 */
public class BusinessSalesOrderPage extends BusinessSalesBasePage
{
	protected By orderDateFieldLoc =By.xpath("//div[@controlname='Order Date']//input");
	protected By postingDateFieldLoc = By.xpath("//div[@controlname='Posting Date']//input");
	protected By salesOrderNumberLoc1 = By.xpath("//div[contains(@class, 'ms-nav-layout-head')]//div[@role='heading' and @aria-level='2' and contains(@class, 'title') and not(@tabindex)]");
	protected By okayButton = By.xpath("//button/span[contains(.,'OK')]");

	protected By messageConLoc = By.cssSelector(".ms-nav-content-box.message-dialog");
	protected By dialogButtonCon = By.className("ms-nav-actionbar-container");
	protected By radioButtonContainer = By.className("radio-button-edit-container");

	protected By buttonLoc = By.tagName("button");
	protected By radioLoc = By.tagName("li");
	protected By inputLoc = By.tagName("input");

	Date date = new Date(System.currentTimeMillis());
	SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
	String transactionDate = sdf.format(date);

	public BusinessSalesOrderPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * locates the order date field and enters today's date in it.
	 */
	public void setOrderDate( )
	{
		WebElement orderDateField = wait.waitForElementPresent(orderDateFieldLoc);
		text.enterText(orderDateField, transactionDate);
		text.enterText(orderDateField, transactionDate);
	}

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
	 * gets the order number from the top of the page
	 * will only be present after at least clicking on the items table
	 *
	 * @return the order number
	 */
	public String getOrderNumber( )
	{   waitForPageLoad();
		WebElement title = wait.waitForElementEnabled(salesOrderNumberLoc1);
		String fullText = text.getElementText(title);
		System.out.println(fullText);
		String orderNum = fullText.substring(0, fullText.indexOf(" "));
		return orderNum;
	}

	/**
	 * When posting an order, select the Ship and Invoice option
	 */
	public void selectShipAndInvoicePosting( )
	{
		WebElement radioButton = null;

		WebElement dialogBox = wait.waitForElementDisplayed(messageConLoc);
		WebElement radioButtonCon = wait.waitForElementDisplayed(radioButtonContainer, dialogBox);

		List<WebElement> radioButtonList = wait.waitForAllElementsPresent(radioLoc, radioButtonCon);

		for ( WebElement radio : radioButtonList )
		{
			String label = text.getElementText(radio);
			if ( "Ship and Invoice".equals(label) )
			{
				radioButton = radio.findElement(inputLoc);
				break;
			}
		}

		click.clickElement(radioButton);

		WebElement buttonCon = wait.waitForElementDisplayed(dialogButtonCon, dialogBox);
		List<WebElement> buttonList = wait.waitForAllElementsDisplayed(buttonLoc, buttonCon);
		WebElement button = element.selectElementByText(buttonList, "OK");
		click.clickElementCarefully(button);

		wait.waitForElementNotDisplayedOrStale(radioButton, 30);
	}

	/**
	 * checks if recalculate popup is displayed
	 * @return false if pop is not displayed
	 */
	public boolean isPopupDisplayed(){
		boolean flag=element.isElementDisplayed(taxGroupAlert);
		return flag;
	}

}
