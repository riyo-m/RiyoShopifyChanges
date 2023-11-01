package com.vertex.quality.connectors.netsuite.singlecompany.tests.certification;

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
import com.vertex.quality.connectors.netsuite.singlecompany.pages.transactions.NetsuiteSCSalesOrderPage;
import com.vertex.quality.connectors.netsuite.singlecompany.tests.base.SCCertBaseTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;

public class SCCertInvoiceTests extends SCCertBaseTest {

    /**
     * Certifies Tax Journal works properly
     * CNSL-921
     */
    @Test(groups = { "netsuite_cert_sc"})
    protected void taxJournalTests (){
        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_SUPPLY_CA;
        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.CHLOROFORM_ITEM)
                .quantity("1")
                .amount("100.00")
                .build();
        NetsuiteItem itemTwo = NetsuiteItem
                .builder(NetsuiteItemName.BLACKBERRY_PLAYBOOK)
                .quantity("1")
                .amount("499.00")
                .build();

        //Get menus
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        //Select a customer, make sure a valid address is populated
        NetsuiteSCSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
        //Check the “Enable Item Line Shipping” checkbox under items Subtab
        salesOrderPage.enableLineItemShipping();
        //Enter few items and select shipping address on each item and save the order
        setupBasicOrder(salesOrderPage, customer, itemOne, itemTwo );
        salesOrderPage.calculateShippingCost();
        salesOrderPage.saveOrder();
        //Verify there is a tax associated with the order
        checkOrderHasTax(salesOrderPage);
        //Click on the call details and verify the total tax on the response is same as the tax on the sales order.
        //Verify the “Vertex Tax Error code” field is showing Success.
        salesOrderPage.editOrder();
        salesOrderPage.deleteOrder();
    }

    /**
     * Certifies Location Codes work properly
     * CNSL-922
     */
    @Ignore
    @Test(groups = { "netsuite_cert_sc"})
    protected void locationCodeTests (){

    }

    /**
     * Certifies you can Edit Invoices properly
     * CNSL-923
     */
    @Test(groups = { "netsuite_cert_sc"})
    protected void editInvoiceTests ()
    {
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_SUPPLY_CA;
        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.CHLOROFORM_ITEM)
                .quantity("1")
                .amount("100.00")
                .build();
        NetsuiteItem itemTwo = NetsuiteItem
                .builder(NetsuiteItemName.BLACKBERRY_PLAYBOOK)
                .quantity("1")
                .amount("499.00")
                .build();

        NetsuiteAddress addressOne = NetsuiteAddress
                .builder("94513")
                .fullAddressLine1("18 Oak St")
                .addressLine1("18 Oak St")
                .city("Brentwood")
                .state(State.CA)
                .country(Country.USA)
                .zip9("94513-7500")
                .build();

        String transactionNumber;

        NetsuitePrices expectedPricesBeforeSaving = NetsuitePrices
                .builder()
                .itemTaxRate("7.0%")
                .itemTaxCode("CA-STATE")
                .subtotal("100.00")
                .taxAmount("7.00")
                .total("107.00")
                .build();
        NetsuitePrices expectedPricesAfterSaving = NetsuitePrices
                .builder()
                .itemTaxRate("7.0%")
                .itemTaxCode("CA-STATE")
                .subtotal("100.00")
                .taxAmount("7.00")
                .total("107.00")
                .build();
        NetsuitePrices editedPrices = NetsuitePrices
                .builder()
                .itemTaxRate("7.0%")
                .itemTaxCode("CA-STATE")
                .subtotal("499.00")
                .taxAmount("34.93")
                .total("533.93")
                .build();

        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
        NetsuiteNavigationMenus searchSalesOrderMenu = getTransactionSearchMenu();

        NetsuiteSetupManagerPage setupManagerPage = configureSettings();

        NetsuiteSCSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);

        transactionNumber = salesOrderPage.getTransactionNumber();
        setupBasicOrder(salesOrderPage, customer, addressOne, itemOne);

        checkBasicOrderAmounts(salesOrderPage, expectedPricesBeforeSaving);
        checkOrderWithTax(salesOrderPage, expectedPricesBeforeSaving, itemOne);

        NetsuiteSCSalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();

        checkBasicOrderAmounts(savedSalesOrderPage, expectedPricesAfterSaving);
        checkOrderWithTax(savedSalesOrderPage, expectedPricesAfterSaving, itemOne);

        NetsuiteSCSalesOrderPage editedSalesOrderPage = savedSalesOrderPage.editOrder();
        editedSalesOrderPage.editItem(itemOne, itemTwo);
        savedSalesOrderPage = editedSalesOrderPage.saveOrder();

        checkBasicOrderAmounts(editedSalesOrderPage, editedPrices);
        checkOrderWithTax(editedSalesOrderPage, editedPrices, itemTwo);

        NetsuiteTransactionsSearchPage searchPage = savedSalesOrderPage.navigationPane.navigateThrough(
                searchSalesOrderMenu);
        searchPage.enterTransactionNumberAndId(transactionNumber);
        NetsuiteTransactionsSearchResultPage searchResultsPage = searchPage.submitSearch();
        editedSalesOrderPage = searchResultsPage.edit(transactionNumber);
        NetsuiteTransactionsPage transactionsPage = editedSalesOrderPage.deleteOrder();
        assertTrue(transactionsPage.isOrderDeleted());
    }
}
