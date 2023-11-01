package com.vertex.quality.connectors.ariba.portal.pages.invoice;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.ariba.portal.dialogs.invoice.AribaPortalNoPoInvoiceAddTaxDialog;
import com.vertex.quality.connectors.ariba.portal.dialogs.requisition.AribaPortalEditLineItemContactDialog;
import com.vertex.quality.connectors.ariba.portal.pages.common.AribaPortalPostLoginBasePage;
import org.openqa.selenium.*;

import java.util.List;

/**
 * the representation of the page where an invoice is created
 *
 * @author legacyAribaProgrammer ssalisbury
 */
public class AribaPortalInvoiceCreationPage extends AribaPortalPostLoginBasePage
{
	protected final By invoiceTypesContainer = By.className("is-block");
	protected final By selectButtonParentListContLoc = By.cssSelector("table[role='presentation']");
	protected final By selectButtonListContLoc = By.cssSelector("tr[valign='middle']");
	protected final By dialogHeaderLoc = By.className("w-dlg-header");
	protected final By invoiceTypesClass = By.className("w-rdo-list");
	protected final By popUpWindow = By.className("w-dlg-panel-active");
	protected final By doneButton = By.cssSelector("button[title='Done Button']");
	protected final By invoiceTypeCheckbox = By.className("w-rdo-dsize");
	protected final By LINE_ITEM_ROW = By.cssSelector("tr[dr='1']");
	protected final By amountFieldLoc = By.className("a-ir-dspl-txt");
	protected final By mainLineItemCheckBoxLoc = By.xpath("(//div[@class='w-chk-container'])[2]");
	protected final By clickableCheckBoxLoc = By.xpath("(//label[@class='w-chk w-chk-dsize'])[1]");
	protected final By checkBoxContLoc = By.className("w-chk-container");
	protected final By visibleCheckBoxLoc = By.className("w-chk-native");
	protected final By detailsListCont = By.id("addItemDetail");
	protected final By taxSummaryContLoc = By.className("w-arw-summarygroup-extra");
	protected final By submitButtonLoc = By.className("w-btn-primary");
	protected final By itemsTableContLoc = By.xpath("(//table[@class='awtWrapperTable'])[2]");
	protected final By priceFieldContLoc = By.className("noWrap");
	protected final By addItemListContLoc = By.id("addItem");
	protected final By PO_DROPDOWN_ITEM = By.xpath("//*/span[@class='w-chSelections']/span[@chlname='ChooserList']/a[contains(text(),'PO')][1]");
	protected final By DROP_DOWN_ARROWS = By.xpath("//*/div[@class='w-chWrapRight']/a/div");
	protected final By FIRST_LINE_ITEM = By.xpath("//*/tr[contains(@class,'firstRow') and @dr='1']");
	public AribaPortalNoPoInvoiceAddTaxDialog addTaxDialog;
	public AribaPortalEditLineItemContactDialog supplierContact;

	public AribaPortalInvoiceCreationPage( WebDriver driver )
	{
		super(driver);
		this.addTaxDialog = initializePageObject(AribaPortalNoPoInvoiceAddTaxDialog.class, this);
		this.supplierContact = initializePageObject(AribaPortalEditLineItemContactDialog.class, this);
	}

	/**
	 * selects the invoice type with the given label text
	 *
	 * @param typeLabel the label text of the desired invoice type
	 *
	 * @author osabha
	 */
	public void selectInvoiceType( final String typeLabel ) throws InterruptedException
	{
		// not using the checkbox.setCheckBox because ariba has two check boxes for each visible check box
		// one hidden and one actual but doesn't have the input tag.
		WebElement invoiceTypeCont = findInvoiceTypeCont(typeLabel);
		WebElement targetInvoice = wait.waitForElementPresent(By.tagName("input"), invoiceTypeCont);
		boolean isChecked = checkbox.isCheckboxChecked(targetInvoice);
		WebElement targetInvoiceCheckBox = wait.waitForElementEnabled(invoiceTypeCheckbox, invoiceTypeCont);
		if ( !isChecked )
		{
			click.clickElement(targetInvoiceCheckBox);
		}
		Thread.sleep(4000);
	}

