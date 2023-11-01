package com.vertex.quality.connectors.netsuite.singlecompany.pages.transactions;

import com.vertex.quality.common.utils.selenium.VertexAlertUtilities;
import com.vertex.quality.connectors.netsuite.common.interfaces.NetsuiteOrderWithGiftCertificate;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteInvoicePage;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteSalesOrderPage;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteTransactions;
import com.vertex.quality.connectors.netsuite.common.pages.transactions.NetsuiteTransactionsPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * The Single Company sales order page
 *
 * @author hho
 */
public class NetsuiteSCSalesOrderPage extends NetsuiteSalesOrderPage implements NetsuiteOrderWithGiftCertificate
{
	private String closedTabClass = "unrollformtabheadercollapse";
	protected String itemTaxRateHeader = "Tax";
	protected String summaryTaxHeader = "TAX";
	protected By addressTabLocator = By.id("billingtabtxt");

	public NetsuiteSCSalesOrderPage( final WebDriver driver )
	{
		super(driver);
	}

	@Override
	public void selectShippingDetailsTab( )
	{
		click.clickElement(addressTabLocator);
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
		return initializePageObject(NetsuiteSCSalesOrderPage.class);
	}

	@Override
	public <T extends NetsuiteTransactions> T editOrder( )
	{
		click.clickElement(editLocator);
		return initializePageObject(NetsuiteSCSalesOrderPage.class);
	}

	public <T extends NetsuiteTransactions> T deleteOrder(By locator)
	{
		this.alert = new VertexAlertUtilities(this, driver);
		hover.hoverOverElement(actionMenuDropdown);
		click.clickElement(deleteAction);
		alert.acceptAlert(5);
		wait.waitForElementDisplayed(confirmationBannerLocator, 20);
		return initializePageObject(NetsuiteTransactionsPage.class);
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
	public String getGiftCertificateAmount( )
	{
		return transactionComponent.getSummaryCategoryAmount(summaryGiftCertificateHeader);
	}

	@Override
	public NetsuiteSCInvoicePage fulfillSalesOrder()
	{
		saveOrder();
		NetsuiteInvoicePage page = super.fulfillSalesOrderSC();
		return initializePageObject(NetsuiteSCInvoicePage.class);
	}
}
