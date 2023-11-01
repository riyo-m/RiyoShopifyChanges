package com.vertex.quality.connectors.dynamics365.finance.dialogs;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.dynamics365.finance.dialogs.base.DFinanceBaseDialog;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceAllSalesOrdersPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * dialog on {@link DFinanceAllSalesOrdersPage} for creating a new sales order
 *
 * @author ssalisbury
 */
public class DFinanceCreateSalesOrderDialog extends DFinanceBaseDialog
{
	protected final DFinanceAllSalesOrdersPage salesOrdersParent;

	protected final By CUSTOMER_HEADER = By.xpath("//*[contains(@id,'CustomerTab_header')]/button");
	protected final By OK_BUTTON_FOR_CREATE_SALES_ORDER = By.name("OK");
	protected final By TEXT_CUSTOMER_ACCOUNT = By.cssSelector("[id$=SalesTable_CustAccount_input]");

	public DFinanceCreateSalesOrderDialog( final WebDriver driver, final VertexPage parent )
	{
		super(driver, parent);
		this.salesOrdersParent = (DFinanceAllSalesOrdersPage) this.dFinanceParent;
	}

	/**
	 * expands the customer section of the dialog
	 */
	public void expandCustomerSection( )
	{
		dFinanceParent.expandHeader(CUSTOMER_HEADER);
	}

	/**
	 * click on OK button to complete creation of new sales order
	 */
	public void finishCreatingSalesOrder( )
	{
		click.clickElementCarefully(OK_BUTTON_FOR_CREATE_SALES_ORDER);
		//the dialog deletes itself from the page object once the dialog has been closed
		salesOrdersParent.salesOrderCreator = null;
	}

	/**
	 * chooses the Customer account for the new sales order
	 *
	 * @param customerAccount which customer the sales order will be for
	 */
	public void setCustomerAccount( final String customerAccount )
	{
		text.setTextFieldCarefully(TEXT_CUSTOMER_ACCOUNT, customerAccount);
	}
}