	/**
	 * waits for a popup to open and then closes it
	 *
	 * @author legacyAribaProgrammer ssalisbury
	 */
	public void pop_up_handler( )
	{
		try
		{
			wait.waitForElementDisplayed(popUpWindow, DEFAULT_TIMEOUT);
		}
		catch ( TimeoutException e )
		{
			VertexLogger.log("No popup opened", getClass());
		}

		WebElement popup = driver.findElement(popUpWindow);
		String popupOpenState = null;
		if ( popup != null )
		{
			popupOpenState = popup.getAttribute("_cfopen");
		}
		if ( "true".equals(popupOpenState) )
		{
			wait.waitForElementDisplayed(popUpWindow);
			driver
				.findElement(doneButton)
				.sendKeys(Keys.ENTER);
			wait.waitForElementNotPresent(popUpWindow);
		}
	}

	/**
	 * finds the invoice option with the given label
	 *
	 * @param typeLabel the label of the desired invoice option
	 *
	 * @return the element of the invoice option with the given label
	 *
	 * @author osabha
	 */
	protected WebElement findInvoiceTypeCont( String typeLabel )
	{
		WebElement targetInvoice = null;
		jsWaiter.sleep(3000);
		WebElement invoiceTypesCont = wait.waitForElementPresent(invoiceTypesClass);
		List<WebElement> invoiceTypes = wait.waitForAllElementsPresent(invoiceTypesContainer, invoiceTypesCont);
		targetInvoice = element.selectElementByText(invoiceTypes, typeLabel);

		return targetInvoice;
	}

	/**
	 * locates and clicks on the add item button
	 */
	public void clickAddItemButton( )
	{
		WebElement addItemButton = element.getButtonByText("Add Item");
		click.clickElementCarefully(addItemButton);
	}

	/**
	 * locates and clicks on the catalogItem type
	 *
	 * @return new instance of the Ariba portal invoice add item page
	 */
	public AribaPortalInvoiceAddItemPage addCatalogItem( )
	{
		waitForUpdate();
		jsWaiter.sleep(3000);
		clickAddItemButton();
		waitForPageLoad();
		WebElement listCont = wait.waitForElementPresent(addItemListContLoc);
		jsWaiter.sleep(3000);
		WebElement catalog_item = wait.waitForElementEnabled(By.xpath("//*/form//*/a[contains(@class,'w-pmi-item')][normalize-space(text())='Catalog Item']"));
		click.clickElementCarefully(catalog_item);
		return initializePageObject(AribaPortalInvoiceAddItemPage.class);
	}

	/**
	 * locates and clicks on the add item details button
	 * triggers the drop down of details options
	 */
	public void clickAddItemDetailsButton( )
	{
		WebElement addItemButton = element.getButtonByText("Add Item Details");
		click.clickElementCarefully(addItemButton);
	}

	/**
	 * clicks on add item details button and select taxes from the dropdown list
	 */
	public void addItemTaxesDetail( )
	{
		clickAddItemDetailsButton();
		waitForPageLoad();
		WebElement listCont = wait.waitForElementPresent(detailsListCont);
		List<WebElement> itemTypes = wait.waitForAnyElementsDisplayed(By.tagName("a"), listCont);
		WebElement catalog_item = element.selectElementByText(itemTypes, "Taxes");
		click.clickElementCarefully(catalog_item);
	}

	/**
	 * selects desired line item (By checking the checkbox) from the added items
	 *
	 * @param itemIndex the index of the item to select
	 */
	public void selectItemByIndex( int itemIndex )
	{
		WebElement lineItemsTable = wait.waitForElementPresent(itemsTableContLoc);
		scroll.scrollElementIntoView(lineItemsTable);
		waitForPageLoad();
		int index = itemIndex - 1;
		List<WebElement> lineItemRows = wait.waitForAllElementsPresent(LINE_ITEM_ROW, lineItemsTable);
		WebElement targetLineItemRow = lineItemRows.get(index);
		WebElement checkBoxCont = wait.waitForElementPresent(checkBoxContLoc, targetLineItemRow);

		WebElement visibleCheckBox = wait.waitForElementPresent(visibleCheckBoxLoc, checkBoxCont);
		boolean isChecked = checkbox.isCheckboxChecked(visibleCheckBox);
		if ( !isChecked )
		{
			waitForPageLoad();
			WebElement clickableCheckBox = wait.waitForElementEnabled(clickableCheckBoxLoc, checkBoxCont);
			click.clickElement(clickableCheckBox);
		}
		else
		{
			VertexLogger.log("Already checked");
		}
	}

