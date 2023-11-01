package com.vertex.quality.connectors.concur.pages.panelPages;

import com.vertex.quality.connectors.concur.pojos.ConcurInvoiceExpense;
import net.bytebuddy.asm.Advice;
import org.openqa.selenium.*;

import java.util.ArrayList;
import java.util.List;

/**
 * represents the invoice details page that loads when creating an invoice
 *
 * @author alewis
 */
public class ConcurEnterInvoiceDetailsPage extends ConcurInvoicePage
{
	protected final By containerClass = By.className("x-grid3-col-ReqItemEd2-UnitPrice");
	protected final By expenseTypeContainer = By.className("x-grid3-col-ReqItemEd2-PetCode");
	protected final By invoiceFieldContainer = By.className("divcell");
	protected final By textFieldLoc = By.className("x-form-text");
	protected final By shipToAddressFieldContainer =By.xpath("(//input[@type='text'])[2]");

	protected final By amountRemaining = By.xpath("//*/div[contains(text(),'Breakfast')]");
	protected final By requestLine = By.id("Request.RequestLineItemAddEditGrid");

	protected final By invoiceDetailsTableContainer = By.className("x-panel-body-noheader");
	protected final By addButton = By.className("btn_add");
	protected final By popupSaveFirstLocator = By.className("greenBtn");
	protected final By popupSaveButton = By.className("x-btn-text");
	protected final By popupSaveButton1 = By.xpath("(//button[@CLASS=' x-btn-text btn_save'])[3]");
	protected final By submitButton = By.className("btn_submit");
	protected final By shipToLink = By.xpath("//a[@id='Select_Ship_To_Location_Link']");
	protected final By actionsButtonLocator = By.className("menu_action");
	protected final By actionsMenuLocator = By.id("PaymentRequestPanel.RequestActionsMenu");
	protected final By actionOptionsLocator = By.className("x-menu-item");

	protected final By dialogWindowLocator = By.className("x-window-dlg");
	protected final By dialogWindowButtonClass = By.className("x-btn-text");

	public ConcurEnterInvoiceDetailsPage( final WebDriver driver )
	{
		super(driver);
	}

	/**
	 * enters attributes of invoice in the enter invoice details page
	 *
	 * @param invoiceNameText        text to enter into invoice name field
	 * @param invoiceNumberText      text to enter into invoice number field
	 * @param invoiceDateText        text to enter into invoice date field
	 * @param currencyText           text to enter into currency field
	 * @param totalInvoiceAmountText text to enter into total invoice amount field
	 * @param taxProviderCompanyText text to enter into tax provider company field
	 */
	public void enterInvoiceDetails( final String invoiceNameText, final String invoiceNumberText,
		final String invoiceDateText, final String currencyText, final String totalInvoiceAmountText,
		final String taxProviderCompanyText, String shipToAddressValue)
	{
		waitForPageLoad();

		//enter invoice name
		String invoiceNameFieldText = "Invoice Name";
		WebElement invoiceNameContainer = element.selectElementByText(invoiceFieldContainer, invoiceNameFieldText);
		WebElement invoiceNameField = wait.waitForElementEnabled(textFieldLoc, invoiceNameContainer);
		text.enterText(invoiceNameField, invoiceNameText);

		//enter invoice number
		String invoiceNumberFieldText = "Invoice Number";
		WebElement invoiceNumContainer = element.selectElementByText(invoiceFieldContainer, invoiceNumberFieldText);
		WebElement invoiceNumField = wait.waitForElementEnabled(textFieldLoc, invoiceNumContainer);
		text.enterText(invoiceNumField, invoiceNumberText);

		//enter invoice date
		String invoiceDateFieldText = "Invoice Date";
		WebElement invoiceDateContainer = element.selectElementByText(invoiceFieldContainer, invoiceDateFieldText);
		WebElement invoiceDateField = wait.waitForElementEnabled(textFieldLoc, invoiceDateContainer);
		text.enterText(invoiceDateField, invoiceDateText);

		//TODO - might keep
		//StringBuffer currencyString = new StringBuffer(currencyText);
		//currencyString.append(Keys.ENTER);
		//text.enterText(currencyLocator, "",true);
		//text.enterText(currencyLocator, currencyString,false);
		//waitForPageLoad();

		//enter invoice amount
		String totalInvoiceAmountFieldText = "Total Invoice Amount (incl S&T)";
		WebElement totalInvoiceAmountContainer = element.selectElementByText(invoiceFieldContainer,
			totalInvoiceAmountFieldText);
		WebElement totalInvoiceAmountField = wait.waitForElementEnabled(textFieldLoc, totalInvoiceAmountContainer);
		text.enterText(totalInvoiceAmountField, totalInvoiceAmountText);



		//enter company
		//		waitForPageLoad();
		//
		//		String totalProviderCompanyFieldText = "Tax Provider Company";
		//		WebElement taxProviderCompanyContainer = element.selectElementByText(invoiceFieldContainer,
		//			totalProviderCompanyFieldText);
		//		WebElement taxProviderCompanyField = wait.waitForElementEnabled(textFieldLoc, taxProviderCompanyContainer);
		//
		//		StringBuffer taxProviderCompanyString = new StringBuffer(taxProviderCompanyText);
		//		taxProviderCompanyString.append(Keys.ENTER);
		//		text.enterTextByIndividualCharacters(taxProviderCompanyField, taxProviderCompanyString.toString());

         //click save button
		final String saveBtnText = "Save";
		WebElement saveBtn = element.selectElementByText(popupSaveButton, saveBtnText);
		wait.waitForElementEnabled(saveBtn);
		click.clickElement(saveBtn);
	}

