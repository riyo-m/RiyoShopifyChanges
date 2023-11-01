package com.vertex.quality.connectors.oraclecloud.pages;

import com.vertex.quality.common.pages.VertexAutomationObject;
import com.vertex.quality.connectors.oraclecloud.enums.OracleCloudCreateTransactionPageFieldData;
import com.vertex.quality.connectors.oraclecloud.pages.base.OracleCloudBasePage;
import com.vertex.quality.common.utils.VertexLogger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Create Transaction page for application
 * Can create or edit AR invoices here
 *
 * @author cgajes
 */
public class OracleCloudCreateTransactionPage extends OracleCloudBasePage
{
	protected By blockingPlane = By.className("AFBlockingGlassPane");
	protected By modalPlane = By.className("AFModalGlassPane");

	protected By autoSuggestionLoc = By.xpath(
		"//div[contains(@id, 'suggestions-popup') or contains(@id, 'popup-container')]");
	protected By autoSuggestionFailWarningLoc = By.className("AFNoteWindow");
	protected By searchButtonLoc = By.cssSelector("a[id*='lovIconId'][title*='Search:']");
	protected By searchPopupLoc = By.cssSelector("div[id*='popup-container'] div table[id*='billToNameIdlovPopupId']");
	protected By searchFieldsLoc = By.cssSelector("input[name*='value']");
	protected By searchLabelLoc = By.cssSelector("input[name*='value']+label");
	protected By searchMenuResultsLoc = By.xpath("//div[contains(@id, 'afrLovInternalTableId::db')]");
	protected By mccAllStatesSearchFieldLoc = By.xpath(
			"//div[contains(@id, 'afrLovInternalTableId::db')]/table/tbody/tr[1]/td[2]/div/table/tbody/tr/td[1]/span");
	protected By saveButtonLoc = By.cssSelector("div[role='presentation'][id*='saveMenu']");
	protected By completeAndCreateButtonLoc = By.cssSelector("div[role='presentation'][id*='newTrx']");
	protected By completeButtonArrowLoc = By.cssSelector("a[title='Complete and Create Another']");
	protected By completeButtonArrowOptionsMenuLoc = By.cssSelector(
		"div[id*='ManagerLayerContainer'] div div[id*='opup-container'][data-afr-popupid*='m2']");
	protected By completeButtonArrowOptionsLoc = By.cssSelector("div[id*='ScrollBox'] table tbody tr[role='menuitem']");
	protected By deleteButtonLoc = By.cssSelector("div[id*='commandToolbarButton1'] a");
	protected By deletePopupLoc = By.cssSelector("div[id*='deletePopup'][data-afr-popupid*='deletePopup']");
	protected By invoiceOrMemoLoc = By.xpath(
		"//table[@summary='Invoice Lines' or @summary='Credit Memo Lines' or @summary='Debit Memo Lines']");
	protected By invoiceMemoLinesLoc = By.xpath(
		"//table[@summary='Invoice Lines' or @summary='Credit Memo Lines' or @summary='Debit Memo Lines']/tbody/tr");
	protected By saveAndCloseDetailEditButtonLoc = By.xpath(
		"//a[@role='button']//descendant::span[contains(text(), 'ave and Close')]");
	protected By errorPopupLoc = By.cssSelector(
		"div[id*='popup-container'] div table tr td div[id*='msgDlg'][class*='Error']");
	protected By errorMessageTextLoc = By.cssSelector("div[id*='msgDlg::_cnt']");
	protected By editTaxDetailLoc = By.cssSelector("a[id*='commandImageLink1111']");
	protected By detailTaxLinesLoc = By.cssSelector("div[id*='taxpop::popup-container']");
	protected By taxDetailMoreOptionsLoc = By.cssSelector(
		"div[id*='ATtb1'] div[role='presentation'] div[role='button']");
	protected By detailTaxEditRateNameLoc = By.cssSelector("input[id*='taxRateNameCrId']");
	protected By detailTaxEditAmountLoc = By.cssSelector("input[id*='taxAmtOldId']");
	protected By detailTaxNameLoc = By.cssSelector("span[id*='taxId']");
	protected By editLinePrevLoc = By.cssSelector("a[id*='prev']");

