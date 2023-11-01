package com.vertex.quality.connectors.netsuite.suiteTax.tests.certification;

import com.vertex.quality.common.enums.Country;
import com.vertex.quality.common.enums.State;
import com.vertex.quality.connectors.netsuite.suiteTax.pages.transactions.NetsuiteAPISalesOrderPage;
import com.vertex.quality.connectors.netsuite.suiteTax.tests.base.NetsuiteBaseAPITest;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pojos.*;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import org.testng.annotations.Test;

public class NetsuiteBasicAPITests extends NetsuiteBaseAPITest {

    /**
     * Certifies we're able to create a Sales Order in Suite Tax
     */
    @Test(groups = { "netsuite_suite_cert","netsuite_suite_smoke"})
    protected void CertifySalesOrderTest  ( )
    {
        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("100.00")
                .build();
        NetsuiteAddress addressOne = NetsuiteAddress
                .builder("91608")
                .fullAddressLine1("100 Universal City Plaza")
                .addressLine1("100 Universal City Plz")
                .city("Universal City")
                .state(State.CA)
                .country(Country.USA)
                .zip9("91608-1002")
                .build();

        //Get menus
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();

        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        NetsuiteAPISalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
        setupBasicOrder(salesOrderPage, customer, itemOne);

        NetsuiteAPISalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();
        //deleteDocument(savedSalesOrderPage);
    }
}