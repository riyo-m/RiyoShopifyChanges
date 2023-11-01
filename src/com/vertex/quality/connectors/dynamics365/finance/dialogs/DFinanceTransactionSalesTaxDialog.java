package com.vertex.quality.connectors.dynamics365.finance.dialogs;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.dynamics365.finance.dialogs.base.DFinanceBaseDialog;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceAllSalesOrdersPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Collections;
import java.util.List;

/**
 * a large dialog pane on {@link com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceAllSalesOrdersPage}
 * which displays the sales tax that the Vertex connector calculated for some sales orders (newly created ones?)
 * TODO this needs clarification on which sales orders the tax is being calculated for
 *
 * @author ssalisbury
 */
public class DFinanceTransactionSalesTaxDialog extends DFinanceBaseDialog
{
	protected final DFinanceAllSalesOrdersPage salesOrdersParent;

	protected final By TOTAL_CALCULATED_SALES_TAX_AMOUNT = By.cssSelector("input[id*='TaxAmountCurTotal'][type='text']");
	protected final By TOTAL_CALCULATED_2ndLine_SALES_TAX_AMOUNT = By.xpath("//input[@aria-label='Actual sales tax amount'][@tabindex='-1']");
	protected final By TOTAL_ACTUAL_SALES_TAX_AMOUNT = By.cssSelector("input[id*='TaxRegulationTotal'][type='text']");
	protected final By TOTAL_SALES_INVOICE_AMOUNT = By.cssSelector("input[id*='CustInvoiceJour_InvoiceAmount_Grid'][type='text']");
	protected By OK_BUTTON = By.xpath("//span[contains(.,'OK')]");
	protected final By CLICK_OK = By.name("OKButton");
	protected final By TOTAL_CALCULATED_State_SALES_TAX_AMOUNT =  By.xpath("(//input[@aria-label='Actual sales tax amount'][contains(@tabindex,'')])[1]");
	protected final By ACTUAL_STATE_TAX_AMOUNT = By.xpath("//input[@name='SourceRegulateAmountCur']");
	protected final By VERTEX_INVOICE_TEXT_CODES = By.xpath("//span[text()='Vertex Invoice text codes']");
	protected final By INVOICE_TEXT_CODE = By.xpath("//input[@aria-label='Invoice text code']");
	protected final By INVOICE_TEXT_CODE_DESCRIPTION = By.xpath("//input[@aria-label='Invoice text code description']");
	protected final By INVOICE_TEXT_CODE_COLUMN_HEADER = By.xpath("//div[text()='Invoice text code']");
	protected final By INVOICE_TEXT_CODE_DESCRIPTION_COLUMN_HEADER = By.xpath("//div[text()='Invoice text code description']");
	protected final By ADJUST_TAB = By.xpath("//*[contains(@id,\"Regulation_header\") and @title='Adjustment']");
	protected final By TAX_ADJUST_TYPE = By.xpath("//*[contains(@id,\"TaxAdjustmentType_input\")]");
	protected final By APPLY_ACTUAL_AMOUNTS = By.xpath("//*[contains(@id,\"Apply_label\")]");

	public DFinanceTransactionSalesTaxDialog( final WebDriver driver, final VertexPage parent )
	{
		super(driver, parent);
		this.salesOrdersParent = (DFinanceAllSalesOrdersPage) this.dFinanceParent;
	}

	/**
	 * retrieves the "Total calculated sales tax amount" value
	 *
	 * @return the "Total calculated sales tax amount" value
	 */
	public String getCalculatedSalesTaxAmount( )
	{
		//I think this might need to be enabled because it's technically an input field, so maybe it only gets
		// autopopulated after it loads enough to be editable (but I haven't tested this idea)
		waitForPageLoad();
		click.javascriptClick(TOTAL_CALCULATED_SALES_TAX_AMOUNT);
		wait.waitForElementEnabled(TOTAL_CALCULATED_SALES_TAX_AMOUNT);
		String calculatedSalesTaxAmount = attribute.getElementAttribute(TOTAL_CALCULATED_SALES_TAX_AMOUNT, "value");
		return calculatedSalesTaxAmount;
	}

