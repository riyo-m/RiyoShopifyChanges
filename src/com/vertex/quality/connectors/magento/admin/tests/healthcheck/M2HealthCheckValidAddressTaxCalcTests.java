package com.vertex.quality.connectors.magento.admin.tests.healthcheck;

import com.vertex.quality.connectors.magento.admin.pages.M2AdminSalesTaxConfigPage;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Tests Connection to Vertex (HealthCheck)
 * CMAGM2-942
 *
 * @author rohit-mogane
 */
public class M2HealthCheckValidAddressTaxCalcTests extends MagentoAdminBaseTest {

    /**
     * tests a HealthCheck - valid address and tax calc
     * CMAGM2-940
     */
    @Test(groups = "magento_smoke")
    public void healthCheckValidAddressTaxCalcTest() {
        M2AdminSalesTaxConfigPage taxSettingsPage = navigateToSalesTaxConfig();
        taxSettingsPage.clickConnectionSettingsTab();
        taxSettingsPage.enterCalculationAPIUrl(MagentoData.CALCULATION_API_URL.data);
        taxSettingsPage.enterAddressValidAPIUrl(MagentoData.ADDRESS_VALIDATION_API_URL.data);
        taxSettingsPage.saveConfig();
        taxSettingsPage.clickConnectionSettingsTab();
        String taxStatusString = taxSettingsPage.getConnectionAPIStatus();
        assertEquals(taxStatusString, MagentoData.VERTEX_API_VALID.data);
    }
}
