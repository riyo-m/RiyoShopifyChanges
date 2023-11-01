package com.vertex.quality.connectors.kibo.tests.healthCheck;

import com.vertex.quality.connectors.kibo.enums.OSeriesData;
import com.vertex.quality.connectors.kibo.pages.KiboVertexConnectorPage;
import com.vertex.quality.connectors.kibo.tests.base.KiboTaxCalculationBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Kibo health-check test cases
 *
 * @author Shivam.Soni
 */
public class KiboHealthCheckTests extends KiboTaxCalculationBaseTest {

    KiboVertexConnectorPage connectorPage;

    /**
     * CKIBO-745 Test Case - Perform a Health check - Invalid address URL
     */
    @Test(groups = {"kibo_smoke"})
    public void healthCheckWithInvalidUrlTest() {
        try {
            // Go to configuration page.
            gotoVertexConfiguration();

            // Select connection tab
            connectorPage = new KiboVertexConnectorPage(driver);
            connectorPage.configurationDialog.gotoConnection();

            // Enter invalid url for Tax Calculation & Validate connection
            connectorPage.configurationDialog.enterTaxationUrl(OSeriesData.BAD_URL.data);
            connectorPage.configurationDialog.clickValidateConnection();

            // Assertion for invalid Tax-Calc url
            assertEquals(connectorPage.configurationDialog.getErrorMessage(), OSeriesData.TAX_CALC_BAD_URL_VALIDATION_MSG.data);

            // Enter valid url for Tax Calculation & close config-popup
            connectorPage.configurationDialog.enterTaxationUrl(OSeriesData.O_SERIES_TAX_CALCULATION_URL.data);
            connectorPage.configurationDialog.clickSaveButton();
            connectorPage.configurationDialog.closeConfigAppPopup();

            // Open config-page & select connection tab
            connectorPage.clickConfigureApplicationButton();
            connectorPage.configurationDialog.gotoConnection();

            // Enter invalid url for address-cleansing and validate connection
            connectorPage.configurationDialog.enterCleansingUrl(OSeriesData.BAD_URL.data);
            connectorPage.configurationDialog.clickValidateConnection();

            // Assertion for invalid Address-Cleansing url
            assertEquals(connectorPage.configurationDialog.getErrorMessage(), OSeriesData.ADDRESS_CLEANSING_BAD_URL_VALIDATION_MSG.data);

            // Enter valid url for Address-Cleansing & close config-popup
            connectorPage.configurationDialog.enterCleansingUrl(OSeriesData.O_SERIES_ADDRESS_CLEANSING_URL.data);
            connectorPage.configurationDialog.clickSaveButton();
            connectorPage.configurationDialog.closeConfigAppPopup();

            // Open config-page & select connection tab
            connectorPage.clickConfigureApplicationButton();
            connectorPage.configurationDialog.gotoConnection();

            // Enter invalid url for tax-calc & address-cleansing and validate connection
            connectorPage.configurationDialog.enterTaxationUrl(OSeriesData.BAD_URL.data);
            connectorPage.configurationDialog.enterCleansingUrl(OSeriesData.BAD_URL.data);
            connectorPage.configurationDialog.clickValidateConnection();

            // Assertion for invalid Tax-Calc & Address-Cleansing url
            assertEquals(connectorPage.configurationDialog.getErrorMessage(), OSeriesData.TAX_CALC_BAD_URL_VALIDATION_MSG.data);

            // Remove invalid urls, enter valid urls & save config.
            enterOrRemoveBadUrlsForTaxAndCleansing(false, false);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            // Refresh page to avoid hurdles in execution of post-condition.
            connectorPage = new KiboVertexConnectorPage(driver);
            connectorPage.refreshPage();

            // Go to configuration page.
            gotoVertexConfiguration();

            // Restore valid urls for tax calculation & address cleansing
            enterOrRemoveBadUrlsForTaxAndCleansing(false, false);
            connectorPage.configurationDialog.closeConfigAppPopup();
        }
    }

    /**
     * CKIBO-746 Test Case - Perform a Health check - valid address and tax calc
     */
    @Test(groups = {"kibo_smoke"})
    public void healthCheckWithValidUrlTest() {
        // Go to configuration page.
        gotoVertexConfiguration();

        // Select connection tab
        connectorPage = new KiboVertexConnectorPage(driver);
        connectorPage.configurationDialog.gotoConnection();

        // Enter valid url for Tax Calculation
        connectorPage.configurationDialog.enterTaxationUrl(OSeriesData.O_SERIES_TAX_CALCULATION_URL.data);

        // Enter valid url for Address-Cleansing
        connectorPage.configurationDialog.enterCleansingUrl(OSeriesData.O_SERIES_ADDRESS_CLEANSING_URL.data);

        // Validate connection
        connectorPage.configurationDialog.clickValidateConnection();

        // Assertion for success connection
        assertTrue(connectorPage.configurationDialog.verifySuccessConnection());
    }
}
