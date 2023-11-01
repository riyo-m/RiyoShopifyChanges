package com.vertex.quality.connectors.netsuite.oneworld.pages.transactions;

import com.vertex.quality.common.utils.selenium.VertexAlertUtilities;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteCashRefundPage;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteTransactions;
import com.vertex.quality.connectors.netsuite.common.pages.transactions.NetsuiteTransactionsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * The One World cash refund page
 *
 * @author hho
 */
public class NetsuiteOWCashRefundPage extends NetsuiteCashRefundPage
{
	protected String summaryTaxHeader = "TAX TOTAL";
	protected By vertexCallDetailsTabLocator = By.id("custom293txt");

	public NetsuiteOWCashRefundPage( final WebDriver driver )
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
		click.javascriptClick(saveLocator);
		if ( alert.waitForAlertPresent(TWO_SECOND_TIMEOUT) )
		{
			alert.acceptAlert();
		}
		return initializePageObject(NetsuiteOWCashRefundPage.class);
	}

	@Override
	public <T extends NetsuiteTransactions> T editOrder( )
	{
		click.clickElement(editLocator);
		return initializePageObject(NetsuiteOWCashRefundPage.class);
	}

	@Override
	public <T extends NetsuiteTransactionsPage> T deleteOrder( ){
		this.alert = new VertexAlertUtilities(this, driver);
		click.clickElement(deleteLocator);
		alert.acceptAlert(5);
		wait.waitForElementDisplayed(confirmationBannerLocator, 20);
		return initializePageObject(NetsuiteTransactionsPage.class);
	}

	@Override
	public String getOrderTaxAmount( )
	{
		return transactionComponent.getSummaryCategoryAmount(summaryTaxHeader);
	}

	@Override
	protected By getVertexCallDetailsTabLocator( )
	{
		return vertexCallDetailsTabLocator;
	}
}
