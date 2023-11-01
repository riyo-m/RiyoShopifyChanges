package com.vertex.quality.connectors.concur.pages.panelPages;

import com.vertex.quality.connectors.concur.pojos.ConcurInvoiceExpense;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * represents the invoice details page when selecting a preexisting invoice
 *
 * @author cgajes
 */
public class ConcurViewInvoiceDetailsPage extends ConcurInvoicePage
{

	protected final By actionsButtonLocator = By.className("menu_action");
	protected final By actionsMenuLocator = By.id("PaymentRequestPanel.RequestActionsMenu");
	protected final By actionOptionsLocator = By.className("x-menu-item");

	protected final By dialogWindowLocator = By.className("x-window-dlg");
	protected final By dialogWindowButtonClass = By.className("x-btn-text");

	protected final By itemNumberLocator = By.className("x-grid3-col-SequenceOrder");
	protected final By summaryExpenseTypeLocator = By.className("x-grid3-col-ReqItemRvw-PetCode");
	protected final By summaryLineDescriptionLocator = By.className("x-grid3-col-ReqItemRvw-Description");
	protected final By summaryQuantityLocator = By.className("x-grid3-col-ReqItemRvw-Quantity");
	protected final By summaryUnitPriceLocator = By.className("x-grid3-col-ReqItemRvw-UnitPrice");
	protected final By summaryTotal = By.className("x-grid3-col-ReqItemRvw-Total");
	protected final By summaryCalculatedTaxAmountLocator = By.className("x-grid3-col-ReqItemRvw-CalculatedTaxAmount");
	protected final By summaryCalculatedTaxRateLocator = By.className("x-grid3-col-ReqItemRvw-CalculatedTaxRate");

	public ConcurViewInvoiceDetailsPage( final WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Select Recall Invoice from the actions menu,
	 * making the values editable again
	 *
	 * @return enter details page
	 */
	public ConcurEnterInvoiceDetailsPage recallInvoice( )
	{
		WebElement actionsButton = wait.waitForElementEnabled(actionsButtonLocator);

		click.clickElementCarefully(actionsButton);

		wait.waitForElementDisplayed(actionsMenuLocator);

		WebElement recall = element.selectElementByText(actionOptionsLocator, "Recall Invoice");

		wait.waitForElementEnabled(recall);

		click.clickElementCarefully(recall);

		wait.waitForElementDisplayed(dialogWindowLocator);

		WebElement yesButton = element.selectElementByText(dialogWindowButtonClass, "Yes");

		wait.waitForElementEnabled(yesButton);

		click.clickElementCarefully(yesButton);

		waitForPageLoad();

		return initializePageObject(ConcurEnterInvoiceDetailsPage.class);
	}

	/**
	 * gets the details from an invoice item
	 * only does so for the first item
	 *
	 * @return
	 */
	public ConcurInvoiceExpense getItemDetailsForItemOne( )
	{

		String expenseType = itemizationSummaryGetExpenseType();
		String lineDescription = itemizationSummaryGetLineDescription();
		String quantity = itemizationSummaryGetQuantity();
		String unitPrice = itemizationSummaryGetUnitPrice();
		String total = itemizationSummaryGetTotal();
		String calculatedTaxAmount = itemizationSummaryGetTaxAmount();
		String calculatedTaxRate = itemizationSummaryGetTaxRate();

		ConcurInvoiceExpense itemDetails = ConcurInvoiceExpense
			.builder()
			.expenseTypeInput(expenseType)
			.lineDescriptionInput(lineDescription)
			.quantityInput(quantity)
			.unitPriceInput(unitPrice)
			.totalPayInput(total)
			.calculatedTaxAmountInput(calculatedTaxAmount)
			.calculatedTaxRateInput(calculatedTaxRate)
			.build();

		return itemDetails;
	}

	/**
	 * get the expense type from the itemization summary
	 *
	 * @return expense type found
	 */
	public String itemizationSummaryGetExpenseType( )
	{
		WebElement field = wait.waitForElementEnabled(summaryExpenseTypeLocator);

		String str = field.getText();

		return str;
	}

	/**
	 * get the line description from the itemization summary
	 *
	 * @return line description found
	 */
	public String itemizationSummaryGetLineDescription( )
	{
		WebElement field = wait.waitForElementEnabled(summaryLineDescriptionLocator);

		String str = field.getText();

		return str;
	}

	/**
	 * get the quantity from the itemization summary
	 *
	 * @return item quantity found
	 */
	public String itemizationSummaryGetQuantity( )
	{
		WebElement field = wait.waitForElementEnabled(summaryQuantityLocator);

		String amt = field.getText();

		return amt;
	}

	/**
	 * get the unit price from the itemization summary
	 *
	 * @return unit price found
	 */
	public String itemizationSummaryGetUnitPrice( )
	{
		WebElement field = wait.waitForElementEnabled(summaryUnitPriceLocator);

		String amt = field.getText();

		return amt;
	}

	/**
	 * get the total price from the itemization summary
	 *
	 * @return total found
	 */
	public String itemizationSummaryGetTotal( )
	{
		WebElement field = wait.waitForElementEnabled(summaryTotal);

		String amt = field.getText();

		return amt;
	}

	/**
	 * get the calculated tax amount from the itemization summary
	 *
	 * @return tax amount found
	 */
	public String itemizationSummaryGetTaxAmount( )
	{
		WebElement field = wait.waitForElementEnabled(summaryCalculatedTaxAmountLocator);

		String amt = field.getText();

		return amt;
	}

	/**
	 * get the calculated tax rate from the itemization summary
	 *
	 * @return tax rate found
	 */
	public String itemizationSummaryGetTaxRate( )
	{
		WebElement field = wait.waitForElementEnabled(summaryCalculatedTaxRateLocator);

		String amt = field.getText();

		return amt;
	}
}