	// Unit (UOM) Related locs
	protected By uomPopup = By.cssSelector("div[id$='uOMId::lovDialogId']");
	protected By uomOkButtonLoc = By.xpath("//button[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:pt1:TCF:0:ap1:AT1:_ATp:" +
			"table1:0:uOMId::lovDialogId::ok']");

	protected By buttonTag = By.tagName("BUTTON");

	protected String reviewHeader = "Review Transaction";
	protected String searchHeader = "Manage Transactions";
	protected String uomResultPathString = "//tr[1]/td[2]/div/table/tbody/tr/td[1]/span[text()='%s']";

	Actions action = new Actions(driver);

	public OracleCloudCreateTransactionPage( WebDriver driver ) { super(driver); }

	/**
	 * When entering the business unit, check if a warning regarding the
	 * autosuggest has appeared
	 *
	 * @return true if warning appeared, false if it did not
	 */
	public boolean autoSuggestFailure( )
	{
		boolean warningAppeared = false;
		try
		{
			wait.waitForElementDisplayed(autoSuggestionFailWarningLoc, 10);
			warningAppeared = true;
		}
		catch ( TimeoutException e )
		{

		}

		return warningAppeared;
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

		scroll.scrollElementIntoView(completeAndCreateButtonLoc);

		wait.waitForElementNotEnabled(blockingPlane);

		WebElement button = wait.waitForElementEnabled(completeAndCreateButtonLoc);
		WebElement buttonArrow = wait.waitForElementEnabled(completeButtonArrowLoc, button);

		click.clickElementCarefully(buttonArrow);

		if ( !checkOpen(completeButtonArrowOptionsMenuLoc) )
		{
			wait.waitForElementNotEnabled(blockingPlane);
			buttonArrow.click();
		}

		List<WebElement> optionsList = wait.waitForAllElementsPresent(completeButtonArrowOptionsLoc);
		WebElement select = element.selectElementByAttribute(optionsList, clickOn, attribute);

		if ( !checkOpen(completeButtonArrowOptionsMenuLoc) )
		{
			click.clickElement(buttonArrow);
			optionsList = wait.waitForAllElementsPresent(completeButtonArrowOptionsLoc);
			select = element.selectElementByAttribute(optionsList, clickOn, attribute);
		}
		jsWaiter.sleep(5000);
		click.clickElementCarefully(select);

		try{
			Thread.sleep(15000);
		}
		catch ( InterruptedException e)
		{
			e.printStackTrace();
		}

		jsWaiter.sleep(10000);
		waitForLoadedPage(reviewHeader);


		OracleCloudReviewTransactionPage reviewTransactionPage = initializePageObject(
			OracleCloudReviewTransactionPage.class);
		return reviewTransactionPage;
	}

	/**
	 * On an incomplete invoice, click the delete button on the upper right,
	 * which will open a popup prompting confirmation
	 *
	 * @return the popup asking for confirmation or cancellation of the deletion
	 */
	public WebElement clickDeleteButton( )
	{
		WebElement deleteButton = wait.waitForElementEnabled(deleteButtonLoc);
		click.clickElement(deleteButton);

		WebElement deletePopup = wait.waitForElementDisplayed(deletePopupLoc);
		return deletePopup;
	}

	/**
	 * After the delete button is clicked, click the Yes button to confirm,
	 * then return to previous page
	 * (Prev page currently hardcoded to manage page)
	 *
	 * @param deletePopup the popup with the Yes/No buttons
	 *
	 * @return previous page
	 */
	public OracleCloudManageTransactionsPage clickDeletePopupYesButton( WebElement deletePopup )
	{
		String optionSelect = "Yes";

		List<WebElement> buttonsList = wait.waitForAllElementsDisplayed(buttonTag, deletePopup);
		WebElement button = element.selectElementByText(buttonsList, optionSelect);
		click.clickElementCarefully(button);

		waitForLoadedPage(searchHeader);

		OracleCloudManageTransactionsPage page = initializePageObject(OracleCloudManageTransactionsPage.class);
		return page;
	}

	/**
	 * Clicks the details icon to open the line editing page
	 *
	 * @param line invoice or memo line
	 */
	public void clickDetailsButton( WebElement line )
	{
		String titleAttr = "title";
		String detailsTitle = "Details";

		List<WebElement> buttonsList = element.getWebElements(By.tagName("A"), line);
		WebElement detailsIcon = element.selectElementByAttribute(buttonsList, detailsTitle, titleAttr);
		wait.waitForElementEnabled(detailsIcon);
		detailsIcon.click();

		wait.waitForElementNotDisplayedOrStale(detailsIcon, 20);
	}

