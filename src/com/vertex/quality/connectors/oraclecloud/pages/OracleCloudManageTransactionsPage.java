package com.vertex.quality.connectors.oraclecloud.pages;

import com.vertex.quality.connectors.oraclecloud.pages.base.OracleCloudBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Manage Transaction page for application
 * Search for or select searched for AR invoices here
 *
 * @author cgajes
 */
public class OracleCloudManageTransactionsPage extends OracleCloudBasePage
{
	protected By transactionNumOperatorFieldLoc = By.cssSelector("input[id*='value40']");
	protected By transactionNumberLoc = By.cssSelector("tr[id*='plam202'] td:last-child span span");
	protected By searchButtonLoc = By.cssSelector("button[id*='search']");
	protected By searchResultsTableLoc = By.cssSelector("table[summary='Search Results']");
	protected By searchResultsLinesLoc = By.cssSelector("table[summary='Search Results'] tbody tr td span a");
	protected By searchResultsTextLoc = By.xpath("//table[@summary='Search Results']//parent::div");
	protected By completeAndReviewButtonLoc = By.cssSelector("div[role='presentation'][id*='dupTrx1']");
	protected By completeButtonArrowLoc = By.cssSelector("a[title='Complete and Review']");
	protected By billToNameInputLoc = By.cssSelector("a[title='Search: Bill-to Name']");
	protected By okButtonLoc = By.cssSelector("button[id$='msgDlg::cancel']");

	protected By duplicate = By.cssSelector("img[id*='duplicate']");
	protected By blockingPlane = By.className("AFBlockingGlassPane");
	protected By saveButtonLoc = By.cssSelector("div[role='presentation'][id*='saveMenu']");
	protected String reviewHeader = "Review Transaction";
	protected String editHeader = "Edit Transaction";

	public OracleCloudManageTransactionsPage( WebDriver driver ) { super(driver); }

	/**
	 * Clicks on the duplicate icon on the manage transactions page.
	 */
	public OracleCloudCreateTransactionPage clickDuplicateIcon( )
	{
		WebElement duplicateButton = wait.waitForElementEnabled(duplicate);
		click.clickElementCarefully(duplicateButton);

		OracleCloudCreateTransactionPage transPage = initializePageObject(OracleCloudCreateTransactionPage.class);
		return transPage;
	}

	/**
	 * Wait for verification a page has loaded by checking the page's
	 * header text.
	 *
	 * @param headerTextExpected the page header expected to be shown on the new page
	 */
	public void waitForLoadedPage( String headerTextExpected )
	{
		final WebDriverWait wait = new WebDriverWait(driver, 125);
		wait.until(ExpectedConditions.textToBePresentInElementLocated(headerTagLoc, headerTextExpected));
	}

	/**
	 * Clicks on one of the options under the "Complete and Create Another" button,
	 * accessed by clicking on the arrow on the side of the button
	 *
	 * @param clickOn the text of the option to click on
	 */
	public OracleCloudReviewTransactionPage clickCompleteButtonOption( String clickOn )
	{
		String attribute = "textContent";
		jsWaiter.sleep(6000);

		scroll.scrollElementIntoView(completeAndReviewButtonLoc);

		wait.waitForElementNotEnabled(blockingPlane);

		WebElement button = wait.waitForElementEnabled(completeAndReviewButtonLoc);
		click.clickElementCarefully(button);

		jsWaiter.sleep(6000);
		if( element.isElementDisplayed(okButtonLoc) ){
			clickOk();
			clickBillToName();
			wait.waitForElementEnabled(completeAndReviewButtonLoc);
			click.clickElementCarefully(button);
		}

		jsWaiter.sleep(10000);
		waitForLoadedPage(reviewHeader);

		OracleCloudReviewTransactionPage reviewTransactionPage = initializePageObject(
			OracleCloudReviewTransactionPage.class);
		return reviewTransactionPage;
	}

	/**
	 * Clicks on the search button on the page
	 */
	public void clickSearchButton( )
	{
		WebElement searchButton = wait.waitForElementEnabled(searchButtonLoc);
		click.clickElementCarefully(searchButton);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	/**
	 * Locates the desired search result on the page
	 * from the list of results
	 *
	 * @param transNumber the transaction number for the desired result
	 * @return WebElement for the desired result
	 */
	public WebElement findSearchResult( String transNumber )
	{
		WebElement result = null;
		WebElement searchResultsTable = wait.waitForElementEnabled(searchResultsTableLoc);
		List<WebElement> list = wait.waitForAllElementsDisplayed(searchResultsLinesLoc, searchResultsTable);

		for ( WebElement ele : list )
		{
			String resultNum = ele.getText();
			if ( transNumber.equals(resultNum) )
			{
				result = ele;
				break;
			}
		}
		return result;
	}

	/**
	 * When no results are expected from a search, locate the
	 * resulting text that appears in place of invoices
	 *
	 * @return the text displayed
	 */
	public String getSearchResultText( )
	{
		WebElement txtEle = wait.waitForElementDisplayed(searchResultsTextLoc);
		String txtDisplayed = txtEle.getText();

		return txtDisplayed;
	}

	/**
	 * Locate the Transaction Number field that autofills upon invoice
	 * completion and get the number
	 *
	 * @return the transaction number for that invoice
	 */
	public String getTransactionNumber( )
	{
		scroll.scrollElementIntoView(transactionNumberLoc);
		WebElement transNum = wait.waitForElementDisplayed(transactionNumberLoc);
		String number = transNum.getText();

		return number;
	}

	/**
	 * After locating the desired search result, click on it
	 * to view the full invoice/memo
	 *
	 * @param result the desired result
	 * @return the review transaction page
	 */
	public OracleCloudReviewTransactionPage selectSearchResult( WebElement result )
	{
		wait.waitForElementEnabled(result);
		click.clickElementCarefully(result);

		waitForLoadedPage(reviewHeader);

		OracleCloudReviewTransactionPage page = new OracleCloudReviewTransactionPage(driver);
		return page;
	}

	/**
	 * Writes a transaction number into the Transaction Number Operator search field
	 *
	 * @param input transaction number to input
	 * @return the search field
	 */
	public WebElement writeTransNumOperator( String input )
	{
		WebElement searchField = wait.waitForElementEnabled(transactionNumOperatorFieldLoc);
		text.clearText(searchField);
		text.enterText(searchField, input);

		return searchField;
	}

	/**
	 * Clicks on the "Save" button to save an invoice
	 * ("Save and Close" option not currently implemented)
	 */
	public void clickSaveButton( )
	{
		String text = "Save";

		scroll.scrollElementIntoView(saveButtonLoc);

		WebElement select = element.selectElementByText(saveButtonLoc, text);
		wait.waitForElementEnabled(select);
		click.clickElementCarefully(select);

		waitForLoadedPage(editHeader);
	}

	/**
	 * Clicks on the search button of BillToName input on the page
	 */
	public void clickBillToName( )
	{
		WebElement billToNameInput = wait.waitForElementEnabled(billToNameInputLoc);
		click.clickElementCarefully(billToNameInput);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	/**
	 * Clicks OK in the confirmation dialog box
	 * when payment details are filled
	 */
	public void clickOk( )
	{
		WebElement okButton = wait.waitForElementEnabled(okButtonLoc);
		click.clickElementCarefully(okButton);
	}
}
