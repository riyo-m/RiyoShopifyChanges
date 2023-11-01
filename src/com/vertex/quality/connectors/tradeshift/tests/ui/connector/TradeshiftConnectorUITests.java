package com.vertex.quality.connectors.tradeshift.tests.ui.connector;

import com.vertex.quality.connectors.tradeshift.pages.connector.TradeshiftConnectorSignInPage;
import com.vertex.quality.connectors.tradeshift.tests.ui.base.TradeshiftConnectorUIBaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author alewis
 */
public class TradeshiftConnectorUITests extends TradeshiftConnectorUIBaseTest
{

    /**
     * Test to validate logging into the Tradeshift connector
     *
     * CTRADESHI-215
     * */
    @Test(groups = {"tradeshift","tradeshift_smoke"})
    public void verifyLoginTest(){
        TradeshiftConnectorSignInPage signInPage = loadSignOnPage();
        signInToAdmin(signInPage);
    }

    /**
     * Validates the Tradeshift connector version
     *
     * CTRADESHI-212
     * */
    @Test(groups = {"tradeshift"})
    public void verifyVersionNumberTest(){
        String actualVersionNumber = getConnectorVersionNumber();
        String expectedVersionNumber = "1.0.0.1";
        assertEquals(actualVersionNumber,expectedVersionNumber);
    }

    /**
     * Validates the Tradeshift connector database status
     *
     * CTRADESHI-213
     * */
    @Test(groups = {"tradeshift","tradeshift_smoke"})
    public void verifyDBStatusTest(){
        String actualDBStatus = getConnectorDatabaseStatus();
        String expectedDBStatus = "UP";
        assertEquals(actualDBStatus,expectedDBStatus);
    }

    /**
     * Validates the Tradeshift connector tenant status
     *
     * CTRADESHI-214
     * */
    @Test(groups = {"tradeshift","tradeshift_smoke"})
    public void verifyTenantStatusTest(){
        String actualTenantStatus = getConnectorTenantStatus();
        String expectedTenantStatus = "UP";
        assertEquals(actualTenantStatus,expectedTenantStatus);
    }

    /**
     * Validate creation of a new tenant
     * in the Tradeshift connector
     *
     * CTRADESHI-350
     * */
    @Test(groups = {"tradeshift"})
    public void createTenantTest(){
        createTenant();
    }

    /**
     * Validate creation of a request mapping on the Tradshift connector ui
     *
     * CTRADESHI-349
     * */
    @Test(groups = {"tradeshift"})
    public void createRequestMappingTest(){
        createRequestMapping();
    }

    /**
     * Validate that we cannot create a tenant
     * with the same tenant id as one already
     * in the tradeshift connector
     *
     * CTRADESHI-351
     * */
    @Test(groups = {"tradeshift"})
    public void duplicateTenantTest(){
        String actualErrorMessage = duplicateTenant();
        String expectedErrorMessage = "Tenant with identifier 99 already exists. Unable to create duplicate tenant";
        assertEquals(actualErrorMessage,expectedErrorMessage);
    }
    //ticket not created yet
    @Test(groups = {"tradeshift"})
    public void duplicateRequestMappingTest(){
        String actualErrorMessage = duplicateRequestMapping();
        String expectedErrorMessage = "Unknown error occurred. Please contact the administrator.";
        assertEquals(actualErrorMessage,expectedErrorMessage);
    }

    /**
     * Validate creation of a new tenant with UN/ECE codes
     *
     * CTRADESHI-564
     * */
    @Test(groups = {"tradeshift"})
    public void createUnEceTenantTest(){
        assertTrue(createUnEceTenant());
    }

    /**
     * Validate that the UN/ECE codes can be cleared
     * from a tenant
     *
     * CTRADESHI-565
     * */
    @Test(groups = {"tradeshift"})
    public void clearUNECECodeTenantTest(){
        assertTrue(clearTenantCodes());
    }

    /**
     * Validate that default UN/ECE codes can be added
     * to a tenant
     *
     * CTRADESHI-566
     * */
    @Test(groups = {"tradeshift"})
    public void addDefaultUNECECodeTenantTest(){
        assertTrue(createCustomTenantCodes());
    }

    /**
     * Validate that accurals can be enabled
     * on a tenant
     *
     * CTRADESHI-636
     * */
    @Test(groups = {"tradeshift"})
    public void enableAccrualTenantTest(){
        boolean accrualsEnabled = enableTenantAccrual();
        assertTrue(accrualsEnabled);
    }

    /**
     * Validate that raw tax engine request/response can be enabled
     * for a tenant
     *
     * CTRADESHI-745
     * */
    @Test(groups = {"tradeshift"})
    public void enableTaxEngineResponseTenantTest(){
        String taxEngineDropValue = enableTenantTaxEngineResponse();
        String expected = "Enabled";
        assertEquals(expected, taxEngineDropValue);
    }

    /**
     * Validate that raw tax engine request/response can be disabled
     * for a tenant
     *
     * CTRADESHI-744
     * */
    @Test(groups = {"tradeshift"})
    public void disableTaxEngineResponseTenantTest(){
        String taxEngineDropValue = disableTenantTaxEngineResponse();
        String expected = "Disabled";
        assertEquals(expected, taxEngineDropValue);
    }

	/**
	 * Validate that tax codes can be configured
	 * for a tenant
	 *
	 * TICKET
	 * */
	@Test(groups = {"tradeshift"})
	public void configureTaxCodeTenantTest(){
		assertTrue(createTenantWithTaxConfiguration());
	}
}
