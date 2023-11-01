package com.vertex.quality.connectors.dynamics365.business.pages;

import com.vertex.quality.connectors.dynamics365.business.components.BusinessPagesNavMenu;
import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessBasePage;
import org.openqa.selenium.*;

import java.util.List;

/**
 * represents the sales quote list page
 *
 * @author osabha, cgajes
 */
public class BusinessSalesQuotesListPage extends BusinessBasePage
{
	public BusinessPagesNavMenu pageNavMenu;

	protected By quoteGridLoc = By.cssSelector("table[id*='BusinessGrid']");
	protected By quoteRowsLoc = By.cssSelector("table tbody tr");
	protected By quoteRowSegmentLoc = By.cssSelector("tr td");
	protected By openQuoteLink = By.cssSelector("a['title*='Open record '][aria-label*='No,.']");
	protected By documentNumber = By.xpath("//th[@abbr='No.'][@aria-haspopup='menu']");
	protected By filter = By.xpath("//span[text()='Filter...'][@class='ms-nav-ctxmenu-title']");
	protected By inputNo =  By.xpath("//div[@controlname='No.']//input");
	protected By totalTax = By.xpath("//a[text()='Total Tax (USD)']");

	public BusinessSalesQuotesListPage( WebDriver driver )
	{
		super(driver);

		this.pageNavMenu = new BusinessPagesNavMenu(driver, this);
	}

	/**
	 * Gets a quote from the list based on row
	 *
	 * @param rowNum
	 *
	 * @return element of the row that contains clickable link and number
	 */
	public WebElement getQuoteFromList( int rowNum )
	{
		rowNum--;
		List<WebElement> gridList = wait.waitForAllElementsPresent(quoteGridLoc);
		WebElement grid = gridList.get(gridList.size() - 1);

		List<WebElement> gridRows = wait.waitForAllElementsPresent(quoteRowsLoc, grid);
		WebElement itemRow = gridRows.get(rowNum);

		WebElement selectedQuote = null;
		List<WebElement> segments = wait.waitForAllElementsPresent(quoteRowSegmentLoc, itemRow);
		for ( WebElement segment : segments )
		{
			try
			{
				WebElement ele = wait.waitForElementDisplayed(By.tagName("a"), segment, 5);
				if ( ele
					.getAttribute("title")
					.contains("Open record") )
				{
					selectedQuote = ele;
					break;
				}
			}
			catch ( TimeoutException e )
			{

			}
		}

		return selectedQuote;
	}

	/**
	 * Gets the number of the quote in the specified row
	 *
	 * @param rowIndex
	 *
	 * @return the quote's document number
	 */
	public String getQuoteNumberByRowIndex( int rowIndex )
	{
		WebElement quote = getQuoteFromList(rowIndex);

		String number = quote.getText();

		return number;
	}

	/**
	 * It filter document after Creating it
	 * @param documentNo
	 */
	public void filterDocument(String documentNo)
	{
		WebElement filterEle = wait.waitForElementDisplayed(documentNumber);
		click.clickElementCarefully(filterEle);
		click.clickElementCarefully(filter);
		WebElement noEle = wait.waitForElementDisplayed(inputNo);
		text.enterText(noEle, documentNo+Keys.ENTER);
		String documentXpath = String.format("//a[text()='%s']",documentNo);
		WebElement documentEle = wait.waitForElementDisplayed(By.xpath(documentXpath));
		click.clickElementCarefully(documentEle);
		wait.waitForElementDisplayed(totalTax);
	}
}
