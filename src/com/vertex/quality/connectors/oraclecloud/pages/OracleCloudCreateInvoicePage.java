package com.vertex.quality.connectors.oraclecloud.pages;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pages.VertexAutomationObject;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.oraclecloud.enums.OracleCloudCreateInvoicePageFieldData;
import com.vertex.quality.connectors.oraclecloud.enums.OracleCloudCreateTransactionPageFieldData;
import com.vertex.quality.connectors.oraclecloud.pages.base.OracleCloudBasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Create Invoice page for application
 * Can create or edit AP invoices here
 *
 * @author cgajes
 */
public class OracleCloudCreateInvoicePage extends OracleCloudBasePage
{
	protected By blockingPlane = By.className("AFBlockingGlassPane");

	By validationLoc = By.cssSelector("a[id$='cl3']");
	By invoiceActionsButtonLoc = By.cssSelector("div[id$='mb1'][role='menubar']");
	By invoiceActionsMenuLoc = By.cssSelector("div[id$='popup-container'][data-afr-popupid$='me1']");
	By invoiceActionsOptionsLoc = By.cssSelector("table[id$='ScrollContent'] tbody tr[role='menuitem']");
	By saveAndCreateNextButtonLoc = By.xpath("//span[text()='Save and Create Next']");
	By saveButtonLoc = By.xpath("//span[text()='Save']");
	By saveAndClose = By.xpath("//span[text()='Save and Close']");
	By saveAndCloseMain = By.xpath("//span[text()= 'S']");
	By saveConfirmationLoc = By.xpath("//label[text()='Last Saved']");
	By needsRevalidationLoc = By.cssSelector("a[id*='ap1:cl3']");
	By headerLoc = By.cssSelector("td[id*='SPph::_afrTtxt']");

	By applyOrUnapplyPrepaymentPopupLoc = By.cssSelector("div[id$='popup-container'][data-afr-popupid$='po10']");
	By prepaymentsTableLoc = By.cssSelector("table[summary='Apply or Unapply Prepayments']");
	By prepaymentsTableFirstElementLoc = By.cssSelector("table[summary='Apply or Unapply Prepayments'] tbody tr");
	By applyButtonLoc = By.cssSelector("button[id$='_ATp:cb1']");
	By doneButtonLoc = By.cssSelector("button[id$='cb19']");
	By queryByExampleLoc = By.cssSelector("div[id*='at1:_ATp:_qbeTbr']");
	By qbeSearchNumberLoc = By.cssSelector("input[id*='afr_c4::content']");
	By okButtonLoc = By.cssSelector("button[id$='msgDlg::cancel']");
	By manageHolds = By.xpath("//div[contains(text(), 'Manage Holds')]");
	By nameOption = By.xpath("//select[contains(@id, 'content') and (@_afov = '0')][@title = '']");
	By reasonField = By.xpath("//input[contains(@value, 'variance')]");
	By saveAndCloseButton = By.xpath("//button[text()= 'Save and Close']");

	By expandLinesButtonLoc = By.cssSelector("a[aria-label='Expand Lines']");
	By invoiceLinesLoc = By.cssSelector("table[summary='Invoice Lines'] tbody tr");
	By invoiceLinesTableLoc = By.cssSelector("//table[@summary='Invoice Lines']");
	By activeLineLoc = By.cssSelector("//table[@summary='Invoice Lines']/tbody/tr[contains(@class, 'p_AFSelected')]");
	By invoiceLineExplicitLoc = By.xpath("//tr[@class='xem'][1]");
	By identifyingPOLoc = By.cssSelector("input[id*='ic1::content']");
	By amountLoc = By.cssSelector("input[id*='i3::content']");
	By matchInvoiceLinesArrowLoc = By.cssSelector("img[id*='ap1:cg1::icon']");
	By matchTextBoxLoc = By.cssSelector("div[id*='po7::content']");
	By checkAllMatchesLoc = By.cssSelector("input[id*='ta1:sb3']");
	By applyMatchesButtonLoc = By.cssSelector("button[id*='ap1:cb2']");
	By okMatchesButtonLoc = By.cssSelector("button[id*='ap1:cb17']");
	By shipToLocationFieldLoc = By.cssSelector("input[id*='ic28::content']");

	// Taxes -> Transaction Taxes related locs.
	By taxLinesLoc = By.cssSelector("table[summary='Transaction Tax'] tbody tr");
	By taxesTabsLoc = By.cssSelector("div[id$='tabh::cbc'] div");
	By taxesTableLoc = By.cssSelector("div[id$='tabbc']");
	By taxCancelledCheckLoc = By.cssSelector("span[id*='sbc2'] span span img");
	By transTaxTotalLoc = By.cssSelector("table[id$='TransTaxTotal']");
	By totalFoundLoc = By.cssSelector("table[id$='ap1:pgl11']");
	By totalDueLoc = By.cssSelector("div[id$='p103']");
	By taxesExpandLoc = By.cssSelector("a[title='Expand Taxes']");
	By transTaxesView = By.xpath("//a[text()='Transaction Taxes']");
	By editTaxesButtonLoc = By.xpath("//button[text()='Edit Taxes']");
	By vertTaxCalcSummaryTabLinkLoc = By.xpath("//div[contains(@id, 'oc_t_6872513248::ti')]");

	//Vertex Tax Calculation Summary
	By frameTaxCalcSummary = By.xpath("//iframe[contains(@id, 'FOpt')]");
	By headerTaxCalcSummary = By.xpath("//span[contains(text(), 'Vertex Tax Calculation Summary')]");

	// Edit Transaction Taxes Popup
	By taxPopupLoc = By.cssSelector("div[id*='popup-container'][data-afr-popupid*='TaxPopup']");
	By addTaxLineButtonLoc = By.cssSelector("div[title='Add Row']");
	By editTransTaxNameLoc = By.cssSelector("input[id$='taxRateNameCrId::content']");
	By editTransTaxRateId = By.cssSelector("input[name$=TaxRateId]");
	By editTransScrollbarLoc = By.cssSelector("div[id$='table1::scroller']");

