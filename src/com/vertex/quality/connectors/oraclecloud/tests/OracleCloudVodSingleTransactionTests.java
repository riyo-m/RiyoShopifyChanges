package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.connectors.oraclecloud.pages.OracleCloudCreateOrderPage;
import com.vertex.quality.connectors.oraclecloud.tests.base.OracleCloudBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class OracleCloudVodSingleTransactionTests extends OracleCloudBaseTest {

    final private String billToAccount = "57004";

    /**
     * Test designed for smoke testing Sales Order Management processing
     * via creation of an OM through the UI on a VOD instance.
     *
     * Jira test: COERPC-3268
     */
    @Test( groups = { "oerpc_vod", "oerpc_batch", "oerpc_om_single" })
    public void omSingleVodTest() {
        final String busUnitInput = "VTX_US_BU";
        final String customer = "MCC Customer 1";
        final String item = "Item1000";
        final String expectedTotal = "1,060.00";

        loadInitialTestPage_ecogdev1();

        signInToHomePage();

        OracleCloudCreateOrderPage createOrderPage = navigateToCreateOrderPage();

        orderHeaderRequiredInfo(createOrderPage,customer,busUnitInput, billToAccount);
        createOrderPage.addItem(item);
        createOrderPage.clickSaveButton();

        String calculatedTotal = createOrderPage.getTotalTransTax();

        assertTrue(expectedTotal.equals(calculatedTotal));
    }
}
