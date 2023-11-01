package com.vertex.quality.connectors.netsuite.oneworld.pages.transactions;

import com.vertex.quality.common.utils.selenium.VertexAlertUtilities;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteCreditMemoPage;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteTransactions;
import com.vertex.quality.connectors.netsuite.common.pages.transactions.NetsuiteTransactionsPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * The One World credit memo page
 *
 * @author hho
 */
public class NetsuiteOWCreditMemoPage extends NetsuiteCreditMemoPage
{
	protected String itemTaxRateHeader = "Tax Rate";
	protected String summaryTaxHeader = "TAX TOTAL";
	protected By vertexCallDetailsTabLocator = By.id("custom293txt");

	public NetsuiteOWCreditMemoPage( final WebDriver driver )
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
		click.clickElement(saveLocator);
		if ( alert.waitForAlertPresent(TWO_SECOND_TIMEOUT) )
		{
			alert.acceptAlert();
		}
		wait.waitForElementDisplayed(confirmationBannerLocator, 20);
		return initializePageObject(NetsuiteOWCreditMemoPage.class);
	}

	@Override
	public <T extends NetsuiteTransactions> T editOrder( )
	{
		click.clickElement(editLocator);
		return initializePageObject(NetsuiteOWCreditMemoPage.class);
	}

	@Override
	public <T extends NetsuiteTransactionsPage> T deleteOrder( ){
		this.alert = new VertexAlertUtilities(this, driver);
		getDeleteButton();
		click.clickElement(deleteMemoLocator);
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

	public void deleteMemo()
	{
		WebElement menu =element.getWebElement(actionsMenu);
		hover.hoverOverElement(menu);
		click.clickElement(By.xpath("//span[normalize-space()='Delete']"));
	}
}
