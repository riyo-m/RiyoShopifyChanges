package com.vertex.quality.connectors.ariba.supplier.components;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.ariba.supplier.components.base.AribaSupplierBaseComponent;
import com.vertex.quality.connectors.ariba.supplier.dialogs.AribaSupplierViewEditAddressDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * this class represents the line item details part of the page
 * and contains all the necessary methods to interact with it
 *
 * @author osabha
 */
public class AribaSupplierCreateInvoiceLineItemsDetails extends AribaSupplierBaseComponent
{
	public AribaSupplierCreateInvoiceLineItemsDetails( final WebDriver driver, final VertexPage parent )
	{
		super(driver, parent);
	}

	protected final By tableParentContLoc = By.className("awtWrapperTable");
	protected final By tableContLoc = By.className("tableBody");
	protected final By cellTitleLoc = By.cssSelector("th[class='tableHead']");
	protected final By tableRowClass = By.className("tableBodyClass");
	protected final By checkBoxLoc = By.className("w-chk-dsize");
	protected final By tableLeftFooterLoc = By.className("tableFooterLeft");
	protected final By includeButtonLoc = By.className("inv-IEFI-nt-incl-fully-invoiced");
	protected final By lineItemActionsListContLoc = By.id("InvoiceDetailItem.action.null");
	protected final By tableRowsLoc = By.cssSelector("tr[bgcolor='#ffffff']");
	protected final By primaryRowsElementsLoc = By.className("tableBodyClass");
	protected final By categoryFieldContLoc = By.className("nothing");
	protected final By listOptionsLoc = By.className("w-pmi-item");
	protected final By taxAmountFieldContLox = By.className("fdml-tax-rightCol");
	protected final By commandButtonsContLoc = By.className("w-stepnavbar-bottom-bar");
	protected final By updateButtonLoc = By.cssSelector("button[title='Update']");
	protected final String lineItemActionsButtonDisplayText = "Line Item Actions";
	protected final By shippingFieldLoc = By.cssSelector(".base-ncd-label.ANXLabel");
	/**
	 * locates the container for the line items table
	 *
	 * @return Web Element of the table container
	 */
	public WebElement getLineItemsTableContainer( )
	{
		WebElement tableParentCont = wait.waitForElementDisplayed(tableParentContLoc);
		WebElement tableContainer = wait.waitForElementPresent(tableContLoc, tableParentCont);
		return tableContainer;
	}

	/**
	 * sets the check box of a give line item row to checked
	 *
	 * @param itemRow WebElement of the item row
	 */
	public void selectItemCheckBox( final WebElement itemRow )
	{
		WebElement rowCheckBox = wait.waitForElementEnabled(checkBoxLoc, itemRow);
		checkbox.setCheckbox(rowCheckBox, true);
	}

	/**
	 * going by a given column title it gets the index of that title.
	 *
	 * @param cellTitle name of the column cell
	 *
	 * @return the index of the column cells.
	 */
	public int getColumnIndex( final String cellTitle )
	{
		int index = 0;
		WebElement tableContainer = getLineItemsTableContainer();
		List<WebElement> columnTitles = wait.waitForAnyElementsDisplayed(cellTitleLoc, tableContainer);

		for ( int i = 0 ; i < columnTitles.size() ; i++ )
		{
			WebElement thisColumn = columnTitles.get(i);
			String columnTitle = text.getElementText(thisColumn);
			if ( cellTitle.equals(columnTitle) )
			{
				index = i;
				break;
			}
		}

		return index;
	}

	/**
	 * for a given table row, it determines if it is the target row or not
	 * by checking if the description of the item in that row matches the target item
	 *
	 * @param itemName    name of the item we need it's row
	 * @param columnIndex index of the description column
	 * @param row         the webElement of the row the target item is in
	 *
	 * @return true if the passed element is the target row
	 */
	public boolean isTargetRow( final String itemName, final int columnIndex, final WebElement row )
	{
		boolean isMatch = false;

		List<WebElement> rowCells = wait.waitForAnyElementsDisplayed(By.tagName("td"), row);
		WebElement rowCell = rowCells.get(columnIndex);
		String rawCellText = text.getElementText(rowCell);
		String cleanCellText = rawCellText.trim();
		if ( itemName.equals(cleanCellText) )
		{
			isMatch = true;
		}
		return isMatch;
	}

	/**
	 * locates and clicks on the include button for a given row
	 *
	 * @param row WebElement of the row we want to click the button on
	 */
	public void clickIncludeItem( final WebElement row )
	{
		WebElement includeButton = wait.waitForElementEnabled(includeButtonLoc, row);
		click.clickElement(includeButton);
		waitForPageLoad();
	}

