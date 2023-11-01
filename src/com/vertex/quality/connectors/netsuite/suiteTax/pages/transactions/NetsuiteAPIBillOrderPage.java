package com.vertex.quality.connectors.netsuite.suiteTax.pages.transactions;

import com.vertex.quality.common.utils.selenium.VertexTextUtilities;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteTransactions;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuitePurchaseOrderPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * The Suite Tax purchase order page
 *
 * @author ravunuri
 */
public class NetsuiteAPIBillOrderPage extends NetsuiteAPIPurchaseOrderPage {

    private String summaryTaxHeader = "TAX TOTAL";
    private String itemTaxRateHeader = "Tax Rate";
    private By addressTabLoc = By.id("shippingtxt");
	public VertexTextUtilities setDropdownToValue;

    public NetsuiteAPIBillOrderPage(final WebDriver driver )
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
        click.clickElement(addressTabLoc);
    }

	/**
	 * Saves Bill Order
	 */
	public void saveBill( )
	{
		click.clickElement(saveLocator);
	}

    @Override
    public <T extends NetsuiteTransactions> T saveOrder( )
    {
		click.clickElement(saveLocator);
		return initializePageObject(NetsuiteAPIBillOrderPage.class);
    }

	/**
	 * Submits Payment for the PO Bill
	 *
	 */
	public <T extends NetsuiteTransactions> T submitPayment( )
	{
		click.clickElement(paymentLocator);
		return initializePageObject(NetsuiteAPIBillOrderPage.class);
	}

	/**
	 * Access Purchase Order Bill
	 *
	 */
	public <T extends NetsuiteTransactions> T accessBill( )
	{
		click.clickElement(accessBillLocator);
		return initializePageObject(NetsuiteAPIBillOrderPage.class);
	}

	/**
	 * Inputs the given Vendor tax
	 *
	 * @param vendortax the Vendor Tax
	 */
	public void enterVendorTax( String vendortax )
	{
		By vendorTaxTextField = By.id("custbody_vendor_tax_vt_formattedValue");
		//click.clickElement(vendorTaxTextField);
		text.enterText(vendorTaxTextField, vendortax);
	}

	/**
	 * Selects VERTEX UNDERCHARGE ACTION from dropdown
	 *
	 * @param underchargeaction ACTION the Netsuite Currency
	 */
	public void selectUnderChargeAction(String underchargeaction) {
		By underChargeActionLocator = By.xpath("//*[@id=\"inpt_custbody_undercharge_action_vt11\"]");
		setDropdownToValue(underChargeActionLocator, underchargeaction);
	}

	/**
	 * Selects VERTEX OVERCHARGE ACTION from dropdown
	 *
	 * @param overchargeaction ACTION the Netsuite Currency
	 */
	public void selectOverChargeAction(String overchargeaction) {
		By overChargeActionLocator = By.xpath("//*[@id=\"inpt_custbody_overcharge_action_vt12\"]");
		setDropdownToValue(overChargeActionLocator, overchargeaction);
	}

	/**
	 * Inputs the Location
	 *
	 * @param location the Location
	 */
	public void enterLocation( String location )
	{
		By locationField = By.id("inpt_location6");
		//setDropdownToValue.selectAllAndInputText(locationField, location);
		text.enterText(locationField, location);
	}

    @Override
    public <T extends NetsuiteTransactions> T editOrder( )
    {
        click.clickElement(editLocator);
        wait.waitForElementDisplayed(saveLocator);
        return initializePageObject(NetsuiteAPIBillOrderPage.class);
    }

    @Override
    protected By getVertexCallDetailsTabLocator( )
    {
        return vertexCallDetailsTabLocator;
    }



}
