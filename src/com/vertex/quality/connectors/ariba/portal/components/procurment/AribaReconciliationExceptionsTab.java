package com.vertex.quality.connectors.ariba.portal.components.procurment;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.ariba.portal.components.base.AribaPortalComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * represents the exceptions tab in the invoice reconciliation page
 * contains all the methods necessary to interact with this component.
 *
 * @author osabha
 */
public class AribaReconciliationExceptionsTab extends AribaPortalComponent
{
	public AribaReconciliationExceptionsTab( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}

	protected final By allExceptionsTableCont = By.id("collabToolExcelptionViewId");
	protected final By exceptionTableLoc = By.className("a-invexptns-tbl");
	protected final By exceptionTableHeaderLoc = By.className("a-invexcp-header");
	protected final By actionsListContLoc = By.cssSelector(".awmenu.w-pm-menu");
	protected final By actionsListParentContLoc = By.id("BPR_Body_Inner");

	/**
	 * locates all of the entries in the exceptions table
	 *
	 * @return a list of the web elements for all of the entries in the exceptions table.
	 */
	public List<WebElement> getAllExceptionsLines( )
	{
		WebElement exceptionsContainer = wait.waitForElementDisplayed(allExceptionsTableCont);
		List<WebElement> allVariances = wait.waitForAllElementsDisplayed(exceptionTableLoc, exceptionsContainer);
		return allVariances;
	}

	/**
	 * locates a specific exception in the exceptions table
	 *
	 * @param exceptionTitle title of the specific exception we are looking for
	 *
	 * @return Web Element of the target exception in the exceptions table
	 */
	public WebElement getExceptionTable( final String exceptionTitle )
	{
		List<WebElement> allExceptions = getAllExceptionsLines();
		WebElement targetException = null;

		for ( WebElement exception : allExceptions )
		{
			WebElement exceptionHeader = wait.waitForElementDisplayed(exceptionTableHeaderLoc, exception);
			String exceptionHeaderText = text.getElementText(exceptionHeader);

			if ( exceptionHeaderText != null && exceptionHeaderText.contains(exceptionTitle) )
			{
				targetException = exception;
				break;
			}
			else
			{
				String message = "No header text found for this exception";
				VertexLogger.log(message, VertexLogLevel.DEBUG);
			}
		}
		return targetException;
	}

	/**
	 * clicks on the Action button for a given exception and then selects the desired action from the list
	 *
	 * @param exceptionTable exceptions table webElement
	 * @param action         desired action to click on form the actions list.
	 */
	public void takeActionOnExceptions( final WebElement exceptionTable, final String action )
	{

		List<WebElement> actionButtons = wait.waitForAllElementsEnabled(By.tagName("button"), exceptionTable);
		for ( WebElement actionButton : actionButtons )
		{
			click.clickElement(actionButton);
			WebElement actionsListParentCont = wait.waitForElementPresent(actionsListParentContLoc);
			//			WebElement actionsListCont = wait.waitForElementPresent(actionsListContLoc, exceptionTable);
			WebElement actionsListCont = wait.waitForElementPresent(actionsListContLoc, actionsListParentCont);
			List<WebElement> actionsList = wait.waitForAllElementsPresent(By.tagName("a"), actionsListCont);
			element.selectElementByText(actionsList, action);
		}
	}

	/**
	 * locates all the tax exceptions tables if any are there
	 *
	 * @return returns true if it found any tax variance exceptions
	 */
	public boolean isThereAnyTaxVariance( )
	{
		boolean isThereException = false;
		List<WebElement> taxExceptions = getAllTaxExceptions();
		if ( taxExceptions.size() > 0 )
		{
			isThereException = true;
		}
		return isThereException;
	}

	/**
	 * compares between the invoice tax amount and expected tax amount
	 *
	 * @param exceptionTable single exception table to check for variance in.
	 *
	 * @return true if invoice taxes are more than the expected taxes.
	 */
	public boolean isOverExpected( final WebElement exceptionTable )
	{
		boolean isOver = false;

		return isOver;
	}

	/**
	 * locates all the tax variance exceptions in the exceptions tables
	 *
	 * @return all the tax exceptions found in a list
	 */
	public List<WebElement> getAllTaxExceptions( )
	{
		List<WebElement> allExceptions = getAllExceptionsLines();
		List<WebElement> taxExceptions = new ArrayList<>();
		for ( WebElement exception : allExceptions )
		{
			WebElement exceptionHeader = wait.waitForElementDisplayed(exceptionTableHeaderLoc, exception);
			String exceptionHeaderText = text.getElementText(exceptionHeader);
			if ( exceptionHeaderText != null && exceptionHeaderText.contains("Tax") )
			{
				taxExceptions.add(exception);
			}
		}
		return taxExceptions;
	}

	/**
	 * finds if there are any quantity variances and accepts the vendor amount for each.
	 */
	public void acceptAllQuantityVariances( )
	{
		String actionOnException = "Accept Invoice Quantity";
		WebElement quantityExceptionsTable = getExceptionTable("Quantity");
		if ( quantityExceptionsTable != null )
		{
			takeActionOnExceptions(quantityExceptionsTable, actionOnException);
		}
		else
		{
			VertexLogger.log("there was no quantity variances in this invoice");
		}
	}

	/**
	 * checks if there is a tax calculation failed exception and handles it.
	 */
	public void handleTaxCalculationFailedException( )
	{
		String actionOnException = "Defer to someone else";
		WebElement taxCalculationFieldTable = getExceptionTable("Tax Calculation");
		if ( taxCalculationFieldTable != null )
		{
			takeActionOnExceptions(taxCalculationFieldTable, actionOnException);
		}
		else
		{
			VertexLogger.log("no tax calculation field exception appeared");
		}
	}

	public void handleAllPotentialNonTaxExceptions( )
	{

	}
}
