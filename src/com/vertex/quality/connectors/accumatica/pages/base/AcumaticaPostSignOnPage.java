package com.vertex.quality.connectors.accumatica.pages.base;

import com.vertex.quality.connectors.accumatica.components.AcumaticaHeaderPane;
import com.vertex.quality.connectors.accumatica.components.AcumaticaLeftNavPanel;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaCombobox;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaGlobalSubMenuOption;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaLeftPanelLink;
import com.vertex.quality.connectors.accumatica.interfaces.AcumaticaTableColumn;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * actions/methods used After Login into home page
 *
 * @author saidulu kodadala ssalisbury
 */
public abstract class AcumaticaPostSignOnPage extends AcumaticaBasePage
{
	protected By MAIN_FRAME = By.id("main");

	protected By comboboxSuggestionsDropdown = By.className("selDropTable");

	/**
	 * used in AcumaticaTableColumn entries to mean 'this entry doesn't have an editing overlay'
	 */
	protected static final String INVALID_END_OF_ID
		= "----------------------___________________________________eahfubnqgvuibrigv";

	protected String offsetHeightAttributeString = "offsetHeight";
	//this seems to be the offset in pixels between the top of this element and the bottom of this element
	protected String topOffsetAttributeString = "offsetTop";
	//this seems to be the offset in pixels between the top of this element's parent and the top of this element

	protected final By tableContainer = By.id("ctl00_phL_grid");

	protected final By tableHeaderContainer = By.className("GridHeaderCell");
	protected final By tableHeaderWrapper = By.tagName("thead");
	protected final By tableColumnHeader = By.className("GridHeader");
	protected final By tableColumnLabel = By.className("GridHeaderText");

	protected final By tableBodyContainer = By.className("GridContentCell");

	protected final By tableBodyRowNavigator = By.className("RowNavigator");

	protected final By tableDataBodyContainer = By.className("gridDataT");
	protected final By tableDataBodyWrapper = By.tagName("tbody");
	protected final By tableDataRow = By.tagName("tr");
	protected final By tableDataCell = By.className("GridRow");

	public AcumaticaHeaderPane header;
	public AcumaticaLeftNavPanel leftNavPanel;

	public AcumaticaPostSignOnPage( WebDriver driver )
	{
		super(driver);
		//TODO re-add version of initializePageObject which can take multiple arguments so that these don't have to call the constructor directly
		this.header = new AcumaticaHeaderPane(driver, this);
		this.leftNavPanel = new AcumaticaLeftNavPanel(driver, this);
	}

	/**
	 * accounts for a overlay which blocks access to the page while it's still loading,
	 * where that overlay affects all or almost all pages on acumatica
	 */
	@Override
	public void waitForPageLoad( )
	{
		super.waitForPageLoad();

		try
		{
			WebElement blockingOverlay = getPageLoadingOverlay();
			if ( blockingOverlay != null )
			{
				wait.waitForElementNotDisplayed(blockingOverlay);
			}
		}
		catch ( StaleElementReferenceException e )
		{
		}
	}

	//TODO add utility function somewhere- setTextField(By, str)- waitEnabled; text.enterTxt; txt.pressTb; waitPgLd; which various classes overload on the By for specific text fields?

	/**
	 * finds the overlay element that is overlaid on top of the rest of the DOM while the page is
	 * still loading
	 *
	 * @return the overlay element that is overlaid on top of the rest of the DOM while the page is
	 * still loading
	 * On failure, returns null
	 *
	 * @author ssalisbury
	 */
	protected WebElement getPageLoadingOverlay( )
	{
		WebElement overlay = null;
		final String distinctiveOverlayStyleText = "background-color: transparent";

		final List<WebElement> divs = driver.findElements(By.tagName("div"));

		//Collections.reverse(divs);//minor tweak to improve performance, because right now the overlay is one of the last divs in the DOM; this way, the loop will only get the style attribute of a few divs before finding the overlay instead of getting the style attribute from almost every div on the page
		WebElement div = null;
		for ( int i = divs.size() - 1 ; i >= 0 ; i-- )
		{
			div = divs.get(i);

			String divStyle = div.getAttribute("style");
			if ( divStyle != null && divStyle.contains(distinctiveOverlayStyleText) )
			{
				overlay = div;
				break;
			}
		}

		return overlay;
	}

	/**
	 * Switch to the main iframe of the page
	 */
	public void switchToMainFrame( )
	{
		waitForPageLoad();//TODO delete when it wouldn't destabilize things that haven't yet been looked at
		window.switchToFrame(MAIN_FRAME);
	}

