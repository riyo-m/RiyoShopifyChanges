package com.vertex.quality.connectors.netsuite.common.pages.base;

import com.vertex.quality.connectors.netsuite.common.interfaces.NetsuiteOrderWithTax;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Parent of the cash sale page
 *
 * @author hho
 */
public abstract class NetsuiteCashSalePage extends NetsuiteTransactions implements NetsuiteOrderWithTax
{
	protected By shippingTabLoc = By.id("shippingtxt");
	protected By addressTabLoc = By.id("shippingtxt");
	protected By refundButtonLoc = By.id("tdbody_refund");

	protected String itemQuantityHeader = "Quantity";
	protected String itemTaxCodeHeader = "Tax Code";
	protected String itemTaxRateHeader = "Tax Rate";

	public NetsuiteCashSalePage( final WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Selects the location
	 *
	 * @param location the location
	 */
	public void selectLocation( String location )
	{
		transactionComponent.selectLocation(location);
	}

	/**
	 * Inputs the given date
	 *
	 * @param date the date
	 */
	public void enterDate( String date )
	{
		transactionComponent.enterDate(date);
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
	protected String getItemQuantityHeader( )
	{
		return itemQuantityHeader;
	}
}
