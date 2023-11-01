package com.vertex.quality.connectors.netsuite.NetsuiteCertification.SingleCompanyCertification.tests;

import com.vertex.quality.connectors.netsuite.NetsuiteCertification.SingleCompanyCertification.tests.base.SCCertBaseTest;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.singlecompany.pages.transactions.NetsuiteSCSalesOrderPage;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Certifies that basic functions still work in new Release Environment
 *
 * @author mwilliams
 */
public class SCCertBasicTests extends SCCertBaseTest {

    /**
     * Certifies we're able to create a Sales Order in One World
     */
    @Test(groups = { "netsuite_cert"})
    protected void CertifySalesOrderTest  ( )
    {
        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.TEST_AUTOMATION_CUSTOMER_1;
        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.TEST_PRODUCT_CLASS)
                .quantity("1")
                .amount("10.00")
                .build();
        //Get menus
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();

        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        NetsuiteSCSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
        setupBasicOrder(salesOrderPage, customer, itemOne);

        NetsuiteSCSalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();
        assertTrue(savedSalesOrderPage.wasOrderSaved());
        savedSalesOrderPage.editOrder();
        savedSalesOrderPage.deleteOrder();
    }
}