	/**
	 * After clicking on the details button on an invoice/memo line to edit the line,
	 * click on the "Save and Close" button to exit editing
	 */
	public void clickDetailsSaveAndCloseButton( )
	{
		WebElement saveAndClose = wait.waitForElementEnabled(saveAndCloseDetailEditButtonLoc);
		click.clickElementCarefully(saveAndClose);
	}

	/**
	 * When editing an invoice line after clicking on the Details button for that line,
	 * click the Previous Arrow button to move to the preceding line
	 */
	public void clickPrevButton( )
	{
		wait.waitForElementNotEnabled(blockingPlane);
		WebElement prevButton = wait.waitForElementEnabled(editLinePrevLoc);

		click.clickElementCarefully(prevButton);
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
		waitForPageLoad();
	}

	/**
	 * Once the invoice has been completed, click the edit icon next to
	 * the tax to open a popup
	 *
	 * @return editable detail tax popup
	 */
	public WebElement clickTaxEditButton( )
	{
		scroll.scrollElementIntoView(editTaxDetailLoc);
		WebElement editIcon = wait.waitForElementEnabled(editTaxDetailLoc);
		click.clickElementCarefully(editIcon);

		WebElement detailTax = wait.waitForElementDisplayed(detailTaxLinesLoc);

		return detailTax;
	}

	/**
	 * Clicks on the More Options button on the top of the popup,
	 * and on the subsequent dropdown select Add Row
	 * (cannot actually click on Add Row)
	 *
	 * @param detailPopup detail tax popup
	 */
	public void clickTaxEditAddRow( WebElement detailPopup )
	{
		wait.waitForElementEnabled(detailPopup);

		WebElement moreOptionsButton = wait.waitForElementDisplayed(taxDetailMoreOptionsLoc, detailPopup);

		wait.waitForElementEnabled(moreOptionsButton);
		click.clickElementCarefully(moreOptionsButton);
		WebElement addRow = findActiveElement();
		addRow.sendKeys(Keys.ENTER);
	}

	/**
	 * Clicks one of the buttons on the tax detail popup
	 *
	 * @param detailPopup detail tax popup
	 * @param buttonText  the text displayed on the button to click
	 */
	public void clickTaxEditPopupButton( WebElement detailPopup, String buttonText )
	{
		wait.waitForElementEnabled(detailPopup);
		List<WebElement> buttonsList = wait.waitForAllElementsDisplayed(buttonTag, detailPopup);

		WebElement button = null;
		for ( WebElement ele : buttonsList )
		{
			String textFound = ele.getText();
			if ( textFound.equals(buttonText) )
			{
				button = ele;
				break;
			}
		}
		click.clickElementCarefully(button);

		wait.waitForElementNotDisplayedOrStale(detailPopup, 20);
	}

	/**
	 * On the detail tax popup, input the rate name into the required field
	 * Press enter to complete input
	 *
	 * @param detailPopup detail tax popup
	 * @param input name to input into the field
	 */
	public void detailTaxInputRateName( WebElement detailPopup, String input )
	{
		WebElement rateNameField = wait.waitForElementDisplayed(detailTaxEditRateNameLoc, detailPopup);
		wait.waitForElementEnabled(rateNameField);

		text.enterText(rateNameField, input);
		text.pressEnter(rateNameField);
	}

	/**
	 * On the detail tax popup, input the custom tax amount
	 *
	 * @param detailPopup detail tax popup
	 * @param input amount to input into the field
	 */
	public WebElement detailTaxInputTaxAmount( WebElement detailPopup, String input )
	{
		WebElement taxAmountField = wait.waitForElementEnabled(detailTaxEditAmountLoc, detailPopup);

		text.enterText(taxAmountField, input);

		return taxAmountField;
	}

	/**
	 * After inputting the tax rate name, wait for the tax name to autofill
	 * Necessary before attempting to further interact with the row
	 *
	 * @param detailPopup  detail tax popup
	 * @param expectedText the expected tax name
	 */
	public void detailTaxWaitForName( WebElement detailPopup, String expectedText )
	{
		WebElement taxNameField = wait.waitForElementEnabled(detailTaxNameLoc, detailPopup);
		wait.waitForTextInElement(taxNameField, expectedText);
	}

