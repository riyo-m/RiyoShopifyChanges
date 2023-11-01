package com.vertex.quality.connectors.netsuite.common.pages.base;

import com.vertex.quality.common.utils.selenium.VertexAlertUtilities;
import com.vertex.quality.connectors.netsuite.common.interfaces.NetsuiteOrderWithShipping;
import com.vertex.quality.connectors.netsuite.common.interfaces.NetsuiteOrderWithTax;
import com.vertex.quality.connectors.netsuite.common.pages.transactions.NetsuiteTransactionsPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Parent of the purchase order page
 *
 * @author ravunuri
 */
public abstract class NetsuitePurchaseOrderPage extends NetsuiteTransactions
	implements NetsuiteOrderWithShipping, NetsuiteOrderWithTax
{
	protected String itemTaxCodeHeader = "Tax Code";
	protected By shippingTabLoc = By.id("shippingtxt");

	public NetsuitePurchaseOrderPage(final WebDriver driver )
	{
		super(driver);
	}

	@Override
	public <T extends NetsuiteTransactionsPage> T deleteOrder( ){
		this.alert = new VertexAlertUtilities(this, driver);
		hover.hoverOverElement(actionMenuDropdown);
		click.clickElement(deleteAction);
		alert.acceptAlert(5);
		wait.waitForElementDisplayed(confirmationBannerLocator, 20);
		return initializePageObject(NetsuiteTransactionsPage.class);
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
		WebElement calculateShippingCost;
		String shippingCostText = attribute.getElementAttribute(shippingCostTextField, "value");
		if ( toBeCalculated.equals(shippingCostText) )
		{
			// This needs a double click
			calculateShippingCost = element.getWebElement(calculateShippingCostLocator);
			click.clickElement(calculateShippingCost);
			jsWaiter.sleep(1000);
			if(element.isElementDisplayed(calculateShippingCostLocator)) {
				calculateShippingCost = element.getWebElement(calculateShippingCostLocator);
				click.clickElement(calculateShippingCost);
			}
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

	/**
	 * Selects the location
	 *
	 * @param location the location
	 */
	public void selectLocation( String location )
	{
		setDropdownToValue(locationDropdownLocator, location);
	}
}