	/**
	 * selects a specific item and makes sure all others are not selected
	 *
	 * @param itemIndex index of item to be selected
	 */
	public void selectOnlyOneItemByIndex( int itemIndex ) throws InterruptedException
	{
		selectItemByIndex(itemIndex);
		waitForPageLoad();
	}

	/**
	 * unChecks all the items in the line items table
	 */
	public void deSelectAllItems( )
	{
		WebElement mainLineItemTableCheckBox = wait.waitForElementDisplayed(mainLineItemCheckBoxLoc);
		WebElement checkBoxCont = wait.waitForElementPresent(checkBoxContLoc, mainLineItemTableCheckBox);

		WebElement visibleCheckBox = wait.waitForElementPresent(visibleCheckBoxLoc, checkBoxCont);
		boolean isChecked = checkbox.isCheckboxChecked(visibleCheckBox);
		if ( isChecked )
		{
			WebElement clickableCheckBox = wait.waitForElementEnabled(clickableCheckBoxLoc, checkBoxCont);
			click.clickElement(clickableCheckBox);
			waitForPageLoad();
		}
		else
		{
			WebElement clickableCheckBox = wait.waitForElementEnabled(clickableCheckBoxLoc, checkBoxCont);
			click.clickElement(clickableCheckBox);
			try
			{
				Thread.sleep(2000);
			}
			catch ( InterruptedException e )
			{
				e.printStackTrace();
			}
			mainLineItemTableCheckBox = wait.waitForElementDisplayed(mainLineItemCheckBoxLoc);
			checkBoxCont = wait.waitForElementPresent(checkBoxContLoc, mainLineItemTableCheckBox);
			clickableCheckBox = wait.waitForElementEnabled(clickableCheckBoxLoc, checkBoxCont);
			click.clickElement(clickableCheckBox);
			waitForPageLoad();
		}
	}

	/**
	 * locates the submit button on the invoice creation page and clicks it
	 *
	 * @return new instance of the post invoice submission page class.
	 */
	public AribaPortalPostInvoiceSubmissionPage clickSubmitButton( )
	{
		WebElement submitButton = wait.waitForElementEnabled(submitButtonLoc);
		click.clickElement(submitButton);
		waitForPageLoad();
		AribaPortalPostInvoiceSubmissionPage summaryPage = initializePageObject(
			AribaPortalPostInvoiceSubmissionPage.class);
		return summaryPage;
	}

	/**
	 * locates the payable to supplier total and extracts the total from it
	 *
	 * @return total amount with tax
	 */
	public String getInvoiceTotal( )
	{
		String total;
		String fieldLabel = "Payable To Supplier:";
		total = getInvoiceFieldValue(fieldLabel);
		return total;
	}

	/**
	 * locates the tax field and gets the tax amount
	 *
	 * @return tax amount
	 */
	public String getInvoiceTaxAmount( )
	{
		waitForUpdate();
		String taxAmount;
		String fieldLabel = "Tax:";
		taxAmount = getInvoiceFieldValue(fieldLabel);
		return taxAmount;
	}

	/**
	 * retrieves the value from a given invoice label such as, tax, total ...etc
	 *
	 * @param label name of the field to get value from
	 *
	 * @return field value as a string
	 */
	public String getInvoiceFieldValue( String label )
	{
		WebElement fieldRow = null;
		WebElement taxSummaryContainer = wait.waitForElementPresent(taxSummaryContLoc);
		List<WebElement> summaryRows = wait.waitForAllElementsPresent(By.tagName("tr"), taxSummaryContainer);
		for ( WebElement row : summaryRows )
		{
			String rowLabel = row.getText();
			if ( rowLabel.contains(label) )
			{
				fieldRow = row;
				break;
			}
		}
		WebElement amountField = wait.waitForElementPresent(amountFieldLoc, fieldRow);
		String amount = amountField.getText();
		String cleansedAmount = amount.trim();
		return cleansedAmount;
	}

