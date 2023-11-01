package com.vertex.quality.connectors.magentoTap.admin.tests.healthcheck;

import com.vertex.quality.connectors.magentoTap.admin.pages.M2AdminSalesTaxConfigPage;
import com.vertex.quality.connectors.magentoTap.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magentoTap.common.enums.MagentoData;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * Tests Connection to Vertex (HealthCheck)
 * CCMMER2-24
 *
 * @author Shivam.Soni
 */
public class MagentoTapHealthCheckInValidAddressTaxCalcTests extends MagentoAdminBaseTest {

    M2AdminSalesTaxConfigPage taxSettingsPage;

    /**
     * CMTAP-32 CMTAP - Test Case - Perform a Healthcheck - Invalid address URL
     */
    @Test(groups = "magentoTap_smoke")
    public void healthCheckInValidApiTokenTest() {
        try {
            taxSettingsPage = navigateToSalesTaxConfig();
            taxSettingsPage.clickOnTaxamoTab();
            taxSettingsPage.enterAPIToken(MagentoData.INVALID_API_TOKEN.data);
            taxSettingsPage.clickSaveAndTestCredentialsButton();

            assertEquals(taxSettingsPage.getHealthCheckMSGFromUI(), MagentoData.INVALID_HEALTH_CHECK_MSG.data);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed due to Error/ Exception, Kindly check the logs.");
        } finally {
            quitDriver();
            createChromeDriver();

            taxSettingsPage = navigateToSalesTaxConfig();
            taxSettingsPage.clickOnTaxamoTab();
            taxSettingsPage.enterAPIToken(MagentoData.VALID_API_TOKEN.data);
            taxSettingsPage.clickSaveAndTestCredentialsButton();
        }
    }

    /**
     * CMTAP-33 CMTAP - Test Case - Perform a Healthcheck - valid address and tax calc
     */
    @Test(groups = "magentoTap_smoke")
    public void healthCheckValidApiTokenTest() {
        taxSettingsPage = navigateToSalesTaxConfig();
        taxSettingsPage.clickOnTaxamoTab();
        taxSettingsPage.enterAPIToken(MagentoData.VALID_API_TOKEN.data);
        taxSettingsPage.clickSaveAndTestCredentialsButton();

        assertEquals(taxSettingsPage.getHealthCheckMSGFromUI(), MagentoData.VALID_HEALTH_CHECK_MSG.data);
    }
}