	/**
	 * Waits for an error popup to appear, and returns the popup
	 *
	 * @return the error popup
	 */
	public WebElement errorPopupWaitFor( )
	{
		WebElement errorPopup = wait.waitForElementDisplayed(errorPopupLoc);

		return errorPopup;
	}

	/**
	 * Get the error message displayed on the error popup
	 *
	 * @return the error message
	 */
	public String errorPopupGetMessage(WebElement errorPopup )
	{
		WebElement errorDialogue = wait.waitForElementDisplayed(errorMessageTextLoc, errorPopup);

		String message = errorDialogue.getText();
		return message;
	}

	/**
	 * Cancels a search on a search menu popup
	 * by clicking on the cancel button, closing the menu
	 * without selecting any search result
	 *
	 * @param searchMenu the popup search menu that has been opened
	 */
	public void searchOnPopupClickCancel( WebElement searchMenu )
	{
		String buttonText = "Cancel";

		wait.waitForElementDisplayed(searchMenu);

		List<WebElement> buttonsList = wait.waitForAllElementsDisplayed(buttonTag, searchMenu);
		WebElement cancelButton = element.selectElementByText(buttonsList, buttonText);

		wait.waitForElementEnabled(cancelButton);
		click.clickElement(cancelButton);

		wait.waitForElementNotDisplayedOrStale(searchMenu, 3);
	}

	/**
	 * Confirms the search by clicking on the OK button,
	 * closing the menu and selecting whatever result was previously
	 * chosen
	 *
	 * @param searchMenu the popup search menu that has been opened
	 */
	public void searchOnPopupClickOk( WebElement searchMenu )
	{
		String buttonText = "OK";

		wait.waitForElementDisplayed(searchMenu);

		List<WebElement> buttonsList = wait.waitForAllElementsEnabled(buttonTag, searchMenu);
		WebElement okButton = element.selectElementByText(buttonsList, buttonText);

		wait.waitForElementEnabled(okButton);
		click.clickElementCarefully(okButton);

		wait.waitForElementNotDisplayedOrStale(searchMenu, 10);
	}

	/**
	 * Clicks on the search button on the search popup
	 * after a search query has been entered onto desired field(s)
	 *
	 * @param searchMenu the popup search menu that has been opened
	 */
	public void searchOnPopupClickSearch( WebElement searchMenu )
	{
		String buttonText = "Search";

		wait.waitForElementDisplayed(searchMenu);

		List<WebElement> buttonsList = wait.waitForAllElementsEnabled(buttonTag, searchMenu);
		WebElement searchButton = element.selectElementByText(buttonsList, buttonText);

		wait.waitForElementEnabled(searchButton);
		click.clickElement(searchButton);
	}

	/**
	 * Opens a search menu from one of the input fields
	 * by clicking on the search button on the field
	 *
	 * @param searchTitle title of the search button
	 *
	 * @return the search menu popup that appears
	 */
	public WebElement searchOnPopupOpenMenu( String searchTitle )
	{
		String attribute = "title";
		String searchButtonTitle = searchTitle;

		List<WebElement> searchList = wait.waitForAllElementsDisplayed(searchButtonLoc);
		WebElement searchButtonDesired = element.selectElementByAttribute(searchList, searchButtonTitle, attribute,
			false);

		scroll.scrollElementIntoView(searchButtonDesired);

		wait.waitForElementNotEnabled(blockingPlane);
		wait.waitForElementEnabled(searchButtonDesired);
		searchButtonDesired.click();

		WebElement popup = wait.waitForElementDisplayed(searchPopupLoc);

		return popup;
	}