	By editTransTaxTaxOnlyCheckLoc = By.cssSelector("span[id$='sbc4']");
	By editTransTaxNameSearchLoc = By.cssSelector("a[id*='taxRateNameCrId'][title='Search: Rate Name']");
	By dropdownPopupLoc = By.cssSelector("div[id*='popup-container'][data-afr-popupid*='dropdownPopup']");
	By dropdownPopupSearchLoc = By.cssSelector("a[id*='popupsearch']");
	By searchRateNamePopupLoc = By.cssSelector("div[id$='taxRateNameCrIdlovPopupId::popup-container']");
	By searchNameRateLoc = By.cssSelector("input[name$='value00']");
	By searchNameRegimeLoc = By.cssSelector("input[name$='value20']");
	By searchNameTaxNameLoc = By.cssSelector("input[name$='value30']");
	By searchNameJurisdictionLoc = By.cssSelector("input[name$='value50']");
	By searchNameTableResultsLoc = By.cssSelector("div[id$='taxRateNameCrId_afrLovInternalTableId::db'] table");

	By payInFullPopupLoc = By.cssSelector("div[id*='popup-container'][data-afr-popupid*='pif15']");
	By payInFullSubmitButtonLoc = By.xpath("//button[contains(@id, 'pifbtn1')]");
	By payInFullCancelButtonLoc = By.cssSelector("//button[contains(@id, 'pifbtn2]");
	By paymentNumberLoc = By.cssSelector("input[id*='it1']");

	By errorMsgPopupLoc = By.cssSelector(
		"div[id*='popup-container'] div table tbody tr td div[id*='msgDlg'][class*='Error']");
	By errorMsgContentLoc = By.cssSelector("td[id*='contentContainer']");
	By warningPopupLoc = By.cssSelector("div[id*='popup-container'][data-afr-popupid*='p40']");

	By buttonRole = By.cssSelector("div[role='presentation']");

	By buttonRoleOnPopUp = By.xpath("//div[@role='presentation']/a/span[contains(text(),'Save and Close')]");
	By buttonTag = By.tagName("button");

	By resultsTag = By.tagName("tr");

	Actions action = new Actions(driver);

	public OracleCloudCreateInvoicePage( WebDriver driver ) { super(driver); }

	/**
	 * Method for dynamic locator for columns of Vertex Tax Calculation Summary
	 */
	public WebElement taxCalcSummaryTable_Column( int index )
	{
		By colId = By.xpath(
			"//span[text() = 'Vertex Tax Calculation Summary']/../../table/tbody/tr[2]/td[" + index + "]/p/span");
		WebElement column = wait.waitForElementDisplayed(colId);

		return column;
	}

	/**
	 * Method to click on Manage Holds and resolve
	 */
	public void resolveHolds( )
	{
		wait.waitForElementDisplayed(manageHolds);
		WebElement nameDropdown = wait.waitForElementDisplayed(nameOption);

		dropdown.selectDropdownByDisplayName(nameDropdown, "Variance corrected");

		wait.waitForElementDisplayed(reasonField);
		click.clickElement(saveAndCloseButton);

		wait.waitForElementNotDisplayed(manageHolds);
	}

	/**
	 * On the transaction tax table, checks if the checkmark in the
	 * Canceled column is checked or not, signifying the status of the tax
	 * Checked is canceled, unchecked is active
	 *
	 * @param line line to look at
	 *
	 * @return whether the box is checked or not
	 */
	public boolean checkIfTaxCanceled( WebElement line )
	{
		boolean checked = false;

		WebElement canceledSection = wait.waitForElementDisplayed(taxCancelledCheckLoc, line);
		String status = canceledSection.getAttribute("title");

		if ( "checked".equals(status) )
		{
			checked = true;
		}

		return checked;
	}

	/**
	 * Double checks whether a menu, popup, or dropdown has opened and remained
	 * open before proceeding to interact with it
	 * Necessary because sometimes they close or do not open for unknown reasons
	 * !! need to wait for a moment before checking, since sometimes there is a delay when opening causing an incorrect
	 * false return
	 *
	 * @param locator the locator of the object being checked
	 *
	 * @return whether or not it is displayed and therefore open
	 */
	public boolean checkOpen( By locator )
	{
		boolean open = false;
		if ( element.isElementDisplayed(locator) )
		{
			open = true;
		}

		return open;
	}

	/**
	 * Checks the text at the top of the page which
	 * describes whether the invoice is validated
	 *
	 * @return validation status String
	 */
	public String checkValidationStatus( )
	{
		wait.waitForElementNotEnabled(blockingPlane);
		WebElement validationDisplay = wait.waitForElementDisplayed(validationLoc);
		String validationStatus = validationDisplay.getText();

		return validationStatus;
	}

	/**
	 * Clicks the button to show the invoice lines,
	 * allowing the input of items
	 * Can only be done when the required fields in the invoice header
	 * are filled out
	 */
	public void clickExpandLinesButton( )
	{
		wait.waitForElementDisplayed(expandLinesButtonLoc);

		wait.waitForElementNotEnabled(blockingPlane);

		WebElement expandLinesButton = wait.waitForElementEnabled(expandLinesButtonLoc);

		expandLinesButton.click();

		try
		{
			wait.waitForAnyElementsDisplayed(invoiceLinesLoc, 25);
		}
		catch ( TimeoutException e )
		{
			expandLinesButton = wait.waitForElementEnabled(expandLinesButtonLoc); // Salomone - ap prepayments
			expandLinesButton.click();
			wait.waitForAnyElementsDisplayed(invoiceLinesLoc);
		}
	}

