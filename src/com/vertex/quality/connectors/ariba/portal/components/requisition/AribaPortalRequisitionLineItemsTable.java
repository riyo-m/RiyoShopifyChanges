package com.vertex.quality.connectors.ariba.portal.components.requisition;

import com.vertex.quality.common.enums.PageScrollDestination;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.ariba.portal.components.base.AribaPortalComponent;
import com.vertex.quality.connectors.ariba.portal.dialogs.requisition.AribaPortalRequisitionLineItemTaxesDialog;
import com.vertex.quality.connectors.ariba.portal.enums.AribaPortalLineItemActions;
import com.vertex.quality.connectors.ariba.portal.pages.requisition.AribaPortalRequisitionLineItemDetailsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Component representing the table of line items on a requisition
 *
 * @author dgorecki
 * and imported legacy code from the person who started automating ariba in python
 */
public class AribaPortalRequisitionLineItemsTable extends AribaPortalComponent
{
	protected final By ACTIONS_MENU_LIST = By.id("editActions");
	protected final By LINE_ITEM_TABLE = By.className("catalogTable");
	protected final By LINE_ITEM_VISIBLE_CHECKBOX = By.className("w-chk-native");
	protected final By LINE_ITEM_ROW = By.cssSelector("tr[dr='1']");
	protected final By LINE_ITEM_TABLE_HEADER_CELL = By.className("tableHead");
	protected final By LINE_ITEM_TABLE_CELL = By.className("w-tbl-cell");
	protected final By checkBoxContLoc = By.className("w-chk-container");
	protected final By clickableCheckBoxLoc = By.className("w-chk-dsize");

	public AribaPortalRequisitionLineItemsTable( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}

	/**
	 * Method to specify an action to select once a specific line item has been
	 * selected
	 *
	 * @param actionToSelect enum representing the specific action option to select for the
	 *                       line item
	 *
	 * @author ssalisbury dgorecki osabha
	 */
	public void selectActionForLineItem( final AribaPortalLineItemActions actionToSelect )
	{
		String actionLabel = actionToSelect.getActionOptionLabel();

		WebElement actionsButton = element.getButtonByText("Actions");
		wait.waitForElementDisplayed(actionsButton);
		scroll.scrollElementIntoView(actionsButton, PageScrollDestination.TOP);

		try
		{
			Thread.sleep(3000);
		}
		catch ( InterruptedException e )
		{
			e.printStackTrace();
		}

		click.clickElement(actionsButton);

		waitForPageLoad();

		WebElement actionsList = wait.waitForElementDisplayed(ACTIONS_MENU_LIST);
		waitForPageLoad();

		WebElement menuItem = wait.waitForElementDisplayed(By.linkText(actionLabel), actionsList);
		waitForPageLoad();

		//Dave G. - I've spent the better part of a day ighting this, its not worth it
		try
		{
			Thread.sleep(4000);
		}
		catch ( InterruptedException e )
		{
			e.printStackTrace();
		}

		click.clickElement(menuItem);
	}

	/**
	 * Select the checkbox for a specified row in the requisition line items table.
	 * Note that Ariba checkboxes have two parts, the visible one, which isn't
	 * actually a real checkbox, and a hidden one. You need to check the hidden one
	 * to see if it's checked but click the visible one to actual check it
	 *
	 * @param rowNum the 1-based index of the row to select
	 *
	 * @author ssalisbury dgorecki osabha
	 */
	public void selectRequisitionLineItem( final int rowNum )
	{
		WebElement searchListTable = wait.waitForElementDisplayed(LINE_ITEM_TABLE);
		List<WebElement> checkBoxesConts = wait.waitForAllElementsPresent(checkBoxContLoc, searchListTable);
		List<WebElement> VisibleCheckboxes = wait.waitForAllElementsPresent(LINE_ITEM_VISIBLE_CHECKBOX,
			searchListTable);

		WebElement checkboxElem = checkBoxesConts.get(rowNum);
		WebElement visibleCheckBox = VisibleCheckboxes.get(rowNum);
		boolean isChecked = checkbox.isCheckboxChecked(visibleCheckBox);

		if ( !isChecked )
		{
			WebElement clickableCheckBox = wait.waitForElementEnabled(clickableCheckBoxLoc, checkboxElem);
			click.clickElement(clickableCheckBox);
			waitForPageLoad();
		}
	}

	/**
	 * Selects a requisition line item and then navigates to the Edit Details page
	 * for that line item
	 *
	 * @param rowNum the 1-based index of the line item row
	 *
	 * @return the resulting line item edit details page
	 *
	 * @author ssalisbury dgorecki osabha
	 */
	public AribaPortalRequisitionLineItemDetailsPage editLineItemDetails( final int rowNum )
	{
		selectRequisitionLineItem(rowNum);
		selectActionForLineItem(AribaPortalLineItemActions.EDIT_DETAILS);

		AribaPortalRequisitionLineItemDetailsPage detailsPage = initializePageObject(
			AribaPortalRequisitionLineItemDetailsPage.class);

		return detailsPage;
	}

	/**
	 * Selects a requisition line item and then navigates to the Edit Charges dialog
	 * for that line item
	 *
	 * @param rowNum the 1-based index of the line item row
	 *
	 * @return the resulting line item edit details page
	 *
	 * @author osabha
	 */
	public AribaPortalRequisitionLineItemDetailsPage editLineItemShipping( final int rowNum )
	{
		selectRequisitionLineItem(rowNum);
		selectActionForLineItem(AribaPortalLineItemActions.EDIT_CHARGES);

		AribaPortalRequisitionLineItemDetailsPage detailsPage = initializePageObject(
			AribaPortalRequisitionLineItemDetailsPage.class);

		return detailsPage;
	}




