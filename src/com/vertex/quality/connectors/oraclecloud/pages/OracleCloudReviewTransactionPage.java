package com.vertex.quality.connectors.oraclecloud.pages;

import com.vertex.quality.connectors.oraclecloud.pages.base.OracleCloudBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Review Transaction page for application
 * Can manage or delete completed AR invoices from here
 *
 * @author cgajes
 */
public class OracleCloudReviewTransactionPage extends OracleCloudBasePage
{

	protected By cancelButtonLoc = By.cssSelector("div[role='presentation'][id*='commandToolbarButton2']");
	protected By transactionNumberLoc = By.cssSelector("tr[id*='plam202'] td:last-child span span");
	protected By incompleteButtonLoc = By.cssSelector("div[role='presentation'][id*='incompleteBt1']");
	protected By saveButtonLoc = By.cssSelector("div[role='presentation'][id*='saveMenu']");
	protected By taxDetailLinkLoc = By.cssSelector("a span[id*='inputText134']");
	protected By dialogWindowLoc = By.cssSelector("div[id$='msgDlg']");
	protected By dialogCancelButtonLoc = By.cssSelector("button[id$='msgDlg::cancel']");

	final String billingHeader = "Billing";
	final String editHeader = "Edit Transaction";

	public OracleCloudReviewTransactionPage( WebDriver driver ) { super(driver); }

	/**
	 * Clicks on the "Cancel" button to close the transaction and return to the billing page
	 * If the transaction has not been saved, a confirmation popup will appear - currently not handled
	 *
	 * @return the billing page
	 */
	public OracleCloudReceivablesBillingPage clickCancelButton( )
	{
		String text = "Cancel";

		scroll.scrollElementIntoView(cancelButtonLoc);
		WebElement select = element.selectElementByText(cancelButtonLoc, text);
		wait.waitForElementEnabled(select);
		click.clickElementCarefully(select);

		waitForLoadedPage(billingHeader);

		OracleCloudReceivablesBillingPage billingPage = initializePageObject(OracleCloudReceivablesBillingPage.class);
		return billingPage;
	}

	/**
	 * When reviewing a completed invoice/memo, clicks the "Incomplete" button to
	 * remove the completed status and allow editing again
	 */
	public OracleCloudCreateTransactionPage clickIncompleteButton( )
	{

		String text = "Incomplete";

		scroll.scrollElementIntoView(incompleteButtonLoc);
		WebElement select = element.selectElementByText(incompleteButtonLoc, text);
		wait.waitForElementEnabled(select);
		click.clickElementCarefully(select);

		waitForLoadedPage(editHeader);

		OracleCloudCreateTransactionPage createTransactionPage = initializePageObject(OracleCloudCreateTransactionPage.class);

		return createTransactionPage;
	}

	/**
	 * Clicks on the "Save" button to save an invoice
	 * ("Save and Close" option not currently implemented)
	 *
	 * The calculated tax will not change from default (0.00) until
	 * several seconds after search is clicked
	 */
	public void clickSaveButton( )
	{
		String text = "Save";

		scroll.scrollElementIntoView(saveButtonLoc);

		WebElement select = element.selectElementByText(saveButtonLoc, text);
		wait.waitForElementEnabled(select);
		click.clickElementCarefully(select);
	}

	/**
	 * Locate the Tax field and get the total tax amount displayed
	 *
	 * @return tax displayed
	 */
	public String getTotalTaxAmount( )
	{
		scroll.scrollElementIntoView(taxDetailLinkLoc);
		WebElement taxAmount = wait.waitForElementDisplayed(taxDetailLinkLoc);
		String totalTaxedAmount = taxAmount.getText();

		return totalTaxedAmount;
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
	 * Checks whether the currently displayed tax is "0.00" or not
	 *
	 * @return true if displayed tax is "0.00", false if displayed tax is any other value
	 */
	public boolean getDisplayedTax( )
	{
		boolean isDisplayedTaxZero = false;

		WebElement taxAmount = wait.waitForElementEnabled(taxDetailLinkLoc);
		String amt = taxAmount.getText();

		if ( amt.equals("0.00") )
		{
			isDisplayedTaxZero = true;
		}

		return isDisplayedTaxZero;
	}

	/**
	 * Closes dialog windows by hitting the "Cancel" button for that window
	 * (This button may not actually say "Cancel" but will have that effect)
	 */
	public void closeWindow( )
	{
		WebElement window = wait.waitForElementDisplayed(dialogWindowLoc);

		WebElement button = wait.waitForElementEnabled(dialogCancelButtonLoc, window);

		click.clickElementCarefully(button);
	}
}
