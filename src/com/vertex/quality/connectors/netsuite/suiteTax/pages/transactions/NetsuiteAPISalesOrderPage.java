package com.vertex.quality.connectors.netsuite.suiteTax.pages.transactions;

import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteInvoicePage;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteSalesOrderPage;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteTransactions;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.oneworld.pages.transactions.NetsuiteOWInvoicePage;
import com.vertex.quality.connectors.netsuite.singlecompany.pages.transactions.NetsuiteSCInvoicePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * The Suite Tax sales order page
 *
 * @author mwilliams
 */
public class NetsuiteAPISalesOrderPage extends NetsuiteSalesOrderPage {

    private String summaryTaxHeader = "TAX TOTAL";
    private String itemTaxRateHeader = "Tax Rate";
    private By addressTabLoc = By.id("shippingtxt");

    public NetsuiteAPISalesOrderPage( final WebDriver driver )
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
        return initializePageObject(NetsuiteAPISalesOrderPage.class);
    }

    @Override
    public <T extends NetsuiteTransactions> T editOrder( )
    {
        wait.waitForElementDisplayed(editLocator);
        click.clickElement(editLocator);
        wait.waitForElementDisplayed(saveLocator);
        return initializePageObject(NetsuiteAPISalesOrderPage.class);
    }

    public <T extends NetsuiteTransactions> T previewTaxes( )
    {
        click.clickElement(previewTaxButtonLocator);
        alert.acceptAlert(30);
        return initializePageObject(NetsuiteAPIInvoicePage.class);
    }

    @Override
    protected By getVertexCallDetailsTabLocator( )
    {
        return vertexCallDetailsTabLocator;
    }

    @Override
    public NetsuiteAPIInvoicePage fulfillSalesOrder()
    {
        saveOrder();
        NetsuiteInvoicePage page = super.fulfillSalesOrder();
        return initializePageObject(NetsuiteAPIInvoicePage.class);
    }

    public NetsuiteAPIInvoicePage fulfillSalesOrderBill()
    {
        saveOrder();
        NetsuiteInvoicePage page = super.fulfillSalesOrderSC();
        return initializePageObject(NetsuiteAPIInvoicePage.class);
    }
}
