package com.vertex.quality.connectors.netsuite.singlecompany.tests.certification;

import com.vertex.quality.common.enums.Country;
import com.vertex.quality.common.enums.State;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteAddress;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuitePrices;
import com.vertex.quality.connectors.netsuite.oneworld.pages.transactions.NetsuiteOWInvoicePage;
import com.vertex.quality.connectors.netsuite.singlecompany.pages.transactions.NetsuiteSCCreditMemoPage;
import com.vertex.quality.connectors.netsuite.singlecompany.pages.transactions.NetsuiteSCInvoicePage;
import com.vertex.quality.connectors.netsuite.singlecompany.tests.base.SCCertBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ScTaxTests extends SCCertBaseTest {

    /**
     * Certifies Tax Preview Feature
     * CNSL-916
     */
    @Test(groups = {"netsuite_cert_sc"})
    protected void taxPreviewFeatureTests() {

        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_SUPPLY_CA;
        NetsuiteItem item = NetsuiteItem
                .builder(NetsuiteItemName.CHLOROFORM_ITEM)
                .quantity("1")
                .amount("100.00")
                .taxCode(defaultTaxCode)
                .location("San Francisco")
                .build();
        NetsuiteAddress address = NetsuiteAddress
                .builder("94134")
                .fullAddressLine1("100 University St")
                .addressLine1("100 University St")
                .city("San Francisco")
                .state(State.CA)
                .country(Country.USA)
                .zip9("94134-1302")
                .build();
        NetsuitePrices ExpectedValue = NetsuitePrices.builder()
                .itemTaxRate("8.75%")
                .itemTaxCode(defaultTaxCode)
                .subtotal("100.00")
                .taxAmount("8.75")
                .total("108.75").build();

        //Get menus
        NetsuiteNavigationMenus invoiceMenu = getCreateInvoiceMenu();
        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        //Select a customer, make sure a valid address is populated
        NetsuiteSCInvoicePage invoicePage = setupManagerPage.navigationPane.navigateThrough(invoiceMenu);
        //Enter few items and select shipping address on each item and save the invoice
        setupBasicOrderItemList(invoicePage, customer, address, item);
        //Set post to Vertex
        invoicePage.setVertexPostToVertex();
        //Preview Tax and retrieve the values
        String[] previewValues = invoicePage.previewTax();

        Assert.assertEquals(previewValues[0], ExpectedValue.getTaxAmount());
        Assert.assertEquals(previewValues[1], ExpectedValue.getTotal());
    }

    /**
     * Certifies Tax Adjustment Feature
     * CNSL-917
     */
    @Test(groups = {"netsuite_cert_sc"})
    protected void taxAdjustmentTests() {

        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_SUPPLY_CA;
        NetsuiteItem item = NetsuiteItem
                .builder(NetsuiteItemName.SALES_TAX_ONLY)
                .quantity("1")
                .amount("100.00")
                .taxCode(defaultTaxCode)
                .location("San Francisco")
                .build();
        NetsuiteAddress address = NetsuiteAddress
                .builder("94134")
                .fullAddressLine1("100 University St")
                .addressLine1("100 University St")
                .city("San Francisco")
                .state(State.CA)
                .country(Country.USA)
                .zip9("94134-1302")
                .build();
        NetsuitePrices ExpectedValue = NetsuitePrices.builder()
                .itemTaxRate("7.00%")
                .itemTaxCode(caliTaxCode)
                .subtotal("100.00")
                .taxAmount("7.00")
                .total("107.00").build();

        String postingPeriod = "Dec 2018";
        String transactionNumber;

        NetsuitePrices expectedPricesBeforeSaving = NetsuitePrices
                .builder()
                .itemTaxRate("0.0%")
                .itemTaxCode(caliTaxCode)
                .subtotal("100.00")
                .taxAmount("0.00")
                .total("107.00")
                .build();
        NetsuitePrices expectedPricesAfterSaving = NetsuitePrices
                .builder()
                .itemTaxRate("7.0%")
                .itemTaxCode(caliTaxCode)
                .subtotal("100.00")
                .taxAmount("7.00")
                .total("107.00")
                .build();
        //Check if Tax Adjustment Item exists in the account by entering “Tax Only Adjustment” in the global search box. If the item exists, it will show up in the results.
        //If it doesn't exist, create a new Non-Inventory Item for Sale called Tax Only Adjustment. This will be used for US Tax adjustments

        //Get menus
        NetsuiteNavigationMenus creditMemoMenu = getCreditMemoMenu();
        NetsuiteNavigationMenus searchCreditMemoMenu = getTransactionSearchMenu();
        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        NetsuiteSCCreditMemoPage creditMemoPage = setupManagerPage.navigationPane.navigateThrough(creditMemoMenu);
        //Add Tax Adjustment Item and enter an amount
        transactionNumber = creditMemoPage.getTransactionNumber();
        //setupBasicCreditMemo(creditMemoPage, customer, address, item);
        setupBasicOrderItemList(creditMemoPage, customer, address, item);
        creditMemoPage.selectPostingPeriod(postingPeriod);
        //Verify the VERTEX DISTRIBUTE TAX checkbox is checked and a distributeTaxRequest is posted to Vertex.
        creditMemoPage.setVertexPostToVertex();
        creditMemoPage.setDistributeTax();

        checkBasicOrderAmounts(creditMemoPage, expectedPricesBeforeSaving);
        checkOrderWithTax(creditMemoPage, expectedPricesAfterSaving, item);
        //Save the Credit Memo
        NetsuiteSCCreditMemoPage savedCreditMemoPage = creditMemoPage.saveOrder();

        NetsuiteSCCreditMemoPage editedCreditMemoPage = savedCreditMemoPage.editOrder();

        editedCreditMemoPage.deleteMemo();
    }

    /**
     * Certifies Tax Adjustment Feature
     * CNSL-918
     */
    @Test(groups = {"netsuite_cert_sc"})
    protected void distributeTaxTests() {
        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_SUPPLY_CA;
        NetsuiteItem item = NetsuiteItem
                .builder(NetsuiteItemName.CHLOROFORM_ITEM)
                .quantity("1")
                .amount("100.00")
                .taxCode(defaultTaxCode)
                .location("San Francisco")
                .build();
        NetsuiteAddress address = NetsuiteAddress
                .builder("94513")
                .fullAddressLine1("18 Oak St")
                .addressLine1("18 Oak St")
                .city("Brentwood")
                .state(State.CA)
                .country(Country.USA)
                .zip9("94513-7500")
                .build();
        NetsuitePrices ExpectedValue = NetsuitePrices.builder()
                .itemTaxRate("8.25%")
                .itemTaxCode(defaultTaxCode)
                .subtotal("100.00")
                .taxAmount("8.25")
                .total("108.25").build();
        String location = "San Francisco";

        //Get menus
        NetsuiteNavigationMenus InvoiceMenu = getCreateInvoiceMenu();
        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        NetsuiteSCInvoicePage InvoicePage = setupManagerPage.navigationPane.navigateThrough(InvoiceMenu);
        //Add Tax Adjustment Item and enter an amount
        setupBasicOrderItemList(InvoicePage, customer, address, item);
        InvoicePage.setLocation(item.getLocation());
        //Verify the VERTEX DISTRIBUTE TAX checkbox is checked and a distributeTaxRequest is posted to Vertex.
        InvoicePage.setVertexPostToVertex();
        InvoicePage.setDistributeTax();

        //Save the Invoice
        NetsuiteSCInvoicePage savedInvoicePage = InvoicePage.saveOrder();
        //****Verify Distribute Tax request
        NetsuiteSCInvoicePage editInvoicePage = savedInvoicePage.editOrder();

        savedInvoicePage.deleteOrder();
    }
}