package com.vertex.quality.connectors.netsuite.common.pages.base;

import com.vertex.quality.connectors.netsuite.common.interfaces.NetsuiteOrderWithShipping;
import com.vertex.quality.connectors.netsuite.common.interfaces.NetsuiteOrderWithTax;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Parent of the invoice page
 *
 * @author hho
 */
public abstract class NetsuiteInvoicePage extends NetsuiteTransactions
	implements NetsuiteOrderWithShipping, NetsuiteOrderWithTax
{
	protected By shippingTabLoc = By.id("shippingtxt");
	protected String summaryTransactionDiscountHeader = "TRANSACTION DISCOUNT";

	protected String itemTaxCodeHeader = "Tax Code";
	protected String itemTaxRateHeader = "Tax Rate";

	public NetsuiteInvoicePage( final WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Gets the order transaction discount amount
	 *
	 * @return the order transaction discount amount
	 */
	public String getOrderTransactionDiscountAmount( )
	{
		return transactionComponent.getSummaryCategoryAmount(summaryTransactionDiscountHeader);
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

	@Override
	public String getItemTaxCode( final NetsuiteItem item )
	{
		WebElement itemCell = tableComponent.getInteractableTableCell(itemTable, itemHeaderRow, item
			.getNetsuiteItemName()
			.getItemName(), itemTaxCodeHeader);
		return itemCell.getText();
	}

	@Override
	public String getItemTaxRate( final NetsuiteItem item )
	{
		WebElement itemCell = tableComponent.getInteractableTableCell(itemTable, itemHeaderRow, item
			.getNetsuiteItemName()
			.getItemName(), itemTaxRateHeader);
		return itemCell.getText();
	}

}
