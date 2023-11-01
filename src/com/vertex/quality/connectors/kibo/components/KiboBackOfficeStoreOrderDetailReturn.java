package com.vertex.quality.connectors.kibo.components;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.kibo.enums.KiboProductNames;
import com.vertex.quality.connectors.kibo.pages.KiboReturnPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.List;

/**
 * This class represents the returns tab from the order detail section in the Maxine store in the
 * back office
 * contains all the methods necessary to interact with the returns tab
 *
 * @author osabha
 */
public class KiboBackOfficeStoreOrderDetailReturn extends VertexComponent
{
	public KiboBackOfficeStoreOrderDetailReturn( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}

	protected By tableSupContainerLoc = By.className("x-tree-panel");
	protected By productTagLoc = By.tagName("span");
	protected By tableContainerLoc = By.className("x-box-target");
	protected By tableSubContainerLoc = By.className("x-grid-header-ct-default");
	protected By columnTagLoc = By.tagName("span");
	protected By rowsContainerLoc = By.className("x-tree-view-default");
	protected By rowsContainerTag = By.tagName("tbody");
	protected By rowsTag = By.tagName("tr");
	protected By cellContainerTag = By.tagName("td");
	protected By cellTag = By.tagName("div");
	protected By createReturnButtonLoc = By.className("x-btn-button");
	protected By dropListContainer = By.className("x-boundlist-list-ct");
	protected By returnTypeFieldContainer = By.className("x-form-trigger-wrap-focus");
	protected By reportedIssueFieldContainer = By.className("x-form-trigger-wrap-focus");
	protected By optionFromListLoc = By.className("x-boundlist-item");
	protected By fieldArrowLoc = By.className("x-form-arrow-trigger");
	protected By returnsClass = By.cssSelector(".taco-form-nav > ul > li:nth-child(4)");
	protected By loadMaskLoc = By.className("x-mask");
	protected By qtyFieldLoc = By.cssSelector("input[name='quantity']");
	protected By returnLineCheckBoxLoc = By.className("x-grid-row-checker");
	protected By returnTypeDropListContainer = By.className("x-list-plain");

	/**
	 * Getter method used to locate the returns tab element in the order details section
	 *
	 * @return the return tab element
	 */
	protected WebElement getReturnsButton( )
	{
		WebElement returnsButton = null;

		ExpectedCondition<Boolean> returnsButtonLoadCondition = new ExpectedCondition<Boolean>()
		{
			public Boolean apply( WebDriver driver )
			{
				boolean isPresent = false;
				final String expectedText = "Returns";

				WebElement thisClass = element.selectElementByText(returnsClass, expectedText);
				if ( thisClass != null )
				{
					isPresent = true;
				}

				return isPresent;
			}
		};
		final String expectedText = "Returns";
		returnsButton = element.selectElementByText(returnsClass, expectedText);
		List<WebElement> masks = wait.waitForAllElementsPresent(loadMaskLoc);
		for ( WebElement mask : masks )
		{
			try
			{
				wait.waitForElementNotDisplayed(mask);
			}
			catch ( Exception e )
			{
				System.out.println("Load Mask isn't present");
			}
		}
		return returnsButton;
	}

	/**
	 * Uses the getter method to locate the returns button element and
	 * clicks on it
	 */
	public void clickReturnsButton( )
	{
		WebElement returnsButton = getReturnsButton();

		returnsButton.click();
	}

	/**
	 * method to get the index of a column by looping through the columns and find match to the
	 * column title
	 *
	 * @param columnTitle String
	 *
	 * @return int representing the index of the column regardless of the row number
	 */
	public int getColumnIndex( String columnTitle )
	{
		int columnIndex = 0;

		WebElement tableSupContainer = wait.waitForElementPresent(tableSupContainerLoc);
		WebElement tableSubContainer = wait.waitForElementPresent(tableSubContainerLoc, tableSupContainer);
		WebElement tableContainer = wait.waitForElementPresent(tableContainerLoc, tableSubContainer);
		List<WebElement> columnTags = wait.waitForAllElementsPresent(columnTagLoc, tableContainer);
		for ( int i = 0 ; i < columnTags.size() ; i++ )
		{
			WebElement currColumn = columnTags.get(i);
			String currColumnTitle = currColumn.getText();
			if ( columnTitle.equals(currColumnTitle) )
			{
				columnIndex = i;
				break;
			}
		}

		return columnIndex;
	}

