package com.vertex.quality.connectors.netsuite.singlecompany.pages.transactions;

import com.vertex.quality.connectors.netsuite.common.interfaces.NetsuiteOrderWithTax;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteQuotePage;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteTransactions;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * The Single Company quote page
 *
 * @author hho
 */
public class NetsuiteSCQuotePage extends NetsuiteQuotePage implements NetsuiteOrderWithTax
{
	protected String itemTaxCodeHeader = "Tax Code";
	protected String itemTaxRateHeader = "Tax Rate";
	protected String summaryTaxHeader = "TAX";

	public NetsuiteSCQuotePage( final WebDriver driver )
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
		return initializePageObject(NetsuiteSCQuotePage.class);
	}

	@Override
	public <T extends NetsuiteTransactions> T editOrder( )
	{
		click.clickElement(editLocator);
		return initializePageObject(NetsuiteSCQuotePage.class);
	}

	@Override
	public String getItemTaxCode( final NetsuiteItem item )
	{
		WebElement itemCell = tableComponent.getInteractableTableCell(itemTable, itemHeaderRow, item
			.getNetsuiteItemName()
			.getItemName(), itemTaxCodeHeader);
		return itemCell.getText();
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
}
