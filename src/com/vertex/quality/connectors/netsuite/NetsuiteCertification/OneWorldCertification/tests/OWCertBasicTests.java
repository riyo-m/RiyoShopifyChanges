package com.vertex.quality.connectors.netsuite.NetsuiteCertification.OneWorldCertification.tests;

import com.vertex.quality.connectors.netsuite.NetsuiteCertification.OneWorldCertification.tests.base.OWCertBaseTest;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.oneworld.pages.transactions.NetsuiteOWSalesOrderPage;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Certifies that basic functions still work in new Release Environment
 *
 * @author mwilliams
 */
public class OWCertBasicTests extends OWCertBaseTest {

    /**
     * Certifies we're able to create a Sales Order in One World
     */
    @Test(groups = { "netsuite_cert"})
    protected void CertifySalesOrderTest  ( )
    {
        //Define Customers and Items
        NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
        NetsuiteItem itemOne = NetsuiteItem
                .builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1")
                .amount("100.00")
                .build();
        //Get menus
        NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();

        //Execute test
        NetsuiteSetupManagerPage setupManagerPage = configureSettings();
        NetsuiteOWSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
        setupBasicOrder(salesOrderPage, customer, itemOne);

        NetsuiteOWSalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();
        deleteDocument(savedSalesOrderPage);
    }
}
