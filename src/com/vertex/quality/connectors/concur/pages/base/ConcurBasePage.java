package com.vertex.quality.connectors.concur.pages.base;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.concur.enums.ConcurHeaderTab;
import com.vertex.quality.connectors.concur.pages.misc.ConcurHomePage;
import com.vertex.quality.connectors.concur.pages.panelPages.ConcurAppCenterPage;
import com.vertex.quality.connectors.concur.pages.panelPages.ConcurApprovalPage;
import com.vertex.quality.connectors.concur.pages.panelPages.ConcurExpensePage;
import com.vertex.quality.connectors.concur.pages.panelPages.ConcurInvoicePage;
import com.vertex.quality.connectors.concur.pages.settings.ConcurInvoiceSettingsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import static com.vertex.quality.common.enums.VertexLogLevel.ERROR;

/**
 * Generic representation of a web page on the connector's configuration site.
 *
 * @author alewis
 */
public abstract class ConcurBasePage extends VertexPage
{
	protected String title;
	protected final By sapConcurButton = By.className("cnqr-nav-dashboard");
	protected final By expenseButton = By.linkText("Expense");
	protected final By invoiceButton = By.linkText("Invoice");
	protected final By approvalsButton = By.linkText("Approvals");
	protected final By appCenterButton = By.linkText("App Center");

	protected final By administrationTab = By.xpath("//*/nav/ul/li/span[contains(text(),'Administration')]");
	//protected final By invoice = By.className("submenuitem");
	protected final By invoice = By.xpath("(//a[contains(.,'Invoice')])[2]");
	protected final By loadingScreen = By.className("ext-el-mask-msg");
	protected final By loadingScreen2 = By.className("cnqr-spinner-shape");

	final protected By myTasksID = By.id("cnqr-mytasks-tourtip");
	final protected String expectedHomePageTitle = "MY TASKS";

	public ConcurBasePage( final WebDriver driver )
	{
		super(driver);
	}

	/**
	 * checks if login is successful by checking if page is on homepage
	 *
	 * @return true if page loaded is homepage, false otherwise
	 */
	public boolean checkIfLoggedIn( )
	{
		waitForPageLoad();
		WebElement myTasks = wait.waitForElementDisplayed(myTasksID);
		String login = text.getElementText(myTasks);
		boolean ret = expectedHomePageTitle.equals(login);
		return ret;
	}

	/**
	 * clicks one of panels at top of homepage depending on the string
	 *
	 * @param button possible panels include {SAP Concur,Expense,Invoice,App Center}
	 *
	 * @return page loaded after panel is clicked, all pages extend the base page
	 */
	public <T extends ConcurBasePage> T navigateToHeaderPage( final ConcurHeaderTab button )
	{
		Class<? extends ConcurBasePage> returnPage = null;
		switch ( button )
		{
			case SAP_CONCUR:
				click.clickElement(sapConcurButton);
				returnPage = ConcurHomePage.class;
				break;

			case EXPENSE:
				click.clickElement(expenseButton);
				returnPage = ConcurExpensePage.class;
				break;

			case INVOICE:
				click.clickElement(invoiceButton);
				returnPage = ConcurInvoicePage.class;
				break;

			case APPROVALS:
				click.clickElement(approvalsButton);
				returnPage = ConcurApprovalPage.class;
				break;

			case APP_CENTER:
				click.clickElement(appCenterButton);
				returnPage = ConcurAppCenterPage.class;
				break;
		}
		T initializedReturnPage = initializePageObject(returnPage);
		return initializedReturnPage;
	}

	/**
	 * click administration tab then click "Invoice", "Company", or "Expense" to go to the settings page for each
	 *
	 * @param nextTab - tab to click after clicking admin tab either "Invoice", "Company", or "Expense"
	 *
	 * @return initialized settings page
	 */
	public ConcurInvoiceSettingsPage clickAdmin( String nextTab )
	{
		ConcurInvoiceSettingsPage returnPage = null;

		//click administration tab
		WebElement clickElement = element.selectElementByText(administrationTab, "Administration");
		if ( clickElement != null )
		{
			click.clickElement(clickElement);
		}
		else
		{
			VertexLogger.log("Cant find administration tab", ERROR);
		}
		switch ( nextTab )
		{
			case "Invoice":
				wait.waitForElementPresent(invoice);
				//WebElement popUp = element.selectElementByText(invoice, "Invoice");
				click.clickElement(invoice);
				returnPage = initializePageObject(ConcurInvoiceSettingsPage.class);
				break;

			case "Company":
				//TODO
				break;

			case "Expense":
				//TODO
				break;
		}
		return returnPage;
	}


	/**
	 * Concur has a number of reused elements that can indicate if the page is loading and not ready to be interacted
	 * with.
	 * This test makes sure none of those elements are present and then runs a waitForPageLoad
	 * Note: These webelements must be disappearing from the html once the page is loaded
	 */
	@Override
	public void waitForPageLoad( )
	{
		super.waitForPageLoad();

		wait.waitForElementNotPresent(loadingScreen);

		wait.waitForElementNotPresent(loadingScreen2);

		super.waitForPageLoad();
	}

	/**
	 * tries to clear the text field and enter the given string into it
	 *
	 * @param loc           the text element's locator
	 * @param input         the string to input into the text element
	 * @param maxAttemptNum number of attempts to try and get text before stopping
	 *
	 * @author alewis
	 */
	public void enterTextSeveralTimes( final By loc, final CharSequence input, final int maxAttemptNum )
	{
		WebElement textElement = wait.waitForElementEnabled(loc);
		enterTextSeveralTimes(textElement, input, maxAttemptNum);
	}

	/**
	 * tries to clear the text field and enter the given string into it
	 *
	 * @param textElement the text element's locator
	 * @param input       the string to input into the text element
	 *
	 * @author alewis
	 */
	public void enterTextSeveralTimes( final WebElement textElement, final CharSequence input, final int maxAttemptNum )
	{
		int attemptNum = 0;
		String expectedText = input.toString();
		String elementText = text.retrieveTextFieldContents(textElement);
		while ( !expectedText.equalsIgnoreCase(elementText) && attemptNum < maxAttemptNum )
		{
			text.enterText(textElement, Keys.SPACE);
			text.enterText(textElement, input);
			text.enterText(textElement, Keys.ENTER, false);
			attemptNum++;
			elementText = text.retrieveTextFieldContents(textElement);
		}
	}

	/**
	 * uses Action class to send keys to locator
	 *
	 * @param locator element locator
	 * @param input   input to enter into locator
	 */
	public void enterTextAsAction( final WebElement locator, final CharSequence input )
	{
		Actions performAct = new Actions(driver);
		performAct
			.sendKeys(locator, input)
			.build()
			.perform();
	}
}
