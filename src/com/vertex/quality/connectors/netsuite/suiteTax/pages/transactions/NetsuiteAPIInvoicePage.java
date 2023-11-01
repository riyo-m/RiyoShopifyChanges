package com.vertex.quality.connectors.netsuite.suiteTax.pages.transactions;

import com.vertex.quality.common.utils.selenium.VertexAlertUtilities;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteInvoicePage;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteTransactions;
import com.vertex.quality.connectors.netsuite.common.pages.transactions.NetsuiteTransactionsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * The SuiteTax invoice page
 *
 * @author mwilliams
 */
public class NetsuiteAPIInvoicePage extends NetsuiteInvoicePage
{
	protected String summaryTaxHeader = "Tax Total";
	protected By vertexCallDetailsTabLocator = By.id("custom293txt");
	protected By vertexPostToVertex = By.id("custbody_posttax_vt_fs");

	public NetsuiteAPIInvoicePage(final WebDriver driver )
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
		return initializePageObject(NetsuiteAPIInvoicePage.class);
	}

	public <T extends NetsuiteTransactions> T previewTaxes( )
	{
		click.clickElement(previewTaxButtonLocator);
		alert.acceptAlert(30);
		return initializePageObject(NetsuiteAPIInvoicePage.class);
	}

	public <T extends NetsuiteTransactions> T searchFieldLocator(String searchText)
	{
		click.clickElement(searchTextBoxLocator);
		text.enterText(searchTextBoxLocator, searchText);
		click.clickElement(searchResultListLocator);
		waitForPageLoad();
		return initializePageObject(NetsuiteAPIInvoicePage.class);
	}

	public <T extends NetsuiteTransactions> T expenseMarkAll( )
	{
		click.clickElement(expenseMarkAllLocator);
		if ( alert.waitForAlertPresent(FOUR_SECOND_TIMEOUT) )
		{
			alert.acceptAlert();
		}
		//alert.acceptAlert(30);
		return initializePageObject(NetsuiteAPIInvoicePage.class);
	}


	@Override
	public <T extends NetsuiteTransactions> T editOrder( )
	{
		click.clickElement(editLocator);
		wait.waitForElementDisplayed(saveLocator);
		return initializePageObject(NetsuiteAPIInvoicePage.class);
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

	public <T extends NetsuiteTransactions> T reverseCharge( )
	{
		click.clickElement(authorizeReturnLocator);
		return initializePageObject(NetsuiteAPIInvoicePage.class);
	}

	public <T extends NetsuiteTransactions> T refundSale( )
	{
		click.clickElement(refundLocator);
		return initializePageObject(NetsuiteAPIInvoicePage.class);
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
