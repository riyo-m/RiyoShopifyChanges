package com.vertex.quality.connectors.netsuite.singlecompany.pages.transactions;

import com.vertex.quality.common.utils.selenium.VertexAlertUtilities;
import com.vertex.quality.connectors.netsuite.common.interfaces.NetsuiteOrderWithGiftCertificate;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteCashSalePage;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteTransactions;
import com.vertex.quality.connectors.netsuite.common.pages.transactions.NetsuiteTransactionsPage;
import com.vertex.quality.connectors.netsuite.oneworld.pages.transactions.NetsuiteOWSalesOrderPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * The Single Company cash sale page
 *
 * @author hho
 */
public class NetsuiteSCCashSalePage extends NetsuiteCashSalePage implements NetsuiteOrderWithGiftCertificate
{
	protected By addressTabLoc = By.id("addresstxt");
	protected String summaryTaxHeader = "TAX";

	public NetsuiteSCCashSalePage( final WebDriver driver )
	{
		super(driver);
	}

	@Override
	public void selectShippingDetailsTab( )
	{
		click.clickElement(addressTabLoc);
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
		wait.waitForElementDisplayed(confirmationBannerLocator, 30);
		return initializePageObject(NetsuiteOWSalesOrderPage.class);
	}

	@Override
	public <T extends NetsuiteTransactions> T editOrder( )
	{
		click.clickElement(editLocator);
		return initializePageObject(NetsuiteSCCashSalePage.class);
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

	/**
	 * Creates the cash refund
	 *
	 * @return the cash refund page
	 */
	public NetsuiteSCCashRefundPage createCashRefund( )
	{
		click.clickElement(refundButtonLoc);
		return initializePageObject(NetsuiteSCCashRefundPage.class);
	}

	@Override
	public String getGiftCertificateAmount( )
	{
		return transactionComponent.getSummaryCategoryAmount(summaryGiftCertificateHeader);
	}

	@Override
	public String getOrderTaxAmount( )
	{
		return transactionComponent.getSummaryCategoryAmount(summaryTaxHeader);
	}
}
