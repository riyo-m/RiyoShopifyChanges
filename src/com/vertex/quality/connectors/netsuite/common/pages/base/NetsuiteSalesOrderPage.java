package com.vertex.quality.connectors.netsuite.common.pages.base;

import com.vertex.quality.common.utils.selenium.VertexAlertUtilities;
import com.vertex.quality.connectors.netsuite.common.interfaces.NetsuiteOrderWithShipping;
import com.vertex.quality.connectors.netsuite.common.interfaces.NetsuiteOrderWithTax;
import com.vertex.quality.connectors.netsuite.common.pages.transactions.NetsuiteTransactionsPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.oneworld.pages.transactions.NetsuiteOWInvoicePage;
import com.vertex.quality.connectors.netsuite.singlecompany.pages.transactions.NetsuiteSCInvoicePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Parent of the sales order page
 *
 * @author hho
 */
public abstract class NetsuiteSalesOrderPage extends NetsuiteTransactions
	implements NetsuiteOrderWithShipping, NetsuiteOrderWithTax
{
	protected String itemTaxCodeHeader = "Tax Code";
	protected By shippingTabLoc = By.id("shippingtxt");

	public NetsuiteSalesOrderPage( final WebDriver driver )
	{
		super(driver);
	}

	@Override
	public <T extends NetsuiteTransactionsPage> T deleteOrder( ){
				this.alert = new VertexAlertUtilities(this, driver);
		click.clickElement(deleteLocator);
		alert.acceptAlert(5);
		wait.waitForElementDisplayed(confirmationBannerLocator, 20);
		return initializePageObject(NetsuiteTransactionsPage.class);
	}

	public <T extends NetsuiteTransactionsPage> T deleteOrderInAction( ){
		this.alert = new VertexAlertUtilities(this, driver);
		hover.hoverOverElement(actionMenuDropdown);
		click.clickElement(deleteAction);
		alert.acceptAlert(5);
		wait.waitForElementDisplayed(confirmationBannerLocator, 20);
		return initializePageObject(NetsuiteTransactionsPage.class);
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
		WebElement calculateShippingCost;
		String shippingCostText = attribute.getElementAttribute(shippingCostTextField, "value");
		if ( toBeCalculated.equals(shippingCostText) )
		{
			// This needs a double click
			calculateShippingCost = element.getWebElement(calculateShippingCostLocator);
			click.clickElement(calculateShippingCost);
			jsWaiter.sleep(1000);
			if(element.isElementDisplayed(calculateShippingCostLocator)) {
				calculateShippingCost = element.getWebElement(calculateShippingCostLocator);
				click.clickElement(calculateShippingCost);
			}
		}
	}

	public void taxOverrideCheckbox( )
	{
		WebElement taxOverrideCheckbox = element.getWebElement(taxOverrideCheckboxLocator);
		click.clickElement(taxOverrideCheckbox);
		jsWaiter.sleep(60);
	}

	@Override
	public String getItemTaxCode( final NetsuiteItem item )
	{
		WebElement itemCell = tableComponent.getInteractableTableCell(itemTable, itemHeaderRow, item
			.getNetsuiteItemName()
			.getItemName(), itemTaxCodeHeader);
		return itemCell.getText();
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

	/**
	 * Click Fulfill button
	 * @return NetsuiteInvoicePage
	 */
	public NetsuiteInvoicePage fulfillSalesOrder() {
			click.clickElement(fulfillLocator);
			waitForPageLoad();
			click.clickElement(saveLocator);
		return initializePageObject(NetsuiteOWInvoicePage.class);
	}

	/**
	 * Click Fulfill button for SC
	 * @return NetsuiteInvoicePage for SC
	 */
	public NetsuiteInvoicePage fulfillSalesOrderSC() {
		click.clickElement(fulfillLocator);
		waitForPageLoad();
		hover.hoverOverElement(saveButtonDropDownLocator);
		boolean isSaveNBill_Button_Visible = element.isElementDisplayed(saveNBillLocator);
		if (isSaveNBill_Button_Visible)
		{
			click.clickElement(saveNBillLocator);
		}
		else
			click.clickElement(saveLocator);
		return initializePageObject(NetsuiteSCInvoicePage.class);
	}

	public void enterCouponCode(String coupon) {

		if(!coupon.equals("")) {
			By couponCodeTextField = By.id("couponcode_display");
			text.enterText(couponCodeTextField, coupon);
			WebElement el = driver.findElement(couponCodeTextField);
			el.sendKeys(Keys.ENTER);
		}

		jsWaiter.sleep(500);
	}

	/**
	 * Selects Currency from dropdown
	 *
	 * @param currency the Netsuite Currency
	 */
	public void selectCurrencyByDropdown(String currency) {

		if(!currency.equals("")) {
			setDropdownToValue(currencyDropdownLocator, currency);
		}

		jsWaiter.sleep(500);

	}

	/**
	 * Wait till Record takes affect in Oseries
	 */
	public void waitUntilHasRecordProcessed()
	{
		jsWaiter.sleep(300000);
	}
}
