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
public class M2HealthCheckInValidAddressTaxCalcTests extends MagentoAdminBaseTest {

    /**
     * tests a HealthCheck - Invalid address and tax calc
     * CMAGM2-941
     */
    @Test(groups = "magento_smoke")
    public void healthCheckInValidAddressTaxCalcTest() {
        M2AdminSalesTaxConfigPage taxSettingsPage = navigateToSalesTaxConfig();
        taxSettingsPage.clickConnectionSettingsTab();
        taxSettingsPage.enterCalculationAPIUrl(MagentoData.CALCULATION_INVALID_URL.data);
        taxSettingsPage.saveConfig();
        checkAPIStatus(taxSettingsPage, MagentoData.VERTEX_URL_CALC.data);
        resetStatus(taxSettingsPage);
        taxSettingsPage.clickConnectionSettingsTab();
        taxSettingsPage.enterAddressValidAPIUrl(MagentoData.ADDRESS_VALIDATION_INVALID_URL.data);
        taxSettingsPage.saveConfig();
        checkAPIStatus(taxSettingsPage, MagentoData.VERTEX_URL_ADDRESS.data);
        resetStatus(taxSettingsPage);
    }

    /**
     * check API status valid or invalid
     *
     * @param taxSettingsPage which is object of M2AdminSalesTaxConfigPage
     * @param urlType         which is type of API url
     */
    private void checkAPIStatus(M2AdminSalesTaxConfigPage taxSettingsPage, String urlType) {
        taxSettingsPage.clickConnectionSettingsTab();
        String taxStatusString = taxSettingsPage.getConnectionAPIStatus();
        if (urlType.equals(MagentoData.VERTEX_URL_CALC.data)) {
            assertEquals(taxStatusString, MagentoData.VERTEX_CACL_API_INVALID.data);
        } else {
            assertEquals(taxStatusString, MagentoData.VERTEX_ADD_API_INVALID.data);
        }
    }

    /**
     * reset API status to valid
     *
     * @param taxSettingsPage which is object of M2AdminSalesTaxConfigPage
     */
    private void resetStatus(M2AdminSalesTaxConfigPage taxSettingsPage) {
        taxSettingsPage.enterCalculationAPIUrl(MagentoData.CALCULATION_API_URL.data);
        taxSettingsPage.enterCalculationAPIUrl(MagentoData.ADDRESS_VALIDATION_API_URL.data);
        taxSettingsPage.saveConfig();
    }
}