	/**
	 * On a search popup menu, select one of the search fields
	 * and write a search query to it
	 *
	 * @param searchMenu     the popup search menu that has been opened
	 * @param searchField    the name of the search field on the menu to write to
	 * @param input          the search query to write
	 */
	public WebElement searchOnPopupWriteToField( WebElement searchMenu, String searchField, String input )
	{
		String textAttribute = "innerText";

		wait.waitForElementEnabled(searchMenu);

		List<WebElement> inputFieldsList = wait.waitForAllElementsPresent(searchFieldsLoc, searchMenu);

		List<WebElement> labelFieldsList = wait.waitForAllElementsPresent(searchLabelLoc, searchMenu);
		WebElement label = element.selectElementByAttribute(labelFieldsList, searchField, textAttribute);
		int index = labelFieldsList.indexOf(label);
		WebElement inputField = inputFieldsList.get(index);

		wait.waitForElementEnabled(inputField);
		text.clearText(inputField);
		text.enterText(inputField, input);

		return inputField;
	}

	/**
	 * Selects a result from a search on a popup search menu
	 *
	 * @param searchMenu    the popup search menu that has been opened
	 * @param desiredResult the result intended from the search query, to select
	 */
	public void searchOnPopupSelectResult( WebElement searchMenu, String desiredResult )
	{
		wait.waitForElementDisplayed(searchMenu);

		List<WebElement> resultsList = wait.waitForAllElementsDisplayed(searchMenuResultsLoc, searchMenu);
		By mccAllStates = By.xpath("//div[contains(@id, 'afrLovInternalTableId::db')]/table/tbody/tr[1]/td[2]/div/table/tbody/tr/td[1]/span");

		wait.waitForElementEnabled(mccAllStates);
		click.clickElement(mccAllStates);
	}

	/**
	 * Clicks an option from the auto-suggestion list that
	 * appears on some inputs
	 *
	 * @param expectedText the text of the option to click on
	 */
	public void selectAutoSuggest( String expectedText )
	{
		try {
			wait.waitForElementDisplayed(autoSuggestionLoc);
			WebElement clickOn = element.selectElementByText(autoSuggestionLoc, expectedText);

			// List may disappear before being clicked on; in this case usually fine to move on
			if (element.isElementPresent(autoSuggestionLoc)) {
				wait.waitForElementEnabled(clickOn, 3);
				click.clickElementCarefully(clickOn);
			}
		} catch (TimeoutException te) {
			VertexLogger.log("Auto suggestion dropdown not found. Proceeding.");
		}
	}

	/**
	 * Select an option from a dropdown menu
	 * Object on page must be tagged as "SELECT"
	 *
	 * @param data     			enum containing information for dropdown to select
	 * @param selectOption      text value of the option to be selected
	 */
	public void selectFromDropdown( OracleCloudCreateTransactionPageFieldData data, String selectOption )
	{
		By dropdownLoc = data.getLocator();
		WebElement dropdownWebEle = wait.waitForElementDisplayed(dropdownLoc);
		dropdown.selectDropdownByDisplayName(dropdownWebEle, selectOption);
	}

	/**
	 * Selects a line from the invoice/memo
	 *
	 * @param lineNum the line number displayed on the page
	 *
	 * @return WebElement for the line
	 */
	public WebElement selectInvoiceOrMemoLine( int lineNum )
	{
		lineNum--;
		String lineNumStr = Integer.toString(lineNum);
		String attribute = "_afrrk";

		WebElement lines = wait.waitForElementDisplayed(invoiceOrMemoLoc);
		List<WebElement> linesList = wait.waitForAllElementsDisplayed(invoiceMemoLinesLoc, lines);

		WebElement line = element.selectElementByAttribute(linesList, lineNumStr, attribute);

		return line;
	}

	/**
	 * Select unit of measure from line column popup.
	 * @param unit Unit name as displayed in UI (i.e. "Ea" for Each)
	 */
	public void selectUomFromPopup(String unit) {
		wait.waitForElementDisplayed(uomPopup);

		String uomResultString = String.format(uomResultPathString, unit);
		By uomResultLoc = By.xpath(uomResultString); // By locator initialized here due to varying xpath String.

		wait.waitForElementDisplayed(uomResultLoc);
		click.javascriptClick(uomResultLoc);
		jsWaiter.sleep(2000);
		click.javascriptClick(uomOkButtonLoc);

		wait.waitForElementNotDisplayed(uomPopup);
	}