	/**
	 * does the process of setting the tax category and amount
	 * for a given item
	 *
	 * @param taxAmount   tax amount to add
	 * @param itemName    name of the item to perform on
	 * @param taxCategory tax category
	 */
	public void setItemTaxDetails( final String itemName, final String taxAmount, final String taxCategory )
	{
		int itemIndex = getItemIndex(itemName);
		WebElement itemRow = getItemRow(itemIndex);

		selectItemCheckBox(itemRow);

		WebElement lineItemActionsButton = findLineItemActionsButton();
		click.clickElementCarefully(lineItemActionsButton);

		selectFromList("Tax", lineItemActionsListContLoc);

		WebElement itemDetailsContainer = findItemDetailsContainer(itemIndex);
		WebElement categoryField = findCategoryField(itemDetailsContainer);
		click.clickElement(categoryField);

		String listId = String.format("taxcreator%s", itemIndex);
		By listContLoc = By.id(listId);

		selectFromList(taxCategory, listContLoc);
		enterTaxAmount(taxAmount, itemIndex);

		updateTaxes();
	}

	/**
	 * locates and clicks the update button to update added taxes.
	 */
	public void updateTaxes( )
	{
		WebElement buttonCont = wait.waitForElementDisplayed(commandButtonsContLoc);
		WebElement updateButton = wait.waitForElementEnabled(updateButtonLoc, buttonCont);
		click.clickElement(updateButton);
		waitForPageLoad();
	}

	/**
	 * locates the table row for a given item
	 *
	 * @param itemIndex one based index of the target item
	 *
	 * @return WebElement of the table Row
	 */
	public WebElement getItemRow( final int itemIndex )
	{
		WebElement itemRow;
		int listIndex = itemIndex - 1;

		waitForTaxDetailsTableVisible();

		WebElement tableContainer = getLineItemsTableContainer();
		List<WebElement> tableRows = getPrimaryTableRows(tableContainer);

		itemRow = tableRows.get(listIndex);

		return itemRow;
	}

	/**
	 * gets the item row index based on the item name
	 *
	 * @param itemName name of the item we are locating the row for.
	 *
	 * @return one based index of the row.
	 */
	public int getItemIndex( final String itemName )
	{
		int itemIndex = -1;
		waitForTaxDetailsTableVisible();

		WebElement tableContainer = getLineItemsTableContainer();
		List<WebElement> tableRows = getPrimaryTableRows(tableContainer);
		int descriptionColumnIndex = getColumnIndex("Description");

		int currentIndex = 1;

		for ( WebElement row : tableRows )
		{
			boolean isTargetRow = isTargetRow(itemName, descriptionColumnIndex, row);

			if ( isTargetRow )
			{
				itemIndex = currentIndex;
				break;
			}
			currentIndex++;
		}

		return itemIndex;
	}

	/**
	 * waits until the whole tax details table is loaded and visible
	 */
	protected void waitForTaxDetailsTableVisible( )
	{
		WebElement tableFooter = wait.waitForElementPresent(tableLeftFooterLoc);
		scroll.scrollElementIntoView(tableFooter);
		wait.waitForElementDisplayed(tableLeftFooterLoc);
		waitForPageLoad();
	}

	/**
	 * locates all the primary table rows
	 *
	 * @param tableContainer table container
	 *
	 * @return list of WebElements of the table primary rows.
	 */
	public List<WebElement> getPrimaryTableRows( final WebElement tableContainer )
	{
		List<WebElement> allTableRows = null;
		List<WebElement> tableBodyRows = new ArrayList<>();

		allTableRows = wait.waitForAllElementsPresent(tableRowsLoc, tableContainer);

		for ( WebElement row : allTableRows )
		{
			List<WebElement> tableBodyCells = element.getWebElements(primaryRowsElementsLoc, row);

			if ( tableBodyCells.size() > 0 )
			{
				tableBodyRows.add(row);
			}
		}

		wait.waitForAnyElementsDisplayed(tableRowClass, tableContainer);

		return tableBodyRows;
	}

	/**
	 * locates the line item actions button
	 *
	 * @return WebElement of the button
	 */
	public WebElement findLineItemActionsButton( )
	{
		WebElement lineItemActionButton;
		List<WebElement> buttonsList = wait.waitForAllElementsEnabled(By.tagName("button"));
		lineItemActionButton = element.selectElementByText(buttonsList, lineItemActionsButtonDisplayText);

		scroll.scrollElementIntoView(lineItemActionButton);
		waitForPageLoad();

		return lineItemActionButton;
	}