	/**
	 * Retrieves a string representation of the taxes for a requisition line item
	 *
	 * @param lineItemNum the line item to retrieve taxes for
	 *
	 * @return the taxes for an individual line item in Sting format
	 *
	 * @author ssalisbury dgorecki
	 */
	public String getRequisitionLineItemTaxes( final int lineItemNum )
	{
		//TODO enhance to deal with currencies

		int actualLineItemNum = lineItemNum;
		WebElement taxCell = getLineItemTaxCell(actualLineItemNum);
		scroll.scrollElementIntoView(taxCell);
		String lineItemTaxes = text.getElementText(taxCell);
		return lineItemTaxes;
	}

	/**
	 * Helper method to retrieve the webElement representing the table cell that
	 * contains the taxes link
	 *
	 * @param lineItemNum the 1-based indexed line item
	 *
	 * @return the webelement of the taxes link table cell
	 *
	 * @author ssalisbury dgorecki osabha
	 */
	private WebElement getLineItemTaxCell( final int lineItemNum )
	{
		String taxColumnName = "Taxes";
		int taxColumnIndex = getColumnIndex(taxColumnName);

		WebElement lineItem = getLineItemRowElement(lineItemNum);
		List<WebElement> lineItemCell = wait.waitForAllElementsDisplayed(LINE_ITEM_TABLE_CELL, lineItem);

		WebElement taxCell = lineItemCell.get(taxColumnIndex);
		return taxCell;
	}

	/**
	 * Helper method to get the webelement for a line item row
	 *
	 * @param lineItemNum the number of the line item you want to retrieve
	 *
	 * @return the webelement for the specified line item row
	 *
	 * @author ssalisbury dgorecki osabha
	 */
	private WebElement getLineItemRowElement( final int lineItemNum )
	{
		int index = lineItemNum - 1;

		WebElement itemsTable = wait.waitForElementDisplayed(LINE_ITEM_TABLE);
		List<WebElement> lineItemRows = wait.waitForAllElementsDisplayed(LINE_ITEM_ROW, itemsTable);

		WebElement targetLineItem = lineItemRows.get(index);
		return targetLineItem;
	}

	/**
	 * Internal method to determine the index of the column to retrieve a value from
	 *
	 * @param targetColumn the string label/name of the column
	 *
	 * @return an int indicating the index of the located column, returns -1 if
	 * column not found
	 *
	 * @author ssalisbury dgorecki osabha
	 */
	private int getColumnIndex( final String targetColumn )
	{
		WebElement itemsTable = wait.waitForElementDisplayed(LINE_ITEM_TABLE);
		List<WebElement> columns = wait.waitForAllElementsDisplayed(LINE_ITEM_TABLE_HEADER_CELL, itemsTable);

		int numColumns = columns.size();
		int targetColumnIndex = -1;

		for ( int i = 0 ; i < numColumns && targetColumnIndex == -1 ; i++ )
		{
			WebElement column = columns.get(i);
			String columnText = column.getText();
			if ( targetColumn.equals(columnText) )
			{
				targetColumnIndex = i;
			}
		}

		return targetColumnIndex;
	}

	/**
	 * Opens the taxes dialog on a specified line item (1-based index) in a
	 * requisition
	 *
	 * @param lineItemNum the 1-based index of the specific line item to view the taxes for
	 *
	 * @return the resulting taxes dialog
	 *
	 * @author ssalisbury dgorecki osabha
	 */
	public AribaPortalRequisitionLineItemTaxesDialog openRequisitionLineItemTaxes( final int lineItemNum )
	{
		WebElement taxCell = getLineItemTaxCell(lineItemNum);
		WebElement taxCellLink = wait.waitForElementDisplayed(By.tagName("a"), taxCell);
		click.clickElement(taxCellLink);

		return initializePageObject(AribaPortalRequisitionLineItemTaxesDialog.class, parent);
	}

	/**
	 * select all items and edit details for them
	 *
	 * @return new instance of the line item details page.
	 */
	public AribaPortalRequisitionLineItemDetailsPage editAllLineItemDetails( )
	{
		shouldSelectAllRequisitionLineItems(true);
		selectActionForLineItem(AribaPortalLineItemActions.EDIT_DETAILS);

		AribaPortalRequisitionLineItemDetailsPage detailsPage = initializePageObject(
			AribaPortalRequisitionLineItemDetailsPage.class);

		return detailsPage;
	}

	/**
	 * DeSelect the checkbox for all line items .
	 * to see if it's checked but click the visible one to actual check it
	 *
	 * @param select true of false to select or deselect all line items
	 *
	 * @author ssalisbury dgorecki osabha
	 */
	public void shouldSelectAllRequisitionLineItems( boolean select )
	{
		WebElement searchListTable = wait.waitForElementDisplayed(LINE_ITEM_TABLE);
		WebElement checkBoxCont = wait.waitForElementPresent(checkBoxContLoc, searchListTable);
		WebElement VisibleCheckbox = wait.waitForElementDisplayed(LINE_ITEM_VISIBLE_CHECKBOX, checkBoxCont);

		boolean isChecked = checkbox.isCheckboxChecked(VisibleCheckbox);

		if ( !isChecked == select )
		{
			WebElement clickableCheckBox = wait.waitForElementEnabled(clickableCheckBoxLoc, checkBoxCont);
			click.clickElement(clickableCheckBox);
			waitForPageLoad();
		}
	}
}
