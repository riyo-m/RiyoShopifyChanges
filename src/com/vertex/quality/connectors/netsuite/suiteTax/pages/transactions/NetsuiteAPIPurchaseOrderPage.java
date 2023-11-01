package com.vertex.quality.connectors.netsuite.suiteTax.pages.transactions;

import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuitePurchaseOrderPage;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteTransactions;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * The Suite Tax purchase order page
 *
 * @author ravunuri
 */
public class NetsuiteAPIPurchaseOrderPage extends NetsuitePurchaseOrderPage {

	private String summaryTaxHeader = "TAX TOTAL";
    private String itemTaxRateHeader = "Tax Rate";
    private By addressTabLoc = By.id("shippingtxt");
	protected By unrolled_view_locator = By.xpath("//a[@title='Unrolled view on']");

    public NetsuiteAPIPurchaseOrderPage(final WebDriver driver )
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

    @Override
    public <T extends NetsuiteTransactions> T saveOrder( )
    {
        processMultiButtonSubmitter("Save");
        wait.waitForElementDisplayed(confirmationBannerLocator, 30);
        return initializePageObject(NetsuiteAPIPurchaseOrderPage.class);
    }

	public <T extends NetsuiteTransactions> T billOrder( )
	{
		click.clickElement(billLocator);
		return initializePageObject(NetsuiteAPIBillOrderPage.class);
	}

    @Override
    public <T extends NetsuiteTransactions> T editOrder( )
    {
        click.clickElement(editLocator);
        wait.waitForElementDisplayed(saveLocator);
        return initializePageObject(NetsuiteAPIPurchaseOrderPage.class);
    }

    @Override
    protected By getVertexCallDetailsTabLocator( )
    {
        return vertexCallDetailsTabLocator;
    }

}