	/**
	 * Checks if the taxes summary section is expanded then
	 * click on the Transaction Taxes tab.
	 *
	 * Once Transaction Taxes Tab is loaded the Edit Taxes
	 * button will be clicked.
	 */
	public void clickTransactionTaxesTabAndEditTaxes( )
	{
		wait.waitForElementNotEnabled(blockingPlane);

		try
		{
			click.javascriptClick(taxesExpandLoc);
		}
		catch ( NoSuchElementException | TimeoutException ex )
		{
			VertexLogger.log("Taxes summary section already expanded. Proceeding.", VertexLogLevel.INFO);
		}

		wait.waitForElementDisplayed(transTaxesView);
		click.javascriptClick(transTaxesView);

		jsWaiter.sleep(5000);
		WebElement editTaxesButton = wait.waitForElementDisplayed(editTaxesButtonLoc);
		click.javascriptClick(editTaxesButton);

		wait.waitForElementDisplayed(taxPopupLoc);
	}

	/**
	 * Clicks the button to show the invoice taxes details,
	 * allowing the input of items.
	 * Can only be done when the required fields in the invoice header
	 * are filled out
	 */
	public void clickExpandTaxesButton( )
	{
		wait.waitForElementDisplayed(taxesExpandLoc);

		wait.waitForElementNotEnabled(blockingPlane);

		WebElement expandTaxesButton = wait.waitForElementEnabled(taxesExpandLoc);

		click.javascriptClick(expandTaxesButton);

		try
		{
			wait.waitForElementDisplayed(vertTaxCalcSummaryTabLinkLoc);
		}
		catch ( TimeoutException e )
		{
			expandTaxesButton = wait.waitForElementEnabled(taxesExpandLoc);
			click.javascriptClick(expandTaxesButton);
			wait.waitForElementDisplayed(vertTaxCalcSummaryTabLinkLoc);
		}
	}

	/**
	 * Clicks the invoice actions button at the top of the page to
	 * show a dropdown menu of options
	 *
	 * @return the actions menu
	 */
	public WebElement clickInvoiceActionsButton( )
	{
		WebElement menu;

		wait.waitForElementNotEnabled(blockingPlane);
		wait.waitForElementPresent(invoiceActionsButtonLoc);
		scroll.scrollElementIntoView(invoiceActionsButtonLoc);
		WebElement button = wait.waitForElementEnabled(invoiceActionsButtonLoc);

		click.clickElementCarefully(button);

		try
		{
			menu = wait.waitForElementDisplayed(invoiceActionsMenuLoc, 15);
		}
		catch ( TimeoutException | NoSuchElementException ee )
		{
			button = wait.waitForElementEnabled(invoiceActionsButtonLoc);
			button.click();
			menu = wait.waitForElementDisplayed(invoiceActionsMenuLoc);
		}

		return menu;
	}

