package com.vertex.quality.connectors.netsuite.oneworld.tests;

import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteHomepage;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pages.transactions.NetsuiteTransactionsPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuitePrices;
import com.vertex.quality.connectors.netsuite.oneworld.pages.NetsuiteOWGeneralPreferencesPage;
import com.vertex.quality.connectors.netsuite.oneworld.pages.transactions.NetsuiteOWSalesOrderPage;
import com.vertex.quality.connectors.netsuite.oneworld.tests.base.NetsuiteBaseOWTest;
import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;

public class NetsuiteSCISTests extends NetsuiteBaseOWTest {

    /**
     * Tests to ensure we can navigate to SCIS webstore
     * CNSL-1688
     */
    @Test(groups = { "scis_smoke" })
    protected void basicSCISTest( )
    {
        NetsuiteNavigationMenus generalPreferencesMenu = getGeneralPreferencesMenu();
        NetsuiteHomepage homepage = signintoHomepageAsOneWorld();

        // Should navigate to -> [webstoreUrl]
        // webstoreUrl should be based on the selected User Role oe "Environment"
        driver.navigate().to("http://scis.vertex.com");
    }

}