	/**
	 * Selects a result from a search on a popup search menu
	 *
	 * @param searchMenu    the popup search menu that has been opened
	 */
	public void searchMccAllStates( WebElement searchMenu)
	{
		wait.waitForElementDisplayed(searchMenu);

		wait.waitForElementDisplayed(searchMenuResultsLoc);

		wait.waitForElementEnabled(mccAllStatesSearchFieldLoc);
		click.clickElement(mccAllStatesSearchFieldLoc);
	}
	/**
	 * Waits for an automatic input on a general field
	 *
	 * @param data  enum containing field information
	 * @param input whether it is an input or read only field
	 */
	public void waitForAutomaticInput( OracleCloudCreateTransactionPageFieldData data, boolean input )
	{
		String attribute = "value";
		By loc = data.getLocator();
		WebElement ele = findActiveElement();
		text.pressTab(ele);

		final ExpectedCondition<Boolean> condition = driver ->
		{
			boolean autoInputComplete;
			String currentInput;
			WebElement field;

			if ( input )
			{
				field = wait.waitForElementDisplayed(loc);
				currentInput = field.getAttribute(attribute);
			}
			else
			{
				field = wait.waitForElementDisplayed(loc);
				currentInput = field.getText();
			}

			autoInputComplete = !currentInput.isEmpty();
			return autoInputComplete;
		};

		WebDriverWait wait = new WebDriverWait(driver, VertexAutomationObject.DEFAULT_TIMEOUT);

		wait.until(condition);
	}

	/**
	 * Waits for an automatic input on an invoice/memo line field
	 *
	 * @param data enum containing field information
	 * @param line invoice/memo line the field is on
	 * @param input true if input field, false if read only field
	 */
	public void waitForAutomaticInput( OracleCloudCreateTransactionPageFieldData data, WebElement line, boolean input )
	{
		String attribute = "value";
		By loc = data.getLocator();

		final ExpectedCondition<Boolean> condition = driver ->
		{
			boolean autoInputComplete;
			String currentInput;
			WebElement field = wait.waitForElementDisplayed(loc, line);

			if ( input )
			{
				currentInput = field.getAttribute(attribute);
			}
			else
			{
				currentInput = field.getText();
			}

			autoInputComplete = !currentInput.isEmpty();
			return autoInputComplete;
		};

		WebDriverWait wait = new WebDriverWait(driver, VertexAutomationObject.QUARTER_MINUTE_TIMEOUT);

		wait.until(condition);
	}

	/**
	 *	Writes to the general information fields in the top half
	 *	of the create transaction page
	 *
	 * @param data enum containing field information
	 * @param inputToField input to write
	 * @return WebElement of field interacted with
	 */
	public WebElement writeToGeneralField( OracleCloudCreateTransactionPageFieldData data, String inputToField )
	{
		scroll.scrollElementIntoView(data.getLocator());
		By loc = data.getLocator();
		wait.waitForElementNotEnabled(blockingPlane);
		WebElement inputField = wait.waitForElementEnabled(loc);
		text.enterText(inputField, inputToField);

		return inputField;
	}

	/**
	 * Writes to a general information field and checks to make sure the input was correctly written
	 * Due to Oracle occasionally deselecting input fields
	 *
	 * @param data  enum containing field information
	 * @param input input to write
	 *
	 * @return WebElement of field interacted with
	 */
	public WebElement inputAndCheck( OracleCloudCreateTransactionPageFieldData data, String input )
	{
		WebElement field = writeToGeneralField(data, input);
		text.pressEnter(field);

		while ( !checkInput(data, input) )
		{
			field = writeToGeneralField(data, input);
			text.pressTab(field);
		}

		return field;
	}

	/**
	 * Writes to a general information field and tabs to the next field
	 *
	 * @param data  enum containing field information
	 * @param input input to write
	 *
	 * @return WebElement of field interacted with
	 */
	public WebElement inputAndEnter( OracleCloudCreateTransactionPageFieldData data, String input )
	{
		WebElement field = writeToGeneralField(data, input);
		text.pressEnter(field);

		return field;
	}

	/**
	 * Writes to a general information field and tabs to the next field
	 *
	 * @param data  enum containing field information
	 * @param input input to write
	 *
	 * @return WebElement of field interacted with
	 */
	public WebElement inputAndTab( OracleCloudCreateTransactionPageFieldData data, String input )
	{
		WebElement field = writeToGeneralField(data, input);
		text.pressTab(field);

		return field;
	}

