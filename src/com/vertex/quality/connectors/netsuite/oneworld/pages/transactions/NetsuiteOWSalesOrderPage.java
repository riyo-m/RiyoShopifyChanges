package com.vertex.quality.connectors.netsuite.oneworld.pages.transactions;

import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteInvoicePage;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteSalesOrderPage;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteTransactions;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.singlecompany.pages.transactions.NetsuiteSCInvoicePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * The One World sales order page
 *
 * @author hho
 */
public class NetsuiteOWSalesOrderPage extends NetsuiteSalesOrderPage
{
	private String summaryTaxHeader = "TAX TOTAL";
	private String itemTaxRateHeader = "Tax Rate";
	protected By vertexCallDetailsTabLocator = By.id("custom298_pane_hd");

	public NetsuiteOWSalesOrderPage( final WebDriver driver )
	{
		super(driver);
	}

	@Override
	public String getItemTaxRate( final NetsuiteItem item )
	{
		WebElement itemCell = tableComponent.getInteractableTableCell(itemTable, itemHeaderRow, item
			.getNetsuiteItemName()
			.getItemName(), itemTaxRateHeader);
		return itemCell.getText();
	}

	@Override
	public String getOrderTaxAmount( )
	{
		return transactionComponent.getSummaryCategoryAmount(summaryTaxHeader);
	}

	@Override
	public void selectShippingDetailsTab( )
	{
		selectAddressTab();
	}

	@Override
	public void selectAddressTab( )
	{
		By addressTabLoc = By.id("shippingtxt");
		click.clickElement(addressTabLoc);
	}

	@Override
	public <T extends NetsuiteTransactions> T saveOrder( )
	{
		processMultiButtonSubmitter("Save");
		if ( alert.waitForAlertPresent(FIVE_SECOND_TIMEOUT) )
		{
			alert.acceptAlert();
		}
		wait.waitForElementDisplayed(confirmationBannerLocator, 30);
		return initializePageObject(NetsuiteOWSalesOrderPage.class);
	}

	@Override
	public <T extends NetsuiteTransactions> T editOrder( )
	{
		click.clickElement(editLocator);
		wait.waitForElementDisplayed(saveLocator);
		return initializePageObject(NetsuiteOWSalesOrderPage.class);
	}

	@Override
	protected By getVertexCallDetailsTabLocator( )
	{
		return vertexCallDetailsTabLocator;
	}

	@Override
	public NetsuiteOWInvoicePage fulfillSalesOrder()
	{
		saveOrder();
		NetsuiteInvoicePage page = super.fulfillSalesOrder();
		return initializePageObject(NetsuiteOWInvoicePage.class);
	}
}
