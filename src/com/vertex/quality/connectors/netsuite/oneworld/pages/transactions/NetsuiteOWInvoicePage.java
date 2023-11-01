package com.vertex.quality.connectors.netsuite.oneworld.pages.transactions;

import com.vertex.quality.common.utils.selenium.VertexAlertUtilities;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteInvoicePage;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteTransactions;
import com.vertex.quality.connectors.netsuite.common.pages.transactions.NetsuiteTransactionsPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * The One World invoice page
 *
 * @author hho
 */
public class NetsuiteOWInvoicePage extends NetsuiteInvoicePage
{
	protected String summaryTaxHeader = "TAX TOTAL";
	protected By vertexCallDetailsTabLocator = By.id("custom293txt");
	protected By vertexPostToVertex = By.id("custbody_posttax_vt_fs");

	public NetsuiteOWInvoicePage( final WebDriver driver )
	{
		super(driver);
	}

	@Override
	public void selectShippingDetailsTab( )
	{
		selectAddressTab();
	}

	@Override
	public void selectAddressTab( )
	{
		click.clickElement(shippingTabLoc);
	}

	@Override
	public <T extends NetsuiteTransactions> T saveOrder( )
	{
		processMultiButtonSubmitter("Save");
		wait.waitForElementDisplayed(confirmationBannerLocator, 30);
		return initializePageObject(NetsuiteOWInvoicePage.class);
	}

	@Override
	public <T extends NetsuiteTransactions> T editOrder( )
	{
		click.clickElement(editLocator);
		wait.waitForElementDisplayed(saveLocator);
		return initializePageObject(NetsuiteOWInvoicePage.class);
	}

	public <T extends NetsuiteTransactions> T creditOrder( )
	{
		click.clickElement(creditLocator);
		wait.waitForElementDisplayed(saveLocator);
		return initializePageObject(NetsuiteOWInvoicePage.class);
	}

	public <T extends NetsuiteTransactionsPage> T deleteOrder()
	{
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

	@Override
	protected By getVertexCallDetailsTabLocator( )
	{
		return vertexCallDetailsTabLocator;
	}
}
