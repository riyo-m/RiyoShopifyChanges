package com.vertex.quality.connectors.netsuite.oneworld.pages.transactions;

import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteQuotePage;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteTransactions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * The One World quote page
 *
 * @author hho
 */
public class NetsuiteOWQuotePage extends NetsuiteQuotePage
{
	protected By vertexCallDetailsTabLocator = By.id("custom293txt");

	public NetsuiteOWQuotePage( final WebDriver driver )
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
		return initializePageObject(NetsuiteOWQuotePage.class);
	}

	@Override
	public <T extends NetsuiteTransactions> T editOrder( )
	{
		click.clickElement(editLocator);
		return initializePageObject(NetsuiteOWQuotePage.class);
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
}
