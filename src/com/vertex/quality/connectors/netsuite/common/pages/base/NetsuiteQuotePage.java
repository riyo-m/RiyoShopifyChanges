package com.vertex.quality.connectors.netsuite.common.pages.base;

import com.vertex.quality.common.utils.selenium.VertexAlertUtilities;
import com.vertex.quality.connectors.netsuite.common.interfaces.NetsuiteOrderWithShipping;
import com.vertex.quality.connectors.netsuite.common.pages.transactions.NetsuiteTransactionsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Parent of the quote page
 *
 * @author hho
 */
public abstract class NetsuiteQuotePage extends NetsuiteTransactions implements NetsuiteOrderWithShipping
{
	protected By shippingTabLoc = By.id("shippingtxt");
	protected By actionsMenu = By.id("spn_ACTIONMENU_d1");
	protected By actionsSubmenu = By.id("div_ACTIONMENU_d1");
	protected final By deleteLocator = By.xpath("//div[@id='div_ACTIONMENU_d1']//span[contains(text(), 'Delete')]");

	protected String delete = "Delete";

	public NetsuiteQuotePage( final WebDriver driver )
	{
		super(driver);
	}

	@Override
	public String getOrderShippingAmount( )
	{
		return transactionComponent.getSummaryCategoryAmount(summaryShippingCostHeader);
	}

	@Override
	public String getOrderHandlingAmount( )
	{
		return transactionComponent.getSummaryCategoryAmount(summaryHandlingCostHeader);
	}

	@Override
	public void calculateShippingCost( )
	{
		String shippingCostText = attribute.getElementAttribute(shippingCostTextField, "value");
		if ( toBeCalculated.equals(shippingCostText) )
		{
			click.clickElement(calculateShippingCostButton);
		}
	}

	@Override
	public <T extends NetsuiteTransactionsPage> T deleteOrder( )
	{
		this.alert = new VertexAlertUtilities(this, driver);
		WebElement menu =element.getWebElement(actionsMenu);
		hover.hoverOverElement(menu);
		click.clickElement(deleteLocator);
		if ( alert.waitForAlertPresent(DEFAULT_TIMEOUT) )
		{
			alert.acceptAlert();
		}
		wait.waitForElementDisplayed(confirmationBannerLocator, 20);
		return initializePageObject(NetsuiteTransactionsPage.class);
	}

	@Override
	protected WebElement getDeleteButton( )
	{
		return getElementInHoverableMenu(actionsMenu, actionsSubmenu, delete);
	}
}