	/**
	 * enter number of expenses into invoice popup
	 *
	 * @param expenses an ArrayList of expenses, each expense is inputted using a for loop
	 */
	public ConcurInvoicePage handleAmountRemainingPopup( final ArrayList<ConcurInvoiceExpense> expenses )
	{
		for ( ConcurInvoiceExpense expense : expenses )
		{
			String expenseTypeInput = expense.getExpenseTypeInput();
			String lineDescriptionInput = "  " +
					expense.getLineDescriptionInput();  // need to add double space so "87" will be typed
			String quantityInput = expense.getQuantityInput();
			String unitPriceInput = expense.getUnitPriceInput();
			String totalPayInput = expense.getTotalPayInput();



			WebElement itemAddContainer = wait.waitForElementPresent(requestLine);

			WebElement tableContainer = wait.waitForElementPresent(invoiceDetailsTableContainer, itemAddContainer);

			waitForPageLoad();

			WebElement titleRowContainer = wait.waitForElementDisplayed(By.tagName("thead"), tableContainer);

			List<WebElement> columnHeaders = wait.waitForAllElementsDisplayed(By.tagName("div"), titleRowContainer);

			int expenseTypeIndex = -1;
			int lineDescriptionIndex = -1;
			int quantityContainerIndex = -1;
			int unitPriceIndex = -1;
			int totalPayIndex = -1;

			final String expenseTypeTitle = "Expense Type";
			final String lineDescriptionTitle = "Line Description";
			final String quantityTitle = "Quantity";
			final String unitPriceTitle = "Unit Price";
			final String totalTitle = "Total";

			for ( int k = 1 ; k < columnHeaders.size() ; k++ )
			{
				String unparsedHeaderText = text.getElementText(columnHeaders.get(k));

				if ( unparsedHeaderText != null )
				{
					String columnTitle = parseTitle(unparsedHeaderText);

					if ( expenseTypeTitle.equals(columnTitle) )
					{
						expenseTypeIndex = k;
					}
					else if ( lineDescriptionTitle.equals(columnTitle) )
					{
						lineDescriptionIndex = k;
					}
					else if ( quantityTitle.equals(columnTitle) )
					{
						quantityContainerIndex = k;
					}
					else if ( unitPriceTitle.equals(columnTitle) )
					{
						unitPriceIndex = k;
					}
					else if ( totalTitle.equals(columnTitle) )
					{
						totalPayIndex = k;
					}
				}
			}
			//Given the current layout of expense adding on can only add one row so there should only be one tbody tag in the table
			WebElement fieldContainer = wait.waitForElementDisplayed(By.tagName("tbody"), tableContainer);

			List<WebElement> newRowCellElements = wait.waitForAllElementsPresent(By.tagName("div"), fieldContainer);
			//enter line description

			WebElement expenseTypeContainer = newRowCellElements.get(expenseTypeIndex);
			WebElement lineDescriptionContainer = newRowCellElements.get(lineDescriptionIndex);
			WebElement quantityContainer = newRowCellElements.get(quantityContainerIndex);
			WebElement unitPriceContainer = newRowCellElements.get(unitPriceIndex);
			WebElement totalPayContainer = newRowCellElements.get(totalPayIndex);

			WebElement unitExpenseTypeContainer = wait.waitForElementDisplayed(By.xpath("//*/div[contains(text(),'Fuel')]"));
			click.clickElementCarefully(unitExpenseTypeContainer);
			jsWaiter.sleep(3000);

			WebElement unitExpenseTypeContaine = wait.waitForElementDisplayed(By.xpath("//*/div[contains(@class,'x-grid3-col-ReqItemEd2-Description')]"));
			click.clickElementCarefully(unitExpenseTypeContaine);
			jsWaiter.sleep(3000);

			//enterTextAsAction(quantityContainer, quantityInput);

			try
			{
				enterTextAsAction(unitPriceContainer, unitPriceInput);
			}
			catch ( org.openqa.selenium.StaleElementReferenceException | org.openqa.selenium.InvalidElementStateException ex )
			{
				WebElement aaronUnitPriceContainer = wait.waitForElementDisplayed(containerClass);
				enterTextAsAction(aaronUnitPriceContainer, unitPriceInput);
			}

			//enterTextAsAction(totalPayContainer, totalPayInput);



			waitForPageLoad();

			//click add button
			final String addBtnText = "Add";
			WebElement addBtn = element.selectElementByText(addButton, addBtnText);

			wait.waitForElementEnabled(addBtn);
			click.clickElement(addBtn);

			waitForPageLoad();
		}

		waitForPageLoad();

		//click save button
		final String saveBtnText = "Save";
		WebElement saveBtn = element.selectElementByText(popupSaveFirstLocator, saveBtnText);
		WebElement saveBtnToPress = element.getWebElement(popupSaveButton, saveBtn);
		wait.waitForElementEnabled(saveBtnToPress);
		click.clickElement(saveBtnToPress);

		waitForPageLoad();

		//reset view
		final int halfZoom = 50;
		final int fullZoom = 100;
		window.setZoomPercentage(halfZoom);
		window.setZoomPercentage(fullZoom);

		waitForPageLoad();

		//click submit invoice
		final String submitInvoiceBtnText = "Submit Invoice";
		WebElement foundSubmit = element.selectElementByText(submitButton, submitInvoiceBtnText);

		wait.waitForElementEnabled(foundSubmit);
		click.clickElement(foundSubmit);

		waitForPageLoad();

		ConcurInvoicePage invoiceReturnPage = initializePageObject(ConcurInvoicePage.class);
		return invoiceReturnPage;
	}