	/**
	 * opens a given page in the acumatica website
	 *
	 * @param globalSubMenuTab the section of the site's navigation links wich the page's link is under
	 * @param leftPanelLink    the link to the desired page
	 * @param <M>              the class of the page being opened
	 *
	 * @return the page that was opened
	 */
	public <M extends AcumaticaPostSignOnPage> M openMenuPage( final AcumaticaGlobalSubMenuOption globalSubMenuTab,
		final AcumaticaLeftPanelLink leftPanelLink )
	{
		M newMenu = null;

		header.switchToSubMenuTab(globalSubMenuTab);
		newMenu = leftNavPanel.openLeftMenuLink(leftPanelLink);
		switchToMainFrame();

		return newMenu;
	}

	//TODO make a subclass with the table stuff?

	/**
	 * fetches the container for the whole table that's currently displayed on this page
	 *
	 * Note- this is used instead of directly referencing the locator tableContainer so that other pages with the same
	 * kind of table as this but with a different locator for the overall table can just override this getter function
	 * and use the other table utility functions without further modification
	 *
	 * @return the container for the whole table that's currently displayed on this page
	 */
	protected WebElement getTableContainer( )
	{
		WebElement table = wait.waitForElementDisplayed(tableContainer);
		return table;
	}

	/**
	 * finds the column with the given text above it in the table's header and returns the index of
	 * that column among the table's columns
	 *
	 * @param column the desired column
	 *
	 * @return the index of the desired column among the table's columns
	 * returns -1 if no column has the given name/label
	 */
	protected int getTableColumnIndex( final AcumaticaTableColumn column )
	{
		int columnIndex = -1;

		WebElement table = getTableContainer();
		WebElement headerContainerElem = wait.waitForElementPresent(tableHeaderContainer, table);
		WebElement headerWrapperElem = wait.waitForElementPresent(tableHeaderWrapper, headerContainerElem);

		List<WebElement> columnHeaders = wait.waitForAllElementsDisplayed(tableColumnHeader, headerWrapperElem);

		//TODO fix this so that it scrolls to the left or right when column is past edge of viewport
		//removes the lurking invisible elements in the row of column-header labels, which could screw up counting
		// the index of the column
		columnHeaders.removeIf(element -> !element.isDisplayed());

		WebElement columnHeaderElem = null;
		for ( int i = 0 ; i < columnHeaders.size() ; i++ )
		{
			columnHeaderElem = columnHeaders.get(i);

			WebElement columnLabelElem = null;
			List<WebElement> columnLabels = element.getWebElements(tableColumnLabel, columnHeaderElem);
			assert (columnLabels.size() <= 1);
			if ( columnLabels.size() == 1 )
			{
				columnLabelElem = columnLabels.get(0);
			}

			if ( columnLabelElem != null )
			{
				String columnName = columnLabelElem.getText();
				String columnLabelText = column.getLabel();
				if ( columnLabelText.equals(columnName) )
				{
					columnIndex = i;
					break;
				}
			}
		}

		return columnIndex;
	}

	/**
	 * retrieves the rows of the currently displayed table on this page
	 *
	 * @return the rows of the currently displayed table on this page
	 */
	protected List<WebElement> getTableRows( )
	{
		List<WebElement> tableRows = null;

		WebElement tableContainerElem = getTableContainer();
		WebElement tableDataBodyContainerElem = wait.waitForElementDisplayed(tableDataBodyContainer,
			tableContainerElem);
		WebElement tableDataBodyWrapperElem = wait.waitForElementPresent(tableDataBodyWrapper,
			tableDataBodyContainerElem);

		tableRows = wait.waitForAnyElementsDisplayed(tableDataRow, tableDataBodyWrapperElem);

		//TODO fix this so that it scrolls up or down when column is past edge of viewport
		//removes the lurking invisible 'new row' row element
		tableRows.removeIf(element -> !element.isDisplayed());

		return tableRows;
	}

	/**
	 * identifies which row the RowNavigator overlay is blocking
	 *
	 * @return the index of the row which is blocked by the RowNavigator overlay
	 */
	protected int getTableRowNavigatorIndex( )
	{
		int rowIndex = -1;

		WebElement table = getTableContainer();
		WebElement tableBodyContainerElem = wait.waitForElementPresent(tableBodyContainer, table);
		WebElement tableRowNavigatorElem = wait.waitForElementDisplayed(tableBodyRowNavigator, tableBodyContainerElem);

		String rowNavigatorOffsetHeightString = attribute.getElementAttribute(tableRowNavigatorElem,
			offsetHeightAttributeString);
		String rowNavigatorTopOffsetString = attribute.getElementAttribute(tableRowNavigatorElem,
			topOffsetAttributeString);
		assert (rowNavigatorOffsetHeightString != null && rowNavigatorTopOffsetString != null);

		int rowNavigatorOffsetHeight = Integer.parseInt(rowNavigatorOffsetHeightString);
		int rowNavigatorTopOffset = Integer.parseInt(rowNavigatorTopOffsetString);
		assert (rowNavigatorOffsetHeight > 0 && rowNavigatorTopOffset >= 0);

		rowIndex = rowNavigatorTopOffset / rowNavigatorOffsetHeight;

		return rowIndex;
	}