	/**
	 * this method is to get the row index of the product to return
	 *
	 * @param productToReturn String
	 *
	 * @return int of the row index
	 */
	public int getRowIndex( String productToReturn )
	{
		int rowIndex = 0;

		WebElement rowsSupContainer = wait.waitForElementPresent(rowsContainerLoc);
		WebElement rowsContainer = wait.waitForElementPresent(rowsContainerTag, rowsSupContainer);
		List<WebElement> rows = wait.waitForAllElementsPresent(rowsTag, rowsContainer);
		for ( int i = 0 ; i < rows.size() ; i++ )
		{
			WebElement thisRow = rows
				.get(i)
				.findElement(productTagLoc);

			String thisRowText = thisRow.getText();
			if ( productToReturn.equals(thisRowText) )
			{
				rowIndex = i;
				break;
			}
		}
		return rowIndex;
	}

	/**
	 * getter method to locate the exact cell to interact with from the table of returnable items
	 *
	 * @param cellTitle
	 * @param productToReturn
	 *
	 * @return desired cell WebElement
	 */
	public WebElement getTableCell( String cellTitle, String productToReturn )
	{
		WebElement cell = null;
		int columnIndex = getColumnIndex(cellTitle);
		int rowIndex = getRowIndex(productToReturn);
		WebElement rowsSupContainer = wait.waitForElementPresent(rowsContainerLoc);
		WebElement rowsContainer = wait.waitForElementPresent(rowsContainerTag, rowsSupContainer);
		List<WebElement> rows = wait.waitForAllElementsPresent(rowsTag, rowsContainer);
		WebElement row = rows.get(rowIndex);
		List<WebElement> rowCells = wait.waitForAllElementsPresent(cellContainerTag, row);
		WebElement cellContainer = rowCells.get(columnIndex);
		cell = cellContainer.findElement(cellTag);

		return cell;
	}

	/**
	 * uses the get table cell method to locate the cell by the column title and the product to
	 * return and then interacts with the cell as needed
	 * in this case, a click on the cell is necessary to display the drop down list arrow , then
	 * clicking on the arrow will drop the list then we can select
	 * the issue to reports as passed in the params
	 *
	 * @param issueToReport   String to select the issue to report from the drop list
	 * @param productToReturn to make sure its selecting the right return line concerning the
	 *                        product we want to return
	 */
	public void enterReportedIssue( String issueToReport, KiboProductNames productToReturn )
	{
		final String cellTitle = "Reported Issue";
		String productName = productToReturn.value;
		WebElement cell = getTableCell(cellTitle, productName);
		cell.click();

		WebElement reportedIssueFieldArrowContainer = wait.waitForElementDisplayed(reportedIssueFieldContainer);
		WebElement reportedIssueArrow = wait.waitForElementPresent(fieldArrowLoc, reportedIssueFieldArrowContainer);
		reportedIssueArrow.click();
		WebElement noLongerWantedOptionContainer = wait.waitForElementDisplayed(dropListContainer);
		List<WebElement> listOptions = wait.waitForAllElementsPresent(optionFromListLoc, noLongerWantedOptionContainer);
		WebElement option = element.selectElementByText(listOptions, issueToReport);
		if ( option != null )
		{
			option.click();
		}
	}

