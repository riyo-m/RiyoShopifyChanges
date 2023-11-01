package com.vertex.quality.connectors.netsuite.common.pages.base;

import com.vertex.quality.connectors.netsuite.common.interfaces.NetsuiteOrderWithShipping;
import com.vertex.quality.connectors.netsuite.common.interfaces.NetsuiteOrderWithTax;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Parent of the credit memo page
 *
 * @author hho
 */
public abstract class NetsuiteCreditMemoPage extends NetsuiteTransactions
	implements NetsuiteOrderWithShipping, NetsuiteOrderWithTax
{
	protected By shippingTabLoc = By.id("shippingtxt");
	protected By addressTabLoc = By.id("addresstxt");
	protected By actionsMenu = By.id("spn_ACTIONMENU_d1");
	protected By actionsSubmenu = By.id("div_ACTIONMENU_d1");

	protected String delete = "Delete";
	protected String itemQuantityHeader = "Quantity";
	protected String itemTaxCodeHeader = "Tax Code";

	public NetsuiteCreditMemoPage( final WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Selects the posting period
	 *
	 * @param postingPeriod the posting period
	 */
	public void selectPostingPeriod( String postingPeriod )
	{
		transactionComponent.selectPostingPeriod(postingPeriod);
	}

	/**
	 * Selects the location
	 *
	 * @param location the location
	 */
	public void selectLocation( String location )
	{
		transactionComponent.selectLocation(location);
	}

	@Override
	protected String getItemQuantityHeader( )
	{
		return itemQuantityHeader;
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
	protected WebElement getDeleteButton( )
	{
		return getElementInHoverableMenu(actionsMenu, actionsSubmenu, delete);
	}
}
