package com.vertex.quality.connectors.netsuite.oneworld.pages.transactions;

import com.vertex.quality.common.utils.selenium.VertexAlertUtilities;
import com.vertex.quality.connectors.netsuite.common.interfaces.NetsuiteOrderWithShipping;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteCashSalePage;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteTransactions;
import com.vertex.quality.connectors.netsuite.common.pages.transactions.NetsuiteTransactionsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * The One World cash sale page
 *
 * @author hho
 */
public class NetsuiteOWCashSalePage extends NetsuiteCashSalePage implements NetsuiteOrderWithShipping
{
	protected String summaryTaxHeader = "TAX TOTAL";
	protected By vertexCallDetailsTabLocator = By.id("custom293txt");

	public NetsuiteOWCashSalePage( final WebDriver driver )
	{
		super(driver);
	}

	@Override
	public void selectShippingDetailsTab( )
	{
		click.clickElement(shippingTabLoc);
	}

	@Override
	public void selectAddressTab( )
	{
		click.clickElement(addressTabLoc);
	}

	@Override
	public <T extends NetsuiteTransactions> T saveOrder( )
	{
		processMultiButtonSubmitter("Save");
		wait.waitForElementDisplayed(confirmationBannerLocator, 45);
		return initializePageObject(NetsuiteOWSalesOrderPage.class);
	}

	@Override
	public <T extends NetsuiteTransactions> T editOrder( )
	{
		click.clickElement(editLocator);
		return initializePageObject(NetsuiteOWCashSalePage.class);
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
	public String getOrderTaxAmount( )
	{
		return transactionComponent.getSummaryCategoryAmount(summaryTaxHeader);
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
	 * Creates the cash refund
	 *
	 * @return the cash refund page
	 */
	public NetsuiteOWCashRefundPage createCashRefund( )
	{
		click.clickElement(refundButtonLoc);
		return initializePageObject(NetsuiteOWCashRefundPage.class);
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

	/**
	 * Selects the location
	 *
	 * @param location the location
	 */
	public void selectLocation( String location )
	{
		setDropdownToValue(locationDropdownLocator, location);
	}

	@Override
	protected By getVertexCallDetailsTabLocator( )
	{
		return vertexCallDetailsTabLocator;
	}
}