	/**
	 * selects a certain option from a given list
	 *
	 * @param optionText       option to select
	 * @param listContainerLoc locator of the list container
	 */
	public void selectFromList( final String optionText, final By listContainerLoc )
	{
		WebElement optionButton;
		WebElement listContainer = wait.waitForElementDisplayed(listContainerLoc);
		List<WebElement> optionsList = wait.waitForAllElementsEnabled(listOptionsLoc, listContainer);

		optionButton = element.selectElementByText(optionsList, optionText);
		click.clickElement(optionButton);
		waitForPageLoad();
	}

	/**
	 * locates the category field inside item details container in a specific item row
	 *
	 * @param itemContainer container of item details
	 *
	 * @return WebElement of the field
	 */
	public WebElement findCategoryField( final WebElement itemContainer )
	{
		WebElement categoryField;
		WebElement fieldContainer = wait.waitForElementPresent(categoryFieldContLoc, itemContainer);
		categoryField = wait.waitForElementEnabled(By.tagName("input"), fieldContainer);
		return categoryField;
	}

	/**
	 * locates the container of the item tax and shipping details for a given item
	 *
	 * @param oneBasedItemIndex index of the item we need the details container of
	 *
	 * @return details container as a WebElement
	 */
	public WebElement findItemDetailsContainer( final int oneBasedItemIndex )
	{
		int zeroBasedIndex = oneBasedItemIndex - 1;
		WebElement container = null;
		WebElement tableContainer = getLineItemsTableContainer();
		List<WebElement> allTableRows = null;
		List<WebElement> detailsRows = new ArrayList<>();

		allTableRows = wait.waitForAllElementsPresent(tableRowsLoc, tableContainer);

		for ( WebElement row : allTableRows )
		{
			List<WebElement> tableBodyCells = element.getWebElements(primaryRowsElementsLoc, row);

			if ( tableBodyCells.size() == 0 )
			{
				detailsRows.add(row);
			}
		}

		if ( detailsRows.size() > 0 )
		{
			container = detailsRows.get(zeroBasedIndex);
		}

		return container;
	}

	/**
	 * locates the tax amount field and then enters the passed tax amount into it
	 *
	 * @param taxAmount         tax amount to enter into field
	 * @param oneBasedItemIndex index of the item we are adding tax details to
	 */
	public void enterTaxAmount( final String taxAmount, int oneBasedItemIndex )
	{
		WebElement rowCont = findItemDetailsContainer(oneBasedItemIndex);
		WebElement taxAmountFieldCont = wait.waitForElementPresent(taxAmountFieldContLox, rowCont);
		List<WebElement> inputFields = wait.waitForAllElementsEnabled(By.tagName("input"), taxAmountFieldCont);
		// in the next line I specify the input field I need by passing it's index, it's the third field after TaxableAmount and Rate fields
		WebElement taxAmountField = inputFields.get(2);
		text.enterText(taxAmountField, taxAmount);
	}

	/**
	 * locates the view address button in the item details container
	 *
	 * @param itemName name of the item we want to change the details for
	 *
	 * @return the webElement of the view edit addresses button
	 */
	public WebElement getViewEditAddressButton( String itemName )
	{
		WebElement editAddressButton;
		int itemIndex = getItemIndex(itemName);
		WebElement itemDetailsContainer = findItemDetailsContainer(itemIndex);
		List<WebElement> aTags = wait.waitForAnyElementsDisplayed(By.tagName("a"), itemDetailsContainer);
		editAddressButton = element.selectElementByText(aTags, "View/Edit Addresses");

		return editAddressButton;
	}

	/**
	 * locates and clicks on the view edit addresses button
	 *
	 * @param itemName name of the item we want to change address details for
	 *
	 * @return new instance of the view edit address dialog
	 */
	public AribaSupplierViewEditAddressDialog clickViewEditAddress( String itemName )
	{
		WebElement editAddressButton = getViewEditAddressButton(itemName);
		click.clickElementCarefully(editAddressButton);
		AribaSupplierViewEditAddressDialog editAddressDialog = initializePageObject(
			AribaSupplierViewEditAddressDialog.class);
		return editAddressDialog;
	}

	public void setItemShippingAmount( String amount, String itemName )
	{

		int itemIndex = getItemIndex(itemName);
		WebElement itemRow = getItemRow(itemIndex);

		selectItemCheckBox(itemRow);

		WebElement itemDetailsContainer = findItemDetailsContainer(itemIndex);
		WebElement shippingAmountFieldCont = getFieldCont("Shipping Amount:", shippingFieldLoc, itemDetailsContainer);
		WebElement shippingAmountField = wait.waitForElementEnabled(By.tagName("input"), shippingAmountFieldCont);
		text.enterText(shippingAmountField, amount);
	}
}