	/**
	 * clicks add item button and then selects non catalog option from the list
	 */
	public void addNonCatalogItem( )
	{
		waitForUpdate();
		clickAddItemButton();
		waitForPageLoad();
		WebElement listCont = wait.waitForElementPresent(addItemListContLoc);
		List<WebElement> itemTypes = wait.waitForAnyElementsDisplayed(By.tagName("a"), listCont);
		WebElement nonCatalog_item = element.selectElementByText(itemTypes, "Non-Catalog Item");
		click.clickElementCarefully(nonCatalog_item);
		waitForUpdate();
	}

	/**
	 * to enter the price of the added non catalog item
	 *
	 * @param itemIndex row number from the items list
	 */
	public void enterItemPrice( int itemIndex, String price )
	{
		WebElement lineItemsTable = wait.waitForElementPresent(itemsTableContLoc);
		List<WebElement> lineItemRows = wait.waitForAllElementsPresent(LINE_ITEM_ROW, lineItemsTable);
		int index = itemIndex - 1;
		WebElement targetLineItemRow = lineItemRows.get(index);
		WebElement priceFieldCont = wait.waitForElementPresent(priceFieldContLoc, targetLineItemRow);
		WebElement priceField = wait.waitForElementEnabled(By.tagName("input"), priceFieldCont);
		text.enterText(priceField, price);
		waitForPageLoad();
	}

	/**
	 * locates and clicks on the Supplier contact select button
	 * to select Contact info  for the selected supplier
	 *
	 * @author osabha
	 */
	public void clickSupplierContactSelectButton( ) throws InterruptedException
	{
		Thread.sleep(4000);
		String expectedConText = "Contact:";
		String expectedConInvoiceText = "Supplier Contact:";
		String expectedButtonText = "select";
		WebElement selectButtonCont = null;
		WebElement selectButton = null;
		WebElement listCon = wait.waitForElementPresent(selectButtonParentListContLoc);
		List<WebElement> selectButtonConts = wait.waitForAllElementsPresent(selectButtonListContLoc, listCon);
		selectButtonCont = element.selectElementByNestedLabel(selectButtonConts, By.tagName("label"), expectedConInvoiceText);
		List<WebElement> selectButtons = wait.waitForAnyElementsDisplayed(By.tagName("a"), selectButtonCont);
		selectButton = element.selectElementByText(selectButtons, expectedButtonText);

		if ( selectButton != null )
		{
			click.clickElement(selectButton);
		}
		wait.waitForAllElementsDisplayed(dialogHeaderLoc);
	}

	/**
	 * Selects the first PO from the PO selection dropdown
	 * for non-PO based invoices
	 * */
	public void selectFirstPO(){
		List<WebElement> dropDowns = driver.findElements(DROP_DOWN_ARROWS);
		click.clickElement(dropDowns.get(1));
		WebElement firstPO = wait.waitForElementDisplayed(PO_DROPDOWN_ITEM);
		click.clickElement(firstPO);
	}

	/**
	 * Selects the first PO from the PO selection dropdown
	 * for non-PO based invoices
	 * */
	public void selectPOByNumber(String PONumber){
		List<WebElement> dropDowns = driver.findElements(DROP_DOWN_ARROWS);
		click.clickElement(dropDowns.get(1));
		String POXpath = String.format("//*/span[@class='w-chSelections']/span[@chlname='ChooserList']/a[text()='%s']",PONumber);
		WebElement PO = wait.waitForElementDisplayed(By.xpath(POXpath));
		click.clickElement(PO);
	}

	/**
	 * Selects the first line item on an invoice for manipulation
	 * */
	public void selectFirstLineItem(){
		WebElement firstLine = wait.waitForElementDisplayed(FIRST_LINE_ITEM);
		click.clickElement(firstLine);
	}
}
