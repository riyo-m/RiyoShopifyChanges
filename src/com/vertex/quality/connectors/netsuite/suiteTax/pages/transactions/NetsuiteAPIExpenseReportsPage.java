package com.vertex.quality.connectors.netsuite.suiteTax.pages.transactions;

import com.vertex.quality.common.utils.selenium.VertexAlertUtilities;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteExpenseReportPage;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteInvoicePage;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteTransactions;
import com.vertex.quality.connectors.netsuite.common.pages.transactions.NetsuiteTransactionsPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * The SuiteTax Expense Reports page
 *
 * @author ravunuri
 */
public class NetsuiteAPIExpenseReportsPage extends NetsuiteExpenseReportPage
{
	protected String summaryTaxHeader = "Tax Total";
	protected By vertexCallDetailsTabLocator = By.id("custom293txt");

	public NetsuiteAPIExpenseReportsPage(final WebDriver driver )
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
		return initializePageObject(NetsuiteAPIExpenseReportsPage.class);
	}

	public <T extends NetsuiteTransactions> T previewTaxes( )
	{
		click.clickElement(previewTaxButtonLocator);
		alert.acceptAlert(30);
		return initializePageObject(NetsuiteAPIExpenseReportsPage.class);
	}

	@Override
	public <T extends NetsuiteTransactions> T editOrder( )
	{
		click.clickElement(editLocator);
		wait.waitForElementDisplayed(saveLocator);
		return initializePageObject(NetsuiteAPIExpenseReportsPage.class);
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
	public String getItemTaxCode(NetsuiteItem item) {
		return null;
	}

	@Override
	public String getItemTaxRate(NetsuiteItem item) {
		return null;
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
