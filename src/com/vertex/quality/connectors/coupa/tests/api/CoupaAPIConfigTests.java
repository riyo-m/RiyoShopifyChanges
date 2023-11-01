package com.vertex.quality.connectors.coupa.tests.api;

import com.vertex.quality.connectors.coupa.tests.api.base.keywords.CoupaConnectorKeywords;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Coupa API configuration tests
 *
 * @author alewis
 */
public class CoupaAPIConfigTests {

    CoupaConnectorKeywords connector = new CoupaConnectorKeywords();

    /**
     * Gets coupa connector version number in log
     *
     * CCOUPA-1489
     */
    @Test(groups = { "coupa", "coupa_api" })
    public void getVersionNumberTest() {
        String version = connector.getVersionNumber();
        assertEquals(version,"1.0.0.13");
    }

    /**
     * Checks to see if database is up through api
     *
     * CCOUPA-1687
     */
    @Test(groups = { "coupa", "coupa_api" })
    public void checkDatabaseUpTest() {
        String status = connector.getDatabaseStatus();
        assertEquals(status,"UP");
    }

    /**
     * Checks to see if database is up through api
     *
     * CCOUPA-1687
     */
    @Test(groups = { "coupa", "coupa_api" })
    public void negativeStatusTest() {
        String status = connector.getDatabaseStatus();
        assertEquals(status,"UP");
    }
}