	/**
	 * Writes to one of the fields on the invoice/memo
	 * on the bottom half of the create transaction page
	 *
	 * @param data         enum containing field information
	 * @param inputToField input to write
	 * @param line         invoice/memo line the field is on
	 *
	 * @return WebElement of field interacted with
	 */
	public WebElement writeToInvoiceOrMemo( OracleCloudCreateTransactionPageFieldData data, String inputToField,
		WebElement line )
	{
		By loc = data.getLocator();
		wait.waitForElementNotEnabled(blockingPlane);
		WebElement inputField = wait.waitForElementPresent(loc, line);
		action
			.moveToElement(inputField)
			.perform();

		wait.waitForElementEnabled(inputField);
		text.enterText(inputField, inputToField);

		return inputField;
	}

	/**
	 * Writes to an invoice/memo field and checks to make sure the input was correctly written
	 * Due to Oracle occasionally deselecting input fields
	 *
	 * @param data  enum containing field information
	 * @param input input to write
	 * @param line  invoice/memo line the field is on
	 *
	 * @return WebElement of field interacted with
	 */
	public WebElement inputAndCheck( OracleCloudCreateTransactionPageFieldData data, String input, WebElement line )
	{
		By loc = data.getLocator();
		writeToInvoiceOrMemo(data, input, line);

		wait.waitForElementNotEnabled(blockingPlane);
		WebElement field = wait.waitForElementDisplayed(loc);
		field.sendKeys(Keys.TAB);

		while ( !checkInput(data, input, line) )
		{
			writeToInvoiceOrMemo(data, input, line);
			field = wait.waitForElementDisplayed(loc);
			field.sendKeys(Keys.TAB);
		}

		jsWaiter.sleep(2000);
		return field;
	}

	/**
	 * Helper method
	 * Finds an input field on a specific line of the invoice/memo
	 * !! Issue making this necessary has been fixed, but leaving for posterity !!
	 *
	 * OLD:
	 * If the field is searched for in other ways, such as waitForElement...(loc, container),
	 * it will always locate the field in line 1 no matter what line is specified as the container
	 * This allows for locating the correct field on the correct line
	 *
	 * @param fieldId id of field
	 * @param line line field is present on
	 *
	 * @return
	 */
	public WebElement findInvoiceField( String fieldId, WebElement line )
	{
		WebElement field = null;
		List<WebElement> list = wait.waitForAllElementsPresent(By.tagName("input"), line);

		for ( WebElement ele : list )
		{
			action
				.moveToElement(ele)
				.perform();
			String str = ele.getAttribute("id");

			if ( str.contains(fieldId) )
			{
				field = ele;
				break;
			}
		}

		return field;

	}

	/**
	 * Helper method
	 * Checks the written input on a general field
	 *
	 * @param data enum containing field information
	 * @param expectedInput the text expected to be in the field
	 * @return whether the input found matches the input expected
	 */
	public boolean checkInput( OracleCloudCreateTransactionPageFieldData data, String expectedInput )
	{
		boolean correct = false;
		wait.waitForElementNotEnabled(blockingPlane);

		By loc = data.getLocator();

		WebElement field = wait.waitForElementDisplayed(loc);

		String actualInput = field.getAttribute("value");

		if ( expectedInput.equals(actualInput) )
		{
			correct = true;
		}

		return correct;
	}

	/**
	 * Helper method
	 * Checks the written input on an invoice/memo field
	 *
	 * @param data enum containing field information
	 * @param expectedInput the text expected to be in the field
	 * @param line line the field is present on
	 * @return whether the input found matches the input expected
	 */
	public boolean checkInput( OracleCloudCreateTransactionPageFieldData data, String expectedInput, WebElement line )
	{
		boolean correct = false;
		By loc = data.getLocator();

		WebElement field = wait.waitForElementDisplayed(loc, line);

		String actualInput = field.getAttribute("value");

		if ( expectedInput.equals(actualInput) )
		{
			correct = true;
		}

		return correct;
	}

	/**
	 * Helper method
	 * Checks whether some elements, such as menus or dropdowns, have opened as intended or
	 * whether they have not
	 *
	 * @param loc locator of element
	 *
	 * @return whether it is open/visible
	 */
	public boolean checkOpen( By loc )
	{
		boolean elementOpen = false;
		if ( element.isElementDisplayed(loc) )
		{
			elementOpen = true;
		}

		return elementOpen;
	}
}