	/**
	 * After clicking Invoice Actions to open the dropdown,
	 * click one of the options displayed on the menu
	 *
	 * @param menu         the dropdown menu displayed
	 * @param expectedText the text of the option desired as it appears on the menu
	 */
	public void clickInvoiceActionFromMenu( WebElement menu, String expectedText )
	{
		List<WebElement> optionsList = wait.waitForAllElementsDisplayed(invoiceActionsOptionsLoc, menu, 80);

		WebElement action = element.selectElementByText(optionsList, expectedText);

		wait.waitForElementEnabled(action);

		click.clickElementCarefully(action);

		wait.waitForElementNotDisplayedOrStale(menu, 20);
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

	/**
	 * Clicks on the "Save" button to save an invoice
	 */
	public void clickSaveButton( )
	{
		String text = "Save";

		scroll.scrollElementIntoView(saveButtonLoc);

		WebElement select = element.selectElementByText(saveButtonLoc, text);
		wait.waitForElementEnabled(select);
		click.clickElementCarefully(select);
		wait.waitForElementDisplayed(saveConfirmationLoc);
	}

	/**
	 * Clicks on "Click and Save" button to save and close the Create Invoice page
	 *
	 * @return
	 */
	public OracleCloudCreateInvoicePage saveAndClose_Main( )
	{
		WebElement button = wait.waitForElementEnabled(saveAndCloseMain);
		click.clickElement(button);

		waitForLoadedPage("Invoices");

		OracleCloudCreateInvoicePage page = initializePageObject(OracleCloudCreateInvoicePage.class);

		return page;
	}

	public OracleCloudCreateInvoicePage clickSaveAndCreateNextButton( )
	{
		WebElement button = wait.waitForElementEnabled(saveAndCreateNextButtonLoc);

		click.clickElementCarefully(button);

		waitForLoadedPage("Create Invoice:");

		OracleCloudCreateInvoicePage page = initializePageObject(OracleCloudCreateInvoicePage.class);

		return page;
	}

	/**
	 * Clicks on the "Save and Close" button to save and close the
	 * edit taxes pop up
	 */
	public OracleCloudCreateInvoicePage clickSaveAndCloseButton( )
	{
		WebElement button = wait.waitForElementEnabled(saveAndClose);

		click.clickElementCarefully(button);

		OracleCloudCreateInvoicePage page = initializePageObject(OracleCloudCreateInvoicePage.class);
		wait.waitForElementNotEnabled(saveAndClose);

		return page;
	}

	/**
	 * Clicks one of the three tabs under the Taxes section;
	 * Transaction Taxes, Withholding Taxes, or Vertex Tax Calculation Summary
	 *
	 * @param tabText the text of the tab to open
	 */
	public void clickTaxTab( String tabText )
	{
		List<WebElement> tabList = wait.waitForAllElementsDisplayed(taxesTabsLoc);
		WebElement tabToSelect = element.selectElementByText(tabList, tabText);
		click.clickElement(tabToSelect);

		wait.waitForElementEnabled(taxesTableLoc);
	}

	/**
	 * Clicks one of the buttons on the tax table
	 * under the Transaction Taxes tab
	 *
	 * @param buttonText the text of the button to click
	 *
	 * @return resultant popup
	 */
	public WebElement clickTransactionTaxButton( String buttonText )
	{
		wait.waitForElementNotEnabled(blockingPlane);
		WebElement transTaxTable = wait.waitForElementDisplayed(taxesTableLoc);
		List<WebElement> buttonList = wait.waitForAllElementsDisplayed(buttonTag, transTaxTable);

		WebElement button = element.selectElementByText(buttonList, buttonText);
		wait.waitForElementEnabled(button);
		click.clickElementCarefully(button); // Salomone - ap tax only invoice (fixed, uses getTransactionTaxElement)

		WebElement taxPopup = wait.waitForElementDisplayed(taxPopupLoc);
		return taxPopup;
	}

	/**
	 * Verifies the transaction tax popup is visible on the page.
	 *
	 * Basically clickTransactionTaxButton, but doesn't click anything
	 * and assumes the Edit Taxes button was already clicked.
	 *
	 * @return resultant popup
	 */
	public WebElement getTransactionTaxElement( )
	{
		wait.waitForElementNotEnabled(blockingPlane);
		WebElement transTaxTable = wait.waitForElementDisplayed(taxesTableLoc);
		List<WebElement> buttonList = wait.waitForAllElementsDisplayed(buttonTag, transTaxTable);

		WebElement taxPopup = wait.waitForElementDisplayed(taxPopupLoc);
		return taxPopup;
	}

	/**
	 * When a popup appears to warn the user of something,
	 * click on one of the buttons
	 *
	 * @param popup      locator for the warning popup
	 * @param buttonText the text of the button to click
	 */
	public void clickWarningPopupButton( WebElement popup, String buttonText )
	{
		List<WebElement> buttonList = wait.waitForAllElementsDisplayed(buttonTag, popup);
		WebElement button = element.selectElementByText(buttonList, buttonText);
		wait.waitForElementEnabled(button);

		click.clickElement(button);
		try
		{
			wait.waitForElementNotDisplayedOrStale(popup, 20);
		}
		catch ( TimeoutException e )
		{
			click.clickElement(button);
			wait.waitForElementNotDisplayedOrStale(popup, 20);
		}
	}

	/**
	 * Edit the Amount field
	 *
	 * @param input amount
	 *
	 * @return the field
	 */
	public WebElement editAmount( String input )
	{
		WebElement field = wait.waitForElementEnabled(amountLoc);
		text.clearText(field);
		text.enterText(field, input);

		return field;
	}

	/**
	 * Edit the Identifying PO field
	 *
	 * @param input new Location
	 *
	 * @return the field
	 */
	public WebElement editIdentifyingPO( String input )
	{
		WebElement field = wait.waitForElementEnabled(identifyingPOLoc);
		text.clearText(field);
		text.enterText(field, input);
		text.pressTab(identifyingPOLoc);

		driver
			.manage()
			.timeouts()
			.implicitlyWait(10, TimeUnit.SECONDS);
		return field;
	}

	/**
	 * Edit the Ship-to Location field
	 *
	 * @param input new Location
	 *
	 * @return the field
	 */
	public WebElement editShipToLocation( String input )
	{
		WebElement field = wait.waitForElementEnabled(shipToLocationFieldLoc); // Salomone - recalc invoice
		text.clearText(field);
		text.enterText(field, input);

		return field;
	}

	/**
	 * Generates an invoice number using the test type String and
	 * a randomly generated set of numbers (invoice numbers must be unique)
	 * Concatenates the test type and random number into one String, starting with "AUTOTST" to
	 * signify it is an automated test
	 *
	 * @param testName short String describing what the test is doing (i.e. "RECALC" or "PREPAY")
	 *
	 * @return the invoice number
	 */
	public String generateInvoiceNumber( String testName )
	{
		Random rnd = new Random();
		int number = rnd.nextInt(100000);

		String invoiceNumber = String.format("AUTOTST_%1$s_%2$s", testName, number);

		return invoiceNumber;
	}

	/**
	 * Get the value currently in the "Date" input
	 * By default, current date auto fills
	 *
	 * @return String of current date in mm/dd/yy format
	 */
	public String getDate( )
	{
		By loc = OracleCloudCreateInvoicePageFieldData.DATE.getLocator();

		WebElement field = wait.waitForElementEnabled(loc);

		String date = field.getAttribute("value");

		return date;
	}

	/**
	 * Get the page header after saving the invoice
	 *
	 * @return page header
	 */
	public String getSavedHeader( )
	{
		scroll.scrollElementIntoView(headerLoc);
		WebElement transNum = wait.waitForElementDisplayed(headerLoc);
		String number = transNum.getText();

		return number;
	}

	/**
	 * Get the value currently in the "Terms Date" input
	 * By default, current date auto fills
	 *
	 * @return String of current date in mm/dd/yy format
	 */
	public String getTermDate( )
	{
		By loc = OracleCloudCreateInvoicePageFieldData.TERMS_DATE.getLocator();

		WebElement field = wait.waitForElementEnabled(loc);

		String date = field.getAttribute("value");

		return date;
	}

	/**
	 * Get the total amount expected (i.e. sum of the cost of all items)
	 *
	 * @return total expected
	 */
	public String getTotalDue( )
	{
		wait.waitForElementNotEnabled(blockingPlane);
		WebElement totalDue = wait.waitForElementEnabled(totalDueLoc);

		String fullTotals = totalDue.getText();
		int index = fullTotals.lastIndexOf("Due") + 4;

		String total = fullTotals.substring(index);

		return total;
	}

	/**
	 * Get the sum of item costs found, based on the current state of
	 * the invoice
	 *
	 * @return the total found
	 */
	public String getTotalFound( )
	{
		wait.waitForElementNotEnabled(blockingPlane);
		WebElement totalFound = wait.waitForElementEnabled(totalFoundLoc);
		String total = totalFound.getText();

		return total;
	}

	/**
	 * On the Transaction Tax table, get the total tax amount
	 * at the bottom
	 *
	 * @return total transaction tax
	 */
	public String getTotalTransTax( )
	{
		wait.waitForElementNotEnabled(blockingPlane);
		WebElement totalTax;

		try
		{
			totalTax = wait.waitForElementEnabled(transTaxTotalLoc);
		}
		catch ( Exception e )
		{
			click.javascriptClick(taxesExpandLoc);
		}

		jsWaiter.sleep(8000);
		click.javascriptClick(transTaxesView);
		totalTax = wait.waitForElementEnabled(transTaxTotalLoc);
		String total = totalTax.getText();

		return total;
	}

	/**
	 * Matches the invoice lines
	 */
	public void matchInvoiceLines( )
	{
		WebElement arrow = wait.waitForElementEnabled(matchInvoiceLinesArrowLoc);

		action.click(arrow);
		action.perform();

		wait.waitForElementDisplayed(matchTextBoxLoc);

		WebElement checkAll = wait.waitForElementPresent(checkAllMatchesLoc);
		action.click(checkAll);
		action.perform();

		WebElement apply = wait.waitForElementEnabled(applyMatchesButtonLoc);
		click.clickElementCarefully(apply);

		WebElement ok = wait.waitForElementEnabled(okMatchesButtonLoc);
		click.clickElementCarefully(ok);
		wait.waitForElementNotDisplayed(matchTextBoxLoc);
	}

	/**
	 * Write inputs to the fields on the rate name search popup,
	 * then press the Enter key to run the search
	 * (errors when attempting to click buttons to run search)
	 *
	 * @param nameSearchPopup  the rate name search popup
	 * @param rateName         the rate name to write
	 * @param rateCode         the rate code to write
	 * @param regimeName       the regime name to write
	 * @param taxName          the tax name to write
	 * @param statusName       the status name to write
	 * @param jurisdictionName the jurisdiction name to write
	 */
	public void rateNameSearchInputFields( WebElement nameSearchPopup, String rateName, String rateCode,
		String regimeName, String taxName, String statusName, String jurisdictionName )
	{
		wait.waitForElementNotEnabled(blockingPlane);
		WebElement rateNameField = wait.waitForElementEnabled(searchNameRateLoc, nameSearchPopup);
		text.enterText(rateNameField, rateName);

		WebElement regimeNameField = wait.waitForElementEnabled(searchNameRegimeLoc, nameSearchPopup);
		text.enterText(regimeNameField, regimeName);

		WebElement taxNameField = wait.waitForElementEnabled(searchNameTaxNameLoc, nameSearchPopup);
		text.enterText(taxNameField, taxName);

		WebElement jurisdictionNameField = wait.waitForElementEnabled(searchNameJurisdictionLoc, nameSearchPopup);
		text.enterText(jurisdictionNameField, jurisdictionName);

		text.pressEnter(jurisdictionNameField);
	}

	/**
	 * After searching for a rate name,
	 * select the row option from the results
	 *
	 * @param nameSearchPopup
	 * @param rowNumber row to be selected
	 */
	public void rateNameSearchSelectResult( WebElement nameSearchPopup, int rowNumber )
	{
		WebElement resultsTable = wait.waitForElementDisplayed(searchNameTableResultsLoc, nameSearchPopup);
		List<WebElement> resultsList = wait.waitForAllElementsDisplayed(resultsTag, resultsTable);

		WebElement select = element.selectElementByAttribute(resultsList, String.valueOf(rowNumber), "_afrrk"); // 1 for ecog-test, 2 for dev1
		click.clickElement(select);
		click.performDoubleClick(select);

		wait.waitForElementNotDisplayedOrStale(nameSearchPopup, 10);
	}

	// TODO Michael Salomone May need to deprecate this method in favor of selectInvoiceLine (below)

	/**
	 * Selects the invoice line that is currently active
	 *
	 * @return the active line
	 */
	public WebElement selectActiveInvoiceLine( )
	{
		WebElement lines = wait.waitForElementDisplayed(activeLineLoc);
		List<WebElement> linesList = wait.waitForAllElementsDisplayed(invoiceLinesTableLoc, lines);

		return lines;
	}

	/**
	 * Select an option from a dropdown menu
	 * Object on page must be tagged as "SELECT"
	 *
	 * @param data         enum containing information for dropdown to select
	 * @param selectOption text value of the option to be selected
	 */
	public void selectFromDropdown( OracleCloudCreateInvoicePageFieldData data, String selectOption )
	{
		By dropdownLoc = data.getLocator();
		WebElement dropdownWebEle = wait.waitForElementDisplayed(dropdownLoc);
		dropdown.selectDropdownByDisplayName(dropdownWebEle, selectOption);
	}

	/**
	 * Selects a line from the invoice
	 *
	 * @param lineNum the line number displayed on the page
	 *
	 * @return WebElement for the line
	 */
	public void clickInvoiceLine( int lineNum )
	{
		lineNum--;
		String lineNumStr = Integer.toString(lineNum);
		String attribute = "_afrrk";

		List<WebElement> linesList = wait.waitForAllElementsDisplayed(invoiceLinesLoc);

		WebElement line = element.selectElementByAttribute(linesList, lineNumStr, attribute);
		click.clickElementCarefully(line);
	}

	/**
	 * Select the invoice line at the position indicated
	 * by the lineNum parameter.
	 *
	 * @param lineNum the line number to select on the page.
	 *
	 * @return the selected line.
	 */
	public WebElement selectInvoiceLine( int lineNum )
	{
		lineNum--;
		String lineNumStr = Integer.toString(lineNum);
		String attribute = "_afrrk";

		List<WebElement> linesList = wait.waitForAllElementsDisplayed(invoiceLinesLoc);

		WebElement line = element.selectElementByAttribute(linesList, lineNumStr, attribute);

		return line;
	}

	/**
	 * Explicitly clicks the first invoice line in scenarios where the Oracle
	 * UI does not.
	 */
	public void selectInvoiceLineNotDisplayed( )
	{
		WebElement firstLine = wait.waitForElementDisplayed(invoiceLineExplicitLoc);
		click.clickElementCarefully(firstLine);
	}

	/**
	 * On the transaction tax table, select
	 * one of the lines
	 *
	 * @param lineNum number of the line desired
	 *
	 * @return the line
	 */
	public WebElement selectTaxLine( int lineNum )
	{
		lineNum--;
		String lineNumStr = Integer.toString(lineNum);
		String attribute = "_afrrk";
		List<WebElement> linesList;

		try
		{
			linesList = wait.waitForAllElementsDisplayed(taxLinesLoc); // Salomone - cancelInvoice
		}
		catch ( TimeoutException toe )
		{
			clickExpandTaxesButton();
			clickTaxTab("Transaction Taxes");
			linesList = wait.waitForAllElementsDisplayed(taxLinesLoc);
		}

		WebElement line = element.selectElementByAttribute(linesList, lineNumStr, attribute);

		return line;
	}

	/**
	 * On the edit transaction tax popup, click on the
	 * Tax Only checkbox to check it off
	 *
	 * @param taxPopup the edit tax popup
	 */
	public void taxPopupCheckTaxOnly( WebElement taxPopup )
	{
		wait.waitForElementNotEnabled(blockingPlane);
		WebElement checkbox = wait.waitForElementEnabled(editTransTaxTaxOnlyCheckLoc, taxPopup);
		action.click(checkbox);
		action.perform();
	}

	/**
	 * On the edit transaction tax popup, click on the
	 * add row button to add an editable row
	 *
	 * @param taxPopup the edit tax popup
	 */
	public void taxPopupClickAddRow( WebElement taxPopup )
	{
		wait.waitForElementNotEnabled(blockingPlane);

		WebElement button = wait.waitForElementEnabled(addTaxLineButtonLoc, taxPopup);

		click.clickElement(button);
	}

	/**
	 * Click a button on the edit transaction tax popup,
	 * based on the text displayed on that button
	 *
	 * @param taxPopup   the edit tax popup
	 * @param buttonText the text of the button desired
	 */
	public void taxPopupClickButton( WebElement taxPopup, String buttonText )
	{
		List<WebElement> buttonList = wait.waitForAllElementsDisplayed(buttonRole, taxPopup);
		WebElement button = element.selectElementByText(buttonList, buttonText);

		wait.waitForElementEnabled(button);
		wait.waitForElementNotEnabled(blockingPlane);
		click.clickElementCarefully(button);

		wait.waitForElementNotDisplayedOrStale(taxPopup, 85);
	}

	/**
	 * Click a button on the edit transaction tax popup,
	 * based on the text displayed on that button
	 *
	 * @param taxPopup   the edit tax popup
	 * @param buttonText the text of the button desired
	 */
	public void taxPopupClickSaveAndCloseButton( WebElement taxPopup)
	{
		wait.waitForElementEnabled(buttonRoleOnPopUp);
		wait.waitForElementNotEnabled(blockingPlane);
		click.clickElementCarefully(buttonRoleOnPopUp);

		wait.waitForElementNotDisplayedOrStale(taxPopup, 85);
	}

	/**
	 * On the edit transaction tax popup, first click the arrow in the name field
	 * to open a dropdown menu. At the bottom of the menu, click the search button
	 * in order to open the search rate name popup
	 *
	 * @param taxPopup the edit tax popup
	 *
	 * @return the search rate name popup
	 */
	public WebElement taxPopupSearchName( WebElement taxPopup )
	{
		wait.waitForElementNotEnabled(blockingPlane);
		WebElement searchDropdownButton;

		try
		{
			searchDropdownButton = wait.waitForElementEnabled(editTransTaxNameSearchLoc, taxPopup, 15);
		}
		catch ( Exception ex )
		{
			String tableId = "_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:pm1:r1:0:ap1:AT1:r19:0:AT1:_ATp:table1::scroller";
			scroll.scrollTableLeft(editTransScrollbarLoc, tableId, "2000");
			searchDropdownButton = wait.waitForElementEnabled(editTransTaxNameSearchLoc, taxPopup);
		}
		click.clickElement(searchDropdownButton);

		WebElement dropdown = wait.waitForElementDisplayed(dropdownPopupLoc);
		WebElement search = wait.waitForElementEnabled(dropdownPopupSearchLoc, dropdown);

		click.clickElement(search);

		WebElement popup = wait.waitForElementDisplayed(searchRateNamePopupLoc);

		return popup;
	}

	/**
	 * On the edit transaction tax popup, write an input into
	 * the rate name field
	 * This may open the search rate name popup
	 *
	 * @param taxPopup the edit tax popup
	 * @param name     the name to input
	 *
	 * @return the search rate name popup
	 */
	public WebElement taxPopupWriteName( WebElement taxPopup, String name )
	{
		wait.waitForElementNotEnabled(blockingPlane);
		WebElement field = wait.waitForElementEnabled(editTransTaxNameLoc, taxPopup);
		text.enterText(field, name);
		text.pressTab(field);

		wait.waitForElementNotEnabled(blockingPlane);
		WebElement searchNamePopup = wait.waitForElementEnabled(editTransTaxNameLoc);

		return searchNamePopup;
	}

	/**
	 * On the edit transaction tax popup, write an input into
	 * the rate id field
	 *
	 * @param taxPopup the edit tax popup
	 * @param name     the name to input
	 *
	 * @return
	 */
	public WebElement taxPopupWriteRateId( WebElement taxPopup, String name )
	{
		wait.waitForElementNotEnabled(blockingPlane);
		WebElement field = wait.waitForElementEnabled(editTransTaxRateId, taxPopup);
		text.enterText(field, name);
		text.pressTab(field);

		wait.waitForElementNotEnabled(blockingPlane);
		WebElement searchNamePopup = wait.waitForElementEnabled(editTransTaxRateId);

		return searchNamePopup;
	}

	/**
	 * On the edit transaction tax popup, write an input to the
	 * amount field
	 *
	 * @param taxPopup the edit tax popup
	 * @param amount   amount to input
	 *
	 * @return the amount field written to
	 */
	public WebElement taxPopupWriteAmount( WebElement taxPopup, String amount, int rownum )
	{
		wait.waitForElementNotEnabled(blockingPlane);
		String rowNumber = String.valueOf(rownum - 1);
		By editTransTaxAmountLoc = By.cssSelector("input[id$='" + rowNumber + ":inputText4::content']");

		WebElement field = wait.waitForElementDisplayed(editTransTaxAmountLoc);
		text.clearText(field);
		text.enterText(field, amount);
		text.pressEnter(field);
		return field;
	}

	/**
	 * Click on Vertex Tax Calculation Summary
	 */
	public void taxCalcSummaryTable( )
	{
		click.clickElement(vertTaxCalcSummaryTabLinkLoc);
		new WebDriverWait(driver, 15).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameTaxCalcSummary));
		wait.waitForElementDisplayed(headerTaxCalcSummary, 15);
	}

	/**
	 * Select an option from a dropdown menu
	 * Object on page must be tagged as "SELECT"
	 *
	 * @param data         enum containing information for dropdown to select
	 * @param selectOption text value of the option to be selected
	 */
	public void selectFromDropdown( OracleCloudCreateTransactionPageFieldData data, String selectOption )
	{
		By dropdownLoc = data.getLocator();
		WebElement dropdownWebEle = wait.waitForElementDisplayed(dropdownLoc);
		dropdown.selectDropdownByDisplayName(dropdownWebEle, selectOption);
	}

	/**
	 * Waits for an automatic input on an empty field
	 *
	 * @param data  enum containing field information
	 * @param input whether it is an input or read only field
	 */
	public void waitForAutomaticInput( OracleCloudCreateInvoicePageFieldData data, boolean input )
	{
		String attribute = "value";
		wait.waitForElementNotEnabled(blockingPlane);
		jsWaiter.sleep(7000);

		final ExpectedCondition<Boolean> condition = driver ->
		{
			boolean autoInputComplete;
			String currentInput;
			By loc = data.getLocator();
			if ( input )
			{
				WebElement field = wait.waitForElementDisplayed(loc);
				currentInput = field.getAttribute(attribute);
			}
			else
			{
				WebElement field = wait.waitForElementDisplayed(loc);
				currentInput = field.getText();
			}
			autoInputComplete = !currentInput.isEmpty();
			return autoInputComplete;
		};

		WebDriverWait wait = new WebDriverWait(driver, VertexAutomationObject.QUARTER_MINUTE_TIMEOUT);

		wait.until(condition);
	}

	/**
	 * When recalculating tax or taking certain actions on a validated invoice
	 * that automatically update the invoice, wait for the invoice to refresh
	 * so labelling is correct before checking it
	 *
	 * (When updating a validated invoice, the supplier search field changes from
	 * readonly to input)
	 */
	public void waitForCalculation( )
	{
		final ExpectedCondition<Boolean> condition = driver ->
		{
			boolean validated;
			By supplierLoc = OracleCloudCreateInvoicePageFieldData.SUPPLIER.getLocator();
			wait.waitForElementNotEnabled(blockingPlane);
			WebElement messageArea = wait.waitForElementDisplayed(validationLoc);
			String current = messageArea.getText();

			validated = !current.isEmpty() && element.isElementDisplayed(supplierLoc);
			return validated;
		};

		WebDriverWait wait = new WebDriverWait(driver, VertexAutomationObject.HALF_MINUTE_TIMEOUT);

		wait.until(condition);
	}

	public String getErrorMessage( )
	{
		WebElement popup = wait.waitForElementDisplayed(errorMsgPopupLoc);
		WebElement message = wait.waitForElementDisplayed(errorMsgContentLoc, popup);
		String errorMsg = message.getText();
		return errorMsg;
	}

	/**
	 * Locate the Payment Number field that autofills upon filling
	 * Payment Document and get the number
	 *
	 * @return the payment number for that invoice
	 */
	public String getPaymentNumber( )
	{
		scroll.scrollElementIntoView(paymentNumberLoc);
		WebElement payNum = wait.waitForElementDisplayed(paymentNumberLoc);
		String number = payNum.getAttribute("value");
		if ( number.equals("") )
		{
			number = payNum.getAttribute("value");
		}
		return number;
	}

	/**
	 * Wait for "Needs revalidation" link
	 */
	public void waitForNeedsRevalidation( )
	{
		final WebDriverWait wait = new WebDriverWait(driver, 125);
		wait.until(ExpectedConditions.textToBePresentInElementLocated(needsRevalidationLoc, "Needs revalidation"));
	}

	/**
	 * Wait for AP invoice validation by scanning the webpage for
	 * text matching "Validated".
	 */
	public void waitForValidationByText( )
	{
		final WebDriverWait wait = new WebDriverWait(driver, 125);
		wait.until(ExpectedConditions.textToBePresentInElementLocated(needsRevalidationLoc, "Validated"));
	}

	/**
	 * When validating an invoice that is in a non-validated state,
	 * wait for the invoice to refresh so labelling is correct before checking it
	 *
	 * (When validating an unvalidated invoice, the supplier search field changes from
	 * input to readonly)
	 */
	public void waitForValidation( )
	{
		final ExpectedCondition<Boolean> condition = driver ->
		{
			boolean validated;
			WebElement messageArea = wait.waitForElementDisplayed(validationLoc);
			String current = messageArea.getText();

			validated = !current.isEmpty();
			return validated;
		};

		WebDriverWait wait = new WebDriverWait(driver, 90);

		wait.until(condition);
	}

	public void waitForRevalidation( )
	{
		final ExpectedCondition<Boolean> condition = driver ->
		{
			boolean validated;
			WebElement messageArea = wait.waitForElementDisplayed(validationLoc);
			String current = messageArea.getText();

			validated = !current.equals("Needs revalidation") && !current.equals("Not validated");
			return validated;
		};

		WebDriverWait wait = new WebDriverWait(driver, 90);

		wait.until(condition);
	}

	public WebElement waitForWarningPopup( )
	{
		WebElement popup = wait.waitForElementDisplayed(warningPopupLoc);
		return popup;
	}

	public WebElement waitForPrepaymentPopup( )
	{
		WebElement popup = wait.waitForElementDisplayed(applyOrUnapplyPrepaymentPopupLoc);
		return popup;
	}

	/**
	 * Select the apply or unapply prepayment option from the actions dropdown,
	 * and apply a prepayment to the current invoice.
	 *
	 * @param popup        the element representing the prepayment application window.
	 * @param numberSelect the prepayment invoice number.
	 */
	public void applyPrepayment( WebElement popup, String numberSelect )
	{
		WebElement table = wait.waitForElementDisplayed(prepaymentsTableLoc, popup);

		searchPrepaymentInvoice(numberSelect);

		jsWaiter.sleep(7200);

		WebElement desiredPrepayment = wait.waitForElementDisplayed(prepaymentsTableFirstElementLoc);

		jsWaiter.sleep(7200);

		click.clickElement(desiredPrepayment);

		WebElement apply = wait.waitForElementEnabled(applyButtonLoc);
		click.clickElementCarefully(apply);
		// wait for application

		WebElement done = wait.waitForElementEnabled(doneButtonLoc);
		click.clickElementCarefully(done);

		wait.waitForElementNotDisplayedOrStale(popup, 20);
	}

	/**
	 * Selects the Query By Example icon and searches for the provided
	 * invoice number all within the prepayment application window.
	 *
	 * @param prepaymentNum the prepayment invoice number.
	 */
	public void searchPrepaymentInvoice( String prepaymentNum )
	{
		driver
			.manage()
			.timeouts()
			.implicitlyWait(10, TimeUnit.SECONDS);

		WebElement qbeIcon = wait.waitForElementDisplayed(queryByExampleLoc);
		click.clickElement(qbeIcon);

		WebElement inputField = wait.waitForElementDisplayed(qbeSearchNumberLoc);

		inputField.clear();
		inputField.sendKeys(prepaymentNum);

		inputField = wait.waitForElementEnabled(qbeSearchNumberLoc);

		text.pressEnter(inputField);
	}

	/**
	 * Writes to the invoice header fields in the top half
	 * of the create invoice page
	 *
	 * @param data         enum containing field information
	 * @param inputToField input to write
	 *
	 * @return WebElement of field interacted with
	 */
	public WebElement writeToHeaderField( OracleCloudCreateInvoicePageFieldData data, String inputToField )
	{
		wait.waitForElementNotEnabled(blockingPlane);

		By loc = data.getLocator();
		WebElement inputField = wait.waitForElementEnabled(loc);

		inputField.clear();
		inputField.sendKeys(inputToField);

		inputField = wait.waitForElementEnabled(loc);

		return inputField;
	}

	/**
	 * Writes to a header field and checks to make sure the input was correctly written
	 * Due to Oracle occasionally deselecting input fields
	 *
	 * @param data  enum containing field information
	 * @param input input to write
	 *
	 * @return WebElement of field interacted with
	 */
	public WebElement inputAndCheck( OracleCloudCreateInvoicePageFieldData data, String input )
	{
		WebElement field = writeToHeaderField(data, input);
		text.pressEnter(field);

		while ( !checkInput(data, input) )
		{
			field = writeToHeaderField(data, input);
			text.pressEnter(field);
		}

		return field;
	}

	/**
	 * Writes to ain nformation field, and mimics a tab key press.
	 *
	 * @param data  enum containing field information
	 * @param input input to write
	 *
	 * @return WebElement of field interacted with
	 */
	public WebElement inputAndTab( OracleCloudCreateInvoicePageFieldData data, String input )
	{
		WebElement field = writeToHeaderField(data, input);
		text.pressTab(field);

		return field;
	}

	/**
	 * Writes to one of the fields under the Lines section
	 * of the page
	 *
	 * @param data  enum containing field information
	 * @param input input to write
	 * @param line  line the field is on
	 *
	 * @return WebElement of field interacted with
	 */
	public WebElement writeToLine( OracleCloudCreateInvoicePageFieldData data, String input, WebElement line )
	{
		By loc = data.getLocator();

		WebElement inputField;

		try
		{
			inputField = wait.waitForElementPresent(loc, line, 15);
		}
		catch ( TimeoutException te )
		{
			inputField = wait.waitForElementDisplayed(loc); // Salomone - AP Calif and prepayments
		}

		action
			.moveToElement(inputField)
			.perform();
		inputField = wait.waitForElementEnabled(inputField);
		text.enterText(inputField, input);
		text.pressTab(inputField);

		return inputField;
	}

	/**
	 * Helper method
	 * Checks the written input on a header field
	 *
	 * @param data          enum containing field information
	 * @param expectedInput the text expected to be in the field
	 *
	 * @return whether the input found matches the input expected
	 */
	public boolean checkInput( OracleCloudCreateInvoicePageFieldData data, String expectedInput )
	{
		boolean correct = false;
		wait.waitForElementNotEnabled(blockingPlane);

		By loc = data.getLocator();

		WebElement field = wait.waitForElementEnabled(loc);

		String actualInput = field.getAttribute("value");

		if ( expectedInput.equals(actualInput) )
		{
			correct = true;
		}

		return correct;
	}

	/**
	 * Wait for Pay in Full popup to appear after selecting the
	 * Pay in Full option.
	 *
	 * @return div containing popup container.
	 */
	public WebElement waitForFullPaymentPopup( )
	{
		WebElement popup = wait.waitForElementDisplayed(payInFullPopupLoc);
		return popup;
	}

	/**
	 * Fill all necessary popup fields to fulfill Pay In Full action.
	 */
	public String fillFullPaymentPopup( WebElement popup, String processProfile, String paymentDoc )
	{

		writeToHeaderField(OracleCloudCreateInvoicePageFieldData.PAYMENT_PROCESS_PROFILE, processProfile);

		inputAndCheck(OracleCloudCreateInvoicePageFieldData.PAYMENT_DOCUMENT, paymentDoc);

		waitForAutomaticInput(OracleCloudCreateInvoicePageFieldData.PAYMENT_NUMBER, true);
		String paymentNumber = getPaymentNumber();

		WebElement submit = driver.findElement(payInFullSubmitButtonLoc);
		click.clickElementCarefully(submit); // Salomone - ap prepayments

		wait.waitForElementNotDisplayedOrStale(popup, 30);
		clickOk();
		return paymentNumber;
	}
}