	/**
	 * Deletes the invoice by selecting the delete option from the actions menu
	 * and return to invoice list after deletion
	 *
	 * @return
	 */
	public ConcurInvoicePage deleteInvoiceReturnToList( )
	{
		WebElement actionsButton = wait.waitForElementEnabled(actionsButtonLocator);

		click.clickElementCarefully(actionsButton);

		wait.waitForElementDisplayed(actionsMenuLocator);

		WebElement delete = element.selectElementByText(actionOptionsLocator, "Delete Invoice");

		wait.waitForElementEnabled(delete);

		click.clickElementCarefully(delete);

		wait.waitForElementDisplayed(dialogWindowLocator);

		WebElement yesButton = element.selectElementByText(dialogWindowButtonClass, "Yes");

		wait.waitForElementEnabled(yesButton);

		click.clickElementCarefully(yesButton);

		// popup asking if user wants to return to list or create a new invoice may appear
		// if not, returning to list is default
		try
		{
			WebElement backToListButton = element.selectElementByText(dialogWindowButtonClass, "Back to List");

			wait.waitForElementEnabled(backToListButton);

			click.clickElementCarefully(backToListButton);
		}
		catch ( TimeoutException e )
		{

		}

		return initializePageObject(ConcurInvoicePage.class);
	}

	private String parseTitle( final String unparsedExpectedTitle )
	{
		String returnTitle = unparsedExpectedTitle;
		if ( unparsedExpectedTitle.length() > 0 )
		{
			if ( returnTitle
				.substring(0, 1)
				.equals("*") )
			{
				returnTitle = returnTitle.substring(1);
			}
		}
		if ( unparsedExpectedTitle.length() > 0 )
		{
			if ( returnTitle
				.substring(0, 1)
				.equals(" ") )
			{
				returnTitle = returnTitle.substring(1);
			}
		}
		return returnTitle;
	}
}
