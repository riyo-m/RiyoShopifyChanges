package com.vertex.quality.connectors.netsuite.oneworld.tests.certification;

import com.vertex.quality.common.enums.Country;
import com.vertex.quality.common.enums.State;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pages.transactions.NetsuiteTransactionsPage;
import com.vertex.quality.connectors.netsuite.common.pages.transactions.NetsuiteTransactionsSearchPage;
import com.vertex.quality.connectors.netsuite.common.pages.transactions.NetsuiteTransactionsSearchResultPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteAddress;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuitePrices;
import com.vertex.quality.connectors.netsuite.oneworld.pages.transactions.NetsuiteOWInvoicePage;
import com.vertex.quality.connectors.netsuite.oneworld.pages.transactions.NetsuiteOWSalesOrderPage;
import com.vertex.quality.connectors.netsuite.oneworld.tests.base.OWCertBaseTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class OWCertInvoiceTests extends OWCertBaseTest {

    /**
     * Certifies Sales order to Invoice function works properly
     * CNSL-910
     */
    @Test(groups = { "netsuite_cert_ow"})
    protected void salesOrdertoInvoiceTests (){
        //Create a sales order
        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("100.00")
                .taxCode(defaultTaxCode)
                .build();

        //Get menus
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        NetsuiteOWSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
        //Enter item and save the order
        setupBasicOrder(salesOrderPage, customer, itemOne);
        //Click on the “Fulfill” button. This will take you to the fulfillment page
        //Click on the “Save and Bill” button. This will save the fulfillment and open the invoice page
        NetsuiteOWInvoicePage invoicePage = salesOrderPage.fulfillSalesOrder();
        //Check the “VERTEX POST TO VERTEX” checkbox to post it real-time
        invoicePage.setVertexPostToVertex();
        //Save the invoice
        invoicePage.saveOrder();
        //Verify the “VERTEX PROCESS DATE” is populated with current date
        //**
        //Make sure tax is showing correctly.
        //Verify the “VERTEX TAX ERROR CODE” field is showing Success
        invoicePage.editOrder();
        invoicePage.deleteOrder();
    }

    /**
     * Certifies Location codes work properly
     * CNSL-909
     */
    @Ignore
    @Test(groups = { "netsuite_cert_ow"})
    protected void locationCodeTests (){

    }

    /**
     * Certifies you can Edit Invoices properly
     * CNSL-908
     */
    @Test(groups = { "netsuite_cert_ow"})
    protected void editInvoiceTests ()
    {
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("100.00")
                .build();
        NetsuiteItem itemTwo = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("2")
                .amount("200.00")
                .build();

        NetsuiteAddress addressOne = NetsuiteAddress
                .builder("91608")
                .fullAddressLine1("100 Universal City Plz")
                .addressLine1("100 Universal City Plaza")
                .city("Universal City")
                .state(State.CA)
                .country(Country.USA)
                .zip9("91608-1002")
                .build();

        String transactionNumber;

        NetsuitePrices expectedPricesBeforeSaving = NetsuitePrices
                .builder()
                .itemTaxRate("0.0%")
                .itemTaxCode(defaultTaxCode)
                .subtotal("100.00")
                .taxAmount("0.00")
                .total("100.00")
                .build();
        NetsuitePrices expectedPricesAfterSaving = NetsuitePrices
                .builder()
                .itemTaxRate("9.5%")
                .itemTaxCode(defaultTaxCode)
                .subtotal("100.00")
                .taxAmount("9.50")
                .total("109.50")
                .build();
        NetsuitePrices editedPrices = NetsuitePrices
                .builder()
                .itemTaxRate("9.5%")
                .itemTaxCode(defaultTaxCode)
                .subtotal("200.00")
                .taxAmount("19.00")
                .total("219.00")
                .build();

        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
        NetsuiteNavigationMenus searchSalesOrderMenu = getTransactionSearchMenu();

        NetsuiteSetupManagerPage setupManagerPage = configureSettings();

        NetsuiteOWSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);

        transactionNumber = salesOrderPage.getTransactionNumber();
        setupBasicOrder(salesOrderPage, customer, addressOne, itemOne);

        checkBasicOrderAmounts(salesOrderPage, expectedPricesBeforeSaving);
        checkOrderWithTax(salesOrderPage, expectedPricesBeforeSaving, itemOne);

        NetsuiteOWSalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();

        checkBasicOrderAmounts(savedSalesOrderPage, expectedPricesAfterSaving);
        checkOrderWithTax(savedSalesOrderPage, expectedPricesAfterSaving, itemOne);

        NetsuiteOWSalesOrderPage editedSalesOrderPage = savedSalesOrderPage.editOrder();
        editedSalesOrderPage.editItem(itemOne, itemTwo);
        savedSalesOrderPage = editedSalesOrderPage.saveOrder();

        checkBasicOrderAmounts(editedSalesOrderPage, editedPrices);
        checkOrderWithTax(editedSalesOrderPage, editedPrices, itemOne);

        NetsuiteTransactionsSearchPage searchPage = savedSalesOrderPage.navigationPane.navigateThrough(
                searchSalesOrderMenu);
        searchPage.enterTransactionNumberAndId(transactionNumber);
        NetsuiteTransactionsSearchResultPage searchResultsPage = searchPage.submitSearch();
        editedSalesOrderPage = searchResultsPage.edit(transactionNumber);
        NetsuiteTransactionsPage transactionsPage = editedSalesOrderPage.deleteOrder();
        assertTrue(transactionsPage.isOrderDeleted());
    }
}