	/**
	 * retrieves the "Total calculated sales tax amount" value
	 * @author Vishwa
	 * @return the "Total calculated sales tax amount" value
	 */
	public String getActualLineItemSalesTaxAmount(int rowvalue)
	{
		waitForPageLoad();
		String value = "(//input[contains(@id, 'SourceRegulateAmountCur')])["+rowvalue+"]";
		String	calculatedLineItemSalesTaxAmount = driver.findElement(By.xpath(value)).getAttribute("value");
		return calculatedLineItemSalesTaxAmount;
	}

	/**
	 * retrieves the "Total calculated state sales tax amount" value
	 * @author Vishwa
	 * @return the "Total calculated state sales tax amount" value
	 */
	public String getCalculatedStateSalesTaxAmount( )
	{
		waitForPageLoad();
		wait.waitForElementEnabled(TOTAL_CALCULATED_State_SALES_TAX_AMOUNT);
		String calculatedStateSalesTaxAmount = attribute.getElementAttribute(TOTAL_CALCULATED_State_SALES_TAX_AMOUNT, "value");
		return calculatedStateSalesTaxAmount;
	}

	/**
	 * retrieves the "Total calculated 2ndLine sales tax amount" value
	 * @Author Vishwa
	 * @return the "Total calculated 2ndLine sales tax amount" value
	 */
	public String get2ndLineCalculatedSalesTaxAmount( )
	{
		wait.waitForElementEnabled(TOTAL_CALCULATED_2ndLine_SALES_TAX_AMOUNT);
		String calculated2ndLineCalculatedSalesTaxAmount = attribute.getElementAttribute(TOTAL_CALCULATED_2ndLine_SALES_TAX_AMOUNT, "value");
		return calculated2ndLineCalculatedSalesTaxAmount;
	}

	/**
	 * retrieve the "Total actual sales tax amount" value
	 *
	 * @return the "Total actual sales tax amount" value
	 */
	public String getActualSalesTaxAmount( )
	{
		wait.waitForElementEnabled(TOTAL_ACTUAL_SALES_TAX_AMOUNT);
		String actualSalesTaxAmount = attribute.getElementAttribute(TOTAL_ACTUAL_SALES_TAX_AMOUNT, "value");
		return actualSalesTaxAmount;
	}

	/**
	 * Find the element and get its attribute value
	 * @return
	 */
	public Boolean getActualSalesTaxAmountAttributeAndValue(){
		wait.waitForElementEnabled(TOTAL_ACTUAL_SALES_TAX_AMOUNT);
		String attributeValue = driver.findElement(TOTAL_ACTUAL_SALES_TAX_AMOUNT).getAttribute("readonly");
		
		return  attributeValue == null? true: false;
	}

	/**
	 * retrieve the "Total actual sales tax amount" value
	 * @author Vishwa
	 * @return the "Total actual sales tax amount" value
	 */
	public String getSalesInvoiceAmount( )
	{
		wait.waitForElementPresent(TOTAL_SALES_INVOICE_AMOUNT);
		String totalSalesInvoiceAmount = attribute.getElementAttribute(TOTAL_SALES_INVOICE_AMOUNT, "value");
		return totalSalesInvoiceAmount;
	}

	/**
	 * Click on "OK" button to close the dialog
	 */
	public void clickAdjustmentTab( )
	{
		wait.waitForElementDisplayed(ADJUST_TAB);
		click.clickElementCarefully(ADJUST_TAB);
		waitForPageLoad();
	}

	public void adjustTaxLine(int line, String amount)
	{
		driver.findElement(By.cssSelector("body")).sendKeys(Keys.END);
		WebElement lineElement = wait.waitForElementPresent(By.xpath("//input[contains(@id,'TmpTaxRegulation_SourceRegulateAmountCur')]["+line+"]"));

		click.clickElementIgnoreExceptionAndRetry(lineElement);
		waitForPageLoad();
		text.selectAllAndInputText(lineElement,amount);
	}

	/**
	 * Click on "OK" button to close the dialog
	 */
	public void closeSalesTaxCalculation( )
	{
		wait.waitForElementDisplayed(OK_BUTTON);
		click.clickElementCarefully(OK_BUTTON);
		waitForPageLoad();
		//the dialog deletes itself from the page object once the dialog has been closed
		//salesOrdersParent.salesTaxCalculator = null;
	}