	/**
	 * finds a particular cell in the table
	 * Warning- you should re-fetch a table cell after clicking on it before trying to do anything more to it
	 *
	 * @param column   which column the cell is under
	 * @param rowIndex which row to find the cell in
	 *
	 * @return a particular cell in the table
	 * returns null if something goes wrong
	 */
	protected WebElement getTableCell( final AcumaticaTableColumn column, final int rowIndex )
	{
		WebElement targetCell = null;

		int rowNavigatorIndex = getTableRowNavigatorIndex();
		boolean isRowBlockedByRowNavigator = rowIndex == rowNavigatorIndex;

		By cellEditOverlay = column.getEditingOverlay();
		WebElement table = getTableContainer();

		if ( element.isElementDisplayed(cellEditOverlay, table) && isRowBlockedByRowNavigator )
		{
			targetCell = element.getWebElement(cellEditOverlay, table);
		}
		else
		{
			int columnIndex = getTableColumnIndex(column);
			WebElement dataCellWrapperElem = null;

			if ( isRowBlockedByRowNavigator )
			{
				WebElement tableBodyContainerElem = wait.waitForElementPresent(tableBodyContainer, table);
				WebElement tableRowNavigatorElem = wait.waitForElementDisplayed(tableBodyRowNavigator,
					tableBodyContainerElem);
				WebElement navigatorDataBodyWrapperElem = wait.waitForElementPresent(tableDataBodyWrapper,
					tableRowNavigatorElem);
				dataCellWrapperElem = navigatorDataBodyWrapperElem;
			}
			else
			{
				List<WebElement> rows = getTableRows();
				if ( rowIndex < rows.size() )
				{
					dataCellWrapperElem = rows.get(rowIndex);
				}
			}
			if ( dataCellWrapperElem != null )
			{
				List<WebElement> rowDataCells = wait.waitForAllElementsDisplayed(tableDataCell, dataCellWrapperElem);

				//removes the invisible elements lurking among the table cells in the row,
				// which could throw off the cell's index relative to its column's header label
				//TODO fix this so that it scrolls to the left or right when cell is past edge of viewport
				rowDataCells.removeIf(element -> !element.isDisplayed());

				if ( columnIndex < rowDataCells.size() )
				{
					targetCell = rowDataCells.get(columnIndex);
				}
			}
		}

		return targetCell;
	}

	//TODO add standard table access function- takes one column with row-identifying value, one column whose cell in that row is of interest

	/**
	 * Overloads {@link #setCombobox(AcumaticaCombobox, CharSequence, boolean)}
	 * to NOT skip interacting with the combobox if it already holds the desired text
	 */
	protected void setCombobox( AcumaticaCombobox combobox, CharSequence inputText )
	{
		setCombobox(combobox, inputText, false);
	}

	/**
	 * puts some text into a combobox text field
	 *
	 * @param combobox        the combobox text field whose contents will be edited
	 * @param inputText       the text which should be entered
	 * @param alwaysOverwrite whether the text should be written into the combobox even if the combobox is already
	 *                        filled with that same text
	 */
	protected void setCombobox( AcumaticaCombobox combobox, CharSequence inputText, boolean alwaysOverwrite )
	{
		By field = combobox.getTextField();

		WebElement textField = wait.waitForElementDisplayed(field);
		String currentText = text.getElementText(textField);
		if ( alwaysOverwrite || !currentText.startsWith(inputText.toString()) )
		{
			wait.waitForElementEnabled(field);
			text.selectAllAndInputText(field, inputText);

			List<WebElement> dropdowns = wait.waitForAnyElementsDisplayed(comboboxSuggestionsDropdown);

			for ( WebElement dropdown : dropdowns )
			{
				String controlId = attribute.getElementAttribute(dropdown, "controlid");
				if ( controlId.contains(combobox.getDistinctiveIdContents()) )
				{
					wait.waitForElementDisplayed(dropdown);
					break;
				}
			}
		}
		text.pressTab(field);
		waitForPageLoad();
	}
}