	/**
	 * Getter method to locate the check box on the return line for each item
	 *
	 * @param productToReturn String telling the number of the line wanted
	 *
	 * @return returns the exact  check box from the line desired
	 */
	protected WebElement getReturnLineCheckBox( String productToReturn )
	{
		WebElement returnLineCheckBox = null;
		WebElement rowsSupContainer = wait.waitForElementPresent(rowsContainerLoc);
		WebElement rowsContainer = wait.waitForElementPresent(rowsContainerTag, rowsSupContainer);
		List<WebElement> rows = wait.waitForAllElementsPresent(rowsTag, rowsContainer);
		WebElement currRow = element.selectElementByNestedLabel(rows, productTagLoc, productToReturn);
		if ( currRow != null )
		{
			returnLineCheckBox = element.getWebElement(returnLineCheckBoxLoc, currRow);
		}

		return returnLineCheckBox;
	}

	/**
	 * uses the getter method to locate the checkbox and then clicks on it
	 *
	 * @param productToReturn String gets passed to the getter method to locate the correct checkbox
	 *                        from the desired line
	 */
	public void clickReturnLineCheckBox( KiboProductNames productToReturn )
	{
		String productName = productToReturn.value;
		WebElement returnLineCheckBox = getReturnLineCheckBox(productName);

		returnLineCheckBox.click();
	}

	/**
	 * Selects the return type for the item from the drop list
	 *
	 * @param returnType      to select the return type from the return list
	 * @param productToReturn gets passed to the getter method to locate the return type field on
	 *                        the desired return line
	 */
	public void enterReturnType( KiboProductNames productToReturn, String returnType )
	{
		final String errorMessage = "Unable to locate the list container";
		String cellTitle = "Type";
		String productName = productToReturn.value;
		WebElement returnTypeField = getTableCell(cellTitle, productName);

		returnTypeField.click();

		WebElement returnTypeFieldArrowContainer = wait.waitForElementDisplayed(returnTypeFieldContainer);
		WebElement returnTypeArrow = wait.waitForElementPresent(fieldArrowLoc, returnTypeFieldArrowContainer);
		returnTypeArrow.click();

		WebElement listContainer = null;
		String expectedText = "Replace\nRefund";

		listContainer = element.selectElementByText(returnTypeDropListContainer, expectedText);

		if ( listContainer != null )
		{
			List<WebElement> listOptions = wait.waitForAllElementsPresent(optionFromListLoc, listContainer);

			WebElement option = element.selectElementByText(listOptions, returnType);
			if ( option != null )
			{
				option.click();
			}
		}
		else
		{
			throw new RuntimeException(errorMessage);
		}
	}

	/**
	 * This method does both getting the field, clicking it and typing in the quantity to return
	 *
	 * @param productToReturn to locate which product return line we want to enter return quantity
	 *                        for
	 * @param qty             String  the number of items to return
	 */
	public void enterQtyToReturn( String qty, KiboProductNames productToReturn )
	{
		final String cellTitle = "Qty to Return";
		String productName = productToReturn.value;
		WebElement qtyFieldCon = getTableCell(cellTitle, productName);

		qtyFieldCon.click();

		WebElement qtyField = wait.waitForElementPresent(qtyFieldLoc);
		text.enterText(qtyField, qty);

		qtyField.click();
	}

	/**
	 * This is a getter method to locate the create return button to click on it
	 *
	 * @return the element to click on
	 */
	protected WebElement getCreateReturnButton( )
	{
		WebElement createReturnButton = null;
		final String expectedText = "Create Return";
		createReturnButton = element.selectElementByText(createReturnButtonLoc, expectedText);

		return createReturnButton;
	}

	/**
	 * Uses the getter method to locate the create return button element and then clicks on it
	 *
	 * @return new return page class
	 */
	public KiboReturnPage clickCreateReturn( )
	{
		WebElement createReturn = getCreateReturnButton();
		createReturn.click();
		List<WebElement> masks = wait.waitForAllElementsPresent(loadMaskLoc);
		for ( WebElement mask : masks )
		{
			try
			{
				wait.waitForElementNotDisplayed(mask);
			}
			catch ( Exception e )
			{
				System.out.println("Load Mask isn't present");
			}
		}

		return new KiboReturnPage(driver, parent);
	}
}