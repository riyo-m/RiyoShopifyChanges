package com.vertex.quality.connectors.netsuite.common.pages.base;

import com.vertex.quality.connectors.netsuite.common.interfaces.NetsuiteOrderWithShipping;
import com.vertex.quality.connectors.netsuite.common.interfaces.NetsuiteOrderWithTax;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteExpense;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Parent of the Expense Report page
 *
 * @author ravunuri
 */
public abstract class NetsuiteExpenseReportPage extends NetsuiteTransactions
	implements NetsuiteOrderWithShipping, NetsuiteOrderWithTax
{
	protected By shippingTabLoc = By.id("shippingtxt");

	protected String itemTaxRateHeader = "Tax Rate";

	public NetsuiteExpenseReportPage(final WebDriver driver )
	{
		super(driver);
	}


	@Override
	public String getOrderShippingAmount( )
	{
		return transactionComponent.getSummaryCategoryAmount(summaryShippingCostHeader);
	}

	@Override
	public String getOrderHandlingAmount( )
	{
		return transactionComponent.getSummaryCategoryAmount(summaryHandlingCostHeader);
	}

	@Override
	public void calculateShippingCost( )
	{
		String shippingCostText = attribute.getElementAttribute(shippingCostTextField, "value");
		if ( toBeCalculated.equals(shippingCostText) )
		{
			click.clickElement(calculateShippingCostButton);
		}
	}

}
