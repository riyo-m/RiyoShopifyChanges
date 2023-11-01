package com.vertex.quality.connectors.netsuite.suiteTax.tests;

import com.vertex.quality.common.enums.Country;
import com.vertex.quality.common.enums.State;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteInvoicePage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteAddress;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuitePrices;
import com.vertex.quality.connectors.netsuite.suiteTax.pages.transactions.NetsuiteAPICreditMemoPage;
import com.vertex.quality.connectors.netsuite.suiteTax.tests.base.NetsuiteBaseVatTest;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import org.testng.annotations.Test;

/**
 * Tests Transactions
 *
 * @author mwilliams
 */
public class SuiteTaxTransactionTests extends NetsuiteBaseVatTest {

    /**
     * Checks Distribute Tax Message Functionality
     * CNSAPI-928
     */
    @Test(groups = {"suite_tax_regression"})
    protected void distributeTaxMessageTest() {

        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1").amount("100.00").location("01: San Francisco").taxDetailCode("CA State").taxRate("10")
				.taxBasis("1").taxAmt("10")
				.build();
        NetsuiteAddress addressOne = NetsuiteAddress
                .builder("91608")
                .fullAddressLine1("100 Universal City Plaza")
                .addressLine1("100 Universal City Plz").city("Universal City")
                .state(State.CA).country(Country.USA).zip9("91608-1002")
                .build();
        //Get menus
        NetsuiteNavigationMenus invoiceMenu = getCreateInvoiceMenu();
        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        NetsuiteInvoicePage invoicePage = setupManagerPage.navigationPane.navigateThrough(invoiceMenu);
        //Enter an Item
        setupBasicInvoice(invoicePage, customer, addressOne, itemOne);
        //Set Distribute Tax and Tax Override checkbox
        invoicePage.setDistributeTax();
        invoicePage.setTaxDetailsOverride();
        //Add a Tax detail
        addTaxDetailToInvoice(invoicePage, itemOne);
        invoicePage.saveOrder();

        getDistributedTaxValue(invoicePage, "10.00");

        //invoicePage.editOrder();
        //invoicePage.deleteOrder();
    }

    /**
     * Testing Credit Memo Feature
     * CNSAPI-929
	 * @author mwilliams
     */
    @Test(groups = {"netsuite_suite_smoke"})
    protected void taxAdjustmentTests() {

        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_SUPPLY_CA;
        NetsuiteItem item = NetsuiteItem
                .builder(NetsuiteItemName.SALES_TAX_ONLY)
                .quantity("1").amount("100.00").memoLocation("01: San Francisco")
                .build();
        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1").amount("100.00").location("01: San Francisco").taxDetailCode("CA State")
                .taxRate("10").taxBasis("1").taxAmt("10")
                .build();
        NetsuiteAddress address = NetsuiteAddress
                .builder("91608").fullAddressLine1("100 Universal City Plaza")
                .addressLine1("100 Universal City Plz").city("Universal City")
				.state(State.CA).country(Country.USA).zip9("91608-1002")
                .build();

        NetsuitePrices expectedPricesBeforeSaving = NetsuitePrices
                .builder()
                .itemTaxRate("0.0%").subtotal("100.00").taxAmount("7.75").total("100.00")
                .build();

        //Get menus
        NetsuiteNavigationMenus creditMemoMenu = getCreditMemoMenu();

        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        NetsuiteAPICreditMemoPage creditMemoPage = setupManagerPage.navigationPane.navigateThrough(creditMemoMenu);

        //Add Tax Adjustment Item and enter an amount
        setupBasicCreditMemo(creditMemoPage, customer, null, item);

        //Verify the VERTEX DISTRIBUTE TAX checkbox is checked and a distributeTaxRequest is posted to Vertex.
        creditMemoPage.setDistributeTax();
        creditMemoPage.setTaxDetailsOverride();

        addTaxDetailToInvoice(creditMemoPage, itemOne);
        //Save the Credit Memo
        creditMemoPage.saveOrder();
        getDistributedTaxValue(creditMemoPage, "10.00");
    }

    /**
     * Verify Item level Flex Fields are functioning properly
     * using the Flex Field "Class"
	 * @author mwilliams
     * CNSAPI-1392  (Ref: CORST-180)
     */
    @Test(groups = {"netsuite_suite_smoke"})
    protected void lineItemFlexFieldTest() {

        //Define Customers, Items, etc...
        itemClass = "Accessories";
        taxRate = .095;
        super.baseTest();
    }
}