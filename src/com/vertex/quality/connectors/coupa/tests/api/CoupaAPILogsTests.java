package com.vertex.quality.connectors.coupa.tests.api;

import com.vertex.quality.connectors.coupa.tests.api.base.keywords.CoupaMessageLogKeywords;
import org.testng.annotations.Test;

/**
 * Coupa API Logs tests
 *
 * @author alewis
 */
public class CoupaAPILogsTests {

    CoupaMessageLogKeywords logs = new CoupaMessageLogKeywords();

    /**
     * checks to see if getLogs request gets payload
     *
     * CCOUPA-1688
     */
    @Test(groups = { "coupa", "coupa_api" })
    public void getPayloadLogTest() {
        String payload = logs.getLogs();
    }
}