	/**
	 * set adjustSalesTaxDetail
	 *
	 * @param adjustValue, default Summary, option Detail
	 */
	public void adjustSalesTaxDetail(String adjustValue)
	{
		wait.waitForElementDisplayed(TAX_ADJUST_TYPE);
		element.selectElementByText(TAX_ADJUST_TYPE, adjustValue);
		waitForPageLoad();
	}

	/**
	 * Click on "OK" button sale tax dialog window
	 */
	public void clickOkButton( )
	{
		wait.waitForElementDisplayed(CLICK_OK);
		click.clickElementCarefully(CLICK_OK);
		waitForPageLoad();
	}

	/**
	 * Clicks the Vertex Invoice text code linke
	 */
	public void clickVertexInvoiceTextCode(){
		wait.waitForElementDisplayed(VERTEX_INVOICE_TEXT_CODES);
		click.clickElementCarefully(VERTEX_INVOICE_TEXT_CODES);
	}

	/**
	 * Finds and retrieves the Invoice text code and description
	 * @param invoiceTextCodeValue
	 * @param invoiceTextCodeDescriptionVal
	 * @return - whether the values being passed in are found in the Invoice text code
	 */
	public boolean clickAndReturnInvoiceTextCodeAndDescription(String invoiceTextCodeValue, String invoiceTextCodeDescriptionVal){


		wait.waitForElementDisplayed(INVOICE_TEXT_CODE);
		String invoiceTextCode = attribute.getElementAttribute(INVOICE_TEXT_CODE, "value");
		String invoiceTextCodeDescription = attribute.getElementAttribute(INVOICE_TEXT_CODE_DESCRIPTION, "value");

		String[] invoiceTextCodeResults = {invoiceTextCode, invoiceTextCodeDescription};
		boolean resultVal = false;
		for(int i = 0; i <= invoiceTextCodeResults.length; i++){
			if(invoiceTextCodeResults[0].contains(invoiceTextCodeValue) && invoiceTextCodeResults[1].contains(invoiceTextCodeDescriptionVal)){
				resultVal = true;
			}
		}

		return resultVal;
	}

	/**
	 * Checks to see how many Invoice Text Codes appear, validates it's the correct code and description, and the column header have the correct names
	 * @param invoiceTextCodeExpectedAmount
	 * @param invoiceTextCodeValue
	 * @param invoiceTextCodeDescriptionVal
	 * @param invoiceTextCodeColumn
	 * @param invoiceTextCodeDescriptionColumn
	 */
	public boolean validateInvoiceTextCodeAndColumnHeaders(int invoiceTextCodeExpectedAmount, String invoiceTextCodeValue, String invoiceTextCodeDescriptionVal,
														   String invoiceTextCodeColumn, String invoiceTextCodeDescriptionColumn){

		List <WebElement> invoiceTextCodeAmount = wait.waitForAllElementsDisplayed(By.xpath("//div[@data-dyn-controlname=\"InvoiceTextCodes\"]//div[@class='fixedDataTableRowLayout_rowWrapper']"));
		invoiceTextCodeAmount.remove(0);
		String invoiceTextCode = attribute.getElementAttribute(INVOICE_TEXT_CODE, "value");
		String invoiceTextCodeDescription = attribute.getElementAttribute(INVOICE_TEXT_CODE_DESCRIPTION, "value");
		WebElement invoiceTextCodeElement = wait.waitForElementDisplayed(INVOICE_TEXT_CODE_COLUMN_HEADER);
		String invoiceTextCodeColumnHeader = invoiceTextCodeElement.getText();
		WebElement invoiceTextCodeDescriptionElement = wait.waitForElementDisplayed(INVOICE_TEXT_CODE_DESCRIPTION_COLUMN_HEADER);
		String invoiceTextCodeDescriptionColumnHeader = invoiceTextCodeDescriptionElement.getText();

		boolean resultVal = false;

		if(invoiceTextCodeAmount.size() == invoiceTextCodeExpectedAmount && invoiceTextCode.contains(invoiceTextCodeValue) &&
				invoiceTextCodeDescription.contains(invoiceTextCodeDescriptionVal) && invoiceTextCodeColumnHeader.contains(invoiceTextCodeColumn)
				&& invoiceTextCodeDescriptionColumnHeader.contains(invoiceTextCodeDescriptionColumn)){
				resultVal = true;
		}

		return resultVal;
	